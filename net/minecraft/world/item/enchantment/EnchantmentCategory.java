/*    */ package net.minecraft.world.item.enchantment;
/*    */ 
/*    */ import net.minecraft.world.entity.EquipmentSlot;
/*    */ import net.minecraft.world.item.ArmorItem;
/*    */ import net.minecraft.world.item.Item;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum EnchantmentCategory
/*    */ {
/* 17 */   ARMOR
/*    */   {
/*    */     public boolean canEnchant(Item $$0) {
/* 20 */       return $$0 instanceof ArmorItem;
/*    */     }
/*    */   },
/* 23 */   ARMOR_FEET
/*    */   {
/*    */     public boolean canEnchant(Item $$0) {
/* 26 */       if ($$0 instanceof ArmorItem) { ArmorItem $$1 = (ArmorItem)$$0; if ($$1.getEquipmentSlot() == EquipmentSlot.FEET); }  return false;
/*    */     }
/*    */   },
/* 29 */   ARMOR_LEGS
/*    */   {
/*    */     public boolean canEnchant(Item $$0) {
/* 32 */       if ($$0 instanceof ArmorItem) { ArmorItem $$1 = (ArmorItem)$$0; if ($$1.getEquipmentSlot() == EquipmentSlot.LEGS); }  return false;
/*    */     }
/*    */   },
/* 35 */   ARMOR_CHEST
/*    */   {
/*    */     public boolean canEnchant(Item $$0) {
/* 38 */       if ($$0 instanceof ArmorItem) { ArmorItem $$1 = (ArmorItem)$$0; if ($$1.getEquipmentSlot() == EquipmentSlot.CHEST); }  return false;
/*    */     }
/*    */   },
/* 41 */   ARMOR_HEAD
/*    */   {
/*    */     public boolean canEnchant(Item $$0) {
/* 44 */       if ($$0 instanceof ArmorItem) { ArmorItem $$1 = (ArmorItem)$$0; if ($$1.getEquipmentSlot() == EquipmentSlot.HEAD); }  return false;
/*    */     }
/*    */   },
/* 47 */   WEAPON
/*    */   {
/*    */     public boolean canEnchant(Item $$0) {
/* 50 */       return $$0 instanceof net.minecraft.world.item.SwordItem;
/*    */     }
/*    */   },
/* 53 */   DIGGER
/*    */   {
/*    */     public boolean canEnchant(Item $$0) {
/* 56 */       return $$0 instanceof net.minecraft.world.item.DiggerItem;
/*    */     }
/*    */   },
/* 59 */   FISHING_ROD
/*    */   {
/*    */     public boolean canEnchant(Item $$0) {
/* 62 */       return $$0 instanceof net.minecraft.world.item.FishingRodItem;
/*    */     }
/*    */   },
/* 65 */   TRIDENT
/*    */   {
/*    */     public boolean canEnchant(Item $$0) {
/* 68 */       return $$0 instanceof net.minecraft.world.item.TridentItem;
/*    */     }
/*    */   },
/* 71 */   BREAKABLE
/*    */   {
/*    */     public boolean canEnchant(Item $$0) {
/* 74 */       return $$0.canBeDepleted();
/*    */     }
/*    */   },
/* 77 */   BOW
/*    */   {
/*    */     public boolean canEnchant(Item $$0) {
/* 80 */       return $$0 instanceof net.minecraft.world.item.BowItem;
/*    */     }
/*    */   },
/* 83 */   WEARABLE
/*    */   {
/*    */     public boolean canEnchant(Item $$0) {
/* 86 */       return ($$0 instanceof net.minecraft.world.item.Equipable || Block.byItem($$0) instanceof net.minecraft.world.item.Equipable);
/*    */     }
/*    */   },
/* 89 */   CROSSBOW
/*    */   {
/*    */     public boolean canEnchant(Item $$0) {
/* 92 */       return $$0 instanceof net.minecraft.world.item.CrossbowItem;
/*    */     }
/*    */   },
/* 95 */   VANISHABLE
/*    */   {
/*    */     public boolean canEnchant(Item $$0) {
/* 98 */       return ($$0 instanceof net.minecraft.world.item.Vanishable || Block.byItem($$0) instanceof net.minecraft.world.item.Vanishable || BREAKABLE.canEnchant($$0));
/*    */     }
/*    */   };
/*    */   
/*    */   public abstract boolean canEnchant(Item paramItem);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\enchantment\EnchantmentCategory.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */