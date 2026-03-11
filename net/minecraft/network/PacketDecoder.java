/*    */ package net.minecraft.network;
/*    */ 
/*    */ import com.mojang.logging.LogUtils;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.ByteToMessageDecoder;
/*    */ import io.netty.util.Attribute;
/*    */ import io.netty.util.AttributeKey;
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.util.profiling.jfr.JvmProfiler;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class PacketDecoder
/*    */   extends ByteToMessageDecoder implements ProtocolSwapHandler {
/* 17 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   private final AttributeKey<ConnectionProtocol.CodecData<?>> codecKey;
/*    */   
/*    */   public PacketDecoder(AttributeKey<ConnectionProtocol.CodecData<?>> $$0) {
/* 21 */     this.codecKey = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void decode(ChannelHandlerContext $$0, ByteBuf $$1, List<Object> $$2) throws Exception {
/* 26 */     int $$3 = $$1.readableBytes();
/* 27 */     if ($$3 == 0) {
/*    */       return;
/*    */     }
/*    */     
/* 31 */     Attribute<ConnectionProtocol.CodecData<?>> $$4 = $$0.channel().attr(this.codecKey);
/* 32 */     ConnectionProtocol.CodecData<?> $$5 = (ConnectionProtocol.CodecData)$$4.get();
/* 33 */     FriendlyByteBuf $$6 = new FriendlyByteBuf($$1);
/* 34 */     int $$7 = $$6.readVarInt();
/* 35 */     Packet<?> $$8 = $$5.createPacket($$7, $$6);
/*    */     
/* 37 */     if ($$8 == null) {
/* 38 */       throw new IOException("Bad packet id " + $$7);
/*    */     }
/*    */     
/* 41 */     JvmProfiler.INSTANCE.onPacketReceived($$5
/* 42 */         .protocol(), $$7, $$0
/*    */         
/* 44 */         .channel().remoteAddress(), $$3);
/*    */ 
/*    */ 
/*    */     
/* 48 */     if ($$6.readableBytes() > 0) {
/* 49 */       throw new IOException("Packet " + $$5.protocol().id() + "/" + $$7 + " (" + $$8.getClass().getSimpleName() + ") was larger than I expected, found " + $$6.readableBytes() + " bytes extra whilst reading packet " + $$7);
/*    */     }
/* 51 */     $$2.add($$8);
/*    */ 
/*    */     
/* 54 */     if (LOGGER.isDebugEnabled()) {
/* 55 */       LOGGER.debug(Connection.PACKET_RECEIVED_MARKER, " IN: [{}:{}] {}", new Object[] { $$5.protocol().id(), Integer.valueOf($$7), $$8.getClass().getName() });
/*    */     }
/*    */     
/* 58 */     ProtocolSwapHandler.swapProtocolIfNeeded($$4, $$8);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\PacketDecoder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */