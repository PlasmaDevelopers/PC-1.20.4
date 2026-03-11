/*    */ package net.minecraft.world.entity.ai.targeting;
/*    */ 
/*    */ import java.util.function.Predicate;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.world.Difficulty;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.Mob;
/*    */ 
/*    */ public class TargetingConditions {
/* 11 */   public static final TargetingConditions DEFAULT = forCombat();
/*    */   
/*    */   private static final double MIN_VISIBILITY_DISTANCE_FOR_INVISIBLE_TARGET = 2.0D;
/*    */   private final boolean isCombat;
/* 15 */   private double range = -1.0D;
/*    */   private boolean checkLineOfSight = true;
/*    */   private boolean testInvisible = true;
/*    */   @Nullable
/*    */   private Predicate<LivingEntity> selector;
/*    */   
/*    */   private TargetingConditions(boolean $$0) {
/* 22 */     this.isCombat = $$0;
/*    */   }
/*    */   
/*    */   public static TargetingConditions forCombat() {
/* 26 */     return new TargetingConditions(true);
/*    */   }
/*    */   
/*    */   public static TargetingConditions forNonCombat() {
/* 30 */     return new TargetingConditions(false);
/*    */   }
/*    */   
/*    */   public TargetingConditions copy() {
/* 34 */     TargetingConditions $$0 = this.isCombat ? forCombat() : forNonCombat();
/* 35 */     $$0.range = this.range;
/* 36 */     $$0.checkLineOfSight = this.checkLineOfSight;
/* 37 */     $$0.testInvisible = this.testInvisible;
/* 38 */     $$0.selector = this.selector;
/* 39 */     return $$0;
/*    */   }
/*    */   
/*    */   public TargetingConditions range(double $$0) {
/* 43 */     this.range = $$0;
/* 44 */     return this;
/*    */   }
/*    */   
/*    */   public TargetingConditions ignoreLineOfSight() {
/* 48 */     this.checkLineOfSight = false;
/* 49 */     return this;
/*    */   }
/*    */   
/*    */   public TargetingConditions ignoreInvisibilityTesting() {
/* 53 */     this.testInvisible = false;
/* 54 */     return this;
/*    */   }
/*    */   
/*    */   public TargetingConditions selector(@Nullable Predicate<LivingEntity> $$0) {
/* 58 */     this.selector = $$0;
/* 59 */     return this;
/*    */   }
/*    */   
/*    */   public boolean test(@Nullable LivingEntity $$0, LivingEntity $$1) {
/* 63 */     if ($$0 == $$1) {
/* 64 */       return false;
/*    */     }
/* 66 */     if (!$$1.canBeSeenByAnyone()) {
/* 67 */       return false;
/*    */     }
/* 69 */     if (this.selector != null && !this.selector.test($$1)) {
/* 70 */       return false;
/*    */     }
/* 72 */     if ($$0 == null) {
/* 73 */       if (this.isCombat && (!$$1.canBeSeenAsEnemy() || $$1.level().getDifficulty() == Difficulty.PEACEFUL)) {
/* 74 */         return false;
/*    */       }
/*    */     } else {
/* 77 */       if (this.isCombat && (!$$0.canAttack($$1) || !$$0.canAttackType($$1.getType()) || $$0.isAlliedTo((Entity)$$1))) {
/* 78 */         return false;
/*    */       }
/*    */       
/* 81 */       if (this.range > 0.0D) {
/* 82 */         double $$2 = this.testInvisible ? $$1.getVisibilityPercent((Entity)$$0) : 1.0D;
/* 83 */         double $$3 = Math.max(this.range * $$2, 2.0D);
/* 84 */         double $$4 = $$0.distanceToSqr($$1.getX(), $$1.getY(), $$1.getZ());
/* 85 */         if ($$4 > $$3 * $$3) {
/* 86 */           return false;
/*    */         }
/*    */       } 
/*    */ 
/*    */       
/* 91 */       if (this.checkLineOfSight && $$0 instanceof Mob) { Mob $$5 = (Mob)$$0; if (!$$5.getSensing().hasLineOfSight((Entity)$$1))
/* 92 */           return false;  }
/*    */     
/*    */     } 
/* 95 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\targeting\TargetingConditions.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */