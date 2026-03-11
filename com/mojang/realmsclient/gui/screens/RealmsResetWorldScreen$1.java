/*     */ package com.mojang.realmsclient.gui.screens;
/*     */ 
/*     */ import com.mojang.realmsclient.client.RealmsClient;
/*     */ import com.mojang.realmsclient.dto.RealmsServer;
/*     */ import com.mojang.realmsclient.dto.WorldTemplatePaginatedList;
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
/* 117 */     super($$1);
/*     */   }
/*     */   public void run() {
/* 120 */     RealmsClient $$0 = RealmsClient.create();
/*     */     try {
/* 122 */       WorldTemplatePaginatedList $$1 = $$0.fetchWorldTemplates(1, 10, RealmsServer.WorldType.NORMAL);
/* 123 */       WorldTemplatePaginatedList $$2 = $$0.fetchWorldTemplates(1, 10, RealmsServer.WorldType.ADVENTUREMAP);
/* 124 */       WorldTemplatePaginatedList $$3 = $$0.fetchWorldTemplates(1, 10, RealmsServer.WorldType.EXPERIENCE);
/* 125 */       WorldTemplatePaginatedList $$4 = $$0.fetchWorldTemplates(1, 10, RealmsServer.WorldType.INSPIRATION);
/* 126 */       RealmsResetWorldScreen.access$000(RealmsResetWorldScreen.this).execute(() -> {
/*     */             RealmsResetWorldScreen.this.templates = $$0;
/*     */             RealmsResetWorldScreen.this.adventuremaps = $$1;
/*     */             RealmsResetWorldScreen.this.experiences = $$2;
/*     */             RealmsResetWorldScreen.this.inspirations = $$3;
/*     */           });
/* 132 */     } catch (RealmsServiceException $$5) {
/* 133 */       RealmsResetWorldScreen.LOGGER.error("Couldn't fetch templates in reset world", (Throwable)$$5);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\gui\screens\RealmsResetWorldScreen$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */