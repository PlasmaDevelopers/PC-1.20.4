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
/*    */ import net.minecraft.world.scores.DisplaySlot;
/*    */ 
/*    */ public class ScoreboardSlotArgument implements ArgumentType<DisplaySlot> {
/* 20 */   private static final Collection<String> EXAMPLES = Arrays.asList(new String[] { "sidebar", "foo.bar" }); static {
/* 21 */     ERROR_INVALID_VALUE = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("argument.scoreboardDisplaySlot.invalid", new Object[] { $$0 }));
/*    */   }
/*    */   
/*    */   public static final DynamicCommandExceptionType ERROR_INVALID_VALUE;
/*    */   
/*    */   public static ScoreboardSlotArgument displaySlot() {
/* 27 */     return new ScoreboardSlotArgument();
/*    */   }
/*    */   
/*    */   public static DisplaySlot getDisplaySlot(CommandContext<CommandSourceStack> $$0, String $$1) {
/* 31 */     return (DisplaySlot)$$0.getArgument($$1, DisplaySlot.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public DisplaySlot parse(StringReader $$0) throws CommandSyntaxException {
/* 36 */     String $$1 = $$0.readUnquotedString();
/* 37 */     DisplaySlot $$2 = (DisplaySlot)DisplaySlot.CODEC.byName($$1);
/* 38 */     if ($$2 == null) {
/* 39 */       throw ERROR_INVALID_VALUE.create($$1);
/*    */     }
/* 41 */     return $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> $$0, SuggestionsBuilder $$1) {
/* 46 */     return SharedSuggestionProvider.suggest(Arrays.<DisplaySlot>stream(DisplaySlot.values()).map(DisplaySlot::getSerializedName), $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<String> getExamples() {
/* 51 */     return EXAMPLES;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\ScoreboardSlotArgument.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */