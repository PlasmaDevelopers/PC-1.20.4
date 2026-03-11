/*     */ package net.minecraft.world.inventory;
/*     */ 
/*     */ import net.minecraft.advancements.CriteriaTriggers;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.SimpleContainer;
/*     */ import net.minecraft.world.entity.player.Inventory;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.alchemy.Potion;
/*     */ import net.minecraft.world.item.alchemy.PotionBrewing;
/*     */ import net.minecraft.world.item.alchemy.PotionUtils;
/*     */ 
/*     */ 
/*     */ public class BrewingStandMenu
/*     */   extends AbstractContainerMenu
/*     */ {
/*     */   private static final int BOTTLE_SLOT_START = 0;
/*     */   private static final int BOTTLE_SLOT_END = 2;
/*     */   private static final int INGREDIENT_SLOT = 3;
/*     */   private static final int FUEL_SLOT = 4;
/*     */   private static final int SLOT_COUNT = 5;
/*     */   private static final int DATA_COUNT = 2;
/*     */   private static final int INV_SLOT_START = 5;
/*     */   private static final int INV_SLOT_END = 32;
/*     */   private static final int USE_ROW_SLOT_START = 32;
/*     */   private static final int USE_ROW_SLOT_END = 41;
/*     */   private final Container brewingStand;
/*     */   private final ContainerData brewingStandData;
/*     */   private final Slot ingredientSlot;
/*     */   
/*     */   public BrewingStandMenu(int $$0, Inventory $$1) {
/*  35 */     this($$0, $$1, (Container)new SimpleContainer(5), new SimpleContainerData(2));
/*     */   }
/*     */   
/*     */   public BrewingStandMenu(int $$0, Inventory $$1, Container $$2, ContainerData $$3) {
/*  39 */     super(MenuType.BREWING_STAND, $$0);
/*  40 */     checkContainerSize($$2, 5);
/*  41 */     checkContainerDataCount($$3, 2);
/*  42 */     this.brewingStand = $$2;
/*  43 */     this.brewingStandData = $$3;
/*     */     
/*  45 */     addSlot(new PotionSlot($$2, 0, 56, 51));
/*  46 */     addSlot(new PotionSlot($$2, 1, 79, 58));
/*  47 */     addSlot(new PotionSlot($$2, 2, 102, 51));
/*  48 */     this.ingredientSlot = addSlot(new IngredientsSlot($$2, 3, 79, 17));
/*  49 */     addSlot(new FuelSlot($$2, 4, 17, 17));
/*     */     
/*  51 */     addDataSlots($$3);
/*     */     
/*  53 */     for (int $$4 = 0; $$4 < 3; $$4++) {
/*  54 */       for (int $$5 = 0; $$5 < 9; $$5++) {
/*  55 */         addSlot(new Slot((Container)$$1, $$5 + $$4 * 9 + 9, 8 + $$5 * 18, 84 + $$4 * 18));
/*     */       }
/*     */     } 
/*  58 */     for (int $$6 = 0; $$6 < 9; $$6++) {
/*  59 */       addSlot(new Slot((Container)$$1, $$6, 8 + $$6 * 18, 142));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean stillValid(Player $$0) {
/*  65 */     return this.brewingStand.stillValid($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack quickMoveStack(Player $$0, int $$1) {
/*  70 */     ItemStack $$2 = ItemStack.EMPTY;
/*  71 */     Slot $$3 = (Slot)this.slots.get($$1);
/*  72 */     if ($$3 != null && $$3.hasItem()) {
/*  73 */       ItemStack $$4 = $$3.getItem();
/*  74 */       $$2 = $$4.copy();
/*     */       
/*  76 */       if (($$1 >= 0 && $$1 <= 2) || $$1 == 3 || $$1 == 4) {
/*  77 */         if (!moveItemStackTo($$4, 5, 41, true)) {
/*  78 */           return ItemStack.EMPTY;
/*     */         }
/*  80 */         $$3.onQuickCraft($$4, $$2);
/*  81 */       } else if (FuelSlot.mayPlaceItem($$2)) {
/*  82 */         if (moveItemStackTo($$4, 4, 5, false) || (this.ingredientSlot.mayPlace($$4) && !moveItemStackTo($$4, 3, 4, false))) {
/*  83 */           return ItemStack.EMPTY;
/*     */         }
/*  85 */       } else if (this.ingredientSlot.mayPlace($$4)) {
/*  86 */         if (!moveItemStackTo($$4, 3, 4, false)) {
/*  87 */           return ItemStack.EMPTY;
/*     */         }
/*  89 */       } else if (PotionSlot.mayPlaceItem($$2) && $$2.getCount() == 1) {
/*  90 */         if (!moveItemStackTo($$4, 0, 3, false)) {
/*  91 */           return ItemStack.EMPTY;
/*     */         }
/*  93 */       } else if ($$1 >= 5 && $$1 < 32) {
/*  94 */         if (!moveItemStackTo($$4, 32, 41, false)) {
/*  95 */           return ItemStack.EMPTY;
/*     */         }
/*  97 */       } else if ($$1 >= 32 && $$1 < 41) {
/*  98 */         if (!moveItemStackTo($$4, 5, 32, false)) {
/*  99 */           return ItemStack.EMPTY;
/*     */         }
/*     */       }
/* 102 */       else if (!moveItemStackTo($$4, 5, 41, false)) {
/* 103 */         return ItemStack.EMPTY;
/*     */       } 
/*     */       
/* 106 */       if ($$4.isEmpty()) {
/* 107 */         $$3.setByPlayer(ItemStack.EMPTY);
/*     */       } else {
/* 109 */         $$3.setChanged();
/*     */       } 
/* 111 */       if ($$4.getCount() == $$2.getCount()) {
/* 112 */         return ItemStack.EMPTY;
/*     */       }
/* 114 */       $$3.onTake($$0, $$4);
/*     */     } 
/*     */     
/* 117 */     return $$2;
/*     */   }
/*     */   
/*     */   public int getFuel() {
/* 121 */     return this.brewingStandData.get(1);
/*     */   }
/*     */   
/*     */   public int getBrewingTicks() {
/* 125 */     return this.brewingStandData.get(0);
/*     */   }
/*     */   
/*     */   private static class PotionSlot extends Slot {
/*     */     public PotionSlot(Container $$0, int $$1, int $$2, int $$3) {
/* 130 */       super($$0, $$1, $$2, $$3);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean mayPlace(ItemStack $$0) {
/* 135 */       return mayPlaceItem($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public int getMaxStackSize() {
/* 140 */       return 1;
/*     */     }
/*     */ 
/*     */     
/*     */     public void onTake(Player $$0, ItemStack $$1) {
/* 145 */       Potion $$2 = PotionUtils.getPotion($$1);
/* 146 */       if ($$0 instanceof ServerPlayer) {
/* 147 */         CriteriaTriggers.BREWED_POTION.trigger((ServerPlayer)$$0, (Holder)$$2.builtInRegistryHolder());
/*     */       }
/* 149 */       super.onTake($$0, $$1);
/*     */     }
/*     */     
/*     */     public static boolean mayPlaceItem(ItemStack $$0) {
/* 153 */       return ($$0.is(Items.POTION) || $$0.is(Items.SPLASH_POTION) || $$0.is(Items.LINGERING_POTION) || $$0.is(Items.GLASS_BOTTLE));
/*     */     }
/*     */   }
/*     */   
/*     */   private static class IngredientsSlot extends Slot {
/*     */     public IngredientsSlot(Container $$0, int $$1, int $$2, int $$3) {
/* 159 */       super($$0, $$1, $$2, $$3);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean mayPlace(ItemStack $$0) {
/* 164 */       return PotionBrewing.isIngredient($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public int getMaxStackSize() {
/* 169 */       return 64;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class FuelSlot extends Slot {
/*     */     public FuelSlot(Container $$0, int $$1, int $$2, int $$3) {
/* 175 */       super($$0, $$1, $$2, $$3);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean mayPlace(ItemStack $$0) {
/* 180 */       return mayPlaceItem($$0);
/*     */     }
/*     */     
/*     */     public static boolean mayPlaceItem(ItemStack $$0) {
/* 184 */       return $$0.is(Items.BLAZE_POWDER);
/*     */     }
/*     */ 
/*     */     
/*     */     public int getMaxStackSize() {
/* 189 */       return 64;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\inventory\BrewingStandMenu.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */