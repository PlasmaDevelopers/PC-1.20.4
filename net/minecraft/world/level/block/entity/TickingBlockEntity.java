package net.minecraft.world.level.block.entity;

import net.minecraft.core.BlockPos;

public interface TickingBlockEntity {
  void tick();
  
  boolean isRemoved();
  
  BlockPos getPos();
  
  String getType();
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\TickingBlockEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */