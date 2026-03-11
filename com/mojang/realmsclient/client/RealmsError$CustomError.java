/*     */ package com.mojang.realmsclient.client;
/*     */ 
/*     */ import com.mojang.realmsclient.exception.RealmsHttpException;
/*     */ import java.util.Locale;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.network.chat.Component;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class CustomError
/*     */   extends Record
/*     */   implements RealmsError
/*     */ {
/*     */   private final int httpCode;
/*     */   @Nullable
/*     */   private final Component payload;
/*     */   
/*     */   public final String toString() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> toString : (Lcom/mojang/realmsclient/client/RealmsError$CustomError;)Ljava/lang/String;
/*     */     //   6: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #92	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lcom/mojang/realmsclient/client/RealmsError$CustomError;
/*     */   }
/*     */   
/*     */   public final int hashCode() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lcom/mojang/realmsclient/client/RealmsError$CustomError;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #92	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lcom/mojang/realmsclient/client/RealmsError$CustomError;
/*     */   }
/*     */   
/*     */   public final boolean equals(Object $$0) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lcom/mojang/realmsclient/client/RealmsError$CustomError;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #92	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lcom/mojang/realmsclient/client/RealmsError$CustomError;
/*     */     //   0	8	1	$$0	Ljava/lang/Object;
/*     */   }
/*     */   
/*     */   public CustomError(int $$0, @Nullable Component $$1) {
/*  92 */     this.httpCode = $$0; this.payload = $$1; } public int httpCode() { return this.httpCode; } @Nullable public Component payload() { return this.payload; }
/*  93 */    public static final CustomError SERVICE_BUSY = new CustomError(429, (Component)Component.translatable("mco.errorMessage.serviceBusy"));
/*  94 */   public static final Component RETRY_MESSAGE = (Component)Component.translatable("mco.errorMessage.retry");
/*     */   
/*     */   public static CustomError unknownCompatibilityResponse(String $$0) {
/*  97 */     return new CustomError(500, (Component)Component.translatable("mco.errorMessage.realmsService.unknownCompatibility", new Object[] { $$0 }));
/*     */   }
/*     */   
/*     */   public static CustomError connectivityError(RealmsHttpException $$0) {
/* 101 */     return new CustomError(500, (Component)Component.translatable("mco.errorMessage.realmsService.connectivity", new Object[] { $$0.getMessage() }));
/*     */   }
/*     */   
/*     */   public static CustomError retry(int $$0) {
/* 105 */     return new CustomError($$0, RETRY_MESSAGE);
/*     */   }
/*     */   
/*     */   public static CustomError noPayload(int $$0) {
/* 109 */     return new CustomError($$0, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public int errorCode() {
/* 114 */     return this.httpCode;
/*     */   }
/*     */ 
/*     */   
/*     */   public Component errorMessage() {
/* 119 */     return (this.payload != null) ? this.payload : NO_MESSAGE;
/*     */   }
/*     */ 
/*     */   
/*     */   public String logMessage() {
/* 124 */     if (this.payload != null) {
/* 125 */       return String.format(Locale.ROOT, "Realms service error (%d) with message '%s'", new Object[] { Integer.valueOf(this.httpCode), this.payload.getString() });
/*     */     }
/* 127 */     return String.format(Locale.ROOT, "Realms service error (%d) with no payload", new Object[] { Integer.valueOf(this.httpCode) });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\client\RealmsError$CustomError.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */