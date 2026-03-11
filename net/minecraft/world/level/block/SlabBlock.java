/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.tags.FluidTags;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.EnumProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.block.state.properties.SlabType;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ import net.minecraft.world.level.pathfinder.PathComputationType;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.Shapes;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class SlabBlock extends Block implements SimpleWaterloggedBlock {
/*  29 */   public static final MapCodec<SlabBlock> CODEC = simpleCodec(SlabBlock::new);
/*     */ 
/*     */   
/*     */   public MapCodec<? extends SlabBlock> codec() {
/*  33 */     return CODEC;
/*     */   }
/*     */   
/*  36 */   public static final EnumProperty<SlabType> TYPE = BlockStateProperties.SLAB_TYPE;
/*  37 */   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
/*     */   
/*  39 */   protected static final VoxelShape BOTTOM_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
/*  40 */   protected static final VoxelShape TOP_AABB = Block.box(0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D);
/*     */   
/*     */   public SlabBlock(BlockBehaviour.Properties $$0) {
/*  43 */     super($$0);
/*     */     
/*  45 */     registerDefaultState((BlockState)((BlockState)defaultBlockState().setValue((Property)TYPE, (Comparable)SlabType.BOTTOM)).setValue((Property)WATERLOGGED, Boolean.valueOf(false)));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean useShapeForLightOcclusion(BlockState $$0) {
/*  50 */     return ($$0.getValue((Property)TYPE) != SlabType.DOUBLE);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/*  55 */     $$0.add(new Property[] { (Property)TYPE, (Property)WATERLOGGED });
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  60 */     SlabType $$4 = (SlabType)$$0.getValue((Property)TYPE);
/*  61 */     switch ($$4) {
/*     */       case LAND:
/*  63 */         return Shapes.block();
/*     */       case WATER:
/*  65 */         return TOP_AABB;
/*     */     } 
/*  67 */     return BOTTOM_AABB;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/*  74 */     BlockPos $$1 = $$0.getClickedPos();
/*  75 */     BlockState $$2 = $$0.getLevel().getBlockState($$1);
/*  76 */     if ($$2.is(this)) {
/*  77 */       return (BlockState)((BlockState)$$2.setValue((Property)TYPE, (Comparable)SlabType.DOUBLE)).setValue((Property)WATERLOGGED, Boolean.valueOf(false));
/*     */     }
/*     */     
/*  80 */     FluidState $$3 = $$0.getLevel().getFluidState($$1);
/*  81 */     BlockState $$4 = (BlockState)((BlockState)defaultBlockState().setValue((Property)TYPE, (Comparable)SlabType.BOTTOM)).setValue((Property)WATERLOGGED, Boolean.valueOf(($$3.getType() == Fluids.WATER)));
/*     */     
/*  83 */     Direction $$5 = $$0.getClickedFace();
/*  84 */     if ($$5 == Direction.DOWN || ($$5 != Direction.UP && ($$0.getClickLocation()).y - $$1.getY() > 0.5D)) {
/*  85 */       return (BlockState)$$4.setValue((Property)TYPE, (Comparable)SlabType.TOP);
/*     */     }
/*  87 */     return $$4;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBeReplaced(BlockState $$0, BlockPlaceContext $$1) {
/*  92 */     ItemStack $$2 = $$1.getItemInHand();
/*     */     
/*  94 */     SlabType $$3 = (SlabType)$$0.getValue((Property)TYPE);
/*  95 */     if ($$3 == SlabType.DOUBLE || !$$2.is(asItem())) {
/*  96 */       return false;
/*     */     }
/*     */     
/*  99 */     if ($$1.replacingClickedOnBlock()) {
/* 100 */       boolean $$4 = (($$1.getClickLocation()).y - $$1.getClickedPos().getY() > 0.5D);
/* 101 */       Direction $$5 = $$1.getClickedFace();
/* 102 */       if ($$3 == SlabType.BOTTOM) {
/* 103 */         return ($$5 == Direction.UP || ($$4 && $$5.getAxis().isHorizontal()));
/*     */       }
/* 105 */       return ($$5 == Direction.DOWN || (!$$4 && $$5.getAxis().isHorizontal()));
/*     */     } 
/*     */     
/* 108 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidState getFluidState(BlockState $$0) {
/* 113 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 114 */       return Fluids.WATER.getSource(false);
/*     */     }
/* 116 */     return super.getFluidState($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean placeLiquid(LevelAccessor $$0, BlockPos $$1, BlockState $$2, FluidState $$3) {
/* 121 */     if ($$2.getValue((Property)TYPE) != SlabType.DOUBLE) {
/* 122 */       return super.placeLiquid($$0, $$1, $$2, $$3);
/*     */     }
/* 124 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceLiquid(@Nullable Player $$0, BlockGetter $$1, BlockPos $$2, BlockState $$3, Fluid $$4) {
/* 129 */     if ($$3.getValue((Property)TYPE) != SlabType.DOUBLE) {
/* 130 */       return super.canPlaceLiquid($$0, $$1, $$2, $$3, $$4);
/*     */     }
/* 132 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 137 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 138 */       $$3.scheduleTick($$4, (Fluid)Fluids.WATER, Fluids.WATER.getTickDelay((LevelReader)$$3));
/*     */     }
/* 140 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
/* 145 */     switch ($$3) {
/*     */       case LAND:
/* 147 */         return false;
/*     */       case WATER:
/* 149 */         return $$1.getFluidState($$2).is(FluidTags.WATER);
/*     */       case AIR:
/* 151 */         return false;
/*     */     } 
/* 153 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\SlabBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */