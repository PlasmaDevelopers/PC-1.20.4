package net.minecraft.world.level.levelgen.structure;

import javax.annotation.Nullable;

public interface StructurePieceAccessor {
  void addPiece(StructurePiece paramStructurePiece);
  
  @Nullable
  StructurePiece findCollisionPiece(BoundingBox paramBoundingBox);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\StructurePieceAccessor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */