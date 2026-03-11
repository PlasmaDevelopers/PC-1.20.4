/*    */ package net.minecraft.world.entity.monster;
/*    */ 
/*    */ import net.minecraft.world.entity.PathfinderMob;
/*    */ import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class null
/*    */   extends MeleeAttackGoal
/*    */ {
/*    */   null(PathfinderMob $$1, double $$2, boolean $$3) {
/* 54 */     super($$1, $$2, $$3);
/*    */   }
/*    */   public void stop() {
/* 57 */     super.stop();
/* 58 */     AbstractSkeleton.this.setAggressive(false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void start() {
/* 63 */     super.start();
/* 64 */     AbstractSkeleton.this.setAggressive(true);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\AbstractSkeleton$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */