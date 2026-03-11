/*    */ package net.minecraft.world.level;
/*    */ 
/*    */ import net.minecraft.world.level.dimension.DimensionType;
/*    */ 
/*    */ public interface LevelTimeAccess extends LevelReader {
/*    */   long dayTime();
/*    */   
/*    */   default float getMoonBrightness() {
/*  9 */     return DimensionType.MOON_BRIGHTNESS_PER_PHASE[dimensionType().moonPhase(dayTime())];
/*    */   }
/*    */   
/*    */   default float getTimeOfDay(float $$0) {
/* 13 */     return dimensionType().timeOfDay(dayTime());
/*    */   }
/*    */   
/*    */   default int getMoonPhase() {
/* 17 */     return dimensionType().moonPhase(dayTime());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\LevelTimeAccess.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */