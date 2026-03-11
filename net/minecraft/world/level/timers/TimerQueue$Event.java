/*    */ package net.minecraft.world.level.timers;
/*    */ 
/*    */ import com.google.common.primitives.UnsignedLong;
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
/*    */ public class Event<T>
/*    */ {
/*    */   public final long triggerTime;
/*    */   public final UnsignedLong sequentialId;
/*    */   public final String id;
/*    */   public final TimerCallback<T> callback;
/*    */   
/*    */   Event(long $$0, UnsignedLong $$1, String $$2, TimerCallback<T> $$3) {
/* 35 */     this.triggerTime = $$0;
/* 36 */     this.sequentialId = $$1;
/* 37 */     this.id = $$2;
/* 38 */     this.callback = $$3;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\timers\TimerQueue$Event.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */