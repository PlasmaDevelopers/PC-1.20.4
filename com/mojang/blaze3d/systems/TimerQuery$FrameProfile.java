/*    */ package com.mojang.blaze3d.systems;
/*    */ 
/*    */ import org.lwjgl.opengl.ARBTimerQuery;
/*    */ import org.lwjgl.opengl.GL32C;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FrameProfile
/*    */ {
/*    */   private static final long NO_RESULT = 0L;
/*    */   private static final long CANCELLED_RESULT = -1L;
/*    */   private final int queryName;
/*    */   private long result;
/*    */   
/*    */   FrameProfile(int $$0) {
/* 49 */     this.queryName = $$0;
/*    */   }
/*    */   
/*    */   public void cancel() {
/* 53 */     RenderSystem.assertOnRenderThread();
/*    */     
/* 55 */     if (this.result != 0L) {
/*    */       return;
/*    */     }
/*    */     
/* 59 */     this.result = -1L;
/*    */     
/* 61 */     GL32C.glDeleteQueries(this.queryName);
/*    */   }
/*    */   
/*    */   public boolean isDone() {
/* 65 */     RenderSystem.assertOnRenderThread();
/*    */     
/* 67 */     if (this.result != 0L) {
/* 68 */       return true;
/*    */     }
/*    */     
/* 71 */     if (1 == GL32C.glGetQueryObjecti(this.queryName, 34919)) {
/* 72 */       this.result = ARBTimerQuery.glGetQueryObjecti64(this.queryName, 34918);
/* 73 */       GL32C.glDeleteQueries(this.queryName);
/* 74 */       return true;
/*    */     } 
/* 76 */     return false;
/*    */   }
/*    */   
/*    */   public long get() {
/* 80 */     RenderSystem.assertOnRenderThread();
/*    */     
/* 82 */     if (this.result == 0L) {
/* 83 */       this.result = ARBTimerQuery.glGetQueryObjecti64(this.queryName, 34918);
/* 84 */       GL32C.glDeleteQueries(this.queryName);
/*    */     } 
/*    */     
/* 87 */     return this.result;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\systems\TimerQuery$FrameProfile.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */