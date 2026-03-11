/*     */ package net.minecraft.client.particle;
/*     */ 
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import net.minecraft.client.Camera;
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
/*     */ 
/*     */ 
/*     */ class SparkParticle
/*     */   extends SimpleAnimatedParticle
/*     */ {
/*     */   private boolean trail;
/*     */   private boolean flicker;
/*     */   private final ParticleEngine engine;
/*     */   private float fadeR;
/*     */   private float fadeG;
/*     */   private float fadeB;
/*     */   private boolean hasFade;
/*     */   
/*     */   SparkParticle(ClientLevel $$0, double $$1, double $$2, double $$3, double $$4, double $$5, double $$6, ParticleEngine $$7, SpriteSet $$8) {
/* 273 */     super($$0, $$1, $$2, $$3, $$8, 0.1F);
/* 274 */     this.xd = $$4;
/* 275 */     this.yd = $$5;
/* 276 */     this.zd = $$6;
/* 277 */     this.engine = $$7;
/*     */     
/* 279 */     this.quadSize *= 0.75F;
/*     */     
/* 281 */     this.lifetime = 48 + this.random.nextInt(12);
/* 282 */     setSpriteFromAge($$8);
/*     */   }
/*     */   
/*     */   public void setTrail(boolean $$0) {
/* 286 */     this.trail = $$0;
/*     */   }
/*     */   
/*     */   public void setFlicker(boolean $$0) {
/* 290 */     this.flicker = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(VertexConsumer $$0, Camera $$1, float $$2) {
/* 295 */     if (!this.flicker || this.age < this.lifetime / 3 || (this.age + this.lifetime) / 3 % 2 == 0) {
/* 296 */       super.render($$0, $$1, $$2);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 302 */     super.tick();
/*     */     
/* 304 */     if (this.trail && this.age < this.lifetime / 2 && (this.age + this.lifetime) % 2 == 0) {
/* 305 */       SparkParticle $$0 = new SparkParticle(this.level, this.x, this.y, this.z, 0.0D, 0.0D, 0.0D, this.engine, this.sprites);
/* 306 */       $$0.setAlpha(0.99F);
/* 307 */       $$0.setColor(this.rCol, this.gCol, this.bCol);
/* 308 */       $$0.age = $$0.lifetime / 2;
/* 309 */       if (this.hasFade) {
/* 310 */         $$0.hasFade = true;
/* 311 */         $$0.fadeR = this.fadeR;
/* 312 */         $$0.fadeG = this.fadeG;
/* 313 */         $$0.fadeB = this.fadeB;
/*     */       } 
/* 315 */       $$0.flicker = this.flicker;
/* 316 */       this.engine.add($$0);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\FireworkParticles$SparkParticle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */