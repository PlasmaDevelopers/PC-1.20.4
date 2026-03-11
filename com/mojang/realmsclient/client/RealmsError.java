/*     */ package com.mojang.realmsclient.client;
/*     */ 
/*     */ import com.google.common.base.Strings;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParser;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.realmsclient.exception.RealmsHttpException;
/*     */ import java.util.Locale;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.resources.language.I18n;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.util.GsonHelper;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public interface RealmsError
/*     */ {
/*  17 */   public static final Component NO_MESSAGE = (Component)Component.translatable("mco.errorMessage.noDetails");
/*     */   
/*  19 */   public static final Logger LOGGER = LogUtils.getLogger();
/*     */   int errorCode();
/*     */   Component errorMessage();
/*     */   String logMessage();
/*     */   public static final class ErrorWithJsonPayload extends Record implements RealmsError { private final int httpCode; private final int code; @Nullable
/*     */     private final String reason; @Nullable
/*     */     private final String message;
/*     */     
/*  27 */     public ErrorWithJsonPayload(int $$0, int $$1, @Nullable String $$2, @Nullable String $$3) { this.httpCode = $$0; this.code = $$1; this.reason = $$2; this.message = $$3; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lcom/mojang/realmsclient/client/RealmsError$ErrorWithJsonPayload;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #27	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*  27 */       //   0	7	0	this	Lcom/mojang/realmsclient/client/RealmsError$ErrorWithJsonPayload; } public int httpCode() { return this.httpCode; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lcom/mojang/realmsclient/client/RealmsError$ErrorWithJsonPayload;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #27	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/mojang/realmsclient/client/RealmsError$ErrorWithJsonPayload; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lcom/mojang/realmsclient/client/RealmsError$ErrorWithJsonPayload;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #27	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lcom/mojang/realmsclient/client/RealmsError$ErrorWithJsonPayload;
/*  27 */       //   0	8	1	$$0	Ljava/lang/Object; } public int code() { return this.code; } @Nullable public String reason() { return this.reason; } @Nullable public String message() { return this.message; }
/*     */     
/*     */     public int errorCode() {
/*  30 */       return this.code;
/*     */     }
/*     */ 
/*     */     
/*     */     public Component errorMessage() {
/*  35 */       String $$0 = "mco.errorMessage." + this.code;
/*  36 */       if (I18n.exists($$0)) {
/*  37 */         return (Component)Component.translatable($$0);
/*     */       }
/*     */       
/*  40 */       if (this.reason != null) {
/*  41 */         String $$1 = "mco.errorReason." + this.reason;
/*  42 */         if (I18n.exists($$1)) {
/*  43 */           return (Component)Component.translatable($$1);
/*     */         }
/*     */       } 
/*     */       
/*  47 */       return (this.message != null) ? (Component)Component.literal(this.message) : NO_MESSAGE;
/*     */     }
/*     */ 
/*     */     
/*     */     public String logMessage() {
/*  52 */       return String.format(Locale.ROOT, "Realms service error (%d/%d/%s) with message '%s'", new Object[] { Integer.valueOf(this.httpCode), Integer.valueOf(this.code), this.reason, this.message });
/*     */     } }
/*     */   public static final class ErrorWithRawPayload extends Record implements RealmsError { private final int httpCode; private final String payload;
/*     */     
/*  56 */     public ErrorWithRawPayload(int $$0, String $$1) { this.httpCode = $$0; this.payload = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lcom/mojang/realmsclient/client/RealmsError$ErrorWithRawPayload;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #56	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/mojang/realmsclient/client/RealmsError$ErrorWithRawPayload; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lcom/mojang/realmsclient/client/RealmsError$ErrorWithRawPayload;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #56	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/mojang/realmsclient/client/RealmsError$ErrorWithRawPayload; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lcom/mojang/realmsclient/client/RealmsError$ErrorWithRawPayload;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #56	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lcom/mojang/realmsclient/client/RealmsError$ErrorWithRawPayload;
/*  56 */       //   0	8	1	$$0	Ljava/lang/Object; } public int httpCode() { return this.httpCode; } public String payload() { return this.payload; }
/*     */     
/*     */     public int errorCode() {
/*  59 */       return this.httpCode;
/*     */     }
/*     */ 
/*     */     
/*     */     public Component errorMessage() {
/*  64 */       return (Component)Component.literal(this.payload);
/*     */     }
/*     */ 
/*     */     
/*     */     public String logMessage() {
/*  69 */       return String.format(Locale.ROOT, "Realms service error (%d) with raw payload '%s'", new Object[] { Integer.valueOf(this.httpCode), this.payload });
/*     */     } }
/*     */   public static final class AuthenticationError extends Record implements RealmsError { private final String message; public static final int ERROR_CODE = 401;
/*     */     
/*  73 */     public AuthenticationError(String $$0) { this.message = $$0; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lcom/mojang/realmsclient/client/RealmsError$AuthenticationError;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #73	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/mojang/realmsclient/client/RealmsError$AuthenticationError; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lcom/mojang/realmsclient/client/RealmsError$AuthenticationError;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #73	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/mojang/realmsclient/client/RealmsError$AuthenticationError; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lcom/mojang/realmsclient/client/RealmsError$AuthenticationError;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #73	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lcom/mojang/realmsclient/client/RealmsError$AuthenticationError;
/*  73 */       //   0	8	1	$$0	Ljava/lang/Object; } public String message() { return this.message; }
/*     */ 
/*     */ 
/*     */     
/*     */     public int errorCode() {
/*  78 */       return 401;
/*     */     }
/*     */ 
/*     */     
/*     */     public Component errorMessage() {
/*  83 */       return (Component)Component.literal(this.message);
/*     */     }
/*     */ 
/*     */     
/*     */     public String logMessage() {
/*  88 */       return String.format(Locale.ROOT, "Realms authentication error with message '%s'", new Object[] { this.message });
/*     */     } }
/*     */   public static final class CustomError extends Record implements RealmsError { private final int httpCode; @Nullable
/*     */     private final Component payload;
/*  92 */     public CustomError(int $$0, @Nullable Component $$1) { this.httpCode = $$0; this.payload = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lcom/mojang/realmsclient/client/RealmsError$CustomError;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #92	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/mojang/realmsclient/client/RealmsError$CustomError; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lcom/mojang/realmsclient/client/RealmsError$CustomError;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #92	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/mojang/realmsclient/client/RealmsError$CustomError; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lcom/mojang/realmsclient/client/RealmsError$CustomError;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #92	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lcom/mojang/realmsclient/client/RealmsError$CustomError;
/*  92 */       //   0	8	1	$$0	Ljava/lang/Object; } public int httpCode() { return this.httpCode; } @Nullable public Component payload() { return this.payload; }
/*  93 */      public static final CustomError SERVICE_BUSY = new CustomError(429, (Component)Component.translatable("mco.errorMessage.serviceBusy"));
/*  94 */     public static final Component RETRY_MESSAGE = (Component)Component.translatable("mco.errorMessage.retry");
/*     */     
/*     */     public static CustomError unknownCompatibilityResponse(String $$0) {
/*  97 */       return new CustomError(500, (Component)Component.translatable("mco.errorMessage.realmsService.unknownCompatibility", new Object[] { $$0 }));
/*     */     }
/*     */     
/*     */     public static CustomError connectivityError(RealmsHttpException $$0) {
/* 101 */       return new CustomError(500, (Component)Component.translatable("mco.errorMessage.realmsService.connectivity", new Object[] { $$0.getMessage() }));
/*     */     }
/*     */     
/*     */     public static CustomError retry(int $$0) {
/* 105 */       return new CustomError($$0, RETRY_MESSAGE);
/*     */     }
/*     */     
/*     */     public static CustomError noPayload(int $$0) {
/* 109 */       return new CustomError($$0, null);
/*     */     }
/*     */ 
/*     */     
/*     */     public int errorCode() {
/* 114 */       return this.httpCode;
/*     */     }
/*     */ 
/*     */     
/*     */     public Component errorMessage() {
/* 119 */       return (this.payload != null) ? this.payload : NO_MESSAGE;
/*     */     }
/*     */ 
/*     */     
/*     */     public String logMessage() {
/* 124 */       if (this.payload != null) {
/* 125 */         return String.format(Locale.ROOT, "Realms service error (%d) with message '%s'", new Object[] { Integer.valueOf(this.httpCode), this.payload.getString() });
/*     */       }
/* 127 */       return String.format(Locale.ROOT, "Realms service error (%d) with no payload", new Object[] { Integer.valueOf(this.httpCode) });
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   static RealmsError parse(int $$0, String $$1) {
/* 133 */     if ($$0 == 429)
/*     */     {
/* 135 */       return CustomError.SERVICE_BUSY;
/*     */     }
/*     */     
/* 138 */     if (Strings.isNullOrEmpty($$1)) {
/* 139 */       return CustomError.noPayload($$0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 150 */       JsonObject $$2 = JsonParser.parseString($$1).getAsJsonObject();
/* 151 */       String $$3 = GsonHelper.getAsString($$2, "reason", null);
/* 152 */       String $$4 = GsonHelper.getAsString($$2, "errorMsg", null);
/* 153 */       int $$5 = GsonHelper.getAsInt($$2, "errorCode", -1);
/* 154 */       if ($$4 != null || $$3 != null || $$5 != -1) {
/* 155 */         return new ErrorWithJsonPayload($$0, 
/*     */             
/* 157 */             ($$5 != -1) ? $$5 : $$0, $$3, $$4);
/*     */ 
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 163 */     catch (Exception $$6) {
/* 164 */       LOGGER.error("Could not parse RealmsError", $$6);
/*     */     } 
/*     */     
/* 167 */     return new ErrorWithRawPayload($$0, $$1);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\client\RealmsError.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */