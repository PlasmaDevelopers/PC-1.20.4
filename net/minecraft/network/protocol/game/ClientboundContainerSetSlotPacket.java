/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ public class ClientboundContainerSetSlotPacket
/*    */   implements Packet<ClientGamePacketListener> {
/*    */   public static final int CARRIED_ITEM = -1;
/*    */   public static final int PLAYER_INVENTORY = -2;
/*    */   private final int containerId;
/*    */   private final int stateId;
/*    */   private final int slot;
/*    */   private final ItemStack itemStack;
/*    */   
/*    */   public ClientboundContainerSetSlotPacket(int $$0, int $$1, int $$2, ItemStack $$3) {
/* 18 */     this.containerId = $$0;
/* 19 */     this.stateId = $$1;
/* 20 */     this.slot = $$2;
/* 21 */     this.itemStack = $$3.copy();
/*    */   }
/*    */   
/*    */   public ClientboundContainerSetSlotPacket(FriendlyByteBuf $$0) {
/* 25 */     this.containerId = $$0.readByte();
/* 26 */     this.stateId = $$0.readVarInt();
/* 27 */     this.slot = $$0.readShort();
/* 28 */     this.itemStack = $$0.readItem();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 33 */     $$0.writeByte(this.containerId);
/* 34 */     $$0.writeVarInt(this.stateId);
/* 35 */     $$0.writeShort(this.slot);
/* 36 */     $$0.writeItem(this.itemStack);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 41 */     $$0.handleContainerSetSlot(this);
/*    */   }
/*    */   
/*    */   public int getContainerId() {
/* 45 */     return this.containerId;
/*    */   }
/*    */   
/*    */   public int getSlot() {
/* 49 */     return this.slot;
/*    */   }
/*    */   
/*    */   public ItemStack getItem() {
/* 53 */     return this.itemStack;
/*    */   }
/*    */   
/*    */   public int getStateId() {
/* 57 */     return this.stateId;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundContainerSetSlotPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */