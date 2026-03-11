/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ 
/*    */ public class ServerboundPlayerInputPacket implements Packet<ServerGamePacketListener> {
/*    */   private static final int FLAG_JUMPING = 1;
/*    */   private static final int FLAG_SHIFT_KEY_DOWN = 2;
/*    */   private final float xxa;
/*    */   private final float zza;
/*    */   private final boolean isJumping;
/*    */   private final boolean isShiftKeyDown;
/*    */   
/*    */   public ServerboundPlayerInputPacket(float $$0, float $$1, boolean $$2, boolean $$3) {
/* 16 */     this.xxa = $$0;
/* 17 */     this.zza = $$1;
/* 18 */     this.isJumping = $$2;
/* 19 */     this.isShiftKeyDown = $$3;
/*    */   }
/*    */   
/*    */   public ServerboundPlayerInputPacket(FriendlyByteBuf $$0) {
/* 23 */     this.xxa = $$0.readFloat();
/* 24 */     this.zza = $$0.readFloat();
/*    */     
/* 26 */     byte $$1 = $$0.readByte();
/* 27 */     this.isJumping = (($$1 & 0x1) > 0);
/* 28 */     this.isShiftKeyDown = (($$1 & 0x2) > 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 33 */     $$0.writeFloat(this.xxa);
/* 34 */     $$0.writeFloat(this.zza);
/*    */     
/* 36 */     byte $$1 = 0;
/* 37 */     if (this.isJumping) {
/* 38 */       $$1 = (byte)($$1 | 0x1);
/*    */     }
/* 40 */     if (this.isShiftKeyDown) {
/* 41 */       $$1 = (byte)($$1 | 0x2);
/*    */     }
/* 43 */     $$0.writeByte($$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ServerGamePacketListener $$0) {
/* 48 */     $$0.handlePlayerInput(this);
/*    */   }
/*    */   
/*    */   public float getXxa() {
/* 52 */     return this.xxa;
/*    */   }
/*    */   
/*    */   public float getZza() {
/* 56 */     return this.zza;
/*    */   }
/*    */   
/*    */   public boolean isJumping() {
/* 60 */     return this.isJumping;
/*    */   }
/*    */   
/*    */   public boolean isShiftKeyDown() {
/* 64 */     return this.isShiftKeyDown;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ServerboundPlayerInputPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */