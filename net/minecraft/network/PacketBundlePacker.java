/*    */ package net.minecraft.network;
/*    */ 
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.DecoderException;
/*    */ import io.netty.handler.codec.MessageToMessageDecoder;
/*    */ import io.netty.util.AttributeKey;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.network.protocol.BundlerInfo;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ 
/*    */ public class PacketBundlePacker
/*    */   extends MessageToMessageDecoder<Packet<?>>
/*    */ {
/*    */   @Nullable
/*    */   private BundlerInfo.Bundler currentBundler;
/*    */   @Nullable
/*    */   private BundlerInfo infoForCurrentBundler;
/*    */   private final AttributeKey<? extends BundlerInfo.Provider> bundlerAttributeKey;
/*    */   
/*    */   public PacketBundlePacker(AttributeKey<? extends BundlerInfo.Provider> $$0) {
/* 22 */     this.bundlerAttributeKey = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void decode(ChannelHandlerContext $$0, Packet<?> $$1, List<Object> $$2) throws Exception {
/* 27 */     BundlerInfo.Provider $$3 = (BundlerInfo.Provider)$$0.channel().attr(this.bundlerAttributeKey).get();
/* 28 */     if ($$3 == null) {
/* 29 */       throw new DecoderException("Bundler not configured: " + $$1);
/*    */     }
/* 31 */     BundlerInfo $$4 = $$3.bundlerInfo();
/*    */     
/* 33 */     if (this.currentBundler != null) {
/* 34 */       if (this.infoForCurrentBundler != $$4) {
/* 35 */         throw new DecoderException("Bundler handler changed during bundling");
/*    */       }
/* 37 */       Packet<?> $$5 = this.currentBundler.addPacket($$1);
/* 38 */       if ($$5 != null) {
/* 39 */         this.infoForCurrentBundler = null;
/* 40 */         this.currentBundler = null;
/* 41 */         $$2.add($$5);
/*    */       } 
/*    */     } else {
/* 44 */       BundlerInfo.Bundler $$6 = $$4.startPacketBundling($$1);
/* 45 */       if ($$6 != null) {
/* 46 */         this.currentBundler = $$6;
/* 47 */         this.infoForCurrentBundler = $$4;
/*    */       } else {
/* 49 */         $$2.add($$1);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\PacketBundlePacker.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */