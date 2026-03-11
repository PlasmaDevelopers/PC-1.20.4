/*    */ package net.minecraft.world.entity.ai.goal;
/*    */ 
/*    */ import net.minecraft.world.entity.Mob;
/*    */ 
/*    */ public class OpenDoorGoal extends DoorInteractGoal {
/*    */   private final boolean closeDoor;
/*    */   private int forgetTime;
/*    */   
/*    */   public OpenDoorGoal(Mob $$0, boolean $$1) {
/* 10 */     super($$0);
/* 11 */     this.mob = $$0;
/* 12 */     this.closeDoor = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canContinueToUse() {
/* 17 */     return (this.closeDoor && this.forgetTime > 0 && super.canContinueToUse());
/*    */   }
/*    */ 
/*    */   
/*    */   public void start() {
/* 22 */     this.forgetTime = 20;
/* 23 */     setOpen(true);
/*    */   }
/*    */ 
/*    */   
/*    */   public void stop() {
/* 28 */     setOpen(false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 33 */     this.forgetTime--;
/* 34 */     super.tick();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\OpenDoorGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */