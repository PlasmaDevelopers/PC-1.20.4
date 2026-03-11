package net.minecraft.world.level.levelgen;

import javax.annotation.Nullable;
import net.minecraft.world.level.block.state.BlockState;

public interface SurfaceRule {
  @Nullable
  BlockState tryApply(int paramInt1, int paramInt2, int paramInt3);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\SurfaceRules$SurfaceRule.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */