/*     */ package net.minecraft.server.commands;
/*     */ 
/*     */ import com.mojang.brigadier.Command;
/*     */ import com.mojang.brigadier.CommandDispatcher;
/*     */ import com.mojang.brigadier.Message;
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.context.ContextChain;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.UncheckedIOException;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import java.util.Collection;
/*     */ import java.util.Locale;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.commands.CommandResultCallback;
/*     */ import net.minecraft.commands.CommandSource;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.Commands;
/*     */ import net.minecraft.commands.ExecutionCommandSource;
/*     */ import net.minecraft.commands.FunctionInstantiationException;
/*     */ import net.minecraft.commands.arguments.item.FunctionArgument;
/*     */ import net.minecraft.commands.execution.ChainModifiers;
/*     */ import net.minecraft.commands.execution.CustomCommandExecutor;
/*     */ import net.minecraft.commands.execution.ExecutionContext;
/*     */ import net.minecraft.commands.execution.ExecutionControl;
/*     */ import net.minecraft.commands.execution.Frame;
/*     */ import net.minecraft.commands.execution.TraceCallbacks;
/*     */ import net.minecraft.commands.execution.tasks.CallFunction;
/*     */ import net.minecraft.commands.functions.CommandFunction;
/*     */ import net.minecraft.commands.functions.InstantiatedFunction;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.TimeUtil;
/*     */ import net.minecraft.util.profiling.ProfileResults;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class DebugCommand {
/*  47 */   static final Logger LOGGER = LogUtils.getLogger();
/*  48 */   private static final SimpleCommandExceptionType ERROR_NOT_RUNNING = new SimpleCommandExceptionType((Message)Component.translatable("commands.debug.notRunning"));
/*  49 */   private static final SimpleCommandExceptionType ERROR_ALREADY_RUNNING = new SimpleCommandExceptionType((Message)Component.translatable("commands.debug.alreadyRunning"));
/*     */   
/*  51 */   static final SimpleCommandExceptionType NO_RECURSIVE_TRACES = new SimpleCommandExceptionType((Message)Component.translatable("commands.debug.function.noRecursion"));
/*  52 */   static final SimpleCommandExceptionType NO_RETURN_RUN = new SimpleCommandExceptionType((Message)Component.translatable("commands.debug.function.noReturnRun"));
/*     */   
/*     */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/*  55 */     $$0.register(
/*  56 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("debug")
/*  57 */         .requires($$0 -> $$0.hasPermission(3)))
/*  58 */         .then(Commands.literal("start").executes($$0 -> start((CommandSourceStack)$$0.getSource()))))
/*  59 */         .then(Commands.literal("stop").executes($$0 -> stop((CommandSourceStack)$$0.getSource()))))
/*  60 */         .then((
/*  61 */           (LiteralArgumentBuilder)Commands.literal("function").requires($$0 -> $$0.hasPermission(3)))
/*  62 */           .then(
/*  63 */             Commands.argument("name", (ArgumentType)FunctionArgument.functions())
/*  64 */             .suggests(FunctionCommand.SUGGEST_FUNCTION)
/*  65 */             .executes((Command)new TraceCustomExecutor()))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int start(CommandSourceStack $$0) throws CommandSyntaxException {
/*  72 */     MinecraftServer $$1 = $$0.getServer();
/*  73 */     if ($$1.isTimeProfilerRunning()) {
/*  74 */       throw ERROR_ALREADY_RUNNING.create();
/*     */     }
/*  76 */     $$1.startTimeProfiler();
/*  77 */     $$0.sendSuccess(() -> Component.translatable("commands.debug.started"), true);
/*  78 */     return 0;
/*     */   }
/*     */   
/*     */   private static int stop(CommandSourceStack $$0) throws CommandSyntaxException {
/*  82 */     MinecraftServer $$1 = $$0.getServer();
/*  83 */     if (!$$1.isTimeProfilerRunning()) {
/*  84 */       throw ERROR_NOT_RUNNING.create();
/*     */     }
/*  86 */     ProfileResults $$2 = $$1.stopTimeProfiler();
/*     */     
/*  88 */     double $$3 = $$2.getNanoDuration() / TimeUtil.NANOSECONDS_PER_SECOND;
/*  89 */     double $$4 = $$2.getTickDuration() / $$3;
/*  90 */     $$0.sendSuccess(() -> Component.translatable("commands.debug.stopped", new Object[] { String.format(Locale.ROOT, "%.2f", new Object[] { Double.valueOf($$0) }), Integer.valueOf($$1.getTickDuration()), String.format(Locale.ROOT, "%.2f", new Object[] { Double.valueOf($$2) }) }), true);
/*     */     
/*  92 */     return (int)$$4;
/*     */   }
/*     */   
/*     */   private static class TraceCustomExecutor
/*     */     extends CustomCommandExecutor.WithErrorHandling<CommandSourceStack> implements CustomCommandExecutor.CommandAdapter<CommandSourceStack> {
/*     */     public void runGuarded(CommandSourceStack $$0, ContextChain<CommandSourceStack> $$1, ChainModifiers $$2, ExecutionControl<CommandSourceStack> $$3) throws CommandSyntaxException {
/*  98 */       if ($$2.isReturn()) {
/*  99 */         throw DebugCommand.NO_RETURN_RUN.create();
/*     */       }
/*     */       
/* 102 */       if ($$3.tracer() != null) {
/* 103 */         throw DebugCommand.NO_RECURSIVE_TRACES.create();
/*     */       }
/* 105 */       CommandContext<CommandSourceStack> $$4 = $$1.getTopContext();
/*     */       
/* 107 */       Collection<CommandFunction<CommandSourceStack>> $$5 = FunctionArgument.getFunctions($$4, "name");
/*     */       
/* 109 */       MinecraftServer $$6 = $$0.getServer();
/* 110 */       String $$7 = "debug-trace-" + Util.getFilenameFormattedDateTime() + ".txt";
/*     */       
/* 112 */       CommandDispatcher<CommandSourceStack> $$8 = $$0.getServer().getFunctions().getDispatcher();
/*     */       
/* 114 */       int $$9 = 0;
/*     */       try {
/* 116 */         Path $$10 = $$6.getFile("debug").toPath();
/* 117 */         Files.createDirectories($$10, (FileAttribute<?>[])new FileAttribute[0]);
/*     */         
/* 119 */         final PrintWriter output = new PrintWriter(Files.newBufferedWriter($$10.resolve($$7), StandardCharsets.UTF_8, new java.nio.file.OpenOption[0]));
/* 120 */         DebugCommand.Tracer $$12 = new DebugCommand.Tracer($$11);
/* 121 */         $$3.tracer($$12);
/*     */         
/* 123 */         for (CommandFunction<CommandSourceStack> $$13 : $$5) {
/*     */           try {
/* 125 */             CommandSourceStack $$14 = $$0.withSource($$12).withMaximumPermission(2);
/*     */             
/* 127 */             InstantiatedFunction<CommandSourceStack> $$15 = $$13.instantiate(null, $$8, $$14);
/* 128 */             $$3.queueNext((new CallFunction<CommandSourceStack>($$15, CommandResultCallback.EMPTY, false)
/*     */                 {
/*     */                   public void execute(CommandSourceStack $$0, ExecutionContext<CommandSourceStack> $$1, Frame $$2) {
/* 131 */                     output.println(function.id());
/* 132 */                     super.execute((ExecutionCommandSource)$$0, $$1, $$2);
/*     */                   }
/* 134 */                 }).bind($$14));
/* 135 */             $$9 += $$15.entries().size();
/* 136 */           } catch (FunctionInstantiationException $$16) {
/* 137 */             $$0.sendFailure($$16.messageComponent());
/*     */           } 
/*     */         } 
/* 140 */       } catch (UncheckedIOException|java.io.IOException $$17) {
/* 141 */         DebugCommand.LOGGER.warn("Tracing failed", $$17);
/* 142 */         $$0.sendFailure((Component)Component.translatable("commands.debug.function.traceFailed"));
/*     */       } 
/*     */       
/* 145 */       int $$18 = $$9;
/* 146 */       $$3.queueNext(($$4, $$5) -> {
/*     */             if ($$0.size() == 1) {
/*     */               $$1.sendSuccess((), true);
/*     */             } else {
/*     */               $$1.sendSuccess((), true);
/*     */             } 
/*     */           });
/*     */     }
/*     */   } class null extends CallFunction<CommandSourceStack> { null(InstantiatedFunction<CommandSourceStack> $$1, CommandResultCallback $$2, boolean $$3) {
/*     */       super($$1, $$2, $$3);
/*     */     }
/*     */     public void execute(CommandSourceStack $$0, ExecutionContext<CommandSourceStack> $$1, Frame $$2) {
/*     */       output.println(function.id());
/*     */       super.execute((ExecutionCommandSource)$$0, $$1, $$2);
/*     */     } }
/*     */   private static class Tracer implements CommandSource, TraceCallbacks { public static final int INDENT_OFFSET = 1; private final PrintWriter output;
/*     */     Tracer(PrintWriter $$0) {
/* 163 */       this.output = $$0;
/*     */     }
/*     */     private int lastIndent; private boolean waitingForResult;
/*     */     private void indentAndSave(int $$0) {
/* 167 */       printIndent($$0);
/* 168 */       this.lastIndent = $$0;
/*     */     }
/*     */     
/*     */     private void printIndent(int $$0) {
/* 172 */       for (int $$1 = 0; $$1 < $$0 + 1; $$1++) {
/* 173 */         this.output.write("    ");
/*     */       }
/*     */     }
/*     */     
/*     */     private void newLine() {
/* 178 */       if (this.waitingForResult) {
/* 179 */         this.output.println();
/* 180 */         this.waitingForResult = false;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void onCommand(int $$0, String $$1) {
/* 186 */       newLine();
/* 187 */       indentAndSave($$0);
/* 188 */       this.output.print("[C] ");
/* 189 */       this.output.print($$1);
/* 190 */       this.waitingForResult = true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void onReturn(int $$0, String $$1, int $$2) {
/* 195 */       if (this.waitingForResult) {
/* 196 */         this.output.print(" -> ");
/* 197 */         this.output.println($$2);
/* 198 */         this.waitingForResult = false;
/*     */       } else {
/* 200 */         indentAndSave($$0);
/* 201 */         this.output.print("[R = ");
/* 202 */         this.output.print($$2);
/* 203 */         this.output.print("] ");
/* 204 */         this.output.println($$1);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void onCall(int $$0, ResourceLocation $$1, int $$2) {
/* 210 */       newLine();
/* 211 */       indentAndSave($$0);
/* 212 */       this.output.print("[F] ");
/* 213 */       this.output.print($$1);
/* 214 */       this.output.print(" size=");
/* 215 */       this.output.println($$2);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onError(String $$0) {
/* 220 */       newLine();
/* 221 */       indentAndSave(this.lastIndent + 1);
/* 222 */       this.output.print("[E] ");
/* 223 */       this.output.print($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public void sendSystemMessage(Component $$0) {
/* 228 */       newLine();
/* 229 */       printIndent(this.lastIndent + 1);
/* 230 */       this.output.print("[M] ");
/* 231 */       this.output.println($$0.getString());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean acceptsSuccess() {
/* 236 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean acceptsFailure() {
/* 241 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldInformAdmins() {
/* 246 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean alwaysAccepts() {
/* 251 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void close() {
/* 256 */       IOUtils.closeQuietly(this.output);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\DebugCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */