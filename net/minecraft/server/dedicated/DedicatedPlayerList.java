/*     */ package net.minecraft.server.dedicated;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.io.IOException;
/*     */ import net.minecraft.core.LayeredRegistryAccess;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.server.RegistryLayer;
/*     */ import net.minecraft.server.players.PlayerList;
/*     */ import net.minecraft.world.level.storage.PlayerDataStorage;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class DedicatedPlayerList extends PlayerList {
/*  14 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   public DedicatedPlayerList(DedicatedServer $$0, LayeredRegistryAccess<RegistryLayer> $$1, PlayerDataStorage $$2) {
/*  17 */     super($$0, $$1, $$2, ($$0.getProperties()).maxPlayers);
/*     */     
/*  19 */     DedicatedServerProperties $$3 = $$0.getProperties();
/*  20 */     setViewDistance($$3.viewDistance);
/*  21 */     setSimulationDistance($$3.simulationDistance);
/*  22 */     super.setUsingWhiteList(((Boolean)$$3.whiteList.get()).booleanValue());
/*     */     
/*  24 */     loadUserBanList();
/*  25 */     saveUserBanList();
/*  26 */     loadIpBanList();
/*  27 */     saveIpBanList();
/*  28 */     loadOps();
/*  29 */     loadWhiteList();
/*  30 */     saveOps();
/*  31 */     if (!getWhiteList().getFile().exists()) {
/*  32 */       saveWhiteList();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUsingWhiteList(boolean $$0) {
/*  38 */     super.setUsingWhiteList($$0);
/*  39 */     getServer().storeUsingWhiteList($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void op(GameProfile $$0) {
/*  44 */     super.op($$0);
/*  45 */     saveOps();
/*     */   }
/*     */ 
/*     */   
/*     */   public void deop(GameProfile $$0) {
/*  50 */     super.deop($$0);
/*  51 */     saveOps();
/*     */   }
/*     */ 
/*     */   
/*     */   public void reloadWhiteList() {
/*  56 */     loadWhiteList();
/*     */   }
/*     */   
/*     */   private void saveIpBanList() {
/*     */     try {
/*  61 */       getIpBans().save();
/*  62 */     } catch (IOException $$0) {
/*  63 */       LOGGER.warn("Failed to save ip banlist: ", $$0);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void saveUserBanList() {
/*     */     try {
/*  69 */       getBans().save();
/*  70 */     } catch (IOException $$0) {
/*  71 */       LOGGER.warn("Failed to save user banlist: ", $$0);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void loadIpBanList() {
/*     */     try {
/*  77 */       getIpBans().load();
/*  78 */     } catch (IOException $$0) {
/*  79 */       LOGGER.warn("Failed to load ip banlist: ", $$0);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void loadUserBanList() {
/*     */     try {
/*  85 */       getBans().load();
/*  86 */     } catch (IOException $$0) {
/*  87 */       LOGGER.warn("Failed to load user banlist: ", $$0);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void loadOps() {
/*     */     try {
/*  93 */       getOps().load();
/*  94 */     } catch (Exception $$0) {
/*  95 */       LOGGER.warn("Failed to load operators list: ", $$0);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void saveOps() {
/*     */     try {
/* 101 */       getOps().save();
/* 102 */     } catch (Exception $$0) {
/* 103 */       LOGGER.warn("Failed to save operators list: ", $$0);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void loadWhiteList() {
/*     */     try {
/* 109 */       getWhiteList().load();
/* 110 */     } catch (Exception $$0) {
/* 111 */       LOGGER.warn("Failed to load white-list: ", $$0);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void saveWhiteList() {
/*     */     try {
/* 117 */       getWhiteList().save();
/* 118 */     } catch (Exception $$0) {
/* 119 */       LOGGER.warn("Failed to save white-list: ", $$0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isWhiteListed(GameProfile $$0) {
/* 125 */     return (!isUsingWhitelist() || isOp($$0) || getWhiteList().isWhiteListed($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public DedicatedServer getServer() {
/* 130 */     return (DedicatedServer)super.getServer();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBypassPlayerLimit(GameProfile $$0) {
/* 135 */     return getOps().canBypassPlayerLimit($$0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\dedicated\DedicatedPlayerList.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */