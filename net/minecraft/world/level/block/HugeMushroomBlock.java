/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.world.item.context.BlockPlaceContext;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.StateDefinition;
/*    */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ 
/*    */ public class HugeMushroomBlock extends Block {
/* 16 */   public static final MapCodec<HugeMushroomBlock> CODEC = simpleCodec(HugeMushroomBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<HugeMushroomBlock> codec() {
/* 20 */     return CODEC;
/*    */   }
/*    */   
/* 23 */   public static final BooleanProperty NORTH = PipeBlock.NORTH;
/* 24 */   public static final BooleanProperty EAST = PipeBlock.EAST;
/* 25 */   public static final BooleanProperty SOUTH = PipeBlock.SOUTH;
/* 26 */   public static final BooleanProperty WEST = PipeBlock.WEST;
/* 27 */   public static final BooleanProperty UP = PipeBlock.UP;
/* 28 */   public static final BooleanProperty DOWN = PipeBlock.DOWN;
/*    */   
/* 30 */   private static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION = PipeBlock.PROPERTY_BY_DIRECTION;
/*    */   
/*    */   public HugeMushroomBlock(BlockBehaviour.Properties $$0) {
/* 33 */     super($$0);
/* 34 */     registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)NORTH, Boolean.valueOf(true))).setValue((Property)EAST, Boolean.valueOf(true))).setValue((Property)SOUTH, Boolean.valueOf(true))).setValue((Property)WEST, Boolean.valueOf(true))).setValue((Property)UP, Boolean.valueOf(true))).setValue((Property)DOWN, Boolean.valueOf(true)));
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 39 */     Level level = $$0.getLevel();
/* 40 */     BlockPos $$2 = $$0.getClickedPos();
/*    */     
/* 42 */     return (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)defaultBlockState()
/* 43 */       .setValue((Property)DOWN, Boolean.valueOf(!level.getBlockState($$2.below()).is(this))))
/* 44 */       .setValue((Property)UP, Boolean.valueOf(!level.getBlockState($$2.above()).is(this))))
/* 45 */       .setValue((Property)NORTH, Boolean.valueOf(!level.getBlockState($$2.north()).is(this))))
/* 46 */       .setValue((Property)EAST, Boolean.valueOf(!level.getBlockState($$2.east()).is(this))))
/* 47 */       .setValue((Property)SOUTH, Boolean.valueOf(!level.getBlockState($$2.south()).is(this))))
/* 48 */       .setValue((Property)WEST, Boolean.valueOf(!level.getBlockState($$2.west()).is(this)));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 54 */     if ($$2.is(this)) {
/* 55 */       return (BlockState)$$0.setValue((Property)PROPERTY_BY_DIRECTION.get($$1), Boolean.valueOf(false));
/*    */     }
/* 57 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState rotate(BlockState $$0, Rotation $$1) {
/* 62 */     return (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)$$0
/* 63 */       .setValue((Property)PROPERTY_BY_DIRECTION.get($$1.rotate(Direction.NORTH)), $$0.getValue((Property)NORTH)))
/* 64 */       .setValue((Property)PROPERTY_BY_DIRECTION.get($$1.rotate(Direction.SOUTH)), $$0.getValue((Property)SOUTH)))
/* 65 */       .setValue((Property)PROPERTY_BY_DIRECTION.get($$1.rotate(Direction.EAST)), $$0.getValue((Property)EAST)))
/* 66 */       .setValue((Property)PROPERTY_BY_DIRECTION.get($$1.rotate(Direction.WEST)), $$0.getValue((Property)WEST)))
/* 67 */       .setValue((Property)PROPERTY_BY_DIRECTION.get($$1.rotate(Direction.UP)), $$0.getValue((Property)UP)))
/* 68 */       .setValue((Property)PROPERTY_BY_DIRECTION.get($$1.rotate(Direction.DOWN)), $$0.getValue((Property)DOWN));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public BlockState mirror(BlockState $$0, Mirror $$1) {
/* 74 */     return (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)$$0
/* 75 */       .setValue((Property)PROPERTY_BY_DIRECTION.get($$1.mirror(Direction.NORTH)), $$0.getValue((Property)NORTH)))
/* 76 */       .setValue((Property)PROPERTY_BY_DIRECTION.get($$1.mirror(Direction.SOUTH)), $$0.getValue((Property)SOUTH)))
/* 77 */       .setValue((Property)PROPERTY_BY_DIRECTION.get($$1.mirror(Direction.EAST)), $$0.getValue((Property)EAST)))
/* 78 */       .setValue((Property)PROPERTY_BY_DIRECTION.get($$1.mirror(Direction.WEST)), $$0.getValue((Property)WEST)))
/* 79 */       .setValue((Property)PROPERTY_BY_DIRECTION.get($$1.mirror(Direction.UP)), $$0.getValue((Property)UP)))
/* 80 */       .setValue((Property)PROPERTY_BY_DIRECTION.get($$1.mirror(Direction.DOWN)), $$0.getValue((Property)DOWN));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 86 */     $$0.add(new Property[] { (Property)UP, (Property)DOWN, (Property)NORTH, (Property)EAST, (Property)SOUTH, (Property)WEST });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\HugeMushroomBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */