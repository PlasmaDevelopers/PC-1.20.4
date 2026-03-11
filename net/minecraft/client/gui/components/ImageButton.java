/*    */ package net.minecraft.client.gui.components;
/*    */ 
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.network.chat.CommonComponents;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ public class ImageButton extends Button {
/*    */   protected final WidgetSprites sprites;
/*    */   
/*    */   public ImageButton(int $$0, int $$1, int $$2, int $$3, WidgetSprites $$4, Button.OnPress $$5) {
/* 12 */     this($$0, $$1, $$2, $$3, $$4, $$5, CommonComponents.EMPTY);
/*    */   }
/*    */   
/*    */   public ImageButton(int $$0, int $$1, int $$2, int $$3, WidgetSprites $$4, Button.OnPress $$5, Component $$6) {
/* 16 */     super($$0, $$1, $$2, $$3, $$6, $$5, DEFAULT_NARRATION);
/* 17 */     this.sprites = $$4;
/*    */   }
/*    */   
/*    */   public ImageButton(int $$0, int $$1, WidgetSprites $$2, Button.OnPress $$3, Component $$4) {
/* 21 */     this(0, 0, $$0, $$1, $$2, $$3, $$4);
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderWidget(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 26 */     ResourceLocation $$4 = this.sprites.get(isActive(), isHoveredOrFocused());
/* 27 */     $$0.blitSprite($$4, getX(), getY(), this.width, this.height);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\ImageButton.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */