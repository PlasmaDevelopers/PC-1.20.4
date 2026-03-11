/*    */ package net.minecraft.world.entity.ai.goal.target;
/*    */ 
/*    */ import java.util.EnumSet;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.Mob;
/*    */ import net.minecraft.world.entity.TamableAnimal;
/*    */ import net.minecraft.world.entity.ai.goal.Goal;
/*    */ import net.minecraft.world.entity.ai.targeting.TargetingConditions;
/*    */ 
/*    */ public class OwnerHurtByTargetGoal extends TargetGoal {
/*    */   private final TamableAnimal tameAnimal;
/*    */   private LivingEntity ownerLastHurtBy;
/*    */   private int timestamp;
/*    */   
/*    */   public OwnerHurtByTargetGoal(TamableAnimal $$0) {
/* 16 */     super((Mob)$$0, false);
/* 17 */     this.tameAnimal = $$0;
/* 18 */     setFlags(EnumSet.of(Goal.Flag.TARGET));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canUse() {
/* 23 */     if (!this.tameAnimal.isTame() || this.tameAnimal.isOrderedToSit()) {
/* 24 */       return false;
/*    */     }
/* 26 */     LivingEntity $$0 = this.tameAnimal.getOwner();
/* 27 */     if ($$0 == null) {
/* 28 */       return false;
/*    */     }
/* 30 */     this.ownerLastHurtBy = $$0.getLastHurtByMob();
/* 31 */     int $$1 = $$0.getLastHurtByMobTimestamp();
/* 32 */     return ($$1 != this.timestamp && canAttack(this.ownerLastHurtBy, TargetingConditions.DEFAULT) && this.tameAnimal.wantsToAttack(this.ownerLastHurtBy, $$0));
/*    */   }
/*    */ 
/*    */   
/*    */   public void start() {
/* 37 */     this.mob.setTarget(this.ownerLastHurtBy);
/*    */     
/* 39 */     LivingEntity $$0 = this.tameAnimal.getOwner();
/* 40 */     if ($$0 != null) {
/* 41 */       this.timestamp = $$0.getLastHurtByMobTimestamp();
/*    */     }
/*    */     
/* 44 */     super.start();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\target\OwnerHurtByTargetGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */