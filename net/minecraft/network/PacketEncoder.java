/*    */ package net.minecraft.network;
/*    */ 
/*    */ import com.mojang.logging.LogUtils;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.MessageToByteEncoder;
/*    */ import io.netty.util.Attribute;
/*    */ import io.netty.util.AttributeKey;
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.util.profiling.jfr.JvmProfiler;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class PacketEncoder
/*    */   extends MessageToByteEncoder<Packet<?>> {
/* 16 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/*    */   private final AttributeKey<ConnectionProtocol.CodecData<?>> codecKey;
/*    */   
/*    */   public PacketEncoder(AttributeKey<ConnectionProtocol.CodecData<?>> $$0) {
/* 21 */     this.codecKey = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void encode(ChannelHandlerContext $$0, Packet<?> $$1, ByteBuf $$2) throws Exception {
/* 26 */     Attribute<ConnectionProtocol.CodecData<?>> $$3 = $$0.channel().attr(this.codecKey);
/* 27 */     ConnectionProtocol.CodecData<?> $$4 = (ConnectionProtocol.CodecData)$$3.get();
/* 28 */     if ($$4 == null) {
/* 29 */       throw new RuntimeException("ConnectionProtocol unknown: " + $$1);
/*    */     }
/* 31 */     int $$5 = $$4.packetId($$1);
/*    */ 
/*    */     
/* 34 */     if (LOGGER.isDebugEnabled()) {
/* 35 */       LOGGER.debug(Connection.PACKET_SENT_MARKER, "OUT: [{}:{}] {}", new Object[] { $$4.protocol().id(), Integer.valueOf($$5), $$1.getClass().getName() });
/*    */     }
/*    */     
/* 38 */     if ($$5 == -1) {
/* 39 */       throw new IOException("Can't serialize unregistered packet");
/*    */     }
/*    */     
/* 42 */     FriendlyByteBuf $$6 = new FriendlyByteBuf($$2);
/* 43 */     $$6.writeVarInt($$5);
/*    */     
/*    */     try {
/* 46 */       int $$7 = $$6.writerIndex();
/* 47 */       $$1.write($$6);
/* 48 */       int $$8 = $$6.writerIndex() - $$7;
/* 49 */       if ($$8 > 8388608) {
/* 50 */         throw new IllegalArgumentException("Packet too big (is " + $$8 + ", should be less than 8388608): " + $$1);
/*    */       }
/*    */       
/* 53 */       JvmProfiler.INSTANCE.onPacketSent($$4
/* 54 */           .protocol(), $$5, $$0
/*    */           
/* 56 */           .channel().remoteAddress(), $$8);
/*    */     
/*    */     }
/* 59 */     catch (Throwable $$9) {
/* 60 */       LOGGER.error("Error receiving packet {}", Integer.valueOf($$5), $$9);
/* 61 */       if ($$1.isSkippable()) {
/* 62 */         throw new SkipPacketException($$9);
/*    */       }
/* 64 */       throw $$9;
/*    */     } finally {
/*    */       
/* 67 */       ProtocolSwapHandler.swapProtocolIfNeeded($$3, $$1);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\PacketEncoder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */