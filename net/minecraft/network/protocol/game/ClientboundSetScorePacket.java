/*    */ package net.minecraft.network.protocol.game;
/*    */ public final class ClientboundSetScorePacket extends Record implements Packet<ClientGamePacketListener> {
/*    */   private final String owner;
/*    */   private final String objectiveName;
/*    */   private final int score;
/*    */   @Nullable
/*    */   private final Component display;
/*    */   @Nullable
/*    */   private final NumberFormat numberFormat;
/*    */   
/* 11 */   public ClientboundSetScorePacket(String $$0, String $$1, int $$2, @Nullable Component $$3, @Nullable NumberFormat $$4) { this.owner = $$0; this.objectiveName = $$1; this.score = $$2; this.display = $$3; this.numberFormat = $$4; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/game/ClientboundSetScorePacket;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 11 */     //   0	7	0	this	Lnet/minecraft/network/protocol/game/ClientboundSetScorePacket; } public String owner() { return this.owner; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/game/ClientboundSetScorePacket;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/game/ClientboundSetScorePacket; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/game/ClientboundSetScorePacket;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/game/ClientboundSetScorePacket;
/* 11 */     //   0	8	1	$$0	Ljava/lang/Object; } public String objectiveName() { return this.objectiveName; } public int score() { return this.score; } @Nullable public Component display() { return this.display; } @Nullable public NumberFormat numberFormat() { return this.numberFormat; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ClientboundSetScorePacket(FriendlyByteBuf $$0) {
/* 19 */     this($$0
/* 20 */         .readUtf(), $$0
/* 21 */         .readUtf(), $$0
/* 22 */         .readVarInt(), (Component)$$0
/* 23 */         .readNullable(FriendlyByteBuf::readComponentTrusted), (NumberFormat)$$0
/* 24 */         .readNullable(NumberFormatTypes::readFromStream));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 30 */     $$0.writeUtf(this.owner);
/* 31 */     $$0.writeUtf(this.objectiveName);
/* 32 */     $$0.writeVarInt(this.score);
/* 33 */     $$0.writeNullable(this.display, FriendlyByteBuf::writeComponent);
/* 34 */     $$0.writeNullable(this.numberFormat, NumberFormatTypes::writeToStream);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 39 */     $$0.handleSetScore(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundSetScorePacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */