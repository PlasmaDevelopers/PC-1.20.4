/*    */ package net.minecraft.server.commands;
/*    */ import com.mojang.brigadier.CommandDispatcher;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import java.util.List;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.Commands;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.ComponentUtils;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.server.players.PlayerList;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ 
/*    */ public class ListPlayersCommand {
/*    */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/* 18 */     $$0.register(
/* 19 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("list")
/* 20 */         .executes($$0 -> listPlayers((CommandSourceStack)$$0.getSource())))
/* 21 */         .then(
/* 22 */           Commands.literal("uuids")
/* 23 */           .executes($$0 -> listPlayersWithUuids((CommandSourceStack)$$0.getSource()))));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static int listPlayers(CommandSourceStack $$0) {
/* 29 */     return format($$0, Player::getDisplayName);
/*    */   }
/*    */   
/*    */   private static int listPlayersWithUuids(CommandSourceStack $$0) {
/* 33 */     return format($$0, $$0 -> Component.translatable("commands.list.nameAndId", new Object[] { $$0.getName(), Component.translationArg($$0.getGameProfile().getId()) }));
/*    */   }
/*    */   
/*    */   private static int format(CommandSourceStack $$0, Function<ServerPlayer, Component> $$1) {
/* 37 */     PlayerList $$2 = $$0.getServer().getPlayerList();
/* 38 */     List<ServerPlayer> $$3 = $$2.getPlayers();
/* 39 */     Component $$4 = ComponentUtils.formatList($$3, $$1);
/* 40 */     $$0.sendSuccess(() -> Component.translatable("commands.list.players", new Object[] { Integer.valueOf($$0.size()), Integer.valueOf($$1.getMaxPlayers()), $$2 }), false);
/* 41 */     return $$3.size();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\ListPlayersCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */