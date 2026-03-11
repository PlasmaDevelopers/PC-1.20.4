/*    */ package net.minecraft.network;
/*    */ 
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ 
/*    */ public interface PacketSendListener
/*    */ {
/*    */   static PacketSendListener thenRun(final Runnable runnable) {
/* 10 */     return new PacketSendListener()
/*    */       {
/*    */         public void onSuccess() {
/* 13 */           runnable.run();
/*    */         }
/*    */ 
/*    */         
/*    */         @Nullable
/*    */         public Packet<?> onFailure() {
/* 19 */           runnable.run();
/* 20 */           return null;
/*    */         }
/*    */       };
/*    */   }
/*    */   
/*    */   static PacketSendListener exceptionallySend(final Supplier<Packet<?>> handler) {
/* 26 */     return new PacketSendListener()
/*    */       {
/*    */         @Nullable
/*    */         public Packet<?> onFailure() {
/* 30 */           return handler.get();
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */   
/*    */   default void onSuccess() {}
/*    */   
/*    */   @Nullable
/*    */   default Packet<?> onFailure() {
/* 40 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\PacketSendListener.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */