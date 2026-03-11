/*    */ package net.minecraft.world.entity.boss.enderdragon.phases;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.ParticleTypes;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
/*    */ import net.minecraft.world.level.levelgen.Heightmap;
/*    */ import net.minecraft.world.level.levelgen.feature.EndPodiumFeature;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class DragonLandingPhase extends AbstractDragonPhaseInstance {
/*    */   @Nullable
/*    */   private Vec3 targetLocation;
/*    */   
/*    */   public DragonLandingPhase(EnderDragon $$0) {
/* 18 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void doClientTick() {
/* 23 */     Vec3 $$0 = this.dragon.getHeadLookVector(1.0F).normalize();
/* 24 */     $$0.yRot(-0.7853982F);
/*    */     
/* 26 */     double $$1 = this.dragon.head.getX();
/* 27 */     double $$2 = this.dragon.head.getY(0.5D);
/* 28 */     double $$3 = this.dragon.head.getZ();
/* 29 */     for (int $$4 = 0; $$4 < 8; $$4++) {
/* 30 */       RandomSource $$5 = this.dragon.getRandom();
/* 31 */       double $$6 = $$1 + $$5.nextGaussian() / 2.0D;
/* 32 */       double $$7 = $$2 + $$5.nextGaussian() / 2.0D;
/* 33 */       double $$8 = $$3 + $$5.nextGaussian() / 2.0D;
/* 34 */       Vec3 $$9 = this.dragon.getDeltaMovement();
/* 35 */       this.dragon.level().addParticle((ParticleOptions)ParticleTypes.DRAGON_BREATH, $$6, $$7, $$8, -$$0.x * 0.07999999821186066D + $$9.x, -$$0.y * 0.30000001192092896D + $$9.y, -$$0.z * 0.07999999821186066D + $$9.z);
/* 36 */       $$0.yRot(0.19634955F);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void doServerTick() {
/* 42 */     if (this.targetLocation == null) {
/* 43 */       this.targetLocation = Vec3.atBottomCenterOf((Vec3i)this.dragon.level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EndPodiumFeature.getLocation(this.dragon.getFightOrigin())));
/*    */     }
/*    */     
/* 46 */     if (this.targetLocation.distanceToSqr(this.dragon.getX(), this.dragon.getY(), this.dragon.getZ()) < 1.0D) {
/* 47 */       ((DragonSittingFlamingPhase)this.dragon.getPhaseManager().<DragonSittingFlamingPhase>getPhase(EnderDragonPhase.SITTING_FLAMING)).resetFlameCount();
/* 48 */       this.dragon.getPhaseManager().setPhase(EnderDragonPhase.SITTING_SCANNING);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public float getFlySpeed() {
/* 54 */     return 1.5F;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getTurnSpeed() {
/* 59 */     float $$0 = (float)this.dragon.getDeltaMovement().horizontalDistance() + 1.0F;
/* 60 */     float $$1 = Math.min($$0, 40.0F);
/*    */     
/* 62 */     return $$1 / $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void begin() {
/* 67 */     this.targetLocation = null;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Vec3 getFlyTargetLocation() {
/* 73 */     return this.targetLocation;
/*    */   }
/*    */ 
/*    */   
/*    */   public EnderDragonPhase<DragonLandingPhase> getPhase() {
/* 78 */     return EnderDragonPhase.LANDING;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\boss\enderdragon\phases\DragonLandingPhase.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */