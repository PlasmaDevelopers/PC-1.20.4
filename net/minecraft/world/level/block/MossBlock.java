/*    */ package net.minecraft.world.level.block;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.Registry;
/*    */ import net.minecraft.data.worldgen.features.CaveFeatures;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
/*    */ 
/*    */ public class MossBlock extends Block implements BonemealableBlock {
/* 14 */   public static final MapCodec<MossBlock> CODEC = simpleCodec(MossBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<MossBlock> codec() {
/* 18 */     return CODEC;
/*    */   }
/*    */   
/*    */   public MossBlock(BlockBehaviour.Properties $$0) {
/* 22 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isValidBonemealTarget(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/* 27 */     return $$0.getBlockState($$1.above()).isAir();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isBonemealSuccess(Level $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 32 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void performBonemeal(ServerLevel $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 37 */     $$0.registryAccess()
/* 38 */       .registry(Registries.CONFIGURED_FEATURE)
/* 39 */       .flatMap($$0 -> $$0.getHolder(CaveFeatures.MOSS_PATCH_BONEMEAL))
/* 40 */       .ifPresent($$3 -> ((ConfiguredFeature)$$3.value()).place((WorldGenLevel)$$0, $$0.getChunkSource().getGenerator(), $$1, $$2.above()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\MossBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */