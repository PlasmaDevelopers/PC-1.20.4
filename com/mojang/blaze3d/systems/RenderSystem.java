/*      */ package com.mojang.blaze3d.systems;
/*      */ 
/*      */ import com.google.common.collect.Queues;
/*      */ import com.mojang.blaze3d.DontObfuscate;
/*      */ import com.mojang.blaze3d.pipeline.RenderCall;
/*      */ import com.mojang.blaze3d.platform.GLX;
/*      */ import com.mojang.blaze3d.platform.GlStateManager;
/*      */ import com.mojang.blaze3d.shaders.FogShape;
/*      */ import com.mojang.blaze3d.vertex.PoseStack;
/*      */ import com.mojang.blaze3d.vertex.Tesselator;
/*      */ import com.mojang.blaze3d.vertex.VertexFormat;
/*      */ import com.mojang.blaze3d.vertex.VertexSorting;
/*      */ import com.mojang.logging.LogUtils;
/*      */ import it.unimi.dsi.fastutil.ints.IntConsumer;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.FloatBuffer;
/*      */ import java.nio.IntBuffer;
/*      */ import java.util.Locale;
/*      */ import java.util.Objects;
/*      */ import java.util.concurrent.ConcurrentLinkedQueue;
/*      */ import java.util.concurrent.atomic.AtomicBoolean;
/*      */ import java.util.concurrent.atomic.AtomicLong;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.IntConsumer;
/*      */ import java.util.function.IntSupplier;
/*      */ import java.util.function.Supplier;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.Util;
/*      */ import net.minecraft.client.GraphicsStatus;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.OptionInstance;
/*      */ import net.minecraft.client.renderer.ShaderInstance;
/*      */ import net.minecraft.client.renderer.texture.AbstractTexture;
/*      */ import net.minecraft.client.renderer.texture.TextureManager;
/*      */ import net.minecraft.resources.ResourceLocation;
/*      */ import net.minecraft.util.Mth;
/*      */ import net.minecraft.util.TimeSource;
/*      */ import org.joml.Matrix3f;
/*      */ import org.joml.Matrix3fc;
/*      */ import org.joml.Matrix4f;
/*      */ import org.joml.Matrix4fc;
/*      */ import org.joml.Vector3f;
/*      */ import org.lwjgl.glfw.GLFW;
/*      */ import org.lwjgl.glfw.GLFWErrorCallbackI;
/*      */ import org.slf4j.Logger;
/*      */ 
/*      */ @DontObfuscate
/*      */ public class RenderSystem {
/*   49 */   static final Logger LOGGER = LogUtils.getLogger();
/*   50 */   private static final ConcurrentLinkedQueue<RenderCall> recordingQueue = Queues.newConcurrentLinkedQueue();
/*   51 */   private static final Tesselator RENDER_THREAD_TESSELATOR = new Tesselator(1536);
/*      */   
/*      */   private static final int MINIMUM_ATLAS_TEXTURE_SIZE = 1024;
/*      */   
/*      */   private static boolean isReplayingQueue;
/*      */   
/*      */   @Nullable
/*      */   private static Thread gameThread;
/*      */   @Nullable
/*      */   private static Thread renderThread;
/*   61 */   private static int MAX_SUPPORTED_TEXTURE_SIZE = -1;
/*      */   
/*      */   private static boolean isInInit;
/*   64 */   private static double lastDrawTime = Double.MIN_VALUE;
/*      */   
/*   66 */   private static final AutoStorageIndexBuffer sharedSequential = new AutoStorageIndexBuffer(1, 1, IntConsumer::accept); private static final AutoStorageIndexBuffer sharedSequentialQuad; private static final AutoStorageIndexBuffer sharedSequentialLines; static {
/*   67 */     sharedSequentialQuad = new AutoStorageIndexBuffer(4, 6, ($$0, $$1) -> {
/*      */           $$0.accept($$1 + 0);
/*      */           $$0.accept($$1 + 1);
/*      */           $$0.accept($$1 + 2);
/*      */           $$0.accept($$1 + 2);
/*      */           $$0.accept($$1 + 3);
/*      */           $$0.accept($$1 + 0);
/*      */         });
/*   75 */     sharedSequentialLines = new AutoStorageIndexBuffer(4, 6, ($$0, $$1) -> {
/*      */           $$0.accept($$1 + 0);
/*      */           $$0.accept($$1 + 1);
/*      */           $$0.accept($$1 + 2);
/*      */           $$0.accept($$1 + 3);
/*      */           $$0.accept($$1 + 2);
/*      */           $$0.accept($$1 + 1);
/*      */         });
/*      */   }
/*   84 */   private static Matrix3f inverseViewRotationMatrix = (new Matrix3f()).zero();
/*   85 */   private static Matrix4f projectionMatrix = new Matrix4f();
/*   86 */   private static Matrix4f savedProjectionMatrix = new Matrix4f();
/*   87 */   private static VertexSorting vertexSorting = VertexSorting.DISTANCE_TO_ORIGIN;
/*   88 */   private static VertexSorting savedVertexSorting = VertexSorting.DISTANCE_TO_ORIGIN;
/*      */   
/*   90 */   private static final PoseStack modelViewStack = new PoseStack();
/*   91 */   private static Matrix4f modelViewMatrix = new Matrix4f();
/*      */   
/*   93 */   private static Matrix4f textureMatrix = new Matrix4f();
/*      */   
/*   95 */   private static final int[] shaderTextures = new int[12];
/*      */   
/*   97 */   private static final float[] shaderColor = new float[] { 1.0F, 1.0F, 1.0F, 1.0F };
/*      */   
/*   99 */   private static float shaderGlintAlpha = 1.0F;
/*      */   private static float shaderFogStart;
/*  101 */   private static float shaderFogEnd = 1.0F;
/*  102 */   private static final float[] shaderFogColor = new float[] { 0.0F, 0.0F, 0.0F, 0.0F };
/*  103 */   private static FogShape shaderFogShape = FogShape.SPHERE;
/*      */   
/*  105 */   private static final Vector3f[] shaderLightDirections = new Vector3f[2];
/*      */   
/*      */   private static float shaderGameTime;
/*      */   
/*  109 */   private static float shaderLineWidth = 1.0F;
/*      */   
/*  111 */   private static String apiDescription = "Unknown";
/*      */   
/*      */   @Nullable
/*      */   private static ShaderInstance shader;
/*      */   
/*  116 */   private static final AtomicLong pollEventsWaitStart = new AtomicLong();
/*  117 */   private static final AtomicBoolean pollingEvents = new AtomicBoolean(false);
/*      */   
/*      */   public static void initRenderThread() {
/*  120 */     if (renderThread != null || gameThread == Thread.currentThread()) {
/*  121 */       throw new IllegalStateException("Could not initialize render thread");
/*      */     }
/*  123 */     renderThread = Thread.currentThread();
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isOnRenderThread() {
/*  128 */     return (Thread.currentThread() == renderThread);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isOnRenderThreadOrInit() {
/*  134 */     return (isInInit || isOnRenderThread());
/*      */   }
/*      */   
/*      */   public static void initGameThread(boolean $$0) {
/*  138 */     boolean $$1 = (renderThread == Thread.currentThread());
/*  139 */     if (gameThread != null || renderThread == null || $$1 == $$0) {
/*  140 */       throw new IllegalStateException("Could not initialize tick thread");
/*      */     }
/*  142 */     gameThread = Thread.currentThread();
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isOnGameThread() {
/*  147 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void assertInInitPhase() {
/*  153 */     if (!isInInitPhase()) {
/*  154 */       throw constructThreadException();
/*      */     }
/*      */   }
/*      */   
/*      */   public static void assertOnGameThreadOrInit() {
/*  159 */     if (isInInit || isOnGameThread()) {
/*      */       return;
/*      */     }
/*  162 */     throw constructThreadException();
/*      */   }
/*      */   
/*      */   public static void assertOnRenderThreadOrInit() {
/*  166 */     if (isInInit || isOnRenderThread()) {
/*      */       return;
/*      */     }
/*  169 */     throw constructThreadException();
/*      */   }
/*      */   
/*      */   public static void assertOnRenderThread() {
/*  173 */     if (!isOnRenderThread()) {
/*  174 */       throw constructThreadException();
/*      */     }
/*      */   }
/*      */   
/*      */   public static void assertOnGameThread() {
/*  179 */     if (!isOnGameThread()) {
/*  180 */       throw constructThreadException();
/*      */     }
/*      */   }
/*      */   
/*      */   private static IllegalStateException constructThreadException() {
/*  185 */     return new IllegalStateException("Rendersystem called from wrong thread");
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isInInitPhase() {
/*  190 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void recordRenderCall(RenderCall $$0) {
/*  196 */     recordingQueue.add($$0);
/*      */   }
/*      */   
/*      */   private static void pollEvents() {
/*  200 */     pollEventsWaitStart.set(Util.getMillis());
/*  201 */     pollingEvents.set(true);
/*  202 */     GLFW.glfwPollEvents();
/*      */     
/*  204 */     pollingEvents.set(false);
/*      */   }
/*      */   
/*      */   public static boolean isFrozenAtPollEvents() {
/*  208 */     return (pollingEvents.get() && Util.getMillis() - pollEventsWaitStart.get() > 200L);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void flipFrame(long $$0) {
/*  213 */     pollEvents();
/*  214 */     replayQueue();
/*      */ 
/*      */     
/*  217 */     Tesselator.getInstance().getBuilder().clear();
/*      */     
/*  219 */     GLFW.glfwSwapBuffers($$0);
/*  220 */     pollEvents();
/*      */   }
/*      */   
/*      */   public static void replayQueue() {
/*  224 */     isReplayingQueue = true;
/*  225 */     while (!recordingQueue.isEmpty()) {
/*  226 */       RenderCall $$0 = recordingQueue.poll();
/*  227 */       $$0.execute();
/*      */     } 
/*  229 */     isReplayingQueue = false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void limitDisplayFPS(int $$0) {
/*  235 */     double $$1 = lastDrawTime + 1.0D / $$0;
/*  236 */     double $$2 = GLFW.glfwGetTime();
/*  237 */     while ($$2 < $$1) {
/*  238 */       GLFW.glfwWaitEventsTimeout($$1 - $$2);
/*  239 */       $$2 = GLFW.glfwGetTime();
/*      */     } 
/*  241 */     lastDrawTime = $$2;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void disableDepthTest() {
/*  246 */     assertOnRenderThread();
/*      */ 
/*      */ 
/*      */     
/*  250 */     GlStateManager._disableDepthTest();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enableDepthTest() {
/*  255 */     assertOnGameThreadOrInit();
/*      */ 
/*      */ 
/*      */     
/*  259 */     GlStateManager._enableDepthTest();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enableScissor(int $$0, int $$1, int $$2, int $$3) {
/*  264 */     assertOnGameThreadOrInit();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  271 */     GlStateManager._enableScissorTest();
/*  272 */     GlStateManager._scissorBox($$0, $$1, $$2, $$3);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void disableScissor() {
/*  277 */     assertOnGameThreadOrInit();
/*      */ 
/*      */ 
/*      */     
/*  281 */     GlStateManager._disableScissorTest();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void depthFunc(int $$0) {
/*  286 */     assertOnRenderThread();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  292 */     GlStateManager._depthFunc($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void depthMask(boolean $$0) {
/*  297 */     assertOnRenderThread();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  303 */     GlStateManager._depthMask($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enableBlend() {
/*  308 */     assertOnRenderThread();
/*      */ 
/*      */ 
/*      */     
/*  312 */     GlStateManager._enableBlend();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void disableBlend() {
/*  318 */     assertOnRenderThread();
/*      */ 
/*      */ 
/*      */     
/*  322 */     GlStateManager._disableBlend();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void blendFunc(GlStateManager.SourceFactor $$0, GlStateManager.DestFactor $$1) {
/*  327 */     assertOnRenderThread();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  333 */     GlStateManager._blendFunc($$0.value, $$1.value);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void blendFunc(int $$0, int $$1) {
/*  338 */     assertOnRenderThread();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  344 */     GlStateManager._blendFunc($$0, $$1);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void blendFuncSeparate(GlStateManager.SourceFactor $$0, GlStateManager.DestFactor $$1, GlStateManager.SourceFactor $$2, GlStateManager.DestFactor $$3) {
/*  349 */     assertOnRenderThread();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  355 */     GlStateManager._blendFuncSeparate($$0.value, $$1.value, $$2.value, $$3.value);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void blendFuncSeparate(int $$0, int $$1, int $$2, int $$3) {
/*  360 */     assertOnRenderThread();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  366 */     GlStateManager._blendFuncSeparate($$0, $$1, $$2, $$3);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void blendEquation(int $$0) {
/*  371 */     assertOnRenderThread();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  377 */     GlStateManager._blendEquation($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enableCull() {
/*  382 */     assertOnRenderThread();
/*      */ 
/*      */ 
/*      */     
/*  386 */     GlStateManager._enableCull();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void disableCull() {
/*  391 */     assertOnRenderThread();
/*      */ 
/*      */ 
/*      */     
/*  395 */     GlStateManager._disableCull();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void polygonMode(int $$0, int $$1) {
/*  401 */     assertOnRenderThread();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  407 */     GlStateManager._polygonMode($$0, $$1);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enablePolygonOffset() {
/*  412 */     assertOnRenderThread();
/*      */ 
/*      */ 
/*      */     
/*  416 */     GlStateManager._enablePolygonOffset();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void disablePolygonOffset() {
/*  421 */     assertOnRenderThread();
/*      */ 
/*      */ 
/*      */     
/*  425 */     GlStateManager._disablePolygonOffset();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void polygonOffset(float $$0, float $$1) {
/*  430 */     assertOnRenderThread();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  436 */     GlStateManager._polygonOffset($$0, $$1);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enableColorLogicOp() {
/*  441 */     assertOnRenderThread();
/*      */ 
/*      */ 
/*      */     
/*  445 */     GlStateManager._enableColorLogicOp();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void disableColorLogicOp() {
/*  450 */     assertOnRenderThread();
/*      */ 
/*      */ 
/*      */     
/*  454 */     GlStateManager._disableColorLogicOp();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void logicOp(GlStateManager.LogicOp $$0) {
/*  459 */     assertOnRenderThread();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  465 */     GlStateManager._logicOp($$0.value);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void activeTexture(int $$0) {
/*  471 */     assertOnRenderThread();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  477 */     GlStateManager._activeTexture($$0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void texParameter(int $$0, int $$1, int $$2) {
/*  488 */     GlStateManager._texParameter($$0, $$1, $$2);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void deleteTexture(int $$0) {
/*  493 */     assertOnGameThreadOrInit();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  499 */     GlStateManager._deleteTexture($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void bindTextureForSetup(int $$0) {
/*  504 */     bindTexture($$0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void bindTexture(int $$0) {
/*  514 */     GlStateManager._bindTexture($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void viewport(int $$0, int $$1, int $$2, int $$3) {
/*  519 */     assertOnGameThreadOrInit();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  525 */     GlStateManager._viewport($$0, $$1, $$2, $$3);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void colorMask(boolean $$0, boolean $$1, boolean $$2, boolean $$3) {
/*  531 */     assertOnRenderThread();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  537 */     GlStateManager._colorMask($$0, $$1, $$2, $$3);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void stencilFunc(int $$0, int $$1, int $$2) {
/*  543 */     assertOnRenderThread();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  549 */     GlStateManager._stencilFunc($$0, $$1, $$2);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void stencilMask(int $$0) {
/*  554 */     assertOnRenderThread();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  560 */     GlStateManager._stencilMask($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void stencilOp(int $$0, int $$1, int $$2) {
/*  565 */     assertOnRenderThread();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  571 */     GlStateManager._stencilOp($$0, $$1, $$2);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void clearDepth(double $$0) {
/*  577 */     assertOnGameThreadOrInit();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  583 */     GlStateManager._clearDepth($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void clearColor(float $$0, float $$1, float $$2, float $$3) {
/*  588 */     assertOnGameThreadOrInit();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  594 */     GlStateManager._clearColor($$0, $$1, $$2, $$3);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void clearStencil(int $$0) {
/*  599 */     assertOnRenderThread();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  605 */     GlStateManager._clearStencil($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void clear(int $$0, boolean $$1) {
/*  610 */     assertOnGameThreadOrInit();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  616 */     GlStateManager._clear($$0, $$1);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setShaderFogStart(float $$0) {
/*  621 */     assertOnRenderThread();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  627 */     _setShaderFogStart($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void _setShaderFogStart(float $$0) {
/*  632 */     shaderFogStart = $$0;
/*      */   }
/*      */   
/*      */   public static float getShaderFogStart() {
/*  636 */     assertOnRenderThread();
/*  637 */     return shaderFogStart;
/*      */   }
/*      */   
/*      */   public static void setShaderGlintAlpha(double $$0) {
/*  641 */     setShaderGlintAlpha((float)$$0);
/*      */   }
/*      */   
/*      */   public static void setShaderGlintAlpha(float $$0) {
/*  645 */     assertOnRenderThread();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  651 */     _setShaderGlintAlpha($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void _setShaderGlintAlpha(float $$0) {
/*  656 */     shaderGlintAlpha = $$0;
/*      */   }
/*      */   
/*      */   public static float getShaderGlintAlpha() {
/*  660 */     assertOnRenderThread();
/*  661 */     return shaderGlintAlpha;
/*      */   }
/*      */   
/*      */   public static void setShaderFogEnd(float $$0) {
/*  665 */     assertOnRenderThread();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  671 */     _setShaderFogEnd($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void _setShaderFogEnd(float $$0) {
/*  676 */     shaderFogEnd = $$0;
/*      */   }
/*      */   
/*      */   public static float getShaderFogEnd() {
/*  680 */     assertOnRenderThread();
/*  681 */     return shaderFogEnd;
/*      */   }
/*      */   
/*      */   public static void setShaderFogColor(float $$0, float $$1, float $$2, float $$3) {
/*  685 */     assertOnRenderThread();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  691 */     _setShaderFogColor($$0, $$1, $$2, $$3);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setShaderFogColor(float $$0, float $$1, float $$2) {
/*  696 */     setShaderFogColor($$0, $$1, $$2, 1.0F);
/*      */   }
/*      */   
/*      */   private static void _setShaderFogColor(float $$0, float $$1, float $$2, float $$3) {
/*  700 */     shaderFogColor[0] = $$0;
/*  701 */     shaderFogColor[1] = $$1;
/*  702 */     shaderFogColor[2] = $$2;
/*  703 */     shaderFogColor[3] = $$3;
/*      */   }
/*      */   
/*      */   public static float[] getShaderFogColor() {
/*  707 */     assertOnRenderThread();
/*  708 */     return shaderFogColor;
/*      */   }
/*      */   
/*      */   public static void setShaderFogShape(FogShape $$0) {
/*  712 */     assertOnRenderThread();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  718 */     _setShaderFogShape($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void _setShaderFogShape(FogShape $$0) {
/*  723 */     shaderFogShape = $$0;
/*      */   }
/*      */   
/*      */   public static FogShape getShaderFogShape() {
/*  727 */     assertOnRenderThread();
/*  728 */     return shaderFogShape;
/*      */   }
/*      */   
/*      */   public static void setShaderLights(Vector3f $$0, Vector3f $$1) {
/*  732 */     assertOnRenderThread();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  738 */     _setShaderLights($$0, $$1);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void _setShaderLights(Vector3f $$0, Vector3f $$1) {
/*  743 */     shaderLightDirections[0] = $$0;
/*  744 */     shaderLightDirections[1] = $$1;
/*      */   }
/*      */   
/*      */   public static void setupShaderLights(ShaderInstance $$0) {
/*  748 */     assertOnRenderThread();
/*  749 */     if ($$0.LIGHT0_DIRECTION != null) {
/*  750 */       $$0.LIGHT0_DIRECTION.set(shaderLightDirections[0]);
/*      */     }
/*  752 */     if ($$0.LIGHT1_DIRECTION != null) {
/*  753 */       $$0.LIGHT1_DIRECTION.set(shaderLightDirections[1]);
/*      */     }
/*      */   }
/*      */   
/*      */   public static void setShaderColor(float $$0, float $$1, float $$2, float $$3) {
/*  758 */     if (!isOnRenderThread()) {
/*  759 */       recordRenderCall(() -> _setShaderColor($$0, $$1, $$2, $$3));
/*      */     }
/*      */     else {
/*      */       
/*  763 */       _setShaderColor($$0, $$1, $$2, $$3);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void _setShaderColor(float $$0, float $$1, float $$2, float $$3) {
/*  768 */     shaderColor[0] = $$0;
/*  769 */     shaderColor[1] = $$1;
/*  770 */     shaderColor[2] = $$2;
/*  771 */     shaderColor[3] = $$3;
/*      */   }
/*      */   
/*      */   public static float[] getShaderColor() {
/*  775 */     assertOnRenderThread();
/*  776 */     return shaderColor;
/*      */   }
/*      */   
/*      */   public static void drawElements(int $$0, int $$1, int $$2) {
/*  780 */     assertOnRenderThread();
/*      */ 
/*      */ 
/*      */     
/*  784 */     GlStateManager._drawElements($$0, $$1, $$2, 0L);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void lineWidth(float $$0) {
/*  789 */     if (!isOnRenderThread()) {
/*  790 */       recordRenderCall(() -> shaderLineWidth = $$0);
/*      */     }
/*      */     else {
/*      */       
/*  794 */       shaderLineWidth = $$0;
/*      */     } 
/*      */   }
/*      */   
/*      */   public static float getShaderLineWidth() {
/*  799 */     assertOnRenderThread();
/*  800 */     return shaderLineWidth;
/*      */   }
/*      */   
/*      */   public static void pixelStore(int $$0, int $$1) {
/*  804 */     assertOnGameThreadOrInit();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  810 */     GlStateManager._pixelStore($$0, $$1);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void readPixels(int $$0, int $$1, int $$2, int $$3, int $$4, int $$5, ByteBuffer $$6) {
/*  815 */     assertOnRenderThread();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  821 */     GlStateManager._readPixels($$0, $$1, $$2, $$3, $$4, $$5, $$6);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void getString(int $$0, Consumer<String> $$1) {
/*  826 */     assertOnRenderThread();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  833 */     $$1.accept(GlStateManager._getString($$0));
/*      */   }
/*      */ 
/*      */   
/*      */   public static String getBackendDescription() {
/*  838 */     assertInInitPhase();
/*  839 */     return String.format(Locale.ROOT, "LWJGL version %s", new Object[] { GLX._getLWJGLVersion() });
/*      */   }
/*      */   
/*      */   public static String getApiDescription() {
/*  843 */     return apiDescription;
/*      */   }
/*      */   
/*      */   public static TimeSource.NanoTimeSource initBackendSystem() {
/*  847 */     assertInInitPhase();
/*  848 */     Objects.requireNonNull(GLX._initGlfw()); return GLX._initGlfw()::getAsLong;
/*      */   }
/*      */   
/*      */   public static void initRenderer(int $$0, boolean $$1) {
/*  852 */     assertInInitPhase();
/*  853 */     GLX._init($$0, $$1);
/*  854 */     apiDescription = GLX.getOpenGLVersionString();
/*      */   }
/*      */   
/*      */   public static void setErrorCallback(GLFWErrorCallbackI $$0) {
/*  858 */     assertInInitPhase();
/*  859 */     GLX._setGlfwErrorCallback($$0);
/*      */   }
/*      */   
/*      */   public static void renderCrosshair(int $$0) {
/*  863 */     assertOnRenderThread();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  869 */     GLX._renderCrosshair($$0, true, true, true);
/*      */   }
/*      */ 
/*      */   
/*      */   public static String getCapsString() {
/*  874 */     assertOnRenderThread();
/*  875 */     return "Using framebuffer using OpenGL 3.2";
/*      */   }
/*      */   
/*      */   public static void setupDefaultState(int $$0, int $$1, int $$2, int $$3) {
/*  879 */     assertInInitPhase();
/*  880 */     GlStateManager._clearDepth(1.0D);
/*  881 */     GlStateManager._enableDepthTest();
/*  882 */     GlStateManager._depthFunc(515);
/*      */     
/*  884 */     projectionMatrix.identity();
/*  885 */     savedProjectionMatrix.identity();
/*      */     
/*  887 */     modelViewMatrix.identity();
/*      */     
/*  889 */     textureMatrix.identity();
/*      */     
/*  891 */     GlStateManager._viewport($$0, $$1, $$2, $$3);
/*      */   }
/*      */   
/*      */   public static int maxSupportedTextureSize() {
/*  895 */     if (MAX_SUPPORTED_TEXTURE_SIZE == -1) {
/*  896 */       assertOnRenderThreadOrInit();
/*  897 */       int $$0 = GlStateManager._getInteger(3379);
/*  898 */       for (int $$1 = Math.max(32768, $$0); $$1 >= 1024; $$1 >>= 1) {
/*  899 */         GlStateManager._texImage2D(32868, 0, 6408, $$1, $$1, 0, 6408, 5121, null);
/*  900 */         int $$2 = GlStateManager._getTexLevelParameter(32868, 0, 4096);
/*  901 */         if ($$2 != 0) {
/*  902 */           MAX_SUPPORTED_TEXTURE_SIZE = $$1;
/*  903 */           return $$1;
/*      */         } 
/*      */       } 
/*  906 */       MAX_SUPPORTED_TEXTURE_SIZE = Math.max($$0, 1024);
/*  907 */       LOGGER.info("Failed to determine maximum texture size by probing, trying GL_MAX_TEXTURE_SIZE = {}", Integer.valueOf(MAX_SUPPORTED_TEXTURE_SIZE));
/*      */     } 
/*      */     
/*  910 */     return MAX_SUPPORTED_TEXTURE_SIZE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void glBindBuffer(int $$0, IntSupplier $$1) {
/*  920 */     GlStateManager._glBindBuffer($$0, $$1.getAsInt());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void glBindVertexArray(Supplier<Integer> $$0) {
/*  931 */     GlStateManager._glBindVertexArray(((Integer)$$0.get()).intValue());
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glBufferData(int $$0, ByteBuffer $$1, int $$2) {
/*  936 */     assertOnRenderThreadOrInit();
/*  937 */     GlStateManager._glBufferData($$0, $$1, $$2);
/*      */   }
/*      */   
/*      */   public static void glDeleteBuffers(int $$0) {
/*  941 */     assertOnRenderThread();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  947 */     GlStateManager._glDeleteBuffers($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glDeleteVertexArrays(int $$0) {
/*  952 */     assertOnRenderThread();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  958 */     GlStateManager._glDeleteVertexArrays($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glUniform1i(int $$0, int $$1) {
/*  963 */     assertOnRenderThread();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  969 */     GlStateManager._glUniform1i($$0, $$1);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glUniform1(int $$0, IntBuffer $$1) {
/*  974 */     assertOnRenderThread();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  980 */     GlStateManager._glUniform1($$0, $$1);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glUniform2(int $$0, IntBuffer $$1) {
/*  985 */     assertOnRenderThread();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  991 */     GlStateManager._glUniform2($$0, $$1);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glUniform3(int $$0, IntBuffer $$1) {
/*  996 */     assertOnRenderThread();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1002 */     GlStateManager._glUniform3($$0, $$1);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glUniform4(int $$0, IntBuffer $$1) {
/* 1007 */     assertOnRenderThread();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1013 */     GlStateManager._glUniform4($$0, $$1);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glUniform1(int $$0, FloatBuffer $$1) {
/* 1018 */     assertOnRenderThread();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1024 */     GlStateManager._glUniform1($$0, $$1);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glUniform2(int $$0, FloatBuffer $$1) {
/* 1029 */     assertOnRenderThread();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1035 */     GlStateManager._glUniform2($$0, $$1);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glUniform3(int $$0, FloatBuffer $$1) {
/* 1040 */     assertOnRenderThread();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1046 */     GlStateManager._glUniform3($$0, $$1);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glUniform4(int $$0, FloatBuffer $$1) {
/* 1051 */     assertOnRenderThread();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1057 */     GlStateManager._glUniform4($$0, $$1);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glUniformMatrix2(int $$0, boolean $$1, FloatBuffer $$2) {
/* 1062 */     assertOnRenderThread();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1068 */     GlStateManager._glUniformMatrix2($$0, $$1, $$2);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glUniformMatrix3(int $$0, boolean $$1, FloatBuffer $$2) {
/* 1073 */     assertOnRenderThread();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1079 */     GlStateManager._glUniformMatrix3($$0, $$1, $$2);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glUniformMatrix4(int $$0, boolean $$1, FloatBuffer $$2) {
/* 1084 */     assertOnRenderThread();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1090 */     GlStateManager._glUniformMatrix4($$0, $$1, $$2);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setupOverlayColor(IntSupplier $$0, int $$1) {
/* 1095 */     assertOnRenderThread();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1102 */     int $$2 = $$0.getAsInt();
/* 1103 */     setShaderTexture(1, $$2);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void teardownOverlayColor() {
/* 1108 */     assertOnRenderThread();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1114 */     setShaderTexture(1, 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setupLevelDiffuseLighting(Vector3f $$0, Vector3f $$1, Matrix4f $$2) {
/* 1119 */     assertOnRenderThread();
/*      */ 
/*      */ 
/*      */     
/* 1123 */     GlStateManager.setupLevelDiffuseLighting($$0, $$1, $$2);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setupGuiFlatDiffuseLighting(Vector3f $$0, Vector3f $$1) {
/* 1128 */     assertOnRenderThread();
/*      */ 
/*      */ 
/*      */     
/* 1132 */     GlStateManager.setupGuiFlatDiffuseLighting($$0, $$1);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setupGui3DDiffuseLighting(Vector3f $$0, Vector3f $$1) {
/* 1137 */     assertOnRenderThread();
/*      */ 
/*      */ 
/*      */     
/* 1141 */     GlStateManager.setupGui3DDiffuseLighting($$0, $$1);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginInitialization() {
/* 1146 */     isInInit = true;
/*      */   }
/*      */   
/*      */   public static void finishInitialization() {
/* 1150 */     isInInit = false;
/* 1151 */     if (!recordingQueue.isEmpty()) {
/* 1152 */       replayQueue();
/*      */     }
/* 1154 */     if (!recordingQueue.isEmpty()) {
/* 1155 */       throw new IllegalStateException("Recorded to render queue during initialization");
/*      */     }
/*      */   }
/*      */   
/*      */   public static void glGenBuffers(Consumer<Integer> $$0) {
/* 1160 */     if (!isOnRenderThread()) {
/* 1161 */       recordRenderCall(() -> $$0.accept(Integer.valueOf(GlStateManager._glGenBuffers())));
/*      */     }
/*      */     else {
/*      */       
/* 1165 */       $$0.accept(Integer.valueOf(GlStateManager._glGenBuffers()));
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void glGenVertexArrays(Consumer<Integer> $$0) {
/* 1170 */     if (!isOnRenderThread()) {
/* 1171 */       recordRenderCall(() -> $$0.accept(Integer.valueOf(GlStateManager._glGenVertexArrays())));
/*      */     }
/*      */     else {
/*      */       
/* 1175 */       $$0.accept(Integer.valueOf(GlStateManager._glGenVertexArrays()));
/*      */     } 
/*      */   }
/*      */   
/*      */   public static Tesselator renderThreadTesselator() {
/* 1180 */     assertOnRenderThread();
/* 1181 */     return RENDER_THREAD_TESSELATOR;
/*      */   }
/*      */   
/*      */   public static void defaultBlendFunc() {
/* 1185 */     blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public static void runAsFancy(Runnable $$0) {
/* 1190 */     boolean $$1 = Minecraft.useShaderTransparency();
/* 1191 */     if (!$$1) {
/* 1192 */       $$0.run();
/*      */       
/*      */       return;
/*      */     } 
/* 1196 */     OptionInstance<GraphicsStatus> $$2 = (Minecraft.getInstance()).options.graphicsMode();
/* 1197 */     GraphicsStatus $$3 = (GraphicsStatus)$$2.get();
/*      */     
/* 1199 */     $$2.set(GraphicsStatus.FANCY);
/* 1200 */     $$0.run();
/* 1201 */     $$2.set($$3);
/*      */   }
/*      */   
/*      */   public static void setShader(Supplier<ShaderInstance> $$0) {
/* 1205 */     if (!isOnRenderThread()) {
/* 1206 */       recordRenderCall(() -> shader = $$0.get());
/*      */     }
/*      */     else {
/*      */       
/* 1210 */       shader = $$0.get();
/*      */     } 
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getShader() {
/* 1216 */     assertOnRenderThread();
/* 1217 */     return shader;
/*      */   }
/*      */   
/*      */   public static void setShaderTexture(int $$0, ResourceLocation $$1) {
/* 1221 */     if (!isOnRenderThread()) {
/* 1222 */       recordRenderCall(() -> _setShaderTexture($$0, $$1));
/*      */     }
/*      */     else {
/*      */       
/* 1226 */       _setShaderTexture($$0, $$1);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void _setShaderTexture(int $$0, ResourceLocation $$1) {
/* 1231 */     if ($$0 >= 0 && $$0 < shaderTextures.length) {
/* 1232 */       TextureManager $$2 = Minecraft.getInstance().getTextureManager();
/* 1233 */       AbstractTexture $$3 = $$2.getTexture($$1);
/* 1234 */       shaderTextures[$$0] = $$3.getId();
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void setShaderTexture(int $$0, int $$1) {
/* 1239 */     if (!isOnRenderThread()) {
/* 1240 */       recordRenderCall(() -> _setShaderTexture($$0, $$1));
/*      */     }
/*      */     else {
/*      */       
/* 1244 */       _setShaderTexture($$0, $$1);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void _setShaderTexture(int $$0, int $$1) {
/* 1249 */     if ($$0 >= 0 && $$0 < shaderTextures.length) {
/* 1250 */       shaderTextures[$$0] = $$1;
/*      */     }
/*      */   }
/*      */   
/*      */   public static int getShaderTexture(int $$0) {
/* 1255 */     assertOnRenderThread();
/* 1256 */     if ($$0 >= 0 && $$0 < shaderTextures.length) {
/* 1257 */       return shaderTextures[$$0];
/*      */     }
/* 1259 */     return 0;
/*      */   }
/*      */   
/*      */   public static void setProjectionMatrix(Matrix4f $$0, VertexSorting $$1) {
/* 1263 */     Matrix4f $$2 = new Matrix4f((Matrix4fc)$$0);
/* 1264 */     if (!isOnRenderThread()) {
/* 1265 */       recordRenderCall(() -> {
/*      */             projectionMatrix = $$0;
/*      */             vertexSorting = $$1;
/*      */           });
/*      */     } else {
/* 1270 */       projectionMatrix = $$2;
/* 1271 */       vertexSorting = $$1;
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void setInverseViewRotationMatrix(Matrix3f $$0) {
/* 1276 */     Matrix3f $$1 = new Matrix3f((Matrix3fc)$$0);
/* 1277 */     if (!isOnRenderThread()) {
/* 1278 */       recordRenderCall(() -> inverseViewRotationMatrix = $$0);
/*      */     }
/*      */     else {
/*      */       
/* 1282 */       inverseViewRotationMatrix = $$1;
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void setTextureMatrix(Matrix4f $$0) {
/* 1287 */     Matrix4f $$1 = new Matrix4f((Matrix4fc)$$0);
/* 1288 */     if (!isOnRenderThread()) {
/* 1289 */       recordRenderCall(() -> textureMatrix = $$0);
/*      */     }
/*      */     else {
/*      */       
/* 1293 */       textureMatrix = $$1;
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void resetTextureMatrix() {
/* 1298 */     if (!isOnRenderThread()) {
/* 1299 */       recordRenderCall(() -> textureMatrix.identity());
/*      */     }
/*      */     else {
/*      */       
/* 1303 */       textureMatrix.identity();
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void applyModelViewMatrix() {
/* 1308 */     Matrix4f $$0 = new Matrix4f((Matrix4fc)modelViewStack.last().pose());
/* 1309 */     if (!isOnRenderThread()) {
/* 1310 */       recordRenderCall(() -> modelViewMatrix = $$0);
/*      */     }
/*      */     else {
/*      */       
/* 1314 */       modelViewMatrix = $$0;
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void backupProjectionMatrix() {
/* 1319 */     if (!isOnRenderThread()) {
/* 1320 */       recordRenderCall(() -> _backupProjectionMatrix());
/*      */     }
/*      */     else {
/*      */       
/* 1324 */       _backupProjectionMatrix();
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void _backupProjectionMatrix() {
/* 1329 */     savedProjectionMatrix = projectionMatrix;
/* 1330 */     savedVertexSorting = vertexSorting;
/*      */   }
/*      */   
/*      */   public static void restoreProjectionMatrix() {
/* 1334 */     if (!isOnRenderThread()) {
/* 1335 */       recordRenderCall(() -> _restoreProjectionMatrix());
/*      */     }
/*      */     else {
/*      */       
/* 1339 */       _restoreProjectionMatrix();
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void _restoreProjectionMatrix() {
/* 1344 */     projectionMatrix = savedProjectionMatrix;
/* 1345 */     vertexSorting = savedVertexSorting;
/*      */   }
/*      */   
/*      */   public static Matrix4f getProjectionMatrix() {
/* 1349 */     assertOnRenderThread();
/* 1350 */     return projectionMatrix;
/*      */   }
/*      */   
/*      */   public static Matrix3f getInverseViewRotationMatrix() {
/* 1354 */     assertOnRenderThread();
/* 1355 */     return inverseViewRotationMatrix;
/*      */   }
/*      */   
/*      */   public static Matrix4f getModelViewMatrix() {
/* 1359 */     assertOnRenderThread();
/* 1360 */     return modelViewMatrix;
/*      */   }
/*      */   
/*      */   public static PoseStack getModelViewStack() {
/* 1364 */     return modelViewStack;
/*      */   }
/*      */   
/*      */   public static Matrix4f getTextureMatrix() {
/* 1368 */     assertOnRenderThread();
/* 1369 */     return textureMatrix;
/*      */   }
/*      */   
/*      */   public static AutoStorageIndexBuffer getSequentialBuffer(VertexFormat.Mode $$0) {
/* 1373 */     assertOnRenderThread();
/* 1374 */     switch ($$0) { case SHORT: case INT:  }  return 
/*      */ 
/*      */       
/* 1377 */       sharedSequential;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setShaderGameTime(long $$0, float $$1) {
/* 1382 */     float $$2 = ((float)($$0 % 24000L) + $$1) / 24000.0F;
/* 1383 */     if (!isOnRenderThread()) {
/* 1384 */       recordRenderCall(() -> shaderGameTime = $$0);
/*      */     }
/*      */     else {
/*      */       
/* 1388 */       shaderGameTime = $$2;
/*      */     } 
/*      */   }
/*      */   
/*      */   public static float getShaderGameTime() {
/* 1393 */     assertOnRenderThread();
/* 1394 */     return shaderGameTime;
/*      */   }
/*      */   
/*      */   public static VertexSorting getVertexSorting() {
/* 1398 */     assertOnRenderThread();
/* 1399 */     return vertexSorting;
/*      */   }
/*      */   private static interface IndexGenerator {
/*      */     void accept(IntConsumer param1IntConsumer, int param1Int); }
/*      */   
/*      */   public static final class AutoStorageIndexBuffer { private final int vertexStride;
/*      */     private final int indexStride;
/*      */     private final IndexGenerator generator;
/*      */     private int name;
/* 1408 */     private VertexFormat.IndexType type = VertexFormat.IndexType.SHORT;
/*      */     private int indexCount;
/*      */     
/*      */     AutoStorageIndexBuffer(int $$0, int $$1, IndexGenerator $$2) {
/* 1412 */       this.vertexStride = $$0;
/* 1413 */       this.indexStride = $$1;
/* 1414 */       this.generator = $$2;
/*      */     }
/*      */     
/*      */     public boolean hasStorage(int $$0) {
/* 1418 */       return ($$0 <= this.indexCount);
/*      */     }
/*      */     
/*      */     public void bind(int $$0) {
/* 1422 */       if (this.name == 0) {
/* 1423 */         this.name = GlStateManager._glGenBuffers();
/*      */       }
/* 1425 */       GlStateManager._glBindBuffer(34963, this.name);
/*      */       
/* 1427 */       ensureStorage($$0);
/*      */     }
/*      */     
/*      */     private void ensureStorage(int $$0) {
/* 1431 */       if (hasStorage($$0)) {
/*      */         return;
/*      */       }
/*      */       
/* 1435 */       $$0 = Mth.roundToward($$0 * 2, this.indexStride);
/* 1436 */       RenderSystem.LOGGER.debug("Growing IndexBuffer: Old limit {}, new limit {}.", Integer.valueOf(this.indexCount), Integer.valueOf($$0));
/*      */       
/* 1438 */       int $$1 = $$0 / this.indexStride;
/* 1439 */       int $$2 = $$1 * this.vertexStride;
/*      */       
/* 1441 */       VertexFormat.IndexType $$3 = VertexFormat.IndexType.least($$2);
/* 1442 */       int $$4 = Mth.roundToward($$0 * $$3.bytes, 4);
/*      */       
/* 1444 */       GlStateManager._glBufferData(34963, $$4, 35048);
/*      */       
/* 1446 */       ByteBuffer $$5 = GlStateManager._glMapBuffer(34963, 35001);
/* 1447 */       if ($$5 == null) {
/* 1448 */         throw new RuntimeException("Failed to map GL buffer");
/*      */       }
/*      */       
/* 1451 */       this.type = $$3;
/*      */       
/* 1453 */       IntConsumer $$6 = intConsumer($$5); int $$7;
/* 1454 */       for ($$7 = 0; $$7 < $$0; $$7 += this.indexStride) {
/* 1455 */         this.generator.accept($$6, $$7 * this.vertexStride / this.indexStride);
/*      */       }
/*      */       
/* 1458 */       GlStateManager._glUnmapBuffer(34963);
/*      */       
/* 1460 */       this.indexCount = $$0;
/*      */     }
/*      */     
/*      */     private IntConsumer intConsumer(ByteBuffer $$0) {
/* 1464 */       switch (this.type) {
/*      */         case SHORT:
/* 1466 */           return $$1 -> $$0.putShort((short)$$1);
/*      */       } 
/*      */       
/* 1469 */       Objects.requireNonNull($$0); return $$0::putInt;
/*      */     }
/*      */ 
/*      */     
/*      */     public VertexFormat.IndexType type() {
/* 1474 */       return this.type;
/*      */     }
/*      */     
/*      */     private static interface IndexGenerator {
/*      */       void accept(IntConsumer param2IntConsumer, int param2Int);
/*      */     } }
/*      */ 
/*      */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\systems\RenderSystem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */