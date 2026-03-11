/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ public final class ClientboundHurtAnimationPacket extends Record implements Packet<ClientGamePacketListener> {
/*    */   private final int id;
/*    */   private final float yaw;
/*    */   
/*  7 */   public ClientboundHurtAnimationPacket(int $$0, float $$1) { this.id = $$0; this.yaw = $$1; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/game/ClientboundHurtAnimationPacket;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  7 */     //   0	7	0	this	Lnet/minecraft/network/protocol/game/ClientboundHurtAnimationPacket; } public int id() { return this.id; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/game/ClientboundHurtAnimationPacket;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/game/ClientboundHurtAnimationPacket; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/game/ClientboundHurtAnimationPacket;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/game/ClientboundHurtAnimationPacket;
/*  7 */     //   0	8	1	$$0	Ljava/lang/Object; } public float yaw() { return this.yaw; }
/*    */    public ClientboundHurtAnimationPacket(LivingEntity $$0) {
/*  9 */     this($$0.getId(), $$0.getHurtDir());
/*    */   }
/*    */   
/*    */   public ClientboundHurtAnimationPacket(FriendlyByteBuf $$0) {
/* 13 */     this($$0.readVarInt(), $$0.readFloat());
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 18 */     $$0.writeVarInt(this.id);
/* 19 */     $$0.writeFloat(this.yaw);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 24 */     $$0.handleHurtAnimation(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundHurtAnimationPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */