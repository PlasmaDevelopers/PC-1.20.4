/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.valueproviders.IntProvider;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*     */ import net.minecraft.world.level.block.entity.SculkShriekerBlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class SculkShriekerBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
/*  33 */   public static final MapCodec<SculkShriekerBlock> CODEC = simpleCodec(SculkShriekerBlock::new);
/*     */ 
/*     */   
/*     */   public MapCodec<SculkShriekerBlock> codec() {
/*  37 */     return CODEC;
/*     */   }
/*     */   
/*  40 */   public static final BooleanProperty SHRIEKING = BlockStateProperties.SHRIEKING;
/*  41 */   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
/*  42 */   public static final BooleanProperty CAN_SUMMON = BlockStateProperties.CAN_SUMMON;
/*     */   
/*  44 */   protected static final VoxelShape COLLIDER = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
/*     */   
/*  46 */   public static final double TOP_Y = COLLIDER.max(Direction.Axis.Y);
/*     */   
/*     */   public SculkShriekerBlock(BlockBehaviour.Properties $$0) {
/*  49 */     super($$0);
/*     */     
/*  51 */     registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)SHRIEKING, Boolean.valueOf(false))).setValue((Property)WATERLOGGED, Boolean.valueOf(false))).setValue((Property)CAN_SUMMON, Boolean.valueOf(false)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/*  56 */     $$0.add(new Property[] { (Property)SHRIEKING });
/*  57 */     $$0.add(new Property[] { (Property)WATERLOGGED });
/*  58 */     $$0.add(new Property[] { (Property)CAN_SUMMON });
/*     */   }
/*     */ 
/*     */   
/*     */   public void stepOn(Level $$0, BlockPos $$1, BlockState $$2, Entity $$3) {
/*  63 */     if ($$0 instanceof ServerLevel) { ServerLevel $$4 = (ServerLevel)$$0;
/*  64 */       ServerPlayer $$5 = SculkShriekerBlockEntity.tryGetPlayer($$3);
/*  65 */       if ($$5 != null) {
/*  66 */         $$4.getBlockEntity($$1, BlockEntityType.SCULK_SHRIEKER).ifPresent($$2 -> $$2.tryShriek($$0, $$1));
/*     */       } }
/*     */ 
/*     */     
/*  70 */     super.stepOn($$0, $$1, $$2, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRemove(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/*  75 */     if ($$1 instanceof ServerLevel) { ServerLevel $$5 = (ServerLevel)$$1; if (((Boolean)$$0.getValue((Property)SHRIEKING)).booleanValue() && !$$0.is($$3.getBlock()))
/*     */       {
/*  77 */         $$5.getBlockEntity($$2, BlockEntityType.SCULK_SHRIEKER).ifPresent($$1 -> $$1.tryRespond($$0)); }  }
/*     */     
/*  79 */     super.onRemove($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/*  84 */     if (((Boolean)$$0.getValue((Property)SHRIEKING)).booleanValue()) {
/*  85 */       $$1.setBlock($$2, (BlockState)$$0.setValue((Property)SHRIEKING, Boolean.valueOf(false)), 3);
/*     */       
/*  87 */       $$1.getBlockEntity($$2, BlockEntityType.SCULK_SHRIEKER).ifPresent($$1 -> $$1.tryRespond($$0));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public RenderShape getRenderShape(BlockState $$0) {
/*  93 */     return RenderShape.MODEL;
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getCollisionShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  98 */     return COLLIDER;
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getOcclusionShape(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/* 103 */     return COLLIDER;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean useShapeForLightOcclusion(BlockState $$0) {
/* 108 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockEntity newBlockEntity(BlockPos $$0, BlockState $$1) {
/* 114 */     return (BlockEntity)new SculkShriekerBlockEntity($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 119 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 120 */       $$3.scheduleTick($$4, (Fluid)Fluids.WATER, Fluids.WATER.getTickDelay((LevelReader)$$3));
/*     */     }
/* 122 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 128 */     return (BlockState)defaultBlockState().setValue((Property)WATERLOGGED, Boolean.valueOf(($$0.getLevel().getFluidState($$0.getClickedPos()).getType() == Fluids.WATER)));
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidState getFluidState(BlockState $$0) {
/* 133 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 134 */       return Fluids.WATER.getSource(false);
/*     */     }
/* 136 */     return super.getFluidState($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void spawnAfterBreak(BlockState $$0, ServerLevel $$1, BlockPos $$2, ItemStack $$3, boolean $$4) {
/* 141 */     super.spawnAfterBreak($$0, $$1, $$2, $$3, $$4);
/* 142 */     if ($$4) {
/* 143 */       tryDropExperience($$1, $$2, $$3, (IntProvider)ConstantInt.of(5));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level $$0, BlockState $$1, BlockEntityType<T> $$2) {
/* 150 */     if (!$$0.isClientSide) {
/* 151 */       return BaseEntityBlock.createTickerHelper($$2, BlockEntityType.SCULK_SHRIEKER, ($$0, $$1, $$2, $$3) -> VibrationSystem.Ticker.tick($$0, $$3.getVibrationData(), $$3.getVibrationUser()));
/*     */     }
/*     */     
/* 154 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\SculkShriekerBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */