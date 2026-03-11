/*     */ package net.minecraft.commands;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.brigadier.CommandDispatcher;
/*     */ import com.mojang.brigadier.ParseResults;
/*     */ import com.mojang.brigadier.StringReader;
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import com.mojang.brigadier.builder.ArgumentBuilder;
/*     */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*     */ import com.mojang.brigadier.builder.RequiredArgumentBuilder;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.context.CommandContextBuilder;
/*     */ import com.mojang.brigadier.context.ContextChain;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.tree.CommandNode;
/*     */ import com.mojang.brigadier.tree.RootCommandNode;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.function.UnaryOperator;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.SharedConstants;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.commands.execution.ExecutionContext;
/*     */ import net.minecraft.commands.synchronization.ArgumentTypeInfos;
/*     */ import net.minecraft.commands.synchronization.ArgumentUtils;
/*     */ import net.minecraft.commands.synchronization.SuggestionProviders;
/*     */ import net.minecraft.core.HolderLookup;
/*     */ import net.minecraft.core.HolderOwner;
/*     */ import net.minecraft.core.HolderSet;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.data.registries.VanillaRegistries;
/*     */ import net.minecraft.gametest.framework.TestCommand;
/*     */ import net.minecraft.network.chat.ClickEvent;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.ComponentUtils;
/*     */ import net.minecraft.network.chat.HoverEvent;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.network.chat.Style;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.game.ClientboundCommandsPacket;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.server.commands.AdvancementCommands;
/*     */ import net.minecraft.server.commands.AttributeCommand;
/*     */ import net.minecraft.server.commands.BanIpCommands;
/*     */ import net.minecraft.server.commands.BanListCommands;
/*     */ import net.minecraft.server.commands.BanPlayerCommands;
/*     */ import net.minecraft.server.commands.BossBarCommands;
/*     */ import net.minecraft.server.commands.ClearInventoryCommands;
/*     */ import net.minecraft.server.commands.CloneCommands;
/*     */ import net.minecraft.server.commands.DamageCommand;
/*     */ import net.minecraft.server.commands.DataPackCommand;
/*     */ import net.minecraft.server.commands.DeOpCommands;
/*     */ import net.minecraft.server.commands.DebugCommand;
/*     */ import net.minecraft.server.commands.DebugConfigCommand;
/*     */ import net.minecraft.server.commands.DebugMobSpawningCommand;
/*     */ import net.minecraft.server.commands.DebugPathCommand;
/*     */ import net.minecraft.server.commands.DefaultGameModeCommands;
/*     */ import net.minecraft.server.commands.DifficultyCommand;
/*     */ import net.minecraft.server.commands.EffectCommands;
/*     */ import net.minecraft.server.commands.EmoteCommands;
/*     */ import net.minecraft.server.commands.EnchantCommand;
/*     */ import net.minecraft.server.commands.ExecuteCommand;
/*     */ import net.minecraft.server.commands.ExperienceCommand;
/*     */ import net.minecraft.server.commands.FillBiomeCommand;
/*     */ import net.minecraft.server.commands.FillCommand;
/*     */ import net.minecraft.server.commands.ForceLoadCommand;
/*     */ import net.minecraft.server.commands.FunctionCommand;
/*     */ import net.minecraft.server.commands.GameModeCommand;
/*     */ import net.minecraft.server.commands.GameRuleCommand;
/*     */ import net.minecraft.server.commands.GiveCommand;
/*     */ import net.minecraft.server.commands.HelpCommand;
/*     */ import net.minecraft.server.commands.ItemCommands;
/*     */ import net.minecraft.server.commands.JfrCommand;
/*     */ import net.minecraft.server.commands.KickCommand;
/*     */ import net.minecraft.server.commands.KillCommand;
/*     */ import net.minecraft.server.commands.ListPlayersCommand;
/*     */ import net.minecraft.server.commands.LocateCommand;
/*     */ import net.minecraft.server.commands.LootCommand;
/*     */ import net.minecraft.server.commands.MsgCommand;
/*     */ import net.minecraft.server.commands.OpCommand;
/*     */ import net.minecraft.server.commands.PardonCommand;
/*     */ import net.minecraft.server.commands.PardonIpCommand;
/*     */ import net.minecraft.server.commands.ParticleCommand;
/*     */ import net.minecraft.server.commands.PerfCommand;
/*     */ import net.minecraft.server.commands.PlaceCommand;
/*     */ import net.minecraft.server.commands.PlaySoundCommand;
/*     */ import net.minecraft.server.commands.PublishCommand;
/*     */ import net.minecraft.server.commands.RaidCommand;
/*     */ import net.minecraft.server.commands.RandomCommand;
/*     */ import net.minecraft.server.commands.RecipeCommand;
/*     */ import net.minecraft.server.commands.ReloadCommand;
/*     */ import net.minecraft.server.commands.ResetChunksCommand;
/*     */ import net.minecraft.server.commands.ReturnCommand;
/*     */ import net.minecraft.server.commands.RideCommand;
/*     */ import net.minecraft.server.commands.SaveAllCommand;
/*     */ import net.minecraft.server.commands.SaveOffCommand;
/*     */ import net.minecraft.server.commands.SaveOnCommand;
/*     */ import net.minecraft.server.commands.SayCommand;
/*     */ import net.minecraft.server.commands.ScheduleCommand;
/*     */ import net.minecraft.server.commands.ScoreboardCommand;
/*     */ import net.minecraft.server.commands.SeedCommand;
/*     */ import net.minecraft.server.commands.ServerPackCommand;
/*     */ import net.minecraft.server.commands.SetBlockCommand;
/*     */ import net.minecraft.server.commands.SetPlayerIdleTimeoutCommand;
/*     */ import net.minecraft.server.commands.SetSpawnCommand;
/*     */ import net.minecraft.server.commands.SetWorldSpawnCommand;
/*     */ import net.minecraft.server.commands.SpawnArmorTrimsCommand;
/*     */ import net.minecraft.server.commands.SpectateCommand;
/*     */ import net.minecraft.server.commands.SpreadPlayersCommand;
/*     */ import net.minecraft.server.commands.StopCommand;
/*     */ import net.minecraft.server.commands.StopSoundCommand;
/*     */ import net.minecraft.server.commands.SummonCommand;
/*     */ import net.minecraft.server.commands.TagCommand;
/*     */ import net.minecraft.server.commands.TeamCommand;
/*     */ import net.minecraft.server.commands.TeamMsgCommand;
/*     */ import net.minecraft.server.commands.TeleportCommand;
/*     */ import net.minecraft.server.commands.TellRawCommand;
/*     */ import net.minecraft.server.commands.TickCommand;
/*     */ import net.minecraft.server.commands.TimeCommand;
/*     */ import net.minecraft.server.commands.TitleCommand;
/*     */ import net.minecraft.server.commands.TriggerCommand;
/*     */ import net.minecraft.server.commands.WardenSpawnTrackerCommand;
/*     */ import net.minecraft.server.commands.WeatherCommand;
/*     */ import net.minecraft.server.commands.WhitelistCommand;
/*     */ import net.minecraft.server.commands.WorldBorderCommand;
/*     */ import net.minecraft.server.commands.data.DataCommands;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.tags.TagKey;
/*     */ import net.minecraft.util.profiling.jfr.JvmProfiler;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Commands
/*     */ {
/* 146 */   private static final ThreadLocal<ExecutionContext<CommandSourceStack>> CURRENT_EXECUTION_CONTEXT = new ThreadLocal<>();
/*     */   
/* 148 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */ 
/*     */   
/*     */   public static final int LEVEL_ALL = 0;
/*     */   
/*     */   public static final int LEVEL_MODERATORS = 1;
/*     */   
/*     */   public static final int LEVEL_GAMEMASTERS = 2;
/*     */   
/*     */   public static final int LEVEL_ADMINS = 3;
/*     */   
/*     */   public static final int LEVEL_OWNERS = 4;
/*     */   
/* 161 */   private final CommandDispatcher<CommandSourceStack> dispatcher = new CommandDispatcher();
/*     */   
/*     */   public enum CommandSelection {
/* 164 */     ALL(true, true),
/* 165 */     DEDICATED(false, true),
/* 166 */     INTEGRATED(true, false);
/*     */     
/*     */     final boolean includeIntegrated;
/*     */     
/*     */     final boolean includeDedicated;
/*     */     
/*     */     CommandSelection(boolean $$0, boolean $$1) {
/* 173 */       this.includeIntegrated = $$0;
/* 174 */       this.includeDedicated = $$1;
/*     */     }
/*     */   }
/*     */   
/*     */   public Commands(CommandSelection $$0, CommandBuildContext $$1) {
/* 179 */     AdvancementCommands.register(this.dispatcher);
/* 180 */     AttributeCommand.register(this.dispatcher, $$1);
/* 181 */     ExecuteCommand.register(this.dispatcher, $$1);
/* 182 */     BossBarCommands.register(this.dispatcher);
/* 183 */     ClearInventoryCommands.register(this.dispatcher, $$1);
/* 184 */     CloneCommands.register(this.dispatcher, $$1);
/* 185 */     DamageCommand.register(this.dispatcher, $$1);
/* 186 */     DataCommands.register(this.dispatcher);
/* 187 */     DataPackCommand.register(this.dispatcher);
/* 188 */     DebugCommand.register(this.dispatcher);
/* 189 */     DefaultGameModeCommands.register(this.dispatcher);
/* 190 */     DifficultyCommand.register(this.dispatcher);
/* 191 */     EffectCommands.register(this.dispatcher, $$1);
/* 192 */     EmoteCommands.register(this.dispatcher);
/* 193 */     EnchantCommand.register(this.dispatcher, $$1);
/* 194 */     ExperienceCommand.register(this.dispatcher);
/* 195 */     FillCommand.register(this.dispatcher, $$1);
/* 196 */     FillBiomeCommand.register(this.dispatcher, $$1);
/* 197 */     ForceLoadCommand.register(this.dispatcher);
/* 198 */     FunctionCommand.register(this.dispatcher);
/* 199 */     GameModeCommand.register(this.dispatcher);
/* 200 */     GameRuleCommand.register(this.dispatcher);
/* 201 */     GiveCommand.register(this.dispatcher, $$1);
/* 202 */     HelpCommand.register(this.dispatcher);
/* 203 */     ItemCommands.register(this.dispatcher, $$1);
/* 204 */     KickCommand.register(this.dispatcher);
/* 205 */     KillCommand.register(this.dispatcher);
/* 206 */     ListPlayersCommand.register(this.dispatcher);
/* 207 */     LocateCommand.register(this.dispatcher, $$1);
/* 208 */     LootCommand.register(this.dispatcher, $$1);
/* 209 */     MsgCommand.register(this.dispatcher);
/* 210 */     ParticleCommand.register(this.dispatcher, $$1);
/* 211 */     PlaceCommand.register(this.dispatcher);
/* 212 */     PlaySoundCommand.register(this.dispatcher);
/* 213 */     RandomCommand.register(this.dispatcher);
/* 214 */     ReloadCommand.register(this.dispatcher);
/* 215 */     RecipeCommand.register(this.dispatcher);
/* 216 */     ReturnCommand.register(this.dispatcher);
/* 217 */     RideCommand.register(this.dispatcher);
/* 218 */     SayCommand.register(this.dispatcher);
/* 219 */     ScheduleCommand.register(this.dispatcher);
/* 220 */     ScoreboardCommand.register(this.dispatcher);
/* 221 */     SeedCommand.register(this.dispatcher, ($$0 != CommandSelection.INTEGRATED));
/* 222 */     SetBlockCommand.register(this.dispatcher, $$1);
/* 223 */     SetSpawnCommand.register(this.dispatcher);
/* 224 */     SetWorldSpawnCommand.register(this.dispatcher);
/* 225 */     SpectateCommand.register(this.dispatcher);
/* 226 */     SpreadPlayersCommand.register(this.dispatcher);
/* 227 */     StopSoundCommand.register(this.dispatcher);
/* 228 */     SummonCommand.register(this.dispatcher, $$1);
/* 229 */     TagCommand.register(this.dispatcher);
/* 230 */     TeamCommand.register(this.dispatcher);
/* 231 */     TeamMsgCommand.register(this.dispatcher);
/* 232 */     TeleportCommand.register(this.dispatcher);
/* 233 */     TellRawCommand.register(this.dispatcher);
/* 234 */     TickCommand.register(this.dispatcher);
/* 235 */     TimeCommand.register(this.dispatcher);
/* 236 */     TitleCommand.register(this.dispatcher);
/* 237 */     TriggerCommand.register(this.dispatcher);
/* 238 */     WeatherCommand.register(this.dispatcher);
/* 239 */     WorldBorderCommand.register(this.dispatcher);
/*     */ 
/*     */     
/* 242 */     if (JvmProfiler.INSTANCE.isAvailable()) {
/* 243 */       JfrCommand.register(this.dispatcher);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 251 */     if (SharedConstants.IS_RUNNING_IN_IDE) {
/*     */       
/* 253 */       TestCommand.register(this.dispatcher);
/* 254 */       ResetChunksCommand.register(this.dispatcher);
/* 255 */       RaidCommand.register(this.dispatcher);
/* 256 */       DebugPathCommand.register(this.dispatcher);
/* 257 */       DebugMobSpawningCommand.register(this.dispatcher);
/* 258 */       WardenSpawnTrackerCommand.register(this.dispatcher);
/* 259 */       SpawnArmorTrimsCommand.register(this.dispatcher);
/* 260 */       ServerPackCommand.register(this.dispatcher);
/* 261 */       if ($$0.includeDedicated) {
/* 262 */         DebugConfigCommand.register(this.dispatcher);
/*     */       }
/*     */     } 
/*     */     
/* 266 */     if ($$0.includeDedicated) {
/* 267 */       BanIpCommands.register(this.dispatcher);
/* 268 */       BanListCommands.register(this.dispatcher);
/* 269 */       BanPlayerCommands.register(this.dispatcher);
/* 270 */       DeOpCommands.register(this.dispatcher);
/* 271 */       OpCommand.register(this.dispatcher);
/* 272 */       PardonCommand.register(this.dispatcher);
/* 273 */       PardonIpCommand.register(this.dispatcher);
/* 274 */       PerfCommand.register(this.dispatcher);
/* 275 */       SaveAllCommand.register(this.dispatcher);
/* 276 */       SaveOffCommand.register(this.dispatcher);
/* 277 */       SaveOnCommand.register(this.dispatcher);
/* 278 */       SetPlayerIdleTimeoutCommand.register(this.dispatcher);
/* 279 */       StopCommand.register(this.dispatcher);
/* 280 */       WhitelistCommand.register(this.dispatcher);
/*     */     } 
/*     */     
/* 283 */     if ($$0.includeIntegrated) {
/* 284 */       PublishCommand.register(this.dispatcher);
/*     */     }
/*     */     
/* 287 */     this.dispatcher.setConsumer(ExecutionCommandSource.resultConsumer());
/*     */   }
/*     */   
/*     */   public static <S> ParseResults<S> mapSource(ParseResults<S> $$0, UnaryOperator<S> $$1) {
/* 291 */     CommandContextBuilder<S> $$2 = $$0.getContext();
/* 292 */     CommandContextBuilder<S> $$3 = $$2.withSource($$1.apply((S)$$2.getSource()));
/* 293 */     return new ParseResults($$3, $$0.getReader(), $$0.getExceptions());
/*     */   }
/*     */   
/*     */   public void performPrefixedCommand(CommandSourceStack $$0, String $$1) {
/* 297 */     $$1 = $$1.startsWith("/") ? $$1.substring(1) : $$1;
/* 298 */     performCommand(this.dispatcher.parse($$1, $$0), $$1);
/*     */   }
/*     */   
/*     */   public void performCommand(ParseResults<CommandSourceStack> $$0, String $$1) {
/* 302 */     CommandSourceStack $$2 = (CommandSourceStack)$$0.getContext().getSource();
/* 303 */     $$2.getServer().getProfiler().push(() -> "/" + $$0);
/* 304 */     ContextChain<CommandSourceStack> $$3 = finishParsing($$0, $$1, $$2);
/*     */     
/*     */     try {
/* 307 */       if ($$3 != null) {
/* 308 */         executeCommandInContext($$2, $$3 -> ExecutionContext.queueInitialCommandExecution($$3, $$0, $$1, $$2, CommandResultCallback.EMPTY));
/*     */       }
/* 310 */     } catch (Exception $$4) {
/* 311 */       MutableComponent $$5 = Component.literal(($$4.getMessage() == null) ? $$4.getClass().getName() : $$4.getMessage());
/* 312 */       if (LOGGER.isDebugEnabled()) {
/* 313 */         LOGGER.error("Command exception: /{}", $$1, $$4);
/* 314 */         StackTraceElement[] $$6 = $$4.getStackTrace();
/* 315 */         for (int $$7 = 0; $$7 < Math.min($$6.length, 3); $$7++) {
/* 316 */           $$5.append("\n\n")
/* 317 */             .append($$6[$$7].getMethodName())
/* 318 */             .append("\n ")
/* 319 */             .append($$6[$$7].getFileName())
/* 320 */             .append(":")
/* 321 */             .append(String.valueOf($$6[$$7].getLineNumber()));
/*     */         }
/*     */       } 
/* 324 */       $$2.sendFailure((Component)Component.translatable("command.failed").withStyle($$1 -> $$1.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, $$0))));
/* 325 */       if (SharedConstants.IS_RUNNING_IN_IDE) {
/* 326 */         $$2.sendFailure((Component)Component.literal(Util.describeError($$4)));
/* 327 */         LOGGER.error("'/{}' threw an exception", $$1, $$4);
/*     */       } 
/*     */     } finally {
/* 330 */       $$2.getServer().getProfiler().pop();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private static ContextChain<CommandSourceStack> finishParsing(ParseResults<CommandSourceStack> $$0, String $$1, CommandSourceStack $$2) {
/*     */     try {
/* 338 */       validateParseResults($$0);
/*     */       
/* 340 */       return (ContextChain<CommandSourceStack>)ContextChain.tryFlatten($$0.getContext().build($$1))
/* 341 */         .orElseThrow(() -> CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownCommand().createWithContext($$0.getReader()));
/* 342 */     } catch (CommandSyntaxException $$3) {
/* 343 */       $$2.sendFailure(ComponentUtils.fromMessage($$3.getRawMessage()));
/*     */       
/* 345 */       if ($$3.getInput() != null && $$3.getCursor() >= 0) {
/* 346 */         int $$4 = Math.min($$3.getInput().length(), $$3.getCursor());
/* 347 */         MutableComponent $$5 = Component.empty().withStyle(ChatFormatting.GRAY).withStyle($$1 -> $$1.withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/" + $$0)));
/*     */ 
/*     */         
/* 350 */         if ($$4 > 10) {
/* 351 */           $$5.append(CommonComponents.ELLIPSIS);
/*     */         }
/* 353 */         $$5.append($$3.getInput().substring(Math.max(0, $$4 - 10), $$4));
/* 354 */         if ($$4 < $$3.getInput().length()) {
/* 355 */           MutableComponent mutableComponent = Component.literal($$3.getInput().substring($$4)).withStyle(new ChatFormatting[] { ChatFormatting.RED, ChatFormatting.UNDERLINE });
/* 356 */           $$5.append((Component)mutableComponent);
/*     */         } 
/* 358 */         $$5.append((Component)Component.translatable("command.context.here").withStyle(new ChatFormatting[] { ChatFormatting.RED, ChatFormatting.ITALIC }));
/* 359 */         $$2.sendFailure((Component)$$5);
/*     */       } 
/* 361 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void executeCommandInContext(CommandSourceStack $$0, Consumer<ExecutionContext<CommandSourceStack>> $$1) {
/* 366 */     MinecraftServer $$2 = $$0.getServer();
/*     */     
/* 368 */     ExecutionContext<CommandSourceStack> $$3 = CURRENT_EXECUTION_CONTEXT.get();
/* 369 */     boolean $$4 = ($$3 == null);
/*     */     
/* 371 */     if ($$4) {
/*     */       
/* 373 */       int $$5 = Math.max(1, $$2.getGameRules().getInt(GameRules.RULE_MAX_COMMAND_CHAIN_LENGTH));
/* 374 */       int $$6 = $$2.getGameRules().getInt(GameRules.RULE_MAX_COMMAND_FORK_COUNT); 
/* 375 */       try { ExecutionContext<CommandSourceStack> $$7 = new ExecutionContext($$5, $$6, $$2.getProfiler()); 
/* 376 */         try { CURRENT_EXECUTION_CONTEXT.set($$7);
/* 377 */           $$1.accept($$7);
/* 378 */           $$7.runCommandQueue();
/* 379 */           $$7.close(); } catch (Throwable throwable) { try { $$7.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  }
/* 380 */       finally { CURRENT_EXECUTION_CONTEXT.set(null); }
/*     */     
/*     */     } else {
/* 383 */       $$1.accept($$3);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void sendCommands(ServerPlayer $$0) {
/* 388 */     Map<CommandNode<CommandSourceStack>, CommandNode<SharedSuggestionProvider>> $$1 = Maps.newHashMap();
/* 389 */     RootCommandNode<SharedSuggestionProvider> $$2 = new RootCommandNode();
/* 390 */     $$1.put(this.dispatcher.getRoot(), $$2);
/* 391 */     fillUsableCommands((CommandNode<CommandSourceStack>)this.dispatcher.getRoot(), (CommandNode<SharedSuggestionProvider>)$$2, $$0.createCommandSourceStack(), $$1);
/* 392 */     $$0.connection.send((Packet)new ClientboundCommandsPacket($$2));
/*     */   }
/*     */   
/*     */   private void fillUsableCommands(CommandNode<CommandSourceStack> $$0, CommandNode<SharedSuggestionProvider> $$1, CommandSourceStack $$2, Map<CommandNode<CommandSourceStack>, CommandNode<SharedSuggestionProvider>> $$3) {
/* 396 */     for (CommandNode<CommandSourceStack> $$4 : (Iterable<CommandNode<CommandSourceStack>>)$$0.getChildren()) {
/* 397 */       if ($$4.canUse($$2)) {
/* 398 */         ArgumentBuilder<SharedSuggestionProvider, ?> $$5 = $$4.createBuilder();
/* 399 */         $$5.requires($$0 -> true);
/* 400 */         if ($$5.getCommand() != null)
/*     */         {
/*     */           
/* 403 */           $$5.executes($$0 -> 0);
/*     */         }
/* 405 */         if ($$5 instanceof RequiredArgumentBuilder) {
/* 406 */           RequiredArgumentBuilder<SharedSuggestionProvider, ?> $$6 = (RequiredArgumentBuilder<SharedSuggestionProvider, ?>)$$5;
/* 407 */           if ($$6.getSuggestionsProvider() != null)
/*     */           {
/*     */             
/* 410 */             $$6.suggests(SuggestionProviders.safelySwap($$6.getSuggestionsProvider()));
/*     */           }
/*     */         } 
/* 413 */         if ($$5.getRedirect() != null) {
/* 414 */           $$5.redirect($$3.get($$5.getRedirect()));
/*     */         }
/* 416 */         CommandNode<SharedSuggestionProvider> $$7 = $$5.build();
/* 417 */         $$3.put($$4, $$7);
/* 418 */         $$1.addChild($$7);
/* 419 */         if (!$$4.getChildren().isEmpty()) {
/* 420 */           fillUsableCommands($$4, $$7, $$2, $$3);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static LiteralArgumentBuilder<CommandSourceStack> literal(String $$0) {
/* 427 */     return LiteralArgumentBuilder.literal($$0);
/*     */   }
/*     */   
/*     */   public static <T> RequiredArgumentBuilder<CommandSourceStack, T> argument(String $$0, ArgumentType<T> $$1) {
/* 431 */     return RequiredArgumentBuilder.argument($$0, $$1);
/*     */   }
/*     */   
/*     */   public static Predicate<String> createValidator(ParseFunction $$0) {
/* 435 */     return $$1 -> {
/*     */         try {
/*     */           $$0.parse(new StringReader($$1));
/*     */           return true;
/* 439 */         } catch (CommandSyntaxException $$2) {
/*     */           return false;
/*     */         } 
/*     */       };
/*     */   }
/*     */   
/*     */   public CommandDispatcher<CommandSourceStack> getDispatcher() {
/* 446 */     return this.dispatcher;
/*     */   }
/*     */   
/*     */   public static <S> void validateParseResults(ParseResults<S> $$0) throws CommandSyntaxException {
/* 450 */     CommandSyntaxException $$1 = getParseException($$0);
/* 451 */     if ($$1 != null) {
/* 452 */       throw $$1;
/*     */     }
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static <S> CommandSyntaxException getParseException(ParseResults<S> $$0) {
/* 458 */     if (!$$0.getReader().canRead())
/* 459 */       return null; 
/* 460 */     if ($$0.getExceptions().size() == 1)
/* 461 */       return $$0.getExceptions().values().iterator().next(); 
/* 462 */     if ($$0.getContext().getRange().isEmpty()) {
/* 463 */       return CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownCommand().createWithContext($$0.getReader());
/*     */     }
/* 465 */     return CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownArgument().createWithContext($$0.getReader());
/*     */   }
/*     */   
/*     */   public static CommandBuildContext createValidationContext(final HolderLookup.Provider registries) {
/* 469 */     return new CommandBuildContext()
/*     */       {
/*     */         public <T> HolderLookup<T> holderLookup(ResourceKey<? extends Registry<T>> $$0) {
/* 472 */           final HolderLookup.RegistryLookup<T> original = registries.lookupOrThrow($$0);
/*     */           
/* 474 */           return (HolderLookup<T>)new HolderLookup.Delegate<T>((HolderLookup)$$1)
/*     */             {
/*     */               public Optional<HolderSet.Named<T>> get(TagKey<T> $$0) {
/* 477 */                 return Optional.of(getOrThrow($$0));
/*     */               }
/*     */ 
/*     */               
/*     */               public HolderSet.Named<T> getOrThrow(TagKey<T> $$0) {
/* 482 */                 Optional<HolderSet.Named<T>> $$1 = original.get($$0);
/* 483 */                 return $$1.orElseGet(() -> HolderSet.emptyNamed((HolderOwner)$$0, $$1));
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   } @FunctionalInterface
/*     */   public static interface ParseFunction {
/*     */     void parse(StringReader param1StringReader) throws CommandSyntaxException; } public static void validate() {
/* 491 */     CommandBuildContext $$0 = createValidationContext(VanillaRegistries.createLookup());
/* 492 */     CommandDispatcher<CommandSourceStack> $$1 = (new Commands(CommandSelection.ALL, $$0)).getDispatcher();
/* 493 */     RootCommandNode<CommandSourceStack> $$2 = $$1.getRoot();
/*     */     
/* 495 */     $$1.findAmbiguities(($$1, $$2, $$3, $$4) -> LOGGER.warn("Ambiguity between arguments {} and {} with inputs: {}", new Object[] { $$0.getPath($$2), $$0.getPath($$3), $$4 }));
/*     */ 
/*     */ 
/*     */     
/* 499 */     Set<ArgumentType<?>> $$3 = ArgumentUtils.findUsedArgumentTypes((CommandNode)$$2);
/* 500 */     Set<ArgumentType<?>> $$4 = (Set<ArgumentType<?>>)$$3.stream().filter($$0 -> !ArgumentTypeInfos.isClassRecognized($$0.getClass())).collect(Collectors.toSet());
/* 501 */     if (!$$4.isEmpty()) {
/* 502 */       LOGGER.warn("Missing type registration for following arguments:\n {}", $$4.stream().map($$0 -> "\t" + $$0).collect(Collectors.joining(",\n")));
/* 503 */       throw new IllegalStateException("Unregistered argument types");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\Commands.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */