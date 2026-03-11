/*    */ package net.minecraft.commands.arguments;
/*    */ import com.mojang.brigadier.Message;
/*    */ import com.mojang.brigadier.StringReader;
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
/*    */ import com.mojang.brigadier.suggestion.Suggestions;
/*    */ import com.mojang.brigadier.suggestion.SuggestionsBuilder;
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.SharedSuggestionProvider;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.server.ServerScoreboard;
/*    */ import net.minecraft.world.scores.Objective;
/*    */ 
/*    */ public class ObjectiveArgument implements ArgumentType<String> {
/*    */   private static final DynamicCommandExceptionType ERROR_OBJECTIVE_NOT_FOUND;
/* 21 */   private static final Collection<String> EXAMPLES = Arrays.asList(new String[] { "foo", "*", "012" }); private static final DynamicCommandExceptionType ERROR_OBJECTIVE_READ_ONLY; static {
/* 22 */     ERROR_OBJECTIVE_NOT_FOUND = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("arguments.objective.notFound", new Object[] { $$0 }));
/* 23 */     ERROR_OBJECTIVE_READ_ONLY = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("arguments.objective.readonly", new Object[] { $$0 }));
/*    */   }
/*    */   public static ObjectiveArgument objective() {
/* 26 */     return new ObjectiveArgument();
/*    */   }
/*    */   
/*    */   public static Objective getObjective(CommandContext<CommandSourceStack> $$0, String $$1) throws CommandSyntaxException {
/* 30 */     String $$2 = (String)$$0.getArgument($$1, String.class);
/* 31 */     ServerScoreboard serverScoreboard = ((CommandSourceStack)$$0.getSource()).getServer().getScoreboard();
/* 32 */     Objective $$4 = serverScoreboard.getObjective($$2);
/* 33 */     if ($$4 == null) {
/* 34 */       throw ERROR_OBJECTIVE_NOT_FOUND.create($$2);
/*    */     }
/* 36 */     return $$4;
/*    */   }
/*    */   
/*    */   public static Objective getWritableObjective(CommandContext<CommandSourceStack> $$0, String $$1) throws CommandSyntaxException {
/* 40 */     Objective $$2 = getObjective($$0, $$1);
/* 41 */     if ($$2.getCriteria().isReadOnly()) {
/* 42 */       throw ERROR_OBJECTIVE_READ_ONLY.create($$2.getName());
/*    */     }
/* 44 */     return $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public String parse(StringReader $$0) throws CommandSyntaxException {
/* 49 */     return $$0.readUnquotedString();
/*    */   }
/*    */ 
/*    */   
/*    */   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> $$0, SuggestionsBuilder $$1) {
/* 54 */     S $$2 = (S)$$0.getSource();
/* 55 */     if ($$2 instanceof CommandSourceStack) { CommandSourceStack $$3 = (CommandSourceStack)$$2;
/* 56 */       return SharedSuggestionProvider.suggest($$3.getServer().getScoreboard().getObjectiveNames(), $$1); }
/* 57 */      if ($$2 instanceof SharedSuggestionProvider) { SharedSuggestionProvider $$4 = (SharedSuggestionProvider)$$2;
/* 58 */       return $$4.customSuggestion($$0); }
/*    */     
/* 60 */     return Suggestions.empty();
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<String> getExamples() {
/* 65 */     return EXAMPLES;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\ObjectiveArgument.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */