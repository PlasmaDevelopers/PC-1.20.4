/*     */ package net.minecraft.server.commands;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.brigadier.CommandDispatcher;
/*     */ import com.mojang.brigadier.Message;
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import com.mojang.brigadier.arguments.BoolArgumentType;
/*     */ import com.mojang.brigadier.arguments.IntegerArgumentType;
/*     */ import com.mojang.brigadier.builder.ArgumentBuilder;
/*     */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*     */ import com.mojang.brigadier.builder.RequiredArgumentBuilder;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*     */ import com.mojang.brigadier.suggestion.Suggestions;
/*     */ import com.mojang.brigadier.suggestion.SuggestionsBuilder;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.advancements.critereon.MinMaxBounds;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.Commands;
/*     */ import net.minecraft.commands.SharedSuggestionProvider;
/*     */ import net.minecraft.commands.arguments.RangeArgument;
/*     */ import net.minecraft.commands.arguments.ResourceLocationArgument;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.RandomSequence;
/*     */ import net.minecraft.world.RandomSequences;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RandomCommand
/*     */ {
/*  37 */   private static final SimpleCommandExceptionType ERROR_RANGE_TOO_LARGE = new SimpleCommandExceptionType((Message)Component.translatable("commands.random.error.range_too_large"));
/*  38 */   private static final SimpleCommandExceptionType ERROR_RANGE_TOO_SMALL = new SimpleCommandExceptionType((Message)Component.translatable("commands.random.error.range_too_small"));
/*     */   
/*     */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/*  41 */     $$0.register(
/*  42 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("random")
/*  43 */         .then(
/*  44 */           (ArgumentBuilder)drawRandomValueTree("value", false)))
/*     */         
/*  46 */         .then(
/*  47 */           (ArgumentBuilder)drawRandomValueTree("roll", true)))
/*     */         
/*  49 */         .then((
/*  50 */           (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("reset")
/*  51 */           .requires($$0 -> $$0.hasPermission(2)))
/*  52 */           .then((
/*  53 */             (LiteralArgumentBuilder)Commands.literal("*")
/*  54 */             .executes($$0 -> resetAllSequences((CommandSourceStack)$$0.getSource())))
/*  55 */             .then((
/*  56 */               (RequiredArgumentBuilder)Commands.argument("seed", (ArgumentType)IntegerArgumentType.integer())
/*  57 */               .executes($$0 -> resetAllSequencesAndSetNewDefaults((CommandSourceStack)$$0.getSource(), IntegerArgumentType.getInteger($$0, "seed"), true, true)))
/*     */ 
/*     */               
/*  60 */               .then((
/*  61 */                 (RequiredArgumentBuilder)Commands.argument("includeWorldSeed", (ArgumentType)BoolArgumentType.bool())
/*  62 */                 .executes($$0 -> resetAllSequencesAndSetNewDefaults((CommandSourceStack)$$0.getSource(), IntegerArgumentType.getInteger($$0, "seed"), BoolArgumentType.getBool($$0, "includeWorldSeed"), true)))
/*     */ 
/*     */                 
/*  65 */                 .then(
/*  66 */                   Commands.argument("includeSequenceId", (ArgumentType)BoolArgumentType.bool())
/*  67 */                   .executes($$0 -> resetAllSequencesAndSetNewDefaults((CommandSourceStack)$$0.getSource(), IntegerArgumentType.getInteger($$0, "seed"), BoolArgumentType.getBool($$0, "includeWorldSeed"), BoolArgumentType.getBool($$0, "includeSequenceId"))))))))
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*  74 */           .then((
/*  75 */             (RequiredArgumentBuilder)Commands.argument("sequence", (ArgumentType)ResourceLocationArgument.id())
/*  76 */             .suggests(RandomCommand::suggestRandomSequence)
/*  77 */             .executes($$0 -> resetSequence((CommandSourceStack)$$0.getSource(), ResourceLocationArgument.getId($$0, "sequence"))))
/*  78 */             .then((
/*  79 */               (RequiredArgumentBuilder)Commands.argument("seed", (ArgumentType)IntegerArgumentType.integer())
/*  80 */               .executes($$0 -> resetSequence((CommandSourceStack)$$0.getSource(), ResourceLocationArgument.getId($$0, "sequence"), IntegerArgumentType.getInteger($$0, "seed"), true, true)))
/*     */ 
/*     */               
/*  83 */               .then((
/*  84 */                 (RequiredArgumentBuilder)Commands.argument("includeWorldSeed", (ArgumentType)BoolArgumentType.bool())
/*  85 */                 .executes($$0 -> resetSequence((CommandSourceStack)$$0.getSource(), ResourceLocationArgument.getId($$0, "sequence"), IntegerArgumentType.getInteger($$0, "seed"), BoolArgumentType.getBool($$0, "includeWorldSeed"), true)))
/*     */ 
/*     */                 
/*  88 */                 .then(
/*  89 */                   Commands.argument("includeSequenceId", (ArgumentType)BoolArgumentType.bool())
/*  90 */                   .executes($$0 -> resetSequence((CommandSourceStack)$$0.getSource(), ResourceLocationArgument.getId($$0, "sequence"), IntegerArgumentType.getInteger($$0, "seed"), BoolArgumentType.getBool($$0, "includeWorldSeed"), BoolArgumentType.getBool($$0, "includeSequenceId")))))))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static LiteralArgumentBuilder<CommandSourceStack> drawRandomValueTree(String $$0, boolean $$1) {
/* 102 */     return (LiteralArgumentBuilder<CommandSourceStack>)Commands.literal($$0)
/* 103 */       .then((
/* 104 */         (RequiredArgumentBuilder)Commands.argument("range", (ArgumentType)RangeArgument.intRange())
/* 105 */         .executes($$1 -> randomSample((CommandSourceStack)$$1.getSource(), RangeArgument.Ints.getRange($$1, "range"), null, $$0)))
/* 106 */         .then((
/* 107 */           (RequiredArgumentBuilder)Commands.argument("sequence", (ArgumentType)ResourceLocationArgument.id())
/* 108 */           .suggests(RandomCommand::suggestRandomSequence)
/* 109 */           .requires($$0 -> $$0.hasPermission(2)))
/* 110 */           .executes($$1 -> randomSample((CommandSourceStack)$$1.getSource(), RangeArgument.Ints.getRange($$1, "range"), ResourceLocationArgument.getId($$1, "sequence"), $$0))));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static CompletableFuture<Suggestions> suggestRandomSequence(CommandContext<CommandSourceStack> $$0, SuggestionsBuilder $$1) {
/* 116 */     List<String> $$2 = Lists.newArrayList();
/* 117 */     ((CommandSourceStack)$$0.getSource()).getLevel().getRandomSequences().forAllSequences(($$1, $$2) -> $$0.add($$1.toString()));
/* 118 */     return SharedSuggestionProvider.suggest($$2, $$1);
/*     */   }
/*     */   
/*     */   private static int randomSample(CommandSourceStack $$0, MinMaxBounds.Ints $$1, @Nullable ResourceLocation $$2, boolean $$3) throws CommandSyntaxException {
/*     */     RandomSource $$5;
/* 123 */     if ($$2 != null) {
/* 124 */       RandomSource $$4 = $$0.getLevel().getRandomSequence($$2);
/*     */     } else {
/* 126 */       $$5 = $$0.getLevel().getRandom();
/*     */     } 
/*     */     
/* 129 */     int $$6 = ((Integer)$$1.min().orElse(Integer.valueOf(-2147483648))).intValue();
/* 130 */     int $$7 = ((Integer)$$1.max().orElse(Integer.valueOf(2147483647))).intValue();
/* 131 */     long $$8 = $$7 - $$6;
/* 132 */     if ($$8 == 0L) {
/* 133 */       throw ERROR_RANGE_TOO_SMALL.create();
/*     */     }
/* 135 */     if ($$8 >= 2147483647L) {
/* 136 */       throw ERROR_RANGE_TOO_LARGE.create();
/*     */     }
/* 138 */     int $$9 = Mth.randomBetweenInclusive($$5, $$6, $$7);
/* 139 */     if ($$3) {
/* 140 */       $$0.getServer().getPlayerList().broadcastSystemMessage((Component)Component.translatable("commands.random.roll", new Object[] { $$0.getDisplayName(), Integer.valueOf($$9), Integer.valueOf($$6), Integer.valueOf($$7) }), false);
/*     */     } else {
/* 142 */       $$0.sendSuccess(() -> Component.translatable("commands.random.sample.success", new Object[] { Integer.valueOf($$0) }), false);
/*     */     } 
/*     */     
/* 145 */     return $$9;
/*     */   }
/*     */   
/*     */   private static int resetSequence(CommandSourceStack $$0, ResourceLocation $$1) throws CommandSyntaxException {
/* 149 */     $$0.getLevel().getRandomSequences().reset($$1);
/* 150 */     $$0.sendSuccess(() -> Component.translatable("commands.random.reset.success", new Object[] { Component.translationArg($$0) }), false);
/* 151 */     return 1;
/*     */   }
/*     */   
/*     */   private static int resetSequence(CommandSourceStack $$0, ResourceLocation $$1, int $$2, boolean $$3, boolean $$4) throws CommandSyntaxException {
/* 155 */     $$0.getLevel().getRandomSequences().reset($$1, $$2, $$3, $$4);
/* 156 */     $$0.sendSuccess(() -> Component.translatable("commands.random.reset.success", new Object[] { Component.translationArg($$0) }), false);
/* 157 */     return 1;
/*     */   }
/*     */   
/*     */   private static int resetAllSequences(CommandSourceStack $$0) {
/* 161 */     int $$1 = $$0.getLevel().getRandomSequences().clear();
/* 162 */     $$0.sendSuccess(() -> Component.translatable("commands.random.reset.all.success", new Object[] { Integer.valueOf($$0) }), false);
/* 163 */     return $$1;
/*     */   }
/*     */   
/*     */   private static int resetAllSequencesAndSetNewDefaults(CommandSourceStack $$0, int $$1, boolean $$2, boolean $$3) {
/* 167 */     RandomSequences $$4 = $$0.getLevel().getRandomSequences();
/* 168 */     $$4.setSeedDefaults($$1, $$2, $$3);
/* 169 */     int $$5 = $$4.clear();
/* 170 */     $$0.sendSuccess(() -> Component.translatable("commands.random.reset.all.success", new Object[] { Integer.valueOf($$0) }), false);
/* 171 */     return $$5;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\RandomCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */