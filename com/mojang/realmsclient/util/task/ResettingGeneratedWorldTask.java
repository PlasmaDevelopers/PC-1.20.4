/*    */ package com.mojang.realmsclient.util.task;
/*    */ 
/*    */ import com.mojang.realmsclient.client.RealmsClient;
/*    */ import com.mojang.realmsclient.exception.RealmsServiceException;
/*    */ import com.mojang.realmsclient.util.WorldGenerationInfo;
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ public class ResettingGeneratedWorldTask extends ResettingWorldTask {
/*    */   private final WorldGenerationInfo generationInfo;
/*    */   
/*    */   public ResettingGeneratedWorldTask(WorldGenerationInfo $$0, long $$1, Component $$2, Runnable $$3) {
/* 12 */     super($$1, $$2, $$3);
/* 13 */     this.generationInfo = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void sendResetRequest(RealmsClient $$0, long $$1) throws RealmsServiceException {
/* 18 */     $$0.resetWorldWithSeed($$1, this.generationInfo);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclien\\util\task\ResettingGeneratedWorldTask.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */