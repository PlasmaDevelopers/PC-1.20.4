/*    */ package net.minecraft.server.commands;
/*    */ import com.mojang.brigadier.CommandDispatcher;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.Commands;
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ public class StopCommand {
/*    */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/* 12 */     $$0.register(
/* 13 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("stop")
/* 14 */         .requires($$0 -> $$0.hasPermission(4)))
/* 15 */         .executes($$0 -> {
/*    */             ((CommandSourceStack)$$0.getSource()).sendSuccess((), true);
/*    */             ((CommandSourceStack)$$0.getSource()).getServer().halt(false);
/*    */             return 1;
/*    */           }));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\StopCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */