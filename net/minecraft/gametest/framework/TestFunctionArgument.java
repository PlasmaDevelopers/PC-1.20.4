/*    */ package net.minecraft.gametest.framework;
/*    */ 
/*    */ import com.mojang.brigadier.StringReader;
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*    */ import com.mojang.brigadier.suggestion.Suggestions;
/*    */ import com.mojang.brigadier.suggestion.SuggestionsBuilder;
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import java.util.Optional;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import java.util.stream.Stream;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.SharedSuggestionProvider;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.MutableComponent;
/*    */ 
/*    */ public class TestFunctionArgument
/*    */   implements ArgumentType<TestFunction> {
/* 22 */   private static final Collection<String> EXAMPLES = Arrays.asList(new String[] { "techtests.piston", "techtests" });
/*    */ 
/*    */   
/*    */   public TestFunction parse(StringReader $$0) throws CommandSyntaxException {
/* 26 */     String $$1 = $$0.readUnquotedString();
/* 27 */     Optional<TestFunction> $$2 = GameTestRegistry.findTestFunction($$1);
/* 28 */     if ($$2.isPresent()) {
/* 29 */       return $$2.get();
/*    */     }
/* 31 */     MutableComponent mutableComponent = Component.literal("No such test: " + $$1);
/* 32 */     throw new CommandSyntaxException(new SimpleCommandExceptionType(mutableComponent), mutableComponent);
/*    */   }
/*    */ 
/*    */   
/*    */   public static TestFunctionArgument testFunctionArgument() {
/* 37 */     return new TestFunctionArgument();
/*    */   }
/*    */   
/*    */   public static TestFunction getTestFunction(CommandContext<CommandSourceStack> $$0, String $$1) {
/* 41 */     return (TestFunction)$$0.getArgument($$1, TestFunction.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> $$0, SuggestionsBuilder $$1) {
/* 46 */     Stream<String> $$2 = GameTestRegistry.getAllTestFunctions().stream().map(TestFunction::getTestName);
/* 47 */     return SharedSuggestionProvider.suggest($$2, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<String> getExamples() {
/* 52 */     return EXAMPLES;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\gametest\framework\TestFunctionArgument.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */