/*     */ package net.minecraft.world.level.chunk;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.UnmodifiableIterator;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.shorts.ShortListIterator;
/*     */ import java.util.Map;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.CrashReport;
/*     */ import net.minecraft.CrashReportCategory;
/*     */ import net.minecraft.ReportedException;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraft.network.protocol.game.ClientboundLevelChunkPacketData;
/*     */ import net.minecraft.server.level.FullChunkStatus;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.util.profiling.ProfilerFiller;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelHeightAccessor;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.EntityBlock;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.BlockEntityTicker;
/*     */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*     */ import net.minecraft.world.level.block.entity.TickingBlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.gameevent.EuclideanGameEventListenerRegistry;
/*     */ import net.minecraft.world.level.gameevent.GameEventListener;
/*     */ import net.minecraft.world.level.gameevent.GameEventListenerRegistry;
/*     */ import net.minecraft.world.level.levelgen.Heightmap;
/*     */ import net.minecraft.world.level.levelgen.blending.BlendingData;
/*     */ import net.minecraft.world.level.lighting.LightEngine;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ import net.minecraft.world.ticks.LevelChunkTicks;
/*     */ import net.minecraft.world.ticks.SerializableTickContainer;
/*     */ import net.minecraft.world.ticks.TickContainerAccess;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class LevelChunk extends ChunkAccess {
/*  53 */   static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*  55 */   private static final TickingBlockEntity NULL_TICKER = new TickingBlockEntity()
/*     */     {
/*     */       public void tick() {}
/*     */ 
/*     */ 
/*     */       
/*     */       public boolean isRemoved() {
/*  62 */         return true;
/*     */       }
/*     */ 
/*     */       
/*     */       public BlockPos getPos() {
/*  67 */         return BlockPos.ZERO;
/*     */       }
/*     */ 
/*     */       
/*     */       public String getType() {
/*  72 */         return "<null>";
/*     */       }
/*     */     };
/*     */   
/*  76 */   private final Map<BlockPos, RebindableTickingBlockEntityWrapper> tickersInLevel = Maps.newHashMap();
/*     */   
/*     */   private boolean loaded;
/*     */   
/*     */   final Level level;
/*     */   
/*     */   @Nullable
/*     */   private Supplier<FullChunkStatus> fullStatus;
/*     */   @Nullable
/*     */   private PostLoadProcessor postLoad;
/*     */   private final Int2ObjectMap<GameEventListenerRegistry> gameEventListenerRegistrySections;
/*     */   private final LevelChunkTicks<Block> blockTicks;
/*     */   private final LevelChunkTicks<Fluid> fluidTicks;
/*     */   
/*     */   public LevelChunk(Level $$0, ChunkPos $$1) {
/*  91 */     this($$0, $$1, UpgradeData.EMPTY, new LevelChunkTicks(), new LevelChunkTicks(), 0L, (LevelChunkSection[])null, (PostLoadProcessor)null, (BlendingData)null);
/*     */   }
/*     */   
/*     */   public LevelChunk(Level $$0, ChunkPos $$1, UpgradeData $$2, LevelChunkTicks<Block> $$3, LevelChunkTicks<Fluid> $$4, long $$5, @Nullable LevelChunkSection[] $$6, @Nullable PostLoadProcessor $$7, @Nullable BlendingData $$8) {
/*  95 */     super($$1, $$2, (LevelHeightAccessor)$$0, $$0.registryAccess().registryOrThrow(Registries.BIOME), $$5, $$6, $$8);
/*  96 */     this.level = $$0;
/*     */     
/*  98 */     this.gameEventListenerRegistrySections = (Int2ObjectMap<GameEventListenerRegistry>)new Int2ObjectOpenHashMap();
/*     */     
/* 100 */     for (Heightmap.Types $$9 : Heightmap.Types.values()) {
/* 101 */       if (ChunkStatus.FULL.heightmapsAfter().contains($$9)) {
/* 102 */         this.heightmaps.put($$9, new Heightmap(this, $$9));
/*     */       }
/*     */     } 
/*     */     
/* 106 */     this.postLoad = $$7;
/* 107 */     this.blockTicks = $$3;
/* 108 */     this.fluidTicks = $$4;
/*     */   }
/*     */   
/*     */   public LevelChunk(ServerLevel $$0, ProtoChunk $$1, @Nullable PostLoadProcessor $$2) {
/* 112 */     this((Level)$$0, $$1.getPos(), $$1.getUpgradeData(), $$1.unpackBlockTicks(), $$1.unpackFluidTicks(), $$1.getInhabitedTime(), $$1.getSections(), $$2, $$1.getBlendingData());
/*     */     
/* 114 */     for (BlockEntity $$3 : $$1.getBlockEntities().values()) {
/* 115 */       setBlockEntity($$3);
/*     */     }
/*     */     
/* 118 */     this.pendingBlockEntities.putAll($$1.getBlockEntityNbts());
/*     */     
/* 120 */     for (int $$4 = 0; $$4 < ($$1.getPostProcessing()).length; $$4++) {
/* 121 */       this.postProcessing[$$4] = $$1.getPostProcessing()[$$4];
/*     */     }
/*     */     
/* 124 */     setAllStarts($$1.getAllStarts());
/* 125 */     setAllReferences($$1.getAllReferences());
/*     */     
/* 127 */     for (Map.Entry<Heightmap.Types, Heightmap> $$5 : $$1.getHeightmaps()) {
/* 128 */       if (ChunkStatus.FULL.heightmapsAfter().contains($$5.getKey())) {
/* 129 */         setHeightmap($$5.getKey(), ((Heightmap)$$5.getValue()).getRawData());
/*     */       }
/*     */     } 
/*     */     
/* 133 */     this.skyLightSources = $$1.skyLightSources;
/* 134 */     setLightCorrect($$1.isLightCorrect());
/*     */     
/* 136 */     this.unsaved = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public TickContainerAccess<Block> getBlockTicks() {
/* 141 */     return (TickContainerAccess<Block>)this.blockTicks;
/*     */   }
/*     */ 
/*     */   
/*     */   public TickContainerAccess<Fluid> getFluidTicks() {
/* 146 */     return (TickContainerAccess<Fluid>)this.fluidTicks;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChunkAccess.TicksToSave getTicksForSerialization() {
/* 151 */     return new ChunkAccess.TicksToSave((SerializableTickContainer<Block>)this.blockTicks, (SerializableTickContainer<Fluid>)this.fluidTicks);
/*     */   }
/*     */ 
/*     */   
/*     */   public GameEventListenerRegistry getListenerRegistry(int $$0) {
/* 156 */     Level level = this.level; if (level instanceof ServerLevel) { ServerLevel $$1 = (ServerLevel)level;
/* 157 */       return (GameEventListenerRegistry)this.gameEventListenerRegistrySections.computeIfAbsent($$0, $$2 -> new EuclideanGameEventListenerRegistry($$0, $$1, this::removeGameEventListenerRegistry)); }
/*     */     
/* 159 */     return super.getListenerRegistry($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState getBlockState(BlockPos $$0) {
/* 164 */     int $$1 = $$0.getX();
/* 165 */     int $$2 = $$0.getY();
/* 166 */     int $$3 = $$0.getZ();
/* 167 */     if (this.level.isDebug()) {
/* 168 */       BlockState $$4 = null;
/* 169 */       if ($$2 == 60) {
/* 170 */         $$4 = Blocks.BARRIER.defaultBlockState();
/*     */       }
/* 172 */       if ($$2 == 70) {
/* 173 */         $$4 = DebugLevelSource.getBlockStateFor($$1, $$3);
/*     */       }
/* 175 */       return ($$4 == null) ? Blocks.AIR.defaultBlockState() : $$4;
/*     */     } 
/*     */     
/*     */     try {
/* 179 */       int $$5 = getSectionIndex($$2);
/* 180 */       if ($$5 >= 0 && $$5 < this.sections.length) {
/* 181 */         LevelChunkSection $$6 = this.sections[$$5];
/* 182 */         if (!$$6.hasOnlyAir()) {
/* 183 */           return $$6.getBlockState($$1 & 0xF, $$2 & 0xF, $$3 & 0xF);
/*     */         }
/*     */       } 
/* 186 */       return Blocks.AIR.defaultBlockState();
/* 187 */     } catch (Throwable $$7) {
/* 188 */       CrashReport $$8 = CrashReport.forThrowable($$7, "Getting block state");
/* 189 */       CrashReportCategory $$9 = $$8.addCategory("Block being got");
/* 190 */       $$9.setDetail("Location", () -> CrashReportCategory.formatLocation((LevelHeightAccessor)this, $$0, $$1, $$2));
/* 191 */       throw new ReportedException($$8);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidState getFluidState(BlockPos $$0) {
/* 197 */     return getFluidState($$0.getX(), $$0.getY(), $$0.getZ());
/*     */   }
/*     */   
/*     */   public FluidState getFluidState(int $$0, int $$1, int $$2) {
/*     */     try {
/* 202 */       int $$3 = getSectionIndex($$1);
/* 203 */       if ($$3 >= 0 && $$3 < this.sections.length) {
/* 204 */         LevelChunkSection $$4 = this.sections[$$3];
/* 205 */         if (!$$4.hasOnlyAir()) {
/* 206 */           return $$4.getFluidState($$0 & 0xF, $$1 & 0xF, $$2 & 0xF);
/*     */         }
/*     */       } 
/* 209 */       return Fluids.EMPTY.defaultFluidState();
/* 210 */     } catch (Throwable $$5) {
/* 211 */       CrashReport $$6 = CrashReport.forThrowable($$5, "Getting fluid state");
/* 212 */       CrashReportCategory $$7 = $$6.addCategory("Block being got");
/* 213 */       $$7.setDetail("Location", () -> CrashReportCategory.formatLocation((LevelHeightAccessor)this, $$0, $$1, $$2));
/* 214 */       throw new ReportedException($$6);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockState setBlockState(BlockPos $$0, BlockState $$1, boolean $$2) {
/* 221 */     int $$3 = $$0.getY();
/*     */     
/* 223 */     LevelChunkSection $$4 = getSection(getSectionIndex($$3));
/* 224 */     boolean $$5 = $$4.hasOnlyAir();
/*     */     
/* 226 */     if ($$5 && $$1.isAir()) {
/* 227 */       return null;
/*     */     }
/*     */     
/* 230 */     int $$6 = $$0.getX() & 0xF;
/* 231 */     int $$7 = $$3 & 0xF;
/* 232 */     int $$8 = $$0.getZ() & 0xF;
/* 233 */     BlockState $$9 = $$4.setBlockState($$6, $$7, $$8, $$1);
/*     */     
/* 235 */     if ($$9 == $$1) {
/* 236 */       return null;
/*     */     }
/*     */     
/* 239 */     Block $$10 = $$1.getBlock();
/*     */     
/* 241 */     ((Heightmap)this.heightmaps.get(Heightmap.Types.MOTION_BLOCKING)).update($$6, $$3, $$8, $$1);
/* 242 */     ((Heightmap)this.heightmaps.get(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES)).update($$6, $$3, $$8, $$1);
/* 243 */     ((Heightmap)this.heightmaps.get(Heightmap.Types.OCEAN_FLOOR)).update($$6, $$3, $$8, $$1);
/* 244 */     ((Heightmap)this.heightmaps.get(Heightmap.Types.WORLD_SURFACE)).update($$6, $$3, $$8, $$1);
/* 245 */     boolean $$11 = $$4.hasOnlyAir();
/* 246 */     if ($$5 != $$11) {
/* 247 */       this.level.getChunkSource().getLightEngine().updateSectionStatus($$0, $$11);
/*     */     }
/*     */     
/* 250 */     if (LightEngine.hasDifferentLightProperties(this, $$0, $$9, $$1)) {
/* 251 */       ProfilerFiller $$12 = this.level.getProfiler();
/* 252 */       $$12.push("updateSkyLightSources");
/* 253 */       this.skyLightSources.update(this, $$6, $$3, $$8);
/* 254 */       $$12.popPush("queueCheckLight");
/* 255 */       this.level.getChunkSource().getLightEngine().checkBlock($$0);
/* 256 */       $$12.pop();
/*     */     } 
/*     */     
/* 259 */     boolean $$13 = $$9.hasBlockEntity();
/* 260 */     if (!this.level.isClientSide) {
/* 261 */       $$9.onRemove(this.level, $$0, $$1, $$2);
/* 262 */     } else if (!$$9.is($$10) && $$13) {
/* 263 */       removeBlockEntity($$0);
/*     */     } 
/*     */     
/* 266 */     if (!$$4.getBlockState($$6, $$7, $$8).is($$10)) {
/* 267 */       return null;
/*     */     }
/*     */     
/* 270 */     if (!this.level.isClientSide) {
/* 271 */       $$1.onPlace(this.level, $$0, $$9, $$2);
/*     */     }
/* 273 */     if ($$1.hasBlockEntity()) {
/* 274 */       BlockEntity $$14 = getBlockEntity($$0, EntityCreationType.CHECK);
/* 275 */       if ($$14 == null) {
/* 276 */         $$14 = ((EntityBlock)$$10).newBlockEntity($$0, $$1);
/* 277 */         if ($$14 != null) {
/* 278 */           addAndRegisterBlockEntity($$14);
/*     */         }
/*     */       } else {
/* 281 */         $$14.setBlockState($$1);
/* 282 */         updateBlockEntityTicker($$14);
/*     */       } 
/*     */     } 
/*     */     
/* 286 */     this.unsaved = true;
/* 287 */     return $$9;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void addEntity(Entity $$0) {}
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private BlockEntity createBlockEntity(BlockPos $$0) {
/* 298 */     BlockState $$1 = getBlockState($$0);
/* 299 */     if (!$$1.hasBlockEntity()) {
/* 300 */       return null;
/*     */     }
/*     */     
/* 303 */     return ((EntityBlock)$$1.getBlock()).newBlockEntity($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockEntity getBlockEntity(BlockPos $$0) {
/* 309 */     return getBlockEntity($$0, EntityCreationType.CHECK);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public BlockEntity getBlockEntity(BlockPos $$0, EntityCreationType $$1) {
/* 314 */     BlockEntity $$2 = this.blockEntities.get($$0);
/* 315 */     if ($$2 == null) {
/* 316 */       CompoundTag $$3 = this.pendingBlockEntities.remove($$0);
/* 317 */       if ($$3 != null) {
/* 318 */         BlockEntity $$4 = promotePendingBlockEntity($$0, $$3);
/* 319 */         if ($$4 != null) {
/* 320 */           return $$4;
/*     */         }
/*     */       } 
/*     */     } 
/* 324 */     if ($$2 == null) {
/* 325 */       if ($$1 == EntityCreationType.IMMEDIATE) {
/* 326 */         $$2 = createBlockEntity($$0);
/* 327 */         if ($$2 != null) {
/* 328 */           addAndRegisterBlockEntity($$2);
/*     */         }
/*     */       } 
/* 331 */     } else if ($$2.isRemoved()) {
/* 332 */       this.blockEntities.remove($$0);
/* 333 */       return null;
/*     */     } 
/*     */     
/* 336 */     return $$2;
/*     */   }
/*     */   
/*     */   public void addAndRegisterBlockEntity(BlockEntity $$0) {
/* 340 */     setBlockEntity($$0);
/*     */     
/* 342 */     if (isInLevel()) {
/* 343 */       Level level = this.level; if (level instanceof ServerLevel) { ServerLevel $$1 = (ServerLevel)level;
/* 344 */         addGameEventListener($$0, $$1); }
/*     */       
/* 346 */       updateBlockEntityTicker($$0);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isInLevel() {
/* 351 */     return (this.loaded || this.level.isClientSide());
/*     */   }
/*     */   
/*     */   boolean isTicking(BlockPos $$0) {
/* 355 */     if (!this.level.getWorldBorder().isWithinBounds($$0)) {
/* 356 */       return false;
/*     */     }
/*     */     
/* 359 */     Level level = this.level; if (level instanceof ServerLevel) { ServerLevel $$1 = (ServerLevel)level;
/* 360 */       return (getFullStatus().isOrAfter(FullChunkStatus.BLOCK_TICKING) && $$1
/* 361 */         .areEntitiesLoaded(ChunkPos.asLong($$0))); }
/*     */ 
/*     */     
/* 364 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBlockEntity(BlockEntity $$0) {
/* 369 */     BlockPos $$1 = $$0.getBlockPos();
/* 370 */     if (!getBlockState($$1).hasBlockEntity()) {
/*     */       return;
/*     */     }
/*     */     
/* 374 */     $$0.setLevel(this.level);
/* 375 */     $$0.clearRemoved();
/*     */     
/* 377 */     BlockEntity $$2 = this.blockEntities.put($$1.immutable(), $$0);
/* 378 */     if ($$2 != null && $$2 != $$0) {
/* 379 */       $$2.setRemoved();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public CompoundTag getBlockEntityNbtForSaving(BlockPos $$0) {
/* 386 */     BlockEntity $$1 = getBlockEntity($$0);
/* 387 */     if ($$1 != null && !$$1.isRemoved()) {
/* 388 */       CompoundTag $$2 = $$1.saveWithFullMetadata();
/* 389 */       $$2.putBoolean("keepPacked", false);
/* 390 */       return $$2;
/*     */     } 
/* 392 */     CompoundTag $$3 = this.pendingBlockEntities.get($$0);
/* 393 */     if ($$3 != null) {
/* 394 */       $$3 = $$3.copy();
/* 395 */       $$3.putBoolean("keepPacked", true);
/*     */     } 
/* 397 */     return $$3;
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeBlockEntity(BlockPos $$0) {
/* 402 */     if (isInLevel()) {
/* 403 */       BlockEntity $$1 = this.blockEntities.remove($$0);
/* 404 */       if ($$1 != null) {
/* 405 */         Level level = this.level; if (level instanceof ServerLevel) { ServerLevel $$2 = (ServerLevel)level;
/* 406 */           removeGameEventListener($$1, $$2); }
/*     */         
/* 408 */         $$1.setRemoved();
/*     */       } 
/*     */     } 
/*     */     
/* 412 */     removeBlockEntityTicker($$0);
/*     */   }
/*     */   
/*     */   private <T extends BlockEntity> void removeGameEventListener(T $$0, ServerLevel $$1) {
/* 416 */     Block $$2 = $$0.getBlockState().getBlock();
/*     */     
/* 418 */     if ($$2 instanceof EntityBlock) {
/* 419 */       GameEventListener $$3 = ((EntityBlock)$$2).getListener($$1, (BlockEntity)$$0);
/* 420 */       if ($$3 != null) {
/* 421 */         int $$4 = SectionPos.blockToSectionCoord($$0.getBlockPos().getY());
/*     */         
/* 423 */         GameEventListenerRegistry $$5 = getListenerRegistry($$4);
/* 424 */         $$5.unregister($$3);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void removeGameEventListenerRegistry(int $$0) {
/* 430 */     this.gameEventListenerRegistrySections.remove($$0);
/*     */   }
/*     */   
/*     */   private void removeBlockEntityTicker(BlockPos $$0) {
/* 434 */     RebindableTickingBlockEntityWrapper $$1 = this.tickersInLevel.remove($$0);
/* 435 */     if ($$1 != null) {
/* 436 */       $$1.rebind(NULL_TICKER);
/*     */     }
/*     */   }
/*     */   
/*     */   public void runPostLoad() {
/* 441 */     if (this.postLoad != null) {
/* 442 */       this.postLoad.run(this);
/* 443 */       this.postLoad = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 448 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void replaceWithPacketData(FriendlyByteBuf $$0, CompoundTag $$1, Consumer<ClientboundLevelChunkPacketData.BlockEntityTagOutput> $$2) {
/* 453 */     clearAllBlockEntities();
/*     */     
/* 455 */     for (LevelChunkSection $$3 : this.sections) {
/* 456 */       $$3.read($$0);
/*     */     }
/*     */     
/* 459 */     for (Heightmap.Types $$4 : Heightmap.Types.values()) {
/* 460 */       String $$5 = $$4.getSerializationKey();
/* 461 */       if ($$1.contains($$5, 12)) {
/* 462 */         setHeightmap($$4, $$1.getLongArray($$5));
/*     */       }
/*     */     } 
/*     */     
/* 466 */     initializeLightSources();
/*     */     
/* 468 */     $$2.accept(($$0, $$1, $$2) -> {
/*     */           BlockEntity $$3 = getBlockEntity($$0, EntityCreationType.IMMEDIATE);
/*     */           if ($$3 != null && $$2 != null && $$3.getType() == $$1) {
/*     */             $$3.load($$2);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void replaceBiomes(FriendlyByteBuf $$0) {
/* 479 */     for (LevelChunkSection $$1 : this.sections) {
/* 480 */       $$1.readBiomes($$0);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setLoaded(boolean $$0) {
/* 485 */     this.loaded = $$0;
/*     */   }
/*     */   
/*     */   public Level getLevel() {
/* 489 */     return this.level;
/*     */   }
/*     */   
/*     */   public Map<BlockPos, BlockEntity> getBlockEntities() {
/* 493 */     return this.blockEntities;
/*     */   }
/*     */   
/*     */   public void postProcessGeneration() {
/* 497 */     ChunkPos $$0 = getPos();
/* 498 */     for (int $$1 = 0; $$1 < this.postProcessing.length; $$1++) {
/* 499 */       if (this.postProcessing[$$1] != null) {
/* 500 */         for (ShortListIterator<Short> shortListIterator = this.postProcessing[$$1].iterator(); shortListIterator.hasNext(); ) { Short $$2 = shortListIterator.next();
/* 501 */           BlockPos $$3 = ProtoChunk.unpackOffsetCoordinates($$2.shortValue(), getSectionYFromSectionIndex($$1), $$0);
/* 502 */           BlockState $$4 = getBlockState($$3);
/* 503 */           FluidState $$5 = $$4.getFluidState();
/* 504 */           if (!$$5.isEmpty()) {
/* 505 */             $$5.tick(this.level, $$3);
/*     */           }
/*     */           
/* 508 */           if (!($$4.getBlock() instanceof net.minecraft.world.level.block.LiquidBlock)) {
/* 509 */             BlockState $$6 = Block.updateFromNeighbourShapes($$4, (LevelAccessor)this.level, $$3);
/* 510 */             this.level.setBlock($$3, $$6, 20);
/*     */           }  }
/*     */         
/* 513 */         this.postProcessing[$$1].clear();
/*     */       } 
/*     */     } 
/*     */     
/* 517 */     for (UnmodifiableIterator<BlockPos> unmodifiableIterator = ImmutableList.copyOf(this.pendingBlockEntities.keySet()).iterator(); unmodifiableIterator.hasNext(); ) { BlockPos $$7 = unmodifiableIterator.next();
/* 518 */       getBlockEntity($$7); }
/*     */     
/* 520 */     this.pendingBlockEntities.clear();
/* 521 */     this.upgradeData.upgrade(this);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private BlockEntity promotePendingBlockEntity(BlockPos $$0, CompoundTag $$1) {
/*     */     BlockEntity $$5;
/* 527 */     BlockState $$2 = getBlockState($$0);
/* 528 */     if ("DUMMY".equals($$1.getString("id"))) {
/* 529 */       if ($$2.hasBlockEntity()) {
/* 530 */         BlockEntity $$3 = ((EntityBlock)$$2.getBlock()).newBlockEntity($$0, $$2);
/*     */       } else {
/* 532 */         BlockEntity $$4 = null;
/* 533 */         LOGGER.warn("Tried to load a DUMMY block entity @ {} but found not block entity block {} at location", $$0, $$2);
/*     */       } 
/*     */     } else {
/* 536 */       $$5 = BlockEntity.loadStatic($$0, $$2, $$1);
/*     */     } 
/*     */     
/* 539 */     if ($$5 != null) {
/* 540 */       $$5.setLevel(this.level);
/* 541 */       addAndRegisterBlockEntity($$5);
/*     */     } else {
/* 543 */       LOGGER.warn("Tried to load a block entity for block {} but failed at location {}", $$2, $$0);
/*     */     } 
/*     */     
/* 546 */     return $$5;
/*     */   }
/*     */   
/*     */   public void unpackTicks(long $$0) {
/* 550 */     this.blockTicks.unpack($$0);
/* 551 */     this.fluidTicks.unpack($$0);
/*     */   }
/*     */   
/*     */   public void registerTickContainerInLevel(ServerLevel $$0) {
/* 555 */     $$0.getBlockTicks().addContainer(this.chunkPos, this.blockTicks);
/* 556 */     $$0.getFluidTicks().addContainer(this.chunkPos, this.fluidTicks);
/*     */   }
/*     */   
/*     */   public void unregisterTickContainerFromLevel(ServerLevel $$0) {
/* 560 */     $$0.getBlockTicks().removeContainer(this.chunkPos);
/* 561 */     $$0.getFluidTicks().removeContainer(this.chunkPos);
/*     */   }
/*     */   
/*     */   public enum EntityCreationType {
/* 565 */     IMMEDIATE,
/* 566 */     QUEUED,
/* 567 */     CHECK;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChunkStatus getStatus() {
/* 572 */     return ChunkStatus.FULL;
/*     */   }
/*     */   
/*     */   public FullChunkStatus getFullStatus() {
/* 576 */     if (this.fullStatus == null) {
/* 577 */       return FullChunkStatus.FULL;
/*     */     }
/* 579 */     return this.fullStatus.get();
/*     */   }
/*     */   
/*     */   public void setFullStatus(Supplier<FullChunkStatus> $$0) {
/* 583 */     this.fullStatus = $$0;
/*     */   }
/*     */   
/*     */   public void clearAllBlockEntities() {
/* 587 */     this.blockEntities.values().forEach(BlockEntity::setRemoved);
/* 588 */     this.blockEntities.clear();
/*     */     
/* 590 */     this.tickersInLevel.values().forEach($$0 -> $$0.rebind(NULL_TICKER));
/* 591 */     this.tickersInLevel.clear();
/*     */   }
/*     */   
/*     */   public void registerAllBlockEntitiesAfterLevelLoad() {
/* 595 */     this.blockEntities.values().forEach($$0 -> {
/*     */           Level $$1 = this.level;
/*     */           if ($$1 instanceof ServerLevel) {
/*     */             ServerLevel $$2 = (ServerLevel)$$1;
/*     */             addGameEventListener($$0, $$2);
/*     */           } 
/*     */           updateBlockEntityTicker($$0);
/*     */         });
/*     */   } private <T extends BlockEntity> void addGameEventListener(T $$0, ServerLevel $$1) {
/* 604 */     Block $$2 = $$0.getBlockState().getBlock();
/*     */     
/* 606 */     if ($$2 instanceof EntityBlock) {
/* 607 */       GameEventListener $$3 = ((EntityBlock)$$2).getListener($$1, (BlockEntity)$$0);
/* 608 */       if ($$3 != null) {
/* 609 */         getListenerRegistry(SectionPos.blockToSectionCoord($$0.getBlockPos().getY())).register($$3);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private <T extends BlockEntity> void updateBlockEntityTicker(T $$0) {
/* 615 */     BlockState $$1 = $$0.getBlockState();
/* 616 */     BlockEntityTicker<T> $$2 = $$1.getTicker(this.level, $$0.getType());
/* 617 */     if ($$2 == null) {
/* 618 */       removeBlockEntityTicker($$0.getBlockPos());
/*     */     } else {
/* 620 */       this.tickersInLevel.compute($$0.getBlockPos(), ($$2, $$3) -> {
/*     */             TickingBlockEntity $$4 = createTicker($$0, $$1);
/*     */             if ($$3 != null) {
/*     */               $$3.rebind($$4);
/*     */               return $$3;
/*     */             } 
/*     */             if (isInLevel()) {
/*     */               RebindableTickingBlockEntityWrapper $$5 = new RebindableTickingBlockEntityWrapper($$4);
/*     */               this.level.addBlockEntityTicker($$5);
/*     */               return $$5;
/*     */             } 
/*     */             return null;
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private <T extends BlockEntity> TickingBlockEntity createTicker(T $$0, BlockEntityTicker<T> $$1) {
/* 639 */     return new BoundTickingBlockEntity<>($$0, $$1);
/*     */   }
/*     */   
/*     */   private class BoundTickingBlockEntity<T extends BlockEntity> implements TickingBlockEntity {
/*     */     private final T blockEntity;
/*     */     private final BlockEntityTicker<T> ticker;
/*     */     private boolean loggedInvalidBlockState;
/*     */     
/*     */     BoundTickingBlockEntity(T $$0, BlockEntityTicker<T> $$1) {
/* 648 */       this.blockEntity = $$0;
/* 649 */       this.ticker = $$1;
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/* 654 */       if (!this.blockEntity.isRemoved() && this.blockEntity.hasLevel()) {
/* 655 */         BlockPos $$0 = this.blockEntity.getBlockPos();
/* 656 */         if (LevelChunk.this.isTicking($$0)) {
/*     */           try {
/* 658 */             ProfilerFiller $$1 = LevelChunk.this.level.getProfiler();
/* 659 */             $$1.push(this::getType);
/* 660 */             BlockState $$2 = LevelChunk.this.getBlockState($$0);
/* 661 */             if (this.blockEntity.getType().isValid($$2)) {
/* 662 */               this.ticker.tick(LevelChunk.this.level, this.blockEntity.getBlockPos(), $$2, (BlockEntity)this.blockEntity);
/* 663 */               this.loggedInvalidBlockState = false;
/*     */             }
/* 665 */             else if (!this.loggedInvalidBlockState) {
/* 666 */               this.loggedInvalidBlockState = true;
/* 667 */               LevelChunk.LOGGER.warn("Block entity {} @ {} state {} invalid for ticking:", new Object[] { LogUtils.defer(this::getType), LogUtils.defer(this::getPos), $$2 });
/*     */             } 
/*     */             
/* 670 */             $$1.pop();
/* 671 */           } catch (Throwable $$3) {
/* 672 */             CrashReport $$4 = CrashReport.forThrowable($$3, "Ticking block entity");
/* 673 */             CrashReportCategory $$5 = $$4.addCategory("Block entity being ticked");
/* 674 */             this.blockEntity.fillCrashReportCategory($$5);
/*     */             
/* 676 */             throw new ReportedException($$4);
/*     */           } 
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isRemoved() {
/* 684 */       return this.blockEntity.isRemoved();
/*     */     }
/*     */ 
/*     */     
/*     */     public BlockPos getPos() {
/* 689 */       return this.blockEntity.getBlockPos();
/*     */     }
/*     */ 
/*     */     
/*     */     public String getType() {
/* 694 */       return BlockEntityType.getKey(this.blockEntity.getType()).toString();
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 699 */       return "Level ticker for " + getType() + "@" + getPos();
/*     */     }
/*     */   }
/*     */   
/*     */   private class RebindableTickingBlockEntityWrapper implements TickingBlockEntity {
/*     */     private TickingBlockEntity ticker;
/*     */     
/*     */     RebindableTickingBlockEntityWrapper(TickingBlockEntity $$0) {
/* 707 */       this.ticker = $$0;
/*     */     }
/*     */     
/*     */     void rebind(TickingBlockEntity $$0) {
/* 711 */       this.ticker = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/* 716 */       this.ticker.tick();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isRemoved() {
/* 721 */       return this.ticker.isRemoved();
/*     */     }
/*     */ 
/*     */     
/*     */     public BlockPos getPos() {
/* 726 */       return this.ticker.getPos();
/*     */     }
/*     */ 
/*     */     
/*     */     public String getType() {
/* 731 */       return this.ticker.getType();
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 736 */       return "" + this.ticker + " <wrapped>";
/*     */     }
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface PostLoadProcessor {
/*     */     void run(LevelChunk param1LevelChunk);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\chunk\LevelChunk.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */