/*    */ package net.minecraft.client.multiplayer.chat.report;
/*    */ 
/*    */ import com.mojang.authlib.minecraft.UserApiService;
/*    */ import java.util.Objects;
/*    */ import java.util.UUID;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.screens.ConfirmScreen;
/*    */ import net.minecraft.client.gui.screens.Screen;
/*    */ import net.minecraft.client.multiplayer.chat.ChatLog;
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ 
/*    */ public final class ReportingContext
/*    */ {
/*    */   private static final int LOG_CAPACITY = 1024;
/*    */   private final AbuseReportSender sender;
/*    */   private final ReportEnvironment environment;
/*    */   private final ChatLog chatLog;
/*    */   @Nullable
/*    */   private Report draftReport;
/*    */   
/*    */   public ReportingContext(AbuseReportSender $$0, ReportEnvironment $$1, ChatLog $$2) {
/* 24 */     this.sender = $$0;
/* 25 */     this.environment = $$1;
/* 26 */     this.chatLog = $$2;
/*    */   }
/*    */   
/*    */   public static ReportingContext create(ReportEnvironment $$0, UserApiService $$1) {
/* 30 */     ChatLog $$2 = new ChatLog(1024);
/* 31 */     AbuseReportSender $$3 = AbuseReportSender.create($$0, $$1);
/* 32 */     return new ReportingContext($$3, $$0, $$2);
/*    */   }
/*    */   
/*    */   public void draftReportHandled(Minecraft $$0, Screen $$1, Runnable $$2, boolean $$3) {
/* 36 */     if (this.draftReport != null) {
/* 37 */       Report $$4 = this.draftReport.copy();
/* 38 */       $$0.setScreen((Screen)new ConfirmScreen($$4 -> {
/*    */               setReportDraft(null);
/*    */               
/*    */               if ($$4) {
/*    */                 $$0.setScreen($$1.createScreen($$2, this));
/*    */               } else {
/*    */                 $$3.run();
/*    */               } 
/* 46 */             }(Component)Component.translatable($$3 ? "gui.abuseReport.draft.quittotitle.title" : "gui.abuseReport.draft.title"), 
/* 47 */             (Component)Component.translatable($$3 ? "gui.abuseReport.draft.quittotitle.content" : "gui.abuseReport.draft.content"), 
/* 48 */             (Component)Component.translatable("gui.abuseReport.draft.edit"), 
/* 49 */             (Component)Component.translatable("gui.abuseReport.draft.discard")));
/*    */     } else {
/*    */       
/* 52 */       $$2.run();
/*    */     } 
/*    */   }
/*    */   
/*    */   public AbuseReportSender sender() {
/* 57 */     return this.sender;
/*    */   }
/*    */   
/*    */   public ChatLog chatLog() {
/* 61 */     return this.chatLog;
/*    */   }
/*    */   
/*    */   public boolean matches(ReportEnvironment $$0) {
/* 65 */     return Objects.equals(this.environment, $$0);
/*    */   }
/*    */   
/*    */   public void setReportDraft(@Nullable Report $$0) {
/* 69 */     this.draftReport = $$0;
/*    */   }
/*    */   
/*    */   public boolean hasDraftReport() {
/* 73 */     return (this.draftReport != null);
/*    */   }
/*    */   
/*    */   public boolean hasDraftReportFor(UUID $$0) {
/* 77 */     return (hasDraftReport() && this.draftReport.isReportedPlayer($$0));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\multiplayer\chat\report\ReportingContext.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */