/*    */ package net.minecraft.client.multiplayer.chat.report;
/*    */ 
/*    */ import com.mojang.authlib.minecraft.report.AbuseReport;
/*    */ import com.mojang.authlib.minecraft.report.AbuseReportLimits;
/*    */ import com.mojang.datafixers.util.Either;
/*    */ import java.time.Instant;
/*    */ import java.util.UUID;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.gui.components.Tooltip;
/*    */ import net.minecraft.client.gui.screens.Screen;
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ 
/*    */ public abstract class Report
/*    */ {
/*    */   protected final UUID reportId;
/*    */   protected final Instant createdAt;
/*    */   protected final UUID reportedProfileId;
/* 19 */   protected String comments = "";
/*    */   @Nullable
/*    */   protected ReportReason reason;
/*    */   
/*    */   public Report(UUID $$0, Instant $$1, UUID $$2) {
/* 24 */     this.reportId = $$0;
/* 25 */     this.createdAt = $$1;
/* 26 */     this.reportedProfileId = $$2;
/*    */   }
/*    */   
/*    */   public boolean isReportedPlayer(UUID $$0) {
/* 30 */     return $$0.equals(this.reportedProfileId);
/*    */   }
/*    */   
/*    */   public abstract Report copy();
/*    */   
/*    */   public abstract Screen createScreen(Screen paramScreen, ReportingContext paramReportingContext);
/*    */   
/*    */   public static abstract class Builder<R extends Report> {
/*    */     protected final R report;
/*    */     protected final AbuseReportLimits limits;
/*    */     
/*    */     protected Builder(R $$0, AbuseReportLimits $$1) {
/* 42 */       this.report = $$0;
/* 43 */       this.limits = $$1;
/*    */     }
/*    */     
/*    */     public R report() {
/* 47 */       return this.report;
/*    */     }
/*    */     
/*    */     public UUID reportedProfileId() {
/* 51 */       return ((Report)this.report).reportedProfileId;
/*    */     }
/*    */     
/*    */     public String comments() {
/* 55 */       return ((Report)this.report).comments;
/*    */     }
/*    */     
/*    */     public void setComments(String $$0) {
/* 59 */       ((Report)this.report).comments = $$0;
/*    */     }
/*    */     
/*    */     @Nullable
/*    */     public ReportReason reason() {
/* 64 */       return ((Report)this.report).reason;
/*    */     }
/*    */     
/*    */     public void setReason(ReportReason $$0) {
/* 68 */       ((Report)this.report).reason = $$0;
/*    */     }
/*    */     public abstract boolean hasContent();
/*    */     @Nullable
/*    */     public abstract Report.CannotBuildReason checkBuildable();
/*    */     public abstract Either<Report.Result, Report.CannotBuildReason> build(ReportingContext param1ReportingContext); }
/*    */   
/*    */   public static final class Result extends Record { private final UUID id;
/*    */     private final ReportType reportType;
/*    */     private final AbuseReport report;
/*    */     
/* 79 */     public Result(UUID $$0, ReportType $$1, AbuseReport $$2) { this.id = $$0; this.reportType = $$1; this.report = $$2; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/multiplayer/chat/report/Report$Result;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #79	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/* 79 */       //   0	7	0	this	Lnet/minecraft/client/multiplayer/chat/report/Report$Result; } public UUID id() { return this.id; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/multiplayer/chat/report/Report$Result;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #79	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/client/multiplayer/chat/report/Report$Result; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/multiplayer/chat/report/Report$Result;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #79	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/client/multiplayer/chat/report/Report$Result;
/* 79 */       //   0	8	1	$$0	Ljava/lang/Object; } public ReportType reportType() { return this.reportType; } public AbuseReport report() { return this.report; }
/*    */      }
/*    */   public static final class CannotBuildReason extends Record { private final Component message;
/* 82 */     public CannotBuildReason(Component $$0) { this.message = $$0; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/multiplayer/chat/report/Report$CannotBuildReason;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #82	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/client/multiplayer/chat/report/Report$CannotBuildReason; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/multiplayer/chat/report/Report$CannotBuildReason;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #82	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/client/multiplayer/chat/report/Report$CannotBuildReason; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/multiplayer/chat/report/Report$CannotBuildReason;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #82	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/client/multiplayer/chat/report/Report$CannotBuildReason;
/* 82 */       //   0	8	1	$$0	Ljava/lang/Object; } public Component message() { return this.message; }
/* 83 */      public static final CannotBuildReason NO_REASON = new CannotBuildReason((Component)Component.translatable("gui.abuseReport.send.no_reason"));
/* 84 */     public static final CannotBuildReason NO_REPORTED_MESSAGES = new CannotBuildReason((Component)Component.translatable("gui.chatReport.send.no_reported_messages"));
/* 85 */     public static final CannotBuildReason TOO_MANY_MESSAGES = new CannotBuildReason((Component)Component.translatable("gui.chatReport.send.too_many_messages"));
/* 86 */     public static final CannotBuildReason COMMENT_TOO_LONG = new CannotBuildReason((Component)Component.translatable("gui.abuseReport.send.comment_too_long"));
/*    */     
/*    */     public Tooltip tooltip() {
/* 89 */       return Tooltip.create(this.message);
/*    */     } }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\multiplayer\chat\report\Report.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */