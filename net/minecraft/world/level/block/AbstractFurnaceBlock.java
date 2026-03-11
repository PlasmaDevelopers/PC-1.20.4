/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.Containers;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*     */ import net.minecraft.world.level.block.state.properties.DirectionProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public abstract class AbstractFurnaceBlock extends BaseEntityBlock {
/*  31 */   public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
/*  32 */   public static final BooleanProperty LIT = BlockStateProperties.LIT;
/*     */   
/*     */   protected AbstractFurnaceBlock(BlockBehaviour.Properties $$0) {
/*  35 */     super($$0);
/*  36 */     registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)FACING, (Comparable)Direction.NORTH)).setValue((Property)LIT, Boolean.valueOf(false)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract MapCodec<? extends AbstractFurnaceBlock> codec();
/*     */ 
/*     */   
/*     */   public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
/*  44 */     if ($$1.isClientSide) {
/*  45 */       return InteractionResult.SUCCESS;
/*     */     }
/*     */     
/*  48 */     openContainer($$1, $$2, $$3);
/*     */     
/*  50 */     return InteractionResult.CONSUME;
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract void openContainer(Level paramLevel, BlockPos paramBlockPos, Player paramPlayer);
/*     */   
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/*  57 */     return (BlockState)defaultBlockState().setValue((Property)FACING, (Comparable)$$0.getHorizontalDirection().getOpposite());
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPlacedBy(Level $$0, BlockPos $$1, BlockState $$2, LivingEntity $$3, ItemStack $$4) {
/*  62 */     if ($$4.hasCustomHoverName()) {
/*  63 */       BlockEntity $$5 = $$0.getBlockEntity($$1);
/*  64 */       if ($$5 instanceof AbstractFurnaceBlockEntity) {
/*  65 */         ((AbstractFurnaceBlockEntity)$$5).setCustomName($$4.getHoverName());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRemove(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/*  72 */     if ($$0.is($$3.getBlock())) {
/*     */       return;
/*     */     }
/*     */     
/*  76 */     BlockEntity $$5 = $$1.getBlockEntity($$2);
/*  77 */     if ($$5 instanceof AbstractFurnaceBlockEntity) {
/*  78 */       if ($$1 instanceof ServerLevel) {
/*  79 */         Containers.dropContents($$1, $$2, (Container)$$5);
/*  80 */         ((AbstractFurnaceBlockEntity)$$5).getRecipesToAwardAndPopExperience((ServerLevel)$$1, Vec3.atCenterOf((Vec3i)$$2));
/*     */       } 
/*  82 */       super.onRemove($$0, $$1, $$2, $$3, $$4);
/*  83 */       $$1.updateNeighbourForOutputSignal($$2, this);
/*     */     } else {
/*  85 */       super.onRemove($$0, $$1, $$2, $$3, $$4);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasAnalogOutputSignal(BlockState $$0) {
/*  91 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAnalogOutputSignal(BlockState $$0, Level $$1, BlockPos $$2) {
/*  96 */     return AbstractContainerMenu.getRedstoneSignalFromBlockEntity($$1.getBlockEntity($$2));
/*     */   }
/*     */ 
/*     */   
/*     */   public RenderShape getRenderShape(BlockState $$0) {
/* 101 */     return RenderShape.MODEL;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState rotate(BlockState $$0, Rotation $$1) {
/* 106 */     return (BlockState)$$0.setValue((Property)FACING, (Comparable)$$1.rotate((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState mirror(BlockState $$0, Mirror $$1) {
/* 111 */     return $$0.rotate($$1.getRotation((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 116 */     $$0.add(new Property[] { (Property)FACING, (Property)LIT });
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected static <T extends BlockEntity> BlockEntityTicker<T> createFurnaceTicker(Level $$0, BlockEntityType<T> $$1, BlockEntityType<? extends AbstractFurnaceBlockEntity> $$2) {
/* 121 */     return $$0.isClientSide ? null : createTickerHelper($$1, $$2, AbstractFurnaceBlockEntity::serverTick);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\AbstractFurnaceBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */