/*     */ package net.minecraft.client.particle;
/*     */ 
/*     */ import net.minecraft.client.multiplayer.ClientLevel;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.SimpleParticleType;
/*     */ import net.minecraft.util.Mth;
/*     */ 
/*     */ 
/*     */ public class DragonBreathParticle
/*     */   extends TextureSheetParticle
/*     */ {
/*     */   private static final int COLOR_MIN = 11993298;
/*     */   private static final int COLOR_MAX = 14614777;
/*     */   private static final float COLOR_MIN_RED = 0.7176471F;
/*     */   private static final float COLOR_MIN_GREEN = 0.0F;
/*     */   private static final float COLOR_MIN_BLUE = 0.8235294F;
/*     */   private static final float COLOR_MAX_RED = 0.8745098F;
/*     */   private static final float COLOR_MAX_GREEN = 0.0F;
/*     */   private static final float COLOR_MAX_BLUE = 0.9764706F;
/*     */   private boolean hasHitGround;
/*     */   private final SpriteSet sprites;
/*     */   
/*     */   DragonBreathParticle(ClientLevel $$0, double $$1, double $$2, double $$3, double $$4, double $$5, double $$6, SpriteSet $$7) {
/*  24 */     super($$0, $$1, $$2, $$3);
/*  25 */     this.friction = 0.96F;
/*  26 */     this.xd = $$4;
/*  27 */     this.yd = $$5;
/*  28 */     this.zd = $$6;
/*     */     
/*  30 */     this.rCol = Mth.nextFloat(this.random, 0.7176471F, 0.8745098F);
/*  31 */     this.gCol = Mth.nextFloat(this.random, 0.0F, 0.0F);
/*  32 */     this.bCol = Mth.nextFloat(this.random, 0.8235294F, 0.9764706F);
/*     */     
/*  34 */     this.quadSize *= 0.75F;
/*     */     
/*  36 */     this.lifetime = (int)(20.0D / (this.random.nextFloat() * 0.8D + 0.2D));
/*  37 */     this.hasHitGround = false;
/*  38 */     this.hasPhysics = false;
/*     */     
/*  40 */     this.sprites = $$7;
/*  41 */     setSpriteFromAge($$7);
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/*  46 */     this.xo = this.x;
/*  47 */     this.yo = this.y;
/*  48 */     this.zo = this.z;
/*     */     
/*  50 */     if (this.age++ >= this.lifetime) {
/*  51 */       remove();
/*     */       
/*     */       return;
/*     */     } 
/*  55 */     setSpriteFromAge(this.sprites);
/*     */     
/*  57 */     if (this.onGround) {
/*  58 */       this.yd = 0.0D;
/*  59 */       this.hasHitGround = true;
/*     */     } 
/*     */     
/*  62 */     if (this.hasHitGround) {
/*  63 */       this.yd += 0.002D;
/*     */     }
/*     */     
/*  66 */     move(this.xd, this.yd, this.zd);
/*     */     
/*  68 */     if (this.y == this.yo) {
/*  69 */       this.xd *= 1.1D;
/*  70 */       this.zd *= 1.1D;
/*     */     } 
/*     */     
/*  73 */     this.xd *= this.friction;
/*  74 */     this.zd *= this.friction;
/*     */     
/*  76 */     if (this.hasHitGround) {
/*  77 */       this.yd *= this.friction;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public ParticleRenderType getRenderType() {
/*  83 */     return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getQuadSize(float $$0) {
/*  88 */     return this.quadSize * Mth.clamp((this.age + $$0) / this.lifetime * 32.0F, 0.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public static class Provider implements ParticleProvider<SimpleParticleType> {
/*     */     private final SpriteSet sprites;
/*     */     
/*     */     public Provider(SpriteSet $$0) {
/*  95 */       this.sprites = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 100 */       return new DragonBreathParticle($$1, $$2, $$3, $$4, $$5, $$6, $$7, this.sprites);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\DragonBreathParticle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */