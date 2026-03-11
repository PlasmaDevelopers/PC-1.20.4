/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function3;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
/*    */ import net.minecraft.world.phys.shapes.VoxelShape;
/*    */ 
/*    */ public class FungusBlock extends BushBlock implements BonemealableBlock {
/*    */   static {
/* 24 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)ResourceKey.codec(Registries.CONFIGURED_FEATURE).fieldOf("feature").forGetter(()), (App)BuiltInRegistries.BLOCK.byNameCodec().fieldOf("grows_on").forGetter(()), (App)propertiesCodec()).apply((Applicative)$$0, FungusBlock::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final MapCodec<FungusBlock> CODEC;
/*    */ 
/*    */   
/*    */   public MapCodec<FungusBlock> codec() {
/* 32 */     return CODEC;
/*    */   }
/*    */   
/* 35 */   protected static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 9.0D, 12.0D);
/*    */   
/*    */   private static final double BONEMEAL_SUCCESS_PROBABILITY = 0.4D;
/*    */   private final Block requiredBlock;
/*    */   private final ResourceKey<ConfiguredFeature<?, ?>> feature;
/*    */   
/*    */   protected FungusBlock(ResourceKey<ConfiguredFeature<?, ?>> $$0, Block $$1, BlockBehaviour.Properties $$2) {
/* 42 */     super($$2);
/* 43 */     this.feature = $$0;
/* 44 */     this.requiredBlock = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 49 */     return SHAPE;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean mayPlaceOn(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/* 54 */     return ($$0.is(BlockTags.NYLIUM) || $$0.is(Blocks.MYCELIUM) || $$0.is(Blocks.SOUL_SOIL) || super.mayPlaceOn($$0, $$1, $$2));
/*    */   }
/*    */   
/*    */   private Optional<? extends Holder<ConfiguredFeature<?, ?>>> getFeature(LevelReader $$0) {
/* 58 */     return $$0.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).getHolder(this.feature);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isValidBonemealTarget(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/* 64 */     BlockState $$3 = $$0.getBlockState($$1.below());
/* 65 */     return $$3.is(this.requiredBlock);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isBonemealSuccess(Level $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 70 */     return ($$1.nextFloat() < 0.4D);
/*    */   }
/*    */ 
/*    */   
/*    */   public void performBonemeal(ServerLevel $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 75 */     getFeature((LevelReader)$$0).ifPresent($$3 -> ((ConfiguredFeature)$$3.value()).place((WorldGenLevel)$$0, $$0.getChunkSource().getGenerator(), $$1, $$2));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\FungusBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */