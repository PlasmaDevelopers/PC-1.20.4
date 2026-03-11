package net.minecraft.world.level.chunk;

import java.util.List;
import net.minecraft.core.IdMap;

public interface Factory {
  <A> Palette<A> create(int paramInt, IdMap<A> paramIdMap, PaletteResize<A> paramPaletteResize, List<A> paramList);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\chunk\Palette$Factory.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */