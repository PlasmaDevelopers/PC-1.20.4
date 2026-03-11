package net.minecraft.client.renderer.debug;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;

public interface SimpleDebugRenderer {
  void render(PoseStack paramPoseStack, MultiBufferSource paramMultiBufferSource, double paramDouble1, double paramDouble2, double paramDouble3);
  
  default void clear() {}
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\debug\DebugRenderer$SimpleDebugRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */