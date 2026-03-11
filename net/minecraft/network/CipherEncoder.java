/*    */ package net.minecraft.network;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.MessageToByteEncoder;
/*    */ import javax.crypto.Cipher;
/*    */ 
/*    */ public class CipherEncoder
/*    */   extends MessageToByteEncoder<ByteBuf> {
/*    */   private final CipherBase cipher;
/*    */   
/*    */   public CipherEncoder(Cipher $$0) {
/* 13 */     this.cipher = new CipherBase($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void encode(ChannelHandlerContext $$0, ByteBuf $$1, ByteBuf $$2) throws Exception {
/* 18 */     this.cipher.encipher($$1, $$2);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\CipherEncoder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */