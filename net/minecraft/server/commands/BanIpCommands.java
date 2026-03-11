/*    */ package net.minecraft.server.commands;
/*    */ 
/*    */ import com.google.common.net.InetAddresses;
/*    */ import com.mojang.brigadier.CommandDispatcher;
/*    */ import com.mojang.brigadier.Message;
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.arguments.StringArgumentType;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.builder.RequiredArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.Commands;
/*    */ import net.minecraft.commands.arguments.MessageArgument;
/*    */ import net.minecraft.commands.arguments.selector.EntitySelector;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.server.players.IpBanList;
/*    */ import net.minecraft.server.players.IpBanListEntry;
/*    */ import net.minecraft.server.players.StoredUserEntry;
/*    */ 
/*    */ public class BanIpCommands {
/* 26 */   private static final SimpleCommandExceptionType ERROR_INVALID_IP = new SimpleCommandExceptionType((Message)Component.translatable("commands.banip.invalid"));
/* 27 */   private static final SimpleCommandExceptionType ERROR_ALREADY_BANNED = new SimpleCommandExceptionType((Message)Component.translatable("commands.banip.failed"));
/*    */   
/*    */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/* 30 */     $$0.register(
/* 31 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("ban-ip")
/* 32 */         .requires($$0 -> $$0.hasPermission(3)))
/* 33 */         .then((
/* 34 */           (RequiredArgumentBuilder)Commands.argument("target", (ArgumentType)StringArgumentType.word())
/* 35 */           .executes($$0 -> banIpOrName((CommandSourceStack)$$0.getSource(), StringArgumentType.getString($$0, "target"), null)))
/* 36 */           .then(
/* 37 */             Commands.argument("reason", (ArgumentType)MessageArgument.message())
/* 38 */             .executes($$0 -> banIpOrName((CommandSourceStack)$$0.getSource(), StringArgumentType.getString($$0, "target"), MessageArgument.getMessage($$0, "reason"))))));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static int banIpOrName(CommandSourceStack $$0, String $$1, @Nullable Component $$2) throws CommandSyntaxException {
/* 45 */     if (InetAddresses.isInetAddress($$1)) {
/* 46 */       return banIp($$0, $$1, $$2);
/*    */     }
/* 48 */     ServerPlayer $$3 = $$0.getServer().getPlayerList().getPlayerByName($$1);
/* 49 */     if ($$3 != null) {
/* 50 */       return banIp($$0, $$3.getIpAddress(), $$2);
/*    */     }
/*    */     
/* 53 */     throw ERROR_INVALID_IP.create();
/*    */   }
/*    */   
/*    */   private static int banIp(CommandSourceStack $$0, String $$1, @Nullable Component $$2) throws CommandSyntaxException {
/* 57 */     IpBanList $$3 = $$0.getServer().getPlayerList().getIpBans();
/* 58 */     if ($$3.isBanned($$1)) {
/* 59 */       throw ERROR_ALREADY_BANNED.create();
/*    */     }
/* 61 */     List<ServerPlayer> $$4 = $$0.getServer().getPlayerList().getPlayersWithAddress($$1);
/* 62 */     IpBanListEntry $$5 = new IpBanListEntry($$1, null, $$0.getTextName(), null, ($$2 == null) ? null : $$2.getString());
/* 63 */     $$3.add((StoredUserEntry)$$5);
/*    */     
/* 65 */     $$0.sendSuccess(() -> Component.translatable("commands.banip.success", new Object[] { $$0, $$1.getReason() }), true);
/* 66 */     if (!$$4.isEmpty()) {
/* 67 */       $$0.sendSuccess(() -> Component.translatable("commands.banip.info", new Object[] { Integer.valueOf($$0.size()), EntitySelector.joinNames($$0) }), true);
/*    */     }
/*    */     
/* 70 */     for (ServerPlayer $$6 : $$4) {
/* 71 */       $$6.connection.disconnect((Component)Component.translatable("multiplayer.disconnect.ip_banned"));
/*    */     }
/*    */     
/* 74 */     return $$4.size();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\BanIpCommands.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */