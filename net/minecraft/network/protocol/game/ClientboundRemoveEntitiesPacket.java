/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.ints.IntArrayList;
/*    */ import it.unimi.dsi.fastutil.ints.IntList;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ 
/*    */ public class ClientboundRemoveEntitiesPacket implements Packet<ClientGamePacketListener> {
/*    */   private final IntList entityIds;
/*    */   
/*    */   public ClientboundRemoveEntitiesPacket(IntList $$0) {
/* 13 */     this.entityIds = (IntList)new IntArrayList($$0);
/*    */   }
/*    */   
/*    */   public ClientboundRemoveEntitiesPacket(int... $$0) {
/* 17 */     this.entityIds = (IntList)new IntArrayList($$0);
/*    */   }
/*    */   
/*    */   public ClientboundRemoveEntitiesPacket(FriendlyByteBuf $$0) {
/* 21 */     this.entityIds = $$0.readIntIdList();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 26 */     $$0.writeIntIdList(this.entityIds);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 31 */     $$0.handleRemoveEntities(this);
/*    */   }
/*    */   
/*    */   public IntList getEntityIds() {
/* 35 */     return this.entityIds;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundRemoveEntitiesPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */