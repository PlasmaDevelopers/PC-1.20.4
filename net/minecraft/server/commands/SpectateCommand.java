/*    */ package net.minecraft.server.commands;
/*    */ 
/*    */ import com.mojang.brigadier.CommandDispatcher;
/*    */ import com.mojang.brigadier.Message;
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.builder.RequiredArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
/*    */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.Commands;
/*    */ import net.minecraft.commands.arguments.EntityArgument;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.level.GameType;
/*    */ 
/*    */ public class SpectateCommand
/*    */ {
/*    */   private static final DynamicCommandExceptionType ERROR_NOT_SPECTATOR;
/* 24 */   private static final SimpleCommandExceptionType ERROR_SELF = new SimpleCommandExceptionType((Message)Component.translatable("commands.spectate.self")); static {
/* 25 */     ERROR_NOT_SPECTATOR = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("commands.spectate.not_spectator", new Object[] { $$0 }));
/*    */   }
/*    */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/* 28 */     $$0.register(
/* 29 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("spectate")
/* 30 */         .requires($$0 -> $$0.hasPermission(2)))
/* 31 */         .executes($$0 -> spectate((CommandSourceStack)$$0.getSource(), null, ((CommandSourceStack)$$0.getSource()).getPlayerOrException())))
/* 32 */         .then((
/* 33 */           (RequiredArgumentBuilder)Commands.argument("target", (ArgumentType)EntityArgument.entity())
/* 34 */           .executes($$0 -> spectate((CommandSourceStack)$$0.getSource(), EntityArgument.getEntity($$0, "target"), ((CommandSourceStack)$$0.getSource()).getPlayerOrException())))
/* 35 */           .then(
/* 36 */             Commands.argument("player", (ArgumentType)EntityArgument.player())
/* 37 */             .executes($$0 -> spectate((CommandSourceStack)$$0.getSource(), EntityArgument.getEntity($$0, "target"), EntityArgument.getPlayer($$0, "player"))))));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static int spectate(CommandSourceStack $$0, @Nullable Entity $$1, ServerPlayer $$2) throws CommandSyntaxException {
/* 44 */     if ($$2 == $$1)
/* 45 */       throw ERROR_SELF.create(); 
/* 46 */     if ($$2.gameMode.getGameModeForPlayer() != GameType.SPECTATOR) {
/* 47 */       throw ERROR_NOT_SPECTATOR.create($$2.getDisplayName());
/*    */     }
/*    */     
/* 50 */     $$2.setCamera($$1);
/* 51 */     if ($$1 != null) {
/* 52 */       $$0.sendSuccess(() -> Component.translatable("commands.spectate.success.started", new Object[] { $$0.getDisplayName() }), false);
/*    */     } else {
/* 54 */       $$0.sendSuccess(() -> Component.translatable("commands.spectate.success.stopped"), false);
/*    */     } 
/* 56 */     return 1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\SpectateCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */