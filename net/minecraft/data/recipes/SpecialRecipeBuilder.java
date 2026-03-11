/*    */ package net.minecraft.data.recipes;
/*    */ 
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.item.crafting.CraftingBookCategory;
/*    */ import net.minecraft.world.item.crafting.Recipe;
/*    */ 
/*    */ public class SpecialRecipeBuilder
/*    */ {
/*    */   private final Function<CraftingBookCategory, Recipe<?>> factory;
/*    */   
/*    */   public SpecialRecipeBuilder(Function<CraftingBookCategory, Recipe<?>> $$0) {
/* 13 */     this.factory = $$0;
/*    */   }
/*    */   
/*    */   public static SpecialRecipeBuilder special(Function<CraftingBookCategory, Recipe<?>> $$0) {
/* 17 */     return new SpecialRecipeBuilder($$0);
/*    */   }
/*    */   
/*    */   public void save(RecipeOutput $$0, String $$1) {
/* 21 */     save($$0, new ResourceLocation($$1));
/*    */   }
/*    */   
/*    */   public void save(RecipeOutput $$0, ResourceLocation $$1) {
/* 25 */     $$0.accept($$1, this.factory.apply(CraftingBookCategory.MISC), null);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\recipes\SpecialRecipeBuilder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */