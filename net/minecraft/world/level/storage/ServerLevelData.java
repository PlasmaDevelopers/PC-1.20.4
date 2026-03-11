/*    */ package net.minecraft.world.level.storage;
/*    */ 
/*    */ import java.util.Locale;
/*    */ import java.util.UUID;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.CrashReportCategory;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.world.level.GameType;
/*    */ import net.minecraft.world.level.LevelHeightAccessor;
/*    */ import net.minecraft.world.level.border.WorldBorder;
/*    */ import net.minecraft.world.level.timers.TimerQueue;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface ServerLevelData
/*    */   extends WritableLevelData
/*    */ {
/*    */   default void fillCrashReportCategory(CrashReportCategory $$0, LevelHeightAccessor $$1) {
/* 29 */     super.fillCrashReportCategory($$0, $$1);
/* 30 */     $$0.setDetail("Level name", this::getLevelName);
/* 31 */     $$0.setDetail("Level game mode", () -> String.format(Locale.ROOT, "Game mode: %s (ID %d). Hardcore: %b. Cheats: %b", new Object[] { getGameType().getName(), Integer.valueOf(getGameType().getId()), Boolean.valueOf(isHardcore()), Boolean.valueOf(getAllowCommands()) }));
/* 32 */     $$0.setDetail("Level weather", () -> String.format(Locale.ROOT, "Rain time: %d (now: %b), thunder time: %d (now: %b)", new Object[] { Integer.valueOf(getRainTime()), Boolean.valueOf(isRaining()), Integer.valueOf(getThunderTime()), Boolean.valueOf(isThundering()) }));
/*    */   }
/*    */   
/*    */   String getLevelName();
/*    */   
/*    */   void setThundering(boolean paramBoolean);
/*    */   
/*    */   int getRainTime();
/*    */   
/*    */   void setRainTime(int paramInt);
/*    */   
/*    */   void setThunderTime(int paramInt);
/*    */   
/*    */   int getThunderTime();
/*    */   
/*    */   int getClearWeatherTime();
/*    */   
/*    */   void setClearWeatherTime(int paramInt);
/*    */   
/*    */   int getWanderingTraderSpawnDelay();
/*    */   
/*    */   void setWanderingTraderSpawnDelay(int paramInt);
/*    */   
/*    */   int getWanderingTraderSpawnChance();
/*    */   
/*    */   void setWanderingTraderSpawnChance(int paramInt);
/*    */   
/*    */   @Nullable
/*    */   UUID getWanderingTraderId();
/*    */   
/*    */   void setWanderingTraderId(UUID paramUUID);
/*    */   
/*    */   GameType getGameType();
/*    */   
/*    */   void setWorldBorder(WorldBorder.Settings paramSettings);
/*    */   
/*    */   WorldBorder.Settings getWorldBorder();
/*    */   
/*    */   boolean isInitialized();
/*    */   
/*    */   void setInitialized(boolean paramBoolean);
/*    */   
/*    */   boolean getAllowCommands();
/*    */   
/*    */   void setGameType(GameType paramGameType);
/*    */   
/*    */   TimerQueue<MinecraftServer> getScheduledEvents();
/*    */   
/*    */   void setGameTime(long paramLong);
/*    */   
/*    */   void setDayTime(long paramLong);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\ServerLevelData.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */