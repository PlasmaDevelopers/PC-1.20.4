/*    */ package net.minecraft.world.entity.boss.enderdragon.phases;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.ParticleTypes;
/*    */ import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
/*    */ import net.minecraft.world.level.levelgen.Heightmap;
/*    */ import net.minecraft.world.level.levelgen.feature.EndPodiumFeature;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class DragonDeathPhase extends AbstractDragonPhaseInstance {
/*    */   @Nullable
/*    */   private Vec3 targetLocation;
/*    */   
/*    */   public DragonDeathPhase(EnderDragon $$0) {
/* 18 */     super($$0);
/*    */   }
/*    */   private int time;
/*    */   
/*    */   public void doClientTick() {
/* 23 */     if (this.time++ % 10 == 0) {
/* 24 */       float $$0 = (this.dragon.getRandom().nextFloat() - 0.5F) * 8.0F;
/* 25 */       float $$1 = (this.dragon.getRandom().nextFloat() - 0.5F) * 4.0F;
/* 26 */       float $$2 = (this.dragon.getRandom().nextFloat() - 0.5F) * 8.0F;
/* 27 */       this.dragon.level().addParticle((ParticleOptions)ParticleTypes.EXPLOSION_EMITTER, this.dragon.getX() + $$0, this.dragon.getY() + 2.0D + $$1, this.dragon.getZ() + $$2, 0.0D, 0.0D, 0.0D);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void doServerTick() {
/* 33 */     this.time++;
/*    */     
/* 35 */     if (this.targetLocation == null) {
/* 36 */       BlockPos $$0 = this.dragon.level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, EndPodiumFeature.getLocation(this.dragon.getFightOrigin()));
/* 37 */       this.targetLocation = Vec3.atBottomCenterOf((Vec3i)$$0);
/*    */     } 
/*    */     
/* 40 */     double $$1 = this.targetLocation.distanceToSqr(this.dragon.getX(), this.dragon.getY(), this.dragon.getZ());
/* 41 */     if ($$1 < 100.0D || $$1 > 22500.0D || this.dragon.horizontalCollision || this.dragon.verticalCollision) {
/* 42 */       this.dragon.setHealth(0.0F);
/*    */     } else {
/* 44 */       this.dragon.setHealth(1.0F);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void begin() {
/* 50 */     this.targetLocation = null;
/* 51 */     this.time = 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getFlySpeed() {
/* 56 */     return 3.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Vec3 getFlyTargetLocation() {
/* 62 */     return this.targetLocation;
/*    */   }
/*    */ 
/*    */   
/*    */   public EnderDragonPhase<DragonDeathPhase> getPhase() {
/* 67 */     return EnderDragonPhase.DYING;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\boss\enderdragon\phases\DragonDeathPhase.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */