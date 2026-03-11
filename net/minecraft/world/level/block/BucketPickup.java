package net.minecraft.world.level.block;

import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

public interface BucketPickup {
  ItemStack pickupBlock(@Nullable Player paramPlayer, LevelAccessor paramLevelAccessor, BlockPos paramBlockPos, BlockState paramBlockState);
  
  Optional<SoundEvent> getPickupSound();
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\BucketPickup.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */