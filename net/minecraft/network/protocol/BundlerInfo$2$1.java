/*    */ package net.minecraft.network.protocol;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class null
/*    */   implements BundlerInfo.Bundler
/*    */ {
/* 46 */   private final List<Packet<T>> bundlePackets = new ArrayList<>();
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Packet<?> addPacket(Packet<?> $$0) {
/* 51 */     if ($$0 == delimiterPacket)
/*    */     {
/* 53 */       return constructor.apply(this.bundlePackets);
/*    */     }
/*    */     
/* 56 */     Packet<T> $$1 = (Packet)$$0;
/* 57 */     if (this.bundlePackets.size() >= 4096) {
/* 58 */       throw new IllegalStateException("Too many packets in a bundle");
/*    */     }
/* 60 */     this.bundlePackets.add($$1);
/* 61 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\BundlerInfo$2$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */