package net.minecraft.world.level.levelgen;

public interface ContextProvider {
  DensityFunction.FunctionContext forIndex(int paramInt);
  
  void fillAllDirectly(double[] paramArrayOfdouble, DensityFunction paramDensityFunction);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\DensityFunction$ContextProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */