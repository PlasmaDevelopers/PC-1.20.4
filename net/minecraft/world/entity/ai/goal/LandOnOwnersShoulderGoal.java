/*    */ package net.minecraft.world.entity.ai.goal;
/*    */ 
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.world.entity.animal.ShoulderRidingEntity;
/*    */ 
/*    */ public class LandOnOwnersShoulderGoal
/*    */   extends Goal {
/*    */   private final ShoulderRidingEntity entity;
/*    */   private ServerPlayer owner;
/*    */   private boolean isSittingOnShoulder;
/*    */   
/*    */   public LandOnOwnersShoulderGoal(ShoulderRidingEntity $$0) {
/* 13 */     this.entity = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canUse() {
/* 18 */     ServerPlayer $$0 = (ServerPlayer)this.entity.getOwner();
/* 19 */     boolean $$1 = ($$0 != null && !$$0.isSpectator() && !($$0.getAbilities()).flying && !$$0.isInWater() && !$$0.isInPowderSnow);
/* 20 */     return (!this.entity.isOrderedToSit() && $$1 && this.entity.canSitOnShoulder());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isInterruptable() {
/* 25 */     return !this.isSittingOnShoulder;
/*    */   }
/*    */ 
/*    */   
/*    */   public void start() {
/* 30 */     this.owner = (ServerPlayer)this.entity.getOwner();
/* 31 */     this.isSittingOnShoulder = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 36 */     if (this.isSittingOnShoulder || this.entity.isInSittingPose() || this.entity.isLeashed()) {
/*    */       return;
/*    */     }
/*    */     
/* 40 */     if (this.entity.getBoundingBox().intersects(this.owner.getBoundingBox()))
/* 41 */       this.isSittingOnShoulder = this.entity.setEntityOnShoulder(this.owner); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\LandOnOwnersShoulderGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */