/*     */ package net.minecraft.world.level.levelgen.structure.structures;
/*     */ 
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.StructureManager;
/*     */ import net.minecraft.world.level.WorldGenLevel;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*     */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*     */ import net.minecraft.world.level.levelgen.structure.StructurePiece;
/*     */ import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
/*     */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
/*     */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
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
/*     */ 
/*     */ 
/*     */ public class FillerCorridor
/*     */   extends StrongholdPieces.StrongholdPiece
/*     */ {
/*     */   private final int steps;
/*     */   
/*     */   public FillerCorridor(int $$0, BoundingBox $$1, Direction $$2) {
/* 361 */     super(StructurePieceType.STRONGHOLD_FILLER_CORRIDOR, $$0, $$1);
/*     */     
/* 363 */     setOrientation($$2);
/* 364 */     this.steps = ($$2 == Direction.NORTH || $$2 == Direction.SOUTH) ? $$1.getZSpan() : $$1.getXSpan();
/*     */   }
/*     */   
/*     */   public FillerCorridor(CompoundTag $$0) {
/* 368 */     super(StructurePieceType.STRONGHOLD_FILLER_CORRIDOR, $$0);
/* 369 */     this.steps = $$0.getInt("Steps");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addAdditionalSaveData(StructurePieceSerializationContext $$0, CompoundTag $$1) {
/* 374 */     super.addAdditionalSaveData($$0, $$1);
/* 375 */     $$1.putInt("Steps", this.steps);
/*     */   }
/*     */   
/*     */   public static BoundingBox findPieceBox(StructurePieceAccessor $$0, RandomSource $$1, int $$2, int $$3, int $$4, Direction $$5) {
/* 379 */     int $$6 = 3;
/*     */     
/* 381 */     BoundingBox $$7 = BoundingBox.orientBox($$2, $$3, $$4, -1, -1, 0, 5, 5, 4, $$5);
/*     */     
/* 383 */     StructurePiece $$8 = $$0.findCollisionPiece($$7);
/* 384 */     if ($$8 == null)
/*     */     {
/* 386 */       return null;
/*     */     }
/*     */     
/* 389 */     if ($$8.getBoundingBox().minY() == $$7.minY())
/*     */     {
/* 391 */       for (int $$9 = 2; $$9 >= 1; $$9--) {
/* 392 */         $$7 = BoundingBox.orientBox($$2, $$3, $$4, -1, -1, 0, 5, 5, $$9, $$5);
/* 393 */         if (!$$8.getBoundingBox().intersects($$7))
/*     */         {
/*     */           
/* 396 */           return BoundingBox.orientBox($$2, $$3, $$4, -1, -1, 0, 5, 5, $$9 + 1, $$5);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 401 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/* 407 */     for (int $$7 = 0; $$7 < this.steps; $$7++) {
/*     */       
/* 409 */       placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 0, 0, $$7, $$4);
/* 410 */       placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 1, 0, $$7, $$4);
/* 411 */       placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 2, 0, $$7, $$4);
/* 412 */       placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 3, 0, $$7, $$4);
/* 413 */       placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 4, 0, $$7, $$4);
/*     */       
/* 415 */       for (int $$8 = 1; $$8 <= 3; $$8++) {
/* 416 */         placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 0, $$8, $$7, $$4);
/* 417 */         placeBlock($$0, Blocks.CAVE_AIR.defaultBlockState(), 1, $$8, $$7, $$4);
/* 418 */         placeBlock($$0, Blocks.CAVE_AIR.defaultBlockState(), 2, $$8, $$7, $$4);
/* 419 */         placeBlock($$0, Blocks.CAVE_AIR.defaultBlockState(), 3, $$8, $$7, $$4);
/* 420 */         placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 4, $$8, $$7, $$4);
/*     */       } 
/*     */       
/* 423 */       placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 0, 4, $$7, $$4);
/* 424 */       placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 1, 4, $$7, $$4);
/* 425 */       placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 2, 4, $$7, $$4);
/* 426 */       placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 3, 4, $$7, $$4);
/* 427 */       placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 4, 4, $$7, $$4);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\structures\StrongholdPieces$FillerCorridor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */