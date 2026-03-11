/*    */ package net.minecraft.world.inventory;
/*    */ 
/*    */ import net.minecraft.tags.ItemTags;
/*    */ import net.minecraft.world.SimpleContainer;
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
/*    */ class null
/*    */   extends SimpleContainer
/*    */ {
/*    */   null(int $$1) {
/* 29 */     super($$1);
/*    */   }
/*    */   public boolean canPlaceItem(int $$0, ItemStack $$1) {
/* 32 */     return $$1.is(ItemTags.BEACON_PAYMENT_ITEMS);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxStackSize() {
/* 37 */     return 1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\inventory\BeaconMenu$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */