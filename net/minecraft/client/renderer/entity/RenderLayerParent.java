package net.minecraft.client.renderer.entity;

import net.minecraft.resources.ResourceLocation;

public interface RenderLayerParent<T extends net.minecraft.world.entity.Entity, M extends net.minecraft.client.model.EntityModel<T>> {
  M getModel();
  
  ResourceLocation getTextureLocation(T paramT);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\RenderLayerParent.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */