/*    */ package com.mojang.realmsclient.util;
/*    */ 
/*    */ import com.google.gson.annotations.SerializedName;
/*    */ import com.mojang.logging.LogUtils;
/*    */ import com.mojang.realmsclient.dto.GuardedSerializer;
/*    */ import com.mojang.realmsclient.dto.ReflectionBasedSerialization;
/*    */ import java.nio.charset.StandardCharsets;
/*    */ import java.nio.file.Files;
/*    */ import java.nio.file.NoSuchFileException;
/*    */ import java.nio.file.Path;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class RealmsPersistence
/*    */ {
/*    */   private static final String FILE_NAME = "realms_persistence.json";
/* 17 */   private static final GuardedSerializer GSON = new GuardedSerializer();
/* 18 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/*    */   public RealmsPersistenceData read() {
/* 21 */     return readFile();
/*    */   }
/*    */   
/*    */   public void save(RealmsPersistenceData $$0) {
/* 25 */     writeFile($$0);
/*    */   }
/*    */   
/*    */   public static RealmsPersistenceData readFile() {
/* 29 */     Path $$0 = getPathToData();
/*    */ 
/*    */     
/* 32 */     try { String $$1 = Files.readString($$0, StandardCharsets.UTF_8);
/* 33 */       RealmsPersistenceData $$2 = (RealmsPersistenceData)GSON.fromJson($$1, RealmsPersistenceData.class);
/*    */       
/* 35 */       if ($$2 != null) {
/* 36 */         return $$2;
/*    */       } }
/* 38 */     catch (NoSuchFileException noSuchFileException) {  }
/* 39 */     catch (Exception $$3)
/* 40 */     { LOGGER.warn("Failed to read Realms storage {}", $$0, $$3); }
/*    */ 
/*    */     
/* 43 */     return new RealmsPersistenceData();
/*    */   }
/*    */   
/*    */   public static void writeFile(RealmsPersistenceData $$0) {
/* 47 */     Path $$1 = getPathToData();
/*    */     
/*    */     try {
/* 50 */       Files.writeString($$1, GSON.toJson($$0), StandardCharsets.UTF_8, new java.nio.file.OpenOption[0]);
/* 51 */     } catch (Exception exception) {}
/*    */   }
/*    */ 
/*    */   
/*    */   private static Path getPathToData() {
/* 56 */     return (Minecraft.getInstance()).gameDirectory.toPath().resolve("realms_persistence.json");
/*    */   }
/*    */   
/*    */   public static class RealmsPersistenceData implements ReflectionBasedSerialization {
/*    */     @SerializedName("newsLink")
/*    */     public String newsLink;
/*    */     @SerializedName("hasUnreadNews")
/*    */     public boolean hasUnreadNews;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclien\\util\RealmsPersistence.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */