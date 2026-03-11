/*     */ package net.minecraft.network.protocol.game;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Optionull;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.RemoteChatSession;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.world.level.GameType;
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
/*     */ public final class Entry
/*     */   extends Record
/*     */ {
/*     */   private final UUID profileId;
/*     */   @Nullable
/*     */   private final GameProfile profile;
/*     */   private final boolean listed;
/*     */   private final int latency;
/*     */   private final GameType gameMode;
/*     */   @Nullable
/*     */   private final Component displayName;
/*     */   @Nullable
/*     */   final RemoteChatSession.Data chatSession;
/*     */   
/*     */   @Nullable
/*     */   public RemoteChatSession.Data chatSession() {
/* 139 */     return this.chatSession; } @Nullable public Component displayName() { return this.displayName; } public GameType gameMode() { return this.gameMode; } public int latency() { return this.latency; } public boolean listed() { return this.listed; } @Nullable public GameProfile profile() { return this.profile; } public UUID profileId() { return this.profileId; } public final boolean equals(Object $$0) { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/game/ClientboundPlayerInfoUpdatePacket$Entry;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #139	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lnet/minecraft/network/protocol/game/ClientboundPlayerInfoUpdatePacket$Entry;
/* 139 */     //   0	8	1	$$0	Ljava/lang/Object; } public Entry(UUID $$0, @Nullable GameProfile $$1, boolean $$2, int $$3, GameType $$4, @Nullable Component $$5, @Nullable RemoteChatSession.Data $$6) { this.profileId = $$0; this.profile = $$1; this.listed = $$2; this.latency = $$3; this.gameMode = $$4; this.displayName = $$5; this.chatSession = $$6; }
/*     */   public final int hashCode() { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/game/ClientboundPlayerInfoUpdatePacket$Entry;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #139	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/network/protocol/game/ClientboundPlayerInfoUpdatePacket$Entry; }
/*     */   public final String toString() { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/game/ClientboundPlayerInfoUpdatePacket$Entry;)Ljava/lang/String;
/*     */     //   6: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #139	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/* 141 */     //   0	7	0	this	Lnet/minecraft/network/protocol/game/ClientboundPlayerInfoUpdatePacket$Entry; } Entry(ServerPlayer $$0) { this($$0
/* 142 */         .getUUID(), $$0
/* 143 */         .getGameProfile(), true, $$0.connection
/*     */         
/* 145 */         .latency(), $$0.gameMode
/* 146 */         .getGameModeForPlayer(), $$0
/* 147 */         .getTabListDisplayName(), 
/* 148 */         (RemoteChatSession.Data)Optionull.map($$0.getChatSession(), RemoteChatSession::asData)); }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundPlayerInfoUpdatePacket$Entry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */