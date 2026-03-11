/*     */ package net.minecraft.server.commands;
/*     */ import com.mojang.brigadier.CommandDispatcher;
/*     */ import com.mojang.brigadier.Message;
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import com.mojang.brigadier.arguments.IntegerArgumentType;
/*     */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*     */ import com.mojang.brigadier.builder.RequiredArgumentBuilder;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*     */ import com.mojang.brigadier.suggestion.Suggestions;
/*     */ import com.mojang.brigadier.suggestion.SuggestionsBuilder;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.Commands;
/*     */ import net.minecraft.commands.SharedSuggestionProvider;
/*     */ import net.minecraft.commands.arguments.ObjectiveArgument;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.server.ServerScoreboard;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.scores.Objective;
/*     */ import net.minecraft.world.scores.ReadOnlyScoreInfo;
/*     */ import net.minecraft.world.scores.ScoreAccess;
/*     */ import net.minecraft.world.scores.ScoreHolder;
/*     */ import net.minecraft.world.scores.Scoreboard;
/*     */ import net.minecraft.world.scores.criteria.ObjectiveCriteria;
/*     */ 
/*     */ public class TriggerCommand {
/*  31 */   private static final SimpleCommandExceptionType ERROR_NOT_PRIMED = new SimpleCommandExceptionType((Message)Component.translatable("commands.trigger.failed.unprimed"));
/*  32 */   private static final SimpleCommandExceptionType ERROR_INVALID_OBJECTIVE = new SimpleCommandExceptionType((Message)Component.translatable("commands.trigger.failed.invalid"));
/*     */   
/*     */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/*  35 */     $$0.register(
/*  36 */         (LiteralArgumentBuilder)Commands.literal("trigger")
/*  37 */         .then((
/*  38 */           (RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("objective", (ArgumentType)ObjectiveArgument.objective())
/*  39 */           .suggests(($$0, $$1) -> suggestObjectives((CommandSourceStack)$$0.getSource(), $$1))
/*  40 */           .executes($$0 -> simpleTrigger((CommandSourceStack)$$0.getSource(), ((CommandSourceStack)$$0.getSource()).getPlayerOrException(), ObjectiveArgument.getObjective($$0, "objective"))))
/*  41 */           .then(
/*  42 */             Commands.literal("add")
/*  43 */             .then(
/*  44 */               Commands.argument("value", (ArgumentType)IntegerArgumentType.integer())
/*  45 */               .executes($$0 -> addValue((CommandSourceStack)$$0.getSource(), ((CommandSourceStack)$$0.getSource()).getPlayerOrException(), ObjectiveArgument.getObjective($$0, "objective"), IntegerArgumentType.getInteger($$0, "value"))))))
/*     */ 
/*     */           
/*  48 */           .then(
/*  49 */             Commands.literal("set")
/*  50 */             .then(
/*  51 */               Commands.argument("value", (ArgumentType)IntegerArgumentType.integer())
/*  52 */               .executes($$0 -> setValue((CommandSourceStack)$$0.getSource(), ((CommandSourceStack)$$0.getSource()).getPlayerOrException(), ObjectiveArgument.getObjective($$0, "objective"), IntegerArgumentType.getInteger($$0, "value")))))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CompletableFuture<Suggestions> suggestObjectives(CommandSourceStack $$0, SuggestionsBuilder $$1) {
/*  60 */     Entity entity = $$0.getEntity();
/*  61 */     List<String> $$3 = Lists.newArrayList();
/*     */     
/*  63 */     if (entity != null) {
/*  64 */       ServerScoreboard serverScoreboard = $$0.getServer().getScoreboard();
/*     */       
/*  66 */       for (Objective $$5 : serverScoreboard.getObjectives()) {
/*  67 */         if ($$5.getCriteria() == ObjectiveCriteria.TRIGGER) {
/*  68 */           ReadOnlyScoreInfo $$6 = serverScoreboard.getPlayerScoreInfo((ScoreHolder)entity, $$5);
/*  69 */           if ($$6 != null && !$$6.isLocked()) {
/*  70 */             $$3.add($$5.getName());
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  76 */     return SharedSuggestionProvider.suggest($$3, $$1);
/*     */   }
/*     */   
/*     */   private static int addValue(CommandSourceStack $$0, ServerPlayer $$1, Objective $$2, int $$3) throws CommandSyntaxException {
/*  80 */     ScoreAccess $$4 = getScore((Scoreboard)$$0.getServer().getScoreboard(), (ScoreHolder)$$1, $$2);
/*  81 */     int $$5 = $$4.add($$3);
/*  82 */     $$0.sendSuccess(() -> Component.translatable("commands.trigger.add.success", new Object[] { $$0.getFormattedDisplayName(), Integer.valueOf($$1) }), true);
/*  83 */     return $$5;
/*     */   }
/*     */   
/*     */   private static int setValue(CommandSourceStack $$0, ServerPlayer $$1, Objective $$2, int $$3) throws CommandSyntaxException {
/*  87 */     ScoreAccess $$4 = getScore((Scoreboard)$$0.getServer().getScoreboard(), (ScoreHolder)$$1, $$2);
/*  88 */     $$4.set($$3);
/*  89 */     $$0.sendSuccess(() -> Component.translatable("commands.trigger.set.success", new Object[] { $$0.getFormattedDisplayName(), Integer.valueOf($$1) }), true);
/*  90 */     return $$3;
/*     */   }
/*     */   
/*     */   private static int simpleTrigger(CommandSourceStack $$0, ServerPlayer $$1, Objective $$2) throws CommandSyntaxException {
/*  94 */     ScoreAccess $$3 = getScore((Scoreboard)$$0.getServer().getScoreboard(), (ScoreHolder)$$1, $$2);
/*  95 */     int $$4 = $$3.add(1);
/*  96 */     $$0.sendSuccess(() -> Component.translatable("commands.trigger.simple.success", new Object[] { $$0.getFormattedDisplayName() }), true);
/*  97 */     return $$4;
/*     */   }
/*     */   
/*     */   private static ScoreAccess getScore(Scoreboard $$0, ScoreHolder $$1, Objective $$2) throws CommandSyntaxException {
/* 101 */     if ($$2.getCriteria() != ObjectiveCriteria.TRIGGER) {
/* 102 */       throw ERROR_INVALID_OBJECTIVE.create();
/*     */     }
/*     */     
/* 105 */     ReadOnlyScoreInfo $$3 = $$0.getPlayerScoreInfo($$1, $$2);
/*     */     
/* 107 */     if ($$3 == null || $$3.isLocked()) {
/* 108 */       throw ERROR_NOT_PRIMED.create();
/*     */     }
/*     */     
/* 111 */     ScoreAccess $$4 = $$0.getOrCreatePlayerScore($$1, $$2);
/* 112 */     $$4.lock();
/* 113 */     return $$4;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\TriggerCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */