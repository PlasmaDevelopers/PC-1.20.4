/*    */ package net.minecraft.server.commands;
/*    */ import com.mojang.brigadier.CommandDispatcher;
/*    */ import com.mojang.brigadier.Message;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.Commands;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.world.Difficulty;
/*    */ 
/*    */ public class DifficultyCommand {
/*    */   static {
/* 16 */     ERROR_ALREADY_DIFFICULT = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("commands.difficulty.failure", new Object[] { $$0 }));
/*    */   } private static final DynamicCommandExceptionType ERROR_ALREADY_DIFFICULT;
/*    */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/* 19 */     LiteralArgumentBuilder<CommandSourceStack> $$1 = Commands.literal("difficulty");
/*    */     
/* 21 */     for (Difficulty $$2 : Difficulty.values()) {
/* 22 */       $$1.then(Commands.literal($$2.getKey()).executes($$1 -> setDifficulty((CommandSourceStack)$$1.getSource(), $$0)));
/*    */     }
/*    */     
/* 25 */     $$0.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)$$1
/*    */         
/* 27 */         .requires($$0 -> $$0.hasPermission(2)))
/* 28 */         .executes($$0 -> {
/*    */             Difficulty $$1 = ((CommandSourceStack)$$0.getSource()).getLevel().getDifficulty();
/*    */             ((CommandSourceStack)$$0.getSource()).sendSuccess((), false);
/*    */             return $$1.getId();
/*    */           }));
/*    */   }
/*    */ 
/*    */   
/*    */   public static int setDifficulty(CommandSourceStack $$0, Difficulty $$1) throws CommandSyntaxException {
/* 37 */     MinecraftServer $$2 = $$0.getServer();
/* 38 */     if ($$2.getWorldData().getDifficulty() == $$1) {
/* 39 */       throw ERROR_ALREADY_DIFFICULT.create($$1.getKey());
/*    */     }
/*    */     
/* 42 */     $$2.setDifficulty($$1, true);
/* 43 */     $$0.sendSuccess(() -> Component.translatable("commands.difficulty.success", new Object[] { $$0.getDisplayName() }), true);
/*    */     
/* 45 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\DifficultyCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */