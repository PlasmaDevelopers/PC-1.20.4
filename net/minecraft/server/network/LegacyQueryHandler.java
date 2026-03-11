/*     */ package net.minecraft.server.network;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelInboundHandlerAdapter;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import java.net.SocketAddress;
/*     */ import java.util.Locale;
/*     */ import net.minecraft.server.ServerInfo;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class LegacyQueryHandler extends ChannelInboundHandlerAdapter {
/*  16 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private final ServerInfo server;
/*     */   
/*     */   public LegacyQueryHandler(ServerInfo $$0) {
/*  21 */     this.server = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelRead(ChannelHandlerContext $$0, Object $$1) {
/*  26 */     ByteBuf $$2 = (ByteBuf)$$1;
/*     */     
/*  28 */     $$2.markReaderIndex();
/*     */     
/*  30 */     boolean $$3 = true;
/*     */     
/*  32 */     try { if ($$2.readUnsignedByte() != 254) {
/*     */         return;
/*     */       }
/*     */       
/*  36 */       SocketAddress $$4 = $$0.channel().remoteAddress();
/*     */       
/*  38 */       int $$5 = $$2.readableBytes();
/*  39 */       if ($$5 == 0) {
/*  40 */         LOGGER.debug("Ping: (<1.3.x) from {}", $$4);
/*     */ 
/*     */         
/*  43 */         String $$6 = createVersion0Response(this.server);
/*  44 */         sendFlushAndClose($$0, createLegacyDisconnectPacket($$0.alloc(), $$6));
/*     */       } else {
/*     */         
/*  47 */         if ($$2.readUnsignedByte() != 1) {
/*     */           return;
/*     */         }
/*     */         
/*  51 */         if ($$2.isReadable()) {
/*     */ 
/*     */           
/*  54 */           if (!readCustomPayloadPacket($$2)) {
/*     */             return;
/*     */           }
/*  57 */           LOGGER.debug("Ping: (1.6) from {}", $$4);
/*     */         } else {
/*  59 */           LOGGER.debug("Ping: (1.4-1.5.x) from {}", $$4);
/*     */         } 
/*     */         
/*  62 */         String $$7 = createVersion1Response(this.server);
/*  63 */         sendFlushAndClose($$0, createLegacyDisconnectPacket($$0.alloc(), $$7));
/*     */       } 
/*     */       
/*  66 */       $$2.release();
/*  67 */       $$3 = false; }
/*  68 */     catch (RuntimeException runtimeException) {  }
/*     */     finally
/*  70 */     { if ($$3) {
/*     */         
/*  72 */         $$2.resetReaderIndex();
/*  73 */         $$0.channel().pipeline().remove((ChannelHandler)this);
/*  74 */         $$0.fireChannelRead($$1);
/*     */       }  }
/*     */   
/*     */   }
/*     */   
/*     */   private static boolean readCustomPayloadPacket(ByteBuf $$0) {
/*  80 */     short $$1 = $$0.readUnsignedByte();
/*  81 */     if ($$1 != 250) {
/*  82 */       return false;
/*     */     }
/*  84 */     String $$2 = LegacyProtocolUtils.readLegacyString($$0);
/*  85 */     if (!"MC|PingHost".equals($$2)) {
/*  86 */       return false;
/*     */     }
/*  88 */     int $$3 = $$0.readUnsignedShort();
/*  89 */     if ($$0.readableBytes() != $$3) {
/*  90 */       return false;
/*     */     }
/*  92 */     short $$4 = $$0.readUnsignedByte();
/*  93 */     if ($$4 < 73) {
/*  94 */       return false;
/*     */     }
/*  96 */     String $$5 = LegacyProtocolUtils.readLegacyString($$0);
/*  97 */     int $$6 = $$0.readInt();
/*  98 */     if ($$6 > 65535) {
/*  99 */       return false;
/*     */     }
/* 101 */     return true;
/*     */   }
/*     */   
/*     */   private static String createVersion0Response(ServerInfo $$0) {
/* 105 */     return String.format(Locale.ROOT, "%s§%d§%d", new Object[] { $$0.getMotd(), Integer.valueOf($$0.getPlayerCount()), Integer.valueOf($$0.getMaxPlayers()) });
/*     */   }
/*     */   
/*     */   private static String createVersion1Response(ServerInfo $$0) {
/* 109 */     return String.format(Locale.ROOT, "§1\000%d\000%s\000%s\000%d\000%d", new Object[] { Integer.valueOf(127), $$0.getServerVersion(), $$0.getMotd(), Integer.valueOf($$0.getPlayerCount()), Integer.valueOf($$0.getMaxPlayers()) });
/*     */   }
/*     */   
/*     */   private static void sendFlushAndClose(ChannelHandlerContext $$0, ByteBuf $$1) {
/* 113 */     $$0.pipeline().firstContext().writeAndFlush($$1).addListener((GenericFutureListener)ChannelFutureListener.CLOSE);
/*     */   }
/*     */   
/*     */   private static ByteBuf createLegacyDisconnectPacket(ByteBufAllocator $$0, String $$1) {
/* 117 */     ByteBuf $$2 = $$0.buffer();
/* 118 */     $$2.writeByte(255);
/* 119 */     LegacyProtocolUtils.writeLegacyString($$2, $$1);
/* 120 */     return $$2;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\network\LegacyQueryHandler.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */