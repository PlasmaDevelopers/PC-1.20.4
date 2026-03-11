/*     */ package net.minecraft.server.packs;
/*     */ 
/*     */ import com.mojang.logging.LogUtils;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.DirectoryNotEmptyException;
/*     */ import java.nio.file.FileVisitResult;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.NoSuchFileException;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.SimpleFileVisitor;
/*     */ import java.nio.file.attribute.BasicFileAttributes;
/*     */ import java.nio.file.attribute.FileTime;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ 
/*     */ public class DownloadCacheCleaner
/*     */ {
/*  25 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   private static final class PathAndTime extends Record { final Path path; private final FileTime modifiedTime;
/*  27 */     PathAndTime(Path $$0, FileTime $$1) { this.path = $$0; this.modifiedTime = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/server/packs/DownloadCacheCleaner$PathAndTime;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #27	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*  27 */       //   0	7	0	this	Lnet/minecraft/server/packs/DownloadCacheCleaner$PathAndTime; } public Path path() { return this.path; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/server/packs/DownloadCacheCleaner$PathAndTime;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #27	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/server/packs/DownloadCacheCleaner$PathAndTime; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/server/packs/DownloadCacheCleaner$PathAndTime;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #27	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/server/packs/DownloadCacheCleaner$PathAndTime;
/*  27 */       //   0	8	1	$$0	Ljava/lang/Object; } public FileTime modifiedTime() { return this.modifiedTime; }
/*  28 */      public static final Comparator<PathAndTime> NEWEST_FIRST = Comparator.<PathAndTime, Comparable>comparing(PathAndTime::modifiedTime).reversed(); }
/*     */   private static final class PathAndPriority extends Record { final Path path; final int removalPriority;
/*     */     
/*  31 */     PathAndPriority(Path $$0, int $$1) { this.path = $$0; this.removalPriority = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/server/packs/DownloadCacheCleaner$PathAndPriority;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #31	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/server/packs/DownloadCacheCleaner$PathAndPriority; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/server/packs/DownloadCacheCleaner$PathAndPriority;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #31	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/server/packs/DownloadCacheCleaner$PathAndPriority; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/server/packs/DownloadCacheCleaner$PathAndPriority;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #31	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/server/packs/DownloadCacheCleaner$PathAndPriority;
/*  31 */       //   0	8	1	$$0	Ljava/lang/Object; } public Path path() { return this.path; } public int removalPriority() { return this.removalPriority; }
/*  32 */      public static final Comparator<PathAndPriority> HIGHEST_PRIORITY_FIRST = Comparator.<PathAndPriority, Comparable>comparing(PathAndPriority::removalPriority).reversed(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void vacuumCacheDir(Path $$0, int $$1) {
/*     */     try {
/*  44 */       List<PathAndTime> $$2 = listFilesWithModificationTimes($$0);
/*  45 */       int $$3 = $$2.size() - $$1;
/*  46 */       if ($$3 <= 0) {
/*     */         return;
/*     */       }
/*     */       
/*  50 */       $$2.sort(PathAndTime.NEWEST_FIRST);
/*  51 */       List<PathAndPriority> $$4 = prioritizeFilesInDirs($$2);
/*     */       
/*  53 */       Collections.reverse($$4);
/*  54 */       $$4.sort(PathAndPriority.HIGHEST_PRIORITY_FIRST);
/*     */       
/*  56 */       Set<Path> $$5 = new HashSet<>();
/*  57 */       for (int $$6 = 0; $$6 < $$3; $$6++) {
/*  58 */         PathAndPriority $$7 = $$4.get($$6);
/*  59 */         Path $$8 = $$7.path;
/*     */         try {
/*  61 */           Files.delete($$8);
/*     */ 
/*     */           
/*  64 */           if ($$7.removalPriority == 0) {
/*  65 */             $$5.add($$8.getParent());
/*     */           }
/*  67 */         } catch (IOException $$9) {
/*     */           
/*  69 */           LOGGER.warn("Failed to delete cache file {}", $$8, $$9);
/*     */         } 
/*     */       } 
/*  72 */       $$5.remove($$0);
/*  73 */       for (Path $$10 : $$5) {
/*     */         try {
/*  75 */           Files.delete($$10);
/*  76 */         } catch (DirectoryNotEmptyException directoryNotEmptyException) {
/*     */         
/*  78 */         } catch (IOException $$11) {
/*  79 */           LOGGER.warn("Failed to delete empty(?) cache directory {}", $$10, $$11);
/*     */         }
/*     */       
/*     */       } 
/*  83 */     } catch (IOException|java.io.UncheckedIOException $$12) {
/*  84 */       LOGGER.error("Failed to vacuum cache dir {}", $$0, $$12);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static List<PathAndTime> listFilesWithModificationTimes(final Path cacheDir) throws IOException {
/*     */     try {
/*  90 */       final List<PathAndTime> unsortedFiles = new ArrayList<>();
/*  91 */       Files.walkFileTree(cacheDir, new SimpleFileVisitor<Path>()
/*     */           {
/*     */             public FileVisitResult visitFile(Path $$0, BasicFileAttributes $$1)
/*     */             {
/*  95 */               if ($$1.isRegularFile() && !$$0.getParent().equals(cacheDir)) {
/*     */ 
/*     */                 
/*  98 */                 FileTime $$2 = $$1.lastModifiedTime();
/*  99 */                 unsortedFiles.add(new DownloadCacheCleaner.PathAndTime($$0, $$2));
/*     */               } 
/* 101 */               return FileVisitResult.CONTINUE;
/*     */             }
/*     */           });
/* 104 */       return $$1;
/* 105 */     } catch (NoSuchFileException $$2) {
/*     */       
/* 107 */       return List.of();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static List<PathAndPriority> prioritizeFilesInDirs(List<PathAndTime> $$0) {
/* 112 */     List<PathAndPriority> $$1 = new ArrayList<>();
/*     */ 
/*     */     
/* 115 */     Object2IntOpenHashMap<Path> $$2 = new Object2IntOpenHashMap();
/* 116 */     for (PathAndTime $$3 : $$0) {
/* 117 */       int $$4 = $$2.addTo($$3.path.getParent(), 1);
/* 118 */       $$1.add(new PathAndPriority($$3.path, $$4));
/*     */     } 
/*     */     
/* 121 */     return $$1;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\DownloadCacheCleaner.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */