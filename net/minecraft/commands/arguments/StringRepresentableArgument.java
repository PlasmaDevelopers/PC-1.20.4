/*    */ package net.minecraft.commands.arguments;
/*    */ import com.google.gson.JsonPrimitive;
/*    */ import com.mojang.brigadier.Message;
/*    */ import com.mojang.brigadier.StringReader;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
/*    */ import com.mojang.brigadier.suggestion.Suggestions;
/*    */ import com.mojang.brigadier.suggestion.SuggestionsBuilder;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.DynamicOps;
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import java.util.function.Supplier;
/*    */ import java.util.stream.Collectors;
/*    */ import net.minecraft.commands.SharedSuggestionProvider;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.util.StringRepresentable;
/*    */ 
/*    */ public class StringRepresentableArgument<T extends Enum<T> & StringRepresentable> implements ArgumentType<T> {
/*    */   private static final DynamicCommandExceptionType ERROR_INVALID_VALUE;
/*    */   
/*    */   static {
/* 24 */     ERROR_INVALID_VALUE = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("argument.enum.invalid", new Object[] { $$0 }));
/*    */   }
/*    */   private final Codec<T> codec; private final Supplier<T[]> values;
/*    */   
/*    */   protected StringRepresentableArgument(Codec<T> $$0, Supplier<T[]> $$1) {
/* 29 */     this.codec = $$0;
/* 30 */     this.values = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public T parse(StringReader $$0) throws CommandSyntaxException {
/* 35 */     String $$1 = $$0.readUnquotedString();
/* 36 */     return (T)this.codec.parse((DynamicOps)JsonOps.INSTANCE, new JsonPrimitive($$1)).result().orElseThrow(() -> ERROR_INVALID_VALUE.create($$0));
/*    */   }
/*    */ 
/*    */   
/*    */   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> $$0, SuggestionsBuilder $$1) {
/* 41 */     return SharedSuggestionProvider.suggest((Iterable)Arrays.<Enum>stream((Enum[])this.values.get()).map($$0 -> ((StringRepresentable)$$0).getSerializedName()).map(this::convertId).collect(Collectors.toList()), $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<String> getExamples() {
/* 46 */     return (Collection<String>)Arrays.<Enum>stream((Enum[])this.values.get()).map($$0 -> ((StringRepresentable)$$0).getSerializedName()).map(this::convertId).limit(2L).collect(Collectors.toList());
/*    */   }
/*    */   
/*    */   protected String convertId(String $$0) {
/* 50 */     return $$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\StringRepresentableArgument.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */