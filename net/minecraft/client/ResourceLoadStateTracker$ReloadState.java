/*    */ package net.minecraft.client;
/*    */ 
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.CrashReportCategory;
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
/*    */ class ReloadState
/*    */ {
/*    */   private final ResourceLoadStateTracker.ReloadReason reloadReason;
/*    */   private final List<String> packs;
/*    */   @Nullable
/*    */   ResourceLoadStateTracker.RecoveryInfo recoveryReloadInfo;
/*    */   boolean finished;
/*    */   
/*    */   ReloadState(ResourceLoadStateTracker.ReloadReason $$0, List<String> $$1) {
/* 84 */     this.reloadReason = $$0;
/* 85 */     this.packs = $$1;
/*    */   }
/*    */   
/*    */   public void fillCrashInfo(CrashReportCategory $$0) {
/* 89 */     $$0.setDetail("Reload reason", this.reloadReason.name);
/* 90 */     $$0.setDetail("Finished", this.finished ? "Yes" : "No");
/* 91 */     $$0.setDetail("Packs", () -> String.join(", ", (Iterable)this.packs));
/*    */     
/* 93 */     if (this.recoveryReloadInfo != null)
/* 94 */       this.recoveryReloadInfo.fillCrashInfo($$0); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\ResourceLoadStateTracker$ReloadState.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */