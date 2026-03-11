/*    */ package com.mojang.realmsclient;
/*    */ 
/*    */ import java.util.Locale;
/*    */ 
/*    */ public enum Unit {
/*  6 */   B,
/*  7 */   KB,
/*  8 */   MB,
/*  9 */   GB;
/*    */   
/*    */   private static final int BASE_UNIT = 1024;
/*    */ 
/*    */   
/*    */   public static Unit getLargest(long $$0) {
/* 15 */     if ($$0 < 1024L) {
/* 16 */       return B;
/*    */     }
/*    */     
/*    */     try {
/* 20 */       int $$1 = (int)(Math.log($$0) / Math.log(1024.0D));
/* 21 */       String $$2 = String.valueOf("KMGTPE".charAt($$1 - 1));
/*    */       
/* 23 */       return valueOf($$2 + "B");
/* 24 */     } catch (Exception $$3) {
/* 25 */       return GB;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static double convertTo(long $$0, Unit $$1) {
/* 30 */     if ($$1 == B) {
/* 31 */       return $$0;
/*    */     }
/*    */     
/* 34 */     return $$0 / Math.pow(1024.0D, $$1.ordinal());
/*    */   }
/*    */   
/*    */   public static String humanReadable(long $$0) {
/* 38 */     int $$1 = 1024;
/* 39 */     if ($$0 < 1024L) {
/* 40 */       return "" + $$0 + " B";
/*    */     }
/* 42 */     int $$2 = (int)(Math.log($$0) / Math.log(1024.0D));
/* 43 */     String $$3 = "" + "KMGTPE".charAt($$2 - 1);
/* 44 */     return String.format(Locale.ROOT, "%.1f %sB", new Object[] { Double.valueOf($$0 / Math.pow(1024.0D, $$2)), $$3 });
/*    */   }
/*    */   
/*    */   public static String humanReadable(long $$0, Unit $$1) {
/* 48 */     return String.format(Locale.ROOT, "%." + (($$1 == GB) ? "1" : "0") + "f %s", new Object[] { Double.valueOf(convertTo($$0, $$1)), $$1.name() });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\Unit.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */