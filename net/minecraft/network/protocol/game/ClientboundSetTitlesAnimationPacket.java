/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ 
/*    */ public class ClientboundSetTitlesAnimationPacket
/*    */   implements Packet<ClientGamePacketListener> {
/*    */   private final int fadeIn;
/*    */   
/*    */   public ClientboundSetTitlesAnimationPacket(int $$0, int $$1, int $$2) {
/* 12 */     this.fadeIn = $$0;
/* 13 */     this.stay = $$1;
/* 14 */     this.fadeOut = $$2;
/*    */   }
/*    */   private final int stay; private final int fadeOut;
/*    */   public ClientboundSetTitlesAnimationPacket(FriendlyByteBuf $$0) {
/* 18 */     this.fadeIn = $$0.readInt();
/* 19 */     this.stay = $$0.readInt();
/* 20 */     this.fadeOut = $$0.readInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 25 */     $$0.writeInt(this.fadeIn);
/* 26 */     $$0.writeInt(this.stay);
/* 27 */     $$0.writeInt(this.fadeOut);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 32 */     $$0.setTitlesAnimation(this);
/*    */   }
/*    */   
/*    */   public int getFadeIn() {
/* 36 */     return this.fadeIn;
/*    */   }
/*    */   
/*    */   public int getStay() {
/* 40 */     return this.stay;
/*    */   }
/*    */   
/*    */   public int getFadeOut() {
/* 44 */     return this.fadeOut;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundSetTitlesAnimationPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */