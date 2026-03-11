/*    */ package net.minecraft.network;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandler.Sharable;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.EncoderException;
/*    */ import io.netty.handler.codec.MessageToByteEncoder;
/*    */ 
/*    */ @Sharable
/*    */ public class Varint21LengthFieldPrepender
/*    */   extends MessageToByteEncoder<ByteBuf>
/*    */ {
/*    */   public static final int MAX_VARINT21_BYTES = 3;
/*    */   
/*    */   protected void encode(ChannelHandlerContext $$0, ByteBuf $$1, ByteBuf $$2) {
/* 16 */     int $$3 = $$1.readableBytes();
/* 17 */     int $$4 = VarInt.getByteSize($$3);
/*    */     
/* 19 */     if ($$4 > 3) {
/* 20 */       throw new EncoderException("unable to fit " + $$3 + " into 3");
/*    */     }
/*    */     
/* 23 */     $$2.ensureWritable($$4 + $$3);
/*    */     
/* 25 */     VarInt.write($$2, $$3);
/* 26 */     $$2.writeBytes($$1, $$1.readerIndex(), $$3);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\Varint21LengthFieldPrepender.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */