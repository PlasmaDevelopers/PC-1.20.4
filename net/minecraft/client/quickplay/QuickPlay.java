/*    */ package net.minecraft.client.quickplay;
/*    */ import com.mojang.realmsclient.RealmsMainScreen;
/*    */ import com.mojang.realmsclient.client.RealmsClient;
/*    */ import com.mojang.realmsclient.dto.RealmsServer;
/*    */ import com.mojang.realmsclient.dto.RealmsServerList;
/*    */ import com.mojang.realmsclient.exception.RealmsServiceException;
/*    */ import com.mojang.realmsclient.gui.screens.RealmsLongRunningMcoTaskScreen;
/*    */ import com.mojang.realmsclient.util.task.GetServerDetailsTask;
/*    */ import com.mojang.realmsclient.util.task.LongRunningTask;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.screens.ConnectScreen;
/*    */ import net.minecraft.client.gui.screens.DisconnectedScreen;
/*    */ import net.minecraft.client.gui.screens.Screen;
/*    */ import net.minecraft.client.gui.screens.TitleScreen;
/*    */ import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
/*    */ import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
/*    */ import net.minecraft.client.main.GameConfig;
/*    */ import net.minecraft.client.multiplayer.ServerData;
/*    */ import net.minecraft.client.multiplayer.ServerList;
/*    */ import net.minecraft.client.multiplayer.resolver.ServerAddress;
/*    */ import net.minecraft.client.resources.language.I18n;
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ public class QuickPlay {
/* 26 */   public static final Component ERROR_TITLE = (Component)Component.translatable("quickplay.error.title");
/* 27 */   private static final Component INVALID_IDENTIFIER = (Component)Component.translatable("quickplay.error.invalid_identifier");
/* 28 */   private static final Component REALM_CONNECT = (Component)Component.translatable("quickplay.error.realm_connect");
/* 29 */   private static final Component REALM_PERMISSION = (Component)Component.translatable("quickplay.error.realm_permission");
/*    */   
/* 31 */   private static final Component TO_TITLE = (Component)Component.translatable("gui.toTitle");
/* 32 */   private static final Component TO_WORLD_LIST = (Component)Component.translatable("gui.toWorld");
/* 33 */   private static final Component TO_REALMS_LIST = (Component)Component.translatable("gui.toRealms");
/*    */   
/*    */   public static void connect(Minecraft $$0, GameConfig.QuickPlayData $$1, RealmsClient $$2) {
/* 36 */     String $$3 = $$1.singleplayer();
/* 37 */     String $$4 = $$1.multiplayer();
/* 38 */     String $$5 = $$1.realms();
/*    */     
/* 40 */     if (!Util.isBlank($$3)) {
/* 41 */       joinSingleplayerWorld($$0, $$3);
/* 42 */     } else if (!Util.isBlank($$4)) {
/* 43 */       joinMultiplayerWorld($$0, $$4);
/* 44 */     } else if (!Util.isBlank($$5)) {
/* 45 */       joinRealmsWorld($$0, $$2, $$5);
/*    */     } 
/*    */   }
/*    */   
/*    */   private static void joinSingleplayerWorld(Minecraft $$0, String $$1) {
/* 50 */     if (!$$0.getLevelSource().levelExists($$1)) {
/* 51 */       SelectWorldScreen selectWorldScreen = new SelectWorldScreen((Screen)new TitleScreen());
/* 52 */       $$0.setScreen((Screen)new DisconnectedScreen((Screen)selectWorldScreen, ERROR_TITLE, INVALID_IDENTIFIER, TO_WORLD_LIST));
/*    */       return;
/*    */     } 
/* 55 */     $$0.createWorldOpenFlows().checkForBackupAndLoad($$1, () -> $$0.setScreen((Screen)new TitleScreen()));
/*    */   }
/*    */   
/*    */   private static void joinMultiplayerWorld(Minecraft $$0, String $$1) {
/* 59 */     ServerList $$2 = new ServerList($$0);
/* 60 */     $$2.load();
/* 61 */     ServerData $$3 = $$2.get($$1);
/* 62 */     if ($$3 == null) {
/* 63 */       $$3 = new ServerData(I18n.get("selectServer.defaultName", new Object[0]), $$1, ServerData.Type.OTHER);
/* 64 */       $$2.add($$3, true);
/* 65 */       $$2.save();
/*    */     } 
/*    */     
/* 68 */     ServerAddress $$4 = ServerAddress.parseString($$1);
/* 69 */     ConnectScreen.startConnecting((Screen)new JoinMultiplayerScreen((Screen)new TitleScreen()), $$0, $$4, $$3, true);
/*    */   }
/*    */   
/*    */   private static void joinRealmsWorld(Minecraft $$0, RealmsClient $$1, String $$2) {
/*    */     long $$3;
/*    */     RealmsServerList $$4;
/*    */     try {
/* 76 */       $$3 = Long.parseLong($$2);
/* 77 */       $$4 = $$1.listWorlds();
/* 78 */     } catch (NumberFormatException $$5) {
/* 79 */       RealmsMainScreen realmsMainScreen = new RealmsMainScreen((Screen)new TitleScreen());
/* 80 */       $$0.setScreen((Screen)new DisconnectedScreen((Screen)realmsMainScreen, ERROR_TITLE, INVALID_IDENTIFIER, TO_REALMS_LIST));
/*    */       return;
/* 82 */     } catch (RealmsServiceException $$7) {
/* 83 */       TitleScreen titleScreen = new TitleScreen();
/* 84 */       $$0.setScreen((Screen)new DisconnectedScreen((Screen)titleScreen, ERROR_TITLE, REALM_CONNECT, TO_TITLE));
/*    */       return;
/*    */     } 
/* 87 */     RealmsServer $$11 = $$4.servers.stream().filter($$1 -> ($$1.id == $$0)).findFirst().orElse(null);
/* 88 */     if ($$11 == null) {
/* 89 */       RealmsMainScreen realmsMainScreen = new RealmsMainScreen((Screen)new TitleScreen());
/* 90 */       $$0.setScreen((Screen)new DisconnectedScreen((Screen)realmsMainScreen, ERROR_TITLE, REALM_PERMISSION, TO_REALMS_LIST));
/*    */       
/*    */       return;
/*    */     } 
/* 94 */     TitleScreen $$13 = new TitleScreen();
/* 95 */     GetServerDetailsTask $$14 = new GetServerDetailsTask((Screen)$$13, $$11);
/* 96 */     $$0.setScreen((Screen)new RealmsLongRunningMcoTaskScreen((Screen)$$13, new LongRunningTask[] { (LongRunningTask)$$14 }));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\quickplay\QuickPlay.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */