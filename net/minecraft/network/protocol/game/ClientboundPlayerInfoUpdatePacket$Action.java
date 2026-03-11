/*     */ package net.minecraft.network.protocol.game;
/*     */ 
/*     */ import com.google.common.collect.Multimap;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import java.util.Objects;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.RemoteChatSession;
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
/*     */ public enum Action
/*     */ {
/*     */   ADD_PLAYER, INITIALIZE_CHAT, UPDATE_GAME_MODE, UPDATE_LISTED, UPDATE_LATENCY, UPDATE_DISPLAY_NAME;
/*     */   final Reader reader;
/*     */   final Writer writer;
/*     */   
/*     */   static {
/*  80 */     ADD_PLAYER = new Action("ADD_PLAYER", 0, ($$0, $$1) -> {
/*     */           GameProfile $$2 = new GameProfile($$0.profileId, $$1.readUtf(16));
/*     */           
/*     */           $$2.getProperties().putAll((Multimap)$$1.readGameProfileProperties());
/*     */           
/*     */           $$0.profile = $$2;
/*     */         }($$0, $$1) -> {
/*     */           GameProfile $$2 = Objects.<GameProfile>requireNonNull($$1.profile());
/*     */           
/*     */           $$0.writeUtf($$2.getName(), 16);
/*     */           $$0.writeGameProfileProperties($$2.getProperties());
/*     */         });
/*  92 */     INITIALIZE_CHAT = new Action("INITIALIZE_CHAT", 1, ($$0, $$1) -> $$0.chatSession = (RemoteChatSession.Data)$$1.readNullable(RemoteChatSession.Data::read), ($$0, $$1) -> $$0.writeNullable($$1.chatSession, RemoteChatSession.Data::write));
/*     */ 
/*     */ 
/*     */     
/*  96 */     UPDATE_GAME_MODE = new Action("UPDATE_GAME_MODE", 2, ($$0, $$1) -> $$0.gameMode = GameType.byId($$1.readVarInt()), ($$0, $$1) -> $$0.writeVarInt($$1.gameMode().getId()));
/*     */ 
/*     */ 
/*     */     
/* 100 */     UPDATE_LISTED = new Action("UPDATE_LISTED", 3, ($$0, $$1) -> $$0.listed = $$1.readBoolean(), ($$0, $$1) -> $$0.writeBoolean($$1.listed()));
/*     */ 
/*     */ 
/*     */     
/* 104 */     UPDATE_LATENCY = new Action("UPDATE_LATENCY", 4, ($$0, $$1) -> $$0.latency = $$1.readVarInt(), ($$0, $$1) -> $$0.writeVarInt($$1.latency()));
/*     */ 
/*     */ 
/*     */     
/* 108 */     UPDATE_DISPLAY_NAME = new Action("UPDATE_DISPLAY_NAME", 5, ($$0, $$1) -> $$0.displayName = (Component)$$1.readNullable(FriendlyByteBuf::readComponentTrusted), ($$0, $$1) -> $$0.writeNullable($$1.displayName(), FriendlyByteBuf::writeComponent));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Action(Reader $$0, Writer $$1) {
/* 118 */     this.reader = $$0;
/* 119 */     this.writer = $$1;
/*     */   }
/*     */   
/*     */   public static interface Reader {
/*     */     void read(ClientboundPlayerInfoUpdatePacket.EntryBuilder param2EntryBuilder, FriendlyByteBuf param2FriendlyByteBuf);
/*     */   }
/*     */   
/*     */   public static interface Writer {
/*     */     void write(FriendlyByteBuf param2FriendlyByteBuf, ClientboundPlayerInfoUpdatePacket.Entry param2Entry);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundPlayerInfoUpdatePacket$Action.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */