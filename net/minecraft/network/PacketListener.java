/*    */ package net.minecraft.network;
/*    */ 
/*    */ import net.minecraft.CrashReport;
/*    */ import net.minecraft.CrashReportCategory;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.network.protocol.PacketFlow;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface PacketListener
/*    */ {
/*    */   default boolean shouldHandleMessage(Packet<?> $$0) {
/* 19 */     return isAcceptingMessages();
/*    */   }
/*    */   
/*    */   default boolean shouldPropagateHandlingExceptions() {
/* 23 */     return true;
/*    */   }
/*    */   
/*    */   default void fillCrashReport(CrashReport $$0) {
/* 27 */     CrashReportCategory $$1 = $$0.addCategory("Connection");
/* 28 */     $$1.setDetail("Protocol", () -> protocol().id());
/* 29 */     $$1.setDetail("Flow", () -> flow().toString());
/* 30 */     fillListenerSpecificCrashDetails($$1);
/*    */   }
/*    */   
/*    */   default void fillListenerSpecificCrashDetails(CrashReportCategory $$0) {}
/*    */   
/*    */   PacketFlow flow();
/*    */   
/*    */   ConnectionProtocol protocol();
/*    */   
/*    */   void onDisconnect(Component paramComponent);
/*    */   
/*    */   boolean isAcceptingMessages();
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\PacketListener.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */