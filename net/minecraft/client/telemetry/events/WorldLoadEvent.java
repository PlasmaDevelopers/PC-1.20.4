/*    */ package net.minecraft.client.telemetry.events;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.multiplayer.ServerData;
/*    */ import net.minecraft.client.telemetry.TelemetryEventSender;
/*    */ import net.minecraft.client.telemetry.TelemetryEventType;
/*    */ import net.minecraft.client.telemetry.TelemetryProperty;
/*    */ import net.minecraft.client.telemetry.TelemetryPropertyMap;
/*    */ import net.minecraft.world.level.GameType;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldLoadEvent
/*    */ {
/*    */   private boolean eventSent;
/*    */   @Nullable
/*    */   private TelemetryProperty.GameMode gameMode;
/*    */   @Nullable
/*    */   private String serverBrand;
/*    */   @Nullable
/*    */   private final String minigameName;
/*    */   
/*    */   public WorldLoadEvent(@Nullable String $$0) {
/* 25 */     this.minigameName = $$0;
/*    */   }
/*    */   
/*    */   public void addProperties(TelemetryPropertyMap.Builder $$0) {
/* 29 */     if (this.serverBrand != null) {
/* 30 */       $$0.put(TelemetryProperty.SERVER_MODDED, Boolean.valueOf(!this.serverBrand.equals("vanilla")));
/*    */     }
/* 32 */     $$0.put(TelemetryProperty.SERVER_TYPE, getServerType());
/*    */   }
/*    */   
/*    */   private TelemetryProperty.ServerType getServerType() {
/* 36 */     ServerData $$0 = Minecraft.getInstance().getCurrentServer();
/* 37 */     if ($$0 != null && $$0.isRealm()) {
/* 38 */       return TelemetryProperty.ServerType.REALM;
/*    */     }
/* 40 */     if (Minecraft.getInstance().hasSingleplayerServer()) {
/* 41 */       return TelemetryProperty.ServerType.LOCAL;
/*    */     }
/* 43 */     return TelemetryProperty.ServerType.OTHER;
/*    */   }
/*    */   
/*    */   public boolean send(TelemetryEventSender $$0) {
/* 47 */     if (this.eventSent || this.gameMode == null || this.serverBrand == null) {
/* 48 */       return false;
/*    */     }
/* 50 */     this.eventSent = true;
/* 51 */     $$0.send(TelemetryEventType.WORLD_LOADED, $$0 -> {
/*    */           $$0.put(TelemetryProperty.GAME_MODE, this.gameMode);
/*    */           if (this.minigameName != null) {
/*    */             $$0.put(TelemetryProperty.REALMS_MAP_CONTENT, this.minigameName);
/*    */           }
/*    */         });
/* 57 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setGameMode(GameType $$0, boolean $$1) {
/* 62 */     switch ($$0) { default: throw new IncompatibleClassChangeError();
/* 63 */       case SURVIVAL: if ($$1);
/*    */       case CREATIVE: 
/*    */       case ADVENTURE: 
/* 66 */       case SPECTATOR: break; }  this.gameMode = TelemetryProperty.GameMode.SPECTATOR;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setServerBrand(String $$0) {
/* 71 */     this.serverBrand = $$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\telemetry\events\WorldLoadEvent.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */