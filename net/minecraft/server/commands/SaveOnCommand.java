/*    */ package net.minecraft.server.commands;
/*    */ import com.mojang.brigadier.CommandDispatcher;
/*    */ import com.mojang.brigadier.Message;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ 
/*    */ public class SaveOnCommand {
/* 13 */   private static final SimpleCommandExceptionType ERROR_ALREADY_ON = new SimpleCommandExceptionType((Message)Component.translatable("commands.save.alreadyOn"));
/*    */   
/*    */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/* 16 */     $$0.register(
/* 17 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("save-on")
/* 18 */         .requires($$0 -> $$0.hasPermission(4)))
/* 19 */         .executes($$0 -> {
/*    */             CommandSourceStack $$1 = (CommandSourceStack)$$0.getSource();
/*    */             boolean $$2 = false;
/*    */             for (ServerLevel $$3 : $$1.getServer().getAllLevels()) {
/*    */               if ($$3 != null && $$3.noSave) {
/*    */                 $$3.noSave = false;
/*    */                 $$2 = true;
/*    */               } 
/*    */             } 
/*    */             if (!$$2)
/*    */               throw ERROR_ALREADY_ON.create(); 
/*    */             $$1.sendSuccess((), true);
/*    */             return 1;
/*    */           }));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\SaveOnCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */