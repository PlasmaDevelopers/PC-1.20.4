/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.SimpleParticleType;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ 
/*    */ public class PlayerCloudParticle
/*    */   extends TextureSheetParticle {
/*    */   PlayerCloudParticle(ClientLevel $$0, double $$1, double $$2, double $$3, double $$4, double $$5, double $$6, SpriteSet $$7) {
/* 12 */     super($$0, $$1, $$2, $$3, 0.0D, 0.0D, 0.0D);
/* 13 */     this.friction = 0.96F;
/* 14 */     this.sprites = $$7;
/*    */     
/* 16 */     float $$8 = 2.5F;
/* 17 */     this.xd *= 0.10000000149011612D;
/* 18 */     this.yd *= 0.10000000149011612D;
/* 19 */     this.zd *= 0.10000000149011612D;
/* 20 */     this.xd += $$4;
/* 21 */     this.yd += $$5;
/* 22 */     this.zd += $$6;
/*    */     
/* 24 */     float $$9 = 1.0F - (float)(Math.random() * 0.30000001192092896D);
/* 25 */     this.rCol = $$9;
/* 26 */     this.gCol = $$9;
/* 27 */     this.bCol = $$9;
/* 28 */     this.quadSize *= 1.875F;
/*    */     
/* 30 */     int $$10 = (int)(8.0D / (Math.random() * 0.8D + 0.3D));
/* 31 */     this.lifetime = (int)Math.max($$10 * 2.5F, 1.0F);
/*    */     
/* 33 */     this.hasPhysics = false;
/* 34 */     setSpriteFromAge($$7);
/*    */   }
/*    */   private final SpriteSet sprites;
/*    */   
/*    */   public ParticleRenderType getRenderType() {
/* 39 */     return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getQuadSize(float $$0) {
/* 44 */     return this.quadSize * Mth.clamp((this.age + $$0) / this.lifetime * 32.0F, 0.0F, 1.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 49 */     super.tick();
/* 50 */     if (!this.removed) {
/* 51 */       setSpriteFromAge(this.sprites);
/* 52 */       Player $$0 = this.level.getNearestPlayer(this.x, this.y, this.z, 2.0D, false);
/* 53 */       if ($$0 != null) {
/* 54 */         double $$1 = $$0.getY();
/* 55 */         if (this.y > $$1) {
/* 56 */           this.y += ($$1 - this.y) * 0.2D;
/* 57 */           this.yd += (($$0.getDeltaMovement()).y - this.yd) * 0.2D;
/* 58 */           setPos(this.x, this.y, this.z);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public static class Provider implements ParticleProvider<SimpleParticleType> {
/*    */     private final SpriteSet sprites;
/*    */     
/*    */     public Provider(SpriteSet $$0) {
/* 68 */       this.sprites = $$0;
/*    */     }
/*    */ 
/*    */     
/*    */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 73 */       return new PlayerCloudParticle($$1, $$2, $$3, $$4, $$5, $$6, $$7, this.sprites);
/*    */     }
/*    */   }
/*    */   
/*    */   public static class SneezeProvider implements ParticleProvider<SimpleParticleType> {
/*    */     private final SpriteSet sprites;
/*    */     
/*    */     public SneezeProvider(SpriteSet $$0) {
/* 81 */       this.sprites = $$0;
/*    */     }
/*    */ 
/*    */     
/*    */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 86 */       Particle $$8 = new PlayerCloudParticle($$1, $$2, $$3, $$4, $$5, $$6, $$7, this.sprites);
/* 87 */       $$8.setColor(200.0F, 50.0F, 120.0F);
/* 88 */       $$8.setAlpha(0.4F);
/* 89 */       return $$8;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\PlayerCloudParticle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */