/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.data.worldgen.placement.VegetationPlacements;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.biome.Biome;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
/*    */ import net.minecraft.world.level.levelgen.placement.PlacedFeature;
/*    */ 
/*    */ public class GrassBlock extends SpreadingSnowyDirtBlock implements BonemealableBlock {
/* 21 */   public static final MapCodec<GrassBlock> CODEC = simpleCodec(GrassBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<GrassBlock> codec() {
/* 25 */     return CODEC;
/*    */   }
/*    */   
/*    */   public GrassBlock(BlockBehaviour.Properties $$0) {
/* 29 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isValidBonemealTarget(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/* 34 */     return $$0.getBlockState($$1.above()).isAir();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isBonemealSuccess(Level $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 39 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void performBonemeal(ServerLevel $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 44 */     BlockPos $$4 = $$2.above();
/*    */     
/* 46 */     BlockState $$5 = Blocks.SHORT_GRASS.defaultBlockState();
/*    */     
/* 48 */     Optional<Holder.Reference<PlacedFeature>> $$6 = $$0.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getHolder(VegetationPlacements.GRASS_BONEMEAL);
/*    */     
/*    */     int $$7;
/* 51 */     label32: for ($$7 = 0; $$7 < 128; $$7++) {
/* 52 */       Holder<PlacedFeature> $$13; BlockPos $$8 = $$4;
/* 53 */       for (int $$9 = 0; $$9 < $$7 / 16; ) {
/* 54 */         $$8 = $$8.offset($$1.nextInt(3) - 1, ($$1.nextInt(3) - 1) * $$1.nextInt(3) / 2, $$1.nextInt(3) - 1);
/* 55 */         if ($$0.getBlockState($$8.below()).is(this)) { if ($$0.getBlockState($$8).isCollisionShapeFullBlock((BlockGetter)$$0, $$8))
/*    */             continue label32; 
/*    */           $$9++; }
/*    */         
/*    */         continue label32;
/*    */       } 
/* 61 */       BlockState $$10 = $$0.getBlockState($$8);
/* 62 */       if ($$10.is($$5.getBlock()) && $$1.nextInt(10) == 0) {
/* 63 */         ((BonemealableBlock)$$5.getBlock()).performBonemeal($$0, $$1, $$8, $$10);
/*    */       }
/*    */       
/* 66 */       if (!$$10.isAir()) {
/*    */         continue;
/*    */       }
/*    */ 
/*    */       
/* 71 */       if ($$1.nextInt(8) == 0) {
/* 72 */         List<ConfiguredFeature<?, ?>> $$11 = ((Biome)$$0.getBiome($$8).value()).getGenerationSettings().getFlowerFeatures();
/* 73 */         if ($$11.isEmpty()) {
/*    */           continue;
/*    */         }
/*    */         
/* 77 */         Holder<PlacedFeature> $$12 = ((RandomPatchConfiguration)((ConfiguredFeature)$$11.get(0)).config()).feature();
/* 78 */       } else if ($$6.isPresent()) {
/* 79 */         $$13 = (Holder<PlacedFeature>)$$6.get();
/*    */       } else {
/*    */         continue;
/*    */       } 
/*    */       
/* 84 */       ((PlacedFeature)$$13.value()).place((WorldGenLevel)$$0, $$0.getChunkSource().getGenerator(), $$1, $$8);
/*    */       continue;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\GrassBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */