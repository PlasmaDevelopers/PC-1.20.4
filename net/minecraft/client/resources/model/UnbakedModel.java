package net.minecraft.client.resources.model;

import java.util.Collection;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;

public interface UnbakedModel {
  Collection<ResourceLocation> getDependencies();
  
  void resolveParents(Function<ResourceLocation, UnbakedModel> paramFunction);
  
  @Nullable
  BakedModel bake(ModelBaker paramModelBaker, Function<Material, TextureAtlasSprite> paramFunction, ModelState paramModelState, ResourceLocation paramResourceLocation);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\model\UnbakedModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */