/*    */ package net.minecraft.client.gui.screens.reporting;
/*    */ import java.util.UUID;
/*    */ import net.minecraft.ChatFormatting;
/*    */ import net.minecraft.Optionull;
/*    */ import net.minecraft.client.gui.components.AbstractWidget;
/*    */ import net.minecraft.client.gui.components.Button;
/*    */ import net.minecraft.client.gui.components.MultiLineEditBox;
/*    */ import net.minecraft.client.gui.components.StringWidget;
/*    */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*    */ import net.minecraft.client.gui.layouts.CommonLayouts;
/*    */ import net.minecraft.client.gui.layouts.FrameLayout;
/*    */ import net.minecraft.client.gui.layouts.LayoutElement;
/*    */ import net.minecraft.client.gui.layouts.LayoutSettings;
/*    */ import net.minecraft.client.gui.layouts.LinearLayout;
/*    */ import net.minecraft.client.gui.screens.Screen;
/*    */ import net.minecraft.client.multiplayer.chat.report.NameReport;
/*    */ import net.minecraft.client.multiplayer.chat.report.Report;
/*    */ import net.minecraft.client.multiplayer.chat.report.ReportingContext;
/*    */ import net.minecraft.network.chat.CommonComponents;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.MutableComponent;
/*    */ 
/*    */ public class NameReportScreen extends AbstractReportScreen<NameReport.Builder> {
/* 24 */   private static final Component TITLE = (Component)Component.translatable("gui.abuseReport.name.title");
/*    */   private static final int BUTTON_WIDTH = 120;
/* 26 */   private final LinearLayout layout = LinearLayout.vertical().spacing(8);
/*    */   
/*    */   private MultiLineEditBox commentBox;
/*    */   private Button sendButton;
/*    */   
/*    */   private NameReportScreen(Screen $$0, ReportingContext $$1, NameReport.Builder $$2) {
/* 32 */     super(TITLE, $$0, $$1, $$2);
/*    */   }
/*    */   
/*    */   public NameReportScreen(Screen $$0, ReportingContext $$1, UUID $$2, String $$3) {
/* 36 */     this($$0, $$1, new NameReport.Builder($$2, $$3, $$1.sender().reportLimits()));
/*    */   }
/*    */   
/*    */   public NameReportScreen(Screen $$0, ReportingContext $$1, NameReport $$2) {
/* 40 */     this($$0, $$1, new NameReport.Builder($$2, $$1.sender().reportLimits()));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void init() {
/* 45 */     this.layout.defaultCellSetting().alignHorizontallyCenter();
/* 46 */     this.layout.addChild((LayoutElement)new StringWidget(this.title, this.font));
/*    */     
/* 48 */     MutableComponent mutableComponent = Component.literal(((NameReport)this.reportBuilder.report()).getReportedName()).withStyle(ChatFormatting.YELLOW);
/* 49 */     this.layout.addChild((LayoutElement)new StringWidget((Component)Component.translatable("gui.abuseReport.name.reporting", new Object[] { mutableComponent }), this.font), $$0 -> $$0.alignHorizontallyLeft().padding(0, 8));
/*    */     
/* 51 */     Objects.requireNonNull(this.font); this.commentBox = createCommentBox(280, 9 * 8, $$0 -> {
/*    */           this.reportBuilder.setComments($$0);
/*    */           onReportChanged();
/*    */         });
/* 55 */     this.layout.addChild((LayoutElement)CommonLayouts.labeledElement(this.font, (LayoutElement)this.commentBox, MORE_COMMENTS_LABEL, $$0 -> $$0.paddingBottom(12)));
/*    */     
/* 57 */     LinearLayout $$1 = (LinearLayout)this.layout.addChild((LayoutElement)LinearLayout.horizontal().spacing(8));
/* 58 */     $$1.addChild((LayoutElement)Button.builder(CommonComponents.GUI_BACK, $$0 -> onClose()).width(120).build());
/* 59 */     this.sendButton = (Button)$$1.addChild((LayoutElement)Button.builder(SEND_REPORT, $$0 -> sendReport()).width(120).build());
/*    */     
/* 61 */     onReportChanged();
/*    */     
/* 63 */     this.layout.visitWidgets($$1 -> (AbstractWidget)$$0.addRenderableWidget($$1));
/* 64 */     repositionElements();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void repositionElements() {
/* 69 */     this.layout.arrangeElements();
/* 70 */     FrameLayout.centerInRectangle((LayoutElement)this.layout, getRectangle());
/*    */   }
/*    */   
/*    */   private void onReportChanged() {
/* 74 */     Report.CannotBuildReason $$0 = this.reportBuilder.checkBuildable();
/* 75 */     this.sendButton.active = ($$0 == null);
/* 76 */     this.sendButton.setTooltip((Tooltip)Optionull.map($$0, Report.CannotBuildReason::tooltip));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean mouseReleased(double $$0, double $$1, int $$2) {
/* 85 */     if (super.mouseReleased($$0, $$1, $$2)) {
/* 86 */       return true;
/*    */     }
/* 88 */     return this.commentBox.mouseReleased($$0, $$1, $$2);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\reporting\NameReportScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */