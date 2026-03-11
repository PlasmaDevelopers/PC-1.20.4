/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BambooLeaves;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.EnumProperty;
/*     */ import net.minecraft.world.level.block.state.properties.IntegerProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.level.pathfinder.PathComputationType;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class BambooStalkBlock extends Block implements BonemealableBlock {
/*  31 */   public static final MapCodec<BambooStalkBlock> CODEC = simpleCodec(BambooStalkBlock::new);
/*     */   protected static final float SMALL_LEAVES_AABB_OFFSET = 3.0F;
/*     */   
/*     */   public MapCodec<BambooStalkBlock> codec() {
/*  35 */     return CODEC;
/*     */   }
/*     */ 
/*     */   
/*     */   protected static final float LARGE_LEAVES_AABB_OFFSET = 5.0F;
/*     */   
/*     */   protected static final float COLLISION_AABB_OFFSET = 1.5F;
/*  42 */   protected static final VoxelShape SMALL_SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 16.0D, 11.0D);
/*  43 */   protected static final VoxelShape LARGE_SHAPE = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 16.0D, 13.0D);
/*  44 */   protected static final VoxelShape COLLISION_SHAPE = Block.box(6.5D, 0.0D, 6.5D, 9.5D, 16.0D, 9.5D);
/*     */   
/*  46 */   public static final IntegerProperty AGE = BlockStateProperties.AGE_1;
/*  47 */   public static final EnumProperty<BambooLeaves> LEAVES = BlockStateProperties.BAMBOO_LEAVES;
/*  48 */   public static final IntegerProperty STAGE = BlockStateProperties.STAGE;
/*     */   
/*     */   public static final int MAX_HEIGHT = 16;
/*     */   public static final int STAGE_GROWING = 0;
/*     */   public static final int STAGE_DONE_GROWING = 1;
/*     */   public static final int AGE_THIN_BAMBOO = 0;
/*     */   public static final int AGE_THICK_BAMBOO = 1;
/*     */   
/*     */   public BambooStalkBlock(BlockBehaviour.Properties $$0) {
/*  57 */     super($$0);
/*  58 */     registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)AGE, Integer.valueOf(0))).setValue((Property)LEAVES, (Comparable)BambooLeaves.NONE)).setValue((Property)STAGE, Integer.valueOf(0)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/*  63 */     $$0.add(new Property[] { (Property)AGE, (Property)LEAVES, (Property)STAGE });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean propagatesSkylightDown(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/*  68 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  73 */     VoxelShape $$4 = ($$0.getValue((Property)LEAVES) == BambooLeaves.LARGE) ? LARGE_SHAPE : SMALL_SHAPE;
/*  74 */     Vec3 $$5 = $$0.getOffset($$1, $$2);
/*  75 */     return $$4.move($$5.x, $$5.y, $$5.z);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
/*  80 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getCollisionShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  85 */     Vec3 $$4 = $$0.getOffset($$1, $$2);
/*  86 */     return COLLISION_SHAPE.move($$4.x, $$4.y, $$4.z);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCollisionShapeFullBlock(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/*  91 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/*  97 */     FluidState $$1 = $$0.getLevel().getFluidState($$0.getClickedPos());
/*  98 */     if (!$$1.isEmpty()) {
/*  99 */       return null;
/*     */     }
/*     */     
/* 102 */     BlockState $$2 = $$0.getLevel().getBlockState($$0.getClickedPos().below());
/* 103 */     if ($$2.is(BlockTags.BAMBOO_PLANTABLE_ON)) {
/* 104 */       if ($$2.is(Blocks.BAMBOO_SAPLING))
/* 105 */         return (BlockState)defaultBlockState().setValue((Property)AGE, Integer.valueOf(0)); 
/* 106 */       if ($$2.is(Blocks.BAMBOO)) {
/* 107 */         int $$3 = (((Integer)$$2.getValue((Property)AGE)).intValue() > 0) ? 1 : 0;
/* 108 */         return (BlockState)defaultBlockState().setValue((Property)AGE, Integer.valueOf($$3));
/*     */       } 
/* 110 */       BlockState $$4 = $$0.getLevel().getBlockState($$0.getClickedPos().above());
/* 111 */       if ($$4.is(Blocks.BAMBOO)) {
/* 112 */         return (BlockState)defaultBlockState().setValue((Property)AGE, $$4.getValue((Property)AGE));
/*     */       }
/* 114 */       return Blocks.BAMBOO_SAPLING.defaultBlockState();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 119 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/* 124 */     if (!$$0.canSurvive((LevelReader)$$1, $$2)) {
/* 125 */       $$1.destroyBlock($$2, true);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRandomlyTicking(BlockState $$0) {
/* 131 */     return (((Integer)$$0.getValue((Property)STAGE)).intValue() == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void randomTick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/* 136 */     if (((Integer)$$0.getValue((Property)STAGE)).intValue() != 0) {
/*     */       return;
/*     */     }
/*     */     
/* 140 */     if ($$3.nextInt(3) == 0 && $$1.isEmptyBlock($$2.above()) && $$1.getRawBrightness($$2.above(), 0) >= 9) {
/* 141 */       int $$4 = getHeightBelowUpToMax((BlockGetter)$$1, $$2) + 1;
/* 142 */       if ($$4 < 16) {
/* 143 */         growBamboo($$0, (Level)$$1, $$2, $$3, $$4);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/* 150 */     return $$1.getBlockState($$2.below()).is(BlockTags.BAMBOO_PLANTABLE_ON);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 155 */     if (!$$0.canSurvive((LevelReader)$$3, $$4)) {
/* 156 */       $$3.scheduleTick($$4, this, 1);
/*     */     }
/*     */     
/* 159 */     if ($$1 == Direction.UP && 
/* 160 */       $$2.is(Blocks.BAMBOO) && ((Integer)$$2.getValue((Property)AGE)).intValue() > ((Integer)$$0.getValue((Property)AGE)).intValue()) {
/* 161 */       $$3.setBlock($$4, (BlockState)$$0.cycle((Property)AGE), 2);
/*     */     }
/*     */ 
/*     */     
/* 165 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isValidBonemealTarget(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/* 170 */     int $$3 = getHeightAboveUpToMax((BlockGetter)$$0, $$1);
/* 171 */     int $$4 = getHeightBelowUpToMax((BlockGetter)$$0, $$1);
/* 172 */     return ($$3 + $$4 + 1 < 16 && ((Integer)$$0.getBlockState($$1.above($$3)).getValue((Property)STAGE)).intValue() != 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBonemealSuccess(Level $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 177 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void performBonemeal(ServerLevel $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 182 */     int $$4 = getHeightAboveUpToMax((BlockGetter)$$0, $$2);
/* 183 */     int $$5 = getHeightBelowUpToMax((BlockGetter)$$0, $$2);
/* 184 */     int $$6 = $$4 + $$5 + 1;
/*     */     
/* 186 */     int $$7 = 1 + $$1.nextInt(2);
/* 187 */     for (int $$8 = 0; $$8 < $$7; $$8++) {
/* 188 */       BlockPos $$9 = $$2.above($$4);
/* 189 */       BlockState $$10 = $$0.getBlockState($$9);
/* 190 */       if ($$6 >= 16 || ((Integer)$$10.getValue((Property)STAGE)).intValue() == 1 || !$$0.isEmptyBlock($$9.above())) {
/*     */         return;
/*     */       }
/*     */       
/* 194 */       growBamboo($$10, (Level)$$0, $$9, $$1, $$6);
/*     */       
/* 196 */       $$4++;
/* 197 */       $$6++;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public float getDestroyProgress(BlockState $$0, Player $$1, BlockGetter $$2, BlockPos $$3) {
/* 203 */     if ($$1.getMainHandItem().getItem() instanceof net.minecraft.world.item.SwordItem) {
/* 204 */       return 1.0F;
/*     */     }
/*     */     
/* 207 */     return super.getDestroyProgress($$0, $$1, $$2, $$3);
/*     */   }
/*     */   
/*     */   protected void growBamboo(BlockState $$0, Level $$1, BlockPos $$2, RandomSource $$3, int $$4) {
/* 211 */     BlockState $$5 = $$1.getBlockState($$2.below());
/* 212 */     BlockPos $$6 = $$2.below(2);
/* 213 */     BlockState $$7 = $$1.getBlockState($$6);
/*     */     
/* 215 */     BambooLeaves $$8 = BambooLeaves.NONE;
/* 216 */     if ($$4 >= 1) {
/* 217 */       if (!$$5.is(Blocks.BAMBOO) || $$5.getValue((Property)LEAVES) == BambooLeaves.NONE) {
/* 218 */         $$8 = BambooLeaves.SMALL;
/* 219 */       } else if ($$5.is(Blocks.BAMBOO) && $$5.getValue((Property)LEAVES) != BambooLeaves.NONE) {
/* 220 */         $$8 = BambooLeaves.LARGE;
/*     */         
/* 222 */         if ($$7.is(Blocks.BAMBOO)) {
/* 223 */           $$1.setBlock($$2.below(), (BlockState)$$5.setValue((Property)LEAVES, (Comparable)BambooLeaves.SMALL), 3);
/* 224 */           $$1.setBlock($$6, (BlockState)$$7.setValue((Property)LEAVES, (Comparable)BambooLeaves.NONE), 3);
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 229 */     int $$9 = (((Integer)$$0.getValue((Property)AGE)).intValue() == 1 || $$7.is(Blocks.BAMBOO)) ? 1 : 0;
/* 230 */     int $$10 = (($$4 >= 11 && $$3.nextFloat() < 0.25F) || $$4 == 15) ? 1 : 0;
/* 231 */     $$1.setBlock($$2.above(), (BlockState)((BlockState)((BlockState)defaultBlockState().setValue((Property)AGE, Integer.valueOf($$9))).setValue((Property)LEAVES, (Comparable)$$8)).setValue((Property)STAGE, Integer.valueOf($$10)), 3);
/*     */   }
/*     */   
/*     */   protected int getHeightAboveUpToMax(BlockGetter $$0, BlockPos $$1) {
/* 235 */     int $$2 = 0;
/* 236 */     while ($$2 < 16 && $$0.getBlockState($$1.above($$2 + 1)).is(Blocks.BAMBOO)) {
/* 237 */       $$2++;
/*     */     }
/* 239 */     return $$2;
/*     */   }
/*     */   
/*     */   protected int getHeightBelowUpToMax(BlockGetter $$0, BlockPos $$1) {
/* 243 */     int $$2 = 0;
/* 244 */     while ($$2 < 16 && $$0.getBlockState($$1.below($$2 + 1)).is(Blocks.BAMBOO)) {
/* 245 */       $$2++;
/*     */     }
/* 247 */     return $$2;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\BambooStalkBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */