/*    */ package net.minecraft.world.level.levelgen.feature.stateproviders;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.RotatedPillarBlock;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class RotatedBlockProvider extends BlockStateProvider {
/*    */   public static final Codec<RotatedBlockProvider> CODEC;
/*    */   
/*    */   static {
/* 15 */     CODEC = BlockState.CODEC.fieldOf("state").xmap(BlockBehaviour.BlockStateBase::getBlock, Block::defaultBlockState).xmap(RotatedBlockProvider::new, $$0 -> $$0.block).codec();
/*    */   }
/*    */   private final Block block;
/*    */   
/*    */   public RotatedBlockProvider(Block $$0) {
/* 20 */     this.block = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected BlockStateProviderType<?> type() {
/* 25 */     return BlockStateProviderType.ROTATED_BLOCK_PROVIDER;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState getState(RandomSource $$0, BlockPos $$1) {
/* 30 */     Direction.Axis $$2 = Direction.Axis.getRandom($$0);
/* 31 */     return (BlockState)this.block.defaultBlockState().setValue((Property)RotatedPillarBlock.AXIS, (Comparable)$$2);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\stateproviders\RotatedBlockProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */