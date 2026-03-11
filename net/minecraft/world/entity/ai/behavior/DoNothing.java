/*    */ package net.minecraft.world.entity.ai.behavior;
/*    */ 
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DoNothing
/*    */   implements BehaviorControl<LivingEntity>
/*    */ {
/*    */   private final int minDuration;
/*    */   private final int maxDuration;
/* 15 */   private Behavior.Status status = Behavior.Status.STOPPED;
/*    */   private long endTimestamp;
/*    */   
/*    */   public DoNothing(int $$0, int $$1) {
/* 19 */     this.minDuration = $$0;
/* 20 */     this.maxDuration = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public Behavior.Status getStatus() {
/* 25 */     return this.status;
/*    */   }
/*    */ 
/*    */   
/*    */   public final boolean tryStart(ServerLevel $$0, LivingEntity $$1, long $$2) {
/* 30 */     this.status = Behavior.Status.RUNNING;
/* 31 */     int $$3 = this.minDuration + $$0.getRandom().nextInt(this.maxDuration + 1 - this.minDuration);
/* 32 */     this.endTimestamp = $$2 + $$3;
/* 33 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public final void tickOrStop(ServerLevel $$0, LivingEntity $$1, long $$2) {
/* 38 */     if ($$2 > this.endTimestamp) {
/* 39 */       doStop($$0, $$1, $$2);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public final void doStop(ServerLevel $$0, LivingEntity $$1, long $$2) {
/* 45 */     this.status = Behavior.Status.STOPPED;
/*    */   }
/*    */ 
/*    */   
/*    */   public String debugString() {
/* 50 */     return getClass().getSimpleName();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\DoNothing.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */