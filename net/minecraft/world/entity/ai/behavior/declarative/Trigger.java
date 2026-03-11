package net.minecraft.world.entity.ai.behavior.declarative;

import net.minecraft.server.level.ServerLevel;

public interface Trigger<E extends net.minecraft.world.entity.LivingEntity> {
  boolean trigger(ServerLevel paramServerLevel, E paramE, long paramLong);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\declarative\Trigger.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */