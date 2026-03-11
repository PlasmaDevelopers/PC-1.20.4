/*     */ package net.minecraft.client.gui.spectator;
/*     */ 
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.network.chat.Component;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class CloseSpectatorItem
/*     */   implements SpectatorMenuItem
/*     */ {
/*     */   public void selectItem(SpectatorMenu $$0) {
/* 132 */     $$0.exit();
/*     */   }
/*     */ 
/*     */   
/*     */   public Component getName() {
/* 137 */     return SpectatorMenu.CLOSE_MENU_TEXT;
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderIcon(GuiGraphics $$0, float $$1, int $$2) {
/* 142 */     $$0.blitSprite(SpectatorMenu.CLOSE_SPRITE, 0, 0, 16, 16);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEnabled() {
/* 147 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\spectator\SpectatorMenu$CloseSpectatorItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */