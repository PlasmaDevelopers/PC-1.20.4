/*     */ package net.minecraft.world.level.biome;
/*     */ 
/*     */ import com.google.common.base.Suppliers;
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.serialization.Codec;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.SharedConstants;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.QuartPos;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ 
/*     */ public abstract class BiomeSource
/*     */   implements BiomeResolver {
/*  28 */   public static final Codec<BiomeSource> CODEC = BuiltInRegistries.BIOME_SOURCE.byNameCodec().dispatchStable(BiomeSource::codec, Function.identity());
/*     */ 
/*     */   
/*  31 */   private final Supplier<Set<Holder<Biome>>> possibleBiomes = (Supplier<Set<Holder<Biome>>>)Suppliers.memoize(() -> (Set)collectPossibleBiomes().distinct().collect(ImmutableSet.toImmutableSet()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<Holder<Biome>> possibleBiomes() {
/*  41 */     return this.possibleBiomes.get();
/*     */   }
/*     */   
/*     */   public Set<Holder<Biome>> getBiomesWithin(int $$0, int $$1, int $$2, int $$3, Climate.Sampler $$4) {
/*  45 */     int $$5 = QuartPos.fromBlock($$0 - $$3);
/*  46 */     int $$6 = QuartPos.fromBlock($$1 - $$3);
/*  47 */     int $$7 = QuartPos.fromBlock($$2 - $$3);
/*  48 */     int $$8 = QuartPos.fromBlock($$0 + $$3);
/*  49 */     int $$9 = QuartPos.fromBlock($$1 + $$3);
/*  50 */     int $$10 = QuartPos.fromBlock($$2 + $$3);
/*     */     
/*  52 */     int $$11 = $$8 - $$5 + 1;
/*  53 */     int $$12 = $$9 - $$6 + 1;
/*  54 */     int $$13 = $$10 - $$7 + 1;
/*     */     
/*  56 */     Set<Holder<Biome>> $$14 = Sets.newHashSet();
/*     */     
/*  58 */     for (int $$15 = 0; $$15 < $$13; $$15++) {
/*  59 */       for (int $$16 = 0; $$16 < $$11; $$16++) {
/*  60 */         for (int $$17 = 0; $$17 < $$12; $$17++) {
/*  61 */           int $$18 = $$5 + $$16;
/*  62 */           int $$19 = $$6 + $$17;
/*  63 */           int $$20 = $$7 + $$15;
/*  64 */           $$14.add(getNoiseBiome($$18, $$19, $$20, $$4));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  69 */     return $$14;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Pair<BlockPos, Holder<Biome>> findBiomeHorizontal(int $$0, int $$1, int $$2, int $$3, Predicate<Holder<Biome>> $$4, RandomSource $$5, Climate.Sampler $$6) {
/*  74 */     return findBiomeHorizontal($$0, $$1, $$2, $$3, 1, $$4, $$5, false, $$6);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Pair<BlockPos, Holder<Biome>> findClosestBiome3d(BlockPos $$0, int $$1, int $$2, int $$3, Predicate<Holder<Biome>> $$4, Climate.Sampler $$5, LevelReader $$6) {
/*  81 */     Set<Holder<Biome>> $$7 = (Set<Holder<Biome>>)possibleBiomes().stream().filter($$4).collect(Collectors.toUnmodifiableSet());
/*     */     
/*  83 */     if ($$7.isEmpty()) {
/*  84 */       return null;
/*     */     }
/*     */     
/*  87 */     int $$8 = Math.floorDiv($$1, $$2);
/*  88 */     int[] $$9 = Mth.outFromOrigin($$0.getY(), $$6.getMinBuildHeight() + 1, $$6.getMaxBuildHeight(), $$3).toArray();
/*     */     
/*  90 */     for (BlockPos.MutableBlockPos $$10 : BlockPos.spiralAround(BlockPos.ZERO, $$8, Direction.EAST, Direction.SOUTH)) {
/*  91 */       int $$11 = $$0.getX() + $$10.getX() * $$2;
/*  92 */       int $$12 = $$0.getZ() + $$10.getZ() * $$2;
/*  93 */       int $$13 = QuartPos.fromBlock($$11);
/*  94 */       int $$14 = QuartPos.fromBlock($$12);
/*  95 */       for (int $$15 : $$9) {
/*  96 */         int $$16 = QuartPos.fromBlock($$15);
/*  97 */         Holder<Biome> $$17 = getNoiseBiome($$13, $$16, $$14, $$5);
/*  98 */         if ($$7.contains($$17)) {
/*  99 */           return Pair.of(new BlockPos($$11, $$15, $$12), $$17);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 104 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Pair<BlockPos, Holder<Biome>> findBiomeHorizontal(int $$0, int $$1, int $$2, int $$3, int $$4, Predicate<Holder<Biome>> $$5, RandomSource $$6, boolean $$7, Climate.Sampler $$8) {
/* 116 */     int $$9 = QuartPos.fromBlock($$0);
/* 117 */     int $$10 = QuartPos.fromBlock($$2);
/* 118 */     int $$11 = QuartPos.fromBlock($$3);
/*     */     
/* 120 */     int $$12 = QuartPos.fromBlock($$1);
/*     */     
/* 122 */     Pair<BlockPos, Holder<Biome>> $$13 = null;
/* 123 */     int $$14 = 0;
/*     */     
/* 125 */     int $$15 = $$7 ? 0 : $$11; int $$16;
/* 126 */     for ($$16 = $$15; $$16 <= $$11; $$16 += $$4) {
/* 127 */       int $$17; for ($$17 = SharedConstants.debugGenerateSquareTerrainWithoutNoise ? 0 : -$$16; $$17 <= $$16; $$17 += $$4) {
/* 128 */         boolean $$18 = (Math.abs($$17) == $$16); int $$19;
/* 129 */         for ($$19 = -$$16; $$19 <= $$16; $$19 += $$4) {
/* 130 */           if ($$7) {
/*     */             
/* 132 */             boolean $$20 = (Math.abs($$19) == $$16);
/* 133 */             if (!$$20 && !$$18) {
/*     */               continue;
/*     */             }
/*     */           } 
/*     */           
/* 138 */           int $$21 = $$9 + $$19;
/* 139 */           int $$22 = $$10 + $$17;
/* 140 */           Holder<Biome> $$23 = getNoiseBiome($$21, $$12, $$22, $$8);
/* 141 */           if ($$5.test($$23)) {
/* 142 */             if ($$13 == null || $$6.nextInt($$14 + 1) == 0) {
/* 143 */               BlockPos $$24 = new BlockPos(QuartPos.toBlock($$21), $$1, QuartPos.toBlock($$22));
/* 144 */               if ($$7) {
/* 145 */                 return Pair.of($$24, $$23);
/*     */               }
/* 147 */               $$13 = Pair.of($$24, $$23);
/*     */             } 
/* 149 */             $$14++;
/*     */           } 
/*     */           continue;
/*     */         } 
/*     */       } 
/*     */     } 
/* 155 */     return $$13;
/*     */   }
/*     */   
/*     */   public void addDebugInfo(List<String> $$0, BlockPos $$1, Climate.Sampler $$2) {}
/*     */   
/*     */   protected abstract Codec<? extends BiomeSource> codec();
/*     */   
/*     */   protected abstract Stream<Holder<Biome>> collectPossibleBiomes();
/*     */   
/*     */   public abstract Holder<Biome> getNoiseBiome(int paramInt1, int paramInt2, int paramInt3, Climate.Sampler paramSampler);
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\biome\BiomeSource.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */