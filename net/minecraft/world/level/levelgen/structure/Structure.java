/*     */ package net.minecraft.world.level.levelgen.structure;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Either;
/*     */ import com.mojang.datafixers.util.Function4;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Predicate;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.HolderSet;
/*     */ import net.minecraft.core.QuartPos;
/*     */ import net.minecraft.core.RegistryAccess;
/*     */ import net.minecraft.core.RegistryCodecs;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.resources.RegistryFileCodec;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.StringRepresentable;
/*     */ import net.minecraft.world.entity.MobCategory;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.LevelHeightAccessor;
/*     */ import net.minecraft.world.level.StructureManager;
/*     */ import net.minecraft.world.level.WorldGenLevel;
/*     */ import net.minecraft.world.level.biome.Biome;
/*     */ import net.minecraft.world.level.biome.BiomeSource;
/*     */ import net.minecraft.world.level.block.Rotation;
/*     */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*     */ import net.minecraft.world.level.levelgen.GenerationStep;
/*     */ import net.minecraft.world.level.levelgen.Heightmap;
/*     */ import net.minecraft.world.level.levelgen.LegacyRandomSource;
/*     */ import net.minecraft.world.level.levelgen.RandomState;
/*     */ import net.minecraft.world.level.levelgen.WorldgenRandom;
/*     */ import net.minecraft.world.level.levelgen.structure.pieces.PiecesContainer;
/*     */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
/*     */ 
/*     */ public abstract class Structure {
/*  44 */   public static final Codec<Structure> DIRECT_CODEC = BuiltInRegistries.STRUCTURE_TYPE.byNameCodec().dispatch(Structure::type, StructureType::codec);
/*  45 */   public static final Codec<Holder<Structure>> CODEC = (Codec<Holder<Structure>>)RegistryFileCodec.create(Registries.STRUCTURE, DIRECT_CODEC); protected final StructureSettings settings;
/*     */   public static final class StructureSettings extends Record { final HolderSet<Biome> biomes; final Map<MobCategory, StructureSpawnOverride> spawnOverrides; final GenerationStep.Decoration step; final TerrainAdjustment terrainAdaptation; public static final MapCodec<StructureSettings> CODEC;
/*  47 */     public StructureSettings(HolderSet<Biome> $$0, Map<MobCategory, StructureSpawnOverride> $$1, GenerationStep.Decoration $$2, TerrainAdjustment $$3) { this.biomes = $$0; this.spawnOverrides = $$1; this.step = $$2; this.terrainAdaptation = $$3; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/structure/Structure$StructureSettings;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #47	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*  47 */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/structure/Structure$StructureSettings; } public HolderSet<Biome> biomes() { return this.biomes; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/structure/Structure$StructureSettings;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #47	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/structure/Structure$StructureSettings; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/structure/Structure$StructureSettings;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #47	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/levelgen/structure/Structure$StructureSettings;
/*  47 */       //   0	8	1	$$0	Ljava/lang/Object; } public Map<MobCategory, StructureSpawnOverride> spawnOverrides() { return this.spawnOverrides; } public GenerationStep.Decoration step() { return this.step; } public TerrainAdjustment terrainAdaptation() { return this.terrainAdaptation; }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/*  53 */       CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)RegistryCodecs.homogeneousList(Registries.BIOME).fieldOf("biomes").forGetter(StructureSettings::biomes), (App)Codec.simpleMap(MobCategory.CODEC, StructureSpawnOverride.CODEC, StringRepresentable.keys((StringRepresentable[])MobCategory.values())).fieldOf("spawn_overrides").forGetter(StructureSettings::spawnOverrides), (App)GenerationStep.Decoration.CODEC.fieldOf("step").forGetter(StructureSettings::step), (App)TerrainAdjustment.CODEC.optionalFieldOf("terrain_adaptation", TerrainAdjustment.NONE).forGetter(StructureSettings::terrainAdaptation)).apply((Applicative)$$0, StructureSettings::new));
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <S extends Structure> RecordCodecBuilder<S, StructureSettings> settingsCodec(RecordCodecBuilder.Instance<S> $$0) {
/*  63 */     return StructureSettings.CODEC.forGetter($$0 -> $$0.settings);
/*     */   }
/*     */   
/*     */   public static <S extends Structure> Codec<S> simpleCodec(Function<StructureSettings, S> $$0) {
/*  67 */     return RecordCodecBuilder.create($$1 -> $$1.group((App)settingsCodec($$1)).apply((Applicative)$$1, $$0));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Structure(StructureSettings $$0) {
/*  73 */     this.settings = $$0;
/*     */   }
/*     */   
/*     */   public HolderSet<Biome> biomes() {
/*  77 */     return this.settings.biomes;
/*     */   }
/*     */   
/*     */   public Map<MobCategory, StructureSpawnOverride> spawnOverrides() {
/*  81 */     return this.settings.spawnOverrides;
/*     */   }
/*     */   
/*     */   public GenerationStep.Decoration step() {
/*  85 */     return this.settings.step;
/*     */   }
/*     */   
/*     */   public TerrainAdjustment terrainAdaptation() {
/*  89 */     return this.settings.terrainAdaptation;
/*     */   }
/*     */   
/*     */   public BoundingBox adjustBoundingBox(BoundingBox $$0) {
/*  93 */     if (terrainAdaptation() != TerrainAdjustment.NONE) {
/*  94 */       return $$0.inflatedBy(12);
/*     */     }
/*  96 */     return $$0;
/*     */   }
/*     */   public static final class GenerationContext extends Record { private final RegistryAccess registryAccess; final ChunkGenerator chunkGenerator; private final BiomeSource biomeSource; final RandomState randomState; private final StructureTemplateManager structureTemplateManager; private final WorldgenRandom random; private final long seed; private final ChunkPos chunkPos; private final LevelHeightAccessor heightAccessor; final Predicate<Holder<Biome>> validBiome;
/*  99 */     public GenerationContext(RegistryAccess $$0, ChunkGenerator $$1, BiomeSource $$2, RandomState $$3, StructureTemplateManager $$4, WorldgenRandom $$5, long $$6, ChunkPos $$7, LevelHeightAccessor $$8, Predicate<Holder<Biome>> $$9) { this.registryAccess = $$0; this.chunkGenerator = $$1; this.biomeSource = $$2; this.randomState = $$3; this.structureTemplateManager = $$4; this.random = $$5; this.seed = $$6; this.chunkPos = $$7; this.heightAccessor = $$8; this.validBiome = $$9; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationContext;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #99	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationContext; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationContext;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #99	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationContext; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationContext;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #99	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationContext;
/*  99 */       //   0	8	1	$$0	Ljava/lang/Object; } public RegistryAccess registryAccess() { return this.registryAccess; } public ChunkGenerator chunkGenerator() { return this.chunkGenerator; } public BiomeSource biomeSource() { return this.biomeSource; } public RandomState randomState() { return this.randomState; } public StructureTemplateManager structureTemplateManager() { return this.structureTemplateManager; } public WorldgenRandom random() { return this.random; } public long seed() { return this.seed; } public ChunkPos chunkPos() { return this.chunkPos; } public LevelHeightAccessor heightAccessor() { return this.heightAccessor; } public Predicate<Holder<Biome>> validBiome() { return this.validBiome; }
/*     */      public GenerationContext(RegistryAccess $$0, ChunkGenerator $$1, BiomeSource $$2, RandomState $$3, StructureTemplateManager $$4, long $$5, ChunkPos $$6, LevelHeightAccessor $$7, Predicate<Holder<Biome>> $$8) {
/* 101 */       this($$0, $$1, $$2, $$3, $$4, makeRandom($$5, $$6), $$5, $$6, $$7, $$8);
/*     */     }
/*     */     
/*     */     private static WorldgenRandom makeRandom(long $$0, ChunkPos $$1) {
/* 105 */       WorldgenRandom $$2 = new WorldgenRandom((RandomSource)new LegacyRandomSource(0L));
/* 106 */       $$2.setLargeFeatureSeed($$0, $$1.x, $$1.z);
/* 107 */       return $$2;
/*     */     } }
/*     */   public static final class GenerationStub extends Record { private final BlockPos position; private final Either<Consumer<StructurePiecesBuilder>, StructurePiecesBuilder> generator;
/* 110 */     public GenerationStub(BlockPos $$0, Either<Consumer<StructurePiecesBuilder>, StructurePiecesBuilder> $$1) { this.position = $$0; this.generator = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationStub;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #110	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationStub; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationStub;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #110	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationStub; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationStub;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #110	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationStub;
/* 110 */       //   0	8	1	$$0	Ljava/lang/Object; } public BlockPos position() { return this.position; } public Either<Consumer<StructurePiecesBuilder>, StructurePiecesBuilder> generator() { return this.generator; }
/*     */      public GenerationStub(BlockPos $$0, Consumer<StructurePiecesBuilder> $$1) {
/* 112 */       this($$0, Either.left($$1));
/*     */     }
/*     */     public StructurePiecesBuilder getPiecesBuilder() {
/* 115 */       return (StructurePiecesBuilder)this.generator.map($$0 -> {
/*     */             StructurePiecesBuilder $$1 = new StructurePiecesBuilder();
/*     */             $$0.accept($$1);
/*     */             return $$1;
/*     */           }$$0 -> $$0);
/*     */     } }
/*     */ 
/*     */   
/*     */   public StructureStart generate(RegistryAccess $$0, ChunkGenerator $$1, BiomeSource $$2, RandomState $$3, StructureTemplateManager $$4, long $$5, ChunkPos $$6, int $$7, LevelHeightAccessor $$8, Predicate<Holder<Biome>> $$9) {
/* 124 */     GenerationContext $$10 = new GenerationContext($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$8, $$9);
/* 125 */     Optional<GenerationStub> $$11 = findValidGenerationPoint($$10);
/* 126 */     if ($$11.isPresent()) {
/* 127 */       StructurePiecesBuilder $$12 = ((GenerationStub)$$11.get()).getPiecesBuilder();
/*     */       
/* 129 */       StructureStart $$13 = new StructureStart(this, $$6, $$7, $$12.build());
/* 130 */       if ($$13.isValid()) {
/* 131 */         return $$13;
/*     */       }
/*     */     } 
/* 134 */     return StructureStart.INVALID_START;
/*     */   }
/*     */   
/*     */   protected static Optional<GenerationStub> onTopOfChunkCenter(GenerationContext $$0, Heightmap.Types $$1, Consumer<StructurePiecesBuilder> $$2) {
/* 138 */     ChunkPos $$3 = $$0.chunkPos();
/* 139 */     int $$4 = $$3.getMiddleBlockX();
/* 140 */     int $$5 = $$3.getMiddleBlockZ();
/* 141 */     int $$6 = $$0.chunkGenerator().getFirstOccupiedHeight($$4, $$5, $$1, $$0.heightAccessor(), $$0.randomState());
/* 142 */     return Optional.of(new GenerationStub(new BlockPos($$4, $$6, $$5), $$2));
/*     */   }
/*     */   
/*     */   private static boolean isValidBiome(GenerationStub $$0, GenerationContext $$1) {
/* 146 */     BlockPos $$2 = $$0.position();
/* 147 */     return $$1.validBiome.test($$1.chunkGenerator.getBiomeSource().getNoiseBiome(QuartPos.fromBlock($$2.getX()), QuartPos.fromBlock($$2.getY()), QuartPos.fromBlock($$2.getZ()), $$1.randomState.sampler()));
/*     */   }
/*     */ 
/*     */   
/*     */   public void afterPlace(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, PiecesContainer $$6) {}
/*     */   
/*     */   private static int[] getCornerHeights(GenerationContext $$0, int $$1, int $$2, int $$3, int $$4) {
/* 154 */     ChunkGenerator $$5 = $$0.chunkGenerator();
/* 155 */     LevelHeightAccessor $$6 = $$0.heightAccessor();
/* 156 */     RandomState $$7 = $$0.randomState();
/* 157 */     return new int[] { $$5
/* 158 */         .getFirstOccupiedHeight($$1, $$3, Heightmap.Types.WORLD_SURFACE_WG, $$6, $$7), $$5
/* 159 */         .getFirstOccupiedHeight($$1, $$3 + $$4, Heightmap.Types.WORLD_SURFACE_WG, $$6, $$7), $$5
/* 160 */         .getFirstOccupiedHeight($$1 + $$2, $$3, Heightmap.Types.WORLD_SURFACE_WG, $$6, $$7), $$5
/* 161 */         .getFirstOccupiedHeight($$1 + $$2, $$3 + $$4, Heightmap.Types.WORLD_SURFACE_WG, $$6, $$7) };
/*     */   }
/*     */ 
/*     */   
/*     */   protected static int getLowestY(GenerationContext $$0, int $$1, int $$2) {
/* 166 */     ChunkPos $$3 = $$0.chunkPos();
/* 167 */     int $$4 = $$3.getMinBlockX();
/* 168 */     int $$5 = $$3.getMinBlockZ();
/* 169 */     return getLowestY($$0, $$4, $$5, $$1, $$2);
/*     */   }
/*     */   
/*     */   protected static int getLowestY(GenerationContext $$0, int $$1, int $$2, int $$3, int $$4) {
/* 173 */     int[] $$5 = getCornerHeights($$0, $$1, $$3, $$2, $$4);
/* 174 */     return Math.min(Math.min($$5[0], $$5[1]), Math.min($$5[2], $$5[3]));
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   protected BlockPos getLowestYIn5by5BoxOffset7Blocks(GenerationContext $$0, Rotation $$1) {
/* 179 */     int $$2 = 5;
/* 180 */     int $$3 = 5;
/* 181 */     if ($$1 == Rotation.CLOCKWISE_90) {
/* 182 */       $$2 = -5;
/* 183 */     } else if ($$1 == Rotation.CLOCKWISE_180) {
/* 184 */       $$2 = -5;
/* 185 */       $$3 = -5;
/* 186 */     } else if ($$1 == Rotation.COUNTERCLOCKWISE_90) {
/* 187 */       $$3 = -5;
/*     */     } 
/*     */     
/* 190 */     ChunkPos $$4 = $$0.chunkPos();
/* 191 */     int $$5 = $$4.getBlockX(7);
/* 192 */     int $$6 = $$4.getBlockZ(7);
/* 193 */     return new BlockPos($$5, getLowestY($$0, $$5, $$6, $$2, $$3), $$6);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Optional<GenerationStub> findValidGenerationPoint(GenerationContext $$0) {
/* 199 */     return findGenerationPoint($$0).filter($$1 -> isValidBiome($$1, $$0));
/*     */   }
/*     */   
/*     */   protected abstract Optional<GenerationStub> findGenerationPoint(GenerationContext paramGenerationContext);
/*     */   
/*     */   public abstract StructureType<?> type();
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\Structure.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */