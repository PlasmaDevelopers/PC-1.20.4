/*    */ package net.minecraft.server.commands;
/*    */ import com.google.common.net.InetAddresses;
/*    */ import com.mojang.brigadier.CommandDispatcher;
/*    */ import com.mojang.brigadier.Message;
/*    */ import com.mojang.brigadier.arguments.StringArgumentType;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*    */ import com.mojang.brigadier.suggestion.SuggestionsBuilder;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.Commands;
/*    */ import net.minecraft.commands.SharedSuggestionProvider;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.server.players.IpBanList;
/*    */ 
/*    */ public class PardonIpCommand {
/* 19 */   private static final SimpleCommandExceptionType ERROR_INVALID = new SimpleCommandExceptionType((Message)Component.translatable("commands.pardonip.invalid"));
/* 20 */   private static final SimpleCommandExceptionType ERROR_NOT_BANNED = new SimpleCommandExceptionType((Message)Component.translatable("commands.pardonip.failed"));
/*    */   
/*    */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/* 23 */     $$0.register(
/* 24 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("pardon-ip")
/* 25 */         .requires($$0 -> $$0.hasPermission(3)))
/* 26 */         .then(
/* 27 */           Commands.argument("target", (ArgumentType)StringArgumentType.word())
/* 28 */           .suggests(($$0, $$1) -> SharedSuggestionProvider.suggest(((CommandSourceStack)$$0.getSource()).getServer().getPlayerList().getIpBans().getUserList(), $$1))
/* 29 */           .executes($$0 -> unban((CommandSourceStack)$$0.getSource(), StringArgumentType.getString($$0, "target")))));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static int unban(CommandSourceStack $$0, String $$1) throws CommandSyntaxException {
/* 35 */     if (!InetAddresses.isInetAddress($$1)) {
/* 36 */       throw ERROR_INVALID.create();
/*    */     }
/*    */     
/* 39 */     IpBanList $$2 = $$0.getServer().getPlayerList().getIpBans();
/* 40 */     if (!$$2.isBanned($$1)) {
/* 41 */       throw ERROR_NOT_BANNED.create();
/*    */     }
/*    */     
/* 44 */     $$2.remove($$1);
/* 45 */     $$0.sendSuccess(() -> Component.translatable("commands.pardonip.success", new Object[] { $$0 }), true);
/* 46 */     return 1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\PardonIpCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */