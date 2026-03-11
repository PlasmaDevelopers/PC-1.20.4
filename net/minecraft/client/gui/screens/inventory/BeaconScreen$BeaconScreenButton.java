/*     */ package net.minecraft.client.gui.screens.inventory;
/*     */ 
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.AbstractButton;
/*     */ import net.minecraft.client.gui.narration.NarrationElementOutput;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class BeaconScreenButton
/*     */   extends AbstractButton
/*     */   implements BeaconScreen.BeaconButton
/*     */ {
/*     */   private boolean selected;
/*     */   
/*     */   protected BeaconScreenButton(int $$0, int $$1) {
/* 154 */     super($$0, $$1, 22, 22, CommonComponents.EMPTY);
/*     */   }
/*     */   
/*     */   protected BeaconScreenButton(int $$0, int $$1, Component $$2) {
/* 158 */     super($$0, $$1, 22, 22, $$2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderWidget(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/*     */     ResourceLocation $$7;
/* 165 */     if (!this.active) {
/* 166 */       ResourceLocation $$4 = BeaconScreen.BUTTON_DISABLED_SPRITE;
/* 167 */     } else if (this.selected) {
/* 168 */       ResourceLocation $$5 = BeaconScreen.BUTTON_SELECTED_SPRITE;
/* 169 */     } else if (isHoveredOrFocused()) {
/* 170 */       ResourceLocation $$6 = BeaconScreen.BUTTON_HIGHLIGHTED_SPRITE;
/*     */     } else {
/* 172 */       $$7 = BeaconScreen.BUTTON_SPRITE;
/*     */     } 
/*     */     
/* 175 */     $$0.blitSprite($$7, getX(), getY(), this.width, this.height);
/*     */     
/* 177 */     renderIcon($$0);
/*     */   }
/*     */   
/*     */   protected abstract void renderIcon(GuiGraphics paramGuiGraphics);
/*     */   
/*     */   public boolean isSelected() {
/* 183 */     return this.selected;
/*     */   }
/*     */   
/*     */   public void setSelected(boolean $$0) {
/* 187 */     this.selected = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateWidgetNarration(NarrationElementOutput $$0) {
/* 192 */     defaultButtonNarrationText($$0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\inventory\BeaconScreen$BeaconScreenButton.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */