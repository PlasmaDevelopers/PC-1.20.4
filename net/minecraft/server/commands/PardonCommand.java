/*    */ package net.minecraft.server.commands;
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import com.mojang.brigadier.CommandDispatcher;
/*    */ import com.mojang.brigadier.Message;
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*    */ import com.mojang.brigadier.suggestion.SuggestionsBuilder;
/*    */ import java.util.Collection;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.Commands;
/*    */ import net.minecraft.commands.SharedSuggestionProvider;
/*    */ import net.minecraft.commands.arguments.GameProfileArgument;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.server.players.UserBanList;
/*    */ 
/*    */ public class PardonCommand {
/* 21 */   private static final SimpleCommandExceptionType ERROR_NOT_BANNED = new SimpleCommandExceptionType((Message)Component.translatable("commands.pardon.failed"));
/*    */   
/*    */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/* 24 */     $$0.register(
/* 25 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("pardon")
/* 26 */         .requires($$0 -> $$0.hasPermission(3)))
/* 27 */         .then(
/* 28 */           Commands.argument("targets", (ArgumentType)GameProfileArgument.gameProfile())
/* 29 */           .suggests(($$0, $$1) -> SharedSuggestionProvider.suggest(((CommandSourceStack)$$0.getSource()).getServer().getPlayerList().getBans().getUserList(), $$1))
/* 30 */           .executes($$0 -> pardonPlayers((CommandSourceStack)$$0.getSource(), GameProfileArgument.getGameProfiles($$0, "targets")))));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static int pardonPlayers(CommandSourceStack $$0, Collection<GameProfile> $$1) throws CommandSyntaxException {
/* 36 */     UserBanList $$2 = $$0.getServer().getPlayerList().getBans();
/* 37 */     int $$3 = 0;
/*    */     
/* 39 */     for (GameProfile $$4 : $$1) {
/* 40 */       if ($$2.isBanned($$4)) {
/* 41 */         $$2.remove($$4);
/* 42 */         $$3++;
/* 43 */         $$0.sendSuccess(() -> Component.translatable("commands.pardon.success", new Object[] { Component.literal($$0.getName()) }), true);
/*    */       } 
/*    */     } 
/*    */     
/* 47 */     if ($$3 == 0) {
/* 48 */       throw ERROR_NOT_BANNED.create();
/*    */     }
/*    */     
/* 51 */     return $$3;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\PardonCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */