/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import java.util.List;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.world.entity.EquipmentSlot;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ public class ClientboundSetEquipmentPacket implements Packet<ClientGamePacketListener> {
/*    */   private static final byte CONTINUE_MASK = -128;
/*    */   private final int entity;
/*    */   private final List<Pair<EquipmentSlot, ItemStack>> slots;
/*    */   
/*    */   public ClientboundSetEquipmentPacket(int $$0, List<Pair<EquipmentSlot, ItemStack>> $$1) {
/* 18 */     this.entity = $$0;
/* 19 */     this.slots = $$1;
/*    */   }
/*    */   public ClientboundSetEquipmentPacket(FriendlyByteBuf $$0) {
/*    */     int $$2;
/* 23 */     this.entity = $$0.readVarInt();
/* 24 */     EquipmentSlot[] $$1 = EquipmentSlot.values();
/*    */     
/* 26 */     this.slots = Lists.newArrayList();
/*    */     do {
/* 28 */       $$2 = $$0.readByte();
/* 29 */       EquipmentSlot $$3 = $$1[$$2 & 0x7F];
/* 30 */       ItemStack $$4 = $$0.readItem();
/* 31 */       this.slots.add(Pair.of($$3, $$4));
/* 32 */     } while (($$2 & 0xFFFFFF80) != 0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 40 */     $$0.writeVarInt(this.entity);
/*    */     
/* 42 */     int $$1 = this.slots.size();
/* 43 */     for (int $$2 = 0; $$2 < $$1; $$2++) {
/* 44 */       Pair<EquipmentSlot, ItemStack> $$3 = this.slots.get($$2);
/* 45 */       EquipmentSlot $$4 = (EquipmentSlot)$$3.getFirst();
/* 46 */       boolean $$5 = ($$2 != $$1 - 1);
/* 47 */       int $$6 = $$4.ordinal();
/* 48 */       $$0.writeByte($$5 ? ($$6 | 0xFFFFFF80) : $$6);
/* 49 */       $$0.writeItem((ItemStack)$$3.getSecond());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 55 */     $$0.handleSetEquipment(this);
/*    */   }
/*    */   
/*    */   public int getEntity() {
/* 59 */     return this.entity;
/*    */   }
/*    */   
/*    */   public List<Pair<EquipmentSlot, ItemStack>> getSlots() {
/* 63 */     return this.slots;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundSetEquipmentPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */