/*    */ package net.minecraft.world.item.crafting;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Objects;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Serializer<T extends SingleItemRecipe>
/*    */   implements RecipeSerializer<T>
/*    */ {
/*    */   final SingleItemRecipe.Factory<T> factory;
/*    */   private final Codec<T> codec;
/*    */   
/*    */   protected Serializer(SingleItemRecipe.Factory<T> $$0) {
/* 69 */     this.factory = $$0;
/* 70 */     this.codec = RecordCodecBuilder.create($$1 -> {
/*    */           Objects.requireNonNull($$0);
/*    */           return $$1.group((App)ExtraCodecs.strictOptionalField((Codec)Codec.STRING, "group", "").forGetter(()), (App)Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(()), (App)ItemStack.RESULT_CODEC.forGetter(())).apply((Applicative)$$1, $$0::create);
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Codec<T> codec() {
/* 79 */     return this.codec;
/*    */   }
/*    */ 
/*    */   
/*    */   public T fromNetwork(FriendlyByteBuf $$0) {
/* 84 */     String $$1 = $$0.readUtf();
/* 85 */     Ingredient $$2 = Ingredient.fromNetwork($$0);
/* 86 */     ItemStack $$3 = $$0.readItem();
/* 87 */     return this.factory.create($$1, $$2, $$3);
/*    */   }
/*    */ 
/*    */   
/*    */   public void toNetwork(FriendlyByteBuf $$0, T $$1) {
/* 92 */     $$0.writeUtf(((SingleItemRecipe)$$1).group);
/* 93 */     ((SingleItemRecipe)$$1).ingredient.toNetwork($$0);
/* 94 */     $$0.writeItem(((SingleItemRecipe)$$1).result);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\crafting\SingleItemRecipe$Serializer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */