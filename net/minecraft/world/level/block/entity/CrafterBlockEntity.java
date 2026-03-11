/*     */ package net.minecraft.world.level.block.entity;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import it.unimi.dsi.fastutil.ints.IntArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.NonNullList;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.world.ContainerHelper;
/*     */ import net.minecraft.world.entity.player.Inventory;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.entity.player.StackedContents;
/*     */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*     */ import net.minecraft.world.inventory.ContainerData;
/*     */ import net.minecraft.world.inventory.CrafterMenu;
/*     */ import net.minecraft.world.inventory.CraftingContainer;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.CrafterBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ 
/*     */ public class CrafterBlockEntity
/*     */   extends RandomizableContainerBlockEntity implements CraftingContainer {
/*     */   public static final int CONTAINER_WIDTH = 3;
/*     */   public static final int CONTAINER_HEIGHT = 3;
/*     */   public static final int CONTAINER_SIZE = 9;
/*     */   public static final int SLOT_DISABLED = 1;
/*     */   public static final int SLOT_ENABLED = 0;
/*     */   public static final int DATA_TRIGGERED = 9;
/*     */   public static final int NUM_DATA = 10;
/*  33 */   private NonNullList<ItemStack> items = NonNullList.withSize(9, ItemStack.EMPTY);
/*     */   
/*  35 */   private int craftingTicksRemaining = 0;
/*     */   
/*  37 */   protected final ContainerData containerData = new ContainerData()
/*     */     {
/*  39 */       private final int[] slotStates = new int[9];
/*  40 */       private int triggered = 0;
/*     */ 
/*     */       
/*     */       public int get(int $$0) {
/*  44 */         return ($$0 == 9) ? this.triggered : this.slotStates[$$0];
/*     */       }
/*     */ 
/*     */       
/*     */       public void set(int $$0, int $$1) {
/*  49 */         if ($$0 == 9) {
/*  50 */           this.triggered = $$1;
/*     */         } else {
/*  52 */           this.slotStates[$$0] = $$1;
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/*     */       public int getCount() {
/*  58 */         return 10;
/*     */       }
/*     */     };
/*     */   
/*     */   public CrafterBlockEntity(BlockPos $$0, BlockState $$1) {
/*  63 */     super(BlockEntityType.CRAFTER, $$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Component getDefaultName() {
/*  68 */     return (Component)Component.translatable("container.crafter");
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractContainerMenu createMenu(int $$0, Inventory $$1) {
/*  73 */     return (AbstractContainerMenu)new CrafterMenu($$0, $$1, this, this.containerData);
/*     */   }
/*     */   
/*     */   public void setSlotState(int $$0, boolean $$1) {
/*  77 */     if (!slotCanBeDisabled($$0)) {
/*     */       return;
/*     */     }
/*  80 */     this.containerData.set($$0, $$1 ? 0 : 1);
/*  81 */     setChanged();
/*     */   }
/*     */   
/*     */   public boolean isSlotDisabled(int $$0) {
/*  85 */     if ($$0 >= 0 && $$0 < 9) {
/*  86 */       return (this.containerData.get($$0) == 1);
/*     */     }
/*  88 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceItem(int $$0, ItemStack $$1) {
/*  93 */     if (this.containerData.get($$0) == 1) {
/*  94 */       return false;
/*     */     }
/*     */     
/*  97 */     ItemStack $$2 = (ItemStack)this.items.get($$0);
/*  98 */     int $$3 = $$2.getCount();
/*  99 */     if ($$3 >= $$2.getMaxStackSize()) {
/* 100 */       return false;
/*     */     }
/*     */     
/* 103 */     if ($$2.isEmpty()) {
/* 104 */       return true;
/*     */     }
/*     */     
/* 107 */     return !smallerStackExist($$3, $$2, $$0);
/*     */   }
/*     */   
/*     */   private boolean smallerStackExist(int $$0, ItemStack $$1, int $$2) {
/* 111 */     for (int $$3 = $$2 + 1; $$3 < 9; $$3++) {
/* 112 */       if (!isSlotDisabled($$3)) {
/* 113 */         ItemStack $$4 = getItem($$3);
/* 114 */         if ($$4.isEmpty() || ($$4.getCount() < $$0 && ItemStack.isSameItemSameTags($$4, $$1))) {
/* 115 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/* 119 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void load(CompoundTag $$0) {
/* 124 */     super.load($$0);
/*     */     
/* 126 */     this.craftingTicksRemaining = $$0.getInt("crafting_ticks_remaining");
/* 127 */     this.items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
/* 128 */     if (!tryLoadLootTable($$0)) {
/* 129 */       ContainerHelper.loadAllItems($$0, this.items);
/*     */     }
/*     */     
/* 132 */     int[] $$1 = $$0.getIntArray("disabled_slots");
/*     */     
/* 134 */     for (int $$2 = 0; $$2 < 9; $$2++) {
/* 135 */       this.containerData.set($$2, 0);
/*     */     }
/* 137 */     for (int $$3 : $$1) {
/* 138 */       if (slotCanBeDisabled($$3)) {
/* 139 */         this.containerData.set($$3, 1);
/*     */       }
/*     */     } 
/*     */     
/* 143 */     this.containerData.set(9, $$0.getInt("triggered"));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void saveAdditional(CompoundTag $$0) {
/* 148 */     super.saveAdditional($$0);
/*     */     
/* 150 */     $$0.putInt("crafting_ticks_remaining", this.craftingTicksRemaining);
/* 151 */     if (!trySaveLootTable($$0)) {
/* 152 */       ContainerHelper.saveAllItems($$0, this.items);
/*     */     }
/*     */     
/* 155 */     addDisabledSlots($$0);
/* 156 */     addTriggered($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getContainerSize() {
/* 161 */     return 9;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 166 */     for (ItemStack $$0 : this.items) {
/* 167 */       if (!$$0.isEmpty()) {
/* 168 */         return false;
/*     */       }
/*     */     } 
/* 171 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getItem(int $$0) {
/* 176 */     return (ItemStack)this.items.get($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setItem(int $$0, ItemStack $$1) {
/* 181 */     if (isSlotDisabled($$0))
/*     */     {
/* 183 */       setSlotState($$0, true);
/*     */     }
/* 185 */     super.setItem($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean stillValid(Player $$0) {
/* 190 */     if (this.level == null || this.level.getBlockEntity(this.worldPosition) != this) {
/* 191 */       return false;
/*     */     }
/*     */     
/* 194 */     return ($$0.distanceToSqr(this.worldPosition.getX() + 0.5D, this.worldPosition.getY() + 0.5D, this.worldPosition.getZ() + 0.5D) <= 64.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public NonNullList<ItemStack> getItems() {
/* 199 */     return this.items;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setItems(NonNullList<ItemStack> $$0) {
/* 204 */     this.items = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWidth() {
/* 209 */     return 3;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeight() {
/* 214 */     return 3;
/*     */   }
/*     */ 
/*     */   
/*     */   public void fillStackedContents(StackedContents $$0) {
/* 219 */     for (ItemStack $$1 : this.items) {
/* 220 */       $$0.accountSimpleStack($$1);
/*     */     }
/*     */   }
/*     */   
/*     */   private void addDisabledSlots(CompoundTag $$0) {
/* 225 */     IntArrayList intArrayList = new IntArrayList();
/* 226 */     for (int $$2 = 0; $$2 < 9; $$2++) {
/* 227 */       if (isSlotDisabled($$2)) {
/* 228 */         intArrayList.add($$2);
/*     */       }
/*     */     } 
/*     */     
/* 232 */     $$0.putIntArray("disabled_slots", (List)intArrayList);
/*     */   }
/*     */   
/*     */   private void addTriggered(CompoundTag $$0) {
/* 236 */     $$0.putInt("triggered", this.containerData.get(9));
/*     */   }
/*     */   
/*     */   public void setTriggered(boolean $$0) {
/* 240 */     this.containerData.set(9, $$0 ? 1 : 0);
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   public boolean isTriggered() {
/* 245 */     return (this.containerData.get(9) == 1);
/*     */   }
/*     */   
/*     */   public static void serverTick(Level $$0, BlockPos $$1, BlockState $$2, CrafterBlockEntity $$3) {
/* 249 */     int $$4 = $$3.craftingTicksRemaining - 1;
/*     */     
/* 251 */     if ($$4 < 0) {
/*     */       return;
/*     */     }
/*     */     
/* 255 */     $$3.craftingTicksRemaining = $$4;
/* 256 */     if ($$4 == 0) {
/* 257 */       $$0.setBlock($$1, (BlockState)$$2.setValue((Property)CrafterBlock.CRAFTING, Boolean.valueOf(false)), 3);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setCraftingTicksRemaining(int $$0) {
/* 262 */     this.craftingTicksRemaining = $$0;
/*     */   }
/*     */   
/*     */   public int getRedstoneSignal() {
/* 266 */     int $$0 = 0;
/*     */     
/* 268 */     for (int $$1 = 0; $$1 < getContainerSize(); $$1++) {
/* 269 */       ItemStack $$2 = getItem($$1);
/*     */       
/* 271 */       if (!$$2.isEmpty() || isSlotDisabled($$1)) {
/* 272 */         $$0++;
/*     */       }
/*     */     } 
/*     */     
/* 276 */     return $$0;
/*     */   }
/*     */   
/*     */   private boolean slotCanBeDisabled(int $$0) {
/* 280 */     return ($$0 > -1 && $$0 < 9 && ((ItemStack)this.items
/*     */       
/* 282 */       .get($$0)).isEmpty());
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\CrafterBlockEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */