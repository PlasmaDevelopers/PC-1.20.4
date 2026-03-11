/*    */ package net.minecraft.world.level.levelgen.structure.pieces;
/*    */ 
/*    */ import java.util.Optional;
/*    */ import java.util.function.Predicate;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.QuartPos;
/*    */ import net.minecraft.core.RegistryAccess;
/*    */ import net.minecraft.world.level.ChunkPos;
/*    */ import net.minecraft.world.level.LevelHeightAccessor;
/*    */ import net.minecraft.world.level.biome.Biome;
/*    */ import net.minecraft.world.level.biome.BiomeSource;
/*    */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*    */ import net.minecraft.world.level.levelgen.Heightmap;
/*    */ import net.minecraft.world.level.levelgen.RandomState;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
/*    */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ @FunctionalInterface
/*    */ public interface PieceGeneratorSupplier<C extends FeatureConfiguration>
/*    */ {
/*    */   static <C extends FeatureConfiguration> PieceGeneratorSupplier<C> simple(Predicate<Context<C>> $$0, PieceGenerator<C> $$1) {
/* 24 */     Optional<PieceGenerator<C>> $$2 = Optional.of($$1);
/* 25 */     return $$2 -> $$0.test($$2) ? $$1 : Optional.empty();
/*    */   }
/*    */   
/*    */   static <C extends FeatureConfiguration> Predicate<Context<C>> checkForBiomeOnTop(Heightmap.Types $$0) {
/* 29 */     return $$1 -> $$1.validBiomeOnTop($$0);
/*    */   } Optional<PieceGenerator<C>> createGenerator(Context<C> paramContext);
/*    */   public static final class Context<C extends FeatureConfiguration> extends Record { private final ChunkGenerator chunkGenerator; private final BiomeSource biomeSource; private final RandomState randomState; private final long seed; private final ChunkPos chunkPos; private final C config; private final LevelHeightAccessor heightAccessor; private final Predicate<Holder<Biome>> validBiome; private final StructureTemplateManager structureTemplateManager; private final RegistryAccess registryAccess;
/* 32 */     public Context(ChunkGenerator $$0, BiomeSource $$1, RandomState $$2, long $$3, ChunkPos $$4, C $$5, LevelHeightAccessor $$6, Predicate<Holder<Biome>> $$7, StructureTemplateManager $$8, RegistryAccess $$9) { this.chunkGenerator = $$0; this.biomeSource = $$1; this.randomState = $$2; this.seed = $$3; this.chunkPos = $$4; this.config = $$5; this.heightAccessor = $$6; this.validBiome = $$7; this.structureTemplateManager = $$8; this.registryAccess = $$9; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/structure/pieces/PieceGeneratorSupplier$Context;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #32	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/structure/pieces/PieceGeneratorSupplier$Context;
/*    */       // Local variable type table:
/*    */       //   start	length	slot	name	signature
/* 32 */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/structure/pieces/PieceGeneratorSupplier$Context<TC;>; } public ChunkGenerator chunkGenerator() { return this.chunkGenerator; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/structure/pieces/PieceGeneratorSupplier$Context;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #32	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/structure/pieces/PieceGeneratorSupplier$Context;
/*    */       // Local variable type table:
/*    */       //   start	length	slot	name	signature
/*    */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/structure/pieces/PieceGeneratorSupplier$Context<TC;>; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/structure/pieces/PieceGeneratorSupplier$Context;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #32	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/world/level/levelgen/structure/pieces/PieceGeneratorSupplier$Context;
/*    */       //   0	8	1	$$0	Ljava/lang/Object;
/*    */       // Local variable type table:
/*    */       //   start	length	slot	name	signature
/* 32 */       //   0	8	0	this	Lnet/minecraft/world/level/levelgen/structure/pieces/PieceGeneratorSupplier$Context<TC;>; } public BiomeSource biomeSource() { return this.biomeSource; } public RandomState randomState() { return this.randomState; } public long seed() { return this.seed; } public ChunkPos chunkPos() { return this.chunkPos; } public C config() { return this.config; } public LevelHeightAccessor heightAccessor() { return this.heightAccessor; } public Predicate<Holder<Biome>> validBiome() { return this.validBiome; } public StructureTemplateManager structureTemplateManager() { return this.structureTemplateManager; } public RegistryAccess registryAccess() { return this.registryAccess; }
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
/*    */     public boolean validBiomeOnTop(Heightmap.Types $$0) {
/* 49 */       int $$1 = this.chunkPos.getMiddleBlockX();
/* 50 */       int $$2 = this.chunkPos.getMiddleBlockZ();
/* 51 */       int $$3 = this.chunkGenerator.getFirstOccupiedHeight($$1, $$2, $$0, this.heightAccessor, this.randomState);
/* 52 */       Holder<Biome> $$4 = this.chunkGenerator.getBiomeSource().getNoiseBiome(QuartPos.fromBlock($$1), QuartPos.fromBlock($$3), QuartPos.fromBlock($$2), this.randomState.sampler());
/* 53 */       return this.validBiome.test($$4);
/*    */     } }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\pieces\PieceGeneratorSupplier.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */