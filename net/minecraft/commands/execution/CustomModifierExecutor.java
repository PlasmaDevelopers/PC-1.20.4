/*    */ package net.minecraft.commands.execution;
/*    */ 
/*    */ import com.mojang.brigadier.RedirectModifier;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.context.ContextChain;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ 
/*    */ public interface CustomModifierExecutor<T>
/*    */ {
/*    */   void apply(T paramT, List<T> paramList, ContextChain<T> paramContextChain, ChainModifiers paramChainModifiers, ExecutionControl<T> paramExecutionControl);
/*    */   
/*    */   public static interface ModifierAdapter<T>
/*    */     extends RedirectModifier<T>, CustomModifierExecutor<T> {
/*    */     default Collection<T> apply(CommandContext<T> $$0) throws CommandSyntaxException {
/* 17 */       throw new UnsupportedOperationException("This function should not run");
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\execution\CustomModifierExecutor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */