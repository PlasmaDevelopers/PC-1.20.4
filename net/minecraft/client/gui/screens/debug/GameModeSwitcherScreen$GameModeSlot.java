/*     */ package net.minecraft.client.gui.screens.debug;
/*     */ 
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.AbstractWidget;
/*     */ import net.minecraft.client.gui.narration.NarrationElementOutput;
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
/*     */ public class GameModeSlot
/*     */   extends AbstractWidget
/*     */ {
/*     */   final GameModeSwitcherScreen.GameModeIcon icon;
/*     */   private boolean isSelected;
/*     */   
/*     */   public GameModeSlot(GameModeSwitcherScreen.GameModeIcon $$1, int $$2, int $$3) {
/* 222 */     super($$2, $$3, 26, 26, $$1.getName());
/* 223 */     this.icon = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderWidget(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 228 */     drawSlot($$0);
/* 229 */     this.icon.drawIcon($$0, getX() + 5, getY() + 5);
/*     */     
/* 231 */     if (this.isSelected) {
/* 232 */       drawSelection($$0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateWidgetNarration(NarrationElementOutput $$0) {
/* 238 */     defaultButtonNarrationText($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isHoveredOrFocused() {
/* 243 */     return (super.isHoveredOrFocused() || this.isSelected);
/*     */   }
/*     */   
/*     */   public void setSelected(boolean $$0) {
/* 247 */     this.isSelected = $$0;
/*     */   }
/*     */   
/*     */   private void drawSlot(GuiGraphics $$0) {
/* 251 */     $$0.blitSprite(GameModeSwitcherScreen.SLOT_SPRITE, getX(), getY(), 26, 26);
/*     */   }
/*     */   
/*     */   private void drawSelection(GuiGraphics $$0) {
/* 255 */     $$0.blitSprite(GameModeSwitcherScreen.SELECTION_SPRITE, getX(), getY(), 26, 26);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\debug\GameModeSwitcherScreen$GameModeSlot.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */