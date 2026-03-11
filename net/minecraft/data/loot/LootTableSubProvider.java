package net.minecraft.data.loot;

import java.util.function.BiConsumer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;

@FunctionalInterface
public interface LootTableSubProvider {
  void generate(BiConsumer<ResourceLocation, LootTable.Builder> paramBiConsumer);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\loot\LootTableSubProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */