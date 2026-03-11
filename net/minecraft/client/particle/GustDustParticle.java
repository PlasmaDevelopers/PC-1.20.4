/*    */ package net.minecraft.client.particle;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import net.minecraft.client.Camera;
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.SimpleParticleType;
/*    */ import net.minecraft.util.Mth;
/*    */ import org.joml.Vector3f;
/*    */ import org.joml.Vector3fc;
/*    */ 
/*    */ public class GustDustParticle extends TextureSheetParticle {
/* 12 */   private final Vector3f fromColor = new Vector3f(0.5F, 0.5F, 0.5F);
/* 13 */   private final Vector3f toColor = new Vector3f(1.0F, 1.0F, 1.0F);
/*    */   
/*    */   GustDustParticle(ClientLevel $$0, double $$1, double $$2, double $$3, double $$4, double $$5, double $$6) {
/* 16 */     super($$0, $$1, $$2, $$3);
/* 17 */     this.hasPhysics = false;
/*    */     
/* 19 */     this.xd = $$4 + Mth.randomBetween(this.random, -0.4F, 0.4F);
/* 20 */     this.zd = $$6 + Mth.randomBetween(this.random, -0.4F, 0.4F);
/*    */     
/* 22 */     double $$7 = Math.random() * 2.0D;
/* 23 */     double $$8 = Math.sqrt(this.xd * this.xd + this.yd * this.yd + this.zd * this.zd);
/*    */     
/* 25 */     this.xd = this.xd / $$8 * $$7 * 0.4000000059604645D;
/* 26 */     this.zd = this.zd / $$8 * $$7 * 0.4000000059604645D;
/*    */     
/* 28 */     this.quadSize *= 2.5F;
/*    */     
/* 30 */     this.xd *= 0.07999999821186066D;
/* 31 */     this.zd *= 0.07999999821186066D;
/*    */     
/* 33 */     this.lifetime = 18 + this.random.nextInt(4);
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(VertexConsumer $$0, Camera $$1, float $$2) {
/* 38 */     lerpColors($$2);
/* 39 */     super.render($$0, $$1, $$2);
/*    */   }
/*    */   
/*    */   private void lerpColors(float $$0) {
/* 43 */     float $$1 = (this.age + $$0) / (this.lifetime + 1);
/* 44 */     Vector3f $$2 = (new Vector3f((Vector3fc)this.fromColor)).lerp((Vector3fc)this.toColor, $$1);
/* 45 */     this.rCol = $$2.x();
/* 46 */     this.gCol = $$2.y();
/* 47 */     this.bCol = $$2.z();
/*    */   }
/*    */ 
/*    */   
/*    */   public ParticleRenderType getRenderType() {
/* 52 */     return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 57 */     if (this.age++ >= this.lifetime) {
/* 58 */       remove();
/*    */       
/*    */       return;
/*    */     } 
/* 62 */     this.xo = this.x;
/* 63 */     this.zo = this.z;
/*    */     
/* 65 */     move(this.xd, 0.0D, this.zd);
/*    */     
/* 67 */     this.xd *= 0.99D;
/* 68 */     this.zd *= 0.99D;
/*    */   }
/*    */   
/*    */   public static class GustDustParticleProvider implements ParticleProvider<SimpleParticleType> {
/*    */     private final SpriteSet sprite;
/*    */     
/*    */     public GustDustParticleProvider(SpriteSet $$0) {
/* 75 */       this.sprite = $$0;
/*    */     }
/*    */ 
/*    */     
/*    */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 80 */       GustDustParticle $$8 = new GustDustParticle($$1, $$2, $$3, $$4, $$5, $$6, $$7);
/* 81 */       $$8.pickSprite(this.sprite);
/* 82 */       return $$8;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\GustDustParticle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */