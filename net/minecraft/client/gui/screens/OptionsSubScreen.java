/*    */ package net.minecraft.client.gui.screens;
/*    */ 
/*    */ import net.minecraft.client.Options;
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ public class OptionsSubScreen extends Screen {
/*    */   protected final Screen lastScreen;
/*    */   protected final Options options;
/*    */   
/*    */   public OptionsSubScreen(Screen $$0, Options $$1, Component $$2) {
/* 11 */     super($$2);
/* 12 */     this.lastScreen = $$0;
/* 13 */     this.options = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public void removed() {
/* 18 */     this.minecraft.options.save();
/*    */   }
/*    */ 
/*    */   
/*    */   public void onClose() {
/* 23 */     this.minecraft.setScreen(this.lastScreen);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\OptionsSubScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */