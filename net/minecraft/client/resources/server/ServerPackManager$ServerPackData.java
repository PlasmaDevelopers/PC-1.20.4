/*     */ package net.minecraft.client.resources.server;
/*     */ 
/*     */ import com.google.common.hash.HashCode;
/*     */ import java.net.URL;
/*     */ import java.nio.file.Path;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
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
/*     */ class ServerPackData
/*     */ {
/*     */   final UUID id;
/*     */   final URL url;
/*     */   @Nullable
/*     */   final HashCode hash;
/*     */   @Nullable
/*     */   Path path;
/*     */   @Nullable
/*     */   ServerPackManager.RemovalReason removalReason;
/* 110 */   ServerPackManager.PackDownloadStatus downloadStatus = ServerPackManager.PackDownloadStatus.REQUESTED;
/* 111 */   ServerPackManager.ActivationStatus activationStatus = ServerPackManager.ActivationStatus.INACTIVE;
/*     */   boolean promptAccepted;
/*     */   
/*     */   ServerPackData(UUID $$0, URL $$1, @Nullable HashCode $$2) {
/* 115 */     this.id = $$0;
/* 116 */     this.url = $$1;
/* 117 */     this.hash = $$2;
/*     */   }
/*     */   
/*     */   public void setRemovalReasonIfNotSet(ServerPackManager.RemovalReason $$0) {
/* 121 */     if (this.removalReason == null) {
/* 122 */       this.removalReason = $$0;
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isRemoved() {
/* 127 */     return (this.removalReason != null);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\server\ServerPackManager$ServerPackData.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */