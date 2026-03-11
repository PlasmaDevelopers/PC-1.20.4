/*    */ package com.mojang.realmsclient.gui.screens;
/*    */ 
/*    */ import com.mojang.realmsclient.util.task.LongRunningTask;
/*    */ import net.minecraft.client.gui.screens.Screen;
/*    */ 
/*    */ public class RealmsLongRunningMcoTickTaskScreen extends RealmsLongRunningMcoTaskScreen {
/*    */   private final LongRunningTask task;
/*    */   
/*    */   public RealmsLongRunningMcoTickTaskScreen(Screen $$0, LongRunningTask $$1) {
/* 10 */     super($$0, new LongRunningTask[] { $$1 });
/* 11 */     this.task = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 16 */     super.tick();
/* 17 */     this.task.tick();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void cancel() {
/* 22 */     this.task.abortTask();
/* 23 */     super.cancel();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\gui\screens\RealmsLongRunningMcoTickTaskScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */