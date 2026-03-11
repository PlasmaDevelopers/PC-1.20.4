/*     */ package net.minecraft.util.eventlog;
/*     */ 
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Reader;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.FileChannel;
/*     */ import java.nio.channels.FileLock;
/*     */ import java.nio.channels.ReadableByteChannel;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.OpenOption;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.StandardOpenOption;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import java.time.LocalDate;
/*     */ import java.time.format.DateTimeFormatter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.stream.Stream;
/*     */ import java.util.zip.GZIPInputStream;
/*     */ import java.util.zip.GZIPOutputStream;
/*     */ import javax.annotation.Nullable;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class EventLogDirectory {
/*  34 */   static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private static final int COMPRESS_BUFFER_SIZE = 4096;
/*     */   
/*     */   private static final String COMPRESSED_EXTENSION = ".gz";
/*     */   
/*     */   private final Path root;
/*     */   private final String extension;
/*     */   
/*     */   private EventLogDirectory(Path $$0, String $$1) {
/*  44 */     this.root = $$0;
/*  45 */     this.extension = $$1;
/*     */   }
/*     */   
/*     */   public static EventLogDirectory open(Path $$0, String $$1) throws IOException {
/*  49 */     Files.createDirectories($$0, (FileAttribute<?>[])new FileAttribute[0]);
/*  50 */     return new EventLogDirectory($$0, $$1);
/*     */   }
/*     */   
/*     */   public FileList listFiles() throws IOException {
/*  54 */     Stream<Path> $$0 = Files.list(this.root);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  59 */     try { FileList fileList = new FileList($$0.filter($$0 -> Files.isRegularFile($$0, new java.nio.file.LinkOption[0])).map(this::parseFile).filter(Objects::nonNull).toList());
/*     */       
/*  61 */       if ($$0 != null) $$0.close();  return fileList; }
/*     */     catch (Throwable throwable) { if ($$0 != null)
/*     */         try { $$0.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/*  66 */      } @Nullable private File parseFile(Path $$0) { String $$1 = $$0.getFileName().toString();
/*  67 */     int $$2 = $$1.indexOf('.');
/*  68 */     if ($$2 == -1) {
/*  69 */       return null;
/*     */     }
/*     */     
/*  72 */     FileId $$3 = FileId.parse($$1.substring(0, $$2));
/*  73 */     if ($$3 != null) {
/*  74 */       String $$4 = $$1.substring($$2);
/*  75 */       if ($$4.equals(this.extension))
/*  76 */         return new RawFile($$0, $$3); 
/*  77 */       if ($$4.equals(this.extension + ".gz")) {
/*  78 */         return new CompressedFile($$0, $$3);
/*     */       }
/*     */     } 
/*     */     
/*  82 */     return null; }
/*     */ 
/*     */   
/*     */   static void tryCompress(Path $$0, Path $$1) throws IOException {
/*  86 */     if (Files.exists($$1, new java.nio.file.LinkOption[0])) {
/*  87 */       throw new IOException("Compressed target file already exists: " + $$1);
/*     */     }
/*  89 */     FileChannel $$2 = FileChannel.open($$0, new OpenOption[] { StandardOpenOption.WRITE, StandardOpenOption.READ }); 
/*  90 */     try { FileLock $$3 = $$2.tryLock();
/*  91 */       if ($$3 == null)
/*     */       {
/*  93 */         throw new IOException("Raw log file is already locked, cannot compress: " + $$0);
/*     */       }
/*  95 */       writeCompressed($$2, $$1);
/*     */       
/*  97 */       $$2.truncate(0L);
/*  98 */       if ($$2 != null) $$2.close();  } catch (Throwable throwable) { if ($$2 != null)
/*  99 */         try { $$2.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  Files.delete($$0);
/*     */   }
/*     */   
/*     */   private static void writeCompressed(ReadableByteChannel $$0, Path $$1) throws IOException {
/* 103 */     OutputStream $$2 = new GZIPOutputStream(Files.newOutputStream($$1, new OpenOption[0])); 
/* 104 */     try { byte[] $$3 = new byte[4096];
/* 105 */       ByteBuffer $$4 = ByteBuffer.wrap($$3);
/* 106 */       while ($$0.read($$4) >= 0) {
/* 107 */         $$4.flip();
/* 108 */         $$2.write($$3, 0, $$4.limit());
/* 109 */         $$4.clear();
/*     */       } 
/* 111 */       $$2.close(); }
/*     */     catch (Throwable throwable) { try { $$2.close(); }
/*     */       catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */        throw throwable; }
/* 115 */      } public RawFile createNewFile(LocalDate $$0) throws IOException { int $$1 = 1;
/*     */     
/* 117 */     Set<FileId> $$2 = listFiles().ids();
/*     */     while (true) {
/* 119 */       FileId $$3 = new FileId($$0, $$1++);
/* 120 */       if (!$$2.contains($$3)) {
/* 121 */         RawFile $$4 = new RawFile(this.root.resolve($$3.toFileName(this.extension)), $$3);
/* 122 */         Files.createFile($$4.path(), (FileAttribute<?>[])new FileAttribute[0]);
/* 123 */         return $$4;
/*     */       } 
/*     */     }  }
/*     */   
/*     */   public static class FileList implements Iterable<File> { private final List<EventLogDirectory.File> files;
/*     */     
/*     */     FileList(List<EventLogDirectory.File> $$0) {
/* 130 */       this.files = new ArrayList<>($$0);
/*     */     }
/*     */     
/*     */     public FileList prune(LocalDate $$0, int $$1) {
/* 134 */       this.files.removeIf($$2 -> {
/*     */             EventLogDirectory.FileId $$3 = $$2.id();
/*     */             LocalDate $$4 = $$3.date().plusDays($$0);
/*     */             if (!$$1.isBefore($$4)) {
/*     */               try {
/*     */                 Files.delete($$2.path());
/*     */                 return true;
/* 141 */               } catch (IOException $$5) {
/*     */                 EventLogDirectory.LOGGER.warn("Failed to delete expired event log file: {}", $$2.path(), $$5);
/*     */               } 
/*     */             }
/*     */             return false;
/*     */           });
/* 147 */       return this;
/*     */     }
/*     */     
/*     */     public FileList compressAll() {
/* 151 */       ListIterator<EventLogDirectory.File> $$0 = this.files.listIterator();
/* 152 */       while ($$0.hasNext()) {
/* 153 */         EventLogDirectory.File $$1 = $$0.next();
/*     */         try {
/* 155 */           $$0.set($$1.compress());
/* 156 */         } catch (IOException $$2) {
/* 157 */           EventLogDirectory.LOGGER.warn("Failed to compress event log file: {}", $$1.path(), $$2);
/*     */         } 
/*     */       } 
/* 160 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Iterator<EventLogDirectory.File> iterator() {
/* 165 */       return this.files.iterator();
/*     */     }
/*     */     
/*     */     public Stream<EventLogDirectory.File> stream() {
/* 169 */       return this.files.stream();
/*     */     }
/*     */     
/*     */     public Set<EventLogDirectory.FileId> ids() {
/* 173 */       return (Set<EventLogDirectory.FileId>)this.files.stream().map(EventLogDirectory.File::id).collect(Collectors.toSet());
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class RawFile
/*     */     extends Record
/*     */     implements File
/*     */   {
/*     */     private final Path path;
/*     */     
/*     */     private final EventLogDirectory.FileId id;
/*     */ 
/*     */     
/*     */     public RawFile(Path $$0, EventLogDirectory.FileId $$1) {
/* 188 */       this.path = $$0; this.id = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/util/eventlog/EventLogDirectory$RawFile;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #188	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/util/eventlog/EventLogDirectory$RawFile; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/util/eventlog/EventLogDirectory$RawFile;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #188	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/util/eventlog/EventLogDirectory$RawFile; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/util/eventlog/EventLogDirectory$RawFile;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #188	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/util/eventlog/EventLogDirectory$RawFile;
/* 188 */       //   0	8	1	$$0	Ljava/lang/Object; } public Path path() { return this.path; } public EventLogDirectory.FileId id() { return this.id; }
/*     */      public FileChannel openChannel() throws IOException {
/* 190 */       return FileChannel.open(this.path, new OpenOption[] { StandardOpenOption.WRITE, StandardOpenOption.READ });
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public Reader openReader() throws IOException {
/* 196 */       return Files.exists(this.path, new java.nio.file.LinkOption[0]) ? Files.newBufferedReader(this.path) : null;
/*     */     }
/*     */ 
/*     */     
/*     */     public EventLogDirectory.CompressedFile compress() throws IOException {
/* 201 */       Path $$0 = this.path.resolveSibling(this.path.getFileName().toString() + ".gz");
/* 202 */       EventLogDirectory.tryCompress(this.path, $$0);
/* 203 */       return new EventLogDirectory.CompressedFile($$0, this.id);
/*     */     } }
/*     */   public static final class CompressedFile extends Record implements File { private final Path path; private final EventLogDirectory.FileId id;
/*     */     
/* 207 */     public CompressedFile(Path $$0, EventLogDirectory.FileId $$1) { this.path = $$0; this.id = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/util/eventlog/EventLogDirectory$CompressedFile;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #207	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 207 */       //   0	7	0	this	Lnet/minecraft/util/eventlog/EventLogDirectory$CompressedFile; } public Path path() { return this.path; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/util/eventlog/EventLogDirectory$CompressedFile;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #207	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/util/eventlog/EventLogDirectory$CompressedFile; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/util/eventlog/EventLogDirectory$CompressedFile;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #207	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/util/eventlog/EventLogDirectory$CompressedFile;
/* 207 */       //   0	8	1	$$0	Ljava/lang/Object; } public EventLogDirectory.FileId id() { return this.id; }
/*     */     
/*     */     @Nullable
/*     */     public Reader openReader() throws IOException {
/* 211 */       if (!Files.exists(this.path, new java.nio.file.LinkOption[0])) {
/* 212 */         return null;
/*     */       }
/* 214 */       return new BufferedReader(new InputStreamReader(new GZIPInputStream(Files.newInputStream(this.path, new OpenOption[0]))));
/*     */     }
/*     */ 
/*     */     
/*     */     public CompressedFile compress() {
/* 219 */       return this;
/*     */     } }
/*     */   public static final class FileId extends Record { private final LocalDate date; private final int index;
/*     */     
/* 223 */     public FileId(LocalDate $$0, int $$1) { this.date = $$0; this.index = $$1; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/util/eventlog/EventLogDirectory$FileId;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #223	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/util/eventlog/EventLogDirectory$FileId; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/util/eventlog/EventLogDirectory$FileId;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #223	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/util/eventlog/EventLogDirectory$FileId;
/* 223 */       //   0	8	1	$$0	Ljava/lang/Object; } public LocalDate date() { return this.date; } public int index() { return this.index; }
/* 224 */      private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.BASIC_ISO_DATE;
/*     */     
/*     */     @Nullable
/*     */     public static FileId parse(String $$0) {
/* 228 */       int $$1 = $$0.indexOf("-");
/* 229 */       if ($$1 == -1) {
/* 230 */         return null;
/*     */       }
/*     */       
/* 233 */       String $$2 = $$0.substring(0, $$1);
/* 234 */       String $$3 = $$0.substring($$1 + 1);
/*     */       
/*     */       try {
/* 237 */         return new FileId(
/* 238 */             LocalDate.parse($$2, DATE_FORMATTER), 
/* 239 */             Integer.parseInt($$3));
/*     */       }
/* 241 */       catch (NumberFormatException|java.time.format.DateTimeParseException $$4) {
/* 242 */         return null;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 248 */       return DATE_FORMATTER.format(this.date) + "-" + DATE_FORMATTER.format(this.date);
/*     */     }
/*     */     
/*     */     public String toFileName(String $$0) {
/* 252 */       return "" + this + this;
/*     */     } }
/*     */ 
/*     */   
/*     */   public static interface File {
/*     */     Path path();
/*     */     
/*     */     EventLogDirectory.FileId id();
/*     */     
/*     */     @Nullable
/*     */     Reader openReader() throws IOException;
/*     */     
/*     */     EventLogDirectory.CompressedFile compress() throws IOException;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\eventlog\EventLogDirectory.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */