/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.sounds.SoundEvent;
/*    */ import net.minecraft.sounds.SoundSource;
/*    */ 
/*    */ public class ClientboundSoundPacket implements Packet<ClientGamePacketListener> {
/*    */   public static final float LOCATION_ACCURACY = 8.0F;
/*    */   private final Holder<SoundEvent> sound;
/*    */   private final SoundSource source;
/*    */   private final int x;
/*    */   private final int y;
/*    */   private final int z;
/*    */   private final float volume;
/*    */   private final float pitch;
/*    */   private final long seed;
/*    */   
/*    */   public ClientboundSoundPacket(Holder<SoundEvent> $$0, SoundSource $$1, double $$2, double $$3, double $$4, float $$5, float $$6, long $$7) {
/* 23 */     this.sound = $$0;
/* 24 */     this.source = $$1;
/* 25 */     this.x = (int)($$2 * 8.0D);
/* 26 */     this.y = (int)($$3 * 8.0D);
/* 27 */     this.z = (int)($$4 * 8.0D);
/* 28 */     this.volume = $$5;
/* 29 */     this.pitch = $$6;
/* 30 */     this.seed = $$7;
/*    */   }
/*    */   
/*    */   public ClientboundSoundPacket(FriendlyByteBuf $$0) {
/* 34 */     this.sound = $$0.readById(BuiltInRegistries.SOUND_EVENT.asHolderIdMap(), SoundEvent::readFromNetwork);
/* 35 */     this.source = (SoundSource)$$0.readEnum(SoundSource.class);
/* 36 */     this.x = $$0.readInt();
/* 37 */     this.y = $$0.readInt();
/* 38 */     this.z = $$0.readInt();
/* 39 */     this.volume = $$0.readFloat();
/* 40 */     this.pitch = $$0.readFloat();
/* 41 */     this.seed = $$0.readLong();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 46 */     $$0.writeId(BuiltInRegistries.SOUND_EVENT.asHolderIdMap(), this.sound, ($$0, $$1) -> $$1.writeToNetwork($$0));
/* 47 */     $$0.writeEnum((Enum)this.source);
/* 48 */     $$0.writeInt(this.x);
/* 49 */     $$0.writeInt(this.y);
/* 50 */     $$0.writeInt(this.z);
/* 51 */     $$0.writeFloat(this.volume);
/* 52 */     $$0.writeFloat(this.pitch);
/* 53 */     $$0.writeLong(this.seed);
/*    */   }
/*    */   
/*    */   public Holder<SoundEvent> getSound() {
/* 57 */     return this.sound;
/*    */   }
/*    */   
/*    */   public SoundSource getSource() {
/* 61 */     return this.source;
/*    */   }
/*    */   
/*    */   public double getX() {
/* 65 */     return (this.x / 8.0F);
/*    */   }
/*    */   
/*    */   public double getY() {
/* 69 */     return (this.y / 8.0F);
/*    */   }
/*    */   
/*    */   public double getZ() {
/* 73 */     return (this.z / 8.0F);
/*    */   }
/*    */   
/*    */   public float getVolume() {
/* 77 */     return this.volume;
/*    */   }
/*    */   
/*    */   public float getPitch() {
/* 81 */     return this.pitch;
/*    */   }
/*    */   
/*    */   public long getSeed() {
/* 85 */     return this.seed;
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 90 */     $$0.handleSoundEvent(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundSoundPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */