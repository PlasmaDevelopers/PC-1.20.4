/*     */ package com.mojang.realmsclient.util.task;
/*     */ 
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.realmsclient.client.RealmsClient;
/*     */ import com.mojang.realmsclient.dto.RealmsServer;
/*     */ import com.mojang.realmsclient.dto.RealmsServerAddress;
/*     */ import com.mojang.realmsclient.exception.RealmsServiceException;
/*     */ import com.mojang.realmsclient.exception.RetryCallException;
/*     */ import com.mojang.realmsclient.gui.screens.RealmsGenericErrorScreen;
/*     */ import com.mojang.realmsclient.gui.screens.RealmsLongConfirmationScreen;
/*     */ import com.mojang.realmsclient.gui.screens.RealmsLongRunningMcoTaskScreen;
/*     */ import com.mojang.realmsclient.gui.screens.RealmsLongRunningMcoTickTaskScreen;
/*     */ import com.mojang.realmsclient.gui.screens.RealmsTermsScreen;
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
/*     */ import java.net.URL;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.CancellationException;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.TimeoutException;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.screens.GenericDirtMessageScreen;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.client.resources.server.DownloadedPackSource;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class GetServerDetailsTask
/*     */   extends LongRunningTask
/*     */ {
/*  32 */   private static final Component APPLYING_PACK_TEXT = (Component)Component.translatable("multiplayer.applyingPack");
/*     */   
/*  34 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*  36 */   private static final Component TITLE = (Component)Component.translatable("mco.connect.connecting");
/*     */   
/*     */   private final RealmsServer server;
/*     */   private final Screen lastScreen;
/*     */   
/*     */   public GetServerDetailsTask(Screen $$0, RealmsServer $$1) {
/*  42 */     this.lastScreen = $$0;
/*  43 */     this.server = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void run() {
/*     */     RealmsServerAddress $$0;
/*     */     try {
/*  50 */       $$0 = fetchServerAddress();
/*  51 */     } catch (CancellationException $$1) {
/*  52 */       LOGGER.info("User aborted connecting to realms");
/*     */       return;
/*  54 */     } catch (RealmsServiceException $$2) {
/*  55 */       boolean $$3; switch ($$2.realmsError.errorCode()) {
/*     */         case 6002:
/*  57 */           setScreen((Screen)new RealmsTermsScreen(this.lastScreen, this.server));
/*     */           return;
/*     */         case 6006:
/*  60 */           $$3 = Minecraft.getInstance().isLocalPlayer(this.server.ownerUUID);
/*  61 */           if ($$3) {  } else {  }  setScreen(
/*     */               
/*  63 */               (Screen)new RealmsGenericErrorScreen((Component)Component.translatable("mco.brokenworld.nonowner.title"), (Component)Component.translatable("mco.brokenworld.nonowner.error"), this.lastScreen));
/*     */           return;
/*     */       } 
/*  66 */       error($$2);
/*  67 */       LOGGER.error("Couldn't connect to world", (Throwable)$$2);
/*     */       
/*     */       return;
/*  70 */     } catch (TimeoutException $$4) {
/*  71 */       error((Component)Component.translatable("mco.errorMessage.connectionFailure"));
/*     */       return;
/*  73 */     } catch (Exception $$5) {
/*  74 */       LOGGER.error("Couldn't connect to world", $$5);
/*  75 */       error($$5);
/*     */       
/*     */       return;
/*     */     } 
/*  79 */     boolean $$7 = ($$0.resourcePackUrl != null && $$0.resourcePackHash != null);
/*     */ 
/*     */ 
/*     */     
/*  83 */     Screen $$8 = $$7 ? (Screen)resourcePackDownloadConfirmationScreen($$0, generatePackId(this.server), this::connectScreen) : (Screen)connectScreen($$0);
/*     */     
/*  85 */     setScreen($$8);
/*     */   }
/*     */   
/*     */   private static UUID generatePackId(RealmsServer $$0) {
/*  89 */     if ($$0.minigameName != null) {
/*  90 */       return UUID.nameUUIDFromBytes(("minigame:" + $$0.minigameName).getBytes(StandardCharsets.UTF_8));
/*     */     }
/*  92 */     return UUID.nameUUIDFromBytes(("realms:" + $$0.name + ":" + $$0.activeSlot).getBytes(StandardCharsets.UTF_8));
/*     */   }
/*     */ 
/*     */   
/*     */   public Component getTitle() {
/*  97 */     return TITLE;
/*     */   }
/*     */   
/*     */   private RealmsServerAddress fetchServerAddress() throws RealmsServiceException, TimeoutException, CancellationException {
/* 101 */     RealmsClient $$0 = RealmsClient.create();
/* 102 */     for (int $$1 = 0; $$1 < 40; $$1++) {
/* 103 */       if (aborted()) {
/* 104 */         throw new CancellationException();
/*     */       }
/*     */       
/*     */       try {
/* 108 */         return $$0.join(this.server.id);
/* 109 */       } catch (RetryCallException $$2) {
/* 110 */         pause($$2.delaySeconds);
/*     */       } 
/*     */     } 
/* 113 */     throw new TimeoutException();
/*     */   }
/*     */   
/*     */   public RealmsLongRunningMcoTaskScreen connectScreen(RealmsServerAddress $$0) {
/* 117 */     return (RealmsLongRunningMcoTaskScreen)new RealmsLongRunningMcoTickTaskScreen(this.lastScreen, new ConnectTask(this.lastScreen, this.server, $$0));
/*     */   }
/*     */   
/*     */   private RealmsLongConfirmationScreen resourcePackDownloadConfirmationScreen(RealmsServerAddress $$0, UUID $$1, Function<RealmsServerAddress, Screen> $$2) {
/* 121 */     BooleanConsumer $$3 = $$3 -> {
/*     */         if (!$$3) {
/*     */           setScreen(this.lastScreen);
/*     */ 
/*     */           
/*     */           return;
/*     */         } 
/*     */ 
/*     */         
/*     */         setScreen((Screen)new GenericDirtMessageScreen(APPLYING_PACK_TEXT));
/*     */ 
/*     */         
/*     */         scheduleResourcePackDownload($$0, $$1).thenRun(()).exceptionally(());
/*     */       };
/*     */ 
/*     */     
/* 137 */     return new RealmsLongConfirmationScreen($$3, RealmsLongConfirmationScreen.Type.INFO, 
/*     */ 
/*     */         
/* 140 */         (Component)Component.translatable("mco.configure.world.resourcepack.question.line1"), 
/* 141 */         (Component)Component.translatable("mco.configure.world.resourcepack.question.line2"), true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private CompletableFuture<?> scheduleResourcePackDownload(RealmsServerAddress $$0, UUID $$1) {
/*     */     try {
/* 148 */       DownloadedPackSource $$2 = Minecraft.getInstance().getDownloadedPackSource();
/* 149 */       CompletableFuture<Void> $$3 = $$2.waitForPackFeedback($$1);
/* 150 */       $$2.allowServerPacks();
/* 151 */       $$2.pushPack($$1, new URL($$0.resourcePackUrl), $$0.resourcePackHash);
/* 152 */       return $$3;
/* 153 */     } catch (Exception $$4) {
/* 154 */       CompletableFuture<Void> $$5 = new CompletableFuture<>();
/* 155 */       $$5.completeExceptionally($$4);
/* 156 */       return $$5;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclien\\util\task\GetServerDetailsTask.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */