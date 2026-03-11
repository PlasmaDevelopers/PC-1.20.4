/*    */ package net.minecraft.client.telemetry.events;
/*    */ import it.unimi.dsi.fastutil.longs.LongArrayList;
/*    */ import it.unimi.dsi.fastutil.longs.LongList;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.telemetry.TelemetryEventSender;
/*    */ import net.minecraft.client.telemetry.TelemetryEventType;
/*    */ import net.minecraft.client.telemetry.TelemetryProperty;
/*    */ import net.minecraft.client.telemetry.TelemetryPropertyMap;
/*    */ 
/*    */ public final class PerformanceMetricsEvent extends AggregatedTelemetryEvent {
/* 11 */   private static final long DEDICATED_MEMORY_KB = toKilobytes(Runtime.getRuntime().maxMemory());
/* 12 */   private final LongList fpsSamples = (LongList)new LongArrayList();
/* 13 */   private final LongList frameTimeSamples = (LongList)new LongArrayList();
/* 14 */   private final LongList usedMemorySamples = (LongList)new LongArrayList();
/*    */ 
/*    */   
/*    */   public void tick(TelemetryEventSender $$0) {
/* 18 */     if (Minecraft.getInstance().telemetryOptInExtra()) {
/* 19 */       super.tick($$0);
/*    */     }
/*    */   }
/*    */   
/*    */   private void resetValues() {
/* 24 */     this.fpsSamples.clear();
/* 25 */     this.frameTimeSamples.clear();
/* 26 */     this.usedMemorySamples.clear();
/*    */   }
/*    */ 
/*    */   
/*    */   public void takeSample() {
/* 31 */     this.fpsSamples.add(Minecraft.getInstance().getFps());
/* 32 */     takeUsedMemorySample();
/* 33 */     this.frameTimeSamples.add(Minecraft.getInstance().getFrameTimeNs());
/*    */   }
/*    */   
/*    */   private void takeUsedMemorySample() {
/* 37 */     long $$0 = Runtime.getRuntime().totalMemory();
/* 38 */     long $$1 = Runtime.getRuntime().freeMemory();
/* 39 */     long $$2 = $$0 - $$1;
/* 40 */     this.usedMemorySamples.add(toKilobytes($$2));
/*    */   }
/*    */ 
/*    */   
/*    */   public void sendEvent(TelemetryEventSender $$0) {
/* 45 */     $$0.send(TelemetryEventType.PERFORMANCE_METRICS, $$0 -> {
/*    */           $$0.put(TelemetryProperty.FRAME_RATE_SAMPLES, new LongArrayList(this.fpsSamples));
/*    */           $$0.put(TelemetryProperty.RENDER_TIME_SAMPLES, new LongArrayList(this.frameTimeSamples));
/*    */           $$0.put(TelemetryProperty.USED_MEMORY_SAMPLES, new LongArrayList(this.usedMemorySamples));
/*    */           $$0.put(TelemetryProperty.NUMBER_OF_SAMPLES, Integer.valueOf(getSampleCount()));
/*    */           $$0.put(TelemetryProperty.RENDER_DISTANCE, Integer.valueOf((Minecraft.getInstance()).options.getEffectiveRenderDistance()));
/*    */           $$0.put(TelemetryProperty.DEDICATED_MEMORY_KB, Integer.valueOf((int)DEDICATED_MEMORY_KB));
/*    */         });
/* 53 */     resetValues();
/*    */   }
/*    */   
/*    */   private static long toKilobytes(long $$0) {
/* 57 */     return $$0 / 1000L;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\telemetry\events\PerformanceMetricsEvent.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */