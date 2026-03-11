/*    */ package net.minecraft.world.entity;
/*    */ 
/*    */ import java.util.function.Predicate;
/*    */ import net.minecraft.world.Container;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ public interface SlotAccess
/*    */ {
/*  9 */   public static final SlotAccess NULL = new SlotAccess()
/*    */     {
/*    */       public ItemStack get() {
/* 12 */         return ItemStack.EMPTY;
/*    */       }
/*    */ 
/*    */       
/*    */       public boolean set(ItemStack $$0) {
/* 17 */         return false;
/*    */       }
/*    */     };
/*    */   
/*    */   static SlotAccess forContainer(final Container inventory, final int id, final Predicate<ItemStack> validator) {
/* 22 */     return new SlotAccess()
/*    */       {
/*    */         public ItemStack get() {
/* 25 */           return inventory.getItem(id);
/*    */         }
/*    */ 
/*    */         
/*    */         public boolean set(ItemStack $$0) {
/* 30 */           if (!validator.test($$0)) {
/* 31 */             return false;
/*    */           }
/* 33 */           inventory.setItem(id, $$0);
/* 34 */           return true;
/*    */         }
/*    */       };
/*    */   }
/*    */   
/*    */   static SlotAccess forContainer(Container $$0, int $$1) {
/* 40 */     return forContainer($$0, $$1, $$0 -> true);
/*    */   }
/*    */   
/*    */   static SlotAccess forEquipmentSlot(final LivingEntity entity, final EquipmentSlot slot, final Predicate<ItemStack> validator) {
/* 44 */     return new SlotAccess()
/*    */       {
/*    */         public ItemStack get() {
/* 47 */           return entity.getItemBySlot(slot);
/*    */         }
/*    */ 
/*    */         
/*    */         public boolean set(ItemStack $$0) {
/* 52 */           if (!validator.test($$0)) {
/* 53 */             return false;
/*    */           }
/*    */           
/* 56 */           entity.setItemSlot(slot, $$0);
/* 57 */           return true;
/*    */         }
/*    */       };
/*    */   }
/*    */   
/*    */   static SlotAccess forEquipmentSlot(LivingEntity $$0, EquipmentSlot $$1) {
/* 63 */     return forEquipmentSlot($$0, $$1, $$0 -> true);
/*    */   }
/*    */   
/*    */   ItemStack get();
/*    */   
/*    */   boolean set(ItemStack paramItemStack);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\SlotAccess.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */