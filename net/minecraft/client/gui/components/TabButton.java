/*    */ package net.minecraft.client.gui.components;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.Font;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.client.gui.components.tabs.Tab;
/*    */ import net.minecraft.client.gui.components.tabs.TabManager;
/*    */ import net.minecraft.client.gui.narration.NarratedElementType;
/*    */ import net.minecraft.client.gui.narration.NarrationElementOutput;
/*    */ import net.minecraft.client.sounds.SoundManager;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.FormattedText;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ public class TabButton extends AbstractWidget {
/* 16 */   private static final WidgetSprites SPRITES = new WidgetSprites(new ResourceLocation("widget/tab_selected"), new ResourceLocation("widget/tab"), new ResourceLocation("widget/tab_selected_highlighted"), new ResourceLocation("widget/tab_highlighted"));
/*    */   
/*    */   private static final int SELECTED_OFFSET = 3;
/*    */   
/*    */   private static final int TEXT_MARGIN = 1;
/*    */   
/*    */   private static final int UNDERLINE_HEIGHT = 1;
/*    */   
/*    */   private static final int UNDERLINE_MARGIN_X = 4;
/*    */   
/*    */   private static final int UNDERLINE_MARGIN_BOTTOM = 2;
/*    */   
/*    */   private final TabManager tabManager;
/*    */   
/*    */   private final Tab tab;
/*    */ 
/*    */   
/*    */   public TabButton(TabManager $$0, Tab $$1, int $$2, int $$3) {
/* 34 */     super(0, 0, $$2, $$3, $$1.getTabTitle());
/* 35 */     this.tabManager = $$0;
/* 36 */     this.tab = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderWidget(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 41 */     $$0.blitSprite(SPRITES.get(isSelected(), isHovered()), getX(), getY(), this.width, this.height);
/*    */     
/* 43 */     Font $$4 = (Minecraft.getInstance()).font;
/* 44 */     int $$5 = this.active ? -1 : -6250336;
/* 45 */     renderString($$0, $$4, $$5);
/*    */     
/* 47 */     if (isSelected()) {
/* 48 */       renderFocusUnderline($$0, $$4, $$5);
/*    */     }
/*    */   }
/*    */   
/*    */   public void renderString(GuiGraphics $$0, Font $$1, int $$2) {
/* 53 */     int $$3 = getX() + 1;
/* 54 */     int $$4 = getY() + (isSelected() ? 0 : 3);
/* 55 */     int $$5 = getX() + getWidth() - 1;
/* 56 */     int $$6 = getY() + getHeight();
/* 57 */     renderScrollingString($$0, $$1, getMessage(), $$3, $$4, $$5, $$6, $$2);
/*    */   }
/*    */   
/*    */   private void renderFocusUnderline(GuiGraphics $$0, Font $$1, int $$2) {
/* 61 */     int $$3 = Math.min($$1.width((FormattedText)getMessage()), getWidth() - 4);
/* 62 */     int $$4 = getX() + (getWidth() - $$3) / 2;
/* 63 */     int $$5 = getY() + getHeight() - 2;
/* 64 */     $$0.fill($$4, $$5, $$4 + $$3, $$5 + 1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void updateWidgetNarration(NarrationElementOutput $$0) {
/* 69 */     $$0.add(NarratedElementType.TITLE, (Component)Component.translatable("gui.narrate.tab", new Object[] { this.tab.getTabTitle() }));
/*    */   }
/*    */ 
/*    */   
/*    */   public void playDownSound(SoundManager $$0) {}
/*    */ 
/*    */   
/*    */   public Tab tab() {
/* 77 */     return this.tab;
/*    */   }
/*    */   
/*    */   public boolean isSelected() {
/* 81 */     return (this.tabManager.getCurrentTab() == this.tab);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\TabButton.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */