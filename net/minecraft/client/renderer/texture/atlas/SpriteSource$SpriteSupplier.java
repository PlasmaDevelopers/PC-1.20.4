package net.minecraft.client.renderer.texture.atlas;

import java.util.function.Function;
import net.minecraft.client.renderer.texture.SpriteContents;

public interface SpriteSupplier extends Function<SpriteResourceLoader, SpriteContents> {
  default void discard() {}
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\texture\atlas\SpriteSource$SpriteSupplier.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */