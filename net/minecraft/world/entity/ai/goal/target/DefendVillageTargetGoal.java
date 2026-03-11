/*    */ package net.minecraft.world.entity.ai.goal.target;
/*    */ 
/*    */ import java.util.EnumSet;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.Mob;
/*    */ import net.minecraft.world.entity.ai.goal.Goal;
/*    */ import net.minecraft.world.entity.ai.targeting.TargetingConditions;
/*    */ import net.minecraft.world.entity.animal.IronGolem;
/*    */ import net.minecraft.world.entity.npc.Villager;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.phys.AABB;
/*    */ 
/*    */ public class DefendVillageTargetGoal extends TargetGoal {
/*    */   private final IronGolem golem;
/*    */   @Nullable
/*    */   private LivingEntity potentialTarget;
/* 19 */   private final TargetingConditions attackTargeting = TargetingConditions.forCombat().range(64.0D);
/*    */   
/*    */   public DefendVillageTargetGoal(IronGolem $$0) {
/* 22 */     super((Mob)$$0, false, true);
/* 23 */     this.golem = $$0;
/* 24 */     setFlags(EnumSet.of(Goal.Flag.TARGET));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canUse() {
/* 29 */     AABB $$0 = this.golem.getBoundingBox().inflate(10.0D, 8.0D, 10.0D);
/* 30 */     List<? extends LivingEntity> $$1 = this.golem.level().getNearbyEntities(Villager.class, this.attackTargeting, (LivingEntity)this.golem, $$0);
/* 31 */     List<Player> $$2 = this.golem.level().getNearbyPlayers(this.attackTargeting, (LivingEntity)this.golem, $$0);
/*    */     
/* 33 */     for (LivingEntity $$3 : $$1) {
/* 34 */       Villager $$4 = (Villager)$$3;
/* 35 */       for (Player $$5 : $$2) {
/* 36 */         int $$6 = $$4.getPlayerReputation($$5);
/*    */         
/* 38 */         if ($$6 <= -100) {
/* 39 */           this.potentialTarget = (LivingEntity)$$5;
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 44 */     if (this.potentialTarget == null) {
/* 45 */       return false;
/*    */     }
/*    */     
/* 48 */     if (this.potentialTarget instanceof Player && (this.potentialTarget.isSpectator() || ((Player)this.potentialTarget).isCreative())) {
/* 49 */       return false;
/*    */     }
/*    */     
/* 52 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void start() {
/* 57 */     this.golem.setTarget(this.potentialTarget);
/* 58 */     super.start();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\target\DefendVillageTargetGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */