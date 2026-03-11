/*    */ package com.mojang.realmsclient.util.task;
/*    */ import com.mojang.logging.LogUtils;
/*    */ import com.mojang.realmsclient.client.RealmsClient;
/*    */ import com.mojang.realmsclient.dto.RealmsServer;
/*    */ import com.mojang.realmsclient.exception.RetryCallException;
/*    */ import com.mojang.realmsclient.gui.screens.RealmsConfigureWorldScreen;
/*    */ import net.minecraft.client.gui.screens.Screen;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class CloseServerTask extends LongRunningTask {
/* 12 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/* 14 */   private static final Component TITLE = (Component)Component.translatable("mco.configure.world.closing");
/*    */   
/*    */   private final RealmsServer serverData;
/*    */   private final RealmsConfigureWorldScreen configureScreen;
/*    */   
/*    */   public CloseServerTask(RealmsServer $$0, RealmsConfigureWorldScreen $$1) {
/* 20 */     this.serverData = $$0;
/* 21 */     this.configureScreen = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/* 26 */     RealmsClient $$0 = RealmsClient.create();
/* 27 */     for (int $$1 = 0; $$1 < 25; $$1++) {
/* 28 */       if (aborted()) {
/*    */         return;
/*    */       }
/*    */       
/*    */       try {
/* 33 */         boolean $$2 = $$0.close(this.serverData.id).booleanValue();
/* 34 */         if ($$2) {
/* 35 */           this.configureScreen.stateChanged();
/* 36 */           this.serverData.state = RealmsServer.State.CLOSED;
/* 37 */           setScreen((Screen)this.configureScreen);
/*    */           break;
/*    */         } 
/* 40 */       } catch (RetryCallException $$3) {
/* 41 */         if (aborted()) {
/*    */           return;
/*    */         }
/* 44 */         pause($$3.delaySeconds);
/* 45 */       } catch (Exception $$4) {
/* 46 */         if (aborted()) {
/*    */           return;
/*    */         }
/* 49 */         LOGGER.error("Failed to close server", $$4);
/* 50 */         error($$4);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Component getTitle() {
/* 57 */     return TITLE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclien\\util\task\CloseServerTask.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */