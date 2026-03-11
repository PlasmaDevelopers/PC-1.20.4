/*    */ package net.minecraft.world.item.crafting;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Objects;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ 
/*    */ public class SimpleCraftingRecipeSerializer<T extends CraftingRecipe> implements RecipeSerializer<T> {
/*    */   private final Factory<T> constructor;
/*    */   
/*    */   public SimpleCraftingRecipeSerializer(Factory<T> $$0) {
/* 12 */     this.constructor = $$0;
/* 13 */     this.codec = RecordCodecBuilder.create($$1 -> {
/*    */           Objects.requireNonNull($$0);
/*    */           return $$1.group((App)CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter(CraftingRecipe::category)).apply((Applicative)$$1, $$0::create);
/*    */         });
/*    */   }
/*    */   private final Codec<T> codec;
/*    */   public Codec<T> codec() {
/* 20 */     return this.codec;
/*    */   }
/*    */ 
/*    */   
/*    */   public T fromNetwork(FriendlyByteBuf $$0) {
/* 25 */     CraftingBookCategory $$1 = (CraftingBookCategory)$$0.readEnum(CraftingBookCategory.class);
/* 26 */     return this.constructor.create($$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public void toNetwork(FriendlyByteBuf $$0, T $$1) {
/* 31 */     $$0.writeEnum($$1.category());
/*    */   }
/*    */   
/*    */   @FunctionalInterface
/*    */   public static interface Factory<T extends CraftingRecipe> {
/*    */     T create(CraftingBookCategory param1CraftingBookCategory);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\crafting\SimpleCraftingRecipeSerializer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */