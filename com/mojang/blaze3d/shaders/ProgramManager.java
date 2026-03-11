/*    */ package com.mojang.blaze3d.shaders;
/*    */ 
/*    */ import com.mojang.blaze3d.platform.GlStateManager;
/*    */ import com.mojang.blaze3d.systems.RenderSystem;
/*    */ import com.mojang.logging.LogUtils;
/*    */ import java.io.IOException;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ 
/*    */ public class ProgramManager
/*    */ {
/* 12 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/*    */   public static void glUseProgram(int $$0) {
/* 15 */     RenderSystem.assertOnRenderThread();
/* 16 */     GlStateManager._glUseProgram($$0);
/*    */   }
/*    */   
/*    */   public static void releaseProgram(Shader $$0) {
/* 20 */     RenderSystem.assertOnRenderThread();
/* 21 */     $$0.getFragmentProgram().close();
/* 22 */     $$0.getVertexProgram().close();
/*    */     
/* 24 */     GlStateManager.glDeleteProgram($$0.getId());
/*    */   }
/*    */   
/*    */   public static int createProgram() throws IOException {
/* 28 */     RenderSystem.assertOnRenderThread();
/* 29 */     int $$0 = GlStateManager.glCreateProgram();
/*    */     
/* 31 */     if ($$0 <= 0) {
/* 32 */       throw new IOException("Could not create shader program (returned program ID " + $$0 + ")");
/*    */     }
/*    */     
/* 35 */     return $$0;
/*    */   }
/*    */   
/*    */   public static void linkShader(Shader $$0) {
/* 39 */     RenderSystem.assertOnRenderThread();
/* 40 */     $$0.attachToProgram();
/* 41 */     GlStateManager.glLinkProgram($$0.getId());
/* 42 */     int $$1 = GlStateManager.glGetProgrami($$0.getId(), 35714);
/* 43 */     if ($$1 == 0) {
/* 44 */       LOGGER.warn("Error encountered when linking program containing VS {} and FS {}. Log output:", $$0.getVertexProgram().getName(), $$0.getFragmentProgram().getName());
/* 45 */       LOGGER.warn(GlStateManager.glGetProgramInfoLog($$0.getId(), 32768));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\shaders\ProgramManager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */