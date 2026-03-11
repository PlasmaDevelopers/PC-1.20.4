/*    */ package net.minecraft.world.inventory;
/*    */ 
/*    */ import net.minecraft.world.Container;
/*    */ import net.minecraft.world.SimpleContainer;
/*    */ import net.minecraft.world.entity.player.Inventory;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ public class ShulkerBoxMenu
/*    */   extends AbstractContainerMenu
/*    */ {
/*    */   private static final int CONTAINER_SIZE = 27;
/*    */   private final Container container;
/*    */   
/*    */   public ShulkerBoxMenu(int $$0, Inventory $$1) {
/* 16 */     this($$0, $$1, (Container)new SimpleContainer(27));
/*    */   }
/*    */   
/*    */   public ShulkerBoxMenu(int $$0, Inventory $$1, Container $$2) {
/* 20 */     super(MenuType.SHULKER_BOX, $$0);
/* 21 */     checkContainerSize($$2, 27);
/* 22 */     this.container = $$2;
/* 23 */     $$2.startOpen($$1.player);
/*    */     
/* 25 */     int $$3 = 3;
/* 26 */     int $$4 = 9;
/*    */     
/* 28 */     for (int $$5 = 0; $$5 < 3; $$5++) {
/* 29 */       for (int $$6 = 0; $$6 < 9; $$6++) {
/* 30 */         addSlot(new ShulkerBoxSlot($$2, $$6 + $$5 * 9, 8 + $$6 * 18, 18 + $$5 * 18));
/*    */       }
/*    */     } 
/*    */     
/* 34 */     for (int $$7 = 0; $$7 < 3; $$7++) {
/* 35 */       for (int $$8 = 0; $$8 < 9; $$8++) {
/* 36 */         addSlot(new Slot((Container)$$1, $$8 + $$7 * 9 + 9, 8 + $$8 * 18, 84 + $$7 * 18));
/*    */       }
/*    */     } 
/* 39 */     for (int $$9 = 0; $$9 < 9; $$9++) {
/* 40 */       addSlot(new Slot((Container)$$1, $$9, 8 + $$9 * 18, 142));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean stillValid(Player $$0) {
/* 46 */     return this.container.stillValid($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack quickMoveStack(Player $$0, int $$1) {
/* 51 */     ItemStack $$2 = ItemStack.EMPTY;
/* 52 */     Slot $$3 = (Slot)this.slots.get($$1);
/* 53 */     if ($$3 != null && $$3.hasItem()) {
/* 54 */       ItemStack $$4 = $$3.getItem();
/* 55 */       $$2 = $$4.copy();
/*    */       
/* 57 */       if ($$1 < this.container.getContainerSize()) {
/* 58 */         if (!moveItemStackTo($$4, this.container.getContainerSize(), this.slots.size(), true)) {
/* 59 */           return ItemStack.EMPTY;
/*    */         }
/*    */       }
/* 62 */       else if (!moveItemStackTo($$4, 0, this.container.getContainerSize(), false)) {
/* 63 */         return ItemStack.EMPTY;
/*    */       } 
/*    */       
/* 66 */       if ($$4.isEmpty()) {
/* 67 */         $$3.setByPlayer(ItemStack.EMPTY);
/*    */       } else {
/* 69 */         $$3.setChanged();
/*    */       } 
/*    */     } 
/* 72 */     return $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public void removed(Player $$0) {
/* 77 */     super.removed($$0);
/* 78 */     this.container.stopOpen($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\inventory\ShulkerBoxMenu.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */