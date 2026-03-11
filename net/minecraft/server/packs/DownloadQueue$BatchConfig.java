/*    */ package net.minecraft.server.packs;
/*    */ 
/*    */ import com.google.common.hash.HashFunction;
/*    */ import java.net.Proxy;
/*    */ import java.util.Map;
/*    */ import net.minecraft.util.HttpUtil;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class BatchConfig
/*    */   extends Record
/*    */ {
/*    */   final HashFunction hashFunction;
/*    */   final int maxSize;
/*    */   final Map<String, String> headers;
/*    */   final Proxy proxy;
/*    */   final HttpUtil.DownloadProgressListener listener;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/server/packs/DownloadQueue$BatchConfig;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #81	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/server/packs/DownloadQueue$BatchConfig;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/server/packs/DownloadQueue$BatchConfig;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #81	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/server/packs/DownloadQueue$BatchConfig;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/server/packs/DownloadQueue$BatchConfig;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #81	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/server/packs/DownloadQueue$BatchConfig;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   public BatchConfig(HashFunction $$0, int $$1, Map<String, String> $$2, Proxy $$3, HttpUtil.DownloadProgressListener $$4) {
/* 81 */     this.hashFunction = $$0; this.maxSize = $$1; this.headers = $$2; this.proxy = $$3; this.listener = $$4; } public HashFunction hashFunction() { return this.hashFunction; } public int maxSize() { return this.maxSize; } public Map<String, String> headers() { return this.headers; } public Proxy proxy() { return this.proxy; } public HttpUtil.DownloadProgressListener listener() { return this.listener; }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\DownloadQueue$BatchConfig.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */