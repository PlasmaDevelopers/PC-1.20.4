package net.minecraft.server.packs.metadata;

import com.google.gson.JsonObject;

public interface MetadataSectionSerializer<T> {
  String getMetadataSectionName();
  
  T fromJson(JsonObject paramJsonObject);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\metadata\MetadataSectionSerializer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */