/*     */ package net.minecraft.world.inventory;
/*     */ 
/*     */ import java.util.Optional;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.entity.player.Inventory;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.entity.player.StackedContents;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.crafting.CraftingRecipe;
/*     */ import net.minecraft.world.item.crafting.Recipe;
/*     */ import net.minecraft.world.item.crafting.RecipeHolder;
/*     */ import net.minecraft.world.item.crafting.RecipeType;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ 
/*     */ public class CraftingMenu extends RecipeBookMenu<CraftingContainer> {
/*     */   public static final int RESULT_SLOT = 0;
/*     */   private static final int CRAFT_SLOT_START = 1;
/*     */   private static final int CRAFT_SLOT_END = 10;
/*     */   private static final int INV_SLOT_START = 10;
/*     */   private static final int INV_SLOT_END = 37;
/*     */   private static final int USE_ROW_SLOT_START = 37;
/*     */   private static final int USE_ROW_SLOT_END = 46;
/*  28 */   private final CraftingContainer craftSlots = new TransientCraftingContainer(this, 3, 3);
/*  29 */   private final ResultContainer resultSlots = new ResultContainer();
/*     */   
/*     */   private final ContainerLevelAccess access;
/*     */   private final Player player;
/*     */   
/*     */   public CraftingMenu(int $$0, Inventory $$1) {
/*  35 */     this($$0, $$1, ContainerLevelAccess.NULL);
/*     */   }
/*     */   
/*     */   public CraftingMenu(int $$0, Inventory $$1, ContainerLevelAccess $$2) {
/*  39 */     super(MenuType.CRAFTING, $$0);
/*  40 */     this.access = $$2;
/*  41 */     this.player = $$1.player;
/*  42 */     addSlot(new ResultSlot($$1.player, this.craftSlots, this.resultSlots, 0, 124, 35));
/*     */     
/*  44 */     for (int $$3 = 0; $$3 < 3; $$3++) {
/*  45 */       for (int $$4 = 0; $$4 < 3; $$4++) {
/*  46 */         addSlot(new Slot(this.craftSlots, $$4 + $$3 * 3, 30 + $$4 * 18, 17 + $$3 * 18));
/*     */       }
/*     */     } 
/*     */     
/*  50 */     for (int $$5 = 0; $$5 < 3; $$5++) {
/*  51 */       for (int $$6 = 0; $$6 < 9; $$6++) {
/*  52 */         addSlot(new Slot((Container)$$1, $$6 + $$5 * 9 + 9, 8 + $$6 * 18, 84 + $$5 * 18));
/*     */       }
/*     */     } 
/*  55 */     for (int $$7 = 0; $$7 < 9; $$7++) {
/*  56 */       addSlot(new Slot((Container)$$1, $$7, 8 + $$7 * 18, 142));
/*     */     }
/*     */   }
/*     */   
/*     */   protected static void slotChangedCraftingGrid(AbstractContainerMenu $$0, Level $$1, Player $$2, CraftingContainer $$3, ResultContainer $$4) {
/*  61 */     if ($$1.isClientSide) {
/*     */       return;
/*     */     }
/*     */     
/*  65 */     ServerPlayer $$5 = (ServerPlayer)$$2;
/*  66 */     ItemStack $$6 = ItemStack.EMPTY;
/*  67 */     Optional<RecipeHolder<CraftingRecipe>> $$7 = $$1.getServer().getRecipeManager().getRecipeFor(RecipeType.CRAFTING, $$3, $$1);
/*  68 */     if ($$7.isPresent()) {
/*  69 */       RecipeHolder<CraftingRecipe> $$8 = $$7.get();
/*  70 */       CraftingRecipe $$9 = (CraftingRecipe)$$8.value();
/*  71 */       if ($$4.setRecipeUsed($$1, $$5, $$8)) {
/*  72 */         ItemStack $$10 = $$9.assemble($$3, $$1.registryAccess());
/*  73 */         if ($$10.isItemEnabled($$1.enabledFeatures())) {
/*  74 */           $$6 = $$10;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  79 */     $$4.setItem(0, $$6);
/*  80 */     $$0.setRemoteSlot(0, $$6);
/*  81 */     $$5.connection.send((Packet)new ClientboundContainerSetSlotPacket($$0.containerId, $$0.incrementStateId(), 0, $$6));
/*     */   }
/*     */ 
/*     */   
/*     */   public void slotsChanged(Container $$0) {
/*  86 */     this.access.execute(($$0, $$1) -> slotChangedCraftingGrid(this, $$0, this.player, this.craftSlots, this.resultSlots));
/*     */   }
/*     */ 
/*     */   
/*     */   public void fillCraftSlotsStackedContents(StackedContents $$0) {
/*  91 */     this.craftSlots.fillStackedContents($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearCraftingContent() {
/*  96 */     this.craftSlots.clearContent();
/*  97 */     this.resultSlots.clearContent();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean recipeMatches(RecipeHolder<? extends Recipe<CraftingContainer>> $$0) {
/* 102 */     return $$0.value().matches(this.craftSlots, this.player.level());
/*     */   }
/*     */ 
/*     */   
/*     */   public void removed(Player $$0) {
/* 107 */     super.removed($$0);
/* 108 */     this.access.execute(($$1, $$2) -> clearContainer($$0, this.craftSlots));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean stillValid(Player $$0) {
/* 113 */     return stillValid(this.access, $$0, Blocks.CRAFTING_TABLE);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack quickMoveStack(Player $$0, int $$1) {
/* 118 */     ItemStack $$2 = ItemStack.EMPTY;
/* 119 */     Slot $$3 = (Slot)this.slots.get($$1);
/* 120 */     if ($$3 != null && $$3.hasItem()) {
/* 121 */       ItemStack $$4 = $$3.getItem();
/* 122 */       $$2 = $$4.copy();
/*     */       
/* 124 */       if ($$1 == 0) {
/* 125 */         this.access.execute(($$2, $$3) -> $$0.getItem().onCraftedBy($$0, $$2, $$1));
/* 126 */         if (!moveItemStackTo($$4, 10, 46, true)) {
/* 127 */           return ItemStack.EMPTY;
/*     */         }
/* 129 */         $$3.onQuickCraft($$4, $$2);
/* 130 */       } else if ($$1 >= 10 && $$1 < 46) {
/* 131 */         if (!moveItemStackTo($$4, 1, 10, false)) {
/* 132 */           if ($$1 < 37) {
/* 133 */             if (!moveItemStackTo($$4, 37, 46, false)) {
/* 134 */               return ItemStack.EMPTY;
/*     */             }
/*     */           }
/* 137 */           else if (!moveItemStackTo($$4, 10, 37, false)) {
/* 138 */             return ItemStack.EMPTY;
/*     */           }
/*     */         
/*     */         }
/*     */       }
/* 143 */       else if (!moveItemStackTo($$4, 10, 46, false)) {
/* 144 */         return ItemStack.EMPTY;
/*     */       } 
/*     */       
/* 147 */       if ($$4.isEmpty()) {
/* 148 */         $$3.setByPlayer(ItemStack.EMPTY);
/*     */       } else {
/* 150 */         $$3.setChanged();
/*     */       } 
/* 152 */       if ($$4.getCount() == $$2.getCount())
/*     */       {
/* 154 */         return ItemStack.EMPTY;
/*     */       }
/* 156 */       $$3.onTake($$0, $$4);
/* 157 */       if ($$1 == 0) {
/* 158 */         $$0.drop($$4, false);
/*     */       }
/*     */     } 
/*     */     
/* 162 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canTakeItemForPickAll(ItemStack $$0, Slot $$1) {
/* 167 */     return ($$1.container != this.resultSlots && super.canTakeItemForPickAll($$0, $$1));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getResultSlotIndex() {
/* 172 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getGridWidth() {
/* 177 */     return this.craftSlots.getWidth();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getGridHeight() {
/* 182 */     return this.craftSlots.getHeight();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSize() {
/* 187 */     return 10;
/*     */   }
/*     */ 
/*     */   
/*     */   public RecipeBookType getRecipeBookType() {
/* 192 */     return RecipeBookType.CRAFTING;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldMoveToInventory(int $$0) {
/* 197 */     return ($$0 != getResultSlotIndex());
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\inventory\CraftingMenu.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */