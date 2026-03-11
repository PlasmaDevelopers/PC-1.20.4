/*    */ package net.minecraft.world.entity.ai.goal;
/*    */ import java.util.EnumSet;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.targeting.TargetingConditions;
/*    */ import net.minecraft.world.entity.animal.IronGolem;
/*    */ import net.minecraft.world.entity.npc.Villager;
/*    */ 
/*    */ public class OfferFlowerGoal extends Goal {
/* 10 */   private static final TargetingConditions OFFER_TARGER_CONTEXT = TargetingConditions.forNonCombat().range(6.0D);
/*    */   
/*    */   public static final int OFFER_TICKS = 400;
/*    */   
/*    */   private final IronGolem golem;
/*    */   private Villager villager;
/*    */   private int tick;
/*    */   
/*    */   public OfferFlowerGoal(IronGolem $$0) {
/* 19 */     this.golem = $$0;
/* 20 */     setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canUse() {
/* 25 */     if (!this.golem.level().isDay()) {
/* 26 */       return false;
/*    */     }
/* 28 */     if (this.golem.getRandom().nextInt(8000) != 0) {
/* 29 */       return false;
/*    */     }
/* 31 */     this.villager = (Villager)this.golem.level().getNearestEntity(Villager.class, OFFER_TARGER_CONTEXT, (LivingEntity)this.golem, this.golem.getX(), this.golem.getY(), this.golem.getZ(), this.golem.getBoundingBox().inflate(6.0D, 2.0D, 6.0D));
/* 32 */     return (this.villager != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canContinueToUse() {
/* 37 */     return (this.tick > 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void start() {
/* 42 */     this.tick = adjustedTickDelay(400);
/* 43 */     this.golem.offerFlower(true);
/*    */   }
/*    */ 
/*    */   
/*    */   public void stop() {
/* 48 */     this.golem.offerFlower(false);
/* 49 */     this.villager = null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 54 */     this.golem.getLookControl().setLookAt((Entity)this.villager, 30.0F, 30.0F);
/* 55 */     this.tick--;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\OfferFlowerGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */