/*    */ package net.minecraft.util;
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
/*    */ 
/*    */ 
/*    */ public class ABGR32
/*    */ {
/*    */   public static int alpha(int $$0) {
/* 47 */     return $$0 >>> 24;
/*    */   }
/*    */   
/*    */   public static int red(int $$0) {
/* 51 */     return $$0 & 0xFF;
/*    */   }
/*    */   
/*    */   public static int green(int $$0) {
/* 55 */     return $$0 >> 8 & 0xFF;
/*    */   }
/*    */   
/*    */   public static int blue(int $$0) {
/* 59 */     return $$0 >> 16 & 0xFF;
/*    */   }
/*    */   
/*    */   public static int transparent(int $$0) {
/* 63 */     return $$0 & 0xFFFFFF;
/*    */   }
/*    */   
/*    */   public static int opaque(int $$0) {
/* 67 */     return $$0 | 0xFF000000;
/*    */   }
/*    */   
/*    */   public static int color(int $$0, int $$1, int $$2, int $$3) {
/* 71 */     return $$0 << 24 | $$1 << 16 | $$2 << 8 | $$3;
/*    */   }
/*    */   
/*    */   public static int color(int $$0, int $$1) {
/* 75 */     return $$0 << 24 | $$1 & 0xFFFFFF;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\FastColor$ABGR32.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */