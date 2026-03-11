/*    */ package net.minecraft.network;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.MessageToMessageDecoder;
/*    */ import java.util.List;
/*    */ import javax.crypto.Cipher;
/*    */ 
/*    */ public class CipherDecoder
/*    */   extends MessageToMessageDecoder<ByteBuf> {
/*    */   private final CipherBase cipher;
/*    */   
/*    */   public CipherDecoder(Cipher $$0) {
/* 14 */     this.cipher = new CipherBase($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void decode(ChannelHandlerContext $$0, ByteBuf $$1, List<Object> $$2) throws Exception {
/* 19 */     $$2.add(this.cipher.decipher($$0, $$1));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\CipherDecoder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */