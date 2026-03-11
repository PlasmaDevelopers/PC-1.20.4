package net.minecraft.data;

@FunctionalInterface
public interface Factory<T extends DataProvider> {
  T create(PackOutput paramPackOutput);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\DataProvider$Factory.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */