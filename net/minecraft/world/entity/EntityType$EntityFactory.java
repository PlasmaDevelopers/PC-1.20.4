package net.minecraft.world.entity;

import net.minecraft.world.level.Level;

public interface EntityFactory<T extends Entity> {
  T create(EntityType<T> paramEntityType, Level paramLevel);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\EntityType$EntityFactory.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */