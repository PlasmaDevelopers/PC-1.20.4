/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.core.NonNullList;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ public class ClientboundContainerSetContentPacket
/*    */   implements Packet<ClientGamePacketListener> {
/*    */   private final int containerId;
/*    */   private final int stateId;
/*    */   private final List<ItemStack> items;
/*    */   private final ItemStack carriedItem;
/*    */   
/*    */   public ClientboundContainerSetContentPacket(int $$0, int $$1, NonNullList<ItemStack> $$2, ItemStack $$3) {
/* 18 */     this.containerId = $$0;
/* 19 */     this.stateId = $$1;
/* 20 */     this.items = (List<ItemStack>)NonNullList.withSize($$2.size(), ItemStack.EMPTY);
/* 21 */     for (int $$4 = 0; $$4 < $$2.size(); $$4++) {
/* 22 */       this.items.set($$4, ((ItemStack)$$2.get($$4)).copy());
/*    */     }
/* 24 */     this.carriedItem = $$3.copy();
/*    */   }
/*    */   
/*    */   public ClientboundContainerSetContentPacket(FriendlyByteBuf $$0) {
/* 28 */     this.containerId = $$0.readUnsignedByte();
/* 29 */     this.stateId = $$0.readVarInt();
/* 30 */     this.items = (List<ItemStack>)$$0.readCollection(NonNullList::createWithCapacity, FriendlyByteBuf::readItem);
/* 31 */     this.carriedItem = $$0.readItem();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 36 */     $$0.writeByte(this.containerId);
/* 37 */     $$0.writeVarInt(this.stateId);
/* 38 */     $$0.writeCollection(this.items, FriendlyByteBuf::writeItem);
/* 39 */     $$0.writeItem(this.carriedItem);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 44 */     $$0.handleContainerContent(this);
/*    */   }
/*    */   
/*    */   public int getContainerId() {
/* 48 */     return this.containerId;
/*    */   }
/*    */   
/*    */   public List<ItemStack> getItems() {
/* 52 */     return this.items;
/*    */   }
/*    */   
/*    */   public ItemStack getCarriedItem() {
/* 56 */     return this.carriedItem;
/*    */   }
/*    */   
/*    */   public int getStateId() {
/* 60 */     return this.stateId;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundContainerSetContentPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */