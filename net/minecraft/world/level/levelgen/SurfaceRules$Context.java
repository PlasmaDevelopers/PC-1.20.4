/*     */ package net.minecraft.world.level.levelgen;
/*     */ 
/*     */ import com.google.common.base.Suppliers;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.biome.Biome;
/*     */ import net.minecraft.world.level.chunk.ChunkAccess;
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
/*     */ public final class Context
/*     */ {
/*     */   private static final int HOW_FAR_BELOW_PRELIMINARY_SURFACE_LEVEL_TO_BUILD_SURFACE = 8;
/*     */   private static final int SURFACE_CELL_BITS = 4;
/*     */   private static final int SURFACE_CELL_SIZE = 16;
/*     */   private static final int SURFACE_CELL_MASK = 15;
/*     */   final SurfaceSystem system;
/*  49 */   final SurfaceRules.Condition temperature = new TemperatureHelperCondition(this);
/*  50 */   final SurfaceRules.Condition steep = new SteepMaterialCondition(this);
/*  51 */   final SurfaceRules.Condition hole = new HoleCondition(this);
/*  52 */   final SurfaceRules.Condition abovePreliminarySurface = new AbovePreliminarySurfaceCondition();
/*     */   
/*     */   final RandomState randomState;
/*     */   
/*     */   final ChunkAccess chunk;
/*     */   private final NoiseChunk noiseChunk;
/*     */   private final Function<BlockPos, Holder<Biome>> biomeGetter;
/*     */   final WorldGenerationContext context;
/*  60 */   private long lastPreliminarySurfaceCellOrigin = Long.MAX_VALUE;
/*  61 */   private final int[] preliminarySurfaceCache = new int[4];
/*     */ 
/*     */   
/*  64 */   long lastUpdateXZ = -9223372036854775807L;
/*     */   
/*     */   int blockX;
/*     */   
/*     */   int blockZ;
/*     */   int surfaceDepth;
/*  70 */   private long lastSurfaceDepth2Update = this.lastUpdateXZ - 1L;
/*     */   
/*     */   private double surfaceSecondary;
/*  73 */   private long lastMinSurfaceLevelUpdate = this.lastUpdateXZ - 1L;
/*     */   
/*     */   private int minSurfaceLevel;
/*     */   
/*  77 */   long lastUpdateY = -9223372036854775807L;
/*  78 */   final BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
/*     */   Supplier<Holder<Biome>> biome;
/*     */   int blockY;
/*     */   int waterHeight;
/*     */   int stoneDepthBelow;
/*     */   int stoneDepthAbove;
/*     */   
/*     */   protected Context(SurfaceSystem $$0, RandomState $$1, ChunkAccess $$2, NoiseChunk $$3, Function<BlockPos, Holder<Biome>> $$4, Registry<Biome> $$5, WorldGenerationContext $$6) {
/*  86 */     this.system = $$0;
/*  87 */     this.randomState = $$1;
/*  88 */     this.chunk = $$2;
/*  89 */     this.noiseChunk = $$3;
/*  90 */     this.biomeGetter = $$4;
/*  91 */     this.context = $$6;
/*     */   }
/*     */   
/*     */   protected void updateXZ(int $$0, int $$1) {
/*  95 */     this.lastUpdateXZ++;
/*  96 */     this.lastUpdateY++;
/*  97 */     this.blockX = $$0;
/*  98 */     this.blockZ = $$1;
/*  99 */     this.surfaceDepth = this.system.getSurfaceDepth($$0, $$1);
/*     */   }
/*     */   
/*     */   protected void updateY(int $$0, int $$1, int $$2, int $$3, int $$4, int $$5) {
/* 103 */     this.lastUpdateY++;
/* 104 */     this.biome = (Supplier<Holder<Biome>>)Suppliers.memoize(() -> (Holder)this.biomeGetter.apply(this.pos.set($$0, $$1, $$2)));
/* 105 */     this.blockY = $$4;
/* 106 */     this.waterHeight = $$2;
/* 107 */     this.stoneDepthBelow = $$1;
/* 108 */     this.stoneDepthAbove = $$0;
/*     */   }
/*     */   
/*     */   protected double getSurfaceSecondary() {
/* 112 */     if (this.lastSurfaceDepth2Update != this.lastUpdateXZ) {
/* 113 */       this.lastSurfaceDepth2Update = this.lastUpdateXZ;
/* 114 */       this.surfaceSecondary = this.system.getSurfaceSecondary(this.blockX, this.blockZ);
/*     */     } 
/* 116 */     return this.surfaceSecondary;
/*     */   }
/*     */   
/*     */   private static int blockCoordToSurfaceCell(int $$0) {
/* 120 */     return $$0 >> 4;
/*     */   }
/*     */   
/*     */   private static int surfaceCellToBlockCoord(int $$0) {
/* 124 */     return $$0 << 4;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getMinSurfaceLevel() {
/* 129 */     if (this.lastMinSurfaceLevelUpdate != this.lastUpdateXZ) {
/* 130 */       this.lastMinSurfaceLevelUpdate = this.lastUpdateXZ;
/* 131 */       int $$0 = blockCoordToSurfaceCell(this.blockX);
/* 132 */       int $$1 = blockCoordToSurfaceCell(this.blockZ);
/*     */       
/* 134 */       long $$2 = ChunkPos.asLong($$0, $$1);
/* 135 */       if (this.lastPreliminarySurfaceCellOrigin != $$2) {
/* 136 */         this.lastPreliminarySurfaceCellOrigin = $$2;
/*     */         
/* 138 */         this.preliminarySurfaceCache[0] = this.noiseChunk.preliminarySurfaceLevel(surfaceCellToBlockCoord($$0), surfaceCellToBlockCoord($$1));
/* 139 */         this.preliminarySurfaceCache[1] = this.noiseChunk.preliminarySurfaceLevel(surfaceCellToBlockCoord($$0 + 1), surfaceCellToBlockCoord($$1));
/* 140 */         this.preliminarySurfaceCache[2] = this.noiseChunk.preliminarySurfaceLevel(surfaceCellToBlockCoord($$0), surfaceCellToBlockCoord($$1 + 1));
/* 141 */         this.preliminarySurfaceCache[3] = this.noiseChunk.preliminarySurfaceLevel(surfaceCellToBlockCoord($$0 + 1), surfaceCellToBlockCoord($$1 + 1));
/*     */       } 
/* 143 */       int $$3 = Mth.floor(Mth.lerp2(((this.blockX & 0xF) / 16.0F), ((this.blockZ & 0xF) / 16.0F), this.preliminarySurfaceCache[0], this.preliminarySurfaceCache[1], this.preliminarySurfaceCache[2], this.preliminarySurfaceCache[3]));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 151 */       this.minSurfaceLevel = $$3 + this.surfaceDepth - 8;
/*     */     } 
/* 153 */     return this.minSurfaceLevel;
/*     */   }
/*     */   
/*     */   private static final class HoleCondition extends SurfaceRules.LazyXZCondition {
/*     */     HoleCondition(SurfaceRules.Context $$0) {
/* 158 */       super($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean compute() {
/* 163 */       return (this.context.surfaceDepth <= 0);
/*     */     }
/*     */   }
/*     */   
/*     */   private final class AbovePreliminarySurfaceCondition
/*     */     implements SurfaceRules.Condition {
/*     */     public boolean test() {
/* 170 */       return (SurfaceRules.Context.this.blockY >= SurfaceRules.Context.this.getMinSurfaceLevel());
/*     */     }
/*     */   }
/*     */   
/*     */   private static class TemperatureHelperCondition extends SurfaceRules.LazyYCondition {
/*     */     TemperatureHelperCondition(SurfaceRules.Context $$0) {
/* 176 */       super($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean compute() {
/* 181 */       return ((Biome)((Holder)this.context.biome.get()).value()).coldEnoughToSnow((BlockPos)this.context.pos.set(this.context.blockX, this.context.blockY, this.context.blockZ));
/*     */     }
/*     */   }
/*     */   
/*     */   private static class SteepMaterialCondition extends SurfaceRules.LazyXZCondition {
/*     */     SteepMaterialCondition(SurfaceRules.Context $$0) {
/* 187 */       super($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean compute() {
/* 192 */       int $$0 = this.context.blockX & 0xF;
/* 193 */       int $$1 = this.context.blockZ & 0xF;
/*     */       
/* 195 */       int $$2 = Math.max($$1 - 1, 0);
/* 196 */       int $$3 = Math.min($$1 + 1, 15);
/*     */       
/* 198 */       ChunkAccess $$4 = this.context.chunk;
/* 199 */       int $$5 = $$4.getHeight(Heightmap.Types.WORLD_SURFACE_WG, $$0, $$2);
/* 200 */       int $$6 = $$4.getHeight(Heightmap.Types.WORLD_SURFACE_WG, $$0, $$3);
/*     */       
/* 202 */       if ($$6 >= $$5 + 4) {
/* 203 */         return true;
/*     */       }
/*     */       
/* 206 */       int $$7 = Math.max($$0 - 1, 0);
/* 207 */       int $$8 = Math.min($$0 + 1, 15);
/* 208 */       int $$9 = $$4.getHeight(Heightmap.Types.WORLD_SURFACE_WG, $$7, $$1);
/* 209 */       int $$10 = $$4.getHeight(Heightmap.Types.WORLD_SURFACE_WG, $$8, $$1);
/*     */       
/* 211 */       return ($$9 >= $$10 + 4);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\SurfaceRules$Context.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */