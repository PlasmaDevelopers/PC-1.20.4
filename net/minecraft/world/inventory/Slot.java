/*     */ package net.minecraft.world.inventory;
/*     */ 
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import java.util.Optional;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ 
/*     */ 
/*     */ public class Slot
/*     */ {
/*     */   private final int slot;
/*     */   public final Container container;
/*     */   public int index;
/*     */   public final int x;
/*     */   public final int y;
/*     */   
/*     */   public Slot(Container $$0, int $$1, int $$2, int $$3) {
/*  21 */     this.container = $$0;
/*  22 */     this.slot = $$1;
/*  23 */     this.x = $$2;
/*  24 */     this.y = $$3;
/*     */   }
/*     */   
/*     */   public void onQuickCraft(ItemStack $$0, ItemStack $$1) {
/*  28 */     int $$2 = $$1.getCount() - $$0.getCount();
/*  29 */     if ($$2 > 0) {
/*  30 */       onQuickCraft($$1, $$2);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onQuickCraft(ItemStack $$0, int $$1) {}
/*     */ 
/*     */   
/*     */   protected void onSwapCraft(int $$0) {}
/*     */ 
/*     */   
/*     */   protected void checkTakeAchievements(ItemStack $$0) {}
/*     */ 
/*     */   
/*     */   public void onTake(Player $$0, ItemStack $$1) {
/*  45 */     setChanged();
/*     */   }
/*     */   
/*     */   public boolean mayPlace(ItemStack $$0) {
/*  49 */     return true;
/*     */   }
/*     */   
/*     */   public ItemStack getItem() {
/*  53 */     return this.container.getItem(this.slot);
/*     */   }
/*     */   
/*     */   public boolean hasItem() {
/*  57 */     return !getItem().isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setByPlayer(ItemStack $$0) {
/*  63 */     setByPlayer($$0, getItem());
/*     */   }
/*     */   public void setByPlayer(ItemStack $$0, ItemStack $$1) {
/*  66 */     set($$0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(ItemStack $$0) {
/*  75 */     this.container.setItem(this.slot, $$0);
/*  76 */     setChanged();
/*     */   }
/*     */   
/*     */   public void setChanged() {
/*  80 */     this.container.setChanged();
/*     */   }
/*     */   
/*     */   public int getMaxStackSize() {
/*  84 */     return this.container.getMaxStackSize();
/*     */   }
/*     */   
/*     */   public int getMaxStackSize(ItemStack $$0) {
/*  88 */     return Math.min(getMaxStackSize(), $$0.getMaxStackSize());
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
/*  93 */     return null;
/*     */   }
/*     */   
/*     */   public ItemStack remove(int $$0) {
/*  97 */     return this.container.removeItem(this.slot, $$0);
/*     */   }
/*     */   
/*     */   public boolean mayPickup(Player $$0) {
/* 101 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isActive() {
/* 105 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Optional<ItemStack> tryRemove(int $$0, int $$1, Player $$2) {
/* 110 */     if (!mayPickup($$2)) {
/* 111 */       return Optional.empty();
/*     */     }
/*     */ 
/*     */     
/* 115 */     if (!allowModification($$2) && $$1 < getItem().getCount()) {
/* 116 */       return Optional.empty();
/*     */     }
/*     */     
/* 119 */     $$0 = Math.min($$0, $$1);
/* 120 */     ItemStack $$3 = remove($$0);
/* 121 */     if ($$3.isEmpty()) {
/* 122 */       return Optional.empty();
/*     */     }
/* 124 */     if (getItem().isEmpty()) {
/* 125 */       setByPlayer(ItemStack.EMPTY, $$3);
/*     */     }
/* 127 */     return Optional.of($$3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack safeTake(int $$0, int $$1, Player $$2) {
/* 136 */     Optional<ItemStack> $$3 = tryRemove($$0, $$1, $$2);
/* 137 */     $$3.ifPresent($$1 -> onTake($$0, $$1));
/* 138 */     return $$3.orElse(ItemStack.EMPTY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack safeInsert(ItemStack $$0) {
/* 145 */     return safeInsert($$0, $$0.getCount());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack safeInsert(ItemStack $$0, int $$1) {
/* 152 */     if ($$0.isEmpty() || !mayPlace($$0)) {
/* 153 */       return $$0;
/*     */     }
/*     */     
/* 156 */     ItemStack $$2 = getItem();
/* 157 */     int $$3 = Math.min(Math.min($$1, $$0.getCount()), getMaxStackSize($$0) - $$2.getCount());
/*     */     
/* 159 */     if ($$2.isEmpty()) {
/* 160 */       setByPlayer($$0.split($$3));
/* 161 */     } else if (ItemStack.isSameItemSameTags($$2, $$0)) {
/* 162 */       $$0.shrink($$3);
/* 163 */       $$2.grow($$3);
/*     */       
/* 165 */       setByPlayer($$2);
/*     */     } 
/*     */     
/* 168 */     return $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean allowModification(Player $$0) {
/* 173 */     return (mayPickup($$0) && mayPlace(getItem()));
/*     */   }
/*     */   
/*     */   public int getContainerSlot() {
/* 177 */     return this.slot;
/*     */   }
/*     */   
/*     */   public boolean isHighlightable() {
/* 181 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isFake() {
/* 185 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\inventory\Slot.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */