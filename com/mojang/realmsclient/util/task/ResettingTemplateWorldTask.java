/*    */ package com.mojang.realmsclient.util.task;
/*    */ 
/*    */ import com.mojang.realmsclient.client.RealmsClient;
/*    */ import com.mojang.realmsclient.dto.WorldTemplate;
/*    */ import com.mojang.realmsclient.exception.RealmsServiceException;
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ public class ResettingTemplateWorldTask extends ResettingWorldTask {
/*    */   private final WorldTemplate template;
/*    */   
/*    */   public ResettingTemplateWorldTask(WorldTemplate $$0, long $$1, Component $$2, Runnable $$3) {
/* 12 */     super($$1, $$2, $$3);
/* 13 */     this.template = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void sendResetRequest(RealmsClient $$0, long $$1) throws RealmsServiceException {
/* 18 */     $$0.resetWorldWithTemplate($$1, this.template.id);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclien\\util\task\ResettingTemplateWorldTask.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */