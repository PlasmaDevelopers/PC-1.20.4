package net.minecraft.server.packs;

import java.io.InputStream;
import java.util.function.BiConsumer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.IoSupplier;

@FunctionalInterface
public interface ResourceOutput extends BiConsumer<ResourceLocation, IoSupplier<InputStream>> {}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\PackResources$ResourceOutput.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */