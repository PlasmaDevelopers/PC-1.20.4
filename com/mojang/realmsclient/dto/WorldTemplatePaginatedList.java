/*    */ package com.mojang.realmsclient.dto;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonParser;
/*    */ import com.mojang.logging.LogUtils;
/*    */ import com.mojang.realmsclient.util.JsonUtils;
/*    */ import java.util.Collections;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class WorldTemplatePaginatedList
/*    */   extends ValueObject {
/* 16 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/*    */   public List<WorldTemplate> templates;
/*    */   public int page;
/*    */   
/*    */   public WorldTemplatePaginatedList(int $$0) {
/* 22 */     this.templates = Collections.emptyList();
/* 23 */     this.page = 0;
/* 24 */     this.size = $$0;
/* 25 */     this.total = -1;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int size;
/*    */ 
/*    */   
/*    */   public int total;
/*    */ 
/*    */ 
/*    */   
/*    */   public WorldTemplatePaginatedList() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isLastPage() {
/* 43 */     return (this.page * this.size >= this.total && this.page > 0 && this.total > 0 && this.size > 0);
/*    */   }
/*    */   
/*    */   public static WorldTemplatePaginatedList parse(String $$0) {
/* 47 */     WorldTemplatePaginatedList $$1 = new WorldTemplatePaginatedList();
/* 48 */     $$1.templates = Lists.newArrayList();
/*    */     try {
/* 50 */       JsonParser $$2 = new JsonParser();
/* 51 */       JsonObject $$3 = $$2.parse($$0).getAsJsonObject();
/* 52 */       if ($$3.get("templates").isJsonArray()) {
/* 53 */         Iterator<JsonElement> $$4 = $$3.get("templates").getAsJsonArray().iterator();
/* 54 */         while ($$4.hasNext()) {
/* 55 */           $$1.templates.add(WorldTemplate.parse(((JsonElement)$$4.next()).getAsJsonObject()));
/*    */         }
/*    */       } 
/*    */       
/* 59 */       $$1.page = JsonUtils.getIntOr("page", $$3, 0);
/* 60 */       $$1.size = JsonUtils.getIntOr("size", $$3, 0);
/* 61 */       $$1.total = JsonUtils.getIntOr("total", $$3, 0);
/* 62 */     } catch (Exception $$5) {
/* 63 */       LOGGER.error("Could not parse WorldTemplatePaginatedList: {}", $$5.getMessage());
/*    */     } 
/* 65 */     return $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\dto\WorldTemplatePaginatedList.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */