/*     */ package net.minecraft.world.item;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.advancements.CriteriaTriggers;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.item.ItemEntity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.flag.FeatureFlagSet;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.item.context.UseOnContext;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.SoundType;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ 
/*     */ public class BlockItem
/*     */   extends Item
/*     */ {
/*     */   public static final String BLOCK_ENTITY_TAG = "BlockEntityTag";
/*     */   
/*     */   public BlockItem(Block $$0, Item.Properties $$1) {
/*  44 */     super($$1);
/*  45 */     this.block = $$0;
/*     */   }
/*     */   public static final String BLOCK_STATE_TAG = "BlockStateTag"; @Deprecated
/*     */   private final Block block;
/*     */   public InteractionResult useOn(UseOnContext $$0) {
/*  50 */     InteractionResult $$1 = place(new BlockPlaceContext($$0));
/*     */     
/*  52 */     if (!$$1.consumesAction() && 
/*  53 */       isEdible()) {
/*  54 */       InteractionResult $$2 = use($$0.getLevel(), $$0.getPlayer(), $$0.getHand()).getResult();
/*  55 */       return ($$2 == InteractionResult.CONSUME) ? InteractionResult.CONSUME_PARTIAL : $$2;
/*     */     } 
/*     */     
/*  58 */     return $$1;
/*     */   }
/*     */   
/*     */   public InteractionResult place(BlockPlaceContext $$0) {
/*  62 */     if (!getBlock().isEnabled($$0.getLevel().enabledFeatures())) {
/*  63 */       return InteractionResult.FAIL;
/*     */     }
/*     */     
/*  66 */     if (!$$0.canPlace()) {
/*  67 */       return InteractionResult.FAIL;
/*     */     }
/*     */     
/*  70 */     BlockPlaceContext $$1 = updatePlacementContext($$0);
/*  71 */     if ($$1 == null) {
/*  72 */       return InteractionResult.FAIL;
/*     */     }
/*     */     
/*  75 */     BlockState $$2 = getPlacementState($$1);
/*  76 */     if ($$2 == null) {
/*  77 */       return InteractionResult.FAIL;
/*     */     }
/*     */     
/*  80 */     if (!placeBlock($$1, $$2)) {
/*  81 */       return InteractionResult.FAIL;
/*     */     }
/*     */     
/*  84 */     BlockPos $$3 = $$1.getClickedPos();
/*  85 */     Level $$4 = $$1.getLevel();
/*  86 */     Player $$5 = $$1.getPlayer();
/*  87 */     ItemStack $$6 = $$1.getItemInHand();
/*     */ 
/*     */     
/*  90 */     BlockState $$7 = $$4.getBlockState($$3);
/*  91 */     if ($$7.is($$2.getBlock())) {
/*  92 */       $$7 = updateBlockStateFromTag($$3, $$4, $$6, $$7);
/*  93 */       updateCustomBlockEntityTag($$3, $$4, $$5, $$6, $$7);
/*  94 */       $$7.getBlock().setPlacedBy($$4, $$3, $$7, (LivingEntity)$$5, $$6);
/*  95 */       if ($$5 instanceof ServerPlayer) {
/*  96 */         CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)$$5, $$3, $$6);
/*     */       }
/*     */     } 
/*  99 */     SoundType $$8 = $$7.getSoundType();
/* 100 */     $$4.playSound($$5, $$3, getPlaceSound($$7), SoundSource.BLOCKS, ($$8.getVolume() + 1.0F) / 2.0F, $$8.getPitch() * 0.8F);
/* 101 */     $$4.gameEvent(GameEvent.BLOCK_PLACE, $$3, GameEvent.Context.of((Entity)$$5, $$7));
/* 102 */     if ($$5 == null || !($$5.getAbilities()).instabuild) {
/* 103 */       $$6.shrink(1);
/*     */     }
/* 105 */     return InteractionResult.sidedSuccess($$4.isClientSide);
/*     */   }
/*     */   
/*     */   protected SoundEvent getPlaceSound(BlockState $$0) {
/* 109 */     return $$0.getSoundType().getPlaceSound();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public BlockPlaceContext updatePlacementContext(BlockPlaceContext $$0) {
/* 114 */     return $$0;
/*     */   }
/*     */   
/*     */   protected boolean updateCustomBlockEntityTag(BlockPos $$0, Level $$1, @Nullable Player $$2, ItemStack $$3, BlockState $$4) {
/* 118 */     return updateCustomBlockEntityTag($$1, $$2, $$0, $$3);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected BlockState getPlacementState(BlockPlaceContext $$0) {
/* 123 */     BlockState $$1 = getBlock().getStateForPlacement($$0);
/* 124 */     return ($$1 != null && canPlace($$0, $$1)) ? $$1 : null;
/*     */   }
/*     */   
/*     */   private BlockState updateBlockStateFromTag(BlockPos $$0, Level $$1, ItemStack $$2, BlockState $$3) {
/* 128 */     BlockState $$4 = $$3;
/* 129 */     CompoundTag $$5 = $$2.getTag();
/* 130 */     if ($$5 != null) {
/* 131 */       CompoundTag $$6 = $$5.getCompound("BlockStateTag");
/* 132 */       StateDefinition<Block, BlockState> $$7 = $$4.getBlock().getStateDefinition();
/* 133 */       for (String $$8 : $$6.getAllKeys()) {
/* 134 */         Property<?> $$9 = $$7.getProperty($$8);
/* 135 */         if ($$9 != null) {
/* 136 */           String $$10 = $$6.get($$8).getAsString();
/* 137 */           $$4 = updateState($$4, $$9, $$10);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 142 */     if ($$4 != $$3) {
/* 143 */       $$1.setBlock($$0, $$4, 2);
/*     */     }
/* 145 */     return $$4;
/*     */   }
/*     */   
/*     */   private static <T extends Comparable<T>> BlockState updateState(BlockState $$0, Property<T> $$1, String $$2) {
/* 149 */     return $$1.getValue($$2).map($$2 -> (BlockState)$$0.setValue($$1, $$2)).orElse($$0);
/*     */   }
/*     */   
/*     */   protected boolean canPlace(BlockPlaceContext $$0, BlockState $$1) {
/* 153 */     Player $$2 = $$0.getPlayer();
/* 154 */     CollisionContext $$3 = ($$2 == null) ? CollisionContext.empty() : CollisionContext.of((Entity)$$2);
/* 155 */     return ((!mustSurvive() || $$1.canSurvive((LevelReader)$$0.getLevel(), $$0.getClickedPos())) && $$0.getLevel().isUnobstructed($$1, $$0.getClickedPos(), $$3));
/*     */   }
/*     */   
/*     */   protected boolean mustSurvive() {
/* 159 */     return true;
/*     */   }
/*     */   
/*     */   protected boolean placeBlock(BlockPlaceContext $$0, BlockState $$1) {
/* 163 */     return $$0.getLevel().setBlock($$0.getClickedPos(), $$1, 11);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean updateCustomBlockEntityTag(Level $$0, @Nullable Player $$1, BlockPos $$2, ItemStack $$3) {
/* 168 */     MinecraftServer $$4 = $$0.getServer();
/* 169 */     if ($$4 == null) {
/* 170 */       return false;
/*     */     }
/*     */     
/* 173 */     CompoundTag $$5 = getBlockEntityData($$3);
/* 174 */     if ($$5 != null) {
/* 175 */       BlockEntity $$6 = $$0.getBlockEntity($$2);
/*     */       
/* 177 */       if ($$6 != null) {
/* 178 */         if (!$$0.isClientSide && $$6.onlyOpCanSetNbt() && ($$1 == null || !$$1.canUseGameMasterBlocks())) {
/* 179 */           return false;
/*     */         }
/* 181 */         CompoundTag $$7 = $$6.saveWithoutMetadata();
/* 182 */         CompoundTag $$8 = $$7.copy();
/*     */         
/* 184 */         $$7.merge($$5);
/* 185 */         if (!$$7.equals($$8)) {
/* 186 */           $$6.load($$7);
/* 187 */           $$6.setChanged();
/* 188 */           return true;
/*     */         } 
/*     */       } 
/*     */     } 
/* 192 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescriptionId() {
/* 197 */     return getBlock().getDescriptionId();
/*     */   }
/*     */ 
/*     */   
/*     */   public void appendHoverText(ItemStack $$0, @Nullable Level $$1, List<Component> $$2, TooltipFlag $$3) {
/* 202 */     super.appendHoverText($$0, $$1, $$2, $$3);
/* 203 */     getBlock().appendHoverText($$0, (BlockGetter)$$1, $$2, $$3);
/*     */   }
/*     */   
/*     */   public Block getBlock() {
/* 207 */     return this.block;
/*     */   }
/*     */   
/*     */   public void registerBlocks(Map<Block, Item> $$0, Item $$1) {
/* 211 */     $$0.put(getBlock(), $$1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canFitInsideContainerItems() {
/* 217 */     return !(this.block instanceof net.minecraft.world.level.block.ShulkerBoxBlock);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDestroyed(ItemEntity $$0) {
/* 222 */     if (this.block instanceof net.minecraft.world.level.block.ShulkerBoxBlock) {
/* 223 */       ItemStack $$1 = $$0.getItem();
/* 224 */       CompoundTag $$2 = getBlockEntityData($$1);
/* 225 */       if ($$2 != null && $$2.contains("Items", 9)) {
/* 226 */         ListTag $$3 = $$2.getList("Items", 10);
/* 227 */         Objects.requireNonNull(CompoundTag.class); ItemUtils.onContainerDestroyed($$0, $$3.stream().map(CompoundTag.class::cast).map(ItemStack::of));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static CompoundTag getBlockEntityData(ItemStack $$0) {
/* 234 */     return $$0.getTagElement("BlockEntityTag");
/*     */   }
/*     */   
/*     */   public static void setBlockEntityData(ItemStack $$0, BlockEntityType<?> $$1, CompoundTag $$2) {
/* 238 */     if ($$2.isEmpty()) {
/* 239 */       $$0.removeTagKey("BlockEntityTag");
/*     */     } else {
/* 241 */       BlockEntity.addEntityType($$2, $$1);
/* 242 */       $$0.addTagElement("BlockEntityTag", (Tag)$$2);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public FeatureFlagSet requiredFeatures() {
/* 248 */     return getBlock().requiredFeatures();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\BlockItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */