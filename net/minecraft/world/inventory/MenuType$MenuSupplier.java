package net.minecraft.world.inventory;

import net.minecraft.world.entity.player.Inventory;

interface MenuSupplier<T extends AbstractContainerMenu> {
  T create(int paramInt, Inventory paramInventory);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\inventory\MenuType$MenuSupplier.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */