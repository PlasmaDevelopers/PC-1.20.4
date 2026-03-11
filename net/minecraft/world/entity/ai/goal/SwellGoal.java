/*    */ package net.minecraft.world.entity.ai.goal;
/*    */ 
/*    */ import java.util.EnumSet;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.monster.Creeper;
/*    */ 
/*    */ public class SwellGoal extends Goal {
/*    */   private final Creeper creeper;
/*    */   @Nullable
/*    */   private LivingEntity target;
/*    */   
/*    */   public SwellGoal(Creeper $$0) {
/* 15 */     this.creeper = $$0;
/* 16 */     setFlags(EnumSet.of(Goal.Flag.MOVE));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canUse() {
/* 21 */     LivingEntity $$0 = this.creeper.getTarget();
/* 22 */     return (this.creeper.getSwellDir() > 0 || ($$0 != null && this.creeper.distanceToSqr((Entity)$$0) < 9.0D));
/*    */   }
/*    */ 
/*    */   
/*    */   public void start() {
/* 27 */     this.creeper.getNavigation().stop();
/* 28 */     this.target = this.creeper.getTarget();
/*    */   }
/*    */ 
/*    */   
/*    */   public void stop() {
/* 33 */     this.target = null;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean requiresUpdateEveryTick() {
/* 38 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 43 */     if (this.target == null) {
/* 44 */       this.creeper.setSwellDir(-1);
/*    */       
/*    */       return;
/*    */     } 
/* 48 */     if (this.creeper.distanceToSqr((Entity)this.target) > 49.0D) {
/* 49 */       this.creeper.setSwellDir(-1);
/*    */       
/*    */       return;
/*    */     } 
/* 53 */     if (!this.creeper.getSensing().hasLineOfSight((Entity)this.target)) {
/* 54 */       this.creeper.setSwellDir(-1);
/*    */       
/*    */       return;
/*    */     } 
/* 58 */     this.creeper.setSwellDir(1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\SwellGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */