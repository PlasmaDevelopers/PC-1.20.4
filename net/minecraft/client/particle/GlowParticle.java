/*     */ package net.minecraft.client.particle;
/*     */ 
/*     */ import net.minecraft.client.multiplayer.ClientLevel;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.SimpleParticleType;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ 
/*     */ public class GlowParticle extends TextureSheetParticle {
/*  10 */   static final RandomSource RANDOM = RandomSource.create();
/*     */   
/*     */   private final SpriteSet sprites;
/*     */   
/*     */   GlowParticle(ClientLevel $$0, double $$1, double $$2, double $$3, double $$4, double $$5, double $$6, SpriteSet $$7) {
/*  15 */     super($$0, $$1, $$2, $$3, $$4, $$5, $$6);
/*  16 */     this.friction = 0.96F;
/*  17 */     this.speedUpWhenYMotionIsBlocked = true;
/*  18 */     this.sprites = $$7;
/*     */     
/*  20 */     this.quadSize *= 0.75F;
/*     */     
/*  22 */     this.hasPhysics = false;
/*  23 */     setSpriteFromAge($$7);
/*     */   }
/*     */ 
/*     */   
/*     */   public ParticleRenderType getRenderType() {
/*  28 */     return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLightColor(float $$0) {
/*  33 */     float $$1 = (this.age + $$0) / this.lifetime;
/*  34 */     $$1 = Mth.clamp($$1, 0.0F, 1.0F);
/*  35 */     int $$2 = super.getLightColor($$0);
/*     */     
/*  37 */     int $$3 = $$2 & 0xFF;
/*  38 */     int $$4 = $$2 >> 16 & 0xFF;
/*  39 */     $$3 += (int)($$1 * 15.0F * 16.0F);
/*  40 */     if ($$3 > 240) {
/*  41 */       $$3 = 240;
/*     */     }
/*  43 */     return $$3 | $$4 << 16;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/*  48 */     super.tick();
/*  49 */     setSpriteFromAge(this.sprites);
/*     */   }
/*     */   
/*     */   public static class GlowSquidProvider implements ParticleProvider<SimpleParticleType> {
/*     */     private final SpriteSet sprite;
/*     */     
/*     */     public GlowSquidProvider(SpriteSet $$0) {
/*  56 */       this.sprite = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/*  61 */       GlowParticle $$8 = new GlowParticle($$1, $$2, $$3, $$4, 0.5D - GlowParticle.RANDOM.nextDouble(), $$6, 0.5D - GlowParticle.RANDOM.nextDouble(), this.sprite);
/*  62 */       if ($$1.random.nextBoolean()) {
/*  63 */         $$8.setColor(0.6F, 1.0F, 0.8F);
/*     */       } else {
/*  65 */         $$8.setColor(0.08F, 0.4F, 0.4F);
/*     */       } 
/*  67 */       $$8.yd *= 0.20000000298023224D;
/*  68 */       if ($$5 == 0.0D && $$7 == 0.0D) {
/*  69 */         $$8.xd *= 0.10000000149011612D;
/*  70 */         $$8.zd *= 0.10000000149011612D;
/*     */       } 
/*  72 */       $$8.setLifetime((int)(8.0D / ($$1.random.nextDouble() * 0.8D + 0.2D)));
/*  73 */       return $$8;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class WaxOnProvider implements ParticleProvider<SimpleParticleType> {
/*  78 */     private final double SPEED_FACTOR = 0.01D;
/*     */     private final SpriteSet sprite;
/*     */     
/*     */     public WaxOnProvider(SpriteSet $$0) {
/*  82 */       this.sprite = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/*  87 */       GlowParticle $$8 = new GlowParticle($$1, $$2, $$3, $$4, 0.0D, 0.0D, 0.0D, this.sprite);
/*  88 */       $$8.setColor(0.91F, 0.55F, 0.08F);
/*     */       
/*  90 */       $$8.setParticleSpeed($$5 * 0.01D / 2.0D, $$6 * 0.01D, $$7 * 0.01D / 2.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  96 */       int $$9 = 10;
/*  97 */       int $$10 = 40;
/*  98 */       $$8.setLifetime($$1.random.nextInt(30) + 10);
/*  99 */       return $$8;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class WaxOffProvider implements ParticleProvider<SimpleParticleType> {
/* 104 */     private final double SPEED_FACTOR = 0.01D;
/*     */     private final SpriteSet sprite;
/*     */     
/*     */     public WaxOffProvider(SpriteSet $$0) {
/* 108 */       this.sprite = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 113 */       GlowParticle $$8 = new GlowParticle($$1, $$2, $$3, $$4, 0.0D, 0.0D, 0.0D, this.sprite);
/* 114 */       $$8.setColor(1.0F, 0.9F, 1.0F);
/*     */       
/* 116 */       $$8.setParticleSpeed($$5 * 0.01D / 2.0D, $$6 * 0.01D, $$7 * 0.01D / 2.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 122 */       int $$9 = 10;
/* 123 */       int $$10 = 40;
/* 124 */       $$8.setLifetime($$1.random.nextInt(30) + 10);
/* 125 */       return $$8;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class ElectricSparkProvider implements ParticleProvider<SimpleParticleType> {
/* 130 */     private final double SPEED_FACTOR = 0.25D;
/*     */     private final SpriteSet sprite;
/*     */     
/*     */     public ElectricSparkProvider(SpriteSet $$0) {
/* 134 */       this.sprite = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 139 */       GlowParticle $$8 = new GlowParticle($$1, $$2, $$3, $$4, 0.0D, 0.0D, 0.0D, this.sprite);
/* 140 */       $$8.setColor(1.0F, 0.9F, 1.0F);
/*     */       
/* 142 */       $$8.setParticleSpeed($$5 * 0.25D, $$6 * 0.25D, $$7 * 0.25D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 148 */       int $$9 = 2;
/* 149 */       int $$10 = 4;
/* 150 */       $$8.setLifetime($$1.random.nextInt(2) + 2);
/* 151 */       return $$8;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class ScrapeProvider implements ParticleProvider<SimpleParticleType> {
/* 156 */     private final double SPEED_FACTOR = 0.01D;
/*     */     private final SpriteSet sprite;
/*     */     
/*     */     public ScrapeProvider(SpriteSet $$0) {
/* 160 */       this.sprite = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 165 */       GlowParticle $$8 = new GlowParticle($$1, $$2, $$3, $$4, 0.0D, 0.0D, 0.0D, this.sprite);
/* 166 */       if ($$1.random.nextBoolean()) {
/* 167 */         $$8.setColor(0.29F, 0.58F, 0.51F);
/*     */       } else {
/* 169 */         $$8.setColor(0.43F, 0.77F, 0.62F);
/*     */       } 
/*     */       
/* 172 */       $$8.setParticleSpeed($$5 * 0.01D, $$6 * 0.01D, $$7 * 0.01D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 178 */       int $$9 = 10;
/* 179 */       int $$10 = 40;
/* 180 */       $$8.setLifetime($$1.random.nextInt(30) + 10);
/* 181 */       return $$8;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\GlowParticle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */