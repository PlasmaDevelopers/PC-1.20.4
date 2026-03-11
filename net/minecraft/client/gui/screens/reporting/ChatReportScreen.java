/*     */ package net.minecraft.client.gui.screens.reporting;
/*     */ import it.unimi.dsi.fastutil.ints.IntSet;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.Optionull;
/*     */ import net.minecraft.client.gui.components.AbstractWidget;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.MultiLineEditBox;
/*     */ import net.minecraft.client.gui.components.StringWidget;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.layouts.CommonLayouts;
/*     */ import net.minecraft.client.gui.layouts.FrameLayout;
/*     */ import net.minecraft.client.gui.layouts.LayoutElement;
/*     */ import net.minecraft.client.gui.layouts.LayoutSettings;
/*     */ import net.minecraft.client.gui.layouts.LinearLayout;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.client.multiplayer.chat.report.ChatReport;
/*     */ import net.minecraft.client.multiplayer.chat.report.Report;
/*     */ import net.minecraft.client.multiplayer.chat.report.ReportReason;
/*     */ import net.minecraft.client.multiplayer.chat.report.ReportingContext;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ 
/*     */ public class ChatReportScreen extends AbstractReportScreen<ChatReport.Builder> {
/*  24 */   private static final Component TITLE = (Component)Component.translatable("gui.chatReport.title"); private static final int BUTTON_WIDTH = 120;
/*  25 */   private static final Component SELECT_CHAT_MESSAGE = (Component)Component.translatable("gui.chatReport.select_chat");
/*     */   
/*  27 */   private final LinearLayout layout = LinearLayout.vertical().spacing(8);
/*     */   
/*     */   private MultiLineEditBox commentBox;
/*     */   private Button sendButton;
/*     */   private Button selectMessagesButton;
/*     */   private Button selectReasonButton;
/*     */   
/*     */   private ChatReportScreen(Screen $$0, ReportingContext $$1, ChatReport.Builder $$2) {
/*  35 */     super(TITLE, $$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   public ChatReportScreen(Screen $$0, ReportingContext $$1, UUID $$2) {
/*  39 */     this($$0, $$1, new ChatReport.Builder($$2, $$1.sender().reportLimits()));
/*     */   }
/*     */   
/*     */   public ChatReportScreen(Screen $$0, ReportingContext $$1, ChatReport $$2) {
/*  43 */     this($$0, $$1, new ChatReport.Builder($$2, $$1.sender().reportLimits()));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init() {
/*  48 */     this.layout.defaultCellSetting().alignHorizontallyCenter();
/*     */     
/*  50 */     this.layout.addChild((LayoutElement)new StringWidget(this.title, this.font));
/*  51 */     this.selectMessagesButton = (Button)this.layout.addChild((LayoutElement)Button.builder(SELECT_CHAT_MESSAGE, $$0 -> this.minecraft.setScreen(new ChatSelectionScreen(this, this.reportingContext, this.reportBuilder, ())))
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  56 */         .width(280).build());
/*     */     
/*  58 */     this
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  63 */       .selectReasonButton = Button.builder(SELECT_REASON, $$0 -> this.minecraft.setScreen(new ReportReasonSelectionScreen(this, this.reportBuilder.reason(), ()))).width(280).build();
/*  64 */     this.layout.addChild((LayoutElement)CommonLayouts.labeledElement(this.font, (LayoutElement)this.selectReasonButton, OBSERVED_WHAT_LABEL));
/*     */     
/*  66 */     Objects.requireNonNull(this.font); this.commentBox = createCommentBox(280, 9 * 8, $$0 -> {
/*     */           this.reportBuilder.setComments($$0);
/*     */           onReportChanged();
/*     */         });
/*  70 */     this.layout.addChild((LayoutElement)CommonLayouts.labeledElement(this.font, (LayoutElement)this.commentBox, MORE_COMMENTS_LABEL, $$0 -> $$0.paddingBottom(12)));
/*     */     
/*  72 */     LinearLayout $$0 = (LinearLayout)this.layout.addChild((LayoutElement)LinearLayout.horizontal().spacing(8));
/*  73 */     $$0.addChild((LayoutElement)Button.builder(CommonComponents.GUI_BACK, $$0 -> onClose()).width(120).build());
/*  74 */     this.sendButton = (Button)$$0.addChild((LayoutElement)Button.builder(SEND_REPORT, $$0 -> sendReport()).width(120).build());
/*     */     
/*  76 */     this.layout.visitWidgets($$1 -> (AbstractWidget)$$0.addRenderableWidget($$1));
/*  77 */     repositionElements();
/*     */     
/*  79 */     onReportChanged();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void repositionElements() {
/*  84 */     this.layout.arrangeElements();
/*  85 */     FrameLayout.centerInRectangle((LayoutElement)this.layout, getRectangle());
/*     */   }
/*     */   
/*     */   private void onReportChanged() {
/*  89 */     IntSet $$0 = this.reportBuilder.reportedMessages();
/*  90 */     if ($$0.isEmpty()) {
/*  91 */       this.selectMessagesButton.setMessage(SELECT_CHAT_MESSAGE);
/*     */     } else {
/*  93 */       this.selectMessagesButton.setMessage((Component)Component.translatable("gui.chatReport.selected_chat", new Object[] { Integer.valueOf($$0.size()) }));
/*     */     } 
/*     */     
/*  96 */     ReportReason $$1 = this.reportBuilder.reason();
/*  97 */     if ($$1 != null) {
/*  98 */       this.selectReasonButton.setMessage($$1.title());
/*     */     } else {
/* 100 */       this.selectReasonButton.setMessage(SELECT_REASON);
/*     */     } 
/*     */     
/* 103 */     Report.CannotBuildReason $$2 = this.reportBuilder.checkBuildable();
/* 104 */     this.sendButton.active = ($$2 == null);
/* 105 */     this.sendButton.setTooltip((Tooltip)Optionull.map($$2, Report.CannotBuildReason::tooltip));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mouseReleased(double $$0, double $$1, int $$2) {
/* 114 */     if (super.mouseReleased($$0, $$1, $$2)) {
/* 115 */       return true;
/*     */     }
/* 117 */     return this.commentBox.mouseReleased($$0, $$1, $$2);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\reporting\ChatReportScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */