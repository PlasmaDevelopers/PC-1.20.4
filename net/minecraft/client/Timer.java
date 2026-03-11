/*    */ package net.minecraft.client;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.floats.FloatUnaryOperator;
/*    */ 
/*    */ public class Timer
/*    */ {
/*    */   public float partialTick;
/*    */   public float tickDelta;
/*    */   private long lastMs;
/*    */   private final float msPerTick;
/*    */   private final FloatUnaryOperator targetMsptProvider;
/*    */   
/*    */   public Timer(float $$0, long $$1, FloatUnaryOperator $$2) {
/* 14 */     this.msPerTick = 1000.0F / $$0;
/* 15 */     this.lastMs = $$1;
/* 16 */     this.targetMsptProvider = $$2;
/*    */   }
/*    */   
/*    */   public int advanceTime(long $$0) {
/* 20 */     this.tickDelta = (float)($$0 - this.lastMs) / this.targetMsptProvider.apply(this.msPerTick);
/* 21 */     this.lastMs = $$0;
/*    */     
/* 23 */     this.partialTick += this.tickDelta;
/* 24 */     int $$1 = (int)this.partialTick;
/* 25 */     this.partialTick -= $$1;
/* 26 */     return $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\Timer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */