/*    */ package net.minecraft.world.entity.ai.goal;
/*    */ 
/*    */ import java.util.EnumSet;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.world.InteractionHand;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.targeting.TargetingConditions;
/*    */ import net.minecraft.world.entity.animal.Wolf;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.Items;
/*    */ import net.minecraft.world.level.Level;
/*    */ 
/*    */ public class BegGoal extends Goal {
/*    */   private final Wolf wolf;
/*    */   @Nullable
/*    */   private Player player;
/*    */   private final Level level;
/*    */   private final float lookDistance;
/*    */   private int lookTime;
/*    */   private final TargetingConditions begTargeting;
/*    */   
/*    */   public BegGoal(Wolf $$0, float $$1) {
/* 25 */     this.wolf = $$0;
/* 26 */     this.level = $$0.level();
/* 27 */     this.lookDistance = $$1;
/* 28 */     this.begTargeting = TargetingConditions.forNonCombat().range($$1);
/* 29 */     setFlags(EnumSet.of(Goal.Flag.LOOK));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canUse() {
/* 34 */     this.player = this.level.getNearestPlayer(this.begTargeting, (LivingEntity)this.wolf);
/* 35 */     if (this.player == null) {
/* 36 */       return false;
/*    */     }
/* 38 */     return playerHoldingInteresting(this.player);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canContinueToUse() {
/* 43 */     if (!this.player.isAlive()) {
/* 44 */       return false;
/*    */     }
/* 46 */     if (this.wolf.distanceToSqr((Entity)this.player) > (this.lookDistance * this.lookDistance)) {
/* 47 */       return false;
/*    */     }
/* 49 */     return (this.lookTime > 0 && playerHoldingInteresting(this.player));
/*    */   }
/*    */ 
/*    */   
/*    */   public void start() {
/* 54 */     this.wolf.setIsInterested(true);
/* 55 */     this.lookTime = adjustedTickDelay(40 + this.wolf.getRandom().nextInt(40));
/*    */   }
/*    */ 
/*    */   
/*    */   public void stop() {
/* 60 */     this.wolf.setIsInterested(false);
/* 61 */     this.player = null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 66 */     this.wolf.getLookControl().setLookAt(this.player.getX(), this.player.getEyeY(), this.player.getZ(), 10.0F, this.wolf.getMaxHeadXRot());
/* 67 */     this.lookTime--;
/*    */   }
/*    */   
/*    */   private boolean playerHoldingInteresting(Player $$0) {
/* 71 */     for (InteractionHand $$1 : InteractionHand.values()) {
/* 72 */       ItemStack $$2 = $$0.getItemInHand($$1);
/* 73 */       if (this.wolf.isTame() && $$2.is(Items.BONE)) {
/* 74 */         return true;
/*    */       }
/* 76 */       if (this.wolf.isFood($$2)) {
/* 77 */         return true;
/*    */       }
/*    */     } 
/* 80 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\BegGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */