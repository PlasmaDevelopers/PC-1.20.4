package net.minecraft;

import java.util.Date;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.level.storage.DataVersion;

public interface WorldVersion {
  DataVersion getDataVersion();
  
  String getId();
  
  String getName();
  
  int getProtocolVersion();
  
  int getPackVersion(PackType paramPackType);
  
  Date getBuildTime();
  
  boolean isStable();
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\WorldVersion.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */