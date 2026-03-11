/*     */ package net.minecraft.world.inventory;
/*     */ 
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
/*     */ import net.minecraft.world.entity.animal.horse.AbstractHorse;
/*     */ import net.minecraft.world.entity.player.Inventory;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ 
/*     */ public class HorseInventoryMenu extends AbstractContainerMenu {
/*     */   private final Container horseContainer;
/*     */   
/*     */   public HorseInventoryMenu(int $$0, Inventory $$1, Container $$2, final AbstractHorse horse) {
/*  16 */     super(null, $$0);
/*  17 */     this.horseContainer = $$2;
/*  18 */     this.horse = horse;
/*  19 */     int $$4 = 3;
/*  20 */     $$2.startOpen($$1.player);
/*     */     
/*  22 */     int $$5 = -18;
/*     */ 
/*     */     
/*  25 */     addSlot(new Slot($$2, 0, 8, 18)
/*     */         {
/*     */           public boolean mayPlace(ItemStack $$0) {
/*  28 */             return ($$0.is(Items.SADDLE) && !hasItem() && horse.isSaddleable());
/*     */           }
/*     */ 
/*     */           
/*     */           public boolean isActive() {
/*  33 */             return horse.isSaddleable();
/*     */           }
/*     */         });
/*     */     
/*  37 */     addSlot(new Slot($$2, 1, 8, 36)
/*     */         {
/*     */           public boolean mayPlace(ItemStack $$0) {
/*  40 */             return horse.isArmor($$0);
/*     */           }
/*     */ 
/*     */           
/*     */           public boolean isActive() {
/*  45 */             return horse.canWearArmor();
/*     */           }
/*     */ 
/*     */           
/*     */           public int getMaxStackSize() {
/*  50 */             return 1;
/*     */           }
/*     */         });
/*     */     
/*  54 */     if (hasChest(horse)) {
/*  55 */       for (int $$6 = 0; $$6 < 3; $$6++) {
/*  56 */         for (int $$7 = 0; $$7 < ((AbstractChestedHorse)horse).getInventoryColumns(); $$7++) {
/*  57 */           addSlot(new Slot($$2, 2 + $$7 + $$6 * ((AbstractChestedHorse)horse).getInventoryColumns(), 80 + $$7 * 18, 18 + $$6 * 18));
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*  62 */     for (int $$8 = 0; $$8 < 3; $$8++) {
/*  63 */       for (int $$9 = 0; $$9 < 9; $$9++) {
/*  64 */         addSlot(new Slot((Container)$$1, $$9 + $$8 * 9 + 9, 8 + $$9 * 18, 102 + $$8 * 18 + -18));
/*     */       }
/*     */     } 
/*  67 */     for (int $$10 = 0; $$10 < 9; $$10++)
/*  68 */       addSlot(new Slot((Container)$$1, $$10, 8 + $$10 * 18, 142)); 
/*     */   }
/*     */   
/*     */   private final AbstractHorse horse;
/*     */   
/*     */   public boolean stillValid(Player $$0) {
/*  74 */     return (!this.horse.hasInventoryChanged(this.horseContainer) && this.horseContainer.stillValid($$0) && this.horse.isAlive() && this.horse.distanceTo((Entity)$$0) < 8.0F);
/*     */   }
/*     */   
/*     */   private boolean hasChest(AbstractHorse $$0) {
/*  78 */     return ($$0 instanceof AbstractChestedHorse && ((AbstractChestedHorse)$$0).hasChest());
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack quickMoveStack(Player $$0, int $$1) {
/*  83 */     ItemStack $$2 = ItemStack.EMPTY;
/*  84 */     Slot $$3 = (Slot)this.slots.get($$1);
/*  85 */     if ($$3 != null && $$3.hasItem()) {
/*  86 */       ItemStack $$4 = $$3.getItem();
/*  87 */       $$2 = $$4.copy();
/*     */       
/*  89 */       int $$5 = this.horseContainer.getContainerSize();
/*  90 */       if ($$1 < $$5) {
/*  91 */         if (!moveItemStackTo($$4, $$5, this.slots.size(), true)) {
/*  92 */           return ItemStack.EMPTY;
/*     */         }
/*  94 */       } else if (getSlot(1).mayPlace($$4) && !getSlot(1).hasItem()) {
/*  95 */         if (!moveItemStackTo($$4, 1, 2, false)) {
/*  96 */           return ItemStack.EMPTY;
/*     */         }
/*  98 */       } else if (getSlot(0).mayPlace($$4)) {
/*  99 */         if (!moveItemStackTo($$4, 0, 1, false)) {
/* 100 */           return ItemStack.EMPTY;
/*     */         }
/* 102 */       } else if ($$5 <= 2 || !moveItemStackTo($$4, 2, $$5, false)) {
/* 103 */         int $$6 = $$5;
/* 104 */         int $$7 = $$6 + 27;
/* 105 */         int $$8 = $$7;
/* 106 */         int $$9 = $$8 + 9;
/* 107 */         if ($$1 >= $$8 && $$1 < $$9) {
/* 108 */           if (!moveItemStackTo($$4, $$6, $$7, false)) {
/* 109 */             return ItemStack.EMPTY;
/*     */           }
/* 111 */         } else if ($$1 >= $$6 && $$1 < $$7) {
/* 112 */           if (!moveItemStackTo($$4, $$8, $$9, false)) {
/* 113 */             return ItemStack.EMPTY;
/*     */           }
/* 115 */         } else if (!moveItemStackTo($$4, $$8, $$7, false)) {
/* 116 */           return ItemStack.EMPTY;
/*     */         } 
/* 118 */         return ItemStack.EMPTY;
/*     */       } 
/* 120 */       if ($$4.isEmpty()) {
/* 121 */         $$3.setByPlayer(ItemStack.EMPTY);
/*     */       } else {
/* 123 */         $$3.setChanged();
/*     */       } 
/*     */     } 
/* 126 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void removed(Player $$0) {
/* 131 */     super.removed($$0);
/* 132 */     this.horseContainer.stopOpen($$0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\inventory\HorseInventoryMenu.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */