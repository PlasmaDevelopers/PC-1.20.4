/*     */ package net.minecraft.world.item.crafting;
/*     */ 
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function5;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraft.util.ExtraCodecs;
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
/*     */   implements RecipeSerializer<ShapedRecipe>
/*     */ {
/*     */   public static final Codec<ShapedRecipe> CODEC;
/*     */   
/*     */   static {
/*  93 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField((Codec)Codec.STRING, "group", "").forGetter(()), (App)CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter(()), (App)ShapedRecipePattern.MAP_CODEC.forGetter(()), (App)ItemStack.ITEM_WITH_COUNT_CODEC.fieldOf("result").forGetter(()), (App)ExtraCodecs.strictOptionalField((Codec)Codec.BOOL, "show_notification", Boolean.valueOf(true)).forGetter(())).apply((Applicative)$$0, ShapedRecipe::new));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Codec<ShapedRecipe> codec() {
/* 103 */     return CODEC;
/*     */   }
/*     */ 
/*     */   
/*     */   public ShapedRecipe fromNetwork(FriendlyByteBuf $$0) {
/* 108 */     String $$1 = $$0.readUtf();
/* 109 */     CraftingBookCategory $$2 = (CraftingBookCategory)$$0.readEnum(CraftingBookCategory.class);
/* 110 */     ShapedRecipePattern $$3 = ShapedRecipePattern.fromNetwork($$0);
/* 111 */     ItemStack $$4 = $$0.readItem();
/* 112 */     boolean $$5 = $$0.readBoolean();
/* 113 */     return new ShapedRecipe($$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public void toNetwork(FriendlyByteBuf $$0, ShapedRecipe $$1) {
/* 118 */     $$0.writeUtf($$1.group);
/* 119 */     $$0.writeEnum($$1.category);
/* 120 */     $$1.pattern.toNetwork($$0);
/* 121 */     $$0.writeItem($$1.result);
/* 122 */     $$0.writeBoolean($$1.showNotification);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\crafting\ShapedRecipe$Serializer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */