/*    */ package net.minecraft.server.commands;
/*    */ 
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.Commands;
/*    */ import net.minecraft.world.level.GameRules;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class null
/*    */   implements GameRules.GameRuleTypeVisitor
/*    */ {
/*    */   public <T extends GameRules.Value<T>> void visit(GameRules.Key<T> $$0, GameRules.Type<T> $$1) {
/* 20 */     base.then((
/* 21 */         (LiteralArgumentBuilder)Commands.literal($$0.getId())
/* 22 */         .executes($$1 -> GameRuleCommand.queryRule((CommandSourceStack)$$1.getSource(), $$0)))
/* 23 */         .then($$1
/* 24 */           .createArgument("value")
/* 25 */           .executes($$1 -> GameRuleCommand.setRule($$1, $$0))));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\GameRuleCommand$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */