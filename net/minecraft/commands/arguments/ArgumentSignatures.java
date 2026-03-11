/*    */ package net.minecraft.commands.arguments;
/*    */ 
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.chat.MessageSignature;
/*    */ import net.minecraft.network.chat.SignableCommand;
/*    */ 
/*    */ public final class ArgumentSignatures extends Record {
/*    */   private final List<Entry> entries;
/*    */   
/* 12 */   public ArgumentSignatures(List<Entry> $$0) { this.entries = $$0; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/commands/arguments/ArgumentSignatures;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #12	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 12 */     //   0	7	0	this	Lnet/minecraft/commands/arguments/ArgumentSignatures; } public List<Entry> entries() { return this.entries; }
/*    */   public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/commands/arguments/ArgumentSignatures;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #12	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/commands/arguments/ArgumentSignatures; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/commands/arguments/ArgumentSignatures;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #12	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/commands/arguments/ArgumentSignatures;
/* 13 */     //   0	8	1	$$0	Ljava/lang/Object; } public static final ArgumentSignatures EMPTY = new ArgumentSignatures(List.of());
/*    */   
/*    */   private static final int MAX_ARGUMENT_COUNT = 8;
/*    */   private static final int MAX_ARGUMENT_NAME_LENGTH = 16;
/*    */   
/*    */   public ArgumentSignatures(FriendlyByteBuf $$0) {
/* 19 */     this((List<Entry>)$$0.readCollection(FriendlyByteBuf.limitValue(java.util.ArrayList::new, 8), Entry::new));
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public MessageSignature get(String $$0) {
/* 24 */     for (Entry $$1 : this.entries) {
/* 25 */       if ($$1.name.equals($$0)) {
/* 26 */         return $$1.signature;
/*    */       }
/*    */     } 
/* 29 */     return null;
/*    */   }
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 33 */     $$0.writeCollection(this.entries, ($$0, $$1) -> $$1.write($$0));
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
/*    */   public static ArgumentSignatures signCommand(SignableCommand<?> $$0, Signer $$1) {
/* 46 */     List<Entry> $$2 = $$0.arguments().stream().map($$1 -> { MessageSignature $$2 = $$0.sign($$1.value()); return ($$2 != null) ? new Entry($$1.name(), $$2) : null; }).filter(Objects::nonNull).toList();
/*    */     
/* 48 */     return new ArgumentSignatures($$2);
/*    */   }
/*    */   
/*    */   public static final class Entry
/*    */     extends Record {
/*    */     final String name;
/*    */     final MessageSignature signature;
/*    */     
/*    */     public Entry(String $$0, MessageSignature $$1) {
/* 57 */       this.name = $$0; this.signature = $$1; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/commands/arguments/ArgumentSignatures$Entry;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #57	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/commands/arguments/ArgumentSignatures$Entry; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/commands/arguments/ArgumentSignatures$Entry;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #57	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/commands/arguments/ArgumentSignatures$Entry; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/commands/arguments/ArgumentSignatures$Entry;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #57	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/commands/arguments/ArgumentSignatures$Entry;
/* 57 */       //   0	8	1	$$0	Ljava/lang/Object; } public String name() { return this.name; } public MessageSignature signature() { return this.signature; }
/*    */      public Entry(FriendlyByteBuf $$0) {
/* 59 */       this($$0.readUtf(16), MessageSignature.read($$0));
/*    */     }
/*    */     
/*    */     public void write(FriendlyByteBuf $$0) {
/* 63 */       $$0.writeUtf(this.name, 16);
/* 64 */       MessageSignature.write($$0, this.signature);
/*    */     }
/*    */   }
/*    */   
/*    */   @FunctionalInterface
/*    */   public static interface Signer {
/*    */     @Nullable
/*    */     MessageSignature sign(String param1String);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\ArgumentSignatures.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */