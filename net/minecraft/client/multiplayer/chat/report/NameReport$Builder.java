/*    */ package net.minecraft.client.multiplayer.chat.report;
/*    */ 
/*    */ import com.mojang.authlib.minecraft.report.AbuseReport;
/*    */ import com.mojang.authlib.minecraft.report.AbuseReportLimits;
/*    */ import com.mojang.authlib.minecraft.report.ReportedEntity;
/*    */ import com.mojang.datafixers.util.Either;
/*    */ import java.time.Instant;
/*    */ import java.util.UUID;
/*    */ import javax.annotation.Nullable;
/*    */ import org.apache.commons.lang3.StringUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Builder
/*    */   extends Report.Builder<NameReport>
/*    */ {
/*    */   public Builder(NameReport $$0, AbuseReportLimits $$1) {
/* 41 */     super($$0, $$1);
/*    */   }
/*    */   
/*    */   public Builder(UUID $$0, String $$1, AbuseReportLimits $$2) {
/* 45 */     super(new NameReport(UUID.randomUUID(), Instant.now(), $$0, $$1), $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasContent() {
/* 50 */     return StringUtils.isNotEmpty(comments());
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Report.CannotBuildReason checkBuildable() {
/* 56 */     if (this.report.comments.length() > this.limits.maxOpinionCommentsLength()) {
/* 57 */       return Report.CannotBuildReason.COMMENT_TOO_LONG;
/*    */     }
/* 59 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public Either<Report.Result, Report.CannotBuildReason> build(ReportingContext $$0) {
/* 64 */     Report.CannotBuildReason $$1 = checkBuildable();
/* 65 */     if ($$1 != null) {
/* 66 */       return Either.right($$1);
/*    */     }
/*    */     
/* 69 */     ReportedEntity $$2 = new ReportedEntity(this.report.reportedProfileId);
/*    */     
/* 71 */     AbuseReport $$3 = AbuseReport.name(this.report.comments, $$2, this.report.createdAt);
/* 72 */     return Either.left(new Report.Result(this.report.reportId, ReportType.USERNAME, $$3));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\multiplayer\chat\report\NameReport$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */