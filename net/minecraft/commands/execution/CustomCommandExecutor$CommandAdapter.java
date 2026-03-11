/*    */ package net.minecraft.commands.execution;
/*    */ 
/*    */ import com.mojang.brigadier.Command;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface CommandAdapter<T>
/*    */   extends Command<T>, CustomCommandExecutor<T>
/*    */ {
/*    */   default int run(CommandContext<T> $$0) throws CommandSyntaxException {
/* 17 */     throw new UnsupportedOperationException("This function should not run");
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\execution\CustomCommandExecutor$CommandAdapter.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */