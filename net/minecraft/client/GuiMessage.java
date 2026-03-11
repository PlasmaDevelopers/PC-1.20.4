/*    */ package net.minecraft.client;
/*    */ public final class GuiMessage extends Record { private final int addedTime;
/*    */   private final Component content;
/*    */   @Nullable
/*    */   private final MessageSignature signature;
/*    */   @Nullable
/*    */   private final GuiMessageTag tag;
/*    */   
/*  9 */   public GuiMessage(int $$0, Component $$1, @Nullable MessageSignature $$2, @Nullable GuiMessageTag $$3) { this.addedTime = $$0; this.content = $$1; this.signature = $$2; this.tag = $$3; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/client/GuiMessage;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  9 */     //   0	7	0	this	Lnet/minecraft/client/GuiMessage; } public int addedTime() { return this.addedTime; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/GuiMessage;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/client/GuiMessage; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/client/GuiMessage;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/client/GuiMessage;
/*  9 */     //   0	8	1	$$0	Ljava/lang/Object; } public Component content() { return this.content; } @Nullable public MessageSignature signature() { return this.signature; } @Nullable public GuiMessageTag tag() { return this.tag; } public static final class Line extends Record { private final int addedTime; private final FormattedCharSequence content; @Nullable
/* 10 */     private final GuiMessageTag tag; private final boolean endOfEntry; public Line(int $$0, FormattedCharSequence $$1, @Nullable GuiMessageTag $$2, boolean $$3) { this.addedTime = $$0; this.content = $$1; this.tag = $$2; this.endOfEntry = $$3; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/GuiMessage$Line;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #10	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/client/GuiMessage$Line; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/GuiMessage$Line;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #10	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/client/GuiMessage$Line; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/GuiMessage$Line;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #10	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/client/GuiMessage$Line;
/* 10 */       //   0	8	1	$$0	Ljava/lang/Object; } public int addedTime() { return this.addedTime; } public FormattedCharSequence content() { return this.content; } @Nullable public GuiMessageTag tag() { return this.tag; } public boolean endOfEntry() { return this.endOfEntry; }
/*    */      }
/*    */    }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\GuiMessage.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */