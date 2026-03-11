/*    */ package net.minecraft.client.gui.screens;
/*    */ 
/*    */ public class LoadingDotsText {
/*  4 */   private static final String[] FRAMES = new String[] { "O o o", "o O o", "o o O", "o O o" };
/*    */ 
/*    */ 
/*    */   
/*    */   private static final long INTERVAL_MS = 300L;
/*    */ 
/*    */ 
/*    */   
/*    */   public static String get(long $$0) {
/* 13 */     int $$1 = (int)($$0 / 300L % FRAMES.length);
/* 14 */     return FRAMES[$$1];
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\LoadingDotsText.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */