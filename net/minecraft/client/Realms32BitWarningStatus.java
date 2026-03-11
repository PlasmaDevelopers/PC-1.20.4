/*    */ package net.minecraft.client;
/*    */ 
/*    */ import com.mojang.logging.LogUtils;
/*    */ import com.mojang.realmsclient.client.RealmsClient;
/*    */ import com.mojang.realmsclient.dto.RealmsServer;
/*    */ import com.mojang.realmsclient.exception.RealmsServiceException;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import java.util.concurrent.CompletionException;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.client.gui.screens.Screen;
/*    */ import net.minecraft.client.gui.screens.multiplayer.Realms32bitWarningScreen;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class Realms32BitWarningStatus {
/* 16 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   private final Minecraft minecraft;
/*    */   @Nullable
/*    */   private CompletableFuture<Boolean> subscriptionCheck;
/*    */   private boolean warningScreenShown;
/*    */   
/*    */   public Realms32BitWarningStatus(Minecraft $$0) {
/* 23 */     this.minecraft = $$0;
/*    */   }
/*    */   
/*    */   public void showRealms32BitWarningIfNeeded(Screen $$0) {
/* 27 */     if (!this.minecraft.is64Bit() && !this.minecraft.options.skipRealms32bitWarning && !this.warningScreenShown && checkForRealmsSubscription().booleanValue()) {
/* 28 */       this.minecraft.setScreen((Screen)new Realms32bitWarningScreen($$0));
/* 29 */       this.warningScreenShown = true;
/*    */     } 
/*    */   }
/*    */   
/*    */   private Boolean checkForRealmsSubscription() {
/* 34 */     if (this.subscriptionCheck == null) {
/* 35 */       this.subscriptionCheck = CompletableFuture.supplyAsync(this::hasRealmsSubscription, Util.backgroundExecutor());
/*    */     }
/*    */     
/*    */     try {
/* 39 */       return this.subscriptionCheck.getNow(Boolean.valueOf(false));
/* 40 */     } catch (CompletionException $$0) {
/* 41 */       LOGGER.warn("Failed to retrieve realms subscriptions", $$0);
/* 42 */       this.warningScreenShown = true;
/* 43 */       return Boolean.valueOf(false);
/*    */     } 
/*    */   }
/*    */   
/*    */   private boolean hasRealmsSubscription() {
/*    */     try {
/* 49 */       return (RealmsClient.create(this.minecraft).listWorlds()).servers.stream().anyMatch($$0 -> (!$$0.expired && this.minecraft.isLocalPlayer($$0.ownerUUID)));
/* 50 */     } catch (RealmsServiceException $$0) {
/* 51 */       return false;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\Realms32BitWarningStatus.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */