/*     */ package net.minecraft.commands.execution.tasks;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.mojang.brigadier.Command;
/*     */ import com.mojang.brigadier.Message;
/*     */ import com.mojang.brigadier.RedirectModifier;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.context.ContextChain;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import net.minecraft.commands.CommandResultCallback;
/*     */ import net.minecraft.commands.ExecutionCommandSource;
/*     */ import net.minecraft.commands.execution.ChainModifiers;
/*     */ import net.minecraft.commands.execution.CommandQueueEntry;
/*     */ import net.minecraft.commands.execution.CustomCommandExecutor;
/*     */ import net.minecraft.commands.execution.CustomModifierExecutor;
/*     */ import net.minecraft.commands.execution.EntryAction;
/*     */ import net.minecraft.commands.execution.ExecutionContext;
/*     */ import net.minecraft.commands.execution.ExecutionControl;
/*     */ import net.minecraft.commands.execution.Frame;
/*     */ import net.minecraft.commands.execution.TraceCallbacks;
/*     */ import net.minecraft.commands.execution.UnboundEntryAction;
/*     */ import net.minecraft.network.chat.Component;
/*     */ 
/*     */ public class BuildContexts<T extends ExecutionCommandSource<T>> {
/*     */   static {
/*  30 */     ERROR_FORK_LIMIT_REACHED = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("command.forkLimit", new Object[] { $$0 }));
/*     */   }
/*     */   @VisibleForTesting
/*     */   public static final DynamicCommandExceptionType ERROR_FORK_LIMIT_REACHED;
/*     */   
/*     */   public BuildContexts(String $$0, ContextChain<T> $$1) {
/*  36 */     this.commandInput = $$0;
/*  37 */     this.command = $$1;
/*     */   }
/*     */   
/*     */   private final String commandInput;
/*     */   private final ContextChain<T> command;
/*     */   
/*     */   protected void execute(T $$0, List<T> $$1, ExecutionContext<T> $$2, Frame $$3, ChainModifiers $$4) {
/*     */     ObjectArrayList<T> objectArrayList;
/*  45 */     ContextChain<T> $$5 = this.command;
/*     */     
/*  47 */     ChainModifiers $$6 = $$4;
/*  48 */     List<T> $$7 = $$1;
/*     */     
/*  50 */     if ($$5.getStage() != ContextChain.Stage.EXECUTE) {
/*  51 */       $$2.profiler().push(() -> "prepare " + this.commandInput);
/*     */       try {
/*  53 */         int $$8 = $$2.forkLimit();
/*  54 */         while ($$5.getStage() != ContextChain.Stage.EXECUTE) {
/*  55 */           CommandContext<T> $$9 = $$5.getTopContext();
/*  56 */           if ($$9.isForked()) {
/*  57 */             $$6 = $$6.setForked();
/*     */           }
/*     */           
/*  60 */           RedirectModifier<T> $$10 = $$9.getRedirectModifier();
/*  61 */           if ($$10 instanceof CustomModifierExecutor) {
/*  62 */             CustomModifierExecutor<T> $$11 = (CustomModifierExecutor<T>)$$10;
/*  63 */             $$11.apply($$0, $$7, $$5, $$6, ExecutionControl.create($$2, $$3));
/*     */             
/*     */             return;
/*     */           } 
/*     */           
/*  68 */           if ($$10 != null) {
/*     */ 
/*     */             
/*  71 */             $$2.incrementCost();
/*     */             
/*  73 */             boolean $$12 = $$6.isForked();
/*  74 */             ObjectArrayList<T> objectArrayList1 = new ObjectArrayList();
/*  75 */             for (ExecutionCommandSource executionCommandSource : $$7) {
/*     */               
/*     */               try {
/*  78 */                 Collection<T> $$15 = ContextChain.runModifier($$9, executionCommandSource, ($$0, $$1, $$2) -> {  }$$12);
/*  79 */                 if (objectArrayList1.size() + $$15.size() >= $$8) {
/*  80 */                   $$0.handleError(ERROR_FORK_LIMIT_REACHED.create(Integer.valueOf($$8)), $$12, $$2.tracer());
/*     */                   return;
/*     */                 } 
/*  83 */                 objectArrayList1.addAll($$15);
/*  84 */               } catch (CommandSyntaxException $$16) {
/*  85 */                 executionCommandSource.handleError($$16, $$12, $$2.tracer());
/*  86 */                 if (!$$12) {
/*     */                   return;
/*     */                 }
/*     */               } 
/*     */             } 
/*     */             
/*  92 */             objectArrayList = objectArrayList1;
/*     */           } 
/*  94 */           $$5 = $$5.nextStage();
/*     */         } 
/*     */       } finally {
/*  97 */         $$2.profiler().pop();
/*     */       } 
/*     */     } 
/*     */     
/* 101 */     if (objectArrayList.isEmpty()) {
/* 102 */       if ($$6.isReturn()) {
/* 103 */         $$2.queueNext(new CommandQueueEntry($$3, FallthroughTask.instance()));
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/* 108 */     CommandContext<T> $$17 = $$5.getTopContext();
/* 109 */     Command<T> $$18 = $$17.getCommand();
/* 110 */     if ($$18 instanceof CustomCommandExecutor) {
/* 111 */       CustomCommandExecutor<T> $$19 = (CustomCommandExecutor<T>)$$18;
/* 112 */       ExecutionControl<T> $$20 = ExecutionControl.create($$2, $$3);
/* 113 */       for (ExecutionCommandSource executionCommandSource : objectArrayList)
/* 114 */         $$19.run(executionCommandSource, $$5, $$6, $$20); 
/*     */     } else {
/*     */       List<ExecutionCommandSource> list;
/* 117 */       if ($$6.isReturn()) {
/*     */         
/* 119 */         ExecutionCommandSource executionCommandSource = (ExecutionCommandSource)objectArrayList.get(0);
/*     */         
/* 121 */         executionCommandSource = executionCommandSource.withCallback(CommandResultCallback.chain(executionCommandSource
/* 122 */               .callback(), $$3
/* 123 */               .returnValueConsumer()));
/*     */         
/* 125 */         list = List.of(executionCommandSource);
/*     */       } 
/*     */ 
/*     */       
/* 129 */       ExecuteCommand<T> $$23 = new ExecuteCommand<>(this.commandInput, $$6, $$17);
/* 130 */       ContinuationTask.schedule($$2, $$3, list, ($$1, $$2) -> new CommandQueueEntry($$1, $$0.bind($$2)));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void traceCommandStart(ExecutionContext<T> $$0, Frame $$1) {
/* 137 */     TraceCallbacks $$2 = $$0.tracer();
/* 138 */     if ($$2 != null) {
/* 139 */       $$2.onCommand($$1.depth(), this.commandInput);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 145 */     return this.commandInput;
/*     */   }
/*     */   
/*     */   public static class Unbound<T extends ExecutionCommandSource<T>> extends BuildContexts<T> implements UnboundEntryAction<T> {
/*     */     public Unbound(String $$0, ContextChain<T> $$1) {
/* 150 */       super($$0, $$1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void execute(T $$0, ExecutionContext<T> $$1, Frame $$2) {
/* 155 */       traceCommandStart($$1, $$2);
/* 156 */       execute($$0, List.of($$0), $$1, $$2, ChainModifiers.DEFAULT);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Continuation<T extends ExecutionCommandSource<T>> extends BuildContexts<T> implements EntryAction<T> {
/*     */     private final ChainModifiers modifiers;
/*     */     private final T originalSource;
/*     */     private final List<T> sources;
/*     */     
/*     */     public Continuation(String $$0, ContextChain<T> $$1, ChainModifiers $$2, T $$3, List<T> $$4) {
/* 166 */       super($$0, $$1);
/* 167 */       this.originalSource = $$3;
/* 168 */       this.sources = $$4;
/* 169 */       this.modifiers = $$2;
/*     */     }
/*     */ 
/*     */     
/*     */     public void execute(ExecutionContext<T> $$0, Frame $$1) {
/* 174 */       execute(this.originalSource, this.sources, $$0, $$1, this.modifiers);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class TopLevel<T extends ExecutionCommandSource<T>> extends BuildContexts<T> implements EntryAction<T> {
/*     */     private final T source;
/*     */     
/*     */     public TopLevel(String $$0, ContextChain<T> $$1, T $$2) {
/* 182 */       super($$0, $$1);
/* 183 */       this.source = $$2;
/*     */     }
/*     */ 
/*     */     
/*     */     public void execute(ExecutionContext<T> $$0, Frame $$1) {
/* 188 */       traceCommandStart($$0, $$1);
/* 189 */       execute(this.source, List.of(this.source), $$0, $$1, ChainModifiers.DEFAULT);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\execution\tasks\BuildContexts.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */