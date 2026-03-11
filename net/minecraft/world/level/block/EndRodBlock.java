/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.item.context.BlockPlaceContext;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.StateDefinition;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ 
/*    */ public class EndRodBlock extends RodBlock {
/* 14 */   public static final MapCodec<EndRodBlock> CODEC = simpleCodec(EndRodBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<EndRodBlock> codec() {
/* 18 */     return CODEC;
/*    */   }
/*    */   
/*    */   protected EndRodBlock(BlockBehaviour.Properties $$0) {
/* 22 */     super($$0);
/* 23 */     registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)FACING, (Comparable)Direction.UP));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 29 */     Direction $$1 = $$0.getClickedFace();
/*    */     
/* 31 */     BlockState $$2 = $$0.getLevel().getBlockState($$0.getClickedPos().relative($$1.getOpposite()));
/* 32 */     if ($$2.is(this) && $$2.getValue((Property)FACING) == $$1) {
/* 33 */       return (BlockState)defaultBlockState().setValue((Property)FACING, (Comparable)$$1.getOpposite());
/*    */     }
/*    */     
/* 36 */     return (BlockState)defaultBlockState().setValue((Property)FACING, (Comparable)$$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public void animateTick(BlockState $$0, Level $$1, BlockPos $$2, RandomSource $$3) {
/* 41 */     Direction $$4 = (Direction)$$0.getValue((Property)FACING);
/* 42 */     double $$5 = $$2.getX() + 0.55D - ($$3.nextFloat() * 0.1F);
/* 43 */     double $$6 = $$2.getY() + 0.55D - ($$3.nextFloat() * 0.1F);
/* 44 */     double $$7 = $$2.getZ() + 0.55D - ($$3.nextFloat() * 0.1F);
/* 45 */     double $$8 = (0.4F - ($$3.nextFloat() + $$3.nextFloat()) * 0.4F);
/*    */     
/* 47 */     if ($$3.nextInt(5) == 0) {
/* 48 */       $$1.addParticle((ParticleOptions)ParticleTypes.END_ROD, $$5 + $$4.getStepX() * $$8, $$6 + $$4.getStepY() * $$8, $$7 + $$4.getStepZ() * $$8, $$3.nextGaussian() * 0.005D, $$3.nextGaussian() * 0.005D, $$3.nextGaussian() * 0.005D);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 54 */     $$0.add(new Property[] { (Property)FACING });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\EndRodBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */