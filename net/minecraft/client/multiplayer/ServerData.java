/*     */ package net.minecraft.client.multiplayer;
/*     */ 
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.io.IOException;
/*     */ import java.util.Base64;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.SharedConstants;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.protocol.status.ServerStatus;
/*     */ import net.minecraft.util.PngInfo;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ 
/*     */ public class ServerData
/*     */ {
/*  19 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private static final int MAX_ICON_SIZE = 1024;
/*     */   public String name;
/*     */   public String ip;
/*     */   public Component status;
/*     */   public Component motd;
/*     */   @Nullable
/*     */   public ServerStatus.Players players;
/*     */   public long ping;
/*  29 */   public int protocol = SharedConstants.getCurrentVersion().getProtocolVersion();
/*  30 */   public Component version = (Component)Component.literal(SharedConstants.getCurrentVersion().getName());
/*     */   public boolean pinged;
/*  32 */   public List<Component> playerList = Collections.emptyList();
/*  33 */   private ServerPackStatus packStatus = ServerPackStatus.PROMPT;
/*     */   
/*     */   @Nullable
/*     */   private byte[] iconBytes;
/*     */   private Type type;
/*     */   private boolean enforcesSecureChat;
/*     */   
/*     */   public ServerData(String $$0, String $$1, Type $$2) {
/*  41 */     this.name = $$0;
/*  42 */     this.ip = $$1;
/*  43 */     this.type = $$2;
/*     */   }
/*     */   
/*     */   public CompoundTag write() {
/*  47 */     CompoundTag $$0 = new CompoundTag();
/*  48 */     $$0.putString("name", this.name);
/*  49 */     $$0.putString("ip", this.ip);
/*     */     
/*  51 */     if (this.iconBytes != null) {
/*  52 */       $$0.putString("icon", Base64.getEncoder().encodeToString(this.iconBytes));
/*     */     }
/*     */     
/*  55 */     if (this.packStatus == ServerPackStatus.ENABLED) {
/*  56 */       $$0.putBoolean("acceptTextures", true);
/*  57 */     } else if (this.packStatus == ServerPackStatus.DISABLED) {
/*  58 */       $$0.putBoolean("acceptTextures", false);
/*     */     } 
/*     */     
/*  61 */     return $$0;
/*     */   }
/*     */   
/*     */   public ServerPackStatus getResourcePackStatus() {
/*  65 */     return this.packStatus;
/*     */   }
/*     */   
/*     */   public void setResourcePackStatus(ServerPackStatus $$0) {
/*  69 */     this.packStatus = $$0;
/*     */   }
/*     */   
/*     */   public static ServerData read(CompoundTag $$0) {
/*  73 */     ServerData $$1 = new ServerData($$0.getString("name"), $$0.getString("ip"), Type.OTHER);
/*     */     
/*  75 */     if ($$0.contains("icon", 8)) {
/*     */       try {
/*  77 */         byte[] $$2 = Base64.getDecoder().decode($$0.getString("icon"));
/*  78 */         $$1.setIconBytes(validateIcon($$2));
/*  79 */       } catch (IllegalArgumentException $$3) {
/*  80 */         LOGGER.warn("Malformed base64 server icon", $$3);
/*     */       } 
/*     */     }
/*     */     
/*  84 */     if ($$0.contains("acceptTextures", 1)) {
/*  85 */       if ($$0.getBoolean("acceptTextures")) {
/*  86 */         $$1.setResourcePackStatus(ServerPackStatus.ENABLED);
/*     */       } else {
/*  88 */         $$1.setResourcePackStatus(ServerPackStatus.DISABLED);
/*     */       } 
/*     */     } else {
/*  91 */       $$1.setResourcePackStatus(ServerPackStatus.PROMPT);
/*     */     } 
/*     */     
/*  94 */     return $$1;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public byte[] getIconBytes() {
/*  99 */     return this.iconBytes;
/*     */   }
/*     */   
/*     */   public void setIconBytes(@Nullable byte[] $$0) {
/* 103 */     this.iconBytes = $$0;
/*     */   }
/*     */   
/*     */   public boolean isLan() {
/* 107 */     return (this.type == Type.LAN);
/*     */   }
/*     */   
/*     */   public boolean isRealm() {
/* 111 */     return (this.type == Type.REALM);
/*     */   }
/*     */   
/*     */   public Type type() {
/* 115 */     return this.type;
/*     */   }
/*     */   
/*     */   public void setEnforcesSecureChat(boolean $$0) {
/* 119 */     this.enforcesSecureChat = $$0;
/*     */   }
/*     */   
/*     */   public boolean enforcesSecureChat() {
/* 123 */     return this.enforcesSecureChat;
/*     */   }
/*     */   
/*     */   public void copyNameIconFrom(ServerData $$0) {
/* 127 */     this.ip = $$0.ip;
/* 128 */     this.name = $$0.name;
/* 129 */     this.iconBytes = $$0.iconBytes;
/*     */   }
/*     */   
/*     */   public void copyFrom(ServerData $$0) {
/* 133 */     copyNameIconFrom($$0);
/* 134 */     setResourcePackStatus($$0.getResourcePackStatus());
/* 135 */     this.type = $$0.type;
/* 136 */     this.enforcesSecureChat = $$0.enforcesSecureChat;
/*     */   }
/*     */   
/*     */   public enum ServerPackStatus {
/* 140 */     ENABLED("enabled"),
/* 141 */     DISABLED("disabled"),
/* 142 */     PROMPT("prompt");
/*     */     
/*     */     private final Component name;
/*     */ 
/*     */     
/*     */     ServerPackStatus(String $$0) {
/* 148 */       this.name = (Component)Component.translatable("addServer.resourcePack." + $$0);
/*     */     }
/*     */     
/*     */     public Component getName() {
/* 152 */       return this.name;
/*     */     }
/*     */   }
/*     */   
/*     */   public enum Type {
/* 157 */     LAN,
/* 158 */     REALM,
/* 159 */     OTHER;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static byte[] validateIcon(@Nullable byte[] $$0) {
/* 164 */     if ($$0 != null) {
/*     */       try {
/* 166 */         PngInfo $$1 = PngInfo.fromBytes($$0);
/* 167 */         if ($$1.width() <= 1024 && $$1.height() <= 1024) {
/* 168 */           return $$0;
/*     */         }
/* 170 */       } catch (IOException $$2) {
/* 171 */         LOGGER.warn("Failed to decode server icon", $$2);
/*     */       } 
/*     */     }
/*     */     
/* 175 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\multiplayer\ServerData.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */