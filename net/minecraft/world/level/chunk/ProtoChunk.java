/*     */ package net.minecraft.world.level.chunk;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
/*     */ import java.util.Collections;
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.LevelHeightAccessor;
/*     */ import net.minecraft.world.level.biome.Biome;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.levelgen.BelowZeroRetrogen;
/*     */ import net.minecraft.world.level.levelgen.GenerationStep;
/*     */ import net.minecraft.world.level.levelgen.Heightmap;
/*     */ import net.minecraft.world.level.levelgen.blending.BlendingData;
/*     */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*     */ import net.minecraft.world.level.levelgen.structure.Structure;
/*     */ import net.minecraft.world.level.levelgen.structure.StructureStart;
/*     */ import net.minecraft.world.level.lighting.LevelLightEngine;
/*     */ import net.minecraft.world.level.lighting.LightEngine;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ import net.minecraft.world.ticks.LevelChunkTicks;
/*     */ import net.minecraft.world.ticks.ProtoChunkTicks;
/*     */ import net.minecraft.world.ticks.SerializableTickContainer;
/*     */ import net.minecraft.world.ticks.TickContainerAccess;
/*     */ 
/*     */ public class ProtoChunk
/*     */   extends ChunkAccess
/*     */ {
/*     */   @Nullable
/*     */   private volatile LevelLightEngine lightEngine;
/*  45 */   private volatile ChunkStatus status = ChunkStatus.EMPTY;
/*  46 */   private final List<CompoundTag> entities = Lists.newArrayList();
/*     */   
/*  48 */   private final Map<GenerationStep.Carving, CarvingMask> carvingMasks = (Map<GenerationStep.Carving, CarvingMask>)new Object2ObjectArrayMap();
/*     */   
/*     */   @Nullable
/*     */   private BelowZeroRetrogen belowZeroRetrogen;
/*     */   private final ProtoChunkTicks<Block> blockTicks;
/*     */   private final ProtoChunkTicks<Fluid> fluidTicks;
/*     */   
/*     */   public ProtoChunk(ChunkPos $$0, UpgradeData $$1, LevelHeightAccessor $$2, Registry<Biome> $$3, @Nullable BlendingData $$4) {
/*  56 */     this($$0, $$1, (LevelChunkSection[])null, new ProtoChunkTicks(), new ProtoChunkTicks(), $$2, $$3, $$4);
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
/*     */   public ProtoChunk(ChunkPos $$0, UpgradeData $$1, @Nullable LevelChunkSection[] $$2, ProtoChunkTicks<Block> $$3, ProtoChunkTicks<Fluid> $$4, LevelHeightAccessor $$5, Registry<Biome> $$6, @Nullable BlendingData $$7) {
/*  69 */     super($$0, $$1, $$5, $$6, 0L, $$2, $$7);
/*  70 */     this.blockTicks = $$3;
/*  71 */     this.fluidTicks = $$4;
/*     */   }
/*     */ 
/*     */   
/*     */   public TickContainerAccess<Block> getBlockTicks() {
/*  76 */     return (TickContainerAccess<Block>)this.blockTicks;
/*     */   }
/*     */ 
/*     */   
/*     */   public TickContainerAccess<Fluid> getFluidTicks() {
/*  81 */     return (TickContainerAccess<Fluid>)this.fluidTicks;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChunkAccess.TicksToSave getTicksForSerialization() {
/*  86 */     return new ChunkAccess.TicksToSave((SerializableTickContainer<Block>)this.blockTicks, (SerializableTickContainer<Fluid>)this.fluidTicks);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState getBlockState(BlockPos $$0) {
/*  91 */     int $$1 = $$0.getY();
/*  92 */     if (isOutsideBuildHeight($$1)) {
/*  93 */       return Blocks.VOID_AIR.defaultBlockState();
/*     */     }
/*     */     
/*  96 */     LevelChunkSection $$2 = getSection(getSectionIndex($$1));
/*  97 */     if ($$2.hasOnlyAir()) {
/*  98 */       return Blocks.AIR.defaultBlockState();
/*     */     }
/*     */     
/* 101 */     return $$2.getBlockState($$0.getX() & 0xF, $$1 & 0xF, $$0.getZ() & 0xF);
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidState getFluidState(BlockPos $$0) {
/* 106 */     int $$1 = $$0.getY();
/* 107 */     if (isOutsideBuildHeight($$1)) {
/* 108 */       return Fluids.EMPTY.defaultFluidState();
/*     */     }
/*     */     
/* 111 */     LevelChunkSection $$2 = getSection(getSectionIndex($$1));
/* 112 */     if ($$2.hasOnlyAir()) {
/* 113 */       return Fluids.EMPTY.defaultFluidState();
/*     */     }
/*     */     
/* 116 */     return $$2.getFluidState($$0.getX() & 0xF, $$1 & 0xF, $$0.getZ() & 0xF);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockState setBlockState(BlockPos $$0, BlockState $$1, boolean $$2) {
/* 122 */     int $$3 = $$0.getX();
/* 123 */     int $$4 = $$0.getY();
/* 124 */     int $$5 = $$0.getZ();
/*     */     
/* 126 */     if ($$4 < getMinBuildHeight() || $$4 >= getMaxBuildHeight()) {
/* 127 */       return Blocks.VOID_AIR.defaultBlockState();
/*     */     }
/*     */     
/* 130 */     int $$6 = getSectionIndex($$4);
/* 131 */     LevelChunkSection $$7 = getSection($$6);
/* 132 */     boolean $$8 = $$7.hasOnlyAir();
/* 133 */     if ($$8 && $$1.is(Blocks.AIR)) {
/* 134 */       return $$1;
/*     */     }
/*     */     
/* 137 */     int $$9 = SectionPos.sectionRelative($$3);
/* 138 */     int $$10 = SectionPos.sectionRelative($$4);
/* 139 */     int $$11 = SectionPos.sectionRelative($$5);
/* 140 */     BlockState $$12 = $$7.setBlockState($$9, $$10, $$11, $$1);
/*     */     
/* 142 */     if (this.status.isOrAfter(ChunkStatus.INITIALIZE_LIGHT)) {
/* 143 */       boolean $$13 = $$7.hasOnlyAir();
/* 144 */       if ($$13 != $$8) {
/* 145 */         this.lightEngine.updateSectionStatus($$0, $$13);
/*     */       }
/*     */       
/* 148 */       if (LightEngine.hasDifferentLightProperties(this, $$0, $$12, $$1)) {
/* 149 */         this.skyLightSources.update(this, $$9, $$4, $$11);
/* 150 */         this.lightEngine.checkBlock($$0);
/*     */       } 
/*     */     } 
/*     */     
/* 154 */     EnumSet<Heightmap.Types> $$14 = getStatus().heightmapsAfter();
/* 155 */     EnumSet<Heightmap.Types> $$15 = null;
/*     */     
/* 157 */     for (Heightmap.Types $$16 : $$14) {
/* 158 */       Heightmap $$17 = this.heightmaps.get($$16);
/* 159 */       if ($$17 == null) {
/* 160 */         if ($$15 == null) {
/* 161 */           $$15 = EnumSet.noneOf(Heightmap.Types.class);
/*     */         }
/* 163 */         $$15.add($$16);
/*     */       } 
/*     */     } 
/*     */     
/* 167 */     if ($$15 != null) {
/* 168 */       Heightmap.primeHeightmaps(this, $$15);
/*     */     }
/*     */     
/* 171 */     for (Heightmap.Types $$18 : $$14) {
/* 172 */       ((Heightmap)this.heightmaps.get($$18)).update($$9, $$4, $$11, $$1);
/*     */     }
/*     */     
/* 175 */     return $$12;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBlockEntity(BlockEntity $$0) {
/* 180 */     this.blockEntities.put($$0.getBlockPos(), $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockEntity getBlockEntity(BlockPos $$0) {
/* 186 */     return this.blockEntities.get($$0);
/*     */   }
/*     */   
/*     */   public Map<BlockPos, BlockEntity> getBlockEntities() {
/* 190 */     return this.blockEntities;
/*     */   }
/*     */   
/*     */   public void addEntity(CompoundTag $$0) {
/* 194 */     this.entities.add($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addEntity(Entity $$0) {
/* 199 */     if ($$0.isPassenger()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 205 */     CompoundTag $$1 = new CompoundTag();
/* 206 */     $$0.save($$1);
/* 207 */     addEntity($$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setStartForStructure(Structure $$0, StructureStart $$1) {
/* 212 */     BelowZeroRetrogen $$2 = getBelowZeroRetrogen();
/* 213 */     if ($$2 != null && $$1.isValid()) {
/* 214 */       BoundingBox $$3 = $$1.getBoundingBox();
/* 215 */       LevelHeightAccessor $$4 = getHeightAccessorForGeneration();
/* 216 */       if ($$3.minY() < $$4.getMinBuildHeight() || $$3.maxY() >= $$4.getMaxBuildHeight()) {
/*     */         return;
/*     */       }
/*     */     } 
/* 220 */     super.setStartForStructure($$0, $$1);
/*     */   }
/*     */   
/*     */   public List<CompoundTag> getEntities() {
/* 224 */     return this.entities;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChunkStatus getStatus() {
/* 229 */     return this.status;
/*     */   }
/*     */   
/*     */   public void setStatus(ChunkStatus $$0) {
/* 233 */     this.status = $$0;
/* 234 */     if (this.belowZeroRetrogen != null && $$0.isOrAfter(this.belowZeroRetrogen.targetStatus())) {
/* 235 */       setBelowZeroRetrogen((BelowZeroRetrogen)null);
/*     */     }
/* 237 */     setUnsaved(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public Holder<Biome> getNoiseBiome(int $$0, int $$1, int $$2) {
/* 242 */     if (getHighestGeneratedStatus().isOrAfter(ChunkStatus.BIOMES)) {
/* 243 */       return super.getNoiseBiome($$0, $$1, $$2);
/*     */     }
/* 245 */     throw new IllegalStateException("Asking for biomes before we have biomes");
/*     */   }
/*     */   
/*     */   public static short packOffsetCoordinates(BlockPos $$0) {
/* 249 */     int $$1 = $$0.getX();
/* 250 */     int $$2 = $$0.getY();
/* 251 */     int $$3 = $$0.getZ();
/* 252 */     int $$4 = $$1 & 0xF;
/* 253 */     int $$5 = $$2 & 0xF;
/* 254 */     int $$6 = $$3 & 0xF;
/* 255 */     return (short)($$4 | $$5 << 4 | $$6 << 8);
/*     */   }
/*     */   
/*     */   public static BlockPos unpackOffsetCoordinates(short $$0, int $$1, ChunkPos $$2) {
/* 259 */     int $$3 = SectionPos.sectionToBlockCoord($$2.x, $$0 & 0xF);
/* 260 */     int $$4 = SectionPos.sectionToBlockCoord($$1, $$0 >>> 4 & 0xF);
/* 261 */     int $$5 = SectionPos.sectionToBlockCoord($$2.z, $$0 >>> 8 & 0xF);
/* 262 */     return new BlockPos($$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public void markPosForPostprocessing(BlockPos $$0) {
/* 267 */     if (!isOutsideBuildHeight($$0)) {
/* 268 */       ChunkAccess.getOrCreateOffsetList(this.postProcessing, getSectionIndex($$0.getY())).add(packOffsetCoordinates($$0));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void addPackedPostProcess(short $$0, int $$1) {
/* 274 */     ChunkAccess.getOrCreateOffsetList(this.postProcessing, $$1).add($$0);
/*     */   }
/*     */   
/*     */   public Map<BlockPos, CompoundTag> getBlockEntityNbts() {
/* 278 */     return Collections.unmodifiableMap(this.pendingBlockEntities);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public CompoundTag getBlockEntityNbtForSaving(BlockPos $$0) {
/* 284 */     BlockEntity $$1 = getBlockEntity($$0);
/* 285 */     if ($$1 != null) {
/* 286 */       return $$1.saveWithFullMetadata();
/*     */     }
/* 288 */     return this.pendingBlockEntities.get($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeBlockEntity(BlockPos $$0) {
/* 293 */     this.blockEntities.remove($$0);
/* 294 */     this.pendingBlockEntities.remove($$0);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public CarvingMask getCarvingMask(GenerationStep.Carving $$0) {
/* 299 */     return this.carvingMasks.get($$0);
/*     */   }
/*     */   
/*     */   public CarvingMask getOrCreateCarvingMask(GenerationStep.Carving $$0) {
/* 303 */     return this.carvingMasks.computeIfAbsent($$0, $$0 -> new CarvingMask(getHeight(), getMinBuildHeight()));
/*     */   }
/*     */   
/*     */   public void setCarvingMask(GenerationStep.Carving $$0, CarvingMask $$1) {
/* 307 */     this.carvingMasks.put($$0, $$1);
/*     */   }
/*     */   
/*     */   public void setLightEngine(LevelLightEngine $$0) {
/* 311 */     this.lightEngine = $$0;
/*     */   }
/*     */   
/*     */   public void setBelowZeroRetrogen(@Nullable BelowZeroRetrogen $$0) {
/* 315 */     this.belowZeroRetrogen = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BelowZeroRetrogen getBelowZeroRetrogen() {
/* 321 */     return this.belowZeroRetrogen;
/*     */   }
/*     */   
/*     */   private static <T> LevelChunkTicks<T> unpackTicks(ProtoChunkTicks<T> $$0) {
/* 325 */     return new LevelChunkTicks($$0.scheduledTicks());
/*     */   }
/*     */   
/*     */   public LevelChunkTicks<Block> unpackBlockTicks() {
/* 329 */     return unpackTicks(this.blockTicks);
/*     */   }
/*     */   
/*     */   public LevelChunkTicks<Fluid> unpackFluidTicks() {
/* 333 */     return unpackTicks(this.fluidTicks);
/*     */   }
/*     */ 
/*     */   
/*     */   public LevelHeightAccessor getHeightAccessorForGeneration() {
/* 338 */     if (isUpgrading()) {
/* 339 */       return BelowZeroRetrogen.UPGRADE_HEIGHT_ACCESSOR;
/*     */     }
/* 341 */     return (LevelHeightAccessor)this;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\chunk\ProtoChunk.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */