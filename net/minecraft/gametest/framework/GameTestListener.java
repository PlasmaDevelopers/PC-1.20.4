package net.minecraft.gametest.framework;

public interface GameTestListener {
  void testStructureLoaded(GameTestInfo paramGameTestInfo);
  
  void testPassed(GameTestInfo paramGameTestInfo);
  
  void testFailed(GameTestInfo paramGameTestInfo);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\gametest\framework\GameTestListener.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */