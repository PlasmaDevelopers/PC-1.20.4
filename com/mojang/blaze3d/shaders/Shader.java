package com.mojang.blaze3d.shaders;

public interface Shader {
  int getId();
  
  void markDirty();
  
  Program getVertexProgram();
  
  Program getFragmentProgram();
  
  void attachToProgram();
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\shaders\Shader.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */