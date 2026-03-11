/*    */ package net.minecraft.server.commands;
/*    */ 
/*    */ import com.mojang.brigadier.CommandDispatcher;
/*    */ import com.mojang.brigadier.Message;
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.arguments.BoolArgumentType;
/*    */ import com.mojang.brigadier.arguments.IntegerArgumentType;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.builder.RequiredArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
/*    */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.Commands;
/*    */ import net.minecraft.commands.arguments.GameModeArgument;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.ComponentUtils;
/*    */ import net.minecraft.network.chat.MutableComponent;
/*    */ import net.minecraft.util.HttpUtil;
/*    */ import net.minecraft.world.level.GameType;
/*    */ 
/*    */ public class PublishCommand
/*    */ {
/*    */   private static final DynamicCommandExceptionType ERROR_ALREADY_PUBLISHED;
/* 27 */   private static final SimpleCommandExceptionType ERROR_FAILED = new SimpleCommandExceptionType((Message)Component.translatable("commands.publish.failed")); static {
/* 28 */     ERROR_ALREADY_PUBLISHED = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("commands.publish.alreadyPublished", new Object[] { $$0 }));
/*    */   }
/*    */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/* 31 */     $$0.register(
/* 32 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("publish")
/* 33 */         .requires($$0 -> $$0.hasPermission(4)))
/* 34 */         .executes($$0 -> publish((CommandSourceStack)$$0.getSource(), HttpUtil.getAvailablePort(), false, null)))
/* 35 */         .then((
/* 36 */           (RequiredArgumentBuilder)Commands.argument("allowCommands", (ArgumentType)BoolArgumentType.bool())
/* 37 */           .executes($$0 -> publish((CommandSourceStack)$$0.getSource(), HttpUtil.getAvailablePort(), BoolArgumentType.getBool($$0, "allowCommands"), null)))
/* 38 */           .then((
/* 39 */             (RequiredArgumentBuilder)Commands.argument("gamemode", (ArgumentType)GameModeArgument.gameMode())
/* 40 */             .executes($$0 -> publish((CommandSourceStack)$$0.getSource(), HttpUtil.getAvailablePort(), BoolArgumentType.getBool($$0, "allowCommands"), GameModeArgument.getGameMode($$0, "gamemode"))))
/* 41 */             .then(
/* 42 */               Commands.argument("port", (ArgumentType)IntegerArgumentType.integer(0, 65535))
/* 43 */               .executes($$0 -> publish((CommandSourceStack)$$0.getSource(), IntegerArgumentType.getInteger($$0, "port"), BoolArgumentType.getBool($$0, "allowCommands"), GameModeArgument.getGameMode($$0, "gamemode")))))));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static int publish(CommandSourceStack $$0, int $$1, boolean $$2, @Nullable GameType $$3) throws CommandSyntaxException {
/* 51 */     if ($$0.getServer().isPublished()) {
/* 52 */       throw ERROR_ALREADY_PUBLISHED.create(Integer.valueOf($$0.getServer().getPort()));
/*    */     }
/* 54 */     if (!$$0.getServer().publishServer($$3, $$2, $$1)) {
/* 55 */       throw ERROR_FAILED.create();
/*    */     }
/* 57 */     $$0.sendSuccess(() -> getSuccessMessage($$0), true);
/* 58 */     return $$1;
/*    */   }
/*    */   
/*    */   public static MutableComponent getSuccessMessage(int $$0) {
/* 62 */     MutableComponent mutableComponent = ComponentUtils.copyOnClickText(String.valueOf($$0));
/* 63 */     return Component.translatable("commands.publish.started", new Object[] { mutableComponent });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\PublishCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */