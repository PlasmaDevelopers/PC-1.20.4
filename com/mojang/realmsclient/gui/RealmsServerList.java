/*    */ package com.mojang.realmsclient.gui;
/*    */ 
/*    */ import com.mojang.realmsclient.dto.RealmsServer;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Comparator;
/*    */ import java.util.HashSet;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import net.minecraft.client.Minecraft;
/*    */ 
/*    */ public class RealmsServerList
/*    */   implements Iterable<RealmsServer>
/*    */ {
/*    */   private final Minecraft minecraft;
/* 16 */   private final Set<RealmsServer> removedServers = new HashSet<>();
/* 17 */   private List<RealmsServer> servers = List.of();
/*    */   
/*    */   public RealmsServerList(Minecraft $$0) {
/* 20 */     this.minecraft = $$0;
/*    */   }
/*    */   
/*    */   public void updateServersList(List<RealmsServer> $$0) {
/* 24 */     List<RealmsServer> $$1 = new ArrayList<>($$0);
/* 25 */     $$1.sort((Comparator<? super RealmsServer>)new RealmsServer.McoServerComparator(this.minecraft.getUser().getName()));
/*    */     
/* 27 */     boolean $$2 = $$1.removeAll(this.removedServers);
/* 28 */     if (!$$2)
/*    */     {
/* 30 */       this.removedServers.clear();
/*    */     }
/*    */     
/* 33 */     this.servers = $$1;
/*    */   }
/*    */   
/*    */   public void removeItem(RealmsServer $$0) {
/* 37 */     this.servers.remove($$0);
/* 38 */     this.removedServers.add($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public Iterator<RealmsServer> iterator() {
/* 43 */     return this.servers.iterator();
/*    */   }
/*    */   
/*    */   public boolean isEmpty() {
/* 47 */     return this.servers.isEmpty();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\gui\RealmsServerList.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */