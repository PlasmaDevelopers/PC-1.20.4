/*    */ package net.minecraft.world.entity.ai.goal.target;
/*    */ 
/*    */ import java.util.function.Predicate;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.Mob;
/*    */ import net.minecraft.world.entity.TamableAnimal;
/*    */ 
/*    */ public class NonTameRandomTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
/*    */   private final TamableAnimal tamableMob;
/*    */   
/*    */   public NonTameRandomTargetGoal(TamableAnimal $$0, Class<T> $$1, boolean $$2, @Nullable Predicate<LivingEntity> $$3) {
/* 13 */     super((Mob)$$0, $$1, 10, $$2, false, $$3);
/* 14 */     this.tamableMob = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canUse() {
/* 19 */     return (!this.tamableMob.isTame() && super.canUse());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canContinueToUse() {
/* 24 */     if (this.targetConditions != null) {
/* 25 */       return this.targetConditions.test((LivingEntity)this.mob, this.target);
/*    */     }
/* 27 */     return super.canContinueToUse();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\target\NonTameRandomTargetGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */