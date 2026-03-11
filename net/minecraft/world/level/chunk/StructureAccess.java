package net.minecraft.world.level.chunk;

import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;

public interface StructureAccess {
  @Nullable
  StructureStart getStartForStructure(Structure paramStructure);
  
  void setStartForStructure(Structure paramStructure, StructureStart paramStructureStart);
  
  LongSet getReferencesForStructure(Structure paramStructure);
  
  void addReferenceForStructure(Structure paramStructure, long paramLong);
  
  Map<Structure, LongSet> getAllReferences();
  
  void setAllReferences(Map<Structure, LongSet> paramMap);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\chunk\StructureAccess.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */