/*     */ package net.minecraft.client.gui.screens.reporting;
/*     */ 
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.screens.multiplayer.WarningScreen;
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
/*     */ class DiscardReportWarningScreen
/*     */   extends WarningScreen
/*     */ {
/*     */   private static final int BUTTON_MARGIN = 20;
/* 142 */   private static final Component TITLE = (Component)Component.translatable("gui.abuseReport.discard.title").withStyle(ChatFormatting.BOLD);
/* 143 */   private static final Component MESSAGE = (Component)Component.translatable("gui.abuseReport.discard.content");
/* 144 */   private static final Component RETURN = (Component)Component.translatable("gui.abuseReport.discard.return");
/* 145 */   private static final Component DRAFT = (Component)Component.translatable("gui.abuseReport.discard.draft");
/* 146 */   private static final Component DISCARD = (Component)Component.translatable("gui.abuseReport.discard.discard");
/*     */   
/*     */   protected DiscardReportWarningScreen() {
/* 149 */     super(TITLE, MESSAGE, MESSAGE);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void initButtons(int $$0) {
/* 154 */     addRenderableWidget(
/* 155 */         (GuiEventListener)Button.builder(RETURN, $$0 -> onClose())
/* 156 */         .pos(this.width / 2 - 155, 100 + $$0)
/* 157 */         .build());
/*     */     
/* 159 */     addRenderableWidget(
/* 160 */         (GuiEventListener)Button.builder(DRAFT, $$0 -> {
/*     */             AbstractReportScreen.this.saveDraft();
/*     */             
/*     */             this.minecraft.setScreen(AbstractReportScreen.this.lastScreen);
/* 164 */           }).pos(this.width / 2 + 5, 100 + $$0)
/* 165 */         .build());
/*     */     
/* 167 */     addRenderableWidget(
/* 168 */         (GuiEventListener)Button.builder(DISCARD, $$0 -> {
/*     */             AbstractReportScreen.this.clearDraft();
/*     */             
/*     */             this.minecraft.setScreen(AbstractReportScreen.this.lastScreen);
/* 172 */           }).pos(this.width / 2 - 75, 130 + $$0)
/* 173 */         .build());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onClose() {
/* 179 */     this.minecraft.setScreen(AbstractReportScreen.this);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldCloseOnEsc() {
/* 184 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderTitle(GuiGraphics $$0) {
/* 189 */     $$0.drawString(this.font, this.title, this.width / 2 - 155, 30, -1);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\reporting\AbstractReportScreen$DiscardReportWarningScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */