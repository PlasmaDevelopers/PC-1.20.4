/*    */ package net.minecraft.world.inventory;
/*    */ 
/*    */ import net.minecraft.world.Container;
/*    */ import net.minecraft.world.SimpleContainer;
/*    */ import net.minecraft.world.entity.player.Inventory;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ public class HopperMenu
/*    */   extends AbstractContainerMenu
/*    */ {
/*    */   public static final int CONTAINER_SIZE = 5;
/*    */   private final Container hopper;
/*    */   
/*    */   public HopperMenu(int $$0, Inventory $$1) {
/* 16 */     this($$0, $$1, (Container)new SimpleContainer(5));
/*    */   }
/*    */   
/*    */   public HopperMenu(int $$0, Inventory $$1, Container $$2) {
/* 20 */     super(MenuType.HOPPER, $$0);
/* 21 */     this.hopper = $$2;
/* 22 */     checkContainerSize($$2, 5);
/*    */     
/* 24 */     $$2.startOpen($$1.player);
/* 25 */     int $$3 = 51;
/*    */     
/* 27 */     for (int $$4 = 0; $$4 < 5; $$4++) {
/* 28 */       addSlot(new Slot($$2, $$4, 44 + $$4 * 18, 20));
/*    */     }
/*    */     
/* 31 */     for (int $$5 = 0; $$5 < 3; $$5++) {
/* 32 */       for (int $$6 = 0; $$6 < 9; $$6++) {
/* 33 */         addSlot(new Slot((Container)$$1, $$6 + $$5 * 9 + 9, 8 + $$6 * 18, $$5 * 18 + 51));
/*    */       }
/*    */     } 
/* 36 */     for (int $$7 = 0; $$7 < 9; $$7++) {
/* 37 */       addSlot(new Slot((Container)$$1, $$7, 8 + $$7 * 18, 109));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean stillValid(Player $$0) {
/* 43 */     return this.hopper.stillValid($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack quickMoveStack(Player $$0, int $$1) {
/* 48 */     ItemStack $$2 = ItemStack.EMPTY;
/* 49 */     Slot $$3 = (Slot)this.slots.get($$1);
/* 50 */     if ($$3 != null && $$3.hasItem()) {
/* 51 */       ItemStack $$4 = $$3.getItem();
/* 52 */       $$2 = $$4.copy();
/*    */       
/* 54 */       if ($$1 < this.hopper.getContainerSize()) {
/* 55 */         if (!moveItemStackTo($$4, this.hopper.getContainerSize(), this.slots.size(), true)) {
/* 56 */           return ItemStack.EMPTY;
/*    */         }
/*    */       }
/* 59 */       else if (!moveItemStackTo($$4, 0, this.hopper.getContainerSize(), false)) {
/* 60 */         return ItemStack.EMPTY;
/*    */       } 
/*    */       
/* 63 */       if ($$4.isEmpty()) {
/* 64 */         $$3.setByPlayer(ItemStack.EMPTY);
/*    */       } else {
/* 66 */         $$3.setChanged();
/*    */       } 
/*    */     } 
/* 69 */     return $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public void removed(Player $$0) {
/* 74 */     super.removed($$0);
/* 75 */     this.hopper.stopOpen($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\inventory\HopperMenu.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */