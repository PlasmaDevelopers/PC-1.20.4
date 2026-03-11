/*    */ package net.minecraft.server.commands;
/*    */ import com.mojang.brigadier.CommandDispatcher;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.ComponentUtils;
/*    */ import net.minecraft.network.chat.MutableComponent;
/*    */ 
/*    */ public class SeedCommand {
/*    */   public static void register(CommandDispatcher<CommandSourceStack> $$0, boolean $$1) {
/* 13 */     $$0.register(
/* 14 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("seed")
/* 15 */         .requires($$1 -> (!$$0 || $$1.hasPermission(2))))
/* 16 */         .executes($$0 -> {
/*    */             long $$1 = ((CommandSourceStack)$$0.getSource()).getLevel().getSeed();
/*    */             MutableComponent mutableComponent = ComponentUtils.copyOnClickText(String.valueOf($$1));
/*    */             ((CommandSourceStack)$$0.getSource()).sendSuccess((), false);
/*    */             return (int)$$1;
/*    */           }));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\SeedCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */