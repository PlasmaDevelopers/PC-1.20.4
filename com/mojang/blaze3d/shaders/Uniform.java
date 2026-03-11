/*     */ package com.mojang.blaze3d.shaders;
/*     */ 
/*     */ import com.mojang.blaze3d.platform.GlStateManager;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import org.joml.Matrix3f;
/*     */ import org.joml.Matrix4f;
/*     */ import org.joml.Vector3f;
/*     */ import org.joml.Vector4f;
/*     */ import org.lwjgl.system.MemoryUtil;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class Uniform
/*     */   extends AbstractUniform implements AutoCloseable {
/*  17 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   public static final int UT_INT1 = 0;
/*     */   
/*     */   public static final int UT_INT2 = 1;
/*     */   
/*     */   public static final int UT_INT3 = 2;
/*     */   
/*     */   public static final int UT_INT4 = 3;
/*     */   
/*     */   public static final int UT_FLOAT1 = 4;
/*     */   
/*     */   public static final int UT_FLOAT2 = 5;
/*     */   
/*     */   public static final int UT_FLOAT3 = 6;
/*     */   
/*     */   public static final int UT_FLOAT4 = 7;
/*     */   public static final int UT_MAT2 = 8;
/*     */   public static final int UT_MAT3 = 9;
/*     */   public static final int UT_MAT4 = 10;
/*     */   private static final boolean TRANSPOSE_MATRICIES = false;
/*     */   private int location;
/*     */   private final int count;
/*     */   private final int type;
/*     */   private final IntBuffer intValues;
/*     */   private final FloatBuffer floatValues;
/*     */   private final String name;
/*     */   private boolean dirty;
/*     */   private final Shader parent;
/*     */   
/*     */   public Uniform(String $$0, int $$1, int $$2, Shader $$3) {
/*  48 */     this.name = $$0;
/*  49 */     this.count = $$2;
/*  50 */     this.type = $$1;
/*  51 */     this.parent = $$3;
/*  52 */     if ($$1 <= 3) {
/*  53 */       this.intValues = MemoryUtil.memAllocInt($$2);
/*  54 */       this.floatValues = null;
/*     */     } else {
/*  56 */       this.intValues = null;
/*  57 */       this.floatValues = MemoryUtil.memAllocFloat($$2);
/*     */     } 
/*  59 */     this.location = -1;
/*  60 */     markDirty();
/*     */   }
/*     */   
/*     */   public static int glGetUniformLocation(int $$0, CharSequence $$1) {
/*  64 */     return GlStateManager._glGetUniformLocation($$0, $$1);
/*     */   }
/*     */   
/*     */   public static void uploadInteger(int $$0, int $$1) {
/*  68 */     RenderSystem.glUniform1i($$0, $$1);
/*     */   }
/*     */   
/*     */   public static int glGetAttribLocation(int $$0, CharSequence $$1) {
/*  72 */     return GlStateManager._glGetAttribLocation($$0, $$1);
/*     */   }
/*     */   
/*     */   public static void glBindAttribLocation(int $$0, int $$1, CharSequence $$2) {
/*  76 */     GlStateManager._glBindAttribLocation($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/*  81 */     if (this.intValues != null) {
/*  82 */       MemoryUtil.memFree(this.intValues);
/*     */     }
/*  84 */     if (this.floatValues != null) {
/*  85 */       MemoryUtil.memFree(this.floatValues);
/*     */     }
/*     */   }
/*     */   
/*     */   private void markDirty() {
/*  90 */     this.dirty = true;
/*  91 */     if (this.parent != null) {
/*  92 */       this.parent.markDirty();
/*     */     }
/*     */   }
/*     */   
/*     */   public static int getTypeFromString(String $$0) {
/*  97 */     int $$1 = -1;
/*     */     
/*  99 */     if ("int".equals($$0)) {
/* 100 */       $$1 = 0;
/* 101 */     } else if ("float".equals($$0)) {
/* 102 */       $$1 = 4;
/* 103 */     } else if ($$0.startsWith("matrix")) {
/* 104 */       if ($$0.endsWith("2x2")) {
/* 105 */         $$1 = 8;
/* 106 */       } else if ($$0.endsWith("3x3")) {
/* 107 */         $$1 = 9;
/* 108 */       } else if ($$0.endsWith("4x4")) {
/* 109 */         $$1 = 10;
/*     */       } 
/*     */     } 
/*     */     
/* 113 */     return $$1;
/*     */   }
/*     */   
/*     */   public void setLocation(int $$0) {
/* 117 */     this.location = $$0;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 121 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public final void set(float $$0) {
/* 126 */     this.floatValues.position(0);
/* 127 */     this.floatValues.put(0, $$0);
/* 128 */     markDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   public final void set(float $$0, float $$1) {
/* 133 */     this.floatValues.position(0);
/* 134 */     this.floatValues.put(0, $$0);
/* 135 */     this.floatValues.put(1, $$1);
/* 136 */     markDirty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(int $$0, float $$1) {
/* 147 */     this.floatValues.position(0);
/* 148 */     this.floatValues.put($$0, $$1);
/* 149 */     markDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   public final void set(float $$0, float $$1, float $$2) {
/* 154 */     this.floatValues.position(0);
/* 155 */     this.floatValues.put(0, $$0);
/* 156 */     this.floatValues.put(1, $$1);
/* 157 */     this.floatValues.put(2, $$2);
/* 158 */     markDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   public final void set(Vector3f $$0) {
/* 163 */     this.floatValues.position(0);
/* 164 */     $$0.get(this.floatValues);
/* 165 */     markDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   public final void set(float $$0, float $$1, float $$2, float $$3) {
/* 170 */     this.floatValues.position(0);
/* 171 */     this.floatValues.put($$0);
/* 172 */     this.floatValues.put($$1);
/* 173 */     this.floatValues.put($$2);
/* 174 */     this.floatValues.put($$3);
/* 175 */     this.floatValues.flip();
/* 176 */     markDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   public final void set(Vector4f $$0) {
/* 181 */     this.floatValues.position(0);
/* 182 */     $$0.get(this.floatValues);
/* 183 */     markDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setSafe(float $$0, float $$1, float $$2, float $$3) {
/* 188 */     this.floatValues.position(0);
/* 189 */     if (this.type >= 4) {
/* 190 */       this.floatValues.put(0, $$0);
/*     */     }
/* 192 */     if (this.type >= 5) {
/* 193 */       this.floatValues.put(1, $$1);
/*     */     }
/* 195 */     if (this.type >= 6) {
/* 196 */       this.floatValues.put(2, $$2);
/*     */     }
/* 198 */     if (this.type >= 7) {
/* 199 */       this.floatValues.put(3, $$3);
/*     */     }
/* 201 */     markDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setSafe(int $$0, int $$1, int $$2, int $$3) {
/* 206 */     this.intValues.position(0);
/* 207 */     if (this.type >= 0) {
/* 208 */       this.intValues.put(0, $$0);
/*     */     }
/* 210 */     if (this.type >= 1) {
/* 211 */       this.intValues.put(1, $$1);
/*     */     }
/* 213 */     if (this.type >= 2) {
/* 214 */       this.intValues.put(2, $$2);
/*     */     }
/* 216 */     if (this.type >= 3) {
/* 217 */       this.intValues.put(3, $$3);
/*     */     }
/* 219 */     markDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   public final void set(int $$0) {
/* 224 */     this.intValues.position(0);
/* 225 */     this.intValues.put(0, $$0);
/* 226 */     markDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   public final void set(int $$0, int $$1) {
/* 231 */     this.intValues.position(0);
/* 232 */     this.intValues.put(0, $$0);
/* 233 */     this.intValues.put(1, $$1);
/* 234 */     markDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   public final void set(int $$0, int $$1, int $$2) {
/* 239 */     this.intValues.position(0);
/* 240 */     this.intValues.put(0, $$0);
/* 241 */     this.intValues.put(1, $$1);
/* 242 */     this.intValues.put(2, $$2);
/* 243 */     markDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   public final void set(int $$0, int $$1, int $$2, int $$3) {
/* 248 */     this.intValues.position(0);
/* 249 */     this.intValues.put(0, $$0);
/* 250 */     this.intValues.put(1, $$1);
/* 251 */     this.intValues.put(2, $$2);
/* 252 */     this.intValues.put(3, $$3);
/* 253 */     markDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   public final void set(float[] $$0) {
/* 258 */     if ($$0.length < this.count) {
/* 259 */       LOGGER.warn("Uniform.set called with a too-small value array (expected {}, got {}). Ignoring.", Integer.valueOf(this.count), Integer.valueOf($$0.length));
/*     */       
/*     */       return;
/*     */     } 
/* 263 */     this.floatValues.position(0);
/* 264 */     this.floatValues.put($$0);
/* 265 */     this.floatValues.position(0);
/* 266 */     markDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setMat2x2(float $$0, float $$1, float $$2, float $$3) {
/* 271 */     this.floatValues.position(0);
/* 272 */     this.floatValues.put(0, $$0);
/* 273 */     this.floatValues.put(1, $$1);
/* 274 */     this.floatValues.put(2, $$2);
/* 275 */     this.floatValues.put(3, $$3);
/* 276 */     markDirty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setMat2x3(float $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 285 */     this.floatValues.position(0);
/* 286 */     this.floatValues.put(0, $$0);
/* 287 */     this.floatValues.put(1, $$1);
/* 288 */     this.floatValues.put(2, $$2);
/* 289 */     this.floatValues.put(3, $$3);
/* 290 */     this.floatValues.put(4, $$4);
/* 291 */     this.floatValues.put(5, $$5);
/* 292 */     markDirty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setMat2x4(float $$0, float $$1, float $$2, float $$3, float $$4, float $$5, float $$6, float $$7) {
/* 302 */     this.floatValues.position(0);
/* 303 */     this.floatValues.put(0, $$0);
/* 304 */     this.floatValues.put(1, $$1);
/* 305 */     this.floatValues.put(2, $$2);
/* 306 */     this.floatValues.put(3, $$3);
/* 307 */     this.floatValues.put(4, $$4);
/* 308 */     this.floatValues.put(5, $$5);
/* 309 */     this.floatValues.put(6, $$6);
/* 310 */     this.floatValues.put(7, $$7);
/* 311 */     markDirty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setMat3x2(float $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 319 */     this.floatValues.position(0);
/* 320 */     this.floatValues.put(0, $$0);
/* 321 */     this.floatValues.put(1, $$1);
/* 322 */     this.floatValues.put(2, $$2);
/* 323 */     this.floatValues.put(3, $$3);
/* 324 */     this.floatValues.put(4, $$4);
/* 325 */     this.floatValues.put(5, $$5);
/* 326 */     markDirty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setMat3x3(float $$0, float $$1, float $$2, float $$3, float $$4, float $$5, float $$6, float $$7, float $$8) {
/* 335 */     this.floatValues.position(0);
/* 336 */     this.floatValues.put(0, $$0);
/* 337 */     this.floatValues.put(1, $$1);
/* 338 */     this.floatValues.put(2, $$2);
/* 339 */     this.floatValues.put(3, $$3);
/* 340 */     this.floatValues.put(4, $$4);
/* 341 */     this.floatValues.put(5, $$5);
/* 342 */     this.floatValues.put(6, $$6);
/* 343 */     this.floatValues.put(7, $$7);
/* 344 */     this.floatValues.put(8, $$8);
/* 345 */     markDirty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setMat3x4(float $$0, float $$1, float $$2, float $$3, float $$4, float $$5, float $$6, float $$7, float $$8, float $$9, float $$10, float $$11) {
/* 355 */     this.floatValues.position(0);
/* 356 */     this.floatValues.put(0, $$0);
/* 357 */     this.floatValues.put(1, $$1);
/* 358 */     this.floatValues.put(2, $$2);
/* 359 */     this.floatValues.put(3, $$3);
/* 360 */     this.floatValues.put(4, $$4);
/* 361 */     this.floatValues.put(5, $$5);
/* 362 */     this.floatValues.put(6, $$6);
/* 363 */     this.floatValues.put(7, $$7);
/* 364 */     this.floatValues.put(8, $$8);
/* 365 */     this.floatValues.put(9, $$9);
/* 366 */     this.floatValues.put(10, $$10);
/* 367 */     this.floatValues.put(11, $$11);
/* 368 */     markDirty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setMat4x2(float $$0, float $$1, float $$2, float $$3, float $$4, float $$5, float $$6, float $$7) {
/* 376 */     this.floatValues.position(0);
/* 377 */     this.floatValues.put(0, $$0);
/* 378 */     this.floatValues.put(1, $$1);
/* 379 */     this.floatValues.put(2, $$2);
/* 380 */     this.floatValues.put(3, $$3);
/* 381 */     this.floatValues.put(4, $$4);
/* 382 */     this.floatValues.put(5, $$5);
/* 383 */     this.floatValues.put(6, $$6);
/* 384 */     this.floatValues.put(7, $$7);
/* 385 */     markDirty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setMat4x3(float $$0, float $$1, float $$2, float $$3, float $$4, float $$5, float $$6, float $$7, float $$8, float $$9, float $$10, float $$11) {
/* 394 */     this.floatValues.position(0);
/* 395 */     this.floatValues.put(0, $$0);
/* 396 */     this.floatValues.put(1, $$1);
/* 397 */     this.floatValues.put(2, $$2);
/* 398 */     this.floatValues.put(3, $$3);
/* 399 */     this.floatValues.put(4, $$4);
/* 400 */     this.floatValues.put(5, $$5);
/* 401 */     this.floatValues.put(6, $$6);
/* 402 */     this.floatValues.put(7, $$7);
/* 403 */     this.floatValues.put(8, $$8);
/* 404 */     this.floatValues.put(9, $$9);
/* 405 */     this.floatValues.put(10, $$10);
/* 406 */     this.floatValues.put(11, $$11);
/* 407 */     markDirty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setMat4x4(float $$0, float $$1, float $$2, float $$3, float $$4, float $$5, float $$6, float $$7, float $$8, float $$9, float $$10, float $$11, float $$12, float $$13, float $$14, float $$15) {
/* 417 */     this.floatValues.position(0);
/* 418 */     this.floatValues.put(0, $$0);
/* 419 */     this.floatValues.put(1, $$1);
/* 420 */     this.floatValues.put(2, $$2);
/* 421 */     this.floatValues.put(3, $$3);
/* 422 */     this.floatValues.put(4, $$4);
/* 423 */     this.floatValues.put(5, $$5);
/* 424 */     this.floatValues.put(6, $$6);
/* 425 */     this.floatValues.put(7, $$7);
/* 426 */     this.floatValues.put(8, $$8);
/* 427 */     this.floatValues.put(9, $$9);
/* 428 */     this.floatValues.put(10, $$10);
/* 429 */     this.floatValues.put(11, $$11);
/* 430 */     this.floatValues.put(12, $$12);
/* 431 */     this.floatValues.put(13, $$13);
/* 432 */     this.floatValues.put(14, $$14);
/* 433 */     this.floatValues.put(15, $$15);
/* 434 */     markDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   public final void set(Matrix4f $$0) {
/* 439 */     this.floatValues.position(0);
/* 440 */     $$0.get(this.floatValues);
/* 441 */     markDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   public final void set(Matrix3f $$0) {
/* 446 */     this.floatValues.position(0);
/* 447 */     $$0.get(this.floatValues);
/* 448 */     markDirty();
/*     */   }
/*     */   
/*     */   public void upload() {
/* 452 */     if (!this.dirty);
/*     */ 
/*     */ 
/*     */     
/* 456 */     this.dirty = false;
/*     */     
/* 458 */     if (this.type <= 3) {
/* 459 */       uploadAsInteger();
/* 460 */     } else if (this.type <= 7) {
/* 461 */       uploadAsFloat();
/* 462 */     } else if (this.type <= 10) {
/* 463 */       uploadAsMatrix();
/*     */     } else {
/* 465 */       LOGGER.warn("Uniform.upload called, but type value ({}) is not a valid type. Ignoring.", Integer.valueOf(this.type));
/*     */       return;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void uploadAsInteger() {
/* 471 */     this.intValues.rewind();
/* 472 */     switch (this.type) {
/*     */       case 0:
/* 474 */         RenderSystem.glUniform1(this.location, this.intValues);
/*     */         return;
/*     */       case 1:
/* 477 */         RenderSystem.glUniform2(this.location, this.intValues);
/*     */         return;
/*     */       case 2:
/* 480 */         RenderSystem.glUniform3(this.location, this.intValues);
/*     */         return;
/*     */       case 3:
/* 483 */         RenderSystem.glUniform4(this.location, this.intValues);
/*     */         return;
/*     */     } 
/* 486 */     LOGGER.warn("Uniform.upload called, but count value ({}) is  not in the range of 1 to 4. Ignoring.", Integer.valueOf(this.count));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void uploadAsFloat() {
/* 492 */     this.floatValues.rewind();
/* 493 */     switch (this.type) {
/*     */       case 4:
/* 495 */         RenderSystem.glUniform1(this.location, this.floatValues);
/*     */         return;
/*     */       case 5:
/* 498 */         RenderSystem.glUniform2(this.location, this.floatValues);
/*     */         return;
/*     */       case 6:
/* 501 */         RenderSystem.glUniform3(this.location, this.floatValues);
/*     */         return;
/*     */       case 7:
/* 504 */         RenderSystem.glUniform4(this.location, this.floatValues);
/*     */         return;
/*     */     } 
/* 507 */     LOGGER.warn("Uniform.upload called, but count value ({}) is not in the range of 1 to 4. Ignoring.", Integer.valueOf(this.count));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void uploadAsMatrix() {
/* 513 */     this.floatValues.clear();
/* 514 */     switch (this.type) {
/*     */       case 8:
/* 516 */         RenderSystem.glUniformMatrix2(this.location, false, this.floatValues);
/*     */         break;
/*     */       case 9:
/* 519 */         RenderSystem.glUniformMatrix3(this.location, false, this.floatValues);
/*     */         break;
/*     */       case 10:
/* 522 */         RenderSystem.glUniformMatrix4(this.location, false, this.floatValues);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getLocation() {
/* 528 */     return this.location;
/*     */   }
/*     */   
/*     */   public int getCount() {
/* 532 */     return this.count;
/*     */   }
/*     */   
/*     */   public int getType() {
/* 536 */     return this.type;
/*     */   }
/*     */   
/*     */   public IntBuffer getIntBuffer() {
/* 540 */     return this.intValues;
/*     */   }
/*     */   
/*     */   public FloatBuffer getFloatBuffer() {
/* 544 */     return this.floatValues;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\shaders\Uniform.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */