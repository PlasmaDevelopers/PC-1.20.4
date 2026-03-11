/*     */ package net.minecraft.client.particle;
/*     */ 
/*     */ import net.minecraft.client.multiplayer.ClientLevel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CherryParticle
/*     */   extends TextureSheetParticle
/*     */ {
/*     */   private static final float ACCELERATION_SCALE = 0.0025F;
/*     */   private static final int INITIAL_LIFETIME = 300;
/*     */   private static final int CURVE_ENDPOINT_TIME = 300;
/*     */   private static final float FALL_ACC = 0.25F;
/*     */   private static final float WIND_BIG = 2.0F;
/*     */   private float rotSpeed;
/*     */   private final float particleRandom;
/*     */   private final float spinAcceleration;
/*     */   
/*     */   protected CherryParticle(ClientLevel $$0, double $$1, double $$2, double $$3, SpriteSet $$4) {
/*  28 */     super($$0, $$1, $$2, $$3);
/*  29 */     setSprite($$4.get(this.random.nextInt(12), 12));
/*     */ 
/*     */     
/*  32 */     this.rotSpeed = (float)Math.toRadians(this.random.nextBoolean() ? -30.0D : 30.0D);
/*  33 */     this.particleRandom = this.random.nextFloat();
/*  34 */     this.spinAcceleration = (float)Math.toRadians(this.random.nextBoolean() ? -5.0D : 5.0D);
/*     */ 
/*     */     
/*  37 */     this.lifetime = 300;
/*     */     
/*  39 */     this.gravity = 7.5E-4F;
/*     */ 
/*     */     
/*  42 */     float $$5 = this.random.nextBoolean() ? 0.05F : 0.075F;
/*  43 */     this.quadSize = $$5;
/*  44 */     setSize($$5, $$5);
/*     */ 
/*     */     
/*  47 */     this.friction = 1.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public ParticleRenderType getRenderType() {
/*  52 */     return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void tick() {
/*  58 */     this.xo = this.x;
/*  59 */     this.yo = this.y;
/*  60 */     this.zo = this.z;
/*     */     
/*  62 */     if (this.lifetime-- <= 0) {
/*  63 */       remove();
/*     */     }
/*  65 */     if (this.removed) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  70 */     float $$0 = (300 - this.lifetime);
/*  71 */     float $$1 = Math.min($$0 / 300.0F, 1.0F);
/*     */     
/*  73 */     double $$2 = Math.cos(Math.toRadians((this.particleRandom * 60.0F))) * 2.0D * Math.pow($$1, 1.25D);
/*  74 */     double $$3 = Math.sin(Math.toRadians((this.particleRandom * 60.0F))) * 2.0D * Math.pow($$1, 1.25D);
/*     */     
/*  76 */     this.xd += $$2 * 0.0024999999441206455D;
/*  77 */     this.zd += $$3 * 0.0024999999441206455D;
/*     */ 
/*     */     
/*  80 */     this.yd -= this.gravity;
/*     */     
/*  82 */     this.rotSpeed += this.spinAcceleration / 20.0F;
/*     */     
/*  84 */     this.oRoll = this.roll;
/*  85 */     this.roll += this.rotSpeed / 20.0F;
/*     */ 
/*     */     
/*  88 */     move(this.xd, this.yd, this.zd);
/*     */ 
/*     */     
/*  91 */     if (this.onGround || (this.lifetime < 299 && (this.xd == 0.0D || this.zd == 0.0D))) {
/*  92 */       remove();
/*     */     }
/*  94 */     if (this.removed) {
/*     */       return;
/*     */     }
/*     */     
/*  98 */     this.xd *= this.friction;
/*  99 */     this.yd *= this.friction;
/* 100 */     this.zd *= this.friction;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\CherryParticle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */