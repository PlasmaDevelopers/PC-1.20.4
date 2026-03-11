/*    */ package net.minecraft.world.entity;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public abstract class FlyingMob extends Mob {
/*    */   protected FlyingMob(EntityType<? extends FlyingMob> $$0, Level $$1) {
/* 10 */     super((EntityType)$$0, $$1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void checkFallDamage(double $$0, boolean $$1, BlockState $$2, BlockPos $$3) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void travel(Vec3 $$0) {
/* 21 */     if (isControlledByLocalInstance()) {
/* 22 */       if (isInWater()) {
/* 23 */         moveRelative(0.02F, $$0);
/* 24 */         move(MoverType.SELF, getDeltaMovement());
/*    */         
/* 26 */         setDeltaMovement(getDeltaMovement().scale(0.800000011920929D));
/* 27 */       } else if (isInLava()) {
/* 28 */         moveRelative(0.02F, $$0);
/* 29 */         move(MoverType.SELF, getDeltaMovement());
/* 30 */         setDeltaMovement(getDeltaMovement().scale(0.5D));
/*    */       } else {
/* 32 */         float $$1 = 0.91F;
/* 33 */         if (onGround()) {
/* 34 */           $$1 = level().getBlockState(getBlockPosBelowThatAffectsMyMovement()).getBlock().getFriction() * 0.91F;
/*    */         }
/*    */         
/* 37 */         float $$2 = 0.16277137F / $$1 * $$1 * $$1;
/*    */         
/* 39 */         $$1 = 0.91F;
/* 40 */         if (onGround()) {
/* 41 */           $$1 = level().getBlockState(getBlockPosBelowThatAffectsMyMovement()).getBlock().getFriction() * 0.91F;
/*    */         }
/*    */         
/* 44 */         moveRelative(onGround() ? (0.1F * $$2) : 0.02F, $$0);
/* 45 */         move(MoverType.SELF, getDeltaMovement());
/*    */         
/* 47 */         setDeltaMovement(getDeltaMovement().scale($$1));
/*    */       } 
/*    */     }
/*    */     
/* 51 */     calculateEntityAnimation(false);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onClimbable() {
/* 56 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\FlyingMob.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */