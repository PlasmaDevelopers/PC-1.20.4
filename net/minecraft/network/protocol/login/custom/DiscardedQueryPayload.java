/*   */ package net.minecraft.network.protocol.login.custom;
/*   */ 
/*   */ import net.minecraft.resources.ResourceLocation;
/*   */ 
/*   */ public final class DiscardedQueryPayload extends Record implements CustomQueryPayload {
/* 6 */   public DiscardedQueryPayload(ResourceLocation $$0) { this.id = $$0; } private final ResourceLocation id; public ResourceLocation id() { return this.id; }
/*   */ 
/*   */   
/*   */   public final String toString() {
/*   */     // Byte code:
/*   */     //   0: aload_0
/*   */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/login/custom/DiscardedQueryPayload;)Ljava/lang/String;
/*   */     //   6: areturn
/*   */     // Line number table:
/*   */     //   Java source line number -> byte code offset
/*   */     //   #6	-> 0
/*   */     // Local variable table:
/*   */     //   start	length	slot	name	descriptor
/*   */     //   0	7	0	this	Lnet/minecraft/network/protocol/login/custom/DiscardedQueryPayload;
/*   */   }
/*   */   
/*   */   public final int hashCode() {
/*   */     // Byte code:
/*   */     //   0: aload_0
/*   */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/login/custom/DiscardedQueryPayload;)I
/*   */     //   6: ireturn
/*   */     // Line number table:
/*   */     //   Java source line number -> byte code offset
/*   */     //   #6	-> 0
/*   */     // Local variable table:
/*   */     //   start	length	slot	name	descriptor
/*   */     //   0	7	0	this	Lnet/minecraft/network/protocol/login/custom/DiscardedQueryPayload;
/*   */   }
/*   */   
/*   */   public final boolean equals(Object $$0) {
/*   */     // Byte code:
/*   */     //   0: aload_0
/*   */     //   1: aload_1
/*   */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/login/custom/DiscardedQueryPayload;Ljava/lang/Object;)Z
/*   */     //   7: ireturn
/*   */     // Line number table:
/*   */     //   Java source line number -> byte code offset
/*   */     //   #6	-> 0
/*   */     // Local variable table:
/*   */     //   start	length	slot	name	descriptor
/*   */     //   0	8	0	this	Lnet/minecraft/network/protocol/login/custom/DiscardedQueryPayload;
/*   */     //   0	8	1	$$0	Ljava/lang/Object;
/*   */   }
/*   */   
/*   */   public void write(FriendlyByteBuf $$0) {}
/*   */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\login\custom\DiscardedQueryPayload.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */