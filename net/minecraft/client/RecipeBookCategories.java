/*    */ package net.minecraft.client;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import net.minecraft.world.inventory.RecipeBookType;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.Items;
/*    */ import net.minecraft.world.level.ItemLike;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ 
/*    */ public enum RecipeBookCategories {
/* 14 */   CRAFTING_SEARCH(new ItemStack[] { new ItemStack((ItemLike)Items.COMPASS) }),
/* 15 */   CRAFTING_BUILDING_BLOCKS(new ItemStack[] { new ItemStack((ItemLike)Blocks.BRICKS) }),
/* 16 */   CRAFTING_REDSTONE(new ItemStack[] { new ItemStack((ItemLike)Items.REDSTONE) }),
/* 17 */   CRAFTING_EQUIPMENT(new ItemStack[] { new ItemStack((ItemLike)Items.IRON_AXE), new ItemStack((ItemLike)Items.GOLDEN_SWORD) }),
/* 18 */   CRAFTING_MISC(new ItemStack[] { new ItemStack((ItemLike)Items.LAVA_BUCKET), new ItemStack((ItemLike)Items.APPLE)
/*    */     }),
/* 20 */   FURNACE_SEARCH(new ItemStack[] { new ItemStack((ItemLike)Items.COMPASS) }),
/* 21 */   FURNACE_FOOD(new ItemStack[] { new ItemStack((ItemLike)Items.PORKCHOP) }),
/* 22 */   FURNACE_BLOCKS(new ItemStack[] { new ItemStack((ItemLike)Blocks.STONE) }),
/* 23 */   FURNACE_MISC(new ItemStack[] { new ItemStack((ItemLike)Items.LAVA_BUCKET), new ItemStack((ItemLike)Items.EMERALD)
/*    */     }),
/* 25 */   BLAST_FURNACE_SEARCH(new ItemStack[] { new ItemStack((ItemLike)Items.COMPASS) }),
/* 26 */   BLAST_FURNACE_BLOCKS(new ItemStack[] { new ItemStack((ItemLike)Blocks.REDSTONE_ORE) }),
/* 27 */   BLAST_FURNACE_MISC(new ItemStack[] { new ItemStack((ItemLike)Items.IRON_SHOVEL), new ItemStack((ItemLike)Items.GOLDEN_LEGGINGS)
/*    */     }),
/* 29 */   SMOKER_SEARCH(new ItemStack[] { new ItemStack((ItemLike)Items.COMPASS) }),
/* 30 */   SMOKER_FOOD(new ItemStack[] { new ItemStack((ItemLike)Items.PORKCHOP)
/*    */     }),
/* 32 */   STONECUTTER(new ItemStack[] { new ItemStack((ItemLike)Items.CHISELED_STONE_BRICKS)
/*    */     }),
/* 34 */   SMITHING(new ItemStack[] { new ItemStack((ItemLike)Items.NETHERITE_CHESTPLATE)
/*    */     }),
/* 36 */   CAMPFIRE(new ItemStack[] { new ItemStack((ItemLike)Items.PORKCHOP)
/*    */     }),
/* 38 */   UNKNOWN(new ItemStack[] { new ItemStack((ItemLike)Items.BARRIER) }); public static final List<RecipeBookCategories> SMOKER_CATEGORIES; public static final List<RecipeBookCategories> BLAST_FURNACE_CATEGORIES; public static final List<RecipeBookCategories> FURNACE_CATEGORIES; public static final List<RecipeBookCategories> CRAFTING_CATEGORIES; public static final Map<RecipeBookCategories, List<RecipeBookCategories>> AGGREGATE_CATEGORIES; private final List<ItemStack> itemIcons;
/*    */   
/*    */   static {
/* 41 */     SMOKER_CATEGORIES = (List<RecipeBookCategories>)ImmutableList.of(SMOKER_SEARCH, SMOKER_FOOD);
/* 42 */     BLAST_FURNACE_CATEGORIES = (List<RecipeBookCategories>)ImmutableList.of(BLAST_FURNACE_SEARCH, BLAST_FURNACE_BLOCKS, BLAST_FURNACE_MISC);
/* 43 */     FURNACE_CATEGORIES = (List<RecipeBookCategories>)ImmutableList.of(FURNACE_SEARCH, FURNACE_FOOD, FURNACE_BLOCKS, FURNACE_MISC);
/* 44 */     CRAFTING_CATEGORIES = (List<RecipeBookCategories>)ImmutableList.of(CRAFTING_SEARCH, CRAFTING_EQUIPMENT, CRAFTING_BUILDING_BLOCKS, CRAFTING_MISC, CRAFTING_REDSTONE);
/*    */     
/* 46 */     AGGREGATE_CATEGORIES = (Map<RecipeBookCategories, List<RecipeBookCategories>>)ImmutableMap.of(CRAFTING_SEARCH, 
/* 47 */         ImmutableList.of(CRAFTING_EQUIPMENT, CRAFTING_BUILDING_BLOCKS, CRAFTING_MISC, CRAFTING_REDSTONE), FURNACE_SEARCH, 
/* 48 */         ImmutableList.of(FURNACE_FOOD, FURNACE_BLOCKS, FURNACE_MISC), BLAST_FURNACE_SEARCH, 
/* 49 */         ImmutableList.of(BLAST_FURNACE_BLOCKS, BLAST_FURNACE_MISC), SMOKER_SEARCH, 
/* 50 */         ImmutableList.of(SMOKER_FOOD));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   RecipeBookCategories(ItemStack... $$0) {
/* 56 */     this.itemIcons = (List<ItemStack>)ImmutableList.copyOf((Object[])$$0);
/*    */   }
/*    */   
/*    */   public static List<RecipeBookCategories> getCategories(RecipeBookType $$0) {
/* 60 */     switch ($$0) { default: throw new IncompatibleClassChangeError();case CRAFTING: case FURNACE: case BLAST_FURNACE: case SMOKER: break; }  return 
/*    */ 
/*    */ 
/*    */       
/* 64 */       SMOKER_CATEGORIES;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<ItemStack> getIconItems() {
/* 69 */     return this.itemIcons;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\RecipeBookCategories.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */