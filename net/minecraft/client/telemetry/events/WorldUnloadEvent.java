/*    */ package net.minecraft.client.telemetry.events;
/*    */ 
/*    */ import java.time.Duration;
/*    */ import java.time.Instant;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.client.telemetry.TelemetryEventSender;
/*    */ import net.minecraft.client.telemetry.TelemetryEventType;
/*    */ import net.minecraft.client.telemetry.TelemetryProperty;
/*    */ import net.minecraft.client.telemetry.TelemetryPropertyMap;
/*    */ 
/*    */ public class WorldUnloadEvent
/*    */ {
/*    */   private static final int NOT_TRACKING_TIME = -1;
/* 14 */   private Optional<Instant> worldLoadedTime = Optional.empty();
/*    */   private long totalTicks;
/*    */   private long lastGameTime;
/*    */   
/*    */   public void onPlayerInfoReceived() {
/* 19 */     this.lastGameTime = -1L;
/* 20 */     if (this.worldLoadedTime.isEmpty()) {
/* 21 */       this.worldLoadedTime = Optional.of(Instant.now());
/*    */     }
/*    */   }
/*    */   
/*    */   public void setTime(long $$0) {
/* 26 */     if (this.lastGameTime != -1L) {
/* 27 */       this.totalTicks += Math.max(0L, $$0 - this.lastGameTime);
/*    */     }
/* 29 */     this.lastGameTime = $$0;
/*    */   }
/*    */   
/*    */   private int getTimeInSecondsSinceLoad(Instant $$0) {
/* 33 */     Duration $$1 = Duration.between($$0, Instant.now());
/* 34 */     return (int)$$1.toSeconds();
/*    */   }
/*    */   
/*    */   public void send(TelemetryEventSender $$0) {
/* 38 */     this.worldLoadedTime.ifPresent($$1 -> $$0.send(TelemetryEventType.WORLD_UNLOADED, ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\telemetry\events\WorldUnloadEvent.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */