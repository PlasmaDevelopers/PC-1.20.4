/*     */ package net.minecraft.world.level.block.entity.trialspawner;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function7;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.UUIDUtil;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.NbtOps;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.random.SimpleWeightedRandomList;
/*     */ import net.minecraft.util.random.WeightedEntry;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.SpawnData;
/*     */ 
/*     */ public class TrialSpawnerData {
/*     */   static {
/*  38 */     MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)UUIDUtil.CODEC_SET.optionalFieldOf("registered_players", Sets.newHashSet()).forGetter(()), (App)UUIDUtil.CODEC_SET.optionalFieldOf("current_mobs", Sets.newHashSet()).forGetter(()), (App)Codec.LONG.optionalFieldOf("cooldown_ends_at", Long.valueOf(0L)).forGetter(()), (App)Codec.LONG.optionalFieldOf("next_mob_spawns_at", Long.valueOf(0L)).forGetter(()), (App)Codec.intRange(0, 2147483647).optionalFieldOf("total_mobs_spawned", Integer.valueOf(0)).forGetter(()), (App)SpawnData.CODEC.optionalFieldOf("spawn_data").forGetter(()), (App)ResourceLocation.CODEC.optionalFieldOf("ejecting_loot_table").forGetter(())).apply((Applicative)$$0, TrialSpawnerData::new));
/*     */   }
/*     */ 
/*     */   
/*     */   public static final String TAG_SPAWN_DATA = "spawn_data";
/*     */   
/*     */   private static final String TAG_NEXT_MOB_SPAWNS_AT = "next_mob_spawns_at";
/*     */   
/*     */   public static MapCodec<TrialSpawnerData> MAP_CODEC;
/*     */   
/*  48 */   protected final Set<UUID> detectedPlayers = new HashSet<>();
/*  49 */   protected final Set<UUID> currentMobs = new HashSet<>();
/*     */   protected long cooldownEndsAt;
/*     */   protected long nextMobSpawnsAt;
/*     */   protected int totalMobsSpawned;
/*     */   protected Optional<SpawnData> nextSpawnData;
/*     */   protected Optional<ResourceLocation> ejectingLootTable;
/*     */   protected SimpleWeightedRandomList<SpawnData> spawnPotentials;
/*     */   @Nullable
/*     */   protected Entity displayEntity;
/*     */   protected double spin;
/*     */   protected double oSpin;
/*     */   
/*     */   public TrialSpawnerData() {
/*  62 */     this(Collections.emptySet(), Collections.emptySet(), 0L, 0L, 0, Optional.empty(), Optional.empty());
/*     */   }
/*     */   
/*     */   public TrialSpawnerData(Set<UUID> $$0, Set<UUID> $$1, long $$2, long $$3, int $$4, Optional<SpawnData> $$5, Optional<ResourceLocation> $$6) {
/*  66 */     this.detectedPlayers.addAll($$0);
/*  67 */     this.currentMobs.addAll($$1);
/*  68 */     this.cooldownEndsAt = $$2;
/*  69 */     this.nextMobSpawnsAt = $$3;
/*  70 */     this.totalMobsSpawned = $$4;
/*  71 */     this.nextSpawnData = $$5;
/*  72 */     this.ejectingLootTable = $$6;
/*     */   }
/*     */   
/*     */   public void setSpawnPotentialsFromConfig(TrialSpawnerConfig $$0) {
/*  76 */     SimpleWeightedRandomList<SpawnData> $$1 = $$0.spawnPotentialsDefinition();
/*  77 */     if ($$1.isEmpty()) {
/*  78 */       this.spawnPotentials = SimpleWeightedRandomList.single(this.nextSpawnData.orElseGet(SpawnData::new));
/*     */     } else {
/*  80 */       this.spawnPotentials = $$1;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void reset() {
/*  85 */     this.detectedPlayers.clear();
/*  86 */     this.totalMobsSpawned = 0;
/*  87 */     this.nextMobSpawnsAt = 0L;
/*  88 */     this.cooldownEndsAt = 0L;
/*  89 */     this.currentMobs.clear();
/*     */   }
/*     */   
/*     */   public boolean hasMobToSpawn() {
/*  93 */     boolean $$0 = (this.nextSpawnData.isPresent() && ((SpawnData)this.nextSpawnData.get()).getEntityToSpawn().contains("id", 8));
/*  94 */     return ($$0 || !this.spawnPotentials.isEmpty());
/*     */   }
/*     */   
/*     */   public boolean hasFinishedSpawningAllMobs(TrialSpawnerConfig $$0, int $$1) {
/*  98 */     return (this.totalMobsSpawned >= $$0.calculateTargetTotalMobs($$1));
/*     */   }
/*     */   
/*     */   public boolean haveAllCurrentMobsDied() {
/* 102 */     return this.currentMobs.isEmpty();
/*     */   }
/*     */   
/*     */   public boolean isReadyToSpawnNextMob(ServerLevel $$0, TrialSpawnerConfig $$1, int $$2) {
/* 106 */     return ($$0.getGameTime() >= this.nextMobSpawnsAt && this.currentMobs.size() < $$1.calculateTargetSimultaneousMobs($$2));
/*     */   }
/*     */   
/*     */   public int countAdditionalPlayers(BlockPos $$0) {
/* 110 */     if (this.detectedPlayers.isEmpty()) {
/* 111 */       Util.logAndPauseIfInIde("Trial Spawner at " + $$0 + " has no detected players");
/*     */     }
/* 113 */     return Math.max(0, this.detectedPlayers.size() - 1);
/*     */   }
/*     */   
/*     */   public void tryDetectPlayers(ServerLevel $$0, BlockPos $$1, PlayerDetector $$2, int $$3) {
/* 117 */     List<UUID> $$4 = $$2.detect($$0, $$1, $$3);
/* 118 */     boolean $$5 = this.detectedPlayers.addAll($$4);
/* 119 */     if ($$5) {
/*     */       
/* 121 */       this.nextMobSpawnsAt = Math.max($$0.getGameTime() + 40L, this.nextMobSpawnsAt);
/* 122 */       $$0.levelEvent(3013, $$1, this.detectedPlayers.size());
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isReadyToOpenShutter(ServerLevel $$0, TrialSpawnerConfig $$1, float $$2) {
/* 127 */     long $$3 = this.cooldownEndsAt - $$1.targetCooldownLength();
/* 128 */     return ((float)$$0.getGameTime() >= (float)$$3 + $$2);
/*     */   }
/*     */   
/*     */   public boolean isReadyToEjectItems(ServerLevel $$0, TrialSpawnerConfig $$1, float $$2) {
/* 132 */     long $$3 = this.cooldownEndsAt - $$1.targetCooldownLength();
/* 133 */     return ((float)($$0.getGameTime() - $$3) % $$2 == 0.0F);
/*     */   }
/*     */   
/*     */   public boolean isCooldownFinished(ServerLevel $$0) {
/* 137 */     return ($$0.getGameTime() >= this.cooldownEndsAt);
/*     */   }
/*     */   
/*     */   public void setEntityId(TrialSpawner $$0, RandomSource $$1, EntityType<?> $$2) {
/* 141 */     getOrCreateNextSpawnData($$0, $$1).getEntityToSpawn().putString("id", BuiltInRegistries.ENTITY_TYPE.getKey($$2).toString());
/*     */   }
/*     */   
/*     */   protected SpawnData getOrCreateNextSpawnData(TrialSpawner $$0, RandomSource $$1) {
/* 145 */     if (this.nextSpawnData.isPresent()) {
/* 146 */       return this.nextSpawnData.get();
/*     */     }
/* 148 */     this.nextSpawnData = Optional.of(this.spawnPotentials.getRandom($$1).map(WeightedEntry.Wrapper::getData).orElseGet(SpawnData::new));
/* 149 */     $$0.markUpdated();
/* 150 */     return this.nextSpawnData.get();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Entity getOrCreateDisplayEntity(TrialSpawner $$0, Level $$1, TrialSpawnerState $$2) {
/* 155 */     if (!$$0.canSpawnInLevel($$1) || !$$2.hasSpinningMob()) {
/* 156 */       return null;
/*     */     }
/*     */     
/* 159 */     if (this.displayEntity == null) {
/* 160 */       CompoundTag $$3 = getOrCreateNextSpawnData($$0, $$1.getRandom()).getEntityToSpawn();
/* 161 */       if ($$3.contains("id", 8)) {
/* 162 */         this.displayEntity = EntityType.loadEntityRecursive($$3, $$1, Function.identity());
/*     */       }
/*     */     } 
/*     */     
/* 166 */     return this.displayEntity;
/*     */   }
/*     */   
/*     */   public CompoundTag getUpdateTag(TrialSpawnerState $$0) {
/* 170 */     CompoundTag $$1 = new CompoundTag();
/*     */     
/* 172 */     if ($$0 == TrialSpawnerState.ACTIVE) {
/* 173 */       $$1.putLong("next_mob_spawns_at", this.nextMobSpawnsAt);
/*     */     }
/*     */     
/* 176 */     this.nextSpawnData.ifPresent($$1 -> $$0.put("spawn_data", (Tag)SpawnData.CODEC.encodeStart((DynamicOps)NbtOps.INSTANCE, $$1).result().orElseThrow(())));
/*     */     
/* 178 */     return $$1;
/*     */   }
/*     */   
/*     */   public double getSpin() {
/* 182 */     return this.spin;
/*     */   }
/*     */   
/*     */   public double getOSpin() {
/* 186 */     return this.oSpin;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\trialspawner\TrialSpawnerData.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */