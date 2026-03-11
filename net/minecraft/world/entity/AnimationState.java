/*    */ package net.minecraft.world.entity;
/*    */ 
/*    */ import java.util.function.Consumer;
/*    */ import net.minecraft.util.Mth;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AnimationState
/*    */ {
/*    */   private static final long STOPPED = 9223372036854775807L;
/* 11 */   private long lastTime = Long.MAX_VALUE;
/*    */   private long accumulatedTime;
/*    */   
/*    */   public void start(int $$0) {
/* 15 */     this.lastTime = $$0 * 1000L / 20L;
/* 16 */     this.accumulatedTime = 0L;
/*    */   }
/*    */   
/*    */   public void startIfStopped(int $$0) {
/* 20 */     if (!isStarted()) {
/* 21 */       start($$0);
/*    */     }
/*    */   }
/*    */   
/*    */   public void animateWhen(boolean $$0, int $$1) {
/* 26 */     if ($$0) {
/* 27 */       startIfStopped($$1);
/*    */     } else {
/* 29 */       stop();
/*    */     } 
/*    */   }
/*    */   
/*    */   public void stop() {
/* 34 */     this.lastTime = Long.MAX_VALUE;
/*    */   }
/*    */   
/*    */   public void ifStarted(Consumer<AnimationState> $$0) {
/* 38 */     if (isStarted()) {
/* 39 */       $$0.accept(this);
/*    */     }
/*    */   }
/*    */   
/*    */   public void updateTime(float $$0, float $$1) {
/* 44 */     if (!isStarted()) {
/*    */       return;
/*    */     }
/*    */     
/* 48 */     long $$2 = Mth.lfloor(($$0 * 1000.0F / 20.0F));
/* 49 */     this.accumulatedTime += (long)((float)($$2 - this.lastTime) * $$1);
/* 50 */     this.lastTime = $$2;
/*    */   }
/*    */   
/*    */   public long getAccumulatedTime() {
/* 54 */     return this.accumulatedTime;
/*    */   }
/*    */   
/*    */   public boolean isStarted() {
/* 58 */     return (this.lastTime != Long.MAX_VALUE);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\AnimationState.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */