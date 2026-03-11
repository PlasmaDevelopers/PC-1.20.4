/*     */ package net.minecraft.server.commands;
/*     */ 
/*     */ import com.mojang.brigadier.CommandDispatcher;
/*     */ import com.mojang.brigadier.Message;
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import com.mojang.brigadier.arguments.IntegerArgumentType;
/*     */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*     */ import com.mojang.brigadier.builder.RequiredArgumentBuilder;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*     */ import com.mojang.brigadier.tree.CommandNode;
/*     */ import com.mojang.brigadier.tree.LiteralCommandNode;
/*     */ import java.util.Collection;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.BiPredicate;
/*     */ import java.util.function.ToIntFunction;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.Commands;
/*     */ import net.minecraft.commands.arguments.EntityArgument;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ 
/*     */ 
/*     */ public class ExperienceCommand
/*     */ {
/*  29 */   private static final SimpleCommandExceptionType ERROR_SET_POINTS_INVALID = new SimpleCommandExceptionType((Message)Component.translatable("commands.experience.set.points.invalid"));
/*     */   
/*     */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/*  32 */     LiteralCommandNode<CommandSourceStack> $$1 = $$0.register(
/*  33 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("experience")
/*  34 */         .requires($$0 -> $$0.hasPermission(2)))
/*  35 */         .then(
/*  36 */           Commands.literal("add")
/*  37 */           .then(
/*  38 */             Commands.argument("targets", (ArgumentType)EntityArgument.players())
/*  39 */             .then((
/*  40 */               (RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("amount", (ArgumentType)IntegerArgumentType.integer())
/*  41 */               .executes($$0 -> addExperience((CommandSourceStack)$$0.getSource(), EntityArgument.getPlayers($$0, "targets"), IntegerArgumentType.getInteger($$0, "amount"), Type.POINTS)))
/*  42 */               .then(
/*  43 */                 Commands.literal("points")
/*  44 */                 .executes($$0 -> addExperience((CommandSourceStack)$$0.getSource(), EntityArgument.getPlayers($$0, "targets"), IntegerArgumentType.getInteger($$0, "amount"), Type.POINTS))))
/*     */               
/*  46 */               .then(
/*  47 */                 Commands.literal("levels")
/*  48 */                 .executes($$0 -> addExperience((CommandSourceStack)$$0.getSource(), EntityArgument.getPlayers($$0, "targets"), IntegerArgumentType.getInteger($$0, "amount"), Type.LEVELS)))))))
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  53 */         .then(
/*  54 */           Commands.literal("set")
/*  55 */           .then(
/*  56 */             Commands.argument("targets", (ArgumentType)EntityArgument.players())
/*  57 */             .then((
/*  58 */               (RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("amount", (ArgumentType)IntegerArgumentType.integer(0))
/*  59 */               .executes($$0 -> setExperience((CommandSourceStack)$$0.getSource(), EntityArgument.getPlayers($$0, "targets"), IntegerArgumentType.getInteger($$0, "amount"), Type.POINTS)))
/*  60 */               .then(
/*  61 */                 Commands.literal("points")
/*  62 */                 .executes($$0 -> setExperience((CommandSourceStack)$$0.getSource(), EntityArgument.getPlayers($$0, "targets"), IntegerArgumentType.getInteger($$0, "amount"), Type.POINTS))))
/*     */               
/*  64 */               .then(
/*  65 */                 Commands.literal("levels")
/*  66 */                 .executes($$0 -> setExperience((CommandSourceStack)$$0.getSource(), EntityArgument.getPlayers($$0, "targets"), IntegerArgumentType.getInteger($$0, "amount"), Type.LEVELS)))))))
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  71 */         .then(
/*  72 */           Commands.literal("query")
/*  73 */           .then((
/*  74 */             (RequiredArgumentBuilder)Commands.argument("targets", (ArgumentType)EntityArgument.player())
/*  75 */             .then(
/*  76 */               Commands.literal("points")
/*  77 */               .executes($$0 -> queryExperience((CommandSourceStack)$$0.getSource(), EntityArgument.getPlayer($$0, "targets"), Type.POINTS))))
/*     */             
/*  79 */             .then(
/*  80 */               Commands.literal("levels")
/*  81 */               .executes($$0 -> queryExperience((CommandSourceStack)$$0.getSource(), EntityArgument.getPlayer($$0, "targets"), Type.LEVELS))))));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  87 */     $$0.register(
/*  88 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("xp")
/*  89 */         .requires($$0 -> $$0.hasPermission(2)))
/*  90 */         .redirect((CommandNode)$$1));
/*     */   }
/*     */ 
/*     */   
/*     */   private static int queryExperience(CommandSourceStack $$0, ServerPlayer $$1, Type $$2) {
/*  95 */     int $$3 = $$2.query.applyAsInt($$1);
/*  96 */     $$0.sendSuccess(() -> Component.translatable("commands.experience.query." + $$0.name, new Object[] { $$1.getDisplayName(), Integer.valueOf($$2) }), false);
/*  97 */     return $$3;
/*     */   }
/*     */   
/*     */   private static int addExperience(CommandSourceStack $$0, Collection<? extends ServerPlayer> $$1, int $$2, Type $$3) {
/* 101 */     for (ServerPlayer $$4 : $$1) {
/* 102 */       $$3.add.accept($$4, Integer.valueOf($$2));
/*     */     }
/*     */     
/* 105 */     if ($$1.size() == 1) {
/* 106 */       $$0.sendSuccess(() -> Component.translatable("commands.experience.add." + $$0.name + ".success.single", new Object[] { Integer.valueOf($$1), ((ServerPlayer)$$2.iterator().next()).getDisplayName() }), true);
/*     */     } else {
/* 108 */       $$0.sendSuccess(() -> Component.translatable("commands.experience.add." + $$0.name + ".success.multiple", new Object[] { Integer.valueOf($$1), Integer.valueOf($$2.size()) }), true);
/*     */     } 
/*     */     
/* 111 */     return $$1.size();
/*     */   }
/*     */   
/*     */   private static int setExperience(CommandSourceStack $$0, Collection<? extends ServerPlayer> $$1, int $$2, Type $$3) throws CommandSyntaxException {
/* 115 */     int $$4 = 0;
/*     */     
/* 117 */     for (ServerPlayer $$5 : $$1) {
/* 118 */       if ($$3.set.test($$5, Integer.valueOf($$2))) {
/* 119 */         $$4++;
/*     */       }
/*     */     } 
/*     */     
/* 123 */     if ($$4 == 0) {
/* 124 */       throw ERROR_SET_POINTS_INVALID.create();
/*     */     }
/*     */     
/* 127 */     if ($$1.size() == 1) {
/* 128 */       $$0.sendSuccess(() -> Component.translatable("commands.experience.set." + $$0.name + ".success.single", new Object[] { Integer.valueOf($$1), ((ServerPlayer)$$2.iterator().next()).getDisplayName() }), true);
/*     */     } else {
/* 130 */       $$0.sendSuccess(() -> Component.translatable("commands.experience.set." + $$0.name + ".success.multiple", new Object[] { Integer.valueOf($$1), Integer.valueOf($$2.size()) }), true);
/*     */     } 
/*     */     
/* 133 */     return $$1.size();
/*     */   }
/*     */   private enum Type { POINTS, LEVELS;
/*     */     static {
/* 137 */       POINTS = new Type("POINTS", 0, "points", Player::giveExperiencePoints, ($$0, $$1) -> {
/*     */             if ($$1.intValue() >= $$0.getXpNeededForNextLevel()) {
/*     */               return false;
/*     */             }
/*     */             $$0.setExperiencePoints($$1.intValue());
/*     */             return true;
/*     */           }$$0 -> Mth.floor($$0.experienceProgress * $$0.getXpNeededForNextLevel()));
/* 144 */       LEVELS = new Type("LEVELS", 1, "levels", ServerPlayer::giveExperienceLevels, ($$0, $$1) -> {
/*     */             $$0.setExperienceLevels($$1.intValue());
/*     */             return true;
/*     */           }$$0 -> $$0.experienceLevel);
/*     */     }
/*     */     public final BiConsumer<ServerPlayer, Integer> add;
/*     */     public final BiPredicate<ServerPlayer, Integer> set;
/*     */     public final String name;
/*     */     final ToIntFunction<ServerPlayer> query;
/*     */     
/*     */     Type(String $$0, BiConsumer<ServerPlayer, Integer> $$1, BiPredicate<ServerPlayer, Integer> $$2, ToIntFunction<ServerPlayer> $$3) {
/* 155 */       this.add = $$1;
/* 156 */       this.name = $$0;
/* 157 */       this.set = $$2;
/* 158 */       this.query = $$3;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\ExperienceCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */