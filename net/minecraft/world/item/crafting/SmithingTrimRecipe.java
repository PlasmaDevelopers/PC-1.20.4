/*     */ package net.minecraft.world.item.crafting;
/*     */ 
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function3;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.Optional;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.RegistryAccess;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.armortrim.ArmorTrim;
/*     */ import net.minecraft.world.item.armortrim.TrimMaterial;
/*     */ import net.minecraft.world.item.armortrim.TrimMaterials;
/*     */ import net.minecraft.world.item.armortrim.TrimPattern;
/*     */ import net.minecraft.world.item.armortrim.TrimPatterns;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ 
/*     */ public class SmithingTrimRecipe implements SmithingRecipe {
/*     */   final Ingredient template;
/*     */   
/*     */   public SmithingTrimRecipe(Ingredient $$0, Ingredient $$1, Ingredient $$2) {
/*  29 */     this.template = $$0;
/*  30 */     this.base = $$1;
/*  31 */     this.addition = $$2;
/*     */   }
/*     */   final Ingredient base; final Ingredient addition;
/*     */   
/*     */   public boolean matches(Container $$0, Level $$1) {
/*  36 */     return (this.template.test($$0.getItem(0)) && this.base
/*  37 */       .test($$0.getItem(1)) && this.addition
/*  38 */       .test($$0.getItem(2)));
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack assemble(Container $$0, RegistryAccess $$1) {
/*  43 */     ItemStack $$2 = $$0.getItem(1);
/*  44 */     if (this.base.test($$2)) {
/*  45 */       Optional<Holder.Reference<TrimMaterial>> $$3 = TrimMaterials.getFromIngredient($$1, $$0.getItem(2));
/*  46 */       Optional<Holder.Reference<TrimPattern>> $$4 = TrimPatterns.getFromTemplate($$1, $$0.getItem(0));
/*     */       
/*  48 */       if ($$3.isPresent() && $$4.isPresent()) {
/*  49 */         Optional<ArmorTrim> $$5 = ArmorTrim.getTrim($$1, $$2, false);
/*     */         
/*  51 */         if ($$5.isPresent() && ((ArmorTrim)$$5.get()).hasPatternAndMaterial((Holder)$$4.get(), (Holder)$$3.get())) {
/*  52 */           return ItemStack.EMPTY;
/*     */         }
/*     */         
/*  55 */         ItemStack $$6 = $$2.copy();
/*  56 */         $$6.setCount(1);
/*  57 */         ArmorTrim $$7 = new ArmorTrim((Holder)$$3.get(), (Holder)$$4.get());
/*     */         
/*  59 */         if (ArmorTrim.setTrim($$1, $$6, $$7)) {
/*  60 */           return $$6;
/*     */         }
/*     */       } 
/*     */     } 
/*  64 */     return ItemStack.EMPTY;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getResultItem(RegistryAccess $$0) {
/*  69 */     ItemStack $$1 = new ItemStack((ItemLike)Items.IRON_CHESTPLATE);
/*  70 */     Optional<Holder.Reference<TrimPattern>> $$2 = $$0.registryOrThrow(Registries.TRIM_PATTERN).holders().findFirst();
/*  71 */     if ($$2.isPresent()) {
/*  72 */       Optional<Holder.Reference<TrimMaterial>> $$3 = $$0.registryOrThrow(Registries.TRIM_MATERIAL).getHolder(TrimMaterials.REDSTONE);
/*  73 */       if ($$3.isPresent()) {
/*  74 */         ArmorTrim $$4 = new ArmorTrim((Holder)$$3.get(), (Holder)$$2.get());
/*  75 */         ArmorTrim.setTrim($$0, $$1, $$4);
/*     */       } 
/*     */     } 
/*  78 */     return $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isTemplateIngredient(ItemStack $$0) {
/*  83 */     return this.template.test($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBaseIngredient(ItemStack $$0) {
/*  88 */     return this.base.test($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAdditionIngredient(ItemStack $$0) {
/*  93 */     return this.addition.test($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public RecipeSerializer<?> getSerializer() {
/*  98 */     return RecipeSerializer.SMITHING_TRIM;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isIncomplete() {
/* 103 */     return Stream.<Ingredient>of(new Ingredient[] { this.template, this.base, this.addition }).anyMatch(Ingredient::isEmpty);
/*     */   }
/*     */   
/*     */   public static class Serializer implements RecipeSerializer<SmithingTrimRecipe> { static {
/* 107 */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Ingredient.CODEC.fieldOf("template").forGetter(()), (App)Ingredient.CODEC.fieldOf("base").forGetter(()), (App)Ingredient.CODEC.fieldOf("addition").forGetter(())).apply((Applicative)$$0, SmithingTrimRecipe::new));
/*     */     }
/*     */ 
/*     */     
/*     */     private static final Codec<SmithingTrimRecipe> CODEC;
/*     */ 
/*     */     
/*     */     public Codec<SmithingTrimRecipe> codec() {
/* 115 */       return CODEC;
/*     */     }
/*     */ 
/*     */     
/*     */     public SmithingTrimRecipe fromNetwork(FriendlyByteBuf $$0) {
/* 120 */       Ingredient $$1 = Ingredient.fromNetwork($$0);
/* 121 */       Ingredient $$2 = Ingredient.fromNetwork($$0);
/* 122 */       Ingredient $$3 = Ingredient.fromNetwork($$0);
/* 123 */       return new SmithingTrimRecipe($$1, $$2, $$3);
/*     */     }
/*     */ 
/*     */     
/*     */     public void toNetwork(FriendlyByteBuf $$0, SmithingTrimRecipe $$1) {
/* 128 */       $$1.template.toNetwork($$0);
/* 129 */       $$1.base.toNetwork($$0);
/* 130 */       $$1.addition.toNetwork($$0);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\crafting\SmithingTrimRecipe.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */