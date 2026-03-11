/*    */ package net.minecraft.world.level.block.entity;
/*    */ 
/*    */ import net.minecraft.world.Container;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class null
/*    */   implements Container
/*    */ {
/*    */   public int getContainerSize() {
/* 41 */     return 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isEmpty() {
/* 46 */     return LecternBlockEntity.this.book.isEmpty();
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getItem(int $$0) {
/* 51 */     return ($$0 == 0) ? LecternBlockEntity.this.book : ItemStack.EMPTY;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack removeItem(int $$0, int $$1) {
/* 56 */     if ($$0 == 0) {
/* 57 */       ItemStack $$2 = LecternBlockEntity.this.book.split($$1);
/* 58 */       if (LecternBlockEntity.this.book.isEmpty()) {
/* 59 */         LecternBlockEntity.this.onBookItemRemove();
/*    */       }
/* 61 */       return $$2;
/*    */     } 
/* 63 */     return ItemStack.EMPTY;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack removeItemNoUpdate(int $$0) {
/* 68 */     if ($$0 == 0) {
/* 69 */       ItemStack $$1 = LecternBlockEntity.this.book;
/* 70 */       LecternBlockEntity.this.book = ItemStack.EMPTY;
/* 71 */       LecternBlockEntity.this.onBookItemRemove();
/* 72 */       return $$1;
/*    */     } 
/* 74 */     return ItemStack.EMPTY;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setItem(int $$0, ItemStack $$1) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMaxStackSize() {
/* 84 */     return 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setChanged() {
/* 89 */     LecternBlockEntity.this.setChanged();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean stillValid(Player $$0) {
/* 94 */     return (Container.stillValidBlockEntity(LecternBlockEntity.this, $$0) && LecternBlockEntity.this.hasBook());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canPlaceItem(int $$0, ItemStack $$1) {
/* 99 */     return false;
/*    */   }
/*    */   
/*    */   public void clearContent() {}
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\LecternBlockEntity$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */