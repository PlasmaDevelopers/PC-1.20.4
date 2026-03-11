/*    */ package net.minecraft.network.protocol.common;
/*    */ 
/*    */ 
/*    */ public final class ServerboundClientInformationPacket extends Record implements Packet<ServerCommonPacketListener> {
/*    */   private final ClientInformation information;
/*    */   
/*  7 */   public ServerboundClientInformationPacket(ClientInformation $$0) { this.information = $$0; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/common/ServerboundClientInformationPacket;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  7 */     //   0	7	0	this	Lnet/minecraft/network/protocol/common/ServerboundClientInformationPacket; } public ClientInformation information() { return this.information; }
/*    */   public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/common/ServerboundClientInformationPacket;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/common/ServerboundClientInformationPacket; }
/*    */   public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/common/ServerboundClientInformationPacket;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/common/ServerboundClientInformationPacket;
/*    */     //   0	8	1	$$0	Ljava/lang/Object; } public ServerboundClientInformationPacket(FriendlyByteBuf $$0) {
/* 10 */     this(new ClientInformation($$0));
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 15 */     this.information.write($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ServerCommonPacketListener $$0) {
/* 20 */     $$0.handleClientInformation(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\common\ServerboundClientInformationPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */