/*    */ package net.minecraft.server.commands;
/*    */ 
/*    */ import com.mojang.brigadier.CommandDispatcher;
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.builder.RequiredArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.Commands;
/*    */ import net.minecraft.commands.arguments.EntityArgument;
/*    */ import net.minecraft.commands.arguments.GameModeArgument;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.MutableComponent;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.world.level.GameRules;
/*    */ import net.minecraft.world.level.GameType;
/*    */ 
/*    */ public class GameModeCommand
/*    */ {
/*    */   public static final int PERMISSION_LEVEL = 2;
/*    */   
/*    */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/* 26 */     $$0.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("gamemode").requires($$0 -> $$0.hasPermission(2)))
/* 27 */         .then((
/* 28 */           (RequiredArgumentBuilder)Commands.argument("gamemode", (ArgumentType)GameModeArgument.gameMode())
/* 29 */           .executes($$0 -> setMode($$0, Collections.singleton(((CommandSourceStack)$$0.getSource()).getPlayerOrException()), GameModeArgument.getGameMode($$0, "gamemode"))))
/* 30 */           .then(
/* 31 */             Commands.argument("target", (ArgumentType)EntityArgument.players())
/* 32 */             .executes($$0 -> setMode($$0, EntityArgument.getPlayers($$0, "target"), GameModeArgument.getGameMode($$0, "gamemode"))))));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static void logGamemodeChange(CommandSourceStack $$0, ServerPlayer $$1, GameType $$2) {
/* 39 */     MutableComponent mutableComponent = Component.translatable("gameMode." + $$2.getName());
/* 40 */     if ($$0.getEntity() == $$1) {
/* 41 */       $$0.sendSuccess(() -> Component.translatable("commands.gamemode.success.self", new Object[] { $$0 }), true);
/*    */     } else {
/* 43 */       if ($$0.getLevel().getGameRules().getBoolean(GameRules.RULE_SENDCOMMANDFEEDBACK)) {
/* 44 */         $$1.sendSystemMessage((Component)Component.translatable("gameMode.changed", new Object[] { mutableComponent }));
/*    */       }
/*    */       
/* 47 */       $$0.sendSuccess(() -> Component.translatable("commands.gamemode.success.other", new Object[] { $$0.getDisplayName(), $$1 }), true);
/*    */     } 
/*    */   }
/*    */   
/*    */   private static int setMode(CommandContext<CommandSourceStack> $$0, Collection<ServerPlayer> $$1, GameType $$2) {
/* 52 */     int $$3 = 0;
/* 53 */     for (ServerPlayer $$4 : $$1) {
/* 54 */       if ($$4.setGameMode($$2)) {
/* 55 */         logGamemodeChange((CommandSourceStack)$$0.getSource(), $$4, $$2);
/* 56 */         $$3++;
/*    */       } 
/*    */     } 
/* 59 */     return $$3;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\GameModeCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */