/*    */ package net.minecraft.world.level;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.world.level.lighting.LevelLightEngine;
/*    */ 
/*    */ public interface BlockAndTintGetter extends BlockGetter {
/*    */   float getShade(Direction paramDirection, boolean paramBoolean);
/*    */   
/*    */   LevelLightEngine getLightEngine();
/*    */   
/*    */   int getBlockTint(BlockPos paramBlockPos, ColorResolver paramColorResolver);
/*    */   
/*    */   default int getBrightness(LightLayer $$0, BlockPos $$1) {
/* 15 */     return getLightEngine().getLayerListener($$0).getLightValue($$1);
/*    */   }
/*    */   
/*    */   default int getRawBrightness(BlockPos $$0, int $$1) {
/* 19 */     return getLightEngine().getRawBrightness($$0, $$1);
/*    */   }
/*    */   
/*    */   default boolean canSeeSky(BlockPos $$0) {
/* 23 */     return (getBrightness(LightLayer.SKY, $$0) >= getMaxLightLevel());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\BlockAndTintGetter.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */