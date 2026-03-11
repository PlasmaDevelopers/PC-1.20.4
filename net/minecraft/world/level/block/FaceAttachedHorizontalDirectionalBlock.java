/*    */ package net.minecraft.world.level.block;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.world.item.context.BlockPlaceContext;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.properties.AttachFace;
/*    */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*    */ import net.minecraft.world.level.block.state.properties.EnumProperty;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ 
/*    */ public abstract class FaceAttachedHorizontalDirectionalBlock extends HorizontalDirectionalBlock {
/* 17 */   public static final EnumProperty<AttachFace> FACE = BlockStateProperties.ATTACH_FACE;
/*    */   
/*    */   protected FaceAttachedHorizontalDirectionalBlock(BlockBehaviour.Properties $$0) {
/* 20 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected abstract MapCodec<? extends FaceAttachedHorizontalDirectionalBlock> codec();
/*    */ 
/*    */   
/*    */   public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/* 28 */     return canAttach($$1, $$2, getConnectedDirection($$0).getOpposite());
/*    */   }
/*    */   
/*    */   public static boolean canAttach(LevelReader $$0, BlockPos $$1, Direction $$2) {
/* 32 */     BlockPos $$3 = $$1.relative($$2);
/* 33 */     return $$0.getBlockState($$3).isFaceSturdy((BlockGetter)$$0, $$3, $$2.getOpposite());
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 39 */     for (Direction $$1 : $$0.getNearestLookingDirections()) {
/*    */       BlockState $$3;
/* 41 */       if ($$1.getAxis() == Direction.Axis.Y) {
/* 42 */         BlockState $$2 = (BlockState)((BlockState)defaultBlockState().setValue((Property)FACE, ($$1 == Direction.UP) ? (Comparable)AttachFace.CEILING : (Comparable)AttachFace.FLOOR)).setValue((Property)FACING, (Comparable)$$0.getHorizontalDirection());
/*    */       } else {
/* 44 */         $$3 = (BlockState)((BlockState)defaultBlockState().setValue((Property)FACE, (Comparable)AttachFace.WALL)).setValue((Property)FACING, (Comparable)$$1.getOpposite());
/*    */       } 
/*    */       
/* 47 */       if ($$3.canSurvive((LevelReader)$$0.getLevel(), $$0.getClickedPos())) {
/* 48 */         return $$3;
/*    */       }
/*    */     } 
/*    */     
/* 52 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 57 */     if (getConnectedDirection($$0).getOpposite() == $$1 && !$$0.canSurvive((LevelReader)$$3, $$4)) {
/* 58 */       return Blocks.AIR.defaultBlockState();
/*    */     }
/* 60 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*    */   }
/*    */   
/*    */   protected static Direction getConnectedDirection(BlockState $$0) {
/* 64 */     switch ((AttachFace)$$0.getValue((Property)FACE)) {
/*    */       case CEILING:
/* 66 */         return Direction.DOWN;
/*    */       case FLOOR:
/* 68 */         return Direction.UP;
/*    */     } 
/* 70 */     return (Direction)$$0.getValue((Property)FACING);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\FaceAttachedHorizontalDirectionalBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */