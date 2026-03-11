package net.minecraft.world.level.border;

import net.minecraft.world.phys.shapes.VoxelShape;

interface BorderExtent {
  double getMinX();
  
  double getMaxX();
  
  double getMinZ();
  
  double getMaxZ();
  
  double getSize();
  
  double getLerpSpeed();
  
  long getLerpRemainingTime();
  
  double getLerpTarget();
  
  BorderStatus getStatus();
  
  void onAbsoluteMaxSizeChange();
  
  void onCenterChange();
  
  BorderExtent update();
  
  VoxelShape getCollisionShape();
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\border\WorldBorder$BorderExtent.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */