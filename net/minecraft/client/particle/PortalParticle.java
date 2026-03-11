/*     */ package net.minecraft.client.particle;
/*     */ 
/*     */ import net.minecraft.client.multiplayer.ClientLevel;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.SimpleParticleType;
/*     */ 
/*     */ public class PortalParticle
/*     */   extends TextureSheetParticle {
/*     */   private final double xStart;
/*     */   
/*     */   protected PortalParticle(ClientLevel $$0, double $$1, double $$2, double $$3, double $$4, double $$5, double $$6) {
/*  12 */     super($$0, $$1, $$2, $$3);
/*     */     
/*  14 */     this.xd = $$4;
/*  15 */     this.yd = $$5;
/*  16 */     this.zd = $$6;
/*  17 */     this.x = $$1;
/*  18 */     this.y = $$2;
/*  19 */     this.z = $$3;
/*  20 */     this.xStart = this.x;
/*  21 */     this.yStart = this.y;
/*  22 */     this.zStart = this.z;
/*     */     
/*  24 */     this.quadSize = 0.1F * (this.random.nextFloat() * 0.2F + 0.5F);
/*     */     
/*  26 */     float $$7 = this.random.nextFloat() * 0.6F + 0.4F;
/*  27 */     this.rCol = $$7 * 0.9F;
/*  28 */     this.gCol = $$7 * 0.3F;
/*  29 */     this.bCol = $$7;
/*     */     
/*  31 */     this.lifetime = (int)(Math.random() * 10.0D) + 40;
/*     */   }
/*     */   private final double yStart; private final double zStart;
/*     */   
/*     */   public ParticleRenderType getRenderType() {
/*  36 */     return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
/*     */   }
/*     */ 
/*     */   
/*     */   public void move(double $$0, double $$1, double $$2) {
/*  41 */     setBoundingBox(getBoundingBox().move($$0, $$1, $$2));
/*  42 */     setLocationFromBoundingbox();
/*     */   }
/*     */ 
/*     */   
/*     */   public float getQuadSize(float $$0) {
/*  47 */     float $$1 = (this.age + $$0) / this.lifetime;
/*  48 */     $$1 = 1.0F - $$1;
/*  49 */     $$1 *= $$1;
/*  50 */     $$1 = 1.0F - $$1;
/*  51 */     return this.quadSize * $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLightColor(float $$0) {
/*  56 */     int $$1 = super.getLightColor($$0);
/*     */     
/*  58 */     float $$2 = this.age / this.lifetime;
/*  59 */     $$2 *= $$2;
/*  60 */     $$2 *= $$2;
/*     */     
/*  62 */     int $$3 = $$1 & 0xFF;
/*  63 */     int $$4 = $$1 >> 16 & 0xFF;
/*  64 */     $$4 += (int)($$2 * 15.0F * 16.0F);
/*  65 */     if ($$4 > 240) {
/*  66 */       $$4 = 240;
/*     */     }
/*  68 */     return $$3 | $$4 << 16;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/*  73 */     this.xo = this.x;
/*  74 */     this.yo = this.y;
/*  75 */     this.zo = this.z;
/*     */     
/*  77 */     if (this.age++ >= this.lifetime) {
/*  78 */       remove();
/*     */       
/*     */       return;
/*     */     } 
/*  82 */     float $$0 = this.age / this.lifetime;
/*  83 */     float $$1 = $$0;
/*  84 */     $$0 = -$$0 + $$0 * $$0 * 2.0F;
/*  85 */     $$0 = 1.0F - $$0;
/*     */     
/*  87 */     this.x = this.xStart + this.xd * $$0;
/*  88 */     this.y = this.yStart + this.yd * $$0 + (1.0F - $$1);
/*  89 */     this.z = this.zStart + this.zd * $$0;
/*     */   }
/*     */   
/*     */   public static class Provider implements ParticleProvider<SimpleParticleType> {
/*     */     private final SpriteSet sprite;
/*     */     
/*     */     public Provider(SpriteSet $$0) {
/*  96 */       this.sprite = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 101 */       PortalParticle $$8 = new PortalParticle($$1, $$2, $$3, $$4, $$5, $$6, $$7);
/* 102 */       $$8.pickSprite(this.sprite);
/* 103 */       return $$8;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\PortalParticle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */