/*     */ package net.minecraft.world.inventory;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.OptionalInt;
/*     */ import java.util.stream.IntStream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.world.entity.player.Inventory;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.crafting.RecipeHolder;
/*     */ import net.minecraft.world.item.crafting.RecipeType;
/*     */ import net.minecraft.world.item.crafting.SmithingRecipe;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ 
/*     */ public class SmithingMenu
/*     */   extends ItemCombinerMenu
/*     */ {
/*     */   public static final int TEMPLATE_SLOT = 0;
/*     */   public static final int BASE_SLOT = 1;
/*     */   public static final int ADDITIONAL_SLOT = 2;
/*     */   public static final int RESULT_SLOT = 3;
/*     */   public static final int TEMPLATE_SLOT_X_PLACEMENT = 8;
/*     */   public static final int BASE_SLOT_X_PLACEMENT = 26;
/*     */   public static final int ADDITIONAL_SLOT_X_PLACEMENT = 44;
/*     */   private static final int RESULT_SLOT_X_PLACEMENT = 98;
/*     */   public static final int SLOT_Y_PLACEMENT = 48;
/*     */   private final Level level;
/*     */   @Nullable
/*     */   private RecipeHolder<SmithingRecipe> selectedRecipe;
/*     */   private final List<RecipeHolder<SmithingRecipe>> recipes;
/*     */   
/*     */   public SmithingMenu(int $$0, Inventory $$1) {
/*  36 */     this($$0, $$1, ContainerLevelAccess.NULL);
/*     */   }
/*     */   
/*     */   public SmithingMenu(int $$0, Inventory $$1, ContainerLevelAccess $$2) {
/*  40 */     super(MenuType.SMITHING, $$0, $$1, $$2);
/*  41 */     this.level = $$1.player.level();
/*  42 */     this.recipes = this.level.getRecipeManager().getAllRecipesFor(RecipeType.SMITHING);
/*     */   }
/*     */ 
/*     */   
/*     */   protected ItemCombinerMenuSlotDefinition createInputSlotDefinitions() {
/*  47 */     return ItemCombinerMenuSlotDefinition.create()
/*  48 */       .withSlot(0, 8, 48, $$0 -> this.recipes.stream().anyMatch(()))
/*  49 */       .withSlot(1, 26, 48, $$0 -> this.recipes.stream().anyMatch(()))
/*  50 */       .withSlot(2, 44, 48, $$0 -> this.recipes.stream().anyMatch(()))
/*  51 */       .withResultSlot(3, 98, 48)
/*  52 */       .build();
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isValidBlock(BlockState $$0) {
/*  57 */     return $$0.is(Blocks.SMITHING_TABLE);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean mayPickup(Player $$0, boolean $$1) {
/*  62 */     return (this.selectedRecipe != null && ((SmithingRecipe)this.selectedRecipe.value()).matches(this.inputSlots, this.level));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onTake(Player $$0, ItemStack $$1) {
/*  67 */     $$1.onCraftedBy($$0.level(), $$0, $$1.getCount());
/*  68 */     this.resultSlots.awardUsedRecipes($$0, getRelevantItems());
/*     */ 
/*     */     
/*  71 */     shrinkStackInSlot(0);
/*  72 */     shrinkStackInSlot(1);
/*  73 */     shrinkStackInSlot(2);
/*     */     
/*  75 */     this.access.execute(($$0, $$1) -> $$0.levelEvent(1044, $$1, 0));
/*     */   }
/*     */   
/*     */   private List<ItemStack> getRelevantItems() {
/*  79 */     return List.of(this.inputSlots
/*  80 */         .getItem(0), this.inputSlots
/*  81 */         .getItem(1), this.inputSlots
/*  82 */         .getItem(2));
/*     */   }
/*     */ 
/*     */   
/*     */   private void shrinkStackInSlot(int $$0) {
/*  87 */     ItemStack $$1 = this.inputSlots.getItem($$0);
/*  88 */     if (!$$1.isEmpty()) {
/*  89 */       $$1.shrink(1);
/*  90 */       this.inputSlots.setItem($$0, $$1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void createResult() {
/*  96 */     List<RecipeHolder<SmithingRecipe>> $$0 = this.level.getRecipeManager().getRecipesFor(RecipeType.SMITHING, this.inputSlots, this.level);
/*  97 */     if ($$0.isEmpty()) {
/*  98 */       this.resultSlots.setItem(0, ItemStack.EMPTY);
/*     */     } else {
/* 100 */       RecipeHolder<SmithingRecipe> $$1 = $$0.get(0);
/* 101 */       ItemStack $$2 = ((SmithingRecipe)$$1.value()).assemble(this.inputSlots, this.level.registryAccess());
/* 102 */       if ($$2.isItemEnabled(this.level.enabledFeatures())) {
/* 103 */         this.selectedRecipe = $$1;
/* 104 */         this.resultSlots.setRecipeUsed($$1);
/* 105 */         this.resultSlots.setItem(0, $$2);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSlotToQuickMoveTo(ItemStack $$0) {
/* 112 */     return findSlotToQuickMoveTo($$0).orElse(0);
/*     */   }
/*     */   
/*     */   private static OptionalInt findSlotMatchingIngredient(SmithingRecipe $$0, ItemStack $$1) {
/* 116 */     if ($$0.isTemplateIngredient($$1))
/* 117 */       return OptionalInt.of(0); 
/* 118 */     if ($$0.isBaseIngredient($$1))
/* 119 */       return OptionalInt.of(1); 
/* 120 */     if ($$0.isAdditionIngredient($$1)) {
/* 121 */       return OptionalInt.of(2);
/*     */     }
/* 123 */     return OptionalInt.empty();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canTakeItemForPickAll(ItemStack $$0, Slot $$1) {
/* 129 */     return ($$1.container != this.resultSlots && super.canTakeItemForPickAll($$0, $$1));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canMoveIntoInputSlots(ItemStack $$0) {
/* 134 */     return findSlotToQuickMoveTo($$0).isPresent();
/*     */   }
/*     */   
/*     */   private OptionalInt findSlotToQuickMoveTo(ItemStack $$0) {
/* 138 */     return this.recipes.stream()
/* 139 */       .flatMapToInt($$1 -> findSlotMatchingIngredient((SmithingRecipe)$$1.value(), $$0).stream())
/* 140 */       .filter($$0 -> !getSlot($$0).hasItem())
/* 141 */       .findFirst();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\inventory\SmithingMenu.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */