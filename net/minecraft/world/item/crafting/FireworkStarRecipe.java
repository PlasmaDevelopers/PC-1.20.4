/*     */ package net.minecraft.world.item.crafting;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.core.RegistryAccess;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.inventory.CraftingContainer;
/*     */ import net.minecraft.world.item.DyeItem;
/*     */ import net.minecraft.world.item.FireworkRocketItem;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ 
/*     */ public class FireworkStarRecipe extends CustomRecipe {
/*  20 */   private static final Ingredient SHAPE_INGREDIENT = Ingredient.of(new ItemLike[] { (ItemLike)Items.FIRE_CHARGE, (ItemLike)Items.FEATHER, (ItemLike)Items.GOLD_NUGGET, (ItemLike)Items.SKELETON_SKULL, (ItemLike)Items.WITHER_SKELETON_SKULL, (ItemLike)Items.CREEPER_HEAD, (ItemLike)Items.PLAYER_HEAD, (ItemLike)Items.DRAGON_HEAD, (ItemLike)Items.ZOMBIE_HEAD, (ItemLike)Items.PIGLIN_HEAD });
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
/*  32 */   private static final Ingredient TRAIL_INGREDIENT = Ingredient.of(new ItemLike[] { (ItemLike)Items.DIAMOND });
/*  33 */   private static final Ingredient FLICKER_INGREDIENT = Ingredient.of(new ItemLike[] { (ItemLike)Items.GLOWSTONE_DUST }); private static final Map<Item, FireworkRocketItem.Shape> SHAPE_BY_ITEM;
/*     */   static {
/*  35 */     SHAPE_BY_ITEM = (Map<Item, FireworkRocketItem.Shape>)Util.make(Maps.newHashMap(), $$0 -> {
/*     */           $$0.put(Items.FIRE_CHARGE, FireworkRocketItem.Shape.LARGE_BALL);
/*     */           $$0.put(Items.FEATHER, FireworkRocketItem.Shape.BURST);
/*     */           $$0.put(Items.GOLD_NUGGET, FireworkRocketItem.Shape.STAR);
/*     */           $$0.put(Items.SKELETON_SKULL, FireworkRocketItem.Shape.CREEPER);
/*     */           $$0.put(Items.WITHER_SKELETON_SKULL, FireworkRocketItem.Shape.CREEPER);
/*     */           $$0.put(Items.CREEPER_HEAD, FireworkRocketItem.Shape.CREEPER);
/*     */           $$0.put(Items.PLAYER_HEAD, FireworkRocketItem.Shape.CREEPER);
/*     */           $$0.put(Items.DRAGON_HEAD, FireworkRocketItem.Shape.CREEPER);
/*     */           $$0.put(Items.ZOMBIE_HEAD, FireworkRocketItem.Shape.CREEPER);
/*     */           $$0.put(Items.PIGLIN_HEAD, FireworkRocketItem.Shape.CREEPER);
/*     */         });
/*     */   }
/*  48 */   private static final Ingredient GUNPOWDER_INGREDIENT = Ingredient.of(new ItemLike[] { (ItemLike)Items.GUNPOWDER });
/*     */   
/*     */   public FireworkStarRecipe(CraftingBookCategory $$0) {
/*  51 */     super($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean matches(CraftingContainer $$0, Level $$1) {
/*  56 */     boolean $$2 = false;
/*  57 */     boolean $$3 = false;
/*  58 */     boolean $$4 = false;
/*  59 */     boolean $$5 = false;
/*  60 */     boolean $$6 = false;
/*     */     
/*  62 */     for (int $$7 = 0; $$7 < $$0.getContainerSize(); $$7++) {
/*  63 */       ItemStack $$8 = $$0.getItem($$7);
/*  64 */       if (!$$8.isEmpty())
/*     */       {
/*     */ 
/*     */         
/*  68 */         if (SHAPE_INGREDIENT.test($$8)) {
/*  69 */           if ($$4) {
/*  70 */             return false;
/*     */           }
/*  72 */           $$4 = true;
/*  73 */         } else if (FLICKER_INGREDIENT.test($$8)) {
/*  74 */           if ($$6) {
/*  75 */             return false;
/*     */           }
/*  77 */           $$6 = true;
/*  78 */         } else if (TRAIL_INGREDIENT.test($$8)) {
/*  79 */           if ($$5) {
/*  80 */             return false;
/*     */           }
/*  82 */           $$5 = true;
/*  83 */         } else if (GUNPOWDER_INGREDIENT.test($$8)) {
/*  84 */           if ($$2) {
/*  85 */             return false;
/*     */           }
/*  87 */           $$2 = true;
/*  88 */         } else if ($$8.getItem() instanceof DyeItem) {
/*  89 */           $$3 = true;
/*     */         } else {
/*  91 */           return false;
/*     */         } 
/*     */       }
/*     */     } 
/*  95 */     return ($$2 && $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack assemble(CraftingContainer $$0, RegistryAccess $$1) {
/* 100 */     ItemStack $$2 = new ItemStack((ItemLike)Items.FIREWORK_STAR);
/* 101 */     CompoundTag $$3 = $$2.getOrCreateTagElement("Explosion");
/*     */     
/* 103 */     FireworkRocketItem.Shape $$4 = FireworkRocketItem.Shape.SMALL_BALL;
/* 104 */     List<Integer> $$5 = Lists.newArrayList();
/*     */     
/* 106 */     for (int $$6 = 0; $$6 < $$0.getContainerSize(); $$6++) {
/* 107 */       ItemStack $$7 = $$0.getItem($$6);
/* 108 */       if (!$$7.isEmpty())
/*     */       {
/*     */ 
/*     */         
/* 112 */         if (SHAPE_INGREDIENT.test($$7)) {
/* 113 */           $$4 = SHAPE_BY_ITEM.get($$7.getItem());
/* 114 */         } else if (FLICKER_INGREDIENT.test($$7)) {
/* 115 */           $$3.putBoolean("Flicker", true);
/* 116 */         } else if (TRAIL_INGREDIENT.test($$7)) {
/* 117 */           $$3.putBoolean("Trail", true);
/* 118 */         } else if ($$7.getItem() instanceof DyeItem) {
/* 119 */           $$5.add(Integer.valueOf(((DyeItem)$$7.getItem()).getDyeColor().getFireworkColor()));
/*     */         } 
/*     */       }
/*     */     } 
/* 123 */     $$3.putIntArray("Colors", $$5);
/* 124 */     $$3.putByte("Type", (byte)$$4.getId());
/*     */     
/* 126 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canCraftInDimensions(int $$0, int $$1) {
/* 131 */     return ($$0 * $$1 >= 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getResultItem(RegistryAccess $$0) {
/* 136 */     return new ItemStack((ItemLike)Items.FIREWORK_STAR);
/*     */   }
/*     */ 
/*     */   
/*     */   public RecipeSerializer<?> getSerializer() {
/* 141 */     return RecipeSerializer.FIREWORK_STAR;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\crafting\FireworkStarRecipe.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */