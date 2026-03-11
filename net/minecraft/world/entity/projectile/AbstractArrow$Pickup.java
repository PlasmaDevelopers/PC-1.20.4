/*    */ package net.minecraft.world.entity.projectile;
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
/*    */ public enum Pickup
/*    */ {
/* 58 */   DISALLOWED, ALLOWED, CREATIVE_ONLY;
/*    */   
/*    */   public static Pickup byOrdinal(int $$0) {
/* 61 */     if ($$0 < 0 || $$0 > (values()).length) {
/* 62 */       $$0 = 0;
/*    */     }
/*    */     
/* 65 */     return values()[$$0];
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\projectile\AbstractArrow$Pickup.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */