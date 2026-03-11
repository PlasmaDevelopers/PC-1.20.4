/*    */ package net.minecraft.client.sounds;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ public class LoopingAudioStream implements AudioStream {
/*    */   private final AudioStreamProvider provider;
/*    */   private AudioStream stream;
/*    */   private final BufferedInputStream bufferedInputStream;
/*    */   
/*    */   @FunctionalInterface
/*    */   public static interface AudioStreamProvider {
/*    */     AudioStream create(InputStream param1InputStream) throws IOException;
/*    */   }
/*    */   
/*    */   private static class NoCloseBuffer extends FilterInputStream {
/*    */     NoCloseBuffer(InputStream $$0) {
/* 18 */       super($$0);
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     public void close() {}
/*    */   }
/*    */ 
/*    */   
/*    */   public LoopingAudioStream(AudioStreamProvider $$0, InputStream $$1) throws IOException {
/* 28 */     this.provider = $$0;
/* 29 */     this.bufferedInputStream = new BufferedInputStream($$1);
/* 30 */     this.bufferedInputStream.mark(2147483647);
/* 31 */     this.stream = $$0.create(new NoCloseBuffer(this.bufferedInputStream));
/*    */   }
/*    */ 
/*    */   
/*    */   public AudioFormat getFormat() {
/* 36 */     return this.stream.getFormat();
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer read(int $$0) throws IOException {
/* 41 */     ByteBuffer $$1 = this.stream.read($$0);
/* 42 */     if (!$$1.hasRemaining()) {
/* 43 */       this.stream.close();
/* 44 */       this.bufferedInputStream.reset();
/* 45 */       this.stream = this.provider.create(new NoCloseBuffer(this.bufferedInputStream));
/* 46 */       $$1 = this.stream.read($$0);
/*    */     } 
/*    */     
/* 49 */     return $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public void close() throws IOException {
/* 54 */     this.stream.close();
/* 55 */     this.bufferedInputStream.close();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\sounds\LoopingAudioStream.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */