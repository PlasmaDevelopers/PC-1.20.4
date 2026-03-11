/*    */ package net.minecraft.util;
/*    */ 
/*    */ public class FastColor {
/*    */   public static class ARGB32 {
/*    */     public static int alpha(int $$0) {
/*  6 */       return $$0 >>> 24;
/*    */     }
/*    */     
/*    */     public static int red(int $$0) {
/* 10 */       return $$0 >> 16 & 0xFF;
/*    */     }
/*    */     
/*    */     public static int green(int $$0) {
/* 14 */       return $$0 >> 8 & 0xFF;
/*    */     }
/*    */     
/*    */     public static int blue(int $$0) {
/* 18 */       return $$0 & 0xFF;
/*    */     }
/*    */     
/*    */     public static int color(int $$0, int $$1, int $$2, int $$3) {
/* 22 */       return $$0 << 24 | $$1 << 16 | $$2 << 8 | $$3;
/*    */     }
/*    */     
/*    */     public static int multiply(int $$0, int $$1) {
/* 26 */       return color(
/* 27 */           alpha($$0) * alpha($$1) / 255, 
/* 28 */           red($$0) * red($$1) / 255, 
/* 29 */           green($$0) * green($$1) / 255, 
/* 30 */           blue($$0) * blue($$1) / 255);
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public static int lerp(float $$0, int $$1, int $$2) {
/* 37 */       int $$3 = Mth.lerpInt($$0, alpha($$1), alpha($$2));
/* 38 */       int $$4 = Mth.lerpInt($$0, red($$1), red($$2));
/* 39 */       int $$5 = Mth.lerpInt($$0, green($$1), green($$2));
/* 40 */       int $$6 = Mth.lerpInt($$0, blue($$1), blue($$2));
/* 41 */       return color($$3, $$4, $$5, $$6);
/*    */     }
/*    */   }
/*    */   
/*    */   public static class ABGR32 {
/*    */     public static int alpha(int $$0) {
/* 47 */       return $$0 >>> 24;
/*    */     }
/*    */     
/*    */     public static int red(int $$0) {
/* 51 */       return $$0 & 0xFF;
/*    */     }
/*    */     
/*    */     public static int green(int $$0) {
/* 55 */       return $$0 >> 8 & 0xFF;
/*    */     }
/*    */     
/*    */     public static int blue(int $$0) {
/* 59 */       return $$0 >> 16 & 0xFF;
/*    */     }
/*    */     
/*    */     public static int transparent(int $$0) {
/* 63 */       return $$0 & 0xFFFFFF;
/*    */     }
/*    */     
/*    */     public static int opaque(int $$0) {
/* 67 */       return $$0 | 0xFF000000;
/*    */     }
/*    */     
/*    */     public static int color(int $$0, int $$1, int $$2, int $$3) {
/* 71 */       return $$0 << 24 | $$1 << 16 | $$2 << 8 | $$3;
/*    */     }
/*    */     
/*    */     public static int color(int $$0, int $$1) {
/* 75 */       return $$0 << 24 | $$1 & 0xFFFFFF;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\FastColor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */