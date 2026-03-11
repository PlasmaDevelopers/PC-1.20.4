/*    */ package com.mojang.math;
/*    */ @FunctionalInterface
/*    */ public interface Axis {
/*    */   public static final Axis XN;
/*    */   public static final Axis XP;
/*    */   public static final Axis YN;
/*    */   
/*    */   static {
/*  9 */     XN = ($$0 -> (new Quaternionf()).rotationX(-$$0));
/* 10 */     XP = ($$0 -> (new Quaternionf()).rotationX($$0));
/* 11 */     YN = ($$0 -> (new Quaternionf()).rotationY(-$$0));
/* 12 */     YP = ($$0 -> (new Quaternionf()).rotationY($$0));
/* 13 */     ZN = ($$0 -> (new Quaternionf()).rotationZ(-$$0));
/* 14 */     ZP = ($$0 -> (new Quaternionf()).rotationZ($$0));
/*    */   } public static final Axis YP; public static final Axis ZN; public static final Axis ZP;
/*    */   static Axis of(Vector3f $$0) {
/* 17 */     return $$1 -> (new Quaternionf()).rotationAxis($$1, (Vector3fc)$$0);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   default Quaternionf rotationDegrees(float $$0) {
/* 23 */     return rotation($$0 * 0.017453292F);
/*    */   }
/*    */   
/*    */   Quaternionf rotation(float paramFloat);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\math\Axis.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */