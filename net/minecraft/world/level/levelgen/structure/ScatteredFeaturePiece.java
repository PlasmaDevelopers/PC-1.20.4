/*    */ package net.minecraft.world.level.levelgen.structure;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.levelgen.Heightmap;
/*    */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
/*    */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
/*    */ 
/*    */ public abstract class ScatteredFeaturePiece extends StructurePiece {
/*    */   protected final int width;
/*    */   protected final int height;
/*    */   protected final int depth;
/* 16 */   protected int heightPosition = -1;
/*    */   
/*    */   protected ScatteredFeaturePiece(StructurePieceType $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, Direction $$7) {
/* 19 */     super($$0, 0, StructurePiece.makeBoundingBox($$1, $$2, $$3, $$7, $$4, $$5, $$6));
/*    */     
/* 21 */     this.width = $$4;
/* 22 */     this.height = $$5;
/* 23 */     this.depth = $$6;
/*    */     
/* 25 */     setOrientation($$7);
/*    */   }
/*    */   
/*    */   protected ScatteredFeaturePiece(StructurePieceType $$0, CompoundTag $$1) {
/* 29 */     super($$0, $$1);
/* 30 */     this.width = $$1.getInt("Width");
/* 31 */     this.height = $$1.getInt("Height");
/* 32 */     this.depth = $$1.getInt("Depth");
/* 33 */     this.heightPosition = $$1.getInt("HPos");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void addAdditionalSaveData(StructurePieceSerializationContext $$0, CompoundTag $$1) {
/* 38 */     $$1.putInt("Width", this.width);
/* 39 */     $$1.putInt("Height", this.height);
/* 40 */     $$1.putInt("Depth", this.depth);
/* 41 */     $$1.putInt("HPos", this.heightPosition);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean updateAverageGroundHeight(LevelAccessor $$0, BoundingBox $$1, int $$2) {
/* 46 */     if (this.heightPosition >= 0) {
/* 47 */       return true;
/*    */     }
/*    */     
/* 50 */     int $$3 = 0;
/* 51 */     int $$4 = 0;
/* 52 */     BlockPos.MutableBlockPos $$5 = new BlockPos.MutableBlockPos();
/* 53 */     for (int $$6 = this.boundingBox.minZ(); $$6 <= this.boundingBox.maxZ(); $$6++) {
/* 54 */       for (int $$7 = this.boundingBox.minX(); $$7 <= this.boundingBox.maxX(); $$7++) {
/* 55 */         $$5.set($$7, 64, $$6);
/* 56 */         if ($$1.isInside((Vec3i)$$5)) {
/* 57 */           $$3 += $$0.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (BlockPos)$$5).getY();
/* 58 */           $$4++;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 63 */     if ($$4 == 0) {
/* 64 */       return false;
/*    */     }
/* 66 */     this.heightPosition = $$3 / $$4;
/* 67 */     this.boundingBox.move(0, this.heightPosition - this.boundingBox.minY() + $$2, 0);
/* 68 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean updateHeightPositionToLowestGroundHeight(LevelAccessor $$0, int $$1) {
/* 73 */     if (this.heightPosition >= 0) {
/* 74 */       return true;
/*    */     }
/*    */     
/* 77 */     int $$2 = $$0.getMaxBuildHeight();
/* 78 */     boolean $$3 = false;
/* 79 */     BlockPos.MutableBlockPos $$4 = new BlockPos.MutableBlockPos();
/* 80 */     for (int $$5 = this.boundingBox.minZ(); $$5 <= this.boundingBox.maxZ(); $$5++) {
/* 81 */       for (int $$6 = this.boundingBox.minX(); $$6 <= this.boundingBox.maxX(); $$6++) {
/*    */         
/* 83 */         $$4.set($$6, 0, $$5);
/* 84 */         $$2 = Math.min($$2, $$0.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (BlockPos)$$4).getY());
/* 85 */         $$3 = true;
/*    */       } 
/*    */     } 
/*    */     
/* 89 */     if (!$$3) {
/* 90 */       return false;
/*    */     }
/* 92 */     this.heightPosition = $$2;
/* 93 */     this.boundingBox.move(0, this.heightPosition - this.boundingBox.minY() + $$1, 0);
/* 94 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\ScatteredFeaturePiece.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */