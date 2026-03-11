/*    */ package net.minecraft.server.commands;
/*    */ import com.mojang.brigadier.CommandDispatcher;
/*    */ import com.mojang.brigadier.Message;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.Commands;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ 
/*    */ public class SaveAllCommand {
/* 14 */   private static final SimpleCommandExceptionType ERROR_FAILED = new SimpleCommandExceptionType((Message)Component.translatable("commands.save.failed"));
/*    */   
/*    */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/* 17 */     $$0.register(
/* 18 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("save-all")
/* 19 */         .requires($$0 -> $$0.hasPermission(4)))
/* 20 */         .executes($$0 -> saveAll((CommandSourceStack)$$0.getSource(), false)))
/* 21 */         .then(
/* 22 */           Commands.literal("flush")
/* 23 */           .executes($$0 -> saveAll((CommandSourceStack)$$0.getSource(), true))));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static int saveAll(CommandSourceStack $$0, boolean $$1) throws CommandSyntaxException {
/* 29 */     $$0.sendSuccess(() -> Component.translatable("commands.save.saving"), false);
/*    */     
/* 31 */     MinecraftServer $$2 = $$0.getServer();
/* 32 */     boolean $$3 = $$2.saveEverything(true, $$1, true);
/*    */     
/* 34 */     if (!$$3) {
/* 35 */       throw ERROR_FAILED.create();
/*    */     }
/*    */     
/* 38 */     $$0.sendSuccess(() -> Component.translatable("commands.save.success"), true);
/*    */     
/* 40 */     return 1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\SaveAllCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */