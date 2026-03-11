/*     */ package net.minecraft.world.item.crafting;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function4;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import net.minecraft.core.NonNullList;
/*     */ import net.minecraft.core.RegistryAccess;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.entity.player.StackedContents;
/*     */ import net.minecraft.world.inventory.CraftingContainer;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.Level;
/*     */ 
/*     */ public class ShapelessRecipe implements CraftingRecipe {
/*     */   final String group;
/*     */   final CraftingBookCategory category;
/*     */   
/*     */   public ShapelessRecipe(String $$0, CraftingBookCategory $$1, ItemStack $$2, NonNullList<Ingredient> $$3) {
/*  22 */     this.group = $$0;
/*  23 */     this.category = $$1;
/*  24 */     this.result = $$2;
/*  25 */     this.ingredients = $$3;
/*     */   }
/*     */   final ItemStack result; final NonNullList<Ingredient> ingredients;
/*     */   
/*     */   public RecipeSerializer<?> getSerializer() {
/*  30 */     return RecipeSerializer.SHAPELESS_RECIPE;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getGroup() {
/*  35 */     return this.group;
/*     */   }
/*     */ 
/*     */   
/*     */   public CraftingBookCategory category() {
/*  40 */     return this.category;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getResultItem(RegistryAccess $$0) {
/*  45 */     return this.result;
/*     */   }
/*     */ 
/*     */   
/*     */   public NonNullList<Ingredient> getIngredients() {
/*  50 */     return this.ingredients;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean matches(CraftingContainer $$0, Level $$1) {
/*  55 */     StackedContents $$2 = new StackedContents();
/*     */     
/*  57 */     int $$3 = 0;
/*  58 */     for (int $$4 = 0; $$4 < $$0.getContainerSize(); $$4++) {
/*  59 */       ItemStack $$5 = $$0.getItem($$4);
/*  60 */       if (!$$5.isEmpty()) {
/*  61 */         $$3++;
/*  62 */         $$2.accountStack($$5, 1);
/*     */       } 
/*     */     } 
/*     */     
/*  66 */     return ($$3 == this.ingredients.size() && $$2.canCraft(this, null));
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack assemble(CraftingContainer $$0, RegistryAccess $$1) {
/*  71 */     return this.result.copy();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canCraftInDimensions(int $$0, int $$1) {
/*  76 */     return ($$0 * $$1 >= this.ingredients.size());
/*     */   }
/*     */   public static class Serializer implements RecipeSerializer<ShapelessRecipe> { private static final Codec<ShapelessRecipe> CODEC;
/*     */     static {
/*  80 */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField((Codec)Codec.STRING, "group", "").forGetter(()), (App)CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter(()), (App)ItemStack.ITEM_WITH_COUNT_CODEC.fieldOf("result").forGetter(()), (App)Ingredient.CODEC_NONEMPTY.listOf().fieldOf("ingredients").flatXmap((), DataResult::success).forGetter(())).apply((Applicative)$$0, ShapelessRecipe::new));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Codec<ShapelessRecipe> codec() {
/*  98 */       return CODEC;
/*     */     }
/*     */ 
/*     */     
/*     */     public ShapelessRecipe fromNetwork(FriendlyByteBuf $$0) {
/* 103 */       String $$1 = $$0.readUtf();
/* 104 */       CraftingBookCategory $$2 = (CraftingBookCategory)$$0.readEnum(CraftingBookCategory.class);
/* 105 */       int $$3 = $$0.readVarInt();
/* 106 */       NonNullList<Ingredient> $$4 = NonNullList.withSize($$3, Ingredient.EMPTY);
/* 107 */       for (int $$5 = 0; $$5 < $$4.size(); $$5++) {
/* 108 */         $$4.set($$5, Ingredient.fromNetwork($$0));
/*     */       }
/* 110 */       ItemStack $$6 = $$0.readItem();
/* 111 */       return new ShapelessRecipe($$1, $$2, $$6, $$4);
/*     */     }
/*     */ 
/*     */     
/*     */     public void toNetwork(FriendlyByteBuf $$0, ShapelessRecipe $$1) {
/* 116 */       $$0.writeUtf($$1.group);
/* 117 */       $$0.writeEnum($$1.category);
/* 118 */       $$0.writeVarInt($$1.ingredients.size());
/* 119 */       for (Ingredient $$2 : $$1.ingredients) {
/* 120 */         $$2.toNetwork($$0);
/*     */       }
/* 122 */       $$0.writeItem($$1.result);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\crafting\ShapelessRecipe.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */