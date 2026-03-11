/*    */ package net.minecraft.network.chat;
/*    */ 
/*    */ import com.mojang.brigadier.ParseResults;
/*    */ import com.mojang.brigadier.context.CommandContextBuilder;
/*    */ import com.mojang.brigadier.context.ParsedArgument;
/*    */ import com.mojang.brigadier.tree.ArgumentCommandNode;
/*    */ import com.mojang.brigadier.tree.CommandNode;
/*    */ import java.util.List;
/*    */ 
/*    */ public final class SignableCommand<S> extends Record {
/*    */   private final List<Argument<S>> arguments;
/*    */   
/* 13 */   public SignableCommand(List<Argument<S>> $$0) { this.arguments = $$0; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/chat/SignableCommand;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #13	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/chat/SignableCommand;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/* 13 */     //   0	7	0	this	Lnet/minecraft/network/chat/SignableCommand<TS;>; } public List<Argument<S>> arguments() { return this.arguments; }
/*    */   public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/chat/SignableCommand;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #13	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/chat/SignableCommand;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	7	0	this	Lnet/minecraft/network/chat/SignableCommand<TS;>; }
/*    */   public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/chat/SignableCommand;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #13	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/chat/SignableCommand;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/* 15 */     //   0	8	0	this	Lnet/minecraft/network/chat/SignableCommand<TS;>; } public static <S> SignableCommand<S> of(ParseResults<S> $$0) { String $$1 = $$0.getReader().getString();
/* 16 */     CommandContextBuilder<S> $$2 = $$0.getContext();
/*    */     
/* 18 */     CommandContextBuilder<S> $$3 = $$2;
/*    */     
/* 20 */     List<Argument<S>> $$4 = collectArguments($$1, $$3);
/*    */     
/*    */     CommandContextBuilder<S> $$5;
/* 23 */     while (($$5 = $$3.getChild()) != null) {
/*    */       
/* 25 */       boolean $$6 = ($$5.getRootNode() != $$2.getRootNode());
/* 26 */       if (!$$6) {
/*    */         break;
/*    */       }
/*    */       
/* 30 */       $$4.addAll(collectArguments($$1, $$5));
/* 31 */       $$3 = $$5;
/*    */     } 
/*    */     
/* 34 */     return new SignableCommand<>($$4); }
/*    */ 
/*    */   
/*    */   private static <S> List<Argument<S>> collectArguments(String $$0, CommandContextBuilder<S> $$1) {
/* 38 */     List<Argument<S>> $$2 = new ArrayList<>();
/* 39 */     for (ParsedCommandNode<S> $$3 : (Iterable<ParsedCommandNode<S>>)$$1.getNodes()) {
/* 40 */       CommandNode commandNode = $$3.getNode(); if (commandNode instanceof ArgumentCommandNode) { ArgumentCommandNode<S, ?> $$4 = (ArgumentCommandNode<S, ?>)commandNode; if ($$4.getType() instanceof net.minecraft.commands.arguments.SignedArgument) {
/* 41 */           ParsedArgument<S, ?> $$5 = (ParsedArgument<S, ?>)$$1.getArguments().get($$4.getName());
/* 42 */           if ($$5 != null) {
/* 43 */             String $$6 = $$5.getRange().get($$0);
/* 44 */             $$2.add(new Argument<>($$4, $$6));
/*    */           } 
/*    */         }  }
/*    */     
/* 48 */     }  return $$2;
/*    */   }
/*    */   public static final class Argument<S> extends Record { private final ArgumentCommandNode<S, ?> node; private final String value;
/* 51 */     public Argument(ArgumentCommandNode<S, ?> $$0, String $$1) { this.node = $$0; this.value = $$1; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/network/chat/SignableCommand$Argument;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #51	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/network/chat/SignableCommand$Argument;
/*    */       // Local variable type table:
/*    */       //   start	length	slot	name	signature
/*    */       //   0	7	0	this	Lnet/minecraft/network/chat/SignableCommand$Argument<TS;>; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/chat/SignableCommand$Argument;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #51	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/network/chat/SignableCommand$Argument;
/*    */       // Local variable type table:
/*    */       //   start	length	slot	name	signature
/*    */       //   0	7	0	this	Lnet/minecraft/network/chat/SignableCommand$Argument<TS;>; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/network/chat/SignableCommand$Argument;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #51	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/network/chat/SignableCommand$Argument;
/*    */       //   0	8	1	$$0	Ljava/lang/Object;
/*    */       // Local variable type table:
/*    */       //   start	length	slot	name	signature
/* 51 */       //   0	8	0	this	Lnet/minecraft/network/chat/SignableCommand$Argument<TS;>; } public ArgumentCommandNode<S, ?> node() { return this.node; } public String value() { return this.value; }
/*    */      public String name() {
/* 53 */       return this.node.getName();
/*    */     } }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\SignableCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */