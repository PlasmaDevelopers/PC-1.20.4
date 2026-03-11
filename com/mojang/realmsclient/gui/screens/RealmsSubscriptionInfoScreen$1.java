/*     */ package com.mojang.realmsclient.gui.screens;
/*     */ 
/*     */ import com.mojang.realmsclient.client.RealmsClient;
/*     */ import com.mojang.realmsclient.exception.RealmsServiceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class null
/*     */   extends Thread
/*     */ {
/*     */   null(String $$1) {
/*  92 */     super($$1);
/*     */   }
/*     */   public void run() {
/*     */     try {
/*  96 */       RealmsClient $$0 = RealmsClient.create();
/*  97 */       $$0.deleteWorld(RealmsSubscriptionInfoScreen.this.serverData.id);
/*  98 */     } catch (RealmsServiceException $$1) {
/*  99 */       RealmsSubscriptionInfoScreen.LOGGER.error("Couldn't delete world", (Throwable)$$1);
/*     */     } 
/*     */     
/* 102 */     RealmsSubscriptionInfoScreen.access$000(RealmsSubscriptionInfoScreen.this).execute(() -> RealmsSubscriptionInfoScreen.access$100(RealmsSubscriptionInfoScreen.this).setScreen(RealmsSubscriptionInfoScreen.this.mainScreen));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\gui\screens\RealmsSubscriptionInfoScreen$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */