/*    */ package net.minecraft.world.entity.ai.behavior;
/*    */ 
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
/*    */ 
/*    */ 
/*    */ public abstract class OneShot<E extends LivingEntity>
/*    */   implements BehaviorControl<E>, Trigger<E>
/*    */ {
/* 11 */   private Behavior.Status status = Behavior.Status.STOPPED;
/*    */ 
/*    */   
/*    */   public final Behavior.Status getStatus() {
/* 15 */     return this.status;
/*    */   }
/*    */ 
/*    */   
/*    */   public final boolean tryStart(ServerLevel $$0, E $$1, long $$2) {
/* 20 */     if (trigger($$0, (LivingEntity)$$1, $$2)) {
/* 21 */       this.status = Behavior.Status.RUNNING;
/* 22 */       return true;
/*    */     } 
/* 24 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public final void tickOrStop(ServerLevel $$0, E $$1, long $$2) {
/* 29 */     doStop($$0, $$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   public final void doStop(ServerLevel $$0, E $$1, long $$2) {
/* 34 */     this.status = Behavior.Status.STOPPED;
/*    */   }
/*    */ 
/*    */   
/*    */   public String debugString() {
/* 39 */     return getClass().getSimpleName();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\OneShot.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */