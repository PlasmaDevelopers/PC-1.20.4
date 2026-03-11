package net.minecraft.data.models.blockstates;

import com.google.gson.JsonElement;
import java.util.function.Supplier;
import net.minecraft.world.level.block.Block;

public interface BlockStateGenerator extends Supplier<JsonElement> {
  Block getBlock();
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\models\blockstates\BlockStateGenerator.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */