/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ public class ClientboundSelectAdvancementsTabPacket implements Packet<ClientGamePacketListener> {
/*    */   @Nullable
/*    */   private final ResourceLocation tab;
/*    */   
/*    */   public ClientboundSelectAdvancementsTabPacket(@Nullable ResourceLocation $$0) {
/* 14 */     this.tab = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 19 */     $$0.handleSelectAdvancementsTab(this);
/*    */   }
/*    */   
/*    */   public ClientboundSelectAdvancementsTabPacket(FriendlyByteBuf $$0) {
/* 23 */     this.tab = (ResourceLocation)$$0.readNullable(FriendlyByteBuf::readResourceLocation);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 28 */     $$0.writeNullable(this.tab, FriendlyByteBuf::writeResourceLocation);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public ResourceLocation getTab() {
/* 33 */     return this.tab;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundSelectAdvancementsTabPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */