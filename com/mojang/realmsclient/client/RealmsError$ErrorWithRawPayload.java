/*    */ package com.mojang.realmsclient.client;
/*    */ 
/*    */ import java.util.Locale;
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ErrorWithRawPayload
/*    */   extends Record
/*    */   implements RealmsError
/*    */ {
/*    */   private final int httpCode;
/*    */   private final String payload;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lcom/mojang/realmsclient/client/RealmsError$ErrorWithRawPayload;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #56	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lcom/mojang/realmsclient/client/RealmsError$ErrorWithRawPayload;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lcom/mojang/realmsclient/client/RealmsError$ErrorWithRawPayload;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #56	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lcom/mojang/realmsclient/client/RealmsError$ErrorWithRawPayload;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lcom/mojang/realmsclient/client/RealmsError$ErrorWithRawPayload;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #56	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lcom/mojang/realmsclient/client/RealmsError$ErrorWithRawPayload;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   public ErrorWithRawPayload(int $$0, String $$1) {
/* 56 */     this.httpCode = $$0; this.payload = $$1; } public int httpCode() { return this.httpCode; } public String payload() { return this.payload; }
/*    */   
/*    */   public int errorCode() {
/* 59 */     return this.httpCode;
/*    */   }
/*    */ 
/*    */   
/*    */   public Component errorMessage() {
/* 64 */     return (Component)Component.literal(this.payload);
/*    */   }
/*    */ 
/*    */   
/*    */   public String logMessage() {
/* 69 */     return String.format(Locale.ROOT, "Realms service error (%d) with raw payload '%s'", new Object[] { Integer.valueOf(this.httpCode), this.payload });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\client\RealmsError$ErrorWithRawPayload.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */