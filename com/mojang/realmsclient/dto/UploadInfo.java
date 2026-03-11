/*     */ package com.mojang.realmsclient.dto;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParser;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.realmsclient.util.JsonUtils;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.annotation.Nullable;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UploadInfo
/*     */   extends ValueObject
/*     */ {
/*  20 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private static final String DEFAULT_SCHEMA = "http://";
/*     */   
/*     */   private static final int DEFAULT_PORT = 8080;
/*  25 */   private static final Pattern URI_SCHEMA_PATTERN = Pattern.compile("^[a-zA-Z][-a-zA-Z0-9+.]+:");
/*     */   
/*     */   private final boolean worldClosed;
/*     */   @Nullable
/*     */   private final String token;
/*     */   private final URI uploadEndpoint;
/*     */   
/*     */   private UploadInfo(boolean $$0, @Nullable String $$1, URI $$2) {
/*  33 */     this.worldClosed = $$0;
/*  34 */     this.token = $$1;
/*  35 */     this.uploadEndpoint = $$2;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static UploadInfo parse(String $$0) {
/*     */     try {
/*  41 */       JsonParser $$1 = new JsonParser();
/*  42 */       JsonObject $$2 = $$1.parse($$0).getAsJsonObject();
/*  43 */       String $$3 = JsonUtils.getStringOr("uploadEndpoint", $$2, null);
/*  44 */       if ($$3 != null) {
/*  45 */         int $$4 = JsonUtils.getIntOr("port", $$2, -1);
/*  46 */         URI $$5 = assembleUri($$3, $$4);
/*  47 */         if ($$5 != null) {
/*  48 */           boolean $$6 = JsonUtils.getBooleanOr("worldClosed", $$2, false);
/*  49 */           String $$7 = JsonUtils.getStringOr("token", $$2, null);
/*  50 */           return new UploadInfo($$6, $$7, $$5);
/*     */         } 
/*     */       } 
/*  53 */     } catch (Exception $$8) {
/*  54 */       LOGGER.error("Could not parse UploadInfo: {}", $$8.getMessage());
/*     */     } 
/*     */     
/*  57 */     return null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   @VisibleForTesting
/*     */   public static URI assembleUri(String $$0, int $$1) {
/*  63 */     Matcher $$2 = URI_SCHEMA_PATTERN.matcher($$0);
/*     */     
/*  65 */     String $$3 = ensureEndpointSchema($$0, $$2);
/*     */     try {
/*  67 */       URI $$4 = new URI($$3);
/*  68 */       int $$5 = selectPortOrDefault($$1, $$4.getPort());
/*  69 */       if ($$5 != $$4.getPort()) {
/*  70 */         return new URI($$4.getScheme(), $$4.getUserInfo(), $$4.getHost(), $$5, $$4.getPath(), $$4.getQuery(), $$4.getFragment());
/*     */       }
/*  72 */       return $$4;
/*  73 */     } catch (URISyntaxException $$6) {
/*  74 */       LOGGER.warn("Failed to parse URI {}", $$3, $$6);
/*     */ 
/*     */       
/*  77 */       return null;
/*     */     } 
/*     */   }
/*     */   private static int selectPortOrDefault(int $$0, int $$1) {
/*  81 */     if ($$0 != -1) {
/*  82 */       return $$0;
/*     */     }
/*  84 */     if ($$1 != -1) {
/*  85 */       return $$1;
/*     */     }
/*  87 */     return 8080;
/*     */   }
/*     */   
/*     */   private static String ensureEndpointSchema(String $$0, Matcher $$1) {
/*  91 */     if ($$1.find()) {
/*  92 */       return $$0;
/*     */     }
/*  94 */     return "http://" + $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String createRequest(@Nullable String $$0) {
/*  99 */     JsonObject $$1 = new JsonObject();
/* 100 */     if ($$0 != null) {
/* 101 */       $$1.addProperty("token", $$0);
/*     */     }
/* 103 */     return $$1.toString();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getToken() {
/* 108 */     return this.token;
/*     */   }
/*     */   
/*     */   public URI getUploadEndpoint() {
/* 112 */     return this.uploadEndpoint;
/*     */   }
/*     */   
/*     */   public boolean isWorldClosed() {
/* 116 */     return this.worldClosed;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\dto\UploadInfo.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */