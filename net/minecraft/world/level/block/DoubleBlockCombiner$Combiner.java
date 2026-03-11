package net.minecraft.world.level.block;

public interface Combiner<S, T> {
  T acceptDouble(S paramS1, S paramS2);
  
  T acceptSingle(S paramS);
  
  T acceptNone();
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\DoubleBlockCombiner$Combiner.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */