/*    */ package net.minecraft.commands.arguments;
/*    */ 
/*    */ import com.google.common.collect.Lists;
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
/*    */ import java.util.List;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.SharedSuggestionProvider;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.stats.Stat;
/*    */ import net.minecraft.stats.StatType;
/*    */ import net.minecraft.world.scores.criteria.ObjectiveCriteria;
/*    */ 
/*    */ public class ObjectiveCriteriaArgument implements ArgumentType<ObjectiveCriteria> {
/* 25 */   private static final Collection<String> EXAMPLES = Arrays.asList(new String[] { "foo", "foo.bar.baz", "minecraft:foo" }); static {
/* 26 */     ERROR_INVALID_VALUE = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("argument.criteria.invalid", new Object[] { $$0 }));
/*    */   }
/*    */   
/*    */   public static final DynamicCommandExceptionType ERROR_INVALID_VALUE;
/*    */   
/*    */   public static ObjectiveCriteriaArgument criteria() {
/* 32 */     return new ObjectiveCriteriaArgument();
/*    */   }
/*    */   
/*    */   public static ObjectiveCriteria getCriteria(CommandContext<CommandSourceStack> $$0, String $$1) {
/* 36 */     return (ObjectiveCriteria)$$0.getArgument($$1, ObjectiveCriteria.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public ObjectiveCriteria parse(StringReader $$0) throws CommandSyntaxException {
/* 41 */     int $$1 = $$0.getCursor();
/* 42 */     while ($$0.canRead() && $$0.peek() != ' ') {
/* 43 */       $$0.skip();
/*    */     }
/* 45 */     String $$2 = $$0.getString().substring($$1, $$0.getCursor());
/* 46 */     return (ObjectiveCriteria)ObjectiveCriteria.byName($$2).orElseThrow(() -> {
/*    */           $$0.setCursor($$1);
/*    */           return ERROR_INVALID_VALUE.create($$2);
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> $$0, SuggestionsBuilder $$1) {
/* 54 */     List<String> $$2 = Lists.newArrayList(ObjectiveCriteria.getCustomCriteriaNames());
/* 55 */     for (StatType<?> $$3 : (Iterable<StatType<?>>)BuiltInRegistries.STAT_TYPE) {
/* 56 */       for (Object $$4 : $$3.getRegistry()) {
/* 57 */         String $$5 = getName($$3, $$4);
/* 58 */         $$2.add($$5);
/*    */       } 
/*    */     } 
/* 61 */     return SharedSuggestionProvider.suggest($$2, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public <T> String getName(StatType<T> $$0, Object $$1) {
/* 66 */     return Stat.buildName($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<String> getExamples() {
/* 71 */     return EXAMPLES;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\ObjectiveCriteriaArgument.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */