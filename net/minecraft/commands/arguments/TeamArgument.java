/*    */ package net.minecraft.commands.arguments;
/*    */ 
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
/*    */ import net.minecraft.world.scores.PlayerTeam;
/*    */ 
/*    */ public class TeamArgument implements ArgumentType<String> {
/* 21 */   private static final Collection<String> EXAMPLES = Arrays.asList(new String[] { "foo", "123" }); static {
/* 22 */     ERROR_TEAM_NOT_FOUND = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("team.notFound", new Object[] { $$0 }));
/*    */   }
/*    */   
/*    */   private static final DynamicCommandExceptionType ERROR_TEAM_NOT_FOUND;
/*    */   
/*    */   public static TeamArgument team() {
/* 28 */     return new TeamArgument();
/*    */   }
/*    */   
/*    */   public static PlayerTeam getTeam(CommandContext<CommandSourceStack> $$0, String $$1) throws CommandSyntaxException {
/* 32 */     String $$2 = (String)$$0.getArgument($$1, String.class);
/* 33 */     ServerScoreboard serverScoreboard = ((CommandSourceStack)$$0.getSource()).getServer().getScoreboard();
/* 34 */     PlayerTeam $$4 = serverScoreboard.getPlayerTeam($$2);
/* 35 */     if ($$4 == null) {
/* 36 */       throw ERROR_TEAM_NOT_FOUND.create($$2);
/*    */     }
/* 38 */     return $$4;
/*    */   }
/*    */ 
/*    */   
/*    */   public String parse(StringReader $$0) throws CommandSyntaxException {
/* 43 */     return $$0.readUnquotedString();
/*    */   }
/*    */ 
/*    */   
/*    */   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> $$0, SuggestionsBuilder $$1) {
/* 48 */     if ($$0.getSource() instanceof SharedSuggestionProvider) {
/* 49 */       return SharedSuggestionProvider.suggest(((SharedSuggestionProvider)$$0.getSource()).getAllTeams(), $$1);
/*    */     }
/* 51 */     return Suggestions.empty();
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<String> getExamples() {
/* 56 */     return EXAMPLES;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\TeamArgument.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */