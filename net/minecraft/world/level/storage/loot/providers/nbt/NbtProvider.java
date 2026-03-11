package net.minecraft.world.level.storage.loot.providers.nbt;

import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;

public interface NbtProvider {
  @Nullable
  Tag get(LootContext paramLootContext);
  
  Set<LootContextParam<?>> getReferencedContextParams();
  
  LootNbtProviderType getType();
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\providers\nbt\NbtProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */