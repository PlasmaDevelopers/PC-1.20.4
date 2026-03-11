/*    */ package net.minecraft.world.entity.ai.goal;
/*    */ import java.util.EnumSet;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.targeting.TargetingConditions;
/*    */ import net.minecraft.world.entity.animal.Animal;
/*    */ import net.minecraft.world.level.Level;
/*    */ 
/*    */ public class BreedGoal extends Goal {
/* 13 */   private static final TargetingConditions PARTNER_TARGETING = TargetingConditions.forNonCombat().range(8.0D).ignoreLineOfSight();
/*    */   
/*    */   protected final Animal animal;
/*    */   private final Class<? extends Animal> partnerClass;
/*    */   protected final Level level;
/*    */   @Nullable
/*    */   protected Animal partner;
/*    */   private int loveTime;
/*    */   private final double speedModifier;
/*    */   
/*    */   public BreedGoal(Animal $$0, double $$1) {
/* 24 */     this($$0, $$1, (Class)$$0.getClass());
/*    */   }
/*    */   
/*    */   public BreedGoal(Animal $$0, double $$1, Class<? extends Animal> $$2) {
/* 28 */     this.animal = $$0;
/* 29 */     this.level = $$0.level();
/* 30 */     this.partnerClass = $$2;
/* 31 */     this.speedModifier = $$1;
/* 32 */     setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canUse() {
/* 37 */     if (!this.animal.isInLove()) {
/* 38 */       return false;
/*    */     }
/* 40 */     this.partner = getFreePartner();
/* 41 */     return (this.partner != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canContinueToUse() {
/* 46 */     return (this.partner.isAlive() && this.partner.isInLove() && this.loveTime < 60 && !this.partner.isPanicking());
/*    */   }
/*    */ 
/*    */   
/*    */   public void stop() {
/* 51 */     this.partner = null;
/* 52 */     this.loveTime = 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 57 */     this.animal.getLookControl().setLookAt((Entity)this.partner, 10.0F, this.animal.getMaxHeadXRot());
/* 58 */     this.animal.getNavigation().moveTo((Entity)this.partner, this.speedModifier);
/* 59 */     this.loveTime++;
/* 60 */     if (this.loveTime >= adjustedTickDelay(60) && this.animal.distanceToSqr((Entity)this.partner) < 9.0D) {
/* 61 */       breed();
/*    */     }
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   private Animal getFreePartner() {
/* 67 */     List<? extends Animal> $$0 = this.level.getNearbyEntities(this.partnerClass, PARTNER_TARGETING, (LivingEntity)this.animal, this.animal.getBoundingBox().inflate(8.0D));
/* 68 */     double $$1 = Double.MAX_VALUE;
/* 69 */     Animal $$2 = null;
/* 70 */     for (Animal $$3 : $$0) {
/* 71 */       if (this.animal.canMate($$3) && !$$3.isPanicking() && this.animal.distanceToSqr((Entity)$$3) < $$1) {
/* 72 */         $$2 = $$3;
/* 73 */         $$1 = this.animal.distanceToSqr((Entity)$$3);
/*    */       } 
/*    */     } 
/* 76 */     return $$2;
/*    */   }
/*    */   
/*    */   protected void breed() {
/* 80 */     this.animal.spawnChildFromBreeding((ServerLevel)this.level, this.partner);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\BreedGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */