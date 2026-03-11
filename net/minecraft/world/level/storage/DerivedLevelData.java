/*     */ package net.minecraft.world.level.storage;
/*     */ 
/*     */ import java.util.UUID;
/*     */ import net.minecraft.CrashReportCategory;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.world.Difficulty;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import net.minecraft.world.level.GameType;
/*     */ import net.minecraft.world.level.LevelHeightAccessor;
/*     */ import net.minecraft.world.level.border.WorldBorder;
/*     */ import net.minecraft.world.level.timers.TimerQueue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DerivedLevelData
/*     */   implements ServerLevelData
/*     */ {
/*     */   private final WorldData worldData;
/*     */   private final ServerLevelData wrapped;
/*     */   
/*     */   public DerivedLevelData(WorldData $$0, ServerLevelData $$1) {
/*  26 */     this.worldData = $$0;
/*  27 */     this.wrapped = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getXSpawn() {
/*  32 */     return this.wrapped.getXSpawn();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getYSpawn() {
/*  37 */     return this.wrapped.getYSpawn();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getZSpawn() {
/*  42 */     return this.wrapped.getZSpawn();
/*     */   }
/*     */ 
/*     */   
/*     */   public float getSpawnAngle() {
/*  47 */     return this.wrapped.getSpawnAngle();
/*     */   }
/*     */ 
/*     */   
/*     */   public long getGameTime() {
/*  52 */     return this.wrapped.getGameTime();
/*     */   }
/*     */ 
/*     */   
/*     */   public long getDayTime() {
/*  57 */     return this.wrapped.getDayTime();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getLevelName() {
/*  62 */     return this.worldData.getLevelName();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getClearWeatherTime() {
/*  67 */     return this.wrapped.getClearWeatherTime();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setClearWeatherTime(int $$0) {}
/*     */ 
/*     */   
/*     */   public boolean isThundering() {
/*  76 */     return this.wrapped.isThundering();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getThunderTime() {
/*  81 */     return this.wrapped.getThunderTime();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRaining() {
/*  86 */     return this.wrapped.isRaining();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRainTime() {
/*  91 */     return this.wrapped.getRainTime();
/*     */   }
/*     */ 
/*     */   
/*     */   public GameType getGameType() {
/*  96 */     return this.worldData.getGameType();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setXSpawn(int $$0) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void setYSpawn(int $$0) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void setZSpawn(int $$0) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSpawnAngle(float $$0) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGameTime(long $$0) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDayTime(long $$0) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSpawn(BlockPos $$0, float $$1) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void setThundering(boolean $$0) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void setThunderTime(int $$0) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRaining(boolean $$0) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRainTime(int $$0) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGameType(GameType $$0) {}
/*     */ 
/*     */   
/*     */   public boolean isHardcore() {
/* 149 */     return this.worldData.isHardcore();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getAllowCommands() {
/* 154 */     return this.worldData.getAllowCommands();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInitialized() {
/* 159 */     return this.wrapped.isInitialized();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInitialized(boolean $$0) {}
/*     */ 
/*     */   
/*     */   public GameRules getGameRules() {
/* 168 */     return this.worldData.getGameRules();
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldBorder.Settings getWorldBorder() {
/* 173 */     return this.wrapped.getWorldBorder();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWorldBorder(WorldBorder.Settings $$0) {}
/*     */ 
/*     */   
/*     */   public Difficulty getDifficulty() {
/* 182 */     return this.worldData.getDifficulty();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDifficultyLocked() {
/* 187 */     return this.worldData.isDifficultyLocked();
/*     */   }
/*     */ 
/*     */   
/*     */   public TimerQueue<MinecraftServer> getScheduledEvents() {
/* 192 */     return this.wrapped.getScheduledEvents();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWanderingTraderSpawnDelay() {
/* 197 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWanderingTraderSpawnDelay(int $$0) {}
/*     */ 
/*     */   
/*     */   public int getWanderingTraderSpawnChance() {
/* 206 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWanderingTraderSpawnChance(int $$0) {}
/*     */ 
/*     */   
/*     */   public UUID getWanderingTraderId() {
/* 215 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWanderingTraderId(UUID $$0) {}
/*     */ 
/*     */   
/*     */   public void fillCrashReportCategory(CrashReportCategory $$0, LevelHeightAccessor $$1) {
/* 224 */     $$0.setDetail("Derived", Boolean.valueOf(true));
/* 225 */     this.wrapped.fillCrashReportCategory($$0, $$1);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\DerivedLevelData.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */