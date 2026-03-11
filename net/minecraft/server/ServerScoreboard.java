/*     */ package net.minecraft.server;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.game.ClientboundResetScorePacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundSetDisplayObjectivePacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundSetObjectivePacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundSetPlayerTeamPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundSetScorePacket;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.util.datafix.DataFixTypes;
/*     */ import net.minecraft.world.level.saveddata.SavedData;
/*     */ import net.minecraft.world.scores.DisplaySlot;
/*     */ import net.minecraft.world.scores.Objective;
/*     */ import net.minecraft.world.scores.PlayerScoreEntry;
/*     */ import net.minecraft.world.scores.PlayerTeam;
/*     */ import net.minecraft.world.scores.Score;
/*     */ import net.minecraft.world.scores.ScoreHolder;
/*     */ import net.minecraft.world.scores.Scoreboard;
/*     */ import net.minecraft.world.scores.ScoreboardSaveData;
/*     */ 
/*     */ public class ServerScoreboard extends Scoreboard {
/*     */   private final MinecraftServer server;
/*  30 */   private final Set<Objective> trackedObjectives = Sets.newHashSet();
/*  31 */   private final List<Runnable> dirtyListeners = Lists.newArrayList();
/*     */   
/*     */   public ServerScoreboard(MinecraftServer $$0) {
/*  34 */     this.server = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onScoreChanged(ScoreHolder $$0, Objective $$1, Score $$2) {
/*  39 */     super.onScoreChanged($$0, $$1, $$2);
/*     */     
/*  41 */     if (this.trackedObjectives.contains($$1)) {
/*  42 */       this.server.getPlayerList().broadcastAll((Packet)new ClientboundSetScorePacket($$0.getScoreboardName(), $$1.getName(), $$2.value(), $$2.display(), $$2.numberFormat()));
/*     */     }
/*     */     
/*  45 */     setDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onScoreLockChanged(ScoreHolder $$0, Objective $$1) {
/*  50 */     super.onScoreLockChanged($$0, $$1);
/*     */     
/*  52 */     setDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPlayerRemoved(ScoreHolder $$0) {
/*  57 */     super.onPlayerRemoved($$0);
/*  58 */     this.server.getPlayerList().broadcastAll((Packet)new ClientboundResetScorePacket($$0.getScoreboardName(), null));
/*  59 */     setDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPlayerScoreRemoved(ScoreHolder $$0, Objective $$1) {
/*  64 */     super.onPlayerScoreRemoved($$0, $$1);
/*  65 */     if (this.trackedObjectives.contains($$1)) {
/*  66 */       this.server.getPlayerList().broadcastAll((Packet)new ClientboundResetScorePacket($$0.getScoreboardName(), $$1.getName()));
/*     */     }
/*  68 */     setDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDisplayObjective(DisplaySlot $$0, @Nullable Objective $$1) {
/*  73 */     Objective $$2 = getDisplayObjective($$0);
/*     */     
/*  75 */     super.setDisplayObjective($$0, $$1);
/*     */     
/*  77 */     if ($$2 != $$1 && $$2 != null) {
/*  78 */       if (getObjectiveDisplaySlotCount($$2) > 0) {
/*  79 */         this.server.getPlayerList().broadcastAll((Packet)new ClientboundSetDisplayObjectivePacket($$0, $$1));
/*     */       } else {
/*  81 */         stopTrackingObjective($$2);
/*     */       } 
/*     */     }
/*     */     
/*  85 */     if ($$1 != null) {
/*  86 */       if (this.trackedObjectives.contains($$1)) {
/*  87 */         this.server.getPlayerList().broadcastAll((Packet)new ClientboundSetDisplayObjectivePacket($$0, $$1));
/*     */       } else {
/*  89 */         startTrackingObjective($$1);
/*     */       } 
/*     */     }
/*     */     
/*  93 */     setDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addPlayerToTeam(String $$0, PlayerTeam $$1) {
/*  98 */     if (super.addPlayerToTeam($$0, $$1)) {
/*  99 */       this.server.getPlayerList().broadcastAll((Packet)ClientboundSetPlayerTeamPacket.createPlayerPacket($$1, $$0, ClientboundSetPlayerTeamPacket.Action.ADD));
/* 100 */       setDirty();
/*     */       
/* 102 */       return true;
/*     */     } 
/*     */     
/* 105 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void removePlayerFromTeam(String $$0, PlayerTeam $$1) {
/* 110 */     super.removePlayerFromTeam($$0, $$1);
/*     */     
/* 112 */     this.server.getPlayerList().broadcastAll((Packet)ClientboundSetPlayerTeamPacket.createPlayerPacket($$1, $$0, ClientboundSetPlayerTeamPacket.Action.REMOVE));
/*     */     
/* 114 */     setDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onObjectiveAdded(Objective $$0) {
/* 119 */     super.onObjectiveAdded($$0);
/* 120 */     setDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onObjectiveChanged(Objective $$0) {
/* 125 */     super.onObjectiveChanged($$0);
/*     */     
/* 127 */     if (this.trackedObjectives.contains($$0)) {
/* 128 */       this.server.getPlayerList().broadcastAll((Packet)new ClientboundSetObjectivePacket($$0, 2));
/*     */     }
/*     */     
/* 131 */     setDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onObjectiveRemoved(Objective $$0) {
/* 136 */     super.onObjectiveRemoved($$0);
/*     */     
/* 138 */     if (this.trackedObjectives.contains($$0)) {
/* 139 */       stopTrackingObjective($$0);
/*     */     }
/*     */     
/* 142 */     setDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onTeamAdded(PlayerTeam $$0) {
/* 147 */     super.onTeamAdded($$0);
/*     */     
/* 149 */     this.server.getPlayerList().broadcastAll((Packet)ClientboundSetPlayerTeamPacket.createAddOrModifyPacket($$0, true));
/*     */     
/* 151 */     setDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onTeamChanged(PlayerTeam $$0) {
/* 156 */     super.onTeamChanged($$0);
/*     */     
/* 158 */     this.server.getPlayerList().broadcastAll((Packet)ClientboundSetPlayerTeamPacket.createAddOrModifyPacket($$0, false));
/*     */     
/* 160 */     setDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onTeamRemoved(PlayerTeam $$0) {
/* 165 */     super.onTeamRemoved($$0);
/*     */     
/* 167 */     this.server.getPlayerList().broadcastAll((Packet)ClientboundSetPlayerTeamPacket.createRemovePacket($$0));
/*     */     
/* 169 */     setDirty();
/*     */   }
/*     */   
/*     */   public void addDirtyListener(Runnable $$0) {
/* 173 */     this.dirtyListeners.add($$0);
/*     */   }
/*     */   
/*     */   protected void setDirty() {
/* 177 */     for (Runnable $$0 : this.dirtyListeners) {
/* 178 */       $$0.run();
/*     */     }
/*     */   }
/*     */   
/*     */   public List<Packet<?>> getStartTrackingPackets(Objective $$0) {
/* 183 */     List<Packet<?>> $$1 = Lists.newArrayList();
/* 184 */     $$1.add(new ClientboundSetObjectivePacket($$0, 0));
/*     */     
/* 186 */     for (DisplaySlot $$2 : DisplaySlot.values()) {
/* 187 */       if (getDisplayObjective($$2) == $$0) {
/* 188 */         $$1.add(new ClientboundSetDisplayObjectivePacket($$2, $$0));
/*     */       }
/*     */     } 
/*     */     
/* 192 */     for (PlayerScoreEntry $$3 : listPlayerScores($$0)) {
/* 193 */       $$1.add(new ClientboundSetScorePacket($$3.owner(), $$0.getName(), $$3.value(), $$3.display(), $$3.numberFormatOverride()));
/*     */     }
/*     */     
/* 196 */     return $$1;
/*     */   }
/*     */   
/*     */   public void startTrackingObjective(Objective $$0) {
/* 200 */     List<Packet<?>> $$1 = getStartTrackingPackets($$0);
/*     */     
/* 202 */     for (ServerPlayer $$2 : this.server.getPlayerList().getPlayers()) {
/* 203 */       for (Packet<?> $$3 : $$1) {
/* 204 */         $$2.connection.send($$3);
/*     */       }
/*     */     } 
/*     */     
/* 208 */     this.trackedObjectives.add($$0);
/*     */   }
/*     */   
/*     */   public List<Packet<?>> getStopTrackingPackets(Objective $$0) {
/* 212 */     List<Packet<?>> $$1 = Lists.newArrayList();
/* 213 */     $$1.add(new ClientboundSetObjectivePacket($$0, 1));
/*     */     
/* 215 */     for (DisplaySlot $$2 : DisplaySlot.values()) {
/* 216 */       if (getDisplayObjective($$2) == $$0) {
/* 217 */         $$1.add(new ClientboundSetDisplayObjectivePacket($$2, $$0));
/*     */       }
/*     */     } 
/*     */     
/* 221 */     return $$1;
/*     */   }
/*     */   
/*     */   public void stopTrackingObjective(Objective $$0) {
/* 225 */     List<Packet<?>> $$1 = getStopTrackingPackets($$0);
/*     */     
/* 227 */     for (ServerPlayer $$2 : this.server.getPlayerList().getPlayers()) {
/* 228 */       for (Packet<?> $$3 : $$1) {
/* 229 */         $$2.connection.send($$3);
/*     */       }
/*     */     } 
/*     */     
/* 233 */     this.trackedObjectives.remove($$0);
/*     */   }
/*     */   
/*     */   public int getObjectiveDisplaySlotCount(Objective $$0) {
/* 237 */     int $$1 = 0;
/*     */     
/* 239 */     for (DisplaySlot $$2 : DisplaySlot.values()) {
/* 240 */       if (getDisplayObjective($$2) == $$0) {
/* 241 */         $$1++;
/*     */       }
/*     */     } 
/*     */     
/* 245 */     return $$1;
/*     */   }
/*     */   
/*     */   public SavedData.Factory<ScoreboardSaveData> dataFactory() {
/* 249 */     return new SavedData.Factory(this::createData, this::createData, DataFixTypes.SAVED_DATA_SCOREBOARD);
/*     */   }
/*     */   
/*     */   private ScoreboardSaveData createData() {
/* 253 */     ScoreboardSaveData $$0 = new ScoreboardSaveData(this);
/* 254 */     Objects.requireNonNull($$0); addDirtyListener($$0::setDirty);
/* 255 */     return $$0;
/*     */   }
/*     */   
/*     */   private ScoreboardSaveData createData(CompoundTag $$0) {
/* 259 */     return createData().load($$0);
/*     */   }
/*     */   
/*     */   public enum Method {
/* 263 */     CHANGE,
/* 264 */     REMOVE;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\ServerScoreboard.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */