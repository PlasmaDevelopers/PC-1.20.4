package net.minecraft.world.entity.ai.behavior.declarative;

import javax.annotation.Nullable;
import net.minecraft.server.level.ServerLevel;

interface TriggerWithResult<E extends net.minecraft.world.entity.LivingEntity, R> {
  @Nullable
  R tryTrigger(ServerLevel paramServerLevel, E paramE, long paramLong);
  
  String debugString();
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\declarative\BehaviorBuilder$TriggerWithResult.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */