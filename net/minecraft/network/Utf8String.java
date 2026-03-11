/*    */ package net.minecraft.network;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.buffer.ByteBufUtil;
/*    */ import io.netty.handler.codec.DecoderException;
/*    */ import io.netty.handler.codec.EncoderException;
/*    */ import java.nio.charset.StandardCharsets;
/*    */ 
/*    */ 
/*    */ public class Utf8String
/*    */ {
/*    */   public static String read(ByteBuf $$0, int $$1) {
/* 13 */     int $$2 = ByteBufUtil.utf8MaxBytes($$1);
/*    */     
/* 15 */     int $$3 = VarInt.read($$0);
/* 16 */     if ($$3 > $$2) {
/* 17 */       throw new DecoderException("The received encoded string buffer length is longer than maximum allowed (" + $$3 + " > " + $$2 + ")");
/*    */     }
/* 19 */     if ($$3 < 0) {
/* 20 */       throw new DecoderException("The received encoded string buffer length is less than zero! Weird string!");
/*    */     }
/* 22 */     int $$4 = $$0.readableBytes();
/* 23 */     if ($$3 > $$4) {
/* 24 */       throw new DecoderException("Not enough bytes in buffer, expected " + $$3 + ", but got " + $$4);
/*    */     }
/*    */     
/* 27 */     String $$5 = $$0.toString($$0.readerIndex(), $$3, StandardCharsets.UTF_8);
/* 28 */     $$0.readerIndex($$0.readerIndex() + $$3);
/* 29 */     if ($$5.length() > $$1) {
/* 30 */       throw new DecoderException("The received string length is longer than maximum allowed (" + $$5.length() + " > " + $$1 + ")");
/*    */     }
/*    */     
/* 33 */     return $$5;
/*    */   }
/*    */   
/*    */   public static void write(ByteBuf $$0, CharSequence $$1, int $$2) {
/* 37 */     if ($$1.length() > $$2) {
/* 38 */       throw new EncoderException("String too big (was " + $$1.length() + " characters, max " + $$2 + ")");
/*    */     }
/*    */     
/* 41 */     int $$3 = ByteBufUtil.utf8MaxBytes($$1);
/* 42 */     ByteBuf $$4 = $$0.alloc().buffer($$3);
/*    */     try {
/* 44 */       int $$5 = ByteBufUtil.writeUtf8($$4, $$1);
/* 45 */       int $$6 = ByteBufUtil.utf8MaxBytes($$2);
/* 46 */       if ($$5 > $$6) {
/* 47 */         throw new EncoderException("String too big (was " + $$5 + " bytes encoded, max " + $$6 + ")");
/*    */       }
/* 49 */       VarInt.write($$0, $$5);
/* 50 */       $$0.writeBytes($$4);
/*    */     } finally {
/* 52 */       $$4.release();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\Utf8String.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */