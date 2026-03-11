/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.List;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.stats.Stats;
/*     */ import net.minecraft.tags.ItemTags;
/*     */ import net.minecraft.world.Containers;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.entity.projectile.Projectile;
/*     */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*     */ import net.minecraft.world.item.BlockItem;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.TooltipFlag;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ import net.minecraft.world.level.pathfinder.PathComputationType;
/*     */ import net.minecraft.world.level.storage.loot.LootParams;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class DecoratedPotBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
/*  56 */   public static final MapCodec<DecoratedPotBlock> CODEC = simpleCodec(DecoratedPotBlock::new);
/*     */ 
/*     */   
/*     */   public MapCodec<DecoratedPotBlock> codec() {
/*  60 */     return CODEC;
/*     */   }
/*     */   
/*  63 */   public static final ResourceLocation SHERDS_DYNAMIC_DROP_ID = new ResourceLocation("sherds");
/*  64 */   private static final VoxelShape BOUNDING_BOX = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);
/*  65 */   private static final DirectionProperty HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;
/*  66 */   public static final BooleanProperty CRACKED = BlockStateProperties.CRACKED;
/*  67 */   private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
/*     */   
/*     */   protected DecoratedPotBlock(BlockBehaviour.Properties $$0) {
/*  70 */     super($$0);
/*  71 */     registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any())
/*  72 */         .setValue((Property)HORIZONTAL_FACING, (Comparable)Direction.NORTH))
/*  73 */         .setValue((Property)WATERLOGGED, Boolean.valueOf(false)))
/*  74 */         .setValue((Property)CRACKED, Boolean.valueOf(false)));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/*  79 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/*  80 */       $$3.scheduleTick($$4, (Fluid)Fluids.WATER, Fluids.WATER.getTickDelay((LevelReader)$$3));
/*     */     }
/*  82 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/*  87 */     FluidState $$1 = $$0.getLevel().getFluidState($$0.getClickedPos());
/*  88 */     return (BlockState)((BlockState)((BlockState)defaultBlockState()
/*  89 */       .setValue((Property)HORIZONTAL_FACING, (Comparable)$$0.getHorizontalDirection()))
/*  90 */       .setValue((Property)WATERLOGGED, Boolean.valueOf(($$1.getType() == Fluids.WATER))))
/*  91 */       .setValue((Property)CRACKED, Boolean.valueOf(false));
/*     */   }
/*     */   
/*     */   public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
/*     */     DecoratedPotBlockEntity $$6;
/*  96 */     BlockEntity blockEntity = $$1.getBlockEntity($$2); if (blockEntity instanceof DecoratedPotBlockEntity) { $$6 = (DecoratedPotBlockEntity)blockEntity; }
/*  97 */     else { return InteractionResult.PASS; }
/*     */ 
/*     */     
/* 100 */     if ($$1.isClientSide) {
/* 101 */       return InteractionResult.CONSUME;
/*     */     }
/*     */     
/* 104 */     ItemStack $$8 = $$3.getItemInHand($$4);
/* 105 */     ItemStack $$9 = $$6.getTheItem();
/* 106 */     if (!$$8.isEmpty() && ($$9
/* 107 */       .isEmpty() || (ItemStack.isSameItemSameTags($$9, $$8) && $$9.getCount() < $$9.getMaxStackSize()))) {
/*     */       float $$12;
/* 109 */       $$6.wobble(DecoratedPotBlockEntity.WobbleStyle.POSITIVE);
/* 110 */       $$3.awardStat(Stats.ITEM_USED.get($$8.getItem()));
/* 111 */       ItemStack $$10 = $$3.isCreative() ? $$8.copyWithCount(1) : $$8.split(1);
/*     */       
/* 113 */       if ($$6.isEmpty()) {
/* 114 */         $$6.setTheItem($$10);
/* 115 */         float $$11 = $$10.getCount() / $$10.getMaxStackSize();
/*     */       } else {
/* 117 */         $$9.grow(1);
/* 118 */         $$12 = $$9.getCount() / $$9.getMaxStackSize();
/*     */       } 
/*     */       
/* 121 */       $$1.playSound(null, $$2, SoundEvents.DECORATED_POT_INSERT, SoundSource.BLOCKS, 1.0F, 0.7F + 0.5F * $$12);
/* 122 */       if ($$1 instanceof ServerLevel) { ServerLevel $$13 = (ServerLevel)$$1;
/* 123 */         $$13.sendParticles((ParticleOptions)ParticleTypes.DUST_PLUME, $$2.getX() + 0.5D, $$2.getY() + 1.2D, $$2.getZ() + 0.5D, 7, 0.0D, 0.0D, 0.0D, 0.0D); }
/*     */       
/* 125 */       $$6.setChanged();
/*     */     } else {
/* 127 */       $$1.playSound(null, $$2, SoundEvents.DECORATED_POT_INSERT_FAIL, SoundSource.BLOCKS, 1.0F, 1.0F);
/* 128 */       $$6.wobble(DecoratedPotBlockEntity.WobbleStyle.NEGATIVE);
/*     */     } 
/* 130 */     $$1.gameEvent((Entity)$$3, GameEvent.BLOCK_CHANGE, $$2);
/* 131 */     return InteractionResult.SUCCESS;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPlacedBy(Level $$0, BlockPos $$1, BlockState $$2, @Nullable LivingEntity $$3, ItemStack $$4) {
/* 136 */     if ($$0.isClientSide) {
/* 137 */       $$0.getBlockEntity($$1, BlockEntityType.DECORATED_POT).ifPresent($$1 -> $$1.setFromItem($$0));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
/* 143 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 148 */     return BOUNDING_BOX;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 153 */     $$0.add(new Property[] { (Property)HORIZONTAL_FACING, (Property)WATERLOGGED, (Property)CRACKED });
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockEntity newBlockEntity(BlockPos $$0, BlockState $$1) {
/* 159 */     return (BlockEntity)new DecoratedPotBlockEntity($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRemove(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/* 164 */     Containers.dropContentsOnDestroy($$0, $$3, $$1, $$2);
/* 165 */     super.onRemove($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ItemStack> getDrops(BlockState $$0, LootParams.Builder $$1) {
/* 170 */     BlockEntity $$2 = (BlockEntity)$$1.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
/*     */     
/* 172 */     if ($$2 instanceof DecoratedPotBlockEntity) { DecoratedPotBlockEntity $$3 = (DecoratedPotBlockEntity)$$2;
/* 173 */       $$1.withDynamicDrop(SHERDS_DYNAMIC_DROP_ID, $$1 -> $$0.getDecorations().sorted().map(Item::getDefaultInstance).forEach($$1)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 180 */     return super.getDrops($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState playerWillDestroy(Level $$0, BlockPos $$1, BlockState $$2, Player $$3) {
/* 185 */     ItemStack $$4 = $$3.getMainHandItem();
/* 186 */     BlockState $$5 = $$2;
/* 187 */     if ($$4.is(ItemTags.BREAKS_DECORATED_POTS) && !EnchantmentHelper.hasSilkTouch($$4)) {
/* 188 */       $$5 = (BlockState)$$2.setValue((Property)CRACKED, Boolean.valueOf(true));
/* 189 */       $$0.setBlock($$1, $$5, 4);
/*     */     } 
/* 191 */     return super.playerWillDestroy($$0, $$1, $$5, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidState getFluidState(BlockState $$0) {
/* 196 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 197 */       return Fluids.WATER.getSource(false);
/*     */     }
/* 199 */     return super.getFluidState($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public SoundType getSoundType(BlockState $$0) {
/* 204 */     if (((Boolean)$$0.getValue((Property)CRACKED)).booleanValue()) {
/* 205 */       return SoundType.DECORATED_POT_CRACKED;
/*     */     }
/* 207 */     return SoundType.DECORATED_POT;
/*     */   }
/*     */ 
/*     */   
/*     */   public void appendHoverText(ItemStack $$0, @Nullable BlockGetter $$1, List<Component> $$2, TooltipFlag $$3) {
/* 212 */     super.appendHoverText($$0, $$1, $$2, $$3);
/*     */     
/* 214 */     DecoratedPotBlockEntity.Decorations $$4 = DecoratedPotBlockEntity.Decorations.load(BlockItem.getBlockEntityData($$0));
/* 215 */     if ($$4.equals(DecoratedPotBlockEntity.Decorations.EMPTY)) {
/*     */       return;
/*     */     }
/*     */     
/* 219 */     $$2.add(CommonComponents.EMPTY);
/* 220 */     Stream.<Item>of(new Item[] { $$4.front(), $$4.left(), $$4.right(), $$4.back()
/* 221 */         }).forEach($$1 -> $$0.add((new ItemStack((ItemLike)$$1, 1)).getHoverName().plainCopy().withStyle(ChatFormatting.GRAY)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onProjectileHit(Level $$0, BlockState $$1, BlockHitResult $$2, Projectile $$3) {
/* 228 */     BlockPos $$4 = $$2.getBlockPos();
/* 229 */     if (!$$0.isClientSide && $$3.mayInteract($$0, $$4) && $$3.mayBreak($$0)) {
/* 230 */       $$0.setBlock($$4, (BlockState)$$1.setValue((Property)CRACKED, Boolean.valueOf(true)), 4);
/* 231 */       $$0.destroyBlock($$4, true, (Entity)$$3);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getCloneItemStack(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/* 237 */     BlockEntity blockEntity = $$0.getBlockEntity($$1); if (blockEntity instanceof DecoratedPotBlockEntity) { DecoratedPotBlockEntity $$3 = (DecoratedPotBlockEntity)blockEntity;
/* 238 */       return $$3.getPotAsItem(); }
/*     */ 
/*     */     
/* 241 */     return super.getCloneItemStack($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasAnalogOutputSignal(BlockState $$0) {
/* 246 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAnalogOutputSignal(BlockState $$0, Level $$1, BlockPos $$2) {
/* 251 */     return AbstractContainerMenu.getRedstoneSignalFromBlockEntity($$1.getBlockEntity($$2));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\DecoratedPotBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */