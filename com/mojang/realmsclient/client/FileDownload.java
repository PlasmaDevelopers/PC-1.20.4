/*     */ package com.mojang.realmsclient.client;
/*     */ 
/*     */ import com.google.common.hash.Hashing;
/*     */ import com.google.common.io.Files;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.realmsclient.dto.WorldDownload;
/*     */ import com.mojang.realmsclient.exception.RealmsDefaultUncaughtExceptionHandler;
/*     */ import com.mojang.realmsclient.gui.screens.RealmsDownloadLatestWorldScreen;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.Locale;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.SharedConstants;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.world.level.storage.LevelStorageSource;
/*     */ import net.minecraft.world.level.validation.ContentValidationException;
/*     */ import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
/*     */ import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
/*     */ import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.commons.io.output.CountingOutputStream;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.apache.http.client.config.RequestConfig;
/*     */ import org.apache.http.client.methods.CloseableHttpResponse;
/*     */ import org.apache.http.client.methods.HttpGet;
/*     */ import org.apache.http.client.methods.HttpUriRequest;
/*     */ import org.apache.http.impl.client.CloseableHttpClient;
/*     */ import org.apache.http.impl.client.HttpClientBuilder;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ 
/*     */ public class FileDownload
/*     */ {
/*  44 */   static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   volatile boolean cancelled;
/*     */   
/*     */   volatile boolean finished;
/*     */   
/*     */   volatile boolean error;
/*     */   
/*     */   volatile boolean extracting;
/*     */   @Nullable
/*     */   private volatile File tempFile;
/*     */   volatile File resourcePackPath;
/*     */   @Nullable
/*     */   private volatile HttpGet request;
/*     */   @Nullable
/*     */   private Thread currentThread;
/*  60 */   private final RequestConfig requestConfig = RequestConfig.custom()
/*  61 */     .setSocketTimeout(120000)
/*  62 */     .setConnectTimeout(120000)
/*  63 */     .build();
/*     */   
/*     */   public long contentLength(String $$0) {
/*  66 */     CloseableHttpClient $$1 = null;
/*  67 */     HttpGet $$2 = null;
/*     */     try {
/*  69 */       $$2 = new HttpGet($$0);
/*     */ 
/*     */       
/*  72 */       $$1 = HttpClientBuilder.create().setDefaultRequestConfig(this.requestConfig).build();
/*  73 */       CloseableHttpResponse $$3 = $$1.execute((HttpUriRequest)$$2);
/*  74 */       return Long.parseLong($$3.getFirstHeader("Content-Length").getValue());
/*  75 */     } catch (Throwable $$5) {
/*  76 */       LOGGER.error("Unable to get content length for download");
/*  77 */       return 0L;
/*     */     } finally {
/*  79 */       if ($$2 != null) {
/*  80 */         $$2.releaseConnection();
/*     */       }
/*  82 */       if ($$1 != null) {
/*     */         try {
/*  84 */           $$1.close();
/*  85 */         } catch (IOException $$7) {
/*  86 */           LOGGER.error("Could not close http client", $$7);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void download(WorldDownload $$0, String $$1, RealmsDownloadLatestWorldScreen.DownloadStatus $$2, LevelStorageSource $$3) {
/*  93 */     if (this.currentThread != null) {
/*     */       return;
/*     */     }
/*     */     
/*  97 */     this.currentThread = new Thread(() -> {
/*     */           CloseableHttpClient $$4 = null;
/*     */           
/*     */           try {
/*     */             this.tempFile = File.createTempFile("backup", ".tar.gz");
/*     */             
/*     */             this.request = new HttpGet($$0.downloadLink);
/*     */             
/*     */             $$4 = HttpClientBuilder.create().setDefaultRequestConfig(this.requestConfig).build();
/*     */             
/*     */             CloseableHttpResponse closeableHttpResponse = $$4.execute((HttpUriRequest)this.request);
/*     */             
/*     */             $$1.totalBytes = Long.parseLong(closeableHttpResponse.getFirstHeader("Content-Length").getValue());
/*     */             
/*     */             if (closeableHttpResponse.getStatusLine().getStatusCode() != 200) {
/*     */               this.error = true;
/*     */               
/*     */               this.request.abort();
/*     */               return;
/*     */             } 
/*     */             OutputStream $$12 = new FileOutputStream(this.tempFile);
/*     */             ProgressListener $$13 = new ProgressListener($$2.trim(), this.tempFile, $$3, $$1);
/*     */             DownloadCountingOutputStream $$14 = new DownloadCountingOutputStream($$12);
/*     */             $$14.setListener($$13);
/*     */             IOUtils.copy(closeableHttpResponse.getEntity().getContent(), (OutputStream)$$14);
/* 122 */           } catch (Exception $$21) {
/*     */             LOGGER.error("Caught exception while downloading: {}", $$21.getMessage());
/*     */             
/*     */             this.error = true;
/*     */           } finally {
/*     */             this.request.releaseConnection();
/*     */             
/*     */             if (this.tempFile != null) {
/*     */               this.tempFile.delete();
/*     */             }
/*     */             
/*     */             if (!this.error) {
/*     */               if (!$$0.resourcePackUrl.isEmpty() && !$$0.resourcePackHash.isEmpty()) {
/*     */                 try {
/*     */                   this.tempFile = File.createTempFile("resources", ".tar.gz");
/*     */                   
/*     */                   this.request = new HttpGet($$0.resourcePackUrl);
/*     */                   
/*     */                   CloseableHttpResponse closeableHttpResponse = $$4.execute((HttpUriRequest)this.request);
/*     */                   
/*     */                   $$1.totalBytes = Long.parseLong(closeableHttpResponse.getFirstHeader("Content-Length").getValue());
/*     */                   
/*     */                   if (closeableHttpResponse.getStatusLine().getStatusCode() != 200) {
/*     */                     this.error = true;
/*     */                     this.request.abort();
/*     */                     return;
/*     */                   } 
/*     */                   OutputStream $$29 = new FileOutputStream(this.tempFile);
/*     */                   ResourcePackProgressListener $$30 = new ResourcePackProgressListener(this.tempFile, $$1, $$0);
/*     */                   DownloadCountingOutputStream $$31 = new DownloadCountingOutputStream($$29);
/*     */                   $$31.setListener($$30);
/*     */                   IOUtils.copy(closeableHttpResponse.getEntity().getContent(), (OutputStream)$$31);
/* 154 */                 } catch (Exception $$32) {
/*     */                   LOGGER.error("Caught exception while downloading: {}", $$32.getMessage());
/*     */                   
/*     */                   this.error = true;
/*     */                 } finally {
/*     */                   this.request.releaseConnection();
/*     */                   
/*     */                   if (this.tempFile != null) {
/*     */                     this.tempFile.delete();
/*     */                   }
/*     */                 } 
/*     */               } else {
/*     */                 this.finished = true;
/*     */               } 
/*     */             }
/*     */             if ($$4 != null) {
/*     */               try {
/*     */                 $$4.close();
/* 172 */               } catch (IOException $$33) {
/*     */                 LOGGER.error("Failed to close Realms download client");
/*     */               } 
/*     */             }
/*     */           } 
/*     */         });
/* 178 */     this.currentThread.setUncaughtExceptionHandler((Thread.UncaughtExceptionHandler)new RealmsDefaultUncaughtExceptionHandler(LOGGER));
/* 179 */     this.currentThread.start();
/*     */   }
/*     */   
/*     */   public void cancel() {
/* 183 */     if (this.request != null) {
/* 184 */       this.request.abort();
/*     */     }
/*     */     
/* 187 */     if (this.tempFile != null) {
/* 188 */       this.tempFile.delete();
/*     */     }
/*     */     
/* 191 */     this.cancelled = true;
/*     */   }
/*     */   
/*     */   public boolean isFinished() {
/* 195 */     return this.finished;
/*     */   }
/*     */   
/*     */   public boolean isError() {
/* 199 */     return this.error;
/*     */   }
/*     */   
/*     */   public boolean isExtracting() {
/* 203 */     return this.extracting;
/*     */   }
/*     */ 
/*     */   
/* 207 */   private static final String[] INVALID_FILE_NAMES = new String[] { "CON", "COM", "PRN", "AUX", "CLOCK$", "NUL", "COM1", "COM2", "COM3", "COM4", "COM5", "COM6", "COM7", "COM8", "COM9", "LPT1", "LPT2", "LPT3", "LPT4", "LPT5", "LPT6", "LPT7", "LPT8", "LPT9" };
/*     */ 
/*     */ 
/*     */   
/*     */   public static String findAvailableFolderName(String $$0) {
/* 212 */     $$0 = $$0.replaceAll("[\\./\"]", "_");
/*     */     
/* 214 */     for (String $$1 : INVALID_FILE_NAMES) {
/* 215 */       if ($$0.equalsIgnoreCase($$1)) {
/* 216 */         $$0 = "_" + $$0 + "_";
/*     */       }
/*     */     } 
/*     */     
/* 220 */     return $$0;
/*     */   }
/*     */   void untarGzipArchive(String $$0, @Nullable File $$1, LevelStorageSource $$2) throws IOException {
/*     */     String $$13;
/* 224 */     Pattern $$3 = Pattern.compile(".*-([0-9]+)$");
/*     */ 
/*     */     
/* 227 */     int $$4 = 1;
/*     */     
/* 229 */     for (char $$5 : SharedConstants.ILLEGAL_FILE_CHARACTERS) {
/* 230 */       $$0 = $$0.replace($$5, '_');
/*     */     }
/*     */     
/* 233 */     if (StringUtils.isEmpty($$0)) {
/* 234 */       $$0 = "Realm";
/*     */     }
/*     */     
/* 237 */     $$0 = findAvailableFolderName($$0);
/*     */     
/*     */     try {
/* 240 */       for (LevelStorageSource.LevelDirectory $$6 : $$2.findLevelCandidates()) {
/* 241 */         String $$7 = $$6.directoryName();
/* 242 */         if ($$7.toLowerCase(Locale.ROOT).startsWith($$0.toLowerCase(Locale.ROOT))) {
/* 243 */           Matcher $$8 = $$3.matcher($$7);
/* 244 */           if ($$8.matches()) {
/* 245 */             int $$9 = Integer.parseInt($$8.group(1));
/* 246 */             if ($$9 > $$4)
/* 247 */               $$4 = $$9; 
/*     */             continue;
/*     */           } 
/* 250 */           $$4++;
/*     */         }
/*     */       
/*     */       } 
/* 254 */     } catch (Exception $$10) {
/* 255 */       LOGGER.error("Error getting level list", $$10);
/* 256 */       this.error = true;
/*     */       
/*     */       return;
/*     */     } 
/* 260 */     if (!$$2.isNewLevelIdAcceptable($$0) || $$4 > 1) {
/* 261 */       String $$11 = $$0 + $$0;
/*     */       
/* 263 */       if (!$$2.isNewLevelIdAcceptable($$11)) {
/* 264 */         boolean $$12 = false;
/*     */         
/* 266 */         while (!$$12) {
/* 267 */           $$4++;
/* 268 */           $$11 = $$0 + $$0;
/*     */           
/* 270 */           if ($$2.isNewLevelIdAcceptable($$11)) {
/* 271 */             $$12 = true;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } else {
/* 276 */       $$13 = $$0;
/*     */     } 
/*     */     
/* 279 */     TarArchiveInputStream $$14 = null;
/* 280 */     File $$15 = new File((Minecraft.getInstance()).gameDirectory.getAbsolutePath(), "saves");
/*     */     try {
/* 282 */       $$15.mkdir();
/*     */       
/* 284 */       $$14 = new TarArchiveInputStream((InputStream)new GzipCompressorInputStream(new BufferedInputStream(new FileInputStream($$1))));
/*     */       
/* 286 */       TarArchiveEntry $$16 = $$14.getNextTarEntry();
/* 287 */       while ($$16 != null) {
/* 288 */         File $$17 = new File($$15, $$16.getName().replace("world", $$13));
/*     */         
/* 290 */         if ($$16.isDirectory()) {
/* 291 */           $$17.mkdirs();
/*     */         } else {
/* 293 */           $$17.createNewFile();
/*     */           
/* 295 */           FileOutputStream $$18 = new FileOutputStream($$17); 
/* 296 */           try { IOUtils.copy((InputStream)$$14, $$18);
/* 297 */             $$18.close(); } catch (Throwable throwable) { try { $$18.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */              throw throwable; }
/*     */         
/* 300 */         }  $$16 = $$14.getNextTarEntry();
/*     */       } 
/* 302 */     } catch (Exception $$22) {
/* 303 */       LOGGER.error("Error extracting world", $$22);
/* 304 */       this.error = true;
/*     */     } finally {
/* 306 */       if ($$14 != null) {
/* 307 */         $$14.close();
/*     */       }
/*     */       
/* 310 */       if ($$1 != null) {
/* 311 */         $$1.delete();
/*     */       }
/*     */       
/* 314 */       try { LevelStorageSource.LevelStorageAccess $$26 = $$2.validateAndCreateAccess($$13); 
/* 315 */         try { $$26.renameAndDropPlayer($$13);
/* 316 */           if ($$26 != null) $$26.close();  } catch (Throwable throwable) { if ($$26 != null) try { $$26.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException|net.minecraft.nbt.NbtException|net.minecraft.nbt.ReportedNbtException $$27)
/* 317 */       { LOGGER.error("Failed to modify unpacked realms level {}", $$13, $$27); }
/* 318 */       catch (ContentValidationException $$28)
/* 319 */       { LOGGER.warn("{}", $$28.getMessage()); }
/*     */ 
/*     */       
/* 322 */       this.resourcePackPath = new File($$15, $$13 + $$13 + "resources.zip");
/*     */     } 
/*     */   }
/*     */   
/*     */   private class ProgressListener implements ActionListener {
/*     */     private final String worldName;
/*     */     private final File tempFile;
/*     */     private final LevelStorageSource levelStorageSource;
/*     */     private final RealmsDownloadLatestWorldScreen.DownloadStatus downloadStatus;
/*     */     
/*     */     ProgressListener(String $$0, File $$1, LevelStorageSource $$2, RealmsDownloadLatestWorldScreen.DownloadStatus $$3) {
/* 333 */       this.worldName = $$0;
/* 334 */       this.tempFile = $$1;
/* 335 */       this.levelStorageSource = $$2;
/* 336 */       this.downloadStatus = $$3;
/*     */     }
/*     */ 
/*     */     
/*     */     public void actionPerformed(ActionEvent $$0) {
/* 341 */       this.downloadStatus.bytesWritten = ((FileDownload.DownloadCountingOutputStream)$$0.getSource()).getByteCount();
/*     */       
/* 343 */       if (this.downloadStatus.bytesWritten >= this.downloadStatus.totalBytes && !FileDownload.this.cancelled && !FileDownload.this.error)
/*     */         try {
/* 345 */           FileDownload.this.extracting = true;
/* 346 */           FileDownload.this.untarGzipArchive(this.worldName, this.tempFile, this.levelStorageSource);
/* 347 */         } catch (IOException $$1) {
/* 348 */           FileDownload.LOGGER.error("Error extracting archive", $$1);
/* 349 */           FileDownload.this.error = true;
/*     */         }  
/*     */     }
/*     */   }
/*     */   
/*     */   private class ResourcePackProgressListener
/*     */     implements ActionListener {
/*     */     private final File tempFile;
/*     */     private final RealmsDownloadLatestWorldScreen.DownloadStatus downloadStatus;
/*     */     private final WorldDownload worldDownload;
/*     */     
/*     */     ResourcePackProgressListener(File $$0, RealmsDownloadLatestWorldScreen.DownloadStatus $$1, WorldDownload $$2) {
/* 361 */       this.tempFile = $$0;
/* 362 */       this.downloadStatus = $$1;
/* 363 */       this.worldDownload = $$2;
/*     */     }
/*     */ 
/*     */     
/*     */     public void actionPerformed(ActionEvent $$0) {
/* 368 */       this.downloadStatus.bytesWritten = ((FileDownload.DownloadCountingOutputStream)$$0.getSource()).getByteCount();
/*     */       
/* 370 */       if (this.downloadStatus.bytesWritten >= this.downloadStatus.totalBytes && !FileDownload.this.cancelled)
/*     */         
/*     */         try {
/* 373 */           String $$1 = Hashing.sha1().hashBytes(Files.toByteArray(this.tempFile)).toString();
/*     */           
/* 375 */           if ($$1.equals(this.worldDownload.resourcePackHash)) {
/* 376 */             FileUtils.copyFile(this.tempFile, FileDownload.this.resourcePackPath);
/* 377 */             FileDownload.this.finished = true;
/*     */           } else {
/* 379 */             FileDownload.LOGGER.error("Resourcepack had wrong hash (expected {}, found {}). Deleting it.", this.worldDownload.resourcePackHash, $$1);
/* 380 */             FileUtils.deleteQuietly(this.tempFile);
/* 381 */             FileDownload.this.error = true;
/*     */           } 
/* 383 */         } catch (IOException $$2) {
/* 384 */           FileDownload.LOGGER.error("Error copying resourcepack file: {}", $$2.getMessage());
/* 385 */           FileDownload.this.error = true;
/*     */         }  
/*     */     }
/*     */   }
/*     */   
/*     */   private static class DownloadCountingOutputStream
/*     */     extends CountingOutputStream {
/*     */     @Nullable
/*     */     private ActionListener listener;
/*     */     
/*     */     public DownloadCountingOutputStream(OutputStream $$0) {
/* 396 */       super($$0);
/*     */     }
/*     */     
/*     */     public void setListener(ActionListener $$0) {
/* 400 */       this.listener = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void afterWrite(int $$0) throws IOException {
/* 405 */       super.afterWrite($$0);
/* 406 */       if (this.listener != null)
/* 407 */         this.listener.actionPerformed(new ActionEvent(this, 0, null)); 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\client\FileDownload.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */