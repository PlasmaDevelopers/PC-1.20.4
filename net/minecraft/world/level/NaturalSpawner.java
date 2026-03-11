/*     */ package net.minecraft.world.level;
/*     */ 
/*     */ import com.mojang.logging.LogUtils;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMaps;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.core.QuartPos;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.tags.BiomeTags;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.tags.FluidTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.VisibleForDebug;
/*     */ import net.minecraft.util.random.WeightedRandomList;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.MobCategory;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.SpawnGroupData;
/*     */ import net.minecraft.world.entity.SpawnPlacements;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.level.biome.Biome;
/*     */ import net.minecraft.world.level.biome.MobSpawnSettings;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.chunk.ChunkAccess;
/*     */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*     */ import net.minecraft.world.level.chunk.LevelChunk;
/*     */ import net.minecraft.world.level.levelgen.Heightmap;
/*     */ import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
/*     */ import net.minecraft.world.level.levelgen.structure.Structure;
/*     */ import net.minecraft.world.level.levelgen.structure.structures.NetherFortressStructure;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.level.pathfinder.PathComputationType;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ 
/*     */ public final class NaturalSpawner
/*     */ {
/*  55 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private static final int MIN_SPAWN_DISTANCE = 24;
/*     */   public static final int SPAWN_DISTANCE_CHUNK = 8;
/*     */   public static final int SPAWN_DISTANCE_BLOCK = 128;
/*  60 */   static final int MAGIC_NUMBER = (int)Math.pow(17.0D, 2.0D); private static final MobCategory[] SPAWNING_CATEGORIES; static {
/*  61 */     SPAWNING_CATEGORIES = (MobCategory[])Stream.<MobCategory>of(MobCategory.values()).filter($$0 -> ($$0 != MobCategory.MISC)).toArray($$0 -> new MobCategory[$$0]);
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface ChunkGetter
/*     */   {
/*     */     void query(long param1Long, Consumer<LevelChunk> param1Consumer);
/*     */   }
/*     */   
/*     */   public static class SpawnState {
/*     */     private final int spawnableChunkCount;
/*     */     private final Object2IntOpenHashMap<MobCategory> mobCategoryCounts;
/*     */     private final PotentialCalculator spawnPotential;
/*     */     private final Object2IntMap<MobCategory> unmodifiableMobCategoryCounts;
/*     */     
/*     */     SpawnState(int $$0, Object2IntOpenHashMap<MobCategory> $$1, PotentialCalculator $$2, LocalMobCapCalculator $$3) {
/*  77 */       this.spawnableChunkCount = $$0;
/*  78 */       this.mobCategoryCounts = $$1;
/*  79 */       this.spawnPotential = $$2;
/*  80 */       this.localMobCapCalculator = $$3;
/*  81 */       this.unmodifiableMobCategoryCounts = Object2IntMaps.unmodifiable((Object2IntMap)$$1);
/*     */     } private final LocalMobCapCalculator localMobCapCalculator; @Nullable
/*     */     private BlockPos lastCheckedPos; @Nullable
/*     */     private EntityType<?> lastCheckedType; private double lastCharge; private boolean canSpawn(EntityType<?> $$0, BlockPos $$1, ChunkAccess $$2) {
/*  85 */       this.lastCheckedPos = $$1;
/*  86 */       this.lastCheckedType = $$0;
/*     */       
/*  88 */       MobSpawnSettings.MobSpawnCost $$3 = NaturalSpawner.getRoughBiome($$1, $$2).getMobSettings().getMobSpawnCost($$0);
/*  89 */       if ($$3 == null) {
/*  90 */         this.lastCharge = 0.0D;
/*  91 */         return true;
/*     */       } 
/*  93 */       double $$4 = $$3.charge();
/*  94 */       this.lastCharge = $$4;
/*  95 */       double $$5 = this.spawnPotential.getPotentialEnergyChange($$1, $$4);
/*  96 */       return ($$5 <= $$3.energyBudget());
/*     */     }
/*     */     private void afterSpawn(Mob $$0, ChunkAccess $$1) {
/*     */       double $$7;
/* 100 */       EntityType<?> $$2 = $$0.getType();
/*     */       
/* 102 */       BlockPos $$3 = $$0.blockPosition();
/* 103 */       if ($$3.equals(this.lastCheckedPos) && $$2 == this.lastCheckedType) {
/* 104 */         double $$4 = this.lastCharge;
/*     */       } else {
/*     */         
/* 107 */         MobSpawnSettings.MobSpawnCost $$5 = NaturalSpawner.getRoughBiome($$3, $$1).getMobSettings().getMobSpawnCost($$2);
/* 108 */         if ($$5 != null) {
/* 109 */           double $$6 = $$5.charge();
/*     */         } else {
/* 111 */           $$7 = 0.0D;
/*     */         } 
/*     */       } 
/* 114 */       this.spawnPotential.addCharge($$3, $$7);
/* 115 */       MobCategory $$8 = $$2.getCategory();
/* 116 */       this.mobCategoryCounts.addTo($$8, 1);
/* 117 */       this.localMobCapCalculator.addMob(new ChunkPos($$3), $$8);
/*     */     }
/*     */     
/*     */     public int getSpawnableChunkCount() {
/* 121 */       return this.spawnableChunkCount;
/*     */     }
/*     */     
/*     */     public Object2IntMap<MobCategory> getMobCategoryCounts() {
/* 125 */       return this.unmodifiableMobCategoryCounts;
/*     */     }
/*     */ 
/*     */     
/*     */     boolean canSpawnForCategory(MobCategory $$0, ChunkPos $$1) {
/* 130 */       int $$2 = $$0.getMaxInstancesPerChunk() * this.spawnableChunkCount / NaturalSpawner.MAGIC_NUMBER;
/* 131 */       if (this.mobCategoryCounts.getInt($$0) >= $$2) {
/* 132 */         return false;
/*     */       }
/*     */       
/* 135 */       if (!this.localMobCapCalculator.canSpawn($$0, $$1)) return false;
/*     */     
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
/*     */   public static SpawnState createState(int $$0, Iterable<Entity> $$1, ChunkGetter $$2, LocalMobCapCalculator $$3) {
/* 158 */     PotentialCalculator $$4 = new PotentialCalculator();
/* 159 */     Object2IntOpenHashMap<MobCategory> $$5 = new Object2IntOpenHashMap();
/*     */     
/* 161 */     for (Iterator<Entity> iterator = $$1.iterator(); iterator.hasNext(); ) { Entity $$6 = iterator.next();
/* 162 */       if ($$6 instanceof Mob) { Mob $$7 = (Mob)$$6; if ($$7.isPersistenceRequired() || $$7.requiresCustomPersistence())
/*     */           continue;  }
/*     */       
/* 165 */       MobCategory $$8 = $$6.getType().getCategory();
/* 166 */       if ($$8 == MobCategory.MISC) {
/*     */         continue;
/*     */       }
/*     */       
/* 170 */       BlockPos $$9 = $$6.blockPosition();
/*     */       
/* 172 */       $$2.query(ChunkPos.asLong($$9), $$6 -> {
/*     */             MobSpawnSettings.MobSpawnCost $$7 = getRoughBiome($$0, (ChunkAccess)$$6).getMobSettings().getMobSpawnCost($$1.getType());
/*     */             
/*     */             if ($$7 != null) {
/*     */               $$2.addCharge($$1.blockPosition(), $$7.charge());
/*     */             }
/*     */             if ($$1 instanceof Mob) {
/*     */               $$3.addMob($$6.getPos(), $$4);
/*     */             }
/*     */             $$5.addTo($$4, 1);
/*     */           }); }
/*     */     
/* 184 */     return new SpawnState($$0, $$5, $$4, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   static Biome getRoughBiome(BlockPos $$0, ChunkAccess $$1) {
/* 189 */     return (Biome)$$1.getNoiseBiome(QuartPos.fromBlock($$0.getX()), QuartPos.fromBlock($$0.getY()), QuartPos.fromBlock($$0.getZ())).value();
/*     */   }
/*     */   
/*     */   public static void spawnForChunk(ServerLevel $$0, LevelChunk $$1, SpawnState $$2, boolean $$3, boolean $$4, boolean $$5) {
/* 193 */     $$0.getProfiler().push("spawner");
/*     */     
/* 195 */     for (MobCategory $$6 : SPAWNING_CATEGORIES) {
/* 196 */       if (($$3 || !$$6.isFriendly()) && ($$4 || $$6
/* 197 */         .isFriendly()) && ($$5 || 
/* 198 */         !$$6.isPersistent()) && $$2
/* 199 */         .canSpawnForCategory($$6, $$1.getPos())) {
/*     */         
/* 201 */         Objects.requireNonNull($$2); Objects.requireNonNull($$2); spawnCategoryForChunk($$6, $$0, $$1, $$2::canSpawn, $$2::afterSpawn);
/*     */       } 
/*     */     } 
/* 204 */     $$0.getProfiler().pop();
/*     */   }
/*     */   
/*     */   public static void spawnCategoryForChunk(MobCategory $$0, ServerLevel $$1, LevelChunk $$2, SpawnPredicate $$3, AfterSpawnCallback $$4) {
/* 208 */     BlockPos $$5 = getRandomPosWithin((Level)$$1, $$2);
/*     */     
/* 210 */     if ($$5.getY() < $$1.getMinBuildHeight() + 1) {
/*     */       return;
/*     */     }
/* 213 */     spawnCategoryForPosition($$0, $$1, (ChunkAccess)$$2, $$5, $$3, $$4);
/*     */   }
/*     */   
/*     */   @VisibleForDebug
/*     */   public static void spawnCategoryForPosition(MobCategory $$0, ServerLevel $$1, BlockPos $$2) {
/* 218 */     spawnCategoryForPosition($$0, $$1, $$1.getChunk($$2), $$2, ($$0, $$1, $$2) -> true, ($$0, $$1) -> {
/*     */         
/*     */         });
/*     */   }
/* 222 */   public static void spawnCategoryForPosition(MobCategory $$0, ServerLevel $$1, ChunkAccess $$2, BlockPos $$3, SpawnPredicate $$4, AfterSpawnCallback $$5) { StructureManager $$6 = $$1.structureManager();
/* 223 */     ChunkGenerator $$7 = $$1.getChunkSource().getGenerator();
/* 224 */     int $$8 = $$3.getY();
/*     */     
/* 226 */     BlockState $$9 = $$2.getBlockState($$3);
/* 227 */     if ($$9.isRedstoneConductor((BlockGetter)$$2, $$3)) {
/*     */       return;
/*     */     }
/*     */     
/* 231 */     BlockPos.MutableBlockPos $$10 = new BlockPos.MutableBlockPos();
/* 232 */     int $$11 = 0;
/*     */     
/* 234 */     for (int $$12 = 0; $$12 < 3; $$12++) {
/* 235 */       int $$13 = $$3.getX();
/* 236 */       int $$14 = $$3.getZ();
/* 237 */       int $$15 = 6;
/*     */       
/* 239 */       MobSpawnSettings.SpawnerData $$16 = null;
/* 240 */       SpawnGroupData $$17 = null;
/*     */       
/* 242 */       int $$18 = Mth.ceil($$1.random.nextFloat() * 4.0F);
/* 243 */       int $$19 = 0;
/*     */ 
/*     */       
/* 246 */       for (int $$20 = 0; $$20 < $$18; $$20++) {
/* 247 */         $$13 += $$1.random.nextInt(6) - $$1.random.nextInt(6);
/* 248 */         $$14 += $$1.random.nextInt(6) - $$1.random.nextInt(6);
/*     */         
/* 250 */         $$10.set($$13, $$8, $$14);
/*     */         
/* 252 */         double $$21 = $$13 + 0.5D;
/* 253 */         double $$22 = $$14 + 0.5D;
/*     */         
/* 255 */         Player $$23 = $$1.getNearestPlayer($$21, $$8, $$22, -1.0D, false);
/* 256 */         if ($$23 != null) {
/*     */ 
/*     */ 
/*     */           
/* 260 */           double $$24 = $$23.distanceToSqr($$21, $$8, $$22);
/* 261 */           if (isRightDistanceToPlayerAndSpawnPoint($$1, $$2, $$10, $$24)) {
/*     */ 
/*     */ 
/*     */             
/* 265 */             if ($$16 == null) {
/* 266 */               Optional<MobSpawnSettings.SpawnerData> $$25 = getRandomSpawnMobAt($$1, $$6, $$7, $$0, $$1.random, (BlockPos)$$10);
/* 267 */               if ($$25.isEmpty()) {
/*     */                 break;
/*     */               }
/* 270 */               $$16 = $$25.get();
/*     */ 
/*     */               
/* 273 */               $$18 = $$16.minCount + $$1.random.nextInt(1 + $$16.maxCount - $$16.minCount);
/*     */             } 
/*     */             
/* 276 */             if (isValidSpawnPostitionForType($$1, $$0, $$6, $$7, $$16, $$10, $$24))
/*     */             {
/*     */ 
/*     */               
/* 280 */               if ($$4.test($$16.type, (BlockPos)$$10, $$2)) {
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 285 */                 Mob $$26 = getMobForSpawn($$1, $$16.type);
/* 286 */                 if ($$26 == null) {
/*     */                   return;
/*     */                 }
/*     */                 
/* 290 */                 $$26.moveTo($$21, $$8, $$22, $$1.random.nextFloat() * 360.0F, 0.0F);
/*     */                 
/* 292 */                 if (isValidPositionForMob($$1, $$26, $$24)) {
/*     */ 
/*     */ 
/*     */                   
/* 296 */                   $$17 = $$26.finalizeSpawn((ServerLevelAccessor)$$1, $$1.getCurrentDifficultyAt($$26.blockPosition()), MobSpawnType.NATURAL, $$17, null);
/*     */                   
/* 298 */                   $$11++;
/* 299 */                   $$19++;
/* 300 */                   $$1.addFreshEntityWithPassengers((Entity)$$26);
/* 301 */                   $$5.run($$26, $$2);
/*     */                   
/* 303 */                   if ($$11 >= $$26.getMaxSpawnClusterSize()) {
/*     */                     return;
/*     */                   }
/* 306 */                   if ($$26.isMaxGroupSizeReached($$19))
/*     */                     break; 
/*     */                 } 
/*     */               }  } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }  } private static boolean isRightDistanceToPlayerAndSpawnPoint(ServerLevel $$0, ChunkAccess $$1, BlockPos.MutableBlockPos $$2, double $$3) {
/* 314 */     if ($$3 <= 576.0D) {
/* 315 */       return false;
/*     */     }
/* 317 */     if ($$0.getSharedSpawnPos().closerToCenterThan((Position)new Vec3($$2.getX() + 0.5D, $$2.getY(), $$2.getZ() + 0.5D), 24.0D)) {
/* 318 */       return false;
/*     */     }
/*     */     
/* 321 */     return (Objects.equals(new ChunkPos((BlockPos)$$2), $$1.getPos()) || $$0.isNaturalSpawningAllowed((BlockPos)$$2));
/*     */   }
/*     */   
/*     */   private static boolean isValidSpawnPostitionForType(ServerLevel $$0, MobCategory $$1, StructureManager $$2, ChunkGenerator $$3, MobSpawnSettings.SpawnerData $$4, BlockPos.MutableBlockPos $$5, double $$6) {
/* 325 */     EntityType<?> $$7 = $$4.type;
/*     */     
/* 327 */     if ($$7.getCategory() == MobCategory.MISC) {
/* 328 */       return false;
/*     */     }
/*     */     
/* 331 */     if (!$$7.canSpawnFarFromPlayer() && $$6 > ($$7.getCategory().getDespawnDistance() * $$7.getCategory().getDespawnDistance())) {
/* 332 */       return false;
/*     */     }
/*     */     
/* 335 */     if (!$$7.canSummon() || !canSpawnMobAt($$0, $$2, $$3, $$1, $$4, (BlockPos)$$5)) {
/* 336 */       return false;
/*     */     }
/*     */     
/* 339 */     SpawnPlacements.Type $$8 = SpawnPlacements.getPlacementType($$7);
/* 340 */     if (!isSpawnPositionOk($$8, (LevelReader)$$0, (BlockPos)$$5, $$7)) {
/* 341 */       return false;
/*     */     }
/* 343 */     if (!SpawnPlacements.checkSpawnRules($$7, (ServerLevelAccessor)$$0, MobSpawnType.NATURAL, (BlockPos)$$5, $$0.random)) {
/* 344 */       return false;
/*     */     }
/* 346 */     if (!$$0.noCollision($$7.getAABB($$5.getX() + 0.5D, $$5.getY(), $$5.getZ() + 0.5D))) {
/* 347 */       return false;
/*     */     }
/* 349 */     return true;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static Mob getMobForSpawn(ServerLevel $$0, EntityType<?> $$1) {
/*     */     try {
/* 355 */       Entity entity = $$1.create((Level)$$0); if (entity instanceof Mob) { Mob $$2 = (Mob)entity;
/* 356 */         return $$2; }
/*     */       
/* 358 */       LOGGER.warn("Can't spawn entity of type: {}", BuiltInRegistries.ENTITY_TYPE.getKey($$1));
/* 359 */     } catch (Exception $$3) {
/* 360 */       LOGGER.warn("Failed to create mob", $$3);
/*     */     } 
/* 362 */     return null;
/*     */   }
/*     */   
/*     */   private static boolean isValidPositionForMob(ServerLevel $$0, Mob $$1, double $$2) {
/* 366 */     if ($$2 > ($$1.getType().getCategory().getDespawnDistance() * $$1.getType().getCategory().getDespawnDistance()) && $$1.removeWhenFarAway($$2)) {
/* 367 */       return false;
/*     */     }
/* 369 */     return ($$1.checkSpawnRules((LevelAccessor)$$0, MobSpawnType.NATURAL) && $$1.checkSpawnObstruction((LevelReader)$$0));
/*     */   }
/*     */   
/*     */   private static Optional<MobSpawnSettings.SpawnerData> getRandomSpawnMobAt(ServerLevel $$0, StructureManager $$1, ChunkGenerator $$2, MobCategory $$3, RandomSource $$4, BlockPos $$5) {
/* 373 */     Holder<Biome> $$6 = $$0.getBiome($$5);
/*     */     
/* 375 */     if ($$3 == MobCategory.WATER_AMBIENT && $$6.is(BiomeTags.REDUCED_WATER_AMBIENT_SPAWNS) && $$4.nextFloat() < 0.98F) {
/* 376 */       return Optional.empty();
/*     */     }
/* 378 */     return mobsAt($$0, $$1, $$2, $$3, $$5, $$6).getRandom($$4);
/*     */   }
/*     */   
/*     */   private static boolean canSpawnMobAt(ServerLevel $$0, StructureManager $$1, ChunkGenerator $$2, MobCategory $$3, MobSpawnSettings.SpawnerData $$4, BlockPos $$5) {
/* 382 */     return mobsAt($$0, $$1, $$2, $$3, $$5, null).unwrap().contains($$4);
/*     */   }
/*     */ 
/*     */   
/*     */   private static WeightedRandomList<MobSpawnSettings.SpawnerData> mobsAt(ServerLevel $$0, StructureManager $$1, ChunkGenerator $$2, MobCategory $$3, BlockPos $$4, @Nullable Holder<Biome> $$5) {
/* 387 */     if (isInNetherFortressBounds($$4, $$0, $$3, $$1)) {
/* 388 */       return NetherFortressStructure.FORTRESS_ENEMIES;
/*     */     }
/* 390 */     return $$2.getMobsAt(($$5 != null) ? $$5 : $$0.getBiome($$4), $$1, $$3, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isInNetherFortressBounds(BlockPos $$0, ServerLevel $$1, MobCategory $$2, StructureManager $$3) {
/* 395 */     if ($$2 != MobCategory.MONSTER || !$$1.getBlockState($$0.below()).is(Blocks.NETHER_BRICKS)) {
/* 396 */       return false;
/*     */     }
/* 398 */     Structure $$4 = (Structure)$$3.registryAccess().registryOrThrow(Registries.STRUCTURE).get(BuiltinStructures.FORTRESS);
/* 399 */     if ($$4 == null) {
/* 400 */       return false;
/*     */     }
/* 402 */     return $$3.getStructureAt($$0, $$4).isValid();
/*     */   }
/*     */   
/*     */   private static BlockPos getRandomPosWithin(Level $$0, LevelChunk $$1) {
/* 406 */     ChunkPos $$2 = $$1.getPos();
/* 407 */     int $$3 = $$2.getMinBlockX() + $$0.random.nextInt(16);
/* 408 */     int $$4 = $$2.getMinBlockZ() + $$0.random.nextInt(16);
/*     */     
/* 410 */     int $$5 = $$1.getHeight(Heightmap.Types.WORLD_SURFACE, $$3, $$4) + 1;
/* 411 */     int $$6 = Mth.randomBetweenInclusive($$0.random, $$0.getMinBuildHeight(), $$5);
/*     */     
/* 413 */     return new BlockPos($$3, $$6, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isValidEmptySpawnBlock(BlockGetter $$0, BlockPos $$1, BlockState $$2, FluidState $$3, EntityType<?> $$4) {
/* 418 */     if ($$2.isCollisionShapeFullBlock($$0, $$1)) {
/* 419 */       return false;
/*     */     }
/*     */     
/* 422 */     if ($$2.isSignalSource()) {
/* 423 */       return false;
/*     */     }
/*     */     
/* 426 */     if (!$$3.isEmpty()) {
/* 427 */       return false;
/*     */     }
/*     */     
/* 430 */     if ($$2.is(BlockTags.PREVENT_MOB_SPAWNING_INSIDE)) {
/* 431 */       return false;
/*     */     }
/*     */     
/* 434 */     if ($$4.isBlockDangerous($$2)) {
/* 435 */       return false;
/*     */     }
/* 437 */     return true;
/*     */   } @FunctionalInterface
/*     */   public static interface SpawnPredicate {
/*     */     boolean test(EntityType<?> param1EntityType, BlockPos param1BlockPos, ChunkAccess param1ChunkAccess); } public static boolean isSpawnPositionOk(SpawnPlacements.Type $$0, LevelReader $$1, BlockPos $$2, @Nullable EntityType<?> $$3) {
/* 441 */     if ($$0 == SpawnPlacements.Type.NO_RESTRICTIONS) {
/* 442 */       return true;
/*     */     }
/* 444 */     if ($$3 == null || !$$1.getWorldBorder().isWithinBounds($$2)) {
/* 445 */       return false;
/*     */     }
/* 447 */     BlockState $$4 = $$1.getBlockState($$2);
/* 448 */     FluidState $$5 = $$1.getFluidState($$2);
/*     */     
/* 450 */     BlockPos $$6 = $$2.above();
/* 451 */     BlockPos $$7 = $$2.below();
/* 452 */     switch ($$0) {
/*     */       
/*     */       case IN_WATER:
/* 455 */         return ($$5.is(FluidTags.WATER) && 
/* 456 */           !$$1.getBlockState($$6).isRedstoneConductor($$1, $$6));
/*     */       case IN_LAVA:
/* 458 */         return $$5.is(FluidTags.LAVA);
/*     */     } 
/*     */     
/* 461 */     BlockState $$8 = $$1.getBlockState($$7);
/* 462 */     if (!$$8.isValidSpawn($$1, $$7, $$3)) {
/* 463 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 467 */     return (isValidEmptySpawnBlock($$1, $$2, $$4, $$5, $$3) && isValidEmptySpawnBlock($$1, $$6, $$1.getBlockState($$6), $$1.getFluidState($$6), $$3));
/*     */   } @FunctionalInterface
/*     */   public static interface AfterSpawnCallback {
/*     */     void run(Mob param1Mob, ChunkAccess param1ChunkAccess); }
/*     */   public static void spawnMobsForChunkGeneration(ServerLevelAccessor $$0, Holder<Biome> $$1, ChunkPos $$2, RandomSource $$3) {
/* 472 */     MobSpawnSettings $$4 = ((Biome)$$1.value()).getMobSettings();
/* 473 */     WeightedRandomList<MobSpawnSettings.SpawnerData> $$5 = $$4.getMobs(MobCategory.CREATURE);
/* 474 */     if ($$5.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 478 */     int $$6 = $$2.getMinBlockX();
/* 479 */     int $$7 = $$2.getMinBlockZ();
/*     */ 
/*     */     
/* 482 */     while ($$3.nextFloat() < $$4.getCreatureProbability()) {
/* 483 */       Optional<MobSpawnSettings.SpawnerData> $$8 = $$5.getRandom($$3);
/* 484 */       if ($$8.isEmpty()) {
/*     */         continue;
/*     */       }
/* 487 */       MobSpawnSettings.SpawnerData $$9 = $$8.get();
/*     */       
/* 489 */       int $$10 = $$9.minCount + $$3.nextInt(1 + $$9.maxCount - $$9.minCount);
/* 490 */       SpawnGroupData $$11 = null;
/*     */       
/* 492 */       int $$12 = $$6 + $$3.nextInt(16);
/* 493 */       int $$13 = $$7 + $$3.nextInt(16);
/* 494 */       int $$14 = $$12;
/* 495 */       int $$15 = $$13;
/*     */       
/* 497 */       for (int $$16 = 0; $$16 < $$10; $$16++) {
/* 498 */         boolean $$17 = false;
/* 499 */         for (int $$18 = 0; !$$17 && $$18 < 4; $$18++) {
/*     */ 
/*     */           
/* 502 */           BlockPos $$19 = getTopNonCollidingPos($$0, $$9.type, $$12, $$13);
/* 503 */           if ($$9.type.canSummon() && isSpawnPositionOk(SpawnPlacements.getPlacementType($$9.type), $$0, $$19, $$9.type)) {
/* 504 */             Entity $$23; float $$20 = $$9.type.getWidth();
/* 505 */             double $$21 = Mth.clamp($$12, $$6 + $$20, $$6 + 16.0D - $$20);
/* 506 */             double $$22 = Mth.clamp($$13, $$7 + $$20, $$7 + 16.0D - $$20);
/*     */             
/* 508 */             if (!$$0.noCollision($$9.type.getAABB($$21, $$19.getY(), $$22))) {
/*     */               continue;
/*     */             }
/*     */             
/* 512 */             if (!SpawnPlacements.checkSpawnRules($$9.type, $$0, MobSpawnType.CHUNK_GENERATION, BlockPos.containing($$21, $$19.getY(), $$22), $$0.getRandom())) {
/*     */               continue;
/*     */             }
/*     */ 
/*     */             
/*     */             try {
/* 518 */               $$23 = $$9.type.create((Level)$$0.getLevel());
/* 519 */             } catch (Exception $$24) {
/* 520 */               LOGGER.warn("Failed to create mob", $$24);
/*     */               
/*     */               continue;
/*     */             } 
/* 524 */             if ($$23 == null) {
/*     */               continue;
/*     */             }
/*     */             
/* 528 */             $$23.moveTo($$21, $$19.getY(), $$22, $$3.nextFloat() * 360.0F, 0.0F);
/*     */             
/* 530 */             if ($$23 instanceof Mob) { Mob $$26 = (Mob)$$23;
/* 531 */               if ($$26.checkSpawnRules($$0, MobSpawnType.CHUNK_GENERATION) && $$26.checkSpawnObstruction($$0)) {
/* 532 */                 $$11 = $$26.finalizeSpawn($$0, $$0.getCurrentDifficultyAt($$26.blockPosition()), MobSpawnType.CHUNK_GENERATION, $$11, null);
/* 533 */                 $$0.addFreshEntityWithPassengers((Entity)$$26);
/* 534 */                 $$17 = true;
/*     */               }  }
/*     */           
/*     */           } 
/*     */           
/* 539 */           $$12 += $$3.nextInt(5) - $$3.nextInt(5);
/* 540 */           $$13 += $$3.nextInt(5) - $$3.nextInt(5);
/* 541 */           while ($$12 < $$6 || $$12 >= $$6 + 16 || $$13 < $$7 || $$13 >= $$7 + 16) {
/* 542 */             $$12 = $$14 + $$3.nextInt(5) - $$3.nextInt(5);
/* 543 */             $$13 = $$15 + $$3.nextInt(5) - $$3.nextInt(5);
/*     */           } 
/*     */           continue;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   private static BlockPos getTopNonCollidingPos(LevelReader $$0, EntityType<?> $$1, int $$2, int $$3) {
/* 551 */     int $$4 = $$0.getHeight(SpawnPlacements.getHeightmapType($$1), $$2, $$3);
/* 552 */     BlockPos.MutableBlockPos $$5 = new BlockPos.MutableBlockPos($$2, $$4, $$3);
/*     */     
/* 554 */     if ($$0.dimensionType().hasCeiling()) {
/*     */       
/*     */       do {
/* 557 */         $$5.move(Direction.DOWN);
/* 558 */       } while (!$$0.getBlockState((BlockPos)$$5).isAir());
/*     */       do {
/* 560 */         $$5.move(Direction.DOWN);
/* 561 */       } while ($$0.getBlockState((BlockPos)$$5).isAir() && $$5.getY() > $$0.getMinBuildHeight());
/*     */     } 
/*     */     
/* 564 */     if (SpawnPlacements.getPlacementType($$1) == SpawnPlacements.Type.ON_GROUND) {
/* 565 */       BlockPos $$6 = $$5.below();
/* 566 */       if ($$0.getBlockState($$6).isPathfindable($$0, $$6, PathComputationType.LAND)) {
/* 567 */         return $$6;
/*     */       }
/*     */     } 
/*     */     
/* 571 */     return $$5.immutable();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\NaturalSpawner.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */