/*     */ package net.minecraft.client.multiplayer;
/*     */ 
/*     */ import com.google.common.base.Supplier;
/*     */ import com.google.common.base.Suppliers;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.resources.DefaultPlayerSkin;
/*     */ import net.minecraft.client.resources.PlayerSkin;
/*     */ import net.minecraft.client.resources.SkinManager;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.RemoteChatSession;
/*     */ import net.minecraft.network.chat.SignedMessageValidator;
/*     */ import net.minecraft.world.entity.player.ProfilePublicKey;
/*     */ import net.minecraft.world.level.GameType;
/*     */ import net.minecraft.world.scores.PlayerTeam;
/*     */ 
/*     */ public class PlayerInfo {
/*     */   private final GameProfile profile;
/*     */   private final Supplier<PlayerSkin> skinLookup;
/*  23 */   private GameType gameMode = GameType.DEFAULT_MODE;
/*     */   private int latency;
/*     */   @Nullable
/*     */   private Component tabListDisplayName;
/*     */   @Nullable
/*     */   private RemoteChatSession chatSession;
/*     */   private SignedMessageValidator messageValidator;
/*     */   
/*     */   public PlayerInfo(GameProfile $$0, boolean $$1) {
/*  32 */     this.profile = $$0;
/*  33 */     this.messageValidator = fallbackMessageValidator($$1);
/*     */     
/*  35 */     Supplier supplier = Suppliers.memoize(() -> createSkinLookup($$0));
/*  36 */     this.skinLookup = (() -> (PlayerSkin)((Supplier<PlayerSkin>)$$0.get()).get());
/*     */   }
/*     */   
/*     */   private static Supplier<PlayerSkin> createSkinLookup(GameProfile $$0) {
/*  40 */     Minecraft $$1 = Minecraft.getInstance();
/*  41 */     SkinManager $$2 = $$1.getSkinManager();
/*  42 */     CompletableFuture<PlayerSkin> $$3 = $$2.getOrLoad($$0);
/*     */ 
/*     */     
/*  45 */     boolean $$4 = !$$1.isLocalPlayer($$0.getId());
/*  46 */     PlayerSkin $$5 = DefaultPlayerSkin.get($$0);
/*  47 */     return () -> {
/*     */         PlayerSkin $$3 = $$0.getNow($$1);
/*  49 */         return ($$2 && !$$3.secure()) ? $$1 : $$3;
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GameProfile getProfile() {
/*  57 */     return this.profile;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public RemoteChatSession getChatSession() {
/*  62 */     return this.chatSession;
/*     */   }
/*     */   
/*     */   public SignedMessageValidator getMessageValidator() {
/*  66 */     return this.messageValidator;
/*     */   }
/*     */   
/*     */   public boolean hasVerifiableChat() {
/*  70 */     return (this.chatSession != null);
/*     */   }
/*     */   
/*     */   protected void setChatSession(RemoteChatSession $$0) {
/*  74 */     this.chatSession = $$0;
/*  75 */     this.messageValidator = $$0.createMessageValidator(ProfilePublicKey.EXPIRY_GRACE_PERIOD);
/*     */   }
/*     */   
/*     */   protected void clearChatSession(boolean $$0) {
/*  79 */     this.chatSession = null;
/*  80 */     this.messageValidator = fallbackMessageValidator($$0);
/*     */   }
/*     */   
/*     */   private static SignedMessageValidator fallbackMessageValidator(boolean $$0) {
/*  84 */     return $$0 ? SignedMessageValidator.REJECT_ALL : SignedMessageValidator.ACCEPT_UNSIGNED;
/*     */   }
/*     */   
/*     */   public GameType getGameMode() {
/*  88 */     return this.gameMode;
/*     */   }
/*     */   
/*     */   protected void setGameMode(GameType $$0) {
/*  92 */     this.gameMode = $$0;
/*     */   }
/*     */   
/*     */   public int getLatency() {
/*  96 */     return this.latency;
/*     */   }
/*     */   
/*     */   protected void setLatency(int $$0) {
/* 100 */     this.latency = $$0;
/*     */   }
/*     */   
/*     */   public PlayerSkin getSkin() {
/* 104 */     return this.skinLookup.get();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public PlayerTeam getTeam() {
/* 109 */     return (Minecraft.getInstance()).level.getScoreboard().getPlayersTeam(getProfile().getName());
/*     */   }
/*     */   
/*     */   public void setTabListDisplayName(@Nullable Component $$0) {
/* 113 */     this.tabListDisplayName = $$0;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Component getTabListDisplayName() {
/* 118 */     return this.tabListDisplayName;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\multiplayer\PlayerInfo.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */