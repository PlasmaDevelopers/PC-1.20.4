/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ public class TieredItem extends Item {
/*    */   private final Tier tier;
/*    */   
/*    */   public TieredItem(Tier $$0, Item.Properties $$1) {
/*  7 */     super($$1.defaultDurability($$0.getUses()));
/*  8 */     this.tier = $$0;
/*    */   }
/*    */   
/*    */   public Tier getTier() {
/* 12 */     return this.tier;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getEnchantmentValue() {
/* 17 */     return this.tier.getEnchantmentValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isValidRepairItem(ItemStack $$0, ItemStack $$1) {
/* 22 */     return (this.tier.getRepairIngredient().test($$1) || super.isValidRepairItem($$0, $$1));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\TieredItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */