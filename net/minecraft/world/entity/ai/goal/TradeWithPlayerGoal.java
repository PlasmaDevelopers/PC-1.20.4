/*    */ package net.minecraft.world.entity.ai.goal;
/*    */ 
/*    */ import java.util.EnumSet;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.npc.AbstractVillager;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ 
/*    */ public class TradeWithPlayerGoal extends Goal {
/*    */   private final AbstractVillager mob;
/*    */   
/*    */   public TradeWithPlayerGoal(AbstractVillager $$0) {
/* 12 */     this.mob = $$0;
/* 13 */     setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canUse() {
/* 18 */     if (!this.mob.isAlive()) {
/* 19 */       return false;
/*    */     }
/* 21 */     if (this.mob.isInWater()) {
/* 22 */       return false;
/*    */     }
/* 24 */     if (!this.mob.onGround()) {
/* 25 */       return false;
/*    */     }
/* 27 */     if (this.mob.hurtMarked) {
/* 28 */       return false;
/*    */     }
/*    */     
/* 31 */     Player $$0 = this.mob.getTradingPlayer();
/* 32 */     if ($$0 == null)
/*    */     {
/* 34 */       return false;
/*    */     }
/*    */     
/* 37 */     if (this.mob.distanceToSqr((Entity)$$0) > 16.0D)
/*    */     {
/* 39 */       return false;
/*    */     }
/*    */     
/* 42 */     return ($$0.containerMenu != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public void start() {
/* 47 */     this.mob.getNavigation().stop();
/*    */   }
/*    */ 
/*    */   
/*    */   public void stop() {
/* 52 */     this.mob.setTradingPlayer(null);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\TradeWithPlayerGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */