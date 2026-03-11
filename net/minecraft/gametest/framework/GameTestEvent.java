/*    */ package net.minecraft.gametest.framework;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ class GameTestEvent {
/*    */   @Nullable
/*    */   public final Long expectedDelay;
/*    */   public final Runnable assertion;
/*    */   
/*    */   private GameTestEvent(@Nullable Long $$0, Runnable $$1) {
/* 11 */     this.expectedDelay = $$0;
/* 12 */     this.assertion = $$1;
/*    */   }
/*    */   
/*    */   static GameTestEvent create(Runnable $$0) {
/* 16 */     return new GameTestEvent(null, $$0);
/*    */   }
/*    */   
/*    */   static GameTestEvent create(long $$0, Runnable $$1) {
/* 20 */     return new GameTestEvent(Long.valueOf($$0), $$1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\gametest\framework\GameTestEvent.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */