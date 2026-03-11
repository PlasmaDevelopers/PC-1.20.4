/*    */ package net.minecraft.world.item.enchantment;
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
/*    */ public enum Rarity
/*    */ {
/* 30 */   COMMON(10),
/* 31 */   UNCOMMON(5),
/* 32 */   RARE(2),
/* 33 */   VERY_RARE(1);
/*    */   
/*    */   private final int weight;
/*    */   
/*    */   Rarity(int $$0) {
/* 38 */     this.weight = $$0;
/*    */   }
/*    */   
/*    */   public int getWeight() {
/* 42 */     return this.weight;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\enchantment\Enchantment$Rarity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */