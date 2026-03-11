/*    */ package net.minecraft.server.commands;
/*    */ 
/*    */ import com.google.common.collect.Iterables;
/*    */ import com.google.common.collect.Lists;
/*    */ import com.mojang.brigadier.CommandDispatcher;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import java.util.Collection;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.Commands;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.server.players.BanListEntry;
/*    */ import net.minecraft.server.players.PlayerList;
/*    */ 
/*    */ public class BanListCommands {
/*    */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/* 18 */     $$0.register(
/* 19 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("banlist")
/* 20 */         .requires($$0 -> $$0.hasPermission(3)))
/* 21 */         .executes($$0 -> {
/*    */             PlayerList $$1 = ((CommandSourceStack)$$0.getSource()).getServer().getPlayerList();
/*    */             
/*    */             return showList((CommandSourceStack)$$0.getSource(), Lists.newArrayList(Iterables.concat($$1.getBans().getEntries(), $$1.getIpBans().getEntries())));
/* 25 */           })).then(
/* 26 */           Commands.literal("ips")
/* 27 */           .executes($$0 -> showList((CommandSourceStack)$$0.getSource(), ((CommandSourceStack)$$0.getSource()).getServer().getPlayerList().getIpBans().getEntries()))))
/*    */         
/* 29 */         .then(
/* 30 */           Commands.literal("players")
/* 31 */           .executes($$0 -> showList((CommandSourceStack)$$0.getSource(), ((CommandSourceStack)$$0.getSource()).getServer().getPlayerList().getBans().getEntries()))));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static int showList(CommandSourceStack $$0, Collection<? extends BanListEntry<?>> $$1) {
/* 37 */     if ($$1.isEmpty()) {
/* 38 */       $$0.sendSuccess(() -> Component.translatable("commands.banlist.none"), false);
/*    */     } else {
/* 40 */       $$0.sendSuccess(() -> Component.translatable("commands.banlist.list", new Object[] { Integer.valueOf($$0.size()) }), false);
/* 41 */       for (BanListEntry<?> $$2 : $$1) {
/* 42 */         $$0.sendSuccess(() -> Component.translatable("commands.banlist.entry", new Object[] { $$0.getDisplayName(), $$0.getSource(), $$0.getReason() }), false);
/*    */       } 
/*    */     } 
/* 45 */     return $$1.size();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\BanListCommands.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */