/*    */ package net.minecraft.client.telemetry.events;
/*    */ 
/*    */ import java.time.Duration;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.telemetry.TelemetryEventSender;
/*    */ import net.minecraft.client.telemetry.TelemetryEventType;
/*    */ import net.minecraft.client.telemetry.TelemetryProperty;
/*    */ import net.minecraft.client.telemetry.TelemetryPropertyMap;
/*    */ 
/*    */ public class WorldLoadTimesEvent {
/*    */   private final boolean newWorld;
/*    */   @Nullable
/*    */   private final Duration worldLoadDuration;
/*    */   
/*    */   public WorldLoadTimesEvent(boolean $$0, @Nullable Duration $$1) {
/* 16 */     this.worldLoadDuration = $$1;
/* 17 */     this.newWorld = $$0;
/*    */   }
/*    */   
/*    */   public void send(TelemetryEventSender $$0) {
/* 21 */     if (this.worldLoadDuration != null)
/* 22 */       $$0.send(TelemetryEventType.WORLD_LOAD_TIMES, $$0 -> {
/*    */             $$0.put(TelemetryProperty.WORLD_LOAD_TIME_MS, Integer.valueOf((int)this.worldLoadDuration.toMillis()));
/*    */             $$0.put(TelemetryProperty.NEW_WORLD, Boolean.valueOf(this.newWorld));
/*    */           }); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\telemetry\events\WorldLoadTimesEvent.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */