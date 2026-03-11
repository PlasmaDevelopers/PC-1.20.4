/*    */ package net.minecraft.world.level.biome;
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.DataResult;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Optional;
/*    */ import java.util.function.Function;
/*    */ import java.util.stream.Collectors;
/*    */ import java.util.stream.Stream;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.HolderGetter;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.resources.RegistryOps;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ public class MultiNoiseBiomeSourceParameterList {
/*    */   public static final Codec<MultiNoiseBiomeSourceParameterList> DIRECT_CODEC;
/*    */   
/*    */   static {
/* 26 */     DIRECT_CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Preset.CODEC.fieldOf("preset").forGetter(()), (App)RegistryOps.retrieveGetter(Registries.BIOME)).apply((Applicative)$$0, MultiNoiseBiomeSourceParameterList::new));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 31 */   public static final Codec<Holder<MultiNoiseBiomeSourceParameterList>> CODEC = (Codec<Holder<MultiNoiseBiomeSourceParameterList>>)RegistryFileCodec.create(Registries.MULTI_NOISE_BIOME_SOURCE_PARAMETER_LIST, DIRECT_CODEC);
/*    */   
/*    */   private final Preset preset;
/*    */   
/*    */   private final Climate.ParameterList<Holder<Biome>> parameters;
/*    */   
/*    */   public MultiNoiseBiomeSourceParameterList(Preset $$0, HolderGetter<Biome> $$1) {
/* 38 */     this.preset = $$0;
/* 39 */     Objects.requireNonNull($$1); this.parameters = $$0.provider.apply($$1::getOrThrow);
/*    */   }
/*    */   
/*    */   public Climate.ParameterList<Holder<Biome>> parameters() {
/* 43 */     return this.parameters;
/*    */   }
/*    */   
/*    */   public static Map<Preset, Climate.ParameterList<ResourceKey<Biome>>> knownPresets() {
/* 47 */     return (Map<Preset, Climate.ParameterList<ResourceKey<Biome>>>)Preset.BY_NAME.values().stream().collect(Collectors.toMap($$0 -> $$0, $$0 -> $$0.provider().apply(())));
/*    */   } public static final class Preset extends Record {
/*    */     private final ResourceLocation id; final SourceProvider provider; public final String toString() {
/*    */       // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/biome/MultiNoiseBiomeSourceParameterList$Preset;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #53	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/world/level/biome/MultiNoiseBiomeSourceParameterList$Preset;
/*    */     } public final int hashCode() {
/*    */       // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/biome/MultiNoiseBiomeSourceParameterList$Preset;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #53	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/world/level/biome/MultiNoiseBiomeSourceParameterList$Preset;
/* 53 */     } public Preset(ResourceLocation $$0, SourceProvider $$1) { this.id = $$0; this.provider = $$1; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/biome/MultiNoiseBiomeSourceParameterList$Preset;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #53	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/world/level/biome/MultiNoiseBiomeSourceParameterList$Preset;
/* 53 */       //   0	8	1	$$0	Ljava/lang/Object; } public ResourceLocation id() { return this.id; } public SourceProvider provider() { return this.provider; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 59 */     public static final Preset NETHER = new Preset(new ResourceLocation("nether"), new SourceProvider()
/*    */         {
/*    */           public <T> Climate.ParameterList<T> apply(Function<ResourceKey<Biome>, T> $$0) {
/* 62 */             return new Climate.ParameterList<>(List.of(
/* 63 */                   Pair.of(Climate.parameters(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F), $$0.apply(Biomes.NETHER_WASTES)), 
/* 64 */                   Pair.of(Climate.parameters(0.0F, -0.5F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F), $$0.apply(Biomes.SOUL_SAND_VALLEY)), 
/* 65 */                   Pair.of(Climate.parameters(0.4F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F), $$0.apply(Biomes.CRIMSON_FOREST)), 
/* 66 */                   Pair.of(Climate.parameters(0.0F, 0.5F, 0.0F, 0.0F, 0.0F, 0.0F, 0.375F), $$0.apply(Biomes.WARPED_FOREST)), 
/* 67 */                   Pair.of(Climate.parameters(-0.5F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.175F), $$0.apply(Biomes.BASALT_DELTAS))));
/*    */           }
/*    */         }); @FunctionalInterface
/*    */     private static interface SourceProvider {
/*    */       <T> Climate.ParameterList<T> apply(Function<ResourceKey<Biome>, T> param2Function); }
/* 72 */     public static final Preset OVERWORLD = new Preset(new ResourceLocation("overworld"), new SourceProvider()
/*    */         {
/*    */           public <T> Climate.ParameterList<T> apply(Function<ResourceKey<Biome>, T> $$0) {
/* 75 */             return MultiNoiseBiomeSourceParameterList.Preset.generateOverworldBiomes($$0);
/*    */           }
/*    */         });
/*    */     static final Map<ResourceLocation, Preset> BY_NAME; public static final Codec<Preset> CODEC;
/*    */     static {
/* 80 */       BY_NAME = (Map<ResourceLocation, Preset>)Stream.<Preset>of(new Preset[] { NETHER, OVERWORLD }).collect(Collectors.toMap(Preset::id, $$0 -> $$0));
/*    */       
/* 82 */       CODEC = ResourceLocation.CODEC.flatXmap($$0 -> (DataResult)Optional.<Preset>ofNullable(BY_NAME.get($$0)).map(DataResult::success).orElseGet(()), $$0 -> DataResult.success($$0.id));
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     static <T> Climate.ParameterList<T> generateOverworldBiomes(Function<ResourceKey<Biome>, T> $$0) {
/* 88 */       ImmutableList.Builder<Pair<Climate.ParameterPoint, T>> $$1 = ImmutableList.builder();
/* 89 */       (new OverworldBiomeBuilder()).addBiomes($$2 -> $$0.add($$2.mapSecond($$1)));
/* 90 */       return new Climate.ParameterList<>((List<Pair<Climate.ParameterPoint, T>>)$$1.build());
/*    */     }
/*    */     
/*    */     public Stream<ResourceKey<Biome>> usedBiomes() {
/* 94 */       return this.provider.apply($$0 -> $$0).values().stream().map(Pair::getSecond).distinct();
/*    */     }
/*    */   }
/*    */   
/*    */   class null implements Preset.SourceProvider {
/*    */     public <T> Climate.ParameterList<T> apply(Function<ResourceKey<Biome>, T> $$0) {
/*    */       return new Climate.ParameterList<>(List.of(Pair.of(Climate.parameters(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F), $$0.apply(Biomes.NETHER_WASTES)), Pair.of(Climate.parameters(0.0F, -0.5F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F), $$0.apply(Biomes.SOUL_SAND_VALLEY)), Pair.of(Climate.parameters(0.4F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F), $$0.apply(Biomes.CRIMSON_FOREST)), Pair.of(Climate.parameters(0.0F, 0.5F, 0.0F, 0.0F, 0.0F, 0.0F, 0.375F), $$0.apply(Biomes.WARPED_FOREST)), Pair.of(Climate.parameters(-0.5F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.175F), $$0.apply(Biomes.BASALT_DELTAS))));
/*    */     }
/*    */   }
/*    */   
/*    */   class null implements Preset.SourceProvider {
/*    */     public <T> Climate.ParameterList<T> apply(Function<ResourceKey<Biome>, T> $$0) {
/*    */       return MultiNoiseBiomeSourceParameterList.Preset.generateOverworldBiomes($$0);
/*    */     }
/*    */   }
/*    */   
/*    */   @FunctionalInterface
/*    */   private static interface SourceProvider {
/*    */     <T> Climate.ParameterList<T> apply(Function<ResourceKey<Biome>, T> param1Function);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\biome\MultiNoiseBiomeSourceParameterList.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */