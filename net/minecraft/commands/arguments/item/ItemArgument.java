/*    */ package net.minecraft.commands.arguments.item;
/*    */ 
/*    */ import com.mojang.brigadier.StringReader;
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.brigadier.suggestion.Suggestions;
/*    */ import com.mojang.brigadier.suggestion.SuggestionsBuilder;
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import net.minecraft.commands.CommandBuildContext;
/*    */ import net.minecraft.core.HolderLookup;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.world.item.Item;
/*    */ 
/*    */ public class ItemArgument
/*    */   implements ArgumentType<ItemInput> {
/* 19 */   private static final Collection<String> EXAMPLES = Arrays.asList(new String[] { "stick", "minecraft:stick", "stick{foo=bar}" });
/*    */   
/*    */   private final HolderLookup<Item> items;
/*    */   
/*    */   public ItemArgument(CommandBuildContext $$0) {
/* 24 */     this.items = $$0.holderLookup(Registries.ITEM);
/*    */   }
/*    */   
/*    */   public static ItemArgument item(CommandBuildContext $$0) {
/* 28 */     return new ItemArgument($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemInput parse(StringReader $$0) throws CommandSyntaxException {
/* 33 */     ItemParser.ItemResult $$1 = ItemParser.parseForItem(this.items, $$0);
/* 34 */     return new ItemInput($$1.item(), $$1.nbt());
/*    */   }
/*    */   
/*    */   public static <S> ItemInput getItem(CommandContext<S> $$0, String $$1) {
/* 38 */     return (ItemInput)$$0.getArgument($$1, ItemInput.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> $$0, SuggestionsBuilder $$1) {
/* 43 */     return ItemParser.fillSuggestions(this.items, $$1, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<String> getExamples() {
/* 48 */     return EXAMPLES;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\item\ItemArgument.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */