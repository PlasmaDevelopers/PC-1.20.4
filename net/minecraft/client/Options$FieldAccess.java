package net.minecraft.client;

import java.util.function.Function;

interface FieldAccess {
  <T> void process(String paramString, OptionInstance<T> paramOptionInstance);
  
  int process(String paramString, int paramInt);
  
  boolean process(String paramString, boolean paramBoolean);
  
  String process(String paramString1, String paramString2);
  
  float process(String paramString, float paramFloat);
  
  <T> T process(String paramString, T paramT, Function<String, T> paramFunction, Function<T, String> paramFunction1);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\Options$FieldAccess.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */