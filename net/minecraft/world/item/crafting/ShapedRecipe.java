/*     */ package net.minecraft.world.item.crafting;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function5;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import net.minecraft.core.NonNullList;
/*     */ import net.minecraft.core.RegistryAccess;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.inventory.CraftingContainer;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.Level;
/*     */ 
/*     */ public class ShapedRecipe implements CraftingRecipe {
/*     */   final ShapedRecipePattern pattern;
/*     */   final ItemStack result;
/*     */   
/*     */   public ShapedRecipe(String $$0, CraftingBookCategory $$1, ShapedRecipePattern $$2, ItemStack $$3, boolean $$4) {
/*  21 */     this.group = $$0;
/*  22 */     this.category = $$1;
/*  23 */     this.pattern = $$2;
/*  24 */     this.result = $$3;
/*  25 */     this.showNotification = $$4;
/*     */   }
/*     */   final String group; final CraftingBookCategory category; final boolean showNotification;
/*     */   public ShapedRecipe(String $$0, CraftingBookCategory $$1, ShapedRecipePattern $$2, ItemStack $$3) {
/*  29 */     this($$0, $$1, $$2, $$3, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public RecipeSerializer<?> getSerializer() {
/*  34 */     return RecipeSerializer.SHAPED_RECIPE;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getGroup() {
/*  39 */     return this.group;
/*     */   }
/*     */ 
/*     */   
/*     */   public CraftingBookCategory category() {
/*  44 */     return this.category;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getResultItem(RegistryAccess $$0) {
/*  49 */     return this.result;
/*     */   }
/*     */ 
/*     */   
/*     */   public NonNullList<Ingredient> getIngredients() {
/*  54 */     return this.pattern.ingredients();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean showNotification() {
/*  59 */     return this.showNotification;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canCraftInDimensions(int $$0, int $$1) {
/*  64 */     return ($$0 >= this.pattern.width() && $$1 >= this.pattern.height());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean matches(CraftingContainer $$0, Level $$1) {
/*  69 */     return this.pattern.matches($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack assemble(CraftingContainer $$0, RegistryAccess $$1) {
/*  74 */     return getResultItem($$1).copy();
/*     */   }
/*     */   
/*     */   public int getWidth() {
/*  78 */     return this.pattern.width();
/*     */   }
/*     */   
/*     */   public int getHeight() {
/*  82 */     return this.pattern.height();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isIncomplete() {
/*  87 */     NonNullList<Ingredient> $$0 = getIngredients();
/*     */     
/*  89 */     return ($$0.isEmpty() || $$0.stream().filter($$0 -> !$$0.isEmpty()).anyMatch($$0 -> (($$0.getItems()).length == 0)));
/*     */   }
/*     */   public static class Serializer implements RecipeSerializer<ShapedRecipe> { public static final Codec<ShapedRecipe> CODEC;
/*     */     static {
/*  93 */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField((Codec)Codec.STRING, "group", "").forGetter(()), (App)CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter(()), (App)ShapedRecipePattern.MAP_CODEC.forGetter(()), (App)ItemStack.ITEM_WITH_COUNT_CODEC.fieldOf("result").forGetter(()), (App)ExtraCodecs.strictOptionalField((Codec)Codec.BOOL, "show_notification", Boolean.valueOf(true)).forGetter(())).apply((Applicative)$$0, ShapedRecipe::new));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Codec<ShapedRecipe> codec() {
/* 103 */       return CODEC;
/*     */     }
/*     */ 
/*     */     
/*     */     public ShapedRecipe fromNetwork(FriendlyByteBuf $$0) {
/* 108 */       String $$1 = $$0.readUtf();
/* 109 */       CraftingBookCategory $$2 = (CraftingBookCategory)$$0.readEnum(CraftingBookCategory.class);
/* 110 */       ShapedRecipePattern $$3 = ShapedRecipePattern.fromNetwork($$0);
/* 111 */       ItemStack $$4 = $$0.readItem();
/* 112 */       boolean $$5 = $$0.readBoolean();
/* 113 */       return new ShapedRecipe($$1, $$2, $$3, $$4, $$5);
/*     */     }
/*     */ 
/*     */     
/*     */     public void toNetwork(FriendlyByteBuf $$0, ShapedRecipe $$1) {
/* 118 */       $$0.writeUtf($$1.group);
/* 119 */       $$0.writeEnum($$1.category);
/* 120 */       $$1.pattern.toNetwork($$0);
/* 121 */       $$0.writeItem($$1.result);
/* 122 */       $$0.writeBoolean($$1.showNotification);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\crafting\ShapedRecipe.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */