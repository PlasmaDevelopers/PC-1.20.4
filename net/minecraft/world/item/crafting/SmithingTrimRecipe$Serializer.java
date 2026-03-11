/*     */ package net.minecraft.world.item.crafting;
/*     */ 
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function3;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import net.minecraft.network.FriendlyByteBuf;
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
/*     */   implements RecipeSerializer<SmithingTrimRecipe>
/*     */ {
/*     */   private static final Codec<SmithingTrimRecipe> CODEC;
/*     */   
/*     */   static {
/* 107 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Ingredient.CODEC.fieldOf("template").forGetter(()), (App)Ingredient.CODEC.fieldOf("base").forGetter(()), (App)Ingredient.CODEC.fieldOf("addition").forGetter(())).apply((Applicative)$$0, SmithingTrimRecipe::new));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Codec<SmithingTrimRecipe> codec() {
/* 115 */     return CODEC;
/*     */   }
/*     */ 
/*     */   
/*     */   public SmithingTrimRecipe fromNetwork(FriendlyByteBuf $$0) {
/* 120 */     Ingredient $$1 = Ingredient.fromNetwork($$0);
/* 121 */     Ingredient $$2 = Ingredient.fromNetwork($$0);
/* 122 */     Ingredient $$3 = Ingredient.fromNetwork($$0);
/* 123 */     return new SmithingTrimRecipe($$1, $$2, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public void toNetwork(FriendlyByteBuf $$0, SmithingTrimRecipe $$1) {
/* 128 */     $$1.template.toNetwork($$0);
/* 129 */     $$1.base.toNetwork($$0);
/* 130 */     $$1.addition.toNetwork($$0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\crafting\SmithingTrimRecipe$Serializer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */