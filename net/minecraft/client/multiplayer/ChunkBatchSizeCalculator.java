/*    */ package net.minecraft.client.multiplayer;
/*    */ 
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.util.Mth;
/*    */ 
/*    */ public class ChunkBatchSizeCalculator {
/*    */   private static final int MAX_OLD_SAMPLES_WEIGHT = 49;
/*    */   private static final int CLAMP_COEFFICIENT = 3;
/*  9 */   private double aggregatedNanosPerChunk = 2000000.0D;
/* 10 */   private int oldSamplesWeight = 1;
/* 11 */   private volatile long chunkBatchStartTime = Util.getNanos();
/*    */   
/*    */   public void onBatchStart() {
/* 14 */     this.chunkBatchStartTime = Util.getNanos();
/*    */   }
/*    */   
/*    */   public void onBatchFinished(int $$0) {
/* 18 */     if ($$0 > 0) {
/*    */ 
/*    */ 
/*    */       
/* 22 */       double $$1 = (Util.getNanos() - this.chunkBatchStartTime);
/* 23 */       double $$2 = $$1 / $$0;
/* 24 */       double $$3 = Mth.clamp($$2, this.aggregatedNanosPerChunk / 3.0D, this.aggregatedNanosPerChunk * 3.0D);
/* 25 */       this.aggregatedNanosPerChunk = (this.aggregatedNanosPerChunk * this.oldSamplesWeight + $$3) / (this.oldSamplesWeight + 1);
/* 26 */       this.oldSamplesWeight = Math.min(49, this.oldSamplesWeight + 1);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public float getDesiredChunksPerTick() {
/* 33 */     return (float)(7000000.0D / this.aggregatedNanosPerChunk);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\multiplayer\ChunkBatchSizeCalculator.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */