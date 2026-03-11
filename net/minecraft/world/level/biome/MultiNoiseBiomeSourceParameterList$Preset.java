/*    */ package net.minecraft.world.level.biome;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.DataResult;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Optional;
/*    */ import java.util.function.Function;
/*    */ import java.util.stream.Collectors;
/*    */ import java.util.stream.Stream;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Preset
/*    */   extends Record
/*    */ {
/*    */   private final ResourceLocation id;
/*    */   final SourceProvider provider;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/biome/MultiNoiseBiomeSourceParameterList$Preset;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #53	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/biome/MultiNoiseBiomeSourceParameterList$Preset;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/biome/MultiNoiseBiomeSourceParameterList$Preset;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #53	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/biome/MultiNoiseBiomeSourceParameterList$Preset;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/biome/MultiNoiseBiomeSourceParameterList$Preset;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #53	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/biome/MultiNoiseBiomeSourceParameterList$Preset;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   public Preset(ResourceLocation $$0, SourceProvider $$1) {
/* 53 */     this.id = $$0; this.provider = $$1; } public ResourceLocation id() { return this.id; } public SourceProvider provider() { return this.provider; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 59 */   public static final Preset NETHER = new Preset(new ResourceLocation("nether"), new SourceProvider()
/*    */       {
/*    */         public <T> Climate.ParameterList<T> apply(Function<ResourceKey<Biome>, T> $$0) {
/* 62 */           return new Climate.ParameterList<>(List.of(
/* 63 */                 Pair.of(Climate.parameters(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F), $$0.apply(Biomes.NETHER_WASTES)), 
/* 64 */                 Pair.of(Climate.parameters(0.0F, -0.5F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F), $$0.apply(Biomes.SOUL_SAND_VALLEY)), 
/* 65 */                 Pair.of(Climate.parameters(0.4F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F), $$0.apply(Biomes.CRIMSON_FOREST)), 
/* 66 */                 Pair.of(Climate.parameters(0.0F, 0.5F, 0.0F, 0.0F, 0.0F, 0.0F, 0.375F), $$0.apply(Biomes.WARPED_FOREST)), 
/* 67 */                 Pair.of(Climate.parameters(-0.5F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.175F), $$0.apply(Biomes.BASALT_DELTAS))));
/*    */         }
/*    */       }); @FunctionalInterface
/*    */   private static interface SourceProvider {
/*    */     <T> Climate.ParameterList<T> apply(Function<ResourceKey<Biome>, T> param2Function); }
/* 72 */   public static final Preset OVERWORLD = new Preset(new ResourceLocation("overworld"), new SourceProvider()
/*    */       {
/*    */         public <T> Climate.ParameterList<T> apply(Function<ResourceKey<Biome>, T> $$0) {
/* 75 */           return MultiNoiseBiomeSourceParameterList.Preset.generateOverworldBiomes($$0);
/*    */         }
/*    */       });
/*    */   static final Map<ResourceLocation, Preset> BY_NAME; public static final Codec<Preset> CODEC;
/*    */   static {
/* 80 */     BY_NAME = (Map<ResourceLocation, Preset>)Stream.<Preset>of(new Preset[] { NETHER, OVERWORLD }).collect(Collectors.toMap(Preset::id, $$0 -> $$0));
/*    */     
/* 82 */     CODEC = ResourceLocation.CODEC.flatXmap($$0 -> (DataResult)Optional.<Preset>ofNullable(BY_NAME.get($$0)).map(DataResult::success).orElseGet(()), $$0 -> DataResult.success($$0.id));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   static <T> Climate.ParameterList<T> generateOverworldBiomes(Function<ResourceKey<Biome>, T> $$0) {
/* 88 */     ImmutableList.Builder<Pair<Climate.ParameterPoint, T>> $$1 = ImmutableList.builder();
/* 89 */     (new OverworldBiomeBuilder()).addBiomes($$2 -> $$0.add($$2.mapSecond($$1)));
/* 90 */     return new Climate.ParameterList<>((List<Pair<Climate.ParameterPoint, T>>)$$1.build());
/*    */   }
/*    */   
/*    */   public Stream<ResourceKey<Biome>> usedBiomes() {
/* 94 */     return this.provider.apply($$0 -> $$0).values().stream().map(Pair::getSecond).distinct();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\biome\MultiNoiseBiomeSourceParameterList$Preset.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */