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
/*     */ import net.minecraft.world.level.block.FenceBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*     */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*     */ import net.minecraft.world.level.levelgen.structure.StructurePiece;
/*     */ import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
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
/*     */ public class BridgeStraight
/*     */   extends NetherFortressPieces.NetherBridgePiece
/*     */ {
/*     */   private static final int WIDTH = 5;
/*     */   private static final int HEIGHT = 10;
/*     */   private static final int DEPTH = 19;
/*     */   
/*     */   public BridgeStraight(int $$0, RandomSource $$1, BoundingBox $$2, Direction $$3) {
/* 284 */     super(StructurePieceType.NETHER_FORTRESS_BRIDGE_STRAIGHT, $$0, $$2);
/*     */     
/* 286 */     setOrientation($$3);
/*     */   }
/*     */   
/*     */   public BridgeStraight(CompoundTag $$0) {
/* 290 */     super(StructurePieceType.NETHER_FORTRESS_BRIDGE_STRAIGHT, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addChildren(StructurePiece $$0, StructurePieceAccessor $$1, RandomSource $$2) {
/* 295 */     generateChildForward((NetherFortressPieces.StartPiece)$$0, $$1, $$2, 1, 3, false);
/*     */   }
/*     */   
/*     */   public static BridgeStraight createPiece(StructurePieceAccessor $$0, RandomSource $$1, int $$2, int $$3, int $$4, Direction $$5, int $$6) {
/* 299 */     BoundingBox $$7 = BoundingBox.orientBox($$2, $$3, $$4, -1, -3, 0, 5, 10, 19, $$5);
/*     */     
/* 301 */     if (!isOkBox($$7) || $$0.findCollisionPiece($$7) != null) {
/* 302 */       return null;
/*     */     }
/*     */     
/* 305 */     return new BridgeStraight($$6, $$1, $$7, $$5);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/* 311 */     generateBox($$0, $$4, 0, 3, 0, 4, 4, 18, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*     */     
/* 313 */     generateBox($$0, $$4, 1, 5, 0, 3, 7, 18, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
/*     */ 
/*     */     
/* 316 */     generateBox($$0, $$4, 0, 5, 0, 0, 5, 18, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/* 317 */     generateBox($$0, $$4, 4, 5, 0, 4, 5, 18, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*     */ 
/*     */     
/* 320 */     generateBox($$0, $$4, 0, 2, 0, 4, 2, 5, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/* 321 */     generateBox($$0, $$4, 0, 2, 13, 4, 2, 18, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/* 322 */     generateBox($$0, $$4, 0, 0, 0, 4, 1, 3, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/* 323 */     generateBox($$0, $$4, 0, 0, 15, 4, 1, 18, Blocks.NETHER_BRICKS.defaultBlockState(), Blocks.NETHER_BRICKS.defaultBlockState(), false);
/*     */     
/* 325 */     for (int $$7 = 0; $$7 <= 4; $$7++) {
/* 326 */       for (int $$8 = 0; $$8 <= 2; $$8++) {
/* 327 */         fillColumnDown($$0, Blocks.NETHER_BRICKS.defaultBlockState(), $$7, -1, $$8, $$4);
/* 328 */         fillColumnDown($$0, Blocks.NETHER_BRICKS.defaultBlockState(), $$7, -1, 18 - $$8, $$4);
/*     */       } 
/*     */     } 
/*     */     
/* 332 */     BlockState $$9 = (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.defaultBlockState().setValue((Property)FenceBlock.NORTH, Boolean.valueOf(true))).setValue((Property)FenceBlock.SOUTH, Boolean.valueOf(true));
/* 333 */     BlockState $$10 = (BlockState)$$9.setValue((Property)FenceBlock.EAST, Boolean.valueOf(true));
/* 334 */     BlockState $$11 = (BlockState)$$9.setValue((Property)FenceBlock.WEST, Boolean.valueOf(true));
/* 335 */     generateBox($$0, $$4, 0, 1, 1, 0, 4, 1, $$10, $$10, false);
/* 336 */     generateBox($$0, $$4, 0, 3, 4, 0, 4, 4, $$10, $$10, false);
/* 337 */     generateBox($$0, $$4, 0, 3, 14, 0, 4, 14, $$10, $$10, false);
/* 338 */     generateBox($$0, $$4, 0, 1, 17, 0, 4, 17, $$10, $$10, false);
/* 339 */     generateBox($$0, $$4, 4, 1, 1, 4, 4, 1, $$11, $$11, false);
/* 340 */     generateBox($$0, $$4, 4, 3, 4, 4, 4, 4, $$11, $$11, false);
/* 341 */     generateBox($$0, $$4, 4, 3, 14, 4, 4, 14, $$11, $$11, false);
/* 342 */     generateBox($$0, $$4, 4, 1, 17, 4, 4, 17, $$11, $$11, false);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\structures\NetherFortressPieces$BridgeStraight.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */