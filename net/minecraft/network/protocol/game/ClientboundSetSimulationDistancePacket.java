/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ public final class ClientboundSetSimulationDistancePacket extends Record implements Packet<ClientGamePacketListener> {
/*    */   private final int simulationDistance;
/*    */   
/*  6 */   public ClientboundSetSimulationDistancePacket(int $$0) { this.simulationDistance = $$0; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/game/ClientboundSetSimulationDistancePacket;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #6	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  6 */     //   0	7	0	this	Lnet/minecraft/network/protocol/game/ClientboundSetSimulationDistancePacket; } public int simulationDistance() { return this.simulationDistance; }
/*    */   public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/game/ClientboundSetSimulationDistancePacket;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #6	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/game/ClientboundSetSimulationDistancePacket; }
/*    */   public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/game/ClientboundSetSimulationDistancePacket;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #6	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/game/ClientboundSetSimulationDistancePacket;
/*    */     //   0	8	1	$$0	Ljava/lang/Object; } public ClientboundSetSimulationDistancePacket(FriendlyByteBuf $$0) {
/*  9 */     this($$0.readVarInt());
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 14 */     $$0.writeVarInt(this.simulationDistance);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 19 */     $$0.handleSetSimulationDistance(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundSetSimulationDistancePacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */