/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.nbt.Tag;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ 
/*    */ public class ClientboundTagQueryPacket implements Packet<ClientGamePacketListener> {
/*    */   private final int transactionId;
/*    */   @Nullable
/*    */   private final CompoundTag tag;
/*    */   
/*    */   public ClientboundTagQueryPacket(int $$0, @Nullable CompoundTag $$1) {
/* 16 */     this.transactionId = $$0;
/* 17 */     this.tag = $$1;
/*    */   }
/*    */   
/*    */   public ClientboundTagQueryPacket(FriendlyByteBuf $$0) {
/* 21 */     this.transactionId = $$0.readVarInt();
/* 22 */     this.tag = $$0.readNbt();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 27 */     $$0.writeVarInt(this.transactionId);
/* 28 */     $$0.writeNbt((Tag)this.tag);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 33 */     $$0.handleTagQueryPacket(this);
/*    */   }
/*    */   
/*    */   public int getTransactionId() {
/* 37 */     return this.transactionId;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public CompoundTag getTag() {
/* 42 */     return this.tag;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSkippable() {
/* 47 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundTagQueryPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */