/*   */ package net.minecraft.network.chat;public final class LastSeenTrackedEntry extends Record { private final MessageSignature signature; private final boolean pending;
/*   */   
/* 3 */   public LastSeenTrackedEntry(MessageSignature $$0, boolean $$1) { this.signature = $$0; this.pending = $$1; } public final String toString() { // Byte code:
/*   */     //   0: aload_0
/*   */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/chat/LastSeenTrackedEntry;)Ljava/lang/String;
/*   */     //   6: areturn
/*   */     // Line number table:
/*   */     //   Java source line number -> byte code offset
/*   */     //   #3	-> 0
/*   */     // Local variable table:
/*   */     //   start	length	slot	name	descriptor
/* 3 */     //   0	7	0	this	Lnet/minecraft/network/chat/LastSeenTrackedEntry; } public MessageSignature signature() { return this.signature; } public final int hashCode() { // Byte code:
/*   */     //   0: aload_0
/*   */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/chat/LastSeenTrackedEntry;)I
/*   */     //   6: ireturn
/*   */     // Line number table:
/*   */     //   Java source line number -> byte code offset
/*   */     //   #3	-> 0
/*   */     // Local variable table:
/*   */     //   start	length	slot	name	descriptor
/*   */     //   0	7	0	this	Lnet/minecraft/network/chat/LastSeenTrackedEntry; } public final boolean equals(Object $$0) { // Byte code:
/*   */     //   0: aload_0
/*   */     //   1: aload_1
/*   */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/chat/LastSeenTrackedEntry;Ljava/lang/Object;)Z
/*   */     //   7: ireturn
/*   */     // Line number table:
/*   */     //   Java source line number -> byte code offset
/*   */     //   #3	-> 0
/*   */     // Local variable table:
/*   */     //   start	length	slot	name	descriptor
/*   */     //   0	8	0	this	Lnet/minecraft/network/chat/LastSeenTrackedEntry;
/* 3 */     //   0	8	1	$$0	Ljava/lang/Object; } public boolean pending() { return this.pending; }
/*   */    public LastSeenTrackedEntry acknowledge() {
/* 5 */     return this.pending ? new LastSeenTrackedEntry(this.signature, false) : this;
/*   */   } }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\LastSeenTrackedEntry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */