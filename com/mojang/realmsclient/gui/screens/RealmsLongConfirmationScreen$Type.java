/*    */ package com.mojang.realmsclient.gui.screens;
/*    */ 
/*    */ import net.minecraft.network.chat.Component;
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
/*    */ public enum Type
/*    */ {
/* 68 */   WARNING(RealmsLongConfirmationScreen.WARNING, -65536),
/* 69 */   INFO(RealmsLongConfirmationScreen.INFO, 8226750);
/*    */   
/*    */   Type(Component $$0, int $$1) {
/* 72 */     this.text = $$0;
/* 73 */     this.colorCode = $$1;
/*    */   }
/*    */   
/*    */   public final int colorCode;
/*    */   public final Component text;
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\gui\screens\RealmsLongConfirmationScreen$Type.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */