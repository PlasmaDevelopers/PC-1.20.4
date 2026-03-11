/*    */ package net.minecraft.network.protocol;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BundleDelimiterPacket<T extends PacketListener>
/*    */   implements Packet<T>
/*    */ {
/*    */   public final void write(FriendlyByteBuf $$0) {}
/*    */   
/*    */   public final void handle(T $$0) {
/* 16 */     throw new AssertionError("This packet should be handled by pipeline");
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\BundleDelimiterPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */