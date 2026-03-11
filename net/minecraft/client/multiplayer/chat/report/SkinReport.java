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
/*    */ import net.minecraft.client.gui.screens.Screen;
/*    */ import net.minecraft.client.gui.screens.reporting.SkinReportScreen;
/*    */ import net.minecraft.client.resources.PlayerSkin;
/*    */ import org.apache.commons.lang3.StringUtils;
/*    */ 
/*    */ public class SkinReport
/*    */   extends Report {
/*    */   final Supplier<PlayerSkin> skinGetter;
/*    */   
/*    */   SkinReport(UUID $$0, Instant $$1, UUID $$2, Supplier<PlayerSkin> $$3) {
/* 22 */     super($$0, $$1, $$2);
/* 23 */     this.skinGetter = $$3;
/*    */   }
/*    */   
/*    */   public Supplier<PlayerSkin> getSkinGetter() {
/* 27 */     return this.skinGetter;
/*    */   }
/*    */ 
/*    */   
/*    */   public SkinReport copy() {
/* 32 */     SkinReport $$0 = new SkinReport(this.reportId, this.createdAt, this.reportedProfileId, this.skinGetter);
/* 33 */     $$0.comments = this.comments;
/* 34 */     $$0.reason = this.reason;
/* 35 */     return $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public Screen createScreen(Screen $$0, ReportingContext $$1) {
/* 40 */     return (Screen)new SkinReportScreen($$0, $$1, this);
/*    */   }
/*    */   
/*    */   public static class Builder extends Report.Builder<SkinReport> {
/*    */     public Builder(SkinReport $$0, AbuseReportLimits $$1) {
/* 45 */       super($$0, $$1);
/*    */     }
/*    */     
/*    */     public Builder(UUID $$0, Supplier<PlayerSkin> $$1, AbuseReportLimits $$2) {
/* 49 */       super(new SkinReport(UUID.randomUUID(), Instant.now(), $$0, $$1), $$2);
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean hasContent() {
/* 54 */       return (StringUtils.isNotEmpty(comments()) || reason() != null);
/*    */     }
/*    */ 
/*    */     
/*    */     @Nullable
/*    */     public Report.CannotBuildReason checkBuildable() {
/* 60 */       if (this.report.reason == null) {
/* 61 */         return Report.CannotBuildReason.NO_REASON;
/*    */       }
/* 63 */       if (this.report.comments.length() > this.limits.maxOpinionCommentsLength()) {
/* 64 */         return Report.CannotBuildReason.COMMENT_TOO_LONG;
/*    */       }
/* 66 */       return null;
/*    */     }
/*    */ 
/*    */     
/*    */     public Either<Report.Result, Report.CannotBuildReason> build(ReportingContext $$0) {
/* 71 */       Report.CannotBuildReason $$1 = checkBuildable();
/* 72 */       if ($$1 != null) {
/* 73 */         return Either.right($$1);
/*    */       }
/*    */       
/* 76 */       String $$2 = ((ReportReason)Objects.<ReportReason>requireNonNull(this.report.reason)).backendName();
/*    */       
/* 78 */       ReportedEntity $$3 = new ReportedEntity(this.report.reportedProfileId);
/*    */ 
/*    */       
/* 81 */       PlayerSkin $$4 = this.report.skinGetter.get();
/* 82 */       String $$5 = $$4.textureUrl();
/*    */       
/* 84 */       AbuseReport $$6 = AbuseReport.skin(this.report.comments, $$2, $$5, $$3, this.report.createdAt);
/* 85 */       return Either.left(new Report.Result(this.report.reportId, ReportType.SKIN, $$6));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\multiplayer\chat\report\SkinReport.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */