/*    */ package net.minecraft.server.commands;
/*    */ 
/*    */ import com.mojang.brigadier.CommandDispatcher;
/*    */ import com.mojang.brigadier.Message;
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.builder.RequiredArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*    */ import java.util.Collection;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.Commands;
/*    */ import net.minecraft.commands.arguments.EntityArgument;
/*    */ import net.minecraft.commands.arguments.MessageArgument;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ 
/*    */ public class KickCommand
/*    */ {
/* 21 */   private static final SimpleCommandExceptionType ERROR_KICKING_OWNER = new SimpleCommandExceptionType((Message)Component.translatable("commands.kick.owner.failed"));
/* 22 */   private static final SimpleCommandExceptionType ERROR_SINGLEPLAYER = new SimpleCommandExceptionType((Message)Component.translatable("commands.kick.singleplayer.failed"));
/*    */   
/*    */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/* 25 */     $$0.register(
/* 26 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("kick")
/* 27 */         .requires($$0 -> $$0.hasPermission(3)))
/* 28 */         .then((
/* 29 */           (RequiredArgumentBuilder)Commands.argument("targets", (ArgumentType)EntityArgument.players())
/* 30 */           .executes($$0 -> kickPlayers((CommandSourceStack)$$0.getSource(), EntityArgument.getPlayers($$0, "targets"), (Component)Component.translatable("multiplayer.disconnect.kicked"))))
/* 31 */           .then(
/* 32 */             Commands.argument("reason", (ArgumentType)MessageArgument.message())
/* 33 */             .executes($$0 -> kickPlayers((CommandSourceStack)$$0.getSource(), EntityArgument.getPlayers($$0, "targets"), MessageArgument.getMessage($$0, "reason"))))));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static int kickPlayers(CommandSourceStack $$0, Collection<ServerPlayer> $$1, Component $$2) throws CommandSyntaxException {
/* 40 */     if (!$$0.getServer().isPublished()) {
/* 41 */       throw ERROR_SINGLEPLAYER.create();
/*    */     }
/*    */     
/* 44 */     int $$3 = 0;
/* 45 */     for (ServerPlayer $$4 : $$1) {
/* 46 */       if ($$0.getServer().isSingleplayerOwner($$4.getGameProfile())) {
/*    */         continue;
/*    */       }
/* 49 */       $$4.connection.disconnect($$2);
/* 50 */       $$0.sendSuccess(() -> Component.translatable("commands.kick.success", new Object[] { $$0.getDisplayName(), $$1 }), true);
/* 51 */       $$3++;
/*    */     } 
/*    */     
/* 54 */     if ($$3 == 0) {
/* 55 */       throw ERROR_KICKING_OWNER.create();
/*    */     }
/*    */     
/* 58 */     return $$3;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\KickCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */