/*    */ package net.minecraft.client.multiplayer.chat.report;
/*    */ 
/*    */ import com.mojang.authlib.minecraft.report.AbuseReportLimits;
/*    */ import com.mojang.datafixers.util.Either;
/*    */ import java.util.UUID;
/*    */ import javax.annotation.Nullable;
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
/*    */ 
/*    */ 
/*    */ public abstract class Builder<R extends Report>
/*    */ {
/*    */   protected final R report;
/*    */   protected final AbuseReportLimits limits;
/*    */   
/*    */   protected Builder(R $$0, AbuseReportLimits $$1) {
/* 42 */     this.report = $$0;
/* 43 */     this.limits = $$1;
/*    */   }
/*    */   
/*    */   public R report() {
/* 47 */     return this.report;
/*    */   }
/*    */   
/*    */   public UUID reportedProfileId() {
/* 51 */     return ((Report)this.report).reportedProfileId;
/*    */   }
/*    */   
/*    */   public String comments() {
/* 55 */     return ((Report)this.report).comments;
/*    */   }
/*    */   
/*    */   public void setComments(String $$0) {
/* 59 */     ((Report)this.report).comments = $$0;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public ReportReason reason() {
/* 64 */     return ((Report)this.report).reason;
/*    */   }
/*    */   
/*    */   public void setReason(ReportReason $$0) {
/* 68 */     ((Report)this.report).reason = $$0;
/*    */   }
/*    */   
/*    */   public abstract boolean hasContent();
/*    */   
/*    */   @Nullable
/*    */   public abstract Report.CannotBuildReason checkBuildable();
/*    */   
/*    */   public abstract Either<Report.Result, Report.CannotBuildReason> build(ReportingContext paramReportingContext);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\multiplayer\chat\report\Report$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */