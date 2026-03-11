package net.minecraft.world.entity.ai.behavior;

import net.minecraft.server.level.ServerLevel;

public interface BehaviorControl<E extends net.minecraft.world.entity.LivingEntity> {
  Behavior.Status getStatus();
  
  boolean tryStart(ServerLevel paramServerLevel, E paramE, long paramLong);
  
  void tickOrStop(ServerLevel paramServerLevel, E paramE, long paramLong);
  
  void doStop(ServerLevel paramServerLevel, E paramE, long paramLong);
  
  String debugString();
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\BehaviorControl.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */