/*    */ package net.minecraft.world.ticks;
/*    */ 
/*    */ import net.minecraft.world.Container;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ 
/*    */ public interface ContainerSingleItem extends Container {
/*    */   ItemStack getTheItem();
/*    */   
/*    */   ItemStack splitTheItem(int paramInt);
/*    */   
/*    */   void setTheItem(ItemStack paramItemStack);
/*    */   
/*    */   BlockEntity getContainerBlockEntity();
/*    */   
/*    */   default ItemStack removeTheItem() {
/* 18 */     return splitTheItem(getMaxStackSize());
/*    */   }
/*    */ 
/*    */   
/*    */   default int getContainerSize() {
/* 23 */     return 1;
/*    */   }
/*    */ 
/*    */   
/*    */   default boolean isEmpty() {
/* 28 */     return getTheItem().isEmpty();
/*    */   }
/*    */ 
/*    */   
/*    */   default void clearContent() {
/* 33 */     removeTheItem();
/*    */   }
/*    */ 
/*    */   
/*    */   default ItemStack removeItemNoUpdate(int $$0) {
/* 38 */     return removeItem($$0, getMaxStackSize());
/*    */   }
/*    */ 
/*    */   
/*    */   default ItemStack getItem(int $$0) {
/* 43 */     return ($$0 == 0) ? getTheItem() : ItemStack.EMPTY;
/*    */   }
/*    */ 
/*    */   
/*    */   default ItemStack removeItem(int $$0, int $$1) {
/* 48 */     if ($$0 != 0) {
/* 49 */       return ItemStack.EMPTY;
/*    */     }
/* 51 */     return splitTheItem($$1);
/*    */   }
/*    */ 
/*    */   
/*    */   default void setItem(int $$0, ItemStack $$1) {
/* 56 */     if ($$0 == 0) {
/* 57 */       setTheItem($$1);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   default boolean stillValid(Player $$0) {
/* 63 */     return Container.stillValidBlockEntity(getContainerBlockEntity(), $$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\ticks\ContainerSingleItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */