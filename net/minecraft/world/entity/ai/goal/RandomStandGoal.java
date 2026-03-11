/*    */ package net.minecraft.world.entity.ai.goal;
/*    */ 
/*    */ import net.minecraft.sounds.SoundEvent;
/*    */ import net.minecraft.world.entity.animal.horse.AbstractHorse;
/*    */ 
/*    */ public class RandomStandGoal extends Goal {
/*    */   private final AbstractHorse horse;
/*    */   private int nextStand;
/*    */   
/*    */   public RandomStandGoal(AbstractHorse $$0) {
/* 11 */     this.horse = $$0;
/* 12 */     resetStandInterval($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void start() {
/* 17 */     this.horse.standIfPossible();
/* 18 */     playStandSound();
/*    */   }
/*    */   
/*    */   private void playStandSound() {
/* 22 */     SoundEvent $$0 = this.horse.getAmbientStandSound();
/* 23 */     if ($$0 != null) {
/* 24 */       this.horse.playSound($$0);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canContinueToUse() {
/* 30 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canUse() {
/* 37 */     this.nextStand++;
/* 38 */     if (this.nextStand > 0 && this.horse.getRandom().nextInt(1000) < this.nextStand) {
/* 39 */       resetStandInterval(this.horse);
/* 40 */       return (!this.horse.isImmobile() && this.horse.getRandom().nextInt(10) == 0);
/*    */     } 
/* 42 */     return false;
/*    */   }
/*    */   
/*    */   private void resetStandInterval(AbstractHorse $$0) {
/* 46 */     this.nextStand = -$$0.getAmbientStandInterval();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean requiresUpdateEveryTick() {
/* 51 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\RandomStandGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */