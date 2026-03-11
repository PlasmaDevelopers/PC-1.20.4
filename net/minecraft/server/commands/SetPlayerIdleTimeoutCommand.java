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
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ public class SetPlayerIdleTimeoutCommand {
/*    */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/* 15 */     $$0.register(
/* 16 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("setidletimeout")
/* 17 */         .requires($$0 -> $$0.hasPermission(3)))
/* 18 */         .then(
/* 19 */           Commands.argument("minutes", (ArgumentType)IntegerArgumentType.integer(0))
/* 20 */           .executes($$0 -> setIdleTimeout((CommandSourceStack)$$0.getSource(), IntegerArgumentType.getInteger($$0, "minutes")))));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static int setIdleTimeout(CommandSourceStack $$0, int $$1) {
/* 26 */     $$0.getServer().setPlayerIdleTimeout($$1);
/* 27 */     $$0.sendSuccess(() -> Component.translatable("commands.setidletimeout.success", new Object[] { Integer.valueOf($$0) }), true);
/* 28 */     return $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\SetPlayerIdleTimeoutCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */