/*    */ package net.minecraft.network.protocol.common;
/*    */ 
/*    */ import java.util.Optional;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ 
/*    */ public final class ClientboundResourcePackPopPacket extends Record implements Packet<ClientCommonPacketListener> {
/*    */   private final Optional<UUID> id;
/*    */   
/*  9 */   public ClientboundResourcePackPopPacket(Optional<UUID> $$0) { this.id = $$0; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/common/ClientboundResourcePackPopPacket;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  9 */     //   0	7	0	this	Lnet/minecraft/network/protocol/common/ClientboundResourcePackPopPacket; } public Optional<UUID> id() { return this.id; }
/*    */   public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/common/ClientboundResourcePackPopPacket;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/common/ClientboundResourcePackPopPacket; } public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/common/ClientboundResourcePackPopPacket;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/common/ClientboundResourcePackPopPacket;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */   } public ClientboundResourcePackPopPacket(FriendlyByteBuf $$0) {
/* 13 */     this($$0
/* 14 */         .readOptional(FriendlyByteBuf::readUUID));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 20 */     $$0.writeOptional(this.id, FriendlyByteBuf::writeUUID);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientCommonPacketListener $$0) {
/* 25 */     $$0.handleResourcePackPop(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\common\ClientboundResourcePackPopPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */