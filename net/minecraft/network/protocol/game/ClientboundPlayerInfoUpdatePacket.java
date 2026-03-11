/*     */ package net.minecraft.network.protocol.game;
/*     */ import com.google.common.base.MoreObjects;
/*     */ import com.google.common.collect.Multimap;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import java.util.Collection;
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.UUID;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Optionull;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraft.network.PacketListener;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.RemoteChatSession;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.world.level.GameType;
/*     */ 
/*     */ public class ClientboundPlayerInfoUpdatePacket implements Packet<ClientGamePacketListener> {
/*     */   private final EnumSet<Action> actions;
/*     */   private final List<Entry> entries;
/*     */   
/*     */   public ClientboundPlayerInfoUpdatePacket(EnumSet<Action> $$0, Collection<ServerPlayer> $$1) {
/*  26 */     this.actions = $$0;
/*  27 */     this.entries = $$1.stream().map(Entry::new).toList();
/*     */   }
/*     */   
/*     */   public ClientboundPlayerInfoUpdatePacket(Action $$0, ServerPlayer $$1) {
/*  31 */     this.actions = EnumSet.of($$0);
/*  32 */     this.entries = List.of(new Entry($$1));
/*     */   }
/*     */   
/*     */   public static ClientboundPlayerInfoUpdatePacket createPlayerInitializing(Collection<ServerPlayer> $$0) {
/*  36 */     EnumSet<Action> $$1 = EnumSet.of(Action.ADD_PLAYER, new Action[] { Action.INITIALIZE_CHAT, Action.UPDATE_GAME_MODE, Action.UPDATE_LISTED, Action.UPDATE_LATENCY, Action.UPDATE_DISPLAY_NAME });
/*  37 */     return new ClientboundPlayerInfoUpdatePacket($$1, $$0);
/*     */   }
/*     */   
/*     */   public ClientboundPlayerInfoUpdatePacket(FriendlyByteBuf $$0) {
/*  41 */     this.actions = $$0.readEnumSet(Action.class);
/*  42 */     this.entries = $$0.readList($$0 -> {
/*     */           EntryBuilder $$1 = new EntryBuilder($$0.readUUID());
/*     */           for (Action $$2 : this.actions) {
/*     */             $$2.reader.read($$1, $$0);
/*     */           }
/*     */           return $$1.build();
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(FriendlyByteBuf $$0) {
/*  53 */     $$0.writeEnumSet(this.actions, Action.class);
/*  54 */     $$0.writeCollection(this.entries, ($$0, $$1) -> {
/*     */           $$0.writeUUID($$1.profileId());
/*     */           for (Action $$2 : this.actions) {
/*     */             $$2.writer.write($$0, $$1);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void handle(ClientGamePacketListener $$0) {
/*  64 */     $$0.handlePlayerInfoUpdate(this);
/*     */   }
/*     */   
/*     */   public EnumSet<Action> actions() {
/*  68 */     return this.actions;
/*     */   }
/*     */   
/*     */   public List<Entry> entries() {
/*  72 */     return this.entries;
/*     */   }
/*     */   
/*     */   public List<Entry> newEntries() {
/*  76 */     return this.actions.contains(Action.ADD_PLAYER) ? this.entries : List.<Entry>of();
/*     */   }
/*     */   public enum Action { ADD_PLAYER, INITIALIZE_CHAT, UPDATE_GAME_MODE, UPDATE_LISTED, UPDATE_LATENCY, UPDATE_DISPLAY_NAME; final Reader reader; final Writer writer;
/*     */     static {
/*  80 */       ADD_PLAYER = new Action("ADD_PLAYER", 0, ($$0, $$1) -> {
/*     */             GameProfile $$2 = new GameProfile($$0.profileId, $$1.readUtf(16));
/*     */             
/*     */             $$2.getProperties().putAll((Multimap)$$1.readGameProfileProperties());
/*     */             
/*     */             $$0.profile = $$2;
/*     */           }($$0, $$1) -> {
/*     */             GameProfile $$2 = Objects.<GameProfile>requireNonNull($$1.profile());
/*     */             
/*     */             $$0.writeUtf($$2.getName(), 16);
/*     */             $$0.writeGameProfileProperties($$2.getProperties());
/*     */           });
/*  92 */       INITIALIZE_CHAT = new Action("INITIALIZE_CHAT", 1, ($$0, $$1) -> $$0.chatSession = (RemoteChatSession.Data)$$1.readNullable(RemoteChatSession.Data::read), ($$0, $$1) -> $$0.writeNullable($$1.chatSession, RemoteChatSession.Data::write));
/*     */ 
/*     */ 
/*     */       
/*  96 */       UPDATE_GAME_MODE = new Action("UPDATE_GAME_MODE", 2, ($$0, $$1) -> $$0.gameMode = GameType.byId($$1.readVarInt()), ($$0, $$1) -> $$0.writeVarInt($$1.gameMode().getId()));
/*     */ 
/*     */ 
/*     */       
/* 100 */       UPDATE_LISTED = new Action("UPDATE_LISTED", 3, ($$0, $$1) -> $$0.listed = $$1.readBoolean(), ($$0, $$1) -> $$0.writeBoolean($$1.listed()));
/*     */ 
/*     */ 
/*     */       
/* 104 */       UPDATE_LATENCY = new Action("UPDATE_LATENCY", 4, ($$0, $$1) -> $$0.latency = $$1.readVarInt(), ($$0, $$1) -> $$0.writeVarInt($$1.latency()));
/*     */ 
/*     */ 
/*     */       
/* 108 */       UPDATE_DISPLAY_NAME = new Action("UPDATE_DISPLAY_NAME", 5, ($$0, $$1) -> $$0.displayName = (Component)$$1.readNullable(FriendlyByteBuf::readComponentTrusted), ($$0, $$1) -> $$0.writeNullable($$1.displayName(), FriendlyByteBuf::writeComponent));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Action(Reader $$0, Writer $$1) {
/* 118 */       this.reader = $$0;
/* 119 */       this.writer = $$1;
/*     */     }
/*     */     
/*     */     public static interface Reader
/*     */     {
/*     */       void read(ClientboundPlayerInfoUpdatePacket.EntryBuilder param2EntryBuilder, FriendlyByteBuf param2FriendlyByteBuf);
/*     */     }
/*     */     
/*     */     public static interface Writer {
/*     */       void write(FriendlyByteBuf param2FriendlyByteBuf, ClientboundPlayerInfoUpdatePacket.Entry param2Entry);
/*     */     } }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 133 */     return MoreObjects.toStringHelper(this)
/* 134 */       .add("actions", this.actions)
/* 135 */       .add("entries", this.entries)
/* 136 */       .toString(); } public static final class Entry extends Record { private final UUID profileId; @Nullable private final GameProfile profile; private final boolean listed; private final int latency; private final GameType gameMode; @Nullable
/*     */     private final Component displayName; @Nullable
/*     */     final RemoteChatSession.Data chatSession; @Nullable
/* 139 */     public RemoteChatSession.Data chatSession() { return this.chatSession; } @Nullable public Component displayName() { return this.displayName; } public GameType gameMode() { return this.gameMode; } public int latency() { return this.latency; } public boolean listed() { return this.listed; } @Nullable public GameProfile profile() { return this.profile; } public UUID profileId() { return this.profileId; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/game/ClientboundPlayerInfoUpdatePacket$Entry;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #139	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/network/protocol/game/ClientboundPlayerInfoUpdatePacket$Entry;
/* 139 */       //   0	8	1	$$0	Ljava/lang/Object; } public Entry(UUID $$0, @Nullable GameProfile $$1, boolean $$2, int $$3, GameType $$4, @Nullable Component $$5, @Nullable RemoteChatSession.Data $$6) { this.profileId = $$0; this.profile = $$1; this.listed = $$2; this.latency = $$3; this.gameMode = $$4; this.displayName = $$5; this.chatSession = $$6; }
/*     */     public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/game/ClientboundPlayerInfoUpdatePacket$Entry;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #139	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/network/protocol/game/ClientboundPlayerInfoUpdatePacket$Entry; }
/*     */     public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/game/ClientboundPlayerInfoUpdatePacket$Entry;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #139	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 141 */       //   0	7	0	this	Lnet/minecraft/network/protocol/game/ClientboundPlayerInfoUpdatePacket$Entry; } Entry(ServerPlayer $$0) { this($$0
/* 142 */           .getUUID(), $$0
/* 143 */           .getGameProfile(), true, $$0.connection
/*     */           
/* 145 */           .latency(), $$0.gameMode
/* 146 */           .getGameModeForPlayer(), $$0
/* 147 */           .getTabListDisplayName(), 
/* 148 */           (RemoteChatSession.Data)Optionull.map($$0.getChatSession(), RemoteChatSession::asData)); }
/*     */      }
/*     */ 
/*     */   
/*     */   private static class EntryBuilder
/*     */   {
/*     */     final UUID profileId;
/*     */     @Nullable
/*     */     GameProfile profile;
/*     */     boolean listed;
/*     */     int latency;
/* 159 */     GameType gameMode = GameType.DEFAULT_MODE;
/*     */     @Nullable
/*     */     Component displayName;
/*     */     @Nullable
/*     */     RemoteChatSession.Data chatSession;
/*     */     
/*     */     EntryBuilder(UUID $$0) {
/* 166 */       this.profileId = $$0;
/*     */     }
/*     */     
/*     */     ClientboundPlayerInfoUpdatePacket.Entry build() {
/* 170 */       return new ClientboundPlayerInfoUpdatePacket.Entry(this.profileId, this.profile, this.listed, this.latency, this.gameMode, this.displayName, this.chatSession);
/*     */     }
/*     */   }
/*     */   
/*     */   public static interface Writer {
/*     */     void write(FriendlyByteBuf param1FriendlyByteBuf, ClientboundPlayerInfoUpdatePacket.Entry param1Entry);
/*     */   }
/*     */   
/*     */   public static interface Reader {
/*     */     void read(ClientboundPlayerInfoUpdatePacket.EntryBuilder param1EntryBuilder, FriendlyByteBuf param1FriendlyByteBuf);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundPlayerInfoUpdatePacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */