/*     */ package net.minecraft.util.eventlog;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ import java.nio.channels.FileChannel;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.OpenOption;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.StandardOpenOption;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class RawFile
/*     */   extends Record
/*     */   implements EventLogDirectory.File
/*     */ {
/*     */   private final Path path;
/*     */   private final EventLogDirectory.FileId id;
/*     */   
/*     */   public final String toString() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> toString : (Lnet/minecraft/util/eventlog/EventLogDirectory$RawFile;)Ljava/lang/String;
/*     */     //   6: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #188	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/util/eventlog/EventLogDirectory$RawFile;
/*     */   }
/*     */   
/*     */   public final int hashCode() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/util/eventlog/EventLogDirectory$RawFile;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #188	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/util/eventlog/EventLogDirectory$RawFile;
/*     */   }
/*     */   
/*     */   public final boolean equals(Object $$0) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lnet/minecraft/util/eventlog/EventLogDirectory$RawFile;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #188	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lnet/minecraft/util/eventlog/EventLogDirectory$RawFile;
/*     */     //   0	8	1	$$0	Ljava/lang/Object;
/*     */   }
/*     */   
/*     */   public RawFile(Path $$0, EventLogDirectory.FileId $$1) {
/* 188 */     this.path = $$0; this.id = $$1; } public Path path() { return this.path; } public EventLogDirectory.FileId id() { return this.id; }
/*     */    public FileChannel openChannel() throws IOException {
/* 190 */     return FileChannel.open(this.path, new OpenOption[] { StandardOpenOption.WRITE, StandardOpenOption.READ });
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Reader openReader() throws IOException {
/* 196 */     return Files.exists(this.path, new java.nio.file.LinkOption[0]) ? Files.newBufferedReader(this.path) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public EventLogDirectory.CompressedFile compress() throws IOException {
/* 201 */     Path $$0 = this.path.resolveSibling(this.path.getFileName().toString() + ".gz");
/* 202 */     EventLogDirectory.tryCompress(this.path, $$0);
/* 203 */     return new EventLogDirectory.CompressedFile($$0, this.id);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\eventlog\EventLogDirectory$RawFile.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */