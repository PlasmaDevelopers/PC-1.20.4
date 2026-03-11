/*     */ package net.minecraft.world.level.block.entity;
/*     */ 
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Predicate;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.NonNullList;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.tags.ItemTags;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.ContainerHelper;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.ChiseledBookShelfBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class ChiseledBookShelfBlockEntity
/*     */   extends BlockEntity
/*     */   implements Container {
/*     */   public static final int MAX_BOOKS_IN_STORAGE = 6;
/*  26 */   private static final Logger LOGGER = LogUtils.getLogger();
/*  27 */   private final NonNullList<ItemStack> items = NonNullList.withSize(6, ItemStack.EMPTY);
/*     */   
/*  29 */   private int lastInteractedSlot = -1;
/*     */   
/*     */   public ChiseledBookShelfBlockEntity(BlockPos $$0, BlockState $$1) {
/*  32 */     super(BlockEntityType.CHISELED_BOOKSHELF, $$0, $$1);
/*     */   }
/*     */   
/*     */   private void updateState(int $$0) {
/*  36 */     if ($$0 < 0 || $$0 >= 6) {
/*  37 */       LOGGER.error("Expected slot 0-5, got {}", Integer.valueOf($$0));
/*     */       
/*     */       return;
/*     */     } 
/*  41 */     this.lastInteractedSlot = $$0;
/*  42 */     BlockState $$1 = getBlockState();
/*  43 */     for (int $$2 = 0; $$2 < ChiseledBookShelfBlock.SLOT_OCCUPIED_PROPERTIES.size(); $$2++) {
/*  44 */       boolean $$3 = !getItem($$2).isEmpty();
/*  45 */       BooleanProperty $$4 = ChiseledBookShelfBlock.SLOT_OCCUPIED_PROPERTIES.get($$2);
/*     */       
/*  47 */       $$1 = (BlockState)$$1.setValue((Property)$$4, Boolean.valueOf($$3));
/*     */     } 
/*     */     
/*  50 */     ((Level)Objects.<Level>requireNonNull(this.level)).setBlock(this.worldPosition, $$1, 3);
/*     */     
/*  52 */     this.level.gameEvent(GameEvent.BLOCK_CHANGE, this.worldPosition, GameEvent.Context.of($$1));
/*     */   }
/*     */ 
/*     */   
/*     */   public void load(CompoundTag $$0) {
/*  57 */     this.items.clear();
/*  58 */     ContainerHelper.loadAllItems($$0, this.items);
/*  59 */     this.lastInteractedSlot = $$0.getInt("last_interacted_slot");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void saveAdditional(CompoundTag $$0) {
/*  64 */     ContainerHelper.saveAllItems($$0, this.items, true);
/*  65 */     $$0.putInt("last_interacted_slot", this.lastInteractedSlot);
/*     */   }
/*     */   
/*     */   public int count() {
/*  69 */     return (int)this.items.stream().filter(Predicate.not(ItemStack::isEmpty)).count();
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearContent() {
/*  74 */     this.items.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getContainerSize() {
/*  79 */     return 6;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  84 */     return this.items.stream().allMatch(ItemStack::isEmpty);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getItem(int $$0) {
/*  89 */     return (ItemStack)this.items.get($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack removeItem(int $$0, int $$1) {
/*  94 */     ItemStack $$2 = Objects.<ItemStack>requireNonNullElse((ItemStack)this.items.get($$0), ItemStack.EMPTY);
/*  95 */     this.items.set($$0, ItemStack.EMPTY);
/*     */     
/*  97 */     if (!$$2.isEmpty()) {
/*  98 */       updateState($$0);
/*     */     }
/*     */     
/* 101 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack removeItemNoUpdate(int $$0) {
/* 106 */     return removeItem($$0, 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setItem(int $$0, ItemStack $$1) {
/* 111 */     if ($$1.is(ItemTags.BOOKSHELF_BOOKS)) {
/* 112 */       this.items.set($$0, $$1);
/* 113 */       updateState($$0);
/* 114 */     } else if ($$1.isEmpty()) {
/* 115 */       removeItem($$0, 1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canTakeItem(Container $$0, int $$1, ItemStack $$2) {
/* 121 */     return $$0.hasAnyMatching($$2 -> $$2.isEmpty() ? true : (
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 126 */         (ItemStack.isSameItemSameTags($$0, $$2) && $$2.getCount() + $$0.getCount() <= Math.min($$2.getMaxStackSize(), $$1.getMaxStackSize()))));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxStackSize() {
/* 132 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean stillValid(Player $$0) {
/* 137 */     return Container.stillValidBlockEntity(this, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceItem(int $$0, ItemStack $$1) {
/* 142 */     return ($$1.is(ItemTags.BOOKSHELF_BOOKS) && getItem($$0).isEmpty() && $$1.getCount() == getMaxStackSize());
/*     */   }
/*     */   
/*     */   public int getLastInteractedSlot() {
/* 146 */     return this.lastInteractedSlot;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\ChiseledBookShelfBlockEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */