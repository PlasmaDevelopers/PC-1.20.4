package net.minecraft.world.inventory;

import net.minecraft.world.item.ItemStack;

public interface ContainerListener {
  void slotChanged(AbstractContainerMenu paramAbstractContainerMenu, int paramInt, ItemStack paramItemStack);
  
  void dataChanged(AbstractContainerMenu paramAbstractContainerMenu, int paramInt1, int paramInt2);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\inventory\ContainerListener.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */