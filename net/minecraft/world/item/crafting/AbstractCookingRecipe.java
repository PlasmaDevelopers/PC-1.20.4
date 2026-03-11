/*    */ package net.minecraft.world.item.crafting;
/*    */ 
/*    */ import net.minecraft.core.NonNullList;
/*    */ import net.minecraft.core.RegistryAccess;
/*    */ import net.minecraft.world.Container;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.Level;
/*    */ 
/*    */ public abstract class AbstractCookingRecipe
/*    */   implements Recipe<Container> {
/*    */   protected final RecipeType<?> type;
/*    */   protected final CookingBookCategory category;
/*    */   protected final String group;
/*    */   protected final Ingredient ingredient;
/*    */   protected final ItemStack result;
/*    */   protected final float experience;
/*    */   protected final int cookingTime;
/*    */   
/*    */   public AbstractCookingRecipe(RecipeType<?> $$0, String $$1, CookingBookCategory $$2, Ingredient $$3, ItemStack $$4, float $$5, int $$6) {
/* 20 */     this.type = $$0;
/* 21 */     this.category = $$2;
/* 22 */     this.group = $$1;
/* 23 */     this.ingredient = $$3;
/* 24 */     this.result = $$4;
/* 25 */     this.experience = $$5;
/* 26 */     this.cookingTime = $$6;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(Container $$0, Level $$1) {
/* 31 */     return this.ingredient.test($$0.getItem(0));
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack assemble(Container $$0, RegistryAccess $$1) {
/* 36 */     return this.result.copy();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canCraftInDimensions(int $$0, int $$1) {
/* 41 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public NonNullList<Ingredient> getIngredients() {
/* 46 */     NonNullList<Ingredient> $$0 = NonNullList.create();
/* 47 */     $$0.add(this.ingredient);
/* 48 */     return $$0;
/*    */   }
/*    */   
/*    */   public float getExperience() {
/* 52 */     return this.experience;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getResultItem(RegistryAccess $$0) {
/* 57 */     return this.result;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getGroup() {
/* 62 */     return this.group;
/*    */   }
/*    */   
/*    */   public int getCookingTime() {
/* 66 */     return this.cookingTime;
/*    */   }
/*    */ 
/*    */   
/*    */   public RecipeType<?> getType() {
/* 71 */     return this.type;
/*    */   }
/*    */   
/*    */   public CookingBookCategory category() {
/* 75 */     return this.category;
/*    */   }
/*    */   
/*    */   public static interface Factory<T extends AbstractCookingRecipe> {
/*    */     T create(String param1String, CookingBookCategory param1CookingBookCategory, Ingredient param1Ingredient, ItemStack param1ItemStack, float param1Float, int param1Int);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\crafting\AbstractCookingRecipe.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */