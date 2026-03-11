/*      */ package net.minecraft.client.gui;
/*      */ 
/*      */ import com.google.common.collect.Lists;
/*      */ import com.google.common.collect.Ordering;
/*      */ import com.mojang.blaze3d.platform.GlStateManager;
/*      */ import com.mojang.blaze3d.platform.Window;
/*      */ import com.mojang.blaze3d.systems.RenderSystem;
/*      */ import com.mojang.blaze3d.vertex.PoseStack;
/*      */ import com.mojang.math.Axis;
/*      */ import java.util.Collection;
/*      */ import java.util.Comparator;
/*      */ import java.util.List;
/*      */ import java.util.Objects;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.ChatFormatting;
/*      */ import net.minecraft.Util;
/*      */ import net.minecraft.client.AttackIndicatorStatus;
/*      */ import net.minecraft.client.Camera;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.Options;
/*      */ import net.minecraft.client.gui.components.BossHealthOverlay;
/*      */ import net.minecraft.client.gui.components.ChatComponent;
/*      */ import net.minecraft.client.gui.components.DebugScreenOverlay;
/*      */ import net.minecraft.client.gui.components.PlayerTabOverlay;
/*      */ import net.minecraft.client.gui.components.SubtitleOverlay;
/*      */ import net.minecraft.client.gui.components.spectator.SpectatorGui;
/*      */ import net.minecraft.client.gui.screens.Screen;
/*      */ import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
/*      */ import net.minecraft.client.multiplayer.ClientLevel;
/*      */ import net.minecraft.client.renderer.LightTexture;
/*      */ import net.minecraft.client.renderer.RenderType;
/*      */ import net.minecraft.client.renderer.entity.ItemRenderer;
/*      */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*      */ import net.minecraft.client.resources.MobEffectTextureManager;
/*      */ import net.minecraft.client.server.IntegratedServer;
/*      */ import net.minecraft.core.BlockPos;
/*      */ import net.minecraft.network.chat.Component;
/*      */ import net.minecraft.network.chat.FormattedText;
/*      */ import net.minecraft.network.chat.MutableComponent;
/*      */ import net.minecraft.network.chat.numbers.NumberFormat;
/*      */ import net.minecraft.network.chat.numbers.StyledFormat;
/*      */ import net.minecraft.resources.ResourceLocation;
/*      */ import net.minecraft.tags.FluidTags;
/*      */ import net.minecraft.util.FastColor;
/*      */ import net.minecraft.util.Mth;
/*      */ import net.minecraft.util.RandomSource;
/*      */ import net.minecraft.util.StringUtil;
/*      */ import net.minecraft.world.effect.MobEffect;
/*      */ import net.minecraft.world.effect.MobEffectInstance;
/*      */ import net.minecraft.world.effect.MobEffects;
/*      */ import net.minecraft.world.entity.Entity;
/*      */ import net.minecraft.world.entity.HumanoidArm;
/*      */ import net.minecraft.world.entity.LivingEntity;
/*      */ import net.minecraft.world.entity.PlayerRideableJumping;
/*      */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*      */ import net.minecraft.world.entity.player.Player;
/*      */ import net.minecraft.world.food.FoodData;
/*      */ import net.minecraft.world.item.ItemStack;
/*      */ import net.minecraft.world.level.GameType;
/*      */ import net.minecraft.world.level.Level;
/*      */ import net.minecraft.world.level.block.Blocks;
/*      */ import net.minecraft.world.level.border.WorldBorder;
/*      */ import net.minecraft.world.phys.BlockHitResult;
/*      */ import net.minecraft.world.phys.EntityHitResult;
/*      */ import net.minecraft.world.phys.HitResult;
/*      */ import net.minecraft.world.scores.DisplaySlot;
/*      */ import net.minecraft.world.scores.Objective;
/*      */ import net.minecraft.world.scores.PlayerScoreEntry;
/*      */ import net.minecraft.world.scores.PlayerTeam;
/*      */ import net.minecraft.world.scores.Scoreboard;
/*      */ import net.minecraft.world.scores.Team;
/*      */ 
/*      */ public class Gui
/*      */ {
/*   75 */   private static final ResourceLocation CROSSHAIR_SPRITE = new ResourceLocation("hud/crosshair");
/*   76 */   private static final ResourceLocation CROSSHAIR_ATTACK_INDICATOR_FULL_SPRITE = new ResourceLocation("hud/crosshair_attack_indicator_full");
/*   77 */   private static final ResourceLocation CROSSHAIR_ATTACK_INDICATOR_BACKGROUND_SPRITE = new ResourceLocation("hud/crosshair_attack_indicator_background");
/*   78 */   private static final ResourceLocation CROSSHAIR_ATTACK_INDICATOR_PROGRESS_SPRITE = new ResourceLocation("hud/crosshair_attack_indicator_progress");
/*   79 */   private static final ResourceLocation EFFECT_BACKGROUND_AMBIENT_SPRITE = new ResourceLocation("hud/effect_background_ambient");
/*   80 */   private static final ResourceLocation EFFECT_BACKGROUND_SPRITE = new ResourceLocation("hud/effect_background");
/*   81 */   private static final ResourceLocation HOTBAR_SPRITE = new ResourceLocation("hud/hotbar");
/*   82 */   private static final ResourceLocation HOTBAR_SELECTION_SPRITE = new ResourceLocation("hud/hotbar_selection");
/*   83 */   private static final ResourceLocation HOTBAR_OFFHAND_LEFT_SPRITE = new ResourceLocation("hud/hotbar_offhand_left");
/*   84 */   private static final ResourceLocation HOTBAR_OFFHAND_RIGHT_SPRITE = new ResourceLocation("hud/hotbar_offhand_right");
/*   85 */   private static final ResourceLocation HOTBAR_ATTACK_INDICATOR_BACKGROUND_SPRITE = new ResourceLocation("hud/hotbar_attack_indicator_background");
/*   86 */   private static final ResourceLocation HOTBAR_ATTACK_INDICATOR_PROGRESS_SPRITE = new ResourceLocation("hud/hotbar_attack_indicator_progress");
/*   87 */   private static final ResourceLocation JUMP_BAR_BACKGROUND_SPRITE = new ResourceLocation("hud/jump_bar_background");
/*   88 */   private static final ResourceLocation JUMP_BAR_COOLDOWN_SPRITE = new ResourceLocation("hud/jump_bar_cooldown");
/*   89 */   private static final ResourceLocation JUMP_BAR_PROGRESS_SPRITE = new ResourceLocation("hud/jump_bar_progress");
/*   90 */   private static final ResourceLocation EXPERIENCE_BAR_BACKGROUND_SPRITE = new ResourceLocation("hud/experience_bar_background");
/*   91 */   private static final ResourceLocation EXPERIENCE_BAR_PROGRESS_SPRITE = new ResourceLocation("hud/experience_bar_progress");
/*   92 */   private static final ResourceLocation ARMOR_EMPTY_SPRITE = new ResourceLocation("hud/armor_empty");
/*   93 */   private static final ResourceLocation ARMOR_HALF_SPRITE = new ResourceLocation("hud/armor_half");
/*   94 */   private static final ResourceLocation ARMOR_FULL_SPRITE = new ResourceLocation("hud/armor_full");
/*   95 */   private static final ResourceLocation FOOD_EMPTY_HUNGER_SPRITE = new ResourceLocation("hud/food_empty_hunger");
/*   96 */   private static final ResourceLocation FOOD_HALF_HUNGER_SPRITE = new ResourceLocation("hud/food_half_hunger");
/*   97 */   private static final ResourceLocation FOOD_FULL_HUNGER_SPRITE = new ResourceLocation("hud/food_full_hunger");
/*   98 */   private static final ResourceLocation FOOD_EMPTY_SPRITE = new ResourceLocation("hud/food_empty");
/*   99 */   private static final ResourceLocation FOOD_HALF_SPRITE = new ResourceLocation("hud/food_half");
/*  100 */   private static final ResourceLocation FOOD_FULL_SPRITE = new ResourceLocation("hud/food_full");
/*  101 */   private static final ResourceLocation AIR_SPRITE = new ResourceLocation("hud/air");
/*  102 */   private static final ResourceLocation AIR_BURSTING_SPRITE = new ResourceLocation("hud/air_bursting");
/*  103 */   private static final ResourceLocation HEART_VEHICLE_CONTAINER_SPRITE = new ResourceLocation("hud/heart/vehicle_container");
/*  104 */   private static final ResourceLocation HEART_VEHICLE_FULL_SPRITE = new ResourceLocation("hud/heart/vehicle_full");
/*  105 */   private static final ResourceLocation HEART_VEHICLE_HALF_SPRITE = new ResourceLocation("hud/heart/vehicle_half");
/*  106 */   private static final ResourceLocation VIGNETTE_LOCATION = new ResourceLocation("textures/misc/vignette.png");
/*  107 */   private static final ResourceLocation PUMPKIN_BLUR_LOCATION = new ResourceLocation("textures/misc/pumpkinblur.png");
/*  108 */   private static final ResourceLocation SPYGLASS_SCOPE_LOCATION = new ResourceLocation("textures/misc/spyglass_scope.png");
/*  109 */   private static final ResourceLocation POWDER_SNOW_OUTLINE_LOCATION = new ResourceLocation("textures/misc/powder_snow_outline.png");
/*      */   
/*  111 */   private static final Comparator<PlayerScoreEntry> SCORE_DISPLAY_ORDER = Comparator.<PlayerScoreEntry, Comparable>comparing(PlayerScoreEntry::value).reversed().thenComparing(PlayerScoreEntry::owner, String.CASE_INSENSITIVE_ORDER);
/*      */   
/*  113 */   private static final Component DEMO_EXPIRED_TEXT = (Component)Component.translatable("demo.demoExpired");
/*  114 */   private static final Component SAVING_TEXT = (Component)Component.translatable("menu.savingLevel");
/*      */   
/*      */   private static final int COLOR_WHITE = 16777215;
/*      */   
/*      */   private static final float MIN_CROSSHAIR_ATTACK_SPEED = 5.0F;
/*      */   
/*      */   private static final int NUM_HEARTS_PER_ROW = 10;
/*      */   
/*      */   private static final int LINE_HEIGHT = 10;
/*      */   private static final String SPACER = ": ";
/*      */   private static final float PORTAL_OVERLAY_ALPHA_MIN = 0.2F;
/*      */   private static final int HEART_SIZE = 9;
/*      */   private static final int HEART_SEPARATION = 8;
/*      */   private static final float AUTOSAVE_FADE_SPEED_FACTOR = 0.2F;
/*  128 */   private final RandomSource random = RandomSource.create();
/*      */   
/*      */   private final Minecraft minecraft;
/*      */   
/*      */   private final ItemRenderer itemRenderer;
/*      */   private final ChatComponent chat;
/*      */   private int tickCount;
/*      */   @Nullable
/*      */   private Component overlayMessageString;
/*      */   private int overlayMessageTime;
/*      */   private boolean animateOverlayMessageColor;
/*      */   private boolean chatDisabledByPlayerShown;
/*  140 */   public float vignetteBrightness = 1.0F;
/*      */   
/*      */   private int toolHighlightTimer;
/*  143 */   private ItemStack lastToolHighlight = ItemStack.EMPTY;
/*      */   
/*      */   private final DebugScreenOverlay debugOverlay;
/*      */   
/*      */   private final SubtitleOverlay subtitleOverlay;
/*      */   private final SpectatorGui spectatorGui;
/*      */   private final PlayerTabOverlay tabList;
/*      */   private final BossHealthOverlay bossOverlay;
/*      */   private int titleTime;
/*      */   @Nullable
/*      */   private Component title;
/*      */   @Nullable
/*      */   private Component subtitle;
/*      */   private int titleFadeInTime;
/*      */   private int titleStayTime;
/*      */   private int titleFadeOutTime;
/*      */   private int lastHealth;
/*      */   private int displayHealth;
/*      */   private long lastHealthTime;
/*      */   private long healthBlinkTime;
/*      */   private int screenWidth;
/*      */   private int screenHeight;
/*      */   private float autosaveIndicatorValue;
/*      */   private float lastAutosaveIndicatorValue;
/*      */   private float scopeScale;
/*      */   
/*      */   public Gui(Minecraft $$0, ItemRenderer $$1) {
/*  170 */     this.minecraft = $$0;
/*  171 */     this.itemRenderer = $$1;
/*  172 */     this.debugOverlay = new DebugScreenOverlay($$0);
/*  173 */     this.spectatorGui = new SpectatorGui($$0);
/*  174 */     this.chat = new ChatComponent($$0);
/*  175 */     this.tabList = new PlayerTabOverlay($$0, this);
/*  176 */     this.bossOverlay = new BossHealthOverlay($$0);
/*  177 */     this.subtitleOverlay = new SubtitleOverlay($$0);
/*      */     
/*  179 */     resetTitleTimes();
/*      */   }
/*      */   
/*      */   public void resetTitleTimes() {
/*  183 */     this.titleFadeInTime = 10;
/*  184 */     this.titleStayTime = 70;
/*  185 */     this.titleFadeOutTime = 20;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void render(GuiGraphics $$0, float $$1) {
/*  191 */     Window $$2 = this.minecraft.getWindow();
/*  192 */     this.screenWidth = $$0.guiWidth();
/*  193 */     this.screenHeight = $$0.guiHeight();
/*  194 */     Font $$3 = getFont();
/*      */     
/*  196 */     RenderSystem.enableBlend();
/*      */     
/*  198 */     if (Minecraft.useFancyGraphics()) {
/*  199 */       renderVignette($$0, this.minecraft.getCameraEntity());
/*      */     } else {
/*  201 */       RenderSystem.enableDepthTest();
/*      */     } 
/*      */     
/*  204 */     float $$4 = this.minecraft.getDeltaFrameTime();
/*  205 */     this.scopeScale = Mth.lerp(0.5F * $$4, this.scopeScale, 1.125F);
/*      */     
/*  207 */     if (this.minecraft.options.getCameraType().isFirstPerson()) {
/*  208 */       if (this.minecraft.player.isScoping()) {
/*  209 */         renderSpyglassOverlay($$0, this.scopeScale);
/*      */       } else {
/*  211 */         this.scopeScale = 0.5F;
/*      */         
/*  213 */         ItemStack $$5 = this.minecraft.player.getInventory().getArmor(3);
/*  214 */         if ($$5.is(Blocks.CARVED_PUMPKIN.asItem())) {
/*  215 */           renderTextureOverlay($$0, PUMPKIN_BLUR_LOCATION, 1.0F);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*  220 */     if (this.minecraft.player.getTicksFrozen() > 0) {
/*  221 */       renderTextureOverlay($$0, POWDER_SNOW_OUTLINE_LOCATION, this.minecraft.player.getPercentFrozen());
/*      */     }
/*      */     
/*  224 */     float $$6 = Mth.lerp($$1, this.minecraft.player.oSpinningEffectIntensity, this.minecraft.player.spinningEffectIntensity);
/*  225 */     if ($$6 > 0.0F && !this.minecraft.player.hasEffect(MobEffects.CONFUSION)) {
/*  226 */       renderPortalOverlay($$0, $$6);
/*      */     }
/*      */     
/*  229 */     if (this.minecraft.gameMode.getPlayerMode() == GameType.SPECTATOR) {
/*  230 */       this.spectatorGui.renderHotbar($$0);
/*  231 */     } else if (!this.minecraft.options.hideGui) {
/*  232 */       renderHotbar($$1, $$0);
/*      */     } 
/*      */     
/*  235 */     if (!this.minecraft.options.hideGui) {
/*  236 */       RenderSystem.enableBlend();
/*      */       
/*  238 */       renderCrosshair($$0);
/*      */       
/*  240 */       this.minecraft.getProfiler().push("bossHealth");
/*  241 */       this.bossOverlay.render($$0);
/*  242 */       this.minecraft.getProfiler().pop();
/*      */       
/*  244 */       if (this.minecraft.gameMode.canHurtPlayer()) {
/*  245 */         renderPlayerHealth($$0);
/*      */       }
/*      */       
/*  248 */       renderVehicleHealth($$0);
/*      */       
/*  250 */       RenderSystem.disableBlend();
/*      */       
/*  252 */       int $$7 = this.screenWidth / 2 - 91;
/*      */       
/*  254 */       PlayerRideableJumping $$8 = this.minecraft.player.jumpableVehicle();
/*  255 */       if ($$8 != null) {
/*  256 */         renderJumpMeter($$8, $$0, $$7);
/*  257 */       } else if (this.minecraft.gameMode.hasExperience()) {
/*      */         
/*  259 */         renderExperienceBar($$0, $$7);
/*      */       } 
/*      */       
/*  262 */       if (this.minecraft.gameMode.getPlayerMode() != GameType.SPECTATOR) {
/*  263 */         renderSelectedItemName($$0);
/*  264 */       } else if (this.minecraft.player.isSpectator()) {
/*  265 */         this.spectatorGui.renderTooltip($$0);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  270 */     if (this.minecraft.player.getSleepTimer() > 0) {
/*  271 */       this.minecraft.getProfiler().push("sleep");
/*      */       
/*  273 */       float $$9 = this.minecraft.player.getSleepTimer();
/*  274 */       float $$10 = $$9 / 100.0F;
/*  275 */       if ($$10 > 1.0F)
/*      */       {
/*  277 */         $$10 = 1.0F - ($$9 - 100.0F) / 10.0F;
/*      */       }
/*      */       
/*  280 */       int $$11 = (int)(220.0F * $$10) << 24 | 0x101020;
/*  281 */       $$0.fill(RenderType.guiOverlay(), 0, 0, this.screenWidth, this.screenHeight, $$11);
/*  282 */       this.minecraft.getProfiler().pop();
/*      */     } 
/*      */     
/*  285 */     if (this.minecraft.isDemo()) {
/*  286 */       renderDemoOverlay($$0);
/*      */     }
/*      */     
/*  289 */     renderEffects($$0);
/*      */     
/*  291 */     if (this.debugOverlay.showDebugScreen()) {
/*  292 */       this.debugOverlay.render($$0);
/*      */     }
/*      */     
/*  295 */     if (!this.minecraft.options.hideGui) {
/*  296 */       if (this.overlayMessageString != null && this.overlayMessageTime > 0) {
/*  297 */         this.minecraft.getProfiler().push("overlayMessage");
/*  298 */         float $$12 = this.overlayMessageTime - $$1;
/*  299 */         int $$13 = (int)($$12 * 255.0F / 20.0F);
/*  300 */         if ($$13 > 255) {
/*  301 */           $$13 = 255;
/*      */         }
/*  303 */         if ($$13 > 8) {
/*  304 */           $$0.pose().pushPose();
/*  305 */           $$0.pose().translate((this.screenWidth / 2), (this.screenHeight - 68), 0.0F);
/*      */           
/*  307 */           int $$14 = 16777215;
/*  308 */           if (this.animateOverlayMessageColor) {
/*  309 */             $$14 = Mth.hsvToRgb($$12 / 50.0F, 0.7F, 0.6F) & 0xFFFFFF;
/*      */           }
/*  311 */           int $$15 = $$13 << 24 & 0xFF000000;
/*  312 */           int $$16 = $$3.width((FormattedText)this.overlayMessageString);
/*  313 */           drawBackdrop($$0, $$3, -4, $$16, 0xFFFFFF | $$15);
/*  314 */           $$0.drawString($$3, this.overlayMessageString, -$$16 / 2, -4, $$14 | $$15);
/*      */           
/*  316 */           $$0.pose().popPose();
/*      */         } 
/*  318 */         this.minecraft.getProfiler().pop();
/*      */       } 
/*      */       
/*  321 */       if (this.title != null && this.titleTime > 0) {
/*  322 */         this.minecraft.getProfiler().push("titleAndSubtitle");
/*  323 */         float $$17 = this.titleTime - $$1;
/*  324 */         int $$18 = 255;
/*  325 */         if (this.titleTime > this.titleFadeOutTime + this.titleStayTime) {
/*  326 */           float $$19 = (this.titleFadeInTime + this.titleStayTime + this.titleFadeOutTime) - $$17;
/*  327 */           $$18 = (int)($$19 * 255.0F / this.titleFadeInTime);
/*      */         } 
/*  329 */         if (this.titleTime <= this.titleFadeOutTime) {
/*  330 */           $$18 = (int)($$17 * 255.0F / this.titleFadeOutTime);
/*      */         }
/*  332 */         $$18 = Mth.clamp($$18, 0, 255);
/*  333 */         if ($$18 > 8) {
/*  334 */           $$0.pose().pushPose();
/*  335 */           $$0.pose().translate((this.screenWidth / 2), (this.screenHeight / 2), 0.0F);
/*      */           
/*  337 */           RenderSystem.enableBlend();
/*      */           
/*  339 */           $$0.pose().pushPose();
/*  340 */           $$0.pose().scale(4.0F, 4.0F, 4.0F);
/*      */           
/*  342 */           int $$20 = $$18 << 24 & 0xFF000000;
/*  343 */           int $$21 = $$3.width((FormattedText)this.title);
/*  344 */           drawBackdrop($$0, $$3, -10, $$21, 0xFFFFFF | $$20);
/*  345 */           $$0.drawString($$3, this.title, -$$21 / 2, -10, 0xFFFFFF | $$20);
/*      */           
/*  347 */           $$0.pose().popPose();
/*      */           
/*  349 */           if (this.subtitle != null) {
/*  350 */             $$0.pose().pushPose();
/*  351 */             $$0.pose().scale(2.0F, 2.0F, 2.0F);
/*      */             
/*  353 */             int $$22 = $$3.width((FormattedText)this.subtitle);
/*  354 */             drawBackdrop($$0, $$3, 5, $$22, 0xFFFFFF | $$20);
/*  355 */             $$0.drawString($$3, this.subtitle, -$$22 / 2, 5, 0xFFFFFF | $$20);
/*      */             
/*  357 */             $$0.pose().popPose();
/*      */           } 
/*  359 */           RenderSystem.disableBlend();
/*      */           
/*  361 */           $$0.pose().popPose();
/*      */         } 
/*  363 */         this.minecraft.getProfiler().pop();
/*      */       } 
/*      */       
/*  366 */       this.subtitleOverlay.render($$0);
/*      */       
/*  368 */       Scoreboard $$23 = this.minecraft.level.getScoreboard();
/*  369 */       Objective $$24 = null;
/*  370 */       PlayerTeam $$25 = $$23.getPlayersTeam(this.minecraft.player.getScoreboardName());
/*  371 */       if ($$25 != null) {
/*  372 */         DisplaySlot $$26 = DisplaySlot.teamColorToSlot($$25.getColor());
/*  373 */         if ($$26 != null) {
/*  374 */           $$24 = $$23.getDisplayObjective($$26);
/*      */         }
/*      */       } 
/*  377 */       Objective $$27 = ($$24 != null) ? $$24 : $$23.getDisplayObjective(DisplaySlot.SIDEBAR);
/*  378 */       if ($$27 != null) {
/*  379 */         displayScoreboardSidebar($$0, $$27);
/*      */       }
/*      */       
/*  382 */       RenderSystem.enableBlend();
/*      */       
/*  384 */       int $$28 = Mth.floor(this.minecraft.mouseHandler.xpos() * $$2.getGuiScaledWidth() / $$2.getScreenWidth());
/*  385 */       int $$29 = Mth.floor(this.minecraft.mouseHandler.ypos() * $$2.getGuiScaledHeight() / $$2.getScreenHeight());
/*      */       
/*  387 */       this.minecraft.getProfiler().push("chat");
/*  388 */       this.chat.render($$0, this.tickCount, $$28, $$29);
/*  389 */       this.minecraft.getProfiler().pop();
/*      */       
/*  391 */       $$27 = $$23.getDisplayObjective(DisplaySlot.LIST);
/*  392 */       if (this.minecraft.options.keyPlayerList.isDown() && (!this.minecraft.isLocalServer() || this.minecraft.player.connection.getListedOnlinePlayers().size() > 1 || $$27 != null)) {
/*  393 */         this.tabList.setVisible(true);
/*  394 */         this.tabList.render($$0, this.screenWidth, $$23, $$27);
/*      */       } else {
/*  396 */         this.tabList.setVisible(false);
/*      */       } 
/*      */       
/*  399 */       renderSavingIndicator($$0);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void drawBackdrop(GuiGraphics $$0, Font $$1, int $$2, int $$3, int $$4) {
/*  404 */     int $$5 = this.minecraft.options.getBackgroundColor(0.0F);
/*  405 */     if ($$5 != 0) {
/*  406 */       int $$6 = -$$3 / 2;
/*  407 */       Objects.requireNonNull($$1); $$0.fill($$6 - 2, $$2 - 2, $$6 + $$3 + 2, $$2 + 9 + 2, FastColor.ARGB32.multiply($$5, $$4));
/*      */     } 
/*      */   }
/*      */   
/*      */   private void renderCrosshair(GuiGraphics $$0) {
/*  412 */     Options $$1 = this.minecraft.options;
/*      */     
/*  414 */     if (!$$1.getCameraType().isFirstPerson()) {
/*      */       return;
/*      */     }
/*      */     
/*  418 */     if (this.minecraft.gameMode.getPlayerMode() == GameType.SPECTATOR && 
/*  419 */       !canRenderCrosshairForSpectator(this.minecraft.hitResult)) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  424 */     if (this.debugOverlay.showDebugScreen() && !this.minecraft.player.isReducedDebugInfo() && !((Boolean)$$1.reducedDebugInfo().get()).booleanValue()) {
/*  425 */       Camera $$2 = this.minecraft.gameRenderer.getMainCamera();
/*      */       
/*  427 */       PoseStack $$3 = RenderSystem.getModelViewStack();
/*  428 */       $$3.pushPose();
/*  429 */       $$3.mulPoseMatrix($$0.pose().last().pose());
/*  430 */       $$3.translate((this.screenWidth / 2), (this.screenHeight / 2), 0.0F);
/*  431 */       $$3.mulPose(Axis.XN.rotationDegrees($$2.getXRot()));
/*  432 */       $$3.mulPose(Axis.YP.rotationDegrees($$2.getYRot()));
/*  433 */       $$3.scale(-1.0F, -1.0F, -1.0F);
/*  434 */       RenderSystem.applyModelViewMatrix();
/*      */       
/*  436 */       RenderSystem.renderCrosshair(10);
/*      */       
/*  438 */       $$3.popPose();
/*  439 */       RenderSystem.applyModelViewMatrix();
/*      */     } else {
/*  441 */       RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/*  442 */       int $$4 = 15;
/*  443 */       $$0.blitSprite(CROSSHAIR_SPRITE, (this.screenWidth - 15) / 2, (this.screenHeight - 15) / 2, 15, 15);
/*      */       
/*  445 */       if (this.minecraft.options.attackIndicator().get() == AttackIndicatorStatus.CROSSHAIR) {
/*  446 */         float $$5 = this.minecraft.player.getAttackStrengthScale(0.0F);
/*  447 */         boolean $$6 = false;
/*  448 */         if (this.minecraft.crosshairPickEntity != null && this.minecraft.crosshairPickEntity instanceof LivingEntity && $$5 >= 1.0F) {
/*  449 */           $$6 = (this.minecraft.player.getCurrentItemAttackStrengthDelay() > 5.0F);
/*  450 */           $$6 &= this.minecraft.crosshairPickEntity.isAlive();
/*      */         } 
/*      */         
/*  453 */         int $$7 = this.screenHeight / 2 - 7 + 16;
/*  454 */         int $$8 = this.screenWidth / 2 - 8;
/*      */         
/*  456 */         if ($$6) {
/*  457 */           $$0.blitSprite(CROSSHAIR_ATTACK_INDICATOR_FULL_SPRITE, $$8, $$7, 16, 16);
/*  458 */         } else if ($$5 < 1.0F) {
/*  459 */           int $$9 = (int)($$5 * 17.0F);
/*      */           
/*  461 */           $$0.blitSprite(CROSSHAIR_ATTACK_INDICATOR_BACKGROUND_SPRITE, $$8, $$7, 16, 4);
/*  462 */           $$0.blitSprite(CROSSHAIR_ATTACK_INDICATOR_PROGRESS_SPRITE, 16, 4, 0, 0, $$8, $$7, $$9, 4);
/*      */         } 
/*      */       } 
/*      */       
/*  466 */       RenderSystem.defaultBlendFunc();
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean canRenderCrosshairForSpectator(@Nullable HitResult $$0) {
/*  471 */     if ($$0 == null) {
/*  472 */       return false;
/*      */     }
/*      */     
/*  475 */     if ($$0.getType() == HitResult.Type.ENTITY)
/*  476 */       return ((EntityHitResult)$$0).getEntity() instanceof net.minecraft.world.MenuProvider; 
/*  477 */     if ($$0.getType() == HitResult.Type.BLOCK) {
/*  478 */       BlockPos $$1 = ((BlockHitResult)$$0).getBlockPos();
/*  479 */       ClientLevel clientLevel = this.minecraft.level;
/*  480 */       return (clientLevel.getBlockState($$1).getMenuProvider((Level)clientLevel, $$1) != null);
/*      */     } 
/*      */     
/*  483 */     return false;
/*      */   }
/*      */   
/*      */   protected void renderEffects(GuiGraphics $$0) {
/*  487 */     Collection<MobEffectInstance> $$1 = this.minecraft.player.getActiveEffects();
/*      */     
/*  489 */     if (!$$1.isEmpty()) { Screen screen = this.minecraft.screen; if (screen instanceof EffectRenderingInventoryScreen) { EffectRenderingInventoryScreen $$2 = (EffectRenderingInventoryScreen)screen; if ($$2.canSeeEffects())
/*      */           return;  }
/*      */        }
/*      */     else { return; }
/*  493 */      RenderSystem.enableBlend();
/*  494 */     int $$3 = 0;
/*  495 */     int $$4 = 0;
/*  496 */     MobEffectTextureManager $$5 = this.minecraft.getMobEffectTextures();
/*      */ 
/*      */     
/*  499 */     List<Runnable> $$6 = Lists.newArrayListWithExpectedSize($$1.size());
/*      */     
/*  501 */     for (MobEffectInstance $$7 : Ordering.natural().reverse().sortedCopy($$1)) {
/*  502 */       MobEffect $$8 = $$7.getEffect();
/*      */       
/*  504 */       if ($$7.showIcon()) {
/*  505 */         int $$9 = this.screenWidth;
/*  506 */         int $$10 = 1;
/*  507 */         if (this.minecraft.isDemo()) {
/*  508 */           $$10 += 15;
/*      */         }
/*      */         
/*  511 */         if ($$8.isBeneficial()) {
/*  512 */           $$3++;
/*  513 */           $$9 -= 25 * $$3;
/*      */         } else {
/*  515 */           $$4++;
/*  516 */           $$9 -= 25 * $$4;
/*  517 */           $$10 += 26;
/*      */         } 
/*      */         
/*  520 */         float $$11 = 1.0F;
/*  521 */         if ($$7.isAmbient()) {
/*  522 */           $$0.blitSprite(EFFECT_BACKGROUND_AMBIENT_SPRITE, $$9, $$10, 24, 24);
/*      */         } else {
/*  524 */           $$0.blitSprite(EFFECT_BACKGROUND_SPRITE, $$9, $$10, 24, 24);
/*      */           
/*  526 */           if ($$7.endsWithin(200)) {
/*  527 */             int $$12 = $$7.getDuration();
/*  528 */             int $$13 = 10 - $$12 / 20;
/*  529 */             $$11 = Mth.clamp($$12 / 10.0F / 5.0F * 0.5F, 0.0F, 0.5F) + Mth.cos($$12 * 3.1415927F / 5.0F) * Mth.clamp($$13 / 10.0F * 0.25F, 0.0F, 0.25F);
/*      */           } 
/*      */         } 
/*      */         
/*  533 */         TextureAtlasSprite $$14 = $$5.get($$8);
/*  534 */         int $$15 = $$9;
/*  535 */         int $$16 = $$10;
/*  536 */         float $$17 = $$11;
/*  537 */         $$6.add(() -> {
/*      */               $$0.setColor(1.0F, 1.0F, 1.0F, $$1);
/*      */               
/*      */               $$0.blit($$2 + 3, $$3 + 3, 0, 18, 18, $$4);
/*      */               $$0.setColor(1.0F, 1.0F, 1.0F, 1.0F);
/*      */             });
/*      */       } 
/*      */     } 
/*  545 */     $$6.forEach(Runnable::run);
/*      */   }
/*      */   
/*      */   private void renderHotbar(float $$0, GuiGraphics $$1) {
/*  549 */     Player $$2 = getCameraPlayer();
/*  550 */     if ($$2 == null) {
/*      */       return;
/*      */     }
/*      */     
/*  554 */     ItemStack $$3 = $$2.getOffhandItem();
/*  555 */     HumanoidArm $$4 = $$2.getMainArm().getOpposite();
/*  556 */     int $$5 = this.screenWidth / 2;
/*  557 */     int $$6 = 182;
/*  558 */     int $$7 = 91;
/*      */     
/*  560 */     $$1.pose().pushPose();
/*  561 */     $$1.pose().translate(0.0F, 0.0F, -90.0F);
/*      */     
/*  563 */     $$1.blitSprite(HOTBAR_SPRITE, $$5 - 91, this.screenHeight - 22, 182, 22);
/*  564 */     $$1.blitSprite(HOTBAR_SELECTION_SPRITE, $$5 - 91 - 1 + ($$2.getInventory()).selected * 20, this.screenHeight - 22 - 1, 24, 23);
/*      */     
/*  566 */     if (!$$3.isEmpty()) {
/*  567 */       if ($$4 == HumanoidArm.LEFT) {
/*  568 */         $$1.blitSprite(HOTBAR_OFFHAND_LEFT_SPRITE, $$5 - 91 - 29, this.screenHeight - 23, 29, 24);
/*      */       } else {
/*  570 */         $$1.blitSprite(HOTBAR_OFFHAND_RIGHT_SPRITE, $$5 + 91, this.screenHeight - 23, 29, 24);
/*      */       } 
/*      */     }
/*      */     
/*  574 */     $$1.pose().popPose();
/*      */     
/*  576 */     int $$8 = 1;
/*  577 */     for (int $$9 = 0; $$9 < 9; $$9++) {
/*  578 */       int $$10 = $$5 - 90 + $$9 * 20 + 2;
/*  579 */       int $$11 = this.screenHeight - 16 - 3;
/*  580 */       renderSlot($$1, $$10, $$11, $$0, $$2, (ItemStack)($$2.getInventory()).items.get($$9), $$8++);
/*      */     } 
/*      */     
/*  583 */     if (!$$3.isEmpty()) {
/*  584 */       int $$12 = this.screenHeight - 16 - 3;
/*  585 */       if ($$4 == HumanoidArm.LEFT) {
/*  586 */         renderSlot($$1, $$5 - 91 - 26, $$12, $$0, $$2, $$3, $$8++);
/*      */       } else {
/*  588 */         renderSlot($$1, $$5 + 91 + 10, $$12, $$0, $$2, $$3, $$8++);
/*      */       } 
/*      */     } 
/*      */     
/*  592 */     RenderSystem.enableBlend();
/*      */     
/*  594 */     if (this.minecraft.options.attackIndicator().get() == AttackIndicatorStatus.HOTBAR) {
/*  595 */       float $$13 = this.minecraft.player.getAttackStrengthScale(0.0F);
/*  596 */       if ($$13 < 1.0F) {
/*  597 */         int $$14 = this.screenHeight - 20;
/*  598 */         int $$15 = $$5 + 91 + 6;
/*  599 */         if ($$4 == HumanoidArm.RIGHT) {
/*  600 */           $$15 = $$5 - 91 - 22;
/*      */         }
/*      */         
/*  603 */         int $$16 = (int)($$13 * 19.0F);
/*      */         
/*  605 */         $$1.blitSprite(HOTBAR_ATTACK_INDICATOR_BACKGROUND_SPRITE, $$15, $$14, 18, 18);
/*  606 */         $$1.blitSprite(HOTBAR_ATTACK_INDICATOR_PROGRESS_SPRITE, 18, 18, 0, 18 - $$16, $$15, $$14 + 18 - $$16, 18, $$16);
/*      */       } 
/*      */     } 
/*      */     
/*  610 */     RenderSystem.disableBlend();
/*      */   }
/*      */ 
/*      */   
/*      */   public void renderJumpMeter(PlayerRideableJumping $$0, GuiGraphics $$1, int $$2) {
/*  615 */     this.minecraft.getProfiler().push("jumpBar");
/*  616 */     float $$3 = this.minecraft.player.getJumpRidingScale();
/*  617 */     int $$4 = 182;
/*      */     
/*  619 */     int $$5 = (int)($$3 * 183.0F);
/*      */     
/*  621 */     int $$6 = this.screenHeight - 32 + 3;
/*  622 */     $$1.blitSprite(JUMP_BAR_BACKGROUND_SPRITE, $$2, $$6, 182, 5);
/*  623 */     if ($$0.getJumpCooldown() > 0) {
/*  624 */       $$1.blitSprite(JUMP_BAR_COOLDOWN_SPRITE, $$2, $$6, 182, 5);
/*  625 */     } else if ($$5 > 0) {
/*  626 */       $$1.blitSprite(JUMP_BAR_PROGRESS_SPRITE, 182, 5, 0, 0, $$2, $$6, $$5, 5);
/*      */     } 
/*  628 */     this.minecraft.getProfiler().pop();
/*      */   }
/*      */   
/*      */   public void renderExperienceBar(GuiGraphics $$0, int $$1) {
/*  632 */     this.minecraft.getProfiler().push("expBar");
/*  633 */     int $$2 = this.minecraft.player.getXpNeededForNextLevel();
/*  634 */     if ($$2 > 0) {
/*  635 */       int $$3 = 182;
/*      */       
/*  637 */       int $$4 = (int)(this.minecraft.player.experienceProgress * 183.0F);
/*      */       
/*  639 */       int $$5 = this.screenHeight - 32 + 3;
/*  640 */       $$0.blitSprite(EXPERIENCE_BAR_BACKGROUND_SPRITE, $$1, $$5, 182, 5);
/*  641 */       if ($$4 > 0) {
/*  642 */         $$0.blitSprite(EXPERIENCE_BAR_PROGRESS_SPRITE, 182, 5, 0, 0, $$1, $$5, $$4, 5);
/*      */       }
/*      */     } 
/*  645 */     this.minecraft.getProfiler().pop();
/*      */     
/*  647 */     if (this.minecraft.player.experienceLevel > 0) {
/*  648 */       this.minecraft.getProfiler().push("expLevel");
/*      */       
/*  650 */       String $$6 = "" + this.minecraft.player.experienceLevel;
/*  651 */       int $$7 = (this.screenWidth - getFont().width($$6)) / 2;
/*  652 */       int $$8 = this.screenHeight - 31 - 4;
/*      */       
/*  654 */       $$0.drawString(getFont(), $$6, $$7 + 1, $$8, 0, false);
/*  655 */       $$0.drawString(getFont(), $$6, $$7 - 1, $$8, 0, false);
/*  656 */       $$0.drawString(getFont(), $$6, $$7, $$8 + 1, 0, false);
/*  657 */       $$0.drawString(getFont(), $$6, $$7, $$8 - 1, 0, false);
/*  658 */       $$0.drawString(getFont(), $$6, $$7, $$8, 8453920, false);
/*  659 */       this.minecraft.getProfiler().pop();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void renderSelectedItemName(GuiGraphics $$0) {
/*  664 */     this.minecraft.getProfiler().push("selectedItemName");
/*      */     
/*  666 */     if (this.toolHighlightTimer > 0 && !this.lastToolHighlight.isEmpty()) {
/*  667 */       MutableComponent $$1 = Component.empty().append(this.lastToolHighlight.getHoverName()).withStyle((this.lastToolHighlight.getRarity()).color);
/*  668 */       if (this.lastToolHighlight.hasCustomHoverName()) {
/*  669 */         $$1.withStyle(ChatFormatting.ITALIC);
/*      */       }
/*      */       
/*  672 */       int $$2 = getFont().width((FormattedText)$$1);
/*  673 */       int $$3 = (this.screenWidth - $$2) / 2;
/*  674 */       int $$4 = this.screenHeight - 59;
/*  675 */       if (!this.minecraft.gameMode.canHurtPlayer())
/*      */       {
/*  677 */         $$4 += 14;
/*      */       }
/*      */       
/*  680 */       int $$5 = (int)(this.toolHighlightTimer * 256.0F / 10.0F);
/*  681 */       if ($$5 > 255) {
/*  682 */         $$5 = 255;
/*      */       }
/*  684 */       if ($$5 > 0) {
/*  685 */         Objects.requireNonNull(getFont()); $$0.fill($$3 - 2, $$4 - 2, $$3 + $$2 + 2, $$4 + 9 + 2, this.minecraft.options.getBackgroundColor(0));
/*  686 */         $$0.drawString(getFont(), (Component)$$1, $$3, $$4, 16777215 + ($$5 << 24));
/*      */       } 
/*      */     } 
/*      */     
/*  690 */     this.minecraft.getProfiler().pop();
/*      */   }
/*      */   public void renderDemoOverlay(GuiGraphics $$0) {
/*      */     MutableComponent mutableComponent;
/*  694 */     this.minecraft.getProfiler().push("demo");
/*      */ 
/*      */     
/*  697 */     if (this.minecraft.level.getGameTime() >= 120500L) {
/*  698 */       Component $$1 = DEMO_EXPIRED_TEXT;
/*      */     } else {
/*  700 */       mutableComponent = Component.translatable("demo.remainingTime", new Object[] { StringUtil.formatTickDuration((int)(120500L - this.minecraft.level.getGameTime()), this.minecraft.level.tickRateManager().tickrate()) });
/*      */     } 
/*      */     
/*  703 */     int $$3 = getFont().width((FormattedText)mutableComponent);
/*  704 */     $$0.drawString(getFont(), (Component)mutableComponent, this.screenWidth - $$3 - 10, 5, 16777215);
/*  705 */     this.minecraft.getProfiler().pop();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void displayScoreboardSidebar(GuiGraphics $$0, Objective $$1) {
/*  711 */     Scoreboard $$2 = $$1.getScoreboard();
/*  712 */     NumberFormat $$3 = $$1.numberFormatOrDefault((NumberFormat)StyledFormat.SIDEBAR_DEFAULT);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  725 */     DisplayEntry[] $$4 = (DisplayEntry[])$$2.listPlayerScores($$1).stream().filter($$0 -> !$$0.isHidden()).sorted(SCORE_DISPLAY_ORDER).limit(15L).map($$2 -> { PlayerTeam $$3 = $$0.getPlayersTeam($$2.owner()); Component $$4 = $$2.ownerName(); MutableComponent mutableComponent1 = PlayerTeam.formatNameForTeam((Team)$$3, $$4); MutableComponent mutableComponent2 = $$2.formatValue($$1); int $$7 = getFont().width((FormattedText)mutableComponent2); static final class DisplayEntry extends Record { final Component name; final Component score; final int scoreWidth; DisplayEntry(Component $$0, Component $$1, int $$2) { this.name = $$0; this.score = $$1; this.scoreWidth = $$2; } public final String toString() { // Byte code:
/*      */               //   0: aload_0
/*      */               //   1: <illegal opcode> toString : (Lnet/minecraft/client/gui/Gui$1DisplayEntry;)Ljava/lang/String;
/*      */               //   6: areturn
/*      */               // Line number table:
/*      */               //   Java source line number -> byte code offset
/*      */               //   #709	-> 0
/*      */               // Local variable table:
/*      */               //   start	length	slot	name	descriptor
/*      */               //   0	7	0	this	Lnet/minecraft/client/gui/Gui$1DisplayEntry; } public final int hashCode() { // Byte code:
/*      */               //   0: aload_0
/*      */               //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/gui/Gui$1DisplayEntry;)I
/*      */               //   6: ireturn
/*      */               // Line number table:
/*      */               //   Java source line number -> byte code offset
/*      */               //   #709	-> 0
/*      */               // Local variable table:
/*      */               //   start	length	slot	name	descriptor
/*      */               //   0	7	0	this	Lnet/minecraft/client/gui/Gui$1DisplayEntry; } public final boolean equals(Object $$0) { // Byte code:
/*      */               //   0: aload_0
/*      */               //   1: aload_1
/*      */               //   2: <illegal opcode> equals : (Lnet/minecraft/client/gui/Gui$1DisplayEntry;Ljava/lang/Object;)Z
/*      */               //   7: ireturn
/*      */               // Line number table:
/*      */               //   Java source line number -> byte code offset
/*      */               //   #709	-> 0
/*      */               // Local variable table:
/*      */               //   start	length	slot	name	descriptor
/*      */               //   0	8	0	this	Lnet/minecraft/client/gui/Gui$1DisplayEntry;
/*  725 */               //   0	8	1	$$0	Ljava/lang/Object; } public Component name() { return this.name; } public Component score() { return this.score; } public int scoreWidth() { return this.scoreWidth; } }; return new DisplayEntry((Component)mutableComponent1, (Component)mutableComponent2, $$7); }).toArray($$0 -> new DisplayEntry[$$0]);
/*      */     
/*  727 */     Component $$5 = $$1.getDisplayName();
/*  728 */     int $$6 = getFont().width((FormattedText)$$5);
/*  729 */     int $$7 = $$6;
/*      */     
/*  731 */     int $$8 = getFont().width(": ");
/*  732 */     for (DisplayEntry $$9 : $$4) {
/*  733 */       $$7 = Math.max($$7, getFont().width((FormattedText)$$9.name) + (($$9.scoreWidth > 0) ? ($$8 + $$9.scoreWidth) : 0));
/*      */     }
/*      */     
/*  736 */     int $$10 = $$7;
/*  737 */     $$0.drawManaged(() -> {
/*      */           int $$5 = $$0.length;
/*      */           Objects.requireNonNull(getFont());
/*      */           int $$6 = $$5 * 9;
/*      */           int $$7 = this.screenHeight / 2 + $$6 / 3;
/*      */           int $$8 = 3;
/*      */           int $$9 = this.screenWidth - $$1 - 3;
/*      */           int $$10 = this.screenWidth - 3 + 2;
/*      */           int $$11 = this.minecraft.options.getBackgroundColor(0.3F);
/*      */           int $$12 = this.minecraft.options.getBackgroundColor(0.4F);
/*      */           Objects.requireNonNull(getFont());
/*      */           int $$13 = $$7 - $$5 * 9;
/*      */           Objects.requireNonNull(getFont());
/*      */           $$2.fill($$9 - 2, $$13 - 9 - 1, $$10, $$13 - 1, $$12);
/*      */           $$2.fill($$9 - 2, $$13 - 1, $$10, $$7, $$11);
/*      */           Objects.requireNonNull(getFont());
/*      */           $$2.drawString(getFont(), $$3, $$9 + $$1 / 2 - $$4 / 2, $$13 - 9, -1, false);
/*      */           for (int $$14 = 0; $$14 < $$5; $$14++) {
/*      */             DisplayEntry $$15 = $$0[$$14];
/*      */             Objects.requireNonNull(getFont());
/*      */             int $$16 = $$7 - ($$5 - $$14) * 9;
/*      */             $$2.drawString(getFont(), $$15.name, $$9, $$16, -1, false);
/*      */             $$2.drawString(getFont(), $$15.score, $$10 - $$15.scoreWidth, $$16, -1, false);
/*      */           } 
/*      */         });
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   private Player getCameraPlayer() {
/*  766 */     Entity entity = this.minecraft.getCameraEntity(); Player $$0 = (Player)entity; return (entity instanceof Player) ? $$0 : null;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   private LivingEntity getPlayerVehicleWithHealth() {
/*  771 */     Player $$0 = getCameraPlayer();
/*  772 */     if ($$0 != null) {
/*  773 */       Entity $$1 = $$0.getVehicle();
/*  774 */       if ($$1 == null) {
/*  775 */         return null;
/*      */       }
/*  777 */       if ($$1 instanceof LivingEntity) {
/*  778 */         return (LivingEntity)$$1;
/*      */       }
/*      */     } 
/*  781 */     return null;
/*      */   }
/*      */   
/*      */   private int getVehicleMaxHearts(@Nullable LivingEntity $$0) {
/*  785 */     if ($$0 == null || !$$0.showVehicleHealth()) {
/*  786 */       return 0;
/*      */     }
/*      */     
/*  789 */     float $$1 = $$0.getMaxHealth();
/*  790 */     int $$2 = (int)($$1 + 0.5F) / 2;
/*  791 */     if ($$2 > 30) {
/*  792 */       $$2 = 30;
/*      */     }
/*  794 */     return $$2;
/*      */   }
/*      */   
/*      */   private int getVisibleVehicleHeartRows(int $$0) {
/*  798 */     return (int)Math.ceil($$0 / 10.0D);
/*      */   }
/*      */   
/*      */   private void renderPlayerHealth(GuiGraphics $$0) {
/*  802 */     Player $$1 = getCameraPlayer();
/*  803 */     if ($$1 == null) {
/*      */       return;
/*      */     }
/*  806 */     int $$2 = Mth.ceil($$1.getHealth());
/*      */     
/*  808 */     boolean $$3 = (this.healthBlinkTime > this.tickCount && (this.healthBlinkTime - this.tickCount) / 3L % 2L == 1L);
/*  809 */     long $$4 = Util.getMillis();
/*  810 */     if ($$2 < this.lastHealth && $$1.invulnerableTime > 0) {
/*  811 */       this.lastHealthTime = $$4;
/*  812 */       this.healthBlinkTime = (this.tickCount + 20);
/*  813 */     } else if ($$2 > this.lastHealth && $$1.invulnerableTime > 0) {
/*  814 */       this.lastHealthTime = $$4;
/*  815 */       this.healthBlinkTime = (this.tickCount + 10);
/*      */     } 
/*  817 */     if ($$4 - this.lastHealthTime > 1000L) {
/*  818 */       this.lastHealth = $$2;
/*  819 */       this.displayHealth = $$2;
/*  820 */       this.lastHealthTime = $$4;
/*      */     } 
/*  822 */     this.lastHealth = $$2;
/*  823 */     int $$5 = this.displayHealth;
/*  824 */     this.random.setSeed((this.tickCount * 312871));
/*      */     
/*  826 */     FoodData $$6 = $$1.getFoodData();
/*  827 */     int $$7 = $$6.getFoodLevel();
/*      */     
/*  829 */     int $$8 = this.screenWidth / 2 - 91;
/*  830 */     int $$9 = this.screenWidth / 2 + 91;
/*      */     
/*  832 */     int $$10 = this.screenHeight - 39;
/*  833 */     float $$11 = Math.max((float)$$1.getAttributeValue(Attributes.MAX_HEALTH), Math.max($$5, $$2));
/*  834 */     int $$12 = Mth.ceil($$1.getAbsorptionAmount());
/*  835 */     int $$13 = Mth.ceil(($$11 + $$12) / 2.0F / 10.0F);
/*  836 */     int $$14 = Math.max(10 - $$13 - 2, 3);
/*  837 */     int $$15 = $$10 - ($$13 - 1) * $$14 - 10;
/*  838 */     int $$16 = $$10 - 10;
/*      */     
/*  840 */     int $$17 = $$1.getArmorValue();
/*  841 */     int $$18 = -1;
/*  842 */     if ($$1.hasEffect(MobEffects.REGENERATION)) {
/*  843 */       $$18 = this.tickCount % Mth.ceil($$11 + 5.0F);
/*      */     }
/*      */ 
/*      */     
/*  847 */     this.minecraft.getProfiler().push("armor");
/*  848 */     for (int $$19 = 0; $$19 < 10; $$19++) {
/*  849 */       if ($$17 > 0) {
/*  850 */         int $$20 = $$8 + $$19 * 8;
/*  851 */         if ($$19 * 2 + 1 < $$17) {
/*  852 */           $$0.blitSprite(ARMOR_FULL_SPRITE, $$20, $$15, 9, 9);
/*      */         }
/*  854 */         if ($$19 * 2 + 1 == $$17) {
/*  855 */           $$0.blitSprite(ARMOR_HALF_SPRITE, $$20, $$15, 9, 9);
/*      */         }
/*  857 */         if ($$19 * 2 + 1 > $$17) {
/*  858 */           $$0.blitSprite(ARMOR_EMPTY_SPRITE, $$20, $$15, 9, 9);
/*      */         }
/*      */       } 
/*      */     } 
/*  862 */     this.minecraft.getProfiler().popPush("health");
/*  863 */     renderHearts($$0, $$1, $$8, $$10, $$14, $$18, $$11, $$2, $$5, $$12, $$3);
/*      */     
/*  865 */     LivingEntity $$21 = getPlayerVehicleWithHealth();
/*  866 */     int $$22 = getVehicleMaxHearts($$21);
/*  867 */     if ($$22 == 0) {
/*      */       
/*  869 */       this.minecraft.getProfiler().popPush("food");
/*  870 */       for (int $$23 = 0; $$23 < 10; $$23++) {
/*  871 */         ResourceLocation $$28, $$29, $$30; int $$24 = $$10;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  876 */         if ($$1.hasEffect(MobEffects.HUNGER)) {
/*  877 */           ResourceLocation $$25 = FOOD_EMPTY_HUNGER_SPRITE;
/*  878 */           ResourceLocation $$26 = FOOD_HALF_HUNGER_SPRITE;
/*  879 */           ResourceLocation $$27 = FOOD_FULL_HUNGER_SPRITE;
/*      */         } else {
/*  881 */           $$28 = FOOD_EMPTY_SPRITE;
/*  882 */           $$29 = FOOD_HALF_SPRITE;
/*  883 */           $$30 = FOOD_FULL_SPRITE;
/*      */         } 
/*      */         
/*  886 */         if ($$1.getFoodData().getSaturationLevel() <= 0.0F && 
/*  887 */           this.tickCount % ($$7 * 3 + 1) == 0) {
/*  888 */           $$24 += this.random.nextInt(3) - 1;
/*      */         }
/*      */ 
/*      */         
/*  892 */         int $$31 = $$9 - $$23 * 8 - 9;
/*  893 */         $$0.blitSprite($$28, $$31, $$24, 9, 9);
/*  894 */         if ($$23 * 2 + 1 < $$7) {
/*  895 */           $$0.blitSprite($$30, $$31, $$24, 9, 9);
/*      */         }
/*  897 */         if ($$23 * 2 + 1 == $$7) {
/*  898 */           $$0.blitSprite($$29, $$31, $$24, 9, 9);
/*      */         }
/*      */       } 
/*  901 */       $$16 -= 10;
/*      */     } 
/*      */ 
/*      */     
/*  905 */     this.minecraft.getProfiler().popPush("air");
/*      */     
/*  907 */     int $$32 = $$1.getMaxAirSupply();
/*  908 */     int $$33 = Math.min($$1.getAirSupply(), $$32);
/*  909 */     if ($$1.isEyeInFluid(FluidTags.WATER) || $$33 < $$32) {
/*      */       
/*  911 */       int $$34 = getVisibleVehicleHeartRows($$22) - 1;
/*  912 */       $$16 -= $$34 * 10;
/*      */       
/*  914 */       int $$35 = Mth.ceil(($$33 - 2) * 10.0D / $$32);
/*  915 */       int $$36 = Mth.ceil($$33 * 10.0D / $$32) - $$35;
/*  916 */       for (int $$37 = 0; $$37 < $$35 + $$36; $$37++) {
/*  917 */         if ($$37 < $$35) {
/*  918 */           $$0.blitSprite(AIR_SPRITE, $$9 - $$37 * 8 - 9, $$16, 9, 9);
/*      */         } else {
/*  920 */           $$0.blitSprite(AIR_BURSTING_SPRITE, $$9 - $$37 * 8 - 9, $$16, 9, 9);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  925 */     this.minecraft.getProfiler().pop();
/*      */   }
/*      */   
/*      */   private enum HeartType {
/*  929 */     CONTAINER((String)new ResourceLocation("hud/heart/container"), new ResourceLocation("hud/heart/container_blinking"), new ResourceLocation("hud/heart/container"), new ResourceLocation("hud/heart/container_blinking"), new ResourceLocation("hud/heart/container_hardcore"), new ResourceLocation("hud/heart/container_hardcore_blinking"), new ResourceLocation("hud/heart/container_hardcore"), new ResourceLocation("hud/heart/container_hardcore_blinking")),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  939 */     NORMAL((String)new ResourceLocation("hud/heart/full"), new ResourceLocation("hud/heart/full_blinking"), new ResourceLocation("hud/heart/half"), new ResourceLocation("hud/heart/half_blinking"), new ResourceLocation("hud/heart/hardcore_full"), new ResourceLocation("hud/heart/hardcore_full_blinking"), new ResourceLocation("hud/heart/hardcore_half"), new ResourceLocation("hud/heart/hardcore_half_blinking")),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  949 */     POISIONED((String)new ResourceLocation("hud/heart/poisoned_full"), new ResourceLocation("hud/heart/poisoned_full_blinking"), new ResourceLocation("hud/heart/poisoned_half"), new ResourceLocation("hud/heart/poisoned_half_blinking"), new ResourceLocation("hud/heart/poisoned_hardcore_full"), new ResourceLocation("hud/heart/poisoned_hardcore_full_blinking"), new ResourceLocation("hud/heart/poisoned_hardcore_half"), new ResourceLocation("hud/heart/poisoned_hardcore_half_blinking")),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  959 */     WITHERED((String)new ResourceLocation("hud/heart/withered_full"), new ResourceLocation("hud/heart/withered_full_blinking"), new ResourceLocation("hud/heart/withered_half"), new ResourceLocation("hud/heart/withered_half_blinking"), new ResourceLocation("hud/heart/withered_hardcore_full"), new ResourceLocation("hud/heart/withered_hardcore_full_blinking"), new ResourceLocation("hud/heart/withered_hardcore_half"), new ResourceLocation("hud/heart/withered_hardcore_half_blinking")),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  969 */     ABSORBING((String)new ResourceLocation("hud/heart/absorbing_full"), new ResourceLocation("hud/heart/absorbing_full_blinking"), new ResourceLocation("hud/heart/absorbing_half"), new ResourceLocation("hud/heart/absorbing_half_blinking"), new ResourceLocation("hud/heart/absorbing_hardcore_full"), new ResourceLocation("hud/heart/absorbing_hardcore_full_blinking"), new ResourceLocation("hud/heart/absorbing_hardcore_half"), new ResourceLocation("hud/heart/absorbing_hardcore_half_blinking")),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  979 */     FROZEN((String)new ResourceLocation("hud/heart/frozen_full"), new ResourceLocation("hud/heart/frozen_full_blinking"), new ResourceLocation("hud/heart/frozen_half"), new ResourceLocation("hud/heart/frozen_half_blinking"), new ResourceLocation("hud/heart/frozen_hardcore_full"), new ResourceLocation("hud/heart/frozen_hardcore_full_blinking"), new ResourceLocation("hud/heart/frozen_hardcore_half"), new ResourceLocation("hud/heart/frozen_hardcore_half_blinking"));
/*      */ 
/*      */     
/*      */     private final ResourceLocation full;
/*      */ 
/*      */     
/*      */     private final ResourceLocation fullBlinking;
/*      */     
/*      */     private final ResourceLocation half;
/*      */     
/*      */     private final ResourceLocation halfBlinking;
/*      */     
/*      */     private final ResourceLocation hardcoreFull;
/*      */     
/*      */     private final ResourceLocation hardcoreFullBlinking;
/*      */     
/*      */     private final ResourceLocation hardcoreHalf;
/*      */     
/*      */     private final ResourceLocation hardcoreHalfBlinking;
/*      */ 
/*      */     
/*      */     HeartType(ResourceLocation $$0, ResourceLocation $$1, ResourceLocation $$2, ResourceLocation $$3, ResourceLocation $$4, ResourceLocation $$5, ResourceLocation $$6, ResourceLocation $$7) {
/* 1001 */       this.full = $$0;
/* 1002 */       this.fullBlinking = $$1;
/* 1003 */       this.half = $$2;
/* 1004 */       this.halfBlinking = $$3;
/* 1005 */       this.hardcoreFull = $$4;
/* 1006 */       this.hardcoreFullBlinking = $$5;
/* 1007 */       this.hardcoreHalf = $$6;
/* 1008 */       this.hardcoreHalfBlinking = $$7;
/*      */     }
/*      */     
/*      */     public ResourceLocation getSprite(boolean $$0, boolean $$1, boolean $$2) {
/* 1012 */       if (!$$0) {
/* 1013 */         if ($$1) {
/* 1014 */           return $$2 ? this.halfBlinking : this.half;
/*      */         }
/* 1016 */         return $$2 ? this.fullBlinking : this.full;
/*      */       } 
/*      */       
/* 1019 */       if ($$1) {
/* 1020 */         return $$2 ? this.hardcoreHalfBlinking : this.hardcoreHalf;
/*      */       }
/* 1022 */       return $$2 ? this.hardcoreFullBlinking : this.hardcoreFull;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     static HeartType forPlayer(Player $$0) {
/*      */       HeartType $$4;
/* 1029 */       if ($$0.hasEffect(MobEffects.POISON)) {
/* 1030 */         HeartType $$1 = POISIONED;
/* 1031 */       } else if ($$0.hasEffect(MobEffects.WITHER)) {
/* 1032 */         HeartType $$2 = WITHERED;
/* 1033 */       } else if ($$0.isFullyFrozen()) {
/* 1034 */         HeartType $$3 = FROZEN;
/*      */       } else {
/* 1036 */         $$4 = NORMAL;
/*      */       } 
/* 1038 */       return $$4;
/*      */     }
/*      */   }
/*      */   
/*      */   private void renderHearts(GuiGraphics $$0, Player $$1, int $$2, int $$3, int $$4, int $$5, float $$6, int $$7, int $$8, int $$9, boolean $$10) {
/* 1043 */     HeartType $$11 = HeartType.forPlayer($$1);
/* 1044 */     boolean $$12 = $$1.level().getLevelData().isHardcore();
/*      */     
/* 1046 */     int $$13 = Mth.ceil($$6 / 2.0D);
/* 1047 */     int $$14 = Mth.ceil($$9 / 2.0D);
/* 1048 */     int $$15 = $$13 * 2;
/*      */     
/* 1050 */     for (int $$16 = $$13 + $$14 - 1; $$16 >= 0; $$16--) {
/* 1051 */       int $$17 = $$16 / 10;
/* 1052 */       int $$18 = $$16 % 10;
/*      */       
/* 1054 */       int $$19 = $$2 + $$18 * 8;
/* 1055 */       int $$20 = $$3 - $$17 * $$4;
/*      */       
/* 1057 */       if ($$7 + $$9 <= 4) {
/* 1058 */         $$20 += this.random.nextInt(2);
/*      */       }
/* 1060 */       if ($$16 < $$13 && $$16 == $$5) {
/* 1061 */         $$20 -= 2;
/*      */       }
/*      */       
/* 1064 */       renderHeart($$0, HeartType.CONTAINER, $$19, $$20, $$12, $$10, false);
/*      */       
/* 1066 */       int $$21 = $$16 * 2;
/*      */       
/* 1068 */       boolean $$22 = ($$16 >= $$13);
/* 1069 */       if ($$22) {
/* 1070 */         int $$23 = $$21 - $$15;
/* 1071 */         if ($$23 < $$9) {
/* 1072 */           boolean $$24 = ($$23 + 1 == $$9);
/* 1073 */           renderHeart($$0, ($$11 == HeartType.WITHERED) ? $$11 : HeartType.ABSORBING, $$19, $$20, $$12, false, $$24);
/*      */         } 
/*      */       } 
/* 1076 */       if ($$10 && $$21 < $$8) {
/* 1077 */         boolean $$25 = ($$21 + 1 == $$8);
/* 1078 */         renderHeart($$0, $$11, $$19, $$20, $$12, true, $$25);
/*      */       } 
/* 1080 */       if ($$21 < $$7) {
/* 1081 */         boolean $$26 = ($$21 + 1 == $$7);
/* 1082 */         renderHeart($$0, $$11, $$19, $$20, $$12, false, $$26);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void renderHeart(GuiGraphics $$0, HeartType $$1, int $$2, int $$3, boolean $$4, boolean $$5, boolean $$6) {
/* 1088 */     $$0.blitSprite($$1.getSprite($$4, $$6, $$5), $$2, $$3, 9, 9);
/*      */   }
/*      */   
/*      */   private void renderVehicleHealth(GuiGraphics $$0) {
/* 1092 */     LivingEntity $$1 = getPlayerVehicleWithHealth();
/* 1093 */     if ($$1 == null) {
/*      */       return;
/*      */     }
/* 1096 */     int $$2 = getVehicleMaxHearts($$1);
/* 1097 */     if ($$2 == 0) {
/*      */       return;
/*      */     }
/* 1100 */     int $$3 = (int)Math.ceil($$1.getHealth());
/*      */ 
/*      */     
/* 1103 */     this.minecraft.getProfiler().popPush("mountHealth");
/*      */ 
/*      */     
/* 1106 */     int $$4 = this.screenHeight - 39;
/* 1107 */     int $$5 = this.screenWidth / 2 + 91;
/* 1108 */     int $$6 = $$4;
/* 1109 */     int $$7 = 0;
/*      */     
/* 1111 */     while ($$2 > 0) {
/* 1112 */       int $$8 = Math.min($$2, 10);
/* 1113 */       $$2 -= $$8;
/*      */       
/* 1115 */       for (int $$9 = 0; $$9 < $$8; $$9++) {
/* 1116 */         int $$10 = $$5 - $$9 * 8 - 9;
/* 1117 */         $$0.blitSprite(HEART_VEHICLE_CONTAINER_SPRITE, $$10, $$6, 9, 9);
/* 1118 */         if ($$9 * 2 + 1 + $$7 < $$3) {
/* 1119 */           $$0.blitSprite(HEART_VEHICLE_FULL_SPRITE, $$10, $$6, 9, 9);
/*      */         }
/* 1121 */         if ($$9 * 2 + 1 + $$7 == $$3) {
/* 1122 */           $$0.blitSprite(HEART_VEHICLE_HALF_SPRITE, $$10, $$6, 9, 9);
/*      */         }
/*      */       } 
/* 1125 */       $$6 -= 10;
/* 1126 */       $$7 += 20;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void renderTextureOverlay(GuiGraphics $$0, ResourceLocation $$1, float $$2) {
/* 1131 */     RenderSystem.disableDepthTest();
/* 1132 */     RenderSystem.depthMask(false);
/*      */     
/* 1134 */     $$0.setColor(1.0F, 1.0F, 1.0F, $$2);
/*      */     
/* 1136 */     $$0.blit($$1, 0, 0, -90, 0.0F, 0.0F, this.screenWidth, this.screenHeight, this.screenWidth, this.screenHeight);
/*      */     
/* 1138 */     RenderSystem.depthMask(true);
/* 1139 */     RenderSystem.enableDepthTest();
/* 1140 */     $$0.setColor(1.0F, 1.0F, 1.0F, 1.0F);
/*      */   }
/*      */   
/*      */   private void renderSpyglassOverlay(GuiGraphics $$0, float $$1) {
/* 1144 */     float $$2 = Math.min(this.screenWidth, this.screenHeight);
/* 1145 */     float $$3 = $$2;
/* 1146 */     float $$4 = Math.min(this.screenWidth / $$2, this.screenHeight / $$3) * $$1;
/*      */     
/* 1148 */     int $$5 = Mth.floor($$2 * $$4);
/* 1149 */     int $$6 = Mth.floor($$3 * $$4);
/*      */     
/* 1151 */     int $$7 = (this.screenWidth - $$5) / 2;
/* 1152 */     int $$8 = (this.screenHeight - $$6) / 2;
/* 1153 */     int $$9 = $$7 + $$5;
/* 1154 */     int $$10 = $$8 + $$6;
/*      */ 
/*      */     
/* 1157 */     $$0.blit(SPYGLASS_SCOPE_LOCATION, $$7, $$8, -90, 0.0F, 0.0F, $$5, $$6, $$5, $$6);
/*      */ 
/*      */     
/* 1160 */     $$0.fill(RenderType.guiOverlay(), 0, $$10, this.screenWidth, this.screenHeight, -90, -16777216);
/*      */     
/* 1162 */     $$0.fill(RenderType.guiOverlay(), 0, 0, this.screenWidth, $$8, -90, -16777216);
/*      */     
/* 1164 */     $$0.fill(RenderType.guiOverlay(), 0, $$8, $$7, $$10, -90, -16777216);
/*      */     
/* 1166 */     $$0.fill(RenderType.guiOverlay(), $$9, $$8, this.screenWidth, $$10, -90, -16777216);
/*      */   }
/*      */   
/*      */   private void updateVignetteBrightness(Entity $$0) {
/* 1170 */     BlockPos $$1 = BlockPos.containing($$0.getX(), $$0.getEyeY(), $$0.getZ());
/* 1171 */     float $$2 = LightTexture.getBrightness($$0.level().dimensionType(), $$0.level().getMaxLocalRawBrightness($$1));
/* 1172 */     float $$3 = Mth.clamp(1.0F - $$2, 0.0F, 1.0F);
/* 1173 */     this.vignetteBrightness += ($$3 - this.vignetteBrightness) * 0.01F;
/*      */   }
/*      */   
/*      */   private void renderVignette(GuiGraphics $$0, @Nullable Entity $$1) {
/* 1177 */     WorldBorder $$2 = this.minecraft.level.getWorldBorder();
/*      */     
/* 1179 */     float $$3 = 0.0F;
/*      */     
/* 1181 */     if ($$1 != null) {
/* 1182 */       float $$4 = (float)$$2.getDistanceToBorder($$1);
/* 1183 */       double $$5 = Math.min($$2.getLerpSpeed() * $$2.getWarningTime() * 1000.0D, Math.abs($$2.getLerpTarget() - $$2.getSize()));
/* 1184 */       double $$6 = Math.max($$2.getWarningBlocks(), $$5);
/* 1185 */       if ($$4 < $$6) {
/* 1186 */         $$3 = 1.0F - (float)($$4 / $$6);
/*      */       }
/*      */     } 
/*      */     
/* 1190 */     RenderSystem.disableDepthTest();
/* 1191 */     RenderSystem.depthMask(false);
/* 1192 */     RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/*      */ 
/*      */     
/* 1195 */     if ($$3 > 0.0F) {
/* 1196 */       $$3 = Mth.clamp($$3, 0.0F, 1.0F);
/* 1197 */       $$0.setColor(0.0F, $$3, $$3, 1.0F);
/*      */     } else {
/* 1199 */       float $$7 = this.vignetteBrightness;
/* 1200 */       $$7 = Mth.clamp($$7, 0.0F, 1.0F);
/* 1201 */       $$0.setColor($$7, $$7, $$7, 1.0F);
/*      */     } 
/*      */     
/* 1204 */     $$0.blit(VIGNETTE_LOCATION, 0, 0, -90, 0.0F, 0.0F, this.screenWidth, this.screenHeight, this.screenWidth, this.screenHeight);
/*      */     
/* 1206 */     RenderSystem.depthMask(true);
/* 1207 */     RenderSystem.enableDepthTest();
/* 1208 */     $$0.setColor(1.0F, 1.0F, 1.0F, 1.0F);
/* 1209 */     RenderSystem.defaultBlendFunc();
/*      */   }
/*      */   
/*      */   private void renderPortalOverlay(GuiGraphics $$0, float $$1) {
/* 1213 */     if ($$1 < 1.0F) {
/* 1214 */       $$1 *= $$1;
/* 1215 */       $$1 *= $$1;
/* 1216 */       $$1 = $$1 * 0.8F + 0.2F;
/*      */     } 
/*      */     
/* 1219 */     RenderSystem.disableDepthTest();
/* 1220 */     RenderSystem.depthMask(false);
/* 1221 */     $$0.setColor(1.0F, 1.0F, 1.0F, $$1);
/*      */     
/* 1223 */     TextureAtlasSprite $$2 = this.minecraft.getBlockRenderer().getBlockModelShaper().getParticleIcon(Blocks.NETHER_PORTAL.defaultBlockState());
/* 1224 */     $$0.blit(0, 0, -90, this.screenWidth, this.screenHeight, $$2);
/*      */     
/* 1226 */     RenderSystem.depthMask(true);
/* 1227 */     RenderSystem.enableDepthTest();
/* 1228 */     $$0.setColor(1.0F, 1.0F, 1.0F, 1.0F);
/*      */   }
/*      */   
/*      */   private void renderSlot(GuiGraphics $$0, int $$1, int $$2, float $$3, Player $$4, ItemStack $$5, int $$6) {
/* 1232 */     if ($$5.isEmpty()) {
/*      */       return;
/*      */     }
/*      */     
/* 1236 */     float $$7 = $$5.getPopTime() - $$3;
/* 1237 */     if ($$7 > 0.0F) {
/* 1238 */       float $$8 = 1.0F + $$7 / 5.0F;
/*      */       
/* 1240 */       $$0.pose().pushPose();
/* 1241 */       $$0.pose().translate(($$1 + 8), ($$2 + 12), 0.0F);
/* 1242 */       $$0.pose().scale(1.0F / $$8, ($$8 + 1.0F) / 2.0F, 1.0F);
/* 1243 */       $$0.pose().translate(-($$1 + 8), -($$2 + 12), 0.0F);
/*      */     } 
/*      */     
/* 1246 */     $$0.renderItem((LivingEntity)$$4, $$5, $$1, $$2, $$6);
/*      */     
/* 1248 */     if ($$7 > 0.0F) {
/* 1249 */       $$0.pose().popPose();
/*      */     }
/*      */     
/* 1252 */     $$0.renderItemDecorations(this.minecraft.font, $$5, $$1, $$2);
/*      */   }
/*      */   
/*      */   public void tick(boolean $$0) {
/* 1256 */     tickAutosaveIndicator();
/* 1257 */     if (!$$0) {
/* 1258 */       tick();
/*      */     }
/*      */   }
/*      */   
/*      */   private void tick() {
/* 1263 */     if (this.overlayMessageTime > 0) {
/* 1264 */       this.overlayMessageTime--;
/*      */     }
/* 1266 */     if (this.titleTime > 0) {
/* 1267 */       this.titleTime--;
/* 1268 */       if (this.titleTime <= 0) {
/* 1269 */         this.title = null;
/* 1270 */         this.subtitle = null;
/*      */       } 
/*      */     } 
/* 1273 */     this.tickCount++;
/*      */     
/* 1275 */     Entity $$0 = this.minecraft.getCameraEntity();
/* 1276 */     if ($$0 != null) {
/* 1277 */       updateVignetteBrightness($$0);
/*      */     }
/*      */     
/* 1280 */     if (this.minecraft.player != null) {
/* 1281 */       ItemStack $$1 = this.minecraft.player.getInventory().getSelected();
/*      */       
/* 1283 */       if ($$1.isEmpty()) {
/* 1284 */         this.toolHighlightTimer = 0;
/* 1285 */       } else if (this.lastToolHighlight.isEmpty() || !$$1.is(this.lastToolHighlight.getItem()) || !$$1.getHoverName().equals(this.lastToolHighlight.getHoverName())) {
/* 1286 */         this.toolHighlightTimer = (int)(40.0D * ((Double)this.minecraft.options.notificationDisplayTime().get()).doubleValue());
/* 1287 */       } else if (this.toolHighlightTimer > 0) {
/* 1288 */         this.toolHighlightTimer--;
/*      */       } 
/* 1290 */       this.lastToolHighlight = $$1;
/*      */     } 
/*      */     
/* 1293 */     this.chat.tick();
/*      */   }
/*      */   
/*      */   private void tickAutosaveIndicator() {
/* 1297 */     IntegratedServer integratedServer = this.minecraft.getSingleplayerServer();
/* 1298 */     boolean $$1 = (integratedServer != null && integratedServer.isCurrentlySaving());
/* 1299 */     this.lastAutosaveIndicatorValue = this.autosaveIndicatorValue;
/* 1300 */     this.autosaveIndicatorValue = Mth.lerp(0.2F, this.autosaveIndicatorValue, $$1 ? 1.0F : 0.0F);
/*      */   }
/*      */   
/*      */   public void setNowPlaying(Component $$0) {
/* 1304 */     MutableComponent mutableComponent = Component.translatable("record.nowPlaying", new Object[] { $$0 });
/* 1305 */     setOverlayMessage((Component)mutableComponent, true);
/* 1306 */     this.minecraft.getNarrator().sayNow((Component)mutableComponent);
/*      */   }
/*      */   
/*      */   public void setOverlayMessage(Component $$0, boolean $$1) {
/* 1310 */     setChatDisabledByPlayerShown(false);
/* 1311 */     this.overlayMessageString = $$0;
/* 1312 */     this.overlayMessageTime = 60;
/* 1313 */     this.animateOverlayMessageColor = $$1;
/*      */   }
/*      */   
/*      */   public void setChatDisabledByPlayerShown(boolean $$0) {
/* 1317 */     this.chatDisabledByPlayerShown = $$0;
/*      */   }
/*      */   
/*      */   public boolean isShowingChatDisabledByPlayer() {
/* 1321 */     return (this.chatDisabledByPlayerShown && this.overlayMessageTime > 0);
/*      */   }
/*      */   
/*      */   public void setTimes(int $$0, int $$1, int $$2) {
/* 1325 */     if ($$0 >= 0) {
/* 1326 */       this.titleFadeInTime = $$0;
/*      */     }
/* 1328 */     if ($$1 >= 0) {
/* 1329 */       this.titleStayTime = $$1;
/*      */     }
/* 1331 */     if ($$2 >= 0) {
/* 1332 */       this.titleFadeOutTime = $$2;
/*      */     }
/* 1334 */     if (this.titleTime > 0) {
/* 1335 */       this.titleTime = this.titleFadeInTime + this.titleStayTime + this.titleFadeOutTime;
/*      */     }
/*      */   }
/*      */   
/*      */   public void setSubtitle(Component $$0) {
/* 1340 */     this.subtitle = $$0;
/*      */   }
/*      */   
/*      */   public void setTitle(Component $$0) {
/* 1344 */     this.title = $$0;
/* 1345 */     this.titleTime = this.titleFadeInTime + this.titleStayTime + this.titleFadeOutTime;
/*      */   }
/*      */   
/*      */   public void clear() {
/* 1349 */     this.title = null;
/* 1350 */     this.subtitle = null;
/* 1351 */     this.titleTime = 0;
/*      */   }
/*      */   
/*      */   public ChatComponent getChat() {
/* 1355 */     return this.chat;
/*      */   }
/*      */   
/*      */   public int getGuiTicks() {
/* 1359 */     return this.tickCount;
/*      */   }
/*      */   
/*      */   public Font getFont() {
/* 1363 */     return this.minecraft.font;
/*      */   }
/*      */   
/*      */   public SpectatorGui getSpectatorGui() {
/* 1367 */     return this.spectatorGui;
/*      */   }
/*      */   
/*      */   public PlayerTabOverlay getTabList() {
/* 1371 */     return this.tabList;
/*      */   }
/*      */   
/*      */   public void onDisconnected() {
/* 1375 */     this.tabList.reset();
/* 1376 */     this.bossOverlay.reset();
/* 1377 */     this.minecraft.getToasts().clear();
/*      */     
/* 1379 */     this.debugOverlay.reset();
/* 1380 */     this.chat.clearMessages(true);
/*      */   }
/*      */   
/*      */   public BossHealthOverlay getBossOverlay() {
/* 1384 */     return this.bossOverlay;
/*      */   }
/*      */   
/*      */   public DebugScreenOverlay getDebugOverlay() {
/* 1388 */     return this.debugOverlay;
/*      */   }
/*      */   
/*      */   public void clearCache() {
/* 1392 */     this.debugOverlay.clearChunkCache();
/*      */   }
/*      */   
/*      */   private void renderSavingIndicator(GuiGraphics $$0) {
/* 1396 */     if (((Boolean)this.minecraft.options.showAutosaveIndicator().get()).booleanValue() && (this.autosaveIndicatorValue > 0.0F || this.lastAutosaveIndicatorValue > 0.0F)) {
/* 1397 */       int $$1 = Mth.floor(255.0F * Mth.clamp(Mth.lerp(this.minecraft.getFrameTime(), this.lastAutosaveIndicatorValue, this.autosaveIndicatorValue), 0.0F, 1.0F));
/* 1398 */       if ($$1 > 8) {
/* 1399 */         Font $$2 = getFont();
/* 1400 */         int $$3 = $$2.width((FormattedText)SAVING_TEXT);
/* 1401 */         int $$4 = 0xFFFFFF | $$1 << 24 & 0xFF000000;
/* 1402 */         $$0.drawString($$2, SAVING_TEXT, this.screenWidth - $$3 - 10, this.screenHeight - 15, $$4);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\Gui.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */