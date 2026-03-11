/*    */ package net.minecraft.world.item.crafting;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Objects;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ public class SimpleCookingSerializer<T extends AbstractCookingRecipe> implements RecipeSerializer<T> {
/*    */   public SimpleCookingSerializer(AbstractCookingRecipe.Factory<T> $$0, int $$1) {
/* 15 */     this.factory = $$0;
/* 16 */     this.codec = RecordCodecBuilder.create($$2 -> {
/*    */           Objects.requireNonNull($$1);
/*    */           return $$2.group((App)ExtraCodecs.strictOptionalField((Codec)Codec.STRING, "group", "").forGetter(()), (App)CookingBookCategory.CODEC.fieldOf("category").orElse(CookingBookCategory.MISC).forGetter(()), (App)Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(()), (App)BuiltInRegistries.ITEM.byNameCodec().xmap(ItemStack::new, ItemStack::getItem).fieldOf("result").forGetter(()), (App)Codec.FLOAT.fieldOf("experience").orElse(Float.valueOf(0.0F)).forGetter(()), (App)Codec.INT.fieldOf("cookingtime").orElse(Integer.valueOf($$0)).forGetter(())).apply((Applicative)$$2, $$1::create);
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   private final AbstractCookingRecipe.Factory<T> factory;
/*    */   
/*    */   private final Codec<T> codec;
/*    */   
/*    */   public Codec<T> codec() {
/* 28 */     return this.codec;
/*    */   }
/*    */ 
/*    */   
/*    */   public T fromNetwork(FriendlyByteBuf $$0) {
/* 33 */     String $$1 = $$0.readUtf();
/* 34 */     CookingBookCategory $$2 = (CookingBookCategory)$$0.readEnum(CookingBookCategory.class);
/* 35 */     Ingredient $$3 = Ingredient.fromNetwork($$0);
/* 36 */     ItemStack $$4 = $$0.readItem();
/* 37 */     float $$5 = $$0.readFloat();
/* 38 */     int $$6 = $$0.readVarInt();
/* 39 */     return this.factory.create($$1, $$2, $$3, $$4, $$5, $$6);
/*    */   }
/*    */ 
/*    */   
/*    */   public void toNetwork(FriendlyByteBuf $$0, T $$1) {
/* 44 */     $$0.writeUtf(((AbstractCookingRecipe)$$1).group);
/* 45 */     $$0.writeEnum($$1.category());
/* 46 */     ((AbstractCookingRecipe)$$1).ingredient.toNetwork($$0);
/* 47 */     $$0.writeItem(((AbstractCookingRecipe)$$1).result);
/* 48 */     $$0.writeFloat(((AbstractCookingRecipe)$$1).experience);
/* 49 */     $$0.writeVarInt(((AbstractCookingRecipe)$$1).cookingTime);
/*    */   }
/*    */   
/*    */   public AbstractCookingRecipe create(String $$0, CookingBookCategory $$1, Ingredient $$2, ItemStack $$3, float $$4, int $$5) {
/* 53 */     return (AbstractCookingRecipe)this.factory.create($$0, $$1, $$2, $$3, $$4, $$5);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\crafting\SimpleCookingSerializer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */