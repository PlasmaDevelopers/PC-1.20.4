/*    */ package net.minecraft.network;
/*    */ 
/*    */ import com.mojang.logging.LogUtils;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.MessageToMessageCodec;
/*    */ import io.netty.util.Attribute;
/*    */ import io.netty.util.AttributeKey;
/*    */ import io.netty.util.ReferenceCountUtil;
/*    */ import java.util.List;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class PacketFlowValidator
/*    */   extends MessageToMessageCodec<Packet<?>, Packet<?>> {
/* 15 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   private final AttributeKey<ConnectionProtocol.CodecData<?>> decoderKey;
/*    */   private final AttributeKey<ConnectionProtocol.CodecData<?>> encoderKey;
/*    */   
/*    */   public PacketFlowValidator(AttributeKey<ConnectionProtocol.CodecData<?>> $$0, AttributeKey<ConnectionProtocol.CodecData<?>> $$1) {
/* 20 */     this.decoderKey = $$0;
/* 21 */     this.encoderKey = $$1;
/*    */   }
/*    */   
/*    */   private static void validatePacket(ChannelHandlerContext $$0, Packet<?> $$1, List<Object> $$2, AttributeKey<ConnectionProtocol.CodecData<?>> $$3) {
/* 25 */     Attribute<ConnectionProtocol.CodecData<?>> $$4 = $$0.channel().attr($$3);
/* 26 */     ConnectionProtocol.CodecData<?> $$5 = (ConnectionProtocol.CodecData)$$4.get();
/* 27 */     if (!$$5.isValidPacketType($$1)) {
/* 28 */       LOGGER.error("Unrecognized packet in pipeline {}:{} - {}", new Object[] { $$5.protocol().id(), $$5.flow(), $$1 });
/*    */     }
/*    */     
/* 31 */     ReferenceCountUtil.retain($$1);
/* 32 */     $$2.add($$1);
/*    */     
/* 34 */     ProtocolSwapHandler.swapProtocolIfNeeded($$4, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void decode(ChannelHandlerContext $$0, Packet<?> $$1, List<Object> $$2) throws Exception {
/* 39 */     validatePacket($$0, $$1, $$2, this.decoderKey);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void encode(ChannelHandlerContext $$0, Packet<?> $$1, List<Object> $$2) throws Exception {
/* 44 */     validatePacket($$0, $$1, $$2, this.encoderKey);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\PacketFlowValidator.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */