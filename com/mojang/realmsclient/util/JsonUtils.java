/*     */ package com.mojang.realmsclient.util;
/*     */ 
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.mojang.util.UndashedUuid;
/*     */ import java.util.Date;
/*     */ import java.util.UUID;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class JsonUtils
/*     */ {
/*     */   public static <T> T getRequired(String $$0, JsonObject $$1, Function<JsonObject, T> $$2) {
/*  14 */     JsonElement $$3 = $$1.get($$0);
/*  15 */     if ($$3 == null || $$3.isJsonNull())
/*  16 */       throw new IllegalStateException("Missing required property: " + $$0); 
/*  17 */     if (!$$3.isJsonObject()) {
/*  18 */       throw new IllegalStateException("Required property " + $$0 + " was not a JsonObject as espected");
/*     */     }
/*  20 */     return $$2.apply($$3.getAsJsonObject());
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static <T> T getOptional(String $$0, JsonObject $$1, Function<JsonObject, T> $$2) {
/*  25 */     JsonElement $$3 = $$1.get($$0);
/*  26 */     if ($$3 == null || $$3.isJsonNull())
/*  27 */       return null; 
/*  28 */     if (!$$3.isJsonObject()) {
/*  29 */       throw new IllegalStateException("Required property " + $$0 + " was not a JsonObject as espected");
/*     */     }
/*  31 */     return $$2.apply($$3.getAsJsonObject());
/*     */   }
/*     */   
/*     */   public static String getRequiredString(String $$0, JsonObject $$1) {
/*  35 */     String $$2 = getStringOr($$0, $$1, null);
/*  36 */     if ($$2 == null) {
/*  37 */       throw new IllegalStateException("Missing required property: " + $$0);
/*     */     }
/*  39 */     return $$2;
/*     */   }
/*     */   
/*     */   public static String getRequiredStringOr(String $$0, JsonObject $$1, String $$2) {
/*  43 */     JsonElement $$3 = $$1.get($$0);
/*  44 */     if ($$3 != null) {
/*  45 */       return $$3.isJsonNull() ? $$2 : $$3.getAsString();
/*     */     }
/*  47 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static String getStringOr(String $$0, JsonObject $$1, @Nullable String $$2) {
/*  53 */     JsonElement $$3 = $$1.get($$0);
/*  54 */     if ($$3 != null) {
/*  55 */       return $$3.isJsonNull() ? $$2 : $$3.getAsString();
/*     */     }
/*  57 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static UUID getUuidOr(String $$0, JsonObject $$1, @Nullable UUID $$2) {
/*  63 */     String $$3 = getStringOr($$0, $$1, null);
/*  64 */     if ($$3 == null) {
/*  65 */       return $$2;
/*     */     }
/*  67 */     return UndashedUuid.fromStringLenient($$3);
/*     */   }
/*     */   
/*     */   public static int getIntOr(String $$0, JsonObject $$1, int $$2) {
/*  71 */     JsonElement $$3 = $$1.get($$0);
/*  72 */     if ($$3 != null) {
/*  73 */       return $$3.isJsonNull() ? $$2 : $$3.getAsInt();
/*     */     }
/*  75 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public static long getLongOr(String $$0, JsonObject $$1, long $$2) {
/*  80 */     JsonElement $$3 = $$1.get($$0);
/*  81 */     if ($$3 != null) {
/*  82 */       return $$3.isJsonNull() ? $$2 : $$3.getAsLong();
/*     */     }
/*  84 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean getBooleanOr(String $$0, JsonObject $$1, boolean $$2) {
/*  89 */     JsonElement $$3 = $$1.get($$0);
/*  90 */     if ($$3 != null) {
/*  91 */       return $$3.isJsonNull() ? $$2 : $$3.getAsBoolean();
/*     */     }
/*  93 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Date getDateOr(String $$0, JsonObject $$1) {
/*  98 */     JsonElement $$2 = $$1.get($$0);
/*  99 */     if ($$2 != null) {
/* 100 */       return new Date(Long.parseLong($$2.getAsString()));
/*     */     }
/* 102 */     return new Date();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclien\\util\JsonUtils.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */