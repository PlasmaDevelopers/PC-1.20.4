/*    */ package net.minecraft.world.entity.ai.behavior.declarative;
/*    */ 
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.ai.behavior.OneShot;
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
/*    */ class null
/*    */   extends OneShot<E>
/*    */ {
/*    */   public boolean trigger(ServerLevel $$0, E $$1, long $$2) {
/* 48 */     Trigger<E> $$3 = (Trigger<E>)resolvedBuilder.tryTrigger($$0, $$1, $$2);
/* 49 */     if ($$3 == null) {
/* 50 */       return false;
/*    */     }
/*    */     
/* 53 */     return $$3.trigger($$0, $$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   public String debugString() {
/* 58 */     return "OneShot[" + resolvedBuilder.debugString() + "]";
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 63 */     return debugString();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\declarative\BehaviorBuilder$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */