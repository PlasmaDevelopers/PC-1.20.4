/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ import net.minecraft.world.entity.EquipmentSlot;
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
/*    */ public enum Type
/*    */ {
/* 66 */   HELMET(EquipmentSlot.HEAD, "helmet"),
/* 67 */   CHESTPLATE(EquipmentSlot.CHEST, "chestplate"),
/* 68 */   LEGGINGS(EquipmentSlot.LEGS, "leggings"),
/* 69 */   BOOTS(EquipmentSlot.FEET, "boots");
/*    */   
/*    */   private final EquipmentSlot slot;
/*    */   private final String name;
/*    */   
/*    */   Type(EquipmentSlot $$0, String $$1) {
/* 75 */     this.slot = $$0;
/* 76 */     this.name = $$1;
/*    */   }
/*    */   
/*    */   public EquipmentSlot getSlot() {
/* 80 */     return this.slot;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 84 */     return this.name;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\ArmorItem$Type.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */