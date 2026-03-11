/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.Registry;
/*    */ import net.minecraft.data.worldgen.features.NetherFeatures;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*    */ import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
/*    */ 
/*    */ public class NyliumBlock extends Block implements BonemealableBlock {
/* 20 */   public static final MapCodec<NyliumBlock> CODEC = simpleCodec(NyliumBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<NyliumBlock> codec() {
/* 24 */     return CODEC;
/*    */   }
/*    */   
/*    */   protected NyliumBlock(BlockBehaviour.Properties $$0) {
/* 28 */     super($$0);
/*    */   }
/*    */   
/*    */   private static boolean canBeNylium(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/* 32 */     BlockPos $$3 = $$2.above();
/* 33 */     BlockState $$4 = $$1.getBlockState($$3);
/*    */ 
/*    */     
/* 36 */     int $$5 = LightEngine.getLightBlockInto((BlockGetter)$$1, $$0, $$2, $$4, $$3, Direction.UP, $$4.getLightBlock((BlockGetter)$$1, $$3));
/* 37 */     return ($$5 < $$1.getMaxLightLevel());
/*    */   }
/*    */ 
/*    */   
/*    */   public void randomTick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/* 42 */     if (!canBeNylium($$0, (LevelReader)$$1, $$2)) {
/* 43 */       $$1.setBlockAndUpdate($$2, Blocks.NETHERRACK.defaultBlockState());
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isValidBonemealTarget(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/* 49 */     return $$0.getBlockState($$1.above()).isAir();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isBonemealSuccess(Level $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 54 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void performBonemeal(ServerLevel $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 59 */     BlockState $$4 = $$0.getBlockState($$2);
/* 60 */     BlockPos $$5 = $$2.above();
/* 61 */     ChunkGenerator $$6 = $$0.getChunkSource().getGenerator();
/* 62 */     Registry<ConfiguredFeature<?, ?>> $$7 = $$0.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE);
/* 63 */     if ($$4.is(Blocks.CRIMSON_NYLIUM)) {
/* 64 */       place($$7, NetherFeatures.CRIMSON_FOREST_VEGETATION_BONEMEAL, $$0, $$6, $$1, $$5);
/* 65 */     } else if ($$4.is(Blocks.WARPED_NYLIUM)) {
/* 66 */       place($$7, NetherFeatures.WARPED_FOREST_VEGETATION_BONEMEAL, $$0, $$6, $$1, $$5);
/* 67 */       place($$7, NetherFeatures.NETHER_SPROUTS_BONEMEAL, $$0, $$6, $$1, $$5);
/* 68 */       if ($$1.nextInt(8) == 0) {
/* 69 */         place($$7, NetherFeatures.TWISTING_VINES_BONEMEAL, $$0, $$6, $$1, $$5);
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   private void place(Registry<ConfiguredFeature<?, ?>> $$0, ResourceKey<ConfiguredFeature<?, ?>> $$1, ServerLevel $$2, ChunkGenerator $$3, RandomSource $$4, BlockPos $$5) {
/* 75 */     $$0.getHolder($$1).ifPresent($$4 -> ((ConfiguredFeature)$$4.value()).place((WorldGenLevel)$$0, $$1, $$2, $$3));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\NyliumBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */