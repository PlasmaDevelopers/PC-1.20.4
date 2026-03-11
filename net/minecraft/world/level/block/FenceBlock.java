/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class FenceBlock extends CrossCollisionBlock {
/*  27 */   public static final MapCodec<FenceBlock> CODEC = simpleCodec(FenceBlock::new);
/*     */   private final VoxelShape[] occlusionByIndex;
/*     */   
/*     */   public MapCodec<FenceBlock> codec() {
/*  31 */     return CODEC;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FenceBlock(BlockBehaviour.Properties $$0) {
/*  37 */     super(2.0F, 2.0F, 16.0F, 16.0F, 24.0F, $$0);
/*  38 */     registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)NORTH, Boolean.valueOf(false))).setValue((Property)EAST, Boolean.valueOf(false))).setValue((Property)SOUTH, Boolean.valueOf(false))).setValue((Property)WEST, Boolean.valueOf(false))).setValue((Property)WATERLOGGED, Boolean.valueOf(false)));
/*     */     
/*  40 */     this.occlusionByIndex = makeShapes(2.0F, 1.0F, 16.0F, 6.0F, 15.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getOcclusionShape(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/*  45 */     return this.occlusionByIndex[getAABBIndex($$0)];
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getVisualShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  50 */     return getShape($$0, $$1, $$2, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
/*  55 */     return false;
/*     */   }
/*     */   
/*     */   public boolean connectsTo(BlockState $$0, boolean $$1, Direction $$2) {
/*  59 */     Block $$3 = $$0.getBlock();
/*     */     
/*  61 */     boolean $$4 = isSameFence($$0);
/*  62 */     boolean $$5 = ($$3 instanceof FenceGateBlock && FenceGateBlock.connectsToDirection($$0, $$2));
/*  63 */     return ((!isExceptionForConnection($$0) && $$1) || $$4 || $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isSameFence(BlockState $$0) {
/*  68 */     return ($$0.is(BlockTags.FENCES) && $$0.is(BlockTags.WOODEN_FENCES) == defaultBlockState().is(BlockTags.WOODEN_FENCES));
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
/*  73 */     if ($$1.isClientSide) {
/*  74 */       ItemStack $$6 = $$3.getItemInHand($$4);
/*  75 */       if ($$6.is(Items.LEAD)) {
/*  76 */         return InteractionResult.SUCCESS;
/*     */       }
/*  78 */       return InteractionResult.PASS;
/*     */     } 
/*     */ 
/*     */     
/*  82 */     return LeadItem.bindPlayerMobs($$3, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/*  87 */     Level level = $$0.getLevel();
/*  88 */     BlockPos $$2 = $$0.getClickedPos();
/*  89 */     FluidState $$3 = $$0.getLevel().getFluidState($$0.getClickedPos());
/*     */ 
/*     */     
/*  92 */     BlockPos $$4 = $$2.north();
/*  93 */     BlockPos $$5 = $$2.east();
/*  94 */     BlockPos $$6 = $$2.south();
/*  95 */     BlockPos $$7 = $$2.west();
/*     */     
/*  97 */     BlockState $$8 = level.getBlockState($$4);
/*  98 */     BlockState $$9 = level.getBlockState($$5);
/*  99 */     BlockState $$10 = level.getBlockState($$6);
/* 100 */     BlockState $$11 = level.getBlockState($$7);
/*     */     
/* 102 */     return (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)super.getStateForPlacement($$0)
/* 103 */       .setValue((Property)NORTH, Boolean.valueOf(connectsTo($$8, $$8.isFaceSturdy((BlockGetter)level, $$4, Direction.SOUTH), Direction.SOUTH))))
/* 104 */       .setValue((Property)EAST, Boolean.valueOf(connectsTo($$9, $$9.isFaceSturdy((BlockGetter)level, $$5, Direction.WEST), Direction.WEST))))
/* 105 */       .setValue((Property)SOUTH, Boolean.valueOf(connectsTo($$10, $$10.isFaceSturdy((BlockGetter)level, $$6, Direction.NORTH), Direction.NORTH))))
/* 106 */       .setValue((Property)WEST, Boolean.valueOf(connectsTo($$11, $$11.isFaceSturdy((BlockGetter)level, $$7, Direction.EAST), Direction.EAST))))
/* 107 */       .setValue((Property)WATERLOGGED, Boolean.valueOf(($$3.getType() == Fluids.WATER)));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 112 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 113 */       $$3.scheduleTick($$4, (Fluid)Fluids.WATER, Fluids.WATER.getTickDelay((LevelReader)$$3));
/*     */     }
/* 115 */     if ($$1.getAxis().getPlane() == Direction.Plane.HORIZONTAL) {
/* 116 */       return (BlockState)$$0.setValue((Property)PROPERTY_BY_DIRECTION.get($$1), Boolean.valueOf(connectsTo($$2, $$2.isFaceSturdy((BlockGetter)$$3, $$5, $$1.getOpposite()), $$1.getOpposite())));
/*     */     }
/* 118 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 123 */     $$0.add(new Property[] { (Property)NORTH, (Property)EAST, (Property)WEST, (Property)SOUTH, (Property)WATERLOGGED });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\FenceBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */