/*   */ package net.minecraft.network.protocol.common.custom;
/*   */ 
/*   */ import net.minecraft.resources.ResourceLocation;
/*   */ 
/*   */ public final class DiscardedPayload extends Record implements CustomPacketPayload {
/* 6 */   public DiscardedPayload(ResourceLocation $$0) { this.id = $$0; } private final ResourceLocation id; public ResourceLocation id() { return this.id; }
/*   */ 
/*   */   
/*   */   public final String toString() {
/*   */     // Byte code:
/*   */     //   0: aload_0
/*   */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/common/custom/DiscardedPayload;)Ljava/lang/String;
/*   */     //   6: areturn
/*   */     // Line number table:
/*   */     //   Java source line number -> byte code offset
/*   */     //   #6	-> 0
/*   */     // Local variable table:
/*   */     //   start	length	slot	name	descriptor
/*   */     //   0	7	0	this	Lnet/minecraft/network/protocol/common/custom/DiscardedPayload;
/*   */   }
/*   */   
/*   */   public final int hashCode() {
/*   */     // Byte code:
/*   */     //   0: aload_0
/*   */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/common/custom/DiscardedPayload;)I
/*   */     //   6: ireturn
/*   */     // Line number table:
/*   */     //   Java source line number -> byte code offset
/*   */     //   #6	-> 0
/*   */     // Local variable table:
/*   */     //   start	length	slot	name	descriptor
/*   */     //   0	7	0	this	Lnet/minecraft/network/protocol/common/custom/DiscardedPayload;
/*   */   }
/*   */   
/*   */   public final boolean equals(Object $$0) {
/*   */     // Byte code:
/*   */     //   0: aload_0
/*   */     //   1: aload_1
/*   */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/common/custom/DiscardedPayload;Ljava/lang/Object;)Z
/*   */     //   7: ireturn
/*   */     // Line number table:
/*   */     //   Java source line number -> byte code offset
/*   */     //   #6	-> 0
/*   */     // Local variable table:
/*   */     //   start	length	slot	name	descriptor
/*   */     //   0	8	0	this	Lnet/minecraft/network/protocol/common/custom/DiscardedPayload;
/*   */     //   0	8	1	$$0	Ljava/lang/Object;
/*   */   }
/*   */   
/*   */   public void write(FriendlyByteBuf $$0) {}
/*   */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\common\custom\DiscardedPayload.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */