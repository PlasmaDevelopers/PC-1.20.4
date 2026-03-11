/*     */ package net.minecraft.world.level.levelgen.carver;
/*     */ 
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.mojang.serialization.Codec;
/*     */ import java.util.Set;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.biome.Biome;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.chunk.CarvingMask;
/*     */ import net.minecraft.world.level.chunk.ChunkAccess;
/*     */ import net.minecraft.world.level.levelgen.Aquifer;
/*     */ import net.minecraft.world.level.levelgen.DensityFunction;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ import org.apache.commons.lang3.mutable.MutableBoolean;
/*     */ 
/*     */ public abstract class WorldCarver<C extends CarverConfiguration>
/*     */ {
/*  33 */   public static final WorldCarver<CaveCarverConfiguration> CAVE = register("cave", new CaveWorldCarver(CaveCarverConfiguration.CODEC));
/*  34 */   public static final WorldCarver<CaveCarverConfiguration> NETHER_CAVE = register("nether_cave", new NetherWorldCarver(CaveCarverConfiguration.CODEC));
/*  35 */   public static final WorldCarver<CanyonCarverConfiguration> CANYON = register("canyon", new CanyonWorldCarver(CanyonCarverConfiguration.CODEC));
/*     */   
/*  37 */   protected static final BlockState AIR = Blocks.AIR.defaultBlockState();
/*  38 */   protected static final BlockState CAVE_AIR = Blocks.CAVE_AIR.defaultBlockState();
/*  39 */   protected static final FluidState WATER = Fluids.WATER.defaultFluidState();
/*  40 */   protected static final FluidState LAVA = Fluids.LAVA.defaultFluidState();
/*     */   
/*     */   private static <C extends CarverConfiguration, F extends WorldCarver<C>> F register(String $$0, F $$1) {
/*  43 */     return (F)Registry.register(BuiltInRegistries.CARVER, $$0, $$1);
/*     */   }
/*     */   
/*  46 */   protected Set<Fluid> liquids = (Set<Fluid>)ImmutableSet.of(Fluids.WATER);
/*     */ 
/*     */   
/*     */   private final Codec<ConfiguredWorldCarver<C>> configuredCodec;
/*     */ 
/*     */   
/*     */   public WorldCarver(Codec<C> $$0) {
/*  53 */     this.configuredCodec = $$0.fieldOf("config").xmap(this::configured, ConfiguredWorldCarver::config).codec();
/*     */   }
/*     */   
/*     */   public ConfiguredWorldCarver<C> configured(C $$0) {
/*  57 */     return new ConfiguredWorldCarver<>(this, $$0);
/*     */   }
/*     */   
/*     */   public Codec<ConfiguredWorldCarver<C>> configuredCodec() {
/*  61 */     return this.configuredCodec;
/*     */   }
/*     */   
/*     */   public int getRange() {
/*  65 */     return 4;
/*     */   }
/*     */   
/*     */   protected boolean carveEllipsoid(CarvingContext $$0, C $$1, ChunkAccess $$2, Function<BlockPos, Holder<Biome>> $$3, Aquifer $$4, double $$5, double $$6, double $$7, double $$8, double $$9, CarvingMask $$10, CarveSkipChecker $$11) {
/*  69 */     ChunkPos $$12 = $$2.getPos();
/*     */     
/*  71 */     double $$13 = $$12.getMiddleBlockX();
/*  72 */     double $$14 = $$12.getMiddleBlockZ();
/*     */     
/*  74 */     double $$15 = 16.0D + $$8 * 2.0D;
/*  75 */     if (Math.abs($$5 - $$13) > $$15 || Math.abs($$7 - $$14) > $$15) {
/*  76 */       return false;
/*     */     }
/*     */     
/*  79 */     int $$16 = $$12.getMinBlockX();
/*  80 */     int $$17 = $$12.getMinBlockZ();
/*     */ 
/*     */     
/*  83 */     int $$18 = Math.max(Mth.floor($$5 - $$8) - $$16 - 1, 0);
/*  84 */     int $$19 = Math.min(Mth.floor($$5 + $$8) - $$16, 15);
/*     */ 
/*     */     
/*  87 */     int $$20 = Math.max(Mth.floor($$6 - $$9) - 1, $$0.getMinGenY() + 1);
/*  88 */     int $$21 = $$2.isUpgrading() ? 0 : 7;
/*  89 */     int $$22 = Math.min(Mth.floor($$6 + $$9) + 1, $$0.getMinGenY() + $$0.getGenDepth() - 1 - $$21);
/*     */     
/*  91 */     int $$23 = Math.max(Mth.floor($$7 - $$8) - $$17 - 1, 0);
/*  92 */     int $$24 = Math.min(Mth.floor($$7 + $$8) - $$17, 15);
/*     */     
/*  94 */     boolean $$25 = false;
/*  95 */     BlockPos.MutableBlockPos $$26 = new BlockPos.MutableBlockPos();
/*  96 */     BlockPos.MutableBlockPos $$27 = new BlockPos.MutableBlockPos();
/*     */     
/*  98 */     for (int $$28 = $$18; $$28 <= $$19; $$28++) {
/*  99 */       int $$29 = $$12.getBlockX($$28);
/*     */ 
/*     */       
/* 102 */       double $$30 = ($$29 + 0.5D - $$5) / $$8;
/* 103 */       for (int $$31 = $$23; $$31 <= $$24; $$31++) {
/* 104 */         int $$32 = $$12.getBlockZ($$31);
/* 105 */         double $$33 = ($$32 + 0.5D - $$7) / $$8;
/* 106 */         if ($$30 * $$30 + $$33 * $$33 < 1.0D) {
/*     */ 
/*     */ 
/*     */           
/* 110 */           MutableBoolean $$34 = new MutableBoolean(false);
/*     */           
/* 112 */           for (int $$35 = $$22; $$35 > $$20; $$35--) {
/* 113 */             double $$36 = ($$35 - 0.5D - $$6) / $$9;
/* 114 */             if (!$$11.shouldSkip($$0, $$30, $$36, $$33, $$35))
/*     */             {
/*     */ 
/*     */               
/* 118 */               if (!$$10.get($$28, $$35, $$31) || isDebugEnabled((CarverConfiguration)$$1)) {
/* 119 */                 $$10.set($$28, $$35, $$31);
/*     */                 
/* 121 */                 $$26.set($$29, $$35, $$32);
/* 122 */                 $$25 |= carveBlock($$0, $$1, $$2, $$3, $$10, $$26, $$27, $$4, $$34);
/*     */               }  } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 128 */     return $$25;
/*     */   }
/*     */   
/*     */   protected boolean carveBlock(CarvingContext $$0, C $$1, ChunkAccess $$2, Function<BlockPos, Holder<Biome>> $$3, CarvingMask $$4, BlockPos.MutableBlockPos $$5, BlockPos.MutableBlockPos $$6, Aquifer $$7, MutableBoolean $$8) {
/* 132 */     BlockState $$9 = $$2.getBlockState((BlockPos)$$5);
/*     */ 
/*     */     
/* 135 */     if ($$9.is(Blocks.GRASS_BLOCK) || $$9.is(Blocks.MYCELIUM)) {
/* 136 */       $$8.setTrue();
/*     */     }
/* 138 */     if (!canReplaceBlock($$1, $$9) && !isDebugEnabled((CarverConfiguration)$$1)) {
/* 139 */       return false;
/*     */     }
/*     */     
/* 142 */     BlockState $$10 = getCarveState($$0, $$1, (BlockPos)$$5, $$7);
/* 143 */     if ($$10 == null) {
/* 144 */       return false;
/*     */     }
/* 146 */     $$2.setBlockState((BlockPos)$$5, $$10, false);
/* 147 */     if ($$7.shouldScheduleFluidUpdate() && !$$10.getFluidState().isEmpty())
/*     */     {
/* 149 */       $$2.markPosForPostprocessing((BlockPos)$$5);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 154 */     if ($$8.isTrue()) {
/* 155 */       $$6.setWithOffset((Vec3i)$$5, Direction.DOWN);
/* 156 */       if ($$2.getBlockState((BlockPos)$$6).is(Blocks.DIRT)) {
/* 157 */         $$0.topMaterial($$3, $$2, (BlockPos)$$6, !$$10.getFluidState().isEmpty()).ifPresent($$2 -> {
/*     */               $$0.setBlockState((BlockPos)$$1, $$2, false);
/*     */               
/*     */               if (!$$2.getFluidState().isEmpty()) {
/*     */                 $$0.markPosForPostprocessing((BlockPos)$$1);
/*     */               }
/*     */             });
/*     */       }
/*     */     } 
/* 166 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private BlockState getCarveState(CarvingContext $$0, C $$1, BlockPos $$2, Aquifer $$3) {
/* 175 */     if ($$2.getY() <= ((CarverConfiguration)$$1).lavaLevel.resolveY($$0))
/*     */     {
/* 177 */       return LAVA.createLegacyBlock();
/*     */     }
/*     */     
/* 180 */     BlockState $$4 = $$3.computeSubstance((DensityFunction.FunctionContext)new DensityFunction.SinglePointContext($$2.getX(), $$2.getY(), $$2.getZ()), 0.0D);
/* 181 */     if ($$4 == null)
/*     */     {
/* 183 */       return isDebugEnabled((CarverConfiguration)$$1) ? ((CarverConfiguration)$$1).debugSettings.getBarrierState() : null;
/*     */     }
/*     */     
/* 186 */     return isDebugEnabled((CarverConfiguration)$$1) ? getDebugState((CarverConfiguration)$$1, $$4) : $$4;
/*     */   }
/*     */   
/*     */   private static BlockState getDebugState(CarverConfiguration $$0, BlockState $$1) {
/* 190 */     if ($$1.is(Blocks.AIR))
/* 191 */       return $$0.debugSettings.getAirState(); 
/* 192 */     if ($$1.is(Blocks.WATER)) {
/* 193 */       BlockState $$2 = $$0.debugSettings.getWaterState();
/* 194 */       if ($$2.hasProperty((Property)BlockStateProperties.WATERLOGGED)) {
/* 195 */         return (BlockState)$$2.setValue((Property)BlockStateProperties.WATERLOGGED, Boolean.valueOf(true));
/*     */       }
/* 197 */       return $$2;
/* 198 */     }  if ($$1.is(Blocks.LAVA)) {
/* 199 */       return $$0.debugSettings.getLavaState();
/*     */     }
/* 201 */     return $$1;
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
/*     */   protected boolean canReplaceBlock(C $$0, BlockState $$1) {
/* 213 */     return $$1.is(((CarverConfiguration)$$0).replaceable);
/*     */   }
/*     */   
/*     */   protected static boolean canReach(ChunkPos $$0, double $$1, double $$2, int $$3, int $$4, float $$5) {
/* 217 */     double $$6 = $$0.getMiddleBlockX();
/* 218 */     double $$7 = $$0.getMiddleBlockZ();
/*     */     
/* 220 */     double $$8 = $$1 - $$6;
/* 221 */     double $$9 = $$2 - $$7;
/* 222 */     double $$10 = ($$4 - $$3);
/* 223 */     double $$11 = ($$5 + 2.0F + 16.0F);
/*     */     
/* 225 */     return ($$8 * $$8 + $$9 * $$9 - $$10 * $$10 <= $$11 * $$11);
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isDebugEnabled(CarverConfiguration $$0) {
/* 230 */     return $$0.debugSettings.isDebugMode();
/*     */   }
/*     */   
/*     */   public abstract boolean carve(CarvingContext paramCarvingContext, C paramC, ChunkAccess paramChunkAccess, Function<BlockPos, Holder<Biome>> paramFunction, RandomSource paramRandomSource, Aquifer paramAquifer, ChunkPos paramChunkPos, CarvingMask paramCarvingMask);
/*     */   
/*     */   public abstract boolean isStartChunk(C paramC, RandomSource paramRandomSource);
/*     */   
/*     */   public static interface CarveSkipChecker {
/*     */     boolean shouldSkip(CarvingContext param1CarvingContext, double param1Double1, double param1Double2, double param1Double3, int param1Int);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\carver\WorldCarver.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */