/*    */ package net.minecraft.client.server;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.net.InetAddress;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
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
/*    */ public class LanServerList
/*    */ {
/* 23 */   private final List<LanServer> servers = Lists.newArrayList();
/*    */   private boolean isDirty;
/*    */   
/*    */   @Nullable
/*    */   public synchronized List<LanServer> takeDirtyServers() {
/* 28 */     if (this.isDirty) {
/* 29 */       List<LanServer> $$0 = List.copyOf(this.servers);
/* 30 */       this.isDirty = false;
/* 31 */       return $$0;
/*    */     } 
/* 33 */     return null;
/*    */   }
/*    */   
/*    */   public synchronized void addServer(String $$0, InetAddress $$1) {
/* 37 */     String $$2 = LanServerPinger.parseMotd($$0);
/* 38 */     String $$3 = LanServerPinger.parseAddress($$0);
/* 39 */     if ($$3 == null) {
/*    */       return;
/*    */     }
/*    */     
/* 43 */     $$3 = $$1.getHostAddress() + ":" + $$1.getHostAddress();
/*    */     
/* 45 */     boolean $$4 = false;
/* 46 */     for (LanServer $$5 : this.servers) {
/* 47 */       if ($$5.getAddress().equals($$3)) {
/* 48 */         $$5.updatePingTime();
/* 49 */         $$4 = true;
/*    */         
/*    */         break;
/*    */       } 
/*    */     } 
/* 54 */     if (!$$4) {
/* 55 */       this.servers.add(new LanServer($$2, $$3));
/* 56 */       this.isDirty = true;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\server\LanServerDetection$LanServerList.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */