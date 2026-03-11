/*    */ package com.mojang.realmsclient.util.task;
/*    */ 
/*    */ import com.mojang.logging.LogUtils;
/*    */ import com.mojang.realmsclient.RealmsMainScreen;
/*    */ import com.mojang.realmsclient.client.RealmsClient;
/*    */ import com.mojang.realmsclient.dto.RealmsServer;
/*    */ import com.mojang.realmsclient.exception.RealmsServiceException;
/*    */ import com.mojang.realmsclient.gui.screens.RealmsResetWorldScreen;
/*    */ import com.mojang.realmsclient.util.WorldGenerationInfo;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.screens.Screen;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class CreateSnapshotRealmTask extends LongRunningTask {
/* 17 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/* 19 */   private static final Component TITLE = (Component)Component.translatable("mco.snapshot.creating");
/*    */   
/*    */   private final long parentId;
/*    */   private final WorldGenerationInfo generationInfo;
/*    */   private final String name;
/*    */   private final String description;
/*    */   private final RealmsMainScreen realmsMainScreen;
/*    */   @Nullable
/*    */   private WorldCreationTask creationTask;
/*    */   @Nullable
/*    */   private ResettingGeneratedWorldTask generateWorldTask;
/*    */   
/*    */   public CreateSnapshotRealmTask(RealmsMainScreen $$0, long $$1, WorldGenerationInfo $$2, String $$3, String $$4) {
/* 32 */     this.parentId = $$1;
/* 33 */     this.generationInfo = $$2;
/* 34 */     this.name = $$3;
/* 35 */     this.description = $$4;
/* 36 */     this.realmsMainScreen = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/* 41 */     RealmsClient $$0 = RealmsClient.create();
/*    */     try {
/* 43 */       RealmsServer $$1 = $$0.createSnapshotRealm(Long.valueOf(this.parentId));
/*    */ 
/*    */       
/* 46 */       this.creationTask = new WorldCreationTask($$1.id, this.name, this.description);
/* 47 */       this.generateWorldTask = new ResettingGeneratedWorldTask(this.generationInfo, $$1.id, RealmsResetWorldScreen.CREATE_WORLD_RESET_TASK_TITLE, () -> Minecraft.getInstance().execute(()));
/*    */ 
/*    */       
/* 50 */       if (aborted()) {
/*    */         return;
/*    */       }
/* 53 */       this.creationTask.run();
/* 54 */       if (aborted()) {
/*    */         return;
/*    */       }
/* 57 */       this.generateWorldTask.run();
/* 58 */     } catch (RealmsServiceException $$2) {
/* 59 */       LOGGER.error("Couldn't create snapshot world", (Throwable)$$2);
/* 60 */       error($$2);
/* 61 */     } catch (Exception $$3) {
/* 62 */       LOGGER.error("Couldn't create snapshot world", $$3);
/* 63 */       error($$3);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Component getTitle() {
/* 69 */     return TITLE;
/*    */   }
/*    */ 
/*    */   
/*    */   public void abortTask() {
/* 74 */     super.abortTask();
/* 75 */     if (this.creationTask != null) {
/* 76 */       this.creationTask.abortTask();
/*    */     }
/* 78 */     if (this.generateWorldTask != null)
/* 79 */       this.generateWorldTask.abortTask(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclien\\util\task\CreateSnapshotRealmTask.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */