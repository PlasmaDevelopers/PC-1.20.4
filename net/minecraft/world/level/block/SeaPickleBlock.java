/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ import net.minecraft.world.level.pathfinder.PathComputationType;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class SeaPickleBlock extends BushBlock implements BonemealableBlock, SimpleWaterloggedBlock {
/*  28 */   public static final MapCodec<SeaPickleBlock> CODEC = simpleCodec(SeaPickleBlock::new);
/*     */   public static final int MAX_PICKLES = 4;
/*     */   
/*     */   public MapCodec<SeaPickleBlock> codec() {
/*  32 */     return CODEC;
/*     */   }
/*     */ 
/*     */   
/*  36 */   public static final IntegerProperty PICKLES = BlockStateProperties.PICKLES;
/*  37 */   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
/*     */   
/*  39 */   protected static final VoxelShape ONE_AABB = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 6.0D, 10.0D);
/*  40 */   protected static final VoxelShape TWO_AABB = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 6.0D, 13.0D);
/*  41 */   protected static final VoxelShape THREE_AABB = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 6.0D, 14.0D);
/*  42 */   protected static final VoxelShape FOUR_AABB = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 7.0D, 14.0D);
/*     */   
/*     */   protected SeaPickleBlock(BlockBehaviour.Properties $$0) {
/*  45 */     super($$0);
/*  46 */     registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)PICKLES, Integer.valueOf(1))).setValue((Property)WATERLOGGED, Boolean.valueOf(true)));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/*  52 */     BlockState $$1 = $$0.getLevel().getBlockState($$0.getClickedPos());
/*  53 */     if ($$1.is(this)) {
/*  54 */       return (BlockState)$$1.setValue((Property)PICKLES, Integer.valueOf(Math.min(4, ((Integer)$$1.getValue((Property)PICKLES)).intValue() + 1)));
/*     */     }
/*     */     
/*  57 */     FluidState $$2 = $$0.getLevel().getFluidState($$0.getClickedPos());
/*  58 */     boolean $$3 = ($$2.getType() == Fluids.WATER);
/*  59 */     return (BlockState)super.getStateForPlacement($$0).setValue((Property)WATERLOGGED, Boolean.valueOf($$3));
/*     */   }
/*     */   
/*     */   public static boolean isDead(BlockState $$0) {
/*  63 */     return !((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean mayPlaceOn(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/*  68 */     return (!$$0.getCollisionShape($$1, $$2).getFaceShape(Direction.UP).isEmpty() || $$0.isFaceSturdy($$1, $$2, Direction.UP));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/*  73 */     BlockPos $$3 = $$2.below();
/*  74 */     return mayPlaceOn($$1.getBlockState($$3), (BlockGetter)$$1, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/*  79 */     if (!$$0.canSurvive((LevelReader)$$3, $$4)) {
/*  80 */       return Blocks.AIR.defaultBlockState();
/*     */     }
/*     */     
/*  83 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/*  84 */       $$3.scheduleTick($$4, (Fluid)Fluids.WATER, Fluids.WATER.getTickDelay((LevelReader)$$3));
/*     */     }
/*     */     
/*  87 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBeReplaced(BlockState $$0, BlockPlaceContext $$1) {
/*  92 */     if (!$$1.isSecondaryUseActive() && $$1.getItemInHand().is(asItem()) && ((Integer)$$0.getValue((Property)PICKLES)).intValue() < 4) {
/*  93 */       return true;
/*     */     }
/*  95 */     return super.canBeReplaced($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 100 */     switch (((Integer)$$0.getValue((Property)PICKLES)).intValue())
/*     */     
/*     */     { default:
/* 103 */         return ONE_AABB;
/*     */       case 2:
/* 105 */         return TWO_AABB;
/*     */       case 3:
/* 107 */         return THREE_AABB;
/*     */       case 4:
/* 109 */         break; }  return FOUR_AABB;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FluidState getFluidState(BlockState $$0) {
/* 115 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 116 */       return Fluids.WATER.getSource(false);
/*     */     }
/*     */     
/* 119 */     return super.getFluidState($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 124 */     $$0.add(new Property[] { (Property)PICKLES, (Property)WATERLOGGED });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isValidBonemealTarget(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/* 129 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBonemealSuccess(Level $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 134 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void performBonemeal(ServerLevel $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 139 */     if (!isDead($$3) && $$0.getBlockState($$2.below()).is(BlockTags.CORAL_BLOCKS)) {
/* 140 */       int $$4 = 5;
/* 141 */       int $$5 = 1;
/* 142 */       int $$6 = 2;
/* 143 */       int $$7 = 0;
/*     */       
/* 145 */       int $$8 = $$2.getX() - 2;
/* 146 */       int $$9 = 0;
/*     */       
/* 148 */       for (int $$10 = 0; $$10 < 5; $$10++) {
/* 149 */         for (int $$11 = 0; $$11 < $$5; $$11++) {
/* 150 */           int $$12 = 2 + $$2.getY() - 1;
/* 151 */           for (int $$13 = $$12 - 2; $$13 < $$12; $$13++) {
/* 152 */             BlockPos $$14 = new BlockPos($$8 + $$10, $$13, $$2.getZ() - $$9 + $$11);
/* 153 */             if ($$14 != $$2)
/*     */             {
/*     */ 
/*     */               
/* 157 */               if ($$1.nextInt(6) == 0 && $$0.getBlockState($$14).is(Blocks.WATER)) {
/* 158 */                 BlockState $$15 = $$0.getBlockState($$14.below());
/* 159 */                 if ($$15.is(BlockTags.CORAL_BLOCKS)) {
/* 160 */                   $$0.setBlock($$14, (BlockState)Blocks.SEA_PICKLE.defaultBlockState().setValue((Property)PICKLES, Integer.valueOf($$1.nextInt(4) + 1)), 3);
/*     */                 }
/*     */               } 
/*     */             }
/*     */           } 
/*     */         } 
/* 166 */         if ($$7 < 2) {
/* 167 */           $$5 += 2;
/* 168 */           $$9++;
/*     */         } else {
/* 170 */           $$5 -= 2;
/* 171 */           $$9--;
/*     */         } 
/* 173 */         $$7++;
/*     */       } 
/*     */       
/* 176 */       $$0.setBlock($$2, (BlockState)$$3.setValue((Property)PICKLES, Integer.valueOf(4)), 2);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
/* 182 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\SeaPickleBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */