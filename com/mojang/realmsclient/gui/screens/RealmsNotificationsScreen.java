/*     */ package com.mojang.realmsclient.gui.screens;
/*     */ 
/*     */ import com.mojang.realmsclient.RealmsAvailability;
/*     */ import com.mojang.realmsclient.dto.RealmsNews;
/*     */ import com.mojang.realmsclient.dto.RealmsNotification;
/*     */ import com.mojang.realmsclient.gui.RealmsDataFetcher;
/*     */ import com.mojang.realmsclient.gui.task.DataFetcher;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.GameNarrator;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.realms.RealmsScreen;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ 
/*     */ public class RealmsNotificationsScreen extends RealmsScreen {
/*  18 */   private static final ResourceLocation UNSEEN_NOTIFICATION_SPRITE = new ResourceLocation("icon/unseen_notification");
/*  19 */   private static final ResourceLocation NEWS_SPRITE = new ResourceLocation("icon/news");
/*  20 */   private static final ResourceLocation INVITE_SPRITE = new ResourceLocation("icon/invite");
/*  21 */   private static final ResourceLocation TRIAL_AVAILABLE_SPRITE = new ResourceLocation("icon/trial_available");
/*     */   
/*     */   private final CompletableFuture<Boolean> validClient;
/*     */   @Nullable
/*     */   private DataFetcher.Subscription realmsDataSubscription;
/*     */   @Nullable
/*     */   private DataFetcherConfiguration currentConfiguration;
/*     */   private volatile int numberOfPendingInvites;
/*     */   private static boolean trialAvailable;
/*     */   private static boolean hasUnreadNews;
/*     */   private static boolean hasUnseenNotifications;
/*     */   private final DataFetcherConfiguration showAll;
/*     */   private final DataFetcherConfiguration onlyNotifications;
/*     */   
/*     */   public RealmsNotificationsScreen() {
/*  36 */     super(GameNarrator.NO_TITLE);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     this.validClient = RealmsAvailability.get().thenApply($$0 -> Boolean.valueOf(($$0.type() == RealmsAvailability.Type.SUCCESS)));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 165 */     this.showAll = new DataFetcherConfiguration()
/*     */       {
/*     */         public DataFetcher.Subscription initDataFetcher(RealmsDataFetcher $$0) {
/* 168 */           DataFetcher.Subscription $$1 = $$0.dataFetcher.createSubscription();
/*     */           
/* 170 */           RealmsNotificationsScreen.this.addNewsAndInvitesSubscriptions($$0, $$1);
/* 171 */           RealmsNotificationsScreen.this.addNotificationsSubscriptions($$0, $$1);
/*     */           
/* 173 */           return $$1;
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean showOldNotifications() {
/* 178 */           return true;
/*     */         }
/*     */       };
/*     */     
/* 182 */     this.onlyNotifications = new DataFetcherConfiguration()
/*     */       {
/*     */         public DataFetcher.Subscription initDataFetcher(RealmsDataFetcher $$0) {
/* 185 */           DataFetcher.Subscription $$1 = $$0.dataFetcher.createSubscription();
/*     */           
/* 187 */           RealmsNotificationsScreen.this.addNotificationsSubscriptions($$0, $$1);
/*     */           
/* 189 */           return $$1;
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean showOldNotifications() {
/* 194 */           return false;
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public void init() {
/*     */     if (this.realmsDataSubscription != null)
/*     */       this.realmsDataSubscription.forceUpdate(); 
/*     */   }
/*     */   
/*     */   public void added() {
/*     */     super.added();
/*     */     (this.minecraft.realmsDataFetcher()).notificationsTask.reset();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private DataFetcherConfiguration getConfiguration() {
/*     */     boolean $$0 = (inTitleScreen() && ((Boolean)this.validClient.getNow(Boolean.valueOf(false))).booleanValue());
/*     */     if (!$$0)
/*     */       return null; 
/*     */     return getRealmsNotificationsEnabled() ? this.showAll : this.onlyNotifications;
/*     */   }
/*     */   
/*     */   public void tick() {
/*     */     DataFetcherConfiguration $$0 = getConfiguration();
/*     */     if (!Objects.equals(this.currentConfiguration, $$0)) {
/*     */       this.currentConfiguration = $$0;
/*     */       if (this.currentConfiguration != null) {
/*     */         this.realmsDataSubscription = this.currentConfiguration.initDataFetcher(this.minecraft.realmsDataFetcher());
/*     */       } else {
/*     */         this.realmsDataSubscription = null;
/*     */       } 
/*     */     } 
/*     */     if (this.realmsDataSubscription != null)
/*     */       this.realmsDataSubscription.tick(); 
/*     */   }
/*     */   
/*     */   private boolean getRealmsNotificationsEnabled() {
/*     */     return ((Boolean)this.minecraft.options.realmsNotifications().get()).booleanValue();
/*     */   }
/*     */   
/*     */   private boolean inTitleScreen() {
/*     */     return this.minecraft.screen instanceof net.minecraft.client.gui.screens.TitleScreen;
/*     */   }
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/*     */     super.render($$0, $$1, $$2, $$3);
/*     */     if (((Boolean)this.validClient.getNow(Boolean.valueOf(false))).booleanValue())
/*     */       drawIcons($$0); 
/*     */   }
/*     */   
/*     */   public void renderBackground(GuiGraphics $$0, int $$1, int $$2, float $$3) {}
/*     */   
/*     */   private void drawIcons(GuiGraphics $$0) {
/*     */     int $$1 = this.numberOfPendingInvites;
/*     */     int $$2 = 24;
/*     */     int $$3 = this.height / 4 + 48;
/*     */     int $$4 = this.width / 2 + 100;
/*     */     int $$5 = $$3 + 48 + 2;
/*     */     int $$6 = $$4 - 3;
/*     */     if (hasUnseenNotifications) {
/*     */       $$0.blitSprite(UNSEEN_NOTIFICATION_SPRITE, $$6 - 12, $$5 + 3, 10, 10);
/*     */       $$6 -= 16;
/*     */     } 
/*     */     if (this.currentConfiguration != null && this.currentConfiguration.showOldNotifications()) {
/*     */       if (hasUnreadNews) {
/*     */         $$0.blitSprite(NEWS_SPRITE, $$6 - 14, $$5 + 1, 14, 14);
/*     */         $$6 -= 16;
/*     */       } 
/*     */       if ($$1 != 0) {
/*     */         $$0.blitSprite(INVITE_SPRITE, $$6 - 14, $$5 + 1, 14, 14);
/*     */         $$6 -= 16;
/*     */       } 
/*     */       if (trialAvailable)
/*     */         $$0.blitSprite(TRIAL_AVAILABLE_SPRITE, $$6 - 10, $$5 + 4, 8, 8); 
/*     */     } 
/*     */   }
/*     */   
/*     */   void addNewsAndInvitesSubscriptions(RealmsDataFetcher $$0, DataFetcher.Subscription $$1) {
/*     */     $$1.subscribe($$0.pendingInvitesTask, $$0 -> this.numberOfPendingInvites = $$0.intValue());
/*     */     $$1.subscribe($$0.trialAvailabilityTask, $$0 -> trialAvailable = $$0.booleanValue());
/*     */     $$1.subscribe($$0.newsTask, $$1 -> {
/*     */           $$0.newsManager.updateUnreadNews($$1);
/*     */           hasUnreadNews = $$0.newsManager.hasUnreadNews();
/*     */         });
/*     */   }
/*     */   
/*     */   void addNotificationsSubscriptions(RealmsDataFetcher $$0, DataFetcher.Subscription $$1) {
/*     */     $$1.subscribe($$0.notificationsTask, $$0 -> {
/*     */           hasUnseenNotifications = false;
/*     */           for (RealmsNotification $$1 : $$0) {
/*     */             if (!$$1.seen()) {
/*     */               hasUnseenNotifications = true;
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         });
/*     */   }
/*     */   
/*     */   private static interface DataFetcherConfiguration {
/*     */     DataFetcher.Subscription initDataFetcher(RealmsDataFetcher param1RealmsDataFetcher);
/*     */     
/*     */     boolean showOldNotifications();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\gui\screens\RealmsNotificationsScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */