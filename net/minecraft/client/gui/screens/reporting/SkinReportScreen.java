/*     */ package net.minecraft.client.gui.screens.reporting;
/*     */ 
/*     */ import java.util.Objects;
/*     */ import java.util.UUID;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.Optionull;
/*     */ import net.minecraft.client.gui.components.AbstractWidget;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.MultiLineEditBox;
/*     */ import net.minecraft.client.gui.components.PlayerSkinWidget;
/*     */ import net.minecraft.client.gui.components.StringWidget;
/*     */ import net.minecraft.client.gui.components.Tooltip;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.layouts.CommonLayouts;
/*     */ import net.minecraft.client.gui.layouts.FrameLayout;
/*     */ import net.minecraft.client.gui.layouts.LayoutElement;
/*     */ import net.minecraft.client.gui.layouts.LayoutSettings;
/*     */ import net.minecraft.client.gui.layouts.LinearLayout;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.client.multiplayer.chat.report.Report;
/*     */ import net.minecraft.client.multiplayer.chat.report.ReportReason;
/*     */ import net.minecraft.client.multiplayer.chat.report.ReportingContext;
/*     */ import net.minecraft.client.multiplayer.chat.report.SkinReport;
/*     */ import net.minecraft.client.resources.PlayerSkin;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ 
/*     */ public class SkinReportScreen extends AbstractReportScreen<SkinReport.Builder> {
/*     */   private static final int BUTTON_WIDTH = 120;
/*  30 */   private static final Component TITLE = (Component)Component.translatable("gui.abuseReport.skin.title");
/*     */   private static final int SKIN_WIDTH = 85;
/*  32 */   private final LinearLayout layout = LinearLayout.vertical().spacing(8);
/*     */   private static final int FORM_WIDTH = 178;
/*     */   private MultiLineEditBox commentBox;
/*     */   private Button sendButton;
/*     */   private Button selectReasonButton;
/*     */   
/*     */   private SkinReportScreen(Screen $$0, ReportingContext $$1, SkinReport.Builder $$2) {
/*  39 */     super(TITLE, $$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   public SkinReportScreen(Screen $$0, ReportingContext $$1, UUID $$2, Supplier<PlayerSkin> $$3) {
/*  43 */     this($$0, $$1, new SkinReport.Builder($$2, $$3, $$1.sender().reportLimits()));
/*     */   }
/*     */   
/*     */   public SkinReportScreen(Screen $$0, ReportingContext $$1, SkinReport $$2) {
/*  47 */     this($$0, $$1, new SkinReport.Builder($$2, $$1.sender().reportLimits()));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init() {
/*  52 */     this.layout.defaultCellSetting().alignHorizontallyCenter();
/*     */     
/*  54 */     this.layout.addChild((LayoutElement)new StringWidget(this.title, this.font));
/*     */     
/*  56 */     LinearLayout $$0 = (LinearLayout)this.layout.addChild((LayoutElement)LinearLayout.horizontal().spacing(8));
/*  57 */     $$0.defaultCellSetting().alignVerticallyMiddle();
/*     */     
/*  59 */     $$0.addChild((LayoutElement)new PlayerSkinWidget(85, 120, this.minecraft.getEntityModels(), ((SkinReport)this.reportBuilder.report()).getSkinGetter()));
/*     */     
/*  61 */     LinearLayout $$1 = (LinearLayout)$$0.addChild((LayoutElement)LinearLayout.vertical().spacing(8));
/*     */     
/*  63 */     this
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  68 */       .selectReasonButton = Button.builder(SELECT_REASON, $$0 -> this.minecraft.setScreen(new ReportReasonSelectionScreen(this, this.reportBuilder.reason(), ()))).width(178).build();
/*  69 */     $$1.addChild((LayoutElement)CommonLayouts.labeledElement(this.font, (LayoutElement)this.selectReasonButton, OBSERVED_WHAT_LABEL));
/*     */     
/*  71 */     Objects.requireNonNull(this.font); this.commentBox = createCommentBox(178, 9 * 8, $$0 -> {
/*     */           this.reportBuilder.setComments($$0);
/*     */           onReportChanged();
/*     */         });
/*  75 */     $$1.addChild((LayoutElement)CommonLayouts.labeledElement(this.font, (LayoutElement)this.commentBox, MORE_COMMENTS_LABEL, $$0 -> $$0.paddingBottom(12)));
/*     */     
/*  77 */     LinearLayout $$2 = (LinearLayout)this.layout.addChild((LayoutElement)LinearLayout.horizontal().spacing(8));
/*  78 */     $$2.addChild((LayoutElement)Button.builder(CommonComponents.GUI_BACK, $$0 -> onClose()).width(120).build());
/*     */     
/*  80 */     this.sendButton = (Button)$$2.addChild((LayoutElement)Button.builder(SEND_REPORT, $$0 -> sendReport()).width(120).build());
/*     */     
/*  82 */     this.layout.visitWidgets($$1 -> (AbstractWidget)$$0.addRenderableWidget($$1));
/*  83 */     repositionElements();
/*     */     
/*  85 */     onReportChanged();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void repositionElements() {
/*  90 */     this.layout.arrangeElements();
/*  91 */     FrameLayout.centerInRectangle((LayoutElement)this.layout, getRectangle());
/*     */   }
/*     */   
/*     */   private void onReportChanged() {
/*  95 */     ReportReason $$0 = this.reportBuilder.reason();
/*  96 */     if ($$0 != null) {
/*  97 */       this.selectReasonButton.setMessage($$0.title());
/*     */     } else {
/*  99 */       this.selectReasonButton.setMessage(SELECT_REASON);
/*     */     } 
/*     */     
/* 102 */     Report.CannotBuildReason $$1 = this.reportBuilder.checkBuildable();
/* 103 */     this.sendButton.active = ($$1 == null);
/* 104 */     this.sendButton.setTooltip((Tooltip)Optionull.map($$1, Report.CannotBuildReason::tooltip));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mouseReleased(double $$0, double $$1, int $$2) {
/* 113 */     if (super.mouseReleased($$0, $$1, $$2)) {
/* 114 */       return true;
/*     */     }
/* 116 */     return this.commentBox.mouseReleased($$0, $$1, $$2);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\reporting\SkinReportScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */