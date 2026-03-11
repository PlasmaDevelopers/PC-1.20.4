/*    */ package net.minecraft.world.level.storage;
/*    */ 
/*    */ import java.util.Locale;
/*    */ import net.minecraft.CrashReportCategory;
/*    */ import net.minecraft.world.Difficulty;
/*    */ import net.minecraft.world.level.GameRules;
/*    */ import net.minecraft.world.level.LevelHeightAccessor;
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
/*    */ 
/*    */ public interface LevelData
/*    */ {
/*    */   default void fillCrashReportCategory(CrashReportCategory $$0, LevelHeightAccessor $$1) {
/* 38 */     $$0.setDetail("Level spawn location", () -> CrashReportCategory.formatLocation($$0, getXSpawn(), getYSpawn(), getZSpawn()));
/* 39 */     $$0.setDetail("Level time", () -> String.format(Locale.ROOT, "%d game time, %d day time", new Object[] { Long.valueOf(getGameTime()), Long.valueOf(getDayTime()) }));
/*    */   }
/*    */   
/*    */   int getXSpawn();
/*    */   
/*    */   int getYSpawn();
/*    */   
/*    */   int getZSpawn();
/*    */   
/*    */   float getSpawnAngle();
/*    */   
/*    */   long getGameTime();
/*    */   
/*    */   long getDayTime();
/*    */   
/*    */   boolean isThundering();
/*    */   
/*    */   boolean isRaining();
/*    */   
/*    */   void setRaining(boolean paramBoolean);
/*    */   
/*    */   boolean isHardcore();
/*    */   
/*    */   GameRules getGameRules();
/*    */   
/*    */   Difficulty getDifficulty();
/*    */   
/*    */   boolean isDifficultyLocked();
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\LevelData.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */