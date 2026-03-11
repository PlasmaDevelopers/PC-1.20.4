/*    */ package net.minecraft.world.level.gameevent;public interface GameEventListener { PositionSource getListenerSource();
/*    */   
/*    */   int getListenerRadius();
/*    */   
/*    */   boolean handleGameEvent(ServerLevel paramServerLevel, GameEvent paramGameEvent, GameEvent.Context paramContext, Vec3 paramVec3);
/*    */   
/*    */   public enum DeliveryMode {
/*  8 */     UNSPECIFIED,
/*  9 */     BY_DISTANCE;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default DeliveryMode getDeliveryMode() {
/* 18 */     return DeliveryMode.UNSPECIFIED;
/*    */   }
/*    */   
/*    */   public static interface Holder<T extends GameEventListener> {
/*    */     T getListener();
/*    */   } }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\gameevent\GameEventListener.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */