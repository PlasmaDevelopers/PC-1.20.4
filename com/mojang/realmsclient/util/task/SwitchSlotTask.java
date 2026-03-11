/*    */ package com.mojang.realmsclient.util.task;
/*    */ 
/*    */ import com.mojang.logging.LogUtils;
/*    */ import com.mojang.realmsclient.client.RealmsClient;
/*    */ import com.mojang.realmsclient.exception.RetryCallException;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class SwitchSlotTask extends LongRunningTask {
/* 10 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/* 12 */   private static final Component TITLE = (Component)Component.translatable("mco.minigame.world.slot.screen.title");
/*    */   
/*    */   private final long worldId;
/*    */   private final int slot;
/*    */   private final Runnable callback;
/*    */   
/*    */   public SwitchSlotTask(long $$0, int $$1, Runnable $$2) {
/* 19 */     this.worldId = $$0;
/* 20 */     this.slot = $$1;
/* 21 */     this.callback = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/* 26 */     RealmsClient $$0 = RealmsClient.create();
/* 27 */     for (int $$1 = 0; $$1 < 25; $$1++) {
/*    */       try {
/* 29 */         if (aborted()) {
/*    */           return;
/*    */         }
/*    */         
/* 33 */         if ($$0.switchSlot(this.worldId, this.slot)) {
/* 34 */           this.callback.run();
/*    */           break;
/*    */         } 
/* 37 */       } catch (RetryCallException $$2) {
/* 38 */         if (aborted()) {
/*    */           return;
/*    */         }
/* 41 */         pause($$2.delaySeconds);
/* 42 */       } catch (Exception $$3) {
/* 43 */         if (aborted()) {
/*    */           return;
/*    */         }
/* 46 */         LOGGER.error("Couldn't switch world!");
/* 47 */         error($$3);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Component getTitle() {
/* 54 */     return TITLE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclien\\util\task\SwitchSlotTask.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */