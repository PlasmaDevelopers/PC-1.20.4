/*    */ package net.minecraft.commands.arguments;
/*    */ import com.mojang.brigadier.Message;
/*    */ import com.mojang.brigadier.StringReader;
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import net.minecraft.advancements.AdvancementHolder;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.item.crafting.RecipeHolder;
/*    */ import net.minecraft.world.item.crafting.RecipeManager;
/*    */ import net.minecraft.world.level.storage.loot.LootDataManager;
/*    */ import net.minecraft.world.level.storage.loot.LootDataType;
/*    */ import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
/*    */ import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
/*    */ 
/*    */ public class ResourceLocationArgument implements ArgumentType<ResourceLocation> {
/*    */   private static final DynamicCommandExceptionType ERROR_UNKNOWN_ADVANCEMENT;
/* 23 */   private static final Collection<String> EXAMPLES = Arrays.asList(new String[] { "foo", "foo:bar", "012" }); private static final DynamicCommandExceptionType ERROR_UNKNOWN_RECIPE; static {
/* 24 */     ERROR_UNKNOWN_ADVANCEMENT = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("advancement.advancementNotFound", new Object[] { $$0 }));
/* 25 */     ERROR_UNKNOWN_RECIPE = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("recipe.notFound", new Object[] { $$0 }));
/* 26 */     ERROR_UNKNOWN_PREDICATE = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("predicate.unknown", new Object[] { $$0 }));
/* 27 */     ERROR_UNKNOWN_ITEM_MODIFIER = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("item_modifier.unknown", new Object[] { $$0 }));
/*    */   }
/*    */   private static final DynamicCommandExceptionType ERROR_UNKNOWN_PREDICATE;
/*    */   private static final DynamicCommandExceptionType ERROR_UNKNOWN_ITEM_MODIFIER;
/*    */   
/*    */   public static ResourceLocationArgument id() {
/* 33 */     return new ResourceLocationArgument();
/*    */   }
/*    */   
/*    */   public static AdvancementHolder getAdvancement(CommandContext<CommandSourceStack> $$0, String $$1) throws CommandSyntaxException {
/* 37 */     ResourceLocation $$2 = getId($$0, $$1);
/* 38 */     AdvancementHolder $$3 = ((CommandSourceStack)$$0.getSource()).getServer().getAdvancements().get($$2);
/* 39 */     if ($$3 == null) {
/* 40 */       throw ERROR_UNKNOWN_ADVANCEMENT.create($$2);
/*    */     }
/* 42 */     return $$3;
/*    */   }
/*    */   
/*    */   public static RecipeHolder<?> getRecipe(CommandContext<CommandSourceStack> $$0, String $$1) throws CommandSyntaxException {
/* 46 */     RecipeManager $$2 = ((CommandSourceStack)$$0.getSource()).getServer().getRecipeManager();
/* 47 */     ResourceLocation $$3 = getId($$0, $$1);
/*    */     
/* 49 */     return (RecipeHolder)$$2.byKey($$3).orElseThrow(() -> ERROR_UNKNOWN_RECIPE.create($$0));
/*    */   }
/*    */   
/*    */   public static LootItemCondition getPredicate(CommandContext<CommandSourceStack> $$0, String $$1) throws CommandSyntaxException {
/* 53 */     ResourceLocation $$2 = getId($$0, $$1);
/*    */     
/* 55 */     LootDataManager $$3 = ((CommandSourceStack)$$0.getSource()).getServer().getLootData();
/* 56 */     LootItemCondition $$4 = (LootItemCondition)$$3.getElement(LootDataType.PREDICATE, $$2);
/* 57 */     if ($$4 == null) {
/* 58 */       throw ERROR_UNKNOWN_PREDICATE.create($$2);
/*    */     }
/* 60 */     return $$4;
/*    */   }
/*    */   
/*    */   public static LootItemFunction getItemModifier(CommandContext<CommandSourceStack> $$0, String $$1) throws CommandSyntaxException {
/* 64 */     ResourceLocation $$2 = getId($$0, $$1);
/*    */     
/* 66 */     LootDataManager $$3 = ((CommandSourceStack)$$0.getSource()).getServer().getLootData();
/* 67 */     LootItemFunction $$4 = (LootItemFunction)$$3.getElement(LootDataType.MODIFIER, $$2);
/* 68 */     if ($$4 == null) {
/* 69 */       throw ERROR_UNKNOWN_ITEM_MODIFIER.create($$2);
/*    */     }
/* 71 */     return $$4;
/*    */   }
/*    */   
/*    */   public static ResourceLocation getId(CommandContext<CommandSourceStack> $$0, String $$1) {
/* 75 */     return (ResourceLocation)$$0.getArgument($$1, ResourceLocation.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation parse(StringReader $$0) throws CommandSyntaxException {
/* 80 */     return ResourceLocation.read($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<String> getExamples() {
/* 85 */     return EXAMPLES;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\ResourceLocationArgument.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */