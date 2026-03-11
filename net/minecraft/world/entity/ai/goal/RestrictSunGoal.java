/*    */ package net.minecraft.world.entity.ai.goal;
/*    */ 
/*    */ import net.minecraft.world.entity.EquipmentSlot;
/*    */ import net.minecraft.world.entity.Mob;
/*    */ import net.minecraft.world.entity.PathfinderMob;
/*    */ import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
/*    */ import net.minecraft.world.entity.ai.util.GoalUtils;
/*    */ 
/*    */ public class RestrictSunGoal
/*    */   extends Goal {
/*    */   public RestrictSunGoal(PathfinderMob $$0) {
/* 12 */     this.mob = $$0;
/*    */   }
/*    */   private final PathfinderMob mob;
/*    */   
/*    */   public boolean canUse() {
/* 17 */     return (this.mob.level().isDay() && this.mob.getItemBySlot(EquipmentSlot.HEAD).isEmpty() && GoalUtils.hasGroundPathNavigation((Mob)this.mob));
/*    */   }
/*    */ 
/*    */   
/*    */   public void start() {
/* 22 */     ((GroundPathNavigation)this.mob.getNavigation()).setAvoidSun(true);
/*    */   }
/*    */ 
/*    */   
/*    */   public void stop() {
/* 27 */     if (GoalUtils.hasGroundPathNavigation((Mob)this.mob))
/* 28 */       ((GroundPathNavigation)this.mob.getNavigation()).setAvoidSun(false); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\RestrictSunGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */