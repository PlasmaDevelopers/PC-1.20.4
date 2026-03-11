package net.minecraft.world.level.chunk;

import javax.annotation.Nullable;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LightLayer;

public interface LightChunkGetter {
  @Nullable
  LightChunk getChunkForLighting(int paramInt1, int paramInt2);
  
  default void onLightUpdate(LightLayer $$0, SectionPos $$1) {}
  
  BlockGetter getLevel();
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\chunk\LightChunkGetter.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */