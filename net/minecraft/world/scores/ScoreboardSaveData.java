/*     */ package net.minecraft.world.scores;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import java.util.Collection;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.nbt.NbtOps;
/*     */ import net.minecraft.nbt.StringTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.network.chat.numbers.NumberFormat;
/*     */ import net.minecraft.network.chat.numbers.NumberFormatTypes;
/*     */ import net.minecraft.world.level.saveddata.SavedData;
/*     */ import net.minecraft.world.scores.criteria.ObjectiveCriteria;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class ScoreboardSaveData extends SavedData {
/*  20 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   public static final String FILE_ID = "scoreboard";
/*     */   private final Scoreboard scoreboard;
/*     */   
/*     */   public ScoreboardSaveData(Scoreboard $$0) {
/*  26 */     this.scoreboard = $$0;
/*     */   }
/*     */   
/*     */   public ScoreboardSaveData load(CompoundTag $$0) {
/*  30 */     loadObjectives($$0.getList("Objectives", 10));
/*  31 */     this.scoreboard.loadPlayerScores($$0.getList("PlayerScores", 10));
/*     */     
/*  33 */     if ($$0.contains("DisplaySlots", 10)) {
/*  34 */       loadDisplaySlots($$0.getCompound("DisplaySlots"));
/*     */     }
/*     */     
/*  37 */     if ($$0.contains("Teams", 9)) {
/*  38 */       loadTeams($$0.getList("Teams", 10));
/*     */     }
/*  40 */     return this;
/*     */   }
/*     */   
/*     */   private void loadTeams(ListTag $$0) {
/*  44 */     for (int $$1 = 0; $$1 < $$0.size(); $$1++) {
/*  45 */       CompoundTag $$2 = $$0.getCompound($$1);
/*     */       
/*  47 */       String $$3 = $$2.getString("Name");
/*  48 */       PlayerTeam $$4 = this.scoreboard.addPlayerTeam($$3);
/*  49 */       MutableComponent mutableComponent = Component.Serializer.fromJson($$2.getString("DisplayName"));
/*  50 */       if (mutableComponent != null) {
/*  51 */         $$4.setDisplayName((Component)mutableComponent);
/*     */       }
/*  53 */       if ($$2.contains("TeamColor", 8)) {
/*  54 */         $$4.setColor(ChatFormatting.getByName($$2.getString("TeamColor")));
/*     */       }
/*  56 */       if ($$2.contains("AllowFriendlyFire", 99)) {
/*  57 */         $$4.setAllowFriendlyFire($$2.getBoolean("AllowFriendlyFire"));
/*     */       }
/*  59 */       if ($$2.contains("SeeFriendlyInvisibles", 99)) {
/*  60 */         $$4.setSeeFriendlyInvisibles($$2.getBoolean("SeeFriendlyInvisibles"));
/*     */       }
/*  62 */       if ($$2.contains("MemberNamePrefix", 8)) {
/*  63 */         MutableComponent mutableComponent1 = Component.Serializer.fromJson($$2.getString("MemberNamePrefix"));
/*  64 */         if (mutableComponent1 != null) {
/*  65 */           $$4.setPlayerPrefix((Component)mutableComponent1);
/*     */         }
/*     */       } 
/*  68 */       if ($$2.contains("MemberNameSuffix", 8)) {
/*  69 */         MutableComponent mutableComponent1 = Component.Serializer.fromJson($$2.getString("MemberNameSuffix"));
/*  70 */         if (mutableComponent1 != null) {
/*  71 */           $$4.setPlayerSuffix((Component)mutableComponent1);
/*     */         }
/*     */       } 
/*  74 */       if ($$2.contains("NameTagVisibility", 8)) {
/*  75 */         Team.Visibility $$8 = Team.Visibility.byName($$2.getString("NameTagVisibility"));
/*  76 */         if ($$8 != null) {
/*  77 */           $$4.setNameTagVisibility($$8);
/*     */         }
/*     */       } 
/*  80 */       if ($$2.contains("DeathMessageVisibility", 8)) {
/*  81 */         Team.Visibility $$9 = Team.Visibility.byName($$2.getString("DeathMessageVisibility"));
/*  82 */         if ($$9 != null) {
/*  83 */           $$4.setDeathMessageVisibility($$9);
/*     */         }
/*     */       } 
/*  86 */       if ($$2.contains("CollisionRule", 8)) {
/*  87 */         Team.CollisionRule $$10 = Team.CollisionRule.byName($$2.getString("CollisionRule"));
/*  88 */         if ($$10 != null) {
/*  89 */           $$4.setCollisionRule($$10);
/*     */         }
/*     */       } 
/*     */       
/*  93 */       loadTeamPlayers($$4, $$2.getList("Players", 8));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void loadTeamPlayers(PlayerTeam $$0, ListTag $$1) {
/*  98 */     for (int $$2 = 0; $$2 < $$1.size(); $$2++) {
/*  99 */       this.scoreboard.addPlayerToTeam($$1.getString($$2), $$0);
/*     */     }
/*     */   }
/*     */   
/*     */   private void loadDisplaySlots(CompoundTag $$0) {
/* 104 */     for (String $$1 : $$0.getAllKeys()) {
/* 105 */       DisplaySlot $$2 = (DisplaySlot)DisplaySlot.CODEC.byName($$1);
/* 106 */       if ($$2 != null) {
/* 107 */         String $$3 = $$0.getString($$1);
/* 108 */         Objective $$4 = this.scoreboard.getObjective($$3);
/* 109 */         this.scoreboard.setDisplayObjective($$2, $$4);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void loadObjectives(ListTag $$0) {
/* 115 */     for (int $$1 = 0; $$1 < $$0.size(); $$1++) {
/* 116 */       CompoundTag $$2 = $$0.getCompound($$1);
/* 117 */       String $$3 = $$2.getString("CriteriaName");
/* 118 */       ObjectiveCriteria $$4 = ObjectiveCriteria.byName($$3).orElseGet(() -> {
/*     */             LOGGER.warn("Unknown scoreboard criteria {}, replacing with {}", $$0, ObjectiveCriteria.DUMMY.getName());
/*     */             
/*     */             return ObjectiveCriteria.DUMMY;
/*     */           });
/* 123 */       String $$5 = $$2.getString("Name");
/* 124 */       MutableComponent mutableComponent = Component.Serializer.fromJson($$2.getString("DisplayName"));
/* 125 */       ObjectiveCriteria.RenderType $$7 = ObjectiveCriteria.RenderType.byId($$2.getString("RenderType"));
/* 126 */       boolean $$8 = $$2.getBoolean("display_auto_update");
/* 127 */       NumberFormat $$9 = NumberFormatTypes.CODEC.parse((DynamicOps)NbtOps.INSTANCE, $$2.get("format")).result().orElse(null);
/*     */       
/* 129 */       this.scoreboard.addObjective($$5, $$4, (Component)mutableComponent, $$7, $$8, $$9);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public CompoundTag save(CompoundTag $$0) {
/* 135 */     $$0.put("Objectives", (Tag)saveObjectives());
/* 136 */     $$0.put("PlayerScores", (Tag)this.scoreboard.savePlayerScores());
/* 137 */     $$0.put("Teams", (Tag)saveTeams());
/*     */     
/* 139 */     saveDisplaySlots($$0);
/*     */     
/* 141 */     return $$0;
/*     */   }
/*     */   
/*     */   private ListTag saveTeams() {
/* 145 */     ListTag $$0 = new ListTag();
/* 146 */     Collection<PlayerTeam> $$1 = this.scoreboard.getPlayerTeams();
/*     */     
/* 148 */     for (PlayerTeam $$2 : $$1) {
/* 149 */       CompoundTag $$3 = new CompoundTag();
/*     */       
/* 151 */       $$3.putString("Name", $$2.getName());
/* 152 */       $$3.putString("DisplayName", Component.Serializer.toJson($$2.getDisplayName()));
/* 153 */       if ($$2.getColor().getId() >= 0) {
/* 154 */         $$3.putString("TeamColor", $$2.getColor().getName());
/*     */       }
/* 156 */       $$3.putBoolean("AllowFriendlyFire", $$2.isAllowFriendlyFire());
/* 157 */       $$3.putBoolean("SeeFriendlyInvisibles", $$2.canSeeFriendlyInvisibles());
/* 158 */       $$3.putString("MemberNamePrefix", Component.Serializer.toJson($$2.getPlayerPrefix()));
/* 159 */       $$3.putString("MemberNameSuffix", Component.Serializer.toJson($$2.getPlayerSuffix()));
/* 160 */       $$3.putString("NameTagVisibility", ($$2.getNameTagVisibility()).name);
/* 161 */       $$3.putString("DeathMessageVisibility", ($$2.getDeathMessageVisibility()).name);
/* 162 */       $$3.putString("CollisionRule", ($$2.getCollisionRule()).name);
/*     */       
/* 164 */       ListTag $$4 = new ListTag();
/*     */       
/* 166 */       for (String $$5 : $$2.getPlayers()) {
/* 167 */         $$4.add(StringTag.valueOf($$5));
/*     */       }
/*     */       
/* 170 */       $$3.put("Players", (Tag)$$4);
/*     */       
/* 172 */       $$0.add($$3);
/*     */     } 
/*     */     
/* 175 */     return $$0;
/*     */   }
/*     */   
/*     */   private void saveDisplaySlots(CompoundTag $$0) {
/* 179 */     CompoundTag $$1 = new CompoundTag();
/*     */     
/* 181 */     for (DisplaySlot $$2 : DisplaySlot.values()) {
/* 182 */       Objective $$3 = this.scoreboard.getDisplayObjective($$2);
/*     */       
/* 184 */       if ($$3 != null) {
/* 185 */         $$1.putString($$2.getSerializedName(), $$3.getName());
/*     */       }
/*     */     } 
/*     */     
/* 189 */     if (!$$1.isEmpty()) {
/* 190 */       $$0.put("DisplaySlots", (Tag)$$1);
/*     */     }
/*     */   }
/*     */   
/*     */   private ListTag saveObjectives() {
/* 195 */     ListTag $$0 = new ListTag();
/* 196 */     Collection<Objective> $$1 = this.scoreboard.getObjectives();
/*     */     
/* 198 */     for (Objective $$2 : $$1) {
/* 199 */       CompoundTag $$3 = new CompoundTag();
/* 200 */       $$3.putString("Name", $$2.getName());
/* 201 */       $$3.putString("CriteriaName", $$2.getCriteria().getName());
/* 202 */       $$3.putString("DisplayName", Component.Serializer.toJson($$2.getDisplayName()));
/* 203 */       $$3.putString("RenderType", $$2.getRenderType().getId());
/* 204 */       $$3.putBoolean("display_auto_update", $$2.displayAutoUpdate());
/* 205 */       NumberFormat $$4 = $$2.numberFormat();
/* 206 */       if ($$4 != null) {
/* 207 */         NumberFormatTypes.CODEC.encodeStart((DynamicOps)NbtOps.INSTANCE, $$4).result().ifPresent($$1 -> $$0.put("format", $$1));
/*     */       }
/* 209 */       $$0.add($$3);
/*     */     } 
/*     */     
/* 212 */     return $$0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\scores\ScoreboardSaveData.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */