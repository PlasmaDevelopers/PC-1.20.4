/*    */ package net.minecraft.commands.arguments.item;
/*    */ import com.mojang.brigadier.StringReader;
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.brigadier.suggestion.Suggestions;
/*    */ import com.mojang.brigadier.suggestion.SuggestionsBuilder;
/*    */ import com.mojang.datafixers.util.Either;
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import java.util.Objects;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import java.util.function.Predicate;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.commands.CommandBuildContext;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.HolderLookup;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.nbt.NbtUtils;
/*    */ import net.minecraft.nbt.Tag;
/*    */ import net.minecraft.world.item.Item;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ public class ItemPredicateArgument implements ArgumentType<ItemPredicateArgument.Result> {
/* 27 */   private static final Collection<String> EXAMPLES = Arrays.asList(new String[] { "stick", "minecraft:stick", "#stick", "#stick{foo=bar}" });
/*    */   
/*    */   private final HolderLookup<Item> items;
/*    */   
/*    */   public ItemPredicateArgument(CommandBuildContext $$0) {
/* 32 */     this.items = $$0.holderLookup(Registries.ITEM);
/*    */   }
/*    */   
/*    */   public static ItemPredicateArgument itemPredicate(CommandBuildContext $$0) {
/* 36 */     return new ItemPredicateArgument($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public Result parse(StringReader $$0) throws CommandSyntaxException {
/* 41 */     Either<ItemParser.ItemResult, ItemParser.TagResult> $$1 = ItemParser.parseForTesting(this.items, $$0);
/* 42 */     return (Result)$$1.map($$0 -> createResult((), $$0.nbt()), $$0 -> {
/*    */           Objects.requireNonNull($$0.tag());
/*    */           return createResult($$0.tag()::contains, $$0.nbt());
/*    */         });
/*    */   }
/*    */   
/*    */   public static Predicate<ItemStack> getItemPredicate(CommandContext<CommandSourceStack> $$0, String $$1) {
/* 49 */     return (Predicate<ItemStack>)$$0.getArgument($$1, Result.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> $$0, SuggestionsBuilder $$1) {
/* 54 */     return ItemParser.fillSuggestions(this.items, $$1, true);
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<String> getExamples() {
/* 59 */     return EXAMPLES;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static Result createResult(Predicate<Holder<Item>> $$0, @Nullable CompoundTag $$1) {
/* 65 */     return ($$1 != null) ? ($$2 -> 
/* 66 */       ($$2.is($$0) && NbtUtils.compareNbt((Tag)$$1, (Tag)$$2.getTag(), true))) : ($$1 -> $$1.is($$0));
/*    */   }
/*    */   
/*    */   public static interface Result extends Predicate<ItemStack> {}
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\item\ItemPredicateArgument.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */