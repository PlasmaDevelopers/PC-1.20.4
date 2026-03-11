/*    */ package net.minecraft.commands;
/*    */ 
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.network.chat.PlayerChatMessage;
/*    */ 
/*    */ public interface CommandSigningContext
/*    */ {
/*  9 */   public static final CommandSigningContext ANONYMOUS = new CommandSigningContext()
/*    */     {
/*    */       @Nullable
/*    */       public PlayerChatMessage getArgument(String $$0) {
/* 13 */         return null;
/*    */       }
/*    */     };
/*    */   @Nullable
/*    */   PlayerChatMessage getArgument(String paramString);
/*    */   public static final class SignedArguments extends Record implements CommandSigningContext { private final Map<String, PlayerChatMessage> arguments;
/*    */     
/* 20 */     public SignedArguments(Map<String, PlayerChatMessage> $$0) { this.arguments = $$0; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/commands/CommandSigningContext$SignedArguments;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #20	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/* 20 */       //   0	7	0	this	Lnet/minecraft/commands/CommandSigningContext$SignedArguments; } public Map<String, PlayerChatMessage> arguments() { return this.arguments; }
/*    */     public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/commands/CommandSigningContext$SignedArguments;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #20	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/commands/CommandSigningContext$SignedArguments; }
/*    */     public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/commands/CommandSigningContext$SignedArguments;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #20	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/commands/CommandSigningContext$SignedArguments;
/*    */       //   0	8	1	$$0	Ljava/lang/Object; } @Nullable
/*    */     public PlayerChatMessage getArgument(String $$0) {
/* 24 */       return this.arguments.get($$0);
/*    */     } }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\CommandSigningContext.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */