/*     */ package net.minecraft.client.particle;
/*     */ 
/*     */ import java.util.Optional;
/*     */ import net.minecraft.client.multiplayer.ClientLevel;
/*     */ import net.minecraft.core.particles.ParticleGroup;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.SimpleParticleType;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ 
/*     */ public class SuspendedParticle
/*     */   extends TextureSheetParticle {
/*     */   SuspendedParticle(ClientLevel $$0, SpriteSet $$1, double $$2, double $$3, double $$4) {
/*  14 */     super($$0, $$2, $$3 - 0.125D, $$4);
/*  15 */     setSize(0.01F, 0.01F);
/*  16 */     pickSprite($$1);
/*     */     
/*  18 */     this.quadSize *= this.random.nextFloat() * 0.6F + 0.2F;
/*  19 */     this.lifetime = (int)(16.0D / (Math.random() * 0.8D + 0.2D));
/*  20 */     this.hasPhysics = false;
/*     */     
/*  22 */     this.friction = 1.0F;
/*  23 */     this.gravity = 0.0F;
/*     */   }
/*     */   
/*     */   SuspendedParticle(ClientLevel $$0, SpriteSet $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/*  27 */     super($$0, $$2, $$3 - 0.125D, $$4, $$5, $$6, $$7);
/*  28 */     setSize(0.01F, 0.01F);
/*  29 */     pickSprite($$1);
/*     */     
/*  31 */     this.quadSize *= this.random.nextFloat() * 0.6F + 0.6F;
/*  32 */     this.lifetime = (int)(16.0D / (Math.random() * 0.8D + 0.2D));
/*  33 */     this.hasPhysics = false;
/*     */     
/*  35 */     this.friction = 1.0F;
/*  36 */     this.gravity = 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public ParticleRenderType getRenderType() {
/*  41 */     return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
/*     */   }
/*     */   
/*     */   public static class UnderwaterProvider implements ParticleProvider<SimpleParticleType> {
/*     */     private final SpriteSet sprite;
/*     */     
/*     */     public UnderwaterProvider(SpriteSet $$0) {
/*  48 */       this.sprite = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/*  53 */       SuspendedParticle $$8 = new SuspendedParticle($$1, this.sprite, $$2, $$3, $$4);
/*  54 */       $$8.setColor(0.4F, 0.4F, 0.7F);
/*  55 */       return $$8;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class SporeBlossomAirProvider implements ParticleProvider<SimpleParticleType> {
/*     */     private final SpriteSet sprite;
/*     */     
/*     */     public SporeBlossomAirProvider(SpriteSet $$0) {
/*  63 */       this.sprite = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/*  68 */       SuspendedParticle $$8 = new SuspendedParticle($$1, this.sprite, $$2, $$3, $$4, 0.0D, -0.800000011920929D, 0.0D)
/*     */         {
/*     */           public Optional<ParticleGroup> getParticleGroup() {
/*  71 */             return Optional.of(ParticleGroup.SPORE_BLOSSOM);
/*     */           }
/*     */         };
/*  74 */       $$8.lifetime = Mth.randomBetweenInclusive($$1.random, 500, 1000);
/*  75 */       $$8.gravity = 0.01F;
/*  76 */       $$8.setColor(0.32F, 0.5F, 0.22F);
/*  77 */       return $$8;
/*     */     }
/*     */   } class null extends SuspendedParticle { null(ClientLevel $$1, SpriteSet $$2, double $$3, double $$4, double $$5, double $$6, double $$7, double $$8) {
/*     */       super($$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8);
/*     */     } public Optional<ParticleGroup> getParticleGroup() {
/*     */       return Optional.of(ParticleGroup.SPORE_BLOSSOM);
/*     */     } }
/*     */   public static class CrimsonSporeProvider implements ParticleProvider<SimpleParticleType> { public CrimsonSporeProvider(SpriteSet $$0) {
/*  85 */       this.sprite = $$0;
/*     */     }
/*     */     private final SpriteSet sprite;
/*     */     
/*     */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/*  90 */       RandomSource $$8 = $$1.random;
/*  91 */       double $$9 = $$8.nextGaussian() * 9.999999974752427E-7D;
/*  92 */       double $$10 = $$8.nextGaussian() * 9.999999747378752E-5D;
/*  93 */       double $$11 = $$8.nextGaussian() * 9.999999974752427E-7D;
/*  94 */       SuspendedParticle $$12 = new SuspendedParticle($$1, this.sprite, $$2, $$3, $$4, $$9, $$10, $$11);
/*  95 */       $$12.setColor(0.9F, 0.4F, 0.5F);
/*  96 */       return $$12;
/*     */     } }
/*     */ 
/*     */   
/*     */   public static class WarpedSporeProvider implements ParticleProvider<SimpleParticleType> {
/*     */     private final SpriteSet sprite;
/*     */     
/*     */     public WarpedSporeProvider(SpriteSet $$0) {
/* 104 */       this.sprite = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 109 */       double $$8 = $$1.random.nextFloat() * -1.9D * $$1.random.nextFloat() * 0.1D;
/* 110 */       SuspendedParticle $$9 = new SuspendedParticle($$1, this.sprite, $$2, $$3, $$4, 0.0D, $$8, 0.0D);
/* 111 */       $$9.setColor(0.1F, 0.1F, 0.3F);
/* 112 */       $$9.setSize(0.001F, 0.001F);
/* 113 */       return $$9;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\SuspendedParticle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */