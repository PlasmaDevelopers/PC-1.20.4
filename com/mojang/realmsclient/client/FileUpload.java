/*     */ package com.mojang.realmsclient.client;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonParser;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.realmsclient.dto.UploadInfo;
/*     */ import com.mojang.realmsclient.gui.screens.UploadResult;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.time.Duration;
/*     */ import java.util.Optional;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.function.Consumer;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.User;
/*     */ import org.apache.http.Header;
/*     */ import org.apache.http.HttpEntity;
/*     */ import org.apache.http.HttpResponse;
/*     */ import org.apache.http.NameValuePair;
/*     */ import org.apache.http.client.config.RequestConfig;
/*     */ import org.apache.http.client.methods.CloseableHttpResponse;
/*     */ import org.apache.http.client.methods.HttpPost;
/*     */ import org.apache.http.client.methods.HttpUriRequest;
/*     */ import org.apache.http.impl.client.CloseableHttpClient;
/*     */ import org.apache.http.impl.client.HttpClientBuilder;
/*     */ import org.apache.http.util.Args;
/*     */ import org.apache.http.util.EntityUtils;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class FileUpload {
/*  35 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private static final int MAX_RETRIES = 5;
/*     */   
/*     */   private static final String UPLOAD_PATH = "/upload";
/*     */   
/*     */   private final File file;
/*     */   private final long worldId;
/*     */   private final int slotId;
/*     */   private final UploadInfo uploadInfo;
/*     */   private final String sessionId;
/*     */   private final String username;
/*     */   private final String clientVersion;
/*     */   private final UploadStatus uploadStatus;
/*  49 */   private final AtomicBoolean cancelled = new AtomicBoolean(false);
/*     */   
/*     */   @Nullable
/*     */   private CompletableFuture<UploadResult> uploadTask;
/*  53 */   private final RequestConfig requestConfig = RequestConfig.custom()
/*  54 */     .setSocketTimeout((int)TimeUnit.MINUTES.toMillis(10L))
/*  55 */     .setConnectTimeout((int)TimeUnit.SECONDS.toMillis(15L))
/*  56 */     .build();
/*     */   
/*     */   public FileUpload(File $$0, long $$1, int $$2, UploadInfo $$3, User $$4, String $$5, UploadStatus $$6) {
/*  59 */     this.file = $$0;
/*  60 */     this.worldId = $$1;
/*  61 */     this.slotId = $$2;
/*  62 */     this.uploadInfo = $$3;
/*  63 */     this.sessionId = $$4.getSessionId();
/*  64 */     this.username = $$4.getName();
/*  65 */     this.clientVersion = $$5;
/*  66 */     this.uploadStatus = $$6;
/*     */   }
/*     */   
/*     */   public void upload(Consumer<UploadResult> $$0) {
/*  70 */     if (this.uploadTask != null) {
/*     */       return;
/*     */     }
/*     */     
/*  74 */     this.uploadTask = CompletableFuture.supplyAsync(() -> requestUpload(0));
/*  75 */     this.uploadTask.thenAccept($$0);
/*     */   }
/*     */   
/*     */   public void cancel() {
/*  79 */     this.cancelled.set(true);
/*  80 */     if (this.uploadTask != null) {
/*  81 */       this.uploadTask.cancel(false);
/*  82 */       this.uploadTask = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private UploadResult requestUpload(int $$0) {
/*  87 */     UploadResult.Builder $$1 = new UploadResult.Builder();
/*  88 */     if (this.cancelled.get()) {
/*  89 */       return $$1.build();
/*     */     }
/*  91 */     this.uploadStatus.totalBytes = this.file.length();
/*  92 */     HttpPost $$2 = new HttpPost(this.uploadInfo.getUploadEndpoint().resolve("/upload/" + this.worldId + "/" + this.slotId));
/*  93 */     CloseableHttpClient $$3 = HttpClientBuilder.create().setDefaultRequestConfig(this.requestConfig).build();
/*     */     try {
/*  95 */       setupRequest($$2);
/*  96 */       CloseableHttpResponse closeableHttpResponse = $$3.execute((HttpUriRequest)$$2);
/*  97 */       long $$5 = getRetryDelaySeconds((HttpResponse)closeableHttpResponse);
/*  98 */       if (shouldRetry($$5, $$0)) {
/*  99 */         return retryUploadAfter($$5, $$0);
/*     */       }
/* 101 */       handleResponse((HttpResponse)closeableHttpResponse, $$1);
/* 102 */     } catch (Exception $$6) {
/* 103 */       if (!this.cancelled.get()) {
/* 104 */         LOGGER.error("Caught exception while uploading: ", $$6);
/*     */       }
/*     */     } finally {
/* 107 */       cleanup($$2, $$3);
/*     */     } 
/* 109 */     return $$1.build();
/*     */   }
/*     */   
/*     */   private void cleanup(HttpPost $$0, @Nullable CloseableHttpClient $$1) {
/* 113 */     $$0.releaseConnection();
/* 114 */     if ($$1 != null) {
/*     */       try {
/* 116 */         $$1.close();
/* 117 */       } catch (IOException $$2) {
/* 118 */         LOGGER.error("Failed to close Realms upload client");
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private void setupRequest(HttpPost $$0) throws FileNotFoundException {
/* 124 */     $$0.setHeader("Cookie", "sid=" + this.sessionId + ";token=" + this.uploadInfo.getToken() + ";user=" + this.username + ";version=" + this.clientVersion);
/* 125 */     CustomInputStreamEntity $$1 = new CustomInputStreamEntity(new FileInputStream(this.file), this.file.length(), this.uploadStatus);
/* 126 */     $$1.setContentType("application/octet-stream");
/* 127 */     $$0.setEntity((HttpEntity)$$1);
/*     */   }
/*     */   
/*     */   private void handleResponse(HttpResponse $$0, UploadResult.Builder $$1) throws IOException {
/* 131 */     int $$2 = $$0.getStatusLine().getStatusCode();
/* 132 */     if ($$2 == 401) {
/* 133 */       LOGGER.debug("Realms server returned 401: {}", $$0.getFirstHeader("WWW-Authenticate"));
/*     */     }
/* 135 */     $$1.withStatusCode($$2);
/* 136 */     if ($$0.getEntity() != null) {
/* 137 */       String $$3 = EntityUtils.toString($$0.getEntity(), "UTF-8");
/*     */       
/* 139 */       if ($$3 != null) {
/*     */         try {
/* 141 */           JsonParser $$4 = new JsonParser();
/* 142 */           JsonElement $$5 = $$4.parse($$3).getAsJsonObject().get("errorMsg");
/* 143 */           Optional<String> $$6 = Optional.<JsonElement>ofNullable($$5).map(JsonElement::getAsString);
/* 144 */           $$1.withErrorMessage($$6.orElse(null));
/* 145 */         } catch (Exception exception) {}
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean shouldRetry(long $$0, int $$1) {
/* 152 */     return ($$0 > 0L && $$1 + 1 < 5);
/*     */   }
/*     */   
/*     */   private UploadResult retryUploadAfter(long $$0, int $$1) throws InterruptedException {
/* 156 */     Thread.sleep(Duration.ofSeconds($$0).toMillis());
/* 157 */     return requestUpload($$1 + 1);
/*     */   }
/*     */   
/*     */   private long getRetryDelaySeconds(HttpResponse $$0) {
/* 161 */     return ((Long)Optional.<Header>ofNullable($$0.getFirstHeader("Retry-After"))
/* 162 */       .map(NameValuePair::getValue)
/* 163 */       .map(Long::valueOf)
/* 164 */       .orElse(Long.valueOf(0L))).longValue();
/*     */   }
/*     */   
/*     */   public boolean isFinished() {
/* 168 */     return (this.uploadTask.isDone() || this.uploadTask.isCancelled());
/*     */   }
/*     */   
/*     */   private static class CustomInputStreamEntity extends InputStreamEntity {
/*     */     private final long length;
/*     */     private final InputStream content;
/*     */     private final UploadStatus uploadStatus;
/*     */     
/*     */     public CustomInputStreamEntity(InputStream $$0, long $$1, UploadStatus $$2) {
/* 177 */       super($$0);
/* 178 */       this.content = $$0;
/* 179 */       this.length = $$1;
/* 180 */       this.uploadStatus = $$2;
/*     */     }
/*     */ 
/*     */     
/*     */     public void writeTo(OutputStream $$0) throws IOException {
/* 185 */       Args.notNull($$0, "Output stream");
/* 186 */       InputStream $$1 = this.content;
/*     */       try {
/* 188 */         byte[] $$2 = new byte[4096];
/*     */         
/* 190 */         if (this.length < 0L) {
/*     */           int $$3;
/* 192 */           while (($$3 = $$1.read($$2)) != -1) {
/* 193 */             $$0.write($$2, 0, $$3);
/* 194 */             this.uploadStatus.bytesWritten += $$3;
/*     */           } 
/*     */         } else {
/*     */           
/* 198 */           long $$4 = this.length;
/* 199 */           while ($$4 > 0L) {
/* 200 */             int $$5 = $$1.read($$2, 0, (int)Math.min(4096L, $$4));
/* 201 */             if ($$5 == -1) {
/*     */               break;
/*     */             }
/* 204 */             $$0.write($$2, 0, $$5);
/* 205 */             this.uploadStatus.bytesWritten += $$5;
/* 206 */             $$4 -= $$5;
/* 207 */             $$0.flush();
/*     */           } 
/*     */         } 
/*     */       } finally {
/* 211 */         $$1.close();
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\client\FileUpload.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */