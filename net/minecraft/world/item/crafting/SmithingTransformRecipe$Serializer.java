/*     */ package net.minecraft.world.item.crafting;
/*     */ 
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function4;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraft.world.item.ItemStack;
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
/*     */ public class Serializer
/*     */   implements RecipeSerializer<SmithingTransformRecipe>
/*     */ {
/*     */   private static final Codec<SmithingTransformRecipe> CODEC;
/*     */   
/*     */   static {
/*  76 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Ingredient.CODEC.fieldOf("template").forGetter(()), (App)Ingredient.CODEC.fieldOf("base").forGetter(()), (App)Ingredient.CODEC.fieldOf("addition").forGetter(()), (App)ItemStack.ITEM_WITH_COUNT_CODEC.fieldOf("result").forGetter(())).apply((Applicative)$$0, SmithingTransformRecipe::new));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Codec<SmithingTransformRecipe> codec() {
/*  85 */     return CODEC;
/*     */   }
/*     */ 
/*     */   
/*     */   public SmithingTransformRecipe fromNetwork(FriendlyByteBuf $$0) {
/*  90 */     Ingredient $$1 = Ingredient.fromNetwork($$0);
/*  91 */     Ingredient $$2 = Ingredient.fromNetwork($$0);
/*  92 */     Ingredient $$3 = Ingredient.fromNetwork($$0);
/*  93 */     ItemStack $$4 = $$0.readItem();
/*  94 */     return new SmithingTransformRecipe($$1, $$2, $$3, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   public void toNetwork(FriendlyByteBuf $$0, SmithingTransformRecipe $$1) {
/*  99 */     $$1.template.toNetwork($$0);
/* 100 */     $$1.base.toNetwork($$0);
/* 101 */     $$1.addition.toNetwork($$0);
/* 102 */     $$0.writeItem($$1.result);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\crafting\SmithingTransformRecipe$Serializer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */