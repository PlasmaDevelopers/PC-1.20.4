/*     */ package com.mojang.realmsclient.gui.screens;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.realmsclient.RealmsMainScreen;
/*     */ import com.mojang.realmsclient.client.RealmsClient;
/*     */ import com.mojang.realmsclient.dto.RealmsServer;
/*     */ import com.mojang.realmsclient.dto.Subscription;
/*     */ import com.mojang.realmsclient.exception.RealmsServiceException;
/*     */ import java.text.DateFormat;
/*     */ import java.util.Calendar;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.TimeZone;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.GameNarrator;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.FittingMultiLineTextWidget;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.screens.ConfirmLinkScreen;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.realms.RealmsScreen;
/*     */ import net.minecraft.util.CommonLinks;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class RealmsSubscriptionInfoScreen extends RealmsScreen {
/*  29 */   static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*  31 */   private static final Component SUBSCRIPTION_TITLE = (Component)Component.translatable("mco.configure.world.subscription.title");
/*  32 */   private static final Component SUBSCRIPTION_START_LABEL = (Component)Component.translatable("mco.configure.world.subscription.start");
/*  33 */   private static final Component TIME_LEFT_LABEL = (Component)Component.translatable("mco.configure.world.subscription.timeleft");
/*  34 */   private static final Component DAYS_LEFT_LABEL = (Component)Component.translatable("mco.configure.world.subscription.recurring.daysleft");
/*  35 */   private static final Component SUBSCRIPTION_EXPIRED_TEXT = (Component)Component.translatable("mco.configure.world.subscription.expired");
/*  36 */   private static final Component SUBSCRIPTION_LESS_THAN_A_DAY_TEXT = (Component)Component.translatable("mco.configure.world.subscription.less_than_a_day");
/*  37 */   private static final Component UNKNOWN = (Component)Component.translatable("mco.configure.world.subscription.unknown");
/*  38 */   private static final Component RECURRING_INFO = (Component)Component.translatable("mco.configure.world.subscription.recurring.info");
/*     */   
/*     */   private final Screen lastScreen;
/*     */   
/*     */   final RealmsServer serverData;
/*     */   final Screen mainScreen;
/*  44 */   private Component daysLeft = UNKNOWN;
/*  45 */   private Component startDate = UNKNOWN;
/*     */   @Nullable
/*     */   private Subscription.SubscriptionType type;
/*     */   
/*     */   public RealmsSubscriptionInfoScreen(Screen $$0, RealmsServer $$1, Screen $$2) {
/*  50 */     super(GameNarrator.NO_TITLE);
/*  51 */     this.lastScreen = $$0;
/*  52 */     this.serverData = $$1;
/*  53 */     this.mainScreen = $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void init() {
/*  58 */     getSubscription(this.serverData.id);
/*     */     
/*  60 */     addRenderableWidget((GuiEventListener)Button.builder((Component)Component.translatable("mco.configure.world.subscription.extend"), $$0 -> ConfirmLinkScreen.confirmLinkNow((Screen)this, CommonLinks.extendRealms(this.serverData.remoteSubscriptionId, this.minecraft.getUser().getProfileId())))
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  65 */         .bounds(this.width / 2 - 100, row(6), 200, 20).build());
/*     */     
/*  67 */     if (this.serverData.expired) {
/*  68 */       addRenderableWidget((GuiEventListener)Button.builder((Component)Component.translatable("mco.configure.world.delete.button"), $$0 -> {
/*     */               MutableComponent mutableComponent1 = Component.translatable("mco.configure.world.delete.question.line1");
/*     */               MutableComponent mutableComponent2 = Component.translatable("mco.configure.world.delete.question.line2");
/*     */               this.minecraft.setScreen((Screen)new RealmsLongConfirmationScreen(this::deleteRealm, RealmsLongConfirmationScreen.Type.WARNING, (Component)mutableComponent1, (Component)mutableComponent2, true));
/*  72 */             }).bounds(this.width / 2 - 100, row(10), 200, 20).build());
/*     */     }
/*  74 */     else if (RealmsMainScreen.isSnapshot() && this.serverData.parentWorldName != null) {
/*  75 */       addRenderableWidget((GuiEventListener)(new FittingMultiLineTextWidget(this.width / 2 - 100, row(8), 200, 46, 
/*  76 */             (Component)Component.translatable("mco.snapshot.subscription.info", new Object[] { this.serverData.parentWorldName }), this.font)).setColor(-6250336));
/*     */     } else {
/*  78 */       addRenderableWidget((GuiEventListener)(new FittingMultiLineTextWidget(this.width / 2 - 100, row(8), 200, 46, RECURRING_INFO, this.font)).setColor(-6250336));
/*     */     } 
/*     */ 
/*     */     
/*  82 */     addRenderableWidget((GuiEventListener)Button.builder(CommonComponents.GUI_BACK, $$0 -> onClose()).bounds(this.width / 2 - 100, row(12), 200, 20).build());
/*     */   }
/*     */ 
/*     */   
/*     */   public Component getNarrationMessage() {
/*  87 */     return CommonComponents.joinLines(new Component[] { SUBSCRIPTION_TITLE, SUBSCRIPTION_START_LABEL, this.startDate, TIME_LEFT_LABEL, this.daysLeft });
/*     */   }
/*     */   
/*     */   private void deleteRealm(boolean $$0) {
/*  91 */     if ($$0) {
/*  92 */       (new Thread("Realms-delete-realm")
/*     */         {
/*     */           public void run() {
/*     */             try {
/*  96 */               RealmsClient $$0 = RealmsClient.create();
/*  97 */               $$0.deleteWorld(RealmsSubscriptionInfoScreen.this.serverData.id);
/*  98 */             } catch (RealmsServiceException $$1) {
/*  99 */               RealmsSubscriptionInfoScreen.LOGGER.error("Couldn't delete world", (Throwable)$$1);
/*     */             } 
/*     */             
/* 102 */             RealmsSubscriptionInfoScreen.this.minecraft.execute(() -> RealmsSubscriptionInfoScreen.this.minecraft.setScreen(RealmsSubscriptionInfoScreen.this.mainScreen));
/*     */           }
/* 104 */         }).start();
/*     */     }
/*     */     
/* 107 */     this.minecraft.setScreen((Screen)this);
/*     */   }
/*     */   
/*     */   private void getSubscription(long $$0) {
/* 111 */     RealmsClient $$1 = RealmsClient.create();
/*     */     try {
/* 113 */       Subscription $$2 = $$1.subscriptionFor($$0);
/* 114 */       this.daysLeft = daysLeftPresentation($$2.daysLeft);
/* 115 */       this.startDate = localPresentation($$2.startDate);
/* 116 */       this.type = $$2.type;
/* 117 */     } catch (RealmsServiceException $$3) {
/* 118 */       LOGGER.error("Couldn't get subscription", (Throwable)$$3);
/* 119 */       this.minecraft.setScreen((Screen)new RealmsGenericErrorScreen($$3, this.lastScreen));
/*     */     } 
/*     */   }
/*     */   
/*     */   private static Component localPresentation(long $$0) {
/* 124 */     Calendar $$1 = new GregorianCalendar(TimeZone.getDefault());
/*     */     
/* 126 */     $$1.setTimeInMillis($$0);
/* 127 */     return (Component)Component.literal(DateFormat.getDateTimeInstance().format($$1.getTime()));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClose() {
/* 132 */     this.minecraft.setScreen(this.lastScreen);
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 137 */     super.render($$0, $$1, $$2, $$3);
/*     */     
/* 139 */     int $$4 = this.width / 2 - 100;
/*     */     
/* 141 */     $$0.drawCenteredString(this.font, SUBSCRIPTION_TITLE, this.width / 2, 17, -1);
/* 142 */     $$0.drawString(this.font, SUBSCRIPTION_START_LABEL, $$4, row(0), -6250336, false);
/* 143 */     $$0.drawString(this.font, this.startDate, $$4, row(1), -1, false);
/*     */     
/* 145 */     if (this.type == Subscription.SubscriptionType.NORMAL) {
/* 146 */       $$0.drawString(this.font, TIME_LEFT_LABEL, $$4, row(3), -6250336, false);
/* 147 */     } else if (this.type == Subscription.SubscriptionType.RECURRING) {
/* 148 */       $$0.drawString(this.font, DAYS_LEFT_LABEL, $$4, row(3), -6250336, false);
/*     */     } 
/*     */     
/* 151 */     $$0.drawString(this.font, this.daysLeft, $$4, row(4), -1, false);
/*     */   }
/*     */   
/*     */   private Component daysLeftPresentation(int $$0) {
/* 155 */     if ($$0 < 0 && this.serverData.expired)
/* 156 */       return SUBSCRIPTION_EXPIRED_TEXT; 
/* 157 */     if ($$0 <= 1) {
/* 158 */       return SUBSCRIPTION_LESS_THAN_A_DAY_TEXT;
/*     */     }
/*     */     
/* 161 */     int $$1 = $$0 / 30;
/* 162 */     int $$2 = $$0 % 30;
/* 163 */     boolean $$3 = ($$1 > 0);
/* 164 */     boolean $$4 = ($$2 > 0);
/*     */     
/* 166 */     if ($$3 && $$4)
/* 167 */       return (Component)Component.translatable("mco.configure.world.subscription.remaining.months.days", new Object[] { Integer.valueOf($$1), Integer.valueOf($$2) }); 
/* 168 */     if ($$3)
/* 169 */       return (Component)Component.translatable("mco.configure.world.subscription.remaining.months", new Object[] { Integer.valueOf($$1) }); 
/* 170 */     if ($$4) {
/* 171 */       return (Component)Component.translatable("mco.configure.world.subscription.remaining.days", new Object[] { Integer.valueOf($$2) });
/*     */     }
/* 173 */     return (Component)Component.empty();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\gui\screens\RealmsSubscriptionInfoScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */