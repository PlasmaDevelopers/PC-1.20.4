/*    */ package net.minecraft.client.gui.layouts;
/*    */ 
/*    */ import java.util.function.Consumer;
/*    */ import net.minecraft.client.gui.Font;
/*    */ import net.minecraft.client.gui.components.StringWidget;
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommonLayouts
/*    */ {
/*    */   private static final int LABEL_SPACING = 4;
/*    */   
/*    */   public static Layout labeledElement(Font $$0, LayoutElement $$1, Component $$2) {
/* 16 */     return labeledElement($$0, $$1, $$2, $$0 -> {
/*    */         
/*    */         });
/*    */   } public static Layout labeledElement(Font $$0, LayoutElement $$1, Component $$2, Consumer<LayoutSettings> $$3) {
/* 20 */     LinearLayout $$4 = LinearLayout.vertical().spacing(4);
/* 21 */     $$4.addChild(new StringWidget($$2, $$0));
/* 22 */     $$4.addChild($$1, $$3);
/* 23 */     return $$4;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\layouts\CommonLayouts.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */