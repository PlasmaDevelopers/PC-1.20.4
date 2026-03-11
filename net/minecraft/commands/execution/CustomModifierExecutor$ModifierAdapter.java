/*    */ package net.minecraft.commands.execution;
/*    */ 
/*    */ import com.mojang.brigadier.RedirectModifier;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import java.util.Collection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface ModifierAdapter<T>
/*    */   extends RedirectModifier<T>, CustomModifierExecutor<T>
/*    */ {
/*    */   default Collection<T> apply(CommandContext<T> $$0) throws CommandSyntaxException {
/* 17 */     throw new UnsupportedOperationException("This function should not run");
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\execution\CustomModifierExecutor$ModifierAdapter.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */