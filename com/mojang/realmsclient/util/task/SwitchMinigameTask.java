/*    */ package com.mojang.realmsclient.util.task;
/*    */ import com.mojang.logging.LogUtils;
/*    */ import com.mojang.realmsclient.client.RealmsClient;
/*    */ import com.mojang.realmsclient.dto.WorldTemplate;
/*    */ import com.mojang.realmsclient.exception.RetryCallException;
/*    */ import com.mojang.realmsclient.gui.screens.RealmsConfigureWorldScreen;
/*    */ import net.minecraft.client.gui.screens.Screen;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class SwitchMinigameTask extends LongRunningTask {
/* 12 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/* 14 */   private static final Component TITLE = (Component)Component.translatable("mco.minigame.world.starting.screen.title");
/*    */   
/*    */   private final long worldId;
/*    */   private final WorldTemplate worldTemplate;
/*    */   private final RealmsConfigureWorldScreen lastScreen;
/*    */   
/*    */   public SwitchMinigameTask(long $$0, WorldTemplate $$1, RealmsConfigureWorldScreen $$2) {
/* 21 */     this.worldId = $$0;
/* 22 */     this.worldTemplate = $$1;
/* 23 */     this.lastScreen = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/* 28 */     RealmsClient $$0 = RealmsClient.create();
/* 29 */     for (int $$1 = 0; $$1 < 25; $$1++) {
/*    */       try {
/* 31 */         if (aborted()) {
/*    */           return;
/*    */         }
/*    */         
/* 35 */         if ($$0.putIntoMinigameMode(this.worldId, this.worldTemplate.id).booleanValue()) {
/* 36 */           setScreen((Screen)this.lastScreen);
/*    */           break;
/*    */         } 
/* 39 */       } catch (RetryCallException $$2) {
/* 40 */         if (aborted()) {
/*    */           return;
/*    */         }
/* 43 */         pause($$2.delaySeconds);
/* 44 */       } catch (Exception $$3) {
/* 45 */         if (aborted()) {
/*    */           return;
/*    */         }
/* 48 */         LOGGER.error("Couldn't start mini game!");
/* 49 */         error($$3);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Component getTitle() {
/* 56 */     return TITLE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclien\\util\task\SwitchMinigameTask.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */