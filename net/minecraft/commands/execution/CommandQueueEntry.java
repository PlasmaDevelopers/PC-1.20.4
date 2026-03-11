/*   */ package net.minecraft.commands.execution;public final class CommandQueueEntry<T> extends Record { private final Frame frame; private final EntryAction<T> action;
/*   */   
/* 3 */   public CommandQueueEntry(Frame $$0, EntryAction<T> $$1) { this.frame = $$0; this.action = $$1; } public final String toString() { // Byte code:
/*   */     //   0: aload_0
/*   */     //   1: <illegal opcode> toString : (Lnet/minecraft/commands/execution/CommandQueueEntry;)Ljava/lang/String;
/*   */     //   6: areturn
/*   */     // Line number table:
/*   */     //   Java source line number -> byte code offset
/*   */     //   #3	-> 0
/*   */     // Local variable table:
/*   */     //   start	length	slot	name	descriptor
/*   */     //   0	7	0	this	Lnet/minecraft/commands/execution/CommandQueueEntry;
/*   */     // Local variable type table:
/*   */     //   start	length	slot	name	signature
/* 3 */     //   0	7	0	this	Lnet/minecraft/commands/execution/CommandQueueEntry<TT;>; } public Frame frame() { return this.frame; } public final int hashCode() { // Byte code:
/*   */     //   0: aload_0
/*   */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/commands/execution/CommandQueueEntry;)I
/*   */     //   6: ireturn
/*   */     // Line number table:
/*   */     //   Java source line number -> byte code offset
/*   */     //   #3	-> 0
/*   */     // Local variable table:
/*   */     //   start	length	slot	name	descriptor
/*   */     //   0	7	0	this	Lnet/minecraft/commands/execution/CommandQueueEntry;
/*   */     // Local variable type table:
/*   */     //   start	length	slot	name	signature
/*   */     //   0	7	0	this	Lnet/minecraft/commands/execution/CommandQueueEntry<TT;>; } public final boolean equals(Object $$0) { // Byte code:
/*   */     //   0: aload_0
/*   */     //   1: aload_1
/*   */     //   2: <illegal opcode> equals : (Lnet/minecraft/commands/execution/CommandQueueEntry;Ljava/lang/Object;)Z
/*   */     //   7: ireturn
/*   */     // Line number table:
/*   */     //   Java source line number -> byte code offset
/*   */     //   #3	-> 0
/*   */     // Local variable table:
/*   */     //   start	length	slot	name	descriptor
/*   */     //   0	8	0	this	Lnet/minecraft/commands/execution/CommandQueueEntry;
/*   */     //   0	8	1	$$0	Ljava/lang/Object;
/*   */     // Local variable type table:
/*   */     //   start	length	slot	name	signature
/* 3 */     //   0	8	0	this	Lnet/minecraft/commands/execution/CommandQueueEntry<TT;>; } public EntryAction<T> action() { return this.action; }
/*   */ 
/*   */ 
/*   */   
/*   */   public void execute(ExecutionContext<T> $$0) {
/* 8 */     this.action.execute($$0, this.frame);
/*   */   } }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\execution\CommandQueueEntry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */