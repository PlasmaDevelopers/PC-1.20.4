/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ 
/*    */ public class ClientboundSetExperiencePacket
/*    */   implements Packet<ClientGamePacketListener> {
/*    */   private final float experienceProgress;
/*    */   
/*    */   public ClientboundSetExperiencePacket(float $$0, int $$1, int $$2) {
/* 12 */     this.experienceProgress = $$0;
/* 13 */     this.totalExperience = $$1;
/* 14 */     this.experienceLevel = $$2;
/*    */   }
/*    */   private final int totalExperience; private final int experienceLevel;
/*    */   public ClientboundSetExperiencePacket(FriendlyByteBuf $$0) {
/* 18 */     this.experienceProgress = $$0.readFloat();
/* 19 */     this.experienceLevel = $$0.readVarInt();
/* 20 */     this.totalExperience = $$0.readVarInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 25 */     $$0.writeFloat(this.experienceProgress);
/* 26 */     $$0.writeVarInt(this.experienceLevel);
/* 27 */     $$0.writeVarInt(this.totalExperience);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 32 */     $$0.handleSetExperience(this);
/*    */   }
/*    */   
/*    */   public float getExperienceProgress() {
/* 36 */     return this.experienceProgress;
/*    */   }
/*    */   
/*    */   public int getTotalExperience() {
/* 40 */     return this.totalExperience;
/*    */   }
/*    */   
/*    */   public int getExperienceLevel() {
/* 44 */     return this.experienceLevel;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundSetExperiencePacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */