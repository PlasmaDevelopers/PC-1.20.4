package net.minecraft.world.level.entity;

import java.util.UUID;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;

public interface EntityAccess {
  int getId();
  
  UUID getUUID();
  
  BlockPos blockPosition();
  
  AABB getBoundingBox();
  
  void setLevelCallback(EntityInLevelCallback paramEntityInLevelCallback);
  
  Stream<? extends EntityAccess> getSelfAndPassengers();
  
  Stream<? extends EntityAccess> getPassengersAndSelf();
  
  void setRemoved(Entity.RemovalReason paramRemovalReason);
  
  boolean shouldBeSaved();
  
  boolean isAlwaysTicking();
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\entity\EntityAccess.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */