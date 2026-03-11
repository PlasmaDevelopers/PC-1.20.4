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
/*    */ 
/*    */ public class TimeCommand {
/*    */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/* 17 */     $$0.register(
/* 18 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("time")
/* 19 */         .requires($$0 -> $$0.hasPermission(2)))
/* 20 */         .then((
/* 21 */           (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("set")
/* 22 */           .then(
/* 23 */             Commands.literal("day")
/* 24 */             .executes($$0 -> setTime((CommandSourceStack)$$0.getSource(), 1000))))
/* 25 */           .then(
/* 26 */             Commands.literal("noon")
/* 27 */             .executes($$0 -> setTime((CommandSourceStack)$$0.getSource(), 6000))))
/* 28 */           .then(
/* 29 */             Commands.literal("night")
/* 30 */             .executes($$0 -> setTime((CommandSourceStack)$$0.getSource(), 13000))))
/* 31 */           .then(
/* 32 */             Commands.literal("midnight")
/* 33 */             .executes($$0 -> setTime((CommandSourceStack)$$0.getSource(), 18000))))
/* 34 */           .then(
/* 35 */             Commands.argument("time", (ArgumentType)TimeArgument.time())
/* 36 */             .executes($$0 -> setTime((CommandSourceStack)$$0.getSource(), IntegerArgumentType.getInteger($$0, "time"))))))
/*    */ 
/*    */         
/* 39 */         .then(
/* 40 */           Commands.literal("add")
/* 41 */           .then(
/* 42 */             Commands.argument("time", (ArgumentType)TimeArgument.time())
/* 43 */             .executes($$0 -> addTime((CommandSourceStack)$$0.getSource(), IntegerArgumentType.getInteger($$0, "time"))))))
/*    */ 
/*    */         
/* 46 */         .then((
/* 47 */           (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("query")
/* 48 */           .then(
/* 49 */             Commands.literal("daytime")
/* 50 */             .executes($$0 -> queryTime((CommandSourceStack)$$0.getSource(), getDayTime(((CommandSourceStack)$$0.getSource()).getLevel())))))
/*    */           
/* 52 */           .then(
/* 53 */             Commands.literal("gametime")
/* 54 */             .executes($$0 -> queryTime((CommandSourceStack)$$0.getSource(), (int)(((CommandSourceStack)$$0.getSource()).getLevel().getGameTime() % 2147483647L)))))
/*    */           
/* 56 */           .then(
/* 57 */             Commands.literal("day")
/* 58 */             .executes($$0 -> queryTime((CommandSourceStack)$$0.getSource(), (int)(((CommandSourceStack)$$0.getSource()).getLevel().getDayTime() / 24000L % 2147483647L))))));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static int getDayTime(ServerLevel $$0) {
/* 65 */     return (int)($$0.getDayTime() % 24000L);
/*    */   }
/*    */   
/*    */   private static int queryTime(CommandSourceStack $$0, int $$1) {
/* 69 */     $$0.sendSuccess(() -> Component.translatable("commands.time.query", new Object[] { Integer.valueOf($$0) }), false);
/* 70 */     return $$1;
/*    */   }
/*    */   
/*    */   public static int setTime(CommandSourceStack $$0, int $$1) {
/* 74 */     for (ServerLevel $$2 : $$0.getServer().getAllLevels()) {
/* 75 */       $$2.setDayTime($$1);
/*    */     }
/* 77 */     $$0.sendSuccess(() -> Component.translatable("commands.time.set", new Object[] { Integer.valueOf($$0) }), true);
/* 78 */     return getDayTime($$0.getLevel());
/*    */   }
/*    */   
/*    */   public static int addTime(CommandSourceStack $$0, int $$1) {
/* 82 */     for (ServerLevel $$2 : $$0.getServer().getAllLevels()) {
/* 83 */       $$2.setDayTime($$2.getDayTime() + $$1);
/*    */     }
/* 85 */     int $$3 = getDayTime($$0.getLevel());
/* 86 */     $$0.sendSuccess(() -> Component.translatable("commands.time.set", new Object[] { Integer.valueOf($$0) }), true);
/* 87 */     return $$3;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\TimeCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */