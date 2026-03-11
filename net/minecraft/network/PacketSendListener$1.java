/*    */ package net.minecraft.network;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class null
/*    */   implements PacketSendListener
/*    */ {
/*    */   public void onSuccess() {
/* 13 */     runnable.run();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Packet<?> onFailure() {
/* 19 */     runnable.run();
/* 20 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\PacketSendListener$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */