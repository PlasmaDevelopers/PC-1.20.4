package net.minecraft.world.level.storage.loot;

@FunctionalInterface
public interface Validator<T> {
  void run(ValidationContext paramValidationContext, LootDataId<T> paramLootDataId, T paramT);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\LootDataType$Validator.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */