/*     */ package net.minecraft.server.commands;
/*     */ 
/*     */ import com.mojang.brigadier.CommandDispatcher;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.context.ContextChain;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.UncheckedIOException;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import java.util.Collection;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.commands.CommandResultCallback;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.ExecutionCommandSource;
/*     */ import net.minecraft.commands.FunctionInstantiationException;
/*     */ import net.minecraft.commands.arguments.item.FunctionArgument;
/*     */ import net.minecraft.commands.execution.ChainModifiers;
/*     */ import net.minecraft.commands.execution.CustomCommandExecutor;
/*     */ import net.minecraft.commands.execution.ExecutionContext;
/*     */ import net.minecraft.commands.execution.ExecutionControl;
/*     */ import net.minecraft.commands.execution.Frame;
/*     */ import net.minecraft.commands.execution.tasks.CallFunction;
/*     */ import net.minecraft.commands.functions.CommandFunction;
/*     */ import net.minecraft.commands.functions.InstantiatedFunction;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class TraceCustomExecutor
/*     */   extends CustomCommandExecutor.WithErrorHandling<CommandSourceStack>
/*     */   implements CustomCommandExecutor.CommandAdapter<CommandSourceStack>
/*     */ {
/*     */   public void runGuarded(CommandSourceStack $$0, ContextChain<CommandSourceStack> $$1, ChainModifiers $$2, ExecutionControl<CommandSourceStack> $$3) throws CommandSyntaxException {
/*  98 */     if ($$2.isReturn()) {
/*  99 */       throw DebugCommand.NO_RETURN_RUN.create();
/*     */     }
/*     */     
/* 102 */     if ($$3.tracer() != null) {
/* 103 */       throw DebugCommand.NO_RECURSIVE_TRACES.create();
/*     */     }
/* 105 */     CommandContext<CommandSourceStack> $$4 = $$1.getTopContext();
/*     */     
/* 107 */     Collection<CommandFunction<CommandSourceStack>> $$5 = FunctionArgument.getFunctions($$4, "name");
/*     */     
/* 109 */     MinecraftServer $$6 = $$0.getServer();
/* 110 */     String $$7 = "debug-trace-" + Util.getFilenameFormattedDateTime() + ".txt";
/*     */     
/* 112 */     CommandDispatcher<CommandSourceStack> $$8 = $$0.getServer().getFunctions().getDispatcher();
/*     */     
/* 114 */     int $$9 = 0;
/*     */     try {
/* 116 */       Path $$10 = $$6.getFile("debug").toPath();
/* 117 */       Files.createDirectories($$10, (FileAttribute<?>[])new FileAttribute[0]);
/*     */       
/* 119 */       final PrintWriter output = new PrintWriter(Files.newBufferedWriter($$10.resolve($$7), StandardCharsets.UTF_8, new java.nio.file.OpenOption[0]));
/* 120 */       DebugCommand.Tracer $$12 = new DebugCommand.Tracer($$11);
/* 121 */       $$3.tracer($$12);
/*     */       
/* 123 */       for (CommandFunction<CommandSourceStack> $$13 : $$5) {
/*     */         try {
/* 125 */           CommandSourceStack $$14 = $$0.withSource($$12).withMaximumPermission(2);
/*     */           
/* 127 */           InstantiatedFunction<CommandSourceStack> $$15 = $$13.instantiate(null, $$8, $$14);
/* 128 */           $$3.queueNext((new CallFunction<CommandSourceStack>($$15, CommandResultCallback.EMPTY, false)
/*     */               {
/*     */                 public void execute(CommandSourceStack $$0, ExecutionContext<CommandSourceStack> $$1, Frame $$2) {
/* 131 */                   output.println(function.id());
/* 132 */                   super.execute((ExecutionCommandSource)$$0, $$1, $$2);
/*     */                 }
/* 134 */               }).bind($$14));
/* 135 */           $$9 += $$15.entries().size();
/* 136 */         } catch (FunctionInstantiationException $$16) {
/* 137 */           $$0.sendFailure($$16.messageComponent());
/*     */         } 
/*     */       } 
/* 140 */     } catch (UncheckedIOException|java.io.IOException $$17) {
/* 141 */       DebugCommand.LOGGER.warn("Tracing failed", $$17);
/* 142 */       $$0.sendFailure((Component)Component.translatable("commands.debug.function.traceFailed"));
/*     */     } 
/*     */     
/* 145 */     int $$18 = $$9;
/* 146 */     $$3.queueNext(($$4, $$5) -> {
/*     */           if ($$0.size() == 1) {
/*     */             $$1.sendSuccess((), true);
/*     */           } else {
/*     */             $$1.sendSuccess((), true);
/*     */           } 
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\DebugCommand$TraceCustomExecutor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */