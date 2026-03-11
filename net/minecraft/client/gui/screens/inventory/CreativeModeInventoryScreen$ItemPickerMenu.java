/*     */ package net.minecraft.client.gui.screens.inventory;
/*     */ 
/*     */ import net.minecraft.core.NonNullList;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.entity.player.Inventory;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*     */ import net.minecraft.world.inventory.Slot;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemPickerMenu
/*     */   extends AbstractContainerMenu
/*     */ {
/* 119 */   public final NonNullList<ItemStack> items = NonNullList.create();
/*     */   
/*     */   private final AbstractContainerMenu inventoryMenu;
/*     */   
/*     */   public ItemPickerMenu(Player $$0) {
/* 124 */     super(null, 0);
/* 125 */     this.inventoryMenu = (AbstractContainerMenu)$$0.inventoryMenu;
/*     */     
/* 127 */     Inventory $$1 = $$0.getInventory();
/* 128 */     for (int $$2 = 0; $$2 < 5; $$2++) {
/* 129 */       for (int $$3 = 0; $$3 < 9; $$3++) {
/* 130 */         addSlot(new CreativeModeInventoryScreen.CustomCreativeSlot((Container)CreativeModeInventoryScreen.CONTAINER, $$2 * 9 + $$3, 9 + $$3 * 18, 18 + $$2 * 18));
/*     */       }
/*     */     } 
/*     */     
/* 134 */     for (int $$4 = 0; $$4 < 9; $$4++) {
/* 135 */       addSlot(new Slot((Container)$$1, $$4, 9 + $$4 * 18, 112));
/*     */     }
/*     */     
/* 138 */     scrollTo(0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean stillValid(Player $$0) {
/* 143 */     return true;
/*     */   }
/*     */   
/*     */   protected int calculateRowCount() {
/* 147 */     return Mth.positiveCeilDiv(this.items.size(), 9) - 5;
/*     */   }
/*     */   
/*     */   protected int getRowIndexForScroll(float $$0) {
/* 151 */     return Math.max((int)(($$0 * calculateRowCount()) + 0.5D), 0);
/*     */   }
/*     */   
/*     */   protected float getScrollForRowIndex(int $$0) {
/* 155 */     return Mth.clamp($$0 / calculateRowCount(), 0.0F, 1.0F);
/*     */   }
/*     */   
/*     */   protected float subtractInputFromScroll(float $$0, double $$1) {
/* 159 */     return Mth.clamp($$0 - (float)($$1 / calculateRowCount()), 0.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public void scrollTo(float $$0) {
/* 163 */     int $$1 = getRowIndexForScroll($$0);
/* 164 */     for (int $$2 = 0; $$2 < 5; $$2++) {
/* 165 */       for (int $$3 = 0; $$3 < 9; $$3++) {
/* 166 */         int $$4 = $$3 + ($$2 + $$1) * 9;
/* 167 */         if ($$4 >= 0 && $$4 < this.items.size()) {
/* 168 */           CreativeModeInventoryScreen.CONTAINER.setItem($$3 + $$2 * 9, (ItemStack)this.items.get($$4));
/*     */         } else {
/* 170 */           CreativeModeInventoryScreen.CONTAINER.setItem($$3 + $$2 * 9, ItemStack.EMPTY);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean canScroll() {
/* 177 */     return (this.items.size() > 45);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack quickMoveStack(Player $$0, int $$1) {
/* 182 */     if ($$1 >= this.slots.size() - 9 && $$1 < this.slots.size()) {
/* 183 */       Slot $$2 = (Slot)this.slots.get($$1);
/*     */       
/* 185 */       if ($$2 != null && $$2.hasItem()) {
/* 186 */         $$2.setByPlayer(ItemStack.EMPTY);
/*     */       }
/*     */     } 
/*     */     
/* 190 */     return ItemStack.EMPTY;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canTakeItemForPickAll(ItemStack $$0, Slot $$1) {
/* 195 */     return ($$1.container != CreativeModeInventoryScreen.CONTAINER);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canDragTo(Slot $$0) {
/* 200 */     return ($$0.container != CreativeModeInventoryScreen.CONTAINER);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getCarried() {
/* 205 */     return this.inventoryMenu.getCarried();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCarried(ItemStack $$0) {
/* 210 */     this.inventoryMenu.setCarried($$0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\inventory\CreativeModeInventoryScreen$ItemPickerMenu.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */