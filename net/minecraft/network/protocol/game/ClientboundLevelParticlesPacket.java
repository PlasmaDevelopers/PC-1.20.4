/*     */ package net.minecraft.network.protocol.game;
/*     */ 
/*     */ import net.minecraft.core.IdMap;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleType;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraft.network.PacketListener;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClientboundLevelParticlesPacket
/*     */   implements Packet<ClientGamePacketListener>
/*     */ {
/*     */   private final double x;
/*     */   private final double y;
/*     */   private final double z;
/*     */   private final float xDist;
/*     */   private final float yDist;
/*     */   
/*     */   public <T extends ParticleOptions> ClientboundLevelParticlesPacket(T $$0, boolean $$1, double $$2, double $$3, double $$4, float $$5, float $$6, float $$7, float $$8, int $$9) {
/*  23 */     this.particle = (ParticleOptions)$$0;
/*  24 */     this.overrideLimiter = $$1;
/*  25 */     this.x = $$2;
/*  26 */     this.y = $$3;
/*  27 */     this.z = $$4;
/*  28 */     this.xDist = $$5;
/*  29 */     this.yDist = $$6;
/*  30 */     this.zDist = $$7;
/*  31 */     this.maxSpeed = $$8;
/*  32 */     this.count = $$9;
/*     */   }
/*     */   private final float zDist; private final float maxSpeed; private final int count; private final boolean overrideLimiter; private final ParticleOptions particle;
/*     */   public ClientboundLevelParticlesPacket(FriendlyByteBuf $$0) {
/*  36 */     ParticleType<?> $$1 = (ParticleType)$$0.readById((IdMap)BuiltInRegistries.PARTICLE_TYPE);
/*  37 */     this.overrideLimiter = $$0.readBoolean();
/*  38 */     this.x = $$0.readDouble();
/*  39 */     this.y = $$0.readDouble();
/*  40 */     this.z = $$0.readDouble();
/*  41 */     this.xDist = $$0.readFloat();
/*  42 */     this.yDist = $$0.readFloat();
/*  43 */     this.zDist = $$0.readFloat();
/*  44 */     this.maxSpeed = $$0.readFloat();
/*  45 */     this.count = $$0.readInt();
/*  46 */     this.particle = readParticle($$0, $$1);
/*     */   }
/*     */   
/*     */   private <T extends ParticleOptions> T readParticle(FriendlyByteBuf $$0, ParticleType<T> $$1) {
/*  50 */     return (T)$$1.getDeserializer().fromNetwork($$1, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(FriendlyByteBuf $$0) {
/*  55 */     $$0.writeId((IdMap)BuiltInRegistries.PARTICLE_TYPE, this.particle.getType());
/*  56 */     $$0.writeBoolean(this.overrideLimiter);
/*  57 */     $$0.writeDouble(this.x);
/*  58 */     $$0.writeDouble(this.y);
/*  59 */     $$0.writeDouble(this.z);
/*  60 */     $$0.writeFloat(this.xDist);
/*  61 */     $$0.writeFloat(this.yDist);
/*  62 */     $$0.writeFloat(this.zDist);
/*  63 */     $$0.writeFloat(this.maxSpeed);
/*  64 */     $$0.writeInt(this.count);
/*  65 */     this.particle.writeToNetwork($$0);
/*     */   }
/*     */   
/*     */   public boolean isOverrideLimiter() {
/*  69 */     return this.overrideLimiter;
/*     */   }
/*     */   
/*     */   public double getX() {
/*  73 */     return this.x;
/*     */   }
/*     */   
/*     */   public double getY() {
/*  77 */     return this.y;
/*     */   }
/*     */   
/*     */   public double getZ() {
/*  81 */     return this.z;
/*     */   }
/*     */   
/*     */   public float getXDist() {
/*  85 */     return this.xDist;
/*     */   }
/*     */   
/*     */   public float getYDist() {
/*  89 */     return this.yDist;
/*     */   }
/*     */   
/*     */   public float getZDist() {
/*  93 */     return this.zDist;
/*     */   }
/*     */   
/*     */   public float getMaxSpeed() {
/*  97 */     return this.maxSpeed;
/*     */   }
/*     */   
/*     */   public int getCount() {
/* 101 */     return this.count;
/*     */   }
/*     */   
/*     */   public ParticleOptions getParticle() {
/* 105 */     return this.particle;
/*     */   }
/*     */ 
/*     */   
/*     */   public void handle(ClientGamePacketListener $$0) {
/* 110 */     $$0.handleParticleEvent(this);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundLevelParticlesPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */