/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ public final class ClientboundTickingStatePacket extends Record implements Packet<ClientGamePacketListener> {
/*    */   private final float tickRate;
/*    */   private final boolean isFrozen;
/*    */   
/*  7 */   public ClientboundTickingStatePacket(float $$0, boolean $$1) { this.tickRate = $$0; this.isFrozen = $$1; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/game/ClientboundTickingStatePacket;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  7 */     //   0	7	0	this	Lnet/minecraft/network/protocol/game/ClientboundTickingStatePacket; } public float tickRate() { return this.tickRate; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/game/ClientboundTickingStatePacket;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/game/ClientboundTickingStatePacket; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/game/ClientboundTickingStatePacket;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/game/ClientboundTickingStatePacket;
/*  7 */     //   0	8	1	$$0	Ljava/lang/Object; } public boolean isFrozen() { return this.isFrozen; }
/*    */    public ClientboundTickingStatePacket(FriendlyByteBuf $$0) {
/*  9 */     this($$0
/* 10 */         .readFloat(), $$0
/* 11 */         .readBoolean());
/*    */   }
/*    */ 
/*    */   
/*    */   public static ClientboundTickingStatePacket from(TickRateManager $$0) {
/* 16 */     return new ClientboundTickingStatePacket($$0.tickrate(), $$0.isFrozen());
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 21 */     $$0.writeFloat(this.tickRate);
/* 22 */     $$0.writeBoolean(this.isFrozen);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 27 */     $$0.handleTickingState(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundTickingStatePacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */