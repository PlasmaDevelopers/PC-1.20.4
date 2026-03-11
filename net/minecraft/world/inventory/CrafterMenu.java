/*     */ package net.minecraft.world.inventory;
/*     */ 
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.entity.player.Inventory;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.crafting.CraftingRecipe;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.CrafterBlock;
/*     */ 
/*     */ public class CrafterMenu
/*     */   extends AbstractContainerMenu
/*     */   implements ContainerListener
/*     */ {
/*     */   protected static final int SLOT_COUNT = 9;
/*     */   private static final int INV_SLOT_START = 9;
/*     */   private static final int INV_SLOT_END = 36;
/*     */   private static final int USE_ROW_SLOT_START = 36;
/*     */   private static final int USE_ROW_SLOT_END = 45;
/*  21 */   private final ResultContainer resultContainer = new ResultContainer();
/*     */   private final ContainerData containerData;
/*     */   private final Player player;
/*     */   private final CraftingContainer container;
/*     */   
/*     */   public CrafterMenu(int $$0, Inventory $$1) {
/*  27 */     super(MenuType.CRAFTER_3x3, $$0);
/*  28 */     this.player = $$1.player;
/*  29 */     this.containerData = new SimpleContainerData(10);
/*  30 */     this.container = new TransientCraftingContainer(this, 3, 3);
/*  31 */     addSlots($$1);
/*     */   }
/*     */   
/*     */   public CrafterMenu(int $$0, Inventory $$1, CraftingContainer $$2, ContainerData $$3) {
/*  35 */     super(MenuType.CRAFTER_3x3, $$0);
/*  36 */     this.player = $$1.player;
/*  37 */     this.containerData = $$3;
/*  38 */     this.container = $$2;
/*  39 */     checkContainerSize($$2, 9);
/*  40 */     $$2.startOpen($$1.player);
/*  41 */     addSlots($$1);
/*  42 */     addSlotListener(this);
/*     */   }
/*     */   
/*     */   private void addSlots(Inventory $$0) {
/*  46 */     for (int $$1 = 0; $$1 < 3; $$1++) {
/*  47 */       for (int $$2 = 0; $$2 < 3; $$2++) {
/*  48 */         int $$3 = $$2 + $$1 * 3;
/*  49 */         addSlot(new CrafterSlot(this.container, $$3, 26 + $$2 * 18, 17 + $$1 * 18, this));
/*     */       } 
/*     */     } 
/*     */     
/*  53 */     for (int $$4 = 0; $$4 < 3; $$4++) {
/*  54 */       for (int $$5 = 0; $$5 < 9; $$5++) {
/*  55 */         addSlot(new Slot((Container)$$0, $$5 + $$4 * 9 + 9, 8 + $$5 * 18, 84 + $$4 * 18));
/*     */       }
/*     */     } 
/*     */     
/*  59 */     for (int $$6 = 0; $$6 < 9; $$6++) {
/*  60 */       addSlot(new Slot((Container)$$0, $$6, 8 + $$6 * 18, 142));
/*     */     }
/*     */     
/*  63 */     addSlot(new NonInteractiveResultSlot(this.resultContainer, 0, 134, 35));
/*  64 */     addDataSlots(this.containerData);
/*  65 */     refreshRecipeResult();
/*     */   }
/*     */   
/*     */   public void setSlotState(int $$0, boolean $$1) {
/*  69 */     CrafterSlot $$2 = (CrafterSlot)getSlot($$0);
/*  70 */     this.containerData.set($$2.index, $$1 ? 0 : 1);
/*  71 */     broadcastChanges();
/*     */   }
/*     */   
/*     */   public boolean isSlotDisabled(int $$0) {
/*  75 */     if ($$0 > -1 && $$0 < 9) {
/*  76 */       return (this.containerData.get($$0) == 1);
/*     */     }
/*  78 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isPowered() {
/*  82 */     return (this.containerData.get(9) == 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack quickMoveStack(Player $$0, int $$1) {
/*  87 */     ItemStack $$2 = ItemStack.EMPTY;
/*  88 */     Slot $$3 = (Slot)this.slots.get($$1);
/*  89 */     if ($$3 != null && $$3.hasItem()) {
/*  90 */       ItemStack $$4 = $$3.getItem();
/*  91 */       $$2 = $$4.copy();
/*     */       
/*  93 */       if ($$1 < 9) {
/*  94 */         if (!moveItemStackTo($$4, 9, 45, true)) {
/*  95 */           return ItemStack.EMPTY;
/*     */         }
/*     */       }
/*  98 */       else if (!moveItemStackTo($$4, 0, 9, false)) {
/*  99 */         return ItemStack.EMPTY;
/*     */       } 
/*     */       
/* 102 */       if ($$4.isEmpty()) {
/* 103 */         $$3.set(ItemStack.EMPTY);
/*     */       } else {
/* 105 */         $$3.setChanged();
/*     */       } 
/* 107 */       if ($$4.getCount() == $$2.getCount())
/*     */       {
/* 109 */         return ItemStack.EMPTY;
/*     */       }
/* 111 */       $$3.onTake($$0, $$4);
/*     */     } 
/*     */     
/* 114 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean stillValid(Player $$0) {
/* 119 */     return this.container.stillValid($$0);
/*     */   }
/*     */   
/*     */   private void refreshRecipeResult() {
/* 123 */     Player player = this.player; if (player instanceof ServerPlayer) { ServerPlayer $$0 = (ServerPlayer)player;
/* 124 */       Level $$1 = $$0.level();
/*     */ 
/*     */       
/* 127 */       ItemStack $$2 = CrafterBlock.getPotentialResults($$1, this.container).map($$1 -> $$1.assemble(this.container, $$0.registryAccess())).orElse(ItemStack.EMPTY);
/* 128 */       this.resultContainer.setItem(0, $$2); }
/*     */   
/*     */   }
/*     */   
/*     */   public Container getContainer() {
/* 133 */     return this.container;
/*     */   }
/*     */ 
/*     */   
/*     */   public void slotChanged(AbstractContainerMenu $$0, int $$1, ItemStack $$2) {
/* 138 */     refreshRecipeResult();
/*     */   }
/*     */   
/*     */   public void dataChanged(AbstractContainerMenu $$0, int $$1, int $$2) {}
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\inventory\CrafterMenu.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */