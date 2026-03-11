package net.minecraft.world.item;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

public interface DispensibleContainerItem {
  default void checkExtraContent(@Nullable Player $$0, Level $$1, ItemStack $$2, BlockPos $$3) {}
  
  boolean emptyContents(@Nullable Player paramPlayer, Level paramLevel, BlockPos paramBlockPos, @Nullable BlockHitResult paramBlockHitResult);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\DispensibleContainerItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */