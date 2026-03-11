package net.minecraft.world.level.block.entity.trialspawner;

import net.minecraft.world.level.Level;

public interface StateAccessor {
  void setState(Level paramLevel, TrialSpawnerState paramTrialSpawnerState);
  
  TrialSpawnerState getState();
  
  void markUpdated();
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\trialspawner\TrialSpawner$StateAccessor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */