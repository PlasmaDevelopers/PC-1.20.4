package net.minecraft.gametest.framework;

public interface TestReporter {
  void onTestFailed(GameTestInfo paramGameTestInfo);
  
  void onTestSuccess(GameTestInfo paramGameTestInfo);
  
  default void finish() {}
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\gametest\framework\TestReporter.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */