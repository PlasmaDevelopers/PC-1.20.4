package net.minecraft.client.renderer.entity.layers;

import java.util.List;
import net.minecraft.client.model.geom.ModelPart;

public interface DrawSelector<T extends net.minecraft.world.entity.monster.warden.Warden, M extends net.minecraft.client.model.EntityModel<T>> {
  List<ModelPart> getPartsToDraw(M paramM);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\layers\WardenEmissiveLayer$DrawSelector.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */