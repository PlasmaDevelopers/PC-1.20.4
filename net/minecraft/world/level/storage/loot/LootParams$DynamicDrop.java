package net.minecraft.world.level.storage.loot;

import java.util.function.Consumer;
import net.minecraft.world.item.ItemStack;

@FunctionalInterface
public interface DynamicDrop {
  void add(Consumer<ItemStack> paramConsumer);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\LootParams$DynamicDrop.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */