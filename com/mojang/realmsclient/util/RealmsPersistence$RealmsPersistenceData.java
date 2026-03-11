package com.mojang.realmsclient.util;

import com.google.gson.annotations.SerializedName;
import com.mojang.realmsclient.dto.ReflectionBasedSerialization;

public class RealmsPersistenceData implements ReflectionBasedSerialization {
  @SerializedName("newsLink")
  public String newsLink;
  
  @SerializedName("hasUnreadNews")
  public boolean hasUnreadNews;
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclien\\util\RealmsPersistence$RealmsPersistenceData.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */