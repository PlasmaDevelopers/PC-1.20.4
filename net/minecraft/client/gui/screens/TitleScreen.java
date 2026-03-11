/*     */ package net.minecraft.client.gui.screens;
/*     */ 
/*     */ import com.mojang.authlib.minecraft.BanDetails;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.realmsclient.RealmsMainScreen;
/*     */ import com.mojang.realmsclient.gui.screens.RealmsNotificationsScreen;
/*     */ import java.io.IOException;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.Executor;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.SharedConstants;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Font;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.AbstractWidget;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.CommonButtons;
/*     */ import net.minecraft.client.gui.components.LogoRenderer;
/*     */ import net.minecraft.client.gui.components.MultiLineLabel;
/*     */ import net.minecraft.client.gui.components.PlainTextButton;
/*     */ import net.minecraft.client.gui.components.SplashRenderer;
/*     */ import net.minecraft.client.gui.components.SpriteIconButton;
/*     */ import net.minecraft.client.gui.components.Tooltip;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.components.toasts.SystemToast;
/*     */ import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
/*     */ import net.minecraft.client.gui.screens.multiplayer.SafetyScreen;
/*     */ import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
/*     */ import net.minecraft.client.renderer.CubeMap;
/*     */ import net.minecraft.client.renderer.PanoramaRenderer;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.level.levelgen.WorldOptions;
/*     */ import net.minecraft.world.level.levelgen.presets.WorldPresets;
/*     */ import net.minecraft.world.level.storage.LevelStorageSource;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class TitleScreen
/*     */   extends Screen {
/*  48 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private static final String DEMO_LEVEL_ID = "Demo_World";
/*  51 */   public static final Component COPYRIGHT_TEXT = (Component)Component.translatable("title.credits");
/*  52 */   public static final CubeMap CUBE_MAP = new CubeMap(new ResourceLocation("textures/gui/title/background/panorama"));
/*  53 */   private static final ResourceLocation PANORAMA_OVERLAY = new ResourceLocation("textures/gui/title/background/panorama_overlay.png");
/*     */   
/*     */   @Nullable
/*     */   private SplashRenderer splash;
/*     */   
/*     */   private Button resetDemoButton;
/*     */   @Nullable
/*     */   private RealmsNotificationsScreen realmsNotificationsScreen;
/*  61 */   private final PanoramaRenderer panorama = new PanoramaRenderer(CUBE_MAP);
/*     */   
/*     */   private final boolean fading;
/*     */   
/*     */   private long fadeInStart;
/*     */   @Nullable
/*     */   private WarningLabel warningLabel;
/*     */   private final LogoRenderer logoRenderer;
/*     */   
/*     */   public TitleScreen() {
/*  71 */     this(false);
/*     */   }
/*     */   
/*     */   public TitleScreen(boolean $$0) {
/*  75 */     this($$0, (LogoRenderer)null);
/*     */   }
/*     */   
/*     */   public TitleScreen(boolean $$0, @Nullable LogoRenderer $$1) {
/*  79 */     super((Component)Component.translatable("narrator.screen.title"));
/*  80 */     this.fading = $$0;
/*  81 */     this.logoRenderer = Objects.<LogoRenderer>requireNonNullElseGet($$1, () -> new LogoRenderer(false));
/*     */   }
/*     */   
/*     */   private boolean realmsNotificationsEnabled() {
/*  85 */     return (this.realmsNotificationsScreen != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/*  90 */     if (realmsNotificationsEnabled()) {
/*  91 */       this.realmsNotificationsScreen.tick();
/*     */     }
/*  93 */     this.minecraft.getRealms32BitWarningStatus().showRealms32BitWarningIfNeeded(this);
/*     */   }
/*     */   
/*     */   public static CompletableFuture<Void> preloadResources(TextureManager $$0, Executor $$1) {
/*  97 */     return CompletableFuture.allOf((CompletableFuture<?>[])new CompletableFuture[] { $$0
/*  98 */           .preload(LogoRenderer.MINECRAFT_LOGO, $$1), $$0
/*  99 */           .preload(LogoRenderer.MINECRAFT_EDITION, $$1), $$0
/* 100 */           .preload(PANORAMA_OVERLAY, $$1), CUBE_MAP
/* 101 */           .preload($$0, $$1) });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPauseScreen() {
/* 107 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldCloseOnEsc() {
/* 112 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init() {
/* 117 */     if (this.splash == null) {
/* 118 */       this.splash = this.minecraft.getSplashManager().getSplash();
/*     */     }
/* 120 */     int $$0 = this.font.width((FormattedText)COPYRIGHT_TEXT);
/* 121 */     int $$1 = this.width - $$0 - 2;
/*     */     
/* 123 */     int $$2 = 24;
/* 124 */     int $$3 = this.height / 4 + 48;
/*     */     
/* 126 */     if (this.minecraft.isDemo()) {
/* 127 */       createDemoMenuOptions($$3, 24);
/*     */     } else {
/* 129 */       createNormalMenuOptions($$3, 24);
/*     */     } 
/*     */     
/* 132 */     SpriteIconButton $$4 = addRenderableWidget(CommonButtons.language(20, $$0 -> this.minecraft.setScreen(new LanguageSelectScreen(this, this.minecraft.options, this.minecraft.getLanguageManager())), true));
/* 133 */     $$4.setPosition(this.width / 2 - 124, $$3 + 72 + 12);
/* 134 */     addRenderableWidget(Button.builder((Component)Component.translatable("menu.options"), $$0 -> this.minecraft.setScreen(new OptionsScreen(this, this.minecraft.options))).bounds(this.width / 2 - 100, $$3 + 72 + 12, 98, 20).build());
/* 135 */     addRenderableWidget(Button.builder((Component)Component.translatable("menu.quit"), $$0 -> this.minecraft.stop()).bounds(this.width / 2 + 2, $$3 + 72 + 12, 98, 20).build());
/* 136 */     SpriteIconButton $$5 = addRenderableWidget(CommonButtons.accessibility(20, $$0 -> this.minecraft.setScreen(new AccessibilityOptionsScreen(this, this.minecraft.options)), true));
/* 137 */     $$5.setPosition(this.width / 2 + 104, $$3 + 72 + 12);
/* 138 */     addRenderableWidget(new PlainTextButton($$1, this.height - 10, $$0, 10, COPYRIGHT_TEXT, $$0 -> this.minecraft.setScreen(new CreditsAndAttributionScreen(this)), this.font));
/*     */     
/* 140 */     if (this.realmsNotificationsScreen == null) {
/* 141 */       this.realmsNotificationsScreen = new RealmsNotificationsScreen();
/*     */     }
/*     */     
/* 144 */     if (realmsNotificationsEnabled()) {
/* 145 */       this.realmsNotificationsScreen.init(this.minecraft, this.width, this.height);
/*     */     }
/*     */     
/* 148 */     if (!this.minecraft.is64Bit()) {
/* 149 */       this
/*     */         
/* 151 */         .warningLabel = new WarningLabel(this.font, MultiLineLabel.create(this.font, (FormattedText)Component.translatable("title.32bit.deprecation"), 350, 2), this.width / 2, $$3 - 24);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createNormalMenuOptions(int $$0, int $$1) {
/* 159 */     addRenderableWidget(Button.builder((Component)Component.translatable("menu.singleplayer"), $$0 -> this.minecraft.setScreen((Screen)new SelectWorldScreen(this))).bounds(this.width / 2 - 100, $$0, 200, 20).build());
/* 160 */     Component $$2 = getMultiplayerDisabledReason();
/* 161 */     boolean $$3 = ($$2 == null);
/*     */     
/* 163 */     Tooltip $$4 = ($$2 != null) ? Tooltip.create($$2) : null;
/* 164 */     ((Button)addRenderableWidget((T)Button.builder((Component)Component.translatable("menu.multiplayer"), $$0 -> {
/*     */             Screen $$1 = this.minecraft.options.skipMultiplayerWarning ? (Screen)new JoinMultiplayerScreen(this) : (Screen)new SafetyScreen(this);
/*     */             
/*     */             this.minecraft.setScreen($$1);
/* 168 */           }).bounds(this.width / 2 - 100, $$0 + $$1 * 1, 200, 20).tooltip($$4).build())).active = $$3;
/* 169 */     ((Button)addRenderableWidget((T)Button.builder((Component)Component.translatable("menu.online"), $$0 -> realmsButtonClicked()).bounds(this.width / 2 - 100, $$0 + $$1 * 2, 200, 20).tooltip($$4).build())).active = $$3;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private Component getMultiplayerDisabledReason() {
/* 174 */     if (this.minecraft.allowsMultiplayer()) {
/* 175 */       return null;
/*     */     }
/*     */     
/* 178 */     if (this.minecraft.isNameBanned()) {
/* 179 */       return (Component)Component.translatable("title.multiplayer.disabled.banned.name");
/*     */     }
/*     */     
/* 182 */     BanDetails $$0 = this.minecraft.multiplayerBan();
/* 183 */     if ($$0 != null) {
/* 184 */       if ($$0.expires() != null) {
/* 185 */         return (Component)Component.translatable("title.multiplayer.disabled.banned.temporary");
/*     */       }
/* 187 */       return (Component)Component.translatable("title.multiplayer.disabled.banned.permanent");
/*     */     } 
/*     */ 
/*     */     
/* 191 */     return (Component)Component.translatable("title.multiplayer.disabled");
/*     */   }
/*     */   
/*     */   private void createDemoMenuOptions(int $$0, int $$1) {
/* 195 */     boolean $$2 = checkDemoWorldPresence();
/*     */     
/* 197 */     addRenderableWidget(Button.builder((Component)Component.translatable("menu.playdemo"), $$1 -> {
/*     */             if ($$0) {
/*     */               this.minecraft.createWorldOpenFlows().checkForBackupAndLoad("Demo_World", ());
/*     */             } else {
/*     */               this.minecraft.createWorldOpenFlows().createFreshLevel("Demo_World", MinecraftServer.DEMO_SETTINGS, WorldOptions.DEMO_OPTIONS, WorldPresets::createNormalWorldDimensions, this);
/*     */             } 
/* 203 */           }).bounds(this.width / 2 - 100, $$0, 200, 20).build());
/* 204 */     this.resetDemoButton = addRenderableWidget(Button.builder((Component)Component.translatable("menu.resetdemo"), $$0 -> { LevelStorageSource $$1 = this.minecraft.getLevelSource(); try { LevelStorageSource.LevelStorageAccess $$2 = $$1.createAccess("Demo_World"); try { if ($$2.hasWorldData())
/*     */                   this.minecraft.setScreen(new ConfirmScreen(this::confirmDemo, (Component)Component.translatable("selectWorld.deleteQuestion"), (Component)Component.translatable("selectWorld.deleteWarning", new Object[] { MinecraftServer.DEMO_SETTINGS.levelName() }), (Component)Component.translatable("selectWorld.deleteButton"), CommonComponents.GUI_CANCEL));  if ($$2 != null)
/*     */                   $$2.close();  }
/* 207 */               catch (Throwable throwable) { if ($$2 != null) try { $$2.close(); } catch (Throwable throwable1)
/*     */                   { throwable.addSuppressed(throwable1); }
/*     */                 
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/*     */                 throw throwable; }
/*     */                }
/* 216 */             catch (IOException $$3)
/*     */             { SystemToast.onWorldAccessFailure(this.minecraft, "Demo_World");
/*     */               LOGGER.warn("Failed to access demo world", $$3); }
/*     */           
/* 220 */           }).bounds(this.width / 2 - 100, $$0 + $$1 * 1, 200, 20).build());
/* 221 */     this.resetDemoButton.active = $$2;
/*     */   }
/*     */   
/*     */   private boolean checkDemoWorldPresence() {
/*     */     
/* 226 */     try { LevelStorageSource.LevelStorageAccess $$0 = this.minecraft.getLevelSource().createAccess("Demo_World"); 
/* 227 */       try { boolean bool = $$0.hasWorldData();
/* 228 */         if ($$0 != null) $$0.close();  return bool; } catch (Throwable throwable) { if ($$0 != null) try { $$0.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException $$1)
/* 229 */     { SystemToast.onWorldAccessFailure(this.minecraft, "Demo_World");
/* 230 */       LOGGER.warn("Failed to read demo world data", $$1);
/*     */       
/* 232 */       return false; }
/*     */   
/*     */   }
/*     */   private void realmsButtonClicked() {
/* 236 */     this.minecraft.setScreen((Screen)new RealmsMainScreen(this));
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 241 */     if (this.fadeInStart == 0L && this.fading) {
/* 242 */       this.fadeInStart = Util.getMillis();
/*     */     }
/* 244 */     float $$4 = this.fading ? ((float)(Util.getMillis() - this.fadeInStart) / 1000.0F) : 1.0F;
/*     */     
/* 246 */     this.panorama.render($$3, Mth.clamp($$4, 0.0F, 1.0F));
/*     */     
/* 248 */     RenderSystem.enableBlend();
/* 249 */     $$0.setColor(1.0F, 1.0F, 1.0F, this.fading ? Mth.ceil(Mth.clamp($$4, 0.0F, 1.0F)) : 1.0F);
/* 250 */     $$0.blit(PANORAMA_OVERLAY, 0, 0, this.width, this.height, 0.0F, 0.0F, 16, 128, 16, 128);
/* 251 */     $$0.setColor(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     
/* 253 */     float $$5 = this.fading ? Mth.clamp($$4 - 1.0F, 0.0F, 1.0F) : 1.0F;
/*     */     
/* 255 */     this.logoRenderer.renderLogo($$0, this.width, $$5);
/*     */     
/* 257 */     int $$6 = Mth.ceil($$5 * 255.0F) << 24;
/*     */     
/* 259 */     if (($$6 & 0xFC000000) == 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 264 */     if (this.warningLabel != null) {
/* 265 */       this.warningLabel.render($$0, $$6);
/*     */     }
/*     */     
/* 268 */     if (this.splash != null && !((Boolean)this.minecraft.options.hideSplashTexts().get()).booleanValue()) {
/* 269 */       this.splash.render($$0, this.width, this.font, $$6);
/*     */     }
/*     */     
/* 272 */     String $$7 = "Minecraft " + SharedConstants.getCurrentVersion().getName();
/*     */     
/* 274 */     if (this.minecraft.isDemo()) {
/* 275 */       $$7 = $$7 + " Demo";
/*     */     } else {
/* 277 */       $$7 = $$7 + $$7;
/*     */     } 
/*     */     
/* 280 */     if (Minecraft.checkModStatus().shouldReportAsModified()) {
/* 281 */       $$7 = $$7 + $$7;
/*     */     }
/*     */     
/* 284 */     $$0.drawString(this.font, $$7, 2, this.height - 10, 0xFFFFFF | $$6);
/*     */     
/* 286 */     for (GuiEventListener $$8 : children()) {
/* 287 */       if ($$8 instanceof AbstractWidget) {
/* 288 */         ((AbstractWidget)$$8).setAlpha($$5);
/*     */       }
/*     */     } 
/*     */     
/* 292 */     super.render($$0, $$1, $$2, $$3);
/*     */     
/* 294 */     if (realmsNotificationsEnabled() && $$5 >= 1.0F) {
/* 295 */       RenderSystem.enableDepthTest();
/* 296 */       this.realmsNotificationsScreen.render($$0, $$1, $$2, $$3);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderBackground(GuiGraphics $$0, int $$1, int $$2, float $$3) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double $$0, double $$1, int $$2) {
/* 307 */     if (super.mouseClicked($$0, $$1, $$2)) {
/* 308 */       return true;
/*     */     }
/*     */     
/* 311 */     if (realmsNotificationsEnabled() && this.realmsNotificationsScreen.mouseClicked($$0, $$1, $$2)) {
/* 312 */       return true;
/*     */     }
/*     */     
/* 315 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void removed() {
/* 320 */     if (this.realmsNotificationsScreen != null) {
/* 321 */       this.realmsNotificationsScreen.removed();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void added() {
/* 327 */     super.added();
/* 328 */     if (this.realmsNotificationsScreen != null) {
/* 329 */       this.realmsNotificationsScreen.added();
/*     */     }
/*     */   }
/*     */   
/*     */   private void confirmDemo(boolean $$0) {
/* 334 */     if ($$0) {
/*     */       
/* 336 */       try { LevelStorageSource.LevelStorageAccess $$1 = this.minecraft.getLevelSource().createAccess("Demo_World"); 
/* 337 */         try { $$1.deleteLevel();
/* 338 */           if ($$1 != null) $$1.close();  } catch (Throwable throwable) { if ($$1 != null) try { $$1.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException $$2)
/* 339 */       { SystemToast.onWorldDeleteFailure(this.minecraft, "Demo_World");
/* 340 */         LOGGER.warn("Failed to delete demo world", $$2); }
/*     */     
/*     */     }
/* 343 */     this.minecraft.setScreen(this);
/*     */   }
/*     */   private static final class WarningLabel extends Record { private final Font font; private final MultiLineLabel label; private final int x; private final int y;
/* 346 */     WarningLabel(Font $$0, MultiLineLabel $$1, int $$2, int $$3) { this.font = $$0; this.label = $$1; this.x = $$2; this.y = $$3; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/gui/screens/TitleScreen$WarningLabel;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #346	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 346 */       //   0	7	0	this	Lnet/minecraft/client/gui/screens/TitleScreen$WarningLabel; } public Font font() { return this.font; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/gui/screens/TitleScreen$WarningLabel;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #346	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/gui/screens/TitleScreen$WarningLabel; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/gui/screens/TitleScreen$WarningLabel;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #346	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/client/gui/screens/TitleScreen$WarningLabel;
/* 346 */       //   0	8	1	$$0	Ljava/lang/Object; } public MultiLineLabel label() { return this.label; } public int x() { return this.x; } public int y() { return this.y; }
/*     */      public void render(GuiGraphics $$0, int $$1) {
/* 348 */       Objects.requireNonNull(this.font); this.label.renderBackgroundCentered($$0, this.x, this.y, 9, 2, 0x200000 | Math.min($$1, 1426063360));
/* 349 */       Objects.requireNonNull(this.font); this.label.renderCentered($$0, this.x, this.y, 9, 0xFFFFFF | $$1);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\TitleScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */