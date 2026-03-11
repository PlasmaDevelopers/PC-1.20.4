/*     */ package net.minecraft.world.level;
/*     */ 
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.HolderLookup;
/*     */ import net.minecraft.core.QuartPos;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.core.RegistryAccess;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.tags.FluidTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.flag.FeatureFlagSet;
/*     */ import net.minecraft.world.level.biome.Biome;
/*     */ import net.minecraft.world.level.biome.BiomeManager;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.chunk.ChunkAccess;
/*     */ import net.minecraft.world.level.chunk.ChunkStatus;
/*     */ import net.minecraft.world.level.dimension.DimensionType;
/*     */ import net.minecraft.world.level.levelgen.Heightmap;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface LevelReader
/*     */   extends BlockAndTintGetter, CollisionGetter, SignalGetter, BiomeManager.NoiseBiomeSource
/*     */ {
/*     */   @Nullable
/*     */   ChunkAccess getChunk(int paramInt1, int paramInt2, ChunkStatus paramChunkStatus, boolean paramBoolean);
/*     */   
/*     */   @Deprecated
/*     */   boolean hasChunk(int paramInt1, int paramInt2);
/*     */   
/*     */   int getHeight(Heightmap.Types paramTypes, int paramInt1, int paramInt2);
/*     */   
/*     */   int getSkyDarken();
/*     */   
/*     */   BiomeManager getBiomeManager();
/*     */   
/*     */   default Holder<Biome> getBiome(BlockPos $$0) {
/*  44 */     return getBiomeManager().getBiome($$0);
/*     */   }
/*     */   
/*     */   default Stream<BlockState> getBlockStatesIfLoaded(AABB $$0) {
/*  48 */     int $$1 = Mth.floor($$0.minX);
/*  49 */     int $$2 = Mth.floor($$0.maxX);
/*  50 */     int $$3 = Mth.floor($$0.minY);
/*  51 */     int $$4 = Mth.floor($$0.maxY);
/*  52 */     int $$5 = Mth.floor($$0.minZ);
/*  53 */     int $$6 = Mth.floor($$0.maxZ);
/*     */     
/*  55 */     if (hasChunksAt($$1, $$3, $$5, $$2, $$4, $$6)) {
/*  56 */       return getBlockStates($$0);
/*     */     }
/*  58 */     return Stream.empty();
/*     */   }
/*     */ 
/*     */   
/*     */   default int getBlockTint(BlockPos $$0, ColorResolver $$1) {
/*  63 */     return $$1.getColor((Biome)getBiome($$0).value(), $$0.getX(), $$0.getZ());
/*     */   }
/*     */ 
/*     */   
/*     */   default Holder<Biome> getNoiseBiome(int $$0, int $$1, int $$2) {
/*  68 */     ChunkAccess $$3 = getChunk(QuartPos.toSection($$0), QuartPos.toSection($$2), ChunkStatus.BIOMES, false);
/*  69 */     if ($$3 != null) {
/*  70 */       return $$3.getNoiseBiome($$0, $$1, $$2);
/*     */     }
/*  72 */     return getUncachedNoiseBiome($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   Holder<Biome> getUncachedNoiseBiome(int paramInt1, int paramInt2, int paramInt3);
/*     */ 
/*     */   
/*     */   boolean isClientSide();
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   int getSeaLevel();
/*     */ 
/*     */   
/*     */   DimensionType dimensionType();
/*     */   
/*     */   default int getMinBuildHeight() {
/*  89 */     return dimensionType().minY();
/*     */   }
/*     */ 
/*     */   
/*     */   default int getHeight() {
/*  94 */     return dimensionType().height();
/*     */   }
/*     */   
/*     */   default BlockPos getHeightmapPos(Heightmap.Types $$0, BlockPos $$1) {
/*  98 */     return new BlockPos($$1.getX(), getHeight($$0, $$1.getX(), $$1.getZ()), $$1.getZ());
/*     */   }
/*     */   
/*     */   default boolean isEmptyBlock(BlockPos $$0) {
/* 102 */     return getBlockState($$0).isAir();
/*     */   }
/*     */   
/*     */   default boolean canSeeSkyFromBelowWater(BlockPos $$0) {
/* 106 */     if ($$0.getY() >= getSeaLevel()) {
/* 107 */       return canSeeSky($$0);
/*     */     }
/* 109 */     BlockPos $$1 = new BlockPos($$0.getX(), getSeaLevel(), $$0.getZ());
/* 110 */     if (!canSeeSky($$1)) {
/* 111 */       return false;
/*     */     }
/* 113 */     $$1 = $$1.below();
/* 114 */     while ($$1.getY() > $$0.getY()) {
/* 115 */       BlockState $$2 = getBlockState($$1);
/* 116 */       if ($$2.getLightBlock(this, $$1) > 0 && !$$2.liquid()) {
/* 117 */         return false;
/*     */       }
/* 119 */       $$1 = $$1.below();
/*     */     } 
/* 121 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   default float getPathfindingCostFromLightLevels(BlockPos $$0) {
/* 126 */     return getLightLevelDependentMagicValue($$0) - 0.5F;
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
/*     */   @Deprecated
/*     */   default float getLightLevelDependentMagicValue(BlockPos $$0) {
/* 141 */     float $$1 = getMaxLocalRawBrightness($$0) / 15.0F;
/*     */     
/* 143 */     float $$2 = $$1 / (4.0F - 3.0F * $$1);
/* 144 */     return Mth.lerp(dimensionType().ambientLight(), $$2, 1.0F);
/*     */   }
/*     */   
/*     */   default ChunkAccess getChunk(BlockPos $$0) {
/* 148 */     return getChunk(SectionPos.blockToSectionCoord($$0.getX()), SectionPos.blockToSectionCoord($$0.getZ()));
/*     */   }
/*     */   
/*     */   default ChunkAccess getChunk(int $$0, int $$1) {
/* 152 */     return getChunk($$0, $$1, ChunkStatus.FULL, true);
/*     */   }
/*     */   
/*     */   default ChunkAccess getChunk(int $$0, int $$1, ChunkStatus $$2) {
/* 156 */     return getChunk($$0, $$1, $$2, true);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   default BlockGetter getChunkForCollisions(int $$0, int $$1) {
/* 162 */     return (BlockGetter)getChunk($$0, $$1, ChunkStatus.EMPTY, false);
/*     */   }
/*     */   
/*     */   default boolean isWaterAt(BlockPos $$0) {
/* 166 */     return getFluidState($$0).is(FluidTags.WATER);
/*     */   }
/*     */   
/*     */   default boolean containsAnyLiquid(AABB $$0) {
/* 170 */     int $$1 = Mth.floor($$0.minX);
/* 171 */     int $$2 = Mth.ceil($$0.maxX);
/* 172 */     int $$3 = Mth.floor($$0.minY);
/* 173 */     int $$4 = Mth.ceil($$0.maxY);
/* 174 */     int $$5 = Mth.floor($$0.minZ);
/* 175 */     int $$6 = Mth.ceil($$0.maxZ);
/*     */     
/* 177 */     BlockPos.MutableBlockPos $$7 = new BlockPos.MutableBlockPos();
/* 178 */     for (int $$8 = $$1; $$8 < $$2; $$8++) {
/* 179 */       for (int $$9 = $$3; $$9 < $$4; $$9++) {
/* 180 */         for (int $$10 = $$5; $$10 < $$6; $$10++) {
/* 181 */           BlockState $$11 = getBlockState((BlockPos)$$7.set($$8, $$9, $$10));
/* 182 */           if (!$$11.getFluidState().isEmpty()) {
/* 183 */             return true;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 188 */     return false;
/*     */   }
/*     */   
/*     */   default int getMaxLocalRawBrightness(BlockPos $$0) {
/* 192 */     return getMaxLocalRawBrightness($$0, getSkyDarken());
/*     */   }
/*     */   
/*     */   default int getMaxLocalRawBrightness(BlockPos $$0, int $$1) {
/* 196 */     if ($$0.getX() < -30000000 || $$0.getZ() < -30000000 || $$0.getX() >= 30000000 || $$0.getZ() >= 30000000) {
/* 197 */       return 15;
/*     */     }
/*     */     
/* 200 */     return getRawBrightness($$0, $$1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default boolean hasChunkAt(int $$0, int $$1) {
/* 208 */     return hasChunk(SectionPos.blockToSectionCoord($$0), SectionPos.blockToSectionCoord($$1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default boolean hasChunkAt(BlockPos $$0) {
/* 216 */     return hasChunkAt($$0.getX(), $$0.getZ());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default boolean hasChunksAt(BlockPos $$0, BlockPos $$1) {
/* 224 */     return hasChunksAt($$0.getX(), $$0.getY(), $$0.getZ(), $$1.getX(), $$1.getY(), $$1.getZ());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default boolean hasChunksAt(int $$0, int $$1, int $$2, int $$3, int $$4, int $$5) {
/* 232 */     if ($$4 < getMinBuildHeight() || $$1 >= getMaxBuildHeight()) {
/* 233 */       return false;
/*     */     }
/*     */     
/* 236 */     return hasChunksAt($$0, $$2, $$3, $$5);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default boolean hasChunksAt(int $$0, int $$1, int $$2, int $$3) {
/* 244 */     int $$4 = SectionPos.blockToSectionCoord($$0);
/* 245 */     int $$5 = SectionPos.blockToSectionCoord($$2);
/* 246 */     int $$6 = SectionPos.blockToSectionCoord($$1);
/* 247 */     int $$7 = SectionPos.blockToSectionCoord($$3);
/*     */     
/* 249 */     for (int $$8 = $$4; $$8 <= $$5; $$8++) {
/* 250 */       for (int $$9 = $$6; $$9 <= $$7; $$9++) {
/* 251 */         if (!hasChunk($$8, $$9)) {
/* 252 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 257 */     return true;
/*     */   }
/*     */   
/*     */   RegistryAccess registryAccess();
/*     */   
/*     */   FeatureFlagSet enabledFeatures();
/*     */   
/*     */   default <T> HolderLookup<T> holderLookup(ResourceKey<? extends Registry<? extends T>> $$0) {
/* 265 */     Registry<T> $$1 = registryAccess().registryOrThrow($$0);
/* 266 */     return $$1.asLookup().filterFeatures(enabledFeatures());
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\LevelReader.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */