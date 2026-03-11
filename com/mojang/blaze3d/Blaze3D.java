/*    */ package com.mojang.blaze3d;
/*    */ 
/*    */ import com.mojang.blaze3d.pipeline.RenderCall;
/*    */ import com.mojang.blaze3d.pipeline.RenderPipeline;
/*    */ import java.util.concurrent.ConcurrentLinkedQueue;
/*    */ import org.lwjgl.glfw.GLFW;
/*    */ import org.lwjgl.system.MemoryUtil;
/*    */ 
/*    */ public class Blaze3D
/*    */ {
/*    */   public static void process(RenderPipeline $$0, float $$1) {
/* 12 */     ConcurrentLinkedQueue<RenderCall> $$2 = $$0.getRecordingQueue();
/*    */   }
/*    */   
/*    */   public static void render(RenderPipeline $$0, float $$1) {
/* 16 */     ConcurrentLinkedQueue<RenderCall> $$2 = $$0.getProcessedQueue();
/*    */   }
/*    */ 
/*    */   
/*    */   public static void youJustLostTheGame() {
/* 21 */     MemoryUtil.memSet(0L, 0, 1L);
/*    */   }
/*    */   
/*    */   public static double getTime() {
/* 25 */     return GLFW.glfwGetTime();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\Blaze3D.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */