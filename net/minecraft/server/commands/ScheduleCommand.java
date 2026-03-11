/*     */ package net.minecraft.server.commands;
/*     */ import com.mojang.brigadier.CommandDispatcher;
/*     */ import com.mojang.brigadier.Message;
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import com.mojang.brigadier.arguments.IntegerArgumentType;
/*     */ import com.mojang.brigadier.arguments.StringArgumentType;
/*     */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*     */ import com.mojang.brigadier.builder.RequiredArgumentBuilder;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
/*     */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*     */ import com.mojang.brigadier.suggestion.SuggestionProvider;
/*     */ import com.mojang.brigadier.suggestion.SuggestionsBuilder;
/*     */ import com.mojang.datafixers.util.Either;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import java.util.Collection;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.Commands;
/*     */ import net.minecraft.commands.SharedSuggestionProvider;
/*     */ import net.minecraft.commands.arguments.item.FunctionArgument;
/*     */ import net.minecraft.commands.functions.CommandFunction;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.world.level.timers.FunctionCallback;
/*     */ import net.minecraft.world.level.timers.FunctionTagCallback;
/*     */ import net.minecraft.world.level.timers.TimerCallback;
/*     */ import net.minecraft.world.level.timers.TimerQueue;
/*     */ 
/*     */ public class ScheduleCommand {
/*  33 */   private static final SimpleCommandExceptionType ERROR_SAME_TICK = new SimpleCommandExceptionType((Message)Component.translatable("commands.schedule.same_tick")); private static final DynamicCommandExceptionType ERROR_CANT_REMOVE; static {
/*  34 */     ERROR_CANT_REMOVE = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("commands.schedule.cleared.failure", new Object[] { $$0 }));
/*     */     
/*  36 */     SUGGEST_SCHEDULE = (($$0, $$1) -> SharedSuggestionProvider.suggest(((CommandSourceStack)$$0.getSource()).getServer().getWorldData().overworldData().getScheduledEvents().getEventsIds(), $$1));
/*     */   } private static final SuggestionProvider<CommandSourceStack> SUGGEST_SCHEDULE;
/*     */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/*  39 */     $$0.register(
/*  40 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("schedule")
/*  41 */         .requires($$0 -> $$0.hasPermission(2)))
/*  42 */         .then(
/*  43 */           Commands.literal("function")
/*  44 */           .then(
/*  45 */             Commands.argument("function", (ArgumentType)FunctionArgument.functions())
/*  46 */             .suggests(FunctionCommand.SUGGEST_FUNCTION)
/*  47 */             .then((
/*  48 */               (RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("time", (ArgumentType)TimeArgument.time())
/*  49 */               .executes($$0 -> schedule((CommandSourceStack)$$0.getSource(), FunctionArgument.getFunctionOrTag($$0, "function"), IntegerArgumentType.getInteger($$0, "time"), true)))
/*  50 */               .then(
/*  51 */                 Commands.literal("append")
/*  52 */                 .executes($$0 -> schedule((CommandSourceStack)$$0.getSource(), FunctionArgument.getFunctionOrTag($$0, "function"), IntegerArgumentType.getInteger($$0, "time"), false))))
/*     */               
/*  54 */               .then(
/*  55 */                 Commands.literal("replace")
/*  56 */                 .executes($$0 -> schedule((CommandSourceStack)$$0.getSource(), FunctionArgument.getFunctionOrTag($$0, "function"), IntegerArgumentType.getInteger($$0, "time"), true)))))))
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  61 */         .then(
/*  62 */           Commands.literal("clear")
/*  63 */           .then(
/*  64 */             Commands.argument("function", (ArgumentType)StringArgumentType.greedyString())
/*  65 */             .suggests(SUGGEST_SCHEDULE)
/*  66 */             .executes($$0 -> remove((CommandSourceStack)$$0.getSource(), StringArgumentType.getString($$0, "function"))))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int schedule(CommandSourceStack $$0, Pair<ResourceLocation, Either<CommandFunction<CommandSourceStack>, Collection<CommandFunction<CommandSourceStack>>>> $$1, int $$2, boolean $$3) throws CommandSyntaxException {
/*  73 */     if ($$2 == 0) {
/*  74 */       throw ERROR_SAME_TICK.create();
/*     */     }
/*     */     
/*  77 */     long $$4 = $$0.getLevel().getGameTime() + $$2;
/*     */     
/*  79 */     ResourceLocation $$5 = (ResourceLocation)$$1.getFirst();
/*  80 */     TimerQueue<MinecraftServer> $$6 = $$0.getServer().getWorldData().overworldData().getScheduledEvents();
/*  81 */     ((Either)$$1.getSecond())
/*  82 */       .ifLeft($$6 -> {
/*     */           String $$7 = $$0.toString();
/*     */           
/*     */           if ($$1) {
/*     */             $$2.remove($$7);
/*     */           }
/*     */           $$2.schedule($$7, $$3, (TimerCallback)new FunctionCallback($$0));
/*     */           $$4.sendSuccess((), true);
/*  90 */         }).ifRight($$6 -> {
/*     */           String $$7 = "#" + $$0;
/*     */           
/*     */           if ($$1) {
/*     */             $$2.remove($$7);
/*     */           }
/*     */           $$2.schedule($$7, $$3, (TimerCallback)new FunctionTagCallback($$0));
/*     */           $$4.sendSuccess((), true);
/*     */         });
/*  99 */     return Math.floorMod($$4, 2147483647);
/*     */   }
/*     */   
/*     */   private static int remove(CommandSourceStack $$0, String $$1) throws CommandSyntaxException {
/* 103 */     int $$2 = $$0.getServer().getWorldData().overworldData().getScheduledEvents().remove($$1);
/* 104 */     if ($$2 == 0) {
/* 105 */       throw ERROR_CANT_REMOVE.create($$1);
/*     */     }
/* 107 */     $$0.sendSuccess(() -> Component.translatable("commands.schedule.cleared.success", new Object[] { Integer.valueOf($$0), $$1 }), true);
/* 108 */     return $$2;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\ScheduleCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */