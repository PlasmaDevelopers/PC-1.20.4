/*    */ package net.minecraft.network.protocol.game;
/*    */ public final class ServerboundContainerSlotStateChangedPacket extends Record implements Packet<ServerGamePacketListener> { private final int slotId;
/*    */   private final int containerId;
/*    */   private final boolean newState;
/*    */   
/*  6 */   public ServerboundContainerSlotStateChangedPacket(int $$0, int $$1, boolean $$2) { this.slotId = $$0; this.containerId = $$1; this.newState = $$2; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/game/ServerboundContainerSlotStateChangedPacket;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #6	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  6 */     //   0	7	0	this	Lnet/minecraft/network/protocol/game/ServerboundContainerSlotStateChangedPacket; } public int slotId() { return this.slotId; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/game/ServerboundContainerSlotStateChangedPacket;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #6	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/game/ServerboundContainerSlotStateChangedPacket; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/game/ServerboundContainerSlotStateChangedPacket;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #6	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/game/ServerboundContainerSlotStateChangedPacket;
/*  6 */     //   0	8	1	$$0	Ljava/lang/Object; } public int containerId() { return this.containerId; } public boolean newState() { return this.newState; }
/*    */   
/*    */   public ServerboundContainerSlotStateChangedPacket(FriendlyByteBuf $$0) {
/*  9 */     this($$0.readVarInt(), $$0.readVarInt(), $$0.readBoolean());
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 14 */     $$0.writeVarInt(this.slotId);
/* 15 */     $$0.writeVarInt(this.containerId);
/* 16 */     $$0.writeBoolean(this.newState);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ServerGamePacketListener $$0) {
/* 21 */     $$0.handleContainerSlotStateChanged(this);
/*    */   } }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ServerboundContainerSlotStateChangedPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */