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
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.SharedSuggestionProvider;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.MutableComponent;
/*    */ 
/*    */ public class TestClassNameArgument
/*    */   implements ArgumentType<String> {
/* 20 */   private static final Collection<String> EXAMPLES = Arrays.asList(new String[] { "techtests", "mobtests" });
/*    */ 
/*    */   
/*    */   public String parse(StringReader $$0) throws CommandSyntaxException {
/* 24 */     String $$1 = $$0.readUnquotedString();
/* 25 */     if (GameTestRegistry.isTestClass($$1)) {
/* 26 */       return $$1;
/*    */     }
/* 28 */     MutableComponent mutableComponent = Component.literal("No such test class: " + $$1);
/* 29 */     throw new CommandSyntaxException(new SimpleCommandExceptionType(mutableComponent), mutableComponent);
/*    */   }
/*    */ 
/*    */   
/*    */   public static TestClassNameArgument testClassName() {
/* 34 */     return new TestClassNameArgument();
/*    */   }
/*    */   
/*    */   public static String getTestClassName(CommandContext<CommandSourceStack> $$0, String $$1) {
/* 38 */     return (String)$$0.getArgument($$1, String.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> $$0, SuggestionsBuilder $$1) {
/* 43 */     return SharedSuggestionProvider.suggest(GameTestRegistry.getAllTestClassNames().stream(), $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<String> getExamples() {
/* 48 */     return EXAMPLES;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\gametest\framework\TestClassNameArgument.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */