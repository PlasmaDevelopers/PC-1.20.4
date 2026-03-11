/*    */ package net.minecraft.client.particle;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import net.minecraft.client.Camera;
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.core.particles.DustColorTransitionOptions;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import org.joml.Vector3f;
/*    */ import org.joml.Vector3fc;
/*    */ 
/*    */ public class DustColorTransitionParticle extends DustParticleBase<DustColorTransitionOptions> {
/*    */   private final Vector3f fromColor;
/*    */   
/*    */   protected DustColorTransitionParticle(ClientLevel $$0, double $$1, double $$2, double $$3, double $$4, double $$5, double $$6, DustColorTransitionOptions $$7, SpriteSet $$8) {
/* 14 */     super($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8);
/*    */     
/* 16 */     float $$9 = this.random.nextFloat() * 0.4F + 0.6F;
/*    */     
/* 18 */     this.fromColor = randomizeColor($$7.getFromColor(), $$9);
/* 19 */     this.toColor = randomizeColor($$7.getToColor(), $$9);
/*    */   }
/*    */   private final Vector3f toColor;
/*    */   private Vector3f randomizeColor(Vector3f $$0, float $$1) {
/* 23 */     return new Vector3f(randomizeColor($$0.x(), $$1), randomizeColor($$0.y(), $$1), randomizeColor($$0.z(), $$1));
/*    */   }
/*    */   
/*    */   private void lerpColors(float $$0) {
/* 27 */     float $$1 = (this.age + $$0) / (this.lifetime + 1.0F);
/* 28 */     Vector3f $$2 = (new Vector3f((Vector3fc)this.fromColor)).lerp((Vector3fc)this.toColor, $$1);
/* 29 */     this.rCol = $$2.x();
/* 30 */     this.gCol = $$2.y();
/* 31 */     this.bCol = $$2.z();
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(VertexConsumer $$0, Camera $$1, float $$2) {
/* 36 */     lerpColors($$2);
/* 37 */     super.render($$0, $$1, $$2);
/*    */   }
/*    */   
/*    */   public static class Provider implements ParticleProvider<DustColorTransitionOptions> {
/*    */     private final SpriteSet sprites;
/*    */     
/*    */     public Provider(SpriteSet $$0) {
/* 44 */       this.sprites = $$0;
/*    */     }
/*    */ 
/*    */     
/*    */     public Particle createParticle(DustColorTransitionOptions $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 49 */       return new DustColorTransitionParticle($$1, $$2, $$3, $$4, $$5, $$6, $$7, $$0, this.sprites);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\DustColorTransitionParticle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */