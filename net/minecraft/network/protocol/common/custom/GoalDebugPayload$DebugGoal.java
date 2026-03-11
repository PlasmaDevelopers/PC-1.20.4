/*    */ package net.minecraft.network.protocol.common.custom;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class DebugGoal
/*    */   extends Record
/*    */ {
/*    */   private final int priority;
/*    */   private final boolean isRunning;
/*    */   private final String name;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/common/custom/GoalDebugPayload$DebugGoal;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #32	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/common/custom/GoalDebugPayload$DebugGoal;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/common/custom/GoalDebugPayload$DebugGoal;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #32	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/common/custom/GoalDebugPayload$DebugGoal;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/common/custom/GoalDebugPayload$DebugGoal;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #32	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/common/custom/GoalDebugPayload$DebugGoal;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   public DebugGoal(int $$0, boolean $$1, String $$2) {
/* 32 */     this.priority = $$0; this.isRunning = $$1; this.name = $$2; } public int priority() { return this.priority; } public boolean isRunning() { return this.isRunning; } public String name() { return this.name; }
/*    */    public DebugGoal(FriendlyByteBuf $$0) {
/* 34 */     this($$0
/* 35 */         .readInt(), $$0
/* 36 */         .readBoolean(), $$0
/* 37 */         .readUtf(255));
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 42 */     $$0.writeInt(this.priority);
/* 43 */     $$0.writeBoolean(this.isRunning);
/* 44 */     $$0.writeUtf(this.name);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\common\custom\GoalDebugPayload$DebugGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */