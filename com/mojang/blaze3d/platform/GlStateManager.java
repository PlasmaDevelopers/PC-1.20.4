/*     */ package com.mojang.blaze3d.platform;
/*     */ 
/*     */ import com.google.common.base.Charsets;
/*     */ import com.mojang.blaze3d.DontObfuscate;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.List;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.stream.IntStream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import org.joml.Matrix4f;
/*     */ import org.joml.Vector3f;
/*     */ import org.joml.Vector3fc;
/*     */ import org.joml.Vector4f;
/*     */ import org.lwjgl.PointerBuffer;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.opengl.GL13;
/*     */ import org.lwjgl.opengl.GL14;
/*     */ import org.lwjgl.opengl.GL15;
/*     */ import org.lwjgl.opengl.GL20;
/*     */ import org.lwjgl.opengl.GL20C;
/*     */ import org.lwjgl.opengl.GL30;
/*     */ import org.lwjgl.opengl.GL32C;
/*     */ import org.lwjgl.system.MemoryStack;
/*     */ import org.lwjgl.system.MemoryUtil;
/*     */ 
/*     */ 
/*     */ @DontObfuscate
/*     */ public class GlStateManager
/*     */ {
/*  34 */   private static final boolean ON_LINUX = (Util.getPlatform() == Util.OS.LINUX);
/*     */   
/*     */   public static final int TEXTURE_COUNT = 12;
/*     */   
/*  38 */   private static final BlendState BLEND = new BlendState();
/*  39 */   private static final DepthState DEPTH = new DepthState();
/*  40 */   private static final CullState CULL = new CullState();
/*  41 */   private static final PolygonOffsetState POLY_OFFSET = new PolygonOffsetState();
/*  42 */   private static final ColorLogicState COLOR_LOGIC = new ColorLogicState();
/*  43 */   private static final StencilState STENCIL = new StencilState();
/*  44 */   private static final ScissorState SCISSOR = new ScissorState(); private static int activeTexture; private static final TextureState[] TEXTURES;
/*     */   
/*     */   static {
/*  47 */     TEXTURES = (TextureState[])IntStream.range(0, 12).mapToObj($$0 -> new TextureState()).toArray($$0 -> new TextureState[$$0]);
/*     */   }
/*  49 */   private static final ColorMask COLOR_MASK = new ColorMask();
/*     */   
/*     */   public static void _disableScissorTest() {
/*  52 */     RenderSystem.assertOnRenderThreadOrInit();
/*  53 */     SCISSOR.mode.disable();
/*     */   }
/*     */   
/*     */   public static void _enableScissorTest() {
/*  57 */     RenderSystem.assertOnRenderThreadOrInit();
/*  58 */     SCISSOR.mode.enable();
/*     */   }
/*     */   
/*     */   public static void _scissorBox(int $$0, int $$1, int $$2, int $$3) {
/*  62 */     RenderSystem.assertOnRenderThreadOrInit();
/*  63 */     GL20.glScissor($$0, $$1, $$2, $$3);
/*     */   }
/*     */   
/*     */   public static void _disableDepthTest() {
/*  67 */     RenderSystem.assertOnRenderThreadOrInit();
/*  68 */     DEPTH.mode.disable();
/*     */   }
/*     */   
/*     */   public static void _enableDepthTest() {
/*  72 */     RenderSystem.assertOnRenderThreadOrInit();
/*  73 */     DEPTH.mode.enable();
/*     */   }
/*     */   
/*     */   public static void _depthFunc(int $$0) {
/*  77 */     RenderSystem.assertOnRenderThreadOrInit();
/*  78 */     if ($$0 != DEPTH.func) {
/*  79 */       DEPTH.func = $$0;
/*  80 */       GL11.glDepthFunc($$0);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void _depthMask(boolean $$0) {
/*  85 */     RenderSystem.assertOnRenderThread();
/*  86 */     if ($$0 != DEPTH.mask) {
/*  87 */       DEPTH.mask = $$0;
/*  88 */       GL11.glDepthMask($$0);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void _disableBlend() {
/*  93 */     RenderSystem.assertOnRenderThread();
/*  94 */     BLEND.mode.disable();
/*     */   }
/*     */   
/*     */   public static void _enableBlend() {
/*  98 */     RenderSystem.assertOnRenderThread();
/*  99 */     BLEND.mode.enable();
/*     */   }
/*     */   
/*     */   public static void _blendFunc(int $$0, int $$1) {
/* 103 */     RenderSystem.assertOnRenderThread();
/* 104 */     if ($$0 != BLEND.srcRgb || $$1 != BLEND.dstRgb) {
/* 105 */       BLEND.srcRgb = $$0;
/* 106 */       BLEND.dstRgb = $$1;
/* 107 */       GL11.glBlendFunc($$0, $$1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void _blendFuncSeparate(int $$0, int $$1, int $$2, int $$3) {
/* 112 */     RenderSystem.assertOnRenderThread();
/* 113 */     if ($$0 != BLEND.srcRgb || $$1 != BLEND.dstRgb || $$2 != BLEND.srcAlpha || $$3 != BLEND.dstAlpha) {
/* 114 */       BLEND.srcRgb = $$0;
/* 115 */       BLEND.dstRgb = $$1;
/* 116 */       BLEND.srcAlpha = $$2;
/* 117 */       BLEND.dstAlpha = $$3;
/* 118 */       glBlendFuncSeparate($$0, $$1, $$2, $$3);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void _blendEquation(int $$0) {
/* 123 */     RenderSystem.assertOnRenderThread();
/* 124 */     GL14.glBlendEquation($$0);
/*     */   }
/*     */   
/*     */   public static int glGetProgrami(int $$0, int $$1) {
/* 128 */     RenderSystem.assertOnRenderThread();
/* 129 */     return GL20.glGetProgrami($$0, $$1);
/*     */   }
/*     */   
/*     */   public static void glAttachShader(int $$0, int $$1) {
/* 133 */     RenderSystem.assertOnRenderThread();
/* 134 */     GL20.glAttachShader($$0, $$1);
/*     */   }
/*     */   
/*     */   public static void glDeleteShader(int $$0) {
/* 138 */     RenderSystem.assertOnRenderThread();
/* 139 */     GL20.glDeleteShader($$0);
/*     */   }
/*     */   
/*     */   public static int glCreateShader(int $$0) {
/* 143 */     RenderSystem.assertOnRenderThread();
/* 144 */     return GL20.glCreateShader($$0);
/*     */   }
/*     */   
/*     */   public static void glShaderSource(int $$0, List<String> $$1) {
/* 148 */     RenderSystem.assertOnRenderThread();
/*     */ 
/*     */ 
/*     */     
/* 152 */     StringBuilder $$2 = new StringBuilder();
/* 153 */     for (String $$3 : $$1) {
/* 154 */       $$2.append($$3);
/*     */     }
/*     */     
/* 157 */     byte[] $$4 = $$2.toString().getBytes(Charsets.UTF_8);
/* 158 */     ByteBuffer $$5 = MemoryUtil.memAlloc($$4.length + 1);
/* 159 */     $$5.put($$4);
/* 160 */     $$5.put((byte)0);
/* 161 */     $$5.flip();
/*     */     
/* 163 */     try { MemoryStack $$6 = MemoryStack.stackPush(); 
/* 164 */       try { PointerBuffer $$7 = $$6.mallocPointer(1);
/* 165 */         $$7.put($$5);
/* 166 */         GL20C.nglShaderSource($$0, 1, $$7.address0(), 0L);
/* 167 */         if ($$6 != null) $$6.close();  } catch (Throwable throwable) { if ($$6 != null)
/* 168 */           try { $$6.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } finally { MemoryUtil.memFree($$5); }
/*     */   
/*     */   }
/*     */   
/*     */   public static void glCompileShader(int $$0) {
/* 173 */     RenderSystem.assertOnRenderThread();
/* 174 */     GL20.glCompileShader($$0);
/*     */   }
/*     */   
/*     */   public static int glGetShaderi(int $$0, int $$1) {
/* 178 */     RenderSystem.assertOnRenderThread();
/* 179 */     return GL20.glGetShaderi($$0, $$1);
/*     */   }
/*     */   
/*     */   public static void _glUseProgram(int $$0) {
/* 183 */     RenderSystem.assertOnRenderThread();
/* 184 */     GL20.glUseProgram($$0);
/*     */   }
/*     */   
/*     */   public static int glCreateProgram() {
/* 188 */     RenderSystem.assertOnRenderThread();
/* 189 */     return GL20.glCreateProgram();
/*     */   }
/*     */   
/*     */   public static void glDeleteProgram(int $$0) {
/* 193 */     RenderSystem.assertOnRenderThread();
/* 194 */     GL20.glDeleteProgram($$0);
/*     */   }
/*     */   
/*     */   public static void glLinkProgram(int $$0) {
/* 198 */     RenderSystem.assertOnRenderThread();
/* 199 */     GL20.glLinkProgram($$0);
/*     */   }
/*     */   
/*     */   public static int _glGetUniformLocation(int $$0, CharSequence $$1) {
/* 203 */     RenderSystem.assertOnRenderThread();
/* 204 */     return GL20.glGetUniformLocation($$0, $$1);
/*     */   }
/*     */   
/*     */   public static void _glUniform1(int $$0, IntBuffer $$1) {
/* 208 */     RenderSystem.assertOnRenderThread();
/* 209 */     GL20.glUniform1iv($$0, $$1);
/*     */   }
/*     */   
/*     */   public static void _glUniform1i(int $$0, int $$1) {
/* 213 */     RenderSystem.assertOnRenderThread();
/* 214 */     GL20.glUniform1i($$0, $$1);
/*     */   }
/*     */   
/*     */   public static void _glUniform1(int $$0, FloatBuffer $$1) {
/* 218 */     RenderSystem.assertOnRenderThread();
/* 219 */     GL20.glUniform1fv($$0, $$1);
/*     */   }
/*     */   
/*     */   public static void _glUniform2(int $$0, IntBuffer $$1) {
/* 223 */     RenderSystem.assertOnRenderThread();
/* 224 */     GL20.glUniform2iv($$0, $$1);
/*     */   }
/*     */   
/*     */   public static void _glUniform2(int $$0, FloatBuffer $$1) {
/* 228 */     RenderSystem.assertOnRenderThread();
/* 229 */     GL20.glUniform2fv($$0, $$1);
/*     */   }
/*     */   
/*     */   public static void _glUniform3(int $$0, IntBuffer $$1) {
/* 233 */     RenderSystem.assertOnRenderThread();
/* 234 */     GL20.glUniform3iv($$0, $$1);
/*     */   }
/*     */   
/*     */   public static void _glUniform3(int $$0, FloatBuffer $$1) {
/* 238 */     RenderSystem.assertOnRenderThread();
/* 239 */     GL20.glUniform3fv($$0, $$1);
/*     */   }
/*     */   
/*     */   public static void _glUniform4(int $$0, IntBuffer $$1) {
/* 243 */     RenderSystem.assertOnRenderThread();
/* 244 */     GL20.glUniform4iv($$0, $$1);
/*     */   }
/*     */   
/*     */   public static void _glUniform4(int $$0, FloatBuffer $$1) {
/* 248 */     RenderSystem.assertOnRenderThread();
/* 249 */     GL20.glUniform4fv($$0, $$1);
/*     */   }
/*     */   
/*     */   public static void _glUniformMatrix2(int $$0, boolean $$1, FloatBuffer $$2) {
/* 253 */     RenderSystem.assertOnRenderThread();
/* 254 */     GL20.glUniformMatrix2fv($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   public static void _glUniformMatrix3(int $$0, boolean $$1, FloatBuffer $$2) {
/* 258 */     RenderSystem.assertOnRenderThread();
/* 259 */     GL20.glUniformMatrix3fv($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   public static void _glUniformMatrix4(int $$0, boolean $$1, FloatBuffer $$2) {
/* 263 */     RenderSystem.assertOnRenderThread();
/* 264 */     GL20.glUniformMatrix4fv($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   public static int _glGetAttribLocation(int $$0, CharSequence $$1) {
/* 268 */     RenderSystem.assertOnRenderThread();
/* 269 */     return GL20.glGetAttribLocation($$0, $$1);
/*     */   }
/*     */   
/*     */   public static void _glBindAttribLocation(int $$0, int $$1, CharSequence $$2) {
/* 273 */     RenderSystem.assertOnRenderThread();
/* 274 */     GL20.glBindAttribLocation($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   public static int _glGenBuffers() {
/* 278 */     RenderSystem.assertOnRenderThreadOrInit();
/* 279 */     return GL15.glGenBuffers();
/*     */   }
/*     */   
/*     */   public static int _glGenVertexArrays() {
/* 283 */     RenderSystem.assertOnRenderThreadOrInit();
/* 284 */     return GL30.glGenVertexArrays();
/*     */   }
/*     */   
/*     */   public static void _glBindBuffer(int $$0, int $$1) {
/* 288 */     RenderSystem.assertOnRenderThreadOrInit();
/* 289 */     GL15.glBindBuffer($$0, $$1);
/*     */   }
/*     */   
/*     */   public static void _glBindVertexArray(int $$0) {
/* 293 */     RenderSystem.assertOnRenderThreadOrInit();
/* 294 */     GL30.glBindVertexArray($$0);
/*     */   }
/*     */   
/*     */   public static void _glBufferData(int $$0, ByteBuffer $$1, int $$2) {
/* 298 */     RenderSystem.assertOnRenderThreadOrInit();
/* 299 */     GL15.glBufferData($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   public static void _glBufferData(int $$0, long $$1, int $$2) {
/* 303 */     RenderSystem.assertOnRenderThreadOrInit();
/* 304 */     GL15.glBufferData($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static ByteBuffer _glMapBuffer(int $$0, int $$1) {
/* 309 */     RenderSystem.assertOnRenderThreadOrInit();
/* 310 */     return GL15.glMapBuffer($$0, $$1);
/*     */   }
/*     */   
/*     */   public static void _glUnmapBuffer(int $$0) {
/* 314 */     RenderSystem.assertOnRenderThreadOrInit();
/* 315 */     GL15.glUnmapBuffer($$0);
/*     */   }
/*     */   
/*     */   public static void _glDeleteBuffers(int $$0) {
/* 319 */     RenderSystem.assertOnRenderThread();
/* 320 */     if (ON_LINUX) {
/*     */       
/* 322 */       GL32C.glBindBuffer(34962, $$0);
/* 323 */       GL32C.glBufferData(34962, 0L, 35048);
/* 324 */       GL32C.glBindBuffer(34962, 0);
/*     */     } 
/* 326 */     GL15.glDeleteBuffers($$0);
/*     */   }
/*     */   
/*     */   public static void _glCopyTexSubImage2D(int $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7) {
/* 330 */     RenderSystem.assertOnRenderThreadOrInit();
/* 331 */     GL20.glCopyTexSubImage2D($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7);
/*     */   }
/*     */   
/*     */   public static void _glDeleteVertexArrays(int $$0) {
/* 335 */     RenderSystem.assertOnRenderThread();
/* 336 */     GL30.glDeleteVertexArrays($$0);
/*     */   }
/*     */   
/*     */   public static void _glBindFramebuffer(int $$0, int $$1) {
/* 340 */     RenderSystem.assertOnRenderThreadOrInit();
/*     */     
/* 342 */     GL30.glBindFramebuffer($$0, $$1);
/*     */   }
/*     */   
/*     */   public static void _glBlitFrameBuffer(int $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, int $$8, int $$9) {
/* 346 */     RenderSystem.assertOnRenderThreadOrInit();
/* 347 */     GL30.glBlitFramebuffer($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8, $$9);
/*     */   }
/*     */   
/*     */   public static void _glBindRenderbuffer(int $$0, int $$1) {
/* 351 */     RenderSystem.assertOnRenderThreadOrInit();
/*     */     
/* 353 */     GL30.glBindRenderbuffer($$0, $$1);
/*     */   }
/*     */   
/*     */   public static void _glDeleteRenderbuffers(int $$0) {
/* 357 */     RenderSystem.assertOnRenderThreadOrInit();
/*     */     
/* 359 */     GL30.glDeleteRenderbuffers($$0);
/*     */   }
/*     */   
/*     */   public static void _glDeleteFramebuffers(int $$0) {
/* 363 */     RenderSystem.assertOnRenderThreadOrInit();
/*     */     
/* 365 */     GL30.glDeleteFramebuffers($$0);
/*     */   }
/*     */   
/*     */   public static int glGenFramebuffers() {
/* 369 */     RenderSystem.assertOnRenderThreadOrInit();
/*     */     
/* 371 */     return GL30.glGenFramebuffers();
/*     */   }
/*     */   
/*     */   public static int glGenRenderbuffers() {
/* 375 */     RenderSystem.assertOnRenderThreadOrInit();
/*     */     
/* 377 */     return GL30.glGenRenderbuffers();
/*     */   }
/*     */   
/*     */   public static void _glRenderbufferStorage(int $$0, int $$1, int $$2, int $$3) {
/* 381 */     RenderSystem.assertOnRenderThreadOrInit();
/*     */     
/* 383 */     GL30.glRenderbufferStorage($$0, $$1, $$2, $$3);
/*     */   }
/*     */   
/*     */   public static void _glFramebufferRenderbuffer(int $$0, int $$1, int $$2, int $$3) {
/* 387 */     RenderSystem.assertOnRenderThreadOrInit();
/*     */     
/* 389 */     GL30.glFramebufferRenderbuffer($$0, $$1, $$2, $$3);
/*     */   }
/*     */   
/*     */   public static int glCheckFramebufferStatus(int $$0) {
/* 393 */     RenderSystem.assertOnRenderThreadOrInit();
/*     */     
/* 395 */     return GL30.glCheckFramebufferStatus($$0);
/*     */   }
/*     */   
/*     */   public static void _glFramebufferTexture2D(int $$0, int $$1, int $$2, int $$3, int $$4) {
/* 399 */     RenderSystem.assertOnRenderThreadOrInit();
/*     */     
/* 401 */     GL30.glFramebufferTexture2D($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */   
/*     */   public static int getBoundFramebuffer() {
/* 405 */     RenderSystem.assertOnRenderThread();
/*     */     
/* 407 */     return _getInteger(36006);
/*     */   }
/*     */   
/*     */   public static void glActiveTexture(int $$0) {
/* 411 */     RenderSystem.assertOnRenderThread();
/* 412 */     GL13.glActiveTexture($$0);
/*     */   }
/*     */   
/*     */   public static void glBlendFuncSeparate(int $$0, int $$1, int $$2, int $$3) {
/* 416 */     RenderSystem.assertOnRenderThread();
/* 417 */     GL14.glBlendFuncSeparate($$0, $$1, $$2, $$3);
/*     */   }
/*     */   
/*     */   public static String glGetShaderInfoLog(int $$0, int $$1) {
/* 421 */     RenderSystem.assertOnRenderThread();
/* 422 */     return GL20.glGetShaderInfoLog($$0, $$1);
/*     */   }
/*     */   
/*     */   public static String glGetProgramInfoLog(int $$0, int $$1) {
/* 426 */     RenderSystem.assertOnRenderThread();
/* 427 */     return GL20.glGetProgramInfoLog($$0, $$1);
/*     */   }
/*     */   
/*     */   public static void setupLevelDiffuseLighting(Vector3f $$0, Vector3f $$1, Matrix4f $$2) {
/* 431 */     RenderSystem.assertOnRenderThread();
/*     */     
/* 433 */     Vector4f $$3 = $$2.transform(new Vector4f((Vector3fc)$$0, 1.0F));
/* 434 */     Vector4f $$4 = $$2.transform(new Vector4f((Vector3fc)$$1, 1.0F));
/* 435 */     RenderSystem.setShaderLights(new Vector3f($$3.x(), $$3.y(), $$3.z()), new Vector3f($$4.x(), $$4.y(), $$4.z()));
/*     */   }
/*     */   
/*     */   public static void setupGuiFlatDiffuseLighting(Vector3f $$0, Vector3f $$1) {
/* 439 */     RenderSystem.assertOnRenderThread();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 444 */     Matrix4f $$2 = (new Matrix4f()).scaling(1.0F, -1.0F, 1.0F).rotateY(-0.3926991F).rotateX(2.3561945F);
/* 445 */     setupLevelDiffuseLighting($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   public static void setupGui3DDiffuseLighting(Vector3f $$0, Vector3f $$1) {
/* 449 */     RenderSystem.assertOnRenderThread();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 454 */     Matrix4f $$2 = (new Matrix4f()).rotationYXZ(1.0821041F, 3.2375858F, 0.0F).rotateYXZ(-0.3926991F, 2.3561945F, 0.0F);
/* 455 */     setupLevelDiffuseLighting($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   public static void _enableCull() {
/* 459 */     RenderSystem.assertOnRenderThread();
/* 460 */     CULL.enable.enable();
/*     */   }
/*     */   
/*     */   public static void _disableCull() {
/* 464 */     RenderSystem.assertOnRenderThread();
/* 465 */     CULL.enable.disable();
/*     */   }
/*     */   
/*     */   public static void _polygonMode(int $$0, int $$1) {
/* 469 */     RenderSystem.assertOnRenderThread();
/* 470 */     GL11.glPolygonMode($$0, $$1);
/*     */   }
/*     */   
/*     */   public static void _enablePolygonOffset() {
/* 474 */     RenderSystem.assertOnRenderThread();
/* 475 */     POLY_OFFSET.fill.enable();
/*     */   }
/*     */   
/*     */   public static void _disablePolygonOffset() {
/* 479 */     RenderSystem.assertOnRenderThread();
/* 480 */     POLY_OFFSET.fill.disable();
/*     */   }
/*     */   
/*     */   public static void _polygonOffset(float $$0, float $$1) {
/* 484 */     RenderSystem.assertOnRenderThread();
/* 485 */     if ($$0 != POLY_OFFSET.factor || $$1 != POLY_OFFSET.units) {
/* 486 */       POLY_OFFSET.factor = $$0;
/* 487 */       POLY_OFFSET.units = $$1;
/* 488 */       GL11.glPolygonOffset($$0, $$1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public enum LogicOp
/*     */   {
/* 494 */     AND(5377),
/* 495 */     AND_INVERTED(5380),
/* 496 */     AND_REVERSE(5378),
/* 497 */     CLEAR(5376),
/* 498 */     COPY(5379),
/* 499 */     COPY_INVERTED(5388),
/* 500 */     EQUIV(5385),
/* 501 */     INVERT(5386),
/* 502 */     NAND(5390),
/* 503 */     NOOP(5381),
/* 504 */     NOR(5384),
/* 505 */     OR(5383),
/* 506 */     OR_INVERTED(5389),
/* 507 */     OR_REVERSE(5387),
/* 508 */     SET(5391),
/* 509 */     XOR(5382);
/*     */     
/*     */     public final int value;
/*     */ 
/*     */     
/*     */     LogicOp(int $$0) {
/* 515 */       this.value = $$0;
/*     */     }
/*     */   }
/*     */   
/*     */   public static void _enableColorLogicOp() {
/* 520 */     RenderSystem.assertOnRenderThread();
/* 521 */     COLOR_LOGIC.enable.enable();
/*     */   }
/*     */   
/*     */   public static void _disableColorLogicOp() {
/* 525 */     RenderSystem.assertOnRenderThread();
/* 526 */     COLOR_LOGIC.enable.disable();
/*     */   }
/*     */   
/*     */   public static void _logicOp(int $$0) {
/* 530 */     RenderSystem.assertOnRenderThread();
/* 531 */     if ($$0 != COLOR_LOGIC.op) {
/* 532 */       COLOR_LOGIC.op = $$0;
/* 533 */       GL11.glLogicOp($$0);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void _activeTexture(int $$0) {
/* 538 */     RenderSystem.assertOnRenderThread();
/* 539 */     if (activeTexture != $$0 - 33984) {
/* 540 */       activeTexture = $$0 - 33984;
/* 541 */       glActiveTexture($$0);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void _texParameter(int $$0, int $$1, float $$2) {
/* 546 */     RenderSystem.assertOnRenderThreadOrInit();
/* 547 */     GL11.glTexParameterf($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   public static void _texParameter(int $$0, int $$1, int $$2) {
/* 551 */     RenderSystem.assertOnRenderThreadOrInit();
/* 552 */     GL11.glTexParameteri($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   public static int _getTexLevelParameter(int $$0, int $$1, int $$2) {
/* 556 */     RenderSystem.assertInInitPhase();
/* 557 */     return GL11.glGetTexLevelParameteri($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   public static int _genTexture() {
/* 561 */     RenderSystem.assertOnRenderThreadOrInit();
/* 562 */     return GL11.glGenTextures();
/*     */   }
/*     */   
/*     */   public static void _genTextures(int[] $$0) {
/* 566 */     RenderSystem.assertOnRenderThreadOrInit();
/* 567 */     GL11.glGenTextures($$0);
/*     */   }
/*     */   
/*     */   public static void _deleteTexture(int $$0) {
/* 571 */     RenderSystem.assertOnRenderThreadOrInit();
/* 572 */     GL11.glDeleteTextures($$0);
/* 573 */     for (TextureState $$1 : TEXTURES) {
/* 574 */       if ($$1.binding == $$0) {
/* 575 */         $$1.binding = -1;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void _deleteTextures(int[] $$0) {
/* 581 */     RenderSystem.assertOnRenderThreadOrInit();
/* 582 */     for (TextureState $$1 : TEXTURES) {
/* 583 */       for (int $$2 : $$0) {
/* 584 */         if ($$1.binding == $$2) {
/* 585 */           $$1.binding = -1;
/*     */         }
/*     */       } 
/*     */     } 
/* 589 */     GL11.glDeleteTextures($$0);
/*     */   }
/*     */   
/*     */   public static void _bindTexture(int $$0) {
/* 593 */     RenderSystem.assertOnRenderThreadOrInit();
/* 594 */     if ($$0 != (TEXTURES[activeTexture]).binding) {
/* 595 */       (TEXTURES[activeTexture]).binding = $$0;
/* 596 */       GL11.glBindTexture(3553, $$0);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int _getActiveTexture() {
/* 601 */     return activeTexture + 33984;
/*     */   }
/*     */   
/*     */   public static void _texImage2D(int $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, @Nullable IntBuffer $$8) {
/* 605 */     RenderSystem.assertOnRenderThreadOrInit();
/* 606 */     GL11.glTexImage2D($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8);
/*     */   }
/*     */   
/*     */   public static void _texSubImage2D(int $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, long $$8) {
/* 610 */     RenderSystem.assertOnRenderThreadOrInit();
/* 611 */     GL11.glTexSubImage2D($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8);
/*     */   }
/*     */   
/*     */   public static void upload(int $$0, int $$1, int $$2, int $$3, int $$4, NativeImage.Format $$5, IntBuffer $$6, Consumer<IntBuffer> $$7) {
/* 615 */     if (!RenderSystem.isOnRenderThreadOrInit()) {
/* 616 */       RenderSystem.recordRenderCall(() -> _upload($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7));
/*     */     } else {
/* 618 */       _upload($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void _upload(int $$0, int $$1, int $$2, int $$3, int $$4, NativeImage.Format $$5, IntBuffer $$6, Consumer<IntBuffer> $$7) {
/*     */     try {
/* 624 */       RenderSystem.assertOnRenderThreadOrInit();
/*     */       
/* 626 */       _pixelStore(3314, $$3);
/* 627 */       _pixelStore(3316, 0);
/* 628 */       _pixelStore(3315, 0);
/* 629 */       $$5.setUnpackPixelStoreState();
/* 630 */       GL11.glTexSubImage2D(3553, $$0, $$1, $$2, $$3, $$4, $$5.glFormat(), 5121, $$6);
/*     */     } finally {
/* 632 */       $$7.accept($$6);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void _getTexImage(int $$0, int $$1, int $$2, int $$3, long $$4) {
/* 637 */     RenderSystem.assertOnRenderThread();
/* 638 */     GL11.glGetTexImage($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */   
/*     */   public static void _viewport(int $$0, int $$1, int $$2, int $$3) {
/* 642 */     RenderSystem.assertOnRenderThreadOrInit();
/* 643 */     Viewport.INSTANCE.x = $$0;
/* 644 */     Viewport.INSTANCE.y = $$1;
/* 645 */     Viewport.INSTANCE.width = $$2;
/* 646 */     Viewport.INSTANCE.height = $$3;
/* 647 */     GL11.glViewport($$0, $$1, $$2, $$3);
/*     */   }
/*     */   
/*     */   public static void _colorMask(boolean $$0, boolean $$1, boolean $$2, boolean $$3) {
/* 651 */     RenderSystem.assertOnRenderThread();
/* 652 */     if ($$0 != COLOR_MASK.red || $$1 != COLOR_MASK.green || $$2 != COLOR_MASK.blue || $$3 != COLOR_MASK.alpha) {
/* 653 */       COLOR_MASK.red = $$0;
/* 654 */       COLOR_MASK.green = $$1;
/* 655 */       COLOR_MASK.blue = $$2;
/* 656 */       COLOR_MASK.alpha = $$3;
/* 657 */       GL11.glColorMask($$0, $$1, $$2, $$3);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void _stencilFunc(int $$0, int $$1, int $$2) {
/* 662 */     RenderSystem.assertOnRenderThread();
/* 663 */     if ($$0 != STENCIL.func.func || $$0 != STENCIL.func.ref || $$0 != STENCIL.func.mask) {
/* 664 */       STENCIL.func.func = $$0;
/* 665 */       STENCIL.func.ref = $$1;
/* 666 */       STENCIL.func.mask = $$2;
/* 667 */       GL11.glStencilFunc($$0, $$1, $$2);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void _stencilMask(int $$0) {
/* 672 */     RenderSystem.assertOnRenderThread();
/* 673 */     if ($$0 != STENCIL.mask) {
/* 674 */       STENCIL.mask = $$0;
/* 675 */       GL11.glStencilMask($$0);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void _stencilOp(int $$0, int $$1, int $$2) {
/* 680 */     RenderSystem.assertOnRenderThread();
/* 681 */     if ($$0 != STENCIL.fail || $$1 != STENCIL.zfail || $$2 != STENCIL.zpass) {
/* 682 */       STENCIL.fail = $$0;
/* 683 */       STENCIL.zfail = $$1;
/* 684 */       STENCIL.zpass = $$2;
/* 685 */       GL11.glStencilOp($$0, $$1, $$2);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void _clearDepth(double $$0) {
/* 690 */     RenderSystem.assertOnRenderThreadOrInit();
/* 691 */     GL11.glClearDepth($$0);
/*     */   }
/*     */   
/*     */   public static void _clearColor(float $$0, float $$1, float $$2, float $$3) {
/* 695 */     RenderSystem.assertOnRenderThreadOrInit();
/* 696 */     GL11.glClearColor($$0, $$1, $$2, $$3);
/*     */   }
/*     */   
/*     */   public static void _clearStencil(int $$0) {
/* 700 */     RenderSystem.assertOnRenderThread();
/* 701 */     GL11.glClearStencil($$0);
/*     */   }
/*     */   
/*     */   public static void _clear(int $$0, boolean $$1) {
/* 705 */     RenderSystem.assertOnRenderThreadOrInit();
/* 706 */     GL11.glClear($$0);
/*     */     
/* 708 */     if ($$1) {
/* 709 */       _getError();
/*     */     }
/*     */   }
/*     */   
/*     */   public static void _glDrawPixels(int $$0, int $$1, int $$2, int $$3, long $$4) {
/* 714 */     RenderSystem.assertOnRenderThread();
/* 715 */     GL11.glDrawPixels($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */   
/*     */   public static void _vertexAttribPointer(int $$0, int $$1, int $$2, boolean $$3, int $$4, long $$5) {
/* 719 */     RenderSystem.assertOnRenderThread();
/* 720 */     GL20.glVertexAttribPointer($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */   
/*     */   public static void _vertexAttribIPointer(int $$0, int $$1, int $$2, int $$3, long $$4) {
/* 724 */     RenderSystem.assertOnRenderThread();
/* 725 */     GL30.glVertexAttribIPointer($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */   
/*     */   public static void _enableVertexAttribArray(int $$0) {
/* 729 */     RenderSystem.assertOnRenderThread();
/* 730 */     GL20.glEnableVertexAttribArray($$0);
/*     */   }
/*     */   
/*     */   public static void _disableVertexAttribArray(int $$0) {
/* 734 */     RenderSystem.assertOnRenderThread();
/* 735 */     GL20.glDisableVertexAttribArray($$0);
/*     */   }
/*     */   
/*     */   public static void _drawElements(int $$0, int $$1, int $$2, long $$3) {
/* 739 */     RenderSystem.assertOnRenderThread();
/* 740 */     GL11.glDrawElements($$0, $$1, $$2, $$3);
/*     */   }
/*     */   
/*     */   public static void _pixelStore(int $$0, int $$1) {
/* 744 */     RenderSystem.assertOnRenderThreadOrInit();
/* 745 */     GL11.glPixelStorei($$0, $$1);
/*     */   }
/*     */   
/*     */   public static void _readPixels(int $$0, int $$1, int $$2, int $$3, int $$4, int $$5, ByteBuffer $$6) {
/* 749 */     RenderSystem.assertOnRenderThread();
/* 750 */     GL11.glReadPixels($$0, $$1, $$2, $$3, $$4, $$5, $$6);
/*     */   }
/*     */   
/*     */   public static void _readPixels(int $$0, int $$1, int $$2, int $$3, int $$4, int $$5, long $$6) {
/* 754 */     RenderSystem.assertOnRenderThread();
/* 755 */     GL11.glReadPixels($$0, $$1, $$2, $$3, $$4, $$5, $$6);
/*     */   }
/*     */   
/*     */   public static int _getError() {
/* 759 */     RenderSystem.assertOnRenderThread();
/* 760 */     return GL11.glGetError();
/*     */   }
/*     */   
/*     */   public static String _getString(int $$0) {
/* 764 */     RenderSystem.assertOnRenderThread();
/* 765 */     return GL11.glGetString($$0);
/*     */   }
/*     */   
/*     */   public static int _getInteger(int $$0) {
/* 769 */     RenderSystem.assertOnRenderThreadOrInit();
/* 770 */     return GL11.glGetInteger($$0);
/*     */   }
/*     */   
/*     */   public enum Viewport
/*     */   {
/* 775 */     INSTANCE;
/*     */     protected int height;
/*     */     protected int width;
/*     */     protected int y;
/*     */     protected int x;
/*     */     
/*     */     public static int x() {
/* 782 */       return INSTANCE.x;
/*     */     }
/*     */     
/*     */     public static int y() {
/* 786 */       return INSTANCE.y;
/*     */     }
/*     */     
/*     */     public static int width() {
/* 790 */       return INSTANCE.width;
/*     */     }
/*     */     
/*     */     public static int height() {
/* 794 */       return INSTANCE.height;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class TextureState {
/*     */     public int binding;
/*     */   }
/*     */   
/*     */   private static class BlendState {
/* 803 */     public final GlStateManager.BooleanState mode = new GlStateManager.BooleanState(3042);
/* 804 */     public int srcRgb = 1;
/* 805 */     public int dstRgb = 0;
/* 806 */     public int srcAlpha = 1;
/* 807 */     public int dstAlpha = 0;
/*     */   }
/*     */   
/*     */   private static class DepthState {
/* 811 */     public final GlStateManager.BooleanState mode = new GlStateManager.BooleanState(2929);
/*     */     public boolean mask = true;
/* 813 */     public int func = 513;
/*     */   }
/*     */   
/*     */   private static class CullState {
/* 817 */     public final GlStateManager.BooleanState enable = new GlStateManager.BooleanState(2884);
/* 818 */     public int mode = 1029;
/*     */   }
/*     */   
/*     */   private static class PolygonOffsetState {
/* 822 */     public final GlStateManager.BooleanState fill = new GlStateManager.BooleanState(32823);
/* 823 */     public final GlStateManager.BooleanState line = new GlStateManager.BooleanState(10754);
/*     */     public float factor;
/*     */     public float units;
/*     */   }
/*     */   
/*     */   private static class ColorLogicState {
/* 829 */     public final GlStateManager.BooleanState enable = new GlStateManager.BooleanState(3058);
/* 830 */     public int op = 5379; }
/*     */   private static class StencilFunc { public int func; public int ref; public int mask;
/*     */     
/*     */     StencilFunc() {
/* 834 */       this.func = 519;
/*     */       
/* 836 */       this.mask = -1;
/*     */     } }
/*     */   private static class StencilState { public final GlStateManager.StencilFunc func; public int mask; public int fail; public int zfail; public int zpass;
/*     */     StencilState() {
/* 840 */       this.func = new GlStateManager.StencilFunc();
/* 841 */       this.mask = -1;
/* 842 */       this.fail = 7680;
/* 843 */       this.zfail = 7680;
/* 844 */       this.zpass = 7680;
/*     */     } }
/*     */ 
/*     */   
/* 848 */   private static class ScissorState { public final GlStateManager.BooleanState mode = new GlStateManager.BooleanState(3089); }
/*     */   private static class ColorMask { public boolean red; public boolean green; public boolean blue; public boolean alpha;
/*     */     
/*     */     ColorMask() {
/* 852 */       this.red = true;
/* 853 */       this.green = true;
/* 854 */       this.blue = true;
/* 855 */       this.alpha = true;
/*     */     } }
/*     */   
/*     */   private static class BooleanState {
/*     */     private final int state;
/*     */     private boolean enabled;
/*     */     
/*     */     public BooleanState(int $$0) {
/* 863 */       this.state = $$0;
/*     */     }
/*     */     
/*     */     public void disable() {
/* 867 */       setEnabled(false);
/*     */     }
/*     */     
/*     */     public void enable() {
/* 871 */       setEnabled(true);
/*     */     }
/*     */     
/*     */     public void setEnabled(boolean $$0) {
/* 875 */       RenderSystem.assertOnRenderThreadOrInit();
/* 876 */       if ($$0 != this.enabled) {
/* 877 */         this.enabled = $$0;
/* 878 */         if ($$0) {
/* 879 */           GL11.glEnable(this.state);
/*     */         } else {
/* 881 */           GL11.glDisable(this.state);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   @DontObfuscate
/*     */   public enum SourceFactor {
/* 889 */     CONSTANT_ALPHA(32771),
/* 890 */     CONSTANT_COLOR(32769),
/* 891 */     DST_ALPHA(772),
/* 892 */     DST_COLOR(774),
/* 893 */     ONE(1),
/* 894 */     ONE_MINUS_CONSTANT_ALPHA(32772),
/* 895 */     ONE_MINUS_CONSTANT_COLOR(32770),
/* 896 */     ONE_MINUS_DST_ALPHA(773),
/* 897 */     ONE_MINUS_DST_COLOR(775),
/* 898 */     ONE_MINUS_SRC_ALPHA(771),
/* 899 */     ONE_MINUS_SRC_COLOR(769),
/* 900 */     SRC_ALPHA(770),
/* 901 */     SRC_ALPHA_SATURATE(776),
/* 902 */     SRC_COLOR(768),
/* 903 */     ZERO(0);
/*     */     
/*     */     public final int value;
/*     */ 
/*     */     
/*     */     SourceFactor(int $$0) {
/* 909 */       this.value = $$0;
/*     */     }
/*     */   }
/*     */   
/*     */   @DontObfuscate
/*     */   public enum DestFactor {
/* 915 */     CONSTANT_ALPHA(32771),
/* 916 */     CONSTANT_COLOR(32769),
/* 917 */     DST_ALPHA(772),
/* 918 */     DST_COLOR(774),
/* 919 */     ONE(1),
/* 920 */     ONE_MINUS_CONSTANT_ALPHA(32772),
/* 921 */     ONE_MINUS_CONSTANT_COLOR(32770),
/* 922 */     ONE_MINUS_DST_ALPHA(773),
/* 923 */     ONE_MINUS_DST_COLOR(775),
/* 924 */     ONE_MINUS_SRC_ALPHA(771),
/* 925 */     ONE_MINUS_SRC_COLOR(769),
/* 926 */     SRC_ALPHA(770),
/* 927 */     SRC_COLOR(768),
/* 928 */     ZERO(0);
/*     */     
/*     */     public final int value;
/*     */ 
/*     */     
/*     */     DestFactor(int $$0) {
/* 934 */       this.value = $$0;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\platform\GlStateManager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */