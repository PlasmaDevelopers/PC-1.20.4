/*    */ package net.minecraft.client.gui.screens.social;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import com.google.common.collect.Sets;
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import com.mojang.authlib.minecraft.UserApiService;
/*    */ import java.util.Map;
/*    */ import java.util.Objects;
/*    */ import java.util.Set;
/*    */ import java.util.UUID;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.screens.Screen;
/*    */ import net.minecraft.client.multiplayer.PlayerInfo;
/*    */ 
/*    */ public class PlayerSocialManager {
/* 18 */   private final Set<UUID> hiddenPlayers = Sets.newHashSet(); private final Minecraft minecraft;
/*    */   private final UserApiService service;
/* 20 */   private final Map<String, UUID> discoveredNamesToUUID = Maps.newHashMap();
/*    */   private boolean onlineMode;
/* 22 */   private CompletableFuture<?> pendingBlockListRefresh = CompletableFuture.completedFuture(null);
/*    */   
/*    */   public PlayerSocialManager(Minecraft $$0, UserApiService $$1) {
/* 25 */     this.minecraft = $$0;
/* 26 */     this.service = $$1;
/*    */   }
/*    */   
/*    */   public void hidePlayer(UUID $$0) {
/* 30 */     this.hiddenPlayers.add($$0);
/*    */   }
/*    */   
/*    */   public void showPlayer(UUID $$0) {
/* 34 */     this.hiddenPlayers.remove($$0);
/*    */   }
/*    */   
/*    */   public boolean shouldHideMessageFrom(UUID $$0) {
/* 38 */     return (isHidden($$0) || isBlocked($$0));
/*    */   }
/*    */   
/*    */   public boolean isHidden(UUID $$0) {
/* 42 */     return this.hiddenPlayers.contains($$0);
/*    */   }
/*    */   
/*    */   public void startOnlineMode() {
/* 46 */     this.onlineMode = true;
/* 47 */     Objects.requireNonNull(this.service); this.pendingBlockListRefresh = this.pendingBlockListRefresh.thenRunAsync(this.service::refreshBlockList, Util.ioPool());
/*    */   }
/*    */   
/*    */   public void stopOnlineMode() {
/* 51 */     this.onlineMode = false;
/*    */   }
/*    */   
/*    */   public boolean isBlocked(UUID $$0) {
/* 55 */     if (!this.onlineMode) {
/* 56 */       return false;
/*    */     }
/* 58 */     this.pendingBlockListRefresh.join();
/* 59 */     return this.service.isBlockedPlayer($$0);
/*    */   }
/*    */   
/*    */   public Set<UUID> getHiddenPlayers() {
/* 63 */     return this.hiddenPlayers;
/*    */   }
/*    */   
/*    */   public UUID getDiscoveredUUID(String $$0) {
/* 67 */     return this.discoveredNamesToUUID.getOrDefault($$0, Util.NIL_UUID);
/*    */   }
/*    */   
/*    */   public void addPlayer(PlayerInfo $$0) {
/* 71 */     GameProfile $$1 = $$0.getProfile();
/* 72 */     this.discoveredNamesToUUID.put($$1.getName(), $$1.getId());
/*    */     
/* 74 */     Screen screen = this.minecraft.screen; if (screen instanceof SocialInteractionsScreen) { SocialInteractionsScreen $$2 = (SocialInteractionsScreen)screen;
/* 75 */       $$2.onAddPlayer($$0); }
/*    */   
/*    */   }
/*    */   
/*    */   public void removePlayer(UUID $$0) {
/* 80 */     Screen screen = this.minecraft.screen; if (screen instanceof SocialInteractionsScreen) { SocialInteractionsScreen $$1 = (SocialInteractionsScreen)screen;
/* 81 */       $$1.onRemovePlayer($$0); }
/*    */   
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\social\PlayerSocialManager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */