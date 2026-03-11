/*    */ package net.minecraft.network;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.MessageToByteEncoder;
/*    */ import java.util.zip.Deflater;
/*    */ 
/*    */ public class CompressionEncoder
/*    */   extends MessageToByteEncoder<ByteBuf> {
/* 10 */   private final byte[] encodeBuf = new byte[8192];
/*    */   private final Deflater deflater;
/*    */   private int threshold;
/*    */   
/*    */   public CompressionEncoder(int $$0) {
/* 15 */     this.threshold = $$0;
/* 16 */     this.deflater = new Deflater();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void encode(ChannelHandlerContext $$0, ByteBuf $$1, ByteBuf $$2) {
/* 21 */     int $$3 = $$1.readableBytes();
/*    */     
/* 23 */     if ($$3 < this.threshold) {
/* 24 */       VarInt.write($$2, 0);
/* 25 */       $$2.writeBytes($$1);
/*    */     } else {
/* 27 */       byte[] $$4 = new byte[$$3];
/* 28 */       $$1.readBytes($$4);
/*    */       
/* 30 */       VarInt.write($$2, $$4.length);
/*    */       
/* 32 */       this.deflater.setInput($$4, 0, $$3);
/* 33 */       this.deflater.finish();
/* 34 */       while (!this.deflater.finished()) {
/* 35 */         int $$5 = this.deflater.deflate(this.encodeBuf);
/* 36 */         $$2.writeBytes(this.encodeBuf, 0, $$5);
/*    */       } 
/* 38 */       this.deflater.reset();
/*    */     } 
/*    */   }
/*    */   
/*    */   public int getThreshold() {
/* 43 */     return this.threshold;
/*    */   }
/*    */   
/*    */   public void setThreshold(int $$0) {
/* 47 */     this.threshold = $$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\CompressionEncoder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */