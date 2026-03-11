/*    */ package net.minecraft.client.multiplayer;
/*    */ 
/*    */ import com.google.common.base.Splitter;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelFutureListener;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.channel.SimpleChannelInboundHandler;
/*    */ import io.netty.util.concurrent.GenericFutureListener;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.multiplayer.resolver.ServerAddress;
/*    */ import net.minecraft.server.network.LegacyProtocolUtils;
/*    */ import net.minecraft.util.Mth;
/*    */ 
/*    */ public class LegacyServerPinger extends SimpleChannelInboundHandler<ByteBuf> {
/* 15 */   private static final Splitter SPLITTER = Splitter.on(false).limit(6);
/*    */   
/*    */   private final ServerAddress address;
/*    */   private final Output output;
/*    */   
/*    */   public LegacyServerPinger(ServerAddress $$0, Output $$1) {
/* 21 */     this.address = $$0;
/* 22 */     this.output = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public void channelActive(ChannelHandlerContext $$0) throws Exception {
/* 27 */     super.channelActive($$0);
/* 28 */     ByteBuf $$1 = $$0.alloc().buffer();
/*    */     try {
/* 30 */       $$1.writeByte(254);
/* 31 */       $$1.writeByte(1);
/*    */ 
/*    */ 
/*    */       
/* 35 */       $$1.writeByte(250);
/* 36 */       LegacyProtocolUtils.writeLegacyString($$1, "MC|PingHost");
/* 37 */       int $$2 = $$1.writerIndex();
/* 38 */       $$1.writeShort(0);
/* 39 */       int $$3 = $$1.writerIndex();
/* 40 */       $$1.writeByte(127);
/* 41 */       LegacyProtocolUtils.writeLegacyString($$1, this.address.getHost());
/* 42 */       $$1.writeInt(this.address.getPort());
/* 43 */       int $$4 = $$1.writerIndex() - $$3;
/* 44 */       $$1.setShort($$2, $$4);
/*    */       
/* 46 */       $$0.channel().writeAndFlush($$1).addListener((GenericFutureListener)ChannelFutureListener.CLOSE_ON_FAILURE);
/* 47 */     } catch (Exception $$5) {
/* 48 */       $$1.release();
/* 49 */       throw $$5;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected void channelRead0(ChannelHandlerContext $$0, ByteBuf $$1) {
/* 55 */     short $$2 = $$1.readUnsignedByte();
/*    */     
/* 57 */     if ($$2 == 255) {
/* 58 */       String $$3 = LegacyProtocolUtils.readLegacyString($$1);
/* 59 */       List<String> $$4 = SPLITTER.splitToList($$3);
/*    */       
/* 61 */       if ("§1".equals($$4.get(0))) {
/* 62 */         int $$5 = Mth.getInt($$4.get(1), 0);
/* 63 */         String $$6 = $$4.get(2);
/* 64 */         String $$7 = $$4.get(3);
/* 65 */         int $$8 = Mth.getInt($$4.get(4), -1);
/* 66 */         int $$9 = Mth.getInt($$4.get(5), -1);
/*    */         
/* 68 */         this.output.handleResponse($$5, $$6, $$7, $$8, $$9);
/*    */       } 
/*    */     } 
/*    */     
/* 72 */     $$0.close();
/*    */   }
/*    */ 
/*    */   
/*    */   public void exceptionCaught(ChannelHandlerContext $$0, Throwable $$1) {
/* 77 */     $$0.close();
/*    */   }
/*    */   
/*    */   @FunctionalInterface
/*    */   public static interface Output {
/*    */     void handleResponse(int param1Int1, String param1String1, String param1String2, int param1Int2, int param1Int3);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\multiplayer\LegacyServerPinger.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */