package net.minecraft.world.level.levelgen.structure;

import net.minecraft.world.level.levelgen.WorldgenRandom;

@FunctionalInterface
public interface PieceConstructor {
  StructurePiece construct(WorldgenRandom paramWorldgenRandom, int paramInt1, int paramInt2);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\SinglePieceStructure$PieceConstructor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */