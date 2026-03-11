/*    */ package net.minecraft.client.multiplayer.chat.report;
/*    */ 
/*    */ import com.mojang.authlib.minecraft.report.AbuseReport;
/*    */ import com.mojang.authlib.minecraft.report.AbuseReportLimits;
/*    */ import com.mojang.authlib.minecraft.report.ReportedEntity;
/*    */ import com.mojang.datafixers.util.Either;
/*    */ import java.time.Instant;
/*    */ import java.util.Objects;
/*    */ import java.util.UUID;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.resources.PlayerSkin;
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
/*    */ 
/*    */ public class Builder
/*    */   extends Report.Builder<SkinReport>
/*    */ {
/*    */   public Builder(SkinReport $$0, AbuseReportLimits $$1) {
/* 45 */     super($$0, $$1);
/*    */   }
/*    */   
/*    */   public Builder(UUID $$0, Supplier<PlayerSkin> $$1, AbuseReportLimits $$2) {
/* 49 */     super(new SkinReport(UUID.randomUUID(), Instant.now(), $$0, $$1), $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasContent() {
/* 54 */     return (StringUtils.isNotEmpty(comments()) || reason() != null);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Report.CannotBuildReason checkBuildable() {
/* 60 */     if (this.report.reason == null) {
/* 61 */       return Report.CannotBuildReason.NO_REASON;
/*    */     }
/* 63 */     if (this.report.comments.length() > this.limits.maxOpinionCommentsLength()) {
/* 64 */       return Report.CannotBuildReason.COMMENT_TOO_LONG;
/*    */     }
/* 66 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public Either<Report.Result, Report.CannotBuildReason> build(ReportingContext $$0) {
/* 71 */     Report.CannotBuildReason $$1 = checkBuildable();
/* 72 */     if ($$1 != null) {
/* 73 */       return Either.right($$1);
/*    */     }
/*    */     
/* 76 */     String $$2 = ((ReportReason)Objects.<ReportReason>requireNonNull(this.report.reason)).backendName();
/*    */     
/* 78 */     ReportedEntity $$3 = new ReportedEntity(this.report.reportedProfileId);
/*    */ 
/*    */     
/* 81 */     PlayerSkin $$4 = this.report.skinGetter.get();
/* 82 */     String $$5 = $$4.textureUrl();
/*    */     
/* 84 */     AbuseReport $$6 = AbuseReport.skin(this.report.comments, $$2, $$5, $$3, this.report.createdAt);
/* 85 */     return Either.left(new Report.Result(this.report.reportId, ReportType.SKIN, $$6));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\multiplayer\chat\report\SkinReport$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */