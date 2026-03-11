/*    */ package net.minecraft.network;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.ByteToMessageDecoder;
/*    */ import io.netty.handler.codec.DecoderException;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.util.List;
/*    */ import java.util.zip.DataFormatException;
/*    */ import java.util.zip.Inflater;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CompressionDecoder
/*    */   extends ByteToMessageDecoder
/*    */ {
/*    */   public static final int MAXIMUM_COMPRESSED_LENGTH = 2097152;
/*    */   public static final int MAXIMUM_UNCOMPRESSED_LENGTH = 8388608;
/*    */   private final Inflater inflater;
/*    */   private int threshold;
/*    */   private boolean validateDecompressed;
/*    */   
/*    */   public CompressionDecoder(int $$0, boolean $$1) {
/* 25 */     this.threshold = $$0;
/* 26 */     this.validateDecompressed = $$1;
/* 27 */     this.inflater = new Inflater();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void decode(ChannelHandlerContext $$0, ByteBuf $$1, List<Object> $$2) throws Exception {
/* 32 */     if ($$1.readableBytes() == 0) {
/*    */       return;
/*    */     }
/*    */     
/* 36 */     int $$3 = VarInt.read($$1);
/*    */     
/* 38 */     if ($$3 == 0) {
/* 39 */       $$2.add($$1.readBytes($$1.readableBytes()));
/*    */       
/*    */       return;
/*    */     } 
/* 43 */     if (this.validateDecompressed) {
/* 44 */       if ($$3 < this.threshold)
/* 45 */         throw new DecoderException("Badly compressed packet - size of " + $$3 + " is below server threshold of " + this.threshold); 
/* 46 */       if ($$3 > 8388608) {
/* 47 */         throw new DecoderException("Badly compressed packet - size of " + $$3 + " is larger than protocol maximum of 8388608");
/*    */       }
/*    */     } 
/*    */     
/* 51 */     setupInflaterInput($$1);
/* 52 */     ByteBuf $$4 = inflate($$0, $$3);
/* 53 */     this.inflater.reset();
/*    */     
/* 55 */     $$2.add($$4);
/*    */   }
/*    */   
/*    */   private void setupInflaterInput(ByteBuf $$0) {
/*    */     ByteBuffer $$2;
/* 60 */     if ($$0.nioBufferCount() > 0) {
/* 61 */       ByteBuffer $$1 = $$0.nioBuffer();
/* 62 */       $$0.skipBytes($$0.readableBytes());
/*    */     } else {
/*    */       
/* 65 */       $$2 = ByteBuffer.allocateDirect($$0.readableBytes());
/* 66 */       $$0.readBytes($$2);
/* 67 */       $$2.flip();
/*    */     } 
/* 69 */     this.inflater.setInput($$2);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private ByteBuf inflate(ChannelHandlerContext $$0, int $$1) throws DataFormatException {
/* 75 */     ByteBuf $$2 = $$0.alloc().directBuffer($$1);
/*    */     try {
/* 77 */       ByteBuffer $$3 = $$2.internalNioBuffer(0, $$1);
/* 78 */       int $$4 = $$3.position();
/* 79 */       this.inflater.inflate($$3);
/* 80 */       int $$5 = $$3.position() - $$4;
/* 81 */       if ($$5 != $$1) {
/* 82 */         throw new DecoderException("Badly compressed packet - actual length of uncompressed payload " + $$5 + " is does not match declared size " + $$1);
/*    */       }
/* 84 */       $$2.writerIndex($$2.writerIndex() + $$5);
/* 85 */       return $$2;
/* 86 */     } catch (Exception $$6) {
/* 87 */       $$2.release();
/* 88 */       throw $$6;
/*    */     } 
/*    */   }
/*    */   
/*    */   public void setThreshold(int $$0, boolean $$1) {
/* 93 */     this.threshold = $$0;
/* 94 */     this.validateDecompressed = $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\CompressionDecoder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */