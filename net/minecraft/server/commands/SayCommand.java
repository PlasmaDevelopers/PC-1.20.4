/*    */ package net.minecraft.server.commands;
/*    */ import com.mojang.brigadier.CommandDispatcher;
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
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
/*    */ public class SayCommand {
/*    */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/* 16 */     $$0.register(
/* 17 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("say")
/* 18 */         .requires($$0 -> $$0.hasPermission(2)))
/* 19 */         .then(
/* 20 */           Commands.argument("message", (ArgumentType)MessageArgument.message())
/* 21 */           .executes($$0 -> {
/*    */               MessageArgument.resolveChatMessage($$0, "message", ());
/*    */               return 1;
/*    */             })));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\SayCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */