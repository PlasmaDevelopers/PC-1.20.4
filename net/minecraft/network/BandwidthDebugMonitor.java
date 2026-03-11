/*    */ package net.minecraft.network;
/*    */ 
/*    */ import java.util.concurrent.atomic.AtomicInteger;
/*    */ import net.minecraft.util.SampleLogger;
/*    */ 
/*    */ public class BandwidthDebugMonitor
/*    */ {
/*  8 */   private final AtomicInteger bytesReceived = new AtomicInteger();
/*    */   private final SampleLogger bandwidthLogger;
/*    */   
/*    */   public BandwidthDebugMonitor(SampleLogger $$0) {
/* 12 */     this.bandwidthLogger = $$0;
/*    */   }
/*    */   
/*    */   public void onReceive(int $$0) {
/* 16 */     this.bytesReceived.getAndAdd($$0);
/*    */   }
/*    */   
/*    */   public void tick() {
/* 20 */     this.bandwidthLogger.logSample(this.bytesReceived.getAndSet(0));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\BandwidthDebugMonitor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */