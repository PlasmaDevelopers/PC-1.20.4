/*     */ package net.minecraft.client.particle;
/*     */ import net.minecraft.client.multiplayer.ClientLevel;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.SimpleParticleType;
/*     */ 
/*     */ public class SuspendedTownParticle extends TextureSheetParticle {
/*     */   SuspendedTownParticle(ClientLevel $$0, double $$1, double $$2, double $$3, double $$4, double $$5, double $$6) {
/*   8 */     super($$0, $$1, $$2, $$3, $$4, $$5, $$6);
/*     */     
/*  10 */     float $$7 = this.random.nextFloat() * 0.1F + 0.2F;
/*  11 */     this.rCol = $$7;
/*  12 */     this.gCol = $$7;
/*  13 */     this.bCol = $$7;
/*  14 */     setSize(0.02F, 0.02F);
/*     */     
/*  16 */     this.quadSize *= this.random.nextFloat() * 0.6F + 0.5F;
/*     */     
/*  18 */     this.xd *= 0.019999999552965164D;
/*  19 */     this.yd *= 0.019999999552965164D;
/*  20 */     this.zd *= 0.019999999552965164D;
/*     */     
/*  22 */     this.lifetime = (int)(20.0D / (Math.random() * 0.8D + 0.2D));
/*     */   }
/*     */ 
/*     */   
/*     */   public ParticleRenderType getRenderType() {
/*  27 */     return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
/*     */   }
/*     */ 
/*     */   
/*     */   public void move(double $$0, double $$1, double $$2) {
/*  32 */     setBoundingBox(getBoundingBox().move($$0, $$1, $$2));
/*  33 */     setLocationFromBoundingbox();
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/*  38 */     this.xo = this.x;
/*  39 */     this.yo = this.y;
/*  40 */     this.zo = this.z;
/*     */     
/*  42 */     if (this.lifetime-- <= 0) {
/*  43 */       remove();
/*     */       
/*     */       return;
/*     */     } 
/*  47 */     move(this.xd, this.yd, this.zd);
/*  48 */     this.xd *= 0.99D;
/*  49 */     this.yd *= 0.99D;
/*  50 */     this.zd *= 0.99D;
/*     */   }
/*     */   
/*     */   public static class Provider implements ParticleProvider<SimpleParticleType> {
/*     */     private final SpriteSet sprite;
/*     */     
/*     */     public Provider(SpriteSet $$0) {
/*  57 */       this.sprite = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/*  62 */       SuspendedTownParticle $$8 = new SuspendedTownParticle($$1, $$2, $$3, $$4, $$5, $$6, $$7);
/*  63 */       $$8.pickSprite(this.sprite);
/*  64 */       return $$8;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class HappyVillagerProvider implements ParticleProvider<SimpleParticleType> {
/*     */     private final SpriteSet sprite;
/*     */     
/*     */     public HappyVillagerProvider(SpriteSet $$0) {
/*  72 */       this.sprite = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/*  77 */       SuspendedTownParticle $$8 = new SuspendedTownParticle($$1, $$2, $$3, $$4, $$5, $$6, $$7);
/*  78 */       $$8.pickSprite(this.sprite);
/*  79 */       $$8.setColor(1.0F, 1.0F, 1.0F);
/*  80 */       return $$8;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class ComposterFillProvider implements ParticleProvider<SimpleParticleType> {
/*     */     private final SpriteSet sprite;
/*     */     
/*     */     public ComposterFillProvider(SpriteSet $$0) {
/*  88 */       this.sprite = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/*  93 */       SuspendedTownParticle $$8 = new SuspendedTownParticle($$1, $$2, $$3, $$4, $$5, $$6, $$7);
/*  94 */       $$8.pickSprite(this.sprite);
/*  95 */       $$8.setColor(1.0F, 1.0F, 1.0F);
/*  96 */       $$8.setLifetime(3 + $$1.getRandom().nextInt(5));
/*  97 */       return $$8;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class DolphinSpeedProvider implements ParticleProvider<SimpleParticleType> {
/*     */     private final SpriteSet sprite;
/*     */     
/*     */     public DolphinSpeedProvider(SpriteSet $$0) {
/* 105 */       this.sprite = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 110 */       SuspendedTownParticle $$8 = new SuspendedTownParticle($$1, $$2, $$3, $$4, $$5, $$6, $$7);
/* 111 */       $$8.setColor(0.3F, 0.5F, 1.0F);
/* 112 */       $$8.pickSprite(this.sprite);
/* 113 */       $$8.setAlpha(1.0F - $$1.random.nextFloat() * 0.7F);
/* 114 */       $$8.setLifetime($$8.getLifetime() / 2);
/* 115 */       return $$8;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class EggCrackProvider implements ParticleProvider<SimpleParticleType> {
/*     */     private final SpriteSet sprite;
/*     */     
/*     */     public EggCrackProvider(SpriteSet $$0) {
/* 123 */       this.sprite = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 128 */       SuspendedTownParticle $$8 = new SuspendedTownParticle($$1, $$2, $$3, $$4, $$5, $$6, $$7);
/* 129 */       $$8.pickSprite(this.sprite);
/* 130 */       $$8.setColor(1.0F, 1.0F, 1.0F);
/* 131 */       return $$8;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\SuspendedTownParticle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */