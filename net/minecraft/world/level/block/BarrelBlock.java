/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.stats.Stats;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.Containers;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.MenuProvider;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.monster.piglin.PiglinAi;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.entity.BarrelBlockEntity;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*     */ import net.minecraft.world.level.block.state.properties.DirectionProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ 
/*     */ public class BarrelBlock extends BaseEntityBlock {
/*  32 */   public static final MapCodec<BarrelBlock> CODEC = simpleCodec(BarrelBlock::new);
/*     */ 
/*     */   
/*     */   public MapCodec<BarrelBlock> codec() {
/*  36 */     return CODEC;
/*     */   }
/*     */   
/*  39 */   public static final DirectionProperty FACING = BlockStateProperties.FACING;
/*  40 */   public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
/*     */   
/*     */   public BarrelBlock(BlockBehaviour.Properties $$0) {
/*  43 */     super($$0);
/*  44 */     registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)FACING, (Comparable)Direction.NORTH)).setValue((Property)OPEN, Boolean.valueOf(false)));
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
/*  49 */     if ($$1.isClientSide) {
/*  50 */       return InteractionResult.SUCCESS;
/*     */     }
/*     */     
/*  53 */     BlockEntity $$6 = $$1.getBlockEntity($$2);
/*  54 */     if ($$6 instanceof BarrelBlockEntity) {
/*  55 */       $$3.openMenu((MenuProvider)$$6);
/*  56 */       $$3.awardStat(Stats.OPEN_BARREL);
/*  57 */       PiglinAi.angerNearbyPiglins($$3, true);
/*     */     } 
/*     */     
/*  60 */     return InteractionResult.CONSUME;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRemove(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/*  65 */     Containers.dropContentsOnDestroy($$0, $$3, $$1, $$2);
/*  66 */     super.onRemove($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/*  71 */     BlockEntity $$4 = $$1.getBlockEntity($$2);
/*     */     
/*  73 */     if ($$4 instanceof BarrelBlockEntity) {
/*  74 */       ((BarrelBlockEntity)$$4).recheckOpen();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockEntity newBlockEntity(BlockPos $$0, BlockState $$1) {
/*  81 */     return (BlockEntity)new BarrelBlockEntity($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public RenderShape getRenderShape(BlockState $$0) {
/*  86 */     return RenderShape.MODEL;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPlacedBy(Level $$0, BlockPos $$1, BlockState $$2, @Nullable LivingEntity $$3, ItemStack $$4) {
/*  91 */     if ($$4.hasCustomHoverName()) {
/*  92 */       BlockEntity $$5 = $$0.getBlockEntity($$1);
/*  93 */       if ($$5 instanceof BarrelBlockEntity) {
/*  94 */         ((BarrelBlockEntity)$$5).setCustomName($$4.getHoverName());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasAnalogOutputSignal(BlockState $$0) {
/* 101 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAnalogOutputSignal(BlockState $$0, Level $$1, BlockPos $$2) {
/* 106 */     return AbstractContainerMenu.getRedstoneSignalFromBlockEntity($$1.getBlockEntity($$2));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState rotate(BlockState $$0, Rotation $$1) {
/* 111 */     return (BlockState)$$0.setValue((Property)FACING, (Comparable)$$1.rotate((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState mirror(BlockState $$0, Mirror $$1) {
/* 116 */     return $$0.rotate($$1.getRotation((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 121 */     $$0.add(new Property[] { (Property)FACING, (Property)OPEN });
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 126 */     return (BlockState)defaultBlockState().setValue((Property)FACING, (Comparable)$$0.getNearestLookingDirection().getOpposite());
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\BarrelBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */