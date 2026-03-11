/*    */ package net.minecraft.server.network;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.nio.charset.StandardCharsets;
/*    */ 
/*    */ public class LegacyProtocolUtils
/*    */ {
/*    */   public static final int CUSTOM_PAYLOAD_PACKET_ID = 250;
/*    */   public static final String CUSTOM_PAYLOAD_PACKET_PING_CHANNEL = "MC|PingHost";
/*    */   public static final int GET_INFO_PACKET_ID = 254;
/*    */   public static final int GET_INFO_PACKET_VERSION_1 = 1;
/*    */   public static final int DISCONNECT_PACKET_ID = 255;
/*    */   public static final int FAKE_PROTOCOL_VERSION = 127;
/*    */   
/*    */   public static void writeLegacyString(ByteBuf $$0, String $$1) {
/* 16 */     $$0.writeShort($$1.length());
/* 17 */     $$0.writeCharSequence($$1, StandardCharsets.UTF_16BE);
/*    */   }
/*    */   
/*    */   public static String readLegacyString(ByteBuf $$0) {
/* 21 */     int $$1 = $$0.readShort();
/* 22 */     int $$2 = $$1 * 2;
/* 23 */     String $$3 = $$0.toString($$0.readerIndex(), $$2, StandardCharsets.UTF_16BE);
/* 24 */     $$0.skipBytes($$2);
/* 25 */     return $$3;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\network\LegacyProtocolUtils.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */