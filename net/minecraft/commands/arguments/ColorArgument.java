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
/*    */ import net.minecraft.ChatFormatting;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.SharedSuggestionProvider;
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ public class ColorArgument implements ArgumentType<ChatFormatting> {
/* 20 */   private static final Collection<String> EXAMPLES = Arrays.asList(new String[] { "red", "green" }); static {
/* 21 */     ERROR_INVALID_VALUE = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("argument.color.invalid", new Object[] { $$0 }));
/*    */   }
/*    */   
/*    */   public static final DynamicCommandExceptionType ERROR_INVALID_VALUE;
/*    */   
/*    */   public static ColorArgument color() {
/* 27 */     return new ColorArgument();
/*    */   }
/*    */   
/*    */   public static ChatFormatting getColor(CommandContext<CommandSourceStack> $$0, String $$1) {
/* 31 */     return (ChatFormatting)$$0.getArgument($$1, ChatFormatting.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public ChatFormatting parse(StringReader $$0) throws CommandSyntaxException {
/* 36 */     String $$1 = $$0.readUnquotedString();
/* 37 */     ChatFormatting $$2 = ChatFormatting.getByName($$1);
/* 38 */     if ($$2 == null || $$2.isFormat()) {
/* 39 */       throw ERROR_INVALID_VALUE.create($$1);
/*    */     }
/* 41 */     return $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> $$0, SuggestionsBuilder $$1) {
/* 46 */     return SharedSuggestionProvider.suggest(ChatFormatting.getNames(true, false), $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<String> getExamples() {
/* 51 */     return EXAMPLES;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\ColorArgument.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */