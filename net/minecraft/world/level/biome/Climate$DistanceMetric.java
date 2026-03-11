package net.minecraft.world.level.biome;

interface DistanceMetric<T> {
  long distance(Climate.RTree.Node<T> paramNode, long[] paramArrayOflong);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\biome\Climate$DistanceMetric.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */