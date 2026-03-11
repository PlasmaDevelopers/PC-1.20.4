/*     */ package net.minecraft.client.gui.screens.worldselection;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.mojang.blaze3d.platform.NativeImage;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.LinkOption;
/*     */ import java.nio.file.NoSuchFileException;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.attribute.BasicFileAttributes;
/*     */ import java.time.ZoneId;
/*     */ import java.time.format.DateTimeFormatter;
/*     */ import java.time.format.FormatStyle;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.CompletionException;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.CrashReport;
/*     */ import net.minecraft.SharedConstants;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.AbstractSelectionList;
/*     */ import net.minecraft.client.gui.components.ObjectSelectionList;
/*     */ import net.minecraft.client.gui.components.toasts.SystemToast;
/*     */ import net.minecraft.client.gui.narration.NarrationElementOutput;
/*     */ import net.minecraft.client.gui.navigation.CommonInputs;
/*     */ import net.minecraft.client.gui.screens.AlertScreen;
/*     */ import net.minecraft.client.gui.screens.ConfirmScreen;
/*     */ import net.minecraft.client.gui.screens.ErrorScreen;
/*     */ import net.minecraft.client.gui.screens.FaviconTexture;
/*     */ import net.minecraft.client.gui.screens.GenericDirtMessageScreen;
/*     */ import net.minecraft.client.gui.screens.LoadingDotsText;
/*     */ import net.minecraft.client.gui.screens.NoticeWithLinkScreen;
/*     */ import net.minecraft.client.gui.screens.ProgressScreen;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.client.resources.language.I18n;
/*     */ import net.minecraft.client.resources.sounds.SimpleSoundInstance;
/*     */ import net.minecraft.client.resources.sounds.SoundInstance;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.world.level.LevelSettings;
/*     */ import net.minecraft.world.level.storage.LevelResource;
/*     */ import net.minecraft.world.level.storage.LevelStorageException;
/*     */ import net.minecraft.world.level.storage.LevelStorageSource;
/*     */ import net.minecraft.world.level.storage.LevelSummary;
/*     */ import net.minecraft.world.level.validation.ContentValidationException;
/*     */ import net.minecraft.world.level.validation.ForbiddenSymlinkInfo;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class WorldSelectionList extends ObjectSelectionList<WorldSelectionList.Entry> {
/*  66 */   public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withZone(ZoneId.systemDefault());
/*     */   
/*  68 */   static final ResourceLocation ERROR_HIGHLIGHTED_SPRITE = new ResourceLocation("world_list/error_highlighted");
/*  69 */   static final ResourceLocation ERROR_SPRITE = new ResourceLocation("world_list/error");
/*  70 */   static final ResourceLocation MARKED_JOIN_HIGHLIGHTED_SPRITE = new ResourceLocation("world_list/marked_join_highlighted");
/*  71 */   static final ResourceLocation MARKED_JOIN_SPRITE = new ResourceLocation("world_list/marked_join");
/*  72 */   static final ResourceLocation WARNING_HIGHLIGHTED_SPRITE = new ResourceLocation("world_list/warning_highlighted");
/*  73 */   static final ResourceLocation WARNING_SPRITE = new ResourceLocation("world_list/warning");
/*  74 */   static final ResourceLocation JOIN_HIGHLIGHTED_SPRITE = new ResourceLocation("world_list/join_highlighted");
/*  75 */   static final ResourceLocation JOIN_SPRITE = new ResourceLocation("world_list/join");
/*  76 */   static final Logger LOGGER = LogUtils.getLogger();
/*  77 */   private static final ResourceLocation ICON_MISSING = new ResourceLocation("textures/misc/unknown_server.png");
/*     */   
/*  79 */   static final Component FROM_NEWER_TOOLTIP_1 = (Component)Component.translatable("selectWorld.tooltip.fromNewerVersion1").withStyle(ChatFormatting.RED);
/*  80 */   static final Component FROM_NEWER_TOOLTIP_2 = (Component)Component.translatable("selectWorld.tooltip.fromNewerVersion2").withStyle(ChatFormatting.RED);
/*  81 */   static final Component SNAPSHOT_TOOLTIP_1 = (Component)Component.translatable("selectWorld.tooltip.snapshot1").withStyle(ChatFormatting.GOLD);
/*  82 */   static final Component SNAPSHOT_TOOLTIP_2 = (Component)Component.translatable("selectWorld.tooltip.snapshot2").withStyle(ChatFormatting.GOLD);
/*  83 */   static final Component WORLD_LOCKED_TOOLTIP = (Component)Component.translatable("selectWorld.locked").withStyle(ChatFormatting.RED);
/*  84 */   static final Component WORLD_REQUIRES_CONVERSION = (Component)Component.translatable("selectWorld.conversion.tooltip").withStyle(ChatFormatting.RED);
/*  85 */   static final Component INCOMPATIBLE_VERSION_TOOLTIP = (Component)Component.translatable("selectWorld.incompatible.tooltip").withStyle(ChatFormatting.RED);
/*  86 */   static final Component WORLD_EXPERIMENTAL = (Component)Component.translatable("selectWorld.experimental");
/*     */   
/*     */   private final SelectWorldScreen screen;
/*     */   
/*     */   private CompletableFuture<List<LevelSummary>> pendingLevels;
/*     */   
/*     */   @Nullable
/*     */   private List<LevelSummary> currentlyDisplayedLevels;
/*     */   private String filter;
/*     */   private final LoadingHeader loadingHeader;
/*     */   
/*     */   public WorldSelectionList(SelectWorldScreen $$0, Minecraft $$1, int $$2, int $$3, int $$4, int $$5, String $$6, @Nullable WorldSelectionList $$7) {
/*  98 */     super($$1, $$2, $$3, $$4, $$5);
/*  99 */     this.screen = $$0;
/*     */     
/* 101 */     this.loadingHeader = new LoadingHeader($$1);
/*     */     
/* 103 */     this.filter = $$6;
/* 104 */     if ($$7 != null) {
/* 105 */       this.pendingLevels = $$7.pendingLevels;
/*     */     } else {
/* 107 */       this.pendingLevels = loadLevels();
/*     */     } 
/* 109 */     handleNewLevels(pollLevelsIgnoreErrors());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void clearEntries() {
/* 114 */     children().forEach(Entry::close);
/* 115 */     super.clearEntries();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private List<LevelSummary> pollLevelsIgnoreErrors() {
/*     */     try {
/* 121 */       return this.pendingLevels.getNow(null);
/* 122 */     } catch (CompletionException|java.util.concurrent.CancellationException $$0) {
/* 123 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void reloadWorldList() {
/* 129 */     this.pendingLevels = loadLevels();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean keyPressed(int $$0, int $$1, int $$2) {
/* 134 */     if (CommonInputs.selected($$0)) {
/* 135 */       Optional<WorldListEntry> $$3 = getSelectedOpt();
/* 136 */       if ($$3.isPresent()) {
/* 137 */         if (((WorldListEntry)$$3.get()).canJoin()) {
/* 138 */           this.minecraft.getSoundManager().play((SoundInstance)SimpleSoundInstance.forUI((Holder)SoundEvents.UI_BUTTON_CLICK, 1.0F));
/* 139 */           ((WorldListEntry)$$3.get()).joinWorld();
/*     */         } 
/* 141 */         return true;
/*     */       } 
/*     */     } 
/* 144 */     return super.keyPressed($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderWidget(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 149 */     List<LevelSummary> $$4 = pollLevelsIgnoreErrors();
/*     */     
/* 151 */     if ($$4 != this.currentlyDisplayedLevels) {
/* 152 */       handleNewLevels($$4);
/*     */     }
/* 154 */     super.renderWidget($$0, $$1, $$2, $$3);
/*     */   }
/*     */   
/*     */   private void handleNewLevels(@Nullable List<LevelSummary> $$0) {
/* 158 */     if ($$0 == null) {
/* 159 */       fillLoadingLevels();
/*     */     } else {
/* 161 */       fillLevels(this.filter, $$0);
/*     */     } 
/* 163 */     this.currentlyDisplayedLevels = $$0;
/*     */   }
/*     */   
/*     */   public void updateFilter(String $$0) {
/* 167 */     if (this.currentlyDisplayedLevels != null && !$$0.equals(this.filter)) {
/* 168 */       fillLevels($$0, this.currentlyDisplayedLevels);
/*     */     }
/* 170 */     this.filter = $$0;
/*     */   }
/*     */   
/*     */   private CompletableFuture<List<LevelSummary>> loadLevels() {
/*     */     LevelStorageSource.LevelCandidates $$0;
/*     */     try {
/* 176 */       $$0 = this.minecraft.getLevelSource().findLevelCandidates();
/* 177 */     } catch (LevelStorageException $$1) {
/* 178 */       LOGGER.error("Couldn't load level list", (Throwable)$$1);
/* 179 */       handleLevelLoadFailure($$1.getMessageComponent());
/* 180 */       return CompletableFuture.completedFuture(List.of());
/*     */     } 
/*     */     
/* 183 */     if ($$0.isEmpty()) {
/* 184 */       CreateWorldScreen.openFresh(this.minecraft, null);
/* 185 */       return CompletableFuture.completedFuture(List.of());
/*     */     } 
/*     */     
/* 188 */     return this.minecraft.getLevelSource().loadLevelSummaries($$0)
/* 189 */       .exceptionally($$0 -> {
/*     */           this.minecraft.delayCrash(CrashReport.forThrowable($$0, "Couldn't load level list"));
/*     */           return List.of();
/*     */         });
/*     */   }
/*     */   
/*     */   private void fillLevels(String $$0, List<LevelSummary> $$1) {
/* 196 */     clearEntries();
/* 197 */     $$0 = $$0.toLowerCase(Locale.ROOT);
/* 198 */     for (LevelSummary $$2 : $$1) {
/* 199 */       if (filterAccepts($$0, $$2)) {
/* 200 */         addEntry((AbstractSelectionList.Entry)new WorldListEntry(this, $$2));
/*     */       }
/*     */     } 
/*     */     
/* 204 */     notifyListUpdated();
/*     */   }
/*     */   
/*     */   private boolean filterAccepts(String $$0, LevelSummary $$1) {
/* 208 */     return ($$1.getLevelName().toLowerCase(Locale.ROOT).contains($$0) || $$1.getLevelId().toLowerCase(Locale.ROOT).contains($$0));
/*     */   }
/*     */   
/*     */   private void fillLoadingLevels() {
/* 212 */     clearEntries();
/* 213 */     addEntry((AbstractSelectionList.Entry)this.loadingHeader);
/* 214 */     notifyListUpdated();
/*     */   }
/*     */   
/*     */   private void notifyListUpdated() {
/* 218 */     setScrollAmount(getScrollAmount());
/* 219 */     this.screen.triggerImmediateNarration(true);
/*     */   }
/*     */   
/*     */   private void handleLevelLoadFailure(Component $$0) {
/* 223 */     this.minecraft.setScreen((Screen)new ErrorScreen((Component)Component.translatable("selectWorld.unable_to_load"), $$0));
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getScrollbarPosition() {
/* 228 */     return super.getScrollbarPosition() + 20;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRowWidth() {
/* 233 */     return super.getRowWidth() + 50;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSelected(@Nullable Entry $$0) {
/* 238 */     super.setSelected((AbstractSelectionList.Entry)$$0);
/* 239 */     WorldListEntry $$1 = (WorldListEntry)$$0; this.screen.updateButtonStatus(($$0 instanceof WorldListEntry) ? $$1.summary : null);
/*     */   }
/*     */   
/*     */   public Optional<WorldListEntry> getSelectedOpt() {
/* 243 */     Entry $$0 = (Entry)getSelected();
/* 244 */     if ($$0 instanceof WorldListEntry) { WorldListEntry $$1 = (WorldListEntry)$$0;
/* 245 */       return Optional.of($$1); }
/*     */     
/* 247 */     return Optional.empty();
/*     */   }
/*     */   
/*     */   public SelectWorldScreen getScreen() {
/* 251 */     return this.screen;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateWidgetNarration(NarrationElementOutput $$0) {
/* 256 */     if (children().contains(this.loadingHeader)) {
/* 257 */       this.loadingHeader.updateNarration($$0);
/*     */       return;
/*     */     } 
/* 260 */     super.updateWidgetNarration($$0);
/*     */   }
/*     */   
/*     */   public static abstract class Entry
/*     */     extends ObjectSelectionList.Entry<Entry>
/*     */     implements AutoCloseable {
/*     */     public void close() {}
/*     */   }
/*     */   
/*     */   public final class WorldListEntry
/*     */     extends Entry implements AutoCloseable {
/*     */     private static final int ICON_WIDTH = 32;
/*     */     private static final int ICON_HEIGHT = 32;
/*     */     private final Minecraft minecraft;
/*     */     private final SelectWorldScreen screen;
/*     */     final LevelSummary summary;
/*     */     private final FaviconTexture icon;
/*     */     @Nullable
/*     */     private Path iconFile;
/*     */     private long lastClickTime;
/*     */     
/*     */     public WorldListEntry(WorldSelectionList $$1, LevelSummary $$2) {
/* 282 */       this.minecraft = $$1.minecraft;
/* 283 */       this.screen = $$1.getScreen();
/* 284 */       this.summary = $$2;
/* 285 */       this.icon = FaviconTexture.forWorld(this.minecraft.getTextureManager(), $$2.getLevelId());
/* 286 */       this.iconFile = $$2.getIcon();
/*     */       
/* 288 */       validateIconFile();
/*     */       
/* 290 */       loadIcon();
/*     */     }
/*     */     
/*     */     private void validateIconFile() {
/* 294 */       if (this.iconFile == null) {
/*     */         return;
/*     */       }
/*     */       try {
/* 298 */         BasicFileAttributes $$0 = Files.readAttributes(this.iconFile, BasicFileAttributes.class, new LinkOption[] { LinkOption.NOFOLLOW_LINKS });
/* 299 */         if ($$0.isSymbolicLink()) {
/* 300 */           List<ForbiddenSymlinkInfo> $$1 = this.minecraft.directoryValidator().validateSymlink(this.iconFile);
/* 301 */           if (!$$1.isEmpty()) {
/* 302 */             WorldSelectionList.LOGGER.warn("{}", ContentValidationException.getMessage(this.iconFile, $$1));
/* 303 */             this.iconFile = null;
/*     */           } else {
/* 305 */             $$0 = Files.readAttributes(this.iconFile, BasicFileAttributes.class, new LinkOption[0]);
/*     */           } 
/*     */         } 
/* 308 */         if (!$$0.isRegularFile()) {
/* 309 */           this.iconFile = null;
/*     */         }
/* 311 */       } catch (NoSuchFileException $$2) {
/* 312 */         this.iconFile = null;
/* 313 */       } catch (IOException $$3) {
/* 314 */         WorldSelectionList.LOGGER.error("could not validate symlink", $$3);
/* 315 */         this.iconFile = null;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Component getNarration() {
/* 321 */       MutableComponent mutableComponent = Component.translatable("narrator.select.world_info", new Object[] { this.summary
/* 322 */             .getLevelName(), 
/* 323 */             Component.translationArg(new Date(this.summary.getLastPlayed())), this.summary
/* 324 */             .getInfo() });
/*     */       
/* 326 */       if (this.summary.isLocked()) {
/* 327 */         mutableComponent = CommonComponents.joinForNarration(new Component[] { (Component)mutableComponent, WorldSelectionList.WORLD_LOCKED_TOOLTIP });
/*     */       }
/* 329 */       if (this.summary.isExperimental()) {
/* 330 */         mutableComponent = CommonComponents.joinForNarration(new Component[] { (Component)mutableComponent, WorldSelectionList.WORLD_EXPERIMENTAL });
/*     */       }
/* 332 */       return (Component)Component.translatable("narrator.select", new Object[] { mutableComponent });
/*     */     }
/*     */ 
/*     */     
/*     */     public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/* 337 */       String $$10 = this.summary.getLevelName();
/* 338 */       String $$11 = this.summary.getLevelId();
/* 339 */       long $$12 = this.summary.getLastPlayed();
/* 340 */       if ($$12 != -1L) {
/* 341 */         $$11 = $$11 + " (" + $$11 + ")";
/*     */       }
/*     */       
/* 344 */       if (StringUtils.isEmpty($$10)) {
/* 345 */         $$10 = I18n.get("selectWorld.world", new Object[0]) + " " + I18n.get("selectWorld.world", new Object[0]);
/*     */       }
/*     */       
/* 348 */       Component $$13 = this.summary.getInfo();
/*     */       
/* 350 */       $$0.drawString(this.minecraft.font, $$10, $$3 + 32 + 3, $$2 + 1, 16777215, false);
/* 351 */       Objects.requireNonNull(this.minecraft.font); $$0.drawString(this.minecraft.font, $$11, $$3 + 32 + 3, $$2 + 9 + 3, -8355712, false);
/* 352 */       Objects.requireNonNull(this.minecraft.font); Objects.requireNonNull(this.minecraft.font); $$0.drawString(this.minecraft.font, $$13, $$3 + 32 + 3, $$2 + 9 + 9 + 3, -8355712, false);
/*     */       
/* 354 */       RenderSystem.enableBlend();
/* 355 */       $$0.blit(this.icon.textureLocation(), $$3, $$2, 0.0F, 0.0F, 32, 32, 32, 32);
/* 356 */       RenderSystem.disableBlend();
/*     */       
/* 358 */       if (((Boolean)this.minecraft.options.touchscreen().get()).booleanValue() || $$8) {
/* 359 */         $$0.fill($$3, $$2, $$3 + 32, $$2 + 32, -1601138544);
/* 360 */         int $$14 = $$6 - $$3;
/*     */         
/* 362 */         boolean $$15 = ($$14 < 32);
/* 363 */         ResourceLocation $$16 = $$15 ? WorldSelectionList.JOIN_HIGHLIGHTED_SPRITE : WorldSelectionList.JOIN_SPRITE;
/* 364 */         ResourceLocation $$17 = $$15 ? WorldSelectionList.WARNING_HIGHLIGHTED_SPRITE : WorldSelectionList.WARNING_SPRITE;
/* 365 */         ResourceLocation $$18 = $$15 ? WorldSelectionList.ERROR_HIGHLIGHTED_SPRITE : WorldSelectionList.ERROR_SPRITE;
/* 366 */         ResourceLocation $$19 = $$15 ? WorldSelectionList.MARKED_JOIN_HIGHLIGHTED_SPRITE : WorldSelectionList.MARKED_JOIN_SPRITE;
/* 367 */         if (this.summary instanceof LevelSummary.SymlinkLevelSummary || this.summary instanceof LevelSummary.CorruptedLevelSummary) {
/* 368 */           $$0.blitSprite($$18, $$3, $$2, 32, 32);
/* 369 */           $$0.blitSprite($$19, $$3, $$2, 32, 32);
/*     */           
/*     */           return;
/*     */         } 
/* 373 */         if (this.summary.isLocked()) {
/* 374 */           $$0.blitSprite($$18, $$3, $$2, 32, 32);
/* 375 */           if ($$15) {
/* 376 */             this.screen.setTooltipForNextRenderPass(this.minecraft.font.split((FormattedText)WorldSelectionList.WORLD_LOCKED_TOOLTIP, 175));
/*     */           }
/* 378 */         } else if (this.summary.requiresManualConversion()) {
/* 379 */           $$0.blitSprite($$18, $$3, $$2, 32, 32);
/* 380 */           if ($$15) {
/* 381 */             this.screen.setTooltipForNextRenderPass(this.minecraft.font.split((FormattedText)WorldSelectionList.WORLD_REQUIRES_CONVERSION, 175));
/*     */           }
/* 383 */         } else if (!this.summary.isCompatible()) {
/* 384 */           $$0.blitSprite($$18, $$3, $$2, 32, 32);
/* 385 */           if ($$15) {
/* 386 */             this.screen.setTooltipForNextRenderPass(this.minecraft.font.split((FormattedText)WorldSelectionList.INCOMPATIBLE_VERSION_TOOLTIP, 175));
/*     */           }
/* 388 */         } else if (this.summary.shouldBackup()) {
/* 389 */           $$0.blitSprite($$19, $$3, $$2, 32, 32);
/* 390 */           if (this.summary.isDowngrade()) {
/* 391 */             $$0.blitSprite($$18, $$3, $$2, 32, 32);
/* 392 */             if ($$15) {
/* 393 */               this.screen.setTooltipForNextRenderPass((List)ImmutableList.of(WorldSelectionList.FROM_NEWER_TOOLTIP_1.getVisualOrderText(), WorldSelectionList.FROM_NEWER_TOOLTIP_2.getVisualOrderText()));
/*     */             }
/* 395 */           } else if (!SharedConstants.getCurrentVersion().isStable()) {
/* 396 */             $$0.blitSprite($$17, $$3, $$2, 32, 32);
/* 397 */             if ($$15) {
/* 398 */               this.screen.setTooltipForNextRenderPass((List)ImmutableList.of(WorldSelectionList.SNAPSHOT_TOOLTIP_1.getVisualOrderText(), WorldSelectionList.SNAPSHOT_TOOLTIP_2.getVisualOrderText()));
/*     */             }
/*     */           } 
/*     */         } else {
/* 402 */           $$0.blitSprite($$16, $$3, $$2, 32, 32);
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean mouseClicked(double $$0, double $$1, int $$2) {
/* 409 */       if (!this.summary.primaryActionActive()) {
/* 410 */         return true;
/*     */       }
/* 412 */       WorldSelectionList.this.setSelected(this);
/*     */       
/* 414 */       if ($$0 - WorldSelectionList.this.getRowLeft() <= 32.0D || Util.getMillis() - this.lastClickTime < 250L) {
/* 415 */         if (canJoin()) {
/* 416 */           this.minecraft.getSoundManager().play((SoundInstance)SimpleSoundInstance.forUI((Holder)SoundEvents.UI_BUTTON_CLICK, 1.0F));
/* 417 */           joinWorld();
/*     */         } 
/* 419 */         return true;
/*     */       } 
/*     */       
/* 422 */       this.lastClickTime = Util.getMillis();
/* 423 */       return true;
/*     */     }
/*     */     
/*     */     public boolean canJoin() {
/* 427 */       return this.summary.primaryActionActive();
/*     */     }
/*     */     
/*     */     public void joinWorld() {
/* 431 */       if (!this.summary.primaryActionActive()) {
/*     */         return;
/*     */       }
/*     */       
/* 435 */       if (this.summary instanceof LevelSummary.SymlinkLevelSummary) {
/* 436 */         this.minecraft.setScreen(NoticeWithLinkScreen.createWorldSymlinkWarningScreen(() -> this.minecraft.setScreen(this.screen)));
/*     */         
/*     */         return;
/*     */       } 
/* 440 */       this.minecraft.createWorldOpenFlows().checkForBackupAndLoad(this.summary.getLevelId(), () -> {
/*     */             WorldSelectionList.this.reloadWorldList();
/*     */             this.minecraft.setScreen(this.screen);
/*     */           });
/*     */     }
/*     */     
/*     */     public void deleteWorld() {
/* 447 */       this.minecraft.setScreen((Screen)new ConfirmScreen($$0 -> {
/*     */               if ($$0) {
/*     */                 this.minecraft.setScreen((Screen)new ProgressScreen(true));
/*     */                 
/*     */                 doDeleteWorld();
/*     */               } 
/*     */               
/*     */               this.minecraft.setScreen(this.screen);
/* 455 */             }(Component)Component.translatable("selectWorld.deleteQuestion"), 
/* 456 */             (Component)Component.translatable("selectWorld.deleteWarning", new Object[] { this.summary.getLevelName()
/* 457 */               }), (Component)Component.translatable("selectWorld.deleteButton"), CommonComponents.GUI_CANCEL));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void doDeleteWorld() {
/* 463 */       LevelStorageSource $$0 = this.minecraft.getLevelSource();
/* 464 */       String $$1 = this.summary.getLevelId();
/*     */       
/* 466 */       try { LevelStorageSource.LevelStorageAccess $$2 = $$0.createAccess($$1); 
/* 467 */         try { $$2.deleteLevel();
/* 468 */           if ($$2 != null) $$2.close();  } catch (Throwable throwable) { if ($$2 != null) try { $$2.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException $$3)
/* 469 */       { SystemToast.onWorldDeleteFailure(this.minecraft, $$1);
/* 470 */         WorldSelectionList.LOGGER.error("Failed to delete world {}", $$1, $$3); }
/*     */       
/* 472 */       WorldSelectionList.this.reloadWorldList();
/*     */     } public void editWorld() {
/*     */       LevelStorageSource.LevelStorageAccess $$1;
/*     */       EditWorldScreen $$5;
/* 476 */       queueLoadScreen();
/* 477 */       String $$0 = this.summary.getLevelId();
/*     */       
/*     */       try {
/* 480 */         $$1 = this.minecraft.getLevelSource().validateAndCreateAccess($$0);
/* 481 */       } catch (IOException $$2) {
/* 482 */         SystemToast.onWorldAccessFailure(this.minecraft, $$0);
/* 483 */         WorldSelectionList.LOGGER.error("Failed to access level {}", $$0, $$2);
/* 484 */         WorldSelectionList.this.reloadWorldList();
/*     */         return;
/* 486 */       } catch (ContentValidationException $$3) {
/* 487 */         WorldSelectionList.LOGGER.warn("{}", $$3.getMessage());
/* 488 */         this.minecraft.setScreen(NoticeWithLinkScreen.createWorldSymlinkWarningScreen(() -> this.minecraft.setScreen(this.screen)));
/*     */         
/*     */         return;
/*     */       } 
/*     */       try {
/* 493 */         $$5 = EditWorldScreen.create(this.minecraft, $$1, $$1 -> {
/*     */               $$0.safeClose();
/*     */               
/*     */               if ($$1) {
/*     */                 WorldSelectionList.this.reloadWorldList();
/*     */               }
/*     */               
/*     */               this.minecraft.setScreen(this.screen);
/*     */             });
/* 502 */       } catch (IOException|net.minecraft.nbt.NbtException|net.minecraft.nbt.ReportedNbtException $$6) {
/* 503 */         $$1.safeClose();
/* 504 */         SystemToast.onWorldAccessFailure(this.minecraft, $$0);
/* 505 */         WorldSelectionList.LOGGER.error("Failed to load world data {}", $$0, $$6);
/* 506 */         WorldSelectionList.this.reloadWorldList();
/*     */         return;
/*     */       } 
/* 509 */       this.minecraft.setScreen($$5);
/*     */     }
/*     */     
/*     */     public void recreateWorld() {
/* 513 */       queueLoadScreen();
/*     */       
/* 515 */       try { LevelStorageSource.LevelStorageAccess $$0 = this.minecraft.getLevelSource().validateAndCreateAccess(this.summary.getLevelId());
/*     */         
/* 517 */         try { Pair<LevelSettings, WorldCreationContext> $$1 = this.minecraft.createWorldOpenFlows().recreateWorldData($$0);
/* 518 */           LevelSettings $$2 = (LevelSettings)$$1.getFirst();
/* 519 */           WorldCreationContext $$3 = (WorldCreationContext)$$1.getSecond();
/* 520 */           Path $$4 = CreateWorldScreen.createTempDataPackDirFromExistingWorld($$0.getLevelPath(LevelResource.DATAPACK_DIR), this.minecraft);
/*     */           
/* 522 */           if ($$3.options().isOldCustomizedWorld()) {
/* 523 */             this.minecraft.setScreen((Screen)new ConfirmScreen($$3 -> this.minecraft.setScreen($$3 ? CreateWorldScreen.createFromExisting(this.minecraft, this.screen, $$0, $$1, $$2) : this.screen), 
/*     */                   
/* 525 */                   (Component)Component.translatable("selectWorld.recreate.customized.title"), 
/* 526 */                   (Component)Component.translatable("selectWorld.recreate.customized.text"), CommonComponents.GUI_PROCEED, CommonComponents.GUI_CANCEL));
/*     */           
/*     */           }
/*     */           else {
/*     */             
/* 531 */             this.minecraft.setScreen(CreateWorldScreen.createFromExisting(this.minecraft, this.screen, $$2, $$3, $$4));
/*     */           } 
/* 533 */           if ($$0 != null) $$0.close();  } catch (Throwable throwable) { if ($$0 != null) try { $$0.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (ContentValidationException $$5)
/* 534 */       { WorldSelectionList.LOGGER.warn("{}", $$5.getMessage());
/* 535 */         this.minecraft.setScreen(NoticeWithLinkScreen.createWorldSymlinkWarningScreen(() -> this.minecraft.setScreen(this.screen))); }
/* 536 */       catch (Exception $$6)
/* 537 */       { WorldSelectionList.LOGGER.error("Unable to recreate world", $$6);
/* 538 */         this.minecraft.setScreen((Screen)new AlertScreen(() -> this.minecraft.setScreen(this.screen), 
/*     */               
/* 540 */               (Component)Component.translatable("selectWorld.recreate.error.title"), 
/* 541 */               (Component)Component.translatable("selectWorld.recreate.error.text"))); }
/*     */     
/*     */     }
/*     */ 
/*     */     
/*     */     private void queueLoadScreen() {
/* 547 */       this.minecraft.forceSetScreen((Screen)new GenericDirtMessageScreen((Component)Component.translatable("selectWorld.data_read")));
/*     */     }
/*     */     
/*     */     private void loadIcon() {
/* 551 */       boolean $$0 = (this.iconFile != null && Files.isRegularFile(this.iconFile, new LinkOption[0]));
/* 552 */       if ($$0) { 
/* 553 */         try { InputStream $$1 = Files.newInputStream(this.iconFile, new java.nio.file.OpenOption[0]); 
/* 554 */           try { this.icon.upload(NativeImage.read($$1));
/* 555 */             if ($$1 != null) $$1.close();  } catch (Throwable throwable) { if ($$1 != null) try { $$1.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (Throwable $$2)
/* 556 */         { WorldSelectionList.LOGGER.error("Invalid icon for world {}", this.summary.getLevelId(), $$2);
/* 557 */           this.iconFile = null; }
/*     */          }
/*     */       else
/* 560 */       { this.icon.clear(); }
/*     */     
/*     */     }
/*     */ 
/*     */     
/*     */     public void close() {
/* 566 */       this.icon.close();
/*     */     }
/*     */     
/*     */     public String getLevelName() {
/* 570 */       return this.summary.getLevelName();
/*     */     }
/*     */   }
/*     */   
/*     */   public static class LoadingHeader extends Entry {
/* 575 */     private static final Component LOADING_LABEL = (Component)Component.translatable("selectWorld.loading_list");
/*     */     
/*     */     private final Minecraft minecraft;
/*     */     
/*     */     public LoadingHeader(Minecraft $$0) {
/* 580 */       this.minecraft = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/* 585 */       int $$10 = (this.minecraft.screen.width - this.minecraft.font.width((FormattedText)LOADING_LABEL)) / 2;
/* 586 */       Objects.requireNonNull(this.minecraft.font); int $$11 = $$2 + ($$5 - 9) / 2;
/* 587 */       $$0.drawString(this.minecraft.font, LOADING_LABEL, $$10, $$11, 16777215, false);
/*     */       
/* 589 */       String $$12 = LoadingDotsText.get(Util.getMillis());
/* 590 */       int $$13 = (this.minecraft.screen.width - this.minecraft.font.width($$12)) / 2;
/* 591 */       Objects.requireNonNull(this.minecraft.font); int $$14 = $$11 + 9;
/* 592 */       $$0.drawString(this.minecraft.font, $$12, $$13, $$14, -8355712, false);
/*     */     }
/*     */ 
/*     */     
/*     */     public Component getNarration() {
/* 597 */       return LOADING_LABEL;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\worldselection\WorldSelectionList.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */