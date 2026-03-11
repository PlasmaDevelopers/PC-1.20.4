/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.Optional;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.FrontAndTop;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.MenuProvider;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.inventory.CraftingContainer;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.item.crafting.CraftingRecipe;
/*     */ import net.minecraft.world.item.crafting.RecipeCache;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*     */ import net.minecraft.world.level.block.entity.CrafterBlockEntity;
/*     */ import net.minecraft.world.level.block.entity.HopperBlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*     */ import net.minecraft.world.level.block.state.properties.EnumProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class CrafterBlock extends BaseEntityBlock {
/*  39 */   public static final MapCodec<CrafterBlock> CODEC = simpleCodec(CrafterBlock::new);
/*  40 */   public static final BooleanProperty CRAFTING = BlockStateProperties.CRAFTING;
/*  41 */   public static final BooleanProperty TRIGGERED = BlockStateProperties.TRIGGERED;
/*  42 */   private static final EnumProperty<FrontAndTop> ORIENTATION = BlockStateProperties.ORIENTATION;
/*     */   private static final int MAX_CRAFTING_TICKS = 6;
/*     */   private static final int CRAFTING_TICK_DELAY = 4;
/*  45 */   private static final RecipeCache RECIPE_CACHE = new RecipeCache(10);
/*     */   
/*     */   public CrafterBlock(BlockBehaviour.Properties $$0) {
/*  48 */     super($$0);
/*  49 */     registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)ORIENTATION, (Comparable)FrontAndTop.NORTH_UP))
/*  50 */         .setValue((Property)TRIGGERED, Boolean.valueOf(false)))
/*  51 */         .setValue((Property)CRAFTING, Boolean.valueOf(false)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected MapCodec<CrafterBlock> codec() {
/*  56 */     return CODEC;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasAnalogOutputSignal(BlockState $$0) {
/*  61 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAnalogOutputSignal(BlockState $$0, Level $$1, BlockPos $$2) {
/*  66 */     BlockEntity $$3 = $$1.getBlockEntity($$2);
/*  67 */     if ($$3 instanceof CrafterBlockEntity) { CrafterBlockEntity $$4 = (CrafterBlockEntity)$$3;
/*  68 */       return $$4.getRedstoneSignal(); }
/*     */     
/*  70 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void neighborChanged(BlockState $$0, Level $$1, BlockPos $$2, Block $$3, BlockPos $$4, boolean $$5) {
/*  75 */     boolean $$6 = $$1.hasNeighborSignal($$2);
/*  76 */     boolean $$7 = ((Boolean)$$0.getValue((Property)TRIGGERED)).booleanValue();
/*  77 */     BlockEntity $$8 = $$1.getBlockEntity($$2);
/*     */     
/*  79 */     if ($$6 && !$$7) {
/*  80 */       $$1.scheduleTick($$2, this, 4);
/*  81 */       $$1.setBlock($$2, (BlockState)$$0.setValue((Property)TRIGGERED, Boolean.valueOf(true)), 2);
/*  82 */       setBlockEntityTriggered($$8, true);
/*  83 */     } else if (!$$6 && $$7) {
/*  84 */       $$1.setBlock($$2, (BlockState)((BlockState)$$0.setValue((Property)TRIGGERED, Boolean.valueOf(false))).setValue((Property)CRAFTING, Boolean.valueOf(false)), 2);
/*  85 */       setBlockEntityTriggered($$8, false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/*  91 */     dispenseFrom($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level $$0, BlockState $$1, BlockEntityType<T> $$2) {
/*  97 */     return $$0.isClientSide ? null : createTickerHelper($$2, BlockEntityType.CRAFTER, CrafterBlockEntity::serverTick);
/*     */   }
/*     */   
/*     */   private void setBlockEntityTriggered(@Nullable BlockEntity $$0, boolean $$1) {
/* 101 */     if ($$0 instanceof CrafterBlockEntity) { CrafterBlockEntity $$2 = (CrafterBlockEntity)$$0;
/* 102 */       $$2.setTriggered($$1); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockEntity newBlockEntity(BlockPos $$0, BlockState $$1) {
/* 108 */     CrafterBlockEntity $$2 = new CrafterBlockEntity($$0, $$1);
/* 109 */     $$2.setTriggered(($$1.hasProperty((Property)TRIGGERED) && ((Boolean)$$1.getValue((Property)TRIGGERED)).booleanValue()));
/* 110 */     return (BlockEntity)$$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 115 */     Direction $$1 = $$0.getNearestLookingDirection().getOpposite();
/* 116 */     switch ($$1) { default: throw new IncompatibleClassChangeError();
/*     */       case DOWN: 
/*     */       case UP: 
/* 119 */       case NORTH: case SOUTH: case WEST: case EAST: break; }  Direction $$2 = Direction.UP;
/*     */ 
/*     */     
/* 122 */     return (BlockState)((BlockState)defaultBlockState()
/* 123 */       .setValue((Property)ORIENTATION, (Comparable)FrontAndTop.fromFrontAndTop($$1, $$2)))
/* 124 */       .setValue((Property)TRIGGERED, Boolean.valueOf($$0.getLevel().hasNeighborSignal($$0.getClickedPos())));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPlacedBy(Level $$0, BlockPos $$1, BlockState $$2, LivingEntity $$3, ItemStack $$4) {
/* 129 */     if ($$4.hasCustomHoverName()) { BlockEntity blockEntity = $$0.getBlockEntity($$1); if (blockEntity instanceof CrafterBlockEntity) { CrafterBlockEntity $$5 = (CrafterBlockEntity)blockEntity;
/* 130 */         $$5.setCustomName($$4.getHoverName()); }
/*     */        }
/* 132 */      if (((Boolean)$$2.getValue((Property)TRIGGERED)).booleanValue()) {
/* 133 */       $$0.scheduleTick($$1, this, 4);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRemove(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/* 139 */     Containers.dropContentsOnDestroy($$0, $$3, $$1, $$2);
/* 140 */     super.onRemove($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
/* 145 */     if ($$1.isClientSide) {
/* 146 */       return InteractionResult.SUCCESS;
/*     */     }
/*     */     
/* 149 */     BlockEntity $$6 = $$1.getBlockEntity($$2);
/* 150 */     if ($$6 instanceof CrafterBlockEntity) {
/* 151 */       $$3.openMenu((MenuProvider)$$6);
/*     */     }
/*     */     
/* 154 */     return InteractionResult.CONSUME;
/*     */   }
/*     */   protected void dispenseFrom(BlockState $$0, ServerLevel $$1, BlockPos $$2) {
/*     */     CrafterBlockEntity $$3;
/* 158 */     BlockEntity blockEntity = $$1.getBlockEntity($$2); if (blockEntity instanceof CrafterBlockEntity) { $$3 = (CrafterBlockEntity)blockEntity; }
/*     */     else
/*     */     { return; }
/*     */     
/* 162 */     Optional<CraftingRecipe> $$5 = getPotentialResults((Level)$$1, (CraftingContainer)$$3);
/*     */     
/* 164 */     if ($$5.isEmpty()) {
/* 165 */       $$1.levelEvent(1050, $$2, 0);
/*     */       
/*     */       return;
/*     */     } 
/* 169 */     $$3.setCraftingTicksRemaining(6);
/* 170 */     $$1.setBlock($$2, (BlockState)$$0.setValue((Property)CRAFTING, Boolean.valueOf(true)), 2);
/*     */     
/* 172 */     CraftingRecipe $$6 = $$5.get();
/*     */ 
/*     */     
/* 175 */     ItemStack $$7 = $$6.assemble((Container)$$3, $$1.registryAccess());
/* 176 */     $$7.onCraftedBySystem((Level)$$1);
/*     */ 
/*     */     
/* 179 */     dispenseItem((Level)$$1, $$2, $$3, $$7, $$0);
/*     */ 
/*     */     
/* 182 */     $$6.getRemainingItems((Container)$$3).forEach($$4 -> dispenseItem((Level)$$0, $$1, $$2, $$4, $$3));
/*     */ 
/*     */     
/* 185 */     $$3.getItems().forEach($$0 -> {
/*     */           if ($$0.isEmpty()) {
/*     */             return;
/*     */           }
/*     */           $$0.shrink(1);
/*     */         });
/* 191 */     $$3.setChanged();
/*     */   }
/*     */   
/*     */   public static Optional<CraftingRecipe> getPotentialResults(Level $$0, CraftingContainer $$1) {
/* 195 */     return RECIPE_CACHE.get($$0, $$1);
/*     */   }
/*     */   
/*     */   private void dispenseItem(Level $$0, BlockPos $$1, CrafterBlockEntity $$2, ItemStack $$3, BlockState $$4) {
/* 199 */     Direction $$5 = ((FrontAndTop)$$4.getValue((Property)ORIENTATION)).front();
/* 200 */     Container $$6 = HopperBlockEntity.getContainerAt($$0, $$1.relative($$5));
/* 201 */     ItemStack $$7 = $$3.copy();
/*     */     
/* 203 */     if ($$6 != null && ($$6 instanceof CrafterBlockEntity || $$3.getCount() > $$6.getMaxStackSize())) {
/*     */       
/* 205 */       while (!$$7.isEmpty()) {
/* 206 */         ItemStack $$8 = $$7.copyWithCount(1);
/*     */         
/* 208 */         ItemStack $$9 = HopperBlockEntity.addItem((Container)$$2, $$6, $$8, $$5.getOpposite());
/*     */         
/* 210 */         if (!$$9.isEmpty()) {
/*     */           break;
/*     */         }
/* 213 */         $$7.shrink(1);
/*     */       } 
/* 215 */     } else if ($$6 != null) {
/*     */       
/* 217 */       while (!$$7.isEmpty()) {
/* 218 */         int $$10 = $$7.getCount();
/* 219 */         $$7 = HopperBlockEntity.addItem((Container)$$2, $$6, $$7, $$5.getOpposite());
/*     */         
/* 221 */         if ($$10 == $$7.getCount()) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 228 */     if (!$$7.isEmpty()) {
/* 229 */       Vec3 $$11 = Vec3.atCenterOf((Vec3i)$$1).relative($$5, 0.7D);
/* 230 */       DefaultDispenseItemBehavior.spawnItem($$0, $$7, 6, $$5, (Position)$$11);
/*     */       
/* 232 */       $$0.levelEvent(1049, $$1, 0);
/* 233 */       $$0.levelEvent(2010, $$1, $$5.get3DDataValue());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public RenderShape getRenderShape(BlockState $$0) {
/* 239 */     return RenderShape.MODEL;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState rotate(BlockState $$0, Rotation $$1) {
/* 244 */     return (BlockState)$$0.setValue((Property)ORIENTATION, (Comparable)$$1.rotation().rotate((FrontAndTop)$$0.getValue((Property)ORIENTATION)));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState mirror(BlockState $$0, Mirror $$1) {
/* 249 */     return (BlockState)$$0.setValue((Property)ORIENTATION, (Comparable)$$1.rotation().rotate((FrontAndTop)$$0.getValue((Property)ORIENTATION)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 254 */     $$0.add(new Property[] { (Property)ORIENTATION, (Property)TRIGGERED, (Property)CRAFTING });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\CrafterBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */