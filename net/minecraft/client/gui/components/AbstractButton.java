/*    */ package net.minecraft.client.gui.components;
/*    */ 
/*    */ import com.mojang.blaze3d.systems.RenderSystem;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.Font;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.client.gui.navigation.CommonInputs;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.util.Mth;
/*    */ 
/*    */ public abstract class AbstractButton
/*    */   extends AbstractWidget {
/*    */   protected static final int TEXT_MARGIN = 2;
/* 15 */   private static final WidgetSprites SPRITES = new WidgetSprites(new ResourceLocation("widget/button"), new ResourceLocation("widget/button_disabled"), new ResourceLocation("widget/button_highlighted"));
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AbstractButton(int $$0, int $$1, int $$2, int $$3, Component $$4) {
/* 22 */     super($$0, $$1, $$2, $$3, $$4);
/*    */   }
/*    */ 
/*    */   
/*    */   public abstract void onPress();
/*    */   
/*    */   protected void renderWidget(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 29 */     Minecraft $$4 = Minecraft.getInstance();
/*    */     
/* 31 */     $$0.setColor(1.0F, 1.0F, 1.0F, this.alpha);
/*    */     
/* 33 */     RenderSystem.enableBlend();
/* 34 */     RenderSystem.enableDepthTest();
/* 35 */     $$0.blitSprite(SPRITES.get(this.active, isHoveredOrFocused()), getX(), getY(), getWidth(), getHeight());
/*    */     
/* 37 */     $$0.setColor(1.0F, 1.0F, 1.0F, 1.0F);
/* 38 */     int $$5 = this.active ? 16777215 : 10526880;
/* 39 */     renderString($$0, $$4.font, $$5 | Mth.ceil(this.alpha * 255.0F) << 24);
/*    */   }
/*    */   
/*    */   public void renderString(GuiGraphics $$0, Font $$1, int $$2) {
/* 43 */     renderScrollingString($$0, $$1, 2, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onClick(double $$0, double $$1) {
/* 48 */     onPress();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean keyPressed(int $$0, int $$1, int $$2) {
/* 53 */     if (!this.active || !this.visible) {
/* 54 */       return false;
/*    */     }
/* 56 */     if (CommonInputs.selected($$0)) {
/* 57 */       playDownSound(Minecraft.getInstance().getSoundManager());
/* 58 */       onPress();
/* 59 */       return true;
/*    */     } 
/* 61 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\AbstractButton.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */