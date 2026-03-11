/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ public class ServerboundSetCreativeModeSlotPacket implements Packet<ServerGamePacketListener> {
/*    */   private final int slotNum;
/*    */   
/*    */   public ServerboundSetCreativeModeSlotPacket(int $$0, ItemStack $$1) {
/* 12 */     this.slotNum = $$0;
/* 13 */     this.itemStack = $$1.copy();
/*    */   }
/*    */   private final ItemStack itemStack;
/*    */   
/*    */   public void handle(ServerGamePacketListener $$0) {
/* 18 */     $$0.handleSetCreativeModeSlot(this);
/*    */   }
/*    */   
/*    */   public ServerboundSetCreativeModeSlotPacket(FriendlyByteBuf $$0) {
/* 22 */     this.slotNum = $$0.readShort();
/* 23 */     this.itemStack = $$0.readItem();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 28 */     $$0.writeShort(this.slotNum);
/* 29 */     $$0.writeItem(this.itemStack);
/*    */   }
/*    */   
/*    */   public int getSlotNum() {
/* 33 */     return this.slotNum;
/*    */   }
/*    */   
/*    */   public ItemStack getItem() {
/* 37 */     return this.itemStack;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ServerboundSetCreativeModeSlotPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */