/*    */ package net.minecraft.network.protocol;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.network.ConnectionProtocol;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface Packet<T extends net.minecraft.network.PacketListener>
/*    */ {
/*    */   void write(FriendlyByteBuf paramFriendlyByteBuf);
/*    */   
/*    */   void handle(T paramT);
/*    */   
/*    */   default boolean isSkippable() {
/* 19 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   default ConnectionProtocol nextProtocol() {
/* 28 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\Packet.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */