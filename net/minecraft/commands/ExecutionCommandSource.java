/*    */ package net.minecraft.commands;
/*    */ 
/*    */ import com.mojang.brigadier.CommandDispatcher;
/*    */ import com.mojang.brigadier.Message;
/*    */ import com.mojang.brigadier.ResultConsumer;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandExceptionType;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.commands.execution.TraceCallbacks;
/*    */ 
/*    */ public interface ExecutionCommandSource<T extends ExecutionCommandSource<T>> {
/*    */   boolean hasPermission(int paramInt);
/*    */   
/*    */   T withCallback(CommandResultCallback paramCommandResultCallback);
/*    */   
/*    */   CommandResultCallback callback();
/*    */   
/*    */   default T clearCallbacks() {
/* 20 */     return withCallback(CommandResultCallback.EMPTY);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   CommandDispatcher<T> dispatcher();
/*    */ 
/*    */   
/*    */   void handleError(CommandExceptionType paramCommandExceptionType, Message paramMessage, boolean paramBoolean, @Nullable TraceCallbacks paramTraceCallbacks);
/*    */ 
/*    */   
/*    */   boolean isSilent();
/*    */ 
/*    */   
/*    */   default void handleError(CommandSyntaxException $$0, boolean $$1, @Nullable TraceCallbacks $$2) {
/* 35 */     handleError($$0.getType(), $$0.getRawMessage(), $$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   static <T extends ExecutionCommandSource<T>> ResultConsumer<T> resultConsumer() {
/* 40 */     return ($$0, $$1, $$2) -> ((ExecutionCommandSource)$$0.getSource()).callback().onResult($$1, $$2);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\ExecutionCommandSource.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */