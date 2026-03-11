/*    */ package net.minecraft.network;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import javax.crypto.Cipher;
/*    */ import javax.crypto.ShortBufferException;
/*    */ 
/*    */ public class CipherBase
/*    */ {
/*    */   private final Cipher cipher;
/* 11 */   private byte[] heapIn = new byte[0];
/* 12 */   private byte[] heapOut = new byte[0];
/*    */   
/*    */   protected CipherBase(Cipher $$0) {
/* 15 */     this.cipher = $$0;
/*    */   }
/*    */   
/*    */   private byte[] bufToByte(ByteBuf $$0) {
/* 19 */     int $$1 = $$0.readableBytes();
/* 20 */     if (this.heapIn.length < $$1) {
/* 21 */       this.heapIn = new byte[$$1];
/*    */     }
/* 23 */     $$0.readBytes(this.heapIn, 0, $$1);
/* 24 */     return this.heapIn;
/*    */   }
/*    */   
/*    */   protected ByteBuf decipher(ChannelHandlerContext $$0, ByteBuf $$1) throws ShortBufferException {
/* 28 */     int $$2 = $$1.readableBytes();
/* 29 */     byte[] $$3 = bufToByte($$1);
/*    */     
/* 31 */     ByteBuf $$4 = $$0.alloc().heapBuffer(this.cipher.getOutputSize($$2));
/* 32 */     $$4.writerIndex(this.cipher.update($$3, 0, $$2, $$4.array(), $$4.arrayOffset()));
/*    */     
/* 34 */     return $$4;
/*    */   }
/*    */   
/*    */   protected void encipher(ByteBuf $$0, ByteBuf $$1) throws ShortBufferException {
/* 38 */     int $$2 = $$0.readableBytes();
/* 39 */     byte[] $$3 = bufToByte($$0);
/*    */     
/* 41 */     int $$4 = this.cipher.getOutputSize($$2);
/* 42 */     if (this.heapOut.length < $$4) {
/* 43 */       this.heapOut = new byte[$$4];
/*    */     }
/* 45 */     $$1.writeBytes(this.heapOut, 0, this.cipher.update($$3, 0, $$2, this.heapOut));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\CipherBase.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */