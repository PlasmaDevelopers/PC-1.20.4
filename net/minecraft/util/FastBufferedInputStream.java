/*    */ package net.minecraft.util;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FastBufferedInputStream
/*    */   extends InputStream
/*    */ {
/*    */   private static final int DEFAULT_BUFFER_SIZE = 8192;
/*    */   private final InputStream in;
/*    */   private final byte[] buffer;
/*    */   private int limit;
/*    */   private int position;
/*    */   
/*    */   public FastBufferedInputStream(InputStream $$0) {
/* 21 */     this($$0, 8192);
/*    */   }
/*    */   
/*    */   public FastBufferedInputStream(InputStream $$0, int $$1) {
/* 25 */     this.in = $$0;
/* 26 */     this.buffer = new byte[$$1];
/*    */   }
/*    */ 
/*    */   
/*    */   public int read() throws IOException {
/* 31 */     if (this.position >= this.limit) {
/* 32 */       fill();
/* 33 */       if (this.position >= this.limit) {
/* 34 */         return -1;
/*    */       }
/*    */     } 
/* 37 */     return Byte.toUnsignedInt(this.buffer[this.position++]);
/*    */   }
/*    */ 
/*    */   
/*    */   public int read(byte[] $$0, int $$1, int $$2) throws IOException {
/* 42 */     int $$3 = bytesInBuffer();
/* 43 */     if ($$3 <= 0) {
/* 44 */       if ($$2 >= this.buffer.length) {
/* 45 */         return this.in.read($$0, $$1, $$2);
/*    */       }
/* 47 */       fill();
/* 48 */       $$3 = bytesInBuffer();
/* 49 */       if ($$3 <= 0) {
/* 50 */         return -1;
/*    */       }
/*    */     } 
/* 53 */     if ($$2 > $$3) {
/* 54 */       $$2 = $$3;
/*    */     }
/* 56 */     System.arraycopy(this.buffer, this.position, $$0, $$1, $$2);
/* 57 */     this.position += $$2;
/* 58 */     return $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public long skip(long $$0) throws IOException {
/* 63 */     if ($$0 <= 0L) {
/* 64 */       return 0L;
/*    */     }
/* 66 */     long $$1 = bytesInBuffer();
/* 67 */     if ($$1 <= 0L) {
/* 68 */       return this.in.skip($$0);
/*    */     }
/* 70 */     if ($$0 > $$1) {
/* 71 */       $$0 = $$1;
/*    */     }
/* 73 */     this.position = (int)(this.position + $$0);
/* 74 */     return $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public int available() throws IOException {
/* 79 */     return bytesInBuffer() + this.in.available();
/*    */   }
/*    */ 
/*    */   
/*    */   public void close() throws IOException {
/* 84 */     this.in.close();
/*    */   }
/*    */   
/*    */   private int bytesInBuffer() {
/* 88 */     return this.limit - this.position;
/*    */   }
/*    */   
/*    */   private void fill() throws IOException {
/* 92 */     this.limit = 0;
/* 93 */     this.position = 0;
/* 94 */     int $$0 = this.in.read(this.buffer, 0, this.buffer.length);
/* 95 */     if ($$0 > 0)
/* 96 */       this.limit = $$0; 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\FastBufferedInputStream.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */