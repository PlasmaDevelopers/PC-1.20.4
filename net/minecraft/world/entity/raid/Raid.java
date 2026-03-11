/*     */ package net.minecraft.world.entity.raid;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.advancements.CriteriaTriggers;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.nbt.NbtUtils;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.server.level.ServerBossEvent;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.stats.Stats;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.BossEvent;
/*     */ import net.minecraft.world.Difficulty;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.effect.MobEffects;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.EquipmentSlot;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.SpawnGroupData;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.DyeColor;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.entity.BannerPatterns;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class Raid {
/*     */   private static final int SECTION_RADIUS_FOR_FINDING_NEW_VILLAGE_CENTER = 2;
/*     */   private static final int ATTEMPT_RAID_FARTHEST = 0;
/*     */   private static final int ATTEMPT_RAID_CLOSE = 1;
/*     */   private static final int ATTEMPT_RAID_INSIDE = 2;
/*     */   private static final int VILLAGE_SEARCH_RADIUS = 32;
/*     */   private static final int RAID_TIMEOUT_TICKS = 48000;
/*     */   private static final int NUM_SPAWN_ATTEMPTS = 3;
/*     */   private static final String OMINOUS_BANNER_PATTERN_NAME = "block.minecraft.ominous_banner";
/*     */   private static final String RAIDERS_REMAINING = "event.minecraft.raid.raiders_remaining";
/*     */   public static final int VILLAGE_RADIUS_BUFFER = 16;
/*     */   private static final int POST_RAID_TICK_LIMIT = 40;
/*     */   private static final int DEFAULT_PRE_RAID_TICKS = 300;
/*     */   public static final int MAX_NO_ACTION_TIME = 2400;
/*     */   public static final int MAX_CELEBRATION_TICKS = 600;
/*     */   private static final int OUTSIDE_RAID_BOUNDS_TIMEOUT = 30;
/*     */   public static final int TICKS_PER_DAY = 24000;
/*     */   public static final int DEFAULT_MAX_BAD_OMEN_LEVEL = 5;
/*     */   private static final int LOW_MOB_THRESHOLD = 2;
/*     */   
/*     */   private enum RaidStatus {
/*  66 */     ONGOING,
/*  67 */     VICTORY,
/*  68 */     LOSS,
/*  69 */     STOPPED;
/*     */     
/*  71 */     private static final RaidStatus[] VALUES = values(); static {
/*     */     
/*     */     } static RaidStatus getByName(String $$0) {
/*  74 */       for (RaidStatus $$1 : VALUES) {
/*  75 */         if ($$0.equalsIgnoreCase($$1.name())) {
/*  76 */           return $$1;
/*     */         }
/*     */       } 
/*  79 */       return ONGOING;
/*     */     }
/*     */     
/*     */     public String getName() {
/*  83 */       return name().toLowerCase(Locale.ROOT);
/*     */     }
/*     */   }
/*     */   
/*     */   private enum RaiderType {
/*  88 */     VINDICATOR((String)EntityType.VINDICATOR, new int[] { 0, 0, 2, 0, 1, 4, 2, 5 }),
/*  89 */     EVOKER((String)EntityType.EVOKER, new int[] { 0, 0, 0, 0, 0, 1, 1, 2
/*     */       }),
/*  91 */     PILLAGER((String)EntityType.PILLAGER, new int[] { 0, 4, 3, 3, 4, 4, 4, 2 }),
/*  92 */     WITCH((String)EntityType.WITCH, new int[] { 0, 0, 0, 0, 3, 0, 0, 1 }),
/*  93 */     RAVAGER((String)EntityType.RAVAGER, new int[] { 0, 0, 0, 1, 0, 1, 0, 2 });
/*     */ 
/*     */     
/*  96 */     static final RaiderType[] VALUES = values(); final EntityType<? extends Raider> entityType; final int[] spawnsPerWaveBeforeBonus;
/*     */     static {
/*     */     
/*     */     }
/*     */     
/*     */     RaiderType(EntityType<? extends Raider> $$0, int[] $$1) {
/* 102 */       this.entityType = $$0;
/* 103 */       this.spawnsPerWaveBeforeBonus = $$1;
/*     */     }
/*     */   }
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
/* 127 */   private static final Component RAID_NAME_COMPONENT = (Component)Component.translatable("event.minecraft.raid");
/* 128 */   private static final Component RAID_BAR_VICTORY_COMPONENT = (Component)Component.translatable("event.minecraft.raid.victory.full");
/* 129 */   private static final Component RAID_BAR_DEFEAT_COMPONENT = (Component)Component.translatable("event.minecraft.raid.defeat.full");
/*     */   
/*     */   private static final int HERO_OF_THE_VILLAGE_DURATION = 48000;
/*     */   
/*     */   public static final int VALID_RAID_RADIUS_SQR = 9216;
/*     */   
/*     */   public static final int RAID_REMOVAL_THRESHOLD_SQR = 12544;
/*     */   
/* 137 */   private final Map<Integer, Raider> groupToLeaderMap = Maps.newHashMap();
/* 138 */   private final Map<Integer, Set<Raider>> groupRaiderMap = Maps.newHashMap();
/*     */   
/* 140 */   private final Set<UUID> heroesOfTheVillage = Sets.newHashSet();
/*     */   
/*     */   private long ticksActive;
/*     */   private BlockPos center;
/*     */   private final ServerLevel level;
/*     */   private boolean started;
/*     */   private final int id;
/*     */   private float totalHealth;
/*     */   private int badOmenLevel;
/*     */   private boolean active;
/*     */   private int groupsSpawned;
/* 151 */   private final ServerBossEvent raidEvent = new ServerBossEvent(RAID_NAME_COMPONENT, BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.NOTCHED_10);
/*     */   private int postRaidTicks;
/*     */   private int raidCooldownTicks;
/* 154 */   private final RandomSource random = RandomSource.create();
/*     */   private final int numGroups;
/*     */   private RaidStatus status;
/*     */   private int celebrationTicks;
/* 158 */   private Optional<BlockPos> waveSpawnPos = Optional.empty();
/*     */   
/*     */   public Raid(int $$0, ServerLevel $$1, BlockPos $$2) {
/* 161 */     this.id = $$0;
/* 162 */     this.level = $$1;
/* 163 */     this.active = true;
/* 164 */     this.raidCooldownTicks = 300;
/* 165 */     this.raidEvent.setProgress(0.0F);
/* 166 */     this.center = $$2;
/* 167 */     this.numGroups = getNumGroups($$1.getDifficulty());
/* 168 */     this.status = RaidStatus.ONGOING;
/*     */   }
/*     */   
/*     */   public Raid(ServerLevel $$0, CompoundTag $$1) {
/* 172 */     this.level = $$0;
/* 173 */     this.id = $$1.getInt("Id");
/* 174 */     this.started = $$1.getBoolean("Started");
/* 175 */     this.active = $$1.getBoolean("Active");
/* 176 */     this.ticksActive = $$1.getLong("TicksActive");
/* 177 */     this.badOmenLevel = $$1.getInt("BadOmenLevel");
/* 178 */     this.groupsSpawned = $$1.getInt("GroupsSpawned");
/* 179 */     this.raidCooldownTicks = $$1.getInt("PreRaidTicks");
/* 180 */     this.postRaidTicks = $$1.getInt("PostRaidTicks");
/* 181 */     this.totalHealth = $$1.getFloat("TotalHealth");
/* 182 */     this.center = new BlockPos($$1.getInt("CX"), $$1.getInt("CY"), $$1.getInt("CZ"));
/* 183 */     this.numGroups = $$1.getInt("NumGroups");
/* 184 */     this.status = RaidStatus.getByName($$1.getString("Status"));
/*     */     
/* 186 */     this.heroesOfTheVillage.clear();
/* 187 */     if ($$1.contains("HeroesOfTheVillage", 9)) {
/* 188 */       ListTag $$2 = $$1.getList("HeroesOfTheVillage", 11);
/* 189 */       for (Tag $$3 : $$2) {
/* 190 */         this.heroesOfTheVillage.add(NbtUtils.loadUUID($$3));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isOver() {
/* 196 */     return (isVictory() || isLoss());
/*     */   }
/*     */   
/*     */   public boolean isBetweenWaves() {
/* 200 */     return (hasFirstWaveSpawned() && getTotalRaidersAlive() == 0 && this.raidCooldownTicks > 0);
/*     */   }
/*     */   
/*     */   public boolean hasFirstWaveSpawned() {
/* 204 */     return (this.groupsSpawned > 0);
/*     */   }
/*     */   
/*     */   public boolean isStopped() {
/* 208 */     return (this.status == RaidStatus.STOPPED);
/*     */   }
/*     */   
/*     */   public boolean isVictory() {
/* 212 */     return (this.status == RaidStatus.VICTORY);
/*     */   }
/*     */   
/*     */   public boolean isLoss() {
/* 216 */     return (this.status == RaidStatus.LOSS);
/*     */   }
/*     */   
/*     */   public float getTotalHealth() {
/* 220 */     return this.totalHealth;
/*     */   }
/*     */   
/*     */   public Set<Raider> getAllRaiders() {
/* 224 */     Set<Raider> $$0 = Sets.newHashSet();
/* 225 */     for (Set<Raider> $$1 : this.groupRaiderMap.values()) {
/* 226 */       $$0.addAll($$1);
/*     */     }
/* 228 */     return $$0;
/*     */   }
/*     */   
/*     */   public Level getLevel() {
/* 232 */     return (Level)this.level;
/*     */   }
/*     */   
/*     */   public boolean isStarted() {
/* 236 */     return this.started;
/*     */   }
/*     */   
/*     */   public int getGroupsSpawned() {
/* 240 */     return this.groupsSpawned;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Predicate<ServerPlayer> validPlayer() {
/* 247 */     return $$0 -> {
/*     */         BlockPos $$1 = $$0.blockPosition();
/* 249 */         return ($$0.isAlive() && this.level.getRaidAt($$1) == this);
/*     */       };
/*     */   }
/*     */   
/*     */   private void updatePlayers() {
/* 254 */     Set<ServerPlayer> $$0 = Sets.newHashSet(this.raidEvent.getPlayers());
/* 255 */     List<ServerPlayer> $$1 = this.level.getPlayers(validPlayer());
/*     */     
/* 257 */     for (ServerPlayer $$2 : $$1) {
/* 258 */       if (!$$0.contains($$2)) {
/* 259 */         this.raidEvent.addPlayer($$2);
/*     */       }
/*     */     } 
/*     */     
/* 263 */     for (ServerPlayer $$3 : $$0) {
/* 264 */       if (!$$1.contains($$3)) {
/* 265 */         this.raidEvent.removePlayer($$3);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getMaxBadOmenLevel() {
/* 271 */     return 5;
/*     */   }
/*     */   
/*     */   public int getBadOmenLevel() {
/* 275 */     return this.badOmenLevel;
/*     */   }
/*     */   
/*     */   public void setBadOmenLevel(int $$0) {
/* 279 */     this.badOmenLevel = $$0;
/*     */   }
/*     */   
/*     */   public void absorbBadOmen(Player $$0) {
/* 283 */     if ($$0.hasEffect(MobEffects.BAD_OMEN)) {
/* 284 */       this.badOmenLevel += $$0.getEffect(MobEffects.BAD_OMEN).getAmplifier() + 1;
/* 285 */       this.badOmenLevel = Mth.clamp(this.badOmenLevel, 0, getMaxBadOmenLevel());
/*     */     } 
/* 287 */     $$0.removeEffect(MobEffects.BAD_OMEN);
/*     */   }
/*     */   
/*     */   public void stop() {
/* 291 */     this.active = false;
/* 292 */     this.raidEvent.removeAllPlayers();
/* 293 */     this.status = RaidStatus.STOPPED;
/*     */   }
/*     */   
/*     */   public void tick() {
/* 297 */     if (isStopped()) {
/*     */       return;
/*     */     }
/*     */     
/* 301 */     if (this.status == RaidStatus.ONGOING) {
/* 302 */       boolean $$0 = this.active;
/* 303 */       this.active = this.level.hasChunkAt(this.center);
/*     */       
/* 305 */       if (this.level.getDifficulty() == Difficulty.PEACEFUL) {
/* 306 */         stop();
/*     */         
/*     */         return;
/*     */       } 
/* 310 */       if ($$0 != this.active) {
/* 311 */         this.raidEvent.setVisible(this.active);
/*     */       }
/*     */ 
/*     */       
/* 315 */       if (!this.active) {
/*     */         return;
/*     */       }
/*     */       
/* 319 */       if (!this.level.isVillage(this.center))
/*     */       {
/*     */         
/* 322 */         moveRaidCenterToNearbyVillageSection();
/*     */       }
/*     */ 
/*     */       
/* 326 */       if (!this.level.isVillage(this.center))
/*     */       {
/* 328 */         if (this.groupsSpawned > 0) {
/* 329 */           this.status = RaidStatus.LOSS;
/*     */         } else {
/* 331 */           stop();
/*     */         } 
/*     */       }
/*     */       
/* 335 */       this.ticksActive++;
/*     */       
/* 337 */       if (this.ticksActive >= 48000L) {
/* 338 */         stop();
/*     */         
/*     */         return;
/*     */       } 
/* 342 */       int $$1 = getTotalRaidersAlive();
/*     */ 
/*     */       
/* 345 */       if ($$1 == 0 && hasMoreWaves()) {
/* 346 */         if (this.raidCooldownTicks > 0) {
/* 347 */           boolean $$2 = this.waveSpawnPos.isPresent();
/* 348 */           boolean $$3 = (!$$2 && this.raidCooldownTicks % 5 == 0);
/*     */ 
/*     */           
/* 351 */           if ($$2 && !this.level.isPositionEntityTicking(this.waveSpawnPos.get())) {
/* 352 */             $$3 = true;
/*     */           }
/*     */ 
/*     */           
/* 356 */           if ($$3) {
/*     */             
/* 358 */             int $$4 = 0;
/* 359 */             if (this.raidCooldownTicks < 100) {
/* 360 */               $$4 = 1;
/* 361 */             } else if (this.raidCooldownTicks < 40) {
/* 362 */               $$4 = 2;
/*     */             } 
/* 364 */             this.waveSpawnPos = getValidSpawnPos($$4);
/*     */           } 
/*     */           
/* 367 */           if (this.raidCooldownTicks == 300 || this.raidCooldownTicks % 20 == 0) {
/* 368 */             updatePlayers();
/*     */           }
/* 370 */           this.raidCooldownTicks--;
/* 371 */           this.raidEvent.setProgress(Mth.clamp((300 - this.raidCooldownTicks) / 300.0F, 0.0F, 1.0F));
/* 372 */         } else if (this.raidCooldownTicks == 0 && this.groupsSpawned > 0) {
/* 373 */           this.raidCooldownTicks = 300;
/* 374 */           this.raidEvent.setName(RAID_NAME_COMPONENT);
/*     */           
/*     */           return;
/*     */         } 
/*     */       }
/*     */       
/* 380 */       if (this.ticksActive % 20L == 0L) {
/* 381 */         updatePlayers();
/* 382 */         updateRaiders();
/*     */         
/* 384 */         if ($$1 > 0) {
/*     */           
/* 386 */           if ($$1 <= 2) {
/* 387 */             this.raidEvent.setName((Component)RAID_NAME_COMPONENT.copy().append(" - ").append((Component)Component.translatable("event.minecraft.raid.raiders_remaining", new Object[] { Integer.valueOf($$1) })));
/*     */           } else {
/* 389 */             this.raidEvent.setName(RAID_NAME_COMPONENT);
/*     */           } 
/*     */         } else {
/* 392 */           this.raidEvent.setName(RAID_NAME_COMPONENT);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 402 */       boolean $$5 = false;
/* 403 */       int $$6 = 0;
/* 404 */       while (shouldSpawnGroup()) {
/*     */         
/* 406 */         BlockPos $$7 = this.waveSpawnPos.isPresent() ? this.waveSpawnPos.get() : findRandomSpawnPos($$6, 20);
/* 407 */         if ($$7 != null) {
/* 408 */           this.started = true;
/* 409 */           spawnGroup($$7);
/* 410 */           if (!$$5) {
/* 411 */             playSound($$7);
/* 412 */             $$5 = true;
/*     */           } 
/*     */         } else {
/* 415 */           $$6++;
/*     */         } 
/*     */         
/* 418 */         if ($$6 > 3) {
/* 419 */           stop();
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */       
/* 425 */       if (isStarted() && !hasMoreWaves() && $$1 == 0) {
/* 426 */         if (this.postRaidTicks < 40) {
/* 427 */           this.postRaidTicks++;
/*     */         } else {
/* 429 */           this.status = RaidStatus.VICTORY;
/* 430 */           for (UUID $$8 : this.heroesOfTheVillage) {
/* 431 */             Entity $$9 = this.level.getEntity($$8);
/* 432 */             if ($$9 instanceof LivingEntity) { LivingEntity $$10 = (LivingEntity)$$9; if (!$$9.isSpectator()) {
/* 433 */                 $$10.addEffect(new MobEffectInstance(MobEffects.HERO_OF_THE_VILLAGE, 48000, this.badOmenLevel - 1, false, false, true));
/* 434 */                 if ($$10 instanceof ServerPlayer) { ServerPlayer $$11 = (ServerPlayer)$$10;
/* 435 */                   $$11.awardStat(Stats.RAID_WIN);
/* 436 */                   CriteriaTriggers.RAID_WIN.trigger($$11); }
/*     */               
/*     */               }  }
/*     */           
/*     */           } 
/*     */         } 
/*     */       }
/* 443 */       setDirty();
/* 444 */     } else if (isOver()) {
/* 445 */       this.celebrationTicks++;
/* 446 */       if (this.celebrationTicks >= 600) {
/* 447 */         stop();
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 452 */       if (this.celebrationTicks % 20 == 0) {
/* 453 */         updatePlayers();
/* 454 */         this.raidEvent.setVisible(true);
/* 455 */         if (isVictory()) {
/* 456 */           this.raidEvent.setProgress(0.0F);
/* 457 */           this.raidEvent.setName(RAID_BAR_VICTORY_COMPONENT);
/*     */         } else {
/* 459 */           this.raidEvent.setName(RAID_BAR_DEFEAT_COMPONENT);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void moveRaidCenterToNearbyVillageSection() {
/* 466 */     Stream<SectionPos> $$0 = SectionPos.cube(SectionPos.of(this.center), 2);
/*     */ 
/*     */     
/* 469 */     Objects.requireNonNull(this.level); $$0.filter(this.level::isVillage)
/* 470 */       .map(SectionPos::center)
/* 471 */       .min(Comparator.comparingDouble($$0 -> $$0.distSqr((Vec3i)this.center)))
/* 472 */       .ifPresent(this::setCenter);
/*     */   }
/*     */   
/*     */   private Optional<BlockPos> getValidSpawnPos(int $$0) {
/* 476 */     for (int $$1 = 0; $$1 < 3; $$1++) {
/* 477 */       BlockPos $$2 = findRandomSpawnPos($$0, 1);
/* 478 */       if ($$2 != null) {
/* 479 */         return Optional.of($$2);
/*     */       }
/*     */     } 
/* 482 */     return Optional.empty();
/*     */   }
/*     */   
/*     */   private boolean hasMoreWaves() {
/* 486 */     if (hasBonusWave()) {
/* 487 */       return !hasSpawnedBonusWave();
/*     */     }
/* 489 */     return !isFinalWave();
/*     */   }
/*     */   
/*     */   private boolean isFinalWave() {
/* 493 */     return (getGroupsSpawned() == this.numGroups);
/*     */   }
/*     */   
/*     */   private boolean hasBonusWave() {
/* 497 */     return (this.badOmenLevel > 1);
/*     */   }
/*     */   
/*     */   private boolean hasSpawnedBonusWave() {
/* 501 */     return (getGroupsSpawned() > this.numGroups);
/*     */   }
/*     */   
/*     */   private boolean shouldSpawnBonusGroup() {
/* 505 */     return (isFinalWave() && getTotalRaidersAlive() == 0 && hasBonusWave());
/*     */   }
/*     */   
/*     */   private void updateRaiders() {
/* 509 */     Iterator<Set<Raider>> $$0 = this.groupRaiderMap.values().iterator();
/* 510 */     Set<Raider> $$1 = Sets.newHashSet();
/*     */     
/* 512 */     while ($$0.hasNext()) {
/* 513 */       Set<Raider> $$2 = $$0.next();
/* 514 */       for (Raider $$3 : $$2) {
/* 515 */         BlockPos $$4 = $$3.blockPosition();
/*     */ 
/*     */ 
/*     */         
/* 519 */         if ($$3.isRemoved() || $$3.level().dimension() != this.level.dimension() || this.center.distSqr((Vec3i)$$4) >= 12544.0D) {
/* 520 */           $$1.add($$3); continue;
/*     */         } 
/* 522 */         if ($$3.tickCount <= 600) {
/*     */           continue;
/*     */         }
/*     */ 
/*     */         
/* 527 */         if (this.level.getEntity($$3.getUUID()) == null) {
/* 528 */           $$1.add($$3);
/*     */         }
/*     */ 
/*     */         
/* 532 */         if (!this.level.isVillage($$4) && $$3.getNoActionTime() > 2400) {
/* 533 */           $$3.setTicksOutsideRaid($$3.getTicksOutsideRaid() + 1);
/*     */         }
/*     */         
/* 536 */         if ($$3.getTicksOutsideRaid() >= 30) {
/* 537 */           $$1.add($$3);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 542 */     for (Raider $$5 : $$1) {
/* 543 */       removeFromRaid($$5, true);
/*     */     }
/*     */   }
/*     */   
/*     */   private void playSound(BlockPos $$0) {
/* 548 */     float $$1 = 13.0F;
/* 549 */     int $$2 = 64;
/*     */     
/* 551 */     Collection<ServerPlayer> $$3 = this.raidEvent.getPlayers();
/* 552 */     long $$4 = this.random.nextLong();
/* 553 */     for (ServerPlayer $$5 : this.level.players()) {
/* 554 */       Vec3 $$6 = $$5.position();
/* 555 */       Vec3 $$7 = Vec3.atCenterOf((Vec3i)$$0);
/* 556 */       double $$8 = Math.sqrt(($$7.x - $$6.x) * ($$7.x - $$6.x) + ($$7.z - $$6.z) * ($$7.z - $$6.z));
/*     */       
/* 558 */       double $$9 = $$6.x + 13.0D / $$8 * ($$7.x - $$6.x);
/* 559 */       double $$10 = $$6.z + 13.0D / $$8 * ($$7.z - $$6.z);
/*     */       
/* 561 */       if ($$8 <= 64.0D || $$3.contains($$5)) {
/* 562 */         $$5.connection.send((Packet)new ClientboundSoundPacket((Holder)SoundEvents.RAID_HORN, SoundSource.NEUTRAL, $$9, $$5.getY(), $$10, 64.0F, 1.0F, $$4));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void spawnGroup(BlockPos $$0) {
/* 568 */     boolean $$1 = false;
/* 569 */     int $$2 = this.groupsSpawned + 1;
/* 570 */     this.totalHealth = 0.0F;
/* 571 */     DifficultyInstance $$3 = this.level.getCurrentDifficultyAt($$0);
/* 572 */     boolean $$4 = shouldSpawnBonusGroup();
/*     */     
/* 574 */     for (RaiderType $$5 : RaiderType.VALUES) {
/* 575 */       int $$6 = getDefaultNumSpawns($$5, $$2, $$4) + getPotentialBonusSpawns($$5, this.random, $$2, $$3, $$4);
/* 576 */       int $$7 = 0;
/*     */       
/* 578 */       for (int $$8 = 0; $$8 < $$6; $$8++) {
/* 579 */         Raider $$9 = (Raider)$$5.entityType.create((Level)this.level);
/* 580 */         if ($$9 == null) {
/*     */           break;
/*     */         }
/*     */         
/* 584 */         if (!$$1 && $$9.canBeLeader()) {
/* 585 */           $$9.setPatrolLeader(true);
/* 586 */           setLeader($$2, $$9);
/* 587 */           $$1 = true;
/*     */         } 
/*     */         
/* 590 */         joinRaid($$2, $$9, $$0, false);
/*     */         
/* 592 */         if ($$5.entityType == EntityType.RAVAGER) {
/* 593 */           Raider $$10 = null;
/* 594 */           if ($$2 == getNumGroups(Difficulty.NORMAL)) {
/* 595 */             $$10 = (Raider)EntityType.PILLAGER.create((Level)this.level);
/* 596 */           } else if ($$2 >= getNumGroups(Difficulty.HARD)) {
/*     */             
/* 598 */             if ($$7 == 0) {
/* 599 */               $$10 = (Raider)EntityType.EVOKER.create((Level)this.level);
/*     */             } else {
/* 601 */               $$10 = (Raider)EntityType.VINDICATOR.create((Level)this.level);
/*     */             } 
/*     */           } 
/* 604 */           $$7++;
/*     */           
/* 606 */           if ($$10 != null) {
/* 607 */             joinRaid($$2, $$10, $$0, false);
/* 608 */             $$10.moveTo($$0, 0.0F, 0.0F);
/* 609 */             $$10.startRiding((Entity)$$9);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 615 */     this.waveSpawnPos = Optional.empty();
/* 616 */     this.groupsSpawned++;
/* 617 */     updateBossbar();
/* 618 */     setDirty();
/*     */   }
/*     */   
/*     */   public void joinRaid(int $$0, Raider $$1, @Nullable BlockPos $$2, boolean $$3) {
/* 622 */     boolean $$4 = addWaveMob($$0, $$1);
/*     */     
/* 624 */     if ($$4) {
/* 625 */       $$1.setCurrentRaid(this);
/* 626 */       $$1.setWave($$0);
/* 627 */       $$1.setCanJoinRaid(true);
/* 628 */       $$1.setTicksOutsideRaid(0);
/*     */       
/* 630 */       if (!$$3 && $$2 != null) {
/* 631 */         $$1.setPos($$2.getX() + 0.5D, $$2.getY() + 1.0D, $$2.getZ() + 0.5D);
/* 632 */         $$1.finalizeSpawn((ServerLevelAccessor)this.level, this.level.getCurrentDifficultyAt($$2), MobSpawnType.EVENT, (SpawnGroupData)null, (CompoundTag)null);
/* 633 */         $$1.applyRaidBuffs($$0, false);
/* 634 */         $$1.setOnGround(true);
/* 635 */         this.level.addFreshEntityWithPassengers((Entity)$$1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void updateBossbar() {
/* 641 */     this.raidEvent.setProgress(Mth.clamp(getHealthOfLivingRaiders() / this.totalHealth, 0.0F, 1.0F));
/*     */   }
/*     */   
/*     */   public float getHealthOfLivingRaiders() {
/* 645 */     float $$0 = 0.0F;
/* 646 */     for (Set<Raider> $$1 : this.groupRaiderMap.values()) {
/* 647 */       for (Raider $$2 : $$1) {
/* 648 */         $$0 += $$2.getHealth();
/*     */       }
/*     */     } 
/* 651 */     return $$0;
/*     */   }
/*     */   
/*     */   private boolean shouldSpawnGroup() {
/* 655 */     return (this.raidCooldownTicks == 0 && (this.groupsSpawned < this.numGroups || shouldSpawnBonusGroup()) && getTotalRaidersAlive() == 0);
/*     */   }
/*     */   
/*     */   public int getTotalRaidersAlive() {
/* 659 */     return this.groupRaiderMap.values().stream().mapToInt(Set::size).sum();
/*     */   }
/*     */   
/*     */   public void removeFromRaid(Raider $$0, boolean $$1) {
/* 663 */     Set<Raider> $$2 = this.groupRaiderMap.get(Integer.valueOf($$0.getWave()));
/* 664 */     if ($$2 != null) {
/* 665 */       boolean $$3 = $$2.remove($$0);
/* 666 */       if ($$3) {
/*     */ 
/*     */         
/* 669 */         if ($$1) {
/* 670 */           this.totalHealth -= $$0.getHealth();
/*     */         }
/* 672 */         $$0.setCurrentRaid((Raid)null);
/* 673 */         updateBossbar();
/* 674 */         setDirty();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setDirty() {
/* 680 */     this.level.getRaids().setDirty();
/*     */   }
/*     */   
/*     */   public static ItemStack getLeaderBannerInstance() {
/* 684 */     ItemStack $$0 = new ItemStack((ItemLike)Items.WHITE_BANNER);
/* 685 */     CompoundTag $$1 = new CompoundTag();
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
/* 696 */     ListTag $$2 = (new BannerPattern.Builder()).addPattern(BannerPatterns.RHOMBUS_MIDDLE, DyeColor.CYAN).addPattern(BannerPatterns.STRIPE_BOTTOM, DyeColor.LIGHT_GRAY).addPattern(BannerPatterns.STRIPE_CENTER, DyeColor.GRAY).addPattern(BannerPatterns.BORDER, DyeColor.LIGHT_GRAY).addPattern(BannerPatterns.STRIPE_MIDDLE, DyeColor.BLACK).addPattern(BannerPatterns.HALF_HORIZONTAL, DyeColor.LIGHT_GRAY).addPattern(BannerPatterns.CIRCLE_MIDDLE, DyeColor.LIGHT_GRAY).addPattern(BannerPatterns.BORDER, DyeColor.BLACK).toListTag();
/*     */     
/* 698 */     $$1.put("Patterns", (Tag)$$2);
/*     */     
/* 700 */     BlockItem.setBlockEntityData($$0, BlockEntityType.BANNER, $$1);
/*     */     
/* 702 */     $$0.hideTooltipPart(ItemStack.TooltipPart.ADDITIONAL);
/* 703 */     $$0.setHoverName((Component)Component.translatable("block.minecraft.ominous_banner").withStyle(ChatFormatting.GOLD));
/*     */     
/* 705 */     return $$0;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Raider getLeader(int $$0) {
/* 710 */     return this.groupToLeaderMap.get(Integer.valueOf($$0));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private BlockPos findRandomSpawnPos(int $$0, int $$1) {
/* 715 */     int $$2 = ($$0 == 0) ? 2 : (2 - $$0);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 720 */     BlockPos.MutableBlockPos $$3 = new BlockPos.MutableBlockPos();
/*     */     
/* 722 */     for (int $$4 = 0; $$4 < $$1; $$4++) {
/* 723 */       float $$5 = this.level.random.nextFloat() * 6.2831855F;
/* 724 */       int $$6 = this.center.getX() + Mth.floor(Mth.cos($$5) * 32.0F * $$2) + this.level.random.nextInt(5);
/* 725 */       int $$7 = this.center.getZ() + Mth.floor(Mth.sin($$5) * 32.0F * $$2) + this.level.random.nextInt(5);
/* 726 */       int $$8 = this.level.getHeight(Heightmap.Types.WORLD_SURFACE, $$6, $$7);
/* 727 */       $$3.set($$6, $$8, $$7);
/*     */ 
/*     */       
/* 730 */       if (!this.level.isVillage((BlockPos)$$3) || $$0 >= 2) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 735 */         int $$9 = 10;
/* 736 */         if (this.level.hasChunksAt($$3.getX() - 10, $$3.getZ() - 10, $$3.getX() + 10, $$3.getZ() + 10))
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 742 */           if (this.level.isPositionEntityTicking((BlockPos)$$3))
/*     */           {
/*     */ 
/*     */ 
/*     */             
/* 747 */             if (NaturalSpawner.isSpawnPositionOk(SpawnPlacements.Type.ON_GROUND, (LevelReader)this.level, (BlockPos)$$3, EntityType.RAVAGER) || (this.level
/* 748 */               .getBlockState($$3.below()).is(Blocks.SNOW) && this.level.getBlockState((BlockPos)$$3).isAir()))
/*     */             {
/* 750 */               return (BlockPos)$$3; }  } 
/*     */         }
/*     */       } 
/*     */     } 
/* 754 */     return null;
/*     */   }
/*     */   
/*     */   private boolean addWaveMob(int $$0, Raider $$1) {
/* 758 */     return addWaveMob($$0, $$1, true);
/*     */   }
/*     */   
/*     */   public boolean addWaveMob(int $$0, Raider $$1, boolean $$2) {
/* 762 */     this.groupRaiderMap.computeIfAbsent(Integer.valueOf($$0), $$0 -> Sets.newHashSet());
/* 763 */     Set<Raider> $$3 = this.groupRaiderMap.get(Integer.valueOf($$0));
/* 764 */     Raider $$4 = null;
/*     */ 
/*     */ 
/*     */     
/* 768 */     for (Raider $$5 : $$3) {
/* 769 */       if ($$5.getUUID().equals($$1.getUUID())) {
/* 770 */         $$4 = $$5;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 775 */     if ($$4 != null) {
/* 776 */       $$3.remove($$4);
/* 777 */       $$3.add($$1);
/*     */     } 
/*     */     
/* 780 */     $$3.add($$1);
/* 781 */     if ($$2) {
/* 782 */       this.totalHealth += $$1.getHealth();
/*     */     }
/* 784 */     updateBossbar();
/* 785 */     setDirty();
/* 786 */     return true;
/*     */   }
/*     */   
/*     */   public void setLeader(int $$0, Raider $$1) {
/* 790 */     this.groupToLeaderMap.put(Integer.valueOf($$0), $$1);
/* 791 */     $$1.setItemSlot(EquipmentSlot.HEAD, getLeaderBannerInstance());
/* 792 */     $$1.setDropChance(EquipmentSlot.HEAD, 2.0F);
/*     */   }
/*     */   
/*     */   public void removeLeader(int $$0) {
/* 796 */     this.groupToLeaderMap.remove(Integer.valueOf($$0));
/*     */   }
/*     */   
/*     */   public BlockPos getCenter() {
/* 800 */     return this.center;
/*     */   }
/*     */   
/*     */   private void setCenter(BlockPos $$0) {
/* 804 */     this.center = $$0;
/*     */   }
/*     */   
/*     */   public int getId() {
/* 808 */     return this.id;
/*     */   }
/*     */   
/*     */   private int getDefaultNumSpawns(RaiderType $$0, int $$1, boolean $$2) {
/* 812 */     return $$2 ? $$0.spawnsPerWaveBeforeBonus[this.numGroups] : $$0.spawnsPerWaveBeforeBonus[$$1];
/*     */   }
/*     */   
/*     */   private int getPotentialBonusSpawns(RaiderType $$0, RandomSource $$1, int $$2, DifficultyInstance $$3, boolean $$4) {
/*     */     int $$11, $$12;
/* 817 */     Difficulty $$5 = $$3.getDifficulty();
/* 818 */     boolean $$6 = ($$5 == Difficulty.EASY);
/* 819 */     boolean $$7 = ($$5 == Difficulty.NORMAL);
/*     */     
/* 821 */     switch ($$0) {
/*     */       
/*     */       case EASY:
/* 824 */         if (!$$6 && $$2 > 2 && $$2 != 4) {
/* 825 */           int $$8 = 1;
/*     */           break;
/*     */         } 
/* 828 */         return 0;
/*     */       
/*     */       case NORMAL:
/*     */       case HARD:
/* 832 */         if ($$6) {
/* 833 */           int $$9 = $$1.nextInt(2); break;
/* 834 */         }  if ($$7) {
/* 835 */           int $$10 = 1; break;
/*     */         } 
/* 837 */         $$11 = 2;
/*     */         break;
/*     */       
/*     */       case null:
/* 841 */         $$12 = (!$$6 && $$4) ? 1 : 0;
/*     */         break;
/*     */       default:
/* 844 */         return 0;
/*     */     } 
/*     */     
/* 847 */     return ($$12 > 0) ? $$1.nextInt($$12 + 1) : 0;
/*     */   }
/*     */   
/*     */   public boolean isActive() {
/* 851 */     return this.active;
/*     */   }
/*     */   
/*     */   public CompoundTag save(CompoundTag $$0) {
/* 855 */     $$0.putInt("Id", this.id);
/* 856 */     $$0.putBoolean("Started", this.started);
/* 857 */     $$0.putBoolean("Active", this.active);
/* 858 */     $$0.putLong("TicksActive", this.ticksActive);
/* 859 */     $$0.putInt("BadOmenLevel", this.badOmenLevel);
/* 860 */     $$0.putInt("GroupsSpawned", this.groupsSpawned);
/* 861 */     $$0.putInt("PreRaidTicks", this.raidCooldownTicks);
/* 862 */     $$0.putInt("PostRaidTicks", this.postRaidTicks);
/* 863 */     $$0.putFloat("TotalHealth", this.totalHealth);
/* 864 */     $$0.putInt("NumGroups", this.numGroups);
/* 865 */     $$0.putString("Status", this.status.getName());
/*     */     
/* 867 */     $$0.putInt("CX", this.center.getX());
/* 868 */     $$0.putInt("CY", this.center.getY());
/* 869 */     $$0.putInt("CZ", this.center.getZ());
/*     */     
/* 871 */     ListTag $$1 = new ListTag();
/* 872 */     for (UUID $$2 : this.heroesOfTheVillage) {
/* 873 */       $$1.add(NbtUtils.createUUID($$2));
/*     */     }
/* 875 */     $$0.put("HeroesOfTheVillage", (Tag)$$1);
/*     */     
/* 877 */     return $$0;
/*     */   }
/*     */   
/*     */   public int getNumGroups(Difficulty $$0) {
/* 881 */     switch ($$0) {
/*     */       case EASY:
/* 883 */         return 3;
/*     */       case NORMAL:
/* 885 */         return 5;
/*     */       case HARD:
/* 887 */         return 7;
/*     */     } 
/* 889 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getEnchantOdds() {
/* 894 */     int $$0 = getBadOmenLevel();
/* 895 */     if ($$0 == 2) {
/* 896 */       return 0.1F;
/*     */     }
/* 898 */     if ($$0 == 3) {
/* 899 */       return 0.25F;
/*     */     }
/* 901 */     if ($$0 == 4) {
/* 902 */       return 0.5F;
/*     */     }
/* 904 */     if ($$0 == 5) {
/* 905 */       return 0.75F;
/*     */     }
/* 907 */     return 0.0F;
/*     */   }
/*     */   
/*     */   public void addHeroOfTheVillage(Entity $$0) {
/* 911 */     this.heroesOfTheVillage.add($$0.getUUID());
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\raid\Raid.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */