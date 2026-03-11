/*     */ package net.minecraft.world.item.alchemy;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import java.util.function.Predicate;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.crafting.Ingredient;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ 
/*     */ 
/*     */ public class PotionBrewing
/*     */ {
/*     */   public static final int BREWING_TIME_SECONDS = 20;
/*  17 */   private static final List<Mix<Potion>> POTION_MIXES = Lists.newArrayList();
/*  18 */   private static final List<Mix<Item>> CONTAINER_MIXES = Lists.newArrayList();
/*     */   
/*  20 */   private static final List<Ingredient> ALLOWED_CONTAINERS = Lists.newArrayList(); static {
/*  21 */     ALLOWED_CONTAINER = ($$0 -> {
/*     */         for (Ingredient $$1 : ALLOWED_CONTAINERS) {
/*     */           if ($$1.test($$0))
/*     */             return true; 
/*     */         } 
/*     */         return false;
/*     */       });
/*     */   }
/*     */   private static final Predicate<ItemStack> ALLOWED_CONTAINER;
/*     */   public static boolean isIngredient(ItemStack $$0) {
/*  31 */     return (isContainerIngredient($$0) || isPotionIngredient($$0));
/*     */   }
/*     */   
/*     */   protected static boolean isContainerIngredient(ItemStack $$0) {
/*  35 */     for (Mix<Item> $$1 : CONTAINER_MIXES) {
/*  36 */       if ($$1.ingredient.test($$0)) {
/*  37 */         return true;
/*     */       }
/*     */     } 
/*  40 */     return false;
/*     */   }
/*     */   
/*     */   protected static boolean isPotionIngredient(ItemStack $$0) {
/*  44 */     for (Mix<Potion> $$1 : POTION_MIXES) {
/*  45 */       if ($$1.ingredient.test($$0)) {
/*  46 */         return true;
/*     */       }
/*     */     } 
/*  49 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean isBrewablePotion(Potion $$0) {
/*  53 */     for (Mix<Potion> $$1 : POTION_MIXES) {
/*  54 */       if ($$1.to == $$0) {
/*  55 */         return true;
/*     */       }
/*     */     } 
/*  58 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean hasMix(ItemStack $$0, ItemStack $$1) {
/*  63 */     if (!ALLOWED_CONTAINER.test($$0)) {
/*  64 */       return false;
/*     */     }
/*     */     
/*  67 */     return (hasContainerMix($$0, $$1) || hasPotionMix($$0, $$1));
/*     */   }
/*     */   
/*     */   protected static boolean hasContainerMix(ItemStack $$0, ItemStack $$1) {
/*  71 */     Item $$2 = $$0.getItem();
/*  72 */     for (Mix<Item> $$3 : CONTAINER_MIXES) {
/*  73 */       if ($$3.from == $$2 && $$3.ingredient.test($$1)) {
/*  74 */         return true;
/*     */       }
/*     */     } 
/*  77 */     return false;
/*     */   }
/*     */   
/*     */   protected static boolean hasPotionMix(ItemStack $$0, ItemStack $$1) {
/*  81 */     Potion $$2 = PotionUtils.getPotion($$0);
/*  82 */     for (Mix<Potion> $$3 : POTION_MIXES) {
/*  83 */       if ($$3.from == $$2 && $$3.ingredient.test($$1)) {
/*  84 */         return true;
/*     */       }
/*     */     } 
/*  87 */     return false;
/*     */   }
/*     */   
/*     */   public static ItemStack mix(ItemStack $$0, ItemStack $$1) {
/*  91 */     if (!$$1.isEmpty()) {
/*  92 */       Potion $$2 = PotionUtils.getPotion($$1);
/*  93 */       Item $$3 = $$1.getItem();
/*  94 */       for (Mix<Item> $$4 : CONTAINER_MIXES) {
/*  95 */         if ($$4.from == $$3 && $$4.ingredient.test($$0)) {
/*  96 */           return PotionUtils.setPotion(new ItemStack((ItemLike)$$4.to), $$2);
/*     */         }
/*     */       } 
/*     */       
/* 100 */       for (Mix<Potion> $$5 : POTION_MIXES) {
/* 101 */         if ($$5.from == $$2 && $$5.ingredient.test($$0)) {
/* 102 */           return PotionUtils.setPotion(new ItemStack((ItemLike)$$3), (Potion)$$5.to);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 107 */     return $$1;
/*     */   }
/*     */   
/*     */   public static void bootStrap() {
/* 111 */     addContainer(Items.POTION);
/* 112 */     addContainer(Items.SPLASH_POTION);
/* 113 */     addContainer(Items.LINGERING_POTION);
/*     */     
/* 115 */     addContainerRecipe(Items.POTION, Items.GUNPOWDER, Items.SPLASH_POTION);
/* 116 */     addContainerRecipe(Items.SPLASH_POTION, Items.DRAGON_BREATH, Items.LINGERING_POTION);
/*     */     
/* 118 */     addMix(Potions.WATER, Items.GLISTERING_MELON_SLICE, Potions.MUNDANE);
/* 119 */     addMix(Potions.WATER, Items.GHAST_TEAR, Potions.MUNDANE);
/* 120 */     addMix(Potions.WATER, Items.RABBIT_FOOT, Potions.MUNDANE);
/* 121 */     addMix(Potions.WATER, Items.BLAZE_POWDER, Potions.MUNDANE);
/* 122 */     addMix(Potions.WATER, Items.SPIDER_EYE, Potions.MUNDANE);
/* 123 */     addMix(Potions.WATER, Items.SUGAR, Potions.MUNDANE);
/* 124 */     addMix(Potions.WATER, Items.MAGMA_CREAM, Potions.MUNDANE);
/*     */     
/* 126 */     addMix(Potions.WATER, Items.GLOWSTONE_DUST, Potions.THICK);
/*     */     
/* 128 */     addMix(Potions.WATER, Items.REDSTONE, Potions.MUNDANE);
/*     */     
/* 130 */     addMix(Potions.WATER, Items.NETHER_WART, Potions.AWKWARD);
/*     */     
/* 132 */     addMix(Potions.AWKWARD, Items.GOLDEN_CARROT, Potions.NIGHT_VISION);
/* 133 */     addMix(Potions.NIGHT_VISION, Items.REDSTONE, Potions.LONG_NIGHT_VISION);
/*     */     
/* 135 */     addMix(Potions.NIGHT_VISION, Items.FERMENTED_SPIDER_EYE, Potions.INVISIBILITY);
/* 136 */     addMix(Potions.LONG_NIGHT_VISION, Items.FERMENTED_SPIDER_EYE, Potions.LONG_INVISIBILITY);
/*     */     
/* 138 */     addMix(Potions.INVISIBILITY, Items.REDSTONE, Potions.LONG_INVISIBILITY);
/*     */     
/* 140 */     addMix(Potions.AWKWARD, Items.MAGMA_CREAM, Potions.FIRE_RESISTANCE);
/* 141 */     addMix(Potions.FIRE_RESISTANCE, Items.REDSTONE, Potions.LONG_FIRE_RESISTANCE);
/*     */     
/* 143 */     addMix(Potions.AWKWARD, Items.RABBIT_FOOT, Potions.LEAPING);
/* 144 */     addMix(Potions.LEAPING, Items.REDSTONE, Potions.LONG_LEAPING);
/* 145 */     addMix(Potions.LEAPING, Items.GLOWSTONE_DUST, Potions.STRONG_LEAPING);
/*     */     
/* 147 */     addMix(Potions.LEAPING, Items.FERMENTED_SPIDER_EYE, Potions.SLOWNESS);
/* 148 */     addMix(Potions.LONG_LEAPING, Items.FERMENTED_SPIDER_EYE, Potions.LONG_SLOWNESS);
/*     */     
/* 150 */     addMix(Potions.SLOWNESS, Items.REDSTONE, Potions.LONG_SLOWNESS);
/*     */     
/* 152 */     addMix(Potions.SLOWNESS, Items.GLOWSTONE_DUST, Potions.STRONG_SLOWNESS);
/* 153 */     addMix(Potions.AWKWARD, Items.TURTLE_HELMET, Potions.TURTLE_MASTER);
/* 154 */     addMix(Potions.TURTLE_MASTER, Items.REDSTONE, Potions.LONG_TURTLE_MASTER);
/* 155 */     addMix(Potions.TURTLE_MASTER, Items.GLOWSTONE_DUST, Potions.STRONG_TURTLE_MASTER);
/*     */     
/* 157 */     addMix(Potions.SWIFTNESS, Items.FERMENTED_SPIDER_EYE, Potions.SLOWNESS);
/* 158 */     addMix(Potions.LONG_SWIFTNESS, Items.FERMENTED_SPIDER_EYE, Potions.LONG_SLOWNESS);
/*     */     
/* 160 */     addMix(Potions.AWKWARD, Items.SUGAR, Potions.SWIFTNESS);
/* 161 */     addMix(Potions.SWIFTNESS, Items.REDSTONE, Potions.LONG_SWIFTNESS);
/* 162 */     addMix(Potions.SWIFTNESS, Items.GLOWSTONE_DUST, Potions.STRONG_SWIFTNESS);
/*     */     
/* 164 */     addMix(Potions.AWKWARD, Items.PUFFERFISH, Potions.WATER_BREATHING);
/* 165 */     addMix(Potions.WATER_BREATHING, Items.REDSTONE, Potions.LONG_WATER_BREATHING);
/*     */     
/* 167 */     addMix(Potions.AWKWARD, Items.GLISTERING_MELON_SLICE, Potions.HEALING);
/* 168 */     addMix(Potions.HEALING, Items.GLOWSTONE_DUST, Potions.STRONG_HEALING);
/*     */     
/* 170 */     addMix(Potions.HEALING, Items.FERMENTED_SPIDER_EYE, Potions.HARMING);
/* 171 */     addMix(Potions.STRONG_HEALING, Items.FERMENTED_SPIDER_EYE, Potions.STRONG_HARMING);
/*     */     
/* 173 */     addMix(Potions.HARMING, Items.GLOWSTONE_DUST, Potions.STRONG_HARMING);
/*     */     
/* 175 */     addMix(Potions.POISON, Items.FERMENTED_SPIDER_EYE, Potions.HARMING);
/* 176 */     addMix(Potions.LONG_POISON, Items.FERMENTED_SPIDER_EYE, Potions.HARMING);
/* 177 */     addMix(Potions.STRONG_POISON, Items.FERMENTED_SPIDER_EYE, Potions.STRONG_HARMING);
/*     */     
/* 179 */     addMix(Potions.AWKWARD, Items.SPIDER_EYE, Potions.POISON);
/* 180 */     addMix(Potions.POISON, Items.REDSTONE, Potions.LONG_POISON);
/* 181 */     addMix(Potions.POISON, Items.GLOWSTONE_DUST, Potions.STRONG_POISON);
/*     */     
/* 183 */     addMix(Potions.AWKWARD, Items.GHAST_TEAR, Potions.REGENERATION);
/* 184 */     addMix(Potions.REGENERATION, Items.REDSTONE, Potions.LONG_REGENERATION);
/* 185 */     addMix(Potions.REGENERATION, Items.GLOWSTONE_DUST, Potions.STRONG_REGENERATION);
/*     */     
/* 187 */     addMix(Potions.AWKWARD, Items.BLAZE_POWDER, Potions.STRENGTH);
/* 188 */     addMix(Potions.STRENGTH, Items.REDSTONE, Potions.LONG_STRENGTH);
/* 189 */     addMix(Potions.STRENGTH, Items.GLOWSTONE_DUST, Potions.STRONG_STRENGTH);
/*     */     
/* 191 */     addMix(Potions.WATER, Items.FERMENTED_SPIDER_EYE, Potions.WEAKNESS);
/* 192 */     addMix(Potions.WEAKNESS, Items.REDSTONE, Potions.LONG_WEAKNESS);
/*     */     
/* 194 */     addMix(Potions.AWKWARD, Items.PHANTOM_MEMBRANE, Potions.SLOW_FALLING);
/* 195 */     addMix(Potions.SLOW_FALLING, Items.REDSTONE, Potions.LONG_SLOW_FALLING);
/*     */   }
/*     */   
/*     */   private static void addContainerRecipe(Item $$0, Item $$1, Item $$2) {
/* 199 */     if (!($$0 instanceof net.minecraft.world.item.PotionItem)) {
/* 200 */       throw new IllegalArgumentException("Expected a potion, got: " + BuiltInRegistries.ITEM.getKey($$0));
/*     */     }
/* 202 */     if (!($$2 instanceof net.minecraft.world.item.PotionItem)) {
/* 203 */       throw new IllegalArgumentException("Expected a potion, got: " + BuiltInRegistries.ITEM.getKey($$2));
/*     */     }
/* 205 */     CONTAINER_MIXES.add(new Mix<>($$0, Ingredient.of(new ItemLike[] { (ItemLike)$$1 }, ), $$2));
/*     */   }
/*     */   
/*     */   private static void addContainer(Item $$0) {
/* 209 */     if (!($$0 instanceof net.minecraft.world.item.PotionItem)) {
/* 210 */       throw new IllegalArgumentException("Expected a potion, got: " + BuiltInRegistries.ITEM.getKey($$0));
/*     */     }
/* 212 */     ALLOWED_CONTAINERS.add(Ingredient.of(new ItemLike[] { (ItemLike)$$0 }));
/*     */   }
/*     */   
/*     */   private static void addMix(Potion $$0, Item $$1, Potion $$2) {
/* 216 */     POTION_MIXES.add(new Mix<>($$0, Ingredient.of(new ItemLike[] { (ItemLike)$$1 }, ), $$2));
/*     */   }
/*     */   
/*     */   private static class Mix<T> {
/*     */     final T from;
/*     */     final Ingredient ingredient;
/*     */     final T to;
/*     */     
/*     */     public Mix(T $$0, Ingredient $$1, T $$2) {
/* 225 */       this.from = $$0;
/* 226 */       this.ingredient = $$1;
/* 227 */       this.to = $$2;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\alchemy\PotionBrewing.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */