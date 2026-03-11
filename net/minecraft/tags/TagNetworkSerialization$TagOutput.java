package net.minecraft.tags;

import java.util.List;
import net.minecraft.core.Holder;

@FunctionalInterface
public interface TagOutput<T> {
  void accept(TagKey<T> paramTagKey, List<Holder<T>> paramList);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\tags\TagNetworkSerialization$TagOutput.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */