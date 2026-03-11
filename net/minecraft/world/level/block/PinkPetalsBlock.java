/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.DirectionProperty;
/*     */ import net.minecraft.world.level.block.state.properties.IntegerProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.Shapes;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class PinkPetalsBlock extends BushBlock implements BonemealableBlock {
/*  26 */   public static final MapCodec<PinkPetalsBlock> CODEC = simpleCodec(PinkPetalsBlock::new); public static final int MIN_FLOWERS = 1;
/*     */   public static final int MAX_FLOWERS = 4;
/*     */   
/*     */   public MapCodec<PinkPetalsBlock> codec() {
/*  30 */     return CODEC;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  36 */   public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
/*  37 */   public static final IntegerProperty AMOUNT = BlockStateProperties.FLOWER_AMOUNT; private static final BiFunction<Direction, Integer, VoxelShape> SHAPE_BY_PROPERTIES;
/*     */   static {
/*  39 */     SHAPE_BY_PROPERTIES = Util.memoize(($$0, $$1) -> {
/*     */           VoxelShape[] $$2 = { Block.box(8.0D, 0.0D, 8.0D, 16.0D, 3.0D, 16.0D), Block.box(8.0D, 0.0D, 0.0D, 16.0D, 3.0D, 8.0D), Block.box(0.0D, 0.0D, 0.0D, 8.0D, 3.0D, 8.0D), Block.box(0.0D, 0.0D, 8.0D, 8.0D, 3.0D, 16.0D) };
/*     */           VoxelShape $$3 = Shapes.empty();
/*     */           for (int $$4 = 0; $$4 < $$1.intValue(); $$4++) {
/*     */             int $$5 = Math.floorMod($$4 - $$0.get2DDataValue(), 4);
/*     */             $$3 = Shapes.or($$3, $$2[$$5]);
/*     */           } 
/*     */           return $$3.singleEncompassing();
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PinkPetalsBlock(BlockBehaviour.Properties $$0) {
/*  59 */     super($$0);
/*  60 */     registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)FACING, (Comparable)Direction.NORTH)).setValue((Property)AMOUNT, Integer.valueOf(1)));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState rotate(BlockState $$0, Rotation $$1) {
/*  65 */     return (BlockState)$$0.setValue((Property)FACING, (Comparable)$$1.rotate((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState mirror(BlockState $$0, Mirror $$1) {
/*  70 */     return $$0.rotate($$1.getRotation((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBeReplaced(BlockState $$0, BlockPlaceContext $$1) {
/*  75 */     if (!$$1.isSecondaryUseActive() && $$1.getItemInHand().is(asItem()) && ((Integer)$$0.getValue((Property)AMOUNT)).intValue() < 4) {
/*  76 */       return true;
/*     */     }
/*  78 */     return super.canBeReplaced($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  83 */     return SHAPE_BY_PROPERTIES.apply((Direction)$$0.getValue((Property)FACING), (Integer)$$0.getValue((Property)AMOUNT));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/*  88 */     BlockState $$1 = $$0.getLevel().getBlockState($$0.getClickedPos());
/*  89 */     if ($$1.is(this)) {
/*  90 */       return (BlockState)$$1.setValue((Property)AMOUNT, Integer.valueOf(Math.min(4, ((Integer)$$1.getValue((Property)AMOUNT)).intValue() + 1)));
/*     */     }
/*  92 */     return (BlockState)defaultBlockState().setValue((Property)FACING, (Comparable)$$0.getHorizontalDirection().getOpposite());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/*  97 */     $$0.add(new Property[] { (Property)FACING, (Property)AMOUNT });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isValidBonemealTarget(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/* 102 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBonemealSuccess(Level $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 107 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void performBonemeal(ServerLevel $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 112 */     int $$4 = ((Integer)$$3.getValue((Property)AMOUNT)).intValue();
/* 113 */     if ($$4 < 4) {
/* 114 */       $$0.setBlock($$2, (BlockState)$$3.setValue((Property)AMOUNT, Integer.valueOf($$4 + 1)), 2);
/*     */     } else {
/* 116 */       popResource((Level)$$0, $$2, new ItemStack(this));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\PinkPetalsBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */