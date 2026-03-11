package net.minecraft.client.resources.model;

import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;

public interface ModelBaker {
  UnbakedModel getModel(ResourceLocation paramResourceLocation);
  
  @Nullable
  BakedModel bake(ResourceLocation paramResourceLocation, ModelState paramModelState);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\model\ModelBaker.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */