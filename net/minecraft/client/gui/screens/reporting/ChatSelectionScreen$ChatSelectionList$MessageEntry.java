/*     */ package net.minecraft.client.gui.screens.reporting;
/*     */ 
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Optionull;
/*     */ import net.minecraft.client.GuiMessageTag;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.navigation.CommonInputs;
/*     */ import net.minecraft.locale.Language;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.util.FormattedCharSequence;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MessageEntry
/*     */   extends ChatSelectionScreen.ChatSelectionList.Entry
/*     */ {
/*     */   private static final int CHECKMARK_WIDTH = 9;
/*     */   private static final int CHECKMARK_HEIGHT = 8;
/*     */   private static final int INDENT_AMOUNT = 11;
/*     */   private static final int TAG_MARGIN_LEFT = 4;
/*     */   private final int chatId;
/*     */   private final FormattedText text;
/*     */   private final Component narration;
/*     */   @Nullable
/*     */   private final List<FormattedCharSequence> hoverText;
/*     */   @Nullable
/*     */   private final GuiMessageTag.Icon tagIcon;
/*     */   @Nullable
/*     */   private final List<FormattedCharSequence> tagHoverText;
/*     */   private final boolean canReport;
/*     */   private final boolean playerMessage;
/*     */   
/*     */   public MessageEntry(int $$1, Component $$2, @Nullable Component $$3, GuiMessageTag $$4, boolean $$5, boolean $$6) {
/* 290 */     this.chatId = $$1;
/* 291 */     this.tagIcon = (GuiMessageTag.Icon)Optionull.map($$4, GuiMessageTag::icon);
/* 292 */     this.tagHoverText = ($$4 != null && $$4.text() != null) ? ChatSelectionScreen.access$100($$0.this$0).split((FormattedText)$$4.text(), $$0.getRowWidth()) : null;
/*     */     
/* 294 */     this.canReport = $$5;
/* 295 */     this.playerMessage = $$6;
/* 296 */     FormattedText $$7 = ChatSelectionScreen.access$300($$0.this$0).substrByWidth((FormattedText)$$2, getMaximumTextWidth() - ChatSelectionScreen.access$200($$0.this$0).width((FormattedText)CommonComponents.ELLIPSIS));
/* 297 */     if ($$2 != $$7) {
/* 298 */       this.text = FormattedText.composite(new FormattedText[] { $$7, (FormattedText)CommonComponents.ELLIPSIS });
/* 299 */       this.hoverText = ChatSelectionScreen.access$400($$0.this$0).split((FormattedText)$$2, $$0.getRowWidth());
/*     */     } else {
/* 301 */       this.text = (FormattedText)$$2;
/* 302 */       this.hoverText = null;
/*     */     } 
/* 304 */     this.narration = $$3;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/* 309 */     if (isSelected() && this.canReport) {
/* 310 */       renderSelectedCheckmark($$0, $$2, $$3, $$5);
/*     */     }
/*     */     
/* 313 */     int $$10 = $$3 + getTextIndent();
/* 314 */     Objects.requireNonNull(ChatSelectionScreen.access$500(this.this$1.this$0)); int $$11 = $$2 + 1 + ($$5 - 9) / 2;
/* 315 */     $$0.drawString(ChatSelectionScreen.access$600(this.this$1.this$0), Language.getInstance().getVisualOrder(this.text), $$10, $$11, this.canReport ? -1 : -1593835521);
/*     */     
/* 317 */     if (this.hoverText != null && $$8) {
/* 318 */       this.this$1.this$0.setTooltipForNextRenderPass(this.hoverText);
/*     */     }
/*     */     
/* 321 */     int $$12 = ChatSelectionScreen.access$700(this.this$1.this$0).width(this.text);
/* 322 */     renderTag($$0, $$10 + $$12 + 4, $$2, $$5, $$6, $$7);
/*     */   }
/*     */   
/*     */   private void renderTag(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5) {
/* 326 */     if (this.tagIcon != null) {
/* 327 */       int $$6 = $$2 + ($$3 - this.tagIcon.height) / 2;
/* 328 */       this.tagIcon.draw($$0, $$1, $$6);
/*     */       
/* 330 */       if (this.tagHoverText != null && $$4 >= $$1 && $$4 <= $$1 + this.tagIcon.width && $$5 >= $$6 && $$5 <= $$6 + this.tagIcon.height) {
/* 331 */         this.this$1.this$0.setTooltipForNextRenderPass(this.tagHoverText);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void renderSelectedCheckmark(GuiGraphics $$0, int $$1, int $$2, int $$3) {
/* 337 */     int $$4 = $$2;
/* 338 */     int $$5 = $$1 + ($$3 - 8) / 2;
/*     */     
/* 340 */     RenderSystem.enableBlend();
/* 341 */     $$0.blitSprite(ChatSelectionScreen.CHECKMARK_SPRITE, $$4, $$5, 9, 8);
/* 342 */     RenderSystem.disableBlend();
/*     */   }
/*     */   
/*     */   private int getMaximumTextWidth() {
/* 346 */     int $$0 = (this.tagIcon != null) ? (this.tagIcon.width + 4) : 0;
/* 347 */     return ChatSelectionScreen.ChatSelectionList.this.getRowWidth() - getTextIndent() - 4 - $$0;
/*     */   }
/*     */   
/*     */   private int getTextIndent() {
/* 351 */     return this.playerMessage ? 11 : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public Component getNarration() {
/* 356 */     return isSelected() ? (Component)Component.translatable("narrator.select", new Object[] { this.narration }) : this.narration;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double $$0, double $$1, int $$2) {
/* 361 */     ChatSelectionScreen.ChatSelectionList.this.setSelected((ChatSelectionScreen.ChatSelectionList.Entry)null);
/* 362 */     return toggleReport();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean keyPressed(int $$0, int $$1, int $$2) {
/* 367 */     if (CommonInputs.selected($$0)) {
/* 368 */       return toggleReport();
/*     */     }
/* 370 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSelected() {
/* 375 */     return this.this$1.this$0.report.isReported(this.chatId);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSelect() {
/* 380 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canReport() {
/* 385 */     return this.canReport;
/*     */   }
/*     */   
/*     */   private boolean toggleReport() {
/* 389 */     if (this.canReport) {
/* 390 */       this.this$1.this$0.report.toggleReported(this.chatId);
/* 391 */       this.this$1.this$0.updateConfirmSelectedButton();
/* 392 */       return true;
/*     */     } 
/* 394 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\reporting\ChatSelectionScreen$ChatSelectionList$MessageEntry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */