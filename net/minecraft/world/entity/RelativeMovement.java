/*    */ package net.minecraft.world.entity;
/*    */ 
/*    */ import java.util.EnumSet;
/*    */ import java.util.Set;
/*    */ 
/*    */ public enum RelativeMovement {
/*  7 */   X(0),
/*  8 */   Y(1),
/*  9 */   Z(2),
/* 10 */   Y_ROT(3),
/* 11 */   X_ROT(4); public static final Set<RelativeMovement> ALL;
/*    */   
/*    */   static {
/* 14 */     ALL = Set.of(values());
/* 15 */     ROTATION = Set.of(X_ROT, Y_ROT);
/*    */   }
/*    */   public static final Set<RelativeMovement> ROTATION; private final int bit;
/*    */   
/*    */   RelativeMovement(int $$0) {
/* 20 */     this.bit = $$0;
/*    */   }
/*    */   
/*    */   private int getMask() {
/* 24 */     return 1 << this.bit;
/*    */   }
/*    */   
/*    */   private boolean isSet(int $$0) {
/* 28 */     return (($$0 & getMask()) == getMask());
/*    */   }
/*    */   
/*    */   public static Set<RelativeMovement> unpack(int $$0) {
/* 32 */     Set<RelativeMovement> $$1 = EnumSet.noneOf(RelativeMovement.class);
/*    */     
/* 34 */     for (RelativeMovement $$2 : values()) {
/* 35 */       if ($$2.isSet($$0)) {
/* 36 */         $$1.add($$2);
/*    */       }
/*    */     } 
/*    */     
/* 40 */     return $$1;
/*    */   }
/*    */   
/*    */   public static int pack(Set<RelativeMovement> $$0) {
/* 44 */     int $$1 = 0;
/*    */     
/* 46 */     for (RelativeMovement $$2 : $$0) {
/* 47 */       $$1 |= $$2.getMask();
/*    */     }
/*    */     
/* 50 */     return $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\RelativeMovement.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */