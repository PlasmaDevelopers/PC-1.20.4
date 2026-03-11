/*    */ package net.minecraft.world.level.levelgen.structure.structures;
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.levelgen.structure.Structure;
/*    */ import net.minecraft.world.level.levelgen.structure.StructurePiece;
/*    */ import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
/*    */ import net.minecraft.world.level.levelgen.structure.StructureType;
/*    */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
/*    */ 
/*    */ public class StrongholdStructure extends Structure {
/* 13 */   public static final Codec<StrongholdStructure> CODEC = simpleCodec(StrongholdStructure::new);
/*    */   
/*    */   public StrongholdStructure(Structure.StructureSettings $$0) {
/* 16 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext $$0) {
/* 21 */     return Optional.of(new Structure.GenerationStub($$0.chunkPos().getWorldPosition(), $$1 -> generatePieces($$1, $$0)));
/*    */   }
/*    */   private static void generatePieces(StructurePiecesBuilder $$0, Structure.GenerationContext $$1) {
/*    */     StrongholdPieces.StartPiece $$3;
/* 25 */     int $$2 = 0;
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     do {
/* 31 */       $$0.clear();
/* 32 */       $$1.random().setLargeFeatureSeed($$1.seed() + $$2++, ($$1.chunkPos()).x, ($$1.chunkPos()).z);
/* 33 */       StrongholdPieces.resetPieces();
/*    */       
/* 35 */       $$3 = new StrongholdPieces.StartPiece((RandomSource)$$1.random(), $$1.chunkPos().getBlockX(2), $$1.chunkPos().getBlockZ(2));
/* 36 */       $$0.addPiece($$3);
/* 37 */       $$3.addChildren($$3, (StructurePieceAccessor)$$0, (RandomSource)$$1.random());
/*    */       
/* 39 */       List<StructurePiece> $$4 = $$3.pendingChildren;
/* 40 */       while (!$$4.isEmpty()) {
/* 41 */         int $$5 = $$1.random().nextInt($$4.size());
/* 42 */         StructurePiece $$6 = $$4.remove($$5);
/* 43 */         $$6.addChildren($$3, (StructurePieceAccessor)$$0, (RandomSource)$$1.random());
/*    */       } 
/*    */       
/* 46 */       $$0.moveBelowSeaLevel($$1.chunkGenerator().getSeaLevel(), $$1.chunkGenerator().getMinY(), (RandomSource)$$1.random(), 10);
/* 47 */     } while ($$0.isEmpty() || $$3.portalRoomPiece == null);
/*    */   }
/*    */ 
/*    */   
/*    */   public StructureType<?> type() {
/* 52 */     return StructureType.STRONGHOLD;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\structures\StrongholdStructure.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */