/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.google.common.collect.UnmodifiableIterator;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.mojang.blaze3d.pipeline.RenderTarget;
/*     */ import com.mojang.blaze3d.platform.GlStateManager;
/*     */ import com.mojang.blaze3d.preprocessor.GlslPreprocessor;
/*     */ import com.mojang.blaze3d.shaders.AbstractUniform;
/*     */ import com.mojang.blaze3d.shaders.BlendMode;
/*     */ import com.mojang.blaze3d.shaders.Program;
/*     */ import com.mojang.blaze3d.shaders.ProgramManager;
/*     */ import com.mojang.blaze3d.shaders.Shader;
/*     */ import com.mojang.blaze3d.shaders.Uniform;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import com.mojang.blaze3d.vertex.VertexFormat;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import it.unimi.dsi.fastutil.ints.IntArrayList;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.Reader;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.FileUtil;
/*     */ import net.minecraft.client.renderer.texture.AbstractTexture;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.ChainedJsonException;
/*     */ import net.minecraft.server.packs.resources.Resource;
/*     */ import net.minecraft.server.packs.resources.ResourceProvider;
/*     */ import net.minecraft.util.GsonHelper;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ 
/*     */ public class ShaderInstance
/*     */   implements Shader, AutoCloseable
/*     */ {
/*     */   public static final String SHADER_PATH = "shaders";
/*     */   private static final String SHADER_CORE_PATH = "shaders/core/";
/*     */   private static final String SHADER_INCLUDE_PATH = "shaders/include/";
/*  48 */   static final Logger LOGGER = LogUtils.getLogger();
/*  49 */   private static final AbstractUniform DUMMY_UNIFORM = new AbstractUniform();
/*     */   
/*     */   private static final boolean ALWAYS_REAPPLY = true;
/*     */   private static ShaderInstance lastAppliedShader;
/*  53 */   private static int lastProgramId = -1;
/*     */ 
/*     */   
/*  56 */   private final Map<String, Object> samplerMap = Maps.newHashMap();
/*  57 */   private final List<String> samplerNames = Lists.newArrayList();
/*  58 */   private final List<Integer> samplerLocations = Lists.newArrayList();
/*     */ 
/*     */   
/*  61 */   private final List<Uniform> uniforms = Lists.newArrayList();
/*  62 */   private final List<Integer> uniformLocations = Lists.newArrayList();
/*  63 */   private final Map<String, Uniform> uniformMap = Maps.newHashMap();
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
/*     */   private final Program vertexProgram;
/*     */   
/*     */   private final Program fragmentProgram;
/*     */   
/*     */   private final VertexFormat vertexFormat;
/*     */   
/*     */   @Nullable
/*     */   public final Uniform MODEL_VIEW_MATRIX;
/*     */   
/*     */   @Nullable
/*     */   public final Uniform PROJECTION_MATRIX;
/*     */   
/*     */   @Nullable
/*     */   public final Uniform INVERSE_VIEW_ROTATION_MATRIX;
/*     */   
/*     */   @Nullable
/*     */   public final Uniform TEXTURE_MATRIX;
/*     */   
/*     */   @Nullable
/*     */   public final Uniform SCREEN_SIZE;
/*     */   
/*     */   @Nullable
/*     */   public final Uniform COLOR_MODULATOR;
/*     */   @Nullable
/*     */   public final Uniform LIGHT0_DIRECTION;
/*     */   @Nullable
/*     */   public final Uniform LIGHT1_DIRECTION;
/*     */   @Nullable
/*     */   public final Uniform GLINT_ALPHA;
/*     */   @Nullable
/*     */   public final Uniform FOG_START;
/*     */   @Nullable
/*     */   public final Uniform FOG_END;
/*     */   @Nullable
/*     */   public final Uniform FOG_COLOR;
/*     */   @Nullable
/*     */   public final Uniform FOG_SHAPE;
/*     */   @Nullable
/*     */   public final Uniform LINE_WIDTH;
/*     */   @Nullable
/*     */   public final Uniform GAME_TIME;
/*     */   @Nullable
/*     */   public final Uniform CHUNK_OFFSET;
/*     */   
/*     */   public ShaderInstance(ResourceProvider $$0, String $$1, VertexFormat $$2) throws IOException {
/* 122 */     this.name = $$1;
/* 123 */     this.vertexFormat = $$2;
/* 124 */     ResourceLocation $$3 = new ResourceLocation("shaders/core/" + $$1 + ".json");
/*     */ 
/*     */     
/* 127 */     try { Reader $$4 = $$0.openAsReader($$3); 
/* 128 */       try { JsonObject $$5 = GsonHelper.parse($$4);
/*     */         
/* 130 */         String $$6 = GsonHelper.getAsString($$5, "vertex");
/* 131 */         String $$7 = GsonHelper.getAsString($$5, "fragment");
/*     */         
/* 133 */         JsonArray $$8 = GsonHelper.getAsJsonArray($$5, "samplers", null);
/* 134 */         if ($$8 != null) {
/* 135 */           int $$9 = 0;
/* 136 */           for (JsonElement $$10 : $$8) {
/*     */             try {
/* 138 */               parseSamplerNode($$10);
/* 139 */             } catch (Exception $$11) {
/* 140 */               ChainedJsonException $$12 = ChainedJsonException.forException($$11);
/* 141 */               $$12.prependJsonKey("samplers[" + $$9 + "]");
/* 142 */               throw $$12;
/*     */             } 
/* 144 */             $$9++;
/*     */           } 
/*     */         } 
/*     */         
/* 148 */         JsonArray $$13 = GsonHelper.getAsJsonArray($$5, "attributes", null);
/* 149 */         if ($$13 != null) {
/* 150 */           int $$14 = 0;
/* 151 */           this.attributes = Lists.newArrayListWithCapacity($$13.size());
/* 152 */           this.attributeNames = Lists.newArrayListWithCapacity($$13.size());
/* 153 */           for (JsonElement $$15 : $$13) {
/*     */             try {
/* 155 */               this.attributeNames.add(GsonHelper.convertToString($$15, "attribute"));
/* 156 */             } catch (Exception $$16) {
/* 157 */               ChainedJsonException $$17 = ChainedJsonException.forException($$16);
/* 158 */               $$17.prependJsonKey("attributes[" + $$14 + "]");
/* 159 */               throw $$17;
/*     */             } 
/* 161 */             $$14++;
/*     */           } 
/*     */         } else {
/* 164 */           this.attributes = null;
/* 165 */           this.attributeNames = null;
/*     */         } 
/*     */         
/* 168 */         JsonArray $$18 = GsonHelper.getAsJsonArray($$5, "uniforms", null);
/* 169 */         if ($$18 != null) {
/* 170 */           int $$19 = 0;
/* 171 */           for (JsonElement $$20 : $$18) {
/*     */             try {
/* 173 */               parseUniformNode($$20);
/* 174 */             } catch (Exception $$21) {
/* 175 */               ChainedJsonException $$22 = ChainedJsonException.forException($$21);
/* 176 */               $$22.prependJsonKey("uniforms[" + $$19 + "]");
/* 177 */               throw $$22;
/*     */             } 
/* 179 */             $$19++;
/*     */           } 
/*     */         } 
/*     */         
/* 183 */         this.blend = parseBlendNode(GsonHelper.getAsJsonObject($$5, "blend", null));
/*     */         
/* 185 */         this.vertexProgram = getOrCreate($$0, Program.Type.VERTEX, $$6);
/* 186 */         this.fragmentProgram = getOrCreate($$0, Program.Type.FRAGMENT, $$7);
/*     */         
/* 188 */         this.programId = ProgramManager.createProgram();
/*     */         
/* 190 */         if (this.attributeNames != null) {
/* 191 */           int $$23 = 0;
/* 192 */           for (UnmodifiableIterator<String> unmodifiableIterator = $$2.getElementAttributeNames().iterator(); unmodifiableIterator.hasNext(); ) { String $$24 = unmodifiableIterator.next();
/* 193 */             Uniform.glBindAttribLocation(this.programId, $$23, $$24);
/* 194 */             this.attributes.add(Integer.valueOf($$23));
/* 195 */             $$23++; }
/*     */         
/*     */         } 
/*     */         
/* 199 */         ProgramManager.linkShader(this);
/*     */         
/* 201 */         updateLocations();
/* 202 */         if ($$4 != null) $$4.close();  } catch (Throwable throwable) { if ($$4 != null) try { $$4.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (Exception $$26)
/* 203 */     { ChainedJsonException $$27 = ChainedJsonException.forException($$26);
/* 204 */       $$27.setFilenameAndFlush($$3.getPath());
/* 205 */       throw $$27; }
/*     */ 
/*     */     
/* 208 */     markDirty();
/*     */     
/* 210 */     this.MODEL_VIEW_MATRIX = getUniform("ModelViewMat");
/* 211 */     this.PROJECTION_MATRIX = getUniform("ProjMat");
/* 212 */     this.INVERSE_VIEW_ROTATION_MATRIX = getUniform("IViewRotMat");
/* 213 */     this.TEXTURE_MATRIX = getUniform("TextureMat");
/*     */     
/* 215 */     this.SCREEN_SIZE = getUniform("ScreenSize");
/*     */     
/* 217 */     this.COLOR_MODULATOR = getUniform("ColorModulator");
/* 218 */     this.LIGHT0_DIRECTION = getUniform("Light0_Direction");
/* 219 */     this.LIGHT1_DIRECTION = getUniform("Light1_Direction");
/*     */     
/* 221 */     this.GLINT_ALPHA = getUniform("GlintAlpha");
/* 222 */     this.FOG_START = getUniform("FogStart");
/* 223 */     this.FOG_END = getUniform("FogEnd");
/* 224 */     this.FOG_COLOR = getUniform("FogColor");
/* 225 */     this.FOG_SHAPE = getUniform("FogShape");
/*     */     
/* 227 */     this.LINE_WIDTH = getUniform("LineWidth");
/*     */     
/* 229 */     this.GAME_TIME = getUniform("GameTime");
/*     */     
/* 231 */     this.CHUNK_OFFSET = getUniform("ChunkOffset");
/*     */   }
/*     */ 
/*     */   
/*     */   private static Program getOrCreate(final ResourceProvider resourceProvider, Program.Type $$1, String $$2) throws IOException {
/* 236 */     Program $$10, $$3 = (Program)$$1.getPrograms().get($$2);
/* 237 */     if ($$3 == null)
/* 238 */     { String $$4 = "shaders/core/" + $$2 + $$1.getExtension();
/* 239 */       Resource $$5 = resourceProvider.getResourceOrThrow(new ResourceLocation($$4));
/* 240 */       InputStream $$6 = $$5.open(); 
/* 241 */       try { final String relativePath = FileUtil.getFullResourcePath($$4);
/* 242 */         Program $$8 = Program.compileShader($$1, $$2, $$6, $$5.sourcePackId(), new GlslPreprocessor() {
/* 243 */               private final Set<String> importedPaths = Sets.newHashSet();
/*     */ 
/*     */               
/*     */               public String applyImport(boolean $$0, String $$1) {
/* 247 */                 $$1 = FileUtil.normalizeResourcePath(($$0 ? relativePath : "shaders/include/") + ($$0 ? relativePath : "shaders/include/"));
/*     */                 
/* 249 */                 if (!this.importedPaths.add($$1)) {
/* 250 */                   return null;
/*     */                 }
/*     */                 
/* 253 */                 ResourceLocation $$2 = new ResourceLocation($$1); 
/* 254 */                 try { Reader $$3 = resourceProvider.openAsReader($$2); 
/* 255 */                   try { String str = IOUtils.toString($$3);
/* 256 */                     if ($$3 != null) $$3.close();  return str; } catch (Throwable throwable) { if ($$3 != null) try { $$3.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException $$4)
/* 257 */                 { ShaderInstance.LOGGER.error("Could not open GLSL import {}: {}", $$1, $$4.getMessage());
/* 258 */                   return "#error " + $$4.getMessage(); }
/*     */               
/*     */               }
/*     */             });
/* 262 */         if ($$6 != null) $$6.close();  } catch (Throwable throwable) { if ($$6 != null)
/*     */           try { $$6.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  }
/* 264 */     else { $$10 = $$3; }
/*     */ 
/*     */     
/* 267 */     return $$10;
/*     */   }
/*     */   
/*     */   public static BlendMode parseBlendNode(JsonObject $$0) {
/* 271 */     if ($$0 == null) {
/* 272 */       return new BlendMode();
/*     */     }
/* 274 */     int $$1 = 32774;
/* 275 */     int $$2 = 1;
/* 276 */     int $$3 = 0;
/* 277 */     int $$4 = 1;
/* 278 */     int $$5 = 0;
/* 279 */     boolean $$6 = true;
/* 280 */     boolean $$7 = false;
/*     */     
/* 282 */     if (GsonHelper.isStringValue($$0, "func")) {
/* 283 */       $$1 = BlendMode.stringToBlendFunc($$0.get("func").getAsString());
/* 284 */       if ($$1 != 32774) {
/* 285 */         $$6 = false;
/*     */       }
/*     */     } 
/*     */     
/* 289 */     if (GsonHelper.isStringValue($$0, "srcrgb")) {
/* 290 */       $$2 = BlendMode.stringToBlendFactor($$0.get("srcrgb").getAsString());
/* 291 */       if ($$2 != 1) {
/* 292 */         $$6 = false;
/*     */       }
/*     */     } 
/*     */     
/* 296 */     if (GsonHelper.isStringValue($$0, "dstrgb")) {
/* 297 */       $$3 = BlendMode.stringToBlendFactor($$0.get("dstrgb").getAsString());
/* 298 */       if ($$3 != 0) {
/* 299 */         $$6 = false;
/*     */       }
/*     */     } 
/*     */     
/* 303 */     if (GsonHelper.isStringValue($$0, "srcalpha")) {
/* 304 */       $$4 = BlendMode.stringToBlendFactor($$0.get("srcalpha").getAsString());
/* 305 */       if ($$4 != 1) {
/* 306 */         $$6 = false;
/*     */       }
/* 308 */       $$7 = true;
/*     */     } 
/*     */     
/* 311 */     if (GsonHelper.isStringValue($$0, "dstalpha")) {
/* 312 */       $$5 = BlendMode.stringToBlendFactor($$0.get("dstalpha").getAsString());
/* 313 */       if ($$5 != 0) {
/* 314 */         $$6 = false;
/*     */       }
/* 316 */       $$7 = true;
/*     */     } 
/*     */ 
/*     */     
/* 320 */     if ($$6) {
/* 321 */       return new BlendMode();
/*     */     }
/*     */ 
/*     */     
/* 325 */     if ($$7) {
/* 326 */       return new BlendMode($$2, $$3, $$4, $$5, $$1);
/*     */     }
/* 328 */     return new BlendMode($$2, $$3, $$1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {
/* 334 */     for (Uniform $$0 : this.uniforms) {
/* 335 */       $$0.close();
/*     */     }
/* 337 */     ProgramManager.releaseProgram(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 342 */     RenderSystem.assertOnRenderThread();
/* 343 */     ProgramManager.glUseProgram(0);
/* 344 */     lastProgramId = -1;
/* 345 */     lastAppliedShader = null;
/*     */     
/* 347 */     int $$0 = GlStateManager._getActiveTexture();
/*     */ 
/*     */     
/* 350 */     for (int $$1 = 0; $$1 < this.samplerLocations.size(); $$1++) {
/* 351 */       if (this.samplerMap.get(this.samplerNames.get($$1)) != null) {
/*     */ 
/*     */ 
/*     */         
/* 355 */         GlStateManager._activeTexture(33984 + $$1);
/* 356 */         GlStateManager._bindTexture(0);
/*     */       } 
/*     */     } 
/* 359 */     GlStateManager._activeTexture($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void apply() {
/* 364 */     RenderSystem.assertOnRenderThread();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 372 */     this.dirty = false;
/*     */     
/* 374 */     lastAppliedShader = this;
/*     */     
/* 376 */     this.blend.apply();
/*     */     
/* 378 */     if (this.programId != lastProgramId) {
/* 379 */       ProgramManager.glUseProgram(this.programId);
/* 380 */       lastProgramId = this.programId;
/*     */     } 
/*     */     
/* 383 */     int $$0 = GlStateManager._getActiveTexture();
/*     */     
/* 385 */     for (int $$1 = 0; $$1 < this.samplerLocations.size(); $$1++) {
/* 386 */       String $$2 = this.samplerNames.get($$1);
/*     */       
/* 388 */       if (this.samplerMap.get($$2) != null) {
/*     */ 
/*     */ 
/*     */         
/* 392 */         int $$3 = Uniform.glGetUniformLocation(this.programId, $$2);
/* 393 */         Uniform.uploadInteger($$3, $$1);
/*     */         
/* 395 */         RenderSystem.activeTexture(33984 + $$1);
/* 396 */         Object $$4 = this.samplerMap.get($$2);
/* 397 */         int $$5 = -1;
/* 398 */         if ($$4 instanceof RenderTarget) {
/* 399 */           $$5 = ((RenderTarget)$$4).getColorTextureId();
/* 400 */         } else if ($$4 instanceof AbstractTexture) {
/* 401 */           $$5 = ((AbstractTexture)$$4).getId();
/* 402 */         } else if ($$4 instanceof Integer) {
/* 403 */           $$5 = ((Integer)$$4).intValue();
/*     */         } 
/* 405 */         if ($$5 != -1)
/*     */         {
/*     */           
/* 408 */           RenderSystem.bindTexture($$5); } 
/*     */       } 
/*     */     } 
/* 411 */     GlStateManager._activeTexture($$0);
/*     */     
/* 413 */     for (Uniform $$6 : this.uniforms) {
/* 414 */       $$6.upload();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void markDirty() {
/* 420 */     this.dirty = true;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Uniform getUniform(String $$0) {
/* 425 */     RenderSystem.assertOnRenderThread();
/* 426 */     return this.uniformMap.get($$0);
/*     */   }
/*     */   
/*     */   public AbstractUniform safeGetUniform(String $$0) {
/* 430 */     RenderSystem.assertOnGameThread();
/* 431 */     Uniform $$1 = getUniform($$0);
/* 432 */     return ($$1 == null) ? DUMMY_UNIFORM : (AbstractUniform)$$1;
/*     */   }
/*     */   
/*     */   private void updateLocations() {
/* 436 */     RenderSystem.assertOnRenderThread();
/* 437 */     IntArrayList intArrayList = new IntArrayList();
/* 438 */     for (int $$1 = 0; $$1 < this.samplerNames.size(); $$1++) {
/* 439 */       String $$2 = this.samplerNames.get($$1);
/* 440 */       int $$3 = Uniform.glGetUniformLocation(this.programId, $$2);
/* 441 */       if ($$3 == -1) {
/* 442 */         LOGGER.warn("Shader {} could not find sampler named {} in the specified shader program.", this.name, $$2);
/* 443 */         this.samplerMap.remove($$2);
/* 444 */         intArrayList.add($$1);
/*     */       } else {
/*     */         
/* 447 */         this.samplerLocations.add(Integer.valueOf($$3));
/*     */       } 
/*     */     } 
/*     */     
/* 451 */     for (int $$4 = intArrayList.size() - 1; $$4 >= 0; $$4--) {
/* 452 */       int $$5 = intArrayList.getInt($$4);
/* 453 */       this.samplerNames.remove($$5);
/*     */     } 
/*     */     
/* 456 */     for (Uniform $$6 : this.uniforms) {
/* 457 */       String $$7 = $$6.getName();
/* 458 */       int $$8 = Uniform.glGetUniformLocation(this.programId, $$7);
/* 459 */       if ($$8 == -1) {
/* 460 */         LOGGER.warn("Shader {} could not find uniform named {} in the specified shader program.", this.name, $$7);
/*     */         continue;
/*     */       } 
/* 463 */       this.uniformLocations.add(Integer.valueOf($$8));
/* 464 */       $$6.setLocation($$8);
/* 465 */       this.uniformMap.put($$7, $$6);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void parseSamplerNode(JsonElement $$0) {
/* 470 */     JsonObject $$1 = GsonHelper.convertToJsonObject($$0, "sampler");
/* 471 */     String $$2 = GsonHelper.getAsString($$1, "name");
/*     */     
/* 473 */     if (!GsonHelper.isStringValue($$1, "file")) {
/*     */       
/* 475 */       this.samplerMap.put($$2, null);
/* 476 */       this.samplerNames.add($$2);
/*     */       return;
/*     */     } 
/* 479 */     this.samplerNames.add($$2);
/*     */   }
/*     */   
/*     */   public void setSampler(String $$0, Object $$1) {
/* 483 */     this.samplerMap.put($$0, $$1);
/* 484 */     markDirty();
/*     */   }
/*     */   
/*     */   private void parseUniformNode(JsonElement $$0) throws ChainedJsonException {
/* 488 */     JsonObject $$1 = GsonHelper.convertToJsonObject($$0, "uniform");
/* 489 */     String $$2 = GsonHelper.getAsString($$1, "name");
/* 490 */     int $$3 = Uniform.getTypeFromString(GsonHelper.getAsString($$1, "type"));
/* 491 */     int $$4 = GsonHelper.getAsInt($$1, "count");
/* 492 */     float[] $$5 = new float[Math.max($$4, 16)];
/*     */     
/* 494 */     JsonArray $$6 = GsonHelper.getAsJsonArray($$1, "values");
/* 495 */     if ($$6.size() != $$4 && $$6.size() > 1) {
/* 496 */       throw new ChainedJsonException("Invalid amount of values specified (expected " + $$4 + ", found " + $$6.size() + ")");
/*     */     }
/*     */     
/* 499 */     int $$7 = 0;
/* 500 */     for (JsonElement $$8 : $$6) {
/*     */       try {
/* 502 */         $$5[$$7] = GsonHelper.convertToFloat($$8, "value");
/* 503 */       } catch (Exception $$9) {
/* 504 */         ChainedJsonException $$10 = ChainedJsonException.forException($$9);
/* 505 */         $$10.prependJsonKey("values[" + $$7 + "]");
/* 506 */         throw $$10;
/*     */       } 
/* 508 */       $$7++;
/*     */     } 
/*     */ 
/*     */     
/* 512 */     if ($$4 > 1 && $$6.size() == 1) {
/* 513 */       for (; $$7 < $$4; $$7++) {
/* 514 */         $$5[$$7] = $$5[0];
/*     */       }
/*     */     }
/*     */     
/* 518 */     int $$11 = ($$4 > 1 && $$4 <= 4 && $$3 < 8) ? ($$4 - 1) : 0;
/* 519 */     Uniform $$12 = new Uniform($$2, $$3 + $$11, $$4, this);
/*     */     
/* 521 */     if ($$3 <= 3) {
/*     */       
/* 523 */       $$12.setSafe((int)$$5[0], (int)$$5[1], (int)$$5[2], (int)$$5[3]);
/* 524 */     } else if ($$3 <= 7) {
/*     */       
/* 526 */       $$12.setSafe($$5[0], $$5[1], $$5[2], $$5[3]);
/*     */     } else {
/*     */       
/* 529 */       $$12.set(Arrays.copyOfRange($$5, 0, $$4));
/*     */     } 
/*     */     
/* 532 */     this.uniforms.add($$12);
/*     */   }
/*     */ 
/*     */   
/*     */   public Program getVertexProgram() {
/* 537 */     return this.vertexProgram;
/*     */   }
/*     */ 
/*     */   
/*     */   public Program getFragmentProgram() {
/* 542 */     return this.fragmentProgram;
/*     */   }
/*     */ 
/*     */   
/*     */   public void attachToProgram() {
/* 547 */     this.fragmentProgram.attachToShader(this);
/* 548 */     this.vertexProgram.attachToShader(this);
/*     */   }
/*     */   
/*     */   public VertexFormat getVertexFormat() {
/* 552 */     return this.vertexFormat;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 556 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getId() {
/* 561 */     return this.programId;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\ShaderInstance.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */