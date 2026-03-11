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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StairsDown
/*     */   extends StrongholdPieces.StrongholdPiece
/*     */ {
/*     */   private static final int WIDTH = 5;
/*     */   private static final int HEIGHT = 11;
/*     */   private static final int DEPTH = 5;
/*     */   private final boolean isSource;
/*     */   
/*     */   public StairsDown(StructurePieceType $$0, int $$1, int $$2, int $$3, Direction $$4) {
/* 440 */     super($$0, $$1, makeBoundingBox($$2, 64, $$3, $$4, 5, 11, 5));
/*     */     
/* 442 */     this.isSource = true;
/* 443 */     setOrientation($$4);
/* 444 */     this.entryDoor = StrongholdPieces.StrongholdPiece.SmallDoorType.OPENING;
/*     */   }
/*     */   
/*     */   public StairsDown(int $$0, RandomSource $$1, BoundingBox $$2, Direction $$3) {
/* 448 */     super(StructurePieceType.STRONGHOLD_STAIRS_DOWN, $$0, $$2);
/*     */     
/* 450 */     this.isSource = false;
/* 451 */     setOrientation($$3);
/* 452 */     this.entryDoor = randomSmallDoor($$1);
/*     */   }
/*     */   
/*     */   public StairsDown(StructurePieceType $$0, CompoundTag $$1) {
/* 456 */     super($$0, $$1);
/* 457 */     this.isSource = $$1.getBoolean("Source");
/*     */   }
/*     */   
/*     */   public StairsDown(CompoundTag $$0) {
/* 461 */     this(StructurePieceType.STRONGHOLD_STAIRS_DOWN, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addAdditionalSaveData(StructurePieceSerializationContext $$0, CompoundTag $$1) {
/* 466 */     super.addAdditionalSaveData($$0, $$1);
/* 467 */     $$1.putBoolean("Source", this.isSource);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addChildren(StructurePiece $$0, StructurePieceAccessor $$1, RandomSource $$2) {
/* 472 */     if (this.isSource)
/*     */     {
/* 474 */       StrongholdPieces.imposedPiece = (Class)StrongholdPieces.FiveCrossing.class;
/*     */     }
/* 476 */     generateSmallDoorChildForward((StrongholdPieces.StartPiece)$$0, $$1, $$2, 1, 1);
/*     */   }
/*     */   
/*     */   public static StairsDown createPiece(StructurePieceAccessor $$0, RandomSource $$1, int $$2, int $$3, int $$4, Direction $$5, int $$6) {
/* 480 */     BoundingBox $$7 = BoundingBox.orientBox($$2, $$3, $$4, -1, -7, 0, 5, 11, 5, $$5);
/*     */     
/* 482 */     if (!isOkBox($$7) || $$0.findCollisionPiece($$7) != null) {
/* 483 */       return null;
/*     */     }
/*     */     
/* 486 */     return new StairsDown($$6, $$1, $$7, $$5);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/* 492 */     generateBox($$0, $$4, 0, 0, 0, 4, 10, 4, true, $$3, StrongholdPieces.SMOOTH_STONE_SELECTOR);
/*     */     
/* 494 */     generateSmallDoor($$0, $$3, $$4, this.entryDoor, 1, 7, 0);
/*     */     
/* 496 */     generateSmallDoor($$0, $$3, $$4, StrongholdPieces.StrongholdPiece.SmallDoorType.OPENING, 1, 1, 4);
/*     */ 
/*     */     
/* 499 */     placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 2, 6, 1, $$4);
/* 500 */     placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 1, 5, 1, $$4);
/* 501 */     placeBlock($$0, Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), 1, 6, 1, $$4);
/* 502 */     placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 1, 5, 2, $$4);
/* 503 */     placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 1, 4, 3, $$4);
/* 504 */     placeBlock($$0, Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), 1, 5, 3, $$4);
/* 505 */     placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 2, 4, 3, $$4);
/* 506 */     placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 3, 3, 3, $$4);
/* 507 */     placeBlock($$0, Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), 3, 4, 3, $$4);
/* 508 */     placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 3, 3, 2, $$4);
/* 509 */     placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 3, 2, 1, $$4);
/* 510 */     placeBlock($$0, Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), 3, 3, 1, $$4);
/* 511 */     placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 2, 2, 1, $$4);
/* 512 */     placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 1, 1, 1, $$4);
/* 513 */     placeBlock($$0, Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), 1, 2, 1, $$4);
/* 514 */     placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), 1, 1, 2, $$4);
/* 515 */     placeBlock($$0, Blocks.SMOOTH_STONE_SLAB.defaultBlockState(), 1, 1, 3, $$4);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\structures\StrongholdPieces$StairsDown.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */