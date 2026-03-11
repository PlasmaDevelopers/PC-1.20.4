package net.minecraft.world.level.storage.loot.entries;

import java.util.List;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

@FunctionalInterface
public interface CompositeEntryConstructor<T extends CompositeEntryBase> {
  T create(List<LootPoolEntryContainer> paramList, List<LootItemCondition> paramList1);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\entries\CompositeEntryBase$CompositeEntryConstructor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */