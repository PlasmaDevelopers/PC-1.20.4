/*    */ package net.minecraft.world.level.levelgen.feature;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.stream.Stream;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
/*    */ 
/*    */ public final class ConfiguredFeature<FC extends FeatureConfiguration, F extends Feature<FC>> extends Record {
/*    */   private final F feature;
/*    */   private final FC config;
/*    */   public static final Codec<ConfiguredFeature<?, ?>> DIRECT_CODEC;
/*    */   
/* 18 */   public ConfiguredFeature(F $$0, FC $$1) { this.feature = $$0; this.config = $$1; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/feature/ConfiguredFeature;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #18	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/feature/ConfiguredFeature;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/* 18 */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/feature/ConfiguredFeature<TFC;TF;>; } public F feature() { return this.feature; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/feature/ConfiguredFeature;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #18	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/levelgen/feature/ConfiguredFeature;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/* 18 */     //   0	8	0	this	Lnet/minecraft/world/level/levelgen/feature/ConfiguredFeature<TFC;TF;>; } public FC config() { return this.config; }
/*    */ 
/*    */   
/*    */   static {
/* 22 */     DIRECT_CODEC = BuiltInRegistries.FEATURE.byNameCodec().dispatch($$0 -> $$0.feature, Feature::configuredCodec);
/*    */   }
/* 24 */   public static final Codec<Holder<ConfiguredFeature<?, ?>>> CODEC = (Codec<Holder<ConfiguredFeature<?, ?>>>)RegistryFileCodec.create(Registries.CONFIGURED_FEATURE, DIRECT_CODEC);
/* 25 */   public static final Codec<HolderSet<ConfiguredFeature<?, ?>>> LIST_CODEC = RegistryCodecs.homogeneousList(Registries.CONFIGURED_FEATURE, DIRECT_CODEC);
/*    */   
/*    */   public boolean place(WorldGenLevel $$0, ChunkGenerator $$1, RandomSource $$2, BlockPos $$3) {
/* 28 */     return this.feature.place(this.config, $$0, $$1, $$2, $$3);
/*    */   }
/*    */   
/*    */   public Stream<ConfiguredFeature<?, ?>> getFeatures() {
/* 32 */     return Stream.concat(Stream.of(this), this.config.getFeatures());
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 37 */     return "Configured: " + this.feature + ": " + this.config;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\ConfiguredFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */