/*    */ package net.minecraft.data.recipes.packs;
/*    */ 
/*    */ import net.minecraft.data.PackOutput;
/*    */ import net.minecraft.data.recipes.RecipeCategory;
/*    */ import net.minecraft.data.recipes.RecipeOutput;
/*    */ import net.minecraft.data.recipes.RecipeProvider;
/*    */ import net.minecraft.data.recipes.ShapedRecipeBuilder;
/*    */ import net.minecraft.world.item.Items;
/*    */ import net.minecraft.world.level.ItemLike;
/*    */ 
/*    */ public class BundleRecipeProvider extends RecipeProvider {
/*    */   public BundleRecipeProvider(PackOutput $$0) {
/* 13 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void buildRecipes(RecipeOutput $$0) {
/* 18 */     ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, (ItemLike)Items.BUNDLE)
/* 19 */       .define(Character.valueOf('#'), (ItemLike)Items.RABBIT_HIDE)
/* 20 */       .define(Character.valueOf('-'), (ItemLike)Items.STRING)
/* 21 */       .pattern("-#-")
/* 22 */       .pattern("# #")
/* 23 */       .pattern("###")
/* 24 */       .unlockedBy("has_string", has((ItemLike)Items.STRING))
/* 25 */       .save($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\recipes\packs\BundleRecipeProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */