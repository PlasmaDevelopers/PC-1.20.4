package net.minecraft.world.level.block.entity;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

@FunctionalInterface
interface ResonationEndAction {
  void run(Level paramLevel, BlockPos paramBlockPos, List<LivingEntity> paramList);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\BellBlockEntity$ResonationEndAction.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */