/*    */ package net.minecraft.world.level;
/*    */ 
/*    */ public class FoliageColor {
/*  4 */   private static int[] pixels = new int[65536];
/*    */   
/*    */   public static void init(int[] $$0) {
/*  7 */     pixels = $$0;
/*    */   }
/*    */   
/*    */   public static int get(double $$0, double $$1) {
/* 11 */     $$1 *= $$0;
/* 12 */     int $$2 = (int)((1.0D - $$0) * 255.0D);
/* 13 */     int $$3 = (int)((1.0D - $$1) * 255.0D);
/* 14 */     int $$4 = $$3 << 8 | $$2;
/* 15 */     if ($$4 >= pixels.length) {
/* 16 */       return getDefaultColor();
/*    */     }
/* 18 */     return pixels[$$4];
/*    */   }
/*    */   
/*    */   public static int getEvergreenColor() {
/* 22 */     return 6396257;
/*    */   }
/*    */   
/*    */   public static int getBirchColor() {
/* 26 */     return 8431445;
/*    */   }
/*    */   
/*    */   public static int getDefaultColor() {
/* 30 */     return 4764952;
/*    */   }
/*    */   
/*    */   public static int getMangroveColor() {
/* 34 */     return 9619016;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\FoliageColor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */