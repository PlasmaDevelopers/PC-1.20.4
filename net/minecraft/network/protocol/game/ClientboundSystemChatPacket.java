/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ public final class ClientboundSystemChatPacket extends Record implements Packet<ClientGamePacketListener> {
/*    */   private final Component content;
/*    */   private final boolean overlay;
/*    */   
/*  7 */   public ClientboundSystemChatPacket(Component $$0, boolean $$1) { this.content = $$0; this.overlay = $$1; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/game/ClientboundSystemChatPacket;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  7 */     //   0	7	0	this	Lnet/minecraft/network/protocol/game/ClientboundSystemChatPacket; } public Component content() { return this.content; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/game/ClientboundSystemChatPacket;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/game/ClientboundSystemChatPacket; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/game/ClientboundSystemChatPacket;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/game/ClientboundSystemChatPacket;
/*  7 */     //   0	8	1	$$0	Ljava/lang/Object; } public boolean overlay() { return this.overlay; }
/*    */    public ClientboundSystemChatPacket(FriendlyByteBuf $$0) {
/*  9 */     this($$0.readComponentTrusted(), $$0.readBoolean());
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 14 */     $$0.writeComponent(this.content);
/* 15 */     $$0.writeBoolean(this.overlay);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 20 */     $$0.handleSystemChat(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSkippable() {
/* 25 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundSystemChatPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */