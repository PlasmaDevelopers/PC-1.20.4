package net.minecraft.world.inventory;

import java.util.List;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;

public interface CraftingContainer extends Container, StackedContentsCompatible {
  int getWidth();
  
  int getHeight();
  
  List<ItemStack> getItems();
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\inventory\CraftingContainer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */