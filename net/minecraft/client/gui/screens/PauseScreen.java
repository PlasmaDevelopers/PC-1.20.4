/*     */ package net.minecraft.client.gui.screens;
/*     */ 
/*     */ import com.mojang.realmsclient.RealmsMainScreen;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.SharedConstants;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.StringWidget;
/*     */ import net.minecraft.client.gui.layouts.FrameLayout;
/*     */ import net.minecraft.client.gui.layouts.GridLayout;
/*     */ import net.minecraft.client.gui.layouts.LayoutElement;
/*     */ import net.minecraft.client.gui.screens.achievement.StatsScreen;
/*     */ import net.minecraft.client.gui.screens.advancements.AdvancementsScreen;
/*     */ import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
/*     */ import net.minecraft.client.multiplayer.ServerData;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ 
/*     */ public class PauseScreen
/*     */   extends Screen {
/*  24 */   private static final ResourceLocation DRAFT_REPORT_SPRITE = new ResourceLocation("icon/draft_report");
/*     */   
/*     */   private static final int COLUMNS = 2;
/*     */   private static final int MENU_PADDING_TOP = 50;
/*     */   private static final int BUTTON_PADDING = 4;
/*     */   private static final int BUTTON_WIDTH_FULL = 204;
/*     */   private static final int BUTTON_WIDTH_HALF = 98;
/*  31 */   private static final Component RETURN_TO_GAME = (Component)Component.translatable("menu.returnToGame");
/*  32 */   private static final Component ADVANCEMENTS = (Component)Component.translatable("gui.advancements");
/*  33 */   private static final Component STATS = (Component)Component.translatable("gui.stats");
/*  34 */   private static final Component SEND_FEEDBACK = (Component)Component.translatable("menu.sendFeedback");
/*  35 */   private static final Component REPORT_BUGS = (Component)Component.translatable("menu.reportBugs");
/*  36 */   private static final Component OPTIONS = (Component)Component.translatable("menu.options");
/*  37 */   private static final Component SHARE_TO_LAN = (Component)Component.translatable("menu.shareToLan");
/*  38 */   private static final Component PLAYER_REPORTING = (Component)Component.translatable("menu.playerReporting");
/*  39 */   private static final Component RETURN_TO_MENU = (Component)Component.translatable("menu.returnToMenu");
/*  40 */   private static final Component SAVING_LEVEL = (Component)Component.translatable("menu.savingLevel");
/*  41 */   private static final Component GAME = (Component)Component.translatable("menu.game");
/*  42 */   private static final Component PAUSED = (Component)Component.translatable("menu.paused");
/*     */   
/*     */   private final boolean showPauseMenu;
/*     */   
/*     */   @Nullable
/*     */   private Button disconnectButton;
/*     */   
/*     */   public PauseScreen(boolean $$0) {
/*  50 */     super($$0 ? GAME : PAUSED);
/*  51 */     this.showPauseMenu = $$0;
/*     */   }
/*     */   
/*     */   public boolean showsPauseMenu() {
/*  55 */     return this.showPauseMenu;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init() {
/*  60 */     if (this.showPauseMenu) {
/*  61 */       createPauseMenu();
/*     */     }
/*     */     
/*  64 */     Objects.requireNonNull(this.font); addRenderableWidget(new StringWidget(0, this.showPauseMenu ? 40 : 10, this.width, 9, this.title, this.font));
/*     */   }
/*     */   
/*     */   private void createPauseMenu() {
/*  68 */     GridLayout $$0 = new GridLayout();
/*  69 */     $$0.defaultCellSetting().padding(4, 4, 4, 0);
/*     */     
/*  71 */     GridLayout.RowHelper $$1 = $$0.createRowHelper(2);
/*     */     
/*  73 */     $$1.addChild(
/*  74 */         (LayoutElement)Button.builder(RETURN_TO_GAME, $$0 -> {
/*     */             this.minecraft.setScreen(null);
/*     */             this.minecraft.mouseHandler.grabMouse();
/*  77 */           }).width(204).build(), 2, $$0
/*  78 */         .newCellSettings().paddingTop(50));
/*     */ 
/*     */     
/*  81 */     $$1.addChild((LayoutElement)openScreenButton(ADVANCEMENTS, () -> new AdvancementsScreen(this.minecraft.player.connection.getAdvancements())));
/*  82 */     $$1.addChild((LayoutElement)openScreenButton(STATS, () -> new StatsScreen(this, this.minecraft.player.getStats())));
/*     */     
/*  84 */     $$1.addChild((LayoutElement)openLinkButton(SEND_FEEDBACK, SharedConstants.getCurrentVersion().isStable() ? "https://aka.ms/javafeedback?ref=game" : "https://aka.ms/snapshotfeedback?ref=game"));
/*  85 */     ((Button)$$1.addChild((LayoutElement)openLinkButton(REPORT_BUGS, "https://aka.ms/snapshotbugs?ref=game"))).active = !SharedConstants.getCurrentVersion().getDataVersion().isSideSeries();
/*     */     
/*  87 */     $$1.addChild((LayoutElement)openScreenButton(OPTIONS, () -> new OptionsScreen(this, this.minecraft.options)));
/*     */     
/*  89 */     if (this.minecraft.hasSingleplayerServer() && !this.minecraft.getSingleplayerServer().isPublished()) {
/*  90 */       $$1.addChild((LayoutElement)openScreenButton(SHARE_TO_LAN, () -> new ShareToLanScreen(this)));
/*     */     } else {
/*  92 */       $$1.addChild((LayoutElement)openScreenButton(PLAYER_REPORTING, net.minecraft.client.gui.screens.social.SocialInteractionsScreen::new));
/*     */     } 
/*     */     
/*  95 */     Component $$2 = this.minecraft.isLocalServer() ? RETURN_TO_MENU : CommonComponents.GUI_DISCONNECT;
/*  96 */     this.disconnectButton = (Button)$$1.addChild(
/*  97 */         (LayoutElement)Button.builder($$2, $$0 -> {
/*     */             $$0.active = false;
/*     */             
/*     */             this.minecraft.getReportingContext().draftReportHandled(this.minecraft, this, this::onDisconnect, true);
/* 101 */           }).width(204).build(), 2);
/*     */ 
/*     */ 
/*     */     
/* 105 */     $$0.arrangeElements();
/* 106 */     FrameLayout.alignInRectangle((LayoutElement)$$0, 0, 0, this.width, this.height, 0.5F, 0.25F);
/*     */     
/* 108 */     $$0.visitWidgets(this::addRenderableWidget);
/*     */   }
/*     */   
/*     */   private void onDisconnect() {
/* 112 */     boolean $$0 = this.minecraft.isLocalServer();
/* 113 */     ServerData $$1 = this.minecraft.getCurrentServer();
/*     */     
/* 115 */     this.minecraft.level.disconnect();
/* 116 */     if ($$0) {
/* 117 */       this.minecraft.disconnect(new GenericDirtMessageScreen(SAVING_LEVEL));
/*     */     } else {
/* 119 */       this.minecraft.disconnect();
/*     */     } 
/*     */     
/* 122 */     TitleScreen $$2 = new TitleScreen();
/* 123 */     if ($$0) {
/* 124 */       this.minecraft.setScreen($$2);
/* 125 */     } else if ($$1 != null && $$1.isRealm()) {
/* 126 */       this.minecraft.setScreen((Screen)new RealmsMainScreen($$2));
/*     */     } else {
/* 128 */       this.minecraft.setScreen((Screen)new JoinMultiplayerScreen($$2));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 134 */     super.tick();
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 139 */     super.render($$0, $$1, $$2, $$3);
/*     */     
/* 141 */     if (this.showPauseMenu && this.minecraft != null && this.minecraft.getReportingContext().hasDraftReport() && this.disconnectButton != null) {
/* 142 */       $$0.blitSprite(DRAFT_REPORT_SPRITE, this.disconnectButton.getX() + this.disconnectButton.getWidth() - 17, this.disconnectButton.getY() + 3, 15, 15);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderBackground(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 148 */     if (this.showPauseMenu) {
/* 149 */       super.renderBackground($$0, $$1, $$2, $$3);
/*     */     }
/*     */   }
/*     */   
/*     */   private Button openScreenButton(Component $$0, Supplier<Screen> $$1) {
/* 154 */     return Button.builder($$0, $$1 -> this.minecraft.setScreen($$0.get())).width(98).build();
/*     */   }
/*     */   
/*     */   private Button openLinkButton(Component $$0, String $$1) {
/* 158 */     return Button.builder($$0, ConfirmLinkScreen.confirmLink(this, $$1)).width(98).build();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\PauseScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */