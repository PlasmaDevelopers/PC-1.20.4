/*     */ package net.minecraft.world.level.block.entity.trialspawner;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectListIterator;
/*     */ import java.util.Optional;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.Difficulty;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.SpawnPlacements;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.ClipContext;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.level.SpawnData;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.level.storage.loot.LootParams;
/*     */ import net.minecraft.world.level.storage.loot.LootTable;
/*     */ import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.HitResult;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class TrialSpawner
/*     */ {
/*     */   public static final int DETECT_PLAYER_SPAWN_BUFFER = 40;
/*     */   private static final int MAX_MOB_TRACKING_DISTANCE = 47;
/*  57 */   private static final int MAX_MOB_TRACKING_DISTANCE_SQR = Mth.square(47);
/*     */   private static final float SPAWNING_AMBIENT_SOUND_CHANCE = 0.02F;
/*     */   
/*     */   public Codec<TrialSpawner> codec() {
/*  61 */     return RecordCodecBuilder.create($$0 -> $$0.group((App)TrialSpawnerConfig.MAP_CODEC.forGetter(TrialSpawner::getConfig), (App)TrialSpawnerData.MAP_CODEC.forGetter(TrialSpawner::getData)).apply((Applicative)$$0, ()));
/*     */   }
/*     */ 
/*     */   
/*     */   private final TrialSpawnerConfig config;
/*     */   
/*     */   private final TrialSpawnerData data;
/*     */   
/*     */   private final StateAccessor stateAccessor;
/*     */   private PlayerDetector playerDetector;
/*     */   private boolean overridePeacefulAndMobSpawnRule;
/*     */   
/*     */   public TrialSpawner(StateAccessor $$0, PlayerDetector $$1) {
/*  74 */     this(TrialSpawnerConfig.DEFAULT, new TrialSpawnerData(), $$0, $$1);
/*     */   }
/*     */   
/*     */   public TrialSpawner(TrialSpawnerConfig $$0, TrialSpawnerData $$1, StateAccessor $$2, PlayerDetector $$3) {
/*  78 */     this.config = $$0;
/*  79 */     this.data = $$1;
/*  80 */     this.data.setSpawnPotentialsFromConfig($$0);
/*  81 */     this.stateAccessor = $$2;
/*  82 */     this.playerDetector = $$3;
/*     */   }
/*     */   
/*     */   public TrialSpawnerConfig getConfig() {
/*  86 */     return this.config;
/*     */   }
/*     */   
/*     */   public TrialSpawnerData getData() {
/*  90 */     return this.data;
/*     */   }
/*     */   
/*     */   public TrialSpawnerState getState() {
/*  94 */     return this.stateAccessor.getState();
/*     */   }
/*     */   
/*     */   public void setState(Level $$0, TrialSpawnerState $$1) {
/*  98 */     this.stateAccessor.setState($$0, $$1);
/*     */   }
/*     */   
/*     */   public void markUpdated() {
/* 102 */     this.stateAccessor.markUpdated();
/*     */   }
/*     */   
/*     */   public PlayerDetector getPlayerDetector() {
/* 106 */     return this.playerDetector;
/*     */   }
/*     */   
/*     */   public boolean canSpawnInLevel(Level $$0) {
/* 110 */     if (this.overridePeacefulAndMobSpawnRule) {
/* 111 */       return true;
/*     */     }
/*     */     
/* 114 */     if ($$0.getDifficulty() == Difficulty.PEACEFUL) {
/* 115 */       return false;
/*     */     }
/*     */     
/* 118 */     return $$0.getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING);
/*     */   }
/*     */   
/*     */   public Optional<UUID> spawnMob(ServerLevel $$0, BlockPos $$1) {
/* 122 */     RandomSource $$2 = $$0.getRandom();
/* 123 */     SpawnData $$3 = this.data.getOrCreateNextSpawnData(this, $$0.getRandom());
/* 124 */     CompoundTag $$4 = $$3.entityToSpawn();
/* 125 */     ListTag $$5 = $$4.getList("Pos", 6);
/* 126 */     Optional<EntityType<?>> $$6 = EntityType.by($$4);
/* 127 */     if ($$6.isEmpty()) {
/* 128 */       return Optional.empty();
/*     */     }
/*     */     
/* 131 */     int $$7 = $$5.size();
/*     */     
/* 133 */     double $$8 = ($$7 >= 1) ? $$5.getDouble(0) : ($$1.getX() + ($$2.nextDouble() - $$2.nextDouble()) * this.config.spawnRange() + 0.5D);
/* 134 */     double $$9 = ($$7 >= 2) ? $$5.getDouble(1) : ($$1.getY() + $$2.nextInt(3) - 1);
/* 135 */     double $$10 = ($$7 >= 3) ? $$5.getDouble(2) : ($$1.getZ() + ($$2.nextDouble() - $$2.nextDouble()) * this.config.spawnRange() + 0.5D);
/*     */     
/* 137 */     if (!$$0.noCollision(((EntityType)$$6.get()).getAABB($$8, $$9, $$10))) {
/* 138 */       return Optional.empty();
/*     */     }
/*     */     
/* 141 */     Vec3 $$11 = new Vec3($$8, $$9, $$10);
/*     */ 
/*     */     
/* 144 */     if (!inLineOfSight((Level)$$0, $$1.getCenter(), $$11)) {
/* 145 */       return Optional.empty();
/*     */     }
/*     */     
/* 148 */     BlockPos $$12 = BlockPos.containing((Position)$$11);
/* 149 */     if (!SpawnPlacements.checkSpawnRules($$6.get(), (ServerLevelAccessor)$$0, MobSpawnType.TRIAL_SPAWNER, $$12, $$0.getRandom())) {
/* 150 */       return Optional.empty();
/*     */     }
/*     */     
/* 153 */     Entity $$13 = EntityType.loadEntityRecursive($$4, (Level)$$0, $$4 -> {
/*     */           $$4.moveTo($$0, $$1, $$2, $$3.nextFloat() * 360.0F, 0.0F);
/*     */           
/*     */           return $$4;
/*     */         });
/* 158 */     if ($$13 == null) {
/* 159 */       return Optional.empty();
/*     */     }
/*     */ 
/*     */     
/* 163 */     if ($$13 instanceof Mob) { Mob $$14 = (Mob)$$13;
/* 164 */       if (!$$14.checkSpawnObstruction((LevelReader)$$0)) {
/* 165 */         return Optional.empty();
/*     */       }
/* 167 */       if ($$3.getEntityToSpawn().size() == 1 && $$3.getEntityToSpawn().contains("id", 8)) {
/* 168 */         $$14.finalizeSpawn((ServerLevelAccessor)$$0, $$0.getCurrentDifficultyAt($$14.blockPosition()), MobSpawnType.TRIAL_SPAWNER, null, null);
/* 169 */         $$14.setPersistenceRequired();
/*     */       }  }
/*     */ 
/*     */     
/* 173 */     if (!$$0.tryAddFreshEntityWithPassengers($$13)) {
/* 174 */       return Optional.empty();
/*     */     }
/*     */     
/* 177 */     $$0.levelEvent(3011, $$1, 0);
/* 178 */     $$0.levelEvent(3012, $$12, 0);
/* 179 */     $$0.gameEvent($$13, GameEvent.ENTITY_PLACE, $$12);
/*     */     
/* 181 */     return Optional.of($$13.getUUID());
/*     */   }
/*     */   
/*     */   public void ejectReward(ServerLevel $$0, BlockPos $$1, ResourceLocation $$2) {
/* 185 */     LootTable $$3 = $$0.getServer().getLootData().getLootTable($$2);
/* 186 */     LootParams $$4 = (new LootParams.Builder($$0)).create(LootContextParamSets.EMPTY);
/*     */     
/* 188 */     ObjectArrayList<ItemStack> $$5 = $$3.getRandomItems($$4);
/* 189 */     if (!$$5.isEmpty()) {
/* 190 */       for (ObjectListIterator<ItemStack> objectListIterator = $$5.iterator(); objectListIterator.hasNext(); ) { ItemStack $$6 = objectListIterator.next();
/* 191 */         DefaultDispenseItemBehavior.spawnItem((Level)$$0, $$6, 2, Direction.UP, (Position)Vec3.atBottomCenterOf((Vec3i)$$1).relative(Direction.UP, 1.2D)); }
/*     */ 
/*     */       
/* 194 */       $$0.levelEvent(3014, $$1, 0);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void tickClient(Level $$0, BlockPos $$1) {
/* 199 */     if (!canSpawnInLevel($$0)) {
/* 200 */       this.data.oSpin = this.data.spin;
/*     */       
/*     */       return;
/*     */     } 
/* 204 */     TrialSpawnerState $$2 = getState();
/*     */     
/* 206 */     $$2.emitParticles($$0, $$1);
/*     */     
/* 208 */     if ($$2.hasSpinningMob()) {
/* 209 */       double $$3 = Math.max(0L, this.data.nextMobSpawnsAt - $$0.getGameTime());
/* 210 */       this.data.oSpin = this.data.spin;
/* 211 */       this.data.spin = (this.data.spin + $$2.spinningMobSpeed() / ($$3 + 200.0D)) % 360.0D;
/*     */     } 
/*     */     
/* 214 */     if ($$2.isCapableOfSpawning()) {
/* 215 */       RandomSource $$4 = $$0.getRandom();
/* 216 */       if ($$4.nextFloat() <= 0.02F) {
/* 217 */         $$0.playLocalSound($$1, SoundEvents.TRIAL_SPAWNER_AMBIENT, SoundSource.BLOCKS, $$4.nextFloat() * 0.25F + 0.75F, $$4.nextFloat() + 0.5F, false);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void tickServer(ServerLevel $$0, BlockPos $$1) {
/* 223 */     TrialSpawnerState $$2 = getState();
/*     */     
/* 225 */     if (!canSpawnInLevel((Level)$$0)) {
/* 226 */       if ($$2.isCapableOfSpawning()) {
/* 227 */         this.data.reset();
/* 228 */         setState((Level)$$0, TrialSpawnerState.INACTIVE);
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/* 233 */     if (this.data.currentMobs.removeIf($$2 -> shouldMobBeUntracked($$0, $$1, $$2))) {
/* 234 */       this.data.nextMobSpawnsAt = $$0.getGameTime() + this.config.ticksBetweenSpawn();
/*     */     }
/*     */     
/* 237 */     TrialSpawnerState $$3 = $$2.tickAndGetNext($$1, this, $$0);
/*     */     
/* 239 */     if ($$3 != $$2) {
/* 240 */       setState((Level)$$0, $$3);
/*     */     }
/*     */   }
/*     */   
/*     */   private static boolean shouldMobBeUntracked(ServerLevel $$0, BlockPos $$1, UUID $$2) {
/* 245 */     Entity $$3 = $$0.getEntity($$2);
/* 246 */     return ($$3 == null || 
/* 247 */       !$$3.isAlive() || 
/* 248 */       !$$3.level().dimension().equals($$0.dimension()) || $$3
/* 249 */       .blockPosition().distSqr((Vec3i)$$1) > MAX_MOB_TRACKING_DISTANCE_SQR);
/*     */   }
/*     */   
/*     */   private static boolean inLineOfSight(Level $$0, Vec3 $$1, Vec3 $$2) {
/* 253 */     BlockHitResult $$3 = $$0.clip(new ClipContext($$2, $$1, ClipContext.Block.VISUAL, ClipContext.Fluid.NONE, CollisionContext.empty()));
/* 254 */     return ($$3.getBlockPos().equals(BlockPos.containing((Position)$$1)) || $$3.getType() == HitResult.Type.MISS);
/*     */   }
/*     */   
/*     */   public static void addSpawnParticles(Level $$0, BlockPos $$1, RandomSource $$2) {
/* 258 */     for (int $$3 = 0; $$3 < 20; $$3++) {
/* 259 */       double $$4 = $$1.getX() + 0.5D + ($$2.nextDouble() - 0.5D) * 2.0D;
/* 260 */       double $$5 = $$1.getY() + 0.5D + ($$2.nextDouble() - 0.5D) * 2.0D;
/* 261 */       double $$6 = $$1.getZ() + 0.5D + ($$2.nextDouble() - 0.5D) * 2.0D;
/*     */       
/* 263 */       $$0.addParticle((ParticleOptions)ParticleTypes.SMOKE, $$4, $$5, $$6, 0.0D, 0.0D, 0.0D);
/* 264 */       $$0.addParticle((ParticleOptions)ParticleTypes.FLAME, $$4, $$5, $$6, 0.0D, 0.0D, 0.0D);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void addDetectPlayerParticles(Level $$0, BlockPos $$1, RandomSource $$2, int $$3) {
/* 269 */     for (int $$4 = 0; $$4 < 30 + Math.min($$3, 10) * 5; $$4++) {
/* 270 */       double $$5 = (2.0F * $$2.nextFloat() - 1.0F) * 0.65D;
/* 271 */       double $$6 = (2.0F * $$2.nextFloat() - 1.0F) * 0.65D;
/* 272 */       double $$7 = $$1.getX() + 0.5D + $$5;
/* 273 */       double $$8 = $$1.getY() + 0.1D + $$2.nextFloat() * 0.8D;
/* 274 */       double $$9 = $$1.getZ() + 0.5D + $$6;
/*     */       
/* 276 */       $$0.addParticle((ParticleOptions)ParticleTypes.TRIAL_SPAWNER_DETECTION, $$7, $$8, $$9, 0.0D, 0.0D, 0.0D);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void addEjectItemParticles(Level $$0, BlockPos $$1, RandomSource $$2) {
/* 281 */     for (int $$3 = 0; $$3 < 20; $$3++) {
/* 282 */       double $$4 = $$1.getX() + 0.4D + $$2.nextDouble() * 0.2D;
/* 283 */       double $$5 = $$1.getY() + 0.4D + $$2.nextDouble() * 0.2D;
/* 284 */       double $$6 = $$1.getZ() + 0.4D + $$2.nextDouble() * 0.2D;
/* 285 */       double $$7 = $$2.nextGaussian() * 0.02D;
/* 286 */       double $$8 = $$2.nextGaussian() * 0.02D;
/* 287 */       double $$9 = $$2.nextGaussian() * 0.02D;
/* 288 */       $$0.addParticle((ParticleOptions)ParticleTypes.SMALL_FLAME, $$4, $$5, $$6, $$7, $$8, $$9 * 0.25D);
/* 289 */       $$0.addParticle((ParticleOptions)ParticleTypes.SMOKE, $$4, $$5, $$6, $$7, $$8, $$9);
/*     */     } 
/*     */   }
/*     */   
/*     */   @Deprecated(forRemoval = true)
/*     */   @VisibleForTesting
/*     */   public void setPlayerDetector(PlayerDetector $$0) {
/* 296 */     this.playerDetector = $$0;
/*     */   }
/*     */   
/*     */   @Deprecated(forRemoval = true)
/*     */   @VisibleForTesting
/*     */   public void overridePeacefulAndMobSpawnRule() {
/* 302 */     this.overridePeacefulAndMobSpawnRule = true;
/*     */   }
/*     */   
/*     */   public static interface StateAccessor {
/*     */     void setState(Level param1Level, TrialSpawnerState param1TrialSpawnerState);
/*     */     
/*     */     TrialSpawnerState getState();
/*     */     
/*     */     void markUpdated();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\trialspawner\TrialSpawner.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */