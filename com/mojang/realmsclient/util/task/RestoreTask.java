/*    */ package com.mojang.realmsclient.util.task;
/*    */ import com.mojang.logging.LogUtils;
/*    */ import com.mojang.realmsclient.client.RealmsClient;
/*    */ import com.mojang.realmsclient.dto.Backup;
/*    */ import com.mojang.realmsclient.exception.RealmsServiceException;
/*    */ import com.mojang.realmsclient.exception.RetryCallException;
/*    */ import com.mojang.realmsclient.gui.screens.RealmsConfigureWorldScreen;
/*    */ import com.mojang.realmsclient.gui.screens.RealmsGenericErrorScreen;
/*    */ import net.minecraft.client.gui.screens.Screen;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class RestoreTask extends LongRunningTask {
/* 14 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/* 16 */   private static final Component TITLE = (Component)Component.translatable("mco.backup.restoring");
/*    */   
/*    */   private final Backup backup;
/*    */   private final long worldId;
/*    */   private final RealmsConfigureWorldScreen lastScreen;
/*    */   
/*    */   public RestoreTask(Backup $$0, long $$1, RealmsConfigureWorldScreen $$2) {
/* 23 */     this.backup = $$0;
/* 24 */     this.worldId = $$1;
/* 25 */     this.lastScreen = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/* 30 */     RealmsClient $$0 = RealmsClient.create();
/* 31 */     for (int $$1 = 0; $$1 < 25; $$1++) {
/*    */       try {
/* 33 */         if (aborted()) {
/*    */           return;
/*    */         }
/* 36 */         $$0.restoreWorld(this.worldId, this.backup.backupId);
/* 37 */         pause(1L);
/* 38 */         if (aborted()) {
/*    */           return;
/*    */         }
/* 41 */         setScreen((Screen)this.lastScreen.getNewScreen());
/*    */         return;
/* 43 */       } catch (RetryCallException $$2) {
/* 44 */         if (aborted()) {
/*    */           return;
/*    */         }
/* 47 */         pause($$2.delaySeconds);
/* 48 */       } catch (RealmsServiceException $$3) {
/* 49 */         if (aborted()) {
/*    */           return;
/*    */         }
/* 52 */         LOGGER.error("Couldn't restore backup", (Throwable)$$3);
/* 53 */         setScreen((Screen)new RealmsGenericErrorScreen($$3, (Screen)this.lastScreen));
/*    */         return;
/* 55 */       } catch (Exception $$4) {
/* 56 */         if (aborted()) {
/*    */           return;
/*    */         }
/* 59 */         LOGGER.error("Couldn't restore backup", $$4);
/* 60 */         error($$4);
/*    */         return;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Component getTitle() {
/* 69 */     return TITLE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclien\\util\task\RestoreTask.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */