/*     */ package net.minecraft.client.gui.screens;
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.mojang.blaze3d.platform.InputConstants;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.io.File;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.nio.file.Path;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.CrashReport;
/*     */ import net.minecraft.CrashReportCategory;
/*     */ import net.minecraft.ReportedException;
/*     */ import net.minecraft.SharedConstants;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.ComponentPath;
/*     */ import net.minecraft.client.gui.Font;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.AbstractWidget;
/*     */ import net.minecraft.client.gui.components.Renderable;
/*     */ import net.minecraft.client.gui.components.TabOrderedElement;
/*     */ import net.minecraft.client.gui.components.Tooltip;
/*     */ import net.minecraft.client.gui.components.events.AbstractContainerEventHandler;
/*     */ import net.minecraft.client.gui.components.events.ContainerEventHandler;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.narration.NarratableEntry;
/*     */ import net.minecraft.client.gui.narration.NarratedElementType;
/*     */ import net.minecraft.client.gui.narration.NarrationElementOutput;
/*     */ import net.minecraft.client.gui.narration.ScreenNarrationCollector;
/*     */ import net.minecraft.client.gui.navigation.FocusNavigationEvent;
/*     */ import net.minecraft.client.gui.navigation.ScreenDirection;
/*     */ import net.minecraft.client.gui.navigation.ScreenRectangle;
/*     */ import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
/*     */ import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
/*     */ import net.minecraft.network.chat.ClickEvent;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.Style;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.sounds.Music;
/*     */ import net.minecraft.util.FormattedCharSequence;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.TooltipFlag;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public abstract class Screen extends AbstractContainerEventHandler implements Renderable {
/*  57 */   private static final Logger LOGGER = LogUtils.getLogger();
/*  58 */   private static final Set<String> ALLOWED_PROTOCOLS = Sets.newHashSet((Object[])new String[] { "http", "https" });
/*  59 */   private static final Component USAGE_NARRATION = (Component)Component.translatable("narrator.screen.usage");
/*     */   
/*  61 */   public static final ResourceLocation BACKGROUND_LOCATION = new ResourceLocation("textures/gui/options_background.png");
/*     */   
/*     */   protected final Component title;
/*     */   
/*  65 */   private final List<GuiEventListener> children = Lists.newArrayList();
/*  66 */   private final List<NarratableEntry> narratables = Lists.newArrayList();
/*     */   @Nullable
/*     */   protected Minecraft minecraft;
/*     */   private boolean initialized;
/*     */   public int width;
/*     */   public int height;
/*  72 */   private final List<Renderable> renderables = Lists.newArrayList();
/*     */   
/*     */   protected Font font;
/*     */   @Nullable
/*     */   private URI clickedLink;
/*  77 */   private static final long NARRATE_SUPPRESS_AFTER_INIT_TIME = TimeUnit.SECONDS.toMillis(2L);
/*  78 */   private static final long NARRATE_DELAY_NARRATOR_ENABLED = NARRATE_SUPPRESS_AFTER_INIT_TIME;
/*     */   
/*     */   private static final long NARRATE_DELAY_MOUSE_MOVE = 750L;
/*     */   private static final long NARRATE_DELAY_MOUSE_ACTION = 200L;
/*     */   private static final long NARRATE_DELAY_KEYBOARD_ACTION = 200L;
/*  83 */   private final ScreenNarrationCollector narrationState = new ScreenNarrationCollector();
/*  84 */   private long narrationSuppressTime = Long.MIN_VALUE;
/*  85 */   private long nextNarrationTime = Long.MAX_VALUE;
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private NarratableEntry lastNarratable;
/*     */   
/*     */   @Nullable
/*     */   private DeferredTooltipRendering deferredTooltipRendering;
/*     */   
/*     */   protected final Executor screenExecutor = $$0 -> this.minecraft.execute(());
/*     */ 
/*     */   
/*     */   protected Screen(Component $$0) {
/*  98 */     this.title = $$0;
/*     */   }
/*     */   
/*     */   public Component getTitle() {
/* 102 */     return this.title;
/*     */   }
/*     */   
/*     */   public Component getNarrationMessage() {
/* 106 */     return getTitle();
/*     */   }
/*     */   
/*     */   public final void renderWithTooltip(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 110 */     render($$0, $$1, $$2, $$3);
/* 111 */     if (this.deferredTooltipRendering != null) {
/* 112 */       $$0.renderTooltip(this.font, this.deferredTooltipRendering.tooltip(), this.deferredTooltipRendering.positioner(), $$1, $$2);
/* 113 */       this.deferredTooltipRendering = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 119 */     renderBackground($$0, $$1, $$2, $$3);
/* 120 */     for (Renderable $$4 : this.renderables) {
/* 121 */       $$4.render($$0, $$1, $$2, $$3);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean keyPressed(int $$0, int $$1, int $$2) {
/* 127 */     if ($$0 == 256 && shouldCloseOnEsc()) {
/* 128 */       onClose();
/* 129 */       return true;
/*     */     } 
/* 131 */     if (super.keyPressed($$0, $$1, $$2)) {
/* 132 */       return true;
/*     */     }
/* 134 */     switch ($$0) { case 263: 
/*     */       case 262: 
/*     */       case 265: 
/*     */       case 264: 
/*     */       case 258: 
/*     */       default:
/* 140 */         break; }  FocusNavigationEvent $$3 = null;
/*     */     
/* 142 */     if ($$3 != null) {
/* 143 */       ComponentPath $$4 = nextFocusPath($$3);
/*     */       
/* 145 */       if ($$4 == null && $$3 instanceof FocusNavigationEvent.TabNavigation) {
/* 146 */         clearFocus();
/* 147 */         $$4 = nextFocusPath($$3);
/*     */       } 
/* 149 */       if ($$4 != null) {
/* 150 */         changeFocus($$4);
/*     */       }
/*     */     } 
/* 153 */     return false;
/*     */   }
/*     */   
/*     */   private FocusNavigationEvent.TabNavigation createTabEvent() {
/* 157 */     boolean $$0 = !hasShiftDown();
/* 158 */     return new FocusNavigationEvent.TabNavigation($$0);
/*     */   }
/*     */   
/*     */   private FocusNavigationEvent.ArrowNavigation createArrowEvent(ScreenDirection $$0) {
/* 162 */     return new FocusNavigationEvent.ArrowNavigation($$0);
/*     */   }
/*     */   
/*     */   protected void setInitialFocus(GuiEventListener $$0) {
/* 166 */     ComponentPath $$1 = ComponentPath.path((ContainerEventHandler)this, $$0.nextFocusPath((FocusNavigationEvent)new FocusNavigationEvent.InitialFocus()));
/* 167 */     if ($$1 != null) {
/* 168 */       changeFocus($$1);
/*     */     }
/*     */   }
/*     */   
/*     */   public void clearFocus() {
/* 173 */     ComponentPath $$0 = getCurrentFocusPath();
/* 174 */     if ($$0 != null) {
/* 175 */       $$0.applyFocus(false);
/*     */     }
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   protected void changeFocus(ComponentPath $$0) {
/* 181 */     clearFocus();
/* 182 */     $$0.applyFocus(true);
/*     */   }
/*     */   
/*     */   public boolean shouldCloseOnEsc() {
/* 186 */     return true;
/*     */   }
/*     */   
/*     */   public void onClose() {
/* 190 */     this.minecraft.setScreen(null);
/*     */   }
/*     */   
/*     */   protected <T extends GuiEventListener & Renderable & NarratableEntry> T addRenderableWidget(T $$0) {
/* 194 */     this.renderables.add((Renderable)$$0);
/* 195 */     return (T)addWidget((GuiEventListener)$$0);
/*     */   }
/*     */   
/*     */   protected <T extends Renderable> T addRenderableOnly(T $$0) {
/* 199 */     this.renderables.add((Renderable)$$0);
/* 200 */     return $$0;
/*     */   }
/*     */   
/*     */   protected <T extends GuiEventListener & NarratableEntry> T addWidget(T $$0) {
/* 204 */     this.children.add((GuiEventListener)$$0);
/* 205 */     this.narratables.add((NarratableEntry)$$0);
/* 206 */     return $$0;
/*     */   }
/*     */   
/*     */   protected void removeWidget(GuiEventListener $$0) {
/* 210 */     if ($$0 instanceof Renderable) {
/* 211 */       this.renderables.remove($$0);
/*     */     }
/* 213 */     if ($$0 instanceof NarratableEntry) {
/* 214 */       this.narratables.remove($$0);
/*     */     }
/* 216 */     this.children.remove($$0);
/*     */   }
/*     */   
/*     */   protected void clearWidgets() {
/* 220 */     this.renderables.clear();
/* 221 */     this.children.clear();
/* 222 */     this.narratables.clear();
/*     */   }
/*     */   
/*     */   public static List<Component> getTooltipFromItem(Minecraft $$0, ItemStack $$1) {
/* 226 */     return $$1.getTooltipLines((Player)$$0.player, $$0.options.advancedItemTooltips ? (TooltipFlag)TooltipFlag.Default.ADVANCED : (TooltipFlag)TooltipFlag.Default.NORMAL);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void insertText(String $$0, boolean $$1) {}
/*     */   
/*     */   public boolean handleComponentClicked(@Nullable Style $$0) {
/* 233 */     if ($$0 == null) {
/* 234 */       return false;
/*     */     }
/*     */     
/* 237 */     ClickEvent $$1 = $$0.getClickEvent();
/* 238 */     if (hasShiftDown()) {
/* 239 */       if ($$0.getInsertion() != null) {
/* 240 */         insertText($$0.getInsertion(), false);
/*     */       }
/* 242 */     } else if ($$1 != null) {
/* 243 */       if ($$1.getAction() == ClickEvent.Action.OPEN_URL) {
/* 244 */         if (!((Boolean)this.minecraft.options.chatLinks().get()).booleanValue()) {
/* 245 */           return false;
/*     */         }
/*     */         try {
/* 248 */           URI $$2 = new URI($$1.getValue());
/*     */           
/* 250 */           String $$3 = $$2.getScheme();
/* 251 */           if ($$3 == null) {
/* 252 */             throw new URISyntaxException($$1.getValue(), "Missing protocol");
/*     */           }
/* 254 */           if (!ALLOWED_PROTOCOLS.contains($$3.toLowerCase(Locale.ROOT))) {
/* 255 */             throw new URISyntaxException($$1.getValue(), "Unsupported protocol: " + $$3.toLowerCase(Locale.ROOT));
/*     */           }
/*     */           
/* 258 */           if (((Boolean)this.minecraft.options.chatLinksPrompt().get()).booleanValue()) {
/* 259 */             this.clickedLink = $$2;
/* 260 */             this.minecraft.setScreen(new ConfirmLinkScreen(this::confirmLink, $$1.getValue(), false));
/*     */           } else {
/* 262 */             openLink($$2);
/*     */           } 
/* 264 */         } catch (URISyntaxException $$4) {
/* 265 */           LOGGER.error("Can't open url for {}", $$1, $$4);
/*     */         } 
/* 267 */       } else if ($$1.getAction() == ClickEvent.Action.OPEN_FILE) {
/* 268 */         URI $$5 = (new File($$1.getValue())).toURI();
/* 269 */         openLink($$5);
/* 270 */       } else if ($$1.getAction() == ClickEvent.Action.SUGGEST_COMMAND) {
/* 271 */         insertText(SharedConstants.filterText($$1.getValue()), true);
/* 272 */       } else if ($$1.getAction() == ClickEvent.Action.RUN_COMMAND) {
/* 273 */         String $$6 = SharedConstants.filterText($$1.getValue());
/* 274 */         if ($$6.startsWith("/")) {
/* 275 */           if (!this.minecraft.player.connection.sendUnsignedCommand($$6.substring(1))) {
/* 276 */             LOGGER.error("Not allowed to run command with signed argument from click event: '{}'", $$6);
/*     */           }
/*     */         } else {
/* 279 */           LOGGER.error("Failed to run command without '/' prefix from click event: '{}'", $$6);
/*     */         } 
/* 281 */       } else if ($$1.getAction() == ClickEvent.Action.COPY_TO_CLIPBOARD) {
/* 282 */         this.minecraft.keyboardHandler.setClipboard($$1.getValue());
/*     */       } else {
/* 284 */         LOGGER.error("Don't know how to handle {}", $$1);
/*     */       } 
/*     */       
/* 287 */       return true;
/*     */     } 
/* 289 */     return false;
/*     */   }
/*     */   
/*     */   public final void init(Minecraft $$0, int $$1, int $$2) {
/* 293 */     this.minecraft = $$0;
/* 294 */     this.font = $$0.font;
/* 295 */     this.width = $$1;
/* 296 */     this.height = $$2;
/* 297 */     if (!this.initialized) {
/* 298 */       init();
/*     */     } else {
/* 300 */       repositionElements();
/*     */     } 
/* 302 */     this.initialized = true;
/* 303 */     triggerImmediateNarration(false);
/*     */     
/* 305 */     suppressNarration(NARRATE_SUPPRESS_AFTER_INIT_TIME);
/*     */   }
/*     */   
/*     */   protected void rebuildWidgets() {
/* 309 */     clearWidgets();
/* 310 */     clearFocus();
/* 311 */     init();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<? extends GuiEventListener> children() {
/* 316 */     return this.children;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init() {}
/*     */ 
/*     */   
/*     */   public void tick() {}
/*     */ 
/*     */   
/*     */   public void removed() {}
/*     */ 
/*     */   
/*     */   public void added() {}
/*     */   
/*     */   public void renderBackground(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 332 */     if (this.minecraft.level != null) {
/* 333 */       renderTransparentBackground($$0);
/*     */     } else {
/* 335 */       renderDirtBackground($$0);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void renderTransparentBackground(GuiGraphics $$0) {
/* 340 */     $$0.fillGradient(0, 0, this.width, this.height, -1072689136, -804253680);
/*     */   }
/*     */   
/*     */   public void renderDirtBackground(GuiGraphics $$0) {
/* 344 */     $$0.setColor(0.25F, 0.25F, 0.25F, 1.0F);
/* 345 */     int $$1 = 32;
/* 346 */     $$0.blit(BACKGROUND_LOCATION, 0, 0, 0, 0.0F, 0.0F, this.width, this.height, 32, 32);
/* 347 */     $$0.setColor(1.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public boolean isPauseScreen() {
/* 351 */     return true;
/*     */   }
/*     */   
/*     */   private void confirmLink(boolean $$0) {
/* 355 */     if ($$0) {
/* 356 */       openLink(this.clickedLink);
/*     */     }
/*     */     
/* 359 */     this.clickedLink = null;
/* 360 */     this.minecraft.setScreen(this);
/*     */   }
/*     */   
/*     */   private void openLink(URI $$0) {
/* 364 */     Util.getPlatform().openUri($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean hasControlDown() {
/* 369 */     if (Minecraft.ON_OSX) {
/* 370 */       return (InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 343) || InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 347));
/*     */     }
/*     */     
/* 373 */     return (InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 341) || InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 345));
/*     */   }
/*     */   
/*     */   public static boolean hasShiftDown() {
/* 377 */     return (InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 340) || InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 344));
/*     */   }
/*     */   
/*     */   public static boolean hasAltDown() {
/* 381 */     return (InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 342) || InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 346));
/*     */   }
/*     */   
/*     */   public static boolean isCut(int $$0) {
/* 385 */     return ($$0 == 88 && hasControlDown() && !hasShiftDown() && !hasAltDown());
/*     */   }
/*     */   
/*     */   public static boolean isPaste(int $$0) {
/* 389 */     return ($$0 == 86 && hasControlDown() && !hasShiftDown() && !hasAltDown());
/*     */   }
/*     */   
/*     */   public static boolean isCopy(int $$0) {
/* 393 */     return ($$0 == 67 && hasControlDown() && !hasShiftDown() && !hasAltDown());
/*     */   }
/*     */   
/*     */   public static boolean isSelectAll(int $$0) {
/* 397 */     return ($$0 == 65 && hasControlDown() && !hasShiftDown() && !hasAltDown());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void repositionElements() {
/* 402 */     rebuildWidgets();
/*     */   }
/*     */ 
/*     */   
/*     */   public void resize(Minecraft $$0, int $$1, int $$2) {
/* 407 */     this.width = $$1;
/* 408 */     this.height = $$2;
/* 409 */     repositionElements();
/*     */   }
/*     */   
/*     */   public static void wrapScreenError(Runnable $$0, String $$1, String $$2) {
/*     */     try {
/* 414 */       $$0.run();
/* 415 */     } catch (Throwable $$3) {
/* 416 */       CrashReport $$4 = CrashReport.forThrowable($$3, $$1);
/* 417 */       CrashReportCategory $$5 = $$4.addCategory("Affected screen");
/* 418 */       $$5.setDetail("Screen name", () -> $$0);
/* 419 */       throw new ReportedException($$4);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean isValidCharacterForName(String $$0, char $$1, int $$2) {
/* 424 */     int $$3 = $$0.indexOf(':');
/* 425 */     int $$4 = $$0.indexOf('/');
/*     */     
/* 427 */     if ($$1 == ':') {
/* 428 */       return (($$4 == -1 || $$2 <= $$4) && $$3 == -1);
/*     */     }
/*     */     
/* 431 */     if ($$1 == '/') {
/* 432 */       return ($$2 > $$3);
/*     */     }
/*     */     
/* 435 */     return ($$1 == '_' || $$1 == '-' || ($$1 >= 'a' && $$1 <= 'z') || ($$1 >= '0' && $$1 <= '9') || $$1 == '.');
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isMouseOver(double $$0, double $$1) {
/* 440 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onFilesDrop(List<Path> $$0) {}
/*     */   
/*     */   private void scheduleNarration(long $$0, boolean $$1) {
/* 447 */     this.nextNarrationTime = Util.getMillis() + $$0;
/* 448 */     if ($$1) {
/* 449 */       this.narrationSuppressTime = Long.MIN_VALUE;
/*     */     }
/*     */   }
/*     */   
/*     */   private void suppressNarration(long $$0) {
/* 454 */     this.narrationSuppressTime = Util.getMillis() + $$0;
/*     */   }
/*     */   
/*     */   public void afterMouseMove() {
/* 458 */     scheduleNarration(750L, false);
/*     */   }
/*     */   
/*     */   public void afterMouseAction() {
/* 462 */     scheduleNarration(200L, true);
/*     */   }
/*     */   
/*     */   public void afterKeyboardAction() {
/* 466 */     scheduleNarration(200L, true);
/*     */   }
/*     */   
/*     */   private boolean shouldRunNarration() {
/* 470 */     return this.minecraft.getNarrator().isActive();
/*     */   }
/*     */   
/*     */   public void handleDelayedNarration() {
/* 474 */     if (shouldRunNarration()) {
/* 475 */       long $$0 = Util.getMillis();
/* 476 */       if ($$0 > this.nextNarrationTime && $$0 > this.narrationSuppressTime) {
/* 477 */         runNarration(true);
/* 478 */         this.nextNarrationTime = Long.MAX_VALUE;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void triggerImmediateNarration(boolean $$0) {
/* 484 */     if (shouldRunNarration()) {
/* 485 */       runNarration($$0);
/*     */     }
/*     */   }
/*     */   
/*     */   private void runNarration(boolean $$0) {
/* 490 */     this.narrationState.update(this::updateNarrationState);
/* 491 */     String $$1 = this.narrationState.collectNarrationText(!$$0);
/* 492 */     if (!$$1.isEmpty()) {
/* 493 */       this.minecraft.getNarrator().sayNow($$1);
/*     */     }
/*     */   }
/*     */   
/*     */   protected boolean shouldNarrateNavigation() {
/* 498 */     return true;
/*     */   }
/*     */   
/*     */   protected void updateNarrationState(NarrationElementOutput $$0) {
/* 502 */     $$0.add(NarratedElementType.TITLE, getNarrationMessage());
/* 503 */     if (shouldNarrateNavigation()) {
/* 504 */       $$0.add(NarratedElementType.USAGE, USAGE_NARRATION);
/*     */     }
/* 506 */     updateNarratedWidget($$0);
/*     */   }
/*     */   
/*     */   protected void updateNarratedWidget(NarrationElementOutput $$0) {
/* 510 */     List<NarratableEntry> $$1 = (List<NarratableEntry>)this.narratables.stream().filter(NarratableEntry::isActive).collect(Collectors.toList());
/*     */     
/* 512 */     Collections.sort($$1, Comparator.comparingInt(TabOrderedElement::getTabOrderGroup));
/*     */     
/* 514 */     NarratableSearchResult $$2 = findNarratableWidget($$1, this.lastNarratable);
/* 515 */     if ($$2 != null) {
/*     */       
/* 517 */       if ($$2.priority.isTerminal()) {
/* 518 */         this.lastNarratable = $$2.entry;
/*     */       }
/* 520 */       if ($$1.size() > 1) {
/* 521 */         $$0.add(NarratedElementType.POSITION, (Component)Component.translatable("narrator.position.screen", new Object[] { Integer.valueOf($$2.index + 1), Integer.valueOf($$1.size()) }));
/* 522 */         if ($$2.priority == NarratableEntry.NarrationPriority.FOCUSED) {
/* 523 */           $$0.add(NarratedElementType.USAGE, getUsageNarration());
/*     */         }
/*     */       } 
/* 526 */       $$2.entry.updateNarration($$0.nest());
/*     */     } 
/*     */   }
/*     */   
/*     */   protected Component getUsageNarration() {
/* 531 */     return (Component)Component.translatable("narration.component_list.usage");
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static NarratableSearchResult findNarratableWidget(List<? extends NarratableEntry> $$0, @Nullable NarratableEntry $$1) {
/* 536 */     NarratableSearchResult $$2 = null;
/* 537 */     NarratableSearchResult $$3 = null;
/* 538 */     for (int $$4 = 0, $$5 = $$0.size(); $$4 < $$5; $$4++) {
/* 539 */       NarratableEntry $$6 = $$0.get($$4);
/* 540 */       NarratableEntry.NarrationPriority $$7 = $$6.narrationPriority();
/* 541 */       if ($$7.isTerminal()) {
/* 542 */         if ($$6 == $$1) {
/* 543 */           $$3 = new NarratableSearchResult($$6, $$4, $$7);
/*     */         } else {
/* 545 */           return new NarratableSearchResult($$6, $$4, $$7);
/*     */         } 
/* 547 */       } else if ($$7.compareTo(($$2 != null) ? (Enum)$$2.priority : (Enum)NarratableEntry.NarrationPriority.NONE) > 0) {
/* 548 */         $$2 = new NarratableSearchResult($$6, $$4, $$7);
/*     */       } 
/*     */     } 
/*     */     
/* 552 */     return ($$2 != null) ? $$2 : $$3;
/*     */   }
/*     */   
/*     */   public void narrationEnabled() {
/* 556 */     scheduleNarration(NARRATE_DELAY_NARRATOR_ENABLED, false);
/*     */   }
/*     */   
/*     */   public void setTooltipForNextRenderPass(List<FormattedCharSequence> $$0) {
/* 560 */     setTooltipForNextRenderPass($$0, DefaultTooltipPositioner.INSTANCE, true);
/*     */   }
/*     */   
/*     */   public void setTooltipForNextRenderPass(List<FormattedCharSequence> $$0, ClientTooltipPositioner $$1, boolean $$2) {
/* 564 */     if (this.deferredTooltipRendering == null || $$2) {
/* 565 */       this.deferredTooltipRendering = new DeferredTooltipRendering($$0, $$1);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void setTooltipForNextRenderPass(Component $$0) {
/* 570 */     setTooltipForNextRenderPass(Tooltip.splitTooltip(this.minecraft, $$0));
/*     */   }
/*     */   
/*     */   public void setTooltipForNextRenderPass(Tooltip $$0, ClientTooltipPositioner $$1, boolean $$2) {
/* 574 */     setTooltipForNextRenderPass($$0.toCharSequence(this.minecraft), $$1, $$2);
/*     */   }
/*     */   
/*     */   public static class NarratableSearchResult {
/*     */     public final NarratableEntry entry;
/*     */     public final int index;
/*     */     public final NarratableEntry.NarrationPriority priority;
/*     */     
/*     */     public NarratableSearchResult(NarratableEntry $$0, int $$1, NarratableEntry.NarrationPriority $$2) {
/* 583 */       this.entry = $$0;
/* 584 */       this.index = $$1;
/* 585 */       this.priority = $$2;
/*     */     }
/*     */   }
/*     */   
/*     */   protected static void hideWidgets(AbstractWidget... $$0) {
/* 590 */     for (AbstractWidget $$1 : $$0) {
/* 591 */       $$1.visible = false;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public ScreenRectangle getRectangle() {
/* 597 */     return new ScreenRectangle(0, 0, this.width, this.height);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Music getBackgroundMusic() {
/* 602 */     return null;
/*     */   }
/*     */   private static final class DeferredTooltipRendering extends Record { private final List<FormattedCharSequence> tooltip; private final ClientTooltipPositioner positioner;
/* 605 */     DeferredTooltipRendering(List<FormattedCharSequence> $$0, ClientTooltipPositioner $$1) { this.tooltip = $$0; this.positioner = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/gui/screens/Screen$DeferredTooltipRendering;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #605	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 605 */       //   0	7	0	this	Lnet/minecraft/client/gui/screens/Screen$DeferredTooltipRendering; } public List<FormattedCharSequence> tooltip() { return this.tooltip; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/gui/screens/Screen$DeferredTooltipRendering;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #605	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/gui/screens/Screen$DeferredTooltipRendering; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/gui/screens/Screen$DeferredTooltipRendering;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #605	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/client/gui/screens/Screen$DeferredTooltipRendering;
/* 605 */       //   0	8	1	$$0	Ljava/lang/Object; } public ClientTooltipPositioner positioner() { return this.positioner; }
/*     */      }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\Screen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */