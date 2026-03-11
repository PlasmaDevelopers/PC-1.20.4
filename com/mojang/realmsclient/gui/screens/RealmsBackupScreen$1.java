/*    */ package com.mojang.realmsclient.gui.screens;
/*    */ 
/*    */ import com.mojang.realmsclient.client.RealmsClient;
/*    */ import com.mojang.realmsclient.dto.Backup;
/*    */ import com.mojang.realmsclient.exception.RealmsServiceException;
/*    */ import java.util.List;
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
/*    */ class null
/*    */   extends Thread
/*    */ {
/*    */   null(String $$1) {
/* 72 */     super($$1);
/*    */   }
/*    */   public void run() {
/* 75 */     RealmsClient $$0 = RealmsClient.create();
/*    */     try {
/* 77 */       List<Backup> $$1 = ($$0.backupsFor(RealmsBackupScreen.this.serverData.id)).backups;
/* 78 */       RealmsBackupScreen.access$000(RealmsBackupScreen.this).execute(() -> {
/*    */             RealmsBackupScreen.this.backups = $$0;
/*    */             RealmsBackupScreen.this.noBackups = Boolean.valueOf(RealmsBackupScreen.this.backups.isEmpty());
/*    */             RealmsBackupScreen.this.backupObjectSelectionList.clear();
/*    */             for (Backup $$1 : RealmsBackupScreen.this.backups) {
/*    */               RealmsBackupScreen.this.backupObjectSelectionList.addEntry($$1);
/*    */             }
/*    */           });
/* 86 */     } catch (RealmsServiceException $$2) {
/* 87 */       RealmsBackupScreen.LOGGER.error("Couldn't request backups", (Throwable)$$2);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\gui\screens\RealmsBackupScreen$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */