/*     */ package net.minecraft.network.protocol.game;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.IdMap;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleType;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraft.network.PacketListener;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.level.Explosion;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClientboundExplodePacket
/*     */   implements Packet<ClientGamePacketListener>
/*     */ {
/*     */   private final double x;
/*     */   private final double y;
/*     */   private final double z;
/*     */   private final float power;
/*     */   private final List<BlockPos> toBlow;
/*     */   private final float knockbackX;
/*     */   
/*     */   public ClientboundExplodePacket(double $$0, double $$1, double $$2, float $$3, List<BlockPos> $$4, @Nullable Vec3 $$5, Explosion.BlockInteraction $$6, ParticleOptions $$7, ParticleOptions $$8, SoundEvent $$9) {
/*  33 */     this.x = $$0;
/*  34 */     this.y = $$1;
/*  35 */     this.z = $$2;
/*  36 */     this.power = $$3;
/*  37 */     this.toBlow = Lists.newArrayList($$4);
/*  38 */     this.explosionSound = $$9;
/*     */     
/*  40 */     if ($$5 != null) {
/*  41 */       this.knockbackX = (float)$$5.x;
/*  42 */       this.knockbackY = (float)$$5.y;
/*  43 */       this.knockbackZ = (float)$$5.z;
/*     */     } else {
/*  45 */       this.knockbackX = 0.0F;
/*  46 */       this.knockbackY = 0.0F;
/*  47 */       this.knockbackZ = 0.0F;
/*     */     } 
/*  49 */     this.blockInteraction = $$6;
/*  50 */     this.smallExplosionParticles = $$7;
/*  51 */     this.largeExplosionParticles = $$8;
/*     */   }
/*     */   private final float knockbackY; private final float knockbackZ; private final ParticleOptions smallExplosionParticles; private final ParticleOptions largeExplosionParticles; private final Explosion.BlockInteraction blockInteraction; private final SoundEvent explosionSound;
/*     */   public ClientboundExplodePacket(FriendlyByteBuf $$0) {
/*  55 */     this.x = $$0.readDouble();
/*  56 */     this.y = $$0.readDouble();
/*  57 */     this.z = $$0.readDouble();
/*  58 */     this.power = $$0.readFloat();
/*     */     
/*  60 */     int $$1 = Mth.floor(this.x);
/*  61 */     int $$2 = Mth.floor(this.y);
/*  62 */     int $$3 = Mth.floor(this.z);
/*     */     
/*  64 */     this.toBlow = $$0.readList($$3 -> {
/*     */           int $$4 = $$3.readByte() + $$0;
/*     */           
/*     */           int $$5 = $$3.readByte() + $$1;
/*     */           int $$6 = $$3.readByte() + $$2;
/*     */           return new BlockPos($$4, $$5, $$6);
/*     */         });
/*  71 */     this.knockbackX = $$0.readFloat();
/*  72 */     this.knockbackY = $$0.readFloat();
/*  73 */     this.knockbackZ = $$0.readFloat();
/*     */     
/*  75 */     this.blockInteraction = (Explosion.BlockInteraction)$$0.readEnum(Explosion.BlockInteraction.class);
/*  76 */     this.smallExplosionParticles = readParticle($$0, (ParticleType<ParticleOptions>)$$0.readById((IdMap)BuiltInRegistries.PARTICLE_TYPE));
/*  77 */     this.largeExplosionParticles = readParticle($$0, (ParticleType<ParticleOptions>)$$0.readById((IdMap)BuiltInRegistries.PARTICLE_TYPE));
/*  78 */     this.explosionSound = SoundEvent.readFromNetwork($$0);
/*     */   }
/*     */   
/*     */   public void writeParticle(FriendlyByteBuf $$0, ParticleOptions $$1) {
/*  82 */     $$0.writeId((IdMap)BuiltInRegistries.PARTICLE_TYPE, $$1.getType());
/*  83 */     $$1.writeToNetwork($$0);
/*     */   }
/*     */   
/*     */   private <T extends ParticleOptions> T readParticle(FriendlyByteBuf $$0, ParticleType<T> $$1) {
/*  87 */     return (T)$$1.getDeserializer().fromNetwork($$1, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(FriendlyByteBuf $$0) {
/*  92 */     $$0.writeDouble(this.x);
/*  93 */     $$0.writeDouble(this.y);
/*  94 */     $$0.writeDouble(this.z);
/*  95 */     $$0.writeFloat(this.power);
/*     */     
/*  97 */     int $$1 = Mth.floor(this.x);
/*  98 */     int $$2 = Mth.floor(this.y);
/*  99 */     int $$3 = Mth.floor(this.z);
/*     */     
/* 101 */     $$0.writeCollection(this.toBlow, ($$3, $$4) -> {
/*     */           int $$5 = $$4.getX() - $$0;
/*     */           
/*     */           int $$6 = $$4.getY() - $$1;
/*     */           int $$7 = $$4.getZ() - $$2;
/*     */           $$3.writeByte($$5);
/*     */           $$3.writeByte($$6);
/*     */           $$3.writeByte($$7);
/*     */         });
/* 110 */     $$0.writeFloat(this.knockbackX);
/* 111 */     $$0.writeFloat(this.knockbackY);
/* 112 */     $$0.writeFloat(this.knockbackZ);
/*     */     
/* 114 */     $$0.writeEnum((Enum)this.blockInteraction);
/* 115 */     writeParticle($$0, this.smallExplosionParticles);
/* 116 */     writeParticle($$0, this.largeExplosionParticles);
/* 117 */     this.explosionSound.writeToNetwork($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void handle(ClientGamePacketListener $$0) {
/* 122 */     $$0.handleExplosion(this);
/*     */   }
/*     */   
/*     */   public float getKnockbackX() {
/* 126 */     return this.knockbackX;
/*     */   }
/*     */   
/*     */   public float getKnockbackY() {
/* 130 */     return this.knockbackY;
/*     */   }
/*     */   
/*     */   public float getKnockbackZ() {
/* 134 */     return this.knockbackZ;
/*     */   }
/*     */   
/*     */   public double getX() {
/* 138 */     return this.x;
/*     */   }
/*     */   
/*     */   public double getY() {
/* 142 */     return this.y;
/*     */   }
/*     */   
/*     */   public double getZ() {
/* 146 */     return this.z;
/*     */   }
/*     */   
/*     */   public float getPower() {
/* 150 */     return this.power;
/*     */   }
/*     */   
/*     */   public List<BlockPos> getToBlow() {
/* 154 */     return this.toBlow;
/*     */   }
/*     */   
/*     */   public Explosion.BlockInteraction getBlockInteraction() {
/* 158 */     return this.blockInteraction;
/*     */   }
/*     */   
/*     */   public ParticleOptions getSmallExplosionParticles() {
/* 162 */     return this.smallExplosionParticles;
/*     */   }
/*     */   
/*     */   public ParticleOptions getLargeExplosionParticles() {
/* 166 */     return this.largeExplosionParticles;
/*     */   }
/*     */   
/*     */   public SoundEvent getExplosionSound() {
/* 170 */     return this.explosionSound;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundExplodePacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */