/*    */ package net.minecraft.client.telemetry;
/*    */ 
/*    */ import java.time.Duration;
/*    */ import java.util.UUID;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.advancements.AdvancementHolder;
/*    */ import net.minecraft.client.telemetry.events.PerformanceMetricsEvent;
/*    */ import net.minecraft.client.telemetry.events.WorldLoadEvent;
/*    */ import net.minecraft.client.telemetry.events.WorldLoadTimesEvent;
/*    */ import net.minecraft.client.telemetry.events.WorldUnloadEvent;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.level.GameType;
/*    */ import net.minecraft.world.level.Level;
/*    */ 
/*    */ public class WorldSessionTelemetryManager
/*    */ {
/* 17 */   private final UUID worldSessionId = UUID.randomUUID();
/*    */   
/*    */   private final TelemetryEventSender eventSender;
/*    */   private final WorldLoadEvent worldLoadEvent;
/*    */   private final WorldUnloadEvent worldUnloadEvent;
/*    */   private final PerformanceMetricsEvent performanceMetricsEvent;
/*    */   private final WorldLoadTimesEvent worldLoadTimesEvent;
/*    */   
/*    */   public WorldSessionTelemetryManager(TelemetryEventSender $$0, boolean $$1, @Nullable Duration $$2, @Nullable String $$3) {
/* 26 */     this.worldUnloadEvent = new WorldUnloadEvent();
/* 27 */     this.worldLoadEvent = new WorldLoadEvent($$3);
/* 28 */     this.performanceMetricsEvent = new PerformanceMetricsEvent();
/* 29 */     this.worldLoadTimesEvent = new WorldLoadTimesEvent($$1, $$2);
/*    */     
/* 31 */     this.eventSender = $$0.decorate($$0 -> {
/*    */           this.worldLoadEvent.addProperties($$0);
/*    */           $$0.put(TelemetryProperty.WORLD_SESSION_ID, this.worldSessionId);
/*    */         });
/*    */   }
/*    */   
/*    */   public void tick() {
/* 38 */     this.performanceMetricsEvent.tick(this.eventSender);
/*    */   }
/*    */   
/*    */   public void onPlayerInfoReceived(GameType $$0, boolean $$1) {
/* 42 */     this.worldLoadEvent.setGameMode($$0, $$1);
/* 43 */     this.worldUnloadEvent.onPlayerInfoReceived();
/* 44 */     worldSessionStart();
/*    */   }
/*    */   
/*    */   public void onServerBrandReceived(String $$0) {
/* 48 */     this.worldLoadEvent.setServerBrand($$0);
/* 49 */     worldSessionStart();
/*    */   }
/*    */   
/*    */   public void setTime(long $$0) {
/* 53 */     this.worldUnloadEvent.setTime($$0);
/*    */   }
/*    */   
/*    */   public void worldSessionStart() {
/* 57 */     if (this.worldLoadEvent.send(this.eventSender)) {
/* 58 */       this.worldLoadTimesEvent.send(this.eventSender);
/* 59 */       this.performanceMetricsEvent.start();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisconnect() {
/* 65 */     this.worldLoadEvent.send(this.eventSender);
/* 66 */     this.performanceMetricsEvent.stop();
/* 67 */     this.worldUnloadEvent.send(this.eventSender);
/*    */   }
/*    */   
/*    */   public void onAdvancementDone(Level $$0, AdvancementHolder $$1) {
/* 71 */     ResourceLocation $$2 = $$1.id();
/* 72 */     if ($$1.value().sendsTelemetryEvent() && "minecraft".equals($$2.getNamespace())) {
/* 73 */       long $$3 = $$0.getGameTime();
/* 74 */       this.eventSender.send(TelemetryEventType.ADVANCEMENT_MADE, $$2 -> {
/*    */             $$2.put(TelemetryProperty.ADVANCEMENT_ID, $$0.toString());
/*    */             $$2.put(TelemetryProperty.ADVANCEMENT_GAME_TIME, Long.valueOf($$1));
/*    */           });
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\telemetry\WorldSessionTelemetryManager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */