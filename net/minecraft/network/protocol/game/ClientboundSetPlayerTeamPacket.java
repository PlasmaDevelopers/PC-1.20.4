/*     */ package net.minecraft.network.protocol.game;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.Collection;
/*     */ import java.util.Optional;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraft.network.PacketListener;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.world.scores.PlayerTeam;
/*     */ 
/*     */ public class ClientboundSetPlayerTeamPacket
/*     */   implements Packet<ClientGamePacketListener>
/*     */ {
/*     */   private static final int METHOD_ADD = 0;
/*     */   private static final int METHOD_REMOVE = 1;
/*     */   private static final int METHOD_CHANGE = 2;
/*     */   private static final int METHOD_JOIN = 3;
/*     */   private static final int METHOD_LEAVE = 4;
/*     */   private static final int MAX_VISIBILITY_LENGTH = 40;
/*     */   private static final int MAX_COLLISION_LENGTH = 40;
/*     */   private final int method;
/*     */   private final String name;
/*     */   private final Collection<String> players;
/*     */   private final Optional<Parameters> parameters;
/*     */   
/*     */   private ClientboundSetPlayerTeamPacket(String $$0, int $$1, Optional<Parameters> $$2, Collection<String> $$3) {
/*  30 */     this.name = $$0;
/*  31 */     this.method = $$1;
/*  32 */     this.parameters = $$2;
/*  33 */     this.players = (Collection<String>)ImmutableList.copyOf($$3);
/*     */   }
/*     */   
/*     */   public static ClientboundSetPlayerTeamPacket createAddOrModifyPacket(PlayerTeam $$0, boolean $$1) {
/*  37 */     return new ClientboundSetPlayerTeamPacket($$0
/*  38 */         .getName(), 
/*  39 */         $$1 ? 0 : 2, 
/*  40 */         Optional.of(new Parameters($$0)), 
/*  41 */         $$1 ? $$0.getPlayers() : (Collection<String>)ImmutableList.of());
/*     */   }
/*     */ 
/*     */   
/*     */   public static ClientboundSetPlayerTeamPacket createRemovePacket(PlayerTeam $$0) {
/*  46 */     return new ClientboundSetPlayerTeamPacket($$0
/*  47 */         .getName(), 1, 
/*     */         
/*  49 */         Optional.empty(), 
/*  50 */         (Collection<String>)ImmutableList.of());
/*     */   }
/*     */ 
/*     */   
/*     */   public static ClientboundSetPlayerTeamPacket createPlayerPacket(PlayerTeam $$0, String $$1, Action $$2) {
/*  55 */     return new ClientboundSetPlayerTeamPacket($$0
/*  56 */         .getName(), 
/*  57 */         ($$2 == Action.ADD) ? 3 : 4, 
/*  58 */         Optional.empty(), 
/*  59 */         (Collection<String>)ImmutableList.of($$1));
/*     */   }
/*     */ 
/*     */   
/*     */   public ClientboundSetPlayerTeamPacket(FriendlyByteBuf $$0) {
/*  64 */     this.name = $$0.readUtf();
/*  65 */     this.method = $$0.readByte();
/*     */     
/*  67 */     if (shouldHaveParameters(this.method)) {
/*  68 */       this.parameters = Optional.of(new Parameters($$0));
/*     */     } else {
/*  70 */       this.parameters = Optional.empty();
/*     */     } 
/*     */     
/*  73 */     if (shouldHavePlayerList(this.method)) {
/*  74 */       this.players = $$0.readList(FriendlyByteBuf::readUtf);
/*     */     } else {
/*  76 */       this.players = (Collection<String>)ImmutableList.of();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(FriendlyByteBuf $$0) {
/*  82 */     $$0.writeUtf(this.name);
/*  83 */     $$0.writeByte(this.method);
/*     */     
/*  85 */     if (shouldHaveParameters(this.method)) {
/*  86 */       ((Parameters)this.parameters.<Throwable>orElseThrow(() -> new IllegalStateException("Parameters not present, but method is" + this.method))).write($$0);
/*     */     }
/*     */     
/*  89 */     if (shouldHavePlayerList(this.method)) {
/*  90 */       $$0.writeCollection(this.players, FriendlyByteBuf::writeUtf);
/*     */     }
/*     */   }
/*     */   
/*     */   private static boolean shouldHavePlayerList(int $$0) {
/*  95 */     return ($$0 == 0 || $$0 == 3 || $$0 == 4);
/*     */   }
/*     */   
/*     */   private static boolean shouldHaveParameters(int $$0) {
/*  99 */     return ($$0 == 0 || $$0 == 2);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Action getPlayerAction() {
/* 104 */     switch (this.method) {
/*     */       case 0:
/*     */       case 3:
/* 107 */         return Action.ADD;
/*     */       case 4:
/* 109 */         return Action.REMOVE;
/*     */     } 
/* 111 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Action getTeamAction() {
/* 117 */     switch (this.method) {
/*     */       case 0:
/* 119 */         return Action.ADD;
/*     */       case 1:
/* 121 */         return Action.REMOVE;
/*     */     } 
/* 123 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void handle(ClientGamePacketListener $$0) {
/* 129 */     $$0.handleSetPlayerTeamPacket(this);
/*     */   }
/*     */   
/*     */   public String getName() {
/* 133 */     return this.name;
/*     */   }
/*     */   
/*     */   public Collection<String> getPlayers() {
/* 137 */     return this.players;
/*     */   }
/*     */   
/*     */   public Optional<Parameters> getParameters() {
/* 141 */     return this.parameters;
/*     */   }
/*     */   
/*     */   public enum Action {
/* 145 */     ADD,
/* 146 */     REMOVE;
/*     */   }
/*     */   
/*     */   public static class Parameters
/*     */   {
/*     */     private final Component displayName;
/*     */     private final Component playerPrefix;
/*     */     private final Component playerSuffix;
/*     */     private final String nametagVisibility;
/*     */     private final String collisionRule;
/*     */     private final ChatFormatting color;
/*     */     private final int options;
/*     */     
/*     */     public Parameters(PlayerTeam $$0) {
/* 160 */       this.displayName = $$0.getDisplayName();
/* 161 */       this.options = $$0.packOptions();
/* 162 */       this.nametagVisibility = ($$0.getNameTagVisibility()).name;
/* 163 */       this.collisionRule = ($$0.getCollisionRule()).name;
/* 164 */       this.color = $$0.getColor();
/* 165 */       this.playerPrefix = $$0.getPlayerPrefix();
/* 166 */       this.playerSuffix = $$0.getPlayerSuffix();
/*     */     }
/*     */     
/*     */     public Parameters(FriendlyByteBuf $$0) {
/* 170 */       this.displayName = $$0.readComponentTrusted();
/* 171 */       this.options = $$0.readByte();
/* 172 */       this.nametagVisibility = $$0.readUtf(40);
/* 173 */       this.collisionRule = $$0.readUtf(40);
/* 174 */       this.color = (ChatFormatting)$$0.readEnum(ChatFormatting.class);
/* 175 */       this.playerPrefix = $$0.readComponentTrusted();
/* 176 */       this.playerSuffix = $$0.readComponentTrusted();
/*     */     }
/*     */     
/*     */     public Component getDisplayName() {
/* 180 */       return this.displayName;
/*     */     }
/*     */     
/*     */     public int getOptions() {
/* 184 */       return this.options;
/*     */     }
/*     */     
/*     */     public ChatFormatting getColor() {
/* 188 */       return this.color;
/*     */     }
/*     */     
/*     */     public String getNametagVisibility() {
/* 192 */       return this.nametagVisibility;
/*     */     }
/*     */     
/*     */     public String getCollisionRule() {
/* 196 */       return this.collisionRule;
/*     */     }
/*     */     
/*     */     public Component getPlayerPrefix() {
/* 200 */       return this.playerPrefix;
/*     */     }
/*     */     
/*     */     public Component getPlayerSuffix() {
/* 204 */       return this.playerSuffix;
/*     */     }
/*     */     
/*     */     public void write(FriendlyByteBuf $$0) {
/* 208 */       $$0.writeComponent(this.displayName);
/* 209 */       $$0.writeByte(this.options);
/* 210 */       $$0.writeUtf(this.nametagVisibility);
/* 211 */       $$0.writeUtf(this.collisionRule);
/* 212 */       $$0.writeEnum((Enum)this.color);
/* 213 */       $$0.writeComponent(this.playerPrefix);
/* 214 */       $$0.writeComponent(this.playerSuffix);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundSetPlayerTeamPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */