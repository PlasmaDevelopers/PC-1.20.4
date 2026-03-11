/*    */ package com.mojang.math;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import net.minecraft.Util;
/*    */ import org.joml.Matrix3f;
/*    */ 
/*    */ public enum SymmetricGroup3
/*    */ {
/*  9 */   P123(0, 1, 2),
/* 10 */   P213(1, 0, 2),
/* 11 */   P132(0, 2, 1),
/* 12 */   P231(1, 2, 0),
/* 13 */   P312(2, 0, 1),
/* 14 */   P321(2, 1, 0);
/*    */   private final int[] permutation;
/*    */   private final Matrix3f transformation;
/*    */   private static final int ORDER = 3;
/*    */   private static final SymmetricGroup3[][] cayleyTable;
/*    */   
/*    */   SymmetricGroup3(int $$0, int $$1, int $$2) {
/* 21 */     this.permutation = new int[] { $$0, $$1, $$2 };
/* 22 */     this.transformation = new Matrix3f();
/* 23 */     this.transformation.set(permutation(0), 0, 1.0F);
/* 24 */     this.transformation.set(permutation(1), 1, 1.0F);
/* 25 */     this.transformation.set(permutation(2), 2, 1.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   static {
/* 30 */     cayleyTable = (SymmetricGroup3[][])Util.make(new SymmetricGroup3[(values()).length][(values()).length], $$0 -> {
/*    */           for (SymmetricGroup3 $$1 : values()) {
/*    */             for (SymmetricGroup3 $$2 : values()) {
/*    */               int[] $$3 = new int[3];
/*    */               for (int $$4 = 0; $$4 < 3; $$4++) {
/*    */                 $$3[$$4] = $$1.permutation[$$2.permutation[$$4]];
/*    */               }
/*    */               SymmetricGroup3 $$5 = Arrays.<SymmetricGroup3>stream(values()).filter(()).findFirst().get();
/*    */               $$0[$$1.ordinal()][$$2.ordinal()] = $$5;
/*    */             } 
/*    */           } 
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public SymmetricGroup3 compose(SymmetricGroup3 $$0) {
/* 47 */     return cayleyTable[ordinal()][$$0.ordinal()];
/*    */   }
/*    */   
/*    */   public int permutation(int $$0) {
/* 51 */     return this.permutation[$$0];
/*    */   }
/*    */   
/*    */   public Matrix3f transformation() {
/* 55 */     return this.transformation;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\math\SymmetricGroup3.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */