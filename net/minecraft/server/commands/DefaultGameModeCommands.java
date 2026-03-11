/*    */ package net.minecraft.server.commands;
/*    */ 
/*    */ import com.mojang.brigadier.CommandDispatcher;
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.Commands;
/*    */ import net.minecraft.commands.arguments.GameModeArgument;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.world.level.GameType;
/*    */ 
/*    */ public class DefaultGameModeCommands {
/*    */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/* 18 */     $$0.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("defaultgamemode").requires($$0 -> $$0.hasPermission(2)))
/* 19 */         .then(
/* 20 */           Commands.argument("gamemode", (ArgumentType)GameModeArgument.gameMode())
/* 21 */           .executes($$0 -> setMode((CommandSourceStack)$$0.getSource(), GameModeArgument.getGameMode($$0, "gamemode")))));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static int setMode(CommandSourceStack $$0, GameType $$1) {
/* 27 */     int $$2 = 0;
/* 28 */     MinecraftServer $$3 = $$0.getServer();
/* 29 */     $$3.setDefaultGameType($$1);
/*    */     
/* 31 */     GameType $$4 = $$3.getForcedGameType();
/* 32 */     if ($$4 != null) {
/* 33 */       for (ServerPlayer $$5 : $$3.getPlayerList().getPlayers()) {
/* 34 */         if ($$5.setGameMode($$4)) {
/* 35 */           $$2++;
/*    */         }
/*    */       } 
/*    */     }
/*    */     
/* 40 */     $$0.sendSuccess(() -> Component.translatable("commands.defaultgamemode.success", new Object[] { $$0.getLongDisplayName() }), true);
/*    */     
/* 42 */     return $$2;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\DefaultGameModeCommands.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */