/*    */ package net.minecraft.world.level.border;
/*    */ 
/*    */ public enum BorderStatus {
/*  4 */   GROWING(4259712),
/*  5 */   SHRINKING(16724016),
/*  6 */   STATIONARY(2138367);
/*    */   
/*    */   private final int color;
/*    */ 
/*    */   
/*    */   BorderStatus(int $$0) {
/* 12 */     this.color = $$0;
/*    */   }
/*    */   
/*    */   public int getColor() {
/* 16 */     return this.color;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\border\BorderStatus.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */