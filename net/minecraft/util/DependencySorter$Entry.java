package net.minecraft.util;

import java.util.function.Consumer;

public interface Entry<K> {
  void visitRequiredDependencies(Consumer<K> paramConsumer);
  
  void visitOptionalDependencies(Consumer<K> paramConsumer);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\DependencySorter$Entry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */