/*     */ package net.minecraft.server.commands;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.brigadier.CommandDispatcher;
/*     */ import com.mojang.brigadier.Message;
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import com.mojang.brigadier.arguments.BoolArgumentType;
/*     */ import com.mojang.brigadier.arguments.IntegerArgumentType;
/*     */ import com.mojang.brigadier.arguments.StringArgumentType;
/*     */ import com.mojang.brigadier.builder.ArgumentBuilder;
/*     */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*     */ import com.mojang.brigadier.builder.RequiredArgumentBuilder;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
/*     */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*     */ import com.mojang.brigadier.suggestion.Suggestions;
/*     */ import com.mojang.brigadier.suggestion.SuggestionsBuilder;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMaps;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.Commands;
/*     */ import net.minecraft.commands.SharedSuggestionProvider;
/*     */ import net.minecraft.commands.arguments.ComponentArgument;
/*     */ import net.minecraft.commands.arguments.ObjectiveArgument;
/*     */ import net.minecraft.commands.arguments.ObjectiveCriteriaArgument;
/*     */ import net.minecraft.commands.arguments.OperationArgument;
/*     */ import net.minecraft.commands.arguments.ScoreHolderArgument;
/*     */ import net.minecraft.commands.arguments.ScoreboardSlotArgument;
/*     */ import net.minecraft.commands.arguments.StyleArgument;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.ComponentUtils;
/*     */ import net.minecraft.network.chat.Style;
/*     */ import net.minecraft.network.chat.numbers.BlankFormat;
/*     */ import net.minecraft.network.chat.numbers.FixedFormat;
/*     */ import net.minecraft.network.chat.numbers.NumberFormat;
/*     */ import net.minecraft.network.chat.numbers.StyledFormat;
/*     */ import net.minecraft.server.ServerScoreboard;
/*     */ import net.minecraft.world.scores.DisplaySlot;
/*     */ import net.minecraft.world.scores.Objective;
/*     */ import net.minecraft.world.scores.ReadOnlyScoreInfo;
/*     */ import net.minecraft.world.scores.ScoreAccess;
/*     */ import net.minecraft.world.scores.ScoreHolder;
/*     */ import net.minecraft.world.scores.criteria.ObjectiveCriteria;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ScoreboardCommand
/*     */ {
/*  67 */   private static final SimpleCommandExceptionType ERROR_OBJECTIVE_ALREADY_EXISTS = new SimpleCommandExceptionType((Message)Component.translatable("commands.scoreboard.objectives.add.duplicate"));
/*  68 */   private static final SimpleCommandExceptionType ERROR_DISPLAY_SLOT_ALREADY_EMPTY = new SimpleCommandExceptionType((Message)Component.translatable("commands.scoreboard.objectives.display.alreadyEmpty"));
/*  69 */   private static final SimpleCommandExceptionType ERROR_DISPLAY_SLOT_ALREADY_SET = new SimpleCommandExceptionType((Message)Component.translatable("commands.scoreboard.objectives.display.alreadySet"));
/*  70 */   private static final SimpleCommandExceptionType ERROR_TRIGGER_ALREADY_ENABLED = new SimpleCommandExceptionType((Message)Component.translatable("commands.scoreboard.players.enable.failed")); private static final Dynamic2CommandExceptionType ERROR_NO_VALUE;
/*  71 */   private static final SimpleCommandExceptionType ERROR_NOT_TRIGGER = new SimpleCommandExceptionType((Message)Component.translatable("commands.scoreboard.players.enable.invalid")); static {
/*  72 */     ERROR_NO_VALUE = new Dynamic2CommandExceptionType(($$0, $$1) -> Component.translatableEscape("commands.scoreboard.players.get.null", new Object[] { $$0, $$1 }));
/*     */   }
/*     */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/*  75 */     $$0.register(
/*  76 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("scoreboard")
/*  77 */         .requires($$0 -> $$0.hasPermission(2)))
/*  78 */         .then((
/*  79 */           (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("objectives")
/*  80 */           .then(
/*  81 */             Commands.literal("list")
/*  82 */             .executes($$0 -> listObjectives((CommandSourceStack)$$0.getSource()))))
/*     */           
/*  84 */           .then(
/*  85 */             Commands.literal("add")
/*  86 */             .then(
/*  87 */               Commands.argument("objective", (ArgumentType)StringArgumentType.word())
/*  88 */               .then((
/*  89 */                 (RequiredArgumentBuilder)Commands.argument("criteria", (ArgumentType)ObjectiveCriteriaArgument.criteria())
/*  90 */                 .executes($$0 -> addObjective((CommandSourceStack)$$0.getSource(), StringArgumentType.getString($$0, "objective"), ObjectiveCriteriaArgument.getCriteria($$0, "criteria"), (Component)Component.literal(StringArgumentType.getString($$0, "objective")))))
/*  91 */                 .then(
/*  92 */                   Commands.argument("displayName", (ArgumentType)ComponentArgument.textComponent())
/*  93 */                   .executes($$0 -> addObjective((CommandSourceStack)$$0.getSource(), StringArgumentType.getString($$0, "objective"), ObjectiveCriteriaArgument.getCriteria($$0, "criteria"), ComponentArgument.getComponent($$0, "displayName"))))))))
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*  98 */           .then(
/*  99 */             Commands.literal("modify")
/* 100 */             .then((
/* 101 */               (RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("objective", (ArgumentType)ObjectiveArgument.objective())
/* 102 */               .then(
/* 103 */                 Commands.literal("displayname")
/* 104 */                 .then(
/* 105 */                   Commands.argument("displayName", (ArgumentType)ComponentArgument.textComponent())
/* 106 */                   .executes($$0 -> setDisplayName((CommandSourceStack)$$0.getSource(), ObjectiveArgument.getObjective($$0, "objective"), ComponentArgument.getComponent($$0, "displayName"))))))
/*     */               
/* 108 */               .then((ArgumentBuilder)createRenderTypeModify()))
/* 109 */               .then(
/* 110 */                 Commands.literal("displayautoupdate")
/* 111 */                 .then(
/* 112 */                   Commands.argument("value", (ArgumentType)BoolArgumentType.bool())
/* 113 */                   .executes($$0 -> setDisplayAutoUpdate((CommandSourceStack)$$0.getSource(), ObjectiveArgument.getObjective($$0, "objective"), BoolArgumentType.getBool($$0, "value"))))))
/*     */ 
/*     */               
/* 116 */               .then(
/* 117 */                 addNumberFormats((ArgumentBuilder<CommandSourceStack, ?>)Commands.literal("numberformat"), ($$0, $$1) -> setObjectiveFormat((CommandSourceStack)$$0.getSource(), ObjectiveArgument.getObjective($$0, "objective"), $$1))))))
/*     */ 
/*     */ 
/*     */           
/* 121 */           .then(
/* 122 */             Commands.literal("remove")
/* 123 */             .then(
/* 124 */               Commands.argument("objective", (ArgumentType)ObjectiveArgument.objective())
/* 125 */               .executes($$0 -> removeObjective((CommandSourceStack)$$0.getSource(), ObjectiveArgument.getObjective($$0, "objective"))))))
/*     */ 
/*     */           
/* 128 */           .then(
/* 129 */             Commands.literal("setdisplay")
/* 130 */             .then((
/* 131 */               (RequiredArgumentBuilder)Commands.argument("slot", (ArgumentType)ScoreboardSlotArgument.displaySlot())
/* 132 */               .executes($$0 -> clearDisplaySlot((CommandSourceStack)$$0.getSource(), ScoreboardSlotArgument.getDisplaySlot($$0, "slot"))))
/* 133 */               .then(
/* 134 */                 Commands.argument("objective", (ArgumentType)ObjectiveArgument.objective())
/* 135 */                 .executes($$0 -> setDisplaySlot((CommandSourceStack)$$0.getSource(), ScoreboardSlotArgument.getDisplaySlot($$0, "slot"), ObjectiveArgument.getObjective($$0, "objective"))))))))
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 140 */         .then((
/* 141 */           (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("players")
/* 142 */           .then((
/* 143 */             (LiteralArgumentBuilder)Commands.literal("list")
/* 144 */             .executes($$0 -> listTrackedPlayers((CommandSourceStack)$$0.getSource())))
/* 145 */             .then(
/* 146 */               Commands.argument("target", (ArgumentType)ScoreHolderArgument.scoreHolder())
/* 147 */               .suggests(ScoreHolderArgument.SUGGEST_SCORE_HOLDERS)
/* 148 */               .executes($$0 -> listTrackedPlayerScores((CommandSourceStack)$$0.getSource(), ScoreHolderArgument.getName($$0, "target"))))))
/*     */ 
/*     */           
/* 151 */           .then(
/* 152 */             Commands.literal("set")
/* 153 */             .then(
/* 154 */               Commands.argument("targets", (ArgumentType)ScoreHolderArgument.scoreHolders())
/* 155 */               .suggests(ScoreHolderArgument.SUGGEST_SCORE_HOLDERS)
/* 156 */               .then(
/* 157 */                 Commands.argument("objective", (ArgumentType)ObjectiveArgument.objective())
/* 158 */                 .then(
/* 159 */                   Commands.argument("score", (ArgumentType)IntegerArgumentType.integer())
/* 160 */                   .executes($$0 -> setScore((CommandSourceStack)$$0.getSource(), ScoreHolderArgument.getNamesWithDefaultWildcard($$0, "targets"), ObjectiveArgument.getWritableObjective($$0, "objective"), IntegerArgumentType.getInteger($$0, "score"))))))))
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 165 */           .then(
/* 166 */             Commands.literal("get")
/* 167 */             .then(
/* 168 */               Commands.argument("target", (ArgumentType)ScoreHolderArgument.scoreHolder())
/* 169 */               .suggests(ScoreHolderArgument.SUGGEST_SCORE_HOLDERS)
/* 170 */               .then(
/* 171 */                 Commands.argument("objective", (ArgumentType)ObjectiveArgument.objective())
/* 172 */                 .executes($$0 -> getScore((CommandSourceStack)$$0.getSource(), ScoreHolderArgument.getName($$0, "target"), ObjectiveArgument.getObjective($$0, "objective")))))))
/*     */ 
/*     */ 
/*     */           
/* 176 */           .then(
/* 177 */             Commands.literal("add")
/* 178 */             .then(
/* 179 */               Commands.argument("targets", (ArgumentType)ScoreHolderArgument.scoreHolders())
/* 180 */               .suggests(ScoreHolderArgument.SUGGEST_SCORE_HOLDERS)
/* 181 */               .then(
/* 182 */                 Commands.argument("objective", (ArgumentType)ObjectiveArgument.objective())
/* 183 */                 .then(
/* 184 */                   Commands.argument("score", (ArgumentType)IntegerArgumentType.integer(0))
/* 185 */                   .executes($$0 -> addScore((CommandSourceStack)$$0.getSource(), ScoreHolderArgument.getNamesWithDefaultWildcard($$0, "targets"), ObjectiveArgument.getWritableObjective($$0, "objective"), IntegerArgumentType.getInteger($$0, "score"))))))))
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 190 */           .then(
/* 191 */             Commands.literal("remove")
/* 192 */             .then(
/* 193 */               Commands.argument("targets", (ArgumentType)ScoreHolderArgument.scoreHolders())
/* 194 */               .suggests(ScoreHolderArgument.SUGGEST_SCORE_HOLDERS)
/* 195 */               .then(
/* 196 */                 Commands.argument("objective", (ArgumentType)ObjectiveArgument.objective())
/* 197 */                 .then(
/* 198 */                   Commands.argument("score", (ArgumentType)IntegerArgumentType.integer(0))
/* 199 */                   .executes($$0 -> removeScore((CommandSourceStack)$$0.getSource(), ScoreHolderArgument.getNamesWithDefaultWildcard($$0, "targets"), ObjectiveArgument.getWritableObjective($$0, "objective"), IntegerArgumentType.getInteger($$0, "score"))))))))
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 204 */           .then(
/* 205 */             Commands.literal("reset")
/* 206 */             .then((
/* 207 */               (RequiredArgumentBuilder)Commands.argument("targets", (ArgumentType)ScoreHolderArgument.scoreHolders())
/* 208 */               .suggests(ScoreHolderArgument.SUGGEST_SCORE_HOLDERS)
/* 209 */               .executes($$0 -> resetScores((CommandSourceStack)$$0.getSource(), ScoreHolderArgument.getNamesWithDefaultWildcard($$0, "targets"))))
/* 210 */               .then(
/* 211 */                 Commands.argument("objective", (ArgumentType)ObjectiveArgument.objective())
/* 212 */                 .executes($$0 -> resetScore((CommandSourceStack)$$0.getSource(), ScoreHolderArgument.getNamesWithDefaultWildcard($$0, "targets"), ObjectiveArgument.getObjective($$0, "objective")))))))
/*     */ 
/*     */ 
/*     */           
/* 216 */           .then(
/* 217 */             Commands.literal("enable")
/* 218 */             .then(
/* 219 */               Commands.argument("targets", (ArgumentType)ScoreHolderArgument.scoreHolders())
/* 220 */               .suggests(ScoreHolderArgument.SUGGEST_SCORE_HOLDERS)
/* 221 */               .then(
/* 222 */                 Commands.argument("objective", (ArgumentType)ObjectiveArgument.objective())
/* 223 */                 .suggests(($$0, $$1) -> suggestTriggers((CommandSourceStack)$$0.getSource(), ScoreHolderArgument.getNamesWithDefaultWildcard($$0, "targets"), $$1))
/* 224 */                 .executes($$0 -> enableTrigger((CommandSourceStack)$$0.getSource(), ScoreHolderArgument.getNamesWithDefaultWildcard($$0, "targets"), ObjectiveArgument.getObjective($$0, "objective")))))))
/*     */ 
/*     */ 
/*     */           
/* 228 */           .then((
/* 229 */             (LiteralArgumentBuilder)Commands.literal("display")
/* 230 */             .then(
/* 231 */               Commands.literal("name")
/* 232 */               .then(
/* 233 */                 Commands.argument("targets", (ArgumentType)ScoreHolderArgument.scoreHolders())
/* 234 */                 .suggests(ScoreHolderArgument.SUGGEST_SCORE_HOLDERS)
/* 235 */                 .then((
/* 236 */                   (RequiredArgumentBuilder)Commands.argument("objective", (ArgumentType)ObjectiveArgument.objective())
/* 237 */                   .then(
/* 238 */                     Commands.argument("name", (ArgumentType)ComponentArgument.textComponent())
/* 239 */                     .executes($$0 -> setScoreDisplay((CommandSourceStack)$$0.getSource(), ScoreHolderArgument.getNamesWithDefaultWildcard($$0, "targets"), ObjectiveArgument.getObjective($$0, "objective"), ComponentArgument.getComponent($$0, "name")))))
/*     */                   
/* 241 */                   .executes($$0 -> setScoreDisplay((CommandSourceStack)$$0.getSource(), ScoreHolderArgument.getNamesWithDefaultWildcard($$0, "targets"), ObjectiveArgument.getObjective($$0, "objective"), null))))))
/*     */ 
/*     */ 
/*     */             
/* 245 */             .then(
/* 246 */               Commands.literal("numberformat")
/* 247 */               .then(
/* 248 */                 Commands.argument("targets", (ArgumentType)ScoreHolderArgument.scoreHolders())
/* 249 */                 .suggests(ScoreHolderArgument.SUGGEST_SCORE_HOLDERS)
/* 250 */                 .then(
/* 251 */                   addNumberFormats((ArgumentBuilder<CommandSourceStack, ?>)Commands.argument("objective", (ArgumentType)ObjectiveArgument.objective()), ($$0, $$1) -> setScoreNumberFormat((CommandSourceStack)$$0.getSource(), ScoreHolderArgument.getNamesWithDefaultWildcard($$0, "targets"), ObjectiveArgument.getObjective($$0, "objective"), $$1)))))))
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 258 */           .then(
/* 259 */             Commands.literal("operation")
/* 260 */             .then(
/* 261 */               Commands.argument("targets", (ArgumentType)ScoreHolderArgument.scoreHolders())
/* 262 */               .suggests(ScoreHolderArgument.SUGGEST_SCORE_HOLDERS)
/* 263 */               .then(
/* 264 */                 Commands.argument("targetObjective", (ArgumentType)ObjectiveArgument.objective())
/* 265 */                 .then(
/* 266 */                   Commands.argument("operation", (ArgumentType)OperationArgument.operation())
/* 267 */                   .then(
/* 268 */                     Commands.argument("source", (ArgumentType)ScoreHolderArgument.scoreHolders())
/* 269 */                     .suggests(ScoreHolderArgument.SUGGEST_SCORE_HOLDERS)
/* 270 */                     .then(
/* 271 */                       Commands.argument("sourceObjective", (ArgumentType)ObjectiveArgument.objective())
/* 272 */                       .executes($$0 -> performOperation((CommandSourceStack)$$0.getSource(), ScoreHolderArgument.getNamesWithDefaultWildcard($$0, "targets"), ObjectiveArgument.getWritableObjective($$0, "targetObjective"), OperationArgument.getOperation($$0, "operation"), ScoreHolderArgument.getNamesWithDefaultWildcard($$0, "source"), ObjectiveArgument.getObjective($$0, "sourceObjective")))))))))));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ArgumentBuilder<CommandSourceStack, ?> addNumberFormats(ArgumentBuilder<CommandSourceStack, ?> $$0, NumberFormatCommandExecutor $$1) {
/* 289 */     return $$0
/* 290 */       .then(
/* 291 */         Commands.literal("blank")
/* 292 */         .executes($$1 -> $$0.run($$1, (NumberFormat)BlankFormat.INSTANCE)))
/*     */       
/* 294 */       .then(
/* 295 */         Commands.literal("fixed")
/* 296 */         .then(
/* 297 */           Commands.argument("contents", (ArgumentType)ComponentArgument.textComponent())
/* 298 */           .executes($$1 -> {
/*     */               Component $$2 = ComponentArgument.getComponent($$1, "contents");
/*     */ 
/*     */ 
/*     */               
/*     */               return $$0.run($$1, (NumberFormat)new FixedFormat($$2));
/* 304 */             }))).then(
/* 305 */         Commands.literal("styled")
/* 306 */         .then(
/* 307 */           Commands.argument("style", (ArgumentType)StyleArgument.style())
/* 308 */           .executes($$1 -> {
/*     */               Style $$2 = StyleArgument.getStyle($$1, "style");
/*     */ 
/*     */ 
/*     */               
/*     */               return $$0.run($$1, (NumberFormat)new StyledFormat($$2));
/* 314 */             }))).executes($$1 -> $$0.run($$1, null));
/*     */   }
/*     */   
/*     */   private static LiteralArgumentBuilder<CommandSourceStack> createRenderTypeModify() {
/* 318 */     LiteralArgumentBuilder<CommandSourceStack> $$0 = Commands.literal("rendertype");
/*     */     
/* 320 */     for (ObjectiveCriteria.RenderType $$1 : ObjectiveCriteria.RenderType.values()) {
/* 321 */       $$0.then(Commands.literal($$1.getId())
/* 322 */           .executes($$1 -> setRenderType((CommandSourceStack)$$1.getSource(), ObjectiveArgument.getObjective($$1, "objective"), $$0)));
/*     */     }
/*     */     
/* 325 */     return $$0;
/*     */   }
/*     */   
/*     */   private static CompletableFuture<Suggestions> suggestTriggers(CommandSourceStack $$0, Collection<ScoreHolder> $$1, SuggestionsBuilder $$2) {
/* 329 */     List<String> $$3 = Lists.newArrayList();
/* 330 */     ServerScoreboard serverScoreboard = $$0.getServer().getScoreboard();
/*     */     
/* 332 */     for (Objective $$5 : serverScoreboard.getObjectives()) {
/* 333 */       if ($$5.getCriteria() == ObjectiveCriteria.TRIGGER) {
/* 334 */         boolean $$6 = false;
/* 335 */         for (ScoreHolder $$7 : $$1) {
/* 336 */           ReadOnlyScoreInfo $$8 = serverScoreboard.getPlayerScoreInfo($$7, $$5);
/*     */           
/* 338 */           if ($$8 == null || $$8.isLocked()) {
/* 339 */             $$6 = true;
/*     */             break;
/*     */           } 
/*     */         } 
/* 343 */         if ($$6) {
/* 344 */           $$3.add($$5.getName());
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 349 */     return SharedSuggestionProvider.suggest($$3, $$2);
/*     */   }
/*     */   
/*     */   private static int getScore(CommandSourceStack $$0, ScoreHolder $$1, Objective $$2) throws CommandSyntaxException {
/* 353 */     ServerScoreboard serverScoreboard = $$0.getServer().getScoreboard();
/*     */     
/* 355 */     ReadOnlyScoreInfo $$4 = serverScoreboard.getPlayerScoreInfo($$1, $$2);
/* 356 */     if ($$4 == null) {
/* 357 */       throw ERROR_NO_VALUE.create($$2.getName(), $$1.getFeedbackDisplayName());
/*     */     }
/*     */     
/* 360 */     $$0.sendSuccess(() -> Component.translatable("commands.scoreboard.players.get.success", new Object[] { $$0.getFeedbackDisplayName(), Integer.valueOf($$1.value()), $$2.getFormattedDisplayName() }), false);
/*     */     
/* 362 */     return $$4.value();
/*     */   }
/*     */   
/*     */   private static Component getFirstTargetName(Collection<ScoreHolder> $$0) {
/* 366 */     return ((ScoreHolder)$$0.iterator().next()).getFeedbackDisplayName();
/*     */   }
/*     */   
/*     */   private static int performOperation(CommandSourceStack $$0, Collection<ScoreHolder> $$1, Objective $$2, OperationArgument.Operation $$3, Collection<ScoreHolder> $$4, Objective $$5) throws CommandSyntaxException {
/* 370 */     ServerScoreboard serverScoreboard = $$0.getServer().getScoreboard();
/* 371 */     int $$7 = 0;
/*     */     
/* 373 */     for (ScoreHolder $$8 : $$1) {
/* 374 */       ScoreAccess $$9 = serverScoreboard.getOrCreatePlayerScore($$8, $$2);
/* 375 */       for (ScoreHolder $$10 : $$4) {
/* 376 */         ScoreAccess $$11 = serverScoreboard.getOrCreatePlayerScore($$10, $$5);
/* 377 */         $$3.apply($$9, $$11);
/*     */       } 
/* 379 */       $$7 += $$9.get();
/*     */     } 
/*     */     
/* 382 */     if ($$1.size() == 1) {
/* 383 */       int $$12 = $$7;
/* 384 */       $$0.sendSuccess(() -> Component.translatable("commands.scoreboard.players.operation.success.single", new Object[] { $$0.getFormattedDisplayName(), getFirstTargetName($$1), Integer.valueOf($$2) }), true);
/*     */     } else {
/* 386 */       $$0.sendSuccess(() -> Component.translatable("commands.scoreboard.players.operation.success.multiple", new Object[] { $$0.getFormattedDisplayName(), Integer.valueOf($$1.size()) }), true);
/*     */     } 
/*     */     
/* 389 */     return $$7;
/*     */   }
/*     */   
/*     */   private static int enableTrigger(CommandSourceStack $$0, Collection<ScoreHolder> $$1, Objective $$2) throws CommandSyntaxException {
/* 393 */     if ($$2.getCriteria() != ObjectiveCriteria.TRIGGER) {
/* 394 */       throw ERROR_NOT_TRIGGER.create();
/*     */     }
/* 396 */     ServerScoreboard serverScoreboard = $$0.getServer().getScoreboard();
/*     */     
/* 398 */     int $$4 = 0;
/*     */     
/* 400 */     for (ScoreHolder $$5 : $$1) {
/* 401 */       ScoreAccess $$6 = serverScoreboard.getOrCreatePlayerScore($$5, $$2);
/* 402 */       if ($$6.locked()) {
/* 403 */         $$6.unlock();
/* 404 */         $$4++;
/*     */       } 
/*     */     } 
/*     */     
/* 408 */     if ($$4 == 0) {
/* 409 */       throw ERROR_TRIGGER_ALREADY_ENABLED.create();
/*     */     }
/*     */     
/* 412 */     if ($$1.size() == 1) {
/* 413 */       $$0.sendSuccess(() -> Component.translatable("commands.scoreboard.players.enable.success.single", new Object[] { $$0.getFormattedDisplayName(), getFirstTargetName($$1) }), true);
/*     */     } else {
/* 415 */       $$0.sendSuccess(() -> Component.translatable("commands.scoreboard.players.enable.success.multiple", new Object[] { $$0.getFormattedDisplayName(), Integer.valueOf($$1.size()) }), true);
/*     */     } 
/*     */     
/* 418 */     return $$4;
/*     */   }
/*     */   
/*     */   private static int resetScores(CommandSourceStack $$0, Collection<ScoreHolder> $$1) {
/* 422 */     ServerScoreboard serverScoreboard = $$0.getServer().getScoreboard();
/*     */     
/* 424 */     for (ScoreHolder $$3 : $$1) {
/* 425 */       serverScoreboard.resetAllPlayerScores($$3);
/*     */     }
/*     */     
/* 428 */     if ($$1.size() == 1) {
/* 429 */       $$0.sendSuccess(() -> Component.translatable("commands.scoreboard.players.reset.all.single", new Object[] { getFirstTargetName($$0) }), true);
/*     */     } else {
/* 431 */       $$0.sendSuccess(() -> Component.translatable("commands.scoreboard.players.reset.all.multiple", new Object[] { Integer.valueOf($$0.size()) }), true);
/*     */     } 
/*     */     
/* 434 */     return $$1.size();
/*     */   }
/*     */   
/*     */   private static int resetScore(CommandSourceStack $$0, Collection<ScoreHolder> $$1, Objective $$2) {
/* 438 */     ServerScoreboard serverScoreboard = $$0.getServer().getScoreboard();
/*     */     
/* 440 */     for (ScoreHolder $$4 : $$1) {
/* 441 */       serverScoreboard.resetSinglePlayerScore($$4, $$2);
/*     */     }
/*     */     
/* 444 */     if ($$1.size() == 1) {
/* 445 */       $$0.sendSuccess(() -> Component.translatable("commands.scoreboard.players.reset.specific.single", new Object[] { $$0.getFormattedDisplayName(), getFirstTargetName($$1) }), true);
/*     */     } else {
/* 447 */       $$0.sendSuccess(() -> Component.translatable("commands.scoreboard.players.reset.specific.multiple", new Object[] { $$0.getFormattedDisplayName(), Integer.valueOf($$1.size()) }), true);
/*     */     } 
/*     */     
/* 450 */     return $$1.size();
/*     */   }
/*     */   
/*     */   private static int setScore(CommandSourceStack $$0, Collection<ScoreHolder> $$1, Objective $$2, int $$3) {
/* 454 */     ServerScoreboard serverScoreboard = $$0.getServer().getScoreboard();
/*     */     
/* 456 */     for (ScoreHolder $$5 : $$1) {
/* 457 */       serverScoreboard.getOrCreatePlayerScore($$5, $$2).set($$3);
/*     */     }
/*     */     
/* 460 */     if ($$1.size() == 1) {
/* 461 */       $$0.sendSuccess(() -> Component.translatable("commands.scoreboard.players.set.success.single", new Object[] { $$0.getFormattedDisplayName(), getFirstTargetName($$1), Integer.valueOf($$2) }), true);
/*     */     } else {
/* 463 */       $$0.sendSuccess(() -> Component.translatable("commands.scoreboard.players.set.success.multiple", new Object[] { $$0.getFormattedDisplayName(), Integer.valueOf($$1.size()), Integer.valueOf($$2) }), true);
/*     */     } 
/*     */     
/* 466 */     return $$3 * $$1.size();
/*     */   }
/*     */   
/*     */   private static int setScoreDisplay(CommandSourceStack $$0, Collection<ScoreHolder> $$1, Objective $$2, @Nullable Component $$3) {
/* 470 */     ServerScoreboard serverScoreboard = $$0.getServer().getScoreboard();
/*     */     
/* 472 */     for (ScoreHolder $$5 : $$1) {
/* 473 */       serverScoreboard.getOrCreatePlayerScore($$5, $$2).display($$3);
/*     */     }
/*     */     
/* 476 */     if ($$3 == null) {
/* 477 */       if ($$1.size() == 1) {
/* 478 */         $$0.sendSuccess(() -> Component.translatable("commands.scoreboard.players.display.name.clear.success.single", new Object[] { getFirstTargetName($$0), $$1.getFormattedDisplayName() }), true);
/*     */       } else {
/* 480 */         $$0.sendSuccess(() -> Component.translatable("commands.scoreboard.players.display.name.clear.success.multiple", new Object[] { Integer.valueOf($$0.size()), $$1.getFormattedDisplayName() }), true);
/*     */       }
/*     */     
/* 483 */     } else if ($$1.size() == 1) {
/* 484 */       $$0.sendSuccess(() -> Component.translatable("commands.scoreboard.players.display.name.set.success.single", new Object[] { $$0, getFirstTargetName($$1), $$2.getFormattedDisplayName() }), true);
/*     */     } else {
/* 486 */       $$0.sendSuccess(() -> Component.translatable("commands.scoreboard.players.display.name.set.success.multiple", new Object[] { $$0, Integer.valueOf($$1.size()), $$2.getFormattedDisplayName() }), true);
/*     */     } 
/*     */ 
/*     */     
/* 490 */     return $$1.size();
/*     */   }
/*     */   
/*     */   private static int setScoreNumberFormat(CommandSourceStack $$0, Collection<ScoreHolder> $$1, Objective $$2, @Nullable NumberFormat $$3) {
/* 494 */     ServerScoreboard serverScoreboard = $$0.getServer().getScoreboard();
/*     */     
/* 496 */     for (ScoreHolder $$5 : $$1) {
/* 497 */       serverScoreboard.getOrCreatePlayerScore($$5, $$2).numberFormatOverride($$3);
/*     */     }
/*     */     
/* 500 */     if ($$3 == null) {
/* 501 */       if ($$1.size() == 1) {
/* 502 */         $$0.sendSuccess(() -> Component.translatable("commands.scoreboard.players.display.numberFormat.clear.success.single", new Object[] { getFirstTargetName($$0), $$1.getFormattedDisplayName() }), true);
/*     */       } else {
/* 504 */         $$0.sendSuccess(() -> Component.translatable("commands.scoreboard.players.display.numberFormat.clear.success.multiple", new Object[] { Integer.valueOf($$0.size()), $$1.getFormattedDisplayName() }), true);
/*     */       }
/*     */     
/* 507 */     } else if ($$1.size() == 1) {
/* 508 */       $$0.sendSuccess(() -> Component.translatable("commands.scoreboard.players.display.numberFormat.set.success.single", new Object[] { getFirstTargetName($$0), $$1.getFormattedDisplayName() }), true);
/*     */     } else {
/* 510 */       $$0.sendSuccess(() -> Component.translatable("commands.scoreboard.players.display.numberFormat.set.success.multiple", new Object[] { Integer.valueOf($$0.size()), $$1.getFormattedDisplayName() }), true);
/*     */     } 
/*     */ 
/*     */     
/* 514 */     return $$1.size();
/*     */   }
/*     */   
/*     */   private static int addScore(CommandSourceStack $$0, Collection<ScoreHolder> $$1, Objective $$2, int $$3) {
/* 518 */     ServerScoreboard serverScoreboard = $$0.getServer().getScoreboard();
/* 519 */     int $$5 = 0;
/*     */     
/* 521 */     for (ScoreHolder $$6 : $$1) {
/* 522 */       ScoreAccess $$7 = serverScoreboard.getOrCreatePlayerScore($$6, $$2);
/* 523 */       $$7.set($$7.get() + $$3);
/* 524 */       $$5 += $$7.get();
/*     */     } 
/*     */     
/* 527 */     if ($$1.size() == 1) {
/* 528 */       int $$8 = $$5;
/* 529 */       $$0.sendSuccess(() -> Component.translatable("commands.scoreboard.players.add.success.single", new Object[] { Integer.valueOf($$0), $$1.getFormattedDisplayName(), getFirstTargetName($$2), Integer.valueOf($$3) }), true);
/*     */     } else {
/* 531 */       $$0.sendSuccess(() -> Component.translatable("commands.scoreboard.players.add.success.multiple", new Object[] { Integer.valueOf($$0), $$1.getFormattedDisplayName(), Integer.valueOf($$2.size()) }), true);
/*     */     } 
/*     */     
/* 534 */     return $$5;
/*     */   }
/*     */   
/*     */   private static int removeScore(CommandSourceStack $$0, Collection<ScoreHolder> $$1, Objective $$2, int $$3) {
/* 538 */     ServerScoreboard serverScoreboard = $$0.getServer().getScoreboard();
/* 539 */     int $$5 = 0;
/*     */     
/* 541 */     for (ScoreHolder $$6 : $$1) {
/* 542 */       ScoreAccess $$7 = serverScoreboard.getOrCreatePlayerScore($$6, $$2);
/* 543 */       $$7.set($$7.get() - $$3);
/* 544 */       $$5 += $$7.get();
/*     */     } 
/*     */     
/* 547 */     if ($$1.size() == 1) {
/* 548 */       int $$8 = $$5;
/* 549 */       $$0.sendSuccess(() -> Component.translatable("commands.scoreboard.players.remove.success.single", new Object[] { Integer.valueOf($$0), $$1.getFormattedDisplayName(), getFirstTargetName($$2), Integer.valueOf($$3) }), true);
/*     */     } else {
/* 551 */       $$0.sendSuccess(() -> Component.translatable("commands.scoreboard.players.remove.success.multiple", new Object[] { Integer.valueOf($$0), $$1.getFormattedDisplayName(), Integer.valueOf($$2.size()) }), true);
/*     */     } 
/*     */     
/* 554 */     return $$5;
/*     */   }
/*     */   
/*     */   private static int listTrackedPlayers(CommandSourceStack $$0) {
/* 558 */     Collection<ScoreHolder> $$1 = $$0.getServer().getScoreboard().getTrackedPlayers();
/*     */     
/* 560 */     if ($$1.isEmpty()) {
/* 561 */       $$0.sendSuccess(() -> Component.translatable("commands.scoreboard.players.list.empty"), false);
/*     */     } else {
/* 563 */       $$0.sendSuccess(() -> Component.translatable("commands.scoreboard.players.list.success", new Object[] { Integer.valueOf($$0.size()), ComponentUtils.formatList($$0, ScoreHolder::getFeedbackDisplayName) }), false);
/*     */     } 
/*     */     
/* 566 */     return $$1.size();
/*     */   }
/*     */   
/*     */   private static int listTrackedPlayerScores(CommandSourceStack $$0, ScoreHolder $$1) {
/* 570 */     Object2IntMap<Objective> $$2 = $$0.getServer().getScoreboard().listPlayerScores($$1);
/*     */     
/* 572 */     if ($$2.isEmpty()) {
/* 573 */       $$0.sendSuccess(() -> Component.translatable("commands.scoreboard.players.list.entity.empty", new Object[] { $$0.getFeedbackDisplayName() }), false);
/*     */     } else {
/* 575 */       $$0.sendSuccess(() -> Component.translatable("commands.scoreboard.players.list.entity.success", new Object[] { $$0.getFeedbackDisplayName(), Integer.valueOf($$1.size()) }), false);
/*     */       
/* 577 */       Object2IntMaps.fastForEach($$2, $$1 -> $$0.sendSuccess((), false));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 582 */     return $$2.size();
/*     */   }
/*     */   
/*     */   private static int clearDisplaySlot(CommandSourceStack $$0, DisplaySlot $$1) throws CommandSyntaxException {
/* 586 */     ServerScoreboard serverScoreboard = $$0.getServer().getScoreboard();
/*     */     
/* 588 */     if (serverScoreboard.getDisplayObjective($$1) == null) {
/* 589 */       throw ERROR_DISPLAY_SLOT_ALREADY_EMPTY.create();
/*     */     }
/*     */     
/* 592 */     serverScoreboard.setDisplayObjective($$1, null);
/* 593 */     $$0.sendSuccess(() -> Component.translatable("commands.scoreboard.objectives.display.cleared", new Object[] { $$0.getSerializedName() }), true);
/*     */     
/* 595 */     return 0;
/*     */   }
/*     */   
/*     */   private static int setDisplaySlot(CommandSourceStack $$0, DisplaySlot $$1, Objective $$2) throws CommandSyntaxException {
/* 599 */     ServerScoreboard serverScoreboard = $$0.getServer().getScoreboard();
/*     */     
/* 601 */     if (serverScoreboard.getDisplayObjective($$1) == $$2) {
/* 602 */       throw ERROR_DISPLAY_SLOT_ALREADY_SET.create();
/*     */     }
/*     */     
/* 605 */     serverScoreboard.setDisplayObjective($$1, $$2);
/* 606 */     $$0.sendSuccess(() -> Component.translatable("commands.scoreboard.objectives.display.set", new Object[] { $$0.getSerializedName(), $$1.getDisplayName() }), true);
/*     */     
/* 608 */     return 0;
/*     */   }
/*     */   
/*     */   private static int setDisplayName(CommandSourceStack $$0, Objective $$1, Component $$2) {
/* 612 */     if (!$$1.getDisplayName().equals($$2)) {
/* 613 */       $$1.setDisplayName($$2);
/* 614 */       $$0.sendSuccess(() -> Component.translatable("commands.scoreboard.objectives.modify.displayname", new Object[] { $$0.getName(), $$0.getFormattedDisplayName() }), true);
/*     */     } 
/*     */     
/* 617 */     return 0;
/*     */   }
/*     */   
/*     */   private static int setDisplayAutoUpdate(CommandSourceStack $$0, Objective $$1, boolean $$2) {
/* 621 */     if ($$1.displayAutoUpdate() != $$2) {
/* 622 */       $$1.setDisplayAutoUpdate($$2);
/* 623 */       if ($$2) {
/* 624 */         $$0.sendSuccess(() -> Component.translatable("commands.scoreboard.objectives.modify.displayAutoUpdate.enable", new Object[] { $$0.getName(), $$0.getFormattedDisplayName() }), true);
/*     */       } else {
/* 626 */         $$0.sendSuccess(() -> Component.translatable("commands.scoreboard.objectives.modify.displayAutoUpdate.disable", new Object[] { $$0.getName(), $$0.getFormattedDisplayName() }), true);
/*     */       } 
/*     */     } 
/*     */     
/* 630 */     return 0;
/*     */   }
/*     */   
/*     */   private static int setObjectiveFormat(CommandSourceStack $$0, Objective $$1, @Nullable NumberFormat $$2) {
/* 634 */     $$1.setNumberFormat($$2);
/* 635 */     if ($$2 != null) {
/* 636 */       $$0.sendSuccess(() -> Component.translatable("commands.scoreboard.objectives.modify.objectiveFormat.set", new Object[] { $$0.getName() }), true);
/*     */     } else {
/* 638 */       $$0.sendSuccess(() -> Component.translatable("commands.scoreboard.objectives.modify.objectiveFormat.clear", new Object[] { $$0.getName() }), true);
/*     */     } 
/* 640 */     return 0;
/*     */   }
/*     */   
/*     */   private static int setRenderType(CommandSourceStack $$0, Objective $$1, ObjectiveCriteria.RenderType $$2) {
/* 644 */     if ($$1.getRenderType() != $$2) {
/* 645 */       $$1.setRenderType($$2);
/* 646 */       $$0.sendSuccess(() -> Component.translatable("commands.scoreboard.objectives.modify.rendertype", new Object[] { $$0.getFormattedDisplayName() }), true);
/*     */     } 
/*     */     
/* 649 */     return 0;
/*     */   }
/*     */   
/*     */   private static int removeObjective(CommandSourceStack $$0, Objective $$1) {
/* 653 */     ServerScoreboard serverScoreboard = $$0.getServer().getScoreboard();
/* 654 */     serverScoreboard.removeObjective($$1);
/* 655 */     $$0.sendSuccess(() -> Component.translatable("commands.scoreboard.objectives.remove.success", new Object[] { $$0.getFormattedDisplayName() }), true);
/* 656 */     return serverScoreboard.getObjectives().size();
/*     */   }
/*     */   
/*     */   private static int addObjective(CommandSourceStack $$0, String $$1, ObjectiveCriteria $$2, Component $$3) throws CommandSyntaxException {
/* 660 */     ServerScoreboard serverScoreboard = $$0.getServer().getScoreboard();
/*     */     
/* 662 */     if (serverScoreboard.getObjective($$1) != null) {
/* 663 */       throw ERROR_OBJECTIVE_ALREADY_EXISTS.create();
/*     */     }
/*     */     
/* 666 */     serverScoreboard.addObjective($$1, $$2, $$3, $$2.getDefaultRenderType(), false, null);
/* 667 */     Objective $$5 = serverScoreboard.getObjective($$1);
/*     */     
/* 669 */     $$0.sendSuccess(() -> Component.translatable("commands.scoreboard.objectives.add.success", new Object[] { $$0.getFormattedDisplayName() }), true);
/*     */     
/* 671 */     return serverScoreboard.getObjectives().size();
/*     */   }
/*     */   
/*     */   private static int listObjectives(CommandSourceStack $$0) {
/* 675 */     Collection<Objective> $$1 = $$0.getServer().getScoreboard().getObjectives();
/*     */     
/* 677 */     if ($$1.isEmpty()) {
/* 678 */       $$0.sendSuccess(() -> Component.translatable("commands.scoreboard.objectives.list.empty"), false);
/*     */     } else {
/* 680 */       $$0.sendSuccess(() -> Component.translatable("commands.scoreboard.objectives.list.success", new Object[] { Integer.valueOf($$0.size()), ComponentUtils.formatList($$0, Objective::getFormattedDisplayName) }), false);
/*     */     } 
/*     */     
/* 683 */     return $$1.size();
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface NumberFormatCommandExecutor {
/*     */     int run(CommandContext<CommandSourceStack> param1CommandContext, @Nullable NumberFormat param1NumberFormat) throws CommandSyntaxException;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\ScoreboardCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */