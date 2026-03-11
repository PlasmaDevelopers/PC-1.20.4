/*     */ package net.minecraft.client.gui.screens.reporting;
/*     */ 
/*     */ import com.mojang.authlib.minecraft.report.AbuseReportLimits;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.function.Consumer;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.MultiLineEditBox;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.screens.GenericWaitingScreen;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.client.gui.screens.multiplayer.WarningScreen;
/*     */ import net.minecraft.client.multiplayer.chat.report.Report;
/*     */ import net.minecraft.client.multiplayer.chat.report.ReportingContext;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.network.chat.ThrowingComponent;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public abstract class AbstractReportScreen<B extends Report.Builder<?>> extends Screen {
/*  25 */   private static final Component REPORT_SENT_MESSAGE = (Component)Component.translatable("gui.abuseReport.report_sent_msg");
/*  26 */   private static final Component REPORT_SENDING_TITLE = (Component)Component.translatable("gui.abuseReport.sending.title").withStyle(ChatFormatting.BOLD);
/*  27 */   private static final Component REPORT_SENT_TITLE = (Component)Component.translatable("gui.abuseReport.sent.title").withStyle(ChatFormatting.BOLD);
/*  28 */   private static final Component REPORT_ERROR_TITLE = (Component)Component.translatable("gui.abuseReport.error.title").withStyle(ChatFormatting.BOLD);
/*  29 */   private static final Component REPORT_SEND_GENERIC_ERROR = (Component)Component.translatable("gui.abuseReport.send.generic_error");
/*     */   
/*  31 */   protected static final Component SEND_REPORT = (Component)Component.translatable("gui.abuseReport.send");
/*  32 */   protected static final Component OBSERVED_WHAT_LABEL = (Component)Component.translatable("gui.abuseReport.observed_what");
/*  33 */   protected static final Component SELECT_REASON = (Component)Component.translatable("gui.abuseReport.select_reason");
/*  34 */   private static final Component DESCRIBE_PLACEHOLDER = (Component)Component.translatable("gui.abuseReport.describe");
/*  35 */   protected static final Component MORE_COMMENTS_LABEL = (Component)Component.translatable("gui.abuseReport.more_comments");
/*  36 */   private static final Component MORE_COMMENTS_NARRATION = (Component)Component.translatable("gui.abuseReport.comments");
/*     */   
/*     */   protected static final int MARGIN = 20;
/*     */   
/*     */   protected static final int SCREEN_WIDTH = 280;
/*     */   
/*     */   protected static final int SPACING = 8;
/*  43 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   protected final Screen lastScreen;
/*     */   
/*     */   protected final ReportingContext reportingContext;
/*     */   protected B reportBuilder;
/*     */   
/*     */   protected AbstractReportScreen(Component $$0, Screen $$1, ReportingContext $$2, B $$3) {
/*  51 */     super($$0);
/*  52 */     this.lastScreen = $$1;
/*  53 */     this.reportingContext = $$2;
/*  54 */     this.reportBuilder = $$3;
/*     */   }
/*     */   
/*     */   protected MultiLineEditBox createCommentBox(int $$0, int $$1, Consumer<String> $$2) {
/*  58 */     AbuseReportLimits $$3 = this.reportingContext.sender().reportLimits();
/*     */     
/*  60 */     MultiLineEditBox $$4 = new MultiLineEditBox(this.font, 0, 0, $$0, $$1, DESCRIBE_PLACEHOLDER, MORE_COMMENTS_NARRATION);
/*  61 */     $$4.setValue(this.reportBuilder.comments());
/*  62 */     $$4.setCharacterLimit($$3.maxOpinionCommentsLength());
/*  63 */     $$4.setValueListener($$2);
/*     */     
/*  65 */     return $$4;
/*     */   }
/*     */   
/*     */   protected void sendReport() {
/*  69 */     this.reportBuilder.build(this.reportingContext).ifLeft($$0 -> {
/*     */           CompletableFuture<?> $$1 = this.reportingContext.sender().send($$0.id(), $$0.reportType(), $$0.report());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           this.minecraft.setScreen((Screen)GenericWaitingScreen.createWaiting(REPORT_SENDING_TITLE, CommonComponents.GUI_CANCEL, ()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           $$1.handleAsync((), (Executor)this.minecraft);
/*  87 */         }).ifRight($$0 -> displayReportSendError($$0.message()));
/*     */   }
/*     */   
/*     */   private void onReportSendSuccess() {
/*  91 */     clearDraft();
/*  92 */     this.minecraft.setScreen((Screen)GenericWaitingScreen.createCompleted(REPORT_SENT_TITLE, REPORT_SENT_MESSAGE, CommonComponents.GUI_DONE, () -> this.minecraft.setScreen(null)));
/*     */   }
/*     */   private void onReportSendError(Throwable $$0) {
/*     */     Component $$3;
/*  96 */     LOGGER.error("Encountered error while sending abuse report", $$0);
/*     */ 
/*     */     
/*  99 */     Throwable throwable = $$0.getCause(); if (throwable instanceof ThrowingComponent) { ThrowingComponent $$1 = (ThrowingComponent)throwable;
/* 100 */       Component $$2 = $$1.getComponent(); }
/*     */     else
/* 102 */     { $$3 = REPORT_SEND_GENERIC_ERROR; }
/*     */ 
/*     */     
/* 105 */     displayReportSendError($$3);
/*     */   }
/*     */   
/*     */   private void displayReportSendError(Component $$0) {
/* 109 */     MutableComponent mutableComponent = $$0.copy().withStyle(ChatFormatting.RED);
/*     */     
/* 111 */     this.minecraft.setScreen((Screen)GenericWaitingScreen.createCompleted(REPORT_ERROR_TITLE, (Component)mutableComponent, CommonComponents.GUI_BACK, () -> this.minecraft.setScreen(this)));
/*     */   }
/*     */   
/*     */   void saveDraft() {
/* 115 */     if (this.reportBuilder.hasContent()) {
/* 116 */       this.reportingContext.setReportDraft(this.reportBuilder.report().copy());
/*     */     }
/*     */   }
/*     */   
/*     */   void clearDraft() {
/* 121 */     this.reportingContext.setReportDraft(null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClose() {
/* 126 */     if (this.reportBuilder.hasContent()) {
/* 127 */       this.minecraft.setScreen((Screen)new DiscardReportWarningScreen());
/*     */     } else {
/* 129 */       this.minecraft.setScreen(this.lastScreen);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void removed() {
/* 135 */     saveDraft();
/* 136 */     super.removed();
/*     */   }
/*     */   
/*     */   private class DiscardReportWarningScreen
/*     */     extends WarningScreen {
/*     */     private static final int BUTTON_MARGIN = 20;
/* 142 */     private static final Component TITLE = (Component)Component.translatable("gui.abuseReport.discard.title").withStyle(ChatFormatting.BOLD);
/* 143 */     private static final Component MESSAGE = (Component)Component.translatable("gui.abuseReport.discard.content");
/* 144 */     private static final Component RETURN = (Component)Component.translatable("gui.abuseReport.discard.return");
/* 145 */     private static final Component DRAFT = (Component)Component.translatable("gui.abuseReport.discard.draft");
/* 146 */     private static final Component DISCARD = (Component)Component.translatable("gui.abuseReport.discard.discard");
/*     */     
/*     */     protected DiscardReportWarningScreen() {
/* 149 */       super(TITLE, MESSAGE, MESSAGE);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void initButtons(int $$0) {
/* 154 */       addRenderableWidget(
/* 155 */           (GuiEventListener)Button.builder(RETURN, $$0 -> onClose())
/* 156 */           .pos(this.width / 2 - 155, 100 + $$0)
/* 157 */           .build());
/*     */       
/* 159 */       addRenderableWidget(
/* 160 */           (GuiEventListener)Button.builder(DRAFT, $$0 -> {
/*     */               AbstractReportScreen.this.saveDraft();
/*     */               
/*     */               this.minecraft.setScreen(AbstractReportScreen.this.lastScreen);
/* 164 */             }).pos(this.width / 2 + 5, 100 + $$0)
/* 165 */           .build());
/*     */       
/* 167 */       addRenderableWidget(
/* 168 */           (GuiEventListener)Button.builder(DISCARD, $$0 -> {
/*     */               AbstractReportScreen.this.clearDraft();
/*     */               
/*     */               this.minecraft.setScreen(AbstractReportScreen.this.lastScreen);
/* 172 */             }).pos(this.width / 2 - 75, 130 + $$0)
/* 173 */           .build());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void onClose() {
/* 179 */       this.minecraft.setScreen(AbstractReportScreen.this);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldCloseOnEsc() {
/* 184 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void renderTitle(GuiGraphics $$0) {
/* 189 */       $$0.drawString(this.font, this.title, this.width / 2 - 155, 30, -1);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\reporting\AbstractReportScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */