/*    */ package net.minecraft.world.entity.ai.goal.target;
/*    */ 
/*    */ import java.util.function.Predicate;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.Mob;
/*    */ import net.minecraft.world.entity.raid.Raider;
/*    */ 
/*    */ public class NearestHealableRaiderTargetGoal<T extends LivingEntity>
/*    */   extends NearestAttackableTargetGoal<T> {
/*    */   private static final int DEFAULT_COOLDOWN = 200;
/*    */   private int cooldown;
/*    */   
/*    */   public NearestHealableRaiderTargetGoal(Raider $$0, Class<T> $$1, boolean $$2, @Nullable Predicate<LivingEntity> $$3) {
/* 15 */     super((Mob)$$0, $$1, 500, $$2, false, $$3);
/* 16 */     this.cooldown = 0;
/*    */   }
/*    */   
/*    */   public int getCooldown() {
/* 20 */     return this.cooldown;
/*    */   }
/*    */   
/*    */   public void decrementCooldown() {
/* 24 */     this.cooldown--;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canUse() {
/* 29 */     if (this.cooldown > 0 || !this.mob.getRandom().nextBoolean()) {
/* 30 */       return false;
/*    */     }
/* 32 */     if (!((Raider)this.mob).hasActiveRaid()) {
/* 33 */       return false;
/*    */     }
/*    */     
/* 36 */     findTarget();
/* 37 */     return (this.target != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public void start() {
/* 42 */     this.cooldown = reducedTickDelay(200);
/* 43 */     super.start();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\target\NearestHealableRaiderTargetGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */