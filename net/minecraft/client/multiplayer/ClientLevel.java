/*      */ package net.minecraft.client.multiplayer;
/*      */ import com.google.common.collect.ImmutableMap;
/*      */ import com.google.common.collect.Maps;
/*      */ import com.mojang.logging.LogUtils;
/*      */ import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
/*      */ import java.util.Deque;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.function.BooleanSupplier;
/*      */ import java.util.function.Supplier;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.CrashReport;
/*      */ import net.minecraft.CrashReportCategory;
/*      */ import net.minecraft.ReportedException;
/*      */ import net.minecraft.Util;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.color.block.BlockTintCache;
/*      */ import net.minecraft.client.multiplayer.prediction.BlockStatePredictionHandler;
/*      */ import net.minecraft.client.particle.FireworkParticles;
/*      */ import net.minecraft.client.particle.Particle;
/*      */ import net.minecraft.client.player.AbstractClientPlayer;
/*      */ import net.minecraft.client.player.LocalPlayer;
/*      */ import net.minecraft.client.renderer.BiomeColors;
/*      */ import net.minecraft.client.renderer.DimensionSpecialEffects;
/*      */ import net.minecraft.client.renderer.LevelRenderer;
/*      */ import net.minecraft.client.resources.sounds.EntityBoundSoundInstance;
/*      */ import net.minecraft.client.resources.sounds.SimpleSoundInstance;
/*      */ import net.minecraft.client.resources.sounds.SoundInstance;
/*      */ import net.minecraft.core.BlockPos;
/*      */ import net.minecraft.core.Cursor3D;
/*      */ import net.minecraft.core.Direction;
/*      */ import net.minecraft.core.Holder;
/*      */ import net.minecraft.core.RegistryAccess;
/*      */ import net.minecraft.core.particles.BlockParticleOption;
/*      */ import net.minecraft.core.particles.ParticleOptions;
/*      */ import net.minecraft.core.particles.ParticleTypes;
/*      */ import net.minecraft.core.registries.BuiltInRegistries;
/*      */ import net.minecraft.core.registries.Registries;
/*      */ import net.minecraft.nbt.CompoundTag;
/*      */ import net.minecraft.network.chat.Component;
/*      */ import net.minecraft.network.protocol.Packet;
/*      */ import net.minecraft.resources.ResourceKey;
/*      */ import net.minecraft.sounds.SoundEvent;
/*      */ import net.minecraft.sounds.SoundSource;
/*      */ import net.minecraft.tags.BlockTags;
/*      */ import net.minecraft.util.Mth;
/*      */ import net.minecraft.util.RandomSource;
/*      */ import net.minecraft.util.profiling.ProfilerFiller;
/*      */ import net.minecraft.world.Difficulty;
/*      */ import net.minecraft.world.TickRateManager;
/*      */ import net.minecraft.world.entity.Entity;
/*      */ import net.minecraft.world.entity.player.Player;
/*      */ import net.minecraft.world.flag.FeatureFlagSet;
/*      */ import net.minecraft.world.item.BlockItem;
/*      */ import net.minecraft.world.item.Item;
/*      */ import net.minecraft.world.item.ItemStack;
/*      */ import net.minecraft.world.item.Items;
/*      */ import net.minecraft.world.item.crafting.RecipeManager;
/*      */ import net.minecraft.world.level.BlockGetter;
/*      */ import net.minecraft.world.level.ChunkPos;
/*      */ import net.minecraft.world.level.ColorResolver;
/*      */ import net.minecraft.world.level.GameRules;
/*      */ import net.minecraft.world.level.GameType;
/*      */ import net.minecraft.world.level.Level;
/*      */ import net.minecraft.world.level.LevelHeightAccessor;
/*      */ import net.minecraft.world.level.biome.AmbientParticleSettings;
/*      */ import net.minecraft.world.level.biome.Biome;
/*      */ import net.minecraft.world.level.biome.BiomeManager;
/*      */ import net.minecraft.world.level.biome.Biomes;
/*      */ import net.minecraft.world.level.block.Block;
/*      */ import net.minecraft.world.level.block.state.BlockState;
/*      */ import net.minecraft.world.level.chunk.ChunkSource;
/*      */ import net.minecraft.world.level.chunk.LevelChunk;
/*      */ import net.minecraft.world.level.dimension.DimensionType;
/*      */ import net.minecraft.world.level.entity.EntityTickList;
/*      */ import net.minecraft.world.level.entity.LevelCallback;
/*      */ import net.minecraft.world.level.entity.LevelEntityGetter;
/*      */ import net.minecraft.world.level.entity.TransientEntitySectionManager;
/*      */ import net.minecraft.world.level.gameevent.GameEvent;
/*      */ import net.minecraft.world.level.material.Fluid;
/*      */ import net.minecraft.world.level.material.FluidState;
/*      */ import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
/*      */ import net.minecraft.world.level.storage.LevelData;
/*      */ import net.minecraft.world.level.storage.WritableLevelData;
/*      */ import net.minecraft.world.phys.Vec3;
/*      */ import net.minecraft.world.phys.shapes.VoxelShape;
/*      */ import net.minecraft.world.scores.Scoreboard;
/*      */ import net.minecraft.world.ticks.BlackholeTickAccess;
/*      */ import net.minecraft.world.ticks.LevelTickAccess;
/*      */ import org.slf4j.Logger;
/*      */ 
/*      */ public class ClientLevel extends Level {
/*   94 */   private static final Logger LOGGER = LogUtils.getLogger();
/*      */   
/*      */   private static final double FLUID_PARTICLE_SPAWN_OFFSET = 0.05D;
/*      */   private static final int NORMAL_LIGHT_UPDATES_PER_FRAME = 10;
/*      */   private static final int LIGHT_UPDATE_QUEUE_SIZE_THRESHOLD = 1000;
/*   99 */   final EntityTickList tickingEntities = new EntityTickList();
/*  100 */   private final TransientEntitySectionManager<Entity> entityStorage = new TransientEntitySectionManager(Entity.class, new EntityCallbacks());
/*      */   
/*      */   private final ClientPacketListener connection;
/*      */   
/*      */   private final LevelRenderer levelRenderer;
/*      */   private final ClientLevelData clientLevelData;
/*      */   private final DimensionSpecialEffects effects;
/*      */   private final TickRateManager tickRateManager;
/*  108 */   private final Minecraft minecraft = Minecraft.getInstance();
/*  109 */   final List<AbstractClientPlayer> players = Lists.newArrayList();
/*  110 */   private Scoreboard scoreboard = new Scoreboard();
/*  111 */   private final Map<String, MapItemSavedData> mapData = Maps.newHashMap();
/*      */   
/*      */   private static final long CLOUD_COLOR = 16777215L;
/*      */   
/*      */   private int skyFlashTime;
/*      */   
/*      */   private final Object2ObjectArrayMap<ColorResolver, BlockTintCache> tintCaches;
/*      */   
/*      */   private final ClientChunkCache chunkSource;
/*      */   
/*      */   private final Deque<Runnable> lightUpdateQueue;
/*      */   
/*      */   private int serverSimulationDistance;
/*      */   
/*      */   private final BlockStatePredictionHandler blockStatePredictionHandler;
/*      */ 
/*      */   
/*      */   public void handleBlockChangedAck(int $$0) {
/*  129 */     this.blockStatePredictionHandler.endPredictionsUpTo($$0, this);
/*      */   }
/*      */   
/*      */   public void setServerVerifiedBlockState(BlockPos $$0, BlockState $$1, int $$2) {
/*  133 */     if (!this.blockStatePredictionHandler.updateKnownServerState($$0, $$1)) {
/*  134 */       super.setBlock($$0, $$1, $$2, 512);
/*      */     }
/*      */   }
/*      */   
/*      */   public void syncBlockState(BlockPos $$0, BlockState $$1, Vec3 $$2) {
/*  139 */     BlockState $$3 = getBlockState($$0);
/*  140 */     if ($$3 != $$1) {
/*  141 */       setBlock($$0, $$1, 19);
/*      */ 
/*      */       
/*  144 */       LocalPlayer localPlayer = this.minecraft.player;
/*  145 */       if (this == localPlayer.level() && localPlayer.isColliding($$0, $$1)) {
/*  146 */         localPlayer.absMoveTo($$2.x, $$2.y, $$2.z);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   BlockStatePredictionHandler getBlockStatePredictionHandler() {
/*  152 */     return this.blockStatePredictionHandler;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean setBlock(BlockPos $$0, BlockState $$1, int $$2, int $$3) {
/*  157 */     if (this.blockStatePredictionHandler.isPredicting()) {
/*  158 */       BlockState $$4 = getBlockState($$0);
/*  159 */       boolean $$5 = super.setBlock($$0, $$1, $$2, $$3);
/*  160 */       if ($$5) {
/*  161 */         this.blockStatePredictionHandler.retainKnownServerState($$0, $$4, this.minecraft.player);
/*      */       }
/*  163 */       return $$5;
/*      */     } 
/*  165 */     return super.setBlock($$0, $$1, $$2, $$3);
/*      */   }
/*      */   
/*  168 */   private static final Set<Item> MARKER_PARTICLE_ITEMS = Set.of(Items.BARRIER, Items.LIGHT);
/*      */   
/*      */   public ClientLevel(ClientPacketListener $$0, ClientLevelData $$1, ResourceKey<Level> $$2, Holder<DimensionType> $$3, int $$4, int $$5, Supplier<ProfilerFiller> $$6, LevelRenderer $$7, boolean $$8, long $$9) {
/*  171 */     super($$1, $$2, (RegistryAccess)$$0.registryAccess(), $$3, $$6, true, $$8, $$9, 1000000); this.tintCaches = (Object2ObjectArrayMap<ColorResolver, BlockTintCache>)Util.make(new Object2ObjectArrayMap(3), $$0 -> { $$0.put(BiomeColors.GRASS_COLOR_RESOLVER, new BlockTintCache(())); $$0.put(BiomeColors.FOLIAGE_COLOR_RESOLVER, new BlockTintCache(())); $$0.put(BiomeColors.WATER_COLOR_RESOLVER, new BlockTintCache(()));
/*  172 */         }); this.lightUpdateQueue = Queues.newArrayDeque(); this.blockStatePredictionHandler = new BlockStatePredictionHandler(); this.connection = $$0;
/*  173 */     this.chunkSource = new ClientChunkCache(this, $$4);
/*  174 */     this.tickRateManager = new TickRateManager();
/*  175 */     this.clientLevelData = $$1;
/*  176 */     this.levelRenderer = $$7;
/*  177 */     this.effects = DimensionSpecialEffects.forType((DimensionType)$$3.value());
/*  178 */     setDefaultSpawnPos(new BlockPos(8, 64, 8), 0.0F);
/*      */     
/*  180 */     this.serverSimulationDistance = $$5;
/*  181 */     updateSkyBrightness();
/*  182 */     prepareWeather();
/*      */   }
/*      */   
/*      */   public void queueLightUpdate(Runnable $$0) {
/*  186 */     this.lightUpdateQueue.add($$0);
/*      */   }
/*      */   
/*      */   public void pollLightUpdates() {
/*  190 */     int $$0 = this.lightUpdateQueue.size();
/*  191 */     int $$1 = ($$0 < 1000) ? Math.max(10, $$0 / 10) : $$0;
/*  192 */     for (int $$2 = 0; $$2 < $$1; ) {
/*  193 */       Runnable $$3 = this.lightUpdateQueue.poll();
/*  194 */       if ($$3 != null) {
/*  195 */         $$3.run();
/*      */         $$2++;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isLightUpdateQueueEmpty() {
/*  203 */     return this.lightUpdateQueue.isEmpty();
/*      */   }
/*      */   
/*      */   public DimensionSpecialEffects effects() {
/*  207 */     return this.effects;
/*      */   }
/*      */   
/*      */   public void tick(BooleanSupplier $$0) {
/*  211 */     getWorldBorder().tick();
/*  212 */     if (tickRateManager().runsNormally()) {
/*  213 */       tickTime();
/*      */     }
/*      */     
/*  216 */     if (this.skyFlashTime > 0) {
/*  217 */       setSkyFlashTime(this.skyFlashTime - 1);
/*      */     }
/*      */     
/*  220 */     getProfiler().push("blocks");
/*      */     
/*  222 */     this.chunkSource.tick($$0, true);
/*      */     
/*  224 */     getProfiler().pop();
/*      */   }
/*      */   
/*      */   private void tickTime() {
/*  228 */     setGameTime(this.levelData.getGameTime() + 1L);
/*  229 */     if (this.levelData.getGameRules().getBoolean(GameRules.RULE_DAYLIGHT)) {
/*  230 */       setDayTime(this.levelData.getDayTime() + 1L);
/*      */     }
/*      */   }
/*      */   
/*      */   public void setGameTime(long $$0) {
/*  235 */     this.clientLevelData.setGameTime($$0);
/*      */   }
/*      */   
/*      */   public void setDayTime(long $$0) {
/*  239 */     if ($$0 < 0L) {
/*  240 */       $$0 = -$$0;
/*  241 */       ((GameRules.BooleanValue)getGameRules().getRule(GameRules.RULE_DAYLIGHT)).set(false, null);
/*      */     } else {
/*  243 */       ((GameRules.BooleanValue)getGameRules().getRule(GameRules.RULE_DAYLIGHT)).set(true, null);
/*      */     } 
/*  245 */     this.clientLevelData.setDayTime($$0);
/*      */   }
/*      */   
/*      */   public Iterable<Entity> entitiesForRendering() {
/*  249 */     return getEntities().getAll();
/*      */   }
/*      */   
/*      */   public void tickEntities() {
/*  253 */     ProfilerFiller $$0 = getProfiler();
/*  254 */     $$0.push("entities");
/*      */     
/*  256 */     this.tickingEntities.forEach($$0 -> {
/*      */           if ($$0.isRemoved() || $$0.isPassenger() || this.tickRateManager.isEntityFrozen($$0)) {
/*      */             return;
/*      */           }
/*      */           guardEntityTick(this::tickNonPassenger, $$0);
/*      */         });
/*  262 */     $$0.pop();
/*      */     
/*  264 */     tickBlockEntities();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean shouldTickDeath(Entity $$0) {
/*  269 */     return ($$0.chunkPosition().getChessboardDistance(this.minecraft.player.chunkPosition()) <= this.serverSimulationDistance);
/*      */   }
/*      */   
/*      */   public void tickNonPassenger(Entity $$0) {
/*  273 */     $$0.setOldPosAndRot();
/*      */     
/*  275 */     $$0.tickCount++;
/*  276 */     getProfiler().push(() -> BuiltInRegistries.ENTITY_TYPE.getKey($$0.getType()).toString());
/*  277 */     $$0.tick();
/*  278 */     getProfiler().pop();
/*      */     
/*  280 */     for (Entity $$1 : $$0.getPassengers()) {
/*  281 */       tickPassenger($$0, $$1);
/*      */     }
/*      */   }
/*      */   
/*      */   private void tickPassenger(Entity $$0, Entity $$1) {
/*  286 */     if ($$1.isRemoved() || $$1.getVehicle() != $$0) {
/*  287 */       $$1.stopRiding();
/*      */       
/*      */       return;
/*      */     } 
/*  291 */     if (!($$1 instanceof Player) && !this.tickingEntities.contains($$1)) {
/*      */       return;
/*      */     }
/*      */     
/*  295 */     $$1.setOldPosAndRot();
/*      */     
/*  297 */     $$1.tickCount++;
/*  298 */     $$1.rideTick();
/*      */     
/*  300 */     for (Entity $$2 : $$1.getPassengers()) {
/*  301 */       tickPassenger($$1, $$2);
/*      */     }
/*      */   }
/*      */   
/*      */   public void unload(LevelChunk $$0) {
/*  306 */     $$0.clearAllBlockEntities();
/*  307 */     this.chunkSource.getLightEngine().setLightEnabled($$0.getPos(), false);
/*  308 */     this.entityStorage.stopTicking($$0.getPos());
/*      */   }
/*      */   
/*      */   public void onChunkLoaded(ChunkPos $$0) {
/*  312 */     this.tintCaches.forEach(($$1, $$2) -> $$2.invalidateForChunk($$0.x, $$0.z));
/*  313 */     this.entityStorage.startTicking($$0);
/*  314 */     this.levelRenderer.onChunkLoaded($$0);
/*      */   }
/*      */   
/*      */   public void clearTintCaches() {
/*  318 */     this.tintCaches.forEach(($$0, $$1) -> $$1.invalidateAll());
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasChunk(int $$0, int $$1) {
/*  323 */     return true;
/*      */   }
/*      */   
/*      */   public int getEntityCount() {
/*  327 */     return this.entityStorage.count();
/*      */   }
/*      */   
/*      */   public void addEntity(Entity $$0) {
/*  331 */     removeEntity($$0.getId(), Entity.RemovalReason.DISCARDED);
/*  332 */     this.entityStorage.addEntity((EntityAccess)$$0);
/*      */   }
/*      */   
/*      */   public void removeEntity(int $$0, Entity.RemovalReason $$1) {
/*  336 */     Entity $$2 = (Entity)getEntities().get($$0);
/*  337 */     if ($$2 != null) {
/*  338 */       $$2.setRemoved($$1);
/*  339 */       $$2.onClientRemoval();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public Entity getEntity(int $$0) {
/*  346 */     return (Entity)getEntities().get($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public void disconnect() {
/*  351 */     this.connection.getConnection().disconnect((Component)Component.translatable("multiplayer.status.quitting"));
/*      */   }
/*      */   
/*      */   public void animateTick(int $$0, int $$1, int $$2) {
/*  355 */     int $$3 = 32;
/*  356 */     RandomSource $$4 = RandomSource.create();
/*      */     
/*  358 */     Block $$5 = getMarkerParticleTarget();
/*  359 */     BlockPos.MutableBlockPos $$6 = new BlockPos.MutableBlockPos();
/*  360 */     for (int $$7 = 0; $$7 < 667; $$7++) {
/*  361 */       doAnimateTick($$0, $$1, $$2, 16, $$4, $$5, $$6);
/*  362 */       doAnimateTick($$0, $$1, $$2, 32, $$4, $$5, $$6);
/*      */     } 
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   private Block getMarkerParticleTarget() {
/*  368 */     if (this.minecraft.gameMode.getPlayerMode() == GameType.CREATIVE) {
/*  369 */       ItemStack $$0 = this.minecraft.player.getMainHandItem();
/*  370 */       Item $$1 = $$0.getItem();
/*  371 */       if (MARKER_PARTICLE_ITEMS.contains($$1) && $$1 instanceof BlockItem) { BlockItem $$2 = (BlockItem)$$1;
/*  372 */         return $$2.getBlock(); }
/*      */     
/*      */     } 
/*      */     
/*  376 */     return null;
/*      */   }
/*      */   
/*      */   public void doAnimateTick(int $$0, int $$1, int $$2, int $$3, RandomSource $$4, @Nullable Block $$5, BlockPos.MutableBlockPos $$6) {
/*  380 */     int $$7 = $$0 + this.random.nextInt($$3) - this.random.nextInt($$3);
/*  381 */     int $$8 = $$1 + this.random.nextInt($$3) - this.random.nextInt($$3);
/*  382 */     int $$9 = $$2 + this.random.nextInt($$3) - this.random.nextInt($$3);
/*      */     
/*  384 */     $$6.set($$7, $$8, $$9);
/*  385 */     BlockState $$10 = getBlockState((BlockPos)$$6);
/*  386 */     $$10.getBlock().animateTick($$10, this, (BlockPos)$$6, $$4);
/*      */     
/*  388 */     FluidState $$11 = getFluidState((BlockPos)$$6);
/*      */     
/*  390 */     if (!$$11.isEmpty()) {
/*  391 */       $$11.animateTick(this, (BlockPos)$$6, $$4);
/*      */       
/*  393 */       ParticleOptions $$12 = $$11.getDripParticle();
/*  394 */       if ($$12 != null && this.random.nextInt(10) == 0) {
/*  395 */         boolean $$13 = $$10.isFaceSturdy((BlockGetter)this, (BlockPos)$$6, Direction.DOWN);
/*  396 */         BlockPos $$14 = $$6.below();
/*  397 */         trySpawnDripParticles($$14, getBlockState($$14), $$12, $$13);
/*      */       } 
/*      */     } 
/*      */     
/*  401 */     if ($$5 == $$10.getBlock()) {
/*  402 */       addParticle((ParticleOptions)new BlockParticleOption(ParticleTypes.BLOCK_MARKER, $$10), $$7 + 0.5D, $$8 + 0.5D, $$9 + 0.5D, 0.0D, 0.0D, 0.0D);
/*      */     }
/*      */     
/*  405 */     if (!$$10.isCollisionShapeFullBlock((BlockGetter)this, (BlockPos)$$6)) {
/*  406 */       ((Biome)getBiome((BlockPos)$$6).value()).getAmbientParticle().ifPresent($$1 -> {
/*      */             if ($$1.canSpawn(this.random)) {
/*      */               addParticle($$1.getOptions(), $$0.getX() + this.random.nextDouble(), $$0.getY() + this.random.nextDouble(), $$0.getZ() + this.random.nextDouble(), 0.0D, 0.0D, 0.0D);
/*      */             }
/*      */           });
/*      */     }
/*      */   }
/*      */   
/*      */   private void trySpawnDripParticles(BlockPos $$0, BlockState $$1, ParticleOptions $$2, boolean $$3) {
/*  415 */     if (!$$1.getFluidState().isEmpty()) {
/*      */       return;
/*      */     }
/*      */     
/*  419 */     VoxelShape $$4 = $$1.getCollisionShape((BlockGetter)this, $$0);
/*  420 */     double $$5 = $$4.max(Direction.Axis.Y);
/*  421 */     if ($$5 < 1.0D) {
/*  422 */       if ($$3) {
/*  423 */         spawnFluidParticle($$0.getX(), ($$0.getX() + 1), $$0.getZ(), ($$0.getZ() + 1), ($$0.getY() + 1) - 0.05D, $$2);
/*      */       }
/*  425 */     } else if (!$$1.is(BlockTags.IMPERMEABLE)) {
/*  426 */       double $$6 = $$4.min(Direction.Axis.Y);
/*  427 */       if ($$6 > 0.0D) {
/*  428 */         spawnParticle($$0, $$2, $$4, $$0.getY() + $$6 - 0.05D);
/*      */       } else {
/*  430 */         BlockPos $$7 = $$0.below();
/*  431 */         BlockState $$8 = getBlockState($$7);
/*  432 */         VoxelShape $$9 = $$8.getCollisionShape((BlockGetter)this, $$7);
/*  433 */         double $$10 = $$9.max(Direction.Axis.Y);
/*  434 */         if ($$10 < 1.0D && $$8.getFluidState().isEmpty()) {
/*  435 */           spawnParticle($$0, $$2, $$4, $$0.getY() - 0.05D);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void spawnParticle(BlockPos $$0, ParticleOptions $$1, VoxelShape $$2, double $$3) {
/*  442 */     spawnFluidParticle($$0.getX() + $$2.min(Direction.Axis.X), $$0
/*  443 */         .getX() + $$2.max(Direction.Axis.X), $$0
/*  444 */         .getZ() + $$2.min(Direction.Axis.Z), $$0
/*  445 */         .getZ() + $$2.max(Direction.Axis.Z), $$3, $$1);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void spawnFluidParticle(double $$0, double $$1, double $$2, double $$3, double $$4, ParticleOptions $$5) {
/*  451 */     addParticle($$5, Mth.lerp(this.random.nextDouble(), $$0, $$1), $$4, Mth.lerp(this.random.nextDouble(), $$2, $$3), 0.0D, 0.0D, 0.0D);
/*      */   }
/*      */ 
/*      */   
/*      */   public CrashReportCategory fillReportDetails(CrashReport $$0) {
/*  456 */     CrashReportCategory $$1 = super.fillReportDetails($$0);
/*      */     
/*  458 */     $$1.setDetail("Server brand", () -> this.minecraft.player.connection.serverBrand());
/*  459 */     $$1.setDetail("Server type", () -> (this.minecraft.getSingleplayerServer() == null) ? "Non-integrated multiplayer server" : "Integrated singleplayer server");
/*  460 */     $$1.setDetail("Tracked entity count", () -> String.valueOf(getEntityCount()));
/*      */     
/*  462 */     return $$1;
/*      */   }
/*      */ 
/*      */   
/*      */   public void playSeededSound(@Nullable Player $$0, double $$1, double $$2, double $$3, Holder<SoundEvent> $$4, SoundSource $$5, float $$6, float $$7, long $$8) {
/*  467 */     if ($$0 == this.minecraft.player) {
/*  468 */       playSound($$1, $$2, $$3, (SoundEvent)$$4.value(), $$5, $$6, $$7, false, $$8);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void playSeededSound(@Nullable Player $$0, Entity $$1, Holder<SoundEvent> $$2, SoundSource $$3, float $$4, float $$5, long $$6) {
/*  474 */     if ($$0 == this.minecraft.player) {
/*  475 */       this.minecraft.getSoundManager().play((SoundInstance)new EntityBoundSoundInstance((SoundEvent)$$2.value(), $$3, $$4, $$5, $$1, $$6));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void playLocalSound(Entity $$0, SoundEvent $$1, SoundSource $$2, float $$3, float $$4) {
/*  481 */     this.minecraft.getSoundManager().play((SoundInstance)new EntityBoundSoundInstance($$1, $$2, $$3, $$4, $$0, this.random.nextLong()));
/*      */   }
/*      */ 
/*      */   
/*      */   public void playLocalSound(double $$0, double $$1, double $$2, SoundEvent $$3, SoundSource $$4, float $$5, float $$6, boolean $$7) {
/*  486 */     playSound($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, this.random.nextLong());
/*      */   }
/*      */   
/*      */   private void playSound(double $$0, double $$1, double $$2, SoundEvent $$3, SoundSource $$4, float $$5, float $$6, boolean $$7, long $$8) {
/*  490 */     double $$9 = this.minecraft.gameRenderer.getMainCamera().getPosition().distanceToSqr($$0, $$1, $$2);
/*  491 */     SimpleSoundInstance $$10 = new SimpleSoundInstance($$3, $$4, $$5, $$6, RandomSource.create($$8), $$0, $$1, $$2);
/*      */     
/*  493 */     if ($$7 && $$9 > 100.0D) {
/*      */       
/*  495 */       double $$11 = Math.sqrt($$9) / 40.0D;
/*  496 */       this.minecraft.getSoundManager().playDelayed((SoundInstance)$$10, (int)($$11 * 20.0D));
/*      */     } else {
/*  498 */       this.minecraft.getSoundManager().play((SoundInstance)$$10);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void createFireworks(double $$0, double $$1, double $$2, double $$3, double $$4, double $$5, @Nullable CompoundTag $$6) {
/*  504 */     this.minecraft.particleEngine.add((Particle)new FireworkParticles.Starter(this, $$0, $$1, $$2, $$3, $$4, $$5, this.minecraft.particleEngine, $$6));
/*      */   }
/*      */ 
/*      */   
/*      */   public void sendPacketToServer(Packet<?> $$0) {
/*  509 */     this.connection.send($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public RecipeManager getRecipeManager() {
/*  514 */     return this.connection.getRecipeManager();
/*      */   }
/*      */ 
/*      */   
/*      */   public TickRateManager tickRateManager() {
/*  519 */     return this.tickRateManager;
/*      */   }
/*      */   
/*      */   public void setScoreboard(Scoreboard $$0) {
/*  523 */     this.scoreboard = $$0;
/*      */   }
/*      */ 
/*      */   
/*      */   public LevelTickAccess<Block> getBlockTicks() {
/*  528 */     return BlackholeTickAccess.emptyLevelList();
/*      */   }
/*      */ 
/*      */   
/*      */   public LevelTickAccess<Fluid> getFluidTicks() {
/*  533 */     return BlackholeTickAccess.emptyLevelList();
/*      */   }
/*      */ 
/*      */   
/*      */   public ClientChunkCache getChunkSource() {
/*  538 */     return this.chunkSource;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public MapItemSavedData getMapData(String $$0) {
/*  544 */     return this.mapData.get($$0);
/*      */   }
/*      */   
/*      */   public void overrideMapData(String $$0, MapItemSavedData $$1) {
/*  548 */     this.mapData.put($$0, $$1);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMapData(String $$0, MapItemSavedData $$1) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public int getFreeMapId() {
/*  558 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public Scoreboard getScoreboard() {
/*  563 */     return this.scoreboard;
/*      */   }
/*      */ 
/*      */   
/*      */   public void sendBlockUpdated(BlockPos $$0, BlockState $$1, BlockState $$2, int $$3) {
/*  568 */     this.levelRenderer.blockChanged((BlockGetter)this, $$0, $$1, $$2, $$3);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setBlocksDirty(BlockPos $$0, BlockState $$1, BlockState $$2) {
/*  573 */     this.levelRenderer.setBlockDirty($$0, $$1, $$2);
/*      */   }
/*      */   
/*      */   public void setSectionDirtyWithNeighbors(int $$0, int $$1, int $$2) {
/*  577 */     this.levelRenderer.setSectionDirtyWithNeighbors($$0, $$1, $$2);
/*      */   }
/*      */ 
/*      */   
/*      */   public void destroyBlockProgress(int $$0, BlockPos $$1, int $$2) {
/*  582 */     this.levelRenderer.destroyBlockProgress($$0, $$1, $$2);
/*      */   }
/*      */ 
/*      */   
/*      */   public void globalLevelEvent(int $$0, BlockPos $$1, int $$2) {
/*  587 */     this.levelRenderer.globalLevelEvent($$0, $$1, $$2);
/*      */   }
/*      */ 
/*      */   
/*      */   public void levelEvent(@Nullable Player $$0, int $$1, BlockPos $$2, int $$3) {
/*      */     try {
/*  593 */       this.levelRenderer.levelEvent($$1, $$2, $$3);
/*  594 */     } catch (Throwable $$4) {
/*  595 */       CrashReport $$5 = CrashReport.forThrowable($$4, "Playing level event");
/*  596 */       CrashReportCategory $$6 = $$5.addCategory("Level event being played");
/*      */       
/*  598 */       $$6.setDetail("Block coordinates", CrashReportCategory.formatLocation((LevelHeightAccessor)this, $$2));
/*  599 */       $$6.setDetail("Event source", $$0);
/*  600 */       $$6.setDetail("Event type", Integer.valueOf($$1));
/*  601 */       $$6.setDetail("Event data", Integer.valueOf($$3));
/*      */       
/*  603 */       throw new ReportedException($$5);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void addParticle(ParticleOptions $$0, double $$1, double $$2, double $$3, double $$4, double $$5, double $$6) {
/*  609 */     this.levelRenderer.addParticle($$0, $$0.getType().getOverrideLimiter(), $$1, $$2, $$3, $$4, $$5, $$6);
/*      */   }
/*      */ 
/*      */   
/*      */   public void addParticle(ParticleOptions $$0, boolean $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/*  614 */     this.levelRenderer.addParticle($$0, ($$0.getType().getOverrideLimiter() || $$1), $$2, $$3, $$4, $$5, $$6, $$7);
/*      */   }
/*      */ 
/*      */   
/*      */   public void addAlwaysVisibleParticle(ParticleOptions $$0, double $$1, double $$2, double $$3, double $$4, double $$5, double $$6) {
/*  619 */     this.levelRenderer.addParticle($$0, false, true, $$1, $$2, $$3, $$4, $$5, $$6);
/*      */   }
/*      */ 
/*      */   
/*      */   public void addAlwaysVisibleParticle(ParticleOptions $$0, boolean $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/*  624 */     this.levelRenderer.addParticle($$0, ($$0.getType().getOverrideLimiter() || $$1), true, $$2, $$3, $$4, $$5, $$6, $$7);
/*      */   }
/*      */ 
/*      */   
/*      */   public List<AbstractClientPlayer> players() {
/*  629 */     return this.players;
/*      */   }
/*      */ 
/*      */   
/*      */   public Holder<Biome> getUncachedNoiseBiome(int $$0, int $$1, int $$2) {
/*  634 */     return (Holder<Biome>)registryAccess().registryOrThrow(Registries.BIOME).getHolderOrThrow(Biomes.PLAINS);
/*      */   }
/*      */   
/*      */   public float getSkyDarken(float $$0) {
/*  638 */     float $$1 = getTimeOfDay($$0);
/*      */     
/*  640 */     float $$2 = 1.0F - Mth.cos($$1 * 6.2831855F) * 2.0F + 0.2F;
/*  641 */     $$2 = Mth.clamp($$2, 0.0F, 1.0F);
/*      */     
/*  643 */     $$2 = 1.0F - $$2;
/*      */     
/*  645 */     $$2 *= 1.0F - getRainLevel($$0) * 5.0F / 16.0F;
/*  646 */     $$2 *= 1.0F - getThunderLevel($$0) * 5.0F / 16.0F;
/*      */     
/*  648 */     return $$2 * 0.8F + 0.2F;
/*      */   }
/*      */   
/*      */   public Vec3 getSkyColor(Vec3 $$0, float $$1) {
/*  652 */     float $$2 = getTimeOfDay($$1);
/*      */     
/*  654 */     Vec3 $$3 = $$0.subtract(2.0D, 2.0D, 2.0D).scale(0.25D);
/*  655 */     BiomeManager $$4 = getBiomeManager();
/*  656 */     Vec3 $$5 = CubicSampler.gaussianSampleVec3($$3, ($$1, $$2, $$3) -> Vec3.fromRGB24(((Biome)$$0.getNoiseBiomeAtQuart($$1, $$2, $$3).value()).getSkyColor()));
/*      */     
/*  658 */     float $$6 = Mth.cos($$2 * 6.2831855F) * 2.0F + 0.5F;
/*  659 */     $$6 = Mth.clamp($$6, 0.0F, 1.0F);
/*      */     
/*  661 */     float $$7 = (float)$$5.x * $$6;
/*  662 */     float $$8 = (float)$$5.y * $$6;
/*  663 */     float $$9 = (float)$$5.z * $$6;
/*      */     
/*  665 */     float $$10 = getRainLevel($$1);
/*  666 */     if ($$10 > 0.0F) {
/*  667 */       float $$11 = ($$7 * 0.3F + $$8 * 0.59F + $$9 * 0.11F) * 0.6F;
/*      */       
/*  669 */       float $$12 = 1.0F - $$10 * 0.75F;
/*  670 */       $$7 = $$7 * $$12 + $$11 * (1.0F - $$12);
/*  671 */       $$8 = $$8 * $$12 + $$11 * (1.0F - $$12);
/*  672 */       $$9 = $$9 * $$12 + $$11 * (1.0F - $$12);
/*      */     } 
/*  674 */     float $$13 = getThunderLevel($$1);
/*  675 */     if ($$13 > 0.0F) {
/*  676 */       float $$14 = ($$7 * 0.3F + $$8 * 0.59F + $$9 * 0.11F) * 0.2F;
/*      */       
/*  678 */       float $$15 = 1.0F - $$13 * 0.75F;
/*  679 */       $$7 = $$7 * $$15 + $$14 * (1.0F - $$15);
/*  680 */       $$8 = $$8 * $$15 + $$14 * (1.0F - $$15);
/*  681 */       $$9 = $$9 * $$15 + $$14 * (1.0F - $$15);
/*      */     } 
/*      */     
/*  684 */     int $$16 = getSkyFlashTime();
/*  685 */     if ($$16 > 0) {
/*  686 */       float $$17 = $$16 - $$1;
/*  687 */       if ($$17 > 1.0F) {
/*  688 */         $$17 = 1.0F;
/*      */       }
/*  690 */       $$17 *= 0.45F;
/*  691 */       $$7 = $$7 * (1.0F - $$17) + 0.8F * $$17;
/*  692 */       $$8 = $$8 * (1.0F - $$17) + 0.8F * $$17;
/*  693 */       $$9 = $$9 * (1.0F - $$17) + 1.0F * $$17;
/*      */     } 
/*      */     
/*  696 */     return new Vec3($$7, $$8, $$9);
/*      */   }
/*      */   
/*      */   public Vec3 getCloudColor(float $$0) {
/*  700 */     float $$1 = getTimeOfDay($$0);
/*      */     
/*  702 */     float $$2 = Mth.cos($$1 * 6.2831855F) * 2.0F + 0.5F;
/*  703 */     $$2 = Mth.clamp($$2, 0.0F, 1.0F);
/*      */     
/*  705 */     float $$3 = 1.0F;
/*  706 */     float $$4 = 1.0F;
/*  707 */     float $$5 = 1.0F;
/*      */     
/*  709 */     float $$6 = getRainLevel($$0);
/*  710 */     if ($$6 > 0.0F) {
/*  711 */       float $$7 = ($$3 * 0.3F + $$4 * 0.59F + $$5 * 0.11F) * 0.6F;
/*      */       
/*  713 */       float $$8 = 1.0F - $$6 * 0.95F;
/*  714 */       $$3 = $$3 * $$8 + $$7 * (1.0F - $$8);
/*  715 */       $$4 = $$4 * $$8 + $$7 * (1.0F - $$8);
/*  716 */       $$5 = $$5 * $$8 + $$7 * (1.0F - $$8);
/*      */     } 
/*      */     
/*  719 */     $$3 *= $$2 * 0.9F + 0.1F;
/*  720 */     $$4 *= $$2 * 0.9F + 0.1F;
/*  721 */     $$5 *= $$2 * 0.85F + 0.15F;
/*      */     
/*  723 */     float $$9 = getThunderLevel($$0);
/*  724 */     if ($$9 > 0.0F) {
/*  725 */       float $$10 = ($$3 * 0.3F + $$4 * 0.59F + $$5 * 0.11F) * 0.2F;
/*      */       
/*  727 */       float $$11 = 1.0F - $$9 * 0.95F;
/*  728 */       $$3 = $$3 * $$11 + $$10 * (1.0F - $$11);
/*  729 */       $$4 = $$4 * $$11 + $$10 * (1.0F - $$11);
/*  730 */       $$5 = $$5 * $$11 + $$10 * (1.0F - $$11);
/*      */     } 
/*      */     
/*  733 */     return new Vec3($$3, $$4, $$5);
/*      */   }
/*      */   
/*      */   public float getStarBrightness(float $$0) {
/*  737 */     float $$1 = getTimeOfDay($$0);
/*      */     
/*  739 */     float $$2 = 1.0F - Mth.cos($$1 * 6.2831855F) * 2.0F + 0.25F;
/*  740 */     $$2 = Mth.clamp($$2, 0.0F, 1.0F);
/*      */     
/*  742 */     return $$2 * $$2 * 0.5F;
/*      */   }
/*      */   
/*      */   public int getSkyFlashTime() {
/*  746 */     return ((Boolean)this.minecraft.options.hideLightningFlash().get()).booleanValue() ? 0 : this.skyFlashTime;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setSkyFlashTime(int $$0) {
/*  751 */     this.skyFlashTime = $$0;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getShade(Direction $$0, boolean $$1) {
/*  756 */     boolean $$2 = effects().constantAmbientLight();
/*      */     
/*  758 */     if (!$$1) {
/*  759 */       return $$2 ? 0.9F : 1.0F;
/*      */     }
/*      */     
/*  762 */     switch ($$0) {
/*      */       case DOWN:
/*  764 */         return $$2 ? 0.9F : 0.5F;
/*      */       case UP:
/*  766 */         return $$2 ? 0.9F : 1.0F;
/*      */       case NORTH:
/*      */       case SOUTH:
/*  769 */         return 0.8F;
/*      */       case WEST:
/*      */       case EAST:
/*  772 */         return 0.6F;
/*      */     } 
/*  774 */     return 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getBlockTint(BlockPos $$0, ColorResolver $$1) {
/*  780 */     BlockTintCache $$2 = (BlockTintCache)this.tintCaches.get($$1);
/*  781 */     return $$2.getColor($$0);
/*      */   }
/*      */   
/*      */   public int calculateBlockTint(BlockPos $$0, ColorResolver $$1) {
/*  785 */     int $$2 = ((Integer)(Minecraft.getInstance()).options.biomeBlendRadius().get()).intValue();
/*  786 */     if ($$2 == 0) {
/*  787 */       return $$1.getColor((Biome)getBiome($$0).value(), $$0.getX(), $$0.getZ());
/*      */     }
/*      */     
/*  790 */     int $$3 = ($$2 * 2 + 1) * ($$2 * 2 + 1);
/*  791 */     int $$4 = 0;
/*  792 */     int $$5 = 0;
/*  793 */     int $$6 = 0;
/*      */     
/*  795 */     Cursor3D $$7 = new Cursor3D($$0.getX() - $$2, $$0.getY(), $$0.getZ() - $$2, $$0.getX() + $$2, $$0.getY(), $$0.getZ() + $$2);
/*  796 */     BlockPos.MutableBlockPos $$8 = new BlockPos.MutableBlockPos();
/*  797 */     while ($$7.advance()) {
/*  798 */       $$8.set($$7.nextX(), $$7.nextY(), $$7.nextZ());
/*  799 */       int $$9 = $$1.getColor((Biome)getBiome((BlockPos)$$8).value(), $$8.getX(), $$8.getZ());
/*      */       
/*  801 */       $$4 += ($$9 & 0xFF0000) >> 16;
/*  802 */       $$5 += ($$9 & 0xFF00) >> 8;
/*  803 */       $$6 += $$9 & 0xFF;
/*      */     } 
/*      */     
/*  806 */     return ($$4 / $$3 & 0xFF) << 16 | ($$5 / $$3 & 0xFF) << 8 | $$6 / $$3 & 0xFF;
/*      */   }
/*      */   
/*      */   public void setDefaultSpawnPos(BlockPos $$0, float $$1) {
/*  810 */     this.levelData.setSpawn($$0, $$1);
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/*  815 */     return "ClientLevel";
/*      */   }
/*      */ 
/*      */   
/*      */   public ClientLevelData getLevelData() {
/*  820 */     return this.clientLevelData;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void gameEvent(GameEvent $$0, Vec3 $$1, GameEvent.Context $$2) {}
/*      */ 
/*      */   
/*      */   protected Map<String, MapItemSavedData> getAllMapData() {
/*  829 */     return (Map<String, MapItemSavedData>)ImmutableMap.copyOf(this.mapData);
/*      */   }
/*      */   
/*      */   protected void addMapData(Map<String, MapItemSavedData> $$0) {
/*  833 */     this.mapData.putAll($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public static class ClientLevelData
/*      */     implements WritableLevelData
/*      */   {
/*      */     private final boolean hardcore;
/*      */     private final GameRules gameRules;
/*      */     private final boolean isFlat;
/*      */     private int xSpawn;
/*      */     private int ySpawn;
/*      */     private int zSpawn;
/*      */     private float spawnAngle;
/*      */     private long gameTime;
/*      */     private long dayTime;
/*      */     private boolean raining;
/*      */     private Difficulty difficulty;
/*      */     private boolean difficultyLocked;
/*      */     
/*      */     public ClientLevelData(Difficulty $$0, boolean $$1, boolean $$2) {
/*  854 */       this.difficulty = $$0;
/*  855 */       this.hardcore = $$1;
/*  856 */       this.isFlat = $$2;
/*  857 */       this.gameRules = new GameRules();
/*      */     }
/*      */ 
/*      */     
/*      */     public int getXSpawn() {
/*  862 */       return this.xSpawn;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getYSpawn() {
/*  867 */       return this.ySpawn;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getZSpawn() {
/*  872 */       return this.zSpawn;
/*      */     }
/*      */ 
/*      */     
/*      */     public float getSpawnAngle() {
/*  877 */       return this.spawnAngle;
/*      */     }
/*      */ 
/*      */     
/*      */     public long getGameTime() {
/*  882 */       return this.gameTime;
/*      */     }
/*      */ 
/*      */     
/*      */     public long getDayTime() {
/*  887 */       return this.dayTime;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setXSpawn(int $$0) {
/*  892 */       this.xSpawn = $$0;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setYSpawn(int $$0) {
/*  897 */       this.ySpawn = $$0;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setZSpawn(int $$0) {
/*  902 */       this.zSpawn = $$0;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setSpawnAngle(float $$0) {
/*  907 */       this.spawnAngle = $$0;
/*      */     }
/*      */     
/*      */     public void setGameTime(long $$0) {
/*  911 */       this.gameTime = $$0;
/*      */     }
/*      */     
/*      */     public void setDayTime(long $$0) {
/*  915 */       this.dayTime = $$0;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setSpawn(BlockPos $$0, float $$1) {
/*  920 */       this.xSpawn = $$0.getX();
/*  921 */       this.ySpawn = $$0.getY();
/*  922 */       this.zSpawn = $$0.getZ();
/*  923 */       this.spawnAngle = $$1;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isThundering() {
/*  928 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isRaining() {
/*  933 */       return this.raining;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setRaining(boolean $$0) {
/*  938 */       this.raining = $$0;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isHardcore() {
/*  943 */       return this.hardcore;
/*      */     }
/*      */ 
/*      */     
/*      */     public GameRules getGameRules() {
/*  948 */       return this.gameRules;
/*      */     }
/*      */ 
/*      */     
/*      */     public Difficulty getDifficulty() {
/*  953 */       return this.difficulty;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isDifficultyLocked() {
/*  958 */       return this.difficultyLocked;
/*      */     }
/*      */ 
/*      */     
/*      */     public void fillCrashReportCategory(CrashReportCategory $$0, LevelHeightAccessor $$1) {
/*  963 */       super.fillCrashReportCategory($$0, $$1);
/*      */     }
/*      */     
/*      */     public void setDifficulty(Difficulty $$0) {
/*  967 */       this.difficulty = $$0;
/*      */     }
/*      */     
/*      */     public void setDifficultyLocked(boolean $$0) {
/*  971 */       this.difficultyLocked = $$0;
/*      */     }
/*      */     
/*      */     public double getHorizonHeight(LevelHeightAccessor $$0) {
/*  975 */       if (this.isFlat) {
/*  976 */         return $$0.getMinBuildHeight();
/*      */       }
/*      */       
/*  979 */       return 63.0D;
/*      */     }
/*      */     
/*      */     public float getClearColorScale() {
/*  983 */       if (this.isFlat) {
/*  984 */         return 1.0F;
/*      */       }
/*  986 */       return 0.03125F;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected LevelEntityGetter<Entity> getEntities() {
/*  992 */     return this.entityStorage.getEntityGetter();
/*      */   }
/*      */ 
/*      */   
/*      */   private final class EntityCallbacks
/*      */     implements LevelCallback<Entity>
/*      */   {
/*      */     public void onCreated(Entity $$0) {}
/*      */ 
/*      */     
/*      */     public void onDestroyed(Entity $$0) {}
/*      */ 
/*      */     
/*      */     public void onTickingStart(Entity $$0) {
/* 1006 */       ClientLevel.this.tickingEntities.add($$0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onTickingEnd(Entity $$0) {
/* 1011 */       ClientLevel.this.tickingEntities.remove($$0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onTrackingStart(Entity $$0) {
/* 1016 */       if ($$0 instanceof AbstractClientPlayer) {
/* 1017 */         ClientLevel.this.players.add((AbstractClientPlayer)$$0);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void onTrackingEnd(Entity $$0) {
/* 1023 */       $$0.unRide();
/* 1024 */       ClientLevel.this.players.remove($$0);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void onSectionChange(Entity $$0) {}
/*      */   }
/*      */ 
/*      */   
/*      */   public String gatherChunkSourceStats() {
/* 1034 */     return "Chunks[C] W: " + this.chunkSource.gatherStats() + " E: " + this.entityStorage.gatherStats();
/*      */   }
/*      */ 
/*      */   
/*      */   public void addDestroyBlockEffect(BlockPos $$0, BlockState $$1) {
/* 1039 */     this.minecraft.particleEngine.destroy($$0, $$1);
/*      */   }
/*      */   
/*      */   public void setServerSimulationDistance(int $$0) {
/* 1043 */     this.serverSimulationDistance = $$0;
/*      */   }
/*      */   
/*      */   public int getServerSimulationDistance() {
/* 1047 */     return this.serverSimulationDistance;
/*      */   }
/*      */ 
/*      */   
/*      */   public FeatureFlagSet enabledFeatures() {
/* 1052 */     return this.connection.enabledFeatures();
/*      */   }
/*      */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\multiplayer\ClientLevel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */