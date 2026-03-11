/*    */ package net.minecraft.world.item.enchantment;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum Type
/*    */ {
/* 11 */   ALL(1, 11),
/* 12 */   FIRE(10, 8),
/* 13 */   FALL(5, 6),
/* 14 */   EXPLOSION(5, 8),
/* 15 */   PROJECTILE(3, 6);
/*    */   
/*    */   private final int minCost;
/*    */   private final int levelCost;
/*    */   
/*    */   Type(int $$0, int $$1) {
/* 21 */     this.minCost = $$0;
/* 22 */     this.levelCost = $$1;
/*    */   }
/*    */   
/*    */   public int getMinCost() {
/* 26 */     return this.minCost;
/*    */   }
/*    */   
/*    */   public int getLevelCost() {
/* 30 */     return this.levelCost;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\enchantment\ProtectionEnchantment$Type.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */