/*     */ package net.minecraft.client.resources.server;
/*     */ 
/*     */ import com.mojang.realmsclient.Unit;
/*     */ import java.util.OptionalLong;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.gui.components.toasts.SystemToast;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.util.HttpUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class null
/*     */   implements HttpUtil.DownloadProgressListener
/*     */ {
/* 118 */   private final SystemToast.SystemToastId toastId = new SystemToast.SystemToastId();
/* 119 */   private Component title = (Component)Component.empty(); @Nullable
/* 120 */   private Component message = null;
/*     */   
/*     */   private int count;
/*     */   private int failCount;
/* 124 */   private OptionalLong totalBytes = OptionalLong.empty();
/*     */   
/*     */   private void updateToast() {
/* 127 */     SystemToast.addOrUpdate(DownloadedPackSource.this.minecraft.getToasts(), this.toastId, this.title, this.message);
/*     */   }
/*     */   
/*     */   private void updateProgress(long $$0) {
/* 131 */     if (this.totalBytes.isPresent()) {
/* 132 */       this.message = (Component)Component.translatable("download.pack.progress.percent", new Object[] { Long.valueOf($$0 * 100L / this.totalBytes.getAsLong()) });
/*     */     } else {
/* 134 */       this.message = (Component)Component.translatable("download.pack.progress.bytes", new Object[] { Unit.humanReadable($$0) });
/*     */     } 
/* 136 */     updateToast();
/*     */   }
/*     */ 
/*     */   
/*     */   public void requestStart() {
/* 141 */     this.count++;
/* 142 */     this.title = (Component)Component.translatable("download.pack.title", new Object[] { Integer.valueOf(this.count), Integer.valueOf(this.val$totalCount) });
/* 143 */     updateToast();
/* 144 */     DownloadedPackSource.LOGGER.debug("Starting pack {}/{} download", Integer.valueOf(this.count), Integer.valueOf(totalCount));
/*     */   }
/*     */ 
/*     */   
/*     */   public void downloadStart(OptionalLong $$0) {
/* 149 */     DownloadedPackSource.LOGGER.debug("File size = {} bytes", $$0);
/* 150 */     this.totalBytes = $$0;
/* 151 */     updateProgress(0L);
/*     */   }
/*     */ 
/*     */   
/*     */   public void downloadedBytes(long $$0) {
/* 156 */     DownloadedPackSource.LOGGER.debug("Progress for pack {}: {} bytes", Integer.valueOf(this.count), Long.valueOf($$0));
/* 157 */     updateProgress($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void requestFinished(boolean $$0) {
/* 162 */     if (!$$0) {
/* 163 */       DownloadedPackSource.LOGGER.info("Pack {} failed to download", Integer.valueOf(this.count));
/* 164 */       this.failCount++;
/*     */     } else {
/* 166 */       DownloadedPackSource.LOGGER.debug("Download ended for pack {}", Integer.valueOf(this.count));
/*     */     } 
/*     */     
/* 169 */     if (this.count == totalCount)
/* 170 */       if (this.failCount > 0) {
/* 171 */         this.title = (Component)Component.translatable("download.pack.failed", new Object[] { Integer.valueOf(this.failCount), Integer.valueOf(this.val$totalCount) });
/* 172 */         this.message = null;
/* 173 */         updateToast();
/*     */       } else {
/* 175 */         SystemToast.forceHide(DownloadedPackSource.this.minecraft.getToasts(), this.toastId);
/*     */       }  
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\server\DownloadedPackSource$3.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */