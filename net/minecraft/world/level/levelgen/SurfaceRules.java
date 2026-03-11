/*     */ package net.minecraft.world.level.levelgen;
/*     */ 
/*     */ import com.google.common.base.Suppliers;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function3;
/*     */ import com.mojang.datafixers.util.Function4;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.KeyDispatchDataCodec;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.biome.Biome;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.chunk.ChunkAccess;
/*     */ import net.minecraft.world.level.levelgen.placement.CaveSurface;
/*     */ import net.minecraft.world.level.levelgen.synth.NormalNoise;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SurfaceRules
/*     */ {
/*     */   protected static final class Context
/*     */   {
/*     */     private static final int HOW_FAR_BELOW_PRELIMINARY_SURFACE_LEVEL_TO_BUILD_SURFACE = 8;
/*     */     private static final int SURFACE_CELL_BITS = 4;
/*     */     private static final int SURFACE_CELL_SIZE = 16;
/*     */     private static final int SURFACE_CELL_MASK = 15;
/*     */     final SurfaceSystem system;
/*  49 */     final SurfaceRules.Condition temperature = new TemperatureHelperCondition(this);
/*  50 */     final SurfaceRules.Condition steep = new SteepMaterialCondition(this);
/*  51 */     final SurfaceRules.Condition hole = new HoleCondition(this);
/*  52 */     final SurfaceRules.Condition abovePreliminarySurface = new AbovePreliminarySurfaceCondition();
/*     */     
/*     */     final RandomState randomState;
/*     */     
/*     */     final ChunkAccess chunk;
/*     */     private final NoiseChunk noiseChunk;
/*     */     private final Function<BlockPos, Holder<Biome>> biomeGetter;
/*     */     final WorldGenerationContext context;
/*  60 */     private long lastPreliminarySurfaceCellOrigin = Long.MAX_VALUE;
/*  61 */     private final int[] preliminarySurfaceCache = new int[4];
/*     */ 
/*     */     
/*  64 */     long lastUpdateXZ = -9223372036854775807L;
/*     */     
/*     */     int blockX;
/*     */     
/*     */     int blockZ;
/*     */     int surfaceDepth;
/*  70 */     private long lastSurfaceDepth2Update = this.lastUpdateXZ - 1L;
/*     */     
/*     */     private double surfaceSecondary;
/*  73 */     private long lastMinSurfaceLevelUpdate = this.lastUpdateXZ - 1L;
/*     */     
/*     */     private int minSurfaceLevel;
/*     */     
/*  77 */     long lastUpdateY = -9223372036854775807L;
/*  78 */     final BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
/*     */     Supplier<Holder<Biome>> biome;
/*     */     int blockY;
/*     */     int waterHeight;
/*     */     int stoneDepthBelow;
/*     */     int stoneDepthAbove;
/*     */     
/*     */     protected Context(SurfaceSystem $$0, RandomState $$1, ChunkAccess $$2, NoiseChunk $$3, Function<BlockPos, Holder<Biome>> $$4, Registry<Biome> $$5, WorldGenerationContext $$6) {
/*  86 */       this.system = $$0;
/*  87 */       this.randomState = $$1;
/*  88 */       this.chunk = $$2;
/*  89 */       this.noiseChunk = $$3;
/*  90 */       this.biomeGetter = $$4;
/*  91 */       this.context = $$6;
/*     */     }
/*     */     
/*     */     protected void updateXZ(int $$0, int $$1) {
/*  95 */       this.lastUpdateXZ++;
/*  96 */       this.lastUpdateY++;
/*  97 */       this.blockX = $$0;
/*  98 */       this.blockZ = $$1;
/*  99 */       this.surfaceDepth = this.system.getSurfaceDepth($$0, $$1);
/*     */     }
/*     */     
/*     */     protected void updateY(int $$0, int $$1, int $$2, int $$3, int $$4, int $$5) {
/* 103 */       this.lastUpdateY++;
/* 104 */       this.biome = (Supplier<Holder<Biome>>)Suppliers.memoize(() -> (Holder)this.biomeGetter.apply(this.pos.set($$0, $$1, $$2)));
/* 105 */       this.blockY = $$4;
/* 106 */       this.waterHeight = $$2;
/* 107 */       this.stoneDepthBelow = $$1;
/* 108 */       this.stoneDepthAbove = $$0;
/*     */     }
/*     */     
/*     */     protected double getSurfaceSecondary() {
/* 112 */       if (this.lastSurfaceDepth2Update != this.lastUpdateXZ) {
/* 113 */         this.lastSurfaceDepth2Update = this.lastUpdateXZ;
/* 114 */         this.surfaceSecondary = this.system.getSurfaceSecondary(this.blockX, this.blockZ);
/*     */       } 
/* 116 */       return this.surfaceSecondary;
/*     */     }
/*     */     
/*     */     private static int blockCoordToSurfaceCell(int $$0) {
/* 120 */       return $$0 >> 4;
/*     */     }
/*     */     
/*     */     private static int surfaceCellToBlockCoord(int $$0) {
/* 124 */       return $$0 << 4;
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getMinSurfaceLevel() {
/* 129 */       if (this.lastMinSurfaceLevelUpdate != this.lastUpdateXZ) {
/* 130 */         this.lastMinSurfaceLevelUpdate = this.lastUpdateXZ;
/* 131 */         int $$0 = blockCoordToSurfaceCell(this.blockX);
/* 132 */         int $$1 = blockCoordToSurfaceCell(this.blockZ);
/*     */         
/* 134 */         long $$2 = ChunkPos.asLong($$0, $$1);
/* 135 */         if (this.lastPreliminarySurfaceCellOrigin != $$2) {
/* 136 */           this.lastPreliminarySurfaceCellOrigin = $$2;
/*     */           
/* 138 */           this.preliminarySurfaceCache[0] = this.noiseChunk.preliminarySurfaceLevel(surfaceCellToBlockCoord($$0), surfaceCellToBlockCoord($$1));
/* 139 */           this.preliminarySurfaceCache[1] = this.noiseChunk.preliminarySurfaceLevel(surfaceCellToBlockCoord($$0 + 1), surfaceCellToBlockCoord($$1));
/* 140 */           this.preliminarySurfaceCache[2] = this.noiseChunk.preliminarySurfaceLevel(surfaceCellToBlockCoord($$0), surfaceCellToBlockCoord($$1 + 1));
/* 141 */           this.preliminarySurfaceCache[3] = this.noiseChunk.preliminarySurfaceLevel(surfaceCellToBlockCoord($$0 + 1), surfaceCellToBlockCoord($$1 + 1));
/*     */         } 
/* 143 */         int $$3 = Mth.floor(Mth.lerp2(((this.blockX & 0xF) / 16.0F), ((this.blockZ & 0xF) / 16.0F), this.preliminarySurfaceCache[0], this.preliminarySurfaceCache[1], this.preliminarySurfaceCache[2], this.preliminarySurfaceCache[3]));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 151 */         this.minSurfaceLevel = $$3 + this.surfaceDepth - 8;
/*     */       } 
/* 153 */       return this.minSurfaceLevel;
/*     */     }
/*     */     
/*     */     private static final class HoleCondition extends SurfaceRules.LazyXZCondition {
/*     */       HoleCondition(SurfaceRules.Context $$0) {
/* 158 */         super($$0);
/*     */       }
/*     */ 
/*     */       
/*     */       protected boolean compute() {
/* 163 */         return (this.context.surfaceDepth <= 0);
/*     */       }
/*     */     }
/*     */     
/*     */     private final class AbovePreliminarySurfaceCondition
/*     */       implements SurfaceRules.Condition {
/*     */       public boolean test() {
/* 170 */         return (SurfaceRules.Context.this.blockY >= SurfaceRules.Context.this.getMinSurfaceLevel());
/*     */       }
/*     */     }
/*     */     
/*     */     private static class TemperatureHelperCondition extends SurfaceRules.LazyYCondition {
/*     */       TemperatureHelperCondition(SurfaceRules.Context $$0) {
/* 176 */         super($$0);
/*     */       }
/*     */ 
/*     */       
/*     */       protected boolean compute() {
/* 181 */         return ((Biome)((Holder)this.context.biome.get()).value()).coldEnoughToSnow((BlockPos)this.context.pos.set(this.context.blockX, this.context.blockY, this.context.blockZ));
/*     */       }
/*     */     }
/*     */     
/*     */     private static class SteepMaterialCondition extends SurfaceRules.LazyXZCondition {
/*     */       SteepMaterialCondition(SurfaceRules.Context $$0) {
/* 187 */         super($$0);
/*     */       }
/*     */       
/*     */       protected boolean compute()
/*     */       {
/* 192 */         int $$0 = this.context.blockX & 0xF;
/* 193 */         int $$1 = this.context.blockZ & 0xF;
/*     */         
/* 195 */         int $$2 = Math.max($$1 - 1, 0);
/* 196 */         int $$3 = Math.min($$1 + 1, 15);
/*     */         
/* 198 */         ChunkAccess $$4 = this.context.chunk;
/* 199 */         int $$5 = $$4.getHeight(Heightmap.Types.WORLD_SURFACE_WG, $$0, $$2);
/* 200 */         int $$6 = $$4.getHeight(Heightmap.Types.WORLD_SURFACE_WG, $$0, $$3);
/*     */         
/* 202 */         if ($$6 >= $$5 + 4) {
/* 203 */           return true;
/*     */         }
/*     */         
/* 206 */         int $$7 = Math.max($$0 - 1, 0);
/* 207 */         int $$8 = Math.min($$0 + 1, 15);
/* 208 */         int $$9 = $$4.getHeight(Heightmap.Types.WORLD_SURFACE_WG, $$7, $$1);
/* 209 */         int $$10 = $$4.getHeight(Heightmap.Types.WORLD_SURFACE_WG, $$8, $$1);
/*     */         
/* 211 */         return ($$9 >= $$10 + 4); } } } private static final class HoleCondition extends LazyXZCondition { HoleCondition(SurfaceRules.Context $$0) { super($$0); } protected boolean compute() { return (this.context.surfaceDepth <= 0); } } private final class AbovePreliminarySurfaceCondition implements Condition { public boolean test() { return (this.this$0.blockY >= this.this$0.getMinSurfaceLevel()); } } private static class TemperatureHelperCondition extends LazyYCondition { TemperatureHelperCondition(SurfaceRules.Context $$0) { super($$0); } protected boolean compute() { return ((Biome)((Holder)this.context.biome.get()).value()).coldEnoughToSnow((BlockPos)this.context.pos.set(this.context.blockX, this.context.blockY, this.context.blockZ)); } } private static class SteepMaterialCondition extends LazyXZCondition { protected boolean compute() { int $$0 = this.context.blockX & 0xF; int $$1 = this.context.blockZ & 0xF; int $$2 = Math.max($$1 - 1, 0); int $$3 = Math.min($$1 + 1, 15); ChunkAccess $$4 = this.context.chunk; int $$5 = $$4.getHeight(Heightmap.Types.WORLD_SURFACE_WG, $$0, $$2); int $$6 = $$4.getHeight(Heightmap.Types.WORLD_SURFACE_WG, $$0, $$3); if ($$6 >= $$5 + 4) return true;  int $$7 = Math.max($$0 - 1, 0); int $$8 = Math.min($$0 + 1, 15); int $$9 = $$4.getHeight(Heightmap.Types.WORLD_SURFACE_WG, $$7, $$1); int $$10 = $$4.getHeight(Heightmap.Types.WORLD_SURFACE_WG, $$8, $$1); return ($$9 >= $$10 + 4); }
/*     */ 
/*     */ 
/*     */     
/*     */     SteepMaterialCondition(SurfaceRules.Context $$0) {
/*     */       super($$0);
/*     */     } }
/*     */ 
/*     */   
/*     */   private static abstract class LazyCondition
/*     */     implements Condition
/*     */   {
/*     */     protected final SurfaceRules.Context context;
/*     */     private long lastUpdate;
/*     */     @Nullable
/*     */     Boolean result;
/*     */     
/*     */     protected LazyCondition(SurfaceRules.Context $$0) {
/* 229 */       this.context = $$0;
/* 230 */       this.lastUpdate = getContextLastUpdate() - 1L;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean test() {
/* 235 */       long $$0 = getContextLastUpdate();
/*     */       
/* 237 */       if ($$0 == this.lastUpdate) {
/* 238 */         if (this.result == null) {
/* 239 */           throw new IllegalStateException("Update triggered but the result is null");
/*     */         }
/* 241 */         return this.result.booleanValue();
/*     */       } 
/* 243 */       this.lastUpdate = $$0;
/*     */       
/* 245 */       this.result = Boolean.valueOf(compute());
/*     */       
/* 247 */       return this.result.booleanValue();
/*     */     }
/*     */     
/*     */     protected abstract long getContextLastUpdate();
/*     */     
/*     */     protected abstract boolean compute();
/*     */   }
/*     */   
/*     */   private static abstract class LazyXZCondition extends LazyCondition {
/*     */     protected LazyXZCondition(SurfaceRules.Context $$0) {
/* 257 */       super($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     protected long getContextLastUpdate() {
/* 262 */       return this.context.lastUpdateXZ;
/*     */     }
/*     */   }
/*     */   
/*     */   private static abstract class LazyYCondition extends LazyCondition {
/*     */     protected LazyYCondition(SurfaceRules.Context $$0) {
/* 268 */       super($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     protected long getContextLastUpdate() {
/* 273 */       return this.context.lastUpdateY;
/*     */     } }
/*     */   private static final class NotCondition extends Record implements Condition { private final SurfaceRules.Condition target;
/*     */     
/* 277 */     NotCondition(SurfaceRules.Condition $$0) { this.target = $$0; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/SurfaceRules$NotCondition;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #277	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/SurfaceRules$NotCondition; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/SurfaceRules$NotCondition;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #277	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/SurfaceRules$NotCondition; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/SurfaceRules$NotCondition;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #277	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/levelgen/SurfaceRules$NotCondition;
/* 277 */       //   0	8	1	$$0	Ljava/lang/Object; } public SurfaceRules.Condition target() { return this.target; }
/*     */     
/*     */     public boolean test() {
/* 280 */       return !this.target.test();
/*     */     } }
/*     */ 
/*     */   
/* 284 */   public static final ConditionSource ON_FLOOR = stoneDepthCheck(0, false, CaveSurface.FLOOR);
/* 285 */   public static final ConditionSource UNDER_FLOOR = stoneDepthCheck(0, true, CaveSurface.FLOOR);
/* 286 */   public static final ConditionSource DEEP_UNDER_FLOOR = stoneDepthCheck(0, true, 6, CaveSurface.FLOOR);
/* 287 */   public static final ConditionSource VERY_DEEP_UNDER_FLOOR = stoneDepthCheck(0, true, 30, CaveSurface.FLOOR);
/*     */   
/* 289 */   public static final ConditionSource ON_CEILING = stoneDepthCheck(0, false, CaveSurface.CEILING);
/* 290 */   public static final ConditionSource UNDER_CEILING = stoneDepthCheck(0, true, CaveSurface.CEILING);
/*     */   
/*     */   public static ConditionSource stoneDepthCheck(int $$0, boolean $$1, CaveSurface $$2) {
/* 293 */     return new StoneDepthCheck($$0, $$1, 0, $$2);
/*     */   }
/*     */   
/*     */   public static ConditionSource stoneDepthCheck(int $$0, boolean $$1, int $$2, CaveSurface $$3) {
/* 297 */     return new StoneDepthCheck($$0, $$1, $$2, $$3);
/*     */   }
/*     */   
/*     */   public static ConditionSource not(ConditionSource $$0) {
/* 301 */     return new NotConditionSource($$0);
/*     */   }
/*     */   
/*     */   public static ConditionSource yBlockCheck(VerticalAnchor $$0, int $$1) {
/* 305 */     return new YConditionSource($$0, $$1, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ConditionSource yStartCheck(VerticalAnchor $$0, int $$1) {
/* 312 */     return new YConditionSource($$0, $$1, true);
/*     */   }
/*     */   
/*     */   public static ConditionSource waterBlockCheck(int $$0, int $$1) {
/* 316 */     return new WaterConditionSource($$0, $$1, false);
/*     */   }
/*     */   
/*     */   public static ConditionSource waterStartCheck(int $$0, int $$1) {
/* 320 */     return new WaterConditionSource($$0, $$1, true);
/*     */   }
/*     */   
/*     */   @SafeVarargs
/*     */   public static ConditionSource isBiome(ResourceKey<Biome>... $$0) {
/* 325 */     return isBiome(List.of($$0));
/*     */   }
/*     */   
/*     */   private static BiomeConditionSource isBiome(List<ResourceKey<Biome>> $$0) {
/* 329 */     return new BiomeConditionSource($$0);
/*     */   }
/*     */   
/*     */   public static ConditionSource noiseCondition(ResourceKey<NormalNoise.NoiseParameters> $$0, double $$1) {
/* 333 */     return noiseCondition($$0, $$1, Double.MAX_VALUE);
/*     */   }
/*     */   
/*     */   public static ConditionSource noiseCondition(ResourceKey<NormalNoise.NoiseParameters> $$0, double $$1, double $$2) {
/* 337 */     return new NoiseThresholdConditionSource($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   public static ConditionSource verticalGradient(String $$0, VerticalAnchor $$1, VerticalAnchor $$2) {
/* 341 */     return new VerticalGradientConditionSource(new ResourceLocation($$0), $$1, $$2);
/*     */   }
/*     */   
/*     */   public static ConditionSource steep() {
/* 345 */     return Steep.INSTANCE;
/*     */   }
/*     */   
/*     */   public static ConditionSource hole() {
/* 349 */     return Hole.INSTANCE;
/*     */   }
/*     */   
/*     */   public static ConditionSource abovePreliminarySurface() {
/* 353 */     return AbovePreliminarySurface.INSTANCE;
/*     */   }
/*     */   
/*     */   public static ConditionSource temperature() {
/* 357 */     return Temperature.INSTANCE;
/*     */   }
/*     */   
/*     */   private static final class StateRule
/*     */     extends Record implements SurfaceRule
/*     */   {
/*     */     private final BlockState state;
/*     */     
/* 365 */     StateRule(BlockState $$0) { this.state = $$0; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/SurfaceRules$StateRule;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #365	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/SurfaceRules$StateRule; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/SurfaceRules$StateRule;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #365	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/SurfaceRules$StateRule; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/SurfaceRules$StateRule;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #365	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/levelgen/SurfaceRules$StateRule;
/* 365 */       //   0	8	1	$$0	Ljava/lang/Object; } public BlockState state() { return this.state; }
/*     */     
/*     */     public BlockState tryApply(int $$0, int $$1, int $$2) {
/* 368 */       return this.state;
/*     */     } }
/*     */   private static final class TestRule extends Record implements SurfaceRule { private final SurfaceRules.Condition condition; private final SurfaceRules.SurfaceRule followup;
/*     */     
/* 372 */     TestRule(SurfaceRules.Condition $$0, SurfaceRules.SurfaceRule $$1) { this.condition = $$0; this.followup = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/SurfaceRules$TestRule;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #372	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/SurfaceRules$TestRule; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/SurfaceRules$TestRule;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #372	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/SurfaceRules$TestRule; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/SurfaceRules$TestRule;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #372	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/levelgen/SurfaceRules$TestRule;
/* 372 */       //   0	8	1	$$0	Ljava/lang/Object; } public SurfaceRules.Condition condition() { return this.condition; } public SurfaceRules.SurfaceRule followup() { return this.followup; }
/*     */     
/*     */     @Nullable
/*     */     public BlockState tryApply(int $$0, int $$1, int $$2) {
/* 376 */       if (!this.condition.test()) {
/* 377 */         return null;
/*     */       }
/* 379 */       return this.followup.tryApply($$0, $$1, $$2);
/*     */     } }
/*     */   private static final class SequenceRule extends Record implements SurfaceRule { private final List<SurfaceRules.SurfaceRule> rules;
/*     */     
/* 383 */     SequenceRule(List<SurfaceRules.SurfaceRule> $$0) { this.rules = $$0; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/SurfaceRules$SequenceRule;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #383	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/SurfaceRules$SequenceRule; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/SurfaceRules$SequenceRule;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #383	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/SurfaceRules$SequenceRule; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/SurfaceRules$SequenceRule;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #383	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/levelgen/SurfaceRules$SequenceRule;
/* 383 */       //   0	8	1	$$0	Ljava/lang/Object; } public List<SurfaceRules.SurfaceRule> rules() { return this.rules; }
/*     */     
/*     */     @Nullable
/*     */     public BlockState tryApply(int $$0, int $$1, int $$2) {
/* 387 */       for (SurfaceRules.SurfaceRule $$3 : this.rules) {
/* 388 */         BlockState $$4 = $$3.tryApply($$0, $$1, $$2);
/* 389 */         if ($$4 != null) {
/* 390 */           return $$4;
/*     */         }
/*     */       } 
/* 393 */       return null;
/*     */     } }
/*     */ 
/*     */   
/*     */   public static RuleSource ifTrue(ConditionSource $$0, RuleSource $$1) {
/* 398 */     return new TestRuleSource($$0, $$1);
/*     */   }
/*     */   
/*     */   public static RuleSource sequence(RuleSource... $$0) {
/* 402 */     if ($$0.length == 0) {
/* 403 */       throw new IllegalArgumentException("Need at least 1 rule for a sequence");
/*     */     }
/* 405 */     return new SequenceRuleSource(Arrays.asList($$0));
/*     */   }
/*     */   
/*     */   public static RuleSource state(BlockState $$0) {
/* 409 */     return new BlockRuleSource($$0);
/*     */   }
/*     */   
/*     */   public static RuleSource bandlands() {
/* 413 */     return Bandlands.INSTANCE;
/*     */   }
/*     */   
/*     */   static <A> Codec<? extends A> register(Registry<Codec<? extends A>> $$0, String $$1, KeyDispatchDataCodec<? extends A> $$2) {
/* 417 */     return (Codec<? extends A>)Registry.register($$0, $$1, $$2.codec());
/*     */   }
/*     */   public static interface ConditionSource extends Function<Context, Condition> { public static final Codec<ConditionSource> CODEC;
/*     */     static {
/* 421 */       CODEC = BuiltInRegistries.MATERIAL_CONDITION.byNameCodec().dispatch($$0 -> $$0.codec().codec(), Function.identity());
/*     */     } KeyDispatchDataCodec<? extends ConditionSource> codec();
/*     */     static Codec<? extends ConditionSource> bootstrap(Registry<Codec<? extends ConditionSource>> $$0) {
/* 424 */       SurfaceRules.register($$0, "biome", (KeyDispatchDataCodec)SurfaceRules.BiomeConditionSource.CODEC);
/* 425 */       SurfaceRules.register($$0, "noise_threshold", (KeyDispatchDataCodec)SurfaceRules.NoiseThresholdConditionSource.CODEC);
/* 426 */       SurfaceRules.register($$0, "vertical_gradient", (KeyDispatchDataCodec)SurfaceRules.VerticalGradientConditionSource.CODEC);
/* 427 */       SurfaceRules.register($$0, "y_above", (KeyDispatchDataCodec)SurfaceRules.YConditionSource.CODEC);
/* 428 */       SurfaceRules.register($$0, "water", (KeyDispatchDataCodec)SurfaceRules.WaterConditionSource.CODEC);
/* 429 */       SurfaceRules.register($$0, "temperature", (KeyDispatchDataCodec)SurfaceRules.Temperature.CODEC);
/* 430 */       SurfaceRules.register($$0, "steep", (KeyDispatchDataCodec)SurfaceRules.Steep.CODEC);
/* 431 */       SurfaceRules.register($$0, "not", (KeyDispatchDataCodec)SurfaceRules.NotConditionSource.CODEC);
/* 432 */       SurfaceRules.register($$0, "hole", (KeyDispatchDataCodec)SurfaceRules.Hole.CODEC);
/* 433 */       SurfaceRules.register($$0, "above_preliminary_surface", (KeyDispatchDataCodec)SurfaceRules.AbovePreliminarySurface.CODEC);
/* 434 */       return SurfaceRules.register($$0, "stone_depth", (KeyDispatchDataCodec)SurfaceRules.StoneDepthCheck.CODEC);
/*     */     } }
/*     */   
/*     */   public static interface RuleSource extends Function<Context, SurfaceRule> {
/*     */     public static final Codec<RuleSource> CODEC;
/*     */     
/*     */     static {
/* 441 */       CODEC = BuiltInRegistries.MATERIAL_RULE.byNameCodec().dispatch($$0 -> $$0.codec().codec(), Function.identity());
/*     */     } KeyDispatchDataCodec<? extends RuleSource> codec();
/*     */     static Codec<? extends RuleSource> bootstrap(Registry<Codec<? extends RuleSource>> $$0) {
/* 444 */       SurfaceRules.register($$0, "bandlands", (KeyDispatchDataCodec)SurfaceRules.Bandlands.CODEC);
/* 445 */       SurfaceRules.register($$0, "block", (KeyDispatchDataCodec)SurfaceRules.BlockRuleSource.CODEC);
/* 446 */       SurfaceRules.register($$0, "sequence", (KeyDispatchDataCodec)SurfaceRules.SequenceRuleSource.CODEC);
/* 447 */       return SurfaceRules.register($$0, "condition", (KeyDispatchDataCodec)SurfaceRules.TestRuleSource.CODEC);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class NotConditionSource extends Record implements ConditionSource { private final SurfaceRules.ConditionSource target;
/*     */     
/* 453 */     NotConditionSource(SurfaceRules.ConditionSource $$0) { this.target = $$0; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/SurfaceRules$NotConditionSource;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #453	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/SurfaceRules$NotConditionSource; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/SurfaceRules$NotConditionSource;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #453	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/SurfaceRules$NotConditionSource; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/SurfaceRules$NotConditionSource;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #453	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/levelgen/SurfaceRules$NotConditionSource;
/* 453 */       //   0	8	1	$$0	Ljava/lang/Object; } public SurfaceRules.ConditionSource target() { return this.target; }
/* 454 */      static final KeyDispatchDataCodec<NotConditionSource> CODEC = KeyDispatchDataCodec.of(SurfaceRules.ConditionSource.CODEC.xmap(NotConditionSource::new, NotConditionSource::target).fieldOf("invert"));
/*     */ 
/*     */     
/*     */     public KeyDispatchDataCodec<? extends SurfaceRules.ConditionSource> codec() {
/* 458 */       return (KeyDispatchDataCodec)CODEC;
/*     */     }
/*     */ 
/*     */     
/*     */     public SurfaceRules.Condition apply(SurfaceRules.Context $$0) {
/* 463 */       return new SurfaceRules.NotCondition(this.target.apply($$0));
/*     */     } }
/*     */   private static final class StoneDepthCheck extends Record implements ConditionSource { final int offset; final boolean addSurfaceDepth; final int secondaryDepthRange; private final CaveSurface surfaceType; static final KeyDispatchDataCodec<StoneDepthCheck> CODEC;
/*     */     
/* 467 */     StoneDepthCheck(int $$0, boolean $$1, int $$2, CaveSurface $$3) { this.offset = $$0; this.addSurfaceDepth = $$1; this.secondaryDepthRange = $$2; this.surfaceType = $$3; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/SurfaceRules$StoneDepthCheck;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #467	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/SurfaceRules$StoneDepthCheck; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/SurfaceRules$StoneDepthCheck;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #467	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/SurfaceRules$StoneDepthCheck; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/SurfaceRules$StoneDepthCheck;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #467	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/levelgen/SurfaceRules$StoneDepthCheck;
/* 467 */       //   0	8	1	$$0	Ljava/lang/Object; } public int offset() { return this.offset; } public boolean addSurfaceDepth() { return this.addSurfaceDepth; } public int secondaryDepthRange() { return this.secondaryDepthRange; } public CaveSurface surfaceType() { return this.surfaceType; } static {
/* 468 */       CODEC = KeyDispatchDataCodec.of(RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)Codec.INT.fieldOf("offset").forGetter(StoneDepthCheck::offset), (App)Codec.BOOL.fieldOf("add_surface_depth").forGetter(StoneDepthCheck::addSurfaceDepth), (App)Codec.INT.fieldOf("secondary_depth_range").forGetter(StoneDepthCheck::secondaryDepthRange), (App)CaveSurface.CODEC.fieldOf("surface_type").forGetter(StoneDepthCheck::surfaceType)).apply((Applicative)$$0, StoneDepthCheck::new)));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public KeyDispatchDataCodec<? extends SurfaceRules.ConditionSource> codec() {
/* 478 */       return (KeyDispatchDataCodec)CODEC;
/*     */     }
/*     */     
/*     */     public SurfaceRules.Condition apply(final SurfaceRules.Context ruleContext)
/*     */     {
/* 483 */       final boolean ceiling = (this.surfaceType == CaveSurface.CEILING);
/*     */       class StoneDepthCondition
/*     */         extends SurfaceRules.LazyYCondition {
/*     */         StoneDepthCondition() {
/* 487 */           super(param2Context);
/*     */         }
/*     */ 
/*     */         
/*     */         protected boolean compute() {
/* 492 */           int $$0 = ceiling ? this.context.stoneDepthBelow : this.context.stoneDepthAbove;
/* 493 */           int $$1 = SurfaceRules.StoneDepthCheck.this.addSurfaceDepth ? this.context.surfaceDepth : 0;
/* 494 */           int $$2 = (SurfaceRules.StoneDepthCheck.this.secondaryDepthRange == 0) ? 0 : (int)Mth.map(this.context.getSurfaceSecondary(), -1.0D, 1.0D, 0.0D, SurfaceRules.StoneDepthCheck.this.secondaryDepthRange);
/*     */           
/* 496 */           return ($$0 <= 1 + SurfaceRules.StoneDepthCheck.this.offset + $$1 + $$2);
/*     */         }
/*     */       };
/*     */       
/* 500 */       return new StoneDepthCondition();
/*     */     } }
/*     */    class StoneDepthCondition extends LazyYCondition { StoneDepthCondition() { super(param1Context); } protected boolean compute() { int $$0 = ceiling ? this.context.stoneDepthBelow : this.context.stoneDepthAbove; int $$1 = SurfaceRules.StoneDepthCheck.this.addSurfaceDepth ? this.context.surfaceDepth : 0;
/*     */       int $$2 = (SurfaceRules.StoneDepthCheck.this.secondaryDepthRange == 0) ? 0 : (int)Mth.map(this.context.getSurfaceSecondary(), -1.0D, 1.0D, 0.0D, SurfaceRules.StoneDepthCheck.this.secondaryDepthRange);
/*     */       return ($$0 <= 1 + SurfaceRules.StoneDepthCheck.this.offset + $$1 + $$2); } }
/* 505 */   private enum AbovePreliminarySurface implements ConditionSource { INSTANCE;
/* 506 */     static final KeyDispatchDataCodec<AbovePreliminarySurface> CODEC = KeyDispatchDataCodec.of(MapCodec.unit(INSTANCE)); static {
/*     */     
/*     */     }
/*     */     public KeyDispatchDataCodec<? extends SurfaceRules.ConditionSource> codec() {
/* 510 */       return (KeyDispatchDataCodec)CODEC;
/*     */     }
/*     */ 
/*     */     
/*     */     public SurfaceRules.Condition apply(SurfaceRules.Context $$0) {
/* 515 */       return $$0.abovePreliminarySurface;
/*     */     } }
/*     */ 
/*     */   
/*     */   private enum Hole implements ConditionSource {
/* 520 */     INSTANCE;
/* 521 */     static final KeyDispatchDataCodec<Hole> CODEC = KeyDispatchDataCodec.of(MapCodec.unit(INSTANCE)); static {
/*     */     
/*     */     }
/*     */     public KeyDispatchDataCodec<? extends SurfaceRules.ConditionSource> codec() {
/* 525 */       return (KeyDispatchDataCodec)CODEC;
/*     */     }
/*     */ 
/*     */     
/*     */     public SurfaceRules.Condition apply(SurfaceRules.Context $$0) {
/* 530 */       return $$0.hole;
/*     */     } }
/*     */   private static final class YConditionSource extends Record implements ConditionSource { final VerticalAnchor anchor; final int surfaceDepthMultiplier; final boolean addStoneDepth; static final KeyDispatchDataCodec<YConditionSource> CODEC;
/*     */     
/* 534 */     YConditionSource(VerticalAnchor $$0, int $$1, boolean $$2) { this.anchor = $$0; this.surfaceDepthMultiplier = $$1; this.addStoneDepth = $$2; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/SurfaceRules$YConditionSource;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #534	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/SurfaceRules$YConditionSource; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/SurfaceRules$YConditionSource;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #534	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/SurfaceRules$YConditionSource; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/SurfaceRules$YConditionSource;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #534	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/levelgen/SurfaceRules$YConditionSource;
/* 534 */       //   0	8	1	$$0	Ljava/lang/Object; } public VerticalAnchor anchor() { return this.anchor; } public int surfaceDepthMultiplier() { return this.surfaceDepthMultiplier; } public boolean addStoneDepth() { return this.addStoneDepth; } static {
/* 535 */       CODEC = KeyDispatchDataCodec.of(RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)VerticalAnchor.CODEC.fieldOf("anchor").forGetter(YConditionSource::anchor), (App)Codec.intRange(-20, 20).fieldOf("surface_depth_multiplier").forGetter(YConditionSource::surfaceDepthMultiplier), (App)Codec.BOOL.fieldOf("add_stone_depth").forGetter(YConditionSource::addStoneDepth)).apply((Applicative)$$0, YConditionSource::new)));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public KeyDispatchDataCodec<? extends SurfaceRules.ConditionSource> codec() {
/* 543 */       return (KeyDispatchDataCodec)CODEC;
/*     */     }
/*     */     
/*     */     public SurfaceRules.Condition apply(final SurfaceRules.Context ruleContext) {
/*     */       class YCondition
/*     */         extends SurfaceRules.LazyYCondition {
/*     */         YCondition() {
/* 550 */           super(param2Context);
/*     */         }
/*     */ 
/*     */         
/*     */         protected boolean compute() {
/* 555 */           return (this.context.blockY + (SurfaceRules.YConditionSource.this.addStoneDepth ? this.context.stoneDepthAbove : 0) >= SurfaceRules.YConditionSource.this.anchor.resolveY(this.context.context) + this.context.surfaceDepth * SurfaceRules.YConditionSource.this.surfaceDepthMultiplier);
/*     */         }
/*     */       };
/*     */       
/* 559 */       return new YCondition(); } }
/*     */    class YCondition extends LazyYCondition { YCondition() { super(param1Context); }
/*     */     protected boolean compute() { return (this.context.blockY + (SurfaceRules.YConditionSource.this.addStoneDepth ? this.context.stoneDepthAbove : 0) >= SurfaceRules.YConditionSource.this.anchor.resolveY(this.context.context) + this.context.surfaceDepth * SurfaceRules.YConditionSource.this.surfaceDepthMultiplier); } }
/*     */   private static final class WaterConditionSource extends Record implements ConditionSource { final int offset; final int surfaceDepthMultiplier; final boolean addStoneDepth; static final KeyDispatchDataCodec<WaterConditionSource> CODEC;
/* 563 */     WaterConditionSource(int $$0, int $$1, boolean $$2) { this.offset = $$0; this.surfaceDepthMultiplier = $$1; this.addStoneDepth = $$2; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/SurfaceRules$WaterConditionSource;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #563	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/SurfaceRules$WaterConditionSource; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/SurfaceRules$WaterConditionSource;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #563	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/SurfaceRules$WaterConditionSource; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/SurfaceRules$WaterConditionSource;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #563	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/levelgen/SurfaceRules$WaterConditionSource;
/* 563 */       //   0	8	1	$$0	Ljava/lang/Object; } public int offset() { return this.offset; } public int surfaceDepthMultiplier() { return this.surfaceDepthMultiplier; } public boolean addStoneDepth() { return this.addStoneDepth; } static {
/* 564 */       CODEC = KeyDispatchDataCodec.of(RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)Codec.INT.fieldOf("offset").forGetter(WaterConditionSource::offset), (App)Codec.intRange(-20, 20).fieldOf("surface_depth_multiplier").forGetter(WaterConditionSource::surfaceDepthMultiplier), (App)Codec.BOOL.fieldOf("add_stone_depth").forGetter(WaterConditionSource::addStoneDepth)).apply((Applicative)$$0, WaterConditionSource::new)));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public KeyDispatchDataCodec<? extends SurfaceRules.ConditionSource> codec() {
/* 572 */       return (KeyDispatchDataCodec)CODEC;
/*     */     }
/*     */     
/*     */     public SurfaceRules.Condition apply(final SurfaceRules.Context ruleContext) {
/*     */       class WaterCondition
/*     */         extends SurfaceRules.LazyYCondition {
/*     */         WaterCondition() {
/* 579 */           super(param2Context);
/*     */         }
/*     */ 
/*     */         
/*     */         protected boolean compute() {
/* 584 */           return (this.context.waterHeight == Integer.MIN_VALUE || this.context.blockY + (SurfaceRules.WaterConditionSource.this.addStoneDepth ? this.context.stoneDepthAbove : 0) >= this.context.waterHeight + SurfaceRules.WaterConditionSource.this.offset + this.context.surfaceDepth * SurfaceRules.WaterConditionSource.this.surfaceDepthMultiplier);
/*     */         }
/*     */       };
/*     */       
/* 588 */       return new WaterCondition(); } } class WaterCondition extends LazyYCondition { WaterCondition() { super(param1Context); } protected boolean compute() {
/*     */       return (this.context.waterHeight == Integer.MIN_VALUE || this.context.blockY + (SurfaceRules.WaterConditionSource.this.addStoneDepth ? this.context.stoneDepthAbove : 0) >= this.context.waterHeight + SurfaceRules.WaterConditionSource.this.offset + this.context.surfaceDepth * SurfaceRules.WaterConditionSource.this.surfaceDepthMultiplier);
/*     */     } }
/*     */   private static final class BiomeConditionSource implements ConditionSource { static final KeyDispatchDataCodec<BiomeConditionSource> CODEC; private final List<ResourceKey<Biome>> biomes; final Predicate<ResourceKey<Biome>> biomeNameTest;
/*     */     static {
/* 593 */       CODEC = KeyDispatchDataCodec.of(ResourceKey.codec(Registries.BIOME).listOf().fieldOf("biome_is").xmap(SurfaceRules::isBiome, $$0 -> $$0.biomes));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     BiomeConditionSource(List<ResourceKey<Biome>> $$0) {
/* 599 */       this.biomes = $$0;
/* 600 */       Objects.requireNonNull(Set.copyOf($$0)); this.biomeNameTest = Set.copyOf($$0)::contains;
/*     */     }
/*     */ 
/*     */     
/*     */     public KeyDispatchDataCodec<? extends SurfaceRules.ConditionSource> codec() {
/* 605 */       return (KeyDispatchDataCodec)CODEC;
/*     */     }
/*     */     
/*     */     public SurfaceRules.Condition apply(final SurfaceRules.Context ruleContext) {
/*     */       class BiomeCondition
/*     */         extends SurfaceRules.LazyYCondition {
/*     */         BiomeCondition() {
/* 612 */           super(param2Context);
/*     */         }
/*     */ 
/*     */         
/*     */         protected boolean compute() {
/* 617 */           return ((Holder)this.context.biome.get()).is(SurfaceRules.BiomeConditionSource.this.biomeNameTest);
/*     */         }
/*     */       };
/*     */       
/* 621 */       return new BiomeCondition();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object $$0) {
/* 626 */       if (this == $$0) {
/* 627 */         return true;
/*     */       }
/* 629 */       if ($$0 instanceof BiomeConditionSource) { BiomeConditionSource $$1 = (BiomeConditionSource)$$0;
/* 630 */         return this.biomes.equals($$1.biomes); }
/*     */       
/* 632 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 637 */       return this.biomes.hashCode();
/*     */     }
/*     */     
/*     */     public String toString()
/*     */     {
/* 642 */       return "BiomeConditionSource[biomes=" + this.biomes + "]";
/*     */     } }
/*     */    class BiomeCondition extends LazyYCondition { BiomeCondition() { super(param1Context); } protected boolean compute() { return ((Holder)this.context.biome.get()).is(SurfaceRules.BiomeConditionSource.this.biomeNameTest); } }
/*     */   private static final class NoiseThresholdConditionSource extends Record implements ConditionSource { private final ResourceKey<NormalNoise.NoiseParameters> noise; final double minThreshold; final double maxThreshold; static final KeyDispatchDataCodec<NoiseThresholdConditionSource> CODEC;
/* 646 */     NoiseThresholdConditionSource(ResourceKey<NormalNoise.NoiseParameters> $$0, double $$1, double $$2) { this.noise = $$0; this.minThreshold = $$1; this.maxThreshold = $$2; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/SurfaceRules$NoiseThresholdConditionSource;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #646	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/SurfaceRules$NoiseThresholdConditionSource; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/SurfaceRules$NoiseThresholdConditionSource;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #646	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/SurfaceRules$NoiseThresholdConditionSource; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/SurfaceRules$NoiseThresholdConditionSource;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #646	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/levelgen/SurfaceRules$NoiseThresholdConditionSource;
/* 646 */       //   0	8	1	$$0	Ljava/lang/Object; } public ResourceKey<NormalNoise.NoiseParameters> noise() { return this.noise; } public double minThreshold() { return this.minThreshold; } public double maxThreshold() { return this.maxThreshold; } static {
/* 647 */       CODEC = KeyDispatchDataCodec.of(RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)ResourceKey.codec(Registries.NOISE).fieldOf("noise").forGetter(NoiseThresholdConditionSource::noise), (App)Codec.DOUBLE.fieldOf("min_threshold").forGetter(NoiseThresholdConditionSource::minThreshold), (App)Codec.DOUBLE.fieldOf("max_threshold").forGetter(NoiseThresholdConditionSource::maxThreshold)).apply((Applicative)$$0, NoiseThresholdConditionSource::new)));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public KeyDispatchDataCodec<? extends SurfaceRules.ConditionSource> codec() {
/* 656 */       return (KeyDispatchDataCodec)CODEC;
/*     */     }
/*     */     
/*     */     public SurfaceRules.Condition apply(final SurfaceRules.Context ruleContext)
/*     */     {
/* 661 */       final NormalNoise noise = ruleContext.randomState.getOrCreateNoise(this.noise);
/*     */       class NoiseThresholdCondition extends SurfaceRules.LazyXZCondition {
/*     */         NoiseThresholdCondition() {
/* 664 */           super(param2Context);
/*     */         }
/*     */ 
/*     */         
/*     */         protected boolean compute() {
/* 669 */           double $$0 = noise.getValue(this.context.blockX, 0.0D, this.context.blockZ);
/* 670 */           return ($$0 >= SurfaceRules.NoiseThresholdConditionSource.this.minThreshold && $$0 <= SurfaceRules.NoiseThresholdConditionSource.this.maxThreshold);
/*     */         }
/*     */       };
/*     */       
/* 674 */       return new NoiseThresholdCondition();
/*     */     } } class NoiseThresholdCondition extends LazyXZCondition { NoiseThresholdCondition() { super(param1Context); } protected boolean compute() { double $$0 = noise.getValue(this.context.blockX, 0.0D, this.context.blockZ);
/*     */       return ($$0 >= SurfaceRules.NoiseThresholdConditionSource.this.minThreshold && $$0 <= SurfaceRules.NoiseThresholdConditionSource.this.maxThreshold); } } private static final class VerticalGradientConditionSource extends Record implements ConditionSource {
/*     */     private final ResourceLocation randomName; private final VerticalAnchor trueAtAndBelow; private final VerticalAnchor falseAtAndAbove; static final KeyDispatchDataCodec<VerticalGradientConditionSource> CODEC;
/* 678 */     VerticalGradientConditionSource(ResourceLocation $$0, VerticalAnchor $$1, VerticalAnchor $$2) { this.randomName = $$0; this.trueAtAndBelow = $$1; this.falseAtAndAbove = $$2; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/SurfaceRules$VerticalGradientConditionSource;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #678	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/SurfaceRules$VerticalGradientConditionSource; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/SurfaceRules$VerticalGradientConditionSource;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #678	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/SurfaceRules$VerticalGradientConditionSource; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/SurfaceRules$VerticalGradientConditionSource;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #678	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/levelgen/SurfaceRules$VerticalGradientConditionSource;
/* 678 */       //   0	8	1	$$0	Ljava/lang/Object; } public ResourceLocation randomName() { return this.randomName; } public VerticalAnchor trueAtAndBelow() { return this.trueAtAndBelow; } public VerticalAnchor falseAtAndAbove() { return this.falseAtAndAbove; } static {
/* 679 */       CODEC = KeyDispatchDataCodec.of(RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)ResourceLocation.CODEC.fieldOf("random_name").forGetter(VerticalGradientConditionSource::randomName), (App)VerticalAnchor.CODEC.fieldOf("true_at_and_below").forGetter(VerticalGradientConditionSource::trueAtAndBelow), (App)VerticalAnchor.CODEC.fieldOf("false_at_and_above").forGetter(VerticalGradientConditionSource::falseAtAndAbove)).apply((Applicative)$$0, VerticalGradientConditionSource::new)));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public KeyDispatchDataCodec<? extends SurfaceRules.ConditionSource> codec() {
/* 687 */       return (KeyDispatchDataCodec)CODEC;
/*     */     }
/*     */     
/*     */     public SurfaceRules.Condition apply(final SurfaceRules.Context ruleContext)
/*     */     {
/* 692 */       final int trueAtAndBelow = trueAtAndBelow().resolveY(ruleContext.context);
/* 693 */       final int falseAtAndAbove = falseAtAndAbove().resolveY(ruleContext.context);
/* 694 */       final PositionalRandomFactory randomFactory = ruleContext.randomState.getOrCreateRandomFactory(randomName());
/*     */       class VerticalGradientCondition
/*     */         extends SurfaceRules.LazyYCondition {
/*     */         VerticalGradientCondition() {
/* 698 */           super(param2Context);
/*     */         }
/*     */ 
/*     */         
/*     */         protected boolean compute() {
/* 703 */           int $$0 = this.context.blockY;
/* 704 */           if ($$0 <= trueAtAndBelow) {
/* 705 */             return true;
/*     */           }
/* 707 */           if ($$0 >= falseAtAndAbove) {
/* 708 */             return false;
/*     */           }
/* 710 */           double $$1 = Mth.map($$0, trueAtAndBelow, falseAtAndAbove, 1.0D, 0.0D);
/* 711 */           RandomSource $$2 = randomFactory.at(this.context.blockX, $$0, this.context.blockZ);
/* 712 */           return ($$2.nextFloat() < $$1);
/*     */         }
/*     */       };
/* 715 */       return new VerticalGradientCondition();
/*     */     }
/*     */   } class VerticalGradientCondition extends LazyYCondition { VerticalGradientCondition() { super(param1Context); } protected boolean compute() { int $$0 = this.context.blockY; if ($$0 <= trueAtAndBelow)
/*     */         return true;  if ($$0 >= falseAtAndAbove)
/*     */         return false;  double $$1 = Mth.map($$0, trueAtAndBelow, falseAtAndAbove, 1.0D, 0.0D); RandomSource $$2 = randomFactory.at(this.context.blockX, $$0, this.context.blockZ); return ($$2.nextFloat() < $$1); } }
/* 720 */   private enum Temperature implements ConditionSource { INSTANCE;
/* 721 */     static final KeyDispatchDataCodec<Temperature> CODEC = KeyDispatchDataCodec.of(MapCodec.unit(INSTANCE)); static {
/*     */     
/*     */     }
/*     */     public KeyDispatchDataCodec<? extends SurfaceRules.ConditionSource> codec() {
/* 725 */       return (KeyDispatchDataCodec)CODEC;
/*     */     }
/*     */ 
/*     */     
/*     */     public SurfaceRules.Condition apply(SurfaceRules.Context $$0) {
/* 730 */       return $$0.temperature;
/*     */     } }
/*     */ 
/*     */   
/*     */   private enum Steep implements ConditionSource {
/* 735 */     INSTANCE;
/* 736 */     static final KeyDispatchDataCodec<Steep> CODEC = KeyDispatchDataCodec.of(MapCodec.unit(INSTANCE)); static {
/*     */     
/*     */     }
/*     */     public KeyDispatchDataCodec<? extends SurfaceRules.ConditionSource> codec() {
/* 740 */       return (KeyDispatchDataCodec)CODEC;
/*     */     }
/*     */ 
/*     */     
/*     */     public SurfaceRules.Condition apply(SurfaceRules.Context $$0) {
/* 745 */       return $$0.steep;
/*     */     } }
/*     */   private static final class BlockRuleSource extends Record implements RuleSource { private final BlockState resultState; private final SurfaceRules.StateRule rule;
/*     */     
/* 749 */     private BlockRuleSource(BlockState $$0, SurfaceRules.StateRule $$1) { this.resultState = $$0; this.rule = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/SurfaceRules$BlockRuleSource;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #749	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/SurfaceRules$BlockRuleSource; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/SurfaceRules$BlockRuleSource;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #749	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/SurfaceRules$BlockRuleSource; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/SurfaceRules$BlockRuleSource;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #749	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/levelgen/SurfaceRules$BlockRuleSource;
/* 749 */       //   0	8	1	$$0	Ljava/lang/Object; } public BlockState resultState() { return this.resultState; } public SurfaceRules.StateRule rule() { return this.rule; }
/* 750 */      static final KeyDispatchDataCodec<BlockRuleSource> CODEC = KeyDispatchDataCodec.of(BlockState.CODEC.xmap(BlockRuleSource::new, BlockRuleSource::resultState).fieldOf("result_state"));
/*     */     
/*     */     BlockRuleSource(BlockState $$0) {
/* 753 */       this($$0, new SurfaceRules.StateRule($$0));
/*     */     }
/*     */ 
/*     */     
/*     */     public KeyDispatchDataCodec<? extends SurfaceRules.RuleSource> codec() {
/* 758 */       return (KeyDispatchDataCodec)CODEC;
/*     */     }
/*     */ 
/*     */     
/*     */     public SurfaceRules.SurfaceRule apply(SurfaceRules.Context $$0) {
/* 763 */       return this.rule;
/*     */     } }
/*     */   private static final class TestRuleSource extends Record implements RuleSource { private final SurfaceRules.ConditionSource ifTrue; private final SurfaceRules.RuleSource thenRun; static final KeyDispatchDataCodec<TestRuleSource> CODEC;
/*     */     
/* 767 */     TestRuleSource(SurfaceRules.ConditionSource $$0, SurfaceRules.RuleSource $$1) { this.ifTrue = $$0; this.thenRun = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/SurfaceRules$TestRuleSource;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #767	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/SurfaceRules$TestRuleSource; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/SurfaceRules$TestRuleSource;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #767	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/SurfaceRules$TestRuleSource; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/SurfaceRules$TestRuleSource;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #767	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/levelgen/SurfaceRules$TestRuleSource;
/* 767 */       //   0	8	1	$$0	Ljava/lang/Object; } public SurfaceRules.ConditionSource ifTrue() { return this.ifTrue; } public SurfaceRules.RuleSource thenRun() { return this.thenRun; } static {
/* 768 */       CODEC = KeyDispatchDataCodec.of(RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)SurfaceRules.ConditionSource.CODEC.fieldOf("if_true").forGetter(TestRuleSource::ifTrue), (App)SurfaceRules.RuleSource.CODEC.fieldOf("then_run").forGetter(TestRuleSource::thenRun)).apply((Applicative)$$0, TestRuleSource::new)));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public KeyDispatchDataCodec<? extends SurfaceRules.RuleSource> codec() {
/* 775 */       return (KeyDispatchDataCodec)CODEC;
/*     */     }
/*     */ 
/*     */     
/*     */     public SurfaceRules.SurfaceRule apply(SurfaceRules.Context $$0) {
/* 780 */       return new SurfaceRules.TestRule(this.ifTrue.apply($$0), this.thenRun.apply($$0));
/*     */     } }
/*     */   private static final class SequenceRuleSource extends Record implements RuleSource { private final List<SurfaceRules.RuleSource> sequence;
/*     */     
/* 784 */     SequenceRuleSource(List<SurfaceRules.RuleSource> $$0) { this.sequence = $$0; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/SurfaceRules$SequenceRuleSource;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #784	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/SurfaceRules$SequenceRuleSource; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/SurfaceRules$SequenceRuleSource;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #784	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/SurfaceRules$SequenceRuleSource; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/SurfaceRules$SequenceRuleSource;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #784	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/levelgen/SurfaceRules$SequenceRuleSource;
/* 784 */       //   0	8	1	$$0	Ljava/lang/Object; } public List<SurfaceRules.RuleSource> sequence() { return this.sequence; }
/* 785 */      static final KeyDispatchDataCodec<SequenceRuleSource> CODEC = KeyDispatchDataCodec.of(SurfaceRules.RuleSource.CODEC.listOf().xmap(SequenceRuleSource::new, SequenceRuleSource::sequence).fieldOf("sequence"));
/*     */ 
/*     */     
/*     */     public KeyDispatchDataCodec<? extends SurfaceRules.RuleSource> codec() {
/* 789 */       return (KeyDispatchDataCodec)CODEC;
/*     */     }
/*     */ 
/*     */     
/*     */     public SurfaceRules.SurfaceRule apply(SurfaceRules.Context $$0) {
/* 794 */       if (this.sequence.size() == 1) {
/* 795 */         return ((SurfaceRules.RuleSource)this.sequence.get(0)).apply($$0);
/*     */       }
/*     */       
/* 798 */       ImmutableList.Builder<SurfaceRules.SurfaceRule> $$1 = ImmutableList.builder();
/* 799 */       for (SurfaceRules.RuleSource $$2 : this.sequence) {
/* 800 */         $$1.add($$2.apply($$0));
/*     */       }
/* 802 */       return new SurfaceRules.SequenceRule((List<SurfaceRules.SurfaceRule>)$$1.build());
/*     */     } }
/*     */ 
/*     */   
/*     */   private enum Bandlands implements RuleSource {
/* 807 */     INSTANCE;
/* 808 */     static final KeyDispatchDataCodec<Bandlands> CODEC = KeyDispatchDataCodec.of(MapCodec.unit(INSTANCE)); static {
/*     */     
/*     */     }
/*     */     public KeyDispatchDataCodec<? extends SurfaceRules.RuleSource> codec() {
/* 812 */       return (KeyDispatchDataCodec)CODEC;
/*     */     }
/*     */ 
/*     */     
/*     */     public SurfaceRules.SurfaceRule apply(SurfaceRules.Context $$0) {
/* 817 */       Objects.requireNonNull($$0.system); return $$0.system::getBand;
/*     */     }
/*     */   }
/*     */   
/*     */   protected static interface SurfaceRule {
/*     */     @Nullable
/*     */     BlockState tryApply(int param1Int1, int param1Int2, int param1Int3);
/*     */   }
/*     */   
/*     */   private static interface Condition {
/*     */     boolean test();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\SurfaceRules.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */