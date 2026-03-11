/*    */ package net.minecraft.network.protocol.common;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ 
/*    */ public final class ClientboundResourcePackPushPacket extends Record implements Packet<ClientCommonPacketListener> {
/*    */   private final UUID id;
/*    */   private final String url;
/*    */   private final String hash;
/*    */   private final boolean required;
/*    */   
/* 11 */   public UUID id() { return this.id; } @Nullable private final Component prompt; public static final int MAX_HASH_LENGTH = 40; public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/common/ClientboundResourcePackPushPacket;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/common/ClientboundResourcePackPushPacket; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/common/ClientboundResourcePackPushPacket;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/common/ClientboundResourcePackPushPacket; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/common/ClientboundResourcePackPushPacket;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/common/ClientboundResourcePackPushPacket;
/* 11 */     //   0	8	1	$$0	Ljava/lang/Object; } public String url() { return this.url; } public String hash() { return this.hash; } public boolean required() { return this.required; } @Nullable public Component prompt() { return this.prompt; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ClientboundResourcePackPushPacket(UUID $$0, String $$1, String $$2, boolean $$3, @Nullable Component $$4)
/*    */   {
/* 22 */     if ($$2.length() > 40)
/* 23 */       throw new IllegalArgumentException("Hash is too long (max 40, was " + $$2.length() + ")"); 
/*    */     this.id = $$0;
/*    */     this.url = $$1;
/*    */     this.hash = $$2;
/*    */     this.required = $$3;
/* 28 */     this.prompt = $$4; } public ClientboundResourcePackPushPacket(FriendlyByteBuf $$0) { this($$0
/* 29 */         .readUUID(), $$0
/* 30 */         .readUtf(), $$0
/* 31 */         .readUtf(40), $$0
/* 32 */         .readBoolean(), (Component)$$0
/* 33 */         .readNullable(FriendlyByteBuf::readComponentTrusted)); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 39 */     $$0.writeUUID(this.id);
/* 40 */     $$0.writeUtf(this.url);
/* 41 */     $$0.writeUtf(this.hash);
/* 42 */     $$0.writeBoolean(this.required);
/* 43 */     $$0.writeNullable(this.prompt, FriendlyByteBuf::writeComponent);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientCommonPacketListener $$0) {
/* 48 */     $$0.handleResourcePackPush(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\common\ClientboundResourcePackPushPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */