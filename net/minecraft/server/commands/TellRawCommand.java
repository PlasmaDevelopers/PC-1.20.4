/*    */ package net.minecraft.server.commands;
/*    */ import com.mojang.brigadier.CommandDispatcher;
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.Commands;
/*    */ import net.minecraft.commands.arguments.ComponentArgument;
/*    */ import net.minecraft.commands.arguments.EntityArgument;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.ComponentUtils;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ 
/*    */ public class TellRawCommand {
/*    */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/* 18 */     $$0.register(
/* 19 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("tellraw")
/* 20 */         .requires($$0 -> $$0.hasPermission(2)))
/* 21 */         .then(
/* 22 */           Commands.argument("targets", (ArgumentType)EntityArgument.players())
/* 23 */           .then(
/* 24 */             Commands.argument("message", (ArgumentType)ComponentArgument.textComponent())
/* 25 */             .executes($$0 -> {
/*    */                 int $$1 = 0;
/*    */                 for (ServerPlayer $$2 : EntityArgument.getPlayers($$0, "targets")) {
/*    */                   $$2.sendSystemMessage((Component)ComponentUtils.updateForEntity((CommandSourceStack)$$0.getSource(), ComponentArgument.getComponent($$0, "message"), (Entity)$$2, 0), false);
/*    */                   $$1++;
/*    */                 } 
/*    */                 return $$1;
/*    */               }))));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\TellRawCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */