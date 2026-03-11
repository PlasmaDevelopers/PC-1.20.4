/*     */ package net.minecraft.resources;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonParser;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import com.mojang.serialization.Decoder;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import com.mojang.serialization.JsonOps;
/*     */ import com.mojang.serialization.Lifecycle;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.Reader;
/*     */ import java.io.StringWriter;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.stream.Collectors;
/*     */ import net.minecraft.core.HolderGetter;
/*     */ import net.minecraft.core.HolderOwner;
/*     */ import net.minecraft.core.MappedRegistry;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.core.RegistryAccess;
/*     */ import net.minecraft.core.WritableRegistry;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.network.chat.ChatType;
/*     */ import net.minecraft.server.packs.resources.Resource;
/*     */ import net.minecraft.server.packs.resources.ResourceManager;
/*     */ import net.minecraft.world.damagesource.DamageType;
/*     */ import net.minecraft.world.item.armortrim.TrimMaterial;
/*     */ import net.minecraft.world.item.armortrim.TrimPattern;
/*     */ import net.minecraft.world.level.biome.Biome;
/*     */ import net.minecraft.world.level.dimension.DimensionType;
/*     */ import net.minecraft.world.level.dimension.LevelStem;
/*     */ import net.minecraft.world.level.levelgen.DensityFunction;
/*     */ import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
/*     */ import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
/*     */ import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
/*     */ import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorPreset;
/*     */ import net.minecraft.world.level.levelgen.placement.PlacedFeature;
/*     */ import net.minecraft.world.level.levelgen.presets.WorldPreset;
/*     */ import net.minecraft.world.level.levelgen.structure.Structure;
/*     */ import net.minecraft.world.level.levelgen.structure.StructureSet;
/*     */ import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
/*     */ import net.minecraft.world.level.levelgen.synth.NormalNoise;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class RegistryDataLoader {
/*  53 */   private static final Logger LOGGER = LogUtils.getLogger(); public static final class RegistryData<T> extends Record {
/*     */     private final ResourceKey<? extends Registry<T>> key; private final Codec<T> elementCodec; public final String toString() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/resources/RegistryDataLoader$RegistryData;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #59	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/resources/RegistryDataLoader$RegistryData;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	7	0	this	Lnet/minecraft/resources/RegistryDataLoader$RegistryData<TT;>;
/*     */     } public final int hashCode() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/resources/RegistryDataLoader$RegistryData;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #59	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/resources/RegistryDataLoader$RegistryData;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	7	0	this	Lnet/minecraft/resources/RegistryDataLoader$RegistryData<TT;>;
/*     */     }
/*  59 */     public RegistryData(ResourceKey<? extends Registry<T>> $$0, Codec<T> $$1) { this.key = $$0; this.elementCodec = $$1; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/resources/RegistryDataLoader$RegistryData;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #59	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/resources/RegistryDataLoader$RegistryData;
/*     */       //   0	8	1	$$0	Ljava/lang/Object;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*  59 */       //   0	8	0	this	Lnet/minecraft/resources/RegistryDataLoader$RegistryData<TT;>; } public ResourceKey<? extends Registry<T>> key() { return this.key; } public Codec<T> elementCodec() { return this.elementCodec; }
/*     */      Pair<WritableRegistry<?>, RegistryDataLoader.Loader> create(Lifecycle $$0, Map<ResourceKey<?>, Exception> $$1) {
/*  61 */       MappedRegistry mappedRegistry = new MappedRegistry(this.key, $$0);
/*  62 */       RegistryDataLoader.Loader $$3 = ($$2, $$3) -> RegistryDataLoader.loadRegistryContents($$3, $$2, this.key, $$0, (Decoder<T>)this.elementCodec, $$1);
/*  63 */       return Pair.of(mappedRegistry, $$3);
/*     */     }
/*     */     
/*     */     public void runWithArguments(BiConsumer<ResourceKey<? extends Registry<T>>, Codec<T>> $$0) {
/*  67 */       $$0.accept(this.key, this.elementCodec);
/*     */     }
/*     */   }
/*     */   
/*  71 */   public static final List<RegistryData<?>> WORLDGEN_REGISTRIES = List.of((RegistryData<?>[])new RegistryData[] { new RegistryData(Registries.DIMENSION_TYPE, DimensionType.DIRECT_CODEC), new RegistryData(Registries.BIOME, Biome.DIRECT_CODEC), new RegistryData(Registries.CHAT_TYPE, ChatType.CODEC), new RegistryData(Registries.CONFIGURED_CARVER, ConfiguredWorldCarver.DIRECT_CODEC), new RegistryData(Registries.CONFIGURED_FEATURE, ConfiguredFeature.DIRECT_CODEC), new RegistryData(Registries.PLACED_FEATURE, PlacedFeature.DIRECT_CODEC), new RegistryData(Registries.STRUCTURE, Structure.DIRECT_CODEC), new RegistryData(Registries.STRUCTURE_SET, StructureSet.DIRECT_CODEC), new RegistryData(Registries.PROCESSOR_LIST, StructureProcessorType.DIRECT_CODEC), new RegistryData(Registries.TEMPLATE_POOL, StructureTemplatePool.DIRECT_CODEC), new RegistryData(Registries.NOISE_SETTINGS, NoiseGeneratorSettings.DIRECT_CODEC), new RegistryData(Registries.NOISE, NormalNoise.NoiseParameters.DIRECT_CODEC), new RegistryData(Registries.DENSITY_FUNCTION, DensityFunction.DIRECT_CODEC), new RegistryData(Registries.WORLD_PRESET, WorldPreset.DIRECT_CODEC), new RegistryData(Registries.FLAT_LEVEL_GENERATOR_PRESET, FlatLevelGeneratorPreset.DIRECT_CODEC), new RegistryData(Registries.TRIM_PATTERN, TrimPattern.DIRECT_CODEC), new RegistryData(Registries.TRIM_MATERIAL, TrimMaterial.DIRECT_CODEC), new RegistryData(Registries.DAMAGE_TYPE, DamageType.CODEC), new RegistryData(Registries.MULTI_NOISE_BIOME_SOURCE_PARAMETER_LIST, MultiNoiseBiomeSourceParameterList.DIRECT_CODEC) });
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
/*  93 */   public static final List<RegistryData<?>> DIMENSION_REGISTRIES = List.of(new RegistryData(Registries.LEVEL_STEM, LevelStem.CODEC));
/*     */ 
/*     */ 
/*     */   
/*     */   public static RegistryAccess.Frozen load(ResourceManager $$0, RegistryAccess $$1, List<RegistryData<?>> $$2) {
/*  98 */     Map<ResourceKey<?>, Exception> $$3 = new HashMap<>();
/*     */     
/* 100 */     List<Pair<WritableRegistry<?>, Loader>> $$4 = $$2.stream().map($$1 -> $$1.create(Lifecycle.stable(), $$0)).toList();
/* 101 */     RegistryOps.RegistryInfoLookup $$5 = createContext($$1, $$4);
/*     */     
/* 103 */     $$4.forEach($$2 -> ((Loader)$$2.getSecond()).load($$0, $$1));
/*     */     
/* 105 */     $$4.forEach($$1 -> {
/*     */           Registry<?> $$2 = (Registry)$$1.getFirst();
/*     */           try {
/*     */             $$2.freeze();
/* 109 */           } catch (Exception $$3) {
/*     */             $$0.put($$2.key(), $$3);
/*     */           } 
/*     */         });
/*     */     
/* 114 */     if (!$$3.isEmpty()) {
/* 115 */       logErrors($$3);
/* 116 */       throw new IllegalStateException("Failed to load registries due to above errors");
/*     */     } 
/*     */     
/* 119 */     return (new RegistryAccess.ImmutableRegistryAccess($$4.stream().map(Pair::getFirst).toList())).freeze();
/*     */   }
/*     */   
/*     */   private static RegistryOps.RegistryInfoLookup createContext(RegistryAccess $$0, List<Pair<WritableRegistry<?>, Loader>> $$1) {
/* 123 */     final Map<ResourceKey<? extends Registry<?>>, RegistryOps.RegistryInfo<?>> result = new HashMap<>();
/*     */     
/* 125 */     $$0.registries().forEach($$1 -> $$0.put($$1.key(), createInfoForContextRegistry($$1.value())));
/* 126 */     $$1.forEach($$1 -> $$0.put(((WritableRegistry)$$1.getFirst()).key(), createInfoForNewRegistry((WritableRegistry)$$1.getFirst())));
/*     */ 
/*     */     
/* 129 */     return new RegistryOps.RegistryInfoLookup()
/*     */       {
/*     */         public <T> Optional<RegistryOps.RegistryInfo<T>> lookup(ResourceKey<? extends Registry<? extends T>> $$0)
/*     */         {
/* 133 */           return Optional.ofNullable((RegistryOps.RegistryInfo<T>)result.get($$0));
/*     */         }
/*     */       };
/*     */   } private static interface Loader {
/*     */     void load(ResourceManager param1ResourceManager, RegistryOps.RegistryInfoLookup param1RegistryInfoLookup); }
/*     */   private static <T> RegistryOps.RegistryInfo<T> createInfoForNewRegistry(WritableRegistry<T> $$0) {
/* 139 */     return new RegistryOps.RegistryInfo<>((HolderOwner<T>)$$0.asLookup(), $$0.createRegistrationLookup(), $$0.registryLifecycle());
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T> RegistryOps.RegistryInfo<T> createInfoForContextRegistry(Registry<T> $$0) {
/* 144 */     return new RegistryOps.RegistryInfo<>((HolderOwner<T>)$$0.asLookup(), (HolderGetter<T>)$$0.asTagAddingLookup(), $$0.registryLifecycle());
/*     */   }
/*     */   
/*     */   private static void logErrors(Map<ResourceKey<?>, Exception> $$0) {
/* 148 */     StringWriter $$1 = new StringWriter();
/* 149 */     PrintWriter $$2 = new PrintWriter($$1);
/* 150 */     Map<ResourceLocation, Map<ResourceLocation, Exception>> $$3 = (Map<ResourceLocation, Map<ResourceLocation, Exception>>)$$0.entrySet().stream().collect(Collectors.groupingBy($$0 -> ((ResourceKey)$$0.getKey()).registry(), Collectors.toMap($$0 -> ((ResourceKey)$$0.getKey()).location(), Map.Entry::getValue)));
/* 151 */     $$3.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach($$1 -> {
/*     */           $$0.printf("> Errors in registry %s:%n", new Object[] { $$1.getKey() });
/*     */ 
/*     */           
/*     */           ((Map)$$1.getValue()).entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(());
/*     */         });
/*     */     
/* 158 */     $$2.flush();
/* 159 */     LOGGER.error("Registry loading errors:\n{}", $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   private static String registryDirPath(ResourceLocation $$0) {
/* 164 */     return $$0.getPath();
/*     */   }
/*     */   
/*     */   static <E> void loadRegistryContents(RegistryOps.RegistryInfoLookup $$0, ResourceManager $$1, ResourceKey<? extends Registry<E>> $$2, WritableRegistry<E> $$3, Decoder<E> $$4, Map<ResourceKey<?>, Exception> $$5) {
/* 168 */     String $$6 = registryDirPath($$2.location());
/*     */     
/* 170 */     FileToIdConverter $$7 = FileToIdConverter.json($$6);
/*     */     
/* 172 */     RegistryOps<JsonElement> $$8 = RegistryOps.create((DynamicOps<JsonElement>)JsonOps.INSTANCE, $$0);
/* 173 */     for (Map.Entry<ResourceLocation, Resource> $$9 : $$7.listMatchingResources($$1).entrySet()) {
/* 174 */       ResourceLocation $$10 = $$9.getKey();
/* 175 */       ResourceKey<E> $$11 = ResourceKey.create($$2, $$7.fileToId($$10));
/*     */       
/* 177 */       Resource $$12 = $$9.getValue(); 
/* 178 */       try { Reader $$13 = $$12.openAsReader(); 
/* 179 */         try { JsonElement $$14 = JsonParser.parseReader($$13);
/* 180 */           DataResult<E> $$15 = $$4.parse($$8, $$14);
/* 181 */           E $$16 = (E)$$15.getOrThrow(false, $$0 -> { 
/* 182 */               }); $$3.register($$11, $$16, $$12.isBuiltin() ? Lifecycle.stable() : $$15.lifecycle());
/* 183 */           if ($$13 != null) $$13.close();  } catch (Throwable throwable) { if ($$13 != null) try { $$13.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (Exception $$17)
/* 184 */       { $$5.put($$11, new IllegalStateException(String.format(Locale.ROOT, "Failed to parse %s from pack %s", new Object[] { $$10, $$12.sourcePackId() }), $$17)); }
/*     */     
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\resources\RegistryDataLoader.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */