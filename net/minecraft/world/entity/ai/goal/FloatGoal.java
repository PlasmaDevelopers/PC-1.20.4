/*    */ package net.minecraft.world.entity.ai.goal;
/*    */ 
/*    */ import java.util.EnumSet;
/*    */ import net.minecraft.tags.FluidTags;
/*    */ import net.minecraft.world.entity.Mob;
/*    */ 
/*    */ public class FloatGoal
/*    */   extends Goal {
/*    */   private final Mob mob;
/*    */   
/*    */   public FloatGoal(Mob $$0) {
/* 12 */     this.mob = $$0;
/* 13 */     setFlags(EnumSet.of(Goal.Flag.JUMP));
/* 14 */     $$0.getNavigation().setCanFloat(true);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canUse() {
/* 19 */     return ((this.mob.isInWater() && this.mob.getFluidHeight(FluidTags.WATER) > this.mob.getFluidJumpThreshold()) || this.mob.isInLava());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean requiresUpdateEveryTick() {
/* 24 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 29 */     if (this.mob.getRandom().nextFloat() < 0.8F)
/* 30 */       this.mob.getJumpControl().jump(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\FloatGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */