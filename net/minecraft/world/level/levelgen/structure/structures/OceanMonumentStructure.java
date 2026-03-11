/*    */ package net.minecraft.world.level.levelgen.structure.structures;
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.Objects;
/*    */ import java.util.Optional;
/*    */ import java.util.Set;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.tags.BiomeTags;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.ChunkPos;
/*    */ import net.minecraft.world.level.biome.Biome;
/*    */ import net.minecraft.world.level.levelgen.Heightmap;
/*    */ import net.minecraft.world.level.levelgen.LegacyRandomSource;
/*    */ import net.minecraft.world.level.levelgen.RandomSupport;
/*    */ import net.minecraft.world.level.levelgen.WorldgenRandom;
/*    */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*    */ import net.minecraft.world.level.levelgen.structure.Structure;
/*    */ import net.minecraft.world.level.levelgen.structure.StructurePiece;
/*    */ import net.minecraft.world.level.levelgen.structure.StructureType;
/*    */ import net.minecraft.world.level.levelgen.structure.pieces.PiecesContainer;
/*    */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
/*    */ 
/*    */ public class OceanMonumentStructure extends Structure {
/* 25 */   public static final Codec<OceanMonumentStructure> CODEC = simpleCodec(OceanMonumentStructure::new);
/*    */   
/*    */   public OceanMonumentStructure(Structure.StructureSettings $$0) {
/* 28 */     super($$0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext $$0) {
/* 35 */     int $$1 = $$0.chunkPos().getBlockX(9);
/* 36 */     int $$2 = $$0.chunkPos().getBlockZ(9);
/*    */     
/* 38 */     Set<Holder<Biome>> $$3 = $$0.biomeSource().getBiomesWithin($$1, $$0.chunkGenerator().getSeaLevel(), $$2, 29, $$0.randomState().sampler());
/* 39 */     for (Holder<Biome> $$4 : $$3) {
/* 40 */       if (!$$4.is(BiomeTags.REQUIRED_OCEAN_MONUMENT_SURROUNDING)) {
/* 41 */         return Optional.empty();
/*    */       }
/*    */     } 
/*    */     
/* 45 */     return onTopOfChunkCenter($$0, Heightmap.Types.OCEAN_FLOOR_WG, $$1 -> generatePieces($$1, $$0));
/*    */   }
/*    */   
/*    */   private static StructurePiece createTopPiece(ChunkPos $$0, WorldgenRandom $$1) {
/* 49 */     int $$2 = $$0.getMinBlockX() - 29;
/* 50 */     int $$3 = $$0.getMinBlockZ() - 29;
/* 51 */     Direction $$4 = Direction.Plane.HORIZONTAL.getRandomDirection((RandomSource)$$1);
/* 52 */     return new OceanMonumentPieces.MonumentBuilding((RandomSource)$$1, $$2, $$3, $$4);
/*    */   }
/*    */   
/*    */   private static void generatePieces(StructurePiecesBuilder $$0, Structure.GenerationContext $$1) {
/* 56 */     $$0.addPiece(createTopPiece($$1.chunkPos(), $$1.random()));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static PiecesContainer regeneratePiecesAfterLoad(ChunkPos $$0, long $$1, PiecesContainer $$2) {
/* 62 */     if ($$2.isEmpty()) {
/* 63 */       return $$2;
/*    */     }
/* 65 */     WorldgenRandom $$3 = new WorldgenRandom((RandomSource)new LegacyRandomSource(RandomSupport.generateUniqueSeed()));
/* 66 */     $$3.setLargeFeatureSeed($$1, $$0.x, $$0.z);
/*    */     
/* 68 */     StructurePiece $$4 = $$2.pieces().get(0);
/* 69 */     BoundingBox $$5 = $$4.getBoundingBox();
/*    */ 
/*    */     
/* 72 */     int $$6 = $$5.minX();
/* 73 */     int $$7 = $$5.minZ();
/* 74 */     Direction $$8 = Direction.Plane.HORIZONTAL.getRandomDirection((RandomSource)$$3);
/* 75 */     Direction $$9 = Objects.<Direction>requireNonNullElse($$4.getOrientation(), $$8);
/*    */     
/* 77 */     StructurePiece $$10 = new OceanMonumentPieces.MonumentBuilding((RandomSource)$$3, $$6, $$7, $$9);
/* 78 */     StructurePiecesBuilder $$11 = new StructurePiecesBuilder();
/* 79 */     $$11.addPiece($$10);
/* 80 */     return $$11.build();
/*    */   }
/*    */ 
/*    */   
/*    */   public StructureType<?> type() {
/* 85 */     return StructureType.OCEAN_MONUMENT;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\structures\OceanMonumentStructure.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */