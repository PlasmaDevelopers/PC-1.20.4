/*    */ package net.minecraft.util.eventlog;
/*    */ import com.google.gson.Gson;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonIOException;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.DynamicOps;
/*    */ import java.io.Closeable;
/*    */ import java.io.IOException;
/*    */ import java.io.Writer;
/*    */ import java.nio.channels.Channels;
/*    */ import java.nio.channels.FileChannel;
/*    */ import java.nio.charset.StandardCharsets;
/*    */ import java.nio.file.OpenOption;
/*    */ import java.nio.file.Path;
/*    */ import java.nio.file.StandardOpenOption;
/*    */ import java.util.concurrent.atomic.AtomicInteger;
/*    */ import java.util.function.Function;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.Util;
/*    */ 
/*    */ public class JsonEventLog<T> implements Closeable {
/* 22 */   private static final Gson GSON = new Gson();
/*    */   
/*    */   private final Codec<T> codec;
/*    */   
/*    */   final FileChannel channel;
/* 27 */   private final AtomicInteger referenceCount = new AtomicInteger(1);
/*    */   
/*    */   public JsonEventLog(Codec<T> $$0, FileChannel $$1) {
/* 30 */     this.codec = $$0;
/* 31 */     this.channel = $$1;
/*    */   }
/*    */   
/*    */   public static <T> JsonEventLog<T> open(Codec<T> $$0, Path $$1) throws IOException {
/* 35 */     FileChannel $$2 = FileChannel.open($$1, new OpenOption[] { StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE });
/* 36 */     return new JsonEventLog<>($$0, $$2);
/*    */   }
/*    */   
/*    */   public void write(T $$0) throws IOException, JsonIOException {
/* 40 */     JsonElement $$1 = (JsonElement)Util.getOrThrow(this.codec.encodeStart((DynamicOps)JsonOps.INSTANCE, $$0), IOException::new);
/*    */     
/* 42 */     this.channel.position(this.channel.size());
/* 43 */     Writer $$2 = Channels.newWriter(this.channel, StandardCharsets.UTF_8);
/* 44 */     GSON.toJson($$1, $$2);
/*    */     
/* 46 */     $$2.write(10);
/* 47 */     $$2.flush();
/*    */   }
/*    */   
/*    */   public JsonEventLogReader<T> openReader() throws IOException {
/* 51 */     if (this.referenceCount.get() <= 0) {
/* 52 */       throw new IOException("Event log has already been closed");
/*    */     }
/* 54 */     this.referenceCount.incrementAndGet();
/*    */     
/* 56 */     final JsonEventLogReader<T> reader = JsonEventLogReader.create(this.codec, Channels.newReader(this.channel, StandardCharsets.UTF_8));
/* 57 */     return new JsonEventLogReader<T>()
/*    */       {
/*    */         private volatile long position;
/*    */         
/*    */         @Nullable
/*    */         public T next() throws IOException {
/*    */           try {
/* 64 */             JsonEventLog.this.channel.position(this.position);
/* 65 */             return (T)reader.next();
/*    */           } finally {
/* 67 */             this.position = JsonEventLog.this.channel.position();
/*    */           } 
/*    */         }
/*    */ 
/*    */         
/*    */         public void close() throws IOException {
/* 73 */           JsonEventLog.this.releaseReference();
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */   
/*    */   public void close() throws IOException {
/* 80 */     releaseReference();
/*    */   }
/*    */   
/*    */   void releaseReference() throws IOException {
/* 84 */     if (this.referenceCount.decrementAndGet() <= 0)
/* 85 */       this.channel.close(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\eventlog\JsonEventLog.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */