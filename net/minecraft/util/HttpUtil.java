/*     */ package net.minecraft.util;
/*     */ import com.google.common.hash.Funnels;
/*     */ import com.google.common.hash.HashCode;
/*     */ import com.google.common.hash.HashFunction;
/*     */ import com.google.common.hash.Hasher;
/*     */ import com.google.common.hash.PrimitiveSink;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.UncheckedIOException;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.Proxy;
/*     */ import java.net.ServerSocket;
/*     */ import java.net.URL;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.OpenOption;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.StandardOpenOption;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import java.nio.file.attribute.FileTime;
/*     */ import java.time.Instant;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.OptionalLong;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.FileUtil;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class HttpUtil {
/*  33 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Path downloadFile(Path $$0, URL $$1, Map<String, String> $$2, HashFunction $$3, @Nullable HashCode $$4, int $$5, Proxy $$6, DownloadProgressListener $$7) {
/*     */     Path $$13;
/*  49 */     HttpURLConnection $$8 = null;
/*  50 */     InputStream $$9 = null;
/*     */     
/*  52 */     $$7.requestStart();
/*     */ 
/*     */     
/*  55 */     if ($$4 != null) {
/*  56 */       Path $$10 = cachedFilePath($$0, $$4);
/*     */       try {
/*  58 */         if (checkExistingFile($$10, $$3, $$4)) {
/*  59 */           LOGGER.info("Returning cached file since actual hash matches requested");
/*  60 */           $$7.requestFinished(true);
/*     */           
/*  62 */           updateModificationTime($$10);
/*  63 */           return $$10;
/*     */         } 
/*  65 */       } catch (IOException $$11) {
/*  66 */         LOGGER.warn("Failed to check cached file {}", $$10, $$11);
/*     */       } 
/*     */       try {
/*  69 */         LOGGER.warn("Existing file {} not found or had mismatched hash", $$10);
/*  70 */         Files.deleteIfExists($$10);
/*  71 */       } catch (IOException $$12) {
/*  72 */         $$7.requestFinished(false);
/*  73 */         throw new UncheckedIOException("Failed to remove existing file " + $$10, $$12);
/*     */       } 
/*     */     } else {
/*  76 */       $$13 = null;
/*     */     } 
/*     */     
/*     */     try {
/*  80 */       $$8 = (HttpURLConnection)$$1.openConnection($$6);
/*  81 */       $$8.setInstanceFollowRedirects(true);
/*     */       
/*  83 */       Objects.requireNonNull($$8); $$2.forEach($$8::setRequestProperty);
/*     */       
/*  85 */       $$9 = $$8.getInputStream();
/*  86 */       long $$14 = $$8.getContentLengthLong();
/*  87 */       OptionalLong $$15 = ($$14 != -1L) ? OptionalLong.of($$14) : OptionalLong.empty();
/*     */       
/*  89 */       FileUtil.createDirectoriesSafe($$0);
/*     */       
/*  91 */       $$7.downloadStart($$15);
/*     */       
/*  93 */       if ($$15.isPresent() && 
/*  94 */         $$15.getAsLong() > $$5) {
/*  95 */         throw new IOException("Filesize is bigger than maximum allowed (file is " + $$15 + ", limit is " + $$5 + ")");
/*     */       }
/*     */ 
/*     */       
/*  99 */       if ($$13 != null) {
/* 100 */         HashCode $$16 = downloadAndHash($$3, $$5, $$7, $$9, $$13);
/* 101 */         if (!$$16.equals($$4)) {
/* 102 */           throw new IOException("Hash of downloaded file (" + $$16 + ") did not match requested (" + $$4 + ")");
/*     */         }
/* 104 */         $$7.requestFinished(true);
/* 105 */         return $$13;
/*     */       } 
/* 107 */       Path $$17 = Files.createTempFile($$0, "download", ".tmp", (FileAttribute<?>[])new FileAttribute[0]);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 125 */     catch (Throwable $$20) {
/* 126 */       if ($$8 != null) {
/* 127 */         InputStream $$21 = $$8.getErrorStream();
/* 128 */         if ($$21 != null) {
/*     */           try {
/* 130 */             LOGGER.error("HTTP response error: {}", IOUtils.toString($$21, StandardCharsets.UTF_8));
/* 131 */           } catch (Exception $$22) {
/* 132 */             LOGGER.error("Failed to read response from server");
/*     */           } 
/*     */         }
/*     */       } 
/* 136 */       $$7.requestFinished(false);
/* 137 */       throw new IllegalStateException("Failed to download file " + $$1, $$20);
/*     */     } finally {
/* 139 */       IOUtils.closeQuietly($$9);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void updateModificationTime(Path $$0) {
/*     */     try {
/* 145 */       Files.setLastModifiedTime($$0, FileTime.from(Instant.now()));
/* 146 */     } catch (IOException $$1) {
/* 147 */       LOGGER.warn("Failed to update modification time of {}", $$0, $$1);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static HashCode hashFile(Path $$0, HashFunction $$1) throws IOException {
/* 152 */     Hasher $$2 = $$1.newHasher();
/* 153 */     OutputStream $$3 = Funnels.asOutputStream((PrimitiveSink)$$2); 
/* 154 */     try { InputStream $$4 = Files.newInputStream($$0, new OpenOption[0]);
/*     */       
/* 156 */       try { $$4.transferTo($$3);
/* 157 */         if ($$4 != null) $$4.close();  } catch (Throwable throwable) { if ($$4 != null) try { $$4.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if ($$3 != null) $$3.close();  } catch (Throwable throwable) { if ($$3 != null)
/* 158 */         try { $$3.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  return $$2.hash();
/*     */   }
/*     */   
/*     */   private static boolean checkExistingFile(Path $$0, HashFunction $$1, HashCode $$2) throws IOException {
/* 162 */     if (Files.exists($$0, new java.nio.file.LinkOption[0])) {
/* 163 */       HashCode $$3 = hashFile($$0, $$1);
/* 164 */       if ($$3.equals($$2)) {
/* 165 */         return true;
/*     */       }
/* 167 */       LOGGER.warn("Mismatched hash of file {}, expected {} but found {}", new Object[] { $$0, $$2, $$3 });
/*     */     } 
/*     */     
/* 170 */     return false;
/*     */   }
/*     */   
/*     */   private static Path cachedFilePath(Path $$0, HashCode $$1) {
/* 174 */     return $$0.resolve($$1.toString());
/*     */   }
/*     */   
/*     */   private static HashCode downloadAndHash(HashFunction $$0, int $$1, DownloadProgressListener $$2, InputStream $$3, Path $$4) throws IOException {
/* 178 */     OutputStream $$5 = Files.newOutputStream($$4, new OpenOption[] { StandardOpenOption.CREATE }); 
/* 179 */     try { Hasher $$6 = $$0.newHasher();
/*     */       
/* 181 */       byte[] $$7 = new byte[8196];
/*     */       
/* 183 */       long $$8 = 0L; int $$9;
/* 184 */       while (($$9 = $$3.read($$7)) >= 0) {
/* 185 */         $$8 += $$9;
/* 186 */         $$2.downloadedBytes($$8);
/*     */         
/* 188 */         if ($$8 > $$1) {
/* 189 */           throw new IOException("Filesize was bigger than maximum allowed (got >= " + $$8 + ", limit was " + $$1 + ")");
/*     */         }
/*     */         
/* 192 */         if (Thread.interrupted()) {
/* 193 */           LOGGER.error("INTERRUPTED");
/* 194 */           throw new IOException("Download interrupted");
/*     */         } 
/*     */         
/* 197 */         $$5.write($$7, 0, $$9);
/* 198 */         $$6.putBytes($$7, 0, $$9);
/*     */       } 
/* 200 */       HashCode hashCode = $$6.hash();
/* 201 */       if ($$5 != null) $$5.close();  return hashCode; } catch (Throwable throwable) { if ($$5 != null)
/*     */         try { $$5.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/* 205 */      } public static int getAvailablePort() { try { ServerSocket $$0 = new ServerSocket(0); 
/* 206 */       try { int i = $$0.getLocalPort();
/* 207 */         $$0.close(); return i; } catch (Throwable throwable) { try { $$0.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (IOException $$1)
/* 208 */     { return 25564; }
/*     */      }
/*     */ 
/*     */   
/*     */   public static boolean isPortAvailable(int $$0) {
/* 213 */     if ($$0 < 0 || $$0 > 65535)
/* 214 */       return false; 
/*     */     
/* 216 */     try { ServerSocket $$1 = new ServerSocket($$0); 
/* 217 */       try { boolean bool = ($$1.getLocalPort() == $$0) ? true : false;
/* 218 */         $$1.close(); return bool; } catch (Throwable throwable) { try { $$1.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (IOException $$2)
/* 219 */     { return false; }
/*     */   
/*     */   }
/*     */   
/*     */   public static interface DownloadProgressListener {
/*     */     void requestStart();
/*     */     
/*     */     void downloadStart(OptionalLong param1OptionalLong);
/*     */     
/*     */     void downloadedBytes(long param1Long);
/*     */     
/*     */     void requestFinished(boolean param1Boolean);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\HttpUtil.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */