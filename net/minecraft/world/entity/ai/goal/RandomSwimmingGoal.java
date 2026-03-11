/*    */ package net.minecraft.world.entity.ai.goal;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.world.entity.PathfinderMob;
/*    */ import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class RandomSwimmingGoal
/*    */   extends RandomStrollGoal {
/*    */   public RandomSwimmingGoal(PathfinderMob $$0, double $$1, int $$2) {
/* 11 */     super($$0, $$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   protected Vec3 getPosition() {
/* 17 */     return BehaviorUtils.getRandomSwimmablePos(this.mob, 10, 7);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\RandomSwimmingGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */