/*    */ package net.minecraft.world.level.block;
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.EnumMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*    */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ import net.minecraft.world.phys.shapes.CollisionContext;
/*    */ import net.minecraft.world.phys.shapes.Shapes;
/*    */ import net.minecraft.world.phys.shapes.VoxelShape;
/*    */ 
/*    */ public abstract class PipeBlock extends Block {
/* 20 */   private static final Direction[] DIRECTIONS = Direction.values();
/*    */   
/* 22 */   public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
/* 23 */   public static final BooleanProperty EAST = BlockStateProperties.EAST;
/* 24 */   public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
/* 25 */   public static final BooleanProperty WEST = BlockStateProperties.WEST;
/* 26 */   public static final BooleanProperty UP = BlockStateProperties.UP;
/* 27 */   public static final BooleanProperty DOWN = BlockStateProperties.DOWN; public static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION;
/*    */   static {
/* 29 */     PROPERTY_BY_DIRECTION = (Map<Direction, BooleanProperty>)ImmutableMap.copyOf((Map)Util.make(Maps.newEnumMap(Direction.class), $$0 -> {
/*    */             $$0.put(Direction.NORTH, NORTH);
/*    */             $$0.put(Direction.EAST, EAST);
/*    */             $$0.put(Direction.SOUTH, SOUTH);
/*    */             $$0.put(Direction.WEST, WEST);
/*    */             $$0.put(Direction.UP, UP);
/*    */             $$0.put(Direction.DOWN, DOWN);
/*    */           }));
/*    */   }
/*    */   protected final VoxelShape[] shapeByIndex;
/*    */   
/*    */   protected PipeBlock(float $$0, BlockBehaviour.Properties $$1) {
/* 41 */     super($$1);
/*    */     
/* 43 */     this.shapeByIndex = makeShapes($$0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private VoxelShape[] makeShapes(float $$0) {
/* 50 */     float $$1 = 0.5F - $$0;
/* 51 */     float $$2 = 0.5F + $$0;
/*    */     
/* 53 */     VoxelShape $$3 = Block.box(($$1 * 16.0F), ($$1 * 16.0F), ($$1 * 16.0F), ($$2 * 16.0F), ($$2 * 16.0F), ($$2 * 16.0F));
/*    */     
/* 55 */     VoxelShape[] $$4 = new VoxelShape[DIRECTIONS.length];
/*    */     
/* 57 */     for (int $$5 = 0; $$5 < DIRECTIONS.length; $$5++) {
/* 58 */       Direction $$6 = DIRECTIONS[$$5];
/* 59 */       $$4[$$5] = Shapes.box(0.5D + 
/* 60 */           Math.min(-$$0, $$6.getStepX() * 0.5D), 0.5D + 
/* 61 */           Math.min(-$$0, $$6.getStepY() * 0.5D), 0.5D + 
/* 62 */           Math.min(-$$0, $$6.getStepZ() * 0.5D), 0.5D + 
/* 63 */           Math.max($$0, $$6.getStepX() * 0.5D), 0.5D + 
/* 64 */           Math.max($$0, $$6.getStepY() * 0.5D), 0.5D + 
/* 65 */           Math.max($$0, $$6.getStepZ() * 0.5D));
/*    */     } 
/*    */ 
/*    */     
/* 69 */     VoxelShape[] $$7 = new VoxelShape[64];
/* 70 */     for (int $$8 = 0; $$8 < 64; $$8++) {
/* 71 */       VoxelShape $$9 = $$3;
/* 72 */       for (int $$10 = 0; $$10 < DIRECTIONS.length; $$10++) {
/* 73 */         if (($$8 & 1 << $$10) != 0) {
/* 74 */           $$9 = Shapes.or($$9, $$4[$$10]);
/*    */         }
/*    */       } 
/* 77 */       $$7[$$8] = $$9;
/*    */     } 
/* 79 */     return $$7;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean propagatesSkylightDown(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/* 84 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 89 */     return this.shapeByIndex[getAABBIndex($$0)];
/*    */   }
/*    */   
/*    */   protected int getAABBIndex(BlockState $$0) {
/* 93 */     int $$1 = 0;
/* 94 */     for (int $$2 = 0; $$2 < DIRECTIONS.length; $$2++) {
/* 95 */       if (((Boolean)$$0.getValue((Property)PROPERTY_BY_DIRECTION.get(DIRECTIONS[$$2]))).booleanValue()) {
/* 96 */         $$1 |= 1 << $$2;
/*    */       }
/*    */     } 
/* 99 */     return $$1;
/*    */   }
/*    */   
/*    */   protected abstract MapCodec<? extends PipeBlock> codec();
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\PipeBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */