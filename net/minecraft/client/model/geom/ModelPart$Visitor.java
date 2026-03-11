package net.minecraft.client.model.geom;

import com.mojang.blaze3d.vertex.PoseStack;

@FunctionalInterface
public interface Visitor {
  void visit(PoseStack.Pose paramPose, String paramString, int paramInt, ModelPart.Cube paramCube);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\geom\ModelPart$Visitor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */