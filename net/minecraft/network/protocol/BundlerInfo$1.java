/*    */ package net.minecraft.network.protocol;
/*    */ 
/*    */ import java.util.function.Consumer;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class null
/*    */   implements BundlerInfo
/*    */ {
/*    */   public void unbundlePacket(Packet<?> $$0, Consumer<Packet<?>> $$1) {
/* 17 */     $$1.accept($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public BundlerInfo.Bundler startPacketBundling(Packet<?> $$0) {
/* 23 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\BundlerInfo$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */