/*    */ package net.minecraft.server.commands;
/*    */ import com.mojang.brigadier.CommandDispatcher;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.Commands;
/*    */ import net.minecraft.commands.arguments.MessageArgument;
/*    */ import net.minecraft.network.chat.ChatType;
/*    */ import net.minecraft.network.chat.PlayerChatMessage;
/*    */ import net.minecraft.server.players.PlayerList;
/*    */ 
/*    */ public class EmoteCommands {
/*    */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/* 15 */     $$0.register(
/* 16 */         (LiteralArgumentBuilder)Commands.literal("me")
/* 17 */         .then(
/* 18 */           Commands.argument("action", (ArgumentType)MessageArgument.message()).executes($$0 -> {
/*    */               MessageArgument.resolveChatMessage($$0, "action", ());
/*    */               return 1;
/*    */             })));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\EmoteCommands.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */