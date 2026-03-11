/*    */ package net.minecraft.util;
/*    */ import com.mojang.logging.LogUtils;
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.io.UncheckedIOException;
/*    */ import java.nio.charset.StandardCharsets;
/*    */ import java.nio.file.FileSystem;
/*    */ import java.nio.file.Files;
/*    */ import java.nio.file.Path;
/*    */ import java.nio.file.attribute.BasicFileAttributes;
/*    */ import java.nio.file.attribute.FileAttribute;
/*    */ import java.util.Map;
/*    */ import java.util.stream.Collector;
/*    */ import java.util.stream.Collectors;
/*    */ import java.util.stream.Stream;
/*    */ import net.minecraft.Util;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class FileZipper implements Closeable {
/* 20 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/*    */   private final Path outputFile;
/*    */   private final Path tempFile;
/*    */   private final FileSystem fs;
/*    */   
/*    */   public FileZipper(Path $$0) {
/* 27 */     this.outputFile = $$0;
/* 28 */     this.tempFile = $$0.resolveSibling($$0.getFileName().toString() + "_tmp");
/*    */     try {
/* 30 */       this.fs = Util.ZIP_FILE_SYSTEM_PROVIDER.newFileSystem(this.tempFile, (Map<String, ?>)ImmutableMap.of("create", "true"));
/* 31 */     } catch (IOException $$1) {
/* 32 */       throw new UncheckedIOException($$1);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void add(Path $$0, String $$1) {
/*    */     try {
/* 38 */       Path $$2 = this.fs.getPath(File.separator, new String[0]);
/* 39 */       Path $$3 = $$2.resolve($$0.toString());
/*    */       
/* 41 */       Files.createDirectories($$3.getParent(), (FileAttribute<?>[])new FileAttribute[0]);
/* 42 */       Files.write($$3, $$1.getBytes(StandardCharsets.UTF_8), new java.nio.file.OpenOption[0]);
/* 43 */     } catch (IOException $$4) {
/* 44 */       throw new UncheckedIOException($$4);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void add(Path $$0, File $$1) {
/*    */     try {
/* 50 */       Path $$2 = this.fs.getPath(File.separator, new String[0]);
/* 51 */       Path $$3 = $$2.resolve($$0.toString());
/*    */       
/* 53 */       Files.createDirectories($$3.getParent(), (FileAttribute<?>[])new FileAttribute[0]);
/* 54 */       Files.copy($$1.toPath(), $$3, new java.nio.file.CopyOption[0]);
/* 55 */     } catch (IOException $$4) {
/* 56 */       throw new UncheckedIOException($$4);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void add(Path $$0) {
/*    */     
/* 62 */     try { Path $$1 = this.fs.getPath(File.separator, new String[0]);
/*    */       
/* 64 */       if (Files.isRegularFile($$0, new java.nio.file.LinkOption[0])) {
/* 65 */         Path $$2 = $$1.resolve($$0.getParent().relativize($$0).toString());
/* 66 */         Files.copy($$2, $$0, new java.nio.file.CopyOption[0]);
/*    */         
/*    */         return;
/*    */       } 
/* 70 */       Stream<Path> $$3 = Files.find($$0, 2147483647, ($$0, $$1) -> $$1.isRegularFile(), new java.nio.file.FileVisitOption[0]); 
/* 71 */       try { for (Path $$4 : $$3.collect((Collector)Collectors.toList())) {
/* 72 */           Path $$5 = $$1.resolve($$0.relativize($$4).toString());
/* 73 */           Files.createDirectories($$5.getParent(), (FileAttribute<?>[])new FileAttribute[0]);
/* 74 */           Files.copy($$4, $$5, new java.nio.file.CopyOption[0]);
/*    */         } 
/* 76 */         if ($$3 != null) $$3.close();  } catch (Throwable throwable) { if ($$3 != null)
/* 77 */           try { $$3.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException $$6)
/* 78 */     { throw new UncheckedIOException($$6); }
/*    */   
/*    */   }
/*    */ 
/*    */   
/*    */   public void close() {
/*    */     try {
/* 85 */       this.fs.close();
/* 86 */       Files.move(this.tempFile, this.outputFile, new java.nio.file.CopyOption[0]);
/* 87 */       LOGGER.info("Compressed to {}", this.outputFile);
/* 88 */     } catch (IOException $$0) {
/* 89 */       throw new UncheckedIOException($$0);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\FileZipper.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */