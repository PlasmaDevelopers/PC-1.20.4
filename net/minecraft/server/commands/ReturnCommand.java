/*    */ package net.minecraft.server.commands;
/*    */ 
/*    */ import com.mojang.brigadier.Command;
/*    */ import com.mojang.brigadier.CommandDispatcher;
/*    */ import com.mojang.brigadier.RedirectModifier;
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.arguments.IntegerArgumentType;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.builder.RequiredArgumentBuilder;
/*    */ import com.mojang.brigadier.context.ContextChain;
/*    */ import com.mojang.brigadier.tree.CommandNode;
/*    */ import java.util.List;
/*    */ import net.minecraft.commands.ExecutionCommandSource;
/*    */ import net.minecraft.commands.execution.ChainModifiers;
/*    */ import net.minecraft.commands.execution.CustomCommandExecutor;
/*    */ import net.minecraft.commands.execution.CustomModifierExecutor;
/*    */ import net.minecraft.commands.execution.EntryAction;
/*    */ import net.minecraft.commands.execution.ExecutionControl;
/*    */ import net.minecraft.commands.execution.Frame;
/*    */ import net.minecraft.commands.execution.tasks.BuildContexts;
/*    */ import net.minecraft.commands.execution.tasks.FallthroughTask;
/*    */ 
/*    */ public class ReturnCommand {
/*    */   public static <T extends ExecutionCommandSource<T>> void register(CommandDispatcher<T> $$0) {
/* 25 */     $$0.register(
/* 26 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)LiteralArgumentBuilder.literal("return")
/* 27 */         .requires($$0 -> $$0.hasPermission(2)))
/* 28 */         .then(
/* 29 */           RequiredArgumentBuilder.argument("value", (ArgumentType)IntegerArgumentType.integer())
/* 30 */           .executes((Command)new ReturnValueCustomExecutor<>())))
/*    */         
/* 32 */         .then(
/* 33 */           LiteralArgumentBuilder.literal("fail")
/* 34 */           .executes((Command)new ReturnFailCustomExecutor<>())))
/*    */         
/* 36 */         .then(
/* 37 */           LiteralArgumentBuilder.literal("run")
/* 38 */           .forward((CommandNode)$$0.getRoot(), (RedirectModifier)new ReturnFromCommandCustomModifier<>(), false)));
/*    */   }
/*    */ 
/*    */   
/*    */   private static class ReturnValueCustomExecutor<T extends ExecutionCommandSource<T>>
/*    */     implements CustomCommandExecutor.CommandAdapter<T>
/*    */   {
/*    */     public void run(T $$0, ContextChain<T> $$1, ChainModifiers $$2, ExecutionControl<T> $$3) {
/* 46 */       int $$4 = IntegerArgumentType.getInteger($$1.getTopContext(), "value");
/* 47 */       $$0.callback().onSuccess($$4);
/* 48 */       Frame $$5 = $$3.currentFrame();
/* 49 */       $$5.returnSuccess($$4);
/* 50 */       $$5.discard();
/*    */     }
/*    */   }
/*    */   
/*    */   private static class ReturnFailCustomExecutor<T extends ExecutionCommandSource<T>>
/*    */     implements CustomCommandExecutor.CommandAdapter<T> {
/*    */     public void run(T $$0, ContextChain<T> $$1, ChainModifiers $$2, ExecutionControl<T> $$3) {
/* 57 */       $$0.callback().onFailure();
/* 58 */       Frame $$4 = $$3.currentFrame();
/* 59 */       $$4.returnFailure();
/* 60 */       $$4.discard();
/*    */     }
/*    */   }
/*    */   
/*    */   private static class ReturnFromCommandCustomModifier<T extends ExecutionCommandSource<T>>
/*    */     implements CustomModifierExecutor.ModifierAdapter<T> {
/*    */     public void apply(T $$0, List<T> $$1, ContextChain<T> $$2, ChainModifiers $$3, ExecutionControl<T> $$4) {
/* 67 */       if ($$1.isEmpty()) {
/*    */ 
/*    */ 
/*    */         
/* 71 */         if ($$3.isReturn()) {
/* 72 */           $$4.queueNext(FallthroughTask.instance());
/*    */         }
/*    */         
/*    */         return;
/*    */       } 
/*    */       
/* 78 */       $$4.currentFrame().discard();
/*    */       
/* 80 */       ContextChain<T> $$5 = $$2.nextStage();
/* 81 */       String $$6 = $$5.getTopContext().getInput();
/*    */       
/* 83 */       $$4.queueNext((EntryAction)new BuildContexts.Continuation($$6, $$5, $$3.setReturn(), (ExecutionCommandSource)$$0, $$1));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\ReturnCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */