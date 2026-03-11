/*    */ package com.mojang.realmsclient.gui;
/*    */ import com.mojang.realmsclient.RealmsMainScreen;
/*    */ import com.mojang.realmsclient.client.RealmsClient;
/*    */ import com.mojang.realmsclient.dto.RealmsNews;
/*    */ import com.mojang.realmsclient.dto.RealmsNotification;
/*    */ import com.mojang.realmsclient.dto.RealmsServer;
/*    */ import com.mojang.realmsclient.dto.RealmsServerList;
/*    */ import com.mojang.realmsclient.gui.task.DataFetcher;
/*    */ import com.mojang.realmsclient.gui.task.RepeatedDelayStrategy;
/*    */ import com.mojang.realmsclient.util.RealmsPersistence;
/*    */ import java.time.Duration;
/*    */ import java.util.List;
/*    */ import java.util.Objects;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.util.TimeSource;
/*    */ 
/*    */ public class RealmsDataFetcher {
/* 19 */   public final DataFetcher dataFetcher = new DataFetcher(Util.ioPool(), TimeUnit.MILLISECONDS, (TimeSource)Util.timeSource);
/*    */   
/*    */   private final List<DataFetcher.Task<?>> tasks;
/*    */   
/*    */   public final DataFetcher.Task<List<RealmsNotification>> notificationsTask;
/*    */   
/*    */   public final DataFetcher.Task<ServerListData> serverListUpdateTask;
/*    */   
/*    */   public final DataFetcher.Task<Integer> pendingInvitesTask;
/*    */   public final DataFetcher.Task<Boolean> trialAvailabilityTask;
/*    */   public final DataFetcher.Task<RealmsNews> newsTask;
/* 30 */   public final RealmsNewsManager newsManager = new RealmsNewsManager(new RealmsPersistence());
/*    */   
/*    */   public RealmsDataFetcher(RealmsClient $$0) {
/* 33 */     this.serverListUpdateTask = this.dataFetcher.createTask("server list", () -> {
/*    */           RealmsServerList $$1 = $$0.listWorlds();
/*    */ 
/*    */ 
/*    */           
/*    */           return RealmsMainScreen.isSnapshot() ? new ServerListData($$1.servers, $$0.listSnapshotEligibleRealms()) : new ServerListData($$1.servers, List.of());
/* 39 */         }Duration.ofSeconds(60L), RepeatedDelayStrategy.CONSTANT);
/* 40 */     Objects.requireNonNull($$0); this.pendingInvitesTask = this.dataFetcher.createTask("pending invite count", $$0::pendingInvitesCount, Duration.ofSeconds(10L), RepeatedDelayStrategy.exponentialBackoff(360));
/* 41 */     Objects.requireNonNull($$0); this.trialAvailabilityTask = this.dataFetcher.createTask("trial availablity", $$0::trialAvailable, Duration.ofSeconds(60L), RepeatedDelayStrategy.exponentialBackoff(60));
/* 42 */     Objects.requireNonNull($$0); this.newsTask = this.dataFetcher.createTask("unread news", $$0::getNews, Duration.ofMinutes(5L), RepeatedDelayStrategy.CONSTANT);
/* 43 */     Objects.requireNonNull($$0); this.notificationsTask = this.dataFetcher.createTask("notifications", $$0::getNotifications, Duration.ofMinutes(5L), RepeatedDelayStrategy.CONSTANT);
/* 44 */     this.tasks = List.of(this.notificationsTask, this.serverListUpdateTask, this.pendingInvitesTask, this.trialAvailabilityTask, this.newsTask);
/*    */   }
/*    */   
/*    */   public List<DataFetcher.Task<?>> getTasks() {
/* 48 */     return this.tasks;
/*    */   }
/*    */   public static final class ServerListData extends Record { private final List<RealmsServer> serverList; private final List<RealmsServer> availableSnapshotServers;
/* 51 */     public ServerListData(List<RealmsServer> $$0, List<RealmsServer> $$1) { this.serverList = $$0; this.availableSnapshotServers = $$1; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lcom/mojang/realmsclient/gui/RealmsDataFetcher$ServerListData;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #51	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/* 51 */       //   0	7	0	this	Lcom/mojang/realmsclient/gui/RealmsDataFetcher$ServerListData; } public List<RealmsServer> serverList() { return this.serverList; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lcom/mojang/realmsclient/gui/RealmsDataFetcher$ServerListData;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #51	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lcom/mojang/realmsclient/gui/RealmsDataFetcher$ServerListData; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lcom/mojang/realmsclient/gui/RealmsDataFetcher$ServerListData;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #51	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lcom/mojang/realmsclient/gui/RealmsDataFetcher$ServerListData;
/* 51 */       //   0	8	1	$$0	Ljava/lang/Object; } public List<RealmsServer> availableSnapshotServers() { return this.availableSnapshotServers; }
/*    */      }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\gui\RealmsDataFetcher.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */