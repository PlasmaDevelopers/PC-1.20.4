/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.world.entity.player.Abilities;
/*    */ 
/*    */ public class ClientboundPlayerAbilitiesPacket
/*    */   implements Packet<ClientGamePacketListener> {
/*    */   private static final int FLAG_INVULNERABLE = 1;
/*    */   private static final int FLAG_FLYING = 2;
/*    */   private static final int FLAG_CAN_FLY = 4;
/*    */   private static final int FLAG_INSTABUILD = 8;
/*    */   private final boolean invulnerable;
/*    */   private final boolean isFlying;
/*    */   private final boolean canFly;
/*    */   private final boolean instabuild;
/*    */   private final float flyingSpeed;
/*    */   private final float walkingSpeed;
/*    */   
/*    */   public ClientboundPlayerAbilitiesPacket(Abilities $$0) {
/* 22 */     this.invulnerable = $$0.invulnerable;
/* 23 */     this.isFlying = $$0.flying;
/* 24 */     this.canFly = $$0.mayfly;
/* 25 */     this.instabuild = $$0.instabuild;
/* 26 */     this.flyingSpeed = $$0.getFlyingSpeed();
/* 27 */     this.walkingSpeed = $$0.getWalkingSpeed();
/*    */   }
/*    */   
/*    */   public ClientboundPlayerAbilitiesPacket(FriendlyByteBuf $$0) {
/* 31 */     byte $$1 = $$0.readByte();
/*    */     
/* 33 */     this.invulnerable = (($$1 & 0x1) != 0);
/* 34 */     this.isFlying = (($$1 & 0x2) != 0);
/* 35 */     this.canFly = (($$1 & 0x4) != 0);
/* 36 */     this.instabuild = (($$1 & 0x8) != 0);
/* 37 */     this.flyingSpeed = $$0.readFloat();
/* 38 */     this.walkingSpeed = $$0.readFloat();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 43 */     byte $$1 = 0;
/*    */     
/* 45 */     if (this.invulnerable) {
/* 46 */       $$1 = (byte)($$1 | 0x1);
/*    */     }
/* 48 */     if (this.isFlying) {
/* 49 */       $$1 = (byte)($$1 | 0x2);
/*    */     }
/* 51 */     if (this.canFly) {
/* 52 */       $$1 = (byte)($$1 | 0x4);
/*    */     }
/* 54 */     if (this.instabuild) {
/* 55 */       $$1 = (byte)($$1 | 0x8);
/*    */     }
/*    */     
/* 58 */     $$0.writeByte($$1);
/* 59 */     $$0.writeFloat(this.flyingSpeed);
/* 60 */     $$0.writeFloat(this.walkingSpeed);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 65 */     $$0.handlePlayerAbilities(this);
/*    */   }
/*    */   
/*    */   public boolean isInvulnerable() {
/* 69 */     return this.invulnerable;
/*    */   }
/*    */   
/*    */   public boolean isFlying() {
/* 73 */     return this.isFlying;
/*    */   }
/*    */   
/*    */   public boolean canFly() {
/* 77 */     return this.canFly;
/*    */   }
/*    */   
/*    */   public boolean canInstabuild() {
/* 81 */     return this.instabuild;
/*    */   }
/*    */   
/*    */   public float getFlyingSpeed() {
/* 85 */     return this.flyingSpeed;
/*    */   }
/*    */   
/*    */   public float getWalkingSpeed() {
/* 89 */     return this.walkingSpeed;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundPlayerAbilitiesPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */