/*     */ package com.mojang.blaze3d.platform;
/*     */ 
/*     */ import com.google.common.base.Joiner;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.blaze3d.DontObfuscate;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import com.mojang.blaze3d.vertex.BufferBuilder;
/*     */ import com.mojang.blaze3d.vertex.DefaultVertexFormat;
/*     */ import com.mojang.blaze3d.vertex.Tesselator;
/*     */ import com.mojang.blaze3d.vertex.VertexFormat;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.LongSupplier;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.client.renderer.GameRenderer;
/*     */ import org.lwjgl.Version;
/*     */ import org.lwjgl.glfw.GLFW;
/*     */ import org.lwjgl.glfw.GLFWErrorCallback;
/*     */ import org.lwjgl.glfw.GLFWErrorCallbackI;
/*     */ import org.lwjgl.glfw.GLFWNativeGLX;
/*     */ import org.lwjgl.glfw.GLFWVidMode;
/*     */ import org.lwjgl.opengl.GL;
/*     */ import org.lwjgl.system.MemoryUtil;
/*     */ import org.slf4j.Logger;
/*     */ import oshi.SystemInfo;
/*     */ import oshi.hardware.CentralProcessor;
/*     */ 
/*     */ 
/*     */ @DontObfuscate
/*     */ public class GLX
/*     */ {
/*  34 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   public static String getOpenGLVersionString() {
/*  37 */     RenderSystem.assertOnRenderThread();
/*  38 */     if (GLFW.glfwGetCurrentContext() == 0L) {
/*  39 */       return "NO CONTEXT";
/*     */     }
/*  41 */     return GlStateManager._getString(7937) + " GL version " + GlStateManager._getString(7937) + ", " + GlStateManager._getString(7938);
/*     */   }
/*     */   private static String cpuInfo;
/*     */   public static int _getRefreshRate(Window $$0) {
/*  45 */     RenderSystem.assertOnRenderThread();
/*  46 */     long $$1 = GLFW.glfwGetWindowMonitor($$0.getWindow());
/*  47 */     if ($$1 == 0L) {
/*  48 */       $$1 = GLFW.glfwGetPrimaryMonitor();
/*     */     }
/*  50 */     GLFWVidMode $$2 = ($$1 == 0L) ? null : GLFW.glfwGetVideoMode($$1);
/*  51 */     return ($$2 == null) ? 0 : $$2.refreshRate();
/*     */   }
/*     */   
/*     */   public static String _getLWJGLVersion() {
/*  55 */     RenderSystem.assertInInitPhase();
/*  56 */     return Version.getVersion();
/*     */   }
/*     */   public static LongSupplier _initGlfw() {
/*     */     LongSupplier $$2;
/*  60 */     RenderSystem.assertInInitPhase();
/*     */ 
/*     */ 
/*     */     
/*  64 */     GLFWNativeGLX.setPath(GL.getFunctionProvider());
/*     */     
/*  66 */     Window.checkGlfwError(($$0, $$1) -> {
/*     */           throw new IllegalStateException(String.format(Locale.ROOT, "GLFW error before init: [0x%X]%s", new Object[] { $$0, $$1 }));
/*     */         });
/*     */     
/*  70 */     List<String> $$0 = Lists.newArrayList();
/*     */     
/*  72 */     GLFWErrorCallback $$1 = GLFW.glfwSetErrorCallback(($$1, $$2) -> {
/*     */           String $$3 = ($$2 == 0L) ? "" : MemoryUtil.memUTF8($$2);
/*     */           
/*     */           $$0.add(String.format(Locale.ROOT, "GLFW error during init: [0x%X]%s", new Object[] { Integer.valueOf($$1), $$3 }));
/*     */         });
/*     */     
/*  78 */     if (GLFW.glfwInit()) {
/*  79 */       $$2 = (() -> (long)(GLFW.glfwGetTime() * 1.0E9D));
/*     */       
/*  81 */       for (String $$3 : $$0) {
/*  82 */         LOGGER.error("GLFW error collected during initialization: {}", $$3);
/*     */       }
/*     */     } else {
/*  85 */       throw new IllegalStateException("Failed to initialize GLFW, errors: " + Joiner.on(",").join($$0));
/*     */     } 
/*     */     
/*  88 */     RenderSystem.setErrorCallback((GLFWErrorCallbackI)$$1);
/*  89 */     return $$2;
/*     */   }
/*     */   
/*     */   public static void _setGlfwErrorCallback(GLFWErrorCallbackI $$0) {
/*  93 */     RenderSystem.assertInInitPhase();
/*  94 */     GLFWErrorCallback $$1 = GLFW.glfwSetErrorCallback($$0);
/*  95 */     if ($$1 != null) {
/*  96 */       $$1.free();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean _shouldClose(Window $$0) {
/* 102 */     return GLFW.glfwWindowShouldClose($$0.getWindow());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void _init(int $$0, boolean $$1) {
/* 108 */     RenderSystem.assertInInitPhase();
/*     */     
/*     */     try {
/* 111 */       CentralProcessor $$2 = (new SystemInfo()).getHardware().getProcessor();
/* 112 */       cpuInfo = String.format(Locale.ROOT, "%dx %s", new Object[] { Integer.valueOf($$2.getLogicalProcessorCount()), $$2.getProcessorIdentifier().getName() }).replaceAll("\\s+", " ");
/* 113 */     } catch (Throwable throwable) {}
/*     */ 
/*     */     
/* 116 */     GlDebug.enableDebugCallback($$0, $$1);
/*     */   }
/*     */   
/*     */   public static String _getCpuInfo() {
/* 120 */     return (cpuInfo == null) ? "<unknown>" : cpuInfo;
/*     */   }
/*     */   
/*     */   public static void _renderCrosshair(int $$0, boolean $$1, boolean $$2, boolean $$3) {
/* 124 */     RenderSystem.assertOnRenderThread();
/* 125 */     GlStateManager._depthMask(false);
/* 126 */     GlStateManager._disableCull();
/*     */     
/* 128 */     RenderSystem.setShader(GameRenderer::getRendertypeLinesShader);
/*     */     
/* 130 */     Tesselator $$4 = RenderSystem.renderThreadTesselator();
/* 131 */     BufferBuilder $$5 = $$4.getBuilder();
/* 132 */     RenderSystem.lineWidth(4.0F);
/* 133 */     $$5.begin(VertexFormat.Mode.LINES, DefaultVertexFormat.POSITION_COLOR_NORMAL);
/* 134 */     if ($$1) {
/* 135 */       $$5.vertex(0.0D, 0.0D, 0.0D).color(0, 0, 0, 255).normal(1.0F, 0.0F, 0.0F).endVertex();
/* 136 */       $$5.vertex($$0, 0.0D, 0.0D).color(0, 0, 0, 255).normal(1.0F, 0.0F, 0.0F).endVertex();
/*     */     } 
/* 138 */     if ($$2) {
/* 139 */       $$5.vertex(0.0D, 0.0D, 0.0D).color(0, 0, 0, 255).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 140 */       $$5.vertex(0.0D, $$0, 0.0D).color(0, 0, 0, 255).normal(0.0F, 1.0F, 0.0F).endVertex();
/*     */     } 
/* 142 */     if ($$3) {
/* 143 */       $$5.vertex(0.0D, 0.0D, 0.0D).color(0, 0, 0, 255).normal(0.0F, 0.0F, 1.0F).endVertex();
/* 144 */       $$5.vertex(0.0D, 0.0D, $$0).color(0, 0, 0, 255).normal(0.0F, 0.0F, 1.0F).endVertex();
/*     */     } 
/* 146 */     $$4.end();
/* 147 */     RenderSystem.lineWidth(2.0F);
/* 148 */     $$5.begin(VertexFormat.Mode.LINES, DefaultVertexFormat.POSITION_COLOR_NORMAL);
/* 149 */     if ($$1) {
/* 150 */       $$5.vertex(0.0D, 0.0D, 0.0D).color(255, 0, 0, 255).normal(1.0F, 0.0F, 0.0F).endVertex();
/* 151 */       $$5.vertex($$0, 0.0D, 0.0D).color(255, 0, 0, 255).normal(1.0F, 0.0F, 0.0F).endVertex();
/*     */     } 
/* 153 */     if ($$2) {
/* 154 */       $$5.vertex(0.0D, 0.0D, 0.0D).color(0, 255, 0, 255).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 155 */       $$5.vertex(0.0D, $$0, 0.0D).color(0, 255, 0, 255).normal(0.0F, 1.0F, 0.0F).endVertex();
/*     */     } 
/* 157 */     if ($$3) {
/* 158 */       $$5.vertex(0.0D, 0.0D, 0.0D).color(127, 127, 255, 255).normal(0.0F, 0.0F, 1.0F).endVertex();
/* 159 */       $$5.vertex(0.0D, 0.0D, $$0).color(127, 127, 255, 255).normal(0.0F, 0.0F, 1.0F).endVertex();
/*     */     } 
/* 161 */     $$4.end();
/*     */     
/* 163 */     RenderSystem.lineWidth(1.0F);
/* 164 */     GlStateManager._enableCull();
/* 165 */     GlStateManager._depthMask(true);
/*     */   }
/*     */   
/*     */   public static <T> T make(Supplier<T> $$0) {
/* 169 */     return $$0.get();
/*     */   }
/*     */   
/*     */   public static <T> T make(T $$0, Consumer<T> $$1) {
/* 173 */     $$1.accept($$0);
/* 174 */     return $$0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\platform\GLX.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */