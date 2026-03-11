/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.sounds.SoundSource;
/*    */ 
/*    */ public class ClientboundStopSoundPacket
/*    */   implements Packet<ClientGamePacketListener> {
/*    */   private static final int HAS_SOURCE = 1;
/*    */   private static final int HAS_SOUND = 2;
/*    */   @Nullable
/*    */   private final ResourceLocation name;
/*    */   @Nullable
/*    */   private final SoundSource source;
/*    */   
/*    */   public ClientboundStopSoundPacket(@Nullable ResourceLocation $$0, @Nullable SoundSource $$1) {
/* 20 */     this.name = $$0;
/* 21 */     this.source = $$1;
/*    */   }
/*    */   
/*    */   public ClientboundStopSoundPacket(FriendlyByteBuf $$0) {
/* 25 */     int $$1 = $$0.readByte();
/* 26 */     if (($$1 & 0x1) > 0) {
/* 27 */       this.source = (SoundSource)$$0.readEnum(SoundSource.class);
/*    */     } else {
/* 29 */       this.source = null;
/*    */     } 
/* 31 */     if (($$1 & 0x2) > 0) {
/* 32 */       this.name = $$0.readResourceLocation();
/*    */     } else {
/* 34 */       this.name = null;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 40 */     if (this.source != null) {
/* 41 */       if (this.name != null) {
/* 42 */         $$0.writeByte(3);
/* 43 */         $$0.writeEnum((Enum)this.source);
/* 44 */         $$0.writeResourceLocation(this.name);
/*    */       } else {
/* 46 */         $$0.writeByte(1);
/* 47 */         $$0.writeEnum((Enum)this.source);
/*    */       }
/*    */     
/* 50 */     } else if (this.name != null) {
/* 51 */       $$0.writeByte(2);
/* 52 */       $$0.writeResourceLocation(this.name);
/*    */     } else {
/* 54 */       $$0.writeByte(0);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public ResourceLocation getName() {
/* 61 */     return this.name;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public SoundSource getSource() {
/* 66 */     return this.source;
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 71 */     $$0.handleStopSoundEvent(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundStopSoundPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */