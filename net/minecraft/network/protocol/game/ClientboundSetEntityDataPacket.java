/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.network.syncher.SynchedEntityData;
/*    */ 
/*    */ public final class ClientboundSetEntityDataPacket extends Record implements Packet<ClientGamePacketListener> {
/*    */   private final int id;
/*    */   private final List<SynchedEntityData.DataValue<?>> packedItems;
/*    */   public static final int EOF_MARKER = 255;
/*    */   
/* 10 */   public ClientboundSetEntityDataPacket(int $$0, List<SynchedEntityData.DataValue<?>> $$1) { this.id = $$0; this.packedItems = $$1; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/game/ClientboundSetEntityDataPacket;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #10	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 10 */     //   0	7	0	this	Lnet/minecraft/network/protocol/game/ClientboundSetEntityDataPacket; } public int id() { return this.id; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/game/ClientboundSetEntityDataPacket;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #10	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/game/ClientboundSetEntityDataPacket; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/game/ClientboundSetEntityDataPacket;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #10	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/game/ClientboundSetEntityDataPacket;
/* 10 */     //   0	8	1	$$0	Ljava/lang/Object; } public List<SynchedEntityData.DataValue<?>> packedItems() { return this.packedItems; }
/*    */ 
/*    */   
/*    */   public ClientboundSetEntityDataPacket(FriendlyByteBuf $$0) {
/* 14 */     this($$0
/* 15 */         .readVarInt(), 
/* 16 */         unpack($$0));
/*    */   }
/*    */ 
/*    */   
/*    */   private static void pack(List<SynchedEntityData.DataValue<?>> $$0, FriendlyByteBuf $$1) {
/* 21 */     for (SynchedEntityData.DataValue<?> $$2 : $$0) {
/* 22 */       $$2.write($$1);
/*    */     }
/* 24 */     $$1.writeByte(255);
/*    */   }
/*    */   
/*    */   private static List<SynchedEntityData.DataValue<?>> unpack(FriendlyByteBuf $$0) {
/* 28 */     List<SynchedEntityData.DataValue<?>> $$1 = new ArrayList<>();
/*    */     
/*    */     int $$2;
/* 31 */     while (($$2 = $$0.readUnsignedByte()) != 255) {
/* 32 */       $$1.add(SynchedEntityData.DataValue.read($$0, $$2));
/*    */     }
/*    */     
/* 35 */     return $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 40 */     $$0.writeVarInt(this.id);
/* 41 */     pack(this.packedItems, $$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 46 */     $$0.handleSetEntityData(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundSetEntityDataPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */