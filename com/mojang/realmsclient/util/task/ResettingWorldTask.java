/*    */ package com.mojang.realmsclient.util.task;
/*    */ 
/*    */ import com.mojang.logging.LogUtils;
/*    */ import com.mojang.realmsclient.client.RealmsClient;
/*    */ import com.mojang.realmsclient.exception.RealmsServiceException;
/*    */ import com.mojang.realmsclient.exception.RetryCallException;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public abstract class ResettingWorldTask extends LongRunningTask {
/* 11 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/*    */   private final long serverId;
/*    */   
/*    */   private final Component title;
/*    */   private final Runnable callback;
/*    */   
/*    */   public ResettingWorldTask(long $$0, Component $$1, Runnable $$2) {
/* 19 */     this.serverId = $$0;
/* 20 */     this.title = $$1;
/* 21 */     this.callback = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   protected abstract void sendResetRequest(RealmsClient paramRealmsClient, long paramLong) throws RealmsServiceException;
/*    */   
/*    */   public void run() {
/* 28 */     RealmsClient $$0 = RealmsClient.create();
/* 29 */     for (int $$1 = 0; $$1 < 25; $$1++) {
/*    */       try {
/* 31 */         if (aborted()) {
/*    */           return;
/*    */         }
/*    */         
/* 35 */         sendResetRequest($$0, this.serverId);
/*    */         
/* 37 */         if (aborted()) {
/*    */           return;
/*    */         }
/*    */         
/* 41 */         this.callback.run();
/*    */         
/*    */         return;
/* 44 */       } catch (RetryCallException $$2) {
/* 45 */         if (aborted()) {
/*    */           return;
/*    */         }
/* 48 */         pause($$2.delaySeconds);
/* 49 */       } catch (Exception $$3) {
/* 50 */         if (aborted()) {
/*    */           return;
/*    */         }
/* 53 */         LOGGER.error("Couldn't reset world");
/* 54 */         error($$3);
/*    */         return;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Component getTitle() {
/* 62 */     return this.title;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclien\\util\task\ResettingWorldTask.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */