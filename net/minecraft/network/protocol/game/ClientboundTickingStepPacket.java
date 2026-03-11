/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ 
/*    */ public final class ClientboundTickingStepPacket extends Record implements Packet<ClientGamePacketListener> {
/*    */   private final int tickSteps;
/*    */   
/*  7 */   public ClientboundTickingStepPacket(int $$0) { this.tickSteps = $$0; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/game/ClientboundTickingStepPacket;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  7 */     //   0	7	0	this	Lnet/minecraft/network/protocol/game/ClientboundTickingStepPacket; } public int tickSteps() { return this.tickSteps; }
/*    */   public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/game/ClientboundTickingStepPacket;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/game/ClientboundTickingStepPacket; }
/*    */   public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/game/ClientboundTickingStepPacket;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/game/ClientboundTickingStepPacket;
/*  9 */     //   0	8	1	$$0	Ljava/lang/Object; } public ClientboundTickingStepPacket(FriendlyByteBuf $$0) { this($$0.readVarInt()); }
/*    */ 
/*    */   
/*    */   public static ClientboundTickingStepPacket from(TickRateManager $$0) {
/* 13 */     return new ClientboundTickingStepPacket($$0.frozenTicksToRun());
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 18 */     $$0.writeVarInt(this.tickSteps);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 23 */     $$0.handleTickingStep(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundTickingStepPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */