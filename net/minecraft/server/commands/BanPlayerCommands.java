/*    */ package net.minecraft.server.commands;
/*    */ 
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import com.mojang.brigadier.CommandDispatcher;
/*    */ import com.mojang.brigadier.Message;
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.builder.RequiredArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*    */ import java.util.Collection;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.Commands;
/*    */ import net.minecraft.commands.arguments.GameProfileArgument;
/*    */ import net.minecraft.commands.arguments.MessageArgument;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.server.players.StoredUserEntry;
/*    */ import net.minecraft.server.players.UserBanList;
/*    */ import net.minecraft.server.players.UserBanListEntry;
/*    */ 
/*    */ public class BanPlayerCommands {
/* 25 */   private static final SimpleCommandExceptionType ERROR_ALREADY_BANNED = new SimpleCommandExceptionType((Message)Component.translatable("commands.ban.failed"));
/*    */   
/*    */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/* 28 */     $$0.register(
/* 29 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("ban")
/* 30 */         .requires($$0 -> $$0.hasPermission(3)))
/* 31 */         .then((
/* 32 */           (RequiredArgumentBuilder)Commands.argument("targets", (ArgumentType)GameProfileArgument.gameProfile())
/* 33 */           .executes($$0 -> banPlayers((CommandSourceStack)$$0.getSource(), GameProfileArgument.getGameProfiles($$0, "targets"), null)))
/* 34 */           .then(
/* 35 */             Commands.argument("reason", (ArgumentType)MessageArgument.message())
/* 36 */             .executes($$0 -> banPlayers((CommandSourceStack)$$0.getSource(), GameProfileArgument.getGameProfiles($$0, "targets"), MessageArgument.getMessage($$0, "reason"))))));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static int banPlayers(CommandSourceStack $$0, Collection<GameProfile> $$1, @Nullable Component $$2) throws CommandSyntaxException {
/* 43 */     UserBanList $$3 = $$0.getServer().getPlayerList().getBans();
/* 44 */     int $$4 = 0;
/*    */     
/* 46 */     for (GameProfile $$5 : $$1) {
/* 47 */       if (!$$3.isBanned($$5)) {
/* 48 */         UserBanListEntry $$6 = new UserBanListEntry($$5, null, $$0.getTextName(), null, ($$2 == null) ? null : $$2.getString());
/* 49 */         $$3.add((StoredUserEntry)$$6);
/* 50 */         $$4++;
/* 51 */         $$0.sendSuccess(() -> Component.translatable("commands.ban.success", new Object[] { Component.literal($$0.getName()), $$1.getReason() }), true);
/*    */         
/* 53 */         ServerPlayer $$7 = $$0.getServer().getPlayerList().getPlayer($$5.getId());
/* 54 */         if ($$7 != null) {
/* 55 */           $$7.connection.disconnect((Component)Component.translatable("multiplayer.disconnect.banned"));
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 60 */     if ($$4 == 0) {
/* 61 */       throw ERROR_ALREADY_BANNED.create();
/*    */     }
/*    */     
/* 64 */     return $$4;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\BanPlayerCommands.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */