/*      */ package com.mojang.realmsclient;
/*      */ import com.google.common.util.concurrent.RateLimiter;
/*      */ import com.mojang.realmsclient.client.Ping;
/*      */ import com.mojang.realmsclient.client.RealmsClient;
/*      */ import com.mojang.realmsclient.dto.PingResult;
/*      */ import com.mojang.realmsclient.dto.RealmsNews;
/*      */ import com.mojang.realmsclient.dto.RealmsNotification;
/*      */ import com.mojang.realmsclient.dto.RealmsServer;
/*      */ import com.mojang.realmsclient.dto.RegionPingResult;
/*      */ import com.mojang.realmsclient.exception.RealmsServiceException;
/*      */ import com.mojang.realmsclient.gui.RealmsDataFetcher;
/*      */ import com.mojang.realmsclient.gui.RealmsServerList;
/*      */ import com.mojang.realmsclient.gui.screens.RealmsConfigureWorldScreen;
/*      */ import com.mojang.realmsclient.gui.screens.RealmsCreateRealmScreen;
/*      */ import com.mojang.realmsclient.gui.screens.RealmsGenericErrorScreen;
/*      */ import com.mojang.realmsclient.gui.screens.RealmsLongConfirmationScreen;
/*      */ import com.mojang.realmsclient.gui.screens.RealmsLongRunningMcoTaskScreen;
/*      */ import com.mojang.realmsclient.gui.screens.RealmsPendingInvitesScreen;
/*      */ import com.mojang.realmsclient.gui.screens.RealmsPopupScreen;
/*      */ import com.mojang.realmsclient.gui.task.DataFetcher;
/*      */ import com.mojang.realmsclient.util.RealmsPersistence;
/*      */ import com.mojang.realmsclient.util.RealmsUtil;
/*      */ import com.mojang.realmsclient.util.task.GetServerDetailsTask;
/*      */ import com.mojang.realmsclient.util.task.LongRunningTask;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.List;
/*      */ import java.util.Objects;
/*      */ import java.util.Set;
/*      */ import java.util.UUID;
/*      */ import java.util.concurrent.CompletableFuture;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.Supplier;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.SharedConstants;
/*      */ import net.minecraft.Util;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.gui.Font;
/*      */ import net.minecraft.client.gui.GuiGraphics;
/*      */ import net.minecraft.client.gui.components.AbstractSelectionList;
/*      */ import net.minecraft.client.gui.components.AbstractWidget;
/*      */ import net.minecraft.client.gui.components.Button;
/*      */ import net.minecraft.client.gui.components.CycleButton;
/*      */ import net.minecraft.client.gui.components.FocusableTextWidget;
/*      */ import net.minecraft.client.gui.components.ImageButton;
/*      */ import net.minecraft.client.gui.components.ImageWidget;
/*      */ import net.minecraft.client.gui.components.MultiLineTextWidget;
/*      */ import net.minecraft.client.gui.components.ObjectSelectionList;
/*      */ import net.minecraft.client.gui.components.PopupScreen;
/*      */ import net.minecraft.client.gui.components.SpriteIconButton;
/*      */ import net.minecraft.client.gui.components.Tooltip;
/*      */ import net.minecraft.client.gui.components.WidgetSprites;
/*      */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*      */ import net.minecraft.client.gui.layouts.FrameLayout;
/*      */ import net.minecraft.client.gui.layouts.GridLayout;
/*      */ import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
/*      */ import net.minecraft.client.gui.layouts.Layout;
/*      */ import net.minecraft.client.gui.layouts.LayoutElement;
/*      */ import net.minecraft.client.gui.layouts.LayoutSettings;
/*      */ import net.minecraft.client.gui.layouts.LinearLayout;
/*      */ import net.minecraft.client.gui.layouts.SpacerElement;
/*      */ import net.minecraft.client.gui.navigation.CommonInputs;
/*      */ import net.minecraft.client.gui.navigation.ScreenRectangle;
/*      */ import net.minecraft.client.gui.screens.ConfirmLinkScreen;
/*      */ import net.minecraft.client.gui.screens.ConfirmScreen;
/*      */ import net.minecraft.client.gui.screens.Screen;
/*      */ import net.minecraft.client.resources.sounds.SimpleSoundInstance;
/*      */ import net.minecraft.client.resources.sounds.SoundInstance;
/*      */ import net.minecraft.core.Holder;
/*      */ import net.minecraft.network.chat.CommonComponents;
/*      */ import net.minecraft.network.chat.Component;
/*      */ import net.minecraft.network.chat.FormattedText;
/*      */ import net.minecraft.network.chat.MutableComponent;
/*      */ import net.minecraft.realms.RealmsObjectSelectionList;
/*      */ import net.minecraft.realms.RealmsScreen;
/*      */ import net.minecraft.resources.ResourceLocation;
/*      */ import net.minecraft.sounds.SoundEvents;
/*      */ import net.minecraft.util.Mth;
/*      */ import org.slf4j.Logger;
/*      */ 
/*      */ public class RealmsMainScreen extends RealmsScreen {
/*   82 */   static final ResourceLocation INFO_SPRITE = new ResourceLocation("icon/info");
/*   83 */   static final ResourceLocation NEW_REALM_SPRITE = new ResourceLocation("icon/new_realm");
/*   84 */   static final ResourceLocation EXPIRED_SPRITE = new ResourceLocation("realm_status/expired");
/*   85 */   static final ResourceLocation EXPIRES_SOON_SPRITE = new ResourceLocation("realm_status/expires_soon");
/*   86 */   static final ResourceLocation OPEN_SPRITE = new ResourceLocation("realm_status/open");
/*   87 */   static final ResourceLocation CLOSED_SPRITE = new ResourceLocation("realm_status/closed");
/*   88 */   private static final ResourceLocation INVITE_SPRITE = new ResourceLocation("icon/invite");
/*   89 */   private static final ResourceLocation NEWS_SPRITE = new ResourceLocation("icon/news");
/*      */   
/*   91 */   static final Logger LOGGER = LogUtils.getLogger();
/*      */   
/*   93 */   private static final ResourceLocation LOGO_LOCATION = new ResourceLocation("textures/gui/title/realms.png");
/*   94 */   private static final ResourceLocation NO_REALMS_LOCATION = new ResourceLocation("textures/gui/realms/no_realms.png");
/*      */   
/*   96 */   private static final Component TITLE = (Component)Component.translatable("menu.online");
/*   97 */   private static final Component LOADING_TEXT = (Component)Component.translatable("mco.selectServer.loading");
/*   98 */   static final Component SERVER_UNITIALIZED_TEXT = (Component)Component.translatable("mco.selectServer.uninitialized");
/*   99 */   static final Component SUBSCRIPTION_EXPIRED_TEXT = (Component)Component.translatable("mco.selectServer.expiredList");
/*  100 */   private static final Component SUBSCRIPTION_RENEW_TEXT = (Component)Component.translatable("mco.selectServer.expiredRenew");
/*  101 */   static final Component TRIAL_EXPIRED_TEXT = (Component)Component.translatable("mco.selectServer.expiredTrial");
/*  102 */   private static final Component PLAY_TEXT = (Component)Component.translatable("mco.selectServer.play");
/*  103 */   private static final Component LEAVE_SERVER_TEXT = (Component)Component.translatable("mco.selectServer.leave");
/*  104 */   private static final Component CONFIGURE_SERVER_TEXT = (Component)Component.translatable("mco.selectServer.configure");
/*  105 */   static final Component SERVER_EXPIRED_TOOLTIP = (Component)Component.translatable("mco.selectServer.expired");
/*  106 */   static final Component SERVER_EXPIRES_SOON_TOOLTIP = (Component)Component.translatable("mco.selectServer.expires.soon");
/*  107 */   static final Component SERVER_EXPIRES_IN_DAY_TOOLTIP = (Component)Component.translatable("mco.selectServer.expires.day");
/*  108 */   static final Component SERVER_OPEN_TOOLTIP = (Component)Component.translatable("mco.selectServer.open");
/*  109 */   static final Component SERVER_CLOSED_TOOLTIP = (Component)Component.translatable("mco.selectServer.closed");
/*  110 */   static final Component UNITIALIZED_WORLD_NARRATION = (Component)Component.translatable("gui.narrate.button", new Object[] { SERVER_UNITIALIZED_TEXT });
/*  111 */   private static final Component NO_REALMS_TEXT = (Component)Component.translatable("mco.selectServer.noRealms");
/*  112 */   private static final Component NO_PENDING_INVITES = (Component)Component.translatable("mco.invites.nopending");
/*  113 */   private static final Component PENDING_INVITES = (Component)Component.translatable("mco.invites.pending");
/*      */   
/*      */   private static final int BUTTON_WIDTH = 100;
/*      */   
/*      */   private static final int BUTTON_COLUMNS = 3;
/*      */   
/*      */   private static final int BUTTON_SPACING = 4;
/*      */   private static final int CONTENT_WIDTH = 308;
/*      */   private static final int LOGO_WIDTH = 128;
/*      */   private static final int LOGO_HEIGHT = 34;
/*      */   private static final int LOGO_TEXTURE_WIDTH = 128;
/*      */   private static final int LOGO_TEXTURE_HEIGHT = 64;
/*      */   private static final int LOGO_PADDING = 5;
/*      */   private static final int HEADER_HEIGHT = 44;
/*      */   private static final int FOOTER_PADDING = 11;
/*      */   private static final int NEW_REALM_SPRITE_WIDTH = 40;
/*      */   private static final int NEW_REALM_SPRITE_HEIGHT = 20;
/*      */   private static final int ENTRY_WIDTH = 216;
/*      */   private static final int ITEM_HEIGHT = 36;
/*  132 */   private static final boolean SNAPSHOT = !SharedConstants.getCurrentVersion().isStable();
/*  133 */   private static boolean snapshotToggle = SNAPSHOT;
/*      */   
/*  135 */   private final CompletableFuture<RealmsAvailability.Result> availability = RealmsAvailability.get();
/*      */   
/*      */   @Nullable
/*      */   private DataFetcher.Subscription dataSubscription;
/*      */   
/*  140 */   private final Set<UUID> handledSeenNotifications = new HashSet<>();
/*      */   
/*      */   private static boolean regionsPinged;
/*      */   
/*      */   private final RateLimiter inviteNarrationLimiter;
/*      */   
/*      */   private final Screen lastScreen;
/*      */   
/*      */   private Button playButton;
/*      */   
/*      */   private Button backButton;
/*      */   private Button renewButton;
/*      */   private Button configureButton;
/*      */   private Button leaveButton;
/*      */   RealmSelectionList realmSelectionList;
/*      */   private RealmsServerList serverList;
/*  156 */   private List<RealmsServer> availableSnapshotServers = List.of();
/*      */   
/*      */   private volatile boolean trialsAvailable;
/*      */   
/*      */   @Nullable
/*      */   private volatile String newsLink;
/*      */   
/*      */   long lastClickTime;
/*  164 */   private final List<RealmsNotification> notifications = new ArrayList<>();
/*      */   
/*      */   private Button addRealmButton;
/*      */   
/*      */   private NotificationButton pendingInvitesButton;
/*      */   
/*      */   private NotificationButton newsButton;
/*      */   private LayoutState activeLayoutState;
/*      */   @Nullable
/*      */   private HeaderAndFooterLayout layout;
/*      */   
/*      */   public RealmsMainScreen(Screen $$0) {
/*  176 */     super(TITLE);
/*  177 */     this.lastScreen = $$0;
/*  178 */     this.inviteNarrationLimiter = RateLimiter.create(0.01666666753590107D);
/*      */   }
/*      */ 
/*      */   
/*      */   public void init() {
/*  183 */     this.serverList = new RealmsServerList(this.minecraft);
/*      */     
/*  185 */     this.realmSelectionList = new RealmSelectionList();
/*      */     
/*  187 */     MutableComponent mutableComponent1 = Component.translatable("mco.invites.title");
/*  188 */     this.pendingInvitesButton = new NotificationButton((Component)mutableComponent1, INVITE_SPRITE, $$1 -> this.minecraft.setScreen((Screen)new RealmsPendingInvitesScreen((Screen)this, $$0)));
/*      */     
/*  190 */     MutableComponent mutableComponent2 = Component.translatable("mco.news");
/*  191 */     this.newsButton = new NotificationButton((Component)mutableComponent2, NEWS_SPRITE, $$0 -> {
/*      */           String $$1 = this.newsLink;
/*      */           
/*      */           if ($$1 == null) {
/*      */             return;
/*      */           }
/*      */           
/*      */           ConfirmLinkScreen.confirmLinkNow((Screen)this, $$1);
/*      */           if (this.newsButton.notificationCount() != 0) {
/*      */             RealmsPersistence.RealmsPersistenceData $$2 = RealmsPersistence.readFile();
/*      */             $$2.hasUnreadNews = false;
/*      */             RealmsPersistence.writeFile($$2);
/*      */             this.newsButton.setNotificationCount(0);
/*      */           } 
/*      */         });
/*  206 */     this.newsButton.setTooltip(Tooltip.create((Component)mutableComponent2));
/*      */     
/*  208 */     this.playButton = Button.builder(PLAY_TEXT, $$0 -> play(getSelectedServer(), (Screen)this)).width(100).build();
/*  209 */     this.configureButton = Button.builder(CONFIGURE_SERVER_TEXT, $$0 -> configureClicked(getSelectedServer())).width(100).build();
/*  210 */     this.renewButton = Button.builder(SUBSCRIPTION_RENEW_TEXT, $$0 -> onRenew(getSelectedServer())).width(100).build();
/*      */     
/*  212 */     this.leaveButton = Button.builder(LEAVE_SERVER_TEXT, $$0 -> leaveClicked(getSelectedServer())).width(100).build();
/*  213 */     this.addRealmButton = Button.builder((Component)Component.translatable("mco.selectServer.purchase"), $$0 -> openTrialAvailablePopup()).size(100, 20).build();
/*  214 */     this.backButton = Button.builder(CommonComponents.GUI_BACK, $$0 -> this.minecraft.setScreen(this.lastScreen)).width(100).build();
/*      */     
/*  216 */     if (RealmsClient.ENVIRONMENT == RealmsClient.Environment.STAGE) {
/*  217 */       addRenderableWidget((GuiEventListener)CycleButton.booleanBuilder((Component)Component.literal("Snapshot"), (Component)Component.literal("Release")).create(5, 5, 100, 20, (Component)Component.literal("Realm"), ($$0, $$1) -> {
/*      */               snapshotToggle = $$1.booleanValue();
/*      */               
/*      */               this.availableSnapshotServers = List.of();
/*      */               debugRefreshDataFetchers();
/*      */             }));
/*      */     }
/*  224 */     updateLayout(LayoutState.LOADING);
/*  225 */     updateButtonStates();
/*      */     
/*  227 */     this.availability.thenAcceptAsync($$0 -> { Screen $$1 = $$0.createErrorScreen(this.lastScreen); if ($$1 == null) { this.dataSubscription = initDataFetcher(this.minecraft.realmsDataFetcher()); } else { this.minecraft.setScreen($$1); }  }this.screenExecutor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isSnapshot() {
/*  238 */     return (SNAPSHOT && snapshotToggle);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void repositionElements() {
/*  243 */     if (this.layout != null) {
/*  244 */       this.realmSelectionList.setSize(this.width, this.height - this.layout.getFooterHeight() - this.layout.getHeaderHeight());
/*  245 */       this.layout.arrangeElements();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void updateLayout() {
/*  250 */     if (this.serverList.isEmpty() && this.availableSnapshotServers.isEmpty() && this.notifications.isEmpty()) {
/*  251 */       updateLayout(LayoutState.NO_REALMS);
/*      */     } else {
/*  253 */       updateLayout(LayoutState.LIST);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void updateLayout(LayoutState $$0) {
/*  258 */     if (this.activeLayoutState == $$0) {
/*      */       return;
/*      */     }
/*  261 */     if (this.layout != null) {
/*  262 */       this.layout.visitWidgets($$1 -> $$0.removeWidget($$1));
/*      */     }
/*  264 */     this.layout = createLayout($$0);
/*  265 */     this.activeLayoutState = $$0;
/*  266 */     this.layout.visitWidgets($$1 -> (AbstractWidget)$$0.addRenderableWidget($$1));
/*  267 */     repositionElements();
/*      */   }
/*      */   
/*      */   private HeaderAndFooterLayout createLayout(LayoutState $$0) {
/*  271 */     HeaderAndFooterLayout $$1 = new HeaderAndFooterLayout((Screen)this);
/*  272 */     $$1.setHeaderHeight(44);
/*  273 */     $$1.addToHeader((LayoutElement)createHeader());
/*      */     
/*  275 */     Layout $$2 = createFooter($$0);
/*  276 */     $$2.arrangeElements();
/*  277 */     $$1.setFooterHeight($$2.getHeight() + 22);
/*  278 */     $$1.addToFooter((LayoutElement)$$2);
/*      */     
/*  280 */     switch ($$0) { case COMPATIBLE:
/*  281 */         $$1.addToContents((LayoutElement)new LoadingDotsWidget(this.font, LOADING_TEXT)); break;
/*  282 */       case UNVERIFIABLE: $$1.addToContents((LayoutElement)createNoRealmsContent()); break;
/*  283 */       case NEEDS_DOWNGRADE: $$1.addToContents((LayoutElement)this.realmSelectionList);
/*      */         break; }
/*      */     
/*  286 */     return $$1;
/*      */   }
/*      */   
/*      */   private Layout createHeader() {
/*  290 */     int $$0 = 90;
/*      */     
/*  292 */     LinearLayout $$1 = LinearLayout.horizontal().spacing(4);
/*  293 */     $$1.defaultCellSetting().alignVerticallyMiddle();
/*  294 */     $$1.addChild((LayoutElement)this.pendingInvitesButton);
/*  295 */     $$1.addChild((LayoutElement)this.newsButton);
/*      */     
/*  297 */     LinearLayout $$2 = LinearLayout.horizontal();
/*  298 */     $$2.defaultCellSetting().alignVerticallyMiddle();
/*      */     
/*  300 */     $$2.addChild((LayoutElement)SpacerElement.width(90));
/*  301 */     $$2.addChild((LayoutElement)ImageWidget.texture(128, 34, LOGO_LOCATION, 128, 64), LayoutSettings::alignHorizontallyCenter);
/*  302 */     ((FrameLayout)$$2.addChild((LayoutElement)new FrameLayout(90, 44))).addChild((LayoutElement)$$1, LayoutSettings::alignHorizontallyRight);
/*      */     
/*  304 */     return (Layout)$$2;
/*      */   }
/*      */   
/*      */   private Layout createFooter(LayoutState $$0) {
/*  308 */     GridLayout $$1 = (new GridLayout()).spacing(4);
/*  309 */     GridLayout.RowHelper $$2 = $$1.createRowHelper(3);
/*      */     
/*  311 */     if ($$0 == LayoutState.LIST) {
/*  312 */       $$2.addChild((LayoutElement)this.playButton);
/*  313 */       $$2.addChild((LayoutElement)this.configureButton);
/*  314 */       $$2.addChild((LayoutElement)this.renewButton);
/*  315 */       $$2.addChild((LayoutElement)this.leaveButton);
/*      */     } 
/*  317 */     $$2.addChild((LayoutElement)this.addRealmButton);
/*  318 */     $$2.addChild((LayoutElement)this.backButton);
/*      */     
/*  320 */     return (Layout)$$1;
/*      */   }
/*      */   
/*      */   private LinearLayout createNoRealmsContent() {
/*  324 */     LinearLayout $$0 = LinearLayout.vertical().spacing(10);
/*  325 */     $$0.defaultCellSetting().alignHorizontallyCenter();
/*      */     
/*  327 */     $$0.addChild((LayoutElement)ImageWidget.texture(130, 64, NO_REALMS_LOCATION, 130, 64));
/*      */     
/*  329 */     FocusableTextWidget $$1 = new FocusableTextWidget(308, NO_REALMS_TEXT, this.font, false);
/*  330 */     $$0.addChild((LayoutElement)$$1);
/*      */     
/*  332 */     return $$0;
/*      */   }
/*      */   
/*      */   void updateButtonStates() {
/*  336 */     RealmsServer $$0 = getSelectedServer();
/*  337 */     this.addRealmButton.active = (this.activeLayoutState != LayoutState.LOADING);
/*  338 */     this.playButton.active = ($$0 != null && shouldPlayButtonBeActive($$0));
/*  339 */     this.renewButton.active = ($$0 != null && shouldRenewButtonBeActive($$0));
/*  340 */     this.leaveButton.active = ($$0 != null && shouldLeaveButtonBeActive($$0));
/*  341 */     this.configureButton.active = ($$0 != null && shouldConfigureButtonBeActive($$0));
/*      */   }
/*      */   
/*      */   boolean shouldPlayButtonBeActive(RealmsServer $$0) {
/*  345 */     boolean $$1 = (!$$0.expired && $$0.state == RealmsServer.State.OPEN);
/*  346 */     return ($$1 && ($$0.isCompatible() || isSelfOwnedServer($$0)));
/*      */   }
/*      */   
/*      */   private boolean shouldRenewButtonBeActive(RealmsServer $$0) {
/*  350 */     return ($$0.expired && isSelfOwnedServer($$0));
/*      */   }
/*      */   
/*      */   private boolean shouldConfigureButtonBeActive(RealmsServer $$0) {
/*  354 */     return (isSelfOwnedServer($$0) && $$0.state != RealmsServer.State.UNINITIALIZED);
/*      */   }
/*      */   
/*      */   private boolean shouldLeaveButtonBeActive(RealmsServer $$0) {
/*  358 */     return !isSelfOwnedServer($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public void tick() {
/*  363 */     super.tick();
/*      */     
/*  365 */     if (this.dataSubscription != null) {
/*  366 */       this.dataSubscription.tick();
/*      */     }
/*      */   }
/*      */   
/*      */   public static void refreshPendingInvites() {
/*  371 */     (Minecraft.getInstance().realmsDataFetcher()).pendingInvitesTask.reset();
/*      */   }
/*      */   
/*      */   public static void refreshServerList() {
/*  375 */     (Minecraft.getInstance().realmsDataFetcher()).serverListUpdateTask.reset();
/*      */   }
/*      */   
/*      */   private void debugRefreshDataFetchers() {
/*  379 */     for (DataFetcher.Task<?> $$0 : (Iterable<DataFetcher.Task<?>>)this.minecraft.realmsDataFetcher().getTasks()) {
/*  380 */       $$0.reset();
/*      */     }
/*      */   }
/*      */   
/*      */   private DataFetcher.Subscription initDataFetcher(RealmsDataFetcher $$0) {
/*  385 */     DataFetcher.Subscription $$1 = $$0.dataFetcher.createSubscription();
/*      */     
/*  387 */     $$1.subscribe($$0.serverListUpdateTask, $$0 -> {
/*      */           this.serverList.updateServersList($$0.serverList());
/*      */           
/*      */           this.availableSnapshotServers = $$0.availableSnapshotServers();
/*      */           
/*      */           refreshListAndLayout();
/*      */           
/*      */           boolean $$1 = false;
/*      */           for (RealmsServer $$2 : this.serverList) {
/*      */             if (isSelfOwnedNonExpiredServer($$2)) {
/*      */               $$1 = true;
/*      */             }
/*      */           } 
/*      */           if (!regionsPinged && $$1) {
/*      */             regionsPinged = true;
/*      */             pingRegions();
/*      */           } 
/*      */         });
/*  405 */     callRealmsClient(RealmsClient::getNotifications, $$0 -> {
/*      */           this.notifications.clear();
/*      */           
/*      */           this.notifications.addAll($$0);
/*      */           
/*      */           for (RealmsNotification $$1 : $$0) {
/*      */             if ($$1 instanceof RealmsNotification.InfoPopup) {
/*      */               RealmsNotification.InfoPopup $$2 = (RealmsNotification.InfoPopup)$$1;
/*      */               
/*      */               PopupScreen $$3 = $$2.buildScreen((Screen)this, this::dismissNotification);
/*      */               if ($$3 != null) {
/*      */                 this.minecraft.setScreen((Screen)$$3);
/*      */                 markNotificationsAsSeen(List.of($$1));
/*      */                 break;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */           if (!this.notifications.isEmpty() && this.activeLayoutState != LayoutState.LOADING) {
/*      */             refreshListAndLayout();
/*      */           }
/*      */         });
/*  426 */     $$1.subscribe($$0.pendingInvitesTask, $$0 -> {
/*      */           this.pendingInvitesButton.setNotificationCount($$0.intValue());
/*      */           
/*      */           this.pendingInvitesButton.setTooltip(($$0.intValue() == 0) ? Tooltip.create(NO_PENDING_INVITES) : Tooltip.create(PENDING_INVITES));
/*      */           if ($$0.intValue() > 0 && this.inviteNarrationLimiter.tryAcquire(1)) {
/*      */             this.minecraft.getNarrator().sayNow((Component)Component.translatable("mco.configure.world.invite.narration", new Object[] { $$0 }));
/*      */           }
/*      */         });
/*  434 */     $$1.subscribe($$0.trialAvailabilityTask, $$0 -> this.trialsAvailable = $$0.booleanValue());
/*      */     
/*  436 */     $$1.subscribe($$0.newsTask, $$1 -> {
/*      */           $$0.newsManager.updateUnreadNews($$1);
/*      */           
/*      */           this.newsLink = $$0.newsManager.newsLink();
/*      */           this.newsButton.setNotificationCount($$0.newsManager.hasUnreadNews() ? Integer.MAX_VALUE : 0);
/*      */         });
/*  442 */     return $$1;
/*      */   }
/*      */   
/*      */   private void markNotificationsAsSeen(Collection<RealmsNotification> $$0) {
/*  446 */     List<UUID> $$1 = new ArrayList<>($$0.size());
/*  447 */     for (RealmsNotification $$2 : $$0) {
/*  448 */       if (!$$2.seen() && !this.handledSeenNotifications.contains($$2.uuid())) {
/*  449 */         $$1.add($$2.uuid());
/*      */       }
/*      */     } 
/*      */     
/*  453 */     if (!$$1.isEmpty()) {
/*  454 */       callRealmsClient($$1 -> {
/*      */             $$1.notificationsSeen($$0);
/*      */             return null;
/*      */           }$$1 -> this.handledSeenNotifications.addAll($$0));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static <T> void callRealmsClient(RealmsCall<T> $$0, Consumer<T> $$1) {
/*  466 */     Minecraft $$2 = Minecraft.getInstance();
/*  467 */     CompletableFuture.<T>supplyAsync(() -> {
/*      */           try {
/*      */             return $$0.request(RealmsClient.create($$1));
/*  470 */           } catch (RealmsServiceException $$2) {
/*      */             throw new RuntimeException($$2);
/*      */           } 
/*  473 */         }).thenAcceptAsync($$1, (Executor)$$2).exceptionally($$0 -> {
/*      */           LOGGER.error("Failed to execute call to Realms Service", $$0);
/*      */           return null;
/*      */         });
/*      */   }
/*      */   
/*      */   private void refreshListAndLayout() {
/*  480 */     RealmsServer $$0 = getSelectedServer();
/*  481 */     this.realmSelectionList.clear();
/*      */     
/*  483 */     for (RealmsNotification $$1 : this.notifications) {
/*  484 */       if (addListEntriesForNotification($$1)) {
/*  485 */         markNotificationsAsSeen(List.of($$1));
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/*  491 */     for (RealmsServer $$2 : this.availableSnapshotServers) {
/*  492 */       this.realmSelectionList.addEntry(new AvailableSnapshotEntry($$2));
/*      */     }
/*      */     
/*  495 */     for (RealmsServer $$3 : this.serverList) {
/*      */       Entry $$5;
/*  497 */       if (isSnapshot() && !$$3.isSnapshotRealm()) {
/*  498 */         if ($$3.state == RealmsServer.State.UNINITIALIZED) {
/*      */           continue;
/*      */         }
/*  501 */         Entry $$4 = new ParentEntry($$3);
/*      */       } else {
/*  503 */         $$5 = new ServerEntry($$3);
/*      */       } 
/*  505 */       this.realmSelectionList.addEntry($$5);
/*  506 */       if ($$0 != null && $$0.id == $$3.id) {
/*  507 */         this.realmSelectionList.setSelected($$5);
/*      */       }
/*      */     } 
/*      */     
/*  511 */     updateLayout();
/*  512 */     updateButtonStates();
/*      */   }
/*      */   
/*      */   private boolean addListEntriesForNotification(RealmsNotification $$0) {
/*  516 */     if ($$0 instanceof RealmsNotification.VisitUrl) { RealmsNotification.VisitUrl $$1 = (RealmsNotification.VisitUrl)$$0;
/*  517 */       Component $$2 = $$1.getMessage();
/*  518 */       int $$3 = this.font.wordWrapHeight((FormattedText)$$2, 216);
/*  519 */       int $$4 = Mth.positiveCeilDiv($$3 + 7, 36) - 1;
/*  520 */       this.realmSelectionList.addEntry(new NotificationMessageEntry($$2, $$4 + 2, (RealmsNotification)$$1));
/*  521 */       for (int $$5 = 0; $$5 < $$4; $$5++) {
/*  522 */         this.realmSelectionList.addEntry(new EmptyEntry());
/*      */       }
/*  524 */       this.realmSelectionList.addEntry(new ButtonEntry($$1.buildOpenLinkButton((Screen)this)));
/*  525 */       return true; }
/*      */     
/*  527 */     return false;
/*      */   }
/*      */   
/*      */   private void pingRegions() {
/*  531 */     (new Thread(() -> {
/*      */           List<RegionPingResult> $$0 = Ping.pingAllRegions();
/*      */           
/*      */           RealmsClient $$1 = RealmsClient.create();
/*      */           PingResult $$2 = new PingResult();
/*      */           $$2.pingResults = $$0;
/*      */           $$2.worldIds = getOwnedNonExpiredWorldIds();
/*      */           try {
/*      */             $$1.sendPingResults($$2);
/*  540 */           } catch (Throwable $$3) {
/*      */             LOGGER.warn("Could not send ping result to Realms: ", $$3);
/*      */           } 
/*  543 */         })).start();
/*      */   }
/*      */   
/*      */   private List<Long> getOwnedNonExpiredWorldIds() {
/*  547 */     List<Long> $$0 = Lists.newArrayList();
/*      */     
/*  549 */     for (RealmsServer $$1 : this.serverList) {
/*  550 */       if (isSelfOwnedNonExpiredServer($$1)) {
/*  551 */         $$0.add(Long.valueOf($$1.id));
/*      */       }
/*      */     } 
/*      */     
/*  555 */     return $$0;
/*      */   }
/*      */   
/*      */   private void onRenew(@Nullable RealmsServer $$0) {
/*  559 */     if ($$0 != null) {
/*  560 */       String $$1 = CommonLinks.extendRealms($$0.remoteSubscriptionId, this.minecraft.getUser().getProfileId(), $$0.expiredTrial);
/*  561 */       this.minecraft.keyboardHandler.setClipboard($$1);
/*  562 */       Util.getPlatform().openUri($$1);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void configureClicked(@Nullable RealmsServer $$0) {
/*  567 */     if ($$0 != null && this.minecraft.isLocalPlayer($$0.ownerUUID)) {
/*  568 */       this.minecraft.setScreen((Screen)new RealmsConfigureWorldScreen(this, $$0.id));
/*      */     }
/*      */   }
/*      */   
/*      */   private void leaveClicked(@Nullable RealmsServer $$0) {
/*  573 */     if ($$0 != null && !this.minecraft.isLocalPlayer($$0.ownerUUID)) {
/*  574 */       MutableComponent mutableComponent1 = Component.translatable("mco.configure.world.leave.question.line1");
/*  575 */       MutableComponent mutableComponent2 = Component.translatable("mco.configure.world.leave.question.line2");
/*  576 */       this.minecraft.setScreen((Screen)new RealmsLongConfirmationScreen($$1 -> leaveServer($$1, $$0), RealmsLongConfirmationScreen.Type.INFO, (Component)mutableComponent1, (Component)mutableComponent2, true));
/*      */     } 
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   private RealmsServer getSelectedServer() {
/*  582 */     AbstractSelectionList.Entry entry = this.realmSelectionList.getSelected(); if (entry instanceof ServerEntry) { ServerEntry $$0 = (ServerEntry)entry;
/*  583 */       return $$0.getServer(); }
/*      */     
/*  585 */     return null;
/*      */   }
/*      */   
/*      */   private void leaveServer(boolean $$0, final RealmsServer server) {
/*  589 */     if ($$0) {
/*  590 */       (new Thread("Realms-leave-server")
/*      */         {
/*      */           public void run() {
/*      */             try {
/*  594 */               RealmsClient $$0 = RealmsClient.create();
/*  595 */               $$0.uninviteMyselfFrom(server.id);
/*  596 */               RealmsMainScreen.this.minecraft.execute(RealmsMainScreen::refreshServerList);
/*  597 */             } catch (RealmsServiceException $$1) {
/*  598 */               RealmsMainScreen.LOGGER.error("Couldn't configure world", (Throwable)$$1);
/*  599 */               RealmsMainScreen.this.minecraft.execute(() -> RealmsMainScreen.this.minecraft.setScreen((Screen)new RealmsGenericErrorScreen($$0, (Screen)RealmsMainScreen.this)));
/*      */             } 
/*      */           }
/*  602 */         }).start();
/*      */     }
/*      */     
/*  605 */     this.minecraft.setScreen((Screen)this);
/*      */   }
/*      */   
/*      */   void dismissNotification(UUID $$0) {
/*  609 */     callRealmsClient($$1 -> {
/*      */           $$1.notificationsDismiss(List.of($$0));
/*      */           return null;
/*      */         }$$1 -> {
/*      */           this.notifications.removeIf(());
/*      */           refreshListAndLayout();
/*      */         });
/*      */   }
/*      */   
/*      */   public void resetScreen() {
/*  619 */     this.realmSelectionList.setSelected((Entry)null);
/*  620 */     refreshServerList();
/*      */   }
/*      */ 
/*      */   
/*      */   public Component getNarrationMessage() {
/*  625 */     switch (this.activeLayoutState) { default: throw new IncompatibleClassChangeError();case COMPATIBLE: case UNVERIFIABLE: case NEEDS_DOWNGRADE: break; }  return 
/*      */ 
/*      */       
/*  628 */       super.getNarrationMessage();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/*  634 */     super.render($$0, $$1, $$2, $$3);
/*      */     
/*  636 */     if (isSnapshot()) {
/*  637 */       $$0.drawString(this.font, "Minecraft " + SharedConstants.getCurrentVersion().getName(), 2, this.height - 10, -1);
/*      */     }
/*      */     
/*  640 */     if (this.trialsAvailable && this.addRealmButton.active) {
/*  641 */       RealmsPopupScreen.renderDiamond($$0, this.addRealmButton);
/*      */     }
/*      */     
/*  644 */     switch (RealmsClient.ENVIRONMENT) { case COMPATIBLE:
/*  645 */         renderEnvironment($$0, "STAGE!", -256); break;
/*  646 */       case UNVERIFIABLE: renderEnvironment($$0, "LOCAL!", 8388479);
/*      */         break; }
/*      */   
/*      */   }
/*      */   private void openTrialAvailablePopup() {
/*  651 */     this.minecraft.setScreen((Screen)new RealmsPopupScreen((Screen)this, this.trialsAvailable));
/*      */   }
/*      */   
/*      */   public static void play(@Nullable RealmsServer $$0, Screen $$1) {
/*  655 */     play($$0, $$1, false);
/*      */   }
/*      */   
/*      */   public static void play(@Nullable RealmsServer $$0, Screen $$1, boolean $$2) {
/*  659 */     if ($$0 != null) {
/*  660 */       if (!isSnapshot() || $$2) {
/*  661 */         Minecraft.getInstance().setScreen((Screen)new RealmsLongRunningMcoTaskScreen($$1, new LongRunningTask[] { (LongRunningTask)new GetServerDetailsTask($$1, $$0) }));
/*      */         return;
/*      */       } 
/*  664 */       switch ($$0.compatibility) { case COMPATIBLE:
/*  665 */           Minecraft.getInstance().setScreen((Screen)new RealmsLongRunningMcoTaskScreen($$1, new LongRunningTask[] { (LongRunningTask)new GetServerDetailsTask($$1, $$0) })); break;
/*  666 */         case UNVERIFIABLE: confirmToPlay($$0, $$1, 
/*  667 */               (Component)Component.translatable("mco.compatibility.unverifiable.title").withColor(-171), 
/*  668 */               (Component)Component.translatable("mco.compatibility.unverifiable.message"), CommonComponents.GUI_CONTINUE);
/*      */           break;
/*      */         case NEEDS_DOWNGRADE:
/*  671 */           confirmToPlay($$0, $$1, 
/*  672 */               (Component)Component.translatable("selectWorld.backupQuestion.downgrade").withColor(-2142128), 
/*  673 */               (Component)Component.translatable("mco.compatibility.downgrade.description", new Object[] {
/*  674 */                   Component.literal($$0.activeVersion).withColor(-171), 
/*  675 */                   Component.literal(SharedConstants.getCurrentVersion().getName()).withColor(-171)
/*  676 */                 }), (Component)Component.translatable("mco.compatibility.downgrade")); break;
/*      */         case NEEDS_UPGRADE:
/*  678 */           confirmToPlay($$0, $$1, 
/*  679 */               (Component)Component.translatable("mco.compatibility.upgrade.title").withColor(-171), 
/*  680 */               (Component)Component.translatable("mco.compatibility.upgrade.description", new Object[] {
/*  681 */                   Component.literal($$0.activeVersion).withColor(-171), 
/*  682 */                   Component.literal(SharedConstants.getCurrentVersion().getName()).withColor(-171)
/*  683 */                 }), (Component)Component.translatable("mco.compatibility.upgrade"));
/*      */           break; }
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void confirmToPlay(RealmsServer $$0, Screen $$1, Component $$2, Component $$3, Component $$4) {
/*  690 */     Minecraft.getInstance().setScreen((Screen)new ConfirmScreen($$2 -> { Screen $$4; if ($$2) { RealmsLongRunningMcoTaskScreen realmsLongRunningMcoTaskScreen = new RealmsLongRunningMcoTaskScreen($$0, new LongRunningTask[] { (LongRunningTask)new GetServerDetailsTask($$0, $$1) }); refreshServerList(); } else { $$4 = $$0; }  Minecraft.getInstance().setScreen($$4); }$$2, $$3, $$4, CommonComponents.GUI_CANCEL));
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
/*      */   private class RealmSelectionList
/*      */     extends RealmsObjectSelectionList<Entry>
/*      */   {
/*      */     public RealmSelectionList() {
/*  707 */       super(RealmsMainScreen.this.width, RealmsMainScreen.this.height, 0, 36);
/*      */     }
/*      */ 
/*      */     
/*      */     public void setSelected(@Nullable RealmsMainScreen.Entry $$0) {
/*  712 */       super.setSelected((AbstractSelectionList.Entry)$$0);
/*  713 */       RealmsMainScreen.this.updateButtonStates();
/*      */     }
/*      */ 
/*      */     
/*      */     public int getMaxPosition() {
/*  718 */       return getItemCount() * 36;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getRowWidth() {
/*  723 */       return 300;
/*      */     }
/*      */   }
/*      */   
/*      */   public static Component getVersionComponent(String $$0, boolean $$1) {
/*  728 */     return getVersionComponent($$0, $$1 ? -8355712 : -2142128);
/*      */   }
/*      */   
/*      */   public static Component getVersionComponent(String $$0, int $$1) {
/*  732 */     if (StringUtils.isBlank($$0)) {
/*  733 */       return CommonComponents.EMPTY;
/*      */     }
/*  735 */     return (Component)Component.translatable("mco.version", new Object[] { Component.literal($$0).withColor($$1) });
/*      */   }
/*      */   
/*      */   private abstract class Entry
/*      */     extends ObjectSelectionList.Entry<Entry> {
/*      */     private static final int STATUS_LIGHT_WIDTH = 10;
/*      */     private static final int STATUS_LIGHT_HEIGHT = 28;
/*      */     private static final int PADDING = 7;
/*      */     
/*      */     protected void renderStatusLights(RealmsServer $$0, GuiGraphics $$1, int $$2, int $$3, int $$4, int $$5) {
/*  745 */       int $$6 = $$2 - 10 - 7;
/*  746 */       int $$7 = $$3 + 2;
/*  747 */       if ($$0.expired) {
/*  748 */         drawRealmStatus($$1, $$6, $$7, $$4, $$5, RealmsMainScreen.EXPIRED_SPRITE, () -> RealmsMainScreen.SERVER_EXPIRED_TOOLTIP);
/*  749 */       } else if ($$0.state == RealmsServer.State.CLOSED) {
/*  750 */         drawRealmStatus($$1, $$6, $$7, $$4, $$5, RealmsMainScreen.CLOSED_SPRITE, () -> RealmsMainScreen.SERVER_CLOSED_TOOLTIP);
/*  751 */       } else if (RealmsMainScreen.this.isSelfOwnedServer($$0) && $$0.daysLeft < 7) {
/*  752 */         drawRealmStatus($$1, $$6, $$7, $$4, $$5, RealmsMainScreen.EXPIRES_SOON_SPRITE, () -> ($$0.daysLeft <= 0) ? RealmsMainScreen.SERVER_EXPIRES_SOON_TOOLTIP : (($$0.daysLeft == 1) ? RealmsMainScreen.SERVER_EXPIRES_IN_DAY_TOOLTIP : Component.translatable("mco.selectServer.expires.days", new Object[] { Integer.valueOf($$0.daysLeft) })));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*  761 */       else if ($$0.state == RealmsServer.State.OPEN) {
/*  762 */         drawRealmStatus($$1, $$6, $$7, $$4, $$5, RealmsMainScreen.OPEN_SPRITE, () -> RealmsMainScreen.SERVER_OPEN_TOOLTIP);
/*      */       } 
/*      */     }
/*      */     
/*      */     private void drawRealmStatus(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, ResourceLocation $$5, Supplier<Component> $$6) {
/*  767 */       $$0.blitSprite($$5, $$1, $$2, 10, 28);
/*  768 */       if (RealmsMainScreen.this.realmSelectionList.isMouseOver($$3, $$4) && $$3 >= $$1 && $$3 <= $$1 + 10 && $$4 >= $$2 && $$4 <= $$2 + 28) {
/*  769 */         RealmsMainScreen.this.setTooltipForNextRenderPass($$6.get());
/*      */       }
/*      */     }
/*      */     
/*      */     protected void renderThirdLine(GuiGraphics $$0, int $$1, int $$2, RealmsServer $$3) {
/*  774 */       int $$4 = textX($$2);
/*  775 */       int $$5 = firstLineY($$1);
/*  776 */       int $$6 = thirdLineY($$5);
/*      */       
/*  778 */       if (!RealmsMainScreen.this.isSelfOwnedServer($$3)) {
/*  779 */         $$0.drawString(RealmsMainScreen.this.font, $$3.owner, $$4, thirdLineY($$5), -8355712, false);
/*  780 */       } else if ($$3.expired) {
/*  781 */         Component $$7 = $$3.expiredTrial ? RealmsMainScreen.TRIAL_EXPIRED_TEXT : RealmsMainScreen.SUBSCRIPTION_EXPIRED_TEXT;
/*  782 */         $$0.drawString(RealmsMainScreen.this.font, $$7, $$4, $$6, -2142128, false);
/*      */       } 
/*      */     }
/*      */     
/*      */     protected void renderClampedName(GuiGraphics $$0, String $$1, int $$2, int $$3, int $$4, int $$5) {
/*  787 */       int $$6 = $$4 - $$2;
/*  788 */       if (RealmsMainScreen.this.font.width($$1) > $$6) {
/*  789 */         String $$7 = RealmsMainScreen.this.font.plainSubstrByWidth($$1, $$6 - RealmsMainScreen.this.font.width("... "));
/*  790 */         $$0.drawString(RealmsMainScreen.this.font, $$7 + "...", $$2, $$3, $$5, false);
/*      */       } else {
/*  792 */         $$0.drawString(RealmsMainScreen.this.font, $$1, $$2, $$3, $$5, false);
/*      */       } 
/*      */     }
/*      */     
/*      */     protected int versionTextX(int $$0, int $$1, Component $$2) {
/*  797 */       return $$0 + $$1 - RealmsMainScreen.this.font.width((FormattedText)$$2) - 20;
/*      */     }
/*      */     
/*      */     protected int firstLineY(int $$0) {
/*  801 */       return $$0 + 1;
/*      */     }
/*      */     
/*      */     protected int lineHeight() {
/*  805 */       Objects.requireNonNull(RealmsMainScreen.this.font); return 2 + 9;
/*      */     }
/*      */     
/*      */     protected int textX(int $$0) {
/*  809 */       return $$0 + 36 + 2;
/*      */     }
/*      */     
/*      */     protected int secondLineY(int $$0) {
/*  813 */       return $$0 + lineHeight();
/*      */     }
/*      */     
/*      */     protected int thirdLineY(int $$0) {
/*  817 */       return $$0 + lineHeight() * 2;
/*      */     }
/*      */   }
/*      */   
/*      */   private class NotificationMessageEntry
/*      */     extends Entry {
/*      */     private static final int SIDE_MARGINS = 40;
/*      */     private static final int OUTLINE_COLOR = -12303292;
/*      */     private final Component text;
/*      */     private final int frameItemHeight;
/*  827 */     private final List<AbstractWidget> children = new ArrayList<>();
/*      */     @Nullable
/*      */     private final RealmsMainScreen.CrossButton dismissButton;
/*      */     private final MultiLineTextWidget textWidget;
/*      */     private final GridLayout gridLayout;
/*      */     private final FrameLayout textFrame;
/*  833 */     private int lastEntryWidth = -1;
/*      */     
/*      */     public NotificationMessageEntry(Component $$0, int $$1, RealmsNotification $$2) {
/*  836 */       this.text = $$0;
/*  837 */       this.frameItemHeight = $$1;
/*      */       
/*  839 */       this.gridLayout = new GridLayout();
/*  840 */       int $$3 = 7;
/*  841 */       this.gridLayout.addChild((LayoutElement)ImageWidget.sprite(20, 20, RealmsMainScreen.INFO_SPRITE), 0, 0, this.gridLayout.newCellSettings().padding(7, 7, 0, 0));
/*  842 */       this.gridLayout.addChild((LayoutElement)SpacerElement.width(40), 0, 0);
/*  843 */       Objects.requireNonNull(RealmsMainScreen.this.font); this.textFrame = (FrameLayout)this.gridLayout.addChild((LayoutElement)new FrameLayout(0, 9 * 3 * ($$1 - 1)), 0, 1, this.gridLayout.newCellSettings().paddingTop(7));
/*  844 */       this.textWidget = (MultiLineTextWidget)this.textFrame.addChild((LayoutElement)(new MultiLineTextWidget($$0, RealmsMainScreen.this.font)).setCentered(true), this.textFrame.newChildLayoutSettings().alignHorizontallyCenter().alignVerticallyTop());
/*  845 */       this.gridLayout.addChild((LayoutElement)SpacerElement.width(40), 0, 2);
/*  846 */       if ($$2.dismissable()) {
/*  847 */         this.dismissButton = (RealmsMainScreen.CrossButton)this.gridLayout.addChild((LayoutElement)new RealmsMainScreen.CrossButton($$1 -> RealmsMainScreen.this.dismissNotification($$0.uuid()), (Component)Component.translatable("mco.notification.dismiss")), 0, 2, this.gridLayout.newCellSettings().alignHorizontallyRight().padding(0, 7, 7, 0));
/*      */       } else {
/*  849 */         this.dismissButton = null;
/*      */       } 
/*  851 */       Objects.requireNonNull(this.children); this.gridLayout.visitWidgets(this.children::add);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean keyPressed(int $$0, int $$1, int $$2) {
/*  856 */       if (this.dismissButton != null && this.dismissButton.keyPressed($$0, $$1, $$2)) {
/*  857 */         return true;
/*      */       }
/*  859 */       return super.keyPressed($$0, $$1, $$2);
/*      */     }
/*      */     
/*      */     private void updateEntryWidth(int $$0) {
/*  863 */       if (this.lastEntryWidth != $$0) {
/*  864 */         refreshLayout($$0);
/*  865 */         this.lastEntryWidth = $$0;
/*      */       } 
/*      */     }
/*      */     
/*      */     private void refreshLayout(int $$0) {
/*  870 */       int $$1 = $$0 - 80;
/*  871 */       this.textFrame.setMinWidth($$1);
/*  872 */       this.textWidget.setMaxWidth($$1);
/*  873 */       this.gridLayout.arrangeElements();
/*      */     }
/*      */ 
/*      */     
/*      */     public void renderBack(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/*  878 */       super.renderBack($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8, $$9);
/*  879 */       $$0.renderOutline($$3 - 2, $$2 - 2, $$4, 36 * this.frameItemHeight - 2, -12303292);
/*      */     }
/*      */ 
/*      */     
/*      */     public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/*  884 */       this.gridLayout.setPosition($$3, $$2);
/*  885 */       updateEntryWidth($$4 - 4);
/*      */       
/*  887 */       this.children.forEach($$4 -> $$4.render($$0, $$1, $$2, $$3));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean mouseClicked(double $$0, double $$1, int $$2) {
/*  892 */       if (this.dismissButton != null) {
/*  893 */         this.dismissButton.mouseClicked($$0, $$1, $$2);
/*      */       }
/*  895 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public Component getNarration() {
/*  900 */       return this.text;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private class EmptyEntry
/*      */     extends Entry
/*      */   {
/*      */     public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {}
/*      */     
/*      */     public Component getNarration() {
/*  911 */       return (Component)Component.empty();
/*      */     }
/*      */   }
/*      */   
/*      */   private class ButtonEntry extends Entry {
/*      */     private final Button button;
/*      */     
/*      */     public ButtonEntry(Button $$0) {
/*  919 */       this.button = $$0;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean mouseClicked(double $$0, double $$1, int $$2) {
/*  924 */       this.button.mouseClicked($$0, $$1, $$2);
/*  925 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean keyPressed(int $$0, int $$1, int $$2) {
/*  930 */       if (this.button.keyPressed($$0, $$1, $$2)) {
/*  931 */         return true;
/*      */       }
/*  933 */       return super.keyPressed($$0, $$1, $$2);
/*      */     }
/*      */ 
/*      */     
/*      */     public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/*  938 */       this.button.setPosition(RealmsMainScreen.this.width / 2 - 75, $$2 + 4);
/*  939 */       this.button.render($$0, $$6, $$7, $$9);
/*      */     }
/*      */ 
/*      */     
/*      */     public void setFocused(boolean $$0) {
/*  944 */       super.setFocused($$0);
/*  945 */       this.button.setFocused($$0);
/*      */     }
/*      */ 
/*      */     
/*      */     public Component getNarration() {
/*  950 */       return this.button.getMessage();
/*      */     }
/*      */   }
/*      */   
/*      */   private class AvailableSnapshotEntry extends Entry {
/*  955 */     private static final Component START_SNAPSHOT_REALM = (Component)Component.translatable("mco.snapshot.start");
/*      */     
/*      */     private static final int TEXT_PADDING = 5;
/*      */     private final Tooltip tooltip;
/*      */     private final RealmsServer parent;
/*      */     
/*      */     public AvailableSnapshotEntry(RealmsServer $$0) {
/*  962 */       this.parent = $$0;
/*  963 */       this.tooltip = Tooltip.create((Component)Component.translatable("mco.snapshot.tooltip"));
/*      */     }
/*      */ 
/*      */     
/*      */     public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/*  968 */       $$0.blitSprite(RealmsMainScreen.NEW_REALM_SPRITE, $$3 - 5, $$2 + $$5 / 2 - 10, 40, 20);
/*      */       
/*  970 */       Objects.requireNonNull(RealmsMainScreen.this.font); int $$10 = $$2 + $$5 / 2 - 9 / 2;
/*  971 */       $$0.drawString(RealmsMainScreen.this.font, START_SNAPSHOT_REALM, $$3 + 40 - 2, $$10 - 5, 8388479);
/*  972 */       $$0.drawString(RealmsMainScreen.this.font, (Component)Component.translatable("mco.snapshot.description", new Object[] { this.parent.name }), $$3 + 40 - 2, $$10 + 5, -8355712);
/*  973 */       this.tooltip.refreshTooltipForNextRenderPass($$8, isFocused(), new ScreenRectangle($$3, $$2, $$4, $$5));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean mouseClicked(double $$0, double $$1, int $$2) {
/*  978 */       addSnapshotRealm();
/*  979 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean keyPressed(int $$0, int $$1, int $$2) {
/*  984 */       if (CommonInputs.selected($$0)) {
/*  985 */         addSnapshotRealm();
/*  986 */         return true;
/*      */       } 
/*  988 */       return super.keyPressed($$0, $$1, $$2);
/*      */     }
/*      */     
/*      */     private void addSnapshotRealm() {
/*  992 */       RealmsMainScreen.this.minecraft.getSoundManager().play((SoundInstance)SimpleSoundInstance.forUI((Holder)SoundEvents.UI_BUTTON_CLICK, 1.0F));
/*  993 */       RealmsMainScreen.this.minecraft.setScreen((Screen)(new PopupScreen.Builder((Screen)RealmsMainScreen.this, (Component)Component.translatable("mco.snapshot.createSnapshotPopup.title")))
/*  994 */           .setMessage((Component)Component.translatable("mco.snapshot.createSnapshotPopup.text"))
/*  995 */           .addButton((Component)Component.translatable("mco.selectServer.create"), $$0 -> RealmsMainScreen.this.minecraft.setScreen((Screen)new RealmsCreateRealmScreen(RealmsMainScreen.this, this.parent.id)))
/*      */           
/*  997 */           .addButton(CommonComponents.GUI_CANCEL, PopupScreen::onClose)
/*  998 */           .build());
/*      */     }
/*      */ 
/*      */     
/*      */     public Component getNarration() {
/* 1003 */       return (Component)Component.translatable("gui.narrate.button", new Object[] { CommonComponents.joinForNarration(new Component[] { START_SNAPSHOT_REALM, 
/* 1004 */                 (Component)Component.translatable("mco.snapshot.description", new Object[] { this.parent.name }) }) });
/*      */     }
/*      */   }
/*      */   
/*      */   private class ParentEntry
/*      */     extends Entry {
/*      */     private final RealmsServer server;
/*      */     private final Tooltip tooltip;
/*      */     
/*      */     public ParentEntry(RealmsServer $$0) {
/* 1014 */       this.server = $$0;
/* 1015 */       this.tooltip = Tooltip.create((Component)Component.translatable("mco.snapshot.parent.tooltip"));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean mouseClicked(double $$0, double $$1, int $$2) {
/* 1020 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/* 1025 */       int $$10 = textX($$3);
/* 1026 */       int $$11 = firstLineY($$2);
/*      */       
/* 1028 */       RealmsUtil.renderPlayerFace($$0, $$3, $$2, 32, this.server.ownerUUID);
/*      */       
/* 1030 */       Component $$12 = RealmsMainScreen.getVersionComponent(this.server.activeVersion, -8355712);
/* 1031 */       int $$13 = versionTextX($$3, $$4, $$12);
/* 1032 */       renderClampedName($$0, this.server.getName(), $$10, $$11, $$13, -8355712);
/* 1033 */       if ($$12 != CommonComponents.EMPTY) {
/* 1034 */         $$0.drawString(RealmsMainScreen.this.font, $$12, $$13, $$11, -8355712, false);
/*      */       }
/* 1036 */       $$0.drawString(RealmsMainScreen.this.font, this.server.getDescription(), $$10, secondLineY($$11), -8355712, false);
/*      */       
/* 1038 */       renderThirdLine($$0, $$2, $$3, this.server);
/*      */       
/* 1040 */       renderStatusLights(this.server, $$0, $$3 + $$4, $$2, $$6, $$7);
/* 1041 */       this.tooltip.refreshTooltipForNextRenderPass($$8, isFocused(), new ScreenRectangle($$3, $$2, $$4, $$5));
/*      */     }
/*      */ 
/*      */     
/*      */     public Component getNarration() {
/* 1046 */       return (Component)Component.literal(this.server.name);
/*      */     }
/*      */   }
/*      */   
/*      */   private class ServerEntry
/*      */     extends Entry {
/*      */     private static final int SKIN_HEAD_LARGE_WIDTH = 36;
/*      */     private final RealmsServer serverData;
/*      */     @Nullable
/*      */     private final Tooltip tooltip;
/*      */     
/*      */     public ServerEntry(RealmsServer $$0) {
/* 1058 */       this.serverData = $$0;
/* 1059 */       boolean $$1 = RealmsMainScreen.this.isSelfOwnedServer($$0);
/* 1060 */       if (RealmsMainScreen.isSnapshot() && $$1 && $$0.isSnapshotRealm()) {
/* 1061 */         this.tooltip = Tooltip.create((Component)Component.translatable("mco.snapshot.paired", new Object[] { $$0.parentWorldName }));
/* 1062 */       } else if (!$$1 && $$0.needsUpgrade()) {
/* 1063 */         this.tooltip = Tooltip.create((Component)Component.translatable("mco.snapshot.friendsRealm.upgrade", new Object[] { $$0.owner }));
/* 1064 */       } else if (!$$1 && $$0.needsDowngrade()) {
/* 1065 */         this.tooltip = Tooltip.create((Component)Component.translatable("mco.snapshot.friendsRealm.downgrade", new Object[] { $$0.activeVersion }));
/*      */       } else {
/* 1067 */         this.tooltip = null;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/* 1073 */       if (this.serverData.state == RealmsServer.State.UNINITIALIZED) {
/* 1074 */         $$0.blitSprite(RealmsMainScreen.NEW_REALM_SPRITE, $$3 - 5, $$2 + $$5 / 2 - 10, 40, 20);
/*      */         
/* 1076 */         Objects.requireNonNull(RealmsMainScreen.this.font); int $$10 = $$2 + $$5 / 2 - 9 / 2;
/* 1077 */         $$0.drawString(RealmsMainScreen.this.font, RealmsMainScreen.SERVER_UNITIALIZED_TEXT, $$3 + 40 - 2, $$10, 8388479);
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/* 1082 */       RealmsUtil.renderPlayerFace($$0, $$3, $$2, 32, this.serverData.ownerUUID);
/*      */       
/* 1084 */       renderFirstLine($$0, $$2, $$3, $$4);
/* 1085 */       renderSecondLine($$0, $$2, $$3);
/* 1086 */       renderThirdLine($$0, $$2, $$3, this.serverData);
/*      */       
/* 1088 */       renderStatusLights(this.serverData, $$0, $$3 + $$4, $$2, $$6, $$7);
/*      */       
/* 1090 */       if (this.tooltip != null) {
/* 1091 */         this.tooltip.refreshTooltipForNextRenderPass($$8, isFocused(), new ScreenRectangle($$3, $$2, $$4, $$5));
/*      */       }
/*      */     }
/*      */     
/*      */     private void renderFirstLine(GuiGraphics $$0, int $$1, int $$2, int $$3) {
/* 1096 */       int $$4 = textX($$2);
/* 1097 */       int $$5 = firstLineY($$1);
/*      */       
/* 1099 */       Component $$6 = RealmsMainScreen.getVersionComponent(this.serverData.activeVersion, this.serverData.isCompatible());
/* 1100 */       int $$7 = versionTextX($$2, $$3, $$6);
/* 1101 */       renderClampedName($$0, this.serverData.getName(), $$4, $$5, $$7, -1);
/* 1102 */       if ($$6 != CommonComponents.EMPTY) {
/* 1103 */         $$0.drawString(RealmsMainScreen.this.font, $$6, $$7, $$5, -8355712, false);
/*      */       }
/*      */     }
/*      */     
/*      */     private void renderSecondLine(GuiGraphics $$0, int $$1, int $$2) {
/* 1108 */       int $$3 = textX($$2);
/* 1109 */       int $$4 = firstLineY($$1);
/* 1110 */       int $$5 = secondLineY($$4);
/*      */       
/* 1112 */       if (this.serverData.worldType == RealmsServer.WorldType.MINIGAME) {
/* 1113 */         MutableComponent mutableComponent = Component.literal(this.serverData.getMinigameName()).withStyle(ChatFormatting.GRAY);
/* 1114 */         $$0.drawString(RealmsMainScreen.this.font, (Component)Component.translatable("mco.selectServer.minigameName", new Object[] { mutableComponent }).withColor(-171), $$3, $$5, -1, false);
/*      */       } else {
/* 1116 */         $$0.drawString(RealmsMainScreen.this.font, this.serverData.getDescription(), $$3, secondLineY($$4), -8355712, false);
/*      */       } 
/*      */     }
/*      */     
/*      */     private void playRealm() {
/* 1121 */       RealmsMainScreen.this.minecraft.getSoundManager().play((SoundInstance)SimpleSoundInstance.forUI((Holder)SoundEvents.UI_BUTTON_CLICK, 1.0F));
/* 1122 */       RealmsMainScreen.play(this.serverData, (Screen)RealmsMainScreen.this);
/*      */     }
/*      */     
/*      */     private void createUnitializedRealm() {
/* 1126 */       RealmsMainScreen.this.minecraft.getSoundManager().play((SoundInstance)SimpleSoundInstance.forUI((Holder)SoundEvents.UI_BUTTON_CLICK, 1.0F));
/* 1127 */       RealmsCreateRealmScreen $$0 = new RealmsCreateRealmScreen(RealmsMainScreen.this, this.serverData);
/* 1128 */       RealmsMainScreen.this.minecraft.setScreen((Screen)$$0);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean mouseClicked(double $$0, double $$1, int $$2) {
/* 1133 */       if (this.serverData.state == RealmsServer.State.UNINITIALIZED) {
/* 1134 */         createUnitializedRealm();
/* 1135 */       } else if (RealmsMainScreen.this.shouldPlayButtonBeActive(this.serverData)) {
/* 1136 */         if (Util.getMillis() - RealmsMainScreen.this.lastClickTime < 250L && isFocused()) {
/* 1137 */           playRealm();
/*      */         }
/* 1139 */         RealmsMainScreen.this.lastClickTime = Util.getMillis();
/*      */       } 
/* 1141 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean keyPressed(int $$0, int $$1, int $$2) {
/* 1146 */       if (CommonInputs.selected($$0)) {
/* 1147 */         if (this.serverData.state == RealmsServer.State.UNINITIALIZED) {
/* 1148 */           createUnitializedRealm();
/* 1149 */           return true;
/* 1150 */         }  if (RealmsMainScreen.this.shouldPlayButtonBeActive(this.serverData)) {
/* 1151 */           playRealm();
/* 1152 */           return true;
/*      */         } 
/*      */       } 
/* 1155 */       return super.keyPressed($$0, $$1, $$2);
/*      */     }
/*      */ 
/*      */     
/*      */     public Component getNarration() {
/* 1160 */       if (this.serverData.state == RealmsServer.State.UNINITIALIZED) {
/* 1161 */         return RealmsMainScreen.UNITIALIZED_WORLD_NARRATION;
/*      */       }
/* 1163 */       return (Component)Component.translatable("narrator.select", new Object[] { this.serverData.name });
/*      */     }
/*      */ 
/*      */     
/*      */     public RealmsServer getServer() {
/* 1168 */       return this.serverData;
/*      */     }
/*      */   }
/*      */   
/*      */   boolean isSelfOwnedServer(RealmsServer $$0) {
/* 1173 */     return this.minecraft.isLocalPlayer($$0.ownerUUID);
/*      */   }
/*      */   
/*      */   private boolean isSelfOwnedNonExpiredServer(RealmsServer $$0) {
/* 1177 */     return (isSelfOwnedServer($$0) && !$$0.expired);
/*      */   }
/*      */   
/*      */   private void renderEnvironment(GuiGraphics $$0, String $$1, int $$2) {
/* 1181 */     $$0.pose().pushPose();
/* 1182 */     $$0.pose().translate((this.width / 2 - 25), 20.0F, 0.0F);
/* 1183 */     $$0.pose().mulPose(Axis.ZP.rotationDegrees(-20.0F));
/* 1184 */     $$0.pose().scale(1.5F, 1.5F, 1.5F);
/*      */     
/* 1186 */     $$0.drawString(this.font, $$1, 0, 0, $$2, false);
/*      */     
/* 1188 */     $$0.pose().popPose();
/*      */   }
/*      */   
/*      */   private static class NotificationButton extends SpriteIconButton.CenteredIcon {
/* 1192 */     private static final ResourceLocation[] NOTIFICATION_ICONS = new ResourceLocation[] { new ResourceLocation("notification/1"), new ResourceLocation("notification/2"), new ResourceLocation("notification/3"), new ResourceLocation("notification/4"), new ResourceLocation("notification/5"), new ResourceLocation("notification/more") };
/*      */ 
/*      */     
/*      */     private static final int UNKNOWN_COUNT = 2147483647;
/*      */ 
/*      */     
/*      */     private static final int SIZE = 20;
/*      */ 
/*      */     
/*      */     private static final int SPRITE_SIZE = 14;
/*      */ 
/*      */     
/*      */     private int notificationCount;
/*      */ 
/*      */ 
/*      */     
/*      */     public NotificationButton(Component $$0, ResourceLocation $$1, Button.OnPress $$2) {
/* 1209 */       super(20, 20, $$0, 14, 14, $$1, $$2);
/*      */     }
/*      */     
/*      */     int notificationCount() {
/* 1213 */       return this.notificationCount;
/*      */     }
/*      */     
/*      */     public void setNotificationCount(int $$0) {
/* 1217 */       this.notificationCount = $$0;
/*      */     }
/*      */ 
/*      */     
/*      */     public void renderWidget(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 1222 */       super.renderWidget($$0, $$1, $$2, $$3);
/* 1223 */       if (this.active && this.notificationCount != 0) {
/* 1224 */         drawNotificationCounter($$0);
/*      */       }
/*      */     }
/*      */     
/*      */     private void drawNotificationCounter(GuiGraphics $$0) {
/* 1229 */       $$0.blitSprite(NOTIFICATION_ICONS[Math.min(this.notificationCount, 6) - 1], getX() + getWidth() - 5, getY() - 3, 8, 8);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class CrossButton extends ImageButton {
/* 1234 */     private static final WidgetSprites SPRITES = new WidgetSprites(new ResourceLocation("widget/cross_button"), new ResourceLocation("widget/cross_button_highlighted"));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected CrossButton(Button.OnPress $$0, Component $$1) {
/* 1240 */       super(0, 0, 14, 14, SPRITES, $$0);
/* 1241 */       setTooltip(Tooltip.create($$1));
/*      */     }
/*      */   }
/*      */   
/*      */   private enum LayoutState {
/* 1246 */     LOADING,
/* 1247 */     NO_REALMS,
/* 1248 */     LIST;
/*      */   }
/*      */   
/*      */   private static interface RealmsCall<T> {
/*      */     T request(RealmsClient param1RealmsClient) throws RealmsServiceException;
/*      */   }
/*      */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\RealmsMainScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */