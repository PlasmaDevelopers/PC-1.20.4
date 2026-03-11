/*    */ package net.minecraft.world.entity.schedule;
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
/*    */ class ActivityTransition
/*    */ {
/*    */   private final int time;
/*    */   private final Activity activity;
/*    */   
/*    */   public ActivityTransition(int $$0, Activity $$1) {
/* 47 */     this.time = $$0;
/* 48 */     this.activity = $$1;
/*    */   }
/*    */   
/*    */   public int getTime() {
/* 52 */     return this.time;
/*    */   }
/*    */   
/*    */   public Activity getActivity() {
/* 56 */     return this.activity;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\schedule\ScheduleBuilder$ActivityTransition.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */