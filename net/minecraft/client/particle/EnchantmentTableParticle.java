/*     */ package net.minecraft.client.particle;
/*     */ 
/*     */ import net.minecraft.client.multiplayer.ClientLevel;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.SimpleParticleType;
/*     */ 
/*     */ public class EnchantmentTableParticle
/*     */   extends TextureSheetParticle {
/*     */   private final double xStart;
/*     */   
/*     */   EnchantmentTableParticle(ClientLevel $$0, double $$1, double $$2, double $$3, double $$4, double $$5, double $$6) {
/*  12 */     super($$0, $$1, $$2, $$3);
/*     */     
/*  14 */     this.xd = $$4;
/*  15 */     this.yd = $$5;
/*  16 */     this.zd = $$6;
/*  17 */     this.xStart = $$1;
/*  18 */     this.yStart = $$2;
/*  19 */     this.zStart = $$3;
/*  20 */     this.xo = $$1 + $$4;
/*  21 */     this.yo = $$2 + $$5;
/*  22 */     this.zo = $$3 + $$6;
/*  23 */     this.x = this.xo;
/*  24 */     this.y = this.yo;
/*  25 */     this.z = this.zo;
/*     */     
/*  27 */     this.quadSize = 0.1F * (this.random.nextFloat() * 0.5F + 0.2F);
/*     */     
/*  29 */     float $$7 = this.random.nextFloat() * 0.6F + 0.4F;
/*  30 */     this.rCol = 0.9F * $$7;
/*  31 */     this.gCol = 0.9F * $$7;
/*  32 */     this.bCol = $$7;
/*     */     
/*  34 */     this.hasPhysics = false;
/*     */     
/*  36 */     this.lifetime = (int)(Math.random() * 10.0D) + 30;
/*     */   }
/*     */   private final double yStart; private final double zStart;
/*     */   
/*     */   public ParticleRenderType getRenderType() {
/*  41 */     return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
/*     */   }
/*     */ 
/*     */   
/*     */   public void move(double $$0, double $$1, double $$2) {
/*  46 */     setBoundingBox(getBoundingBox().move($$0, $$1, $$2));
/*  47 */     setLocationFromBoundingbox();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLightColor(float $$0) {
/*  52 */     int $$1 = super.getLightColor($$0);
/*     */     
/*  54 */     float $$2 = this.age / this.lifetime;
/*  55 */     $$2 *= $$2;
/*  56 */     $$2 *= $$2;
/*     */     
/*  58 */     int $$3 = $$1 & 0xFF;
/*  59 */     int $$4 = $$1 >> 16 & 0xFF;
/*  60 */     $$4 += (int)($$2 * 15.0F * 16.0F);
/*  61 */     if ($$4 > 240) {
/*  62 */       $$4 = 240;
/*     */     }
/*  64 */     return $$3 | $$4 << 16;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/*  69 */     this.xo = this.x;
/*  70 */     this.yo = this.y;
/*  71 */     this.zo = this.z;
/*     */     
/*  73 */     if (this.age++ >= this.lifetime) {
/*  74 */       remove();
/*     */       
/*     */       return;
/*     */     } 
/*  78 */     float $$0 = this.age / this.lifetime;
/*  79 */     $$0 = 1.0F - $$0;
/*     */     
/*  81 */     float $$1 = 1.0F - $$0;
/*  82 */     $$1 *= $$1;
/*  83 */     $$1 *= $$1;
/*  84 */     this.x = this.xStart + this.xd * $$0;
/*  85 */     this.y = this.yStart + this.yd * $$0 - ($$1 * 1.2F);
/*  86 */     this.z = this.zStart + this.zd * $$0;
/*     */   }
/*     */   
/*     */   public static class Provider implements ParticleProvider<SimpleParticleType> {
/*     */     private final SpriteSet sprite;
/*     */     
/*     */     public Provider(SpriteSet $$0) {
/*  93 */       this.sprite = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/*  98 */       EnchantmentTableParticle $$8 = new EnchantmentTableParticle($$1, $$2, $$3, $$4, $$5, $$6, $$7);
/*  99 */       $$8.pickSprite(this.sprite);
/* 100 */       return $$8;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class NautilusProvider implements ParticleProvider<SimpleParticleType> {
/*     */     private final SpriteSet sprite;
/*     */     
/*     */     public NautilusProvider(SpriteSet $$0) {
/* 108 */       this.sprite = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 113 */       EnchantmentTableParticle $$8 = new EnchantmentTableParticle($$1, $$2, $$3, $$4, $$5, $$6, $$7);
/* 114 */       $$8.pickSprite(this.sprite);
/* 115 */       return $$8;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\EnchantmentTableParticle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */