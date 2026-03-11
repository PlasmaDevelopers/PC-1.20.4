/*     */ package net.minecraft.client;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.CrashReport;
/*     */ import net.minecraft.CrashReportCategory;
/*     */ import net.minecraft.server.packs.PackResources;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class ResourceLoadStateTracker
/*     */ {
/*  16 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   @Nullable
/*     */   private ReloadState reloadState;
/*     */   
/*     */   private int reloadCount;
/*     */   
/*     */   public void startReload(ReloadReason $$0, List<PackResources> $$1) {
/*  24 */     this.reloadCount++;
/*  25 */     if (this.reloadState != null && !this.reloadState.finished) {
/*  26 */       LOGGER.warn("Reload already ongoing, replacing");
/*     */     }
/*  28 */     this.reloadState = new ReloadState($$0, (List<String>)$$1.stream().map(PackResources::packId).collect(ImmutableList.toImmutableList()));
/*     */   }
/*     */   
/*     */   public void startRecovery(Throwable $$0) {
/*  32 */     if (this.reloadState == null) {
/*  33 */       LOGGER.warn("Trying to signal reload recovery, but nothing was started");
/*  34 */       this.reloadState = new ReloadState(ReloadReason.UNKNOWN, (List<String>)ImmutableList.of());
/*     */     } 
/*     */     
/*  37 */     this.reloadState.recoveryReloadInfo = new RecoveryInfo($$0);
/*     */   }
/*     */   
/*     */   public void finishReload() {
/*  41 */     if (this.reloadState == null) {
/*  42 */       LOGGER.warn("Trying to finish reload, but nothing was started");
/*     */     } else {
/*  44 */       this.reloadState.finished = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void fillCrashReport(CrashReport $$0) {
/*  49 */     CrashReportCategory $$1 = $$0.addCategory("Last reload");
/*  50 */     $$1.setDetail("Reload number", Integer.valueOf(this.reloadCount));
/*  51 */     if (this.reloadState != null)
/*  52 */       this.reloadState.fillCrashInfo($$1); 
/*     */   }
/*     */   
/*     */   private static class RecoveryInfo
/*     */   {
/*     */     private final Throwable error;
/*     */     
/*     */     RecoveryInfo(Throwable $$0) {
/*  60 */       this.error = $$0;
/*     */     }
/*     */     
/*     */     public void fillCrashInfo(CrashReportCategory $$0) {
/*  64 */       $$0.setDetail("Recovery", "Yes");
/*     */       
/*  66 */       $$0.setDetail("Recovery reason", () -> {
/*     */             StringWriter $$0 = new StringWriter();
/*     */             this.error.printStackTrace(new PrintWriter($$0));
/*     */             return $$0.toString();
/*     */           });
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class ReloadState
/*     */   {
/*     */     private final ResourceLoadStateTracker.ReloadReason reloadReason;
/*     */     private final List<String> packs;
/*     */     @Nullable
/*     */     ResourceLoadStateTracker.RecoveryInfo recoveryReloadInfo;
/*     */     boolean finished;
/*     */     
/*     */     ReloadState(ResourceLoadStateTracker.ReloadReason $$0, List<String> $$1) {
/*  84 */       this.reloadReason = $$0;
/*  85 */       this.packs = $$1;
/*     */     }
/*     */     
/*     */     public void fillCrashInfo(CrashReportCategory $$0) {
/*  89 */       $$0.setDetail("Reload reason", this.reloadReason.name);
/*  90 */       $$0.setDetail("Finished", this.finished ? "Yes" : "No");
/*  91 */       $$0.setDetail("Packs", () -> String.join(", ", (Iterable)this.packs));
/*     */       
/*  93 */       if (this.recoveryReloadInfo != null)
/*  94 */         this.recoveryReloadInfo.fillCrashInfo($$0); 
/*     */     }
/*     */   }
/*     */   
/*     */   public enum ReloadReason
/*     */   {
/* 100 */     INITIAL("initial"),
/* 101 */     MANUAL("manual"),
/* 102 */     UNKNOWN("unknown");
/*     */     
/*     */     final String name;
/*     */     
/*     */     ReloadReason(String $$0) {
/* 107 */       this.name = $$0;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\ResourceLoadStateTracker.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */