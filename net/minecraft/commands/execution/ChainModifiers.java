/*    */ package net.minecraft.commands.execution;public final class ChainModifiers extends Record { private final byte flags;
/*    */   
/*  3 */   public ChainModifiers(byte $$0) { this.flags = $$0; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/commands/execution/ChainModifiers;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #3	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  3 */     //   0	7	0	this	Lnet/minecraft/commands/execution/ChainModifiers; } public byte flags() { return this.flags; }
/*    */   public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/commands/execution/ChainModifiers;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #3	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/commands/execution/ChainModifiers; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/commands/execution/ChainModifiers;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #3	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/commands/execution/ChainModifiers;
/*  4 */     //   0	8	1	$$0	Ljava/lang/Object; } public static final ChainModifiers DEFAULT = new ChainModifiers((byte)0);
/*    */   
/*    */   private static final byte FLAG_FORKED = 1;
/*    */   private static final byte FLAG_IS_RETURN = 2;
/*    */   
/*    */   private ChainModifiers setFlag(byte $$0) {
/* 10 */     int $$1 = this.flags | $$0;
/* 11 */     return ($$1 != this.flags) ? new ChainModifiers((byte)$$1) : this;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isForked() {
/* 18 */     return ((this.flags & 0x1) != 0);
/*    */   }
/*    */   
/*    */   public ChainModifiers setForked() {
/* 22 */     return setFlag((byte)1);
/*    */   }
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
/*    */   public boolean isReturn() {
/* 36 */     return ((this.flags & 0x2) != 0);
/*    */   }
/*    */   
/*    */   public ChainModifiers setReturn() {
/* 40 */     return setFlag((byte)2);
/*    */   } }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\execution\ChainModifiers.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */