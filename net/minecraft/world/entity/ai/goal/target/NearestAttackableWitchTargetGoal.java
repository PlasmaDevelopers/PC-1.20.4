/*    */ package net.minecraft.world.entity.ai.goal.target;
/*    */ 
/*    */ import java.util.function.Predicate;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.Mob;
/*    */ import net.minecraft.world.entity.raid.Raider;
/*    */ 
/*    */ public class NearestAttackableWitchTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
/*    */   private boolean canAttack;
/*    */   
/*    */   public NearestAttackableWitchTargetGoal(Raider $$0, Class<T> $$1, int $$2, boolean $$3, boolean $$4, @Nullable Predicate<LivingEntity> $$5) {
/* 13 */     super((Mob)$$0, $$1, $$2, $$3, $$4, $$5);
/* 14 */     this.canAttack = true;
/*    */   }
/*    */   
/*    */   public void setCanAttack(boolean $$0) {
/* 18 */     this.canAttack = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canUse() {
/* 23 */     return (this.canAttack && super.canUse());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\target\NearestAttackableWitchTargetGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */