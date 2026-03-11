package net.minecraft.client.renderer.item;

import javax.annotation.Nullable;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public interface CompassTarget {
  @Nullable
  GlobalPos getPos(ClientLevel paramClientLevel, ItemStack paramItemStack, Entity paramEntity);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\item\CompassItemPropertyFunction$CompassTarget.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */