/*     */ package net.minecraft.world.inventory;
/*     */ 
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.SimpleContainer;
/*     */ import net.minecraft.world.entity.player.Inventory;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.entity.player.StackedContents;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.crafting.AbstractCookingRecipe;
/*     */ import net.minecraft.world.item.crafting.Recipe;
/*     */ import net.minecraft.world.item.crafting.RecipeHolder;
/*     */ import net.minecraft.world.item.crafting.RecipeType;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractFurnaceMenu
/*     */   extends RecipeBookMenu<Container>
/*     */ {
/*     */   public static final int INGREDIENT_SLOT = 0;
/*     */   public static final int FUEL_SLOT = 1;
/*     */   public static final int RESULT_SLOT = 2;
/*     */   public static final int SLOT_COUNT = 3;
/*     */   public static final int DATA_COUNT = 4;
/*     */   private static final int INV_SLOT_START = 3;
/*     */   private static final int INV_SLOT_END = 30;
/*     */   private static final int USE_ROW_SLOT_START = 30;
/*     */   private static final int USE_ROW_SLOT_END = 39;
/*     */   private final Container container;
/*     */   private final ContainerData data;
/*     */   protected final Level level;
/*     */   private final RecipeType<? extends AbstractCookingRecipe> recipeType;
/*     */   private final RecipeBookType recipeBookType;
/*     */   
/*     */   protected AbstractFurnaceMenu(MenuType<?> $$0, RecipeType<? extends AbstractCookingRecipe> $$1, RecipeBookType $$2, int $$3, Inventory $$4) {
/*  41 */     this($$0, $$1, $$2, $$3, $$4, (Container)new SimpleContainer(3), new SimpleContainerData(4));
/*     */   }
/*     */   
/*     */   protected AbstractFurnaceMenu(MenuType<?> $$0, RecipeType<? extends AbstractCookingRecipe> $$1, RecipeBookType $$2, int $$3, Inventory $$4, Container $$5, ContainerData $$6) {
/*  45 */     super($$0, $$3);
/*  46 */     this.recipeType = $$1;
/*  47 */     this.recipeBookType = $$2;
/*  48 */     checkContainerSize($$5, 3);
/*  49 */     checkContainerDataCount($$6, 4);
/*  50 */     this.container = $$5;
/*  51 */     this.data = $$6;
/*  52 */     this.level = $$4.player.level();
/*     */     
/*  54 */     addSlot(new Slot($$5, 0, 56, 17));
/*  55 */     addSlot(new FurnaceFuelSlot(this, $$5, 1, 56, 53));
/*  56 */     addSlot(new FurnaceResultSlot($$4.player, $$5, 2, 116, 35));
/*     */     
/*  58 */     for (int $$7 = 0; $$7 < 3; $$7++) {
/*  59 */       for (int $$8 = 0; $$8 < 9; $$8++) {
/*  60 */         addSlot(new Slot((Container)$$4, $$8 + $$7 * 9 + 9, 8 + $$8 * 18, 84 + $$7 * 18));
/*     */       }
/*     */     } 
/*  63 */     for (int $$9 = 0; $$9 < 9; $$9++) {
/*  64 */       addSlot(new Slot((Container)$$4, $$9, 8 + $$9 * 18, 142));
/*     */     }
/*     */     
/*  67 */     addDataSlots($$6);
/*     */   }
/*     */ 
/*     */   
/*     */   public void fillCraftSlotsStackedContents(StackedContents $$0) {
/*  72 */     if (this.container instanceof StackedContentsCompatible) {
/*  73 */       ((StackedContentsCompatible)this.container).fillStackedContents($$0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearCraftingContent() {
/*  79 */     getSlot(0).set(ItemStack.EMPTY);
/*  80 */     getSlot(2).set(ItemStack.EMPTY);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean recipeMatches(RecipeHolder<? extends Recipe<Container>> $$0) {
/*  85 */     return $$0.value().matches(this.container, this.level);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getResultSlotIndex() {
/*  90 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getGridWidth() {
/*  95 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getGridHeight() {
/* 100 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSize() {
/* 105 */     return 3;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean stillValid(Player $$0) {
/* 110 */     return this.container.stillValid($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack quickMoveStack(Player $$0, int $$1) {
/* 115 */     ItemStack $$2 = ItemStack.EMPTY;
/* 116 */     Slot $$3 = (Slot)this.slots.get($$1);
/* 117 */     if ($$3 != null && $$3.hasItem()) {
/* 118 */       ItemStack $$4 = $$3.getItem();
/* 119 */       $$2 = $$4.copy();
/*     */       
/* 121 */       if ($$1 == 2) {
/* 122 */         if (!moveItemStackTo($$4, 3, 39, true)) {
/* 123 */           return ItemStack.EMPTY;
/*     */         }
/* 125 */         $$3.onQuickCraft($$4, $$2);
/* 126 */       } else if ($$1 == 1 || $$1 == 0) {
/* 127 */         if (!moveItemStackTo($$4, 3, 39, false)) {
/* 128 */           return ItemStack.EMPTY;
/*     */         }
/* 130 */       } else if (canSmelt($$4)) {
/* 131 */         if (!moveItemStackTo($$4, 0, 1, false)) {
/* 132 */           return ItemStack.EMPTY;
/*     */         }
/* 134 */       } else if (isFuel($$4)) {
/* 135 */         if (!moveItemStackTo($$4, 1, 2, false)) {
/* 136 */           return ItemStack.EMPTY;
/*     */         }
/* 138 */       } else if ($$1 >= 3 && $$1 < 30) {
/* 139 */         if (!moveItemStackTo($$4, 30, 39, false)) {
/* 140 */           return ItemStack.EMPTY;
/*     */         }
/* 142 */       } else if ($$1 >= 30 && $$1 < 39 && 
/* 143 */         !moveItemStackTo($$4, 3, 30, false)) {
/* 144 */         return ItemStack.EMPTY;
/*     */       } 
/*     */       
/* 147 */       if ($$4.isEmpty()) {
/* 148 */         $$3.setByPlayer(ItemStack.EMPTY);
/*     */       } else {
/* 150 */         $$3.setChanged();
/*     */       } 
/* 152 */       if ($$4.getCount() == $$2.getCount()) {
/* 153 */         return ItemStack.EMPTY;
/*     */       }
/* 155 */       $$3.onTake($$0, $$4);
/*     */     } 
/*     */     
/* 158 */     return $$2;
/*     */   }
/*     */   
/*     */   protected boolean canSmelt(ItemStack $$0) {
/* 162 */     return this.level.getRecipeManager().getRecipeFor(this.recipeType, (Container)new SimpleContainer(new ItemStack[] { $$0 }, ), this.level).isPresent();
/*     */   }
/*     */   
/*     */   protected boolean isFuel(ItemStack $$0) {
/* 166 */     return AbstractFurnaceBlockEntity.isFuel($$0);
/*     */   }
/*     */   
/*     */   public float getBurnProgress() {
/* 170 */     int $$0 = this.data.get(2);
/* 171 */     int $$1 = this.data.get(3);
/* 172 */     if ($$1 == 0 || $$0 == 0) {
/* 173 */       return 0.0F;
/*     */     }
/* 175 */     return Mth.clamp($$0 / $$1, 0.0F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getLitProgress() {
/* 180 */     int $$0 = this.data.get(1);
/* 181 */     if ($$0 == 0) {
/* 182 */       $$0 = 200;
/*     */     }
/* 184 */     return Mth.clamp(this.data.get(0) / $$0, 0.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public boolean isLit() {
/* 188 */     return (this.data.get(0) > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public RecipeBookType getRecipeBookType() {
/* 193 */     return this.recipeBookType;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldMoveToInventory(int $$0) {
/* 198 */     return ($$0 != 1);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\inventory\AbstractFurnaceMenu.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */