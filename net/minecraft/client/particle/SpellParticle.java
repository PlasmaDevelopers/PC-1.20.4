/*     */ package net.minecraft.client.particle;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.multiplayer.ClientLevel;
/*     */ import net.minecraft.client.player.LocalPlayer;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.SimpleParticleType;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ 
/*     */ public class SpellParticle extends TextureSheetParticle {
/*  11 */   private static final RandomSource RANDOM = RandomSource.create();
/*     */   
/*     */   private final SpriteSet sprites;
/*     */   
/*     */   SpellParticle(ClientLevel $$0, double $$1, double $$2, double $$3, double $$4, double $$5, double $$6, SpriteSet $$7) {
/*  16 */     super($$0, $$1, $$2, $$3, 0.5D - RANDOM.nextDouble(), $$5, 0.5D - RANDOM.nextDouble());
/*  17 */     this.friction = 0.96F;
/*  18 */     this.gravity = -0.1F;
/*  19 */     this.speedUpWhenYMotionIsBlocked = true;
/*  20 */     this.sprites = $$7;
/*  21 */     this.yd *= 0.20000000298023224D;
/*  22 */     if ($$4 == 0.0D && $$6 == 0.0D) {
/*  23 */       this.xd *= 0.10000000149011612D;
/*  24 */       this.zd *= 0.10000000149011612D;
/*     */     } 
/*     */     
/*  27 */     this.quadSize *= 0.75F;
/*     */     
/*  29 */     this.lifetime = (int)(8.0D / (Math.random() * 0.8D + 0.2D));
/*     */     
/*  31 */     this.hasPhysics = false;
/*  32 */     setSpriteFromAge($$7);
/*     */     
/*  34 */     if (isCloseToScopingPlayer()) {
/*  35 */       setAlpha(0.0F);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public ParticleRenderType getRenderType() {
/*  41 */     return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/*  46 */     super.tick();
/*  47 */     setSpriteFromAge(this.sprites);
/*     */     
/*  49 */     if (isCloseToScopingPlayer()) {
/*  50 */       setAlpha(0.0F);
/*     */     } else {
/*  52 */       setAlpha(Mth.lerp(0.05F, this.alpha, 1.0F));
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isCloseToScopingPlayer() {
/*  57 */     Minecraft $$0 = Minecraft.getInstance();
/*  58 */     LocalPlayer $$1 = $$0.player;
/*  59 */     return ($$1 != null && $$1.getEyePosition().distanceToSqr(this.x, this.y, this.z) <= 9.0D && $$0.options.getCameraType().isFirstPerson() && $$1.isScoping());
/*     */   }
/*     */   
/*     */   public static class Provider implements ParticleProvider<SimpleParticleType> {
/*     */     private final SpriteSet sprite;
/*     */     
/*     */     public Provider(SpriteSet $$0) {
/*  66 */       this.sprite = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/*  71 */       return new SpellParticle($$1, $$2, $$3, $$4, $$5, $$6, $$7, this.sprite);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class MobProvider implements ParticleProvider<SimpleParticleType> {
/*     */     private final SpriteSet sprite;
/*     */     
/*     */     public MobProvider(SpriteSet $$0) {
/*  79 */       this.sprite = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/*  84 */       Particle $$8 = new SpellParticle($$1, $$2, $$3, $$4, $$5, $$6, $$7, this.sprite);
/*  85 */       $$8.setColor((float)$$5, (float)$$6, (float)$$7);
/*  86 */       return $$8;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class AmbientMobProvider implements ParticleProvider<SimpleParticleType> {
/*     */     private final SpriteSet sprite;
/*     */     
/*     */     public AmbientMobProvider(SpriteSet $$0) {
/*  94 */       this.sprite = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/*  99 */       Particle $$8 = new SpellParticle($$1, $$2, $$3, $$4, $$5, $$6, $$7, this.sprite);
/* 100 */       $$8.setAlpha(0.15F);
/* 101 */       $$8.setColor((float)$$5, (float)$$6, (float)$$7);
/* 102 */       return $$8;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class WitchProvider implements ParticleProvider<SimpleParticleType> {
/*     */     private final SpriteSet sprite;
/*     */     
/*     */     public WitchProvider(SpriteSet $$0) {
/* 110 */       this.sprite = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 115 */       SpellParticle $$8 = new SpellParticle($$1, $$2, $$3, $$4, $$5, $$6, $$7, this.sprite);
/* 116 */       float $$9 = $$1.random.nextFloat() * 0.5F + 0.35F;
/* 117 */       $$8.setColor(1.0F * $$9, 0.0F * $$9, 1.0F * $$9);
/* 118 */       return $$8;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class InstantProvider implements ParticleProvider<SimpleParticleType> {
/*     */     private final SpriteSet sprite;
/*     */     
/*     */     public InstantProvider(SpriteSet $$0) {
/* 126 */       this.sprite = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 131 */       return new SpellParticle($$1, $$2, $$3, $$4, $$5, $$6, $$7, this.sprite);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\SpellParticle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */