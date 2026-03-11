/*     */ package net.minecraft.world.level.levelgen.structure;
/*     */ 
/*     */ import java.util.function.Predicate;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.RegistryAccess;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.LevelHeightAccessor;
/*     */ import net.minecraft.world.level.biome.Biome;
/*     */ import net.minecraft.world.level.biome.BiomeSource;
/*     */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*     */ import net.minecraft.world.level.levelgen.LegacyRandomSource;
/*     */ import net.minecraft.world.level.levelgen.RandomState;
/*     */ import net.minecraft.world.level.levelgen.WorldgenRandom;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
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
/*     */ public final class GenerationContext
/*     */   extends Record
/*     */ {
/*     */   private final RegistryAccess registryAccess;
/*     */   final ChunkGenerator chunkGenerator;
/*     */   private final BiomeSource biomeSource;
/*     */   final RandomState randomState;
/*     */   private final StructureTemplateManager structureTemplateManager;
/*     */   private final WorldgenRandom random;
/*     */   private final long seed;
/*     */   private final ChunkPos chunkPos;
/*     */   private final LevelHeightAccessor heightAccessor;
/*     */   final Predicate<Holder<Biome>> validBiome;
/*     */   
/*     */   public final String toString() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationContext;)Ljava/lang/String;
/*     */     //   6: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #99	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationContext;
/*     */   }
/*     */   
/*     */   public final int hashCode() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationContext;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #99	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationContext;
/*     */   }
/*     */   
/*     */   public final boolean equals(Object $$0) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationContext;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #99	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationContext;
/*     */     //   0	8	1	$$0	Ljava/lang/Object;
/*     */   }
/*     */   
/*     */   public GenerationContext(RegistryAccess $$0, ChunkGenerator $$1, BiomeSource $$2, RandomState $$3, StructureTemplateManager $$4, WorldgenRandom $$5, long $$6, ChunkPos $$7, LevelHeightAccessor $$8, Predicate<Holder<Biome>> $$9) {
/*  99 */     this.registryAccess = $$0; this.chunkGenerator = $$1; this.biomeSource = $$2; this.randomState = $$3; this.structureTemplateManager = $$4; this.random = $$5; this.seed = $$6; this.chunkPos = $$7; this.heightAccessor = $$8; this.validBiome = $$9; } public RegistryAccess registryAccess() { return this.registryAccess; } public ChunkGenerator chunkGenerator() { return this.chunkGenerator; } public BiomeSource biomeSource() { return this.biomeSource; } public RandomState randomState() { return this.randomState; } public StructureTemplateManager structureTemplateManager() { return this.structureTemplateManager; } public WorldgenRandom random() { return this.random; } public long seed() { return this.seed; } public ChunkPos chunkPos() { return this.chunkPos; } public LevelHeightAccessor heightAccessor() { return this.heightAccessor; } public Predicate<Holder<Biome>> validBiome() { return this.validBiome; }
/*     */    public GenerationContext(RegistryAccess $$0, ChunkGenerator $$1, BiomeSource $$2, RandomState $$3, StructureTemplateManager $$4, long $$5, ChunkPos $$6, LevelHeightAccessor $$7, Predicate<Holder<Biome>> $$8) {
/* 101 */     this($$0, $$1, $$2, $$3, $$4, makeRandom($$5, $$6), $$5, $$6, $$7, $$8);
/*     */   }
/*     */   
/*     */   private static WorldgenRandom makeRandom(long $$0, ChunkPos $$1) {
/* 105 */     WorldgenRandom $$2 = new WorldgenRandom((RandomSource)new LegacyRandomSource(0L));
/* 106 */     $$2.setLargeFeatureSeed($$0, $$1.x, $$1.z);
/* 107 */     return $$2;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\Structure$GenerationContext.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */