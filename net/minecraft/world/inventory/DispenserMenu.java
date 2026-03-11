/*    */ package net.minecraft.world.inventory;
/*    */ 
/*    */ import net.minecraft.world.Container;
/*    */ import net.minecraft.world.SimpleContainer;
/*    */ import net.minecraft.world.entity.player.Inventory;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ 
/*    */ public class DispenserMenu
/*    */   extends AbstractContainerMenu
/*    */ {
/*    */   private static final int SLOT_COUNT = 9;
/*    */   private static final int INV_SLOT_START = 9;
/*    */   private static final int INV_SLOT_END = 36;
/*    */   private static final int USE_ROW_SLOT_START = 36;
/*    */   private static final int USE_ROW_SLOT_END = 45;
/*    */   private final Container dispenser;
/*    */   
/*    */   public DispenserMenu(int $$0, Inventory $$1) {
/* 21 */     this($$0, $$1, (Container)new SimpleContainer(9));
/*    */   }
/*    */   
/*    */   public DispenserMenu(int $$0, Inventory $$1, Container $$2) {
/* 25 */     super(MenuType.GENERIC_3x3, $$0);
/* 26 */     checkContainerSize($$2, 9);
/* 27 */     this.dispenser = $$2;
/* 28 */     $$2.startOpen($$1.player);
/*    */     
/* 30 */     for (int $$3 = 0; $$3 < 3; $$3++) {
/* 31 */       for (int $$4 = 0; $$4 < 3; $$4++) {
/* 32 */         addSlot(new Slot($$2, $$4 + $$3 * 3, 62 + $$4 * 18, 17 + $$3 * 18));
/*    */       }
/*    */     } 
/*    */     
/* 36 */     for (int $$5 = 0; $$5 < 3; $$5++) {
/* 37 */       for (int $$6 = 0; $$6 < 9; $$6++) {
/* 38 */         addSlot(new Slot((Container)$$1, $$6 + $$5 * 9 + 9, 8 + $$6 * 18, 84 + $$5 * 18));
/*    */       }
/*    */     } 
/* 41 */     for (int $$7 = 0; $$7 < 9; $$7++) {
/* 42 */       addSlot(new Slot((Container)$$1, $$7, 8 + $$7 * 18, 142));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean stillValid(Player $$0) {
/* 48 */     return this.dispenser.stillValid($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack quickMoveStack(Player $$0, int $$1) {
/* 53 */     ItemStack $$2 = ItemStack.EMPTY;
/* 54 */     Slot $$3 = (Slot)this.slots.get($$1);
/* 55 */     if ($$3 != null && $$3.hasItem()) {
/* 56 */       ItemStack $$4 = $$3.getItem();
/* 57 */       $$2 = $$4.copy();
/*    */       
/* 59 */       if ($$1 < 9) {
/* 60 */         if (!moveItemStackTo($$4, 9, 45, true)) {
/* 61 */           return ItemStack.EMPTY;
/*    */         }
/*    */       }
/* 64 */       else if (!moveItemStackTo($$4, 0, 9, false)) {
/* 65 */         return ItemStack.EMPTY;
/*    */       } 
/*    */       
/* 68 */       if ($$4.isEmpty()) {
/* 69 */         $$3.setByPlayer(ItemStack.EMPTY);
/*    */       } else {
/* 71 */         $$3.setChanged();
/*    */       } 
/* 73 */       if ($$4.getCount() == $$2.getCount())
/*    */       {
/* 75 */         return ItemStack.EMPTY;
/*    */       }
/* 77 */       $$3.onTake($$0, $$4);
/*    */     } 
/*    */     
/* 80 */     return $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public void removed(Player $$0) {
/* 85 */     super.removed($$0);
/* 86 */     this.dispenser.stopOpen($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\inventory\DispenserMenu.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */