/*     */ package net.minecraft.client.gui.screens.telemetry;
/*     */ 
/*     */ import java.nio.file.Path;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.OptionInstance;
/*     */ import net.minecraft.client.Options;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.AbstractWidget;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.Checkbox;
/*     */ import net.minecraft.client.gui.components.MultiLineTextWidget;
/*     */ import net.minecraft.client.gui.components.StringWidget;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.layouts.FrameLayout;
/*     */ import net.minecraft.client.gui.layouts.GridLayout;
/*     */ import net.minecraft.client.gui.layouts.Layout;
/*     */ import net.minecraft.client.gui.layouts.LayoutElement;
/*     */ import net.minecraft.client.gui.layouts.LinearLayout;
/*     */ import net.minecraft.client.gui.screens.ConfirmLinkScreen;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ 
/*     */ public class TelemetryInfoScreen
/*     */   extends Screen {
/*     */   private static final int PADDING = 8;
/*  29 */   private static final Component TITLE = (Component)Component.translatable("telemetry_info.screen.title");
/*  30 */   private static final Component DESCRIPTION = (Component)Component.translatable("telemetry_info.screen.description").withStyle(ChatFormatting.GRAY);
/*  31 */   private static final Component BUTTON_PRIVACY_STATEMENT = (Component)Component.translatable("telemetry_info.button.privacy_statement");
/*  32 */   private static final Component BUTTON_GIVE_FEEDBACK = (Component)Component.translatable("telemetry_info.button.give_feedback");
/*  33 */   private static final Component BUTTON_SHOW_DATA = (Component)Component.translatable("telemetry_info.button.show_data");
/*  34 */   private static final Component CHECKBOX_OPT_IN = (Component)Component.translatable("telemetry_info.opt_in.description");
/*     */   
/*     */   private final Screen lastScreen;
/*     */   
/*     */   private final Options options;
/*     */   @Nullable
/*     */   private TelemetryEventWidget telemetryEventWidget;
/*     */   private double savedScroll;
/*     */   
/*     */   public TelemetryInfoScreen(Screen $$0, Options $$1) {
/*  44 */     super(TITLE);
/*  45 */     this.lastScreen = $$0;
/*  46 */     this.options = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public Component getNarrationMessage() {
/*  51 */     return (Component)CommonComponents.joinForNarration(new Component[] { super.getNarrationMessage(), DESCRIPTION });
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init() {
/*  56 */     FrameLayout $$0 = new FrameLayout();
/*  57 */     $$0.defaultChildLayoutSetting().padding(8);
/*  58 */     $$0.setMinHeight(this.height);
/*     */     
/*  60 */     LinearLayout $$1 = (LinearLayout)$$0.addChild((LayoutElement)LinearLayout.vertical(), $$0.newChildLayoutSettings().align(0.5F, 0.0F));
/*  61 */     $$1.defaultCellSetting().alignHorizontallyCenter().paddingBottom(8);
/*     */     
/*  63 */     $$1.addChild((LayoutElement)new StringWidget(getTitle(), this.font));
/*  64 */     $$1.addChild((LayoutElement)(new MultiLineTextWidget(DESCRIPTION, this.font)).setMaxWidth(this.width - 16).setCentered(true));
/*     */     
/*  66 */     GridLayout $$2 = twoButtonContainer(
/*  67 */         (AbstractWidget)Button.builder(BUTTON_PRIVACY_STATEMENT, this::openPrivacyStatementLink).build(), 
/*  68 */         (AbstractWidget)Button.builder(BUTTON_GIVE_FEEDBACK, this::openFeedbackLink).build());
/*     */     
/*  70 */     $$1.addChild((LayoutElement)$$2);
/*     */     
/*  72 */     Layout $$3 = createLowerSection();
/*  73 */     $$0.arrangeElements();
/*  74 */     $$3.arrangeElements();
/*     */     
/*  76 */     int $$4 = $$2.getY() + $$2.getHeight();
/*  77 */     int $$5 = $$3.getHeight();
/*  78 */     int $$6 = this.height - $$4 - $$5 - 16;
/*  79 */     this.telemetryEventWidget = new TelemetryEventWidget(0, 0, this.width - 40, $$6, this.minecraft.font);
/*  80 */     this.telemetryEventWidget.setScrollAmount(this.savedScroll);
/*  81 */     this.telemetryEventWidget.setOnScrolledListener($$0 -> this.savedScroll = $$0);
/*  82 */     setInitialFocus((GuiEventListener)this.telemetryEventWidget);
/*     */     
/*  84 */     $$1.addChild((LayoutElement)this.telemetryEventWidget);
/*  85 */     $$1.addChild((LayoutElement)$$3);
/*     */     
/*  87 */     $$0.arrangeElements();
/*  88 */     FrameLayout.alignInRectangle((LayoutElement)$$0, 0, 0, this.width, this.height, 0.5F, 0.0F);
/*  89 */     $$0.visitWidgets($$1 -> (AbstractWidget)$$0.addRenderableWidget($$1));
/*     */   }
/*     */   
/*     */   private Layout createLowerSection() {
/*  93 */     LinearLayout $$0 = LinearLayout.vertical();
/*  94 */     $$0.defaultCellSetting().alignHorizontallyCenter().paddingBottom(4);
/*     */     
/*  96 */     if (this.minecraft.extraTelemetryAvailable()) {
/*  97 */       $$0.addChild((LayoutElement)createTelemetryCheckbox());
/*     */     }
/*     */     
/* 100 */     $$0.addChild((LayoutElement)twoButtonContainer(
/* 101 */           (AbstractWidget)Button.builder(BUTTON_SHOW_DATA, this::openDataFolder).build(), 
/* 102 */           (AbstractWidget)Button.builder(CommonComponents.GUI_DONE, this::openLastScreen).build()));
/*     */ 
/*     */     
/* 105 */     return (Layout)$$0;
/*     */   }
/*     */   
/*     */   private AbstractWidget createTelemetryCheckbox() {
/* 109 */     OptionInstance<Boolean> $$0 = this.options.telemetryOptInExtra();
/*     */ 
/*     */ 
/*     */     
/* 113 */     Checkbox $$1 = Checkbox.builder(CHECKBOX_OPT_IN, this.minecraft.font).selected($$0).onValueChange(this::onOptInChanged).build();
/* 114 */     $$1.active = this.minecraft.extraTelemetryAvailable();
/* 115 */     return (AbstractWidget)$$1;
/*     */   }
/*     */   
/*     */   private void onOptInChanged(AbstractWidget $$0, boolean $$1) {
/* 119 */     if (this.telemetryEventWidget != null) {
/* 120 */       this.telemetryEventWidget.onOptInChanged($$1);
/*     */     }
/*     */   }
/*     */   
/*     */   private void openLastScreen(Button $$0) {
/* 125 */     this.minecraft.setScreen(this.lastScreen);
/*     */   }
/*     */   
/*     */   private void openPrivacyStatementLink(Button $$0) {
/* 129 */     ConfirmLinkScreen.confirmLinkNow(this, "http://go.microsoft.com/fwlink/?LinkId=521839");
/*     */   }
/*     */   
/*     */   private void openFeedbackLink(Button $$0) {
/* 133 */     ConfirmLinkScreen.confirmLinkNow(this, "https://aka.ms/javafeedback?ref=game");
/*     */   }
/*     */   
/*     */   private void openDataFolder(Button $$0) {
/* 137 */     Path $$1 = this.minecraft.getTelemetryManager().getLogDirectory();
/* 138 */     Util.getPlatform().openUri($$1.toUri());
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClose() {
/* 143 */     this.minecraft.setScreen(this.lastScreen);
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderBackground(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 148 */     renderDirtBackground($$0);
/*     */   }
/*     */   
/*     */   private GridLayout twoButtonContainer(AbstractWidget $$0, AbstractWidget $$1) {
/* 152 */     GridLayout $$2 = new GridLayout();
/* 153 */     $$2.defaultCellSetting().alignHorizontallyCenter().paddingHorizontal(4);
/*     */     
/* 155 */     $$2.addChild((LayoutElement)$$0, 0, 0);
/* 156 */     $$2.addChild((LayoutElement)$$1, 0, 1);
/* 157 */     return $$2;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\telemetry\TelemetryInfoScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */