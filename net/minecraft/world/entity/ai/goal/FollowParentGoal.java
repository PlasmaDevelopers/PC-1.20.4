/*    */ package net.minecraft.world.entity.ai.goal;
/*    */ 
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.animal.Animal;
/*    */ 
/*    */ public class FollowParentGoal extends Goal {
/*    */   public static final int HORIZONTAL_SCAN_RANGE = 8;
/*    */   public static final int VERTICAL_SCAN_RANGE = 4;
/*    */   public static final int DONT_FOLLOW_IF_CLOSER_THAN = 3;
/*    */   private final Animal animal;
/*    */   @Nullable
/*    */   private Animal parent;
/*    */   private final double speedModifier;
/*    */   private int timeToRecalcPath;
/*    */   
/*    */   public FollowParentGoal(Animal $$0, double $$1) {
/* 19 */     this.animal = $$0;
/* 20 */     this.speedModifier = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canUse() {
/* 25 */     if (this.animal.getAge() >= 0) {
/* 26 */       return false;
/*    */     }
/*    */     
/* 29 */     List<? extends Animal> $$0 = this.animal.level().getEntitiesOfClass(this.animal.getClass(), this.animal.getBoundingBox().inflate(8.0D, 4.0D, 8.0D));
/*    */     
/* 31 */     Animal $$1 = null;
/* 32 */     double $$2 = Double.MAX_VALUE;
/* 33 */     for (Animal $$3 : $$0) {
/* 34 */       if ($$3.getAge() < 0) {
/*    */         continue;
/*    */       }
/* 37 */       double $$4 = this.animal.distanceToSqr((Entity)$$3);
/* 38 */       if ($$4 > $$2) {
/*    */         continue;
/*    */       }
/* 41 */       $$2 = $$4;
/* 42 */       $$1 = $$3;
/*    */     } 
/*    */     
/* 45 */     if ($$1 == null) {
/* 46 */       return false;
/*    */     }
/* 48 */     if ($$2 < 9.0D) {
/* 49 */       return false;
/*    */     }
/* 51 */     this.parent = $$1;
/* 52 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canContinueToUse() {
/* 57 */     if (this.animal.getAge() >= 0) {
/* 58 */       return false;
/*    */     }
/* 60 */     if (!this.parent.isAlive()) {
/* 61 */       return false;
/*    */     }
/* 63 */     double $$0 = this.animal.distanceToSqr((Entity)this.parent);
/* 64 */     if ($$0 < 9.0D || $$0 > 256.0D) {
/* 65 */       return false;
/*    */     }
/* 67 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void start() {
/* 72 */     this.timeToRecalcPath = 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void stop() {
/* 77 */     this.parent = null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 82 */     if (--this.timeToRecalcPath > 0) {
/*    */       return;
/*    */     }
/* 85 */     this.timeToRecalcPath = adjustedTickDelay(10);
/* 86 */     this.animal.getNavigation().moveTo((Entity)this.parent, this.speedModifier);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\FollowParentGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */