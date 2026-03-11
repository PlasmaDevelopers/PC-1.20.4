package net.minecraft.world.level.levelgen.structure.structures;

import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;

interface MonumentRoomFitter {
  boolean fits(OceanMonumentPieces.RoomDefinition paramRoomDefinition);
  
  OceanMonumentPieces.OceanMonumentPiece create(Direction paramDirection, OceanMonumentPieces.RoomDefinition paramRoomDefinition, RandomSource paramRandomSource);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\structures\OceanMonumentPieces$MonumentRoomFitter.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */