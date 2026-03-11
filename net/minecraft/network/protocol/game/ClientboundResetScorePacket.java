/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ public final class ClientboundResetScorePacket extends Record implements Packet<ClientGamePacketListener> {
/*    */   private final String owner;
/*    */   @Nullable
/*    */   private final String objectiveName;
/*    */   
/*  8 */   public ClientboundResetScorePacket(String $$0, @Nullable String $$1) { this.owner = $$0; this.objectiveName = $$1; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/game/ClientboundResetScorePacket;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #8	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  8 */     //   0	7	0	this	Lnet/minecraft/network/protocol/game/ClientboundResetScorePacket; } public String owner() { return this.owner; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/game/ClientboundResetScorePacket;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #8	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/game/ClientboundResetScorePacket; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/game/ClientboundResetScorePacket;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #8	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/game/ClientboundResetScorePacket;
/*  8 */     //   0	8	1	$$0	Ljava/lang/Object; } @Nullable public String objectiveName() { return this.objectiveName; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ClientboundResetScorePacket(FriendlyByteBuf $$0) {
/* 14 */     this($$0
/* 15 */         .readUtf(), (String)$$0
/* 16 */         .readNullable(FriendlyByteBuf::readUtf));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 22 */     $$0.writeUtf(this.owner);
/* 23 */     $$0.writeNullable(this.objectiveName, FriendlyByteBuf::writeUtf);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 28 */     $$0.handleResetScore(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundResetScorePacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */