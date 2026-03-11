/*    */ package com.mojang.blaze3d.vertex;
/*    */ 
/*    */ public abstract class DefaultedVertexConsumer implements VertexConsumer {
/*    */   protected boolean defaultColorSet;
/*  5 */   protected int defaultR = 255;
/*  6 */   protected int defaultG = 255;
/*  7 */   protected int defaultB = 255;
/*  8 */   protected int defaultA = 255;
/*    */ 
/*    */   
/*    */   public void defaultColor(int $$0, int $$1, int $$2, int $$3) {
/* 12 */     this.defaultR = $$0;
/* 13 */     this.defaultG = $$1;
/* 14 */     this.defaultB = $$2;
/* 15 */     this.defaultA = $$3;
/* 16 */     this.defaultColorSet = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void unsetDefaultColor() {
/* 21 */     this.defaultColorSet = false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\vertex\DefaultedVertexConsumer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */