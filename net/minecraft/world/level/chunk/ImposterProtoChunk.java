/*     */ package net.minecraft.world.level.chunk;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.longs.LongSet;
/*     */ import java.util.Map;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.biome.Biome;
/*     */ import net.minecraft.world.level.biome.BiomeResolver;
/*     */ import net.minecraft.world.level.biome.Climate;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.levelgen.GenerationStep;
/*     */ import net.minecraft.world.level.levelgen.Heightmap;
/*     */ import net.minecraft.world.level.levelgen.blending.BlendingData;
/*     */ import net.minecraft.world.level.levelgen.structure.Structure;
/*     */ import net.minecraft.world.level.levelgen.structure.StructureStart;
/*     */ import net.minecraft.world.level.lighting.ChunkSkyLightSources;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.ticks.BlackholeTickAccess;
/*     */ import net.minecraft.world.ticks.TickContainerAccess;
/*     */ 
/*     */ public class ImposterProtoChunk
/*     */   extends ProtoChunk {
/*     */   private final LevelChunk wrapped;
/*     */   private final boolean allowWrites;
/*     */   
/*     */   public ImposterProtoChunk(LevelChunk $$0, boolean $$1) {
/*  38 */     super($$0.getPos(), UpgradeData.EMPTY, $$0.levelHeightAccessor, $$0.getLevel().registryAccess().registryOrThrow(Registries.BIOME), $$0.getBlendingData());
/*     */     
/*  40 */     this.wrapped = $$0;
/*  41 */     this.allowWrites = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockEntity getBlockEntity(BlockPos $$0) {
/*  47 */     return this.wrapped.getBlockEntity($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState getBlockState(BlockPos $$0) {
/*  52 */     return this.wrapped.getBlockState($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidState getFluidState(BlockPos $$0) {
/*  57 */     return this.wrapped.getFluidState($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxLightLevel() {
/*  62 */     return this.wrapped.getMaxLightLevel();
/*     */   }
/*     */ 
/*     */   
/*     */   public LevelChunkSection getSection(int $$0) {
/*  67 */     if (this.allowWrites) {
/*  68 */       return this.wrapped.getSection($$0);
/*     */     }
/*  70 */     return super.getSection($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockState setBlockState(BlockPos $$0, BlockState $$1, boolean $$2) {
/*  76 */     if (this.allowWrites) {
/*  77 */       return this.wrapped.setBlockState($$0, $$1, $$2);
/*     */     }
/*  79 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBlockEntity(BlockEntity $$0) {
/*  84 */     if (this.allowWrites) {
/*  85 */       this.wrapped.setBlockEntity($$0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void addEntity(Entity $$0) {
/*  91 */     if (this.allowWrites) {
/*  92 */       this.wrapped.addEntity($$0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setStatus(ChunkStatus $$0) {
/*  98 */     if (this.allowWrites) {
/*  99 */       super.setStatus($$0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public LevelChunkSection[] getSections() {
/* 105 */     return this.wrapped.getSections();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setHeightmap(Heightmap.Types $$0, long[] $$1) {}
/*     */ 
/*     */   
/*     */   private Heightmap.Types fixType(Heightmap.Types $$0) {
/* 113 */     if ($$0 == Heightmap.Types.WORLD_SURFACE_WG) {
/* 114 */       return Heightmap.Types.WORLD_SURFACE;
/*     */     }
/*     */     
/* 117 */     if ($$0 == Heightmap.Types.OCEAN_FLOOR_WG) {
/* 118 */       return Heightmap.Types.OCEAN_FLOOR;
/*     */     }
/*     */     
/* 121 */     return $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public Heightmap getOrCreateHeightmapUnprimed(Heightmap.Types $$0) {
/* 126 */     return this.wrapped.getOrCreateHeightmapUnprimed($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeight(Heightmap.Types $$0, int $$1, int $$2) {
/* 131 */     return this.wrapped.getHeight(fixType($$0), $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public Holder<Biome> getNoiseBiome(int $$0, int $$1, int $$2) {
/* 136 */     return this.wrapped.getNoiseBiome($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChunkPos getPos() {
/* 141 */     return this.wrapped.getPos();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public StructureStart getStartForStructure(Structure $$0) {
/* 147 */     return this.wrapped.getStartForStructure($$0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStartForStructure(Structure $$0, StructureStart $$1) {}
/*     */ 
/*     */   
/*     */   public Map<Structure, StructureStart> getAllStarts() {
/* 156 */     return this.wrapped.getAllStarts();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAllStarts(Map<Structure, StructureStart> $$0) {}
/*     */ 
/*     */   
/*     */   public LongSet getReferencesForStructure(Structure $$0) {
/* 165 */     return this.wrapped.getReferencesForStructure($$0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addReferenceForStructure(Structure $$0, long $$1) {}
/*     */ 
/*     */   
/*     */   public Map<Structure, LongSet> getAllReferences() {
/* 174 */     return this.wrapped.getAllReferences();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAllReferences(Map<Structure, LongSet> $$0) {}
/*     */ 
/*     */   
/*     */   public void setUnsaved(boolean $$0) {
/* 183 */     this.wrapped.setUnsaved($$0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUnsaved() {
/* 189 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChunkStatus getStatus() {
/* 194 */     return this.wrapped.getStatus();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeBlockEntity(BlockPos $$0) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void markPosForPostprocessing(BlockPos $$0) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlockEntityNbt(CompoundTag $$0) {}
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public CompoundTag getBlockEntityNbt(BlockPos $$0) {
/* 212 */     return this.wrapped.getBlockEntityNbt($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public CompoundTag getBlockEntityNbtForSaving(BlockPos $$0) {
/* 218 */     return this.wrapped.getBlockEntityNbtForSaving($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void findBlocks(Predicate<BlockState> $$0, BiConsumer<BlockPos, BlockState> $$1) {
/* 223 */     this.wrapped.findBlocks($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public TickContainerAccess<Block> getBlockTicks() {
/* 228 */     if (this.allowWrites) {
/* 229 */       return this.wrapped.getBlockTicks();
/*     */     }
/* 231 */     return BlackholeTickAccess.emptyContainer();
/*     */   }
/*     */ 
/*     */   
/*     */   public TickContainerAccess<Fluid> getFluidTicks() {
/* 236 */     if (this.allowWrites) {
/* 237 */       return this.wrapped.getFluidTicks();
/*     */     }
/* 239 */     return BlackholeTickAccess.emptyContainer();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChunkAccess.TicksToSave getTicksForSerialization() {
/* 244 */     return this.wrapped.getTicksForSerialization();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlendingData getBlendingData() {
/* 250 */     return this.wrapped.getBlendingData();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBlendingData(BlendingData $$0) {
/* 255 */     this.wrapped.setBlendingData($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public CarvingMask getCarvingMask(GenerationStep.Carving $$0) {
/* 260 */     if (this.allowWrites) {
/* 261 */       return super.getCarvingMask($$0);
/*     */     }
/* 263 */     throw (UnsupportedOperationException)Util.pauseInIde(new UnsupportedOperationException("Meaningless in this context"));
/*     */   }
/*     */ 
/*     */   
/*     */   public CarvingMask getOrCreateCarvingMask(GenerationStep.Carving $$0) {
/* 268 */     if (this.allowWrites) {
/* 269 */       return super.getOrCreateCarvingMask($$0);
/*     */     }
/* 271 */     throw (UnsupportedOperationException)Util.pauseInIde(new UnsupportedOperationException("Meaningless in this context"));
/*     */   }
/*     */   
/*     */   public LevelChunk getWrapped() {
/* 275 */     return this.wrapped;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isLightCorrect() {
/* 280 */     return this.wrapped.isLightCorrect();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLightCorrect(boolean $$0) {
/* 285 */     this.wrapped.setLightCorrect($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void fillBiomesFromNoise(BiomeResolver $$0, Climate.Sampler $$1) {
/* 290 */     if (this.allowWrites) {
/* 291 */       this.wrapped.fillBiomesFromNoise($$0, $$1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void initializeLightSources() {
/* 297 */     this.wrapped.initializeLightSources();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChunkSkyLightSources getSkyLightSources() {
/* 302 */     return this.wrapped.getSkyLightSources();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\chunk\ImposterProtoChunk.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */