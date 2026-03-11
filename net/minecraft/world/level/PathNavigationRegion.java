/*     */ package net.minecraft.world.level;
/*     */ 
/*     */ import com.google.common.base.Suppliers;
/*     */ import java.util.List;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.util.profiling.ProfilerFiller;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.level.biome.Biome;
/*     */ import net.minecraft.world.level.biome.Biomes;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.border.WorldBorder;
/*     */ import net.minecraft.world.level.chunk.ChunkAccess;
/*     */ import net.minecraft.world.level.chunk.ChunkSource;
/*     */ import net.minecraft.world.level.chunk.EmptyLevelChunk;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PathNavigationRegion
/*     */   implements BlockGetter, CollisionGetter
/*     */ {
/*     */   protected final int centerX;
/*     */   protected final int centerZ;
/*     */   protected final ChunkAccess[][] chunks;
/*     */   protected boolean allEmpty;
/*     */   protected final Level level;
/*     */   private final Supplier<Holder<Biome>> plains;
/*     */   
/*     */   public PathNavigationRegion(Level $$0, BlockPos $$1, BlockPos $$2) {
/*  40 */     this.level = $$0;
/*     */     
/*  42 */     this.plains = (Supplier<Holder<Biome>>)Suppliers.memoize(() -> $$0.registryAccess().registryOrThrow(Registries.BIOME).getHolderOrThrow(Biomes.PLAINS));
/*     */     
/*  44 */     this.centerX = SectionPos.blockToSectionCoord($$1.getX());
/*  45 */     this.centerZ = SectionPos.blockToSectionCoord($$1.getZ());
/*  46 */     int $$3 = SectionPos.blockToSectionCoord($$2.getX());
/*  47 */     int $$4 = SectionPos.blockToSectionCoord($$2.getZ());
/*     */     
/*  49 */     this.chunks = new ChunkAccess[$$3 - this.centerX + 1][$$4 - this.centerZ + 1];
/*     */     
/*  51 */     ChunkSource $$5 = $$0.getChunkSource();
/*  52 */     this.allEmpty = true;
/*  53 */     for (int $$6 = this.centerX; $$6 <= $$3; $$6++) {
/*  54 */       for (int $$7 = this.centerZ; $$7 <= $$4; $$7++) {
/*  55 */         this.chunks[$$6 - this.centerX][$$7 - this.centerZ] = (ChunkAccess)$$5.getChunkNow($$6, $$7);
/*     */       }
/*     */     } 
/*     */     
/*  59 */     for (int $$8 = SectionPos.blockToSectionCoord($$1.getX()); $$8 <= SectionPos.blockToSectionCoord($$2.getX()); $$8++) {
/*  60 */       for (int $$9 = SectionPos.blockToSectionCoord($$1.getZ()); $$9 <= SectionPos.blockToSectionCoord($$2.getZ()); $$9++) {
/*  61 */         ChunkAccess $$10 = this.chunks[$$8 - this.centerX][$$9 - this.centerZ];
/*  62 */         if ($$10 != null && 
/*  63 */           !$$10.isYSpaceEmpty($$1.getY(), $$2.getY())) {
/*  64 */           this.allEmpty = false;
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private ChunkAccess getChunk(BlockPos $$0) {
/*  73 */     return getChunk(SectionPos.blockToSectionCoord($$0.getX()), SectionPos.blockToSectionCoord($$0.getZ()));
/*     */   }
/*     */   
/*     */   private ChunkAccess getChunk(int $$0, int $$1) {
/*  77 */     int $$2 = $$0 - this.centerX;
/*  78 */     int $$3 = $$1 - this.centerZ;
/*     */     
/*  80 */     if ($$2 < 0 || $$2 >= this.chunks.length || $$3 < 0 || $$3 >= (this.chunks[$$2]).length) {
/*  81 */       return (ChunkAccess)new EmptyLevelChunk(this.level, new ChunkPos($$0, $$1), this.plains.get());
/*     */     }
/*  83 */     ChunkAccess $$4 = this.chunks[$$2][$$3];
/*  84 */     return ($$4 != null) ? $$4 : (ChunkAccess)new EmptyLevelChunk(this.level, new ChunkPos($$0, $$1), this.plains.get());
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldBorder getWorldBorder() {
/*  89 */     return this.level.getWorldBorder();
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockGetter getChunkForCollisions(int $$0, int $$1) {
/*  94 */     return (BlockGetter)getChunk($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<VoxelShape> getEntityCollisions(@Nullable Entity $$0, AABB $$1) {
/*  99 */     return List.of();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockEntity getBlockEntity(BlockPos $$0) {
/* 105 */     ChunkAccess $$1 = getChunk($$0);
/* 106 */     return $$1.getBlockEntity($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState getBlockState(BlockPos $$0) {
/* 111 */     if (isOutsideBuildHeight($$0)) {
/* 112 */       return Blocks.AIR.defaultBlockState();
/*     */     }
/*     */     
/* 115 */     ChunkAccess $$1 = getChunk($$0);
/* 116 */     return $$1.getBlockState($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidState getFluidState(BlockPos $$0) {
/* 121 */     if (isOutsideBuildHeight($$0)) {
/* 122 */       return Fluids.EMPTY.defaultFluidState();
/*     */     }
/*     */     
/* 125 */     ChunkAccess $$1 = getChunk($$0);
/* 126 */     return $$1.getFluidState($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMinBuildHeight() {
/* 131 */     return this.level.getMinBuildHeight();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeight() {
/* 136 */     return this.level.getHeight();
/*     */   }
/*     */   
/*     */   public ProfilerFiller getProfiler() {
/* 140 */     return this.level.getProfiler();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\PathNavigationRegion.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */