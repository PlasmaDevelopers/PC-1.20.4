/*    */ package net.minecraft.network;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.buffer.Unpooled;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.ByteToMessageDecoder;
/*    */ import io.netty.handler.codec.CorruptedFrameException;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ public class Varint21FrameDecoder
/*    */   extends ByteToMessageDecoder
/*    */ {
/*    */   private static final int MAX_VARINT21_BYTES = 3;
/* 16 */   private final ByteBuf helperBuf = Unpooled.directBuffer(3);
/*    */   @Nullable
/*    */   private final BandwidthDebugMonitor monitor;
/*    */   
/*    */   public Varint21FrameDecoder(@Nullable BandwidthDebugMonitor $$0) {
/* 21 */     this.monitor = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void handlerRemoved0(ChannelHandlerContext $$0) {
/* 26 */     this.helperBuf.release();
/*    */   }
/*    */   
/*    */   private static boolean copyVarint(ByteBuf $$0, ByteBuf $$1) {
/* 30 */     for (int $$2 = 0; $$2 < 3; $$2++) {
/* 31 */       if (!$$0.isReadable()) {
/* 32 */         return false;
/*    */       }
/*    */       
/* 35 */       byte $$3 = $$0.readByte();
/* 36 */       $$1.writeByte($$3);
/*    */       
/* 38 */       if (!VarInt.hasContinuationBit($$3)) {
/* 39 */         return true;
/*    */       }
/*    */     } 
/*    */     
/* 43 */     throw new CorruptedFrameException("length wider than 21-bit");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void decode(ChannelHandlerContext $$0, ByteBuf $$1, List<Object> $$2) {
/* 49 */     $$1.markReaderIndex();
/*    */     
/* 51 */     this.helperBuf.clear();
/*    */     
/* 53 */     if (!copyVarint($$1, this.helperBuf)) {
/* 54 */       $$1.resetReaderIndex();
/*    */       
/*    */       return;
/*    */     } 
/* 58 */     int $$3 = VarInt.read(this.helperBuf);
/*    */     
/* 60 */     if ($$1.readableBytes() < $$3) {
/* 61 */       $$1.resetReaderIndex();
/*    */       
/*    */       return;
/*    */     } 
/* 65 */     if (this.monitor != null) {
/* 66 */       this.monitor.onReceive($$3 + VarInt.getByteSize($$3));
/*    */     }
/*    */     
/* 69 */     $$2.add($$1.readBytes($$3));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\Varint21FrameDecoder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */