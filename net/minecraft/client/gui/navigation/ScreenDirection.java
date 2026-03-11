/*    */ package net.minecraft.client.gui.navigation;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.ints.IntComparator;
/*    */ 
/*    */ public enum ScreenDirection {
/*  6 */   UP,
/*  7 */   DOWN,
/*  8 */   LEFT,
/*  9 */   RIGHT;
/*    */   
/*    */   ScreenDirection() {
/* 12 */     this.coordinateValueComparator = (($$0, $$1) -> ($$0 == $$1) ? 0 : (isBefore($$0, $$1) ? -1 : 1));
/*    */   } private final IntComparator coordinateValueComparator;
/*    */   public ScreenAxis getAxis() {
/* 15 */     switch (this) { default: throw new IncompatibleClassChangeError();case UP: case DOWN: case LEFT: case RIGHT: break; }  return 
/*    */       
/* 17 */       ScreenAxis.HORIZONTAL;
/*    */   }
/*    */ 
/*    */   
/*    */   public ScreenDirection getOpposite() {
/* 22 */     switch (this) { default: throw new IncompatibleClassChangeError();case UP: case DOWN: case LEFT: case RIGHT: break; }  return 
/*    */ 
/*    */ 
/*    */       
/* 26 */       LEFT;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isPositive() {
/* 31 */     switch (this) { default: throw new IncompatibleClassChangeError();case UP: case LEFT: case DOWN: case RIGHT: break; }  return true;
/*    */   }
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
/*    */   public boolean isAfter(int $$0, int $$1) {
/* 44 */     if (isPositive()) {
/* 45 */       return ($$0 > $$1);
/*    */     }
/* 47 */     return ($$1 > $$0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isBefore(int $$0, int $$1) {
/* 58 */     if (isPositive()) {
/* 59 */       return ($$0 < $$1);
/*    */     }
/* 61 */     return ($$1 < $$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public IntComparator coordinateValueComparator() {
/* 66 */     return this.coordinateValueComparator;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\navigation\ScreenDirection.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */