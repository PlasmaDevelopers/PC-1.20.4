/*    */ package net.minecraft.server;
/*    */ 
/*    */ public class TickTask implements Runnable {
/*    */   private final int tick;
/*    */   private final Runnable runnable;
/*    */   
/*    */   public TickTask(int $$0, Runnable $$1) {
/*  8 */     this.tick = $$0;
/*  9 */     this.runnable = $$1;
/*    */   }
/*    */   
/*    */   public int getTick() {
/* 13 */     return this.tick;
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/* 18 */     this.runnable.run();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\TickTask.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */