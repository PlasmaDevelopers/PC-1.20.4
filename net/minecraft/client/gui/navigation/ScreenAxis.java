/*    */ package net.minecraft.client.gui.navigation;
/*    */ 
/*    */ public enum ScreenAxis {
/*  4 */   HORIZONTAL,
/*  5 */   VERTICAL;
/*    */ 
/*    */   
/*    */   public ScreenAxis orthogonal() {
/*  9 */     switch (this) { default: throw new IncompatibleClassChangeError();case HORIZONTAL: case VERTICAL: break; }  return 
/*    */       
/* 11 */       HORIZONTAL;
/*    */   }
/*    */ 
/*    */   
/*    */   public ScreenDirection getPositive() {
/* 16 */     switch (this) { default: throw new IncompatibleClassChangeError();case HORIZONTAL: case VERTICAL: break; }  return 
/*    */       
/* 18 */       ScreenDirection.DOWN;
/*    */   }
/*    */ 
/*    */   
/*    */   public ScreenDirection getNegative() {
/* 23 */     switch (this) { default: throw new IncompatibleClassChangeError();case HORIZONTAL: case VERTICAL: break; }  return 
/*    */       
/* 25 */       ScreenDirection.UP;
/*    */   }
/*    */ 
/*    */   
/*    */   public ScreenDirection getDirection(boolean $$0) {
/* 30 */     return $$0 ? getPositive() : getNegative();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\navigation\ScreenAxis.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */