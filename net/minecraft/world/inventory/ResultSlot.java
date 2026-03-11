/*    */ package net.minecraft.world.inventory;
/*    */ 
/*    */ import net.minecraft.core.NonNullList;
/*    */ import net.minecraft.world.Container;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.crafting.RecipeType;
/*    */ 
/*    */ public class ResultSlot extends Slot {
/*    */   private final CraftingContainer craftSlots;
/*    */   private final Player player;
/*    */   private int removeCount;
/*    */   
/*    */   public ResultSlot(Player $$0, CraftingContainer $$1, Container $$2, int $$3, int $$4, int $$5) {
/* 15 */     super($$2, $$3, $$4, $$5);
/* 16 */     this.player = $$0;
/* 17 */     this.craftSlots = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean mayPlace(ItemStack $$0) {
/* 22 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack remove(int $$0) {
/* 27 */     if (hasItem()) {
/* 28 */       this.removeCount += Math.min($$0, getItem().getCount());
/*    */     }
/* 30 */     return super.remove($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onQuickCraft(ItemStack $$0, int $$1) {
/* 35 */     this.removeCount += $$1;
/* 36 */     checkTakeAchievements($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onSwapCraft(int $$0) {
/* 41 */     this.removeCount += $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void checkTakeAchievements(ItemStack $$0) {
/* 46 */     if (this.removeCount > 0) {
/* 47 */       $$0.onCraftedBy(this.player.level(), this.player, this.removeCount);
/*    */     }
/* 49 */     Container container = this.container; if (container instanceof RecipeCraftingHolder) { RecipeCraftingHolder $$1 = (RecipeCraftingHolder)container;
/* 50 */       $$1.awardUsedRecipes(this.player, this.craftSlots.getItems()); }
/*    */     
/* 52 */     this.removeCount = 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onTake(Player $$0, ItemStack $$1) {
/* 57 */     checkTakeAchievements($$1);
/*    */     
/* 59 */     NonNullList<ItemStack> $$2 = $$0.level().getRecipeManager().getRemainingItemsFor(RecipeType.CRAFTING, this.craftSlots, $$0.level());
/*    */     
/* 61 */     for (int $$3 = 0; $$3 < $$2.size(); $$3++) {
/* 62 */       ItemStack $$4 = this.craftSlots.getItem($$3);
/* 63 */       ItemStack $$5 = (ItemStack)$$2.get($$3);
/*    */       
/* 65 */       if (!$$4.isEmpty()) {
/* 66 */         this.craftSlots.removeItem($$3, 1);
/* 67 */         $$4 = this.craftSlots.getItem($$3);
/*    */       } 
/*    */       
/* 70 */       if (!$$5.isEmpty()) {
/* 71 */         if ($$4.isEmpty()) {
/*    */           
/* 73 */           this.craftSlots.setItem($$3, $$5);
/* 74 */         } else if (ItemStack.isSameItemSameTags($$4, $$5)) {
/* 75 */           $$5.grow($$4.getCount());
/* 76 */           this.craftSlots.setItem($$3, $$5);
/* 77 */         } else if (!this.player.getInventory().add($$5)) {
/*    */           
/* 79 */           this.player.drop($$5, false);
/*    */         } 
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isFake() {
/* 87 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\inventory\ResultSlot.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */