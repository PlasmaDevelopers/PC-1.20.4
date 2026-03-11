package net.minecraft.server;

import net.minecraft.server.dedicated.DedicatedServerProperties;

public interface ServerInterface extends ServerInfo {
  DedicatedServerProperties getProperties();
  
  String getServerIp();
  
  int getServerPort();
  
  String getServerName();
  
  String[] getPlayerNames();
  
  String getLevelIdName();
  
  String getPluginNames();
  
  String runCommand(String paramString);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\ServerInterface.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */