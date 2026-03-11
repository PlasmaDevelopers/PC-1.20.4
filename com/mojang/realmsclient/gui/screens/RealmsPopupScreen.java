/*     */ package com.mojang.realmsclient.gui.screens;
/*     */ 
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.FittingMultiLineTextWidget;
/*     */ import net.minecraft.client.gui.components.ImageButton;
/*     */ import net.minecraft.client.gui.components.Tooltip;
/*     */ import net.minecraft.client.gui.components.WidgetSprites;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.screens.ConfirmLinkScreen;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.realms.RealmsScreen;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.packs.resources.ResourceManager;
/*     */ 
/*     */ 
/*     */ public class RealmsPopupScreen
/*     */   extends RealmsScreen
/*     */ {
/*  26 */   private static final Component POPUP_TEXT = (Component)Component.translatable("mco.selectServer.popup");
/*  27 */   private static final Component CLOSE_TEXT = (Component)Component.translatable("mco.selectServer.close");
/*     */   
/*  29 */   private static final ResourceLocation BACKGROUND_SPRITE = new ResourceLocation("popup/background");
/*  30 */   private static final ResourceLocation TRIAL_AVAILABLE_SPRITE = new ResourceLocation("icon/trial_available");
/*     */   
/*  32 */   private static final WidgetSprites CROSS_BUTTON_SPRITES = new WidgetSprites(new ResourceLocation("widget/cross_button"), new ResourceLocation("widget/cross_button_highlighted"));
/*     */   
/*     */   private static final int BG_TEXTURE_WIDTH = 236;
/*     */   
/*     */   private static final int BG_TEXTURE_HEIGHT = 34;
/*     */   
/*     */   private static final int BG_BORDER_SIZE = 6;
/*     */   
/*     */   private static final int IMAGE_WIDTH = 195;
/*     */   
/*     */   private static final int IMAGE_HEIGHT = 152;
/*     */   
/*     */   private static final int BUTTON_SPACING = 4;
/*     */   
/*     */   private static final int PADDING = 10;
/*     */   
/*     */   private static final int WIDTH = 320;
/*     */   private static final int HEIGHT = 172;
/*     */   private static final int TEXT_WIDTH = 100;
/*     */   private static final int BUTTON_WIDTH = 99;
/*     */   private static final int CAROUSEL_SWITCH_INTERVAL = 100;
/*  53 */   private static List<ResourceLocation> carouselImages = List.of();
/*     */   
/*     */   private final Screen backgroundScreen;
/*     */   
/*     */   private final boolean trialAvailable;
/*     */   
/*     */   @Nullable
/*     */   private Button createTrialButton;
/*     */   private int carouselIndex;
/*     */   private int carouselTick;
/*     */   
/*     */   public RealmsPopupScreen(Screen $$0, boolean $$1) {
/*  65 */     super(POPUP_TEXT);
/*  66 */     this.backgroundScreen = $$0;
/*  67 */     this.trialAvailable = $$1;
/*     */   }
/*     */   
/*     */   public static void updateCarouselImages(ResourceManager $$0) {
/*  71 */     Collection<ResourceLocation> $$1 = $$0.listResources("textures/gui/images", $$0 -> $$0.getPath().endsWith(".png")).keySet();
/*  72 */     carouselImages = $$1.stream().filter($$0 -> $$0.getNamespace().equals("realms")).toList();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init() {
/*  77 */     this.backgroundScreen.resize(this.minecraft, this.width, this.height);
/*     */     
/*  79 */     if (this.trialAvailable) {
/*  80 */       this.createTrialButton = (Button)addRenderableWidget(
/*  81 */           (GuiEventListener)Button.builder((Component)Component.translatable("mco.selectServer.trial"), 
/*  82 */             ConfirmLinkScreen.confirmLink((Screen)this, "https://aka.ms/startjavarealmstrial"))
/*  83 */           .bounds(right() - 10 - 99, bottom() - 10 - 4 - 40, 99, 20).build());
/*     */     }
/*     */ 
/*     */     
/*  87 */     addRenderableWidget((GuiEventListener)Button.builder((Component)Component.translatable("mco.selectServer.buy"), 
/*  88 */           ConfirmLinkScreen.confirmLink((Screen)this, "https://aka.ms/BuyJavaRealms"))
/*  89 */         .bounds(right() - 10 - 99, bottom() - 10 - 20, 99, 20).build());
/*     */     
/*  91 */     ImageButton $$0 = (ImageButton)addRenderableWidget((GuiEventListener)new ImageButton(left() + 4, top() + 4, 14, 14, CROSS_BUTTON_SPRITES, $$0 -> onClose(), CLOSE_TEXT));
/*  92 */     $$0.setTooltip(Tooltip.create(CLOSE_TEXT));
/*     */     
/*  94 */     int $$1 = 142 - (this.trialAvailable ? 40 : 20);
/*  95 */     FittingMultiLineTextWidget $$2 = new FittingMultiLineTextWidget(right() - 10 - 100, top() + 10, 100, $$1, POPUP_TEXT, this.font);
/*  96 */     if ($$2.showingScrollBar()) {
/*  97 */       $$2.setWidth(100 - $$2.scrollbarWidth());
/*     */     }
/*  99 */     addRenderableWidget((GuiEventListener)$$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 104 */     super.tick();
/* 105 */     if (++this.carouselTick > 100) {
/* 106 */       this.carouselTick = 0;
/* 107 */       this.carouselIndex = (this.carouselIndex + 1) % carouselImages.size();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 113 */     super.render($$0, $$1, $$2, $$3);
/*     */     
/* 115 */     if (this.createTrialButton != null) {
/* 116 */       renderDiamond($$0, this.createTrialButton);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void renderDiamond(GuiGraphics $$0, Button $$1) {
/* 121 */     int $$2 = 8;
/* 122 */     $$0.pose().pushPose();
/* 123 */     $$0.pose().translate(0.0F, 0.0F, 110.0F);
/* 124 */     $$0.blitSprite(TRIAL_AVAILABLE_SPRITE, $$1.getX() + $$1.getWidth() - 8 - 4, $$1.getY() + $$1.getHeight() / 2 - 4, 8, 8);
/* 125 */     $$0.pose().popPose();
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderBackground(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 130 */     this.backgroundScreen.render($$0, -1, -1, $$3);
/* 131 */     $$0.flush();
/* 132 */     RenderSystem.clear(256, Minecraft.ON_OSX);
/*     */     
/* 134 */     renderTransparentBackground($$0);
/* 135 */     $$0.blitSprite(BACKGROUND_SPRITE, left(), top(), 320, 172);
/* 136 */     if (!carouselImages.isEmpty()) {
/* 137 */       $$0.blit(carouselImages.get(this.carouselIndex), left() + 10, top() + 10, 0, 0.0F, 0.0F, 195, 152, 195, 152);
/*     */     }
/*     */   }
/*     */   
/*     */   private int left() {
/* 142 */     return (this.width - 320) / 2;
/*     */   }
/*     */   
/*     */   private int top() {
/* 146 */     return (this.height - 172) / 2;
/*     */   }
/*     */   
/*     */   private int right() {
/* 150 */     return left() + 320;
/*     */   }
/*     */   
/*     */   private int bottom() {
/* 154 */     return top() + 172;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClose() {
/* 159 */     this.minecraft.setScreen(this.backgroundScreen);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\gui\screens\RealmsPopupScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */