/*    */ package net.minecraft.commands.execution;public final class Frame extends Record { private final int depth;
/*    */   private final CommandResultCallback returnValueConsumer;
/*    */   private final FrameControl frameControl;
/*    */   
/*  5 */   public Frame(int $$0, CommandResultCallback $$1, FrameControl $$2) { this.depth = $$0; this.returnValueConsumer = $$1; this.frameControl = $$2; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/commands/execution/Frame;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #5	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  5 */     //   0	7	0	this	Lnet/minecraft/commands/execution/Frame; } public int depth() { return this.depth; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/commands/execution/Frame;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #5	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/commands/execution/Frame; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/commands/execution/Frame;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #5	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/commands/execution/Frame;
/*  5 */     //   0	8	1	$$0	Ljava/lang/Object; } public CommandResultCallback returnValueConsumer() { return this.returnValueConsumer; } public FrameControl frameControl() { return this.frameControl; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void returnSuccess(int $$0) {
/* 11 */     this.returnValueConsumer.onSuccess($$0);
/*    */   }
/*    */   
/*    */   public void returnFailure() {
/* 15 */     this.returnValueConsumer.onFailure();
/*    */   }
/*    */   
/*    */   public void discard() {
/* 19 */     this.frameControl.discard();
/*    */   }
/*    */   
/*    */   @FunctionalInterface
/*    */   public static interface FrameControl {
/*    */     void discard();
/*    */   } }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\execution\Frame.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */