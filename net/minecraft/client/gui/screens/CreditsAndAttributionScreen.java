/*    */ package net.minecraft.client.gui.screens;
/*    */ 
/*    */ import net.minecraft.client.gui.components.Button;
/*    */ import net.minecraft.client.gui.components.StringWidget;
/*    */ import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
/*    */ import net.minecraft.client.gui.layouts.LayoutElement;
/*    */ import net.minecraft.client.gui.layouts.LinearLayout;
/*    */ import net.minecraft.network.chat.CommonComponents;
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ public class CreditsAndAttributionScreen
/*    */   extends Screen {
/*    */   private static final int BUTTON_SPACING = 8;
/*    */   private static final int BUTTON_WIDTH = 210;
/* 15 */   private static final Component TITLE = (Component)Component.translatable("credits_and_attribution.screen.title");
/* 16 */   private static final Component CREDITS_BUTTON = (Component)Component.translatable("credits_and_attribution.button.credits");
/* 17 */   private static final Component ATTRIBUTION_BUTTON = (Component)Component.translatable("credits_and_attribution.button.attribution");
/* 18 */   private static final Component LICENSES_BUTTON = (Component)Component.translatable("credits_and_attribution.button.licenses");
/*    */   
/*    */   private final Screen lastScreen;
/* 21 */   private final HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this);
/*    */   
/*    */   public CreditsAndAttributionScreen(Screen $$0) {
/* 24 */     super(TITLE);
/* 25 */     this.lastScreen = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void init() {
/* 30 */     this.layout.addToHeader((LayoutElement)new StringWidget(getTitle(), this.font));
/*    */     
/* 32 */     LinearLayout $$0 = ((LinearLayout)this.layout.addToContents((LayoutElement)LinearLayout.vertical())).spacing(8);
/* 33 */     $$0.defaultCellSetting().alignHorizontallyCenter();
/*    */     
/* 35 */     $$0.addChild((LayoutElement)Button.builder(CREDITS_BUTTON, $$0 -> openCreditsScreen()).width(210).build());
/* 36 */     $$0.addChild((LayoutElement)Button.builder(ATTRIBUTION_BUTTON, ConfirmLinkScreen.confirmLink(this, "https://aka.ms/MinecraftJavaAttribution")).width(210).build());
/* 37 */     $$0.addChild((LayoutElement)Button.builder(LICENSES_BUTTON, ConfirmLinkScreen.confirmLink(this, "https://aka.ms/MinecraftJavaLicenses")).width(210).build());
/*    */     
/* 39 */     this.layout.addToFooter((LayoutElement)Button.builder(CommonComponents.GUI_DONE, $$0 -> onClose()).build());
/*    */     
/* 41 */     this.layout.arrangeElements();
/* 42 */     this.layout.visitWidgets(this::addRenderableWidget);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void repositionElements() {
/* 47 */     this.layout.arrangeElements();
/*    */   }
/*    */   
/*    */   private void openCreditsScreen() {
/* 51 */     this.minecraft.setScreen(new WinScreen(false, () -> this.minecraft.setScreen(this)));
/*    */   }
/*    */ 
/*    */   
/*    */   public void onClose() {
/* 56 */     this.minecraft.setScreen(this.lastScreen);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\CreditsAndAttributionScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */