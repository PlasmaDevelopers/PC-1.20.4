/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.mojang.blaze3d.platform.GlStateManager;
/*     */ import com.mojang.blaze3d.shaders.AbstractUniform;
/*     */ import com.mojang.blaze3d.shaders.BlendMode;
/*     */ import com.mojang.blaze3d.shaders.Effect;
/*     */ import com.mojang.blaze3d.shaders.EffectProgram;
/*     */ import com.mojang.blaze3d.shaders.Program;
/*     */ import com.mojang.blaze3d.shaders.ProgramManager;
/*     */ import com.mojang.blaze3d.shaders.Shader;
/*     */ import com.mojang.blaze3d.shaders.Uniform;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import it.unimi.dsi.fastutil.ints.IntArrayList;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InvalidClassException;
/*     */ import java.io.Reader;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.function.IntSupplier;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.ChainedJsonException;
/*     */ import net.minecraft.server.packs.resources.Resource;
/*     */ import net.minecraft.server.packs.resources.ResourceManager;
/*     */ import net.minecraft.util.GsonHelper;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ 
/*     */ public class EffectInstance
/*     */   implements Effect, AutoCloseable
/*     */ {
/*     */   private static final String EFFECT_SHADER_PATH = "shaders/program/";
/*  40 */   private static final Logger LOGGER = LogUtils.getLogger();
/*  41 */   private static final AbstractUniform DUMMY_UNIFORM = new AbstractUniform();
/*     */   
/*     */   private static final boolean ALWAYS_REAPPLY = true;
/*     */   private static EffectInstance lastAppliedEffect;
/*  45 */   private static int lastProgramId = -1;
/*     */ 
/*     */   
/*  48 */   private final Map<String, IntSupplier> samplerMap = Maps.newHashMap();
/*  49 */   private final List<String> samplerNames = Lists.newArrayList();
/*  50 */   private final List<Integer> samplerLocations = Lists.newArrayList();
/*     */ 
/*     */   
/*  53 */   private final List<Uniform> uniforms = Lists.newArrayList();
/*  54 */   private final List<Integer> uniformLocations = Lists.newArrayList();
/*  55 */   private final Map<String, Uniform> uniformMap = Maps.newHashMap();
/*     */   
/*     */   private final int programId;
/*     */   
/*     */   private final String name;
/*     */   
/*     */   private boolean dirty;
/*     */   
/*     */   private final BlendMode blend;
/*     */   
/*     */   private final List<Integer> attributes;
/*     */   
/*     */   private final List<String> attributeNames;
/*     */   
/*     */   private final EffectProgram vertexProgram;
/*     */   
/*     */   private final EffectProgram fragmentProgram;
/*     */   
/*     */   public EffectInstance(ResourceManager $$0, String $$1) throws IOException {
/*  74 */     ResourceLocation $$2 = new ResourceLocation("shaders/program/" + $$1 + ".json");
/*  75 */     this.name = $$1;
/*     */     
/*  77 */     Resource $$3 = $$0.getResourceOrThrow($$2); 
/*  78 */     try { Reader $$4 = $$3.openAsReader(); 
/*  79 */       try { JsonObject $$5 = GsonHelper.parse($$4);
/*     */         
/*  81 */         String $$6 = GsonHelper.getAsString($$5, "vertex");
/*  82 */         String $$7 = GsonHelper.getAsString($$5, "fragment");
/*     */         
/*  84 */         JsonArray $$8 = GsonHelper.getAsJsonArray($$5, "samplers", null);
/*  85 */         if ($$8 != null) {
/*  86 */           int $$9 = 0;
/*  87 */           for (JsonElement $$10 : $$8) {
/*     */             try {
/*  89 */               parseSamplerNode($$10);
/*  90 */             } catch (Exception $$11) {
/*  91 */               ChainedJsonException $$12 = ChainedJsonException.forException($$11);
/*  92 */               $$12.prependJsonKey("samplers[" + $$9 + "]");
/*  93 */               throw $$12;
/*     */             } 
/*  95 */             $$9++;
/*     */           } 
/*     */         } 
/*     */         
/*  99 */         JsonArray $$13 = GsonHelper.getAsJsonArray($$5, "attributes", null);
/* 100 */         if ($$13 != null) {
/* 101 */           int $$14 = 0;
/* 102 */           this.attributes = Lists.newArrayListWithCapacity($$13.size());
/* 103 */           this.attributeNames = Lists.newArrayListWithCapacity($$13.size());
/* 104 */           for (JsonElement $$15 : $$13) {
/*     */             try {
/* 106 */               this.attributeNames.add(GsonHelper.convertToString($$15, "attribute"));
/* 107 */             } catch (Exception $$16) {
/* 108 */               ChainedJsonException $$17 = ChainedJsonException.forException($$16);
/* 109 */               $$17.prependJsonKey("attributes[" + $$14 + "]");
/* 110 */               throw $$17;
/*     */             } 
/* 112 */             $$14++;
/*     */           } 
/*     */         } else {
/* 115 */           this.attributes = null;
/* 116 */           this.attributeNames = null;
/*     */         } 
/*     */         
/* 119 */         JsonArray $$18 = GsonHelper.getAsJsonArray($$5, "uniforms", null);
/* 120 */         if ($$18 != null) {
/* 121 */           int $$19 = 0;
/* 122 */           for (JsonElement $$20 : $$18) {
/*     */             try {
/* 124 */               parseUniformNode($$20);
/* 125 */             } catch (Exception $$21) {
/* 126 */               ChainedJsonException $$22 = ChainedJsonException.forException($$21);
/* 127 */               $$22.prependJsonKey("uniforms[" + $$19 + "]");
/* 128 */               throw $$22;
/*     */             } 
/* 130 */             $$19++;
/*     */           } 
/*     */         } 
/*     */         
/* 134 */         this.blend = parseBlendNode(GsonHelper.getAsJsonObject($$5, "blend", null));
/*     */         
/* 136 */         this.vertexProgram = getOrCreate($$0, Program.Type.VERTEX, $$6);
/* 137 */         this.fragmentProgram = getOrCreate($$0, Program.Type.FRAGMENT, $$7);
/*     */         
/* 139 */         this.programId = ProgramManager.createProgram();
/* 140 */         ProgramManager.linkShader((Shader)this);
/*     */         
/* 142 */         updateLocations();
/*     */         
/* 144 */         if (this.attributeNames != null) {
/* 145 */           for (String $$23 : this.attributeNames) {
/* 146 */             int $$24 = Uniform.glGetAttribLocation(this.programId, $$23);
/* 147 */             this.attributes.add(Integer.valueOf($$24));
/*     */           } 
/*     */         }
/* 150 */         if ($$4 != null) $$4.close();  } catch (Throwable throwable) { if ($$4 != null) try { $$4.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (Exception $$25)
/* 151 */     { ChainedJsonException $$26 = ChainedJsonException.forException($$25);
/* 152 */       $$26.setFilenameAndFlush($$2.getPath() + " (" + $$2.getPath() + ")");
/* 153 */       throw $$26; }
/*     */ 
/*     */     
/* 156 */     markDirty();
/*     */   }
/*     */   public static EffectProgram getOrCreate(ResourceManager $$0, Program.Type $$1, String $$2) throws IOException {
/*     */     EffectProgram $$9;
/* 160 */     Program $$3 = (Program)$$1.getPrograms().get($$2);
/* 161 */     if ($$3 != null && !($$3 instanceof EffectProgram)) {
/* 162 */       throw new InvalidClassException("Program is not of type EffectProgram");
/*     */     }
/*     */ 
/*     */     
/* 166 */     if ($$3 == null)
/* 167 */     { ResourceLocation $$4 = new ResourceLocation("shaders/program/" + $$2 + $$1.getExtension());
/* 168 */       Resource $$5 = $$0.getResourceOrThrow($$4);
/* 169 */       InputStream $$6 = $$5.open(); 
/* 170 */       try { EffectProgram $$7 = EffectProgram.compileShader($$1, $$2, $$6, $$5.sourcePackId());
/* 171 */         if ($$6 != null) $$6.close();  } catch (Throwable throwable) { if ($$6 != null)
/*     */           try { $$6.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  }
/* 173 */     else { $$9 = (EffectProgram)$$3; }
/*     */ 
/*     */     
/* 176 */     return $$9;
/*     */   }
/*     */   
/*     */   public static BlendMode parseBlendNode(@Nullable JsonObject $$0) {
/* 180 */     if ($$0 == null) {
/* 181 */       return new BlendMode();
/*     */     }
/* 183 */     int $$1 = 32774;
/* 184 */     int $$2 = 1;
/* 185 */     int $$3 = 0;
/* 186 */     int $$4 = 1;
/* 187 */     int $$5 = 0;
/* 188 */     boolean $$6 = true;
/* 189 */     boolean $$7 = false;
/*     */     
/* 191 */     if (GsonHelper.isStringValue($$0, "func")) {
/* 192 */       $$1 = BlendMode.stringToBlendFunc($$0.get("func").getAsString());
/* 193 */       if ($$1 != 32774) {
/* 194 */         $$6 = false;
/*     */       }
/*     */     } 
/*     */     
/* 198 */     if (GsonHelper.isStringValue($$0, "srcrgb")) {
/* 199 */       $$2 = BlendMode.stringToBlendFactor($$0.get("srcrgb").getAsString());
/* 200 */       if ($$2 != 1) {
/* 201 */         $$6 = false;
/*     */       }
/*     */     } 
/*     */     
/* 205 */     if (GsonHelper.isStringValue($$0, "dstrgb")) {
/* 206 */       $$3 = BlendMode.stringToBlendFactor($$0.get("dstrgb").getAsString());
/* 207 */       if ($$3 != 0) {
/* 208 */         $$6 = false;
/*     */       }
/*     */     } 
/*     */     
/* 212 */     if (GsonHelper.isStringValue($$0, "srcalpha")) {
/* 213 */       $$4 = BlendMode.stringToBlendFactor($$0.get("srcalpha").getAsString());
/* 214 */       if ($$4 != 1) {
/* 215 */         $$6 = false;
/*     */       }
/* 217 */       $$7 = true;
/*     */     } 
/*     */     
/* 220 */     if (GsonHelper.isStringValue($$0, "dstalpha")) {
/* 221 */       $$5 = BlendMode.stringToBlendFactor($$0.get("dstalpha").getAsString());
/* 222 */       if ($$5 != 0) {
/* 223 */         $$6 = false;
/*     */       }
/* 225 */       $$7 = true;
/*     */     } 
/*     */ 
/*     */     
/* 229 */     if ($$6) {
/* 230 */       return new BlendMode();
/*     */     }
/*     */ 
/*     */     
/* 234 */     if ($$7) {
/* 235 */       return new BlendMode($$2, $$3, $$4, $$5, $$1);
/*     */     }
/* 237 */     return new BlendMode($$2, $$3, $$1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {
/* 243 */     for (Uniform $$0 : this.uniforms) {
/* 244 */       $$0.close();
/*     */     }
/* 246 */     ProgramManager.releaseProgram((Shader)this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 251 */     RenderSystem.assertOnRenderThread();
/* 252 */     ProgramManager.glUseProgram(0);
/* 253 */     lastProgramId = -1;
/* 254 */     lastAppliedEffect = null;
/*     */ 
/*     */     
/* 257 */     for (int $$0 = 0; $$0 < this.samplerLocations.size(); $$0++) {
/* 258 */       if (this.samplerMap.get(this.samplerNames.get($$0)) != null) {
/*     */ 
/*     */ 
/*     */         
/* 262 */         GlStateManager._activeTexture(33984 + $$0);
/* 263 */         GlStateManager._bindTexture(0);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void apply() {
/* 269 */     RenderSystem.assertOnGameThread();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 277 */     this.dirty = false;
/*     */     
/* 279 */     lastAppliedEffect = this;
/*     */     
/* 281 */     this.blend.apply();
/*     */     
/* 283 */     if (this.programId != lastProgramId) {
/* 284 */       ProgramManager.glUseProgram(this.programId);
/* 285 */       lastProgramId = this.programId;
/*     */     } 
/*     */     
/* 288 */     for (int $$0 = 0; $$0 < this.samplerLocations.size(); $$0++) {
/* 289 */       String $$1 = this.samplerNames.get($$0);
/* 290 */       IntSupplier $$2 = this.samplerMap.get($$1);
/* 291 */       if ($$2 != null) {
/*     */ 
/*     */ 
/*     */         
/* 295 */         RenderSystem.activeTexture(33984 + $$0);
/* 296 */         int $$3 = $$2.getAsInt();
/* 297 */         if ($$3 != -1) {
/*     */ 
/*     */           
/* 300 */           RenderSystem.bindTexture($$3);
/*     */           
/* 302 */           Uniform.uploadInteger(((Integer)this.samplerLocations.get($$0)).intValue(), $$0);
/*     */         } 
/*     */       } 
/*     */     } 
/* 306 */     for (Uniform $$4 : this.uniforms) {
/* 307 */       $$4.upload();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void markDirty() {
/* 313 */     this.dirty = true;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Uniform getUniform(String $$0) {
/* 318 */     RenderSystem.assertOnRenderThread();
/* 319 */     return this.uniformMap.get($$0);
/*     */   }
/*     */   
/*     */   public AbstractUniform safeGetUniform(String $$0) {
/* 323 */     RenderSystem.assertOnGameThread();
/* 324 */     Uniform $$1 = getUniform($$0);
/* 325 */     return ($$1 == null) ? DUMMY_UNIFORM : (AbstractUniform)$$1;
/*     */   }
/*     */   
/*     */   private void updateLocations() {
/* 329 */     RenderSystem.assertOnRenderThread();
/* 330 */     IntArrayList intArrayList = new IntArrayList();
/* 331 */     for (int $$1 = 0; $$1 < this.samplerNames.size(); $$1++) {
/* 332 */       String $$2 = this.samplerNames.get($$1);
/* 333 */       int $$3 = Uniform.glGetUniformLocation(this.programId, $$2);
/* 334 */       if ($$3 == -1) {
/* 335 */         LOGGER.warn("Shader {} could not find sampler named {} in the specified shader program.", this.name, $$2);
/* 336 */         this.samplerMap.remove($$2);
/* 337 */         intArrayList.add($$1);
/*     */       } else {
/*     */         
/* 340 */         this.samplerLocations.add(Integer.valueOf($$3));
/*     */       } 
/*     */     } 
/*     */     
/* 344 */     for (int $$4 = intArrayList.size() - 1; $$4 >= 0; $$4--) {
/* 345 */       this.samplerNames.remove(intArrayList.getInt($$4));
/*     */     }
/*     */     
/* 348 */     for (Uniform $$5 : this.uniforms) {
/* 349 */       String $$6 = $$5.getName();
/* 350 */       int $$7 = Uniform.glGetUniformLocation(this.programId, $$6);
/* 351 */       if ($$7 == -1) {
/* 352 */         LOGGER.warn("Shader {} could not find uniform named {} in the specified shader program.", this.name, $$6);
/*     */         continue;
/*     */       } 
/* 355 */       this.uniformLocations.add(Integer.valueOf($$7));
/* 356 */       $$5.setLocation($$7);
/* 357 */       this.uniformMap.put($$6, $$5);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void parseSamplerNode(JsonElement $$0) {
/* 362 */     JsonObject $$1 = GsonHelper.convertToJsonObject($$0, "sampler");
/* 363 */     String $$2 = GsonHelper.getAsString($$1, "name");
/*     */     
/* 365 */     if (!GsonHelper.isStringValue($$1, "file")) {
/*     */       
/* 367 */       this.samplerMap.put($$2, null);
/* 368 */       this.samplerNames.add($$2);
/*     */       return;
/*     */     } 
/* 371 */     this.samplerNames.add($$2);
/*     */   }
/*     */   
/*     */   public void setSampler(String $$0, IntSupplier $$1) {
/* 375 */     if (this.samplerMap.containsKey($$0)) {
/* 376 */       this.samplerMap.remove($$0);
/*     */     }
/*     */     
/* 379 */     this.samplerMap.put($$0, $$1);
/* 380 */     markDirty();
/*     */   }
/*     */   
/*     */   private void parseUniformNode(JsonElement $$0) throws ChainedJsonException {
/* 384 */     JsonObject $$1 = GsonHelper.convertToJsonObject($$0, "uniform");
/* 385 */     String $$2 = GsonHelper.getAsString($$1, "name");
/* 386 */     int $$3 = Uniform.getTypeFromString(GsonHelper.getAsString($$1, "type"));
/* 387 */     int $$4 = GsonHelper.getAsInt($$1, "count");
/* 388 */     float[] $$5 = new float[Math.max($$4, 16)];
/*     */     
/* 390 */     JsonArray $$6 = GsonHelper.getAsJsonArray($$1, "values");
/* 391 */     if ($$6.size() != $$4 && $$6.size() > 1) {
/* 392 */       throw new ChainedJsonException("Invalid amount of values specified (expected " + $$4 + ", found " + $$6.size() + ")");
/*     */     }
/*     */     
/* 395 */     int $$7 = 0;
/* 396 */     for (JsonElement $$8 : $$6) {
/*     */       try {
/* 398 */         $$5[$$7] = GsonHelper.convertToFloat($$8, "value");
/* 399 */       } catch (Exception $$9) {
/* 400 */         ChainedJsonException $$10 = ChainedJsonException.forException($$9);
/* 401 */         $$10.prependJsonKey("values[" + $$7 + "]");
/* 402 */         throw $$10;
/*     */       } 
/* 404 */       $$7++;
/*     */     } 
/*     */ 
/*     */     
/* 408 */     if ($$4 > 1 && $$6.size() == 1) {
/* 409 */       for (; $$7 < $$4; $$7++) {
/* 410 */         $$5[$$7] = $$5[0];
/*     */       }
/*     */     }
/*     */     
/* 414 */     int $$11 = ($$4 > 1 && $$4 <= 4 && $$3 < 8) ? ($$4 - 1) : 0;
/* 415 */     Uniform $$12 = new Uniform($$2, $$3 + $$11, $$4, (Shader)this);
/*     */     
/* 417 */     if ($$3 <= 3) {
/*     */       
/* 419 */       $$12.setSafe((int)$$5[0], (int)$$5[1], (int)$$5[2], (int)$$5[3]);
/* 420 */     } else if ($$3 <= 7) {
/*     */       
/* 422 */       $$12.setSafe($$5[0], $$5[1], $$5[2], $$5[3]);
/*     */     } else {
/*     */       
/* 425 */       $$12.set($$5);
/*     */     } 
/*     */     
/* 428 */     this.uniforms.add($$12);
/*     */   }
/*     */ 
/*     */   
/*     */   public Program getVertexProgram() {
/* 433 */     return (Program)this.vertexProgram;
/*     */   }
/*     */ 
/*     */   
/*     */   public Program getFragmentProgram() {
/* 438 */     return (Program)this.fragmentProgram;
/*     */   }
/*     */ 
/*     */   
/*     */   public void attachToProgram() {
/* 443 */     this.fragmentProgram.attachToEffect(this);
/* 444 */     this.vertexProgram.attachToEffect(this);
/*     */   }
/*     */   
/*     */   public String getName() {
/* 448 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getId() {
/* 453 */     return this.programId;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\EffectInstance.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */