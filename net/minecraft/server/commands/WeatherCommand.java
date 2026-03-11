/*    */ package net.minecraft.server.commands;
/*    */ 
/*    */ import com.mojang.brigadier.CommandDispatcher;
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.arguments.IntegerArgumentType;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.Commands;
/*    */ import net.minecraft.commands.arguments.TimeArgument;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.util.valueproviders.IntProvider;
/*    */ 
/*    */ public class WeatherCommand
/*    */ {
/*    */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/* 19 */     $$0.register(
/* 20 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("weather")
/* 21 */         .requires($$0 -> $$0.hasPermission(2)))
/* 22 */         .then((
/* 23 */           (LiteralArgumentBuilder)Commands.literal("clear")
/* 24 */           .executes($$0 -> setClear((CommandSourceStack)$$0.getSource(), -1)))
/* 25 */           .then(
/* 26 */             Commands.argument("duration", (ArgumentType)TimeArgument.time(1))
/* 27 */             .executes($$0 -> setClear((CommandSourceStack)$$0.getSource(), IntegerArgumentType.getInteger($$0, "duration"))))))
/*    */ 
/*    */         
/* 30 */         .then((
/* 31 */           (LiteralArgumentBuilder)Commands.literal("rain")
/* 32 */           .executes($$0 -> setRain((CommandSourceStack)$$0.getSource(), -1)))
/* 33 */           .then(
/* 34 */             Commands.argument("duration", (ArgumentType)TimeArgument.time(1))
/* 35 */             .executes($$0 -> setRain((CommandSourceStack)$$0.getSource(), IntegerArgumentType.getInteger($$0, "duration"))))))
/*    */ 
/*    */         
/* 38 */         .then((
/* 39 */           (LiteralArgumentBuilder)Commands.literal("thunder")
/* 40 */           .executes($$0 -> setThunder((CommandSourceStack)$$0.getSource(), -1)))
/* 41 */           .then(
/* 42 */             Commands.argument("duration", (ArgumentType)TimeArgument.time(1))
/* 43 */             .executes($$0 -> setThunder((CommandSourceStack)$$0.getSource(), IntegerArgumentType.getInteger($$0, "duration"))))));
/*    */   }
/*    */ 
/*    */   
/*    */   private static final int DEFAULT_TIME = -1;
/*    */   
/*    */   private static int getDuration(CommandSourceStack $$0, int $$1, IntProvider $$2) {
/* 50 */     if ($$1 == -1) {
/* 51 */       return $$2.sample($$0.getLevel().getRandom());
/*    */     }
/* 53 */     return $$1;
/*    */   }
/*    */   
/*    */   private static int setClear(CommandSourceStack $$0, int $$1) {
/* 57 */     $$0.getLevel().setWeatherParameters(getDuration($$0, $$1, ServerLevel.RAIN_DELAY), 0, false, false);
/* 58 */     $$0.sendSuccess(() -> Component.translatable("commands.weather.set.clear"), true);
/* 59 */     return $$1;
/*    */   }
/*    */   
/*    */   private static int setRain(CommandSourceStack $$0, int $$1) {
/* 63 */     $$0.getLevel().setWeatherParameters(0, getDuration($$0, $$1, ServerLevel.RAIN_DURATION), true, false);
/* 64 */     $$0.sendSuccess(() -> Component.translatable("commands.weather.set.rain"), true);
/* 65 */     return $$1;
/*    */   }
/*    */   
/*    */   private static int setThunder(CommandSourceStack $$0, int $$1) {
/* 69 */     $$0.getLevel().setWeatherParameters(0, getDuration($$0, $$1, ServerLevel.THUNDER_DURATION), true, true);
/* 70 */     $$0.sendSuccess(() -> Component.translatable("commands.weather.set.thunder"), true);
/* 71 */     return $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\WeatherCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */