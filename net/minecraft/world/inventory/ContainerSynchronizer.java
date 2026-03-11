package net.minecraft.world.inventory;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;

public interface ContainerSynchronizer {
  void sendInitialData(AbstractContainerMenu paramAbstractContainerMenu, NonNullList<ItemStack> paramNonNullList, ItemStack paramItemStack, int[] paramArrayOfint);
  
  void sendSlotChange(AbstractContainerMenu paramAbstractContainerMenu, int paramInt, ItemStack paramItemStack);
  
  void sendCarriedChange(AbstractContainerMenu paramAbstractContainerMenu, ItemStack paramItemStack);
  
  void sendDataChange(AbstractContainerMenu paramAbstractContainerMenu, int paramInt1, int paramInt2);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\inventory\ContainerSynchronizer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */