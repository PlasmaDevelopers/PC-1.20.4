package net.minecraft.server;

@FunctionalInterface
public interface WorldDataSupplier<D> {
  WorldLoader.DataLoadOutput<D> get(WorldLoader.DataLoadContext paramDataLoadContext);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\WorldLoader$WorldDataSupplier.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */