/*    */ package net.minecraft.client.gui.screens.reporting;
/*    */ import net.minecraft.client.gui.components.AbstractWidget;
/*    */ import net.minecraft.client.gui.components.Button;
/*    */ import net.minecraft.client.gui.components.StringWidget;
/*    */ import net.minecraft.client.gui.components.Tooltip;
/*    */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*    */ import net.minecraft.client.gui.layouts.FrameLayout;
/*    */ import net.minecraft.client.gui.layouts.LayoutElement;
/*    */ import net.minecraft.client.gui.layouts.LinearLayout;
/*    */ import net.minecraft.client.gui.screens.Screen;
/*    */ import net.minecraft.client.gui.screens.social.PlayerEntry;
/*    */ import net.minecraft.client.multiplayer.chat.report.ReportingContext;
/*    */ import net.minecraft.network.chat.CommonComponents;
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ public class ReportPlayerScreen extends Screen {
/* 17 */   private static final Component TITLE = (Component)Component.translatable("gui.abuseReport.title");
/* 18 */   private static final Component MESSAGE = (Component)Component.translatable("gui.abuseReport.message");
/*    */   
/* 20 */   private static final Component REPORT_CHAT = (Component)Component.translatable("gui.abuseReport.type.chat");
/* 21 */   private static final Component REPORT_SKIN = (Component)Component.translatable("gui.abuseReport.type.skin");
/* 22 */   private static final Component REPORT_NAME = (Component)Component.translatable("gui.abuseReport.type.name");
/*    */   
/*    */   private static final int SPACING = 6;
/*    */   
/*    */   private final Screen lastScreen;
/*    */   
/*    */   private final ReportingContext context;
/*    */   private final PlayerEntry player;
/* 30 */   private final LinearLayout layout = LinearLayout.vertical().spacing(6);
/*    */   
/*    */   public ReportPlayerScreen(Screen $$0, ReportingContext $$1, PlayerEntry $$2) {
/* 33 */     super(TITLE);
/* 34 */     this.lastScreen = $$0;
/* 35 */     this.context = $$1;
/* 36 */     this.player = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public Component getNarrationMessage() {
/* 41 */     return (Component)CommonComponents.joinForNarration(new Component[] { super.getNarrationMessage(), MESSAGE });
/*    */   }
/*    */ 
/*    */   
/*    */   protected void init() {
/* 46 */     this.layout.defaultCellSetting().alignHorizontallyCenter();
/* 47 */     this.layout.addChild((LayoutElement)new StringWidget(this.title, this.font), this.layout.newCellSettings().paddingBottom(6));
/*    */     
/* 49 */     this.layout.addChild((LayoutElement)(new MultiLineTextWidget(MESSAGE, this.font)).setCentered(true), this.layout.newCellSettings().paddingBottom(6));
/*    */     
/* 51 */     Button $$0 = (Button)this.layout.addChild((LayoutElement)Button.builder(REPORT_CHAT, $$0 -> this.minecraft.setScreen(new ChatReportScreen(this.lastScreen, this.context, this.player.getPlayerId()))).build());
/* 52 */     if (!this.player.isChatReportable()) {
/* 53 */       $$0.active = false;
/* 54 */       $$0.setTooltip(Tooltip.create((Component)Component.translatable("gui.socialInteractions.tooltip.report.not_reportable")));
/* 55 */     } else if (!this.player.hasRecentMessages()) {
/* 56 */       $$0.active = false;
/* 57 */       $$0.setTooltip(Tooltip.create((Component)Component.translatable("gui.socialInteractions.tooltip.report.no_messages", new Object[] { this.player.getPlayerName() })));
/*    */     } 
/*    */     
/* 60 */     this.layout.addChild((LayoutElement)Button.builder(REPORT_SKIN, $$0 -> this.minecraft.setScreen(new SkinReportScreen(this.lastScreen, this.context, this.player.getPlayerId(), this.player.getSkinGetter()))).build());
/* 61 */     this.layout.addChild((LayoutElement)Button.builder(REPORT_NAME, $$0 -> this.minecraft.setScreen(new NameReportScreen(this.lastScreen, this.context, this.player.getPlayerId(), this.player.getPlayerName()))).build());
/*    */     
/* 63 */     this.layout.addChild((LayoutElement)SpacerElement.height(20));
/*    */     
/* 65 */     this.layout.addChild((LayoutElement)Button.builder(CommonComponents.GUI_CANCEL, $$0 -> onClose()).build());
/*    */     
/* 67 */     this.layout.visitWidgets($$1 -> (AbstractWidget)$$0.addRenderableWidget($$1));
/* 68 */     repositionElements();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void repositionElements() {
/* 73 */     this.layout.arrangeElements();
/* 74 */     FrameLayout.centerInRectangle((LayoutElement)this.layout, getRectangle());
/*    */   }
/*    */ 
/*    */   
/*    */   public void onClose() {
/* 79 */     this.minecraft.setScreen(this.lastScreen);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\reporting\ReportPlayerScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */