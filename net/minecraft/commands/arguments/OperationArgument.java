/*     */ package net.minecraft.commands.arguments;
/*     */ 
/*     */ import com.mojang.brigadier.Message;
/*     */ import com.mojang.brigadier.StringReader;
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*     */ import com.mojang.brigadier.suggestion.Suggestions;
/*     */ import com.mojang.brigadier.suggestion.SuggestionsBuilder;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.SharedSuggestionProvider;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.scores.ScoreAccess;
/*     */ 
/*     */ public class OperationArgument implements ArgumentType<OperationArgument.Operation> {
/*  21 */   private static final Collection<String> EXAMPLES = Arrays.asList(new String[] { "=", ">", "<" });
/*  22 */   private static final SimpleCommandExceptionType ERROR_INVALID_OPERATION = new SimpleCommandExceptionType((Message)Component.translatable("arguments.operation.invalid"));
/*  23 */   private static final SimpleCommandExceptionType ERROR_DIVIDE_BY_ZERO = new SimpleCommandExceptionType((Message)Component.translatable("arguments.operation.div0"));
/*     */   
/*     */   public static OperationArgument operation() {
/*  26 */     return new OperationArgument();
/*     */   }
/*     */   
/*     */   public static Operation getOperation(CommandContext<CommandSourceStack> $$0, String $$1) {
/*  30 */     return (Operation)$$0.getArgument($$1, Operation.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public Operation parse(StringReader $$0) throws CommandSyntaxException {
/*  35 */     if ($$0.canRead()) {
/*  36 */       int $$1 = $$0.getCursor();
/*  37 */       while ($$0.canRead() && $$0.peek() != ' ') {
/*  38 */         $$0.skip();
/*     */       }
/*  40 */       return getOperation($$0.getString().substring($$1, $$0.getCursor()));
/*     */     } 
/*     */     
/*  43 */     throw ERROR_INVALID_OPERATION.create();
/*     */   }
/*     */ 
/*     */   
/*     */   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> $$0, SuggestionsBuilder $$1) {
/*  48 */     return SharedSuggestionProvider.suggest(new String[] { "=", "+=", "-=", "*=", "/=", "%=", "<", ">", "><" }, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<String> getExamples() {
/*  53 */     return EXAMPLES;
/*     */   }
/*     */   
/*     */   private static Operation getOperation(String $$0) throws CommandSyntaxException {
/*  57 */     if ($$0.equals("><")) {
/*  58 */       return ($$0, $$1) -> {
/*     */           int $$2 = $$0.get();
/*     */           
/*     */           $$0.set($$1.get());
/*     */           $$1.set($$2);
/*     */         };
/*     */     }
/*  65 */     return getSimpleOperation($$0);
/*     */   }
/*     */   
/*     */   private static SimpleOperation getSimpleOperation(String $$0) throws CommandSyntaxException {
/*  69 */     switch ($$0) {
/*     */       case "=":
/*     */       
/*     */       
/*     */       case "+=":
/*     */       
/*     */       case "-=":
/*     */       
/*     */       case "*=":
/*     */       
/*     */       case "/=":
/*     */       
/*     */       case "%=":
/*     */       
/*     */       case "<":
/*     */       
/*     */       case ">":
/*     */       
/*     */     } 
/*  88 */     throw ERROR_INVALID_OPERATION.create();
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface Operation
/*     */   {
/*     */     void apply(ScoreAccess param1ScoreAccess1, ScoreAccess param1ScoreAccess2) throws CommandSyntaxException;
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   private static interface SimpleOperation
/*     */     extends Operation {
/*     */     int apply(int param1Int1, int param1Int2) throws CommandSyntaxException;
/*     */     
/*     */     default void apply(ScoreAccess $$0, ScoreAccess $$1) throws CommandSyntaxException {
/* 103 */       $$0.set(apply($$0.get(), $$1.get()));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\OperationArgument.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */