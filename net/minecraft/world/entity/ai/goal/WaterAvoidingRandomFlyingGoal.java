/*    */ package net.minecraft.world.entity.ai.goal;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.world.entity.PathfinderMob;
/*    */ import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
/*    */ import net.minecraft.world.entity.ai.util.HoverRandomPos;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class WaterAvoidingRandomFlyingGoal
/*    */   extends WaterAvoidingRandomStrollGoal
/*    */ {
/*    */   public WaterAvoidingRandomFlyingGoal(PathfinderMob $$0, double $$1) {
/* 13 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   protected Vec3 getPosition() {
/* 19 */     Vec3 $$0 = this.mob.getViewVector(0.0F);
/*    */     
/* 21 */     int $$1 = 8;
/* 22 */     Vec3 $$2 = HoverRandomPos.getPos(this.mob, 8, 7, $$0.x, $$0.z, 1.5707964F, 3, 1);
/* 23 */     if ($$2 != null) {
/* 24 */       return $$2;
/*    */     }
/*    */ 
/*    */     
/* 28 */     return AirAndWaterRandomPos.getPos(this.mob, 8, 4, -2, $$0.x, $$0.z, 1.5707963705062866D);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\WaterAvoidingRandomFlyingGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */