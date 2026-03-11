/*    */ package net.minecraft.network.protocol.common;
/*    */ 
/*    */ 
/*    */ public final class ServerboundResourcePackPacket extends Record implements Packet<ServerCommonPacketListener> {
/*    */   private final UUID id;
/*    */   private final Action action;
/*    */   
/*  8 */   public ServerboundResourcePackPacket(UUID $$0, Action $$1) { this.id = $$0; this.action = $$1; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/common/ServerboundResourcePackPacket;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #8	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  8 */     //   0	7	0	this	Lnet/minecraft/network/protocol/common/ServerboundResourcePackPacket; } public UUID id() { return this.id; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/common/ServerboundResourcePackPacket;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #8	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/common/ServerboundResourcePackPacket; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/common/ServerboundResourcePackPacket;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #8	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/common/ServerboundResourcePackPacket;
/*  8 */     //   0	8	1	$$0	Ljava/lang/Object; } public Action action() { return this.action; }
/*    */ 
/*    */ 
/*    */   
/*    */   public ServerboundResourcePackPacket(FriendlyByteBuf $$0) {
/* 13 */     this($$0
/* 14 */         .readUUID(), (Action)$$0
/* 15 */         .readEnum(Action.class));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 21 */     $$0.writeUUID(this.id);
/* 22 */     $$0.writeEnum(this.action);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ServerCommonPacketListener $$0) {
/* 27 */     $$0.handleResourcePackResponse(this);
/*    */   }
/*    */   
/*    */   public enum Action {
/* 31 */     SUCCESSFULLY_LOADED,
/* 32 */     DECLINED,
/* 33 */     FAILED_DOWNLOAD,
/* 34 */     ACCEPTED,
/* 35 */     DOWNLOADED,
/* 36 */     INVALID_URL,
/* 37 */     FAILED_RELOAD,
/* 38 */     DISCARDED;
/*    */ 
/*    */     
/*    */     public boolean isTerminal() {
/* 42 */       return (this != ACCEPTED && this != DOWNLOADED);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\common\ServerboundResourcePackPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */