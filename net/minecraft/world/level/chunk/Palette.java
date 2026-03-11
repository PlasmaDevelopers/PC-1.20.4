package net.minecraft.world.level.chunk;

import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.IdMap;
import net.minecraft.network.FriendlyByteBuf;

public interface Palette<T> {
  int idFor(T paramT);
  
  boolean maybeHas(Predicate<T> paramPredicate);
  
  T valueFor(int paramInt);
  
  void read(FriendlyByteBuf paramFriendlyByteBuf);
  
  void write(FriendlyByteBuf paramFriendlyByteBuf);
  
  int getSerializedSize();
  
  int getSize();
  
  Palette<T> copy();
  
  public static interface Factory {
    <A> Palette<A> create(int param1Int, IdMap<A> param1IdMap, PaletteResize<A> param1PaletteResize, List<A> param1List);
  }
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\chunk\Palette.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */