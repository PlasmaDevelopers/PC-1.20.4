/*    */ package net.minecraft.world.level.block;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.properties.DirectionProperty;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ 
/*    */ public abstract class HorizontalDirectionalBlock extends Block {
/*  9 */   public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
/*    */   
/*    */   protected HorizontalDirectionalBlock(BlockBehaviour.Properties $$0) {
/* 12 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected abstract MapCodec<? extends HorizontalDirectionalBlock> codec();
/*    */ 
/*    */   
/*    */   public BlockState rotate(BlockState $$0, Rotation $$1) {
/* 20 */     return (BlockState)$$0.setValue((Property)FACING, (Comparable)$$1.rotate((Direction)$$0.getValue((Property)FACING)));
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState mirror(BlockState $$0, Mirror $$1) {
/* 25 */     return $$0.rotate($$1.getRotation((Direction)$$0.getValue((Property)FACING)));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\HorizontalDirectionalBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */