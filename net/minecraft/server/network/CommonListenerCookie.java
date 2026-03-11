/*    */ package net.minecraft.server.network;
/*    */ public final class CommonListenerCookie extends Record { private final GameProfile gameProfile;
/*    */   private final int latency;
/*    */   private final ClientInformation clientInformation;
/*    */   
/*  6 */   public CommonListenerCookie(GameProfile $$0, int $$1, ClientInformation $$2) { this.gameProfile = $$0; this.latency = $$1; this.clientInformation = $$2; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/server/network/CommonListenerCookie;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #6	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  6 */     //   0	7	0	this	Lnet/minecraft/server/network/CommonListenerCookie; } public GameProfile gameProfile() { return this.gameProfile; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/server/network/CommonListenerCookie;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #6	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/server/network/CommonListenerCookie; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/server/network/CommonListenerCookie;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #6	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/server/network/CommonListenerCookie;
/*  6 */     //   0	8	1	$$0	Ljava/lang/Object; } public int latency() { return this.latency; } public ClientInformation clientInformation() { return this.clientInformation; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static CommonListenerCookie createInitial(GameProfile $$0) {
/* 12 */     return new CommonListenerCookie($$0, 0, 
/*    */ 
/*    */         
/* 15 */         ClientInformation.createDefault());
/*    */   } }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\network\CommonListenerCookie.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */