/*     */ package net.minecraft.server.commands;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.brigadier.Command;
/*     */ import com.mojang.brigadier.CommandDispatcher;
/*     */ import com.mojang.brigadier.Message;
/*     */ import com.mojang.brigadier.RedirectModifier;
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import com.mojang.brigadier.arguments.DoubleArgumentType;
/*     */ import com.mojang.brigadier.builder.ArgumentBuilder;
/*     */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*     */ import com.mojang.brigadier.builder.RequiredArgumentBuilder;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.context.ContextChain;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
/*     */ import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
/*     */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*     */ import com.mojang.brigadier.suggestion.SuggestionProvider;
/*     */ import com.mojang.brigadier.suggestion.SuggestionsBuilder;
/*     */ import com.mojang.brigadier.tree.CommandNode;
/*     */ import com.mojang.brigadier.tree.LiteralCommandNode;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.OptionalInt;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.IntFunction;
/*     */ import java.util.function.IntPredicate;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.advancements.critereon.MinMaxBounds;
/*     */ import net.minecraft.commands.CommandBuildContext;
/*     */ import net.minecraft.commands.CommandResultCallback;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.Commands;
/*     */ import net.minecraft.commands.ExecutionCommandSource;
/*     */ import net.minecraft.commands.FunctionInstantiationException;
/*     */ import net.minecraft.commands.SharedSuggestionProvider;
/*     */ import net.minecraft.commands.arguments.DimensionArgument;
/*     */ import net.minecraft.commands.arguments.EntityAnchorArgument;
/*     */ import net.minecraft.commands.arguments.EntityArgument;
/*     */ import net.minecraft.commands.arguments.HeightmapTypeArgument;
/*     */ import net.minecraft.commands.arguments.NbtPathArgument;
/*     */ import net.minecraft.commands.arguments.ObjectiveArgument;
/*     */ import net.minecraft.commands.arguments.RangeArgument;
/*     */ import net.minecraft.commands.arguments.ResourceArgument;
/*     */ import net.minecraft.commands.arguments.ResourceLocationArgument;
/*     */ import net.minecraft.commands.arguments.ResourceOrTagArgument;
/*     */ import net.minecraft.commands.arguments.ScoreHolderArgument;
/*     */ import net.minecraft.commands.arguments.blocks.BlockPredicateArgument;
/*     */ import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
/*     */ import net.minecraft.commands.arguments.coordinates.RotationArgument;
/*     */ import net.minecraft.commands.arguments.coordinates.SwizzleArgument;
/*     */ import net.minecraft.commands.arguments.coordinates.Vec3Argument;
/*     */ import net.minecraft.commands.arguments.item.FunctionArgument;
/*     */ import net.minecraft.commands.execution.ChainModifiers;
/*     */ import net.minecraft.commands.execution.CustomModifierExecutor;
/*     */ import net.minecraft.commands.execution.EntryAction;
/*     */ import net.minecraft.commands.execution.ExecutionControl;
/*     */ import net.minecraft.commands.execution.tasks.BuildContexts;
/*     */ import net.minecraft.commands.execution.tasks.CallFunction;
/*     */ import net.minecraft.commands.execution.tasks.FallthroughTask;
/*     */ import net.minecraft.commands.execution.tasks.IsolatedCall;
/*     */ import net.minecraft.commands.functions.CommandFunction;
/*     */ import net.minecraft.commands.functions.InstantiatedFunction;
/*     */ import net.minecraft.commands.synchronization.SuggestionProviders;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.nbt.ByteTag;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.DoubleTag;
/*     */ import net.minecraft.nbt.FloatTag;
/*     */ import net.minecraft.nbt.IntTag;
/*     */ import net.minecraft.nbt.LongTag;
/*     */ import net.minecraft.nbt.ShortTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.server.ServerScoreboard;
/*     */ import net.minecraft.server.bossevents.CustomBossEvent;
/*     */ import net.minecraft.server.commands.data.DataAccessor;
/*     */ import net.minecraft.server.commands.data.DataCommands;
/*     */ import net.minecraft.server.level.FullChunkStatus;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Attackable;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.OwnableEntity;
/*     */ import net.minecraft.world.entity.Targeting;
/*     */ import net.minecraft.world.entity.TraceableEntity;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.pattern.BlockInWorld;
/*     */ import net.minecraft.world.level.chunk.LevelChunk;
/*     */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*     */ import net.minecraft.world.level.storage.loot.LootContext;
/*     */ import net.minecraft.world.level.storage.loot.LootDataManager;
/*     */ import net.minecraft.world.level.storage.loot.LootDataType;
/*     */ import net.minecraft.world.level.storage.loot.LootParams;
/*     */ import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
/*     */ import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
/*     */ import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import net.minecraft.world.scores.Objective;
/*     */ import net.minecraft.world.scores.ReadOnlyScoreInfo;
/*     */ import net.minecraft.world.scores.ScoreAccess;
/*     */ import net.minecraft.world.scores.ScoreHolder;
/*     */ import net.minecraft.world.scores.Scoreboard;
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
/*     */ public class ExecuteCommand
/*     */ {
/*     */   private static final int MAX_TEST_AREA = 32768;
/*     */   private static final Dynamic2CommandExceptionType ERROR_AREA_TOO_LARGE;
/*     */   
/*     */   static {
/* 145 */     ERROR_AREA_TOO_LARGE = new Dynamic2CommandExceptionType(($$0, $$1) -> Component.translatableEscape("commands.execute.blocks.toobig", new Object[] { $$0, $$1 }));
/*     */   }
/* 147 */   private static final SimpleCommandExceptionType ERROR_CONDITIONAL_FAILED = new SimpleCommandExceptionType((Message)Component.translatable("commands.execute.conditional.fail")); private static final DynamicCommandExceptionType ERROR_CONDITIONAL_FAILED_COUNT; @VisibleForTesting
/* 148 */   public static final Dynamic2CommandExceptionType ERROR_FUNCTION_CONDITION_INSTANTATION_FAILURE; private static final SuggestionProvider<CommandSourceStack> SUGGEST_PREDICATE; static { ERROR_CONDITIONAL_FAILED_COUNT = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("commands.execute.conditional.fail_count", new Object[] { $$0 }));
/*     */ 
/*     */     
/* 151 */     ERROR_FUNCTION_CONDITION_INSTANTATION_FAILURE = new Dynamic2CommandExceptionType(($$0, $$1) -> Component.translatableEscape("commands.execute.function.instantiationFailure", new Object[] { $$0, $$1 }));
/*     */     
/* 153 */     SUGGEST_PREDICATE = (($$0, $$1) -> {
/*     */         LootDataManager $$2 = ((CommandSourceStack)$$0.getSource()).getServer().getLootData();
/*     */         return SharedSuggestionProvider.suggestResource($$2.getKeys(LootDataType.PREDICATE), $$1);
/*     */       }); }
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
/*     */   public static void register(CommandDispatcher<CommandSourceStack> $$0, CommandBuildContext $$1) {
/* 174 */     LiteralCommandNode<CommandSourceStack> $$2 = $$0.register((LiteralArgumentBuilder)Commands.literal("execute").requires($$0 -> $$0.hasPermission(2)));
/*     */     
/* 176 */     $$0.register(
/* 177 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("execute")
/* 178 */         .requires($$0 -> $$0.hasPermission(2)))
/* 179 */         .then(
/* 180 */           Commands.literal("run")
/* 181 */           .redirect((CommandNode)$$0.getRoot())))
/*     */         
/* 183 */         .then(
/* 184 */           addConditionals((CommandNode<CommandSourceStack>)$$2, Commands.literal("if"), true, $$1)))
/*     */         
/* 186 */         .then(
/* 187 */           addConditionals((CommandNode<CommandSourceStack>)$$2, Commands.literal("unless"), false, $$1)))
/*     */         
/* 189 */         .then(
/* 190 */           Commands.literal("as")
/* 191 */           .then(
/* 192 */             Commands.argument("targets", (ArgumentType)EntityArgument.entities())
/* 193 */             .fork((CommandNode)$$2, $$0 -> {
/*     */                 List<CommandSourceStack> $$1 = Lists.newArrayList();
/*     */ 
/*     */                 
/*     */                 for (Entity $$2 : EntityArgument.getOptionalEntities($$0, "targets")) {
/*     */                   $$1.add(((CommandSourceStack)$$0.getSource()).withEntity($$2));
/*     */                 }
/*     */                 
/*     */                 return $$1;
/* 202 */               })))).then(
/* 203 */           Commands.literal("at")
/* 204 */           .then(
/* 205 */             Commands.argument("targets", (ArgumentType)EntityArgument.entities())
/* 206 */             .fork((CommandNode)$$2, $$0 -> {
/*     */                 List<CommandSourceStack> $$1 = Lists.newArrayList();
/*     */ 
/*     */                 
/*     */                 for (Entity $$2 : EntityArgument.getOptionalEntities($$0, "targets")) {
/*     */                   $$1.add(((CommandSourceStack)$$0.getSource()).withLevel((ServerLevel)$$2.level()).withPosition($$2.position()).withRotation($$2.getRotationVector()));
/*     */                 }
/*     */                 
/*     */                 return $$1;
/* 215 */               })))).then((
/* 216 */           (LiteralArgumentBuilder)Commands.literal("store")
/* 217 */           .then(wrapStores($$2, Commands.literal("result"), true)))
/* 218 */           .then(wrapStores($$2, Commands.literal("success"), false))))
/*     */         
/* 220 */         .then((
/* 221 */           (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("positioned")
/* 222 */           .then(
/* 223 */             Commands.argument("pos", (ArgumentType)Vec3Argument.vec3())
/* 224 */             .redirect((CommandNode)$$2, $$0 -> ((CommandSourceStack)$$0.getSource()).withPosition(Vec3Argument.getVec3($$0, "pos")).withAnchor(EntityAnchorArgument.Anchor.FEET))))
/*     */           
/* 226 */           .then(
/* 227 */             Commands.literal("as")
/* 228 */             .then(
/* 229 */               Commands.argument("targets", (ArgumentType)EntityArgument.entities())
/* 230 */               .fork((CommandNode)$$2, $$0 -> {
/*     */                   List<CommandSourceStack> $$1 = Lists.newArrayList();
/*     */ 
/*     */                   
/*     */                   for (Entity $$2 : EntityArgument.getOptionalEntities($$0, "targets")) {
/*     */                     $$1.add(((CommandSourceStack)$$0.getSource()).withPosition($$2.position()));
/*     */                   }
/*     */                   
/*     */                   return $$1;
/* 239 */                 })))).then(
/* 240 */             Commands.literal("over")
/* 241 */             .then(
/* 242 */               Commands.argument("heightmap", (ArgumentType)HeightmapTypeArgument.heightmap())
/* 243 */               .redirect((CommandNode)$$2, $$0 -> {
/*     */                   Vec3 $$1 = ((CommandSourceStack)$$0.getSource()).getPosition();
/*     */ 
/*     */                   
/*     */                   ServerLevel $$2 = ((CommandSourceStack)$$0.getSource()).getLevel();
/*     */                   
/*     */                   double $$3 = $$1.x();
/*     */                   
/*     */                   double $$4 = $$1.z();
/*     */                   
/*     */                   if (!$$2.hasChunk(SectionPos.blockToSectionCoord($$3), SectionPos.blockToSectionCoord($$4))) {
/*     */                     throw BlockPosArgument.ERROR_NOT_LOADED.create();
/*     */                   }
/*     */                   
/*     */                   int $$5 = $$2.getHeight(HeightmapTypeArgument.getHeightmap($$0, "heightmap"), Mth.floor($$3), Mth.floor($$4));
/*     */                   
/*     */                   return ((CommandSourceStack)$$0.getSource()).withPosition(new Vec3($$3, $$5, $$4));
/* 260 */                 }))))).then((
/* 261 */           (LiteralArgumentBuilder)Commands.literal("rotated")
/* 262 */           .then(
/* 263 */             Commands.argument("rot", (ArgumentType)RotationArgument.rotation())
/* 264 */             .redirect((CommandNode)$$2, $$0 -> ((CommandSourceStack)$$0.getSource()).withRotation(RotationArgument.getRotation($$0, "rot").getRotation((CommandSourceStack)$$0.getSource())))))
/*     */           
/* 266 */           .then(
/* 267 */             Commands.literal("as")
/* 268 */             .then(
/* 269 */               Commands.argument("targets", (ArgumentType)EntityArgument.entities())
/* 270 */               .fork((CommandNode)$$2, $$0 -> {
/*     */                   List<CommandSourceStack> $$1 = Lists.newArrayList();
/*     */ 
/*     */                   
/*     */                   for (Entity $$2 : EntityArgument.getOptionalEntities($$0, "targets")) {
/*     */                     $$1.add(((CommandSourceStack)$$0.getSource()).withRotation($$2.getRotationVector()));
/*     */                   }
/*     */ 
/*     */                   
/*     */                   return $$1;
/* 280 */                 }))))).then((
/* 281 */           (LiteralArgumentBuilder)Commands.literal("facing")
/* 282 */           .then(
/* 283 */             Commands.literal("entity")
/* 284 */             .then(
/* 285 */               Commands.argument("targets", (ArgumentType)EntityArgument.entities())
/* 286 */               .then(
/* 287 */                 Commands.argument("anchor", (ArgumentType)EntityAnchorArgument.anchor())
/* 288 */                 .fork((CommandNode)$$2, $$0 -> {
/*     */                     List<CommandSourceStack> $$1 = Lists.newArrayList();
/*     */ 
/*     */                     
/*     */                     EntityAnchorArgument.Anchor $$2 = EntityAnchorArgument.getAnchor($$0, "anchor");
/*     */                     
/*     */                     for (Entity $$3 : EntityArgument.getOptionalEntities($$0, "targets")) {
/*     */                       $$1.add(((CommandSourceStack)$$0.getSource()).facing($$3, $$2));
/*     */                     }
/*     */                     
/*     */                     return $$1;
/* 299 */                   }))))).then(
/* 300 */             Commands.argument("pos", (ArgumentType)Vec3Argument.vec3())
/* 301 */             .redirect((CommandNode)$$2, $$0 -> ((CommandSourceStack)$$0.getSource()).facing(Vec3Argument.getVec3($$0, "pos"))))))
/*     */ 
/*     */         
/* 304 */         .then(
/* 305 */           Commands.literal("align")
/* 306 */           .then(
/* 307 */             Commands.argument("axes", (ArgumentType)SwizzleArgument.swizzle())
/* 308 */             .redirect((CommandNode)$$2, $$0 -> ((CommandSourceStack)$$0.getSource()).withPosition(((CommandSourceStack)$$0.getSource()).getPosition().align(SwizzleArgument.getSwizzle($$0, "axes")))))))
/*     */ 
/*     */         
/* 311 */         .then(
/* 312 */           Commands.literal("anchored")
/* 313 */           .then(
/* 314 */             Commands.argument("anchor", (ArgumentType)EntityAnchorArgument.anchor())
/* 315 */             .redirect((CommandNode)$$2, $$0 -> ((CommandSourceStack)$$0.getSource()).withAnchor(EntityAnchorArgument.getAnchor($$0, "anchor"))))))
/*     */ 
/*     */         
/* 318 */         .then(
/* 319 */           Commands.literal("in")
/* 320 */           .then(
/* 321 */             Commands.argument("dimension", (ArgumentType)DimensionArgument.dimension())
/* 322 */             .redirect((CommandNode)$$2, $$0 -> ((CommandSourceStack)$$0.getSource()).withLevel(DimensionArgument.getDimension($$0, "dimension"))))))
/*     */ 
/*     */         
/* 325 */         .then(
/* 326 */           Commands.literal("summon")
/* 327 */           .then(
/* 328 */             Commands.argument("entity", (ArgumentType)ResourceArgument.resource($$1, Registries.ENTITY_TYPE))
/* 329 */             .suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
/* 330 */             .redirect((CommandNode)$$2, $$0 -> spawnEntityAndRedirect((CommandSourceStack)$$0.getSource(), ResourceArgument.getSummonableEntityType($$0, "entity"))))))
/*     */ 
/*     */         
/* 333 */         .then(
/* 334 */           (ArgumentBuilder)createRelationOperations((CommandNode<CommandSourceStack>)$$2, Commands.literal("on"))));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static ArgumentBuilder<CommandSourceStack, ?> wrapStores(LiteralCommandNode<CommandSourceStack> $$0, LiteralArgumentBuilder<CommandSourceStack> $$1, boolean $$2) {
/* 340 */     $$1.then(
/* 341 */         Commands.literal("score")
/* 342 */         .then(
/* 343 */           Commands.argument("targets", (ArgumentType)ScoreHolderArgument.scoreHolders())
/* 344 */           .suggests(ScoreHolderArgument.SUGGEST_SCORE_HOLDERS)
/* 345 */           .then(
/* 346 */             Commands.argument("objective", (ArgumentType)ObjectiveArgument.objective())
/* 347 */             .redirect((CommandNode)$$0, $$1 -> storeValue((CommandSourceStack)$$1.getSource(), ScoreHolderArgument.getNamesWithDefaultWildcard($$1, "targets"), ObjectiveArgument.getObjective($$1, "objective"), $$0)))));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 352 */     $$1.then(
/* 353 */         Commands.literal("bossbar")
/* 354 */         .then((
/* 355 */           (RequiredArgumentBuilder)Commands.argument("id", (ArgumentType)ResourceLocationArgument.id())
/* 356 */           .suggests(BossBarCommands.SUGGEST_BOSS_BAR)
/* 357 */           .then(
/* 358 */             Commands.literal("value")
/* 359 */             .redirect((CommandNode)$$0, $$1 -> storeValue((CommandSourceStack)$$1.getSource(), BossBarCommands.getBossBar($$1), true, $$0))))
/*     */           
/* 361 */           .then(
/* 362 */             Commands.literal("max")
/* 363 */             .redirect((CommandNode)$$0, $$1 -> storeValue((CommandSourceStack)$$1.getSource(), BossBarCommands.getBossBar($$1), false, $$0)))));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 368 */     for (Iterator<DataCommands.DataProvider> iterator = DataCommands.TARGET_PROVIDERS.iterator(); iterator.hasNext(); ) { DataCommands.DataProvider $$3 = iterator.next();
/* 369 */       $$3.wrap((ArgumentBuilder)$$1, $$3 -> $$3.then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("path", (ArgumentType)NbtPathArgument.nbtPath()).then(Commands.literal("int").then(Commands.argument("scale", (ArgumentType)DoubleArgumentType.doubleArg()).redirect((CommandNode)$$0, ())))).then(Commands.literal("float").then(Commands.argument("scale", (ArgumentType)DoubleArgumentType.doubleArg()).redirect((CommandNode)$$0, ())))).then(Commands.literal("short").then(Commands.argument("scale", (ArgumentType)DoubleArgumentType.doubleArg()).redirect((CommandNode)$$0, ())))).then(Commands.literal("long").then(Commands.argument("scale", (ArgumentType)DoubleArgumentType.doubleArg()).redirect((CommandNode)$$0, ())))).then(Commands.literal("double").then(Commands.argument("scale", (ArgumentType)DoubleArgumentType.doubleArg()).redirect((CommandNode)$$0, ())))).then(Commands.literal("byte").then(Commands.argument("scale", (ArgumentType)DoubleArgumentType.doubleArg()).redirect((CommandNode)$$0, ()))))); }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 423 */     return (ArgumentBuilder)$$1;
/*     */   }
/*     */   
/*     */   private static CommandSourceStack storeValue(CommandSourceStack $$0, Collection<ScoreHolder> $$1, Objective $$2, boolean $$3) {
/* 427 */     ServerScoreboard serverScoreboard = $$0.getServer().getScoreboard();
/*     */     
/* 429 */     return $$0.withCallback(($$4, $$5) -> { for (ScoreHolder $$6 : $$0) { ScoreAccess $$7 = $$1.getOrCreatePlayerScore($$6, $$2); int $$8 = $$3 ? $$5 : ($$4 ? 1 : 0); $$7.set($$8); }  }CommandResultCallback::chain);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static CommandSourceStack storeValue(CommandSourceStack $$0, CustomBossEvent $$1, boolean $$2, boolean $$3) {
/* 439 */     return $$0.withCallback(($$3, $$4) -> { int $$5 = $$0 ? $$4 : ($$3 ? 1 : 0); if ($$1) { $$2.setValue($$5); } else { $$2.setMax($$5); }  }CommandResultCallback::chain);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static CommandSourceStack storeData(CommandSourceStack $$0, DataAccessor $$1, NbtPathArgument.NbtPath $$2, IntFunction<Tag> $$3, boolean $$4) {
/* 450 */     return $$0.withCallback(($$4, $$5) -> {
/*     */           try {
/*     */             CompoundTag $$6 = $$0.getData();
/*     */             int $$7 = $$1 ? $$5 : ($$4 ? 1 : 0);
/*     */             $$2.set((Tag)$$6, $$3.apply($$7));
/*     */             $$0.setData($$6);
/* 456 */           } catch (CommandSyntaxException commandSyntaxException) {}
/*     */         }CommandResultCallback::chain);
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isChunkLoaded(ServerLevel $$0, BlockPos $$1) {
/* 462 */     ChunkPos $$2 = new ChunkPos($$1);
/*     */     
/* 464 */     LevelChunk $$3 = $$0.getChunkSource().getChunkNow($$2.x, $$2.z);
/* 465 */     if ($$3 != null) {
/* 466 */       return ($$3.getFullStatus() == FullChunkStatus.ENTITY_TICKING && $$0.areEntitiesLoaded($$2.toLong()));
/*     */     }
/* 468 */     return false;
/*     */   }
/*     */   
/*     */   private static ArgumentBuilder<CommandSourceStack, ?> addConditionals(CommandNode<CommandSourceStack> $$0, LiteralArgumentBuilder<CommandSourceStack> $$1, boolean $$2, CommandBuildContext $$3) {
/* 472 */     ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)$$1
/* 473 */       .then(
/* 474 */         Commands.literal("block")
/* 475 */         .then(
/* 476 */           Commands.argument("pos", (ArgumentType)BlockPosArgument.blockPos())
/* 477 */           .then(
/* 478 */             addConditional($$0, (ArgumentBuilder<CommandSourceStack, ?>)Commands.argument("block", (ArgumentType)BlockPredicateArgument.blockPredicate($$3)), $$2, $$0 -> BlockPredicateArgument.getBlockPredicate($$0, "block").test(new BlockInWorld((LevelReader)((CommandSourceStack)$$0.getSource()).getLevel(), BlockPosArgument.getLoadedBlockPos($$0, "pos"), true)))))))
/*     */ 
/*     */ 
/*     */       
/* 482 */       .then(
/* 483 */         Commands.literal("biome")
/* 484 */         .then(
/* 485 */           Commands.argument("pos", (ArgumentType)BlockPosArgument.blockPos())
/* 486 */           .then(
/* 487 */             addConditional($$0, (ArgumentBuilder<CommandSourceStack, ?>)Commands.argument("biome", (ArgumentType)ResourceOrTagArgument.resourceOrTag($$3, Registries.BIOME)), $$2, $$0 -> ResourceOrTagArgument.getResourceOrTag($$0, "biome", Registries.BIOME).test(((CommandSourceStack)$$0.getSource()).getLevel().getBiome(BlockPosArgument.getLoadedBlockPos($$0, "pos"))))))))
/*     */ 
/*     */ 
/*     */       
/* 491 */       .then(
/* 492 */         Commands.literal("loaded")
/* 493 */         .then(
/* 494 */           addConditional($$0, (ArgumentBuilder<CommandSourceStack, ?>)Commands.argument("pos", (ArgumentType)BlockPosArgument.blockPos()), $$2, $$0 -> isChunkLoaded(((CommandSourceStack)$$0.getSource()).getLevel(), BlockPosArgument.getBlockPos($$0, "pos"))))))
/*     */ 
/*     */       
/* 497 */       .then(
/* 498 */         Commands.literal("dimension")
/* 499 */         .then(
/* 500 */           addConditional($$0, (ArgumentBuilder<CommandSourceStack, ?>)Commands.argument("dimension", (ArgumentType)DimensionArgument.dimension()), $$2, $$0 -> (DimensionArgument.getDimension($$0, "dimension") == ((CommandSourceStack)$$0.getSource()).getLevel())))))
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 505 */       .then(
/* 506 */         Commands.literal("score")
/* 507 */         .then(
/* 508 */           Commands.argument("target", (ArgumentType)ScoreHolderArgument.scoreHolder())
/* 509 */           .suggests(ScoreHolderArgument.SUGGEST_SCORE_HOLDERS)
/* 510 */           .then((
/* 511 */             (RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("targetObjective", (ArgumentType)ObjectiveArgument.objective())
/* 512 */             .then(
/* 513 */               Commands.literal("=")
/* 514 */               .then(
/* 515 */                 Commands.argument("source", (ArgumentType)ScoreHolderArgument.scoreHolder())
/* 516 */                 .suggests(ScoreHolderArgument.SUGGEST_SCORE_HOLDERS)
/* 517 */                 .then(
/* 518 */                   addConditional($$0, (ArgumentBuilder<CommandSourceStack, ?>)Commands.argument("sourceObjective", (ArgumentType)ObjectiveArgument.objective()), $$2, $$0 -> checkScore($$0, ()))))))
/*     */ 
/*     */ 
/*     */             
/* 522 */             .then(
/* 523 */               Commands.literal("<")
/* 524 */               .then(
/* 525 */                 Commands.argument("source", (ArgumentType)ScoreHolderArgument.scoreHolder())
/* 526 */                 .suggests(ScoreHolderArgument.SUGGEST_SCORE_HOLDERS)
/* 527 */                 .then(
/* 528 */                   addConditional($$0, (ArgumentBuilder<CommandSourceStack, ?>)Commands.argument("sourceObjective", (ArgumentType)ObjectiveArgument.objective()), $$2, $$0 -> checkScore($$0, ()))))))
/*     */ 
/*     */ 
/*     */             
/* 532 */             .then(
/* 533 */               Commands.literal("<=")
/* 534 */               .then(
/* 535 */                 Commands.argument("source", (ArgumentType)ScoreHolderArgument.scoreHolder())
/* 536 */                 .suggests(ScoreHolderArgument.SUGGEST_SCORE_HOLDERS)
/* 537 */                 .then(
/* 538 */                   addConditional($$0, (ArgumentBuilder<CommandSourceStack, ?>)Commands.argument("sourceObjective", (ArgumentType)ObjectiveArgument.objective()), $$2, $$0 -> checkScore($$0, ()))))))
/*     */ 
/*     */ 
/*     */             
/* 542 */             .then(
/* 543 */               Commands.literal(">")
/* 544 */               .then(
/* 545 */                 Commands.argument("source", (ArgumentType)ScoreHolderArgument.scoreHolder())
/* 546 */                 .suggests(ScoreHolderArgument.SUGGEST_SCORE_HOLDERS)
/* 547 */                 .then(
/* 548 */                   addConditional($$0, (ArgumentBuilder<CommandSourceStack, ?>)Commands.argument("sourceObjective", (ArgumentType)ObjectiveArgument.objective()), $$2, $$0 -> checkScore($$0, ()))))))
/*     */ 
/*     */ 
/*     */             
/* 552 */             .then(
/* 553 */               Commands.literal(">=")
/* 554 */               .then(
/* 555 */                 Commands.argument("source", (ArgumentType)ScoreHolderArgument.scoreHolder())
/* 556 */                 .suggests(ScoreHolderArgument.SUGGEST_SCORE_HOLDERS)
/* 557 */                 .then(
/* 558 */                   addConditional($$0, (ArgumentBuilder<CommandSourceStack, ?>)Commands.argument("sourceObjective", (ArgumentType)ObjectiveArgument.objective()), $$2, $$0 -> checkScore($$0, ()))))))
/*     */ 
/*     */ 
/*     */             
/* 562 */             .then(
/* 563 */               Commands.literal("matches")
/* 564 */               .then(
/* 565 */                 addConditional($$0, (ArgumentBuilder<CommandSourceStack, ?>)Commands.argument("range", (ArgumentType)RangeArgument.intRange()), $$2, $$0 -> checkScore($$0, RangeArgument.Ints.getRange($$0, "range")))))))))
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 571 */       .then(
/* 572 */         Commands.literal("blocks")
/* 573 */         .then(
/* 574 */           Commands.argument("start", (ArgumentType)BlockPosArgument.blockPos())
/* 575 */           .then(
/* 576 */             Commands.argument("end", (ArgumentType)BlockPosArgument.blockPos())
/* 577 */             .then((
/* 578 */               (RequiredArgumentBuilder)Commands.argument("destination", (ArgumentType)BlockPosArgument.blockPos())
/* 579 */               .then(
/* 580 */                 addIfBlocksConditional($$0, (ArgumentBuilder<CommandSourceStack, ?>)Commands.literal("all"), $$2, false)))
/*     */               
/* 582 */               .then(
/* 583 */                 addIfBlocksConditional($$0, (ArgumentBuilder<CommandSourceStack, ?>)Commands.literal("masked"), $$2, true)))))))
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 589 */       .then(
/* 590 */         Commands.literal("entity")
/* 591 */         .then((
/* 592 */           (RequiredArgumentBuilder)Commands.argument("entities", (ArgumentType)EntityArgument.entities())
/* 593 */           .fork($$0, $$1 -> expect($$1, $$0, !EntityArgument.getOptionalEntities($$1, "entities").isEmpty())))
/* 594 */           .executes(createNumericConditionalHandler($$2, $$0 -> EntityArgument.getOptionalEntities($$0, "entities").size())))))
/*     */ 
/*     */ 
/*     */       
/* 598 */       .then(
/* 599 */         Commands.literal("predicate")
/* 600 */         .then(
/* 601 */           addConditional($$0, (ArgumentBuilder<CommandSourceStack, ?>)Commands.argument("predicate", (ArgumentType)ResourceLocationArgument.id()).suggests(SUGGEST_PREDICATE), $$2, $$0 -> checkCustomPredicate((CommandSourceStack)$$0.getSource(), ResourceLocationArgument.getPredicate($$0, "predicate"))))))
/*     */ 
/*     */       
/* 604 */       .then(
/* 605 */         Commands.literal("function")
/* 606 */         .then(
/* 607 */           Commands.argument("name", (ArgumentType)FunctionArgument.functions())
/* 608 */           .suggests(FunctionCommand.SUGGEST_FUNCTION)
/* 609 */           .fork($$0, (RedirectModifier)new ExecuteIfFunctionCustomModifier($$2))));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 614 */     for (DataCommands.DataProvider $$4 : DataCommands.SOURCE_PROVIDERS) {
/* 615 */       $$1
/* 616 */         .then($$4
/* 617 */           .wrap((ArgumentBuilder)Commands.literal("data"), $$3 -> $$3.then(((RequiredArgumentBuilder)Commands.argument("path", (ArgumentType)NbtPathArgument.nbtPath()).fork($$0, ())).executes(createNumericConditionalHandler($$1, ())))));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 627 */     return (ArgumentBuilder)$$1;
/*     */   }
/*     */   
/*     */   private static Command<CommandSourceStack> createNumericConditionalHandler(boolean $$0, CommandNumericPredicate $$1) {
/* 631 */     if ($$0) {
/* 632 */       return $$1 -> {
/*     */           int $$2 = $$0.test($$1);
/*     */           
/*     */           if ($$2 > 0) {
/*     */             ((CommandSourceStack)$$1.getSource()).sendSuccess((), false);
/*     */             return $$2;
/*     */           } 
/*     */           throw ERROR_CONDITIONAL_FAILED.create();
/*     */         };
/*     */     }
/* 642 */     return $$1 -> {
/*     */         int $$2 = $$0.test($$1);
/*     */         if ($$2 == 0) {
/*     */           ((CommandSourceStack)$$1.getSource()).sendSuccess((), false);
/*     */           return 1;
/*     */         } 
/*     */         throw ERROR_CONDITIONAL_FAILED_COUNT.create(Integer.valueOf($$2));
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int checkMatchingData(DataAccessor $$0, NbtPathArgument.NbtPath $$1) throws CommandSyntaxException {
/* 655 */     return $$1.countMatching((Tag)$$0.getData());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean checkScore(CommandContext<CommandSourceStack> $$0, IntBiPredicate $$1) throws CommandSyntaxException {
/* 664 */     ScoreHolder $$2 = ScoreHolderArgument.getName($$0, "target");
/* 665 */     Objective $$3 = ObjectiveArgument.getObjective($$0, "targetObjective");
/* 666 */     ScoreHolder $$4 = ScoreHolderArgument.getName($$0, "source");
/* 667 */     Objective $$5 = ObjectiveArgument.getObjective($$0, "sourceObjective");
/*     */     
/* 669 */     ServerScoreboard serverScoreboard = ((CommandSourceStack)$$0.getSource()).getServer().getScoreboard();
/*     */     
/* 671 */     ReadOnlyScoreInfo $$7 = serverScoreboard.getPlayerScoreInfo($$2, $$3);
/* 672 */     ReadOnlyScoreInfo $$8 = serverScoreboard.getPlayerScoreInfo($$4, $$5);
/*     */     
/* 674 */     if ($$7 == null || $$8 == null) {
/* 675 */       return false;
/*     */     }
/*     */     
/* 678 */     return $$1.test($$7.value(), $$8.value());
/*     */   }
/*     */   
/*     */   private static boolean checkScore(CommandContext<CommandSourceStack> $$0, MinMaxBounds.Ints $$1) throws CommandSyntaxException {
/* 682 */     ScoreHolder $$2 = ScoreHolderArgument.getName($$0, "target");
/* 683 */     Objective $$3 = ObjectiveArgument.getObjective($$0, "targetObjective");
/*     */     
/* 685 */     ServerScoreboard serverScoreboard = ((CommandSourceStack)$$0.getSource()).getServer().getScoreboard();
/*     */     
/* 687 */     ReadOnlyScoreInfo $$5 = serverScoreboard.getPlayerScoreInfo($$2, $$3);
/*     */     
/* 689 */     if ($$5 == null) {
/* 690 */       return false;
/*     */     }
/*     */     
/* 693 */     return $$1.matches($$5.value());
/*     */   }
/*     */   
/*     */   private static boolean checkCustomPredicate(CommandSourceStack $$0, LootItemCondition $$1) {
/* 697 */     ServerLevel $$2 = $$0.getLevel();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 702 */     LootParams $$3 = (new LootParams.Builder($$2)).withParameter(LootContextParams.ORIGIN, $$0.getPosition()).withOptionalParameter(LootContextParams.THIS_ENTITY, $$0.getEntity()).create(LootContextParamSets.COMMAND);
/* 703 */     LootContext $$4 = (new LootContext.Builder($$3)).create(Optional.empty());
/* 704 */     $$4.pushVisitedElement(LootContext.createVisitedEntry($$1));
/* 705 */     return $$1.test($$4);
/*     */   }
/*     */   
/*     */   private static Collection<CommandSourceStack> expect(CommandContext<CommandSourceStack> $$0, boolean $$1, boolean $$2) {
/* 709 */     if ($$2 == $$1) {
/* 710 */       return Collections.singleton((CommandSourceStack)$$0.getSource());
/*     */     }
/* 712 */     return Collections.emptyList();
/*     */   }
/*     */ 
/*     */   
/*     */   private static ArgumentBuilder<CommandSourceStack, ?> addConditional(CommandNode<CommandSourceStack> $$0, ArgumentBuilder<CommandSourceStack, ?> $$1, boolean $$2, CommandPredicate $$3) {
/* 717 */     return $$1
/* 718 */       .fork($$0, $$2 -> expect($$2, $$0, $$1.test($$2)))
/* 719 */       .executes($$2 -> {
/*     */           if ($$0 == $$1.test($$2)) {
/*     */             ((CommandSourceStack)$$2.getSource()).sendSuccess((), false);
/*     */             return 1;
/*     */           } 
/*     */           throw ERROR_CONDITIONAL_FAILED.create();
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private static ArgumentBuilder<CommandSourceStack, ?> addIfBlocksConditional(CommandNode<CommandSourceStack> $$0, ArgumentBuilder<CommandSourceStack, ?> $$1, boolean $$2, boolean $$3) {
/* 730 */     return $$1
/* 731 */       .fork($$0, $$2 -> expect($$2, $$0, checkRegions($$2, $$1).isPresent()))
/* 732 */       .executes($$2 ? ($$1 -> checkIfRegions($$1, $$0)) : ($$1 -> checkUnlessRegions($$1, $$0)));
/*     */   }
/*     */   
/*     */   private static int checkIfRegions(CommandContext<CommandSourceStack> $$0, boolean $$1) throws CommandSyntaxException {
/* 736 */     OptionalInt $$2 = checkRegions($$0, $$1);
/* 737 */     if ($$2.isPresent()) {
/* 738 */       ((CommandSourceStack)$$0.getSource()).sendSuccess(() -> Component.translatable("commands.execute.conditional.pass_count", new Object[] { Integer.valueOf($$0.getAsInt()) }), false);
/* 739 */       return $$2.getAsInt();
/*     */     } 
/* 741 */     throw ERROR_CONDITIONAL_FAILED.create();
/*     */   }
/*     */ 
/*     */   
/*     */   private static int checkUnlessRegions(CommandContext<CommandSourceStack> $$0, boolean $$1) throws CommandSyntaxException {
/* 746 */     OptionalInt $$2 = checkRegions($$0, $$1);
/* 747 */     if ($$2.isPresent()) {
/* 748 */       throw ERROR_CONDITIONAL_FAILED_COUNT.create(Integer.valueOf($$2.getAsInt()));
/*     */     }
/* 750 */     ((CommandSourceStack)$$0.getSource()).sendSuccess(() -> Component.translatable("commands.execute.conditional.pass"), false);
/* 751 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   private static OptionalInt checkRegions(CommandContext<CommandSourceStack> $$0, boolean $$1) throws CommandSyntaxException {
/* 756 */     return checkRegions(((CommandSourceStack)$$0.getSource()).getLevel(), BlockPosArgument.getLoadedBlockPos($$0, "start"), BlockPosArgument.getLoadedBlockPos($$0, "end"), BlockPosArgument.getLoadedBlockPos($$0, "destination"), $$1);
/*     */   }
/*     */   
/*     */   private static OptionalInt checkRegions(ServerLevel $$0, BlockPos $$1, BlockPos $$2, BlockPos $$3, boolean $$4) throws CommandSyntaxException {
/* 760 */     BoundingBox $$5 = BoundingBox.fromCorners((Vec3i)$$1, (Vec3i)$$2);
/* 761 */     BoundingBox $$6 = BoundingBox.fromCorners((Vec3i)$$3, (Vec3i)$$3.offset($$5.getLength()));
/* 762 */     BlockPos $$7 = new BlockPos($$6.minX() - $$5.minX(), $$6.minY() - $$5.minY(), $$6.minZ() - $$5.minZ());
/* 763 */     int $$8 = $$5.getXSpan() * $$5.getYSpan() * $$5.getZSpan();
/*     */     
/* 765 */     if ($$8 > 32768) {
/* 766 */       throw ERROR_AREA_TOO_LARGE.create(Integer.valueOf(32768), Integer.valueOf($$8));
/*     */     }
/*     */     
/* 769 */     int $$9 = 0;
/* 770 */     for (int $$10 = $$5.minZ(); $$10 <= $$5.maxZ(); $$10++) {
/* 771 */       for (int $$11 = $$5.minY(); $$11 <= $$5.maxY(); $$11++) {
/* 772 */         for (int $$12 = $$5.minX(); $$12 <= $$5.maxX(); $$12++) {
/* 773 */           BlockPos $$13 = new BlockPos($$12, $$11, $$10);
/* 774 */           BlockPos $$14 = $$13.offset((Vec3i)$$7);
/*     */           
/* 776 */           BlockState $$15 = $$0.getBlockState($$13);
/* 777 */           if (!$$4 || !$$15.is(Blocks.AIR)) {
/*     */ 
/*     */ 
/*     */             
/* 781 */             if ($$15 != $$0.getBlockState($$14)) {
/* 782 */               return OptionalInt.empty();
/*     */             }
/*     */             
/* 785 */             BlockEntity $$16 = $$0.getBlockEntity($$13);
/* 786 */             BlockEntity $$17 = $$0.getBlockEntity($$14);
/* 787 */             if ($$16 != null) {
/* 788 */               if ($$17 == null) {
/* 789 */                 return OptionalInt.empty();
/*     */               }
/* 791 */               if ($$17.getType() != $$16.getType()) {
/* 792 */                 return OptionalInt.empty();
/*     */               }
/* 794 */               CompoundTag $$18 = $$16.saveWithoutMetadata();
/* 795 */               CompoundTag $$19 = $$17.saveWithoutMetadata();
/*     */               
/* 797 */               if (!$$18.equals($$19)) {
/* 798 */                 return OptionalInt.empty();
/*     */               }
/*     */             } 
/*     */             
/* 802 */             $$9++;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 807 */     return OptionalInt.of($$9);
/*     */   }
/*     */   
/*     */   private static RedirectModifier<CommandSourceStack> expandOneToOneEntityRelation(Function<Entity, Optional<Entity>> $$0) {
/* 811 */     return $$1 -> {
/*     */         CommandSourceStack $$2 = (CommandSourceStack)$$1.getSource();
/*     */         Entity $$3 = $$2.getEntity();
/*     */         return ($$3 == null) ? List.of() : ((Optional)$$0.apply($$3)).filter(()).map(()).orElse(List.of());
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static RedirectModifier<CommandSourceStack> expandOneToManyEntityRelation(Function<Entity, Stream<Entity>> $$0) {
/* 822 */     return $$1 -> {
/*     */         CommandSourceStack $$2 = (CommandSourceStack)$$1.getSource();
/*     */         Entity $$3 = $$2.getEntity();
/*     */         if ($$3 == null)
/*     */           return List.of(); 
/*     */         Objects.requireNonNull($$2);
/*     */         return ((Stream)$$0.apply($$3)).filter(()).map($$2::withEntity).toList();
/*     */       };
/*     */   }
/*     */   
/*     */   private static LiteralArgumentBuilder<CommandSourceStack> createRelationOperations(CommandNode<CommandSourceStack> $$0, LiteralArgumentBuilder<CommandSourceStack> $$1) {
/* 833 */     return (LiteralArgumentBuilder<CommandSourceStack>)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)$$1
/* 834 */       .then(
/* 835 */         Commands.literal("owner")
/* 836 */         .fork($$0, expandOneToOneEntityRelation($$0 -> {
/*     */               OwnableEntity $$1 = (OwnableEntity)$$0; return ($$0 instanceof OwnableEntity) ? Optional.<LivingEntity>ofNullable($$1.getOwner()) : Optional.empty();
/* 838 */             })))).then(
/* 839 */         Commands.literal("leasher")
/* 840 */         .fork($$0, expandOneToOneEntityRelation($$0 -> {
/*     */               Mob $$1 = (Mob)$$0; return ($$0 instanceof Mob) ? Optional.<Entity>ofNullable($$1.getLeashHolder()) : Optional.empty();
/* 842 */             })))).then(
/* 843 */         Commands.literal("target")
/* 844 */         .fork($$0, expandOneToOneEntityRelation($$0 -> {
/*     */               Targeting $$1 = (Targeting)$$0; return ($$0 instanceof Targeting) ? Optional.<LivingEntity>ofNullable($$1.getTarget()) : Optional.empty();
/* 846 */             })))).then(
/* 847 */         Commands.literal("attacker")
/* 848 */         .fork($$0, expandOneToOneEntityRelation($$0 -> {
/*     */               Attackable $$1 = (Attackable)$$0; return ($$0 instanceof Attackable) ? Optional.<LivingEntity>ofNullable($$1.getLastAttacker()) : Optional.empty();
/* 850 */             })))).then(
/* 851 */         Commands.literal("vehicle")
/* 852 */         .fork($$0, expandOneToOneEntityRelation($$0 -> Optional.ofNullable($$0.getVehicle())))))
/*     */       
/* 854 */       .then(
/* 855 */         Commands.literal("controller")
/* 856 */         .fork($$0, expandOneToOneEntityRelation($$0 -> Optional.ofNullable($$0.getControllingPassenger())))))
/*     */       
/* 858 */       .then(
/* 859 */         Commands.literal("origin")
/* 860 */         .fork($$0, expandOneToOneEntityRelation($$0 -> {
/*     */               TraceableEntity $$1 = (TraceableEntity)$$0; return ($$0 instanceof TraceableEntity) ? Optional.<Entity>ofNullable($$1.getOwner()) : Optional.empty();
/* 862 */             })))).then(
/* 863 */         Commands.literal("passengers")
/* 864 */         .fork($$0, expandOneToManyEntityRelation($$0 -> $$0.getPassengers().stream())));
/*     */   }
/*     */ 
/*     */   
/*     */   private static CommandSourceStack spawnEntityAndRedirect(CommandSourceStack $$0, Holder.Reference<EntityType<?>> $$1) throws CommandSyntaxException {
/* 869 */     Entity $$2 = SummonCommand.createEntity($$0, $$1, $$0.getPosition(), new CompoundTag(), true);
/* 870 */     return $$0.withEntity($$2);
/*     */   } @FunctionalInterface
/*     */   private static interface CommandPredicate {
/*     */     boolean test(CommandContext<CommandSourceStack> param1CommandContext) throws CommandSyntaxException; } @FunctionalInterface
/*     */   private static interface CommandNumericPredicate {
/*     */     int test(CommandContext<CommandSourceStack> param1CommandContext) throws CommandSyntaxException; }
/*     */   private static class ExecuteIfFunctionCustomModifier implements CustomModifierExecutor.ModifierAdapter<CommandSourceStack> { ExecuteIfFunctionCustomModifier(boolean $$0) {
/* 877 */       this.check = $$0 ? ($$0 -> ($$0 != 0)) : ($$0 -> ($$0 == 0));
/*     */     }
/*     */     private final IntPredicate check;
/*     */     
/*     */     public void apply(CommandSourceStack $$0, List<CommandSourceStack> $$1, ContextChain<CommandSourceStack> $$2, ChainModifiers $$3, ExecutionControl<CommandSourceStack> $$4) {
/* 882 */       ExecuteCommand.scheduleFunctionConditionsAndTest($$0, $$1, FunctionCommand::modifySenderForExecution, this.check, $$2, null, $$4, $$0 -> FunctionArgument.getFunctions($$0, "name"), $$3);
/*     */     } }
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
/*     */   public static <T extends ExecutionCommandSource<T>> void scheduleFunctionConditionsAndTest(T $$0, List<T> $$1, Function<T, T> $$2, IntPredicate $$3, ContextChain<T> $$4, @Nullable CompoundTag $$5, ExecutionControl<T> $$6, CommandGetter<T, Collection<CommandFunction<T>>> $$7, ChainModifiers $$8) {
/*     */     Collection<CommandFunction<T>> $$10;
/* 897 */     List<T> $$9 = new ArrayList<>($$1.size());
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 902 */       $$10 = $$7.get($$4.getTopContext().copyFor($$0));
/* 903 */     } catch (CommandSyntaxException $$11) {
/* 904 */       $$0.handleError($$11, $$8.isForked(), $$6.tracer());
/*     */       
/*     */       return;
/*     */     } 
/* 908 */     int $$13 = $$10.size();
/* 909 */     if ($$13 == 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 914 */     List<InstantiatedFunction<T>> $$14 = new ArrayList<>($$13);
/*     */     
/*     */     try {
/* 917 */       for (CommandFunction<T> $$15 : $$10) {
/*     */         try {
/* 919 */           $$14.add($$15.instantiate($$5, $$0.dispatcher(), $$0));
/* 920 */         } catch (FunctionInstantiationException $$16) {
/* 921 */           throw ERROR_FUNCTION_CONDITION_INSTANTATION_FAILURE.create($$15.id(), $$16.messageComponent());
/*     */         } 
/*     */       } 
/* 924 */     } catch (CommandSyntaxException $$17) {
/* 925 */       $$0.handleError($$17, $$8.isForked(), $$6.tracer());
/*     */     } 
/*     */     
/* 928 */     for (ExecutionCommandSource executionCommandSource1 : $$1) {
/* 929 */       ExecutionCommandSource executionCommandSource2 = (ExecutionCommandSource)$$2.apply((T)executionCommandSource1.clearCallbacks());
/* 930 */       CommandResultCallback $$20 = ($$3, $$4) -> {
/*     */           if ($$0.test($$4)) {
/*     */             $$1.add($$2);
/*     */           }
/*     */         };
/*     */       
/* 936 */       $$6.queueNext((EntryAction)new IsolatedCall($$2 -> { for (InstantiatedFunction<T> $$3 : (Iterable<InstantiatedFunction<T>>)$$0) $$2.queueNext((new CallFunction($$3, $$2.currentFrame().returnValueConsumer(), true)).bind($$1));  $$2.queueNext(FallthroughTask.instance()); }$$20));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 944 */     ContextChain<T> $$21 = $$4.nextStage();
/* 945 */     String $$22 = $$4.getTopContext().getInput();
/* 946 */     $$6.queueNext((EntryAction)new BuildContexts.Continuation($$22, $$21, $$8, (ExecutionCommandSource)$$0, $$9));
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   private static interface IntBiPredicate {
/*     */     boolean test(int param1Int1, int param1Int2);
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface CommandGetter<T, R> {
/*     */     R get(CommandContext<T> param1CommandContext) throws CommandSyntaxException;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\ExecuteCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */