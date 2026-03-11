/*    */ package net.minecraft.world.entity.npc;
/*    */ 
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.nbt.Tag;
/*    */ import net.minecraft.world.SimpleContainer;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.Mob;
/*    */ import net.minecraft.world.entity.item.ItemEntity;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ public interface InventoryCarrier
/*    */ {
/*    */   public static final String TAG_INVENTORY = "Inventory";
/*    */   
/*    */   static void pickUpItem(Mob $$0, InventoryCarrier $$1, ItemEntity $$2) {
/* 16 */     ItemStack $$3 = $$2.getItem();
/* 17 */     if ($$0.wantsToPickUp($$3)) {
/* 18 */       SimpleContainer $$4 = $$1.getInventory();
/* 19 */       boolean $$5 = $$4.canAddItem($$3);
/* 20 */       if (!$$5) {
/*    */         return;
/*    */       }
/*    */       
/* 24 */       $$0.onItemPickup($$2);
/* 25 */       int $$6 = $$3.getCount();
/* 26 */       ItemStack $$7 = $$4.addItem($$3);
/* 27 */       $$0.take((Entity)$$2, $$6 - $$7.getCount());
/* 28 */       if ($$7.isEmpty()) {
/* 29 */         $$2.discard();
/*    */       } else {
/* 31 */         $$3.setCount($$7.getCount());
/*    */       } 
/*    */     } 
/*    */   }
/*    */   SimpleContainer getInventory();
/*    */   default void readInventoryFromTag(CompoundTag $$0) {
/* 37 */     if ($$0.contains("Inventory", 9)) {
/* 38 */       getInventory().fromTag($$0.getList("Inventory", 10));
/*    */     }
/*    */   }
/*    */   
/*    */   default void writeInventoryToTag(CompoundTag $$0) {
/* 43 */     $$0.put("Inventory", (Tag)getInventory().createTag());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\npc\InventoryCarrier.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */