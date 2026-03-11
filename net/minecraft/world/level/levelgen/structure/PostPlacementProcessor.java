/*    */ package net.minecraft.world.level.levelgen.structure;
/*    */ 
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.ChunkPos;
/*    */ import net.minecraft.world.level.StructureManager;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*    */ import net.minecraft.world.level.levelgen.structure.pieces.PiecesContainer;
/*    */ 
/*    */ @FunctionalInterface
/*    */ public interface PostPlacementProcessor {
/*    */   public static final PostPlacementProcessor NONE = ($$0, $$1, $$2, $$3, $$4, $$5, $$6) -> {
/*    */     
/*    */     };
/*    */   
/*    */   void afterPlace(WorldGenLevel paramWorldGenLevel, StructureManager paramStructureManager, ChunkGenerator paramChunkGenerator, RandomSource paramRandomSource, BoundingBox paramBoundingBox, ChunkPos paramChunkPos, PiecesContainer paramPiecesContainer);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\PostPlacementProcessor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */