/*    */ package net.minecraft.server.commands;
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import com.mojang.brigadier.CommandDispatcher;
/*    */ import com.mojang.brigadier.Message;
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
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.server.players.PlayerList;
/*    */ 
/*    */ public class OpCommand {
/* 21 */   private static final SimpleCommandExceptionType ERROR_ALREADY_OP = new SimpleCommandExceptionType((Message)Component.translatable("commands.op.failed"));
/*    */   
/*    */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/* 24 */     $$0.register(
/* 25 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("op")
/* 26 */         .requires($$0 -> $$0.hasPermission(3)))
/* 27 */         .then(
/* 28 */           Commands.argument("targets", (ArgumentType)GameProfileArgument.gameProfile())
/* 29 */           .suggests(($$0, $$1) -> {
/*    */               PlayerList $$2 = ((CommandSourceStack)$$0.getSource()).getServer().getPlayerList();
/*    */               
/*    */               return SharedSuggestionProvider.suggest($$2.getPlayers().stream().filter(()).map(()), $$1);
/* 33 */             }).executes($$0 -> opPlayers((CommandSourceStack)$$0.getSource(), GameProfileArgument.getGameProfiles($$0, "targets")))));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static int opPlayers(CommandSourceStack $$0, Collection<GameProfile> $$1) throws CommandSyntaxException {
/* 39 */     PlayerList $$2 = $$0.getServer().getPlayerList();
/* 40 */     int $$3 = 0;
/*    */     
/* 42 */     for (GameProfile $$4 : $$1) {
/* 43 */       if (!$$2.isOp($$4)) {
/* 44 */         $$2.op($$4);
/* 45 */         $$3++;
/* 46 */         $$0.sendSuccess(() -> Component.translatable("commands.op.success", new Object[] { ((GameProfile)$$0.iterator().next()).getName() }), true);
/*    */       } 
/*    */     } 
/*    */     
/* 50 */     if ($$3 == 0) {
/* 51 */       throw ERROR_ALREADY_OP.create();
/*    */     }
/*    */     
/* 54 */     return $$3;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\OpCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */