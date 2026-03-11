/*     */ package net.minecraft.server.commands;
/*     */ 
/*     */ import com.mojang.brigadier.CommandDispatcher;
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import com.mojang.brigadier.arguments.FloatArgumentType;
/*     */ import com.mojang.brigadier.arguments.IntegerArgumentType;
/*     */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.suggestion.SuggestionsBuilder;
/*     */ import java.util.Arrays;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.Commands;
/*     */ import net.minecraft.commands.SharedSuggestionProvider;
/*     */ import net.minecraft.commands.arguments.TimeArgument;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.server.ServerTickRateManager;
/*     */ import net.minecraft.util.TimeUtil;
/*     */ 
/*     */ public class TickCommand
/*     */ {
/*     */   private static final float MAX_TICKRATE = 10000.0F;
/*  24 */   private static final String DEFAULT_TICKRATE = String.valueOf(20);
/*     */   
/*     */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/*  27 */     $$0.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("tick")
/*  28 */         .requires($$0 -> $$0.hasPermission(3)))
/*  29 */         .then(Commands.literal("query")
/*  30 */           .executes($$0 -> tickQuery((CommandSourceStack)$$0.getSource()))))
/*  31 */         .then(Commands.literal("rate")
/*  32 */           .then(Commands.argument("rate", (ArgumentType)FloatArgumentType.floatArg(1.0F, 10000.0F))
/*  33 */             .suggests(($$0, $$1) -> SharedSuggestionProvider.suggest(new String[] { DEFAULT_TICKRATE
/*  34 */                 }, $$1)).executes($$0 -> setTickingRate((CommandSourceStack)$$0.getSource(), FloatArgumentType.getFloat($$0, "rate"))))))
/*  35 */         .then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("step")
/*  36 */           .executes($$0 -> step((CommandSourceStack)$$0.getSource(), 1)))
/*  37 */           .then(Commands.literal("stop")
/*  38 */             .executes($$0 -> stopStepping((CommandSourceStack)$$0.getSource()))))
/*  39 */           .then(Commands.argument("time", (ArgumentType)TimeArgument.time(1))
/*  40 */             .suggests(($$0, $$1) -> SharedSuggestionProvider.suggest(new String[] { "1t", "1s"
/*  41 */                 }, $$1)).executes($$0 -> step((CommandSourceStack)$$0.getSource(), IntegerArgumentType.getInteger($$0, "time"))))))
/*  42 */         .then(((LiteralArgumentBuilder)Commands.literal("sprint")
/*  43 */           .then(Commands.literal("stop")
/*  44 */             .executes($$0 -> stopSprinting((CommandSourceStack)$$0.getSource()))))
/*  45 */           .then(Commands.argument("time", (ArgumentType)TimeArgument.time(1))
/*  46 */             .suggests(($$0, $$1) -> SharedSuggestionProvider.suggest(new String[] { "60s", "1d", "3d"
/*  47 */                 }, $$1)).executes($$0 -> sprint((CommandSourceStack)$$0.getSource(), IntegerArgumentType.getInteger($$0, "time"))))))
/*  48 */         .then(Commands.literal("unfreeze").executes($$0 -> setFreeze((CommandSourceStack)$$0.getSource(), false))))
/*  49 */         .then(Commands.literal("freeze").executes($$0 -> setFreeze((CommandSourceStack)$$0.getSource(), true))));
/*     */   }
/*     */   
/*     */   private static String nanosToMilisString(long $$0) {
/*  53 */     return String.format("%.1f", new Object[] { Float.valueOf((float)$$0 / (float)TimeUtil.NANOSECONDS_PER_MILLISECOND) });
/*     */   }
/*     */   
/*     */   private static int setTickingRate(CommandSourceStack $$0, float $$1) {
/*  57 */     ServerTickRateManager $$2 = $$0.getServer().tickRateManager();
/*  58 */     $$2.setTickRate($$1);
/*  59 */     String $$3 = String.format("%.1f", new Object[] { Float.valueOf($$1) });
/*  60 */     $$0.sendSuccess(() -> Component.translatable("commands.tick.rate.success", new Object[] { $$0 }), true);
/*  61 */     return (int)$$1;
/*     */   }
/*     */   
/*     */   private static int tickQuery(CommandSourceStack $$0) {
/*  65 */     ServerTickRateManager $$1 = $$0.getServer().tickRateManager();
/*  66 */     String $$2 = nanosToMilisString($$0.getServer().getAverageTickTimeNanos());
/*     */     
/*  68 */     float $$3 = $$1.tickrate();
/*  69 */     String $$4 = String.format("%.1f", new Object[] { Float.valueOf($$3) });
/*  70 */     if ($$1.isSprinting()) {
/*  71 */       $$0.sendSuccess(() -> Component.translatable("commands.tick.status.sprinting"), false);
/*  72 */       $$0.sendSuccess(() -> Component.translatable("commands.tick.query.rate.sprinting", new Object[] { $$0, $$1 }), false);
/*     */     } else {
/*  74 */       if ($$1.isFrozen()) {
/*  75 */         $$0.sendSuccess(() -> Component.translatable("commands.tick.status.frozen"), false);
/*     */       }
/*  77 */       else if ($$1.nanosecondsPerTick() < $$0.getServer().getAverageTickTimeNanos()) {
/*  78 */         $$0.sendSuccess(() -> Component.translatable("commands.tick.status.lagging"), false);
/*     */       } else {
/*  80 */         $$0.sendSuccess(() -> Component.translatable("commands.tick.status.running"), false);
/*     */       } 
/*     */       
/*  83 */       String $$5 = nanosToMilisString($$1.nanosecondsPerTick());
/*  84 */       $$0.sendSuccess(() -> Component.translatable("commands.tick.query.rate.running", new Object[] { $$0, $$1, $$2 }), false);
/*     */     } 
/*     */     
/*  87 */     long[] $$6 = Arrays.copyOf($$0.getServer().getTickTimesNanos(), ($$0.getServer().getTickTimesNanos()).length);
/*  88 */     Arrays.sort($$6);
/*  89 */     String $$7 = nanosToMilisString($$6[$$6.length / 2]);
/*  90 */     String $$8 = nanosToMilisString($$6[(int)($$6.length * 0.95D)]);
/*  91 */     String $$9 = nanosToMilisString($$6[(int)($$6.length * 0.99D)]);
/*     */     
/*  93 */     $$0.sendSuccess(() -> Component.translatable("commands.tick.query.percentiles", new Object[] { $$0, $$1, $$2, Integer.valueOf($$3.length) }), false);
/*  94 */     return (int)$$3;
/*     */   }
/*     */   
/*     */   private static int sprint(CommandSourceStack $$0, int $$1) {
/*  98 */     boolean $$2 = $$0.getServer().tickRateManager().requestGameToSprint($$1);
/*  99 */     if ($$2) {
/* 100 */       $$0.sendSuccess(() -> Component.translatable("commands.tick.sprint.stop.success"), true);
/*     */     }
/* 102 */     $$0.sendSuccess(() -> Component.translatable("commands.tick.status.sprinting"), true);
/* 103 */     return 1;
/*     */   }
/*     */   
/*     */   private static int setFreeze(CommandSourceStack $$0, boolean $$1) {
/* 107 */     ServerTickRateManager $$2 = $$0.getServer().tickRateManager();
/* 108 */     if ($$1) {
/* 109 */       if ($$2.isSprinting()) {
/* 110 */         $$2.stopSprinting();
/*     */       }
/* 112 */       if ($$2.isSteppingForward()) {
/* 113 */         $$2.stopStepping();
/*     */       }
/*     */     } 
/* 116 */     $$2.setFrozen($$1);
/* 117 */     if ($$1) {
/* 118 */       $$0.sendSuccess(() -> Component.translatable("commands.tick.status.frozen"), true);
/*     */     } else {
/* 120 */       $$0.sendSuccess(() -> Component.translatable("commands.tick.status.running"), true);
/*     */     } 
/* 122 */     return $$1 ? 1 : 0;
/*     */   }
/*     */   
/*     */   private static int step(CommandSourceStack $$0, int $$1) {
/* 126 */     ServerTickRateManager $$2 = $$0.getServer().tickRateManager();
/* 127 */     boolean $$3 = $$2.stepGameIfPaused($$1);
/* 128 */     if ($$3) {
/* 129 */       $$0.sendSuccess(() -> Component.translatable("commands.tick.step.success", new Object[] { Integer.valueOf($$0) }), true);
/*     */     } else {
/* 131 */       $$0.sendFailure((Component)Component.translatable("commands.tick.step.fail"));
/*     */     } 
/* 133 */     return 1;
/*     */   }
/*     */   
/*     */   private static int stopStepping(CommandSourceStack $$0) {
/* 137 */     ServerTickRateManager $$1 = $$0.getServer().tickRateManager();
/* 138 */     boolean $$2 = $$1.stopStepping();
/* 139 */     if ($$2) {
/* 140 */       $$0.sendSuccess(() -> Component.translatable("commands.tick.step.stop.success"), true);
/* 141 */       return 1;
/*     */     } 
/* 143 */     $$0.sendFailure((Component)Component.translatable("commands.tick.step.stop.fail"));
/* 144 */     return 0;
/*     */   }
/*     */   
/*     */   private static int stopSprinting(CommandSourceStack $$0) {
/* 148 */     ServerTickRateManager $$1 = $$0.getServer().tickRateManager();
/* 149 */     boolean $$2 = $$1.stopSprinting();
/* 150 */     if ($$2) {
/* 151 */       $$0.sendSuccess(() -> Component.translatable("commands.tick.sprint.stop.success"), true);
/* 152 */       return 1;
/*     */     } 
/* 154 */     $$0.sendFailure((Component)Component.translatable("commands.tick.sprint.stop.fail"));
/* 155 */     return 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\TickCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */