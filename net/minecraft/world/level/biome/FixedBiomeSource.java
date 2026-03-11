/*    */ package net.minecraft.world.level.biome;
/*    */ import com.google.common.collect.Sets;
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.Set;
/*    */ import java.util.function.Predicate;
/*    */ import java.util.stream.Stream;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.util.RandomSource;
/*    */ 
/*    */ public class FixedBiomeSource extends BiomeSource implements BiomeManager.NoiseBiomeSource {
/*    */   public static final Codec<FixedBiomeSource> CODEC;
/*    */   
/*    */   static {
/* 17 */     CODEC = Biome.CODEC.fieldOf("biome").xmap(FixedBiomeSource::new, $$0 -> $$0.biome).stable().codec();
/*    */   }
/*    */   private final Holder<Biome> biome;
/*    */   
/*    */   public FixedBiomeSource(Holder<Biome> $$0) {
/* 22 */     this.biome = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Stream<Holder<Biome>> collectPossibleBiomes() {
/* 27 */     return Stream.of(this.biome);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Codec<? extends BiomeSource> codec() {
/* 32 */     return (Codec)CODEC;
/*    */   }
/*    */ 
/*    */   
/*    */   public Holder<Biome> getNoiseBiome(int $$0, int $$1, int $$2, Climate.Sampler $$3) {
/* 37 */     return this.biome;
/*    */   }
/*    */ 
/*    */   
/*    */   public Holder<Biome> getNoiseBiome(int $$0, int $$1, int $$2) {
/* 42 */     return this.biome;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Pair<BlockPos, Holder<Biome>> findBiomeHorizontal(int $$0, int $$1, int $$2, int $$3, int $$4, Predicate<Holder<Biome>> $$5, RandomSource $$6, boolean $$7, Climate.Sampler $$8) {
/* 48 */     if ($$5.test(this.biome)) {
/* 49 */       if ($$7) {
/* 50 */         return Pair.of(new BlockPos($$0, $$1, $$2), this.biome);
/*    */       }
/* 52 */       return Pair.of(new BlockPos($$0 - $$3 + $$6.nextInt($$3 * 2 + 1), $$1, $$2 - $$3 + $$6.nextInt($$3 * 2 + 1)), this.biome);
/*    */     } 
/*    */     
/* 55 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Pair<BlockPos, Holder<Biome>> findClosestBiome3d(BlockPos $$0, int $$1, int $$2, int $$3, Predicate<Holder<Biome>> $$4, Climate.Sampler $$5, LevelReader $$6) {
/* 61 */     return $$4.test(this.biome) ? Pair.of($$0, this.biome) : null;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<Holder<Biome>> getBiomesWithin(int $$0, int $$1, int $$2, int $$3, Climate.Sampler $$4) {
/* 66 */     return Sets.newHashSet(Set.of(this.biome));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\biome\FixedBiomeSource.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */