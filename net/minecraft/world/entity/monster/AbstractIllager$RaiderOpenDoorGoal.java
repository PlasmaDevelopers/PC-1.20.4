/*    */ package net.minecraft.world.entity.monster;
/*    */ 
/*    */ import net.minecraft.world.entity.Mob;
/*    */ import net.minecraft.world.entity.ai.goal.OpenDoorGoal;
/*    */ import net.minecraft.world.entity.raid.Raider;
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
/*    */ public class RaiderOpenDoorGoal
/*    */   extends OpenDoorGoal
/*    */ {
/*    */   public RaiderOpenDoorGoal(Raider $$1) {
/* 67 */     super((Mob)$$1, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canUse() {
/* 72 */     return (super.canUse() && AbstractIllager.this.hasActiveRaid());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\AbstractIllager$RaiderOpenDoorGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */