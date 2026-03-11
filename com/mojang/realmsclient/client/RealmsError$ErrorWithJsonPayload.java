/*    */ package com.mojang.realmsclient.client;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.resources.language.I18n;
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ public final class ErrorWithJsonPayload extends Record implements RealmsError {
/*    */   private final int httpCode;
/*    */   private final int code;
/*    */   @Nullable
/*    */   private final String reason;
/*    */   @Nullable
/*    */   private final String message;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lcom/mojang/realmsclient/client/RealmsError$ErrorWithJsonPayload;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #27	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lcom/mojang/realmsclient/client/RealmsError$ErrorWithJsonPayload;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lcom/mojang/realmsclient/client/RealmsError$ErrorWithJsonPayload;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #27	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lcom/mojang/realmsclient/client/RealmsError$ErrorWithJsonPayload;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lcom/mojang/realmsclient/client/RealmsError$ErrorWithJsonPayload;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #27	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lcom/mojang/realmsclient/client/RealmsError$ErrorWithJsonPayload;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */   }
/*    */   
/* 27 */   public ErrorWithJsonPayload(int $$0, int $$1, @Nullable String $$2, @Nullable String $$3) { this.httpCode = $$0; this.code = $$1; this.reason = $$2; this.message = $$3; } public int httpCode() { return this.httpCode; } public int code() { return this.code; } @Nullable public String reason() { return this.reason; } @Nullable public String message() { return this.message; }
/*    */   
/*    */   public int errorCode() {
/* 30 */     return this.code;
/*    */   }
/*    */ 
/*    */   
/*    */   public Component errorMessage() {
/* 35 */     String $$0 = "mco.errorMessage." + this.code;
/* 36 */     if (I18n.exists($$0)) {
/* 37 */       return (Component)Component.translatable($$0);
/*    */     }
/*    */     
/* 40 */     if (this.reason != null) {
/* 41 */       String $$1 = "mco.errorReason." + this.reason;
/* 42 */       if (I18n.exists($$1)) {
/* 43 */         return (Component)Component.translatable($$1);
/*    */       }
/*    */     } 
/*    */     
/* 47 */     return (this.message != null) ? (Component)Component.literal(this.message) : NO_MESSAGE;
/*    */   }
/*    */ 
/*    */   
/*    */   public String logMessage() {
/* 52 */     return String.format(Locale.ROOT, "Realms service error (%d/%d/%s) with message '%s'", new Object[] { Integer.valueOf(this.httpCode), Integer.valueOf(this.code), this.reason, this.message });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\client\RealmsError$ErrorWithJsonPayload.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */