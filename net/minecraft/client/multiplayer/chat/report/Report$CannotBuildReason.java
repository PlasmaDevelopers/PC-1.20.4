/*    */ package net.minecraft.client.multiplayer.chat.report;
/*    */ 
/*    */ import net.minecraft.client.gui.components.Tooltip;
/*    */ import net.minecraft.network.chat.Component;
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
/*    */ public final class CannotBuildReason
/*    */   extends Record
/*    */ {
/*    */   private final Component message;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/client/multiplayer/chat/report/Report$CannotBuildReason;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #82	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/client/multiplayer/chat/report/Report$CannotBuildReason;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/multiplayer/chat/report/Report$CannotBuildReason;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #82	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/client/multiplayer/chat/report/Report$CannotBuildReason;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/client/multiplayer/chat/report/Report$CannotBuildReason;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #82	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/client/multiplayer/chat/report/Report$CannotBuildReason;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   public CannotBuildReason(Component $$0) {
/* 82 */     this.message = $$0; } public Component message() { return this.message; }
/* 83 */    public static final CannotBuildReason NO_REASON = new CannotBuildReason((Component)Component.translatable("gui.abuseReport.send.no_reason"));
/* 84 */   public static final CannotBuildReason NO_REPORTED_MESSAGES = new CannotBuildReason((Component)Component.translatable("gui.chatReport.send.no_reported_messages"));
/* 85 */   public static final CannotBuildReason TOO_MANY_MESSAGES = new CannotBuildReason((Component)Component.translatable("gui.chatReport.send.too_many_messages"));
/* 86 */   public static final CannotBuildReason COMMENT_TOO_LONG = new CannotBuildReason((Component)Component.translatable("gui.abuseReport.send.comment_too_long"));
/*    */   
/*    */   public Tooltip tooltip() {
/* 89 */     return Tooltip.create(this.message);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\multiplayer\chat\report\Report$CannotBuildReason.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */