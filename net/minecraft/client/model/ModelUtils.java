/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ 
/*    */ public class ModelUtils
/*    */ {
/*    */   public static float rotlerpRad(float $$0, float $$1, float $$2) {
/*  7 */     float $$3 = $$1 - $$0;
/*  8 */     while ($$3 < -3.1415927F) {
/*  9 */       $$3 += 6.2831855F;
/*    */     }
/* 11 */     while ($$3 >= 3.1415927F) {
/* 12 */       $$3 -= 6.2831855F;
/*    */     }
/* 14 */     return $$0 + $$2 * $$3;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\ModelUtils.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */