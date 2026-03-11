/*     */ package net.minecraft.server.commands;
/*     */ import com.mojang.brigadier.Command;
/*     */ import com.mojang.brigadier.CommandDispatcher;
/*     */ import com.mojang.brigadier.Message;
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import com.mojang.brigadier.builder.ArgumentBuilder;
/*     */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*     */ import com.mojang.brigadier.builder.RequiredArgumentBuilder;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.context.ContextChain;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
/*     */ import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
/*     */ import com.mojang.brigadier.suggestion.SuggestionProvider;
/*     */ import com.mojang.brigadier.suggestion.SuggestionsBuilder;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import java.util.Collection;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.commands.CommandResultCallback;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.Commands;
/*     */ import net.minecraft.commands.ExecutionCommandSource;
/*     */ import net.minecraft.commands.FunctionInstantiationException;
/*     */ import net.minecraft.commands.SharedSuggestionProvider;
/*     */ import net.minecraft.commands.arguments.CompoundTagArgument;
/*     */ import net.minecraft.commands.arguments.NbtPathArgument;
/*     */ import net.minecraft.commands.arguments.item.FunctionArgument;
/*     */ import net.minecraft.commands.execution.ChainModifiers;
/*     */ import net.minecraft.commands.execution.CustomCommandExecutor;
/*     */ import net.minecraft.commands.execution.ExecutionContext;
/*     */ import net.minecraft.commands.execution.ExecutionControl;
/*     */ import net.minecraft.commands.functions.CommandFunction;
/*     */ import net.minecraft.commands.functions.InstantiatedFunction;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.ComponentUtils;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.ServerFunctionManager;
/*     */ import net.minecraft.server.commands.data.DataAccessor;
/*     */ import net.minecraft.server.commands.data.DataCommands;
/*     */ 
/*     */ public class FunctionCommand {
/*     */   private static final DynamicCommandExceptionType ERROR_ARGUMENT_NOT_COMPOUND;
/*     */   static final DynamicCommandExceptionType ERROR_NO_FUNCTIONS;
/*     */   
/*     */   static {
/*  49 */     ERROR_ARGUMENT_NOT_COMPOUND = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("commands.function.error.argument_not_compound", new Object[] { $$0 }));
/*  50 */     ERROR_NO_FUNCTIONS = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("commands.function.scheduled.no_functions", new Object[] { $$0 }));
/*     */     
/*  52 */     ERROR_FUNCTION_INSTANTATION_FAILURE = new Dynamic2CommandExceptionType(($$0, $$1) -> Component.translatableEscape("commands.function.instantiationFailure", new Object[] { $$0, $$1 }));
/*     */     
/*  54 */     SUGGEST_FUNCTION = (($$0, $$1) -> {
/*     */         ServerFunctionManager $$2 = ((CommandSourceStack)$$0.getSource()).getServer().getFunctions();
/*     */         SharedSuggestionProvider.suggestResource($$2.getTagNames(), $$1, "#");
/*     */         return SharedSuggestionProvider.suggestResource($$2.getFunctionNames(), $$1);
/*     */       });
/*     */   } @VisibleForTesting
/*     */   public static final Dynamic2CommandExceptionType ERROR_FUNCTION_INSTANTATION_FAILURE; public static final SuggestionProvider<CommandSourceStack> SUGGEST_FUNCTION; public static void register(final CommandDispatcher<CommandSourceStack> provider) {
/*  61 */     LiteralArgumentBuilder<CommandSourceStack> $$1 = Commands.literal("with");
/*  62 */     for (DataCommands.DataProvider $$2 : DataCommands.SOURCE_PROVIDERS) {
/*  63 */       $$2.wrap((ArgumentBuilder)$$1, $$1 -> $$1.executes((Command)new FunctionCustomExecutor()
/*     */             {
/*     */               protected CompoundTag arguments(CommandContext<CommandSourceStack> $$0) throws CommandSyntaxException
/*     */               {
/*  67 */                 return provider.access($$0).getData();
/*     */               }
/*     */             }).then(Commands.argument("path", (ArgumentType)NbtPathArgument.nbtPath()).executes((Command)new FunctionCustomExecutor()
/*     */               {
/*     */ 
/*     */                 
/*     */                 protected CompoundTag arguments(CommandContext<CommandSourceStack> $$0) throws CommandSyntaxException
/*     */                 {
/*  75 */                   return FunctionCommand.getArgumentTag(NbtPathArgument.getPath($$0, "path"), provider.access($$0));
/*     */                 }
/*     */               })));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  82 */     provider.register(
/*  83 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("function")
/*  84 */         .requires($$0 -> $$0.hasPermission(2)))
/*  85 */         .then((
/*  86 */           (RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("name", (ArgumentType)FunctionArgument.functions())
/*  87 */           .suggests(SUGGEST_FUNCTION)
/*  88 */           .executes((Command)new FunctionCustomExecutor()
/*     */             {
/*     */               @Nullable
/*     */               protected CompoundTag arguments(CommandContext<CommandSourceStack> $$0) {
/*  92 */                 return null;
/*     */               }
/*  95 */             })).then(
/*  96 */             Commands.argument("arguments", (ArgumentType)CompoundTagArgument.compoundTag())
/*  97 */             .executes((Command)new FunctionCustomExecutor()
/*     */               {
/*     */                 protected CompoundTag arguments(CommandContext<CommandSourceStack> $$0) {
/* 100 */                   return CompoundTagArgument.getCompoundTag($$0, "arguments");
/*     */                 }
/* 104 */               }))).then((ArgumentBuilder)$$1)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static CompoundTag getArgumentTag(NbtPathArgument.NbtPath $$0, DataAccessor $$1) throws CommandSyntaxException {
/* 112 */     Tag $$2 = DataCommands.getSingleTag($$0, $$1);
/* 113 */     if ($$2 instanceof CompoundTag) { CompoundTag $$3 = (CompoundTag)$$2;
/* 114 */       return $$3; }
/*     */ 
/*     */     
/* 117 */     throw ERROR_ARGUMENT_NOT_COMPOUND.create($$2.getType().getName());
/*     */   }
/*     */ 
/*     */   
/*     */   private static abstract class FunctionCustomExecutor
/*     */     extends CustomCommandExecutor.WithErrorHandling<CommandSourceStack>
/*     */     implements CustomCommandExecutor.CommandAdapter<CommandSourceStack>
/*     */   {
/*     */     public void runGuarded(CommandSourceStack $$0, ContextChain<CommandSourceStack> $$1, ChainModifiers $$2, ExecutionControl<CommandSourceStack> $$3) throws CommandSyntaxException {
/* 126 */       CommandContext<CommandSourceStack> $$4 = $$1.getTopContext().copyFor($$0);
/*     */       
/* 128 */       Pair<ResourceLocation, Collection<CommandFunction<CommandSourceStack>>> $$5 = FunctionArgument.getFunctionCollection($$4, "name");
/* 129 */       Collection<CommandFunction<CommandSourceStack>> $$6 = (Collection<CommandFunction<CommandSourceStack>>)$$5.getSecond();
/* 130 */       if ($$6.isEmpty()) {
/* 131 */         throw FunctionCommand.ERROR_NO_FUNCTIONS.create(Component.translationArg((ResourceLocation)$$5.getFirst()));
/*     */       }
/*     */       
/* 134 */       CompoundTag $$7 = arguments($$4);
/*     */       
/* 136 */       CommandSourceStack $$8 = FunctionCommand.modifySenderForExecution($$0);
/*     */       
/* 138 */       if ($$6.size() == 1) {
/* 139 */         $$0.sendSuccess(() -> Component.translatable("commands.function.scheduled.single", new Object[] { Component.translationArg(((CommandFunction)$$0.iterator().next()).id()) }), true);
/*     */       } else {
/* 141 */         $$0.sendSuccess(() -> Component.translatable("commands.function.scheduled.multiple", new Object[] { ComponentUtils.formatList($$0.stream().map(CommandFunction::id).toList(), Component::translationArg) }), true);
/*     */       } 
/*     */       
/* 144 */       FunctionCommand.queueFunctions($$6, $$7, $$0, $$8, $$3, FunctionCommand.FULL_CONTEXT_CALLBACKS, $$2);
/*     */     }
/*     */     @Nullable
/*     */     protected abstract CompoundTag arguments(CommandContext<CommandSourceStack> param1CommandContext) throws CommandSyntaxException; }
/*     */   public static CommandSourceStack modifySenderForExecution(CommandSourceStack $$0) {
/* 149 */     return $$0
/* 150 */       .withSuppressedOutput()
/* 151 */       .withMaximumPermission(2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 158 */   static final Callbacks<CommandSourceStack> FULL_CONTEXT_CALLBACKS = new Callbacks<CommandSourceStack>()
/*     */     {
/*     */       public void signalResult(CommandSourceStack $$0, ResourceLocation $$1, int $$2) {
/* 161 */         $$0.sendSuccess(() -> Component.translatable("commands.function.result", new Object[] { Component.translationArg($$0), Integer.valueOf($$1) }), true);
/*     */       }
/*     */     };
/*     */   
/*     */   public static <T extends ExecutionCommandSource<T>> void queueFunctions(Collection<CommandFunction<T>> $$0, @Nullable CompoundTag $$1, T $$2, T $$3, ExecutionControl<T> $$4, Callbacks<T> $$5, ChainModifiers $$6) throws CommandSyntaxException {
/* 166 */     if ($$6.isReturn()) {
/* 167 */       queueFunctionsAsReturn($$0, $$1, $$2, $$3, $$4, $$5);
/*     */     } else {
/* 169 */       queueFunctionsNoReturn($$0, $$1, $$2, $$3, $$4, $$5);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static <T extends ExecutionCommandSource<T>> void instantiateAndQueueFunctions(@Nullable CompoundTag $$0, ExecutionControl<T> $$1, CommandDispatcher<T> $$2, T $$3, CommandFunction<T> $$4, ResourceLocation $$5, CommandResultCallback $$6, boolean $$7) throws CommandSyntaxException {
/*     */     try {
/* 175 */       InstantiatedFunction<T> $$8 = $$4.instantiate($$0, $$2, $$3);
/* 176 */       $$1.queueNext((new CallFunction($$8, $$6, $$7)).bind($$3));
/* 177 */     } catch (FunctionInstantiationException $$9) {
/* 178 */       throw ERROR_FUNCTION_INSTANTATION_FAILURE.create($$5, $$9.messageComponent());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T extends ExecutionCommandSource<T>> CommandResultCallback decorateOutputIfNeeded(T $$0, Callbacks<T> $$1, ResourceLocation $$2, CommandResultCallback $$3) {
/* 184 */     if ($$0.isSilent()) {
/* 185 */       return $$3;
/*     */     }
/* 187 */     return ($$4, $$5) -> {
/*     */         $$0.signalResult($$1, $$2, $$5);
/*     */         $$3.onSuccess($$5);
/*     */       };
/*     */   }
/*     */   
/*     */   private static <T extends ExecutionCommandSource<T>> void queueFunctionsAsReturn(Collection<CommandFunction<T>> $$0, @Nullable CompoundTag $$1, T $$2, T $$3, ExecutionControl<T> $$4, Callbacks<T> $$5) throws CommandSyntaxException {
/* 194 */     CommandDispatcher<T> $$6 = $$2.dispatcher();
/*     */     
/* 196 */     ExecutionCommandSource executionCommandSource = $$3.clearCallbacks();
/*     */ 
/*     */     
/* 199 */     CommandResultCallback $$8 = CommandResultCallback.chain($$2
/* 200 */         .callback(), $$4
/* 201 */         .currentFrame().returnValueConsumer());
/*     */ 
/*     */ 
/*     */     
/* 205 */     for (CommandFunction<T> $$9 : $$0) {
/* 206 */       ResourceLocation $$10 = $$9.id();
/* 207 */       CommandResultCallback $$11 = decorateOutputIfNeeded($$2, $$5, $$10, $$8);
/* 208 */       instantiateAndQueueFunctions($$1, $$4, $$6, (T)executionCommandSource, $$9, $$10, $$11, true);
/*     */     } 
/*     */     
/* 211 */     if ($$8 == CommandResultCallback.EMPTY) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 218 */     $$4.queueNext(FallthroughTask.instance());
/*     */   }
/*     */   
/*     */   private static <T extends ExecutionCommandSource<T>> void queueFunctionsNoReturn(Collection<CommandFunction<T>> $$0, @Nullable CompoundTag $$1, T $$2, T $$3, ExecutionControl<T> $$4, Callbacks<T> $$5) throws CommandSyntaxException {
/* 222 */     CommandDispatcher<T> $$6 = $$2.dispatcher();
/*     */     
/* 224 */     ExecutionCommandSource executionCommandSource = $$3.clearCallbacks();
/*     */     
/* 226 */     CommandResultCallback $$8 = $$2.callback();
/* 227 */     if ($$0.isEmpty()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 232 */     if ($$0.size() == 1) {
/*     */       
/* 234 */       CommandFunction<T> $$9 = $$0.iterator().next();
/*     */       
/* 236 */       ResourceLocation $$10 = $$9.id();
/* 237 */       CommandResultCallback $$11 = decorateOutputIfNeeded($$2, $$5, $$10, $$8);
/* 238 */       instantiateAndQueueFunctions($$1, $$4, $$6, (T)executionCommandSource, $$9, $$10, $$11, false);
/*     */     
/*     */     }
/* 241 */     else if ($$8 == CommandResultCallback.EMPTY) {
/*     */       
/* 243 */       for (CommandFunction<T> $$12 : $$0) {
/* 244 */         ResourceLocation $$13 = $$12.id();
/* 245 */         CommandResultCallback $$14 = decorateOutputIfNeeded($$2, $$5, $$13, $$8);
/* 246 */         instantiateAndQueueFunctions($$1, $$4, $$6, (T)executionCommandSource, $$12, $$13, $$14, false);
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */ 
/*     */       
/* 259 */       Accumulator $$15 = new Accumulator();
/* 260 */       CommandResultCallback $$16 = ($$1, $$2) -> $$0.add($$2);
/* 261 */       for (CommandFunction<T> $$17 : $$0) {
/* 262 */         ResourceLocation $$18 = $$17.id();
/* 263 */         CommandResultCallback $$19 = decorateOutputIfNeeded($$2, $$5, $$18, $$16);
/* 264 */         instantiateAndQueueFunctions($$1, $$4, $$6, (T)executionCommandSource, $$17, $$18, $$19, false);
/*     */       } 
/* 266 */       $$4.queueNext(($$2, $$3) -> {
/*     */             if ($$0.anyResult)
/*     */               $$1.onSuccess($$0.sum); 
/*     */           });
/*     */     } 
/*     */   }
/*     */   
/*     */   public static interface Callbacks<T> {
/*     */     void signalResult(T param1T, ResourceLocation param1ResourceLocation, int param1Int);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\FunctionCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */