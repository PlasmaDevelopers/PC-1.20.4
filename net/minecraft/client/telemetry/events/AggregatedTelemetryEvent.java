/*    */ package net.minecraft.client.telemetry.events;
/*    */ 
/*    */ import java.time.Duration;
/*    */ import java.time.Instant;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.telemetry.TelemetryEventSender;
/*    */ 
/*    */ public abstract class AggregatedTelemetryEvent
/*    */ {
/*    */   private static final int SAMPLE_INTERVAL_MS = 60000;
/*    */   private static final int SAMPLES_PER_EVENT = 10;
/*    */   private int sampleCount;
/*    */   private boolean ticking = false;
/*    */   @Nullable
/*    */   private Instant lastSampleTime;
/*    */   
/*    */   public void start() {
/* 18 */     this.ticking = true;
/* 19 */     this.lastSampleTime = Instant.now();
/* 20 */     this.sampleCount = 0;
/*    */   }
/*    */   
/*    */   public void tick(TelemetryEventSender $$0) {
/* 24 */     if (shouldTakeSample()) {
/* 25 */       takeSample();
/* 26 */       this.sampleCount++;
/* 27 */       this.lastSampleTime = Instant.now();
/*    */     } 
/* 29 */     if (shouldSentEvent()) {
/* 30 */       sendEvent($$0);
/* 31 */       this.sampleCount = 0;
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean shouldTakeSample() {
/* 36 */     return (this.ticking && this.lastSampleTime != null && Duration.between(this.lastSampleTime, Instant.now()).toMillis() > 60000L);
/*    */   }
/*    */   
/*    */   public boolean shouldSentEvent() {
/* 40 */     return (this.sampleCount >= 10);
/*    */   }
/*    */   
/*    */   public void stop() {
/* 44 */     this.ticking = false;
/*    */   }
/*    */   
/*    */   protected int getSampleCount() {
/* 48 */     return this.sampleCount;
/*    */   }
/*    */   
/*    */   public abstract void takeSample();
/*    */   
/*    */   public abstract void sendEvent(TelemetryEventSender paramTelemetryEventSender);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\telemetry\events\AggregatedTelemetryEvent.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */