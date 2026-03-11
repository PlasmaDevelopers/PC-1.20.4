/*    */ package net.minecraft.network.protocol;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.function.Consumer;
/*    */ import java.util.function.Function;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ public interface BundlerInfo
/*    */ {
/*    */   public static final int BUNDLE_SIZE_LIMIT = 4096;
/*    */   
/* 14 */   public static final BundlerInfo EMPTY = new BundlerInfo()
/*    */     {
/*    */       public void unbundlePacket(Packet<?> $$0, Consumer<Packet<?>> $$1) {
/* 17 */         $$1.accept($$0);
/*    */       }
/*    */ 
/*    */       
/*    */       @Nullable
/*    */       public BundlerInfo.Bundler startPacketBundling(Packet<?> $$0) {
/* 23 */         return null;
/*    */       }
/*    */     };
/*    */   
/*    */   static <T extends net.minecraft.network.PacketListener, P extends BundlePacket<T>> BundlerInfo createForPacket(final Class<P> bundlePacketCls, final Function<Iterable<Packet<T>>, P> constructor, final BundleDelimiterPacket<T> delimiterPacket) {
/* 28 */     return new BundlerInfo()
/*    */       {
/*    */         public void unbundlePacket(Packet<?> $$0, Consumer<Packet<?>> $$1) {
/* 31 */           if ($$0.getClass() == bundlePacketCls) {
/* 32 */             BundlePacket<?> bundlePacket = (BundlePacket)$$0;
/* 33 */             $$1.accept(delimiterPacket);
/* 34 */             bundlePacket.subPackets().forEach($$1);
/* 35 */             $$1.accept(delimiterPacket);
/*    */           } else {
/* 37 */             $$1.accept($$0);
/*    */           } 
/*    */         }
/*    */ 
/*    */         
/*    */         @Nullable
/*    */         public BundlerInfo.Bundler startPacketBundling(Packet<?> $$0) {
/* 44 */           if ($$0 == delimiterPacket) {
/* 45 */             return new BundlerInfo.Bundler() {
/* 46 */                 private final List<Packet<T>> bundlePackets = new ArrayList<>();
/*    */ 
/*    */                 
/*    */                 @Nullable
/*    */                 public Packet<?> addPacket(Packet<?> $$0) {
/* 51 */                   if ($$0 == delimiterPacket)
/*    */                   {
/* 53 */                     return constructor.apply(this.bundlePackets);
/*    */                   }
/*    */                   
/* 56 */                   Packet<T> $$1 = (Packet)$$0;
/* 57 */                   if (this.bundlePackets.size() >= 4096) {
/* 58 */                     throw new IllegalStateException("Too many packets in a bundle");
/*    */                   }
/* 60 */                   this.bundlePackets.add($$1);
/* 61 */                   return null;
/*    */                 }
/*    */               };
/*    */           }
/* 65 */           return null;
/*    */         }
/*    */       };
/*    */   }
/*    */   
/*    */   void unbundlePacket(Packet<?> paramPacket, Consumer<Packet<?>> paramConsumer);
/*    */   
/*    */   @Nullable
/*    */   Bundler startPacketBundling(Packet<?> paramPacket);
/*    */   
/*    */   public static interface Provider {
/*    */     BundlerInfo bundlerInfo();
/*    */   }
/*    */   
/*    */   public static interface Bundler {
/*    */     @Nullable
/*    */     Packet<?> addPacket(Packet<?> param1Packet);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\BundlerInfo.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */