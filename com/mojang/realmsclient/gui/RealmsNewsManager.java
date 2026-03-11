/*    */ package com.mojang.realmsclient.gui;
/*    */ 
/*    */ import com.mojang.realmsclient.dto.RealmsNews;
/*    */ import com.mojang.realmsclient.util.RealmsPersistence;
/*    */ 
/*    */ public class RealmsNewsManager
/*    */ {
/*    */   private final RealmsPersistence newsLocalStorage;
/*    */   private boolean hasUnreadNews;
/*    */   private String newsLink;
/*    */   
/*    */   public RealmsNewsManager(RealmsPersistence $$0) {
/* 13 */     this.newsLocalStorage = $$0;
/* 14 */     RealmsPersistence.RealmsPersistenceData $$1 = $$0.read();
/* 15 */     this.hasUnreadNews = $$1.hasUnreadNews;
/* 16 */     this.newsLink = $$1.newsLink;
/*    */   }
/*    */   
/*    */   public boolean hasUnreadNews() {
/* 20 */     return this.hasUnreadNews;
/*    */   }
/*    */   
/*    */   public String newsLink() {
/* 24 */     return this.newsLink;
/*    */   }
/*    */   
/*    */   public void updateUnreadNews(RealmsNews $$0) {
/* 28 */     RealmsPersistence.RealmsPersistenceData $$1 = updateNewsStorage($$0);
/* 29 */     this.hasUnreadNews = $$1.hasUnreadNews;
/* 30 */     this.newsLink = $$1.newsLink;
/*    */   }
/*    */   
/*    */   private RealmsPersistence.RealmsPersistenceData updateNewsStorage(RealmsNews $$0) {
/* 34 */     RealmsPersistence.RealmsPersistenceData $$1 = new RealmsPersistence.RealmsPersistenceData();
/* 35 */     $$1.newsLink = $$0.newsLink;
/*    */     
/* 37 */     RealmsPersistence.RealmsPersistenceData $$2 = this.newsLocalStorage.read();
/* 38 */     boolean $$3 = ($$1.newsLink == null || $$1.newsLink.equals($$2.newsLink));
/* 39 */     if ($$3) {
/* 40 */       return $$2;
/*    */     }
/*    */     
/* 43 */     $$1.hasUnreadNews = true;
/* 44 */     this.newsLocalStorage.save($$1);
/* 45 */     return $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\gui\RealmsNewsManager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */