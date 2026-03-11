/*     */ package net.minecraft.server.commands;
/*     */ 
/*     */ import com.mojang.brigadier.CommandDispatcher;
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import com.mojang.brigadier.arguments.IntegerArgumentType;
/*     */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*     */ import com.mojang.brigadier.builder.RequiredArgumentBuilder;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import java.util.Collection;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.Commands;
/*     */ import net.minecraft.commands.arguments.ComponentArgument;
/*     */ import net.minecraft.commands.arguments.EntityArgument;
/*     */ import net.minecraft.commands.arguments.TimeArgument;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.ComponentUtils;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.game.ClientboundClearTitlesPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TitleCommand
/*     */ {
/*     */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/*  31 */     $$0.register(
/*  32 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("title")
/*  33 */         .requires($$0 -> $$0.hasPermission(2)))
/*  34 */         .then((
/*  35 */           (RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("targets", (ArgumentType)EntityArgument.players())
/*  36 */           .then(
/*  37 */             Commands.literal("clear")
/*  38 */             .executes($$0 -> clearTitle((CommandSourceStack)$$0.getSource(), EntityArgument.getPlayers($$0, "targets")))))
/*     */           
/*  40 */           .then(
/*  41 */             Commands.literal("reset")
/*  42 */             .executes($$0 -> resetTitle((CommandSourceStack)$$0.getSource(), EntityArgument.getPlayers($$0, "targets")))))
/*     */           
/*  44 */           .then(
/*  45 */             Commands.literal("title")
/*  46 */             .then(
/*  47 */               Commands.argument("title", (ArgumentType)ComponentArgument.textComponent())
/*  48 */               .executes($$0 -> showTitle((CommandSourceStack)$$0.getSource(), EntityArgument.getPlayers($$0, "targets"), ComponentArgument.getComponent($$0, "title"), "title", net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket::new)))))
/*     */ 
/*     */           
/*  51 */           .then(
/*  52 */             Commands.literal("subtitle")
/*  53 */             .then(
/*  54 */               Commands.argument("title", (ArgumentType)ComponentArgument.textComponent())
/*  55 */               .executes($$0 -> showTitle((CommandSourceStack)$$0.getSource(), EntityArgument.getPlayers($$0, "targets"), ComponentArgument.getComponent($$0, "title"), "subtitle", net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket::new)))))
/*     */ 
/*     */           
/*  58 */           .then(
/*  59 */             Commands.literal("actionbar")
/*  60 */             .then(
/*  61 */               Commands.argument("title", (ArgumentType)ComponentArgument.textComponent())
/*  62 */               .executes($$0 -> showTitle((CommandSourceStack)$$0.getSource(), EntityArgument.getPlayers($$0, "targets"), ComponentArgument.getComponent($$0, "title"), "actionbar", net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket::new)))))
/*     */ 
/*     */           
/*  65 */           .then(
/*  66 */             Commands.literal("times")
/*  67 */             .then(
/*  68 */               Commands.argument("fadeIn", (ArgumentType)TimeArgument.time())
/*  69 */               .then(
/*  70 */                 Commands.argument("stay", (ArgumentType)TimeArgument.time())
/*  71 */                 .then(
/*  72 */                   Commands.argument("fadeOut", (ArgumentType)TimeArgument.time())
/*  73 */                   .executes($$0 -> setTimes((CommandSourceStack)$$0.getSource(), EntityArgument.getPlayers($$0, "targets"), IntegerArgumentType.getInteger($$0, "fadeIn"), IntegerArgumentType.getInteger($$0, "stay"), IntegerArgumentType.getInteger($$0, "fadeOut")))))))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int clearTitle(CommandSourceStack $$0, Collection<ServerPlayer> $$1) {
/*  83 */     ClientboundClearTitlesPacket $$2 = new ClientboundClearTitlesPacket(false);
/*  84 */     for (ServerPlayer $$3 : $$1) {
/*  85 */       $$3.connection.send((Packet)$$2);
/*     */     }
/*     */     
/*  88 */     if ($$1.size() == 1) {
/*  89 */       $$0.sendSuccess(() -> Component.translatable("commands.title.cleared.single", new Object[] { ((ServerPlayer)$$0.iterator().next()).getDisplayName() }), true);
/*     */     } else {
/*  91 */       $$0.sendSuccess(() -> Component.translatable("commands.title.cleared.multiple", new Object[] { Integer.valueOf($$0.size()) }), true);
/*     */     } 
/*     */     
/*  94 */     return $$1.size();
/*     */   }
/*     */   
/*     */   private static int resetTitle(CommandSourceStack $$0, Collection<ServerPlayer> $$1) {
/*  98 */     ClientboundClearTitlesPacket $$2 = new ClientboundClearTitlesPacket(true);
/*  99 */     for (ServerPlayer $$3 : $$1) {
/* 100 */       $$3.connection.send((Packet)$$2);
/*     */     }
/*     */     
/* 103 */     if ($$1.size() == 1) {
/* 104 */       $$0.sendSuccess(() -> Component.translatable("commands.title.reset.single", new Object[] { ((ServerPlayer)$$0.iterator().next()).getDisplayName() }), true);
/*     */     } else {
/* 106 */       $$0.sendSuccess(() -> Component.translatable("commands.title.reset.multiple", new Object[] { Integer.valueOf($$0.size()) }), true);
/*     */     } 
/*     */     
/* 109 */     return $$1.size();
/*     */   }
/*     */   
/*     */   private static int showTitle(CommandSourceStack $$0, Collection<ServerPlayer> $$1, Component $$2, String $$3, Function<Component, Packet<?>> $$4) throws CommandSyntaxException {
/* 113 */     for (ServerPlayer $$5 : $$1) {
/* 114 */       $$5.connection.send($$4.apply(ComponentUtils.updateForEntity($$0, $$2, (Entity)$$5, 0)));
/*     */     }
/*     */     
/* 117 */     if ($$1.size() == 1) {
/* 118 */       $$0.sendSuccess(() -> Component.translatable("commands.title.show." + $$0 + ".single", new Object[] { ((ServerPlayer)$$1.iterator().next()).getDisplayName() }), true);
/*     */     } else {
/* 120 */       $$0.sendSuccess(() -> Component.translatable("commands.title.show." + $$0 + ".multiple", new Object[] { Integer.valueOf($$1.size()) }), true);
/*     */     } 
/*     */     
/* 123 */     return $$1.size();
/*     */   }
/*     */   
/*     */   private static int setTimes(CommandSourceStack $$0, Collection<ServerPlayer> $$1, int $$2, int $$3, int $$4) {
/* 127 */     ClientboundSetTitlesAnimationPacket $$5 = new ClientboundSetTitlesAnimationPacket($$2, $$3, $$4);
/* 128 */     for (ServerPlayer $$6 : $$1) {
/* 129 */       $$6.connection.send((Packet)$$5);
/*     */     }
/*     */     
/* 132 */     if ($$1.size() == 1) {
/* 133 */       $$0.sendSuccess(() -> Component.translatable("commands.title.times.single", new Object[] { ((ServerPlayer)$$0.iterator().next()).getDisplayName() }), true);
/*     */     } else {
/* 135 */       $$0.sendSuccess(() -> Component.translatable("commands.title.times.multiple", new Object[] { Integer.valueOf($$0.size()) }), true);
/*     */     } 
/*     */     
/* 138 */     return $$1.size();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\TitleCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */