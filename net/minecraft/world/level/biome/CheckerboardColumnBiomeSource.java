/*    */ package net.minecraft.world.level.biome;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.HolderSet;
/*    */ 
/*    */ public class CheckerboardColumnBiomeSource extends BiomeSource {
/*    */   static {
/* 11 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Biome.LIST_CODEC.fieldOf("biomes").forGetter(()), (App)Codec.intRange(0, 62).fieldOf("scale").orElse(Integer.valueOf(2)).forGetter(())).apply((Applicative)$$0, CheckerboardColumnBiomeSource::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<CheckerboardColumnBiomeSource> CODEC;
/*    */   private final HolderSet<Biome> allowedBiomes;
/*    */   private final int bitShift;
/*    */   private final int size;
/*    */   
/*    */   public CheckerboardColumnBiomeSource(HolderSet<Biome> $$0, int $$1) {
/* 21 */     this.allowedBiomes = $$0;
/* 22 */     this.bitShift = $$1 + 2;
/* 23 */     this.size = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Stream<Holder<Biome>> collectPossibleBiomes() {
/* 28 */     return this.allowedBiomes.stream();
/*    */   }
/*    */ 
/*    */   
/*    */   protected Codec<? extends BiomeSource> codec() {
/* 33 */     return (Codec)CODEC;
/*    */   }
/*    */ 
/*    */   
/*    */   public Holder<Biome> getNoiseBiome(int $$0, int $$1, int $$2, Climate.Sampler $$3) {
/* 38 */     return this.allowedBiomes.get(Math.floorMod(($$0 >> this.bitShift) + ($$2 >> this.bitShift), this.allowedBiomes.size()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\biome\CheckerboardColumnBiomeSource.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */