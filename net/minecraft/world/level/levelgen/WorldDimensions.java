/*     */ package net.minecraft.world.level.levelgen;
/*     */ 
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.serialization.Lifecycle;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.function.Function;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.MappedRegistry;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.core.RegistryAccess;
/*     */ import net.minecraft.core.WritableRegistry;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.world.level.biome.BiomeSource;
/*     */ import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
/*     */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*     */ import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
/*     */ import net.minecraft.world.level.dimension.DimensionType;
/*     */ import net.minecraft.world.level.dimension.LevelStem;
/*     */ import net.minecraft.world.level.storage.PrimaryLevelData;
/*     */ 
/*     */ public final class WorldDimensions extends Record {
/*     */   private final Registry<LevelStem> dimensions;
/*     */   public static final MapCodec<WorldDimensions> CODEC;
/*     */   
/*  32 */   public Registry<LevelStem> dimensions() { return this.dimensions; }
/*     */   public final String toString() { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/WorldDimensions;)Ljava/lang/String;
/*     */     //   6: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #32	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/WorldDimensions; } public final int hashCode() { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/WorldDimensions;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #32	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/WorldDimensions; } public final boolean equals(Object $$0) { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/WorldDimensions;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #32	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lnet/minecraft/world/level/levelgen/WorldDimensions;
/*  33 */     //   0	8	1	$$0	Ljava/lang/Object; } static { CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)RegistryCodecs.fullCodec(Registries.LEVEL_STEM, Lifecycle.stable(), LevelStem.CODEC).fieldOf("dimensions").forGetter(WorldDimensions::dimensions)).apply((Applicative)$$0, $$0.stable(WorldDimensions::new))); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  39 */   private static final Set<ResourceKey<LevelStem>> BUILTIN_ORDER = (Set<ResourceKey<LevelStem>>)ImmutableSet.of(LevelStem.OVERWORLD, LevelStem.NETHER, LevelStem.END);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  45 */   private static final int VANILLA_DIMENSION_COUNT = BUILTIN_ORDER.size();
/*     */   
/*     */   public WorldDimensions(Registry<LevelStem> $$0) {
/*  48 */     LevelStem $$1 = (LevelStem)$$0.get(LevelStem.OVERWORLD);
/*  49 */     if ($$1 == null)
/*  50 */       throw new IllegalStateException("Overworld settings missing"); 
/*     */     this.dimensions = $$0;
/*     */   }
/*     */   
/*     */   public static Stream<ResourceKey<LevelStem>> keysInOrder(Stream<ResourceKey<LevelStem>> $$0) {
/*  55 */     return Stream.concat(BUILTIN_ORDER
/*  56 */         .stream(), $$0
/*  57 */         .filter($$0 -> !BUILTIN_ORDER.contains($$0)));
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldDimensions replaceOverworldGenerator(RegistryAccess $$0, ChunkGenerator $$1) {
/*  62 */     Registry<DimensionType> $$2 = $$0.registryOrThrow(Registries.DIMENSION_TYPE);
/*  63 */     Registry<LevelStem> $$3 = withOverworld($$2, this.dimensions, $$1);
/*  64 */     return new WorldDimensions($$3);
/*     */   }
/*     */   
/*     */   public static Registry<LevelStem> withOverworld(Registry<DimensionType> $$0, Registry<LevelStem> $$1, ChunkGenerator $$2) {
/*  68 */     LevelStem $$3 = (LevelStem)$$1.get(LevelStem.OVERWORLD);
/*  69 */     Holder<DimensionType> $$4 = ($$3 == null) ? (Holder<DimensionType>)$$0.getHolderOrThrow(BuiltinDimensionTypes.OVERWORLD) : $$3.type();
/*     */     
/*  71 */     return withOverworld($$1, $$4, $$2);
/*     */   }
/*     */   
/*     */   public static Registry<LevelStem> withOverworld(Registry<LevelStem> $$0, Holder<DimensionType> $$1, ChunkGenerator $$2) {
/*  75 */     MappedRegistry mappedRegistry = new MappedRegistry(Registries.LEVEL_STEM, Lifecycle.experimental());
/*     */     
/*  77 */     mappedRegistry.register(LevelStem.OVERWORLD, new LevelStem($$1, $$2), Lifecycle.stable());
/*  78 */     for (Map.Entry<ResourceKey<LevelStem>, LevelStem> $$4 : (Iterable<Map.Entry<ResourceKey<LevelStem>, LevelStem>>)$$0.entrySet()) {
/*  79 */       ResourceKey<LevelStem> $$5 = $$4.getKey();
/*  80 */       if ($$5 == LevelStem.OVERWORLD) {
/*     */         continue;
/*     */       }
/*  83 */       mappedRegistry.register($$5, $$4.getValue(), $$0.lifecycle($$4.getValue()));
/*     */     } 
/*  85 */     return mappedRegistry.freeze();
/*     */   }
/*     */   
/*     */   public ChunkGenerator overworld() {
/*  89 */     LevelStem $$0 = (LevelStem)this.dimensions.get(LevelStem.OVERWORLD);
/*  90 */     if ($$0 == null) {
/*  91 */       throw new IllegalStateException("Overworld settings missing");
/*     */     }
/*  93 */     return $$0.generator();
/*     */   }
/*     */   
/*     */   public Optional<LevelStem> get(ResourceKey<LevelStem> $$0) {
/*  97 */     return this.dimensions.getOptional($$0);
/*     */   }
/*     */   
/*     */   public ImmutableSet<ResourceKey<Level>> levels() {
/* 101 */     return (ImmutableSet<ResourceKey<Level>>)dimensions().entrySet().stream().map(Map.Entry::getKey).map(Registries::levelStemToLevel).collect(ImmutableSet.toImmutableSet());
/*     */   }
/*     */   
/*     */   public boolean isDebug() {
/* 105 */     return overworld() instanceof DebugLevelSource;
/*     */   }
/*     */   
/*     */   private static PrimaryLevelData.SpecialWorldProperty specialWorldProperty(Registry<LevelStem> $$0) {
/* 109 */     return $$0.getOptional(LevelStem.OVERWORLD).map($$0 -> {
/*     */           ChunkGenerator $$1 = $$0.generator();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           return ($$1 instanceof DebugLevelSource) ? PrimaryLevelData.SpecialWorldProperty.DEBUG : (($$1 instanceof FlatLevelSource) ? PrimaryLevelData.SpecialWorldProperty.FLAT : PrimaryLevelData.SpecialWorldProperty.NONE);
/* 118 */         }).orElse(PrimaryLevelData.SpecialWorldProperty.NONE);
/*     */   }
/*     */   
/*     */   static Lifecycle checkStability(ResourceKey<LevelStem> $$0, LevelStem $$1) {
/* 122 */     return isVanillaLike($$0, $$1) ? Lifecycle.stable() : Lifecycle.experimental();
/*     */   }
/*     */   
/*     */   private static boolean isVanillaLike(ResourceKey<LevelStem> $$0, LevelStem $$1) {
/* 126 */     if ($$0 == LevelStem.OVERWORLD) {
/* 127 */       return isStableOverworld($$1);
/*     */     }
/* 129 */     if ($$0 == LevelStem.NETHER) {
/* 130 */       return isStableNether($$1);
/*     */     }
/* 132 */     if ($$0 == LevelStem.END) {
/* 133 */       return isStableEnd($$1);
/*     */     }
/* 135 */     return false;
/*     */   }
/*     */   
/*     */   private static boolean isStableOverworld(LevelStem $$0) {
/* 139 */     Holder<DimensionType> $$1 = $$0.type();
/* 140 */     if (!$$1.is(BuiltinDimensionTypes.OVERWORLD) && !$$1.is(BuiltinDimensionTypes.OVERWORLD_CAVES)) {
/* 141 */       return false;
/*     */     }
/* 143 */     BiomeSource biomeSource = $$0.generator().getBiomeSource(); if (biomeSource instanceof MultiNoiseBiomeSource) { MultiNoiseBiomeSource $$2 = (MultiNoiseBiomeSource)biomeSource;
/* 144 */       if (!$$2.stable(MultiNoiseBiomeSourceParameterLists.OVERWORLD)) {
/* 145 */         return false;
/*     */       } }
/*     */     
/* 148 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isStableNether(LevelStem $$0) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: invokevirtual type : ()Lnet/minecraft/core/Holder;
/*     */     //   4: getstatic net/minecraft/world/level/dimension/BuiltinDimensionTypes.NETHER : Lnet/minecraft/resources/ResourceKey;
/*     */     //   7: invokeinterface is : (Lnet/minecraft/resources/ResourceKey;)Z
/*     */     //   12: ifeq -> 73
/*     */     //   15: aload_0
/*     */     //   16: invokevirtual generator : ()Lnet/minecraft/world/level/chunk/ChunkGenerator;
/*     */     //   19: astore_3
/*     */     //   20: aload_3
/*     */     //   21: instanceof net/minecraft/world/level/levelgen/NoiseBasedChunkGenerator
/*     */     //   24: ifeq -> 73
/*     */     //   27: aload_3
/*     */     //   28: checkcast net/minecraft/world/level/levelgen/NoiseBasedChunkGenerator
/*     */     //   31: astore_2
/*     */     //   32: aload_2
/*     */     //   33: getstatic net/minecraft/world/level/levelgen/NoiseGeneratorSettings.NETHER : Lnet/minecraft/resources/ResourceKey;
/*     */     //   36: invokevirtual stable : (Lnet/minecraft/resources/ResourceKey;)Z
/*     */     //   39: ifeq -> 73
/*     */     //   42: aload_2
/*     */     //   43: invokevirtual getBiomeSource : ()Lnet/minecraft/world/level/biome/BiomeSource;
/*     */     //   46: astore_3
/*     */     //   47: aload_3
/*     */     //   48: instanceof net/minecraft/world/level/biome/MultiNoiseBiomeSource
/*     */     //   51: ifeq -> 73
/*     */     //   54: aload_3
/*     */     //   55: checkcast net/minecraft/world/level/biome/MultiNoiseBiomeSource
/*     */     //   58: astore_1
/*     */     //   59: aload_1
/*     */     //   60: getstatic net/minecraft/world/level/biome/MultiNoiseBiomeSourceParameterLists.NETHER : Lnet/minecraft/resources/ResourceKey;
/*     */     //   63: invokevirtual stable : (Lnet/minecraft/resources/ResourceKey;)Z
/*     */     //   66: ifeq -> 73
/*     */     //   69: iconst_1
/*     */     //   70: goto -> 74
/*     */     //   73: iconst_0
/*     */     //   74: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #156	-> 0
/*     */     //   #152	-> 1
/*     */     //   #153	-> 15
/*     */     //   #154	-> 36
/*     */     //   #155	-> 42
/*     */     //   #156	-> 63
/*     */     //   #152	-> 74
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	75	0	$$0	Lnet/minecraft/world/level/dimension/LevelStem;
/*     */     //   32	41	2	$$1	Lnet/minecraft/world/level/levelgen/NoiseBasedChunkGenerator;
/*     */     //   59	14	1	$$2	Lnet/minecraft/world/level/biome/MultiNoiseBiomeSource;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isStableEnd(LevelStem $$0) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: invokevirtual type : ()Lnet/minecraft/core/Holder;
/*     */     //   4: getstatic net/minecraft/world/level/dimension/BuiltinDimensionTypes.END : Lnet/minecraft/resources/ResourceKey;
/*     */     //   7: invokeinterface is : (Lnet/minecraft/resources/ResourceKey;)Z
/*     */     //   12: ifeq -> 56
/*     */     //   15: aload_0
/*     */     //   16: invokevirtual generator : ()Lnet/minecraft/world/level/chunk/ChunkGenerator;
/*     */     //   19: astore_2
/*     */     //   20: aload_2
/*     */     //   21: instanceof net/minecraft/world/level/levelgen/NoiseBasedChunkGenerator
/*     */     //   24: ifeq -> 56
/*     */     //   27: aload_2
/*     */     //   28: checkcast net/minecraft/world/level/levelgen/NoiseBasedChunkGenerator
/*     */     //   31: astore_1
/*     */     //   32: aload_1
/*     */     //   33: getstatic net/minecraft/world/level/levelgen/NoiseGeneratorSettings.END : Lnet/minecraft/resources/ResourceKey;
/*     */     //   36: invokevirtual stable : (Lnet/minecraft/resources/ResourceKey;)Z
/*     */     //   39: ifeq -> 56
/*     */     //   42: aload_1
/*     */     //   43: invokevirtual getBiomeSource : ()Lnet/minecraft/world/level/biome/BiomeSource;
/*     */     //   46: instanceof net/minecraft/world/level/biome/TheEndBiomeSource
/*     */     //   49: ifeq -> 56
/*     */     //   52: iconst_1
/*     */     //   53: goto -> 57
/*     */     //   56: iconst_0
/*     */     //   57: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #163	-> 0
/*     */     //   #160	-> 1
/*     */     //   #161	-> 15
/*     */     //   #162	-> 36
/*     */     //   #163	-> 43
/*     */     //   #160	-> 57
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	58	0	$$0	Lnet/minecraft/world/level/dimension/LevelStem;
/*     */     //   32	24	1	$$1	Lnet/minecraft/world/level/levelgen/NoiseBasedChunkGenerator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Complete bake(Registry<LevelStem> $$0) {
/* 173 */     Stream<ResourceKey<LevelStem>> $$1 = Stream.<ResourceKey<LevelStem>>concat($$0.registryKeySet().stream(), this.dimensions.registryKeySet().stream()).distinct();
/*     */     
/* 175 */     List<Entry> $$2 = new ArrayList<>();
/* 176 */     keysInOrder($$1).forEach($$2 -> $$0.getOptional($$2).or(()).ifPresent(()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 182 */     Lifecycle $$3 = ($$2.size() == VANILLA_DIMENSION_COUNT) ? Lifecycle.stable() : Lifecycle.experimental();
/* 183 */     MappedRegistry mappedRegistry = new MappedRegistry(Registries.LEVEL_STEM, $$3);
/* 184 */     $$2.forEach($$1 -> $$0.register($$1.key, $$1.value, $$1.lifecycle()));
/* 185 */     Registry<LevelStem> $$5 = mappedRegistry.freeze();
/*     */     
/* 187 */     PrimaryLevelData.SpecialWorldProperty $$6 = specialWorldProperty($$5);
/* 188 */     return new Complete($$5.freeze(), $$6);
/*     */   }
/*     */   public static final class Complete extends Record { private final Registry<LevelStem> dimensions; private final PrimaryLevelData.SpecialWorldProperty specialWorldProperty;
/* 191 */     public Complete(Registry<LevelStem> $$0, PrimaryLevelData.SpecialWorldProperty $$1) { this.dimensions = $$0; this.specialWorldProperty = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/WorldDimensions$Complete;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #191	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/WorldDimensions$Complete; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/WorldDimensions$Complete;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #191	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/WorldDimensions$Complete; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/WorldDimensions$Complete;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #191	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/levelgen/WorldDimensions$Complete;
/* 191 */       //   0	8	1	$$0	Ljava/lang/Object; } public Registry<LevelStem> dimensions() { return this.dimensions; } public PrimaryLevelData.SpecialWorldProperty specialWorldProperty() { return this.specialWorldProperty; }
/*     */ 
/*     */ 
/*     */     
/*     */     public Lifecycle lifecycle() {
/* 196 */       return this.dimensions.registryLifecycle();
/*     */     }
/*     */     
/*     */     public RegistryAccess.Frozen dimensionsRegistryAccess() {
/* 200 */       return (new RegistryAccess.ImmutableRegistryAccess(List.of(this.dimensions))).freeze();
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\WorldDimensions.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */