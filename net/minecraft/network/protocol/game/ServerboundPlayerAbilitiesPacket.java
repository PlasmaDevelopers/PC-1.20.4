/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.world.entity.player.Abilities;
/*    */ 
/*    */ public class ServerboundPlayerAbilitiesPacket implements Packet<ServerGamePacketListener> {
/*    */   private static final int FLAG_FLYING = 2;
/*    */   private final boolean isFlying;
/*    */   
/*    */   public ServerboundPlayerAbilitiesPacket(Abilities $$0) {
/* 13 */     this.isFlying = $$0.flying;
/*    */   }
/*    */   
/*    */   public ServerboundPlayerAbilitiesPacket(FriendlyByteBuf $$0) {
/* 17 */     byte $$1 = $$0.readByte();
/* 18 */     this.isFlying = (($$1 & 0x2) != 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 23 */     byte $$1 = 0;
/* 24 */     if (this.isFlying) {
/* 25 */       $$1 = (byte)($$1 | 0x2);
/*    */     }
/* 27 */     $$0.writeByte($$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ServerGamePacketListener $$0) {
/* 32 */     $$0.handlePlayerAbilities(this);
/*    */   }
/*    */   
/*    */   public boolean isFlying() {
/* 36 */     return this.isFlying;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ServerboundPlayerAbilitiesPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */