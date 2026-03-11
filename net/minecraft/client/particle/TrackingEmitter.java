/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class TrackingEmitter extends NoRenderParticle {
/*    */   private final Entity entity;
/*    */   private int life;
/*    */   private final int lifeTime;
/*    */   private final ParticleOptions particleType;
/*    */   
/*    */   public TrackingEmitter(ClientLevel $$0, Entity $$1, ParticleOptions $$2) {
/* 15 */     this($$0, $$1, $$2, 3);
/*    */   }
/*    */   
/*    */   public TrackingEmitter(ClientLevel $$0, Entity $$1, ParticleOptions $$2, int $$3) {
/* 19 */     this($$0, $$1, $$2, $$3, $$1.getDeltaMovement());
/*    */   }
/*    */   
/*    */   private TrackingEmitter(ClientLevel $$0, Entity $$1, ParticleOptions $$2, int $$3, Vec3 $$4) {
/* 23 */     super($$0, $$1.getX(), $$1.getY(0.5D), $$1.getZ(), $$4.x, $$4.y, $$4.z);
/* 24 */     this.entity = $$1;
/* 25 */     this.lifeTime = $$3;
/* 26 */     this.particleType = $$2;
/* 27 */     tick();
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 32 */     for (int $$0 = 0; $$0 < 16; $$0++) {
/* 33 */       double $$1 = (this.random.nextFloat() * 2.0F - 1.0F);
/* 34 */       double $$2 = (this.random.nextFloat() * 2.0F - 1.0F);
/* 35 */       double $$3 = (this.random.nextFloat() * 2.0F - 1.0F);
/* 36 */       if ($$1 * $$1 + $$2 * $$2 + $$3 * $$3 <= 1.0D) {
/*    */ 
/*    */         
/* 39 */         double $$4 = this.entity.getX($$1 / 4.0D);
/* 40 */         double $$5 = this.entity.getY(0.5D + $$2 / 4.0D);
/* 41 */         double $$6 = this.entity.getZ($$3 / 4.0D);
/* 42 */         this.level.addParticle(this.particleType, false, $$4, $$5, $$6, $$1, $$2 + 0.2D, $$3);
/*    */       } 
/* 44 */     }  this.life++;
/* 45 */     if (this.life >= this.lifeTime)
/* 46 */       remove(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\TrackingEmitter.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */