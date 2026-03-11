/*    */ package net.minecraft.world.level.gameevent;
/*    */ 
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public interface GameEventListenerRegistry {
/*  6 */   public static final GameEventListenerRegistry NOOP = new GameEventListenerRegistry()
/*    */     {
/*    */       public boolean isEmpty() {
/*  9 */         return true;
/*    */       }
/*    */ 
/*    */ 
/*    */       
/*    */       public void register(GameEventListener $$0) {}
/*    */ 
/*    */ 
/*    */       
/*    */       public void unregister(GameEventListener $$0) {}
/*    */ 
/*    */       
/*    */       public boolean visitInRangeListeners(GameEvent $$0, Vec3 $$1, GameEvent.Context $$2, GameEventListenerRegistry.ListenerVisitor $$3) {
/* 22 */         return false;
/*    */       }
/*    */     };
/*    */   
/*    */   boolean isEmpty();
/*    */   
/*    */   void register(GameEventListener paramGameEventListener);
/*    */   
/*    */   void unregister(GameEventListener paramGameEventListener);
/*    */   
/*    */   boolean visitInRangeListeners(GameEvent paramGameEvent, Vec3 paramVec3, GameEvent.Context paramContext, ListenerVisitor paramListenerVisitor);
/*    */   
/*    */   @FunctionalInterface
/*    */   public static interface ListenerVisitor {
/*    */     void visit(GameEventListener param1GameEventListener, Vec3 param1Vec3);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\gameevent\GameEventListenerRegistry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */