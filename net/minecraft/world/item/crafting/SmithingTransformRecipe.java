/*     */ package net.minecraft.world.item.crafting;
/*     */ 
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function4;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.core.RegistryAccess;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.Level;
/*     */ 
/*     */ public class SmithingTransformRecipe
/*     */   implements SmithingRecipe {
/*     */   final Ingredient template;
/*     */   final Ingredient base;
/*     */   
/*     */   public SmithingTransformRecipe(Ingredient $$0, Ingredient $$1, Ingredient $$2, ItemStack $$3) {
/*  22 */     this.template = $$0;
/*  23 */     this.base = $$1;
/*  24 */     this.addition = $$2;
/*  25 */     this.result = $$3;
/*     */   }
/*     */   final Ingredient addition; final ItemStack result;
/*     */   
/*     */   public boolean matches(Container $$0, Level $$1) {
/*  30 */     return (this.template.test($$0.getItem(0)) && this.base
/*  31 */       .test($$0.getItem(1)) && this.addition
/*  32 */       .test($$0.getItem(2)));
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack assemble(Container $$0, RegistryAccess $$1) {
/*  37 */     ItemStack $$2 = this.result.copy();
/*  38 */     CompoundTag $$3 = $$0.getItem(1).getTag();
/*  39 */     if ($$3 != null) {
/*  40 */       $$2.setTag($$3.copy());
/*     */     }
/*  42 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getResultItem(RegistryAccess $$0) {
/*  47 */     return this.result;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isTemplateIngredient(ItemStack $$0) {
/*  52 */     return this.template.test($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBaseIngredient(ItemStack $$0) {
/*  57 */     return this.base.test($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAdditionIngredient(ItemStack $$0) {
/*  62 */     return this.addition.test($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public RecipeSerializer<?> getSerializer() {
/*  67 */     return RecipeSerializer.SMITHING_TRANSFORM;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isIncomplete() {
/*  72 */     return Stream.<Ingredient>of(new Ingredient[] { this.template, this.base, this.addition }).anyMatch(Ingredient::isEmpty);
/*     */   }
/*     */   
/*     */   public static class Serializer implements RecipeSerializer<SmithingTransformRecipe> { static {
/*  76 */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Ingredient.CODEC.fieldOf("template").forGetter(()), (App)Ingredient.CODEC.fieldOf("base").forGetter(()), (App)Ingredient.CODEC.fieldOf("addition").forGetter(()), (App)ItemStack.ITEM_WITH_COUNT_CODEC.fieldOf("result").forGetter(())).apply((Applicative)$$0, SmithingTransformRecipe::new));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private static final Codec<SmithingTransformRecipe> CODEC;
/*     */ 
/*     */     
/*     */     public Codec<SmithingTransformRecipe> codec() {
/*  85 */       return CODEC;
/*     */     }
/*     */ 
/*     */     
/*     */     public SmithingTransformRecipe fromNetwork(FriendlyByteBuf $$0) {
/*  90 */       Ingredient $$1 = Ingredient.fromNetwork($$0);
/*  91 */       Ingredient $$2 = Ingredient.fromNetwork($$0);
/*  92 */       Ingredient $$3 = Ingredient.fromNetwork($$0);
/*  93 */       ItemStack $$4 = $$0.readItem();
/*  94 */       return new SmithingTransformRecipe($$1, $$2, $$3, $$4);
/*     */     }
/*     */ 
/*     */     
/*     */     public void toNetwork(FriendlyByteBuf $$0, SmithingTransformRecipe $$1) {
/*  99 */       $$1.template.toNetwork($$0);
/* 100 */       $$1.base.toNetwork($$0);
/* 101 */       $$1.addition.toNetwork($$0);
/* 102 */       $$0.writeItem($$1.result);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\crafting\SmithingTransformRecipe.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */