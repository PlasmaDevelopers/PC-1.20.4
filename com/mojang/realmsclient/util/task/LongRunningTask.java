/*    */ package com.mojang.realmsclient.util.task;
/*    */ 
/*    */ import com.mojang.logging.LogUtils;
/*    */ import com.mojang.realmsclient.RealmsMainScreen;
/*    */ import com.mojang.realmsclient.exception.RealmsServiceException;
/*    */ import com.mojang.realmsclient.gui.screens.RealmsGenericErrorScreen;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.screens.Screen;
/*    */ import net.minecraft.client.gui.screens.TitleScreen;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public abstract class LongRunningTask implements Runnable {
/*    */   protected static final int NUMBER_OF_RETRIES = 25;
/* 15 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   private boolean aborted = false;
/*    */   
/*    */   protected static void pause(long $$0) {
/*    */     try {
/* 20 */       Thread.sleep($$0 * 1000L);
/* 21 */     } catch (InterruptedException $$1) {
/* 22 */       Thread.currentThread().interrupt();
/* 23 */       LOGGER.error("", $$1);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static void setScreen(Screen $$0) {
/* 29 */     Minecraft $$1 = Minecraft.getInstance();
/* 30 */     $$1.execute(() -> $$0.setScreen($$1));
/*    */   }
/*    */   
/*    */   protected void error(Component $$0) {
/* 34 */     abortTask();
/* 35 */     Minecraft $$1 = Minecraft.getInstance();
/* 36 */     $$1.execute(() -> $$0.setScreen((Screen)new RealmsGenericErrorScreen($$1, (Screen)new RealmsMainScreen((Screen)new TitleScreen()))));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void error(Exception $$0) {
/* 44 */     if ($$0 instanceof RealmsServiceException) { RealmsServiceException $$1 = (RealmsServiceException)$$0;
/* 45 */       error($$1.realmsError.errorMessage()); }
/*    */     else
/* 47 */     { error((Component)Component.literal($$0.getMessage())); }
/*    */   
/*    */   }
/*    */   
/*    */   protected void error(RealmsServiceException $$0) {
/* 52 */     error($$0.realmsError.errorMessage());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean aborted() {
/* 58 */     return this.aborted;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {}
/*    */ 
/*    */   
/*    */   public void init() {}
/*    */   
/*    */   public void abortTask() {
/* 68 */     this.aborted = true;
/*    */   }
/*    */   
/*    */   public abstract Component getTitle();
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclien\\util\task\LongRunningTask.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */