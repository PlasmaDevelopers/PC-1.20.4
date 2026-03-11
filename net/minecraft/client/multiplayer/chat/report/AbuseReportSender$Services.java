/*    */ package net.minecraft.client.multiplayer.chat.report;
/*    */ 
/*    */ import com.mojang.authlib.exceptions.MinecraftClientException;
/*    */ import com.mojang.authlib.exceptions.MinecraftClientHttpException;
/*    */ import com.mojang.authlib.minecraft.UserApiService;
/*    */ import com.mojang.authlib.minecraft.report.AbuseReport;
/*    */ import com.mojang.authlib.minecraft.report.AbuseReportLimits;
/*    */ import com.mojang.authlib.yggdrasil.request.AbuseReportRequest;
/*    */ import com.mojang.datafixers.util.Unit;
/*    */ import java.util.UUID;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import java.util.concurrent.CompletionException;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ public final class Services extends Record implements AbuseReportSender {
/*    */   private final ReportEnvironment environment;
/*    */   private final UserApiService userApiService;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/client/multiplayer/chat/report/AbuseReportSender$Services;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #32	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/client/multiplayer/chat/report/AbuseReportSender$Services;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/multiplayer/chat/report/AbuseReportSender$Services;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #32	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/client/multiplayer/chat/report/AbuseReportSender$Services;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/client/multiplayer/chat/report/AbuseReportSender$Services;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #32	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/client/multiplayer/chat/report/AbuseReportSender$Services;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */   }
/*    */   
/* 32 */   public Services(ReportEnvironment $$0, UserApiService $$1) { this.environment = $$0; this.userApiService = $$1; } public ReportEnvironment environment() { return this.environment; } public UserApiService userApiService() { return this.userApiService; }
/* 33 */    private static final Component SERVICE_UNAVAILABLE_TEXT = (Component)Component.translatable("gui.abuseReport.send.service_unavailable");
/* 34 */   private static final Component HTTP_ERROR_TEXT = (Component)Component.translatable("gui.abuseReport.send.http_error");
/* 35 */   private static final Component JSON_ERROR_TEXT = (Component)Component.translatable("gui.abuseReport.send.json_error");
/*    */ 
/*    */   
/*    */   public CompletableFuture<Unit> send(UUID $$0, ReportType $$1, AbuseReport $$2) {
/* 39 */     return CompletableFuture.supplyAsync(() -> {
/*    */           AbuseReportRequest $$3 = new AbuseReportRequest(1, $$0, $$1, this.environment.clientInfo(), this.environment.thirdPartyServerInfo(), this.environment.realmInfo(), $$2.backendName());
/*    */ 
/*    */ 
/*    */ 
/*    */           
/*    */           try {
/*    */             this.userApiService.reportAbuse($$3);
/*    */ 
/*    */ 
/*    */ 
/*    */             
/*    */             return Unit.INSTANCE;
/* 52 */           } catch (MinecraftClientHttpException $$4) {
/*    */             Component $$5 = getHttpErrorDescription($$4);
/*    */             throw new CompletionException(new AbuseReportSender.SendException($$5, $$4));
/* 55 */           } catch (MinecraftClientException $$6) {
/*    */             Component $$7 = getErrorDescription($$6);
/*    */             throw new CompletionException(new AbuseReportSender.SendException($$7, $$6));
/*    */           } 
/* 59 */         }Util.ioPool());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isEnabled() {
/* 64 */     return this.userApiService.canSendReports();
/*    */   }
/*    */   
/*    */   private Component getHttpErrorDescription(MinecraftClientHttpException $$0) {
/* 68 */     return (Component)Component.translatable("gui.abuseReport.send.error_message", new Object[] { $$0.getMessage() });
/*    */   }
/*    */   
/*    */   private Component getErrorDescription(MinecraftClientException $$0) {
/* 72 */     switch (AbuseReportSender.null.$SwitchMap$com$mojang$authlib$exceptions$MinecraftClientException$ErrorType[$$0.getType().ordinal()]) { default: throw new IncompatibleClassChangeError();case 1: case 2: case 3: break; }  return 
/*    */ 
/*    */       
/* 75 */       JSON_ERROR_TEXT;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public AbuseReportLimits reportLimits() {
/* 81 */     return this.userApiService.getAbuseReportLimits();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\multiplayer\chat\report\AbuseReportSender$Services.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */