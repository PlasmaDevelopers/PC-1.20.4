package net.minecraft.core;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;

public interface DefaultedRegistry<T> extends Registry<T> {
  @Nonnull
  ResourceLocation getKey(T paramT);
  
  @Nonnull
  T get(@Nullable ResourceLocation paramResourceLocation);
  
  @Nonnull
  T byId(int paramInt);
  
  ResourceLocation getDefaultKey();
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\DefaultedRegistry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */