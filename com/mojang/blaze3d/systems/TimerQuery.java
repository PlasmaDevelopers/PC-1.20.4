/*     */ package com.mojang.blaze3d.systems;
/*     */ 
/*     */ import java.util.Optional;
/*     */ import javax.annotation.Nullable;
/*     */ import org.lwjgl.opengl.ARBTimerQuery;
/*     */ import org.lwjgl.opengl.GL;
/*     */ import org.lwjgl.opengl.GL32C;
/*     */ 
/*     */ public class TimerQuery
/*     */ {
/*     */   public static Optional<TimerQuery> getInstance() {
/*  12 */     return TimerQueryLazyLoader.INSTANCE;
/*     */   }
/*     */   
/*     */   private int nextQueryName;
/*     */   
/*     */   public void beginProfile() {
/*  18 */     RenderSystem.assertOnRenderThread();
/*     */     
/*  20 */     if (this.nextQueryName != 0) {
/*  21 */       throw new IllegalStateException("Current profile not ended");
/*     */     }
/*     */     
/*  24 */     this.nextQueryName = GL32C.glGenQueries();
/*  25 */     GL32C.glBeginQuery(35007, this.nextQueryName);
/*     */   }
/*     */   
/*     */   public FrameProfile endProfile() {
/*  29 */     RenderSystem.assertOnRenderThread();
/*     */     
/*  31 */     if (this.nextQueryName == 0) {
/*  32 */       throw new IllegalStateException("endProfile called before beginProfile");
/*     */     }
/*     */     
/*  35 */     GL32C.glEndQuery(35007);
/*  36 */     FrameProfile $$0 = new FrameProfile(this.nextQueryName);
/*  37 */     this.nextQueryName = 0;
/*  38 */     return $$0;
/*     */   }
/*     */   
/*     */   public static class FrameProfile
/*     */   {
/*     */     private static final long NO_RESULT = 0L;
/*     */     private static final long CANCELLED_RESULT = -1L;
/*     */     private final int queryName;
/*     */     private long result;
/*     */     
/*     */     FrameProfile(int $$0) {
/*  49 */       this.queryName = $$0;
/*     */     }
/*     */     
/*     */     public void cancel() {
/*  53 */       RenderSystem.assertOnRenderThread();
/*     */       
/*  55 */       if (this.result != 0L) {
/*     */         return;
/*     */       }
/*     */       
/*  59 */       this.result = -1L;
/*     */       
/*  61 */       GL32C.glDeleteQueries(this.queryName);
/*     */     }
/*     */     
/*     */     public boolean isDone() {
/*  65 */       RenderSystem.assertOnRenderThread();
/*     */       
/*  67 */       if (this.result != 0L) {
/*  68 */         return true;
/*     */       }
/*     */       
/*  71 */       if (1 == GL32C.glGetQueryObjecti(this.queryName, 34919)) {
/*  72 */         this.result = ARBTimerQuery.glGetQueryObjecti64(this.queryName, 34918);
/*  73 */         GL32C.glDeleteQueries(this.queryName);
/*  74 */         return true;
/*     */       } 
/*  76 */       return false;
/*     */     }
/*     */     
/*     */     public long get() {
/*  80 */       RenderSystem.assertOnRenderThread();
/*     */       
/*  82 */       if (this.result == 0L) {
/*  83 */         this.result = ARBTimerQuery.glGetQueryObjecti64(this.queryName, 34918);
/*  84 */         GL32C.glDeleteQueries(this.queryName);
/*     */       } 
/*     */       
/*  87 */       return this.result;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class TimerQueryLazyLoader {
/*     */     @Nullable
/*     */     private static TimerQuery instantiate() {
/*  94 */       if (!(GL.getCapabilities()).GL_ARB_timer_query) {
/*  95 */         return null;
/*     */       }
/*     */       
/*  98 */       return new TimerQuery();
/*     */     }
/*     */     
/* 101 */     static final Optional<TimerQuery> INSTANCE = Optional.ofNullable(instantiate());
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\systems\TimerQuery.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */