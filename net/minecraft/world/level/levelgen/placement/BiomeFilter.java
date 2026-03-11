/*    */ package net.minecraft.world.level.levelgen.placement;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.biome.Biome;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BiomeFilter
/*    */   extends PlacementFilter
/*    */ {
/* 14 */   private static final BiomeFilter INSTANCE = new BiomeFilter();
/*    */   
/* 16 */   public static Codec<BiomeFilter> CODEC = Codec.unit(() -> INSTANCE);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static BiomeFilter biome() {
/* 22 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean shouldPlace(PlacementContext $$0, RandomSource $$1, BlockPos $$2) {
/* 27 */     PlacedFeature $$3 = $$0.topFeature().<Throwable>orElseThrow(() -> new IllegalStateException("Tried to biome check an unregistered feature, or a feature that should not restrict the biome"));
/* 28 */     Holder<Biome> $$4 = $$0.getLevel().getBiome($$2);
/* 29 */     return $$0.generator().getBiomeGenerationSettings($$4).hasFeature($$3);
/*    */   }
/*    */ 
/*    */   
/*    */   public PlacementModifierType<?> type() {
/* 34 */     return PlacementModifierType.BIOME_FILTER;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\placement\BiomeFilter.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */