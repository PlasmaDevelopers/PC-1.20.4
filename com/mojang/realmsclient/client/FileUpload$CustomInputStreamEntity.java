/*     */ package com.mojang.realmsclient.client;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import org.apache.http.entity.InputStreamEntity;
/*     */ import org.apache.http.util.Args;
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
/*     */ class CustomInputStreamEntity
/*     */   extends InputStreamEntity
/*     */ {
/*     */   private final long length;
/*     */   private final InputStream content;
/*     */   private final UploadStatus uploadStatus;
/*     */   
/*     */   public CustomInputStreamEntity(InputStream $$0, long $$1, UploadStatus $$2) {
/* 177 */     super($$0);
/* 178 */     this.content = $$0;
/* 179 */     this.length = $$1;
/* 180 */     this.uploadStatus = $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeTo(OutputStream $$0) throws IOException {
/* 185 */     Args.notNull($$0, "Output stream");
/* 186 */     InputStream $$1 = this.content;
/*     */     try {
/* 188 */       byte[] $$2 = new byte[4096];
/*     */       
/* 190 */       if (this.length < 0L) {
/*     */         int $$3;
/* 192 */         while (($$3 = $$1.read($$2)) != -1) {
/* 193 */           $$0.write($$2, 0, $$3);
/* 194 */           this.uploadStatus.bytesWritten += $$3;
/*     */         } 
/*     */       } else {
/*     */         
/* 198 */         long $$4 = this.length;
/* 199 */         while ($$4 > 0L) {
/* 200 */           int $$5 = $$1.read($$2, 0, (int)Math.min(4096L, $$4));
/* 201 */           if ($$5 == -1) {
/*     */             break;
/*     */           }
/* 204 */           $$0.write($$2, 0, $$5);
/* 205 */           this.uploadStatus.bytesWritten += $$5;
/* 206 */           $$4 -= $$5;
/* 207 */           $$0.flush();
/*     */         } 
/*     */       } 
/*     */     } finally {
/* 211 */       $$1.close();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\client\FileUpload$CustomInputStreamEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */