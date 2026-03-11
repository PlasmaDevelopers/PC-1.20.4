/*    */ package net.minecraft.world.phys.shapes;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.doubles.AbstractDoubleList;
/*    */ 
/*    */ public class CubePointRange extends AbstractDoubleList {
/*    */   private final int parts;
/*    */   
/*    */   CubePointRange(int $$0) {
/*  9 */     if ($$0 <= 0) {
/* 10 */       throw new IllegalArgumentException("Need at least 1 part");
/*    */     }
/* 12 */     this.parts = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getDouble(int $$0) {
/* 17 */     return $$0 / this.parts;
/*    */   }
/*    */ 
/*    */   
/*    */   public int size() {
/* 22 */     return this.parts + 1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\phys\shapes\CubePointRange.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */