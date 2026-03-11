/*    */ package net.minecraft.client;
/*    */ 
/*    */ public final class Line extends Record {
/*    */   private final int addedTime;
/*    */   private final FormattedCharSequence content;
/*    */   @Nullable
/*    */   private final GuiMessageTag tag;
/*    */   private final boolean endOfEntry;
/*    */   
/* 10 */   public Line(int $$0, FormattedCharSequence $$1, @Nullable GuiMessageTag $$2, boolean $$3) { this.addedTime = $$0; this.content = $$1; this.tag = $$2; this.endOfEntry = $$3; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/client/GuiMessage$Line;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #10	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 10 */     //   0	7	0	this	Lnet/minecraft/client/GuiMessage$Line; } public int addedTime() { return this.addedTime; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/GuiMessage$Line;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #10	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/client/GuiMessage$Line; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/client/GuiMessage$Line;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #10	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/client/GuiMessage$Line;
/* 10 */     //   0	8	1	$$0	Ljava/lang/Object; } public FormattedCharSequence content() { return this.content; } @Nullable public GuiMessageTag tag() { return this.tag; } public boolean endOfEntry() { return this.endOfEntry; }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\GuiMessage$Line.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */