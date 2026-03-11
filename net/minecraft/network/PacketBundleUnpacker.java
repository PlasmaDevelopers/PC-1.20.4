/*    */ package net.minecraft.network;
/*    */ 
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.EncoderException;
/*    */ import io.netty.handler.codec.MessageToMessageEncoder;
/*    */ import io.netty.util.AttributeKey;
/*    */ import java.util.List;
/*    */ import java.util.Objects;
/*    */ import net.minecraft.network.protocol.BundlerInfo;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ 
/*    */ public class PacketBundleUnpacker extends MessageToMessageEncoder<Packet<?>> {
/*    */   private final AttributeKey<? extends BundlerInfo.Provider> bundlerAttributeKey;
/*    */   
/*    */   public PacketBundleUnpacker(AttributeKey<? extends BundlerInfo.Provider> $$0) {
/* 16 */     this.bundlerAttributeKey = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void encode(ChannelHandlerContext $$0, Packet<?> $$1, List<Object> $$2) throws Exception {
/* 21 */     BundlerInfo.Provider $$3 = (BundlerInfo.Provider)$$0.channel().attr(this.bundlerAttributeKey).get();
/* 22 */     if ($$3 == null) {
/* 23 */       throw new EncoderException("Bundler not configured: " + $$1);
/*    */     }
/* 25 */     Objects.requireNonNull($$2); $$3.bundlerInfo().unbundlePacket($$1, $$2::add);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\PacketBundleUnpacker.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */