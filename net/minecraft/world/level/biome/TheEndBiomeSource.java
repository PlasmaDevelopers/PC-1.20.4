/*    */ package net.minecraft.world.level.biome;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.util.Function5;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.stream.Stream;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.HolderGetter;
/*    */ import net.minecraft.core.QuartPos;
/*    */ import net.minecraft.core.SectionPos;
/*    */ import net.minecraft.resources.RegistryOps;
/*    */ import net.minecraft.world.level.levelgen.DensityFunction;
/*    */ 
/*    */ public class TheEndBiomeSource extends BiomeSource {
/*    */   static {
/* 16 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)RegistryOps.retrieveElement(Biomes.THE_END), (App)RegistryOps.retrieveElement(Biomes.END_HIGHLANDS), (App)RegistryOps.retrieveElement(Biomes.END_MIDLANDS), (App)RegistryOps.retrieveElement(Biomes.SMALL_END_ISLANDS), (App)RegistryOps.retrieveElement(Biomes.END_BARRENS)).apply((Applicative)$$0, $$0.stable(TheEndBiomeSource::new)));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<TheEndBiomeSource> CODEC;
/*    */   
/*    */   private final Holder<Biome> end;
/*    */   
/*    */   private final Holder<Biome> highlands;
/*    */   
/*    */   private final Holder<Biome> midlands;
/*    */   private final Holder<Biome> islands;
/*    */   private final Holder<Biome> barrens;
/*    */   
/*    */   public static TheEndBiomeSource create(HolderGetter<Biome> $$0) {
/* 31 */     return new TheEndBiomeSource((Holder<Biome>)$$0
/* 32 */         .getOrThrow(Biomes.THE_END), (Holder<Biome>)$$0
/* 33 */         .getOrThrow(Biomes.END_HIGHLANDS), (Holder<Biome>)$$0
/* 34 */         .getOrThrow(Biomes.END_MIDLANDS), (Holder<Biome>)$$0
/* 35 */         .getOrThrow(Biomes.SMALL_END_ISLANDS), (Holder<Biome>)$$0
/* 36 */         .getOrThrow(Biomes.END_BARRENS));
/*    */   }
/*    */ 
/*    */   
/*    */   private TheEndBiomeSource(Holder<Biome> $$0, Holder<Biome> $$1, Holder<Biome> $$2, Holder<Biome> $$3, Holder<Biome> $$4) {
/* 41 */     this.end = $$0;
/* 42 */     this.highlands = $$1;
/* 43 */     this.midlands = $$2;
/* 44 */     this.islands = $$3;
/* 45 */     this.barrens = $$4;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Stream<Holder<Biome>> collectPossibleBiomes() {
/* 50 */     return Stream.of((Holder<Biome>[])new Holder[] { this.end, this.highlands, this.midlands, this.islands, this.barrens });
/*    */   }
/*    */ 
/*    */   
/*    */   protected Codec<? extends BiomeSource> codec() {
/* 55 */     return (Codec)CODEC;
/*    */   }
/*    */ 
/*    */   
/*    */   public Holder<Biome> getNoiseBiome(int $$0, int $$1, int $$2, Climate.Sampler $$3) {
/* 60 */     int $$4 = QuartPos.toBlock($$0);
/* 61 */     int $$5 = QuartPos.toBlock($$1);
/* 62 */     int $$6 = QuartPos.toBlock($$2);
/*    */     
/* 64 */     int $$7 = SectionPos.blockToSectionCoord($$4);
/* 65 */     int $$8 = SectionPos.blockToSectionCoord($$6);
/*    */     
/* 67 */     if ($$7 * $$7 + $$8 * $$8 <= 4096L) {
/* 68 */       return this.end;
/*    */     }
/*    */     
/* 71 */     int $$9 = (SectionPos.blockToSectionCoord($$4) * 2 + 1) * 8;
/* 72 */     int $$10 = (SectionPos.blockToSectionCoord($$6) * 2 + 1) * 8;
/*    */     
/* 74 */     double $$11 = $$3.erosion().compute((DensityFunction.FunctionContext)new DensityFunction.SinglePointContext($$9, $$5, $$10));
/* 75 */     if ($$11 > 0.25D) {
/* 76 */       return this.highlands;
/*    */     }
/*    */     
/* 79 */     if ($$11 >= -0.0625D) {
/* 80 */       return this.midlands;
/*    */     }
/*    */     
/* 83 */     if ($$11 < -0.21875D) {
/* 84 */       return this.islands;
/*    */     }
/*    */     
/* 87 */     return this.barrens;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\biome\TheEndBiomeSource.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */