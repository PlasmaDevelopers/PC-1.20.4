/*    */ package net.minecraft.client.gui.components;
/*    */ 
/*    */ import net.minecraft.client.Options;
/*    */ import net.minecraft.network.chat.CommonComponents;
/*    */ 
/*    */ public abstract class AbstractOptionSliderButton extends AbstractSliderButton {
/*    */   protected final Options options;
/*    */   
/*    */   protected AbstractOptionSliderButton(Options $$0, int $$1, int $$2, int $$3, int $$4, double $$5) {
/* 10 */     super($$1, $$2, $$3, $$4, CommonComponents.EMPTY, $$5);
/* 11 */     this.options = $$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\AbstractOptionSliderButton.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */