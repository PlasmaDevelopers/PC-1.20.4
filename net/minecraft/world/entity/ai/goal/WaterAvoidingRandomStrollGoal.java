/*    */ package net.minecraft.world.entity.ai.goal;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.world.entity.PathfinderMob;
/*    */ import net.minecraft.world.entity.ai.util.LandRandomPos;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class WaterAvoidingRandomStrollGoal
/*    */   extends RandomStrollGoal
/*    */ {
/*    */   public static final float PROBABILITY = 0.001F;
/*    */   protected final float probability;
/*    */   
/*    */   public WaterAvoidingRandomStrollGoal(PathfinderMob $$0, double $$1) {
/* 15 */     this($$0, $$1, 0.001F);
/*    */   }
/*    */   
/*    */   public WaterAvoidingRandomStrollGoal(PathfinderMob $$0, double $$1, float $$2) {
/* 19 */     super($$0, $$1);
/* 20 */     this.probability = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   protected Vec3 getPosition() {
/* 26 */     if (this.mob.isInWaterOrBubble()) {
/*    */       
/* 28 */       Vec3 $$0 = LandRandomPos.getPos(this.mob, 15, 7);
/* 29 */       return ($$0 == null) ? super.getPosition() : $$0;
/*    */     } 
/* 31 */     if (this.mob.getRandom().nextFloat() >= this.probability) {
/* 32 */       return LandRandomPos.getPos(this.mob, 10, 7);
/*    */     }
/* 34 */     return super.getPosition();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\WaterAvoidingRandomStrollGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */