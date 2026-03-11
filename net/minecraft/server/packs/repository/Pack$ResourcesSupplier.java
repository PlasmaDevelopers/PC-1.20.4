package net.minecraft.server.packs.repository;

import net.minecraft.server.packs.PackResources;

public interface ResourcesSupplier {
  PackResources openPrimary(String paramString);
  
  PackResources openFull(String paramString, Pack.Info paramInfo);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\repository\Pack$ResourcesSupplier.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */