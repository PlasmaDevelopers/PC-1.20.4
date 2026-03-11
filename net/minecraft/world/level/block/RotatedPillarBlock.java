/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.world.item.context.BlockPlaceContext;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.StateDefinition;
/*    */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ 
/*    */ public class RotatedPillarBlock extends Block {
/* 12 */   public static final MapCodec<RotatedPillarBlock> CODEC = simpleCodec(RotatedPillarBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<? extends RotatedPillarBlock> codec() {
/* 16 */     return CODEC;
/*    */   }
/*    */   
/* 19 */   public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;
/*    */   
/*    */   public RotatedPillarBlock(BlockBehaviour.Properties $$0) {
/* 22 */     super($$0);
/* 23 */     registerDefaultState((BlockState)defaultBlockState().setValue((Property)AXIS, (Comparable)Direction.Axis.Y));
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState rotate(BlockState $$0, Rotation $$1) {
/* 28 */     return rotatePillar($$0, $$1);
/*    */   }
/*    */   
/*    */   public static BlockState rotatePillar(BlockState $$0, Rotation $$1) {
/* 32 */     switch ($$1) {
/*    */       case COUNTERCLOCKWISE_90:
/*    */       case CLOCKWISE_90:
/* 35 */         switch ((Direction.Axis)$$0.getValue((Property)AXIS)) {
/*    */           case COUNTERCLOCKWISE_90:
/* 37 */             return (BlockState)$$0.setValue((Property)AXIS, (Comparable)Direction.Axis.Z);
/*    */           case CLOCKWISE_90:
/* 39 */             return (BlockState)$$0.setValue((Property)AXIS, (Comparable)Direction.Axis.X);
/*    */         } 
/* 41 */         return $$0;
/*    */     } 
/*    */     
/* 44 */     return $$0;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 50 */     $$0.add(new Property[] { (Property)AXIS });
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 55 */     return (BlockState)defaultBlockState().setValue((Property)AXIS, (Comparable)$$0.getClickedFace().getAxis());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\RotatedPillarBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */