/*     */ package net.minecraft.world.level.dimension.end;
/*     */ 
/*     */ import com.google.common.collect.ContiguousSet;
/*     */ import com.google.common.collect.DiscreteDomain;
/*     */ import com.google.common.collect.Range;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function7;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.data.worldgen.features.EndFeatures;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.server.level.FullChunkStatus;
/*     */ import net.minecraft.server.level.ServerBossEvent;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.server.level.TicketType;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.Unit;
/*     */ import net.minecraft.world.BossEvent;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntitySelector;
/*     */ import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
/*     */ import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.WorldGenLevel;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.state.pattern.BlockInWorld;
/*     */ import net.minecraft.world.level.block.state.pattern.BlockPattern;
/*     */ import net.minecraft.world.level.chunk.ChunkAccess;
/*     */ import net.minecraft.world.level.chunk.LevelChunk;
/*     */ import net.minecraft.world.level.levelgen.Heightmap;
/*     */ import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
/*     */ import net.minecraft.world.level.levelgen.feature.EndPodiumFeature;
/*     */ import net.minecraft.world.level.levelgen.feature.SpikeFeature;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
/*     */ 
/*     */ public class EndDragonFight {
/*     */   public static final class Data extends Record {
/*     */     final boolean needsStateScanning;
/*     */     final boolean dragonKilled;
/*     */     final boolean previouslyKilled;
/*     */     final boolean isRespawning;
/*     */     final Optional<UUID> dragonUUID;
/*     */     final Optional<BlockPos> exitPortalLocation;
/*     */     final Optional<List<Integer>> gateways;
/*     */     public static final Codec<Data> CODEC;
/*     */     
/*  67 */     public Data(boolean $$0, boolean $$1, boolean $$2, boolean $$3, Optional<UUID> $$4, Optional<BlockPos> $$5, Optional<List<Integer>> $$6) { this.needsStateScanning = $$0; this.dragonKilled = $$1; this.previouslyKilled = $$2; this.isRespawning = $$3; this.dragonUUID = $$4; this.exitPortalLocation = $$5; this.gateways = $$6; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/dimension/end/EndDragonFight$Data;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #67	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*  67 */       //   0	7	0	this	Lnet/minecraft/world/level/dimension/end/EndDragonFight$Data; } public boolean needsStateScanning() { return this.needsStateScanning; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/dimension/end/EndDragonFight$Data;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #67	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/dimension/end/EndDragonFight$Data; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/dimension/end/EndDragonFight$Data;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #67	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/dimension/end/EndDragonFight$Data;
/*  67 */       //   0	8	1	$$0	Ljava/lang/Object; } public boolean dragonKilled() { return this.dragonKilled; } public boolean previouslyKilled() { return this.previouslyKilled; } public boolean isRespawning() { return this.isRespawning; } public Optional<UUID> dragonUUID() { return this.dragonUUID; } public Optional<BlockPos> exitPortalLocation() { return this.exitPortalLocation; } public Optional<List<Integer>> gateways() { return this.gateways; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/*  76 */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.BOOL.fieldOf("NeedsStateScanning").orElse(Boolean.valueOf(true)).forGetter(Data::needsStateScanning), (App)Codec.BOOL.fieldOf("DragonKilled").orElse(Boolean.valueOf(false)).forGetter(Data::dragonKilled), (App)Codec.BOOL.fieldOf("PreviouslyKilled").orElse(Boolean.valueOf(false)).forGetter(Data::previouslyKilled), (App)Codec.BOOL.optionalFieldOf("IsRespawning", Boolean.valueOf(false)).forGetter(Data::isRespawning), (App)UUIDUtil.CODEC.optionalFieldOf("Dragon").forGetter(Data::dragonUUID), (App)BlockPos.CODEC.optionalFieldOf("ExitPortalLocation").forGetter(Data::exitPortalLocation), (App)Codec.list((Codec)Codec.INT).optionalFieldOf("Gateways").forGetter(Data::gateways)).apply((Applicative)$$0, Data::new));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  87 */     public static final Data DEFAULT = new Data(true, false, false, false, Optional.empty(), Optional.empty(), Optional.empty());
/*     */   }
/*     */   
/*  90 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private static final int MAX_TICKS_BEFORE_DRAGON_RESPAWN = 1200;
/*     */   private static final int TIME_BETWEEN_CRYSTAL_SCANS = 100;
/*     */   public static final int TIME_BETWEEN_PLAYER_SCANS = 20;
/*     */   private static final int ARENA_SIZE_CHUNKS = 8;
/*     */   public static final int ARENA_TICKET_LEVEL = 9;
/*     */   private static final int GATEWAY_COUNT = 20;
/*     */   private static final int GATEWAY_DISTANCE = 96;
/*     */   public static final int DRAGON_SPAWN_Y = 128;
/*     */   private final Predicate<Entity> validPlayer;
/* 101 */   private final ServerBossEvent dragonEvent = (ServerBossEvent)(new ServerBossEvent((Component)Component.translatable("entity.minecraft.ender_dragon"), BossEvent.BossBarColor.PINK, BossEvent.BossBarOverlay.PROGRESS)).setPlayBossMusic(true).setCreateWorldFog(true);
/*     */   private final ServerLevel level;
/*     */   private final BlockPos origin;
/* 104 */   private final ObjectArrayList<Integer> gateways = new ObjectArrayList();
/*     */   private final BlockPattern exitPortalPattern;
/*     */   private int ticksSinceDragonSeen;
/*     */   private int crystalsAlive;
/*     */   private int ticksSinceCrystalsScanned;
/* 109 */   private int ticksSinceLastPlayerScan = 21;
/*     */   
/*     */   private boolean dragonKilled;
/*     */   private boolean previouslyKilled;
/*     */   private boolean skipArenaLoadedCheck = false;
/*     */   @Nullable
/*     */   private UUID dragonUUID;
/*     */   private boolean needsStateScanning = true;
/*     */   @Nullable
/*     */   private BlockPos portalLocation;
/*     */   @Nullable
/*     */   private DragonRespawnAnimation respawnStage;
/*     */   private int respawnTime;
/*     */   @Nullable
/*     */   private List<EndCrystal> respawnCrystals;
/*     */   
/*     */   public EndDragonFight(ServerLevel $$0, long $$1, Data $$2) {
/* 126 */     this($$0, $$1, $$2, BlockPos.ZERO);
/*     */   }
/*     */   
/*     */   public EndDragonFight(ServerLevel $$0, long $$1, Data $$2, BlockPos $$3) {
/* 130 */     this.level = $$0;
/* 131 */     this.origin = $$3;
/* 132 */     this.validPlayer = EntitySelector.ENTITY_STILL_ALIVE.and(EntitySelector.withinDistance($$3.getX(), (128 + $$3.getY()), $$3.getZ(), 192.0D));
/* 133 */     this.needsStateScanning = $$2.needsStateScanning;
/* 134 */     this.dragonUUID = $$2.dragonUUID.orElse(null);
/* 135 */     this.dragonKilled = $$2.dragonKilled;
/* 136 */     this.previouslyKilled = $$2.previouslyKilled;
/* 137 */     if ($$2.isRespawning) {
/* 138 */       this.respawnStage = DragonRespawnAnimation.START;
/*     */     }
/* 140 */     this.portalLocation = $$2.exitPortalLocation.orElse(null);
/* 141 */     this.gateways.addAll($$2.gateways.orElseGet(() -> {
/*     */             ObjectArrayList<Integer> $$1 = new ObjectArrayList((Collection)ContiguousSet.create(Range.closedOpen(Integer.valueOf(0), Integer.valueOf(20)), DiscreteDomain.integers()));
/*     */             
/*     */             Util.shuffle((List)$$1, RandomSource.create($$0));
/*     */             return (List)$$1;
/*     */           }));
/* 147 */     this
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
/* 194 */       .exitPortalPattern = BlockPatternBuilder.start().aisle(new String[] { "       ", "       ", "       ", "   #   ", "       ", "       ", "       " }).aisle(new String[] { "       ", "       ", "       ", "   #   ", "       ", "       ", "       " }).aisle(new String[] { "       ", "       ", "       ", "   #   ", "       ", "       ", "       " }).aisle(new String[] { "  ###  ", " #   # ", "#     #", "#  #  #", "#     #", " #   # ", "  ###  " }).aisle(new String[] { "       ", "  ###  ", " ##### ", " ##### ", " ##### ", "  ###  ", "       " }).where('#', BlockInWorld.hasState((Predicate)BlockPredicate.forBlock(Blocks.BEDROCK))).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   @VisibleForTesting
/*     */   public void skipArenaLoadedCheck() {
/* 206 */     this.skipArenaLoadedCheck = true;
/*     */   }
/*     */   
/*     */   public Data saveData() {
/* 210 */     return new Data(this.needsStateScanning, this.dragonKilled, this.previouslyKilled, false, 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 215 */         Optional.ofNullable(this.dragonUUID), 
/* 216 */         Optional.ofNullable(this.portalLocation), 
/* 217 */         (Optional)Optional.of(this.gateways));
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 222 */     this.dragonEvent.setVisible(!this.dragonKilled);
/*     */     
/* 224 */     if (++this.ticksSinceLastPlayerScan >= 20) {
/* 225 */       updatePlayers();
/* 226 */       this.ticksSinceLastPlayerScan = 0;
/*     */     } 
/*     */     
/* 229 */     if (!this.dragonEvent.getPlayers().isEmpty()) {
/* 230 */       this.level.getChunkSource().addRegionTicket(TicketType.DRAGON, new ChunkPos(0, 0), 9, Unit.INSTANCE);
/*     */       
/* 232 */       boolean $$0 = isArenaLoaded();
/*     */       
/* 234 */       if (this.needsStateScanning && $$0) {
/* 235 */         scanState();
/* 236 */         this.needsStateScanning = false;
/*     */       } 
/*     */       
/* 239 */       if (this.respawnStage != null) {
/* 240 */         if (this.respawnCrystals == null && $$0) {
/* 241 */           this.respawnStage = null;
/* 242 */           tryRespawn();
/*     */         } 
/* 244 */         this.respawnStage.tick(this.level, this, this.respawnCrystals, this.respawnTime++, this.portalLocation);
/*     */       } 
/*     */       
/* 247 */       if (!this.dragonKilled) {
/* 248 */         if ((this.dragonUUID == null || ++this.ticksSinceDragonSeen >= 1200) && $$0) {
/* 249 */           findOrCreateDragon();
/* 250 */           this.ticksSinceDragonSeen = 0;
/*     */         } 
/*     */         
/* 253 */         if (++this.ticksSinceCrystalsScanned >= 100 && $$0) {
/* 254 */           updateCrystalCount();
/* 255 */           this.ticksSinceCrystalsScanned = 0;
/*     */         } 
/*     */       } 
/*     */     } else {
/* 259 */       this.level.getChunkSource().removeRegionTicket(TicketType.DRAGON, new ChunkPos(0, 0), 9, Unit.INSTANCE);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void scanState() {
/* 264 */     LOGGER.info("Scanning for legacy world dragon fight...");
/* 265 */     boolean $$0 = hasActiveExitPortal();
/* 266 */     if ($$0) {
/* 267 */       LOGGER.info("Found that the dragon has been killed in this world already.");
/* 268 */       this.previouslyKilled = true;
/*     */     } else {
/* 270 */       LOGGER.info("Found that the dragon has not yet been killed in this world.");
/* 271 */       this.previouslyKilled = false;
/* 272 */       if (findExitPortal() == null) {
/* 273 */         spawnExitPortal(false);
/*     */       }
/*     */     } 
/*     */     
/* 277 */     List<? extends EnderDragon> $$1 = this.level.getDragons();
/* 278 */     if ($$1.isEmpty()) {
/* 279 */       this.dragonKilled = true;
/*     */     } else {
/* 281 */       EnderDragon $$2 = $$1.get(0);
/* 282 */       this.dragonUUID = $$2.getUUID();
/* 283 */       LOGGER.info("Found that there's a dragon still alive ({})", $$2);
/* 284 */       this.dragonKilled = false;
/*     */       
/* 286 */       if (!$$0) {
/* 287 */         LOGGER.info("But we didn't have a portal, let's remove it.");
/* 288 */         $$2.discard();
/* 289 */         this.dragonUUID = null;
/*     */       } 
/*     */     } 
/*     */     
/* 293 */     if (!this.previouslyKilled && this.dragonKilled)
/*     */     {
/* 295 */       this.dragonKilled = false;
/*     */     }
/*     */   }
/*     */   
/*     */   private void findOrCreateDragon() {
/* 300 */     List<? extends EnderDragon> $$0 = this.level.getDragons();
/* 301 */     if ($$0.isEmpty()) {
/* 302 */       LOGGER.debug("Haven't seen the dragon, respawning it");
/* 303 */       createNewDragon();
/*     */     } else {
/* 305 */       LOGGER.debug("Haven't seen our dragon, but found another one to use.");
/* 306 */       this.dragonUUID = ((EnderDragon)$$0.get(0)).getUUID();
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void setRespawnStage(DragonRespawnAnimation $$0) {
/* 311 */     if (this.respawnStage == null) {
/* 312 */       throw new IllegalStateException("Dragon respawn isn't in progress, can't skip ahead in the animation.");
/*     */     }
/*     */     
/* 315 */     this.respawnTime = 0;
/* 316 */     if ($$0 == DragonRespawnAnimation.END) {
/* 317 */       this.respawnStage = null;
/* 318 */       this.dragonKilled = false;
/* 319 */       EnderDragon $$1 = createNewDragon();
/*     */       
/* 321 */       if ($$1 != null) {
/* 322 */         for (ServerPlayer $$2 : this.dragonEvent.getPlayers()) {
/* 323 */           CriteriaTriggers.SUMMONED_ENTITY.trigger($$2, (Entity)$$1);
/*     */         }
/*     */       }
/*     */     } else {
/* 327 */       this.respawnStage = $$0;
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean hasActiveExitPortal() {
/* 332 */     for (int $$0 = -8; $$0 <= 8; $$0++) {
/* 333 */       for (int $$1 = -8; $$1 <= 8; $$1++) {
/* 334 */         LevelChunk $$2 = this.level.getChunk($$0, $$1);
/* 335 */         for (BlockEntity $$3 : $$2.getBlockEntities().values()) {
/* 336 */           if ($$3 instanceof net.minecraft.world.level.block.entity.TheEndPortalBlockEntity) {
/* 337 */             return true;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 343 */     return false;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private BlockPattern.BlockPatternMatch findExitPortal() {
/* 348 */     ChunkPos $$0 = new ChunkPos(this.origin);
/* 349 */     for (int $$1 = -8 + $$0.x; $$1 <= 8 + $$0.x; $$1++) {
/* 350 */       for (int $$2 = -8 + $$0.z; $$2 <= 8 + $$0.z; $$2++) {
/* 351 */         LevelChunk $$3 = this.level.getChunk($$1, $$2);
/* 352 */         for (BlockEntity $$4 : $$3.getBlockEntities().values()) {
/* 353 */           if ($$4 instanceof net.minecraft.world.level.block.entity.TheEndPortalBlockEntity) {
/* 354 */             BlockPattern.BlockPatternMatch $$5 = this.exitPortalPattern.find((LevelReader)this.level, $$4.getBlockPos());
/* 355 */             if ($$5 != null) {
/* 356 */               BlockPos $$6 = $$5.getBlock(3, 3, 3).getPos();
/* 357 */               if (this.portalLocation == null) {
/* 358 */                 this.portalLocation = $$6;
/*     */               }
/* 360 */               return $$5;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 367 */     BlockPos $$7 = EndPodiumFeature.getLocation(this.origin);
/* 368 */     int $$8 = this.level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, $$7).getY();
/*     */     
/* 370 */     for (int $$9 = $$8; $$9 >= this.level.getMinBuildHeight(); $$9--) {
/* 371 */       BlockPattern.BlockPatternMatch $$10 = this.exitPortalPattern.find((LevelReader)this.level, new BlockPos($$7.getX(), $$9, $$7.getZ()));
/* 372 */       if ($$10 != null) {
/* 373 */         if (this.portalLocation == null) {
/* 374 */           this.portalLocation = $$10.getBlock(3, 3, 3).getPos();
/*     */         }
/* 376 */         return $$10;
/*     */       } 
/*     */     } 
/*     */     
/* 380 */     return null;
/*     */   }
/*     */   
/*     */   private boolean isArenaLoaded() {
/* 384 */     if (this.skipArenaLoadedCheck) {
/* 385 */       return true;
/*     */     }
/* 387 */     ChunkPos $$0 = new ChunkPos(this.origin);
/* 388 */     for (int $$1 = -8 + $$0.x; $$1 <= 8 + $$0.x; $$1++) {
/* 389 */       for (int $$2 = 8 + $$0.z; $$2 <= 8 + $$0.z; $$2++) {
/* 390 */         ChunkAccess $$3 = this.level.getChunk($$1, $$2, ChunkStatus.FULL, false);
/* 391 */         if (!($$3 instanceof LevelChunk)) {
/* 392 */           return false;
/*     */         }
/* 394 */         FullChunkStatus $$4 = ((LevelChunk)$$3).getFullStatus();
/* 395 */         if (!$$4.isOrAfter(FullChunkStatus.BLOCK_TICKING)) {
/* 396 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/* 400 */     return true;
/*     */   }
/*     */   
/*     */   private void updatePlayers() {
/* 404 */     Set<ServerPlayer> $$0 = Sets.newHashSet();
/* 405 */     for (ServerPlayer $$1 : this.level.getPlayers(this.validPlayer)) {
/* 406 */       this.dragonEvent.addPlayer($$1);
/* 407 */       $$0.add($$1);
/*     */     } 
/* 409 */     Set<ServerPlayer> $$2 = Sets.newHashSet(this.dragonEvent.getPlayers());
/* 410 */     $$2.removeAll($$0);
/* 411 */     for (ServerPlayer $$3 : $$2) {
/* 412 */       this.dragonEvent.removePlayer($$3);
/*     */     }
/*     */   }
/*     */   
/*     */   private void updateCrystalCount() {
/* 417 */     this.ticksSinceCrystalsScanned = 0;
/* 418 */     this.crystalsAlive = 0;
/*     */     
/* 420 */     for (SpikeFeature.EndSpike $$0 : SpikeFeature.getSpikesForLevel((WorldGenLevel)this.level)) {
/* 421 */       this.crystalsAlive += this.level.getEntitiesOfClass(EndCrystal.class, $$0.getTopBoundingBox()).size();
/*     */     }
/*     */     
/* 424 */     LOGGER.debug("Found {} end crystals still alive", Integer.valueOf(this.crystalsAlive));
/*     */   }
/*     */   
/*     */   public void setDragonKilled(EnderDragon $$0) {
/* 428 */     if ($$0.getUUID().equals(this.dragonUUID)) {
/* 429 */       this.dragonEvent.setProgress(0.0F);
/* 430 */       this.dragonEvent.setVisible(false);
/* 431 */       spawnExitPortal(true);
/* 432 */       spawnNewGateway();
/*     */       
/* 434 */       if (!this.previouslyKilled) {
/* 435 */         this.level.setBlockAndUpdate(this.level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, EndPodiumFeature.getLocation(this.origin)), Blocks.DRAGON_EGG.defaultBlockState());
/*     */       }
/*     */       
/* 438 */       this.previouslyKilled = true;
/* 439 */       this.dragonKilled = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   @VisibleForTesting
/*     */   public void removeAllGateways() {
/* 449 */     this.gateways.clear();
/*     */   }
/*     */   
/*     */   private void spawnNewGateway() {
/* 453 */     if (this.gateways.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 457 */     int $$0 = ((Integer)this.gateways.remove(this.gateways.size() - 1)).intValue();
/* 458 */     int $$1 = Mth.floor(96.0D * Math.cos(2.0D * (-3.141592653589793D + 0.15707963267948966D * $$0)));
/* 459 */     int $$2 = Mth.floor(96.0D * Math.sin(2.0D * (-3.141592653589793D + 0.15707963267948966D * $$0)));
/* 460 */     spawnNewGateway(new BlockPos($$1, 75, $$2));
/*     */   }
/*     */   
/*     */   private void spawnNewGateway(BlockPos $$0) {
/* 464 */     this.level.levelEvent(3000, $$0, 0);
/* 465 */     this.level.registryAccess().registry(Registries.CONFIGURED_FEATURE)
/* 466 */       .flatMap($$0 -> $$0.getHolder(EndFeatures.END_GATEWAY_DELAYED))
/* 467 */       .ifPresent($$1 -> ((ConfiguredFeature)$$1.value()).place((WorldGenLevel)this.level, this.level.getChunkSource().getGenerator(), RandomSource.create(), $$0));
/*     */   }
/*     */ 
/*     */   
/*     */   private void spawnExitPortal(boolean $$0) {
/* 472 */     EndPodiumFeature $$1 = new EndPodiumFeature($$0);
/*     */     
/* 474 */     if (this.portalLocation == null) {
/* 475 */       this.portalLocation = this.level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EndPodiumFeature.getLocation(this.origin)).below();
/* 476 */       while (this.level.getBlockState(this.portalLocation).is(Blocks.BEDROCK) && this.portalLocation.getY() > this.level.getSeaLevel()) {
/* 477 */         this.portalLocation = this.portalLocation.below();
/*     */       }
/*     */     } 
/*     */     
/* 481 */     if ($$1.place((FeatureConfiguration)FeatureConfiguration.NONE, (WorldGenLevel)this.level, this.level.getChunkSource().getGenerator(), RandomSource.create(), this.portalLocation)) {
/*     */ 
/*     */       
/* 484 */       int $$2 = Mth.positiveCeilDiv(4, 16);
/* 485 */       (this.level.getChunkSource()).chunkMap.waitForLightBeforeSending(new ChunkPos(this.portalLocation), $$2);
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private EnderDragon createNewDragon() {
/* 491 */     this.level.getChunkAt(new BlockPos(this.origin.getX(), 128 + this.origin.getY(), this.origin.getZ()));
/* 492 */     EnderDragon $$0 = (EnderDragon)EntityType.ENDER_DRAGON.create((Level)this.level);
/* 493 */     if ($$0 != null) {
/* 494 */       $$0.setDragonFight(this);
/* 495 */       $$0.setFightOrigin(this.origin);
/* 496 */       $$0.getPhaseManager().setPhase(EnderDragonPhase.HOLDING_PATTERN);
/* 497 */       $$0.moveTo(this.origin.getX(), (128 + this.origin.getY()), this.origin.getZ(), this.level.random.nextFloat() * 360.0F, 0.0F);
/* 498 */       this.level.addFreshEntity((Entity)$$0);
/* 499 */       this.dragonUUID = $$0.getUUID();
/*     */     } 
/* 501 */     return $$0;
/*     */   }
/*     */   
/*     */   public void updateDragon(EnderDragon $$0) {
/* 505 */     if ($$0.getUUID().equals(this.dragonUUID)) {
/* 506 */       this.dragonEvent.setProgress($$0.getHealth() / $$0.getMaxHealth());
/* 507 */       this.ticksSinceDragonSeen = 0;
/* 508 */       if ($$0.hasCustomName()) {
/* 509 */         this.dragonEvent.setName($$0.getDisplayName());
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getCrystalsAlive() {
/* 515 */     return this.crystalsAlive;
/*     */   }
/*     */   
/*     */   public void onCrystalDestroyed(EndCrystal $$0, DamageSource $$1) {
/* 519 */     if (this.respawnStage != null && this.respawnCrystals.contains($$0)) {
/* 520 */       LOGGER.debug("Aborting respawn sequence");
/* 521 */       this.respawnStage = null;
/* 522 */       this.respawnTime = 0;
/* 523 */       resetSpikeCrystals();
/* 524 */       spawnExitPortal(true);
/*     */     } else {
/* 526 */       updateCrystalCount();
/* 527 */       Entity $$2 = this.level.getEntity(this.dragonUUID);
/* 528 */       if ($$2 instanceof EnderDragon) {
/* 529 */         ((EnderDragon)$$2).onCrystalDestroyed($$0, $$0.blockPosition(), $$1);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean hasPreviouslyKilledDragon() {
/* 535 */     return this.previouslyKilled;
/*     */   }
/*     */   
/*     */   public void tryRespawn() {
/* 539 */     if (this.dragonKilled && this.respawnStage == null) {
/* 540 */       BlockPos $$0 = this.portalLocation;
/* 541 */       if ($$0 == null) {
/* 542 */         LOGGER.debug("Tried to respawn, but need to find the portal first.");
/* 543 */         BlockPattern.BlockPatternMatch $$1 = findExitPortal();
/* 544 */         if ($$1 == null) {
/* 545 */           LOGGER.debug("Couldn't find a portal, so we made one.");
/* 546 */           spawnExitPortal(true);
/*     */         } else {
/* 548 */           LOGGER.debug("Found the exit portal & saved its location for next time.");
/*     */         } 
/* 550 */         $$0 = this.portalLocation;
/*     */       } 
/*     */       
/* 553 */       List<EndCrystal> $$2 = Lists.newArrayList();
/* 554 */       BlockPos $$3 = $$0.above(1);
/* 555 */       for (Direction $$4 : Direction.Plane.HORIZONTAL) {
/* 556 */         List<EndCrystal> $$5 = this.level.getEntitiesOfClass(EndCrystal.class, new AABB($$3.relative($$4, 2)));
/* 557 */         if ($$5.isEmpty()) {
/*     */           return;
/*     */         }
/* 560 */         $$2.addAll($$5);
/*     */       } 
/*     */       
/* 563 */       LOGGER.debug("Found all crystals, respawning dragon.");
/* 564 */       respawnDragon($$2);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void respawnDragon(List<EndCrystal> $$0) {
/* 569 */     if (this.dragonKilled && this.respawnStage == null) {
/* 570 */       BlockPattern.BlockPatternMatch $$1 = findExitPortal();
/* 571 */       while ($$1 != null) {
/* 572 */         for (int $$2 = 0; $$2 < this.exitPortalPattern.getWidth(); $$2++) {
/* 573 */           for (int $$3 = 0; $$3 < this.exitPortalPattern.getHeight(); $$3++) {
/* 574 */             for (int $$4 = 0; $$4 < this.exitPortalPattern.getDepth(); $$4++) {
/* 575 */               BlockInWorld $$5 = $$1.getBlock($$2, $$3, $$4);
/* 576 */               if ($$5.getState().is(Blocks.BEDROCK) || $$5.getState().is(Blocks.END_PORTAL)) {
/* 577 */                 this.level.setBlockAndUpdate($$5.getPos(), Blocks.END_STONE.defaultBlockState());
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/* 582 */         $$1 = findExitPortal();
/*     */       } 
/*     */       
/* 585 */       this.respawnStage = DragonRespawnAnimation.START;
/* 586 */       this.respawnTime = 0;
/* 587 */       spawnExitPortal(false);
/* 588 */       this.respawnCrystals = $$0;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void resetSpikeCrystals() {
/* 593 */     for (SpikeFeature.EndSpike $$0 : SpikeFeature.getSpikesForLevel((WorldGenLevel)this.level)) {
/* 594 */       List<EndCrystal> $$1 = this.level.getEntitiesOfClass(EndCrystal.class, $$0.getTopBoundingBox());
/* 595 */       for (EndCrystal $$2 : $$1) {
/* 596 */         $$2.setInvulnerable(false);
/* 597 */         $$2.setBeamTarget(null);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public UUID getDragonUUID() {
/* 604 */     return this.dragonUUID;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\dimension\end\EndDragonFight.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */