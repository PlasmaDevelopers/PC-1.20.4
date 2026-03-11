/*     */ package net.minecraft.world.scores;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMaps;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.EnumMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Consumer;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.numbers.NumberFormat;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.scores.criteria.ObjectiveCriteria;
/*     */ import org.apache.commons.lang3.mutable.MutableBoolean;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Scoreboard
/*     */ {
/*     */   public static final String HIDDEN_SCORE_PREFIX = "#";
/*  34 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*  36 */   private final Object2ObjectMap<String, Objective> objectivesByName = (Object2ObjectMap<String, Objective>)new Object2ObjectOpenHashMap(16, 0.5F);
/*  37 */   private final Reference2ObjectMap<ObjectiveCriteria, List<Objective>> objectivesByCriteria = (Reference2ObjectMap<ObjectiveCriteria, List<Objective>>)new Reference2ObjectOpenHashMap();
/*  38 */   private final Map<String, PlayerScores> playerScores = (Map<String, PlayerScores>)new Object2ObjectOpenHashMap(16, 0.5F);
/*  39 */   private final Map<DisplaySlot, Objective> displayObjectives = new EnumMap<>(DisplaySlot.class);
/*  40 */   private final Object2ObjectMap<String, PlayerTeam> teamsByName = (Object2ObjectMap<String, PlayerTeam>)new Object2ObjectOpenHashMap();
/*  41 */   private final Object2ObjectMap<String, PlayerTeam> teamsByPlayer = (Object2ObjectMap<String, PlayerTeam>)new Object2ObjectOpenHashMap();
/*     */   
/*     */   @Nullable
/*     */   public Objective getObjective(@Nullable String $$0) {
/*  45 */     return (Objective)this.objectivesByName.get($$0);
/*     */   }
/*     */   
/*     */   public Objective addObjective(String $$0, ObjectiveCriteria $$1, Component $$2, ObjectiveCriteria.RenderType $$3, boolean $$4, @Nullable NumberFormat $$5) {
/*  49 */     if (this.objectivesByName.containsKey($$0)) {
/*  50 */       throw new IllegalArgumentException("An objective with the name '" + $$0 + "' already exists!");
/*     */     }
/*     */     
/*  53 */     Objective $$6 = new Objective(this, $$0, $$1, $$2, $$3, $$4, $$5);
/*     */     
/*  55 */     ((List<Objective>)this.objectivesByCriteria.computeIfAbsent($$1, $$0 -> Lists.newArrayList())).add($$6);
/*  56 */     this.objectivesByName.put($$0, $$6);
/*  57 */     onObjectiveAdded($$6);
/*  58 */     return $$6;
/*     */   }
/*     */   
/*     */   public final void forAllObjectives(ObjectiveCriteria $$0, ScoreHolder $$1, Consumer<ScoreAccess> $$2) {
/*  62 */     ((List)this.objectivesByCriteria.getOrDefault($$0, Collections.emptyList())).forEach($$2 -> $$0.accept(getOrCreatePlayerScore($$1, $$2, true)));
/*     */   }
/*     */   
/*     */   private PlayerScores getOrCreatePlayerInfo(String $$0) {
/*  66 */     return this.playerScores.computeIfAbsent($$0, $$0 -> new PlayerScores());
/*     */   }
/*     */   
/*     */   public ScoreAccess getOrCreatePlayerScore(ScoreHolder $$0, Objective $$1) {
/*  70 */     return getOrCreatePlayerScore($$0, $$1, false);
/*     */   }
/*     */   
/*     */   public ScoreAccess getOrCreatePlayerScore(final ScoreHolder scoreHolder, final Objective objective, boolean $$2) {
/*  74 */     final boolean canModify = ($$2 || !objective.getCriteria().isReadOnly());
/*     */     
/*  76 */     PlayerScores $$4 = getOrCreatePlayerInfo(scoreHolder.getScoreboardName());
/*  77 */     final MutableBoolean requiresSync = new MutableBoolean();
/*  78 */     final Score score = $$4.getOrCreate(objective, $$1 -> $$0.setTrue());
/*     */     
/*  80 */     return new ScoreAccess()
/*     */       {
/*     */         public int get() {
/*  83 */           return score.value();
/*     */         }
/*     */ 
/*     */         
/*     */         public void set(int $$0) {
/*  88 */           if (!canModify) {
/*  89 */             throw new IllegalStateException("Cannot modify read-only score");
/*     */           }
/*     */           
/*  92 */           boolean $$1 = requiresSync.isTrue();
/*     */           
/*  94 */           if (objective.displayAutoUpdate()) {
/*  95 */             Component $$2 = scoreHolder.getDisplayName();
/*  96 */             if ($$2 != null && !$$2.equals(score.display())) {
/*  97 */               score.display($$2);
/*  98 */               $$1 = true;
/*     */             } 
/*     */           } 
/*     */           
/* 102 */           if ($$0 != score.value()) {
/* 103 */             score.value($$0);
/* 104 */             $$1 = true;
/*     */           } 
/*     */           
/* 107 */           if ($$1) {
/* 108 */             sendScoreToPlayers();
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         @Nullable
/*     */         public Component display() {
/* 115 */           return score.display();
/*     */         }
/*     */ 
/*     */         
/*     */         public void display(@Nullable Component $$0) {
/* 120 */           if (requiresSync.isTrue() || !Objects.equals($$0, score.display())) {
/* 121 */             score.display($$0);
/* 122 */             sendScoreToPlayers();
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/*     */         public void numberFormatOverride(@Nullable NumberFormat $$0) {
/* 128 */           score.numberFormat($$0);
/* 129 */           sendScoreToPlayers();
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean locked() {
/* 134 */           return score.isLocked();
/*     */         }
/*     */ 
/*     */         
/*     */         public void unlock() {
/* 139 */           setLocked(false);
/*     */         }
/*     */ 
/*     */         
/*     */         public void lock() {
/* 144 */           setLocked(true);
/*     */         }
/*     */         
/*     */         private void setLocked(boolean $$0) {
/* 148 */           score.setLocked($$0);
/*     */           
/* 150 */           if (requiresSync.isTrue()) {
/* 151 */             sendScoreToPlayers();
/*     */           }
/*     */           
/* 154 */           Scoreboard.this.onScoreLockChanged(scoreHolder, objective);
/*     */         }
/*     */         
/*     */         private void sendScoreToPlayers() {
/* 158 */           Scoreboard.this.onScoreChanged(scoreHolder, objective, score);
/* 159 */           requiresSync.setFalse();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ReadOnlyScoreInfo getPlayerScoreInfo(ScoreHolder $$0, Objective $$1) {
/* 166 */     PlayerScores $$2 = this.playerScores.get($$0.getScoreboardName());
/* 167 */     if ($$2 != null) {
/* 168 */       return $$2.get($$1);
/*     */     }
/* 170 */     return null;
/*     */   }
/*     */   
/*     */   public Collection<PlayerScoreEntry> listPlayerScores(Objective $$0) {
/* 174 */     List<PlayerScoreEntry> $$1 = new ArrayList<>();
/*     */     
/* 176 */     this.playerScores.forEach(($$2, $$3) -> {
/*     */           Score $$4 = $$3.get($$0);
/*     */           if ($$4 != null) {
/*     */             $$1.add(new PlayerScoreEntry($$2, $$4.value(), $$4.display(), $$4.numberFormat()));
/*     */           }
/*     */         });
/* 182 */     return $$1;
/*     */   }
/*     */   
/*     */   public Collection<Objective> getObjectives() {
/* 186 */     return (Collection<Objective>)this.objectivesByName.values();
/*     */   }
/*     */   
/*     */   public Collection<String> getObjectiveNames() {
/* 190 */     return (Collection<String>)this.objectivesByName.keySet();
/*     */   }
/*     */   
/*     */   public Collection<ScoreHolder> getTrackedPlayers() {
/* 194 */     return this.playerScores.keySet().stream().map(ScoreHolder::forNameOnly).toList();
/*     */   }
/*     */   
/*     */   public void resetAllPlayerScores(ScoreHolder $$0) {
/* 198 */     PlayerScores $$1 = this.playerScores.remove($$0.getScoreboardName());
/* 199 */     if ($$1 != null) {
/* 200 */       onPlayerRemoved($$0);
/*     */     }
/*     */   }
/*     */   
/*     */   public void resetSinglePlayerScore(ScoreHolder $$0, Objective $$1) {
/* 205 */     PlayerScores $$2 = this.playerScores.get($$0.getScoreboardName());
/* 206 */     if ($$2 != null) {
/* 207 */       boolean $$3 = $$2.remove($$1);
/* 208 */       if (!$$2.hasScores()) {
/* 209 */         PlayerScores $$4 = this.playerScores.remove($$0.getScoreboardName());
/* 210 */         if ($$4 != null) {
/* 211 */           onPlayerRemoved($$0);
/*     */         }
/* 213 */       } else if ($$3) {
/* 214 */         onPlayerScoreRemoved($$0, $$1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public Object2IntMap<Objective> listPlayerScores(ScoreHolder $$0) {
/* 220 */     PlayerScores $$1 = this.playerScores.get($$0.getScoreboardName());
/* 221 */     return ($$1 != null) ? $$1.listScores() : Object2IntMaps.emptyMap();
/*     */   }
/*     */   
/*     */   public void removeObjective(Objective $$0) {
/* 225 */     this.objectivesByName.remove($$0.getName());
/*     */     
/* 227 */     for (DisplaySlot $$1 : DisplaySlot.values()) {
/* 228 */       if (getDisplayObjective($$1) == $$0) {
/* 229 */         setDisplayObjective($$1, null);
/*     */       }
/*     */     } 
/*     */     
/* 233 */     List<Objective> $$2 = (List<Objective>)this.objectivesByCriteria.get($$0.getCriteria());
/* 234 */     if ($$2 != null) {
/* 235 */       $$2.remove($$0);
/*     */     }
/*     */     
/* 238 */     for (PlayerScores $$3 : this.playerScores.values()) {
/* 239 */       $$3.remove($$0);
/*     */     }
/*     */     
/* 242 */     onObjectiveRemoved($$0);
/*     */   }
/*     */   
/*     */   public void setDisplayObjective(DisplaySlot $$0, @Nullable Objective $$1) {
/* 246 */     this.displayObjectives.put($$0, $$1);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Objective getDisplayObjective(DisplaySlot $$0) {
/* 251 */     return this.displayObjectives.get($$0);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public PlayerTeam getPlayerTeam(String $$0) {
/* 256 */     return (PlayerTeam)this.teamsByName.get($$0);
/*     */   }
/*     */   
/*     */   public PlayerTeam addPlayerTeam(String $$0) {
/* 260 */     PlayerTeam $$1 = getPlayerTeam($$0);
/* 261 */     if ($$1 != null) {
/* 262 */       LOGGER.warn("Requested creation of existing team '{}'", $$0);
/* 263 */       return $$1;
/*     */     } 
/*     */     
/* 266 */     $$1 = new PlayerTeam(this, $$0);
/* 267 */     this.teamsByName.put($$0, $$1);
/* 268 */     onTeamAdded($$1);
/*     */     
/* 270 */     return $$1;
/*     */   }
/*     */   
/*     */   public void removePlayerTeam(PlayerTeam $$0) {
/* 274 */     this.teamsByName.remove($$0.getName());
/*     */ 
/*     */ 
/*     */     
/* 278 */     for (String $$1 : $$0.getPlayers()) {
/* 279 */       this.teamsByPlayer.remove($$1);
/*     */     }
/*     */     
/* 282 */     onTeamRemoved($$0);
/*     */   }
/*     */   
/*     */   public boolean addPlayerToTeam(String $$0, PlayerTeam $$1) {
/* 286 */     if (getPlayersTeam($$0) != null) {
/* 287 */       removePlayerFromTeam($$0);
/*     */     }
/*     */     
/* 290 */     this.teamsByPlayer.put($$0, $$1);
/* 291 */     return $$1.getPlayers().add($$0);
/*     */   }
/*     */   
/*     */   public boolean removePlayerFromTeam(String $$0) {
/* 295 */     PlayerTeam $$1 = getPlayersTeam($$0);
/*     */     
/* 297 */     if ($$1 != null) {
/* 298 */       removePlayerFromTeam($$0, $$1);
/* 299 */       return true;
/*     */     } 
/* 301 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void removePlayerFromTeam(String $$0, PlayerTeam $$1) {
/* 306 */     if (getPlayersTeam($$0) != $$1) {
/* 307 */       throw new IllegalStateException("Player is either on another team or not on any team. Cannot remove from team '" + $$1.getName() + "'.");
/*     */     }
/*     */     
/* 310 */     this.teamsByPlayer.remove($$0);
/* 311 */     $$1.getPlayers().remove($$0);
/*     */   }
/*     */   
/*     */   public Collection<String> getTeamNames() {
/* 315 */     return (Collection<String>)this.teamsByName.keySet();
/*     */   }
/*     */   
/*     */   public Collection<PlayerTeam> getPlayerTeams() {
/* 319 */     return (Collection<PlayerTeam>)this.teamsByName.values();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public PlayerTeam getPlayersTeam(String $$0) {
/* 324 */     return (PlayerTeam)this.teamsByPlayer.get($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onObjectiveAdded(Objective $$0) {}
/*     */ 
/*     */   
/*     */   public void onObjectiveChanged(Objective $$0) {}
/*     */ 
/*     */   
/*     */   public void onObjectiveRemoved(Objective $$0) {}
/*     */ 
/*     */   
/*     */   protected void onScoreChanged(ScoreHolder $$0, Objective $$1, Score $$2) {}
/*     */ 
/*     */   
/*     */   protected void onScoreLockChanged(ScoreHolder $$0, Objective $$1) {}
/*     */ 
/*     */   
/*     */   public void onPlayerRemoved(ScoreHolder $$0) {}
/*     */ 
/*     */   
/*     */   public void onPlayerScoreRemoved(ScoreHolder $$0, Objective $$1) {}
/*     */ 
/*     */   
/*     */   public void onTeamAdded(PlayerTeam $$0) {}
/*     */ 
/*     */   
/*     */   public void onTeamChanged(PlayerTeam $$0) {}
/*     */ 
/*     */   
/*     */   public void onTeamRemoved(PlayerTeam $$0) {}
/*     */   
/*     */   public void entityRemoved(Entity $$0) {
/* 358 */     if ($$0 instanceof net.minecraft.world.entity.player.Player || $$0.isAlive()) {
/*     */       return;
/*     */     }
/* 361 */     resetAllPlayerScores((ScoreHolder)$$0);
/* 362 */     removePlayerFromTeam($$0.getScoreboardName());
/*     */   }
/*     */   
/*     */   protected ListTag savePlayerScores() {
/* 366 */     ListTag $$0 = new ListTag();
/*     */     
/* 368 */     this.playerScores.forEach(($$1, $$2) -> $$2.listRawScores().forEach(()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 378 */     return $$0;
/*     */   }
/*     */   
/*     */   protected void loadPlayerScores(ListTag $$0) {
/* 382 */     for (int $$1 = 0; $$1 < $$0.size(); $$1++) {
/* 383 */       CompoundTag $$2 = $$0.getCompound($$1);
/* 384 */       Score $$3 = Score.read($$2);
/*     */       
/* 386 */       String $$4 = $$2.getString("Name");
/* 387 */       String $$5 = $$2.getString("Objective");
/* 388 */       Objective $$6 = getObjective($$5);
/*     */       
/* 390 */       if ($$6 == null) {
/* 391 */         LOGGER.error("Unknown objective {} for name {}, ignoring", $$5, $$4);
/*     */       }
/*     */       else {
/*     */         
/* 395 */         getOrCreatePlayerInfo($$4).setScore($$6, $$3);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\scores\Scoreboard.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */