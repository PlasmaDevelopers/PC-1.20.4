/*    */ package com.mojang.realmsclient.util.task;
/*    */ 
/*    */ import com.mojang.logging.LogUtils;
/*    */ import com.mojang.realmsclient.client.RealmsClient;
/*    */ import com.mojang.realmsclient.exception.RealmsServiceException;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class WorldCreationTask extends LongRunningTask {
/* 10 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/* 12 */   private static final Component TITLE = (Component)Component.translatable("mco.create.world.wait");
/*    */   
/*    */   private final String name;
/*    */   private final String motd;
/*    */   private final long worldId;
/*    */   
/*    */   public WorldCreationTask(long $$0, String $$1, String $$2) {
/* 19 */     this.worldId = $$0;
/* 20 */     this.name = $$1;
/* 21 */     this.motd = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/* 26 */     RealmsClient $$0 = RealmsClient.create();
/*    */     
/*    */     try {
/* 29 */       $$0.initializeWorld(this.worldId, this.name, this.motd);
/* 30 */     } catch (RealmsServiceException $$1) {
/* 31 */       LOGGER.error("Couldn't create world", (Throwable)$$1);
/* 32 */       error($$1);
/* 33 */     } catch (Exception $$2) {
/* 34 */       LOGGER.error("Could not create world", $$2);
/* 35 */       error($$2);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Component getTitle() {
/* 41 */     return TITLE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclien\\util\task\WorldCreationTask.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */