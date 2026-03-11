/*     */ package net.minecraft.server.commands;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.brigadier.CommandDispatcher;
/*     */ import com.mojang.brigadier.Message;
/*     */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*     */ import com.mojang.brigadier.suggestion.SuggestionsBuilder;
/*     */ import java.util.Collection;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.Commands;
/*     */ import net.minecraft.commands.SharedSuggestionProvider;
/*     */ import net.minecraft.commands.arguments.GameProfileArgument;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.server.players.PlayerList;
/*     */ import net.minecraft.server.players.UserWhiteList;
/*     */ import net.minecraft.server.players.UserWhiteListEntry;
/*     */ 
/*     */ public class WhitelistCommand {
/*  23 */   private static final SimpleCommandExceptionType ERROR_ALREADY_ENABLED = new SimpleCommandExceptionType((Message)Component.translatable("commands.whitelist.alreadyOn"));
/*  24 */   private static final SimpleCommandExceptionType ERROR_ALREADY_DISABLED = new SimpleCommandExceptionType((Message)Component.translatable("commands.whitelist.alreadyOff"));
/*  25 */   private static final SimpleCommandExceptionType ERROR_ALREADY_WHITELISTED = new SimpleCommandExceptionType((Message)Component.translatable("commands.whitelist.add.failed"));
/*  26 */   private static final SimpleCommandExceptionType ERROR_NOT_WHITELISTED = new SimpleCommandExceptionType((Message)Component.translatable("commands.whitelist.remove.failed"));
/*     */   
/*     */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/*  29 */     $$0.register(
/*  30 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("whitelist")
/*  31 */         .requires($$0 -> $$0.hasPermission(3)))
/*  32 */         .then(
/*  33 */           Commands.literal("on")
/*  34 */           .executes($$0 -> enableWhitelist((CommandSourceStack)$$0.getSource()))))
/*     */         
/*  36 */         .then(
/*  37 */           Commands.literal("off")
/*  38 */           .executes($$0 -> disableWhitelist((CommandSourceStack)$$0.getSource()))))
/*     */         
/*  40 */         .then(
/*  41 */           Commands.literal("list")
/*  42 */           .executes($$0 -> showList((CommandSourceStack)$$0.getSource()))))
/*     */         
/*  44 */         .then(
/*  45 */           Commands.literal("add")
/*  46 */           .then(
/*  47 */             Commands.argument("targets", (ArgumentType)GameProfileArgument.gameProfile())
/*  48 */             .suggests(($$0, $$1) -> {
/*     */                 PlayerList $$2 = ((CommandSourceStack)$$0.getSource()).getServer().getPlayerList();
/*     */                 
/*     */                 return SharedSuggestionProvider.suggest($$2.getPlayers().stream().filter(()).map(()), $$1);
/*  52 */               }).executes($$0 -> addPlayers((CommandSourceStack)$$0.getSource(), GameProfileArgument.getGameProfiles($$0, "targets"))))))
/*     */ 
/*     */         
/*  55 */         .then(
/*  56 */           Commands.literal("remove")
/*  57 */           .then(
/*  58 */             Commands.argument("targets", (ArgumentType)GameProfileArgument.gameProfile())
/*  59 */             .suggests(($$0, $$1) -> SharedSuggestionProvider.suggest(((CommandSourceStack)$$0.getSource()).getServer().getPlayerList().getWhiteListNames(), $$1))
/*  60 */             .executes($$0 -> removePlayers((CommandSourceStack)$$0.getSource(), GameProfileArgument.getGameProfiles($$0, "targets"))))))
/*     */ 
/*     */         
/*  63 */         .then(
/*  64 */           Commands.literal("reload")
/*  65 */           .executes($$0 -> reload((CommandSourceStack)$$0.getSource()))));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int reload(CommandSourceStack $$0) {
/*  71 */     $$0.getServer().getPlayerList().reloadWhiteList();
/*  72 */     $$0.sendSuccess(() -> Component.translatable("commands.whitelist.reloaded"), true);
/*  73 */     $$0.getServer().kickUnlistedPlayers($$0);
/*  74 */     return 1;
/*     */   }
/*     */   
/*     */   private static int addPlayers(CommandSourceStack $$0, Collection<GameProfile> $$1) throws CommandSyntaxException {
/*  78 */     UserWhiteList $$2 = $$0.getServer().getPlayerList().getWhiteList();
/*  79 */     int $$3 = 0;
/*     */     
/*  81 */     for (GameProfile $$4 : $$1) {
/*  82 */       if (!$$2.isWhiteListed($$4)) {
/*  83 */         UserWhiteListEntry $$5 = new UserWhiteListEntry($$4);
/*  84 */         $$2.add((StoredUserEntry)$$5);
/*  85 */         $$0.sendSuccess(() -> Component.translatable("commands.whitelist.add.success", new Object[] { Component.literal($$0.getName()) }), true);
/*  86 */         $$3++;
/*     */       } 
/*     */     } 
/*     */     
/*  90 */     if ($$3 == 0) {
/*  91 */       throw ERROR_ALREADY_WHITELISTED.create();
/*     */     }
/*     */     
/*  94 */     return $$3;
/*     */   }
/*     */   
/*     */   private static int removePlayers(CommandSourceStack $$0, Collection<GameProfile> $$1) throws CommandSyntaxException {
/*  98 */     UserWhiteList $$2 = $$0.getServer().getPlayerList().getWhiteList();
/*  99 */     int $$3 = 0;
/*     */     
/* 101 */     for (GameProfile $$4 : $$1) {
/* 102 */       if ($$2.isWhiteListed($$4)) {
/* 103 */         UserWhiteListEntry $$5 = new UserWhiteListEntry($$4);
/* 104 */         $$2.remove((StoredUserEntry)$$5);
/* 105 */         $$0.sendSuccess(() -> Component.translatable("commands.whitelist.remove.success", new Object[] { Component.literal($$0.getName()) }), true);
/* 106 */         $$3++;
/*     */       } 
/*     */     } 
/*     */     
/* 110 */     if ($$3 == 0) {
/* 111 */       throw ERROR_NOT_WHITELISTED.create();
/*     */     }
/*     */     
/* 114 */     $$0.getServer().kickUnlistedPlayers($$0);
/* 115 */     return $$3;
/*     */   }
/*     */   
/*     */   private static int enableWhitelist(CommandSourceStack $$0) throws CommandSyntaxException {
/* 119 */     PlayerList $$1 = $$0.getServer().getPlayerList();
/* 120 */     if ($$1.isUsingWhitelist()) {
/* 121 */       throw ERROR_ALREADY_ENABLED.create();
/*     */     }
/* 123 */     $$1.setUsingWhiteList(true);
/* 124 */     $$0.sendSuccess(() -> Component.translatable("commands.whitelist.enabled"), true);
/* 125 */     $$0.getServer().kickUnlistedPlayers($$0);
/* 126 */     return 1;
/*     */   }
/*     */   
/*     */   private static int disableWhitelist(CommandSourceStack $$0) throws CommandSyntaxException {
/* 130 */     PlayerList $$1 = $$0.getServer().getPlayerList();
/* 131 */     if (!$$1.isUsingWhitelist()) {
/* 132 */       throw ERROR_ALREADY_DISABLED.create();
/*     */     }
/* 134 */     $$1.setUsingWhiteList(false);
/* 135 */     $$0.sendSuccess(() -> Component.translatable("commands.whitelist.disabled"), true);
/* 136 */     return 1;
/*     */   }
/*     */   
/*     */   private static int showList(CommandSourceStack $$0) {
/* 140 */     String[] $$1 = $$0.getServer().getPlayerList().getWhiteListNames();
/* 141 */     if ($$1.length == 0) {
/* 142 */       $$0.sendSuccess(() -> Component.translatable("commands.whitelist.none"), false);
/*     */     } else {
/* 144 */       $$0.sendSuccess(() -> Component.translatable("commands.whitelist.list", new Object[] { Integer.valueOf($$0.length), String.join(", ", (CharSequence[])$$0) }), false);
/*     */     } 
/* 146 */     return $$1.length;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\WhitelistCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */