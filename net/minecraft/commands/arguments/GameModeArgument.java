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
/*    */ import java.util.stream.Collectors;
/*    */ import java.util.stream.Stream;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.SharedSuggestionProvider;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.world.level.GameType;
/*    */ 
/*    */ public class GameModeArgument implements ArgumentType<GameType> {
/* 22 */   private static final Collection<String> EXAMPLES = (Collection<String>)Stream.<GameType>of(new GameType[] { GameType.SURVIVAL, GameType.CREATIVE }).map(GameType::getName).collect(Collectors.toList());
/* 23 */   private static final GameType[] VALUES = GameType.values();
/*    */   static {
/* 25 */     ERROR_INVALID = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("argument.gamemode.invalid", new Object[] { $$0 }));
/*    */   }
/*    */   private static final DynamicCommandExceptionType ERROR_INVALID;
/*    */   public GameType parse(StringReader $$0) throws CommandSyntaxException {
/* 29 */     String $$1 = $$0.readUnquotedString();
/* 30 */     GameType $$2 = GameType.byName($$1, null);
/* 31 */     if ($$2 == null) {
/* 32 */       throw ERROR_INVALID.createWithContext($$0, $$1);
/*    */     }
/* 34 */     return $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> $$0, SuggestionsBuilder $$1) {
/* 39 */     if ($$0.getSource() instanceof SharedSuggestionProvider) {
/* 40 */       return SharedSuggestionProvider.suggest(Arrays.<GameType>stream(VALUES).map(GameType::getName), $$1);
/*    */     }
/* 42 */     return Suggestions.empty();
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<String> getExamples() {
/* 47 */     return EXAMPLES;
/*    */   }
/*    */   
/*    */   public static GameModeArgument gameMode() {
/* 51 */     return new GameModeArgument();
/*    */   }
/*    */   
/*    */   public static GameType getGameMode(CommandContext<CommandSourceStack> $$0, String $$1) throws CommandSyntaxException {
/* 55 */     return (GameType)$$0.getArgument($$1, GameType.class);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\GameModeArgument.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */