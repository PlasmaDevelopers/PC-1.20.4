/*     */ package net.minecraft.world.inventory;
/*     */ 
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.SimpleContainer;
/*     */ import net.minecraft.world.entity.player.Inventory;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ 
/*     */ public class ChestMenu extends AbstractContainerMenu {
/*     */   private static final int SLOTS_PER_ROW = 9;
/*     */   private final Container container;
/*     */   private final int containerRows;
/*     */   
/*     */   private ChestMenu(MenuType<?> $$0, int $$1, Inventory $$2, int $$3) {
/*  15 */     this($$0, $$1, $$2, (Container)new SimpleContainer(9 * $$3), $$3);
/*     */   }
/*     */   
/*     */   public static ChestMenu oneRow(int $$0, Inventory $$1) {
/*  19 */     return new ChestMenu(MenuType.GENERIC_9x1, $$0, $$1, 1);
/*     */   }
/*     */   
/*     */   public static ChestMenu twoRows(int $$0, Inventory $$1) {
/*  23 */     return new ChestMenu(MenuType.GENERIC_9x2, $$0, $$1, 2);
/*     */   }
/*     */   
/*     */   public static ChestMenu threeRows(int $$0, Inventory $$1) {
/*  27 */     return new ChestMenu(MenuType.GENERIC_9x3, $$0, $$1, 3);
/*     */   }
/*     */   
/*     */   public static ChestMenu fourRows(int $$0, Inventory $$1) {
/*  31 */     return new ChestMenu(MenuType.GENERIC_9x4, $$0, $$1, 4);
/*     */   }
/*     */   
/*     */   public static ChestMenu fiveRows(int $$0, Inventory $$1) {
/*  35 */     return new ChestMenu(MenuType.GENERIC_9x5, $$0, $$1, 5);
/*     */   }
/*     */   
/*     */   public static ChestMenu sixRows(int $$0, Inventory $$1) {
/*  39 */     return new ChestMenu(MenuType.GENERIC_9x6, $$0, $$1, 6);
/*     */   }
/*     */   
/*     */   public static ChestMenu threeRows(int $$0, Inventory $$1, Container $$2) {
/*  43 */     return new ChestMenu(MenuType.GENERIC_9x3, $$0, $$1, $$2, 3);
/*     */   }
/*     */   
/*     */   public static ChestMenu sixRows(int $$0, Inventory $$1, Container $$2) {
/*  47 */     return new ChestMenu(MenuType.GENERIC_9x6, $$0, $$1, $$2, 6);
/*     */   }
/*     */   
/*     */   public ChestMenu(MenuType<?> $$0, int $$1, Inventory $$2, Container $$3, int $$4) {
/*  51 */     super($$0, $$1);
/*  52 */     checkContainerSize($$3, $$4 * 9);
/*  53 */     this.container = $$3;
/*  54 */     this.containerRows = $$4;
/*  55 */     $$3.startOpen($$2.player);
/*     */     
/*  57 */     int $$5 = (this.containerRows - 4) * 18;
/*     */     
/*  59 */     for (int $$6 = 0; $$6 < this.containerRows; $$6++) {
/*  60 */       for (int $$7 = 0; $$7 < 9; $$7++) {
/*  61 */         addSlot(new Slot($$3, $$7 + $$6 * 9, 8 + $$7 * 18, 18 + $$6 * 18));
/*     */       }
/*     */     } 
/*     */     
/*  65 */     for (int $$8 = 0; $$8 < 3; $$8++) {
/*  66 */       for (int $$9 = 0; $$9 < 9; $$9++) {
/*  67 */         addSlot(new Slot((Container)$$2, $$9 + $$8 * 9 + 9, 8 + $$9 * 18, 103 + $$8 * 18 + $$5));
/*     */       }
/*     */     } 
/*  70 */     for (int $$10 = 0; $$10 < 9; $$10++) {
/*  71 */       addSlot(new Slot((Container)$$2, $$10, 8 + $$10 * 18, 161 + $$5));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean stillValid(Player $$0) {
/*  77 */     return this.container.stillValid($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack quickMoveStack(Player $$0, int $$1) {
/*  82 */     ItemStack $$2 = ItemStack.EMPTY;
/*  83 */     Slot $$3 = (Slot)this.slots.get($$1);
/*  84 */     if ($$3 != null && $$3.hasItem()) {
/*  85 */       ItemStack $$4 = $$3.getItem();
/*  86 */       $$2 = $$4.copy();
/*     */       
/*  88 */       if ($$1 < this.containerRows * 9) {
/*  89 */         if (!moveItemStackTo($$4, this.containerRows * 9, this.slots.size(), true)) {
/*  90 */           return ItemStack.EMPTY;
/*     */         }
/*     */       }
/*  93 */       else if (!moveItemStackTo($$4, 0, this.containerRows * 9, false)) {
/*  94 */         return ItemStack.EMPTY;
/*     */       } 
/*     */       
/*  97 */       if ($$4.isEmpty()) {
/*  98 */         $$3.setByPlayer(ItemStack.EMPTY);
/*     */       } else {
/* 100 */         $$3.setChanged();
/*     */       } 
/*     */     } 
/* 103 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void removed(Player $$0) {
/* 108 */     super.removed($$0);
/* 109 */     this.container.stopOpen($$0);
/*     */   }
/*     */   
/*     */   public Container getContainer() {
/* 113 */     return this.container;
/*     */   }
/*     */   
/*     */   public int getRowCount() {
/* 117 */     return this.containerRows;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\inventory\ChestMenu.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */