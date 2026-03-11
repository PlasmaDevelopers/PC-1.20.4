package net.minecraft.world.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;

@FunctionalInterface
public interface SpawnPredicate<T extends Entity> {
  boolean test(EntityType<T> paramEntityType, ServerLevelAccessor paramServerLevelAccessor, MobSpawnType paramMobSpawnType, BlockPos paramBlockPos, RandomSource paramRandomSource);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\SpawnPlacements$SpawnPredicate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */