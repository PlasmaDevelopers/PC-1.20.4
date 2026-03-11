/*    */ package com.mojang.blaze3d.shaders;
/*    */ 
/*    */ public enum FogShape {
/*  4 */   SPHERE(0),
/*  5 */   CYLINDER(1);
/*    */   
/*    */   private final int index;
/*    */   
/*    */   FogShape(int $$0) {
/* 10 */     this.index = $$0;
/*    */   }
/*    */   
/*    */   public int getIndex() {
/* 14 */     return this.index;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\shaders\FogShape.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */