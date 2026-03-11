/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.world.item.trading.MerchantOffers;
/*    */ 
/*    */ public class ClientboundMerchantOffersPacket
/*    */   implements Packet<ClientGamePacketListener>
/*    */ {
/*    */   private final int containerId;
/*    */   private final MerchantOffers offers;
/*    */   private final int villagerLevel;
/*    */   
/*    */   public ClientboundMerchantOffersPacket(int $$0, MerchantOffers $$1, int $$2, int $$3, boolean $$4, boolean $$5) {
/* 16 */     this.containerId = $$0;
/* 17 */     this.offers = $$1.copy();
/* 18 */     this.villagerLevel = $$2;
/* 19 */     this.villagerXp = $$3;
/* 20 */     this.showProgress = $$4;
/* 21 */     this.canRestock = $$5;
/*    */   }
/*    */   private final int villagerXp; private final boolean showProgress; private final boolean canRestock;
/*    */   public ClientboundMerchantOffersPacket(FriendlyByteBuf $$0) {
/* 25 */     this.containerId = $$0.readVarInt();
/* 26 */     this.offers = MerchantOffers.createFromStream($$0);
/* 27 */     this.villagerLevel = $$0.readVarInt();
/* 28 */     this.villagerXp = $$0.readVarInt();
/* 29 */     this.showProgress = $$0.readBoolean();
/* 30 */     this.canRestock = $$0.readBoolean();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 35 */     $$0.writeVarInt(this.containerId);
/* 36 */     this.offers.writeToStream($$0);
/* 37 */     $$0.writeVarInt(this.villagerLevel);
/* 38 */     $$0.writeVarInt(this.villagerXp);
/* 39 */     $$0.writeBoolean(this.showProgress);
/* 40 */     $$0.writeBoolean(this.canRestock);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 45 */     $$0.handleMerchantOffers(this);
/*    */   }
/*    */   
/*    */   public int getContainerId() {
/* 49 */     return this.containerId;
/*    */   }
/*    */   
/*    */   public MerchantOffers getOffers() {
/* 53 */     return this.offers;
/*    */   }
/*    */   
/*    */   public int getVillagerLevel() {
/* 57 */     return this.villagerLevel;
/*    */   }
/*    */   
/*    */   public int getVillagerXp() {
/* 61 */     return this.villagerXp;
/*    */   }
/*    */   
/*    */   public boolean showProgress() {
/* 65 */     return this.showProgress;
/*    */   }
/*    */   
/*    */   public boolean canRestock() {
/* 69 */     return this.canRestock;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundMerchantOffersPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */