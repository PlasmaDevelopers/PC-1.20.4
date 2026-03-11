/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.sounds.SoundEvent;
/*    */ import net.minecraft.sounds.SoundSource;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ 
/*    */ public class ClientboundSoundEntityPacket
/*    */   implements Packet<ClientGamePacketListener>
/*    */ {
/*    */   private final Holder<SoundEvent> sound;
/*    */   private final SoundSource source;
/*    */   private final int id;
/*    */   
/*    */   public ClientboundSoundEntityPacket(Holder<SoundEvent> $$0, SoundSource $$1, Entity $$2, float $$3, float $$4, long $$5) {
/* 20 */     this.sound = $$0;
/* 21 */     this.source = $$1;
/* 22 */     this.id = $$2.getId();
/* 23 */     this.volume = $$3;
/* 24 */     this.pitch = $$4;
/* 25 */     this.seed = $$5;
/*    */   }
/*    */   private final float volume; private final float pitch; private final long seed;
/*    */   public ClientboundSoundEntityPacket(FriendlyByteBuf $$0) {
/* 29 */     this.sound = $$0.readById(BuiltInRegistries.SOUND_EVENT.asHolderIdMap(), SoundEvent::readFromNetwork);
/* 30 */     this.source = (SoundSource)$$0.readEnum(SoundSource.class);
/* 31 */     this.id = $$0.readVarInt();
/* 32 */     this.volume = $$0.readFloat();
/* 33 */     this.pitch = $$0.readFloat();
/* 34 */     this.seed = $$0.readLong();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 39 */     $$0.writeId(BuiltInRegistries.SOUND_EVENT.asHolderIdMap(), this.sound, ($$0, $$1) -> $$1.writeToNetwork($$0));
/* 40 */     $$0.writeEnum((Enum)this.source);
/* 41 */     $$0.writeVarInt(this.id);
/* 42 */     $$0.writeFloat(this.volume);
/* 43 */     $$0.writeFloat(this.pitch);
/* 44 */     $$0.writeLong(this.seed);
/*    */   }
/*    */   
/*    */   public Holder<SoundEvent> getSound() {
/* 48 */     return this.sound;
/*    */   }
/*    */   
/*    */   public SoundSource getSource() {
/* 52 */     return this.source;
/*    */   }
/*    */   
/*    */   public int getId() {
/* 56 */     return this.id;
/*    */   }
/*    */   
/*    */   public float getVolume() {
/* 60 */     return this.volume;
/*    */   }
/*    */   
/*    */   public float getPitch() {
/* 64 */     return this.pitch;
/*    */   }
/*    */   
/*    */   public long getSeed() {
/* 68 */     return this.seed;
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 73 */     $$0.handleSoundEntityEvent(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundSoundEntityPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */