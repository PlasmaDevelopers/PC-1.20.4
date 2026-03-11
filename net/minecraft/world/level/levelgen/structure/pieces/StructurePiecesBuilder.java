/*    */ package net.minecraft.world.level.levelgen.structure.pieces;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*    */ import net.minecraft.world.level.levelgen.structure.StructurePiece;
/*    */ import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
/*    */ 
/*    */ public class StructurePiecesBuilder
/*    */   implements StructurePieceAccessor {
/* 13 */   private final List<StructurePiece> pieces = Lists.newArrayList();
/*    */ 
/*    */   
/*    */   public void addPiece(StructurePiece $$0) {
/* 17 */     this.pieces.add($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public StructurePiece findCollisionPiece(BoundingBox $$0) {
/* 23 */     return StructurePiece.findCollisionPiece(this.pieces, $$0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public void offsetPiecesVertically(int $$0) {
/* 31 */     for (StructurePiece $$1 : this.pieces) {
/* 32 */       $$1.move(0, $$0, 0);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public int moveBelowSeaLevel(int $$0, int $$1, RandomSource $$2, int $$3) {
/* 41 */     int $$4 = $$0 - $$3;
/*    */ 
/*    */     
/* 44 */     BoundingBox $$5 = getBoundingBox();
/* 45 */     int $$6 = $$5.getYSpan() + $$1 + 1;
/*    */     
/* 47 */     if ($$6 < $$4) {
/* 48 */       $$6 += $$2.nextInt($$4 - $$6);
/*    */     }
/*    */ 
/*    */     
/* 52 */     int $$7 = $$6 - $$5.maxY();
/* 53 */     offsetPiecesVertically($$7);
/* 54 */     return $$7;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void moveInsideHeights(RandomSource $$0, int $$1, int $$2) {
/*    */     int $$6;
/* 61 */     BoundingBox $$3 = getBoundingBox();
/* 62 */     int $$4 = $$2 - $$1 + 1 - $$3.getYSpan();
/*    */ 
/*    */     
/* 65 */     if ($$4 > 1) {
/* 66 */       int $$5 = $$1 + $$0.nextInt($$4);
/*    */     } else {
/* 68 */       $$6 = $$1;
/*    */     } 
/*    */ 
/*    */     
/* 72 */     int $$7 = $$6 - $$3.minY();
/* 73 */     offsetPiecesVertically($$7);
/*    */   }
/*    */   
/*    */   public PiecesContainer build() {
/* 77 */     return new PiecesContainer(this.pieces);
/*    */   }
/*    */ 
/*    */   
/*    */   public void clear() {
/* 82 */     this.pieces.clear();
/*    */   }
/*    */   
/*    */   public boolean isEmpty() {
/* 86 */     return this.pieces.isEmpty();
/*    */   }
/*    */   
/*    */   public BoundingBox getBoundingBox() {
/* 90 */     return StructurePiece.createBoundingBox(this.pieces.stream());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\pieces\StructurePiecesBuilder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */