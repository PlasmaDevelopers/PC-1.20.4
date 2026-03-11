/*    */ package net.minecraft.server.packs;
/*    */ 
/*    */ import java.nio.file.Path;
/*    */ import java.nio.file.attribute.FileTime;
/*    */ import java.util.Comparator;
/*    */ 
/*    */ 
/*    */ final class PathAndTime
/*    */   extends Record
/*    */ {
/*    */   final Path path;
/*    */   private final FileTime modifiedTime;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/server/packs/DownloadCacheCleaner$PathAndTime;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #27	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/server/packs/DownloadCacheCleaner$PathAndTime;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/server/packs/DownloadCacheCleaner$PathAndTime;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #27	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/server/packs/DownloadCacheCleaner$PathAndTime;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/server/packs/DownloadCacheCleaner$PathAndTime;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #27	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/server/packs/DownloadCacheCleaner$PathAndTime;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   PathAndTime(Path $$0, FileTime $$1) {
/* 27 */     this.path = $$0; this.modifiedTime = $$1; } public Path path() { return this.path; } public FileTime modifiedTime() { return this.modifiedTime; }
/* 28 */    public static final Comparator<PathAndTime> NEWEST_FIRST = Comparator.<PathAndTime, Comparable>comparing(PathAndTime::modifiedTime).reversed();
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\DownloadCacheCleaner$PathAndTime.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */