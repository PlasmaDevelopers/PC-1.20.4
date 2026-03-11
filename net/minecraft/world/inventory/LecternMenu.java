/*    */ package net.minecraft.world.inventory;
/*    */ 
/*    */ import net.minecraft.world.Container;
/*    */ import net.minecraft.world.SimpleContainer;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ 
/*    */ public class LecternMenu
/*    */   extends AbstractContainerMenu
/*    */ {
/*    */   private static final int DATA_COUNT = 1;
/*    */   private static final int SLOT_COUNT = 1;
/*    */   public static final int BUTTON_PREV_PAGE = 1;
/*    */   public static final int BUTTON_NEXT_PAGE = 2;
/*    */   public static final int BUTTON_TAKE_BOOK = 3;
/*    */   public static final int BUTTON_PAGE_JUMP_RANGE_START = 100;
/*    */   private final Container lectern;
/*    */   private final ContainerData lecternData;
/*    */   
/*    */   public LecternMenu(int $$0) {
/* 22 */     this($$0, (Container)new SimpleContainer(1), new SimpleContainerData(1));
/*    */   }
/*    */   
/*    */   public LecternMenu(int $$0, Container $$1, ContainerData $$2) {
/* 26 */     super(MenuType.LECTERN, $$0);
/* 27 */     checkContainerSize($$1, 1);
/* 28 */     checkContainerDataCount($$2, 1);
/* 29 */     this.lectern = $$1;
/* 30 */     this.lecternData = $$2;
/* 31 */     addSlot(new Slot($$1, 0, 0, 0)
/*    */         {
/*    */           public void setChanged() {
/* 34 */             super.setChanged();
/* 35 */             LecternMenu.this.slotsChanged(this.container);
/*    */           }
/*    */         });
/*    */     
/* 39 */     addDataSlots($$2);
/*    */   } public boolean clickMenuButton(Player $$0, int $$1) {
/*    */     int $$3;
/*    */     int $$4;
/*    */     ItemStack $$5;
/* 44 */     if ($$1 >= 100) {
/* 45 */       int $$2 = $$1 - 100;
/* 46 */       setData(0, $$2);
/* 47 */       return true;
/*    */     } 
/*    */     
/* 50 */     switch ($$1) {
/*    */       case 2:
/* 52 */         $$3 = this.lecternData.get(0);
/* 53 */         setData(0, $$3 + 1);
/* 54 */         return true;
/*    */       
/*    */       case 1:
/* 57 */         $$4 = this.lecternData.get(0);
/* 58 */         setData(0, $$4 - 1);
/* 59 */         return true;
/*    */       
/*    */       case 3:
/* 62 */         if (!$$0.mayBuild()) {
/* 63 */           return false;
/*    */         }
/* 65 */         $$5 = this.lectern.removeItemNoUpdate(0);
/* 66 */         this.lectern.setChanged();
/* 67 */         if (!$$0.getInventory().add($$5)) {
/* 68 */           $$0.drop($$5, false);
/*    */         }
/* 70 */         return true;
/*    */     } 
/*    */     
/* 73 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack quickMoveStack(Player $$0, int $$1) {
/* 78 */     return ItemStack.EMPTY;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setData(int $$0, int $$1) {
/* 83 */     super.setData($$0, $$1);
/* 84 */     broadcastChanges();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean stillValid(Player $$0) {
/* 89 */     return this.lectern.stillValid($$0);
/*    */   }
/*    */   
/*    */   public ItemStack getBook() {
/* 93 */     return this.lectern.getItem(0);
/*    */   }
/*    */   
/*    */   public int getPage() {
/* 97 */     return this.lecternData.get(0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\inventory\LecternMenu.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */