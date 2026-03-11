/*    */ package net.minecraft.network.protocol;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ 
/*    */ public abstract class BundlePacket<T extends PacketListener>
/*    */   implements Packet<T> {
/*    */   private final Iterable<Packet<T>> packets;
/*    */   
/*    */   protected BundlePacket(Iterable<Packet<T>> $$0) {
/* 11 */     this.packets = $$0;
/*    */   }
/*    */   
/*    */   public final Iterable<Packet<T>> subPackets() {
/* 15 */     return this.packets;
/*    */   }
/*    */   
/*    */   public final void write(FriendlyByteBuf $$0) {}
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\BundlePacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */