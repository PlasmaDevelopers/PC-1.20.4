/*    */ package com.mojang.realmsclient.util.task;
/*    */ 
/*    */ import com.mojang.realmsclient.dto.RealmsServer;
/*    */ import com.mojang.realmsclient.dto.RealmsServerAddress;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.screens.Screen;
/*    */ import net.minecraft.client.multiplayer.resolver.ServerAddress;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.realms.RealmsConnect;
/*    */ 
/*    */ public class ConnectTask extends LongRunningTask {
/* 12 */   private static final Component TITLE = (Component)Component.translatable("mco.connect.connecting");
/*    */   
/*    */   private final RealmsConnect realmsConnect;
/*    */   private final RealmsServer server;
/*    */   private final RealmsServerAddress address;
/*    */   
/*    */   public ConnectTask(Screen $$0, RealmsServer $$1, RealmsServerAddress $$2) {
/* 19 */     this.server = $$1;
/* 20 */     this.address = $$2;
/* 21 */     this.realmsConnect = new RealmsConnect($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/* 26 */     this.realmsConnect.connect(this.server, ServerAddress.parseString(this.address.address));
/*    */   }
/*    */ 
/*    */   
/*    */   public void abortTask() {
/* 31 */     super.abortTask();
/* 32 */     this.realmsConnect.abort();
/* 33 */     Minecraft.getInstance().getDownloadedPackSource().cleanupAfterDisconnect();
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 38 */     this.realmsConnect.tick();
/*    */   }
/*    */ 
/*    */   
/*    */   public Component getTitle() {
/* 43 */     return TITLE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclien\\util\task\ConnectTask.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */