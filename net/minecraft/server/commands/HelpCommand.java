/*    */ package net.minecraft.server.commands;
/*    */ import com.google.common.collect.Iterables;
/*    */ import com.mojang.brigadier.CommandDispatcher;
/*    */ import com.mojang.brigadier.Message;
/*    */ import com.mojang.brigadier.ParseResults;
/*    */ import com.mojang.brigadier.arguments.StringArgumentType;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.context.ParsedCommandNode;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*    */ import com.mojang.brigadier.tree.CommandNode;
/*    */ import java.util.Map;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.Commands;
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ public class HelpCommand {
/* 19 */   private static final SimpleCommandExceptionType ERROR_FAILED = new SimpleCommandExceptionType((Message)Component.translatable("commands.help.failed"));
/*    */   
/*    */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/* 22 */     $$0.register(
/* 23 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("help")
/* 24 */         .executes($$1 -> {
/*    */             Map<CommandNode<CommandSourceStack>, String> $$2 = $$0.getSmartUsage((CommandNode)$$0.getRoot(), $$1.getSource());
/*    */             
/*    */             for (String $$3 : $$2.values()) {
/*    */               ((CommandSourceStack)$$1.getSource()).sendSuccess((), false);
/*    */             }
/*    */             return $$2.size();
/* 31 */           })).then(
/* 32 */           Commands.argument("command", (ArgumentType)StringArgumentType.greedyString())
/* 33 */           .executes($$1 -> {
/*    */               ParseResults<CommandSourceStack> $$2 = $$0.parse(StringArgumentType.getString($$1, "command"), $$1.getSource());
/*    */               if ($$2.getContext().getNodes().isEmpty())
/*    */                 throw ERROR_FAILED.create(); 
/*    */               Map<CommandNode<CommandSourceStack>, String> $$3 = $$0.getSmartUsage(((ParsedCommandNode)Iterables.getLast($$2.getContext().getNodes())).getNode(), $$1.getSource());
/*    */               for (String $$4 : $$3.values())
/*    */                 ((CommandSourceStack)$$1.getSource()).sendSuccess((), false); 
/*    */               return $$3.size();
/*    */             })));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\HelpCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */