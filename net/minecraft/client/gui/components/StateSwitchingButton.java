/*    */ package net.minecraft.client.gui.components;
/*    */ 
/*    */ import com.mojang.blaze3d.systems.RenderSystem;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.client.gui.narration.NarrationElementOutput;
/*    */ import net.minecraft.network.chat.CommonComponents;
/*    */ 
/*    */ public class StateSwitchingButton
/*    */   extends AbstractWidget {
/*    */   @Nullable
/*    */   protected WidgetSprites sprites;
/*    */   protected boolean isStateTriggered;
/*    */   
/*    */   public StateSwitchingButton(int $$0, int $$1, int $$2, int $$3, boolean $$4) {
/* 16 */     super($$0, $$1, $$2, $$3, CommonComponents.EMPTY);
/* 17 */     this.isStateTriggered = $$4;
/*    */   }
/*    */   
/*    */   public void initTextureValues(WidgetSprites $$0) {
/* 21 */     this.sprites = $$0;
/*    */   }
/*    */   
/*    */   public void setStateTriggered(boolean $$0) {
/* 25 */     this.isStateTriggered = $$0;
/*    */   }
/*    */   
/*    */   public boolean isStateTriggered() {
/* 29 */     return this.isStateTriggered;
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateWidgetNarration(NarrationElementOutput $$0) {
/* 34 */     defaultButtonNarrationText($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderWidget(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 39 */     if (this.sprites == null) {
/*    */       return;
/*    */     }
/* 42 */     RenderSystem.disableDepthTest();
/* 43 */     $$0.blitSprite(this.sprites.get(this.isStateTriggered, isHoveredOrFocused()), getX(), getY(), this.width, this.height);
/* 44 */     RenderSystem.enableDepthTest();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\StateSwitchingButton.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */