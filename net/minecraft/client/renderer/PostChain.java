/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonSyntaxException;
/*     */ import com.mojang.blaze3d.pipeline.RenderTarget;
/*     */ import com.mojang.blaze3d.pipeline.TextureTarget;
/*     */ import com.mojang.blaze3d.shaders.Uniform;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.texture.AbstractTexture;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.ChainedJsonException;
/*     */ import net.minecraft.server.packs.resources.Resource;
/*     */ import net.minecraft.server.packs.resources.ResourceManager;
/*     */ import net.minecraft.util.GsonHelper;
/*     */ import org.joml.Matrix4f;
/*     */ 
/*     */ 
/*     */ public class PostChain
/*     */   implements AutoCloseable
/*     */ {
/*     */   private static final String MAIN_RENDER_TARGET = "minecraft:main";
/*     */   private final RenderTarget screenTarget;
/*     */   private final ResourceManager resourceManager;
/*     */   private final String name;
/*  37 */   private final List<PostPass> passes = Lists.newArrayList();
/*  38 */   private final Map<String, RenderTarget> customRenderTargets = Maps.newHashMap();
/*  39 */   private final List<RenderTarget> fullSizedTargets = Lists.newArrayList();
/*     */   
/*     */   private Matrix4f shaderOrthoMatrix;
/*     */   private int screenWidth;
/*     */   private int screenHeight;
/*     */   private float time;
/*     */   private float lastStamp;
/*     */   
/*     */   public PostChain(TextureManager $$0, ResourceManager $$1, RenderTarget $$2, ResourceLocation $$3) throws IOException, JsonSyntaxException {
/*  48 */     this.resourceManager = $$1;
/*  49 */     this.screenTarget = $$2;
/*  50 */     this.time = 0.0F;
/*  51 */     this.lastStamp = 0.0F;
/*  52 */     this.screenWidth = $$2.viewWidth;
/*  53 */     this.screenHeight = $$2.viewHeight;
/*  54 */     this.name = $$3.toString();
/*  55 */     updateOrthoMatrix();
/*     */     
/*  57 */     load($$0, $$3);
/*     */   }
/*     */   
/*     */   private void load(TextureManager $$0, ResourceLocation $$1) throws IOException, JsonSyntaxException {
/*  61 */     Resource $$2 = this.resourceManager.getResourceOrThrow($$1); 
/*  62 */     try { Reader $$3 = $$2.openAsReader(); 
/*  63 */       try { JsonObject $$4 = GsonHelper.parse($$3);
/*     */         
/*  65 */         if (GsonHelper.isArrayNode($$4, "targets")) {
/*  66 */           JsonArray $$5 = $$4.getAsJsonArray("targets");
/*  67 */           int $$6 = 0;
/*     */           
/*  69 */           for (JsonElement $$7 : $$5) {
/*     */             try {
/*  71 */               parseTargetNode($$7);
/*  72 */             } catch (Exception $$8) {
/*  73 */               ChainedJsonException $$9 = ChainedJsonException.forException($$8);
/*  74 */               $$9.prependJsonKey("targets[" + $$6 + "]");
/*  75 */               throw $$9;
/*     */             } 
/*  77 */             $$6++;
/*     */           } 
/*     */         } 
/*     */         
/*  81 */         if (GsonHelper.isArrayNode($$4, "passes")) {
/*  82 */           JsonArray $$10 = $$4.getAsJsonArray("passes");
/*  83 */           int $$11 = 0;
/*     */           
/*  85 */           for (JsonElement $$12 : $$10) {
/*     */             try {
/*  87 */               parsePassNode($$0, $$12);
/*  88 */             } catch (Exception $$13) {
/*  89 */               ChainedJsonException $$14 = ChainedJsonException.forException($$13);
/*  90 */               $$14.prependJsonKey("passes[" + $$11 + "]");
/*  91 */               throw $$14;
/*     */             } 
/*  93 */             $$11++;
/*     */           } 
/*     */         } 
/*  96 */         if ($$3 != null) $$3.close();  } catch (Throwable throwable) { if ($$3 != null) try { $$3.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (Exception $$15)
/*  97 */     { ChainedJsonException $$16 = ChainedJsonException.forException($$15);
/*  98 */       $$16.setFilenameAndFlush($$1.getPath() + " (" + $$1.getPath() + ")");
/*  99 */       throw $$16; }
/*     */   
/*     */   }
/*     */   
/*     */   private void parseTargetNode(JsonElement $$0) throws ChainedJsonException {
/* 104 */     if (GsonHelper.isStringValue($$0)) {
/* 105 */       addTempTarget($$0.getAsString(), this.screenWidth, this.screenHeight);
/*     */     } else {
/* 107 */       JsonObject $$1 = GsonHelper.convertToJsonObject($$0, "target");
/*     */       
/* 109 */       String $$2 = GsonHelper.getAsString($$1, "name");
/* 110 */       int $$3 = GsonHelper.getAsInt($$1, "width", this.screenWidth);
/* 111 */       int $$4 = GsonHelper.getAsInt($$1, "height", this.screenHeight);
/*     */       
/* 113 */       if (this.customRenderTargets.containsKey($$2)) {
/* 114 */         throw new ChainedJsonException($$2 + " is already defined");
/*     */       }
/* 116 */       addTempTarget($$2, $$3, $$4);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void parsePassNode(TextureManager $$0, JsonElement $$1) throws IOException {
/* 122 */     JsonObject $$2 = GsonHelper.convertToJsonObject($$1, "pass");
/*     */     
/* 124 */     String $$3 = GsonHelper.getAsString($$2, "name");
/* 125 */     String $$4 = GsonHelper.getAsString($$2, "intarget");
/* 126 */     String $$5 = GsonHelper.getAsString($$2, "outtarget");
/* 127 */     RenderTarget $$6 = getRenderTarget($$4);
/* 128 */     RenderTarget $$7 = getRenderTarget($$5);
/*     */     
/* 130 */     if ($$6 == null) {
/* 131 */       throw new ChainedJsonException("Input target '" + $$4 + "' does not exist");
/*     */     }
/* 133 */     if ($$7 == null) {
/* 134 */       throw new ChainedJsonException("Output target '" + $$5 + "' does not exist");
/*     */     }
/*     */     
/* 137 */     PostPass $$8 = addPass($$3, $$6, $$7);
/*     */     
/* 139 */     JsonArray $$9 = GsonHelper.getAsJsonArray($$2, "auxtargets", null);
/* 140 */     if ($$9 != null) {
/* 141 */       int $$10 = 0;
/* 142 */       for (JsonElement $$11 : $$9) {
/*     */         try {
/* 144 */           boolean $$17; String $$18; JsonObject $$12 = GsonHelper.convertToJsonObject($$11, "auxtarget");
/* 145 */           String $$13 = GsonHelper.getAsString($$12, "name");
/* 146 */           String $$14 = GsonHelper.getAsString($$12, "id");
/*     */ 
/*     */           
/* 149 */           if ($$14.endsWith(":depth")) {
/* 150 */             boolean $$15 = true;
/* 151 */             String $$16 = $$14.substring(0, $$14.lastIndexOf(':'));
/*     */           } else {
/* 153 */             $$17 = false;
/* 154 */             $$18 = $$14;
/*     */           } 
/* 156 */           RenderTarget $$19 = getRenderTarget($$18);
/*     */           
/* 158 */           if ($$19 == null) {
/* 159 */             if ($$17) {
/* 160 */               throw new ChainedJsonException("Render target '" + $$18 + "' can't be used as depth buffer");
/*     */             }
/*     */ 
/*     */             
/* 164 */             ResourceLocation $$20 = new ResourceLocation("textures/effect/" + $$18 + ".png");
/* 165 */             this.resourceManager.getResource($$20).orElseThrow(() -> new ChainedJsonException("Render target or texture '" + $$0 + "' does not exist"));
/*     */             
/* 167 */             RenderSystem.setShaderTexture(0, $$20);
/*     */             
/* 169 */             $$0.bindForSetup($$20);
/* 170 */             AbstractTexture $$21 = $$0.getTexture($$20);
/* 171 */             int $$22 = GsonHelper.getAsInt($$12, "width");
/* 172 */             int $$23 = GsonHelper.getAsInt($$12, "height");
/* 173 */             boolean $$24 = GsonHelper.getAsBoolean($$12, "bilinear");
/* 174 */             if ($$24) {
/* 175 */               RenderSystem.texParameter(3553, 10241, 9729);
/* 176 */               RenderSystem.texParameter(3553, 10240, 9729);
/*     */             } else {
/* 178 */               RenderSystem.texParameter(3553, 10241, 9728);
/* 179 */               RenderSystem.texParameter(3553, 10240, 9728);
/*     */             } 
/* 181 */             Objects.requireNonNull($$21); $$8.addAuxAsset($$13, $$21::getId, $$22, $$23);
/*     */           }
/* 183 */           else if ($$17) {
/* 184 */             Objects.requireNonNull($$19); $$8.addAuxAsset($$13, $$19::getDepthTextureId, $$19.width, $$19.height);
/*     */           } else {
/* 186 */             Objects.requireNonNull($$19); $$8.addAuxAsset($$13, $$19::getColorTextureId, $$19.width, $$19.height);
/*     */           }
/*     */         
/* 189 */         } catch (Exception $$25) {
/* 190 */           ChainedJsonException $$26 = ChainedJsonException.forException($$25);
/* 191 */           $$26.prependJsonKey("auxtargets[" + $$10 + "]");
/* 192 */           throw $$26;
/*     */         } 
/* 194 */         $$10++;
/*     */       } 
/*     */     } 
/*     */     
/* 198 */     JsonArray $$27 = GsonHelper.getAsJsonArray($$2, "uniforms", null);
/* 199 */     if ($$27 != null) {
/* 200 */       int $$28 = 0;
/* 201 */       for (JsonElement $$29 : $$27) {
/*     */         try {
/* 203 */           parseUniformNode($$29);
/* 204 */         } catch (Exception $$30) {
/* 205 */           ChainedJsonException $$31 = ChainedJsonException.forException($$30);
/* 206 */           $$31.prependJsonKey("uniforms[" + $$28 + "]");
/* 207 */           throw $$31;
/*     */         } 
/* 209 */         $$28++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void parseUniformNode(JsonElement $$0) throws ChainedJsonException {
/* 215 */     JsonObject $$1 = GsonHelper.convertToJsonObject($$0, "uniform");
/* 216 */     String $$2 = GsonHelper.getAsString($$1, "name");
/* 217 */     Uniform $$3 = ((PostPass)this.passes.get(this.passes.size() - 1)).getEffect().getUniform($$2);
/*     */     
/* 219 */     if ($$3 == null) {
/* 220 */       throw new ChainedJsonException("Uniform '" + $$2 + "' does not exist");
/*     */     }
/*     */     
/* 223 */     float[] $$4 = new float[4];
/* 224 */     int $$5 = 0;
/* 225 */     JsonArray $$6 = GsonHelper.getAsJsonArray($$1, "values");
/*     */     
/* 227 */     for (JsonElement $$7 : $$6) {
/*     */       try {
/* 229 */         $$4[$$5] = GsonHelper.convertToFloat($$7, "value");
/* 230 */       } catch (Exception $$8) {
/* 231 */         ChainedJsonException $$9 = ChainedJsonException.forException($$8);
/* 232 */         $$9.prependJsonKey("values[" + $$5 + "]");
/* 233 */         throw $$9;
/*     */       } 
/* 235 */       $$5++;
/*     */     } 
/*     */     
/* 238 */     switch ($$5) {
/*     */ 
/*     */ 
/*     */       
/*     */       case 1:
/* 243 */         $$3.set($$4[0]);
/*     */         break;
/*     */       case 2:
/* 246 */         $$3.set($$4[0], $$4[1]);
/*     */         break;
/*     */       case 3:
/* 249 */         $$3.set($$4[0], $$4[1], $$4[2]);
/*     */         break;
/*     */       case 4:
/* 252 */         $$3.set($$4[0], $$4[1], $$4[2], $$4[3]);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   public RenderTarget getTempTarget(String $$0) {
/* 258 */     return this.customRenderTargets.get($$0);
/*     */   }
/*     */   
/*     */   public void addTempTarget(String $$0, int $$1, int $$2) {
/* 262 */     TextureTarget textureTarget = new TextureTarget($$1, $$2, true, Minecraft.ON_OSX);
/* 263 */     textureTarget.setClearColor(0.0F, 0.0F, 0.0F, 0.0F);
/* 264 */     this.customRenderTargets.put($$0, textureTarget);
/*     */     
/* 266 */     if ($$1 == this.screenWidth && $$2 == this.screenHeight) {
/* 267 */       this.fullSizedTargets.add(textureTarget);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/* 273 */     for (RenderTarget $$0 : this.customRenderTargets.values()) {
/* 274 */       $$0.destroyBuffers();
/*     */     }
/* 276 */     for (PostPass $$1 : this.passes) {
/* 277 */       $$1.close();
/*     */     }
/* 279 */     this.passes.clear();
/*     */   }
/*     */   
/*     */   public PostPass addPass(String $$0, RenderTarget $$1, RenderTarget $$2) throws IOException {
/* 283 */     PostPass $$3 = new PostPass(this.resourceManager, $$0, $$1, $$2);
/* 284 */     this.passes.add(this.passes.size(), $$3);
/* 285 */     return $$3;
/*     */   }
/*     */   
/*     */   private void updateOrthoMatrix() {
/* 289 */     this.shaderOrthoMatrix = (new Matrix4f()).setOrtho(0.0F, this.screenTarget.width, 0.0F, this.screenTarget.height, 0.1F, 1000.0F);
/*     */   }
/*     */   
/*     */   public void resize(int $$0, int $$1) {
/* 293 */     this.screenWidth = this.screenTarget.width;
/* 294 */     this.screenHeight = this.screenTarget.height;
/* 295 */     updateOrthoMatrix();
/* 296 */     for (PostPass $$2 : this.passes) {
/* 297 */       $$2.setOrthoMatrix(this.shaderOrthoMatrix);
/*     */     }
/* 299 */     for (RenderTarget $$3 : this.fullSizedTargets) {
/* 300 */       $$3.resize($$0, $$1, Minecraft.ON_OSX);
/*     */     }
/*     */   }
/*     */   
/*     */   public void process(float $$0) {
/* 305 */     if ($$0 < this.lastStamp) {
/* 306 */       this.time += 1.0F - this.lastStamp;
/* 307 */       this.time += $$0;
/*     */     } else {
/* 309 */       this.time += $$0 - this.lastStamp;
/*     */     } 
/* 311 */     this.lastStamp = $$0;
/* 312 */     while (this.time > 20.0F) {
/* 313 */       this.time -= 20.0F;
/*     */     }
/* 315 */     for (PostPass $$1 : this.passes) {
/* 316 */       $$1.process(this.time / 20.0F);
/*     */     }
/*     */   }
/*     */   
/*     */   public final String getName() {
/* 321 */     return this.name;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private RenderTarget getRenderTarget(@Nullable String $$0) {
/* 326 */     if ($$0 == null) {
/* 327 */       return null;
/*     */     }
/* 329 */     if ($$0.equals("minecraft:main")) {
/* 330 */       return this.screenTarget;
/*     */     }
/* 332 */     return this.customRenderTargets.get($$0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\PostChain.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */