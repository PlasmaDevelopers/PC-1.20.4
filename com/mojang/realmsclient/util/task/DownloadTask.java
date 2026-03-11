/*    */ package com.mojang.realmsclient.util.task;
/*    */ 
/*    */ import com.mojang.logging.LogUtils;
/*    */ import com.mojang.realmsclient.client.RealmsClient;
/*    */ import com.mojang.realmsclient.dto.WorldDownload;
/*    */ import com.mojang.realmsclient.exception.RealmsServiceException;
/*    */ import com.mojang.realmsclient.exception.RetryCallException;
/*    */ import com.mojang.realmsclient.gui.screens.RealmsDownloadLatestWorldScreen;
/*    */ import com.mojang.realmsclient.gui.screens.RealmsGenericErrorScreen;
/*    */ import net.minecraft.client.gui.screens.Screen;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class DownloadTask extends LongRunningTask {
/* 15 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/* 17 */   private static final Component TITLE = (Component)Component.translatable("mco.download.preparing");
/*    */   
/*    */   private final long worldId;
/*    */   private final int slot;
/*    */   private final Screen lastScreen;
/*    */   private final String downloadName;
/*    */   
/*    */   public DownloadTask(long $$0, int $$1, String $$2, Screen $$3) {
/* 25 */     this.worldId = $$0;
/* 26 */     this.slot = $$1;
/* 27 */     this.lastScreen = $$3;
/* 28 */     this.downloadName = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/* 33 */     RealmsClient $$0 = RealmsClient.create();
/*    */     
/* 35 */     for (int $$1 = 0; $$1 < 25; $$1++) {
/*    */       try {
/* 37 */         if (aborted()) {
/*    */           return;
/*    */         }
/* 40 */         WorldDownload $$2 = $$0.requestDownloadInfo(this.worldId, this.slot);
/* 41 */         pause(1L);
/* 42 */         if (aborted()) {
/*    */           return;
/*    */         }
/* 45 */         setScreen((Screen)new RealmsDownloadLatestWorldScreen(this.lastScreen, $$2, this.downloadName, $$0 -> { 
/*    */               })); return;
/* 47 */       } catch (RetryCallException $$3) {
/* 48 */         if (aborted()) {
/*    */           return;
/*    */         }
/* 51 */         pause($$3.delaySeconds);
/* 52 */       } catch (RealmsServiceException $$4) {
/* 53 */         if (aborted()) {
/*    */           return;
/*    */         }
/* 56 */         LOGGER.error("Couldn't download world data", (Throwable)$$4);
/* 57 */         setScreen((Screen)new RealmsGenericErrorScreen($$4, this.lastScreen));
/*    */         return;
/* 59 */       } catch (Exception $$5) {
/* 60 */         if (aborted()) {
/*    */           return;
/*    */         }
/* 63 */         LOGGER.error("Couldn't download world data", $$5);
/* 64 */         error($$5);
/*    */         return;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Component getTitle() {
/* 72 */     return TITLE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclien\\util\task\DownloadTask.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */