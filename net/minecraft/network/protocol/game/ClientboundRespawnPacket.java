/*    */ package net.minecraft.network.protocol.game;public final class ClientboundRespawnPacket extends Record implements Packet<ClientGamePacketListener> { private final CommonPlayerSpawnInfo commonPlayerSpawnInfo;
/*    */   private final byte dataToKeep;
/*    */   public static final byte KEEP_ATTRIBUTES = 1;
/*    */   public static final byte KEEP_ENTITY_DATA = 2;
/*    */   public static final byte KEEP_ALL_DATA = 3;
/*    */   
/*  7 */   public ClientboundRespawnPacket(CommonPlayerSpawnInfo $$0, byte $$1) { this.commonPlayerSpawnInfo = $$0; this.dataToKeep = $$1; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/game/ClientboundRespawnPacket;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  7 */     //   0	7	0	this	Lnet/minecraft/network/protocol/game/ClientboundRespawnPacket; } public CommonPlayerSpawnInfo commonPlayerSpawnInfo() { return this.commonPlayerSpawnInfo; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/game/ClientboundRespawnPacket;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/game/ClientboundRespawnPacket; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/game/ClientboundRespawnPacket;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/game/ClientboundRespawnPacket;
/*  7 */     //   0	8	1	$$0	Ljava/lang/Object; } public byte dataToKeep() { return this.dataToKeep; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ClientboundRespawnPacket(FriendlyByteBuf $$0) {
/* 17 */     this(new CommonPlayerSpawnInfo($$0), $$0
/*    */         
/* 19 */         .readByte());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 25 */     this.commonPlayerSpawnInfo.write($$0);
/* 26 */     $$0.writeByte(this.dataToKeep);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 31 */     $$0.handleRespawn(this);
/*    */   }
/*    */   
/*    */   public boolean shouldKeep(byte $$0) {
/* 35 */     return ((this.dataToKeep & $$0) != 0);
/*    */   } }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundRespawnPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */