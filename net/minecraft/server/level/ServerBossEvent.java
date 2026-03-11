/*     */ package net.minecraft.server.level;
/*     */ 
/*     */ import com.google.common.base.Objects;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Set;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.game.ClientboundBossEventPacket;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.BossEvent;
/*     */ 
/*     */ public class ServerBossEvent extends BossEvent {
/*  17 */   private final Set<ServerPlayer> players = Sets.newHashSet();
/*  18 */   private final Set<ServerPlayer> unmodifiablePlayers = Collections.unmodifiableSet(this.players);
/*     */   private boolean visible = true;
/*     */   
/*     */   public ServerBossEvent(Component $$0, BossEvent.BossBarColor $$1, BossEvent.BossBarOverlay $$2) {
/*  22 */     super(Mth.createInsecureUUID(), $$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setProgress(float $$0) {
/*  27 */     if ($$0 != this.progress) {
/*  28 */       super.setProgress($$0);
/*  29 */       broadcast(ClientboundBossEventPacket::createUpdateProgressPacket);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setColor(BossEvent.BossBarColor $$0) {
/*  35 */     if ($$0 != this.color) {
/*  36 */       super.setColor($$0);
/*  37 */       broadcast(ClientboundBossEventPacket::createUpdateStylePacket);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOverlay(BossEvent.BossBarOverlay $$0) {
/*  43 */     if ($$0 != this.overlay) {
/*  44 */       super.setOverlay($$0);
/*  45 */       broadcast(ClientboundBossEventPacket::createUpdateStylePacket);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public BossEvent setDarkenScreen(boolean $$0) {
/*  51 */     if ($$0 != this.darkenScreen) {
/*  52 */       super.setDarkenScreen($$0);
/*  53 */       broadcast(ClientboundBossEventPacket::createUpdatePropertiesPacket);
/*     */     } 
/*  55 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public BossEvent setPlayBossMusic(boolean $$0) {
/*  60 */     if ($$0 != this.playBossMusic) {
/*  61 */       super.setPlayBossMusic($$0);
/*  62 */       broadcast(ClientboundBossEventPacket::createUpdatePropertiesPacket);
/*     */     } 
/*  64 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public BossEvent setCreateWorldFog(boolean $$0) {
/*  69 */     if ($$0 != this.createWorldFog) {
/*  70 */       super.setCreateWorldFog($$0);
/*  71 */       broadcast(ClientboundBossEventPacket::createUpdatePropertiesPacket);
/*     */     } 
/*  73 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setName(Component $$0) {
/*  78 */     if (!Objects.equal($$0, this.name)) {
/*  79 */       super.setName($$0);
/*  80 */       broadcast(ClientboundBossEventPacket::createUpdateNamePacket);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void broadcast(Function<BossEvent, ClientboundBossEventPacket> $$0) {
/*  85 */     if (this.visible) {
/*  86 */       ClientboundBossEventPacket $$1 = $$0.apply(this);
/*  87 */       for (ServerPlayer $$2 : this.players) {
/*  88 */         $$2.connection.send((Packet)$$1);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addPlayer(ServerPlayer $$0) {
/*  94 */     if (this.players.add($$0) && this.visible) {
/*  95 */       $$0.connection.send((Packet)ClientboundBossEventPacket.createAddPacket(this));
/*     */     }
/*     */   }
/*     */   
/*     */   public void removePlayer(ServerPlayer $$0) {
/* 100 */     if (this.players.remove($$0) && this.visible) {
/* 101 */       $$0.connection.send((Packet)ClientboundBossEventPacket.createRemovePacket(getId()));
/*     */     }
/*     */   }
/*     */   
/*     */   public void removeAllPlayers() {
/* 106 */     if (!this.players.isEmpty()) {
/* 107 */       for (ServerPlayer $$0 : Lists.newArrayList(this.players)) {
/* 108 */         removePlayer($$0);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isVisible() {
/* 114 */     return this.visible;
/*     */   }
/*     */   
/*     */   public void setVisible(boolean $$0) {
/* 118 */     if ($$0 != this.visible) {
/* 119 */       this.visible = $$0;
/*     */       
/* 121 */       for (ServerPlayer $$1 : this.players) {
/* 122 */         $$1.connection.send($$0 ? (Packet)ClientboundBossEventPacket.createAddPacket(this) : (Packet)ClientboundBossEventPacket.createRemovePacket(getId()));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public Collection<ServerPlayer> getPlayers() {
/* 128 */     return this.unmodifiablePlayers;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\level\ServerBossEvent.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */