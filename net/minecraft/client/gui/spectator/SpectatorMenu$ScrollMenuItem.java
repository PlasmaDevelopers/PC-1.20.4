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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ScrollMenuItem
/*     */   implements SpectatorMenuItem
/*     */ {
/*     */   private final int direction;
/*     */   private final boolean enabled;
/*     */   
/*     */   public ScrollMenuItem(int $$0, boolean $$1) {
/* 156 */     this.direction = $$0;
/* 157 */     this.enabled = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void selectItem(SpectatorMenu $$0) {
/* 162 */     $$0.page += this.direction;
/*     */   }
/*     */ 
/*     */   
/*     */   public Component getName() {
/* 167 */     return (this.direction < 0) ? SpectatorMenu.PREVIOUS_PAGE_TEXT : SpectatorMenu.NEXT_PAGE_TEXT;
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderIcon(GuiGraphics $$0, float $$1, int $$2) {
/* 172 */     if (this.direction < 0) {
/* 173 */       $$0.blitSprite(SpectatorMenu.SCROLL_LEFT_SPRITE, 0, 0, 16, 16);
/*     */     } else {
/* 175 */       $$0.blitSprite(SpectatorMenu.SCROLL_RIGHT_SPRITE, 0, 0, 16, 16);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEnabled() {
/* 181 */     return this.enabled;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\spectator\SpectatorMenu$ScrollMenuItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */