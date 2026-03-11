/*    */ package com.mojang.realmsclient.dto;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import com.mojang.logging.LogUtils;
/*    */ import com.mojang.realmsclient.util.JsonUtils;
/*    */ import javax.annotation.Nullable;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class WorldTemplate
/*    */   extends ValueObject
/*    */ {
/* 12 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/* 14 */   public String id = "";
/* 15 */   public String name = "";
/* 16 */   public String version = "";
/* 17 */   public String author = "";
/* 18 */   public String link = "";
/*    */   @Nullable
/*    */   public String image;
/* 21 */   public String trailer = "";
/* 22 */   public String recommendedPlayers = "";
/* 23 */   public WorldTemplateType type = WorldTemplateType.WORLD_TEMPLATE;
/*    */   
/*    */   public static WorldTemplate parse(JsonObject $$0) {
/* 26 */     WorldTemplate $$1 = new WorldTemplate();
/*    */     try {
/* 28 */       $$1.id = JsonUtils.getStringOr("id", $$0, "");
/* 29 */       $$1.name = JsonUtils.getStringOr("name", $$0, "");
/* 30 */       $$1.version = JsonUtils.getStringOr("version", $$0, "");
/* 31 */       $$1.author = JsonUtils.getStringOr("author", $$0, "");
/* 32 */       $$1.link = JsonUtils.getStringOr("link", $$0, "");
/* 33 */       $$1.image = JsonUtils.getStringOr("image", $$0, null);
/* 34 */       $$1.trailer = JsonUtils.getStringOr("trailer", $$0, "");
/* 35 */       $$1.recommendedPlayers = JsonUtils.getStringOr("recommendedPlayers", $$0, "");
/* 36 */       $$1.type = WorldTemplateType.valueOf(JsonUtils.getStringOr("type", $$0, WorldTemplateType.WORLD_TEMPLATE.name()));
/* 37 */     } catch (Exception $$2) {
/* 38 */       LOGGER.error("Could not parse WorldTemplate: {}", $$2.getMessage());
/*    */     } 
/* 40 */     return $$1;
/*    */   }
/*    */   
/*    */   public enum WorldTemplateType {
/* 44 */     WORLD_TEMPLATE,
/* 45 */     MINIGAME,
/* 46 */     ADVENTUREMAP,
/* 47 */     EXPERIENCE,
/* 48 */     INSPIRATION;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\dto\WorldTemplate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */