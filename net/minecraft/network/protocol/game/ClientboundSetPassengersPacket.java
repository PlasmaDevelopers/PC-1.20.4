/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ 
/*    */ public class ClientboundSetPassengersPacket implements Packet<ClientGamePacketListener> {
/*    */   private final int vehicle;
/*    */   private final int[] passengers;
/*    */   
/*    */   public ClientboundSetPassengersPacket(Entity $$0) {
/* 14 */     this.vehicle = $$0.getId();
/* 15 */     List<Entity> $$1 = $$0.getPassengers();
/* 16 */     this.passengers = new int[$$1.size()];
/*    */     
/* 18 */     for (int $$2 = 0; $$2 < $$1.size(); $$2++) {
/* 19 */       this.passengers[$$2] = ((Entity)$$1.get($$2)).getId();
/*    */     }
/*    */   }
/*    */   
/*    */   public ClientboundSetPassengersPacket(FriendlyByteBuf $$0) {
/* 24 */     this.vehicle = $$0.readVarInt();
/* 25 */     this.passengers = $$0.readVarIntArray();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 30 */     $$0.writeVarInt(this.vehicle);
/* 31 */     $$0.writeVarIntArray(this.passengers);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 36 */     $$0.handleSetEntityPassengersPacket(this);
/*    */   }
/*    */   
/*    */   public int[] getPassengers() {
/* 40 */     return this.passengers;
/*    */   }
/*    */   
/*    */   public int getVehicle() {
/* 44 */     return this.vehicle;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundSetPassengersPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */