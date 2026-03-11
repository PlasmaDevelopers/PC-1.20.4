/*    */ package com.mojang.realmsclient.util.task;
/*    */ 
/*    */ import com.mojang.logging.LogUtils;
/*    */ import com.mojang.realmsclient.RealmsMainScreen;
/*    */ import com.mojang.realmsclient.client.RealmsClient;
/*    */ import com.mojang.realmsclient.dto.RealmsServer;
/*    */ import com.mojang.realmsclient.exception.RetryCallException;
/*    */ import com.mojang.realmsclient.gui.screens.RealmsConfigureWorldScreen;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.screens.Screen;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class OpenServerTask extends LongRunningTask {
/* 15 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/* 17 */   private static final Component TITLE = (Component)Component.translatable("mco.configure.world.opening");
/*    */   
/*    */   private final RealmsServer serverData;
/*    */   private final Screen returnScreen;
/*    */   private final boolean join;
/*    */   private final Minecraft minecraft;
/*    */   
/*    */   public OpenServerTask(RealmsServer $$0, Screen $$1, boolean $$2, Minecraft $$3) {
/* 25 */     this.serverData = $$0;
/* 26 */     this.returnScreen = $$1;
/* 27 */     this.join = $$2;
/* 28 */     this.minecraft = $$3;
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/* 33 */     RealmsClient $$0 = RealmsClient.create();
/* 34 */     for (int $$1 = 0; $$1 < 25; $$1++) {
/* 35 */       if (aborted()) {
/*    */         return;
/*    */       }
/*    */       
/*    */       try {
/* 40 */         boolean $$2 = $$0.open(this.serverData.id).booleanValue();
/* 41 */         if ($$2) {
/* 42 */           this.minecraft.execute(() -> {
/*    */                 if (this.returnScreen instanceof RealmsConfigureWorldScreen) {
/*    */                   ((RealmsConfigureWorldScreen)this.returnScreen).stateChanged();
/*    */                 }
/*    */                 
/*    */                 this.serverData.state = RealmsServer.State.OPEN;
/*    */                 
/*    */                 if (this.join) {
/*    */                   RealmsMainScreen.play(this.serverData, this.returnScreen);
/*    */                 } else {
/*    */                   this.minecraft.setScreen(this.returnScreen);
/*    */                 } 
/*    */               });
/*    */           break;
/*    */         } 
/* 57 */       } catch (RetryCallException $$3) {
/* 58 */         if (aborted()) {
/*    */           return;
/*    */         }
/* 61 */         pause($$3.delaySeconds);
/* 62 */       } catch (Exception $$4) {
/* 63 */         if (aborted()) {
/*    */           return;
/*    */         }
/* 66 */         LOGGER.error("Failed to open server", $$4);
/* 67 */         error($$4);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Component getTitle() {
/* 74 */     return TITLE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclien\\util\task\OpenServerTask.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */