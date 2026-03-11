/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.stats.Stats;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.MenuProvider;
/*     */ import net.minecraft.world.SimpleMenuProvider;
/*     */ import net.minecraft.world.entity.player.Inventory;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*     */ import net.minecraft.world.inventory.ChestMenu;
/*     */ import net.minecraft.world.inventory.PlayerEnderChestContainer;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*     */ import net.minecraft.world.level.block.entity.ChestBlockEntity;
/*     */ import net.minecraft.world.level.block.entity.EnderChestBlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*     */ import net.minecraft.world.level.block.state.properties.DirectionProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class EnderChestBlock extends AbstractChestBlock<EnderChestBlockEntity> implements SimpleWaterloggedBlock {
/*  47 */   public static final MapCodec<EnderChestBlock> CODEC = simpleCodec(EnderChestBlock::new);
/*     */ 
/*     */   
/*     */   public MapCodec<EnderChestBlock> codec() {
/*  51 */     return CODEC;
/*     */   }
/*     */   
/*  54 */   public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
/*  55 */   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
/*  56 */   protected static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 14.0D, 15.0D);
/*  57 */   private static final Component CONTAINER_TITLE = (Component)Component.translatable("container.enderchest");
/*     */   
/*     */   protected EnderChestBlock(BlockBehaviour.Properties $$0) {
/*  60 */     super($$0, () -> BlockEntityType.ENDER_CHEST);
/*  61 */     registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)FACING, (Comparable)Direction.NORTH)).setValue((Property)WATERLOGGED, Boolean.valueOf(false)));
/*     */   }
/*     */ 
/*     */   
/*     */   public DoubleBlockCombiner.NeighborCombineResult<? extends ChestBlockEntity> combine(BlockState $$0, Level $$1, BlockPos $$2, boolean $$3) {
/*  66 */     return DoubleBlockCombiner.Combiner::acceptNone;
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  71 */     return SHAPE;
/*     */   }
/*     */ 
/*     */   
/*     */   public RenderShape getRenderShape(BlockState $$0) {
/*  76 */     return RenderShape.ENTITYBLOCK_ANIMATED;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/*  81 */     FluidState $$1 = $$0.getLevel().getFluidState($$0.getClickedPos());
/*  82 */     return (BlockState)((BlockState)defaultBlockState().setValue((Property)FACING, (Comparable)$$0.getHorizontalDirection().getOpposite())).setValue((Property)WATERLOGGED, Boolean.valueOf(($$1.getType() == Fluids.WATER)));
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
/*  87 */     PlayerEnderChestContainer $$6 = $$3.getEnderChestInventory();
/*  88 */     BlockEntity $$7 = $$1.getBlockEntity($$2);
/*  89 */     if ($$6 == null || !($$7 instanceof EnderChestBlockEntity)) {
/*  90 */       return InteractionResult.sidedSuccess($$1.isClientSide);
/*     */     }
/*     */     
/*  93 */     BlockPos $$8 = $$2.above();
/*  94 */     if ($$1.getBlockState($$8).isRedstoneConductor((BlockGetter)$$1, $$8)) {
/*  95 */       return InteractionResult.sidedSuccess($$1.isClientSide);
/*     */     }
/*     */     
/*  98 */     if ($$1.isClientSide) {
/*  99 */       return InteractionResult.SUCCESS;
/*     */     }
/*     */     
/* 102 */     EnderChestBlockEntity $$9 = (EnderChestBlockEntity)$$7;
/* 103 */     $$6.setActiveChest($$9);
/*     */     
/* 105 */     $$3.openMenu((MenuProvider)new SimpleMenuProvider(($$1, $$2, $$3) -> ChestMenu.threeRows($$1, $$2, (Container)$$0), CONTAINER_TITLE));
/* 106 */     $$3.awardStat(Stats.OPEN_ENDERCHEST);
/* 107 */     PiglinAi.angerNearbyPiglins($$3, true);
/* 108 */     return InteractionResult.CONSUME;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockEntity newBlockEntity(BlockPos $$0, BlockState $$1) {
/* 113 */     return (BlockEntity)new EnderChestBlockEntity($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level $$0, BlockState $$1, BlockEntityType<T> $$2) {
/* 119 */     return $$0.isClientSide ? createTickerHelper($$2, BlockEntityType.ENDER_CHEST, EnderChestBlockEntity::lidAnimateTick) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void animateTick(BlockState $$0, Level $$1, BlockPos $$2, RandomSource $$3) {
/* 124 */     for (int $$4 = 0; $$4 < 3; $$4++) {
/* 125 */       int $$5 = $$3.nextInt(2) * 2 - 1;
/* 126 */       int $$6 = $$3.nextInt(2) * 2 - 1;
/*     */       
/* 128 */       double $$7 = $$2.getX() + 0.5D + 0.25D * $$5;
/* 129 */       double $$8 = ($$2.getY() + $$3.nextFloat());
/* 130 */       double $$9 = $$2.getZ() + 0.5D + 0.25D * $$6;
/* 131 */       double $$10 = ($$3.nextFloat() * $$5);
/* 132 */       double $$11 = ($$3.nextFloat() - 0.5D) * 0.125D;
/* 133 */       double $$12 = ($$3.nextFloat() * $$6);
/*     */       
/* 135 */       $$1.addParticle((ParticleOptions)ParticleTypes.PORTAL, $$7, $$8, $$9, $$10, $$11, $$12);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState rotate(BlockState $$0, Rotation $$1) {
/* 141 */     return (BlockState)$$0.setValue((Property)FACING, (Comparable)$$1.rotate((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState mirror(BlockState $$0, Mirror $$1) {
/* 146 */     return $$0.rotate($$1.getRotation((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 151 */     $$0.add(new Property[] { (Property)FACING, (Property)WATERLOGGED });
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidState getFluidState(BlockState $$0) {
/* 156 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 157 */       return Fluids.WATER.getSource(false);
/*     */     }
/* 159 */     return super.getFluidState($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 164 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 165 */       $$3.scheduleTick($$4, (Fluid)Fluids.WATER, Fluids.WATER.getTickDelay((LevelReader)$$3));
/*     */     }
/* 167 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
/* 172 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/* 177 */     BlockEntity $$4 = $$1.getBlockEntity($$2);
/*     */     
/* 179 */     if ($$4 instanceof EnderChestBlockEntity)
/* 180 */       ((EnderChestBlockEntity)$$4).recheckOpen(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\EnderChestBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */