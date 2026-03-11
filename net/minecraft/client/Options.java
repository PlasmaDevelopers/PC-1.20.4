/*      */ package net.minecraft.client;
/*      */ import com.google.common.base.MoreObjects;
/*      */ import com.google.common.base.Splitter;
/*      */ import com.google.common.collect.ImmutableList;
/*      */ import com.google.common.collect.Lists;
/*      */ import com.google.common.collect.Sets;
/*      */ import com.google.common.io.Files;
/*      */ import com.google.gson.Gson;
/*      */ import com.google.gson.JsonElement;
/*      */ import com.google.gson.JsonParser;
/*      */ import com.google.gson.reflect.TypeToken;
/*      */ import com.google.gson.stream.JsonReader;
/*      */ import com.mojang.blaze3d.pipeline.RenderTarget;
/*      */ import com.mojang.blaze3d.platform.InputConstants;
/*      */ import com.mojang.blaze3d.platform.VideoMode;
/*      */ import com.mojang.blaze3d.platform.Window;
/*      */ import com.mojang.blaze3d.systems.RenderSystem;
/*      */ import com.mojang.datafixers.util.Pair;
/*      */ import com.mojang.logging.LogUtils;
/*      */ import com.mojang.serialization.Codec;
/*      */ import com.mojang.serialization.DataResult;
/*      */ import com.mojang.serialization.DynamicOps;
/*      */ import com.mojang.serialization.JsonOps;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.File;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.OutputStreamWriter;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.StringReader;
/*      */ import java.nio.charset.StandardCharsets;
/*      */ import java.util.Arrays;
/*      */ import java.util.EnumMap;
/*      */ import java.util.EnumSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Objects;
/*      */ import java.util.Optional;
/*      */ import java.util.Set;
/*      */ import java.util.function.Function;
/*      */ import java.util.stream.Collectors;
/*      */ import java.util.stream.Stream;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.ChatFormatting;
/*      */ import net.minecraft.SharedConstants;
/*      */ import net.minecraft.Util;
/*      */ import net.minecraft.client.gui.components.ChatComponent;
/*      */ import net.minecraft.client.gui.components.Tooltip;
/*      */ import net.minecraft.client.renderer.GpuWarnlistManager;
/*      */ import net.minecraft.client.resources.sounds.SimpleSoundInstance;
/*      */ import net.minecraft.client.resources.sounds.SoundInstance;
/*      */ import net.minecraft.client.sounds.SoundEngine;
/*      */ import net.minecraft.client.sounds.SoundManager;
/*      */ import net.minecraft.client.tutorial.TutorialSteps;
/*      */ import net.minecraft.core.Holder;
/*      */ import net.minecraft.nbt.CompoundTag;
/*      */ import net.minecraft.network.chat.CommonComponents;
/*      */ import net.minecraft.network.chat.Component;
/*      */ import net.minecraft.network.chat.MutableComponent;
/*      */ import net.minecraft.network.protocol.Packet;
/*      */ import net.minecraft.network.protocol.common.ServerboundClientInformationPacket;
/*      */ import net.minecraft.server.level.ClientInformation;
/*      */ import net.minecraft.server.packs.repository.Pack;
/*      */ import net.minecraft.server.packs.repository.PackRepository;
/*      */ import net.minecraft.sounds.SoundEvents;
/*      */ import net.minecraft.sounds.SoundSource;
/*      */ import net.minecraft.util.ExtraCodecs;
/*      */ import net.minecraft.util.GsonHelper;
/*      */ import net.minecraft.util.Mth;
/*      */ import net.minecraft.util.datafix.DataFixTypes;
/*      */ import net.minecraft.world.entity.HumanoidArm;
/*      */ import net.minecraft.world.entity.player.ChatVisiblity;
/*      */ import net.minecraft.world.entity.player.PlayerModelPart;
/*      */ import org.apache.commons.lang3.ArrayUtils;
/*      */ import org.slf4j.Logger;
/*      */ 
/*      */ public class Options {
/*   79 */   static final Logger LOGGER = LogUtils.getLogger();
/*   80 */   static final Gson GSON = new Gson();
/*   81 */   private static final TypeToken<List<String>> LIST_OF_STRINGS_TYPE = new TypeToken<List<String>>() {
/*      */     
/*      */     };
/*      */   public static final int RENDER_DISTANCE_TINY = 2;
/*      */   public static final int RENDER_DISTANCE_SHORT = 4;
/*      */   public static final int RENDER_DISTANCE_NORMAL = 8;
/*      */   public static final int RENDER_DISTANCE_FAR = 12;
/*      */   public static final int RENDER_DISTANCE_REALLY_FAR = 16;
/*      */   public static final int RENDER_DISTANCE_EXTREME = 32;
/*   90 */   private static final Splitter OPTION_SPLITTER = Splitter.on(':').limit(2);
/*      */   
/*      */   private static final float DEFAULT_VOLUME = 1.0F;
/*      */   public static final String DEFAULT_SOUND_DEVICE = "";
/*   94 */   private static final Component ACCESSIBILITY_TOOLTIP_DARK_MOJANG_BACKGROUND = (Component)Component.translatable("options.darkMojangStudiosBackgroundColor.tooltip");
/*   95 */   private final OptionInstance<Boolean> darkMojangStudiosBackground = OptionInstance.createBoolean("options.darkMojangStudiosBackgroundColor", 
/*      */       
/*   97 */       OptionInstance.cachedConstantTooltip(ACCESSIBILITY_TOOLTIP_DARK_MOJANG_BACKGROUND), false);
/*      */ 
/*      */ 
/*      */   
/*      */   public OptionInstance<Boolean> darkMojangStudiosBackground() {
/*  102 */     return this.darkMojangStudiosBackground;
/*      */   }
/*      */   
/*  105 */   private static final Component ACCESSIBILITY_TOOLTIP_HIDE_LIGHTNING_FLASHES = (Component)Component.translatable("options.hideLightningFlashes.tooltip");
/*  106 */   private final OptionInstance<Boolean> hideLightningFlash = OptionInstance.createBoolean("options.hideLightningFlashes", 
/*      */       
/*  108 */       OptionInstance.cachedConstantTooltip(ACCESSIBILITY_TOOLTIP_HIDE_LIGHTNING_FLASHES), false);
/*      */ 
/*      */ 
/*      */   
/*      */   public OptionInstance<Boolean> hideLightningFlash() {
/*  113 */     return this.hideLightningFlash;
/*      */   }
/*      */   
/*  116 */   private static final Component ACCESSIBILITY_TOOLTIP_HIDE_SPLASH_TEXTS = (Component)Component.translatable("options.hideSplashTexts.tooltip");
/*  117 */   private final OptionInstance<Boolean> hideSplashTexts = OptionInstance.createBoolean("options.hideSplashTexts", 
/*      */       
/*  119 */       OptionInstance.cachedConstantTooltip(ACCESSIBILITY_TOOLTIP_HIDE_SPLASH_TEXTS), false); private final OptionInstance<Double> sensitivity; private final OptionInstance<Integer> renderDistance; private final OptionInstance<Integer> simulationDistance; private int serverRenderDistance; private final OptionInstance<Double> entityDistanceScaling; public static final int UNLIMITED_FRAMERATE_CUTOFF = 260;
/*      */   private final OptionInstance<Integer> framerateLimit;
/*      */   private final OptionInstance<CloudStatus> cloudStatus;
/*      */   
/*      */   public OptionInstance<Boolean> hideSplashTexts() {
/*  124 */     return this.hideSplashTexts;
/*      */   }
/*      */   
/*      */   public Options(Minecraft $$0, File $$1) {
/*  128 */     this
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
/*  141 */       .sensitivity = new OptionInstance<>("options.sensitivity", OptionInstance.noTooltip(), ($$0, $$1) -> ($$1.doubleValue() == 0.0D) ? genericValueLabel($$0, (Component)Component.translatable("options.sensitivity.min")) : (($$1.doubleValue() == 1.0D) ? genericValueLabel($$0, (Component)Component.translatable("options.sensitivity.max")) : percentValueLabel($$0, 2.0D * $$1.doubleValue())), OptionInstance.UnitDouble.INSTANCE, Double.valueOf(0.5D), $$0 -> {
/*      */         
/*      */         });
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  161 */     this.serverRenderDistance = 0;
/*      */     
/*  163 */     this
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  169 */       .entityDistanceScaling = new OptionInstance<>("options.entityDistanceScaling", OptionInstance.noTooltip(), Options::percentValueLabel, (new OptionInstance.IntRange(2, 20)).xmap($$0 -> Double.valueOf($$0 / 4.0D), $$0 -> (int)($$0.doubleValue() * 4.0D)), Codec.doubleRange(0.5D, 5.0D), Double.valueOf(1.0D), $$0 -> {
/*      */         
/*      */         });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  179 */     this
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
/*  191 */       .framerateLimit = new OptionInstance<>("options.framerateLimit", OptionInstance.noTooltip(), ($$0, $$1) -> ($$1.intValue() == 260) ? genericValueLabel($$0, (Component)Component.translatable("options.framerateLimit.max")) : genericValueLabel($$0, (Component)Component.translatable("options.framerate", new Object[] { $$1 })), (new OptionInstance.IntRange(1, 26)).xmap($$0 -> Integer.valueOf($$0 * 10), $$0 -> $$0.intValue() / 10), Codec.intRange(10, 260), Integer.valueOf(120), $$0 -> Minecraft.getInstance().getWindow().setFramerateLimit($$0.intValue()));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  199 */     this
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  204 */       .cloudStatus = new OptionInstance<>("options.renderClouds", OptionInstance.noTooltip(), OptionInstance.forOptionEnum(), new OptionInstance.Enum<>(Arrays.asList(CloudStatus.values()), ExtraCodecs.withAlternative(CloudStatus.CODEC, (Codec)Codec.BOOL, $$0 -> $$0.booleanValue() ? CloudStatus.FANCY : CloudStatus.OFF)), CloudStatus.FANCY, $$0 -> {
/*      */           if (Minecraft.useShaderTransparency()) {
/*      */             RenderTarget $$1 = (Minecraft.getInstance()).levelRenderer.getCloudsTarget();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             if ($$1 != null) {
/*      */               $$1.clear(Minecraft.ON_OSX);
/*      */             }
/*      */           } 
/*      */         });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  229 */     this
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  262 */       .graphicsMode = new OptionInstance<>("options.graphics", $$0 -> { switch ($$0) { default: throw new IncompatibleClassChangeError();case FANCY: case FAST: case FABULOUS: break; }  return Tooltip.create(GRAPHICS_TOOLTIP_FABULOUS); }($$0, $$1) -> { MutableComponent $$2 = Component.translatable($$1.getKey()); return (Component)(($$1 == GraphicsStatus.FABULOUS) ? $$2.withStyle(ChatFormatting.ITALIC) : $$2); }new OptionInstance.AltEnum<>(Arrays.asList(GraphicsStatus.values()), (List<GraphicsStatus>)Stream.<GraphicsStatus>of(GraphicsStatus.values()).filter($$0 -> ($$0 != GraphicsStatus.FABULOUS)).collect(Collectors.toList()), () -> (Minecraft.getInstance().isRunning() && Minecraft.getInstance().getGpuWarnlistManager().isSkippingFabulous()), ($$0, $$1) -> { Minecraft $$2 = Minecraft.getInstance(); GpuWarnlistManager $$3 = $$2.getGpuWarnlistManager(); if ($$1 == GraphicsStatus.FABULOUS && $$3.willShowWarning()) { $$3.showWarning(); return; }  $$0.set($$1); $$2.levelRenderer.allChanged(); }Codec.INT.xmap(GraphicsStatus::byId, GraphicsStatus::getId)), GraphicsStatus.FANCY, $$0 -> {
/*      */         
/*      */         });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  274 */     this.ambientOcclusion = OptionInstance.createBoolean("options.ao", true, $$0 -> (Minecraft.getInstance()).levelRenderer.allChanged());
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
/*      */     
/*  288 */     this
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  296 */       .prioritizeChunkUpdates = new OptionInstance<>("options.prioritizeChunkUpdates", $$0 -> { switch ($$0) { default: throw new IncompatibleClassChangeError();case FANCY: case FAST: case FABULOUS: break; }  return Tooltip.create(PRIORITIZE_CHUNK_TOOLTIP_NEARBY); }OptionInstance.forOptionEnum(), new OptionInstance.Enum<>(Arrays.asList(PrioritizeChunkUpdates.values()), Codec.INT.xmap(PrioritizeChunkUpdates::byId, PrioritizeChunkUpdates::getId)), PrioritizeChunkUpdates.NONE, $$0 -> {
/*      */         
/*      */         });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  305 */     this.resourcePacks = Lists.newArrayList();
/*  306 */     this.incompatibleResourcePacks = Lists.newArrayList();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  327 */     this
/*      */ 
/*      */ 
/*      */       
/*  331 */       .chatVisibility = new OptionInstance<>("options.chat.visibility", OptionInstance.noTooltip(), OptionInstance.forOptionEnum(), new OptionInstance.Enum<>(Arrays.asList(ChatVisiblity.values()), Codec.INT.xmap(ChatVisiblity::byId, ChatVisiblity::getId)), ChatVisiblity.FULL, $$0 -> {
/*      */         
/*      */         });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  340 */     this
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  345 */       .chatOpacity = new OptionInstance<>("options.chat.opacity", OptionInstance.noTooltip(), ($$0, $$1) -> percentValueLabel($$0, $$1.doubleValue() * 0.9D + 0.1D), OptionInstance.UnitDouble.INSTANCE, Double.valueOf(1.0D), $$0 -> (Minecraft.getInstance()).gui.getChat().rescaleChat());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  353 */     this
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  358 */       .chatLineSpacing = new OptionInstance<>("options.chat.line_spacing", OptionInstance.noTooltip(), Options::percentValueLabel, OptionInstance.UnitDouble.INSTANCE, Double.valueOf(0.0D), $$0 -> {
/*      */         
/*      */         });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  366 */     this
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  371 */       .textBackgroundOpacity = new OptionInstance<>("options.accessibility.text_background_opacity", OptionInstance.noTooltip(), Options::percentValueLabel, OptionInstance.UnitDouble.INSTANCE, Double.valueOf(0.5D), $$0 -> (Minecraft.getInstance()).gui.getChat().rescaleChat());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  379 */     this
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  384 */       .panoramaSpeed = new OptionInstance<>("options.accessibility.panorama_speed", OptionInstance.noTooltip(), Options::percentValueLabel, OptionInstance.UnitDouble.INSTANCE, Double.valueOf(1.0D), $$0 -> {
/*      */         
/*      */         });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  393 */     this.highContrast = OptionInstance.createBoolean("options.accessibility.high_contrast", 
/*      */         
/*  395 */         OptionInstance.cachedConstantTooltip(ACCESSIBILITY_TOOLTIP_CONTRAST_MODE), false, $$0 -> {
/*      */           PackRepository $$1 = Minecraft.getInstance().getResourcePackRepository();
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           boolean $$2 = $$1.getSelectedIds().contains("high_contrast");
/*      */ 
/*      */ 
/*      */           
/*      */           if (!$$2 && $$0.booleanValue()) {
/*      */             if ($$1.addPack("high_contrast")) {
/*      */               updateResourcePacks($$1);
/*      */             }
/*      */           } else if ($$2 && !$$0.booleanValue() && $$1.removePack("high_contrast")) {
/*      */             updateResourcePacks($$1);
/*      */           } 
/*      */         });
/*      */ 
/*      */ 
/*      */     
/*  416 */     this.narratorHotkey = OptionInstance.createBoolean("options.accessibility.narrator_hotkey", 
/*      */         
/*  418 */         OptionInstance.cachedConstantTooltip(
/*  419 */           Minecraft.ON_OSX ? 
/*  420 */           (Component)Component.translatable("options.accessibility.narrator_hotkey.mac.tooltip") : 
/*  421 */           (Component)Component.translatable("options.accessibility.narrator_hotkey.tooltip")), true);
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
/*  434 */     this.pauseOnLostFocus = true;
/*  435 */     this.modelParts = EnumSet.allOf(PlayerModelPart.class);
/*  436 */     this
/*      */ 
/*      */ 
/*      */       
/*  440 */       .mainHand = new OptionInstance<>("options.mainHand", OptionInstance.noTooltip(), OptionInstance.forOptionEnum(), new OptionInstance.Enum<>(Arrays.asList(HumanoidArm.values()), HumanoidArm.CODEC), HumanoidArm.RIGHT, $$0 -> broadcastOptions());
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
/*  452 */     this
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  462 */       .chatScale = new OptionInstance<>("options.chat.scale", OptionInstance.noTooltip(), ($$0, $$1) -> ($$1.doubleValue() == 0.0D) ? CommonComponents.optionStatus($$0, false) : percentValueLabel($$0, $$1.doubleValue()), OptionInstance.UnitDouble.INSTANCE, Double.valueOf(1.0D), $$0 -> (Minecraft.getInstance()).gui.getChat().rescaleChat());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  470 */     this
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  475 */       .chatWidth = new OptionInstance<>("options.chat.width", OptionInstance.noTooltip(), ($$0, $$1) -> pixelValueLabel($$0, ChatComponent.getWidth($$1.doubleValue())), OptionInstance.UnitDouble.INSTANCE, Double.valueOf(1.0D), $$0 -> (Minecraft.getInstance()).gui.getChat().rescaleChat());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  483 */     this
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  488 */       .chatHeightUnfocused = new OptionInstance<>("options.chat.height.unfocused", OptionInstance.noTooltip(), ($$0, $$1) -> pixelValueLabel($$0, ChatComponent.getHeight($$1.doubleValue())), OptionInstance.UnitDouble.INSTANCE, Double.valueOf(ChatComponent.defaultUnfocusedPct()), $$0 -> (Minecraft.getInstance()).gui.getChat().rescaleChat());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  496 */     this
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  501 */       .chatHeightFocused = new OptionInstance<>("options.chat.height.focused", OptionInstance.noTooltip(), ($$0, $$1) -> pixelValueLabel($$0, ChatComponent.getHeight($$1.doubleValue())), OptionInstance.UnitDouble.INSTANCE, Double.valueOf(1.0D), $$0 -> (Minecraft.getInstance()).gui.getChat().rescaleChat());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  509 */     this
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
/*  520 */       .chatDelay = new OptionInstance<>("options.chat.delay_instant", OptionInstance.noTooltip(), ($$0, $$1) -> ($$1.doubleValue() <= 0.0D) ? Component.translatable("options.chat.delay_none") : Component.translatable("options.chat.delay", new Object[] { String.format(Locale.ROOT, "%.1f", new Object[] { $$1 }) }), (new OptionInstance.IntRange(0, 60)).xmap($$0 -> Double.valueOf($$0 / 10.0D), $$0 -> (int)($$0.doubleValue() * 10.0D)), Codec.doubleRange(0.0D, 6.0D), Double.valueOf(0.0D), $$0 -> Minecraft.getInstance().getChatListener().setMessageDelay($$0.doubleValue()));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  528 */     this
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  534 */       .notificationDisplayTime = new OptionInstance<>("options.notifications.display_time", OptionInstance.cachedConstantTooltip(ACCESSIBILITY_TOOLTIP_NOTIFICATION_DISPLAY_TIME), ($$0, $$1) -> genericValueLabel($$0, (Component)Component.translatable("options.multiplier", new Object[] { $$1 })), (new OptionInstance.IntRange(5, 100)).xmap($$0 -> Double.valueOf($$0 / 10.0D), $$0 -> (int)($$0.doubleValue() * 10.0D)), Codec.doubleRange(0.5D, 10.0D), Double.valueOf(1.0D), $$0 -> {
/*      */         
/*      */         });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  542 */     this
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  552 */       .mipmapLevels = new OptionInstance<>("options.mipmapLevels", OptionInstance.noTooltip(), ($$0, $$1) -> ($$1.intValue() == 0) ? CommonComponents.optionStatus($$0, false) : genericValueLabel($$0, $$1.intValue()), new OptionInstance.IntRange(0, 4), Integer.valueOf(4), $$0 -> {
/*      */         
/*      */         });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  560 */     this.useNativeTransport = true;
/*  561 */     this
/*      */ 
/*      */ 
/*      */       
/*  565 */       .attackIndicator = new OptionInstance<>("options.attackIndicator", OptionInstance.noTooltip(), OptionInstance.forOptionEnum(), new OptionInstance.Enum<>(Arrays.asList(AttackIndicatorStatus.values()), Codec.INT.xmap(AttackIndicatorStatus::byId, AttackIndicatorStatus::getId)), AttackIndicatorStatus.CROSSHAIR, $$0 -> {
/*      */         
/*      */         });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  574 */     this.tutorialStep = TutorialSteps.MOVEMENT;
/*  575 */     this.joinedFirstServer = false;
/*  576 */     this.hideBundleTutorial = false;
/*  577 */     this
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  585 */       .biomeBlendRadius = new OptionInstance<>("options.biomeBlendRadius", OptionInstance.noTooltip(), ($$0, $$1) -> { int $$2 = $$1.intValue() * 2 + 1; return genericValueLabel($$0, (Component)Component.translatable("options.biomeBlendRadius." + $$2)); }new OptionInstance.IntRange(0, 7), Integer.valueOf(2), $$0 -> (Minecraft.getInstance()).levelRenderer.allChanged());
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
/*      */ 
/*      */ 
/*      */     
/*  601 */     this
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  607 */       .mouseWheelSensitivity = new OptionInstance<>("options.mouseWheelSensitivity", OptionInstance.noTooltip(), ($$0, $$1) -> genericValueLabel($$0, (Component)Component.literal(String.format(Locale.ROOT, "%.2f", new Object[] { $$1 }))), (new OptionInstance.IntRange(-200, 100)).xmap(Options::logMouse, Options::unlogMouse), Codec.doubleRange(logMouse(-200), logMouse(100)), Double.valueOf(logMouse(0)), $$0 -> {
/*      */         
/*      */         });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  615 */     this.rawMouseInput = OptionInstance.createBoolean("options.rawMouseInput", true, $$0 -> {
/*      */           Window $$1 = Minecraft.getInstance().getWindow();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           if ($$1 != null) {
/*      */             $$1.updateRawMouseInput($$0.booleanValue());
/*      */           }
/*      */         });
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  630 */     this.glDebugVerbosity = 1;
/*      */     
/*  632 */     this.autoJump = OptionInstance.createBoolean("options.autoJump", false);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  637 */     this.operatorItemsTab = OptionInstance.createBoolean("options.operatorItemsTab", false);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  642 */     this.autoSuggestions = OptionInstance.createBoolean("options.autoSuggestCommands", true);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  647 */     this.chatColors = OptionInstance.createBoolean("options.chat.color", true);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  652 */     this.chatLinks = OptionInstance.createBoolean("options.chat.links", true);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  657 */     this.chatLinksPrompt = OptionInstance.createBoolean("options.chat.links.prompt", true);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  662 */     this.enableVsync = OptionInstance.createBoolean("options.vsync", true, $$0 -> {
/*      */           if (Minecraft.getInstance().getWindow() != null) {
/*      */             Minecraft.getInstance().getWindow().updateVsync($$0.booleanValue());
/*      */           }
/*      */         });
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  671 */     this.entityShadows = OptionInstance.createBoolean("options.entityShadows", true);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  676 */     this.forceUnicodeFont = OptionInstance.createBoolean("options.forceUnicodeFont", false, $$0 -> {
/*      */           Minecraft $$1 = Minecraft.getInstance();
/*      */ 
/*      */           
/*      */           if ($$1.getWindow() != null) {
/*      */             $$1.selectMainFont($$0.booleanValue());
/*      */ 
/*      */             
/*      */             $$1.resizeDisplay();
/*      */           } 
/*      */         });
/*      */ 
/*      */     
/*  689 */     this.invertYMouse = OptionInstance.createBoolean("options.invertMouse", false);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  694 */     this.discreteMouseScroll = OptionInstance.createBoolean("options.discrete_mouse_scroll", false);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  699 */     this.realmsNotifications = OptionInstance.createBoolean("options.realmsNotifications", true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  705 */     this.allowServerListing = OptionInstance.createBoolean("options.allowServerListing", 
/*      */         
/*  707 */         OptionInstance.cachedConstantTooltip(ALLOW_SERVER_LISTING_TOOLTIP), true, $$0 -> broadcastOptions());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  716 */     this.reducedDebugInfo = OptionInstance.createBoolean("options.reducedDebugInfo", false);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  721 */     this.soundSourceVolumes = (Map<SoundSource, OptionInstance<Double>>)Util.make(new EnumMap<>(SoundSource.class), $$0 -> {
/*      */           for (SoundSource $$1 : SoundSource.values()) {
/*      */             $$0.put($$1, createSoundSliderOptionInstance("soundCategory." + $$1.getName(), $$1));
/*      */           }
/*      */         });
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
/*      */     
/*  751 */     this.showSubtitles = OptionInstance.createBoolean("options.showSubtitles", false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  759 */     this.directionalAudio = OptionInstance.createBoolean("options.directionalAudio", $$0 -> $$0.booleanValue() ? Tooltip.create(DIRECTIONAL_AUDIO_TOOLTIP_ON) : Tooltip.create(DIRECTIONAL_AUDIO_TOOLTIP_OFF), false, $$0 -> {
/*      */           SoundManager $$1 = Minecraft.getInstance().getSoundManager();
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           $$1.reload();
/*      */ 
/*      */ 
/*      */           
/*      */           $$1.play((SoundInstance)SimpleSoundInstance.forUI((Holder)SoundEvents.UI_BUTTON_CLICK, 1.0F));
/*      */         });
/*      */ 
/*      */ 
/*      */     
/*  774 */     this
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  779 */       .backgroundForChatOnly = new OptionInstance<>("options.accessibility.text_background", OptionInstance.noTooltip(), ($$0, $$1) -> $$1.booleanValue() ? (Component)Component.translatable("options.accessibility.text_background.chat") : (Component)Component.translatable("options.accessibility.text_background.everywhere"), OptionInstance.BOOLEAN_VALUES, Boolean.valueOf(true), $$0 -> {
/*      */         
/*      */         });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  787 */     this.touchscreen = OptionInstance.createBoolean("options.touchscreen", false);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  792 */     this.fullscreen = OptionInstance.createBoolean("options.fullscreen", false, $$0 -> {
/*      */           Minecraft $$1 = Minecraft.getInstance();
/*      */ 
/*      */           
/*      */           if ($$1.getWindow() != null && $$1.getWindow().isFullscreen() != $$0.booleanValue()) {
/*      */             $$1.getWindow().toggleFullScreen();
/*      */             
/*      */             fullscreen().set(Boolean.valueOf($$1.getWindow().isFullscreen()));
/*      */           } 
/*      */         });
/*      */     
/*  803 */     this.bobView = OptionInstance.createBoolean("options.viewBobbing", true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  811 */     this
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  816 */       .toggleCrouch = new OptionInstance<>("key.sneak", OptionInstance.noTooltip(), ($$0, $$1) -> $$1.booleanValue() ? MOVEMENT_TOGGLE : MOVEMENT_HOLD, OptionInstance.BOOLEAN_VALUES, Boolean.valueOf(false), $$0 -> {
/*      */         
/*      */         });
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  823 */     this
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  828 */       .toggleSprint = new OptionInstance<>("key.sprint", OptionInstance.noTooltip(), ($$0, $$1) -> $$1.booleanValue() ? MOVEMENT_TOGGLE : MOVEMENT_HOLD, OptionInstance.BOOLEAN_VALUES, Boolean.valueOf(false), $$0 -> {
/*      */         
/*      */         });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  840 */     this.hideMatchedNames = OptionInstance.createBoolean("options.hideMatchedNames", 
/*      */         
/*  842 */         OptionInstance.cachedConstantTooltip(CHAT_TOOLTIP_HIDE_MATCHED_NAMES), true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  850 */     this.showAutosaveIndicator = OptionInstance.createBoolean("options.autosaveIndicator", true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  857 */     this.onlyShowSecureChat = OptionInstance.createBoolean("options.onlyShowSecureChat", 
/*      */         
/*  859 */         OptionInstance.cachedConstantTooltip(CHAT_TOOLTIP_ONLY_SHOW_SECURE), false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  867 */     this.keyUp = new KeyMapping("key.forward", 87, "key.categories.movement");
/*  868 */     this.keyLeft = new KeyMapping("key.left", 65, "key.categories.movement");
/*  869 */     this.keyDown = new KeyMapping("key.back", 83, "key.categories.movement");
/*  870 */     this.keyRight = new KeyMapping("key.right", 68, "key.categories.movement");
/*  871 */     this.keyJump = new KeyMapping("key.jump", 32, "key.categories.movement");
/*      */ 
/*      */     
/*  874 */     Objects.requireNonNull(this.toggleCrouch); this.keyShift = new ToggleKeyMapping("key.sneak", 340, "key.categories.movement", this.toggleCrouch::get);
/*  875 */     Objects.requireNonNull(this.toggleSprint); this.keySprint = new ToggleKeyMapping("key.sprint", 341, "key.categories.movement", this.toggleSprint::get);
/*      */     
/*  877 */     this.keyInventory = new KeyMapping("key.inventory", 69, "key.categories.inventory");
/*  878 */     this.keySwapOffhand = new KeyMapping("key.swapOffhand", 70, "key.categories.inventory");
/*  879 */     this.keyDrop = new KeyMapping("key.drop", 81, "key.categories.inventory");
/*      */     
/*  881 */     this.keyUse = new KeyMapping("key.use", InputConstants.Type.MOUSE, 1, "key.categories.gameplay");
/*  882 */     this.keyAttack = new KeyMapping("key.attack", InputConstants.Type.MOUSE, 0, "key.categories.gameplay");
/*  883 */     this.keyPickItem = new KeyMapping("key.pickItem", InputConstants.Type.MOUSE, 2, "key.categories.gameplay");
/*      */     
/*  885 */     this.keyChat = new KeyMapping("key.chat", 84, "key.categories.multiplayer");
/*  886 */     this.keyPlayerList = new KeyMapping("key.playerlist", 258, "key.categories.multiplayer");
/*  887 */     this.keyCommand = new KeyMapping("key.command", 47, "key.categories.multiplayer");
/*  888 */     this.keySocialInteractions = new KeyMapping("key.socialInteractions", 80, "key.categories.multiplayer");
/*      */     
/*  890 */     this.keyScreenshot = new KeyMapping("key.screenshot", 291, "key.categories.misc");
/*  891 */     this.keyTogglePerspective = new KeyMapping("key.togglePerspective", 294, "key.categories.misc");
/*  892 */     this.keySmoothCamera = new KeyMapping("key.smoothCamera", InputConstants.UNKNOWN.getValue(), "key.categories.misc");
/*  893 */     this.keyFullscreen = new KeyMapping("key.fullscreen", 300, "key.categories.misc");
/*  894 */     this.keySpectatorOutlines = new KeyMapping("key.spectatorOutlines", InputConstants.UNKNOWN.getValue(), "key.categories.misc");
/*  895 */     this.keyAdvancements = new KeyMapping("key.advancements", 76, "key.categories.misc");
/*      */     
/*  897 */     this.keyHotbarSlots = new KeyMapping[] { new KeyMapping("key.hotbar.1", 49, "key.categories.inventory"), new KeyMapping("key.hotbar.2", 50, "key.categories.inventory"), new KeyMapping("key.hotbar.3", 51, "key.categories.inventory"), new KeyMapping("key.hotbar.4", 52, "key.categories.inventory"), new KeyMapping("key.hotbar.5", 53, "key.categories.inventory"), new KeyMapping("key.hotbar.6", 54, "key.categories.inventory"), new KeyMapping("key.hotbar.7", 55, "key.categories.inventory"), new KeyMapping("key.hotbar.8", 56, "key.categories.inventory"), new KeyMapping("key.hotbar.9", 57, "key.categories.inventory") };
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
/*  908 */     this.keySaveHotbarActivator = new KeyMapping("key.saveToolbarActivator", 67, "key.categories.creative");
/*  909 */     this.keyLoadHotbarActivator = new KeyMapping("key.loadToolbarActivator", 88, "key.categories.creative");
/*      */     
/*  911 */     this.keyMappings = (KeyMapping[])ArrayUtils.addAll((Object[])new KeyMapping[] { this.keyAttack, this.keyUse, this.keyUp, this.keyLeft, this.keyDown, this.keyRight, this.keyJump, this.keyShift, this.keySprint, this.keyDrop, this.keyInventory, this.keyChat, this.keyPlayerList, this.keyPickItem, this.keyCommand, this.keySocialInteractions, this.keyScreenshot, this.keyTogglePerspective, this.keySmoothCamera, this.keyFullscreen, this.keySpectatorOutlines, this.keySwapOffhand, this.keySaveHotbarActivator, this.keyLoadHotbarActivator, this.keyAdvancements }, (Object[])this.keyHotbarSlots);
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
/*      */     
/*  925 */     this.cameraType = CameraType.FIRST_PERSON;
/*  926 */     this.lastMpIp = "";
/*      */ 
/*      */ 
/*      */     
/*  930 */     this
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  940 */       .fov = new OptionInstance<>("options.fov", OptionInstance.noTooltip(), ($$0, $$1) -> { switch ($$1.intValue()) { case 70: case 110:  }  return genericValueLabel($$0, $$1.intValue()); }new OptionInstance.IntRange(30, 110), Codec.DOUBLE.xmap($$0 -> Integer.valueOf((int)($$0.doubleValue() * 40.0D + 70.0D)), $$0 -> Double.valueOf(($$0.intValue() - 70.0D) / 40.0D)), Integer.valueOf(70), $$0 -> (Minecraft.getInstance()).levelRenderer.needsUpdate());
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
/*  953 */     this.telemetryOptInExtra = OptionInstance.createBoolean("options.telemetry.button", 
/*      */         
/*  955 */         OptionInstance.cachedConstantTooltip(TELEMETRY_TOOLTIP), ($$0, $$1) -> {
/*      */           Minecraft $$2 = Minecraft.getInstance();
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  961 */           return (Component)(!$$2.allowsTelemetry() ? Component.translatable("options.telemetry.state.none") : (($$1.booleanValue() && $$2.extraTelemetryAvailable()) ? Component.translatable("options.telemetry.state.all") : Component.translatable("options.telemetry.state.minimal")));
/*      */         }false, $$0 -> {
/*      */         
/*      */         });
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
/*  976 */     this
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  986 */       .screenEffectScale = new OptionInstance<>("options.screenEffectScale", OptionInstance.cachedConstantTooltip(ACCESSIBILITY_TOOLTIP_SCREEN_EFFECT), ($$0, $$1) -> ($$1.doubleValue() == 0.0D) ? genericValueLabel($$0, CommonComponents.OPTION_OFF) : percentValueLabel($$0, $$1.doubleValue()), OptionInstance.UnitDouble.INSTANCE, Double.valueOf(1.0D), $$0 -> {
/*      */         
/*      */         });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  996 */     this
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
/*      */       
/* 1010 */       .fovEffectScale = new OptionInstance<>("options.fovEffectScale", OptionInstance.cachedConstantTooltip(ACCESSIBILITY_TOOLTIP_FOV_EFFECT), ($$0, $$1) -> ($$1.doubleValue() == 0.0D) ? genericValueLabel($$0, CommonComponents.OPTION_OFF) : percentValueLabel($$0, $$1.doubleValue()), OptionInstance.UnitDouble.INSTANCE.xmap(Mth::square, Math::sqrt), Codec.doubleRange(0.0D, 1.0D), Double.valueOf(1.0D), $$0 -> {
/*      */         
/*      */         });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1020 */     this
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
/* 1033 */       .darknessEffectScale = new OptionInstance<>("options.darknessEffectScale", OptionInstance.cachedConstantTooltip(ACCESSIBILITY_TOOLTIP_DARKNESS_EFFECT), ($$0, $$1) -> ($$1.doubleValue() == 0.0D) ? genericValueLabel($$0, CommonComponents.OPTION_OFF) : percentValueLabel($$0, $$1.doubleValue()), OptionInstance.UnitDouble.INSTANCE.xmap(Mth::square, Math::sqrt), Double.valueOf(1.0D), $$0 -> {
/*      */         
/*      */         });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1043 */     this
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1053 */       .glintSpeed = new OptionInstance<>("options.glintSpeed", OptionInstance.cachedConstantTooltip(ACCESSIBILITY_TOOLTIP_GLINT_SPEED), ($$0, $$1) -> ($$1.doubleValue() == 0.0D) ? genericValueLabel($$0, CommonComponents.OPTION_OFF) : percentValueLabel($$0, $$1.doubleValue()), OptionInstance.UnitDouble.INSTANCE, Double.valueOf(0.5D), $$0 -> {
/*      */         
/*      */         });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1063 */     this
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1073 */       .glintStrength = new OptionInstance<>("options.glintStrength", OptionInstance.cachedConstantTooltip(ACCESSIBILITY_TOOLTIP_GLINT_STRENGTH), ($$0, $$1) -> ($$1.doubleValue() == 0.0D) ? genericValueLabel($$0, CommonComponents.OPTION_OFF) : percentValueLabel($$0, $$1.doubleValue()), OptionInstance.UnitDouble.INSTANCE, Double.valueOf(0.75D), RenderSystem::setShaderGlintAlpha);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1083 */     this
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1093 */       .damageTiltStrength = new OptionInstance<>("options.damageTiltStrength", OptionInstance.cachedConstantTooltip(ACCESSIBILITY_TOOLTIP_DAMAGE_TILT_STRENGTH), ($$0, $$1) -> ($$1.doubleValue() == 0.0D) ? genericValueLabel($$0, CommonComponents.OPTION_OFF) : percentValueLabel($$0, $$1.doubleValue()), OptionInstance.UnitDouble.INSTANCE, Double.valueOf(1.0D), $$0 -> {
/*      */         
/*      */         });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1101 */     this
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
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1118 */       .gamma = new OptionInstance<>("options.gamma", OptionInstance.noTooltip(), ($$0, $$1) -> { int $$2 = (int)($$1.doubleValue() * 100.0D); return ($$2 == 0) ? genericValueLabel($$0, (Component)Component.translatable("options.gamma.min")) : (($$2 == 50) ? genericValueLabel($$0, (Component)Component.translatable("options.gamma.default")) : (($$2 == 100) ? genericValueLabel($$0, (Component)Component.translatable("options.gamma.max")) : genericValueLabel($$0, $$2))); }OptionInstance.UnitDouble.INSTANCE, Double.valueOf(0.5D), $$0 -> {
/*      */         
/*      */         });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1128 */     this
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
/* 1139 */       .guiScale = new OptionInstance<>("options.guiScale", OptionInstance.noTooltip(), ($$0, $$1) -> ($$1.intValue() == 0) ? (Component)Component.translatable("options.guiScale.auto") : (Component)Component.literal(Integer.toString($$1.intValue())), new OptionInstance.ClampingLazyMaxIntRange(0, () -> { Minecraft $$0 = Minecraft.getInstance(); return !$$0.isRunning() ? 2147483646 : $$0.getWindow().calculateScale(0, $$0.isEnforceUnicode()); }2147483646), Integer.valueOf(0), $$0 -> {
/*      */         
/*      */         });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1147 */     this
/*      */ 
/*      */ 
/*      */       
/* 1151 */       .particles = new OptionInstance<>("options.particles", OptionInstance.noTooltip(), OptionInstance.forOptionEnum(), new OptionInstance.Enum<>(Arrays.asList(ParticleStatus.values()), Codec.INT.xmap(ParticleStatus::byId, ParticleStatus::getId)), ParticleStatus.ALL, $$0 -> {
/*      */         
/*      */         });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1160 */     this
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1170 */       .narrator = new OptionInstance<>("options.narrator", OptionInstance.noTooltip(), ($$0, $$1) -> this.minecraft.getNarrator().isActive() ? $$1.getName() : Component.translatable("options.narrator.notavailable"), new OptionInstance.Enum<>(Arrays.asList(NarratorStatus.values()), Codec.INT.xmap(NarratorStatus::byId, NarratorStatus::getId)), NarratorStatus.OFF, $$0 -> this.minecraft.getNarrator().updateNarratorStatus($$0));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1179 */     this.languageCode = "en_us";
/* 1180 */     this
/*      */       
/* 1182 */       .soundDevice = new OptionInstance<>("options.audioDevice", OptionInstance.noTooltip(), ($$0, $$1) -> "".equals($$1) ? Component.translatable("options.audioDevice.default") : ($$1.startsWith("OpenAL Soft on ") ? Component.literal($$1.substring(SoundEngine.OPEN_AL_SOFT_PREFIX_LENGTH)) : Component.literal($$1)), new OptionInstance.LazyEnum<>(() -> Stream.concat(Stream.of(""), Minecraft.getInstance().getSoundManager().getAvailableSoundDevices().stream()).toList(), $$0 -> 
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
/* 1195 */           (!Minecraft.getInstance().isRunning() || $$0 == "" || Minecraft.getInstance().getSoundManager().getAvailableSoundDevices().contains($$0)) ? Optional.of($$0) : Optional.empty(), (Codec<String>)Codec.STRING), "", $$0 -> {
/*      */           SoundManager $$1 = Minecraft.getInstance().getSoundManager();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           $$1.reload();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           $$1.play((SoundInstance)SimpleSoundInstance.forUI((Holder)SoundEvents.UI_BUTTON_CLICK, 1.0F));
/*      */         });
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1214 */     this.onboardAccessibility = true;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1219 */     this.minecraft = $$0;
/* 1220 */     this.optionsFile = new File($$1, "options.txt");
/*      */     
/* 1222 */     boolean $$2 = $$0.is64Bit();
/* 1223 */     boolean $$3 = ($$2 && Runtime.getRuntime().maxMemory() >= 1000000000L);
/*      */     
/* 1225 */     this
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1233 */       .renderDistance = new OptionInstance<>("options.renderDistance", OptionInstance.noTooltip(), ($$0, $$1) -> genericValueLabel($$0, (Component)Component.translatable("options.chunks", new Object[] { $$1 })), new OptionInstance.IntRange(2, $$3 ? 32 : 16), Integer.valueOf($$2 ? 12 : 8), $$0 -> (Minecraft.getInstance()).levelRenderer.needsUpdate());
/*      */ 
/*      */ 
/*      */     
/* 1237 */     this
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1245 */       .simulationDistance = new OptionInstance<>("options.simulationDistance", OptionInstance.noTooltip(), ($$0, $$1) -> genericValueLabel($$0, (Component)Component.translatable("options.chunks", new Object[] { $$1 })), new OptionInstance.IntRange(5, $$3 ? 32 : 16), Integer.valueOf($$2 ? 12 : 8), $$0 -> {
/*      */         
/*      */         });
/*      */ 
/*      */ 
/*      */     
/* 1251 */     this.syncWrites = (Util.getPlatform() == Util.OS.WINDOWS);
/*      */     
/* 1253 */     load();
/*      */   }
/*      */   public OptionInstance<Double> sensitivity() { return this.sensitivity; }
/*      */   public OptionInstance<Integer> renderDistance() { return this.renderDistance; }
/* 1257 */   public OptionInstance<Integer> simulationDistance() { return this.simulationDistance; } public OptionInstance<Double> entityDistanceScaling() { return this.entityDistanceScaling; } public OptionInstance<Integer> framerateLimit() { return this.framerateLimit; } public OptionInstance<CloudStatus> cloudStatus() { return this.cloudStatus; } private static final Component GRAPHICS_TOOLTIP_FAST = (Component)Component.translatable("options.graphics.fast.tooltip"); private static final Component GRAPHICS_TOOLTIP_FABULOUS = (Component)Component.translatable("options.graphics.fabulous.tooltip", new Object[] { Component.translatable("options.graphics.fabulous").withStyle(ChatFormatting.ITALIC) }); private static final Component GRAPHICS_TOOLTIP_FANCY = (Component)Component.translatable("options.graphics.fancy.tooltip"); private final OptionInstance<GraphicsStatus> graphicsMode; private final OptionInstance<Boolean> ambientOcclusion; public OptionInstance<GraphicsStatus> graphicsMode() { return this.graphicsMode; } public OptionInstance<Boolean> ambientOcclusion() { return this.ambientOcclusion; } private static final Component PRIORITIZE_CHUNK_TOOLTIP_NONE = (Component)Component.translatable("options.prioritizeChunkUpdates.none.tooltip"); private static final Component PRIORITIZE_CHUNK_TOOLTIP_PLAYER_AFFECTED = (Component)Component.translatable("options.prioritizeChunkUpdates.byPlayer.tooltip"); private static final Component PRIORITIZE_CHUNK_TOOLTIP_NEARBY = (Component)Component.translatable("options.prioritizeChunkUpdates.nearby.tooltip"); private final OptionInstance<PrioritizeChunkUpdates> prioritizeChunkUpdates; public List<String> resourcePacks; public List<String> incompatibleResourcePacks; private final OptionInstance<ChatVisiblity> chatVisibility; private final OptionInstance<Double> chatOpacity; private final OptionInstance<Double> chatLineSpacing; private final OptionInstance<Double> textBackgroundOpacity; private final OptionInstance<Double> panoramaSpeed; public OptionInstance<PrioritizeChunkUpdates> prioritizeChunkUpdates() { return this.prioritizeChunkUpdates; } public void updateResourcePacks(PackRepository $$0) { ImmutableList immutableList1 = ImmutableList.copyOf(this.resourcePacks); this.resourcePacks.clear(); this.incompatibleResourcePacks.clear(); for (Pack $$2 : $$0.getSelectedPacks()) { if (!$$2.isFixedPosition()) { this.resourcePacks.add($$2.getId()); if (!$$2.getCompatibility().isCompatible()) this.incompatibleResourcePacks.add($$2.getId());  }  }  save(); ImmutableList immutableList2 = ImmutableList.copyOf(this.resourcePacks); if (!immutableList2.equals(immutableList1)) this.minecraft.reloadResourcePacks();  } public OptionInstance<ChatVisiblity> chatVisibility() { return this.chatVisibility; } public OptionInstance<Double> chatOpacity() { return this.chatOpacity; } public OptionInstance<Double> chatLineSpacing() { return this.chatLineSpacing; } public OptionInstance<Double> textBackgroundOpacity() { return this.textBackgroundOpacity; } public OptionInstance<Double> panoramaSpeed() { return this.panoramaSpeed; } private static final Component ACCESSIBILITY_TOOLTIP_CONTRAST_MODE = (Component)Component.translatable("options.accessibility.high_contrast.tooltip"); private final OptionInstance<Boolean> highContrast; private final OptionInstance<Boolean> narratorHotkey; @Nullable public String fullscreenVideoModeString; public boolean hideServerAddress; public boolean advancedItemTooltips; public boolean pauseOnLostFocus; private final Set<PlayerModelPart> modelParts; private final OptionInstance<HumanoidArm> mainHand; public int overrideWidth; public int overrideHeight; private final OptionInstance<Double> chatScale; private final OptionInstance<Double> chatWidth; private final OptionInstance<Double> chatHeightUnfocused; private final OptionInstance<Double> chatHeightFocused; private final OptionInstance<Double> chatDelay; public OptionInstance<Boolean> highContrast() { return this.highContrast; } public OptionInstance<Boolean> narratorHotkey() { return this.narratorHotkey; } public OptionInstance<HumanoidArm> mainHand() { return this.mainHand; } public OptionInstance<Double> chatScale() { return this.chatScale; } public OptionInstance<Double> chatWidth() { return this.chatWidth; } public OptionInstance<Double> chatHeightUnfocused() { return this.chatHeightUnfocused; } public OptionInstance<Double> chatHeightFocused() { return this.chatHeightFocused; } public OptionInstance<Double> chatDelay() { return this.chatDelay; } private static final Component ACCESSIBILITY_TOOLTIP_NOTIFICATION_DISPLAY_TIME = (Component)Component.translatable("options.notifications.display_time.tooltip"); private final OptionInstance<Double> notificationDisplayTime; private final OptionInstance<Integer> mipmapLevels; public boolean useNativeTransport; private final OptionInstance<AttackIndicatorStatus> attackIndicator; public TutorialSteps tutorialStep; public boolean joinedFirstServer; public boolean hideBundleTutorial; private final OptionInstance<Integer> biomeBlendRadius; private final OptionInstance<Double> mouseWheelSensitivity; private final OptionInstance<Boolean> rawMouseInput; public int glDebugVerbosity; private final OptionInstance<Boolean> autoJump; private final OptionInstance<Boolean> operatorItemsTab; private final OptionInstance<Boolean> autoSuggestions; private final OptionInstance<Boolean> chatColors; private final OptionInstance<Boolean> chatLinks; private final OptionInstance<Boolean> chatLinksPrompt; private final OptionInstance<Boolean> enableVsync; private final OptionInstance<Boolean> entityShadows; private final OptionInstance<Boolean> forceUnicodeFont; private final OptionInstance<Boolean> invertYMouse; private final OptionInstance<Boolean> discreteMouseScroll; private final OptionInstance<Boolean> realmsNotifications; public OptionInstance<Double> notificationDisplayTime() { return this.notificationDisplayTime; } public OptionInstance<Integer> mipmapLevels() { return this.mipmapLevels; } public OptionInstance<AttackIndicatorStatus> attackIndicator() { return this.attackIndicator; } public OptionInstance<Integer> biomeBlendRadius() { return this.biomeBlendRadius; } private static double logMouse(int $$0) { return Math.pow(10.0D, $$0 / 100.0D); } private static int unlogMouse(double $$0) { return Mth.floor(Math.log10($$0) * 100.0D); } public OptionInstance<Double> mouseWheelSensitivity() { return this.mouseWheelSensitivity; } public OptionInstance<Boolean> rawMouseInput() { return this.rawMouseInput; } public OptionInstance<Boolean> autoJump() { return this.autoJump; } public OptionInstance<Boolean> operatorItemsTab() { return this.operatorItemsTab; } public OptionInstance<Boolean> autoSuggestions() { return this.autoSuggestions; } public OptionInstance<Boolean> chatColors() { return this.chatColors; } public OptionInstance<Boolean> chatLinks() { return this.chatLinks; } public OptionInstance<Boolean> chatLinksPrompt() { return this.chatLinksPrompt; } public OptionInstance<Boolean> enableVsync() { return this.enableVsync; } public OptionInstance<Boolean> entityShadows() { return this.entityShadows; } public OptionInstance<Boolean> forceUnicodeFont() { return this.forceUnicodeFont; } public OptionInstance<Boolean> invertYMouse() { return this.invertYMouse; } public OptionInstance<Boolean> discreteMouseScroll() { return this.discreteMouseScroll; } public OptionInstance<Boolean> realmsNotifications() { return this.realmsNotifications; } private static final Component ALLOW_SERVER_LISTING_TOOLTIP = (Component)Component.translatable("options.allowServerListing.tooltip"); private final OptionInstance<Boolean> allowServerListing; private final OptionInstance<Boolean> reducedDebugInfo; private final Map<SoundSource, OptionInstance<Double>> soundSourceVolumes; private final OptionInstance<Boolean> showSubtitles; public float getBackgroundOpacity(float $$0) { return ((Boolean)this.backgroundForChatOnly.get()).booleanValue() ? $$0 : ((Double)textBackgroundOpacity().get()).floatValue(); }
/*      */   public OptionInstance<Boolean> allowServerListing() { return this.allowServerListing; }
/*      */   public OptionInstance<Boolean> reducedDebugInfo() { return this.reducedDebugInfo; }
/*      */   public final float getSoundSourceVolume(SoundSource $$0) { return ((Double)getSoundSourceOptionInstance($$0).get()).floatValue(); }
/* 1261 */   public final OptionInstance<Double> getSoundSourceOptionInstance(SoundSource $$0) { return Objects.<OptionInstance<Double>>requireNonNull(this.soundSourceVolumes.get($$0)); } private OptionInstance<Double> createSoundSliderOptionInstance(String $$0, SoundSource $$1) { return new OptionInstance<>($$0, OptionInstance.noTooltip(), ($$0, $$1) -> ($$1.doubleValue() == 0.0D) ? genericValueLabel($$0, CommonComponents.OPTION_OFF) : percentValueLabel($$0, $$1.doubleValue()), OptionInstance.UnitDouble.INSTANCE, Double.valueOf(1.0D), $$1 -> Minecraft.getInstance().getSoundManager().updateSourceVolume($$0, $$1.floatValue())); } public OptionInstance<Boolean> showSubtitles() { return this.showSubtitles; } private static final Component DIRECTIONAL_AUDIO_TOOLTIP_ON = (Component)Component.translatable("options.directionalAudio.on.tooltip"); private static final Component DIRECTIONAL_AUDIO_TOOLTIP_OFF = (Component)Component.translatable("options.directionalAudio.off.tooltip"); private final OptionInstance<Boolean> directionalAudio; private final OptionInstance<Boolean> backgroundForChatOnly; private final OptionInstance<Boolean> touchscreen; private final OptionInstance<Boolean> fullscreen; private final OptionInstance<Boolean> bobView; public OptionInstance<Boolean> directionalAudio() { return this.directionalAudio; } public OptionInstance<Boolean> backgroundForChatOnly() { return this.backgroundForChatOnly; } public OptionInstance<Boolean> touchscreen() { return this.touchscreen; } public OptionInstance<Boolean> fullscreen() { return this.fullscreen; } public OptionInstance<Boolean> bobView() { return this.bobView; } private static final Component MOVEMENT_TOGGLE = (Component)Component.translatable("options.key.toggle"); private static final Component MOVEMENT_HOLD = (Component)Component.translatable("options.key.hold"); private final OptionInstance<Boolean> toggleCrouch; private final OptionInstance<Boolean> toggleSprint; public boolean skipMultiplayerWarning; public boolean skipRealms32bitWarning; public OptionInstance<Boolean> toggleCrouch() { return this.toggleCrouch; } public OptionInstance<Boolean> toggleSprint() { return this.toggleSprint; } private static final Component CHAT_TOOLTIP_HIDE_MATCHED_NAMES = (Component)Component.translatable("options.hideMatchedNames.tooltip"); private final OptionInstance<Boolean> hideMatchedNames; private final OptionInstance<Boolean> showAutosaveIndicator; public OptionInstance<Boolean> hideMatchedNames() { return this.hideMatchedNames; } public OptionInstance<Boolean> showAutosaveIndicator() { return this.showAutosaveIndicator; } private static final Component CHAT_TOOLTIP_ONLY_SHOW_SECURE = (Component)Component.translatable("options.onlyShowSecureChat.tooltip"); private final OptionInstance<Boolean> onlyShowSecureChat; public final KeyMapping keyUp; public final KeyMapping keyLeft; public final KeyMapping keyDown; public final KeyMapping keyRight; public final KeyMapping keyJump; public final KeyMapping keyShift; public final KeyMapping keySprint; public final KeyMapping keyInventory; public final KeyMapping keySwapOffhand; public final KeyMapping keyDrop; public final KeyMapping keyUse; public final KeyMapping keyAttack; public final KeyMapping keyPickItem; public final KeyMapping keyChat; public final KeyMapping keyPlayerList; public final KeyMapping keyCommand; public final KeyMapping keySocialInteractions; public final KeyMapping keyScreenshot; public final KeyMapping keyTogglePerspective; public final KeyMapping keySmoothCamera; public final KeyMapping keyFullscreen; public final KeyMapping keySpectatorOutlines; public final KeyMapping keyAdvancements; public final KeyMapping[] keyHotbarSlots; public final KeyMapping keySaveHotbarActivator; public final KeyMapping keyLoadHotbarActivator; public final KeyMapping[] keyMappings; protected Minecraft minecraft; private final File optionsFile; public boolean hideGui; private CameraType cameraType; public String lastMpIp; public boolean smoothCamera; private final OptionInstance<Integer> fov; public OptionInstance<Boolean> onlyShowSecureChat() { return this.onlyShowSecureChat; } public OptionInstance<Integer> fov() { return this.fov; } private static final Component TELEMETRY_TOOLTIP = (Component)Component.translatable("options.telemetry.button.tooltip", new Object[] { Component.translatable("options.telemetry.state.minimal"), Component.translatable("options.telemetry.state.all") }); private final OptionInstance<Boolean> telemetryOptInExtra; public OptionInstance<Boolean> telemetryOptInExtra() { return this.telemetryOptInExtra; } private static final Component ACCESSIBILITY_TOOLTIP_SCREEN_EFFECT = (Component)Component.translatable("options.screenEffectScale.tooltip"); private final OptionInstance<Double> screenEffectScale; public OptionInstance<Double> screenEffectScale() { return this.screenEffectScale; } private static final Component ACCESSIBILITY_TOOLTIP_FOV_EFFECT = (Component)Component.translatable("options.fovEffectScale.tooltip"); private final OptionInstance<Double> fovEffectScale; public OptionInstance<Double> fovEffectScale() { return this.fovEffectScale; } private static final Component ACCESSIBILITY_TOOLTIP_DARKNESS_EFFECT = (Component)Component.translatable("options.darknessEffectScale.tooltip"); private final OptionInstance<Double> darknessEffectScale; public OptionInstance<Double> darknessEffectScale() { return this.darknessEffectScale; } private static final Component ACCESSIBILITY_TOOLTIP_GLINT_SPEED = (Component)Component.translatable("options.glintSpeed.tooltip"); private final OptionInstance<Double> glintSpeed; public OptionInstance<Double> glintSpeed() { return this.glintSpeed; } private static final Component ACCESSIBILITY_TOOLTIP_GLINT_STRENGTH = (Component)Component.translatable("options.glintStrength.tooltip"); private final OptionInstance<Double> glintStrength; public OptionInstance<Double> glintStrength() { return this.glintStrength; } private static final Component ACCESSIBILITY_TOOLTIP_DAMAGE_TILT_STRENGTH = (Component)Component.translatable("options.damageTiltStrength.tooltip"); private final OptionInstance<Double> damageTiltStrength; private final OptionInstance<Double> gamma; public static final int AUTO_GUI_SCALE = 0; private static final int MAX_GUI_SCALE_INCLUSIVE = 2147483646; private final OptionInstance<Integer> guiScale; private final OptionInstance<ParticleStatus> particles; private final OptionInstance<NarratorStatus> narrator; public String languageCode; private final OptionInstance<String> soundDevice; public boolean onboardAccessibility; public boolean syncWrites; public OptionInstance<Double> damageTiltStrength() { return this.damageTiltStrength; } public OptionInstance<Double> gamma() { return this.gamma; } public OptionInstance<Integer> guiScale() { return this.guiScale; } public OptionInstance<ParticleStatus> particles() { return this.particles; } public OptionInstance<NarratorStatus> narrator() { return this.narrator; } public OptionInstance<String> soundDevice() { return this.soundDevice; } public int getBackgroundColor(float $$0) { return (int)(getBackgroundOpacity($$0) * 255.0F) << 24 & 0xFF000000; }
/*      */ 
/*      */   
/*      */   public int getBackgroundColor(int $$0) {
/* 1265 */     return ((Boolean)this.backgroundForChatOnly.get()).booleanValue() ? $$0 : ((int)(((Double)this.textBackgroundOpacity.get()).doubleValue() * 255.0D) << 24 & 0xFF000000);
/*      */   }
/*      */   
/*      */   public void setKey(KeyMapping $$0, InputConstants.Key $$1) {
/* 1269 */     $$0.setKey($$1);
/* 1270 */     save();
/*      */   }
/*      */   
/*      */   private void processOptions(FieldAccess $$0) {
/* 1274 */     $$0.process("autoJump", this.autoJump);
/* 1275 */     $$0.process("operatorItemsTab", this.operatorItemsTab);
/* 1276 */     $$0.process("autoSuggestions", this.autoSuggestions);
/* 1277 */     $$0.process("chatColors", this.chatColors);
/* 1278 */     $$0.process("chatLinks", this.chatLinks);
/* 1279 */     $$0.process("chatLinksPrompt", this.chatLinksPrompt);
/* 1280 */     $$0.process("enableVsync", this.enableVsync);
/* 1281 */     $$0.process("entityShadows", this.entityShadows);
/* 1282 */     $$0.process("forceUnicodeFont", this.forceUnicodeFont);
/* 1283 */     $$0.process("discrete_mouse_scroll", this.discreteMouseScroll);
/* 1284 */     $$0.process("invertYMouse", this.invertYMouse);
/* 1285 */     $$0.process("realmsNotifications", this.realmsNotifications);
/* 1286 */     $$0.process("reducedDebugInfo", this.reducedDebugInfo);
/* 1287 */     $$0.process("showSubtitles", this.showSubtitles);
/* 1288 */     $$0.process("directionalAudio", this.directionalAudio);
/* 1289 */     $$0.process("touchscreen", this.touchscreen);
/* 1290 */     $$0.process("fullscreen", this.fullscreen);
/* 1291 */     $$0.process("bobView", this.bobView);
/* 1292 */     $$0.process("toggleCrouch", this.toggleCrouch);
/* 1293 */     $$0.process("toggleSprint", this.toggleSprint);
/* 1294 */     $$0.process("darkMojangStudiosBackground", this.darkMojangStudiosBackground);
/* 1295 */     $$0.process("hideLightningFlashes", this.hideLightningFlash);
/* 1296 */     $$0.process("hideSplashTexts", this.hideSplashTexts);
/* 1297 */     $$0.process("mouseSensitivity", this.sensitivity);
/* 1298 */     $$0.process("fov", this.fov);
/* 1299 */     $$0.process("screenEffectScale", this.screenEffectScale);
/* 1300 */     $$0.process("fovEffectScale", this.fovEffectScale);
/* 1301 */     $$0.process("darknessEffectScale", this.darknessEffectScale);
/* 1302 */     $$0.process("glintSpeed", this.glintSpeed);
/* 1303 */     $$0.process("glintStrength", this.glintStrength);
/* 1304 */     $$0.process("damageTiltStrength", this.damageTiltStrength);
/* 1305 */     $$0.process("highContrast", this.highContrast);
/* 1306 */     $$0.process("narratorHotkey", this.narratorHotkey);
/* 1307 */     $$0.process("gamma", this.gamma);
/*      */ 
/*      */     
/* 1310 */     $$0.process("renderDistance", this.renderDistance);
/* 1311 */     $$0.process("simulationDistance", this.simulationDistance);
/* 1312 */     $$0.process("entityDistanceScaling", this.entityDistanceScaling);
/* 1313 */     $$0.process("guiScale", this.guiScale);
/* 1314 */     $$0.process("particles", this.particles);
/* 1315 */     $$0.process("maxFps", this.framerateLimit);
/* 1316 */     $$0.process("graphicsMode", this.graphicsMode);
/* 1317 */     $$0.process("ao", this.ambientOcclusion);
/* 1318 */     $$0.process("prioritizeChunkUpdates", this.prioritizeChunkUpdates);
/* 1319 */     $$0.process("biomeBlendRadius", this.biomeBlendRadius);
/* 1320 */     $$0.process("renderClouds", this.cloudStatus);
/* 1321 */     Objects.requireNonNull(GSON); this.resourcePacks = $$0.<List<String>>process("resourcePacks", this.resourcePacks, Options::readListOfStrings, GSON::toJson);
/* 1322 */     Objects.requireNonNull(GSON); this.incompatibleResourcePacks = $$0.<List<String>>process("incompatibleResourcePacks", this.incompatibleResourcePacks, Options::readListOfStrings, GSON::toJson);
/* 1323 */     this.lastMpIp = $$0.process("lastServer", this.lastMpIp);
/* 1324 */     this.languageCode = $$0.process("lang", this.languageCode);
/* 1325 */     $$0.process("soundDevice", this.soundDevice);
/* 1326 */     $$0.process("chatVisibility", this.chatVisibility);
/* 1327 */     $$0.process("chatOpacity", this.chatOpacity);
/* 1328 */     $$0.process("chatLineSpacing", this.chatLineSpacing);
/* 1329 */     $$0.process("textBackgroundOpacity", this.textBackgroundOpacity);
/* 1330 */     $$0.process("backgroundForChatOnly", this.backgroundForChatOnly);
/*      */     
/* 1332 */     this.hideServerAddress = $$0.process("hideServerAddress", this.hideServerAddress);
/* 1333 */     this.advancedItemTooltips = $$0.process("advancedItemTooltips", this.advancedItemTooltips);
/* 1334 */     this.pauseOnLostFocus = $$0.process("pauseOnLostFocus", this.pauseOnLostFocus);
/* 1335 */     this.overrideWidth = $$0.process("overrideWidth", this.overrideWidth);
/* 1336 */     this.overrideHeight = $$0.process("overrideHeight", this.overrideHeight);
/* 1337 */     $$0.process("chatHeightFocused", this.chatHeightFocused);
/* 1338 */     $$0.process("chatDelay", this.chatDelay);
/* 1339 */     $$0.process("chatHeightUnfocused", this.chatHeightUnfocused);
/* 1340 */     $$0.process("chatScale", this.chatScale);
/* 1341 */     $$0.process("chatWidth", this.chatWidth);
/* 1342 */     $$0.process("notificationDisplayTime", this.notificationDisplayTime);
/* 1343 */     $$0.process("mipmapLevels", this.mipmapLevels);
/* 1344 */     this.useNativeTransport = $$0.process("useNativeTransport", this.useNativeTransport);
/* 1345 */     $$0.process("mainHand", this.mainHand);
/* 1346 */     $$0.process("attackIndicator", this.attackIndicator);
/* 1347 */     $$0.process("narrator", this.narrator);
/* 1348 */     this.tutorialStep = $$0.<TutorialSteps>process("tutorialStep", this.tutorialStep, TutorialSteps::getByName, TutorialSteps::getName);
/* 1349 */     $$0.process("mouseWheelSensitivity", this.mouseWheelSensitivity);
/* 1350 */     $$0.process("rawMouseInput", this.rawMouseInput);
/* 1351 */     this.glDebugVerbosity = $$0.process("glDebugVerbosity", this.glDebugVerbosity);
/* 1352 */     this.skipMultiplayerWarning = $$0.process("skipMultiplayerWarning", this.skipMultiplayerWarning);
/* 1353 */     this.skipRealms32bitWarning = $$0.process("skipRealms32bitWarning", this.skipRealms32bitWarning);
/* 1354 */     $$0.process("hideMatchedNames", this.hideMatchedNames);
/* 1355 */     this.joinedFirstServer = $$0.process("joinedFirstServer", this.joinedFirstServer);
/* 1356 */     this.hideBundleTutorial = $$0.process("hideBundleTutorial", this.hideBundleTutorial);
/* 1357 */     this.syncWrites = $$0.process("syncChunkWrites", this.syncWrites);
/* 1358 */     $$0.process("showAutosaveIndicator", this.showAutosaveIndicator);
/* 1359 */     $$0.process("allowServerListing", this.allowServerListing);
/* 1360 */     $$0.process("onlyShowSecureChat", this.onlyShowSecureChat);
/* 1361 */     $$0.process("panoramaScrollSpeed", this.panoramaSpeed);
/* 1362 */     $$0.process("telemetryOptInExtra", this.telemetryOptInExtra);
/* 1363 */     this.onboardAccessibility = $$0.process("onboardAccessibility", this.onboardAccessibility);
/*      */     
/* 1365 */     for (KeyMapping $$1 : this.keyMappings) {
/* 1366 */       String $$2 = $$1.saveString();
/* 1367 */       String $$3 = $$0.process("key_" + $$1.getName(), $$2);
/* 1368 */       if (!$$2.equals($$3)) {
/* 1369 */         $$1.setKey(InputConstants.getKey($$3));
/*      */       }
/*      */     } 
/*      */     
/* 1373 */     for (SoundSource $$4 : SoundSource.values()) {
/* 1374 */       $$0.process("soundCategory_" + $$4.getName(), this.soundSourceVolumes.get($$4));
/*      */     }
/*      */     
/* 1377 */     for (PlayerModelPart $$5 : PlayerModelPart.values()) {
/* 1378 */       boolean $$6 = this.modelParts.contains($$5);
/* 1379 */       boolean $$7 = $$0.process("modelPart_" + $$5.getId(), $$6);
/* 1380 */       if ($$7 != $$6) {
/* 1381 */         setModelPart($$5, $$7);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public void load() {
/*      */     try {
/* 1388 */       if (!this.optionsFile.exists()) {
/*      */         return;
/*      */       }
/*      */       
/* 1392 */       CompoundTag $$0 = new CompoundTag();
/* 1393 */       BufferedReader $$1 = Files.newReader(this.optionsFile, Charsets.UTF_8); 
/* 1394 */       try { $$1.lines().forEach($$1 -> {
/*      */               try {
/*      */                 final Iterator<String> options = OPTION_SPLITTER.split($$1).iterator();
/*      */                 $$0.putString($$2.next(), $$2.next());
/* 1398 */               } catch (Exception $$3) {
/*      */                 LOGGER.warn("Skipping bad option: {}", $$1);
/*      */               } 
/*      */             });
/* 1402 */         if ($$1 != null) $$1.close();  } catch (Throwable throwable) { if ($$1 != null)
/*      */           try { $$1.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }
/* 1404 */        final CompoundTag options = dataFix($$0);
/*      */       
/* 1406 */       if (!$$2.contains("graphicsMode") && $$2.contains("fancyGraphics"))
/*      */       {
/* 1408 */         if (isTrue($$2.getString("fancyGraphics"))) {
/* 1409 */           this.graphicsMode.set(GraphicsStatus.FANCY);
/*      */         } else {
/* 1411 */           this.graphicsMode.set(GraphicsStatus.FAST);
/*      */         } 
/*      */       }
/*      */       
/* 1415 */       processOptions(new FieldAccess() {
/*      */             @Nullable
/*      */             private String getValueOrNull(String $$0) {
/* 1418 */               return options.contains($$0) ? options.getString($$0) : null;
/*      */             }
/*      */ 
/*      */             
/*      */             public <T> void process(String $$0, OptionInstance<T> $$1) {
/* 1423 */               String $$2 = getValueOrNull($$0);
/* 1424 */               if ($$2 != null) {
/*      */                 
/* 1426 */                 JsonReader $$3 = new JsonReader(new StringReader($$2.isEmpty() ? "\"\"" : $$2));
/*      */                 
/* 1428 */                 JsonElement $$4 = JsonParser.parseReader($$3);
/* 1429 */                 DataResult<T> $$5 = $$1.codec().parse((DynamicOps)JsonOps.INSTANCE, $$4);
/* 1430 */                 $$5.error().ifPresent($$2 -> Options.LOGGER.error("Error parsing option value " + $$0 + " for option " + $$1 + ": " + $$2.message()));
/* 1431 */                 Objects.requireNonNull($$1); $$5.result().ifPresent($$1::set);
/*      */               } 
/*      */             }
/*      */ 
/*      */             
/*      */             public int process(String $$0, int $$1) {
/* 1437 */               String $$2 = getValueOrNull($$0);
/* 1438 */               if ($$2 != null) {
/*      */                 try {
/* 1440 */                   return Integer.parseInt($$2);
/* 1441 */                 } catch (NumberFormatException $$3) {
/* 1442 */                   Options.LOGGER.warn("Invalid integer value for option {} = {}", new Object[] { $$0, $$2, $$3 });
/*      */                 } 
/*      */               }
/* 1445 */               return $$1;
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean process(String $$0, boolean $$1) {
/* 1450 */               String $$2 = getValueOrNull($$0);
/* 1451 */               return ($$2 != null) ? Options.isTrue($$2) : $$1;
/*      */             }
/*      */ 
/*      */             
/*      */             public String process(String $$0, String $$1) {
/* 1456 */               return (String)MoreObjects.firstNonNull(getValueOrNull($$0), $$1);
/*      */             }
/*      */ 
/*      */             
/*      */             public float process(String $$0, float $$1) {
/* 1461 */               String $$2 = getValueOrNull($$0);
/* 1462 */               if ($$2 != null) {
/*      */                 
/* 1464 */                 if (Options.isTrue($$2)) {
/* 1465 */                   return 1.0F;
/*      */                 }
/* 1467 */                 if (Options.isFalse($$2)) {
/* 1468 */                   return 0.0F;
/*      */                 }
/*      */                 try {
/* 1471 */                   return Float.parseFloat($$2);
/* 1472 */                 } catch (NumberFormatException $$3) {
/* 1473 */                   Options.LOGGER.warn("Invalid floating point value for option {} = {}", new Object[] { $$0, $$2, $$3 });
/*      */                 } 
/*      */               } 
/* 1476 */               return $$1;
/*      */             }
/*      */ 
/*      */             
/*      */             public <T> T process(String $$0, T $$1, Function<String, T> $$2, Function<T, String> $$3) {
/* 1481 */               String $$4 = getValueOrNull($$0);
/* 1482 */               return ($$4 == null) ? $$1 : $$2.apply($$4);
/*      */             }
/*      */           });
/*      */       
/* 1486 */       if ($$2.contains("fullscreenResolution")) {
/* 1487 */         this.fullscreenVideoModeString = $$2.getString("fullscreenResolution");
/*      */       }
/*      */       
/* 1490 */       if (this.minecraft.getWindow() != null) {
/* 1491 */         this.minecraft.getWindow().setFramerateLimit(((Integer)this.framerateLimit.get()).intValue());
/*      */       }
/*      */       
/* 1494 */       KeyMapping.resetMapping();
/* 1495 */     } catch (Exception $$3) {
/* 1496 */       LOGGER.error("Failed to load options", $$3);
/*      */     } 
/*      */   }
/*      */   
/*      */   static boolean isTrue(String $$0) {
/* 1501 */     return "true".equals($$0);
/*      */   }
/*      */   
/*      */   static boolean isFalse(String $$0) {
/* 1505 */     return "false".equals($$0);
/*      */   }
/*      */   
/*      */   private CompoundTag dataFix(CompoundTag $$0) {
/* 1509 */     int $$1 = 0;
/*      */     try {
/* 1511 */       $$1 = Integer.parseInt($$0.getString("version"));
/* 1512 */     } catch (RuntimeException runtimeException) {}
/*      */ 
/*      */     
/* 1515 */     return DataFixTypes.OPTIONS.updateToCurrentVersion(this.minecraft.getFixerUpper(), $$0, $$1);
/*      */   }
/*      */   public void save() {
/*      */     
/* 1519 */     try { final PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.optionsFile), StandardCharsets.UTF_8)); 
/* 1520 */       try { $$0.println("version:" + SharedConstants.getCurrentVersion().getDataVersion().getVersion());
/*      */         
/* 1522 */         processOptions(new FieldAccess() {
/*      */               public void writePrefix(String $$0) {
/* 1524 */                 writer.print($$0);
/* 1525 */                 writer.print(':');
/*      */               }
/*      */ 
/*      */               
/*      */               public <T> void process(String $$0, OptionInstance<T> $$1) {
/* 1530 */                 DataResult<JsonElement> $$2 = $$1.codec().encodeStart((DynamicOps)JsonOps.INSTANCE, $$1.get());
/* 1531 */                 $$2.error().ifPresent($$1 -> Options.LOGGER.error("Error saving option " + $$0 + ": " + $$1));
/* 1532 */                 $$2.result().ifPresent($$2 -> {
/*      */                       writePrefix($$0);
/*      */                       $$1.println(Options.GSON.toJson($$2));
/*      */                     });
/*      */               }
/*      */ 
/*      */               
/*      */               public int process(String $$0, int $$1) {
/* 1540 */                 writePrefix($$0);
/* 1541 */                 writer.println($$1);
/* 1542 */                 return $$1;
/*      */               }
/*      */ 
/*      */               
/*      */               public boolean process(String $$0, boolean $$1) {
/* 1547 */                 writePrefix($$0);
/* 1548 */                 writer.println($$1);
/* 1549 */                 return $$1;
/*      */               }
/*      */ 
/*      */               
/*      */               public String process(String $$0, String $$1) {
/* 1554 */                 writePrefix($$0);
/* 1555 */                 writer.println($$1);
/* 1556 */                 return $$1;
/*      */               }
/*      */ 
/*      */               
/*      */               public float process(String $$0, float $$1) {
/* 1561 */                 writePrefix($$0);
/* 1562 */                 writer.println($$1);
/* 1563 */                 return $$1;
/*      */               }
/*      */ 
/*      */               
/*      */               public <T> T process(String $$0, T $$1, Function<String, T> $$2, Function<T, String> $$3) {
/* 1568 */                 writePrefix($$0);
/* 1569 */                 writer.println($$3.apply($$1));
/* 1570 */                 return $$1;
/*      */               }
/*      */             });
/*      */         
/* 1574 */         if (this.minecraft.getWindow().getPreferredFullscreenVideoMode().isPresent()) {
/* 1575 */           $$0.println("fullscreenResolution:" + ((VideoMode)this.minecraft.getWindow().getPreferredFullscreenVideoMode().get()).write());
/*      */         }
/* 1577 */         $$0.close(); } catch (Throwable throwable) { try { $$0.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (Exception $$1)
/* 1578 */     { LOGGER.error("Failed to save options", $$1); }
/*      */ 
/*      */     
/* 1581 */     broadcastOptions();
/*      */   }
/*      */   
/*      */   public ClientInformation buildPlayerInformation() {
/* 1585 */     int $$0 = 0;
/* 1586 */     for (PlayerModelPart $$1 : this.modelParts) {
/* 1587 */       $$0 |= $$1.getMask();
/*      */     }
/*      */     
/* 1590 */     return new ClientInformation(this.languageCode, ((Integer)this.renderDistance.get()).intValue(), this.chatVisibility.get(), ((Boolean)this.chatColors.get()).booleanValue(), $$0, this.mainHand.get(), this.minecraft.isTextFilteringEnabled(), ((Boolean)this.allowServerListing.get()).booleanValue());
/*      */   }
/*      */   
/*      */   public void broadcastOptions() {
/* 1594 */     if (this.minecraft.player != null) {
/* 1595 */       this.minecraft.player.connection.send((Packet)new ServerboundClientInformationPacket(buildPlayerInformation()));
/*      */     }
/*      */   }
/*      */   
/*      */   private void setModelPart(PlayerModelPart $$0, boolean $$1) {
/* 1600 */     if ($$1) {
/* 1601 */       this.modelParts.add($$0);
/*      */     } else {
/* 1603 */       this.modelParts.remove($$0);
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean isModelPartEnabled(PlayerModelPart $$0) {
/* 1608 */     return this.modelParts.contains($$0);
/*      */   }
/*      */   
/*      */   public void toggleModelPart(PlayerModelPart $$0, boolean $$1) {
/* 1612 */     setModelPart($$0, $$1);
/* 1613 */     broadcastOptions();
/*      */   }
/*      */   
/*      */   public CloudStatus getCloudsType() {
/* 1617 */     if (getEffectiveRenderDistance() >= 4) {
/* 1618 */       return this.cloudStatus.get();
/*      */     }
/* 1620 */     return CloudStatus.OFF;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean useNativeTransport() {
/* 1625 */     return this.useNativeTransport;
/*      */   }
/*      */   
/*      */   public void loadSelectedResourcePacks(PackRepository $$0) {
/* 1629 */     Set<String> $$1 = Sets.newLinkedHashSet();
/* 1630 */     for (Iterator<String> $$2 = this.resourcePacks.iterator(); $$2.hasNext(); ) {
/* 1631 */       String $$3 = $$2.next();
/* 1632 */       Pack $$4 = $$0.getPack($$3);
/*      */       
/* 1634 */       if ($$4 == null && !$$3.startsWith("file/"))
/*      */       {
/* 1636 */         $$4 = $$0.getPack("file/" + $$3);
/*      */       }
/*      */       
/* 1639 */       if ($$4 == null) {
/* 1640 */         LOGGER.warn("Removed resource pack {} from options because it doesn't seem to exist anymore", $$3);
/* 1641 */         $$2.remove(); continue;
/* 1642 */       }  if (!$$4.getCompatibility().isCompatible() && !this.incompatibleResourcePacks.contains($$3)) {
/* 1643 */         LOGGER.warn("Removed resource pack {} from options because it is no longer compatible", $$3);
/* 1644 */         $$2.remove(); continue;
/* 1645 */       }  if ($$4.getCompatibility().isCompatible() && this.incompatibleResourcePacks.contains($$3)) {
/* 1646 */         LOGGER.info("Removed resource pack {} from incompatibility list because it's now compatible", $$3);
/* 1647 */         this.incompatibleResourcePacks.remove($$3); continue;
/*      */       } 
/* 1649 */       $$1.add($$4.getId());
/*      */     } 
/*      */     
/* 1652 */     $$0.setSelected($$1);
/*      */   }
/*      */   
/*      */   public CameraType getCameraType() {
/* 1656 */     return this.cameraType;
/*      */   }
/*      */   
/*      */   public void setCameraType(CameraType $$0) {
/* 1660 */     this.cameraType = $$0;
/*      */   }
/*      */   
/*      */   private static List<String> readListOfStrings(String $$0) {
/* 1664 */     List<String> $$1 = (List<String>)GsonHelper.fromNullableJson(GSON, $$0, LIST_OF_STRINGS_TYPE);
/* 1665 */     return ($$1 != null) ? $$1 : Lists.newArrayList();
/*      */   }
/*      */   
/*      */   public File getFile() {
/* 1669 */     return this.optionsFile;
/*      */   }
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
/*      */   public String dumpOptionsForReport() {
/* 1707 */     Stream<Pair<String, Object>> $$0 = Stream.<Pair<String, Object>>builder().add(Pair.of("ao", this.ambientOcclusion.get())).add(Pair.of("biomeBlendRadius", this.biomeBlendRadius.get())).add(Pair.of("enableVsync", this.enableVsync.get())).add(Pair.of("entityDistanceScaling", this.entityDistanceScaling.get())).add(Pair.of("entityShadows", this.entityShadows.get())).add(Pair.of("forceUnicodeFont", this.forceUnicodeFont.get())).add(Pair.of("fov", this.fov.get())).add(Pair.of("fovEffectScale", this.fovEffectScale.get())).add(Pair.of("darknessEffectScale", this.darknessEffectScale.get())).add(Pair.of("glintSpeed", this.glintSpeed.get())).add(Pair.of("glintStrength", this.glintStrength.get())).add(Pair.of("prioritizeChunkUpdates", this.prioritizeChunkUpdates.get())).add(Pair.of("fullscreen", this.fullscreen.get())).add(Pair.of("fullscreenResolution", String.valueOf(this.fullscreenVideoModeString))).add(Pair.of("gamma", this.gamma.get())).add(Pair.of("glDebugVerbosity", Integer.valueOf(this.glDebugVerbosity))).add(Pair.of("graphicsMode", this.graphicsMode.get())).add(Pair.of("guiScale", this.guiScale.get())).add(Pair.of("maxFps", this.framerateLimit.get())).add(Pair.of("mipmapLevels", this.mipmapLevels.get())).add(Pair.of("narrator", this.narrator.get())).add(Pair.of("overrideHeight", Integer.valueOf(this.overrideHeight))).add(Pair.of("overrideWidth", Integer.valueOf(this.overrideWidth))).add(Pair.of("particles", this.particles.get())).add(Pair.of("reducedDebugInfo", this.reducedDebugInfo.get())).add(Pair.of("renderClouds", this.cloudStatus.get())).add(Pair.of("renderDistance", this.renderDistance.get())).add(Pair.of("simulationDistance", this.simulationDistance.get())).add(Pair.of("resourcePacks", this.resourcePacks)).add(Pair.of("screenEffectScale", this.screenEffectScale.get())).add(Pair.of("syncChunkWrites", Boolean.valueOf(this.syncWrites))).add(Pair.of("useNativeTransport", Boolean.valueOf(this.useNativeTransport))).add(Pair.of("soundDevice", this.soundDevice.get())).build();
/*      */     
/* 1709 */     return $$0
/* 1710 */       .<CharSequence>map($$0 -> (String)$$0.getFirst() + ": " + (String)$$0.getFirst())
/* 1711 */       .collect(Collectors.joining(System.lineSeparator()));
/*      */   }
/*      */   
/*      */   public void setServerRenderDistance(int $$0) {
/* 1715 */     this.serverRenderDistance = $$0;
/*      */   }
/*      */   
/*      */   public int getEffectiveRenderDistance() {
/* 1719 */     return (this.serverRenderDistance > 0) ? Math.min(((Integer)this.renderDistance.get()).intValue(), this.serverRenderDistance) : ((Integer)this.renderDistance.get()).intValue();
/*      */   }
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
/*      */ 
/*      */ 
/*      */   
/*      */   private static Component pixelValueLabel(Component $$0, int $$1) {
/* 1737 */     return (Component)Component.translatable("options.pixel_value", new Object[] { $$0, Integer.valueOf($$1) });
/*      */   }
/*      */   
/*      */   private static Component percentValueLabel(Component $$0, double $$1) {
/* 1741 */     return (Component)Component.translatable("options.percent_value", new Object[] { $$0, Integer.valueOf((int)($$1 * 100.0D)) });
/*      */   }
/*      */   
/*      */   public static Component genericValueLabel(Component $$0, Component $$1) {
/* 1745 */     return (Component)Component.translatable("options.generic_value", new Object[] { $$0, $$1 });
/*      */   }
/*      */   
/*      */   public static Component genericValueLabel(Component $$0, int $$1) {
/* 1749 */     return genericValueLabel($$0, (Component)Component.literal(Integer.toString($$1)));
/*      */   }
/*      */   
/*      */   private static interface FieldAccess {
/*      */     <T> void process(String param1String, OptionInstance<T> param1OptionInstance);
/*      */     
/*      */     int process(String param1String, int param1Int);
/*      */     
/*      */     boolean process(String param1String, boolean param1Boolean);
/*      */     
/*      */     String process(String param1String1, String param1String2);
/*      */     
/*      */     float process(String param1String, float param1Float);
/*      */     
/*      */     <T> T process(String param1String, T param1T, Function<String, T> param1Function, Function<T, String> param1Function1);
/*      */   }
/*      */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\Options.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */