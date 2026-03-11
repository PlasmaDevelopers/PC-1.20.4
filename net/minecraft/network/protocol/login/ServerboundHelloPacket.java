/*    */ package net.minecraft.network.protocol.login;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ 
/*    */ public final class ServerboundHelloPacket extends Record implements Packet<ServerLoginPacketListener> {
/*    */   private final String name;
/*    */   private final UUID profileId;
/*    */   
/*  9 */   public ServerboundHelloPacket(String $$0, UUID $$1) { this.name = $$0; this.profileId = $$1; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/login/ServerboundHelloPacket;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  9 */     //   0	7	0	this	Lnet/minecraft/network/protocol/login/ServerboundHelloPacket; } public String name() { return this.name; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/login/ServerboundHelloPacket;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/login/ServerboundHelloPacket; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/login/ServerboundHelloPacket;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/login/ServerboundHelloPacket;
/*  9 */     //   0	8	1	$$0	Ljava/lang/Object; } public UUID profileId() { return this.profileId; }
/*    */    public ServerboundHelloPacket(FriendlyByteBuf $$0) {
/* 11 */     this($$0.readUtf(16), $$0.readUUID());
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 16 */     $$0.writeUtf(this.name, 16);
/* 17 */     $$0.writeUUID(this.profileId);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ServerLoginPacketListener $$0) {
/* 22 */     $$0.handleHello(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\login\ServerboundHelloPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */