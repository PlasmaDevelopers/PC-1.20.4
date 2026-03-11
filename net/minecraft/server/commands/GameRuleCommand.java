/*    */ package net.minecraft.server.commands;
/*    */ 
/*    */ import com.mojang.brigadier.CommandDispatcher;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.Commands;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.world.level.GameRules;
/*    */ 
/*    */ public class GameRuleCommand
/*    */ {
/*    */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/* 15 */     final LiteralArgumentBuilder<CommandSourceStack> base = (LiteralArgumentBuilder<CommandSourceStack>)Commands.literal("gamerule").requires($$0 -> $$0.hasPermission(2));
/*    */     
/* 17 */     GameRules.visitGameRuleTypes(new GameRules.GameRuleTypeVisitor()
/*    */         {
/*    */           public <T extends GameRules.Value<T>> void visit(GameRules.Key<T> $$0, GameRules.Type<T> $$1) {
/* 20 */             base.then((
/* 21 */                 (LiteralArgumentBuilder)Commands.literal($$0.getId())
/* 22 */                 .executes($$1 -> GameRuleCommand.queryRule((CommandSourceStack)$$1.getSource(), $$0)))
/* 23 */                 .then($$1
/* 24 */                   .createArgument("value")
/* 25 */                   .executes($$1 -> GameRuleCommand.setRule($$1, $$0))));
/*    */           }
/*    */         });
/*    */ 
/*    */ 
/*    */     
/* 31 */     $$0.register($$1);
/*    */   }
/*    */   
/*    */   static <T extends GameRules.Value<T>> int setRule(CommandContext<CommandSourceStack> $$0, GameRules.Key<T> $$1) {
/* 35 */     CommandSourceStack $$2 = (CommandSourceStack)$$0.getSource();
/* 36 */     GameRules.Value value = $$2.getServer().getGameRules().getRule($$1);
/* 37 */     value.setFromArgument($$0, "value");
/* 38 */     $$2.sendSuccess(() -> Component.translatable("commands.gamerule.set", new Object[] { $$0.getId(), $$1.toString() }), true);
/* 39 */     return value.getCommandResult();
/*    */   }
/*    */   
/*    */   static <T extends GameRules.Value<T>> int queryRule(CommandSourceStack $$0, GameRules.Key<T> $$1) {
/* 43 */     GameRules.Value value = $$0.getServer().getGameRules().getRule($$1);
/* 44 */     $$0.sendSuccess(() -> Component.translatable("commands.gamerule.query", new Object[] { $$0.getId(), $$1.toString() }), false);
/* 45 */     return value.getCommandResult();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\GameRuleCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */