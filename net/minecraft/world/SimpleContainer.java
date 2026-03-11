/*     */ package net.minecraft.world;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.NonNullList;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.entity.player.StackedContents;
/*     */ import net.minecraft.world.inventory.StackedContentsCompatible;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ 
/*     */ public class SimpleContainer implements Container, StackedContentsCompatible {
/*     */   private final int size;
/*     */   private final NonNullList<ItemStack> items;
/*     */   @Nullable
/*     */   private List<ContainerListener> listeners;
/*     */   
/*     */   public SimpleContainer(int $$0) {
/*  24 */     this.size = $$0;
/*  25 */     this.items = NonNullList.withSize($$0, ItemStack.EMPTY);
/*     */   }
/*     */   
/*     */   public SimpleContainer(ItemStack... $$0) {
/*  29 */     this.size = $$0.length;
/*  30 */     this.items = NonNullList.of(ItemStack.EMPTY, (Object[])$$0);
/*     */   }
/*     */   
/*     */   public void addListener(ContainerListener $$0) {
/*  34 */     if (this.listeners == null) {
/*  35 */       this.listeners = Lists.newArrayList();
/*     */     }
/*  37 */     this.listeners.add($$0);
/*     */   }
/*     */   
/*     */   public void removeListener(ContainerListener $$0) {
/*  41 */     if (this.listeners != null) {
/*  42 */       this.listeners.remove($$0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getItem(int $$0) {
/*  48 */     if ($$0 < 0 || $$0 >= this.items.size()) {
/*  49 */       return ItemStack.EMPTY;
/*     */     }
/*  51 */     return (ItemStack)this.items.get($$0);
/*     */   }
/*     */   
/*     */   public List<ItemStack> removeAllItems() {
/*  55 */     List<ItemStack> $$0 = (List<ItemStack>)this.items.stream().filter($$0 -> !$$0.isEmpty()).collect(Collectors.toList());
/*  56 */     clearContent();
/*  57 */     return $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack removeItem(int $$0, int $$1) {
/*  62 */     ItemStack $$2 = ContainerHelper.removeItem((List<ItemStack>)this.items, $$0, $$1);
/*  63 */     if (!$$2.isEmpty()) {
/*  64 */       setChanged();
/*     */     }
/*  66 */     return $$2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack removeItemType(Item $$0, int $$1) {
/*  74 */     ItemStack $$2 = new ItemStack((ItemLike)$$0, 0);
/*     */     
/*  76 */     for (int $$3 = this.size - 1; $$3 >= 0; $$3--) {
/*  77 */       ItemStack $$4 = getItem($$3);
/*  78 */       if ($$4.getItem().equals($$0)) {
/*  79 */         int $$5 = $$1 - $$2.getCount();
/*  80 */         ItemStack $$6 = $$4.split($$5);
/*  81 */         $$2.grow($$6.getCount());
/*  82 */         if ($$2.getCount() == $$1) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */     } 
/*  87 */     if (!$$2.isEmpty()) {
/*  88 */       setChanged();
/*     */     }
/*  90 */     return $$2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack addItem(ItemStack $$0) {
/*  98 */     if ($$0.isEmpty()) {
/*  99 */       return ItemStack.EMPTY;
/*     */     }
/*     */     
/* 102 */     ItemStack $$1 = $$0.copy();
/*     */     
/* 104 */     moveItemToOccupiedSlotsWithSameType($$1);
/* 105 */     if ($$1.isEmpty()) {
/* 106 */       return ItemStack.EMPTY;
/*     */     }
/*     */     
/* 109 */     moveItemToEmptySlots($$1);
/* 110 */     if ($$1.isEmpty()) {
/* 111 */       return ItemStack.EMPTY;
/*     */     }
/*     */     
/* 114 */     return $$1;
/*     */   }
/*     */   
/*     */   public boolean canAddItem(ItemStack $$0) {
/* 118 */     boolean $$1 = false;
/* 119 */     for (ItemStack $$2 : this.items) {
/* 120 */       if ($$2.isEmpty() || (ItemStack.isSameItemSameTags($$2, $$0) && $$2.getCount() < $$2.getMaxStackSize())) {
/* 121 */         $$1 = true;
/*     */         break;
/*     */       } 
/*     */     } 
/* 125 */     return $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack removeItemNoUpdate(int $$0) {
/* 130 */     ItemStack $$1 = (ItemStack)this.items.get($$0);
/* 131 */     if ($$1.isEmpty()) {
/* 132 */       return ItemStack.EMPTY;
/*     */     }
/* 134 */     this.items.set($$0, ItemStack.EMPTY);
/* 135 */     return $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setItem(int $$0, ItemStack $$1) {
/* 140 */     this.items.set($$0, $$1);
/* 141 */     if (!$$1.isEmpty() && $$1.getCount() > getMaxStackSize()) {
/* 142 */       $$1.setCount(getMaxStackSize());
/*     */     }
/* 144 */     setChanged();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getContainerSize() {
/* 149 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 154 */     for (ItemStack $$0 : this.items) {
/* 155 */       if (!$$0.isEmpty()) {
/* 156 */         return false;
/*     */       }
/*     */     } 
/* 159 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setChanged() {
/* 164 */     if (this.listeners != null) {
/* 165 */       for (ContainerListener $$0 : this.listeners) {
/* 166 */         $$0.containerChanged(this);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean stillValid(Player $$0) {
/* 173 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearContent() {
/* 178 */     this.items.clear();
/* 179 */     setChanged();
/*     */   }
/*     */ 
/*     */   
/*     */   public void fillStackedContents(StackedContents $$0) {
/* 184 */     for (ItemStack $$1 : this.items) {
/* 185 */       $$0.accountStack($$1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 191 */     return ((List)this.items.stream()
/* 192 */       .filter($$0 -> !$$0.isEmpty())
/* 193 */       .collect(Collectors.toList()))
/* 194 */       .toString();
/*     */   }
/*     */   
/*     */   private void moveItemToEmptySlots(ItemStack $$0) {
/* 198 */     for (int $$1 = 0; $$1 < this.size; $$1++) {
/* 199 */       ItemStack $$2 = getItem($$1);
/* 200 */       if ($$2.isEmpty()) {
/* 201 */         setItem($$1, $$0.copyAndClear());
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void moveItemToOccupiedSlotsWithSameType(ItemStack $$0) {
/* 208 */     for (int $$1 = 0; $$1 < this.size; $$1++) {
/* 209 */       ItemStack $$2 = getItem($$1);
/* 210 */       if (ItemStack.isSameItemSameTags($$2, $$0)) {
/* 211 */         moveItemsBetweenStacks($$0, $$2);
/* 212 */         if ($$0.isEmpty()) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void moveItemsBetweenStacks(ItemStack $$0, ItemStack $$1) {
/* 223 */     int $$2 = Math.min(getMaxStackSize(), $$1.getMaxStackSize());
/* 224 */     int $$3 = Math.min($$0.getCount(), $$2 - $$1.getCount());
/* 225 */     if ($$3 > 0) {
/* 226 */       $$1.grow($$3);
/* 227 */       $$0.shrink($$3);
/* 228 */       setChanged();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void fromTag(ListTag $$0) {
/* 233 */     clearContent();
/* 234 */     for (int $$1 = 0; $$1 < $$0.size(); $$1++) {
/* 235 */       ItemStack $$2 = ItemStack.of($$0.getCompound($$1));
/* 236 */       if (!$$2.isEmpty()) {
/* 237 */         addItem($$2);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public ListTag createTag() {
/* 243 */     ListTag $$0 = new ListTag();
/* 244 */     for (int $$1 = 0; $$1 < getContainerSize(); $$1++) {
/* 245 */       ItemStack $$2 = getItem($$1);
/* 246 */       if (!$$2.isEmpty()) {
/* 247 */         $$0.add($$2.save(new CompoundTag()));
/*     */       }
/*     */     } 
/* 250 */     return $$0;
/*     */   }
/*     */   
/*     */   public NonNullList<ItemStack> getItems() {
/* 254 */     return this.items;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\SimpleContainer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */