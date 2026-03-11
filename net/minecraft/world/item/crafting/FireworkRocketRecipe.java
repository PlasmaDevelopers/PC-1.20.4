/*    */ package net.minecraft.world.item.crafting;
/*    */ import net.minecraft.core.RegistryAccess;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.nbt.ListTag;
/*    */ import net.minecraft.world.Container;
/*    */ import net.minecraft.world.inventory.CraftingContainer;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.Items;
/*    */ import net.minecraft.world.level.ItemLike;
/*    */ import net.minecraft.world.level.Level;
/*    */ 
/*    */ public class FireworkRocketRecipe extends CustomRecipe {
/* 13 */   private static final Ingredient PAPER_INGREDIENT = Ingredient.of(new ItemLike[] { (ItemLike)Items.PAPER });
/* 14 */   private static final Ingredient GUNPOWDER_INGREDIENT = Ingredient.of(new ItemLike[] { (ItemLike)Items.GUNPOWDER });
/* 15 */   private static final Ingredient STAR_INGREDIENT = Ingredient.of(new ItemLike[] { (ItemLike)Items.FIREWORK_STAR });
/*    */   
/*    */   public FireworkRocketRecipe(CraftingBookCategory $$0) {
/* 18 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(CraftingContainer $$0, Level $$1) {
/* 23 */     boolean $$2 = false;
/* 24 */     int $$3 = 0;
/*    */     
/* 26 */     for (int $$4 = 0; $$4 < $$0.getContainerSize(); $$4++) {
/* 27 */       ItemStack $$5 = $$0.getItem($$4);
/* 28 */       if (!$$5.isEmpty())
/*    */       {
/*    */ 
/*    */         
/* 32 */         if (PAPER_INGREDIENT.test($$5)) {
/* 33 */           if ($$2) {
/* 34 */             return false;
/*    */           }
/* 36 */           $$2 = true;
/* 37 */         } else if (GUNPOWDER_INGREDIENT.test($$5)) {
/* 38 */           $$3++;
/* 39 */           if ($$3 > 3) {
/* 40 */             return false;
/*    */           }
/* 42 */         } else if (!STAR_INGREDIENT.test($$5)) {
/* 43 */           return false;
/*    */         } 
/*    */       }
/*    */     } 
/* 47 */     return ($$2 && $$3 >= 1);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack assemble(CraftingContainer $$0, RegistryAccess $$1) {
/* 52 */     ItemStack $$2 = new ItemStack((ItemLike)Items.FIREWORK_ROCKET, 3);
/* 53 */     CompoundTag $$3 = $$2.getOrCreateTagElement("Fireworks");
/* 54 */     ListTag $$4 = new ListTag();
/*    */     
/* 56 */     int $$5 = 0;
/*    */     
/* 58 */     for (int $$6 = 0; $$6 < $$0.getContainerSize(); $$6++) {
/* 59 */       ItemStack $$7 = $$0.getItem($$6);
/* 60 */       if (!$$7.isEmpty())
/*    */       {
/*    */ 
/*    */         
/* 64 */         if (GUNPOWDER_INGREDIENT.test($$7)) {
/* 65 */           $$5++;
/* 66 */         } else if (STAR_INGREDIENT.test($$7)) {
/* 67 */           CompoundTag $$8 = $$7.getTagElement("Explosion");
/* 68 */           if ($$8 != null) {
/* 69 */             $$4.add($$8);
/*    */           }
/*    */         } 
/*    */       }
/*    */     } 
/* 74 */     $$3.putByte("Flight", (byte)$$5);
/* 75 */     if (!$$4.isEmpty()) {
/* 76 */       $$3.put("Explosions", (Tag)$$4);
/*    */     }
/*    */     
/* 79 */     return $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canCraftInDimensions(int $$0, int $$1) {
/* 84 */     return ($$0 * $$1 >= 2);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getResultItem(RegistryAccess $$0) {
/* 89 */     return new ItemStack((ItemLike)Items.FIREWORK_ROCKET);
/*    */   }
/*    */ 
/*    */   
/*    */   public RecipeSerializer<?> getSerializer() {
/* 94 */     return RecipeSerializer.FIREWORK_ROCKET;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\crafting\FireworkRocketRecipe.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */