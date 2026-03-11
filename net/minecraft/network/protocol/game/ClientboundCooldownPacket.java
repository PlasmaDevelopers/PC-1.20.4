/*    */ package net.minecraft.network.protocol.game;
/*    */ import net.minecraft.core.IdMap;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.world.item.Item;
/*    */ 
/*    */ public class ClientboundCooldownPacket implements Packet<ClientGamePacketListener> {
/*    */   private final Item item;
/*    */   
/*    */   public ClientboundCooldownPacket(Item $$0, int $$1) {
/* 13 */     this.item = $$0;
/* 14 */     this.duration = $$1;
/*    */   }
/*    */   private final int duration;
/*    */   public ClientboundCooldownPacket(FriendlyByteBuf $$0) {
/* 18 */     this.item = (Item)$$0.readById((IdMap)BuiltInRegistries.ITEM);
/* 19 */     this.duration = $$0.readVarInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 24 */     $$0.writeId((IdMap)BuiltInRegistries.ITEM, this.item);
/* 25 */     $$0.writeVarInt(this.duration);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 30 */     $$0.handleItemCooldown(this);
/*    */   }
/*    */   
/*    */   public Item getItem() {
/* 34 */     return this.item;
/*    */   }
/*    */   
/*    */   public int getDuration() {
/* 38 */     return this.duration;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundCooldownPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */