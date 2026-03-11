/*    */ package net.minecraft.client.renderer;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.level.BlockAndTintGetter;
/*    */ import net.minecraft.world.level.ColorResolver;
/*    */ import net.minecraft.world.level.biome.Biome;
/*    */ 
/*    */ public class BiomeColors {
/*    */   public static final ColorResolver FOLIAGE_COLOR_RESOLVER;
/*  9 */   public static final ColorResolver GRASS_COLOR_RESOLVER = Biome::getGrassColor; public static final ColorResolver WATER_COLOR_RESOLVER; static {
/* 10 */     FOLIAGE_COLOR_RESOLVER = (($$0, $$1, $$2) -> $$0.getFoliageColor());
/* 11 */     WATER_COLOR_RESOLVER = (($$0, $$1, $$2) -> $$0.getWaterColor());
/*    */   }
/*    */   private static int getAverageColor(BlockAndTintGetter $$0, BlockPos $$1, ColorResolver $$2) {
/* 14 */     return $$0.getBlockTint($$1, $$2);
/*    */   }
/*    */   
/*    */   public static int getAverageGrassColor(BlockAndTintGetter $$0, BlockPos $$1) {
/* 18 */     return getAverageColor($$0, $$1, GRASS_COLOR_RESOLVER);
/*    */   }
/*    */   
/*    */   public static int getAverageFoliageColor(BlockAndTintGetter $$0, BlockPos $$1) {
/* 22 */     return getAverageColor($$0, $$1, FOLIAGE_COLOR_RESOLVER);
/*    */   }
/*    */   
/*    */   public static int getAverageWaterColor(BlockAndTintGetter $$0, BlockPos $$1) {
/* 26 */     return getAverageColor($$0, $$1, WATER_COLOR_RESOLVER);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\BiomeColors.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */