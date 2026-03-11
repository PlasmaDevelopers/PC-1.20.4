package net.minecraft.client.renderer.item;

import javax.annotation.Nullable;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

@Deprecated
public interface ItemPropertyFunction {
  float call(ItemStack paramItemStack, @Nullable ClientLevel paramClientLevel, @Nullable LivingEntity paramLivingEntity, int paramInt);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\item\ItemPropertyFunction.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */