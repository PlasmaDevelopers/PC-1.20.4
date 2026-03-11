package net.minecraft.data.models;

import com.google.gson.JsonElement;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import net.minecraft.data.models.blockstates.BlockStateGenerator;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

@FunctionalInterface
interface BlockStateGeneratorSupplier {
  BlockStateGenerator create(Block paramBlock, ResourceLocation paramResourceLocation, TextureMapping paramTextureMapping, BiConsumer<ResourceLocation, Supplier<JsonElement>> paramBiConsumer);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\models\BlockModelGenerators$BlockStateGeneratorSupplier.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */