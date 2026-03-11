/*     */ package net.minecraft.client.gui.screens;
/*     */ import com.mojang.text2speech.Narrator;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.Options;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.AbstractWidget;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.CommonButtons;
/*     */ import net.minecraft.client.gui.components.FocusableTextWidget;
/*     */ import net.minecraft.client.gui.components.LogoRenderer;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.layouts.FrameLayout;
/*     */ import net.minecraft.client.gui.layouts.LayoutElement;
/*     */ import net.minecraft.client.gui.layouts.LayoutSettings;
/*     */ import net.minecraft.client.gui.layouts.LinearLayout;
/*     */ import net.minecraft.client.renderer.PanoramaRenderer;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ 
/*     */ public class AccessibilityOnboardingScreen extends Screen {
/*  22 */   private static final Component ONBOARDING_NARRATOR_MESSAGE = (Component)Component.translatable("accessibility.onboarding.screen.narrator");
/*     */   
/*     */   private static final int PADDING = 4;
/*     */   private static final int TITLE_PADDING = 16;
/*  26 */   private final PanoramaRenderer panorama = new PanoramaRenderer(TitleScreen.CUBE_MAP);
/*     */   
/*     */   private final LogoRenderer logoRenderer;
/*     */   
/*     */   private final Options options;
/*     */   
/*     */   private final boolean narratorAvailable;
/*     */   private boolean hasNarrated;
/*     */   private float timer;
/*     */   private final Runnable onClose;
/*     */   @Nullable
/*     */   private FocusableTextWidget textWidget;
/*     */   
/*     */   public AccessibilityOnboardingScreen(Options $$0, Runnable $$1) {
/*  40 */     super((Component)Component.translatable("accessibility.onboarding.screen.title"));
/*  41 */     this.options = $$0;
/*  42 */     this.onClose = $$1;
/*  43 */     this.logoRenderer = new LogoRenderer(true);
/*  44 */     this.narratorAvailable = Minecraft.getInstance().getNarrator().isActive();
/*     */   }
/*     */ 
/*     */   
/*     */   public void init() {
/*  49 */     int $$0 = initTitleYPos();
/*  50 */     FrameLayout $$1 = new FrameLayout(this.width, this.height - $$0);
/*  51 */     $$1.defaultChildLayoutSetting().alignVerticallyTop().padding(4);
/*     */     
/*  53 */     LinearLayout $$2 = (LinearLayout)$$1.addChild((LayoutElement)LinearLayout.vertical());
/*  54 */     $$2.defaultCellSetting().alignHorizontallyCenter().padding(2);
/*     */     
/*  56 */     this.textWidget = new FocusableTextWidget(this.width - 16, this.title, this.font);
/*  57 */     $$2.addChild((LayoutElement)this.textWidget, $$0 -> $$0.paddingBottom(16));
/*     */     
/*  59 */     AbstractWidget $$3 = this.options.narrator().createButton(this.options, 0, 0, 150);
/*  60 */     $$3.active = this.narratorAvailable;
/*  61 */     $$2.addChild((LayoutElement)$$3);
/*  62 */     if (this.narratorAvailable) {
/*  63 */       setInitialFocus((GuiEventListener)$$3);
/*     */     }
/*     */     
/*  66 */     $$2.addChild((LayoutElement)CommonButtons.accessibility(150, $$0 -> closeAndSetScreen(new AccessibilityOptionsScreen(this, this.minecraft.options)), false));
/*     */     
/*  68 */     $$2.addChild((LayoutElement)CommonButtons.language(150, $$0 -> closeAndSetScreen(new LanguageSelectScreen(this, this.minecraft.options, this.minecraft.getLanguageManager())), false));
/*     */     
/*  70 */     $$1.addChild(
/*  71 */         (LayoutElement)Button.builder(CommonComponents.GUI_CONTINUE, $$0 -> onClose())
/*     */ 
/*     */         
/*  74 */         .build(), $$1
/*  75 */         .newChildLayoutSettings().alignVerticallyBottom().padding(8));
/*     */ 
/*     */     
/*  78 */     $$1.arrangeElements();
/*  79 */     FrameLayout.alignInRectangle((LayoutElement)$$1, 0, $$0, this.width, this.height, 0.5F, 0.0F);
/*  80 */     $$1.visitWidgets(this::addRenderableWidget);
/*     */   }
/*     */   
/*     */   private int initTitleYPos() {
/*  84 */     return 90;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClose() {
/*  89 */     close(this.onClose);
/*     */   }
/*     */   
/*     */   private void closeAndSetScreen(Screen $$0) {
/*  93 */     close(() -> this.minecraft.setScreen($$0));
/*     */   }
/*     */   
/*     */   private void close(Runnable $$0) {
/*  97 */     this.options.onboardAccessibility = false;
/*  98 */     this.options.save();
/*  99 */     Narrator.getNarrator().clear();
/* 100 */     $$0.run();
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 105 */     super.render($$0, $$1, $$2, $$3);
/*     */     
/* 107 */     handleInitialNarrationDelay();
/*     */     
/* 109 */     this.logoRenderer.renderLogo($$0, this.width, 1.0F);
/*     */     
/* 111 */     if (this.textWidget != null) {
/* 112 */       this.textWidget.render($$0, $$1, $$2, $$3);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderBackground(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 118 */     this.panorama.render(0.0F, 1.0F);
/*     */     
/* 120 */     $$0.fill(0, 0, this.width, this.height, -1877995504);
/*     */   }
/*     */   
/*     */   private void handleInitialNarrationDelay() {
/* 124 */     if (!this.hasNarrated && this.narratorAvailable)
/* 125 */       if (this.timer < 40.0F) {
/* 126 */         this.timer++;
/* 127 */       } else if (this.minecraft.isWindowActive()) {
/*     */         
/* 129 */         Narrator.getNarrator().say(ONBOARDING_NARRATOR_MESSAGE.getString(), true);
/* 130 */         this.hasNarrated = true;
/*     */       }  
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\AccessibilityOnboardingScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */