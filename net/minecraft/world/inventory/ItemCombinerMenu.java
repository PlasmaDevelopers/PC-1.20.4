/*     */ package net.minecraft.world.inventory;
/*     */ 
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.SimpleContainer;
/*     */ import net.minecraft.world.entity.player.Inventory;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ 
/*     */ public abstract class ItemCombinerMenu
/*     */   extends AbstractContainerMenu {
/*     */   private static final int INVENTORY_SLOTS_PER_ROW = 9;
/*     */   private static final int INVENTORY_SLOTS_PER_COLUMN = 3;
/*     */   protected final ContainerLevelAccess access;
/*     */   protected final Player player;
/*     */   protected final Container inputSlots;
/*     */   private final List<Integer> inputSlotIndexes;
/*  22 */   protected final ResultContainer resultSlots = new ResultContainer();
/*     */ 
/*     */ 
/*     */   
/*     */   private final int resultSlotIndex;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemCombinerMenu(@Nullable MenuType<?> $$0, int $$1, Inventory $$2, ContainerLevelAccess $$3) {
/*  32 */     super($$0, $$1);
/*  33 */     this.access = $$3;
/*  34 */     this.player = $$2.player;
/*     */     
/*  36 */     ItemCombinerMenuSlotDefinition $$4 = createInputSlotDefinitions();
/*     */     
/*  38 */     this.inputSlots = (Container)createContainer($$4.getNumOfInputSlots());
/*  39 */     this.inputSlotIndexes = $$4.getInputSlotIndexes();
/*  40 */     this.resultSlotIndex = $$4.getResultSlotIndex();
/*     */     
/*  42 */     createInputSlots($$4);
/*  43 */     createResultSlot($$4);
/*  44 */     createInventorySlots($$2);
/*     */   }
/*     */   
/*     */   private void createInputSlots(ItemCombinerMenuSlotDefinition $$0) {
/*  48 */     for (ItemCombinerMenuSlotDefinition.SlotDefinition $$1 : $$0.getSlots()) {
/*  49 */       addSlot(new Slot(this.inputSlots, $$1.slotIndex(), $$1.x(), $$1.y())
/*     */           {
/*     */             public boolean mayPlace(ItemStack $$0) {
/*  52 */               return slot.mayPlace().test($$0);
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */   
/*     */   private void createResultSlot(ItemCombinerMenuSlotDefinition $$0) {
/*  59 */     addSlot(new Slot(this.resultSlots, $$0.getResultSlot().slotIndex(), $$0.getResultSlot().x(), $$0.getResultSlot().y())
/*     */         {
/*     */           public boolean mayPlace(ItemStack $$0) {
/*  62 */             return false;
/*     */           }
/*     */ 
/*     */           
/*     */           public boolean mayPickup(Player $$0) {
/*  67 */             return ItemCombinerMenu.this.mayPickup($$0, hasItem());
/*     */           }
/*     */ 
/*     */           
/*     */           public void onTake(Player $$0, ItemStack $$1) {
/*  72 */             ItemCombinerMenu.this.onTake($$0, $$1);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private void createInventorySlots(Inventory $$0) {
/*  78 */     for (int $$1 = 0; $$1 < 3; $$1++) {
/*  79 */       for (int $$2 = 0; $$2 < 9; $$2++) {
/*  80 */         addSlot(new Slot((Container)$$0, $$2 + $$1 * 9 + 9, 8 + $$2 * 18, 84 + $$1 * 18));
/*     */       }
/*     */     } 
/*  83 */     for (int $$3 = 0; $$3 < 9; $$3++) {
/*  84 */       addSlot(new Slot((Container)$$0, $$3, 8 + $$3 * 18, 142));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SimpleContainer createContainer(int $$0) {
/*  92 */     return new SimpleContainer($$0)
/*     */       {
/*     */         public void setChanged() {
/*  95 */           super.setChanged();
/*  96 */           ItemCombinerMenu.this.slotsChanged((Container)this);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public void slotsChanged(Container $$0) {
/* 103 */     super.slotsChanged($$0);
/*     */     
/* 105 */     if ($$0 == this.inputSlots) {
/* 106 */       createResult();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void removed(Player $$0) {
/* 112 */     super.removed($$0);
/* 113 */     this.access.execute(($$1, $$2) -> clearContainer($$0, this.inputSlots));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean stillValid(Player $$0) {
/* 118 */     return ((Boolean)this.access.<Boolean>evaluate(($$1, $$2) -> !isValidBlock($$1.getBlockState($$2)) ? Boolean.valueOf(false) : Boolean.valueOf(($$0.distanceToSqr($$2.getX() + 0.5D, $$2.getY() + 0.5D, $$2.getZ() + 0.5D) <= 64.0D)), 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 123 */         Boolean.valueOf(true))).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack quickMoveStack(Player $$0, int $$1) {
/* 128 */     ItemStack $$2 = ItemStack.EMPTY;
/* 129 */     Slot $$3 = (Slot)this.slots.get($$1);
/* 130 */     if ($$3 != null && $$3.hasItem()) {
/* 131 */       ItemStack $$4 = $$3.getItem();
/* 132 */       $$2 = $$4.copy();
/*     */       
/* 134 */       int $$5 = getInventorySlotStart();
/* 135 */       int $$6 = getUseRowEnd();
/* 136 */       if ($$1 == getResultSlot()) {
/* 137 */         if (!moveItemStackTo($$4, $$5, $$6, true)) {
/* 138 */           return ItemStack.EMPTY;
/*     */         }
/* 140 */         $$3.onQuickCraft($$4, $$2);
/* 141 */       } else if (this.inputSlotIndexes.contains(Integer.valueOf($$1))) {
/* 142 */         if (!moveItemStackTo($$4, $$5, $$6, false)) {
/* 143 */           return ItemStack.EMPTY;
/*     */         }
/* 145 */       } else if (canMoveIntoInputSlots($$4) && $$1 >= getInventorySlotStart() && $$1 < getUseRowEnd()) {
/* 146 */         int $$7 = getSlotToQuickMoveTo($$2);
/* 147 */         if (!moveItemStackTo($$4, $$7, getResultSlot(), false)) {
/* 148 */           return ItemStack.EMPTY;
/*     */         }
/* 150 */       } else if ($$1 >= getInventorySlotStart() && $$1 < getInventorySlotEnd()) {
/* 151 */         if (!moveItemStackTo($$4, getUseRowStart(), getUseRowEnd(), false)) {
/* 152 */           return ItemStack.EMPTY;
/*     */         }
/* 154 */       } else if ($$1 >= getUseRowStart() && $$1 < getUseRowEnd() && 
/* 155 */         !moveItemStackTo($$4, getInventorySlotStart(), getInventorySlotEnd(), false)) {
/* 156 */         return ItemStack.EMPTY;
/*     */       } 
/*     */ 
/*     */       
/* 160 */       if ($$4.isEmpty()) {
/* 161 */         $$3.setByPlayer(ItemStack.EMPTY);
/*     */       } else {
/* 163 */         $$3.setChanged();
/*     */       } 
/* 165 */       if ($$4.getCount() == $$2.getCount()) {
/* 166 */         return ItemStack.EMPTY;
/*     */       }
/* 168 */       $$3.onTake($$0, $$4);
/*     */     } 
/*     */     
/* 171 */     return $$2;
/*     */   }
/*     */   
/*     */   protected boolean canMoveIntoInputSlots(ItemStack $$0) {
/* 175 */     return true;
/*     */   }
/*     */   
/*     */   public int getSlotToQuickMoveTo(ItemStack $$0) {
/* 179 */     return this.inputSlots.isEmpty() ? 0 : ((Integer)this.inputSlotIndexes.get(0)).intValue();
/*     */   }
/*     */   
/*     */   public int getResultSlot() {
/* 183 */     return this.resultSlotIndex;
/*     */   }
/*     */   
/*     */   private int getInventorySlotStart() {
/* 187 */     return getResultSlot() + 1;
/*     */   }
/*     */   
/*     */   private int getInventorySlotEnd() {
/* 191 */     return getInventorySlotStart() + 27;
/*     */   }
/*     */   
/*     */   private int getUseRowStart() {
/* 195 */     return getInventorySlotEnd();
/*     */   }
/*     */   
/*     */   private int getUseRowEnd() {
/* 199 */     return getUseRowStart() + 9;
/*     */   }
/*     */   
/*     */   protected abstract boolean mayPickup(Player paramPlayer, boolean paramBoolean);
/*     */   
/*     */   protected abstract void onTake(Player paramPlayer, ItemStack paramItemStack);
/*     */   
/*     */   protected abstract boolean isValidBlock(BlockState paramBlockState);
/*     */   
/*     */   public abstract void createResult();
/*     */   
/*     */   protected abstract ItemCombinerMenuSlotDefinition createInputSlotDefinitions();
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\inventory\ItemCombinerMenu.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */