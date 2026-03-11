/*    */ package net.minecraft.network;
/*    */ 
/*    */ import io.netty.util.Attribute;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ 
/*    */ public interface ProtocolSwapHandler {
/*    */   static void swapProtocolIfNeeded(Attribute<ConnectionProtocol.CodecData<?>> $$0, Packet<?> $$1) {
/*  8 */     ConnectionProtocol $$2 = $$1.nextProtocol();
/*  9 */     if ($$2 != null) {
/* 10 */       ConnectionProtocol.CodecData<?> $$3 = (ConnectionProtocol.CodecData)$$0.get();
/* 11 */       ConnectionProtocol $$4 = $$3.protocol();
/* 12 */       if ($$2 != $$4) {
/* 13 */         ConnectionProtocol.CodecData<?> $$5 = $$2.codec($$3.flow());
/* 14 */         $$0.set($$5);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\ProtocolSwapHandler.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */