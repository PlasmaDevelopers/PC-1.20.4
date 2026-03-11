package net.minecraft.tags;

import java.util.Collection;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;

public interface Lookup<T> {
  @Nullable
  T element(ResourceLocation paramResourceLocation);
  
  @Nullable
  Collection<T> tag(ResourceLocation paramResourceLocation);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\tags\TagEntry$Lookup.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */