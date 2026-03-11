/*     */ package net.minecraft.world.inventory;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.SimpleContainer;
/*     */ import net.minecraft.world.entity.player.Inventory;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.crafting.RecipeHolder;
/*     */ import net.minecraft.world.item.crafting.RecipeType;
/*     */ import net.minecraft.world.item.crafting.StonecutterRecipe;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ 
/*     */ public class StonecutterMenu
/*     */   extends AbstractContainerMenu {
/*     */   public static final int INPUT_SLOT = 0;
/*     */   public static final int RESULT_SLOT = 1;
/*     */   private static final int INV_SLOT_START = 2;
/*     */   private static final int INV_SLOT_END = 29;
/*     */   private static final int USE_ROW_SLOT_START = 29;
/*     */   private static final int USE_ROW_SLOT_END = 38;
/*     */   private final ContainerLevelAccess access;
/*  29 */   private final DataSlot selectedRecipeIndex = DataSlot.standalone();
/*     */   
/*     */   private final Level level;
/*  32 */   private List<RecipeHolder<StonecutterRecipe>> recipes = Lists.newArrayList();
/*  33 */   private ItemStack input = ItemStack.EMPTY;
/*     */   long lastSoundTime;
/*     */   final Slot inputSlot;
/*     */   final Slot resultSlot;
/*     */   Runnable slotUpdateListener = () -> {
/*     */     
/*     */     };
/*     */   
/*  41 */   public final Container container = (Container)new SimpleContainer(1)
/*     */     {
/*     */       public void setChanged() {
/*  44 */         super.setChanged();
/*  45 */         StonecutterMenu.this.slotsChanged((Container)this);
/*  46 */         StonecutterMenu.this.slotUpdateListener.run();
/*     */       }
/*     */     };
/*  49 */   final ResultContainer resultContainer = new ResultContainer();
/*     */   
/*     */   public StonecutterMenu(int $$0, Inventory $$1) {
/*  52 */     this($$0, $$1, ContainerLevelAccess.NULL);
/*     */   }
/*     */   
/*     */   public StonecutterMenu(int $$0, Inventory $$1, final ContainerLevelAccess access) {
/*  56 */     super(MenuType.STONECUTTER, $$0);
/*     */     
/*  58 */     this.access = access;
/*  59 */     this.level = $$1.player.level();
/*     */     
/*  61 */     this.inputSlot = addSlot(new Slot(this.container, 0, 20, 33));
/*     */     
/*  63 */     this.resultSlot = addSlot(new Slot(this.resultContainer, 1, 143, 33)
/*     */         {
/*     */           public boolean mayPlace(ItemStack $$0) {
/*  66 */             return false;
/*     */           }
/*     */ 
/*     */           
/*     */           public void onTake(Player $$0, ItemStack $$1) {
/*  71 */             $$1.onCraftedBy($$0.level(), $$0, $$1.getCount());
/*  72 */             StonecutterMenu.this.resultContainer.awardUsedRecipes($$0, getRelevantItems());
/*     */ 
/*     */             
/*  75 */             ItemStack $$2 = StonecutterMenu.this.inputSlot.remove(1);
/*  76 */             if (!$$2.isEmpty()) {
/*  77 */               StonecutterMenu.this.setupResultSlot();
/*     */             }
/*     */             
/*  80 */             access.execute(($$0, $$1) -> {
/*     */                   long $$2 = $$0.getGameTime();
/*     */                   
/*     */                   if (StonecutterMenu.this.lastSoundTime != $$2) {
/*     */                     $$0.playSound(null, $$1, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundSource.BLOCKS, 1.0F, 1.0F);
/*     */                     
/*     */                     StonecutterMenu.this.lastSoundTime = $$2;
/*     */                   } 
/*     */                 });
/*  89 */             super.onTake($$0, $$1);
/*     */           }
/*     */           
/*     */           private List<ItemStack> getRelevantItems() {
/*  93 */             return List.of(StonecutterMenu.this.inputSlot
/*  94 */                 .getItem());
/*     */           }
/*     */         });
/*     */ 
/*     */     
/*  99 */     for (int $$3 = 0; $$3 < 3; $$3++) {
/* 100 */       for (int $$4 = 0; $$4 < 9; $$4++) {
/* 101 */         addSlot(new Slot((Container)$$1, $$4 + $$3 * 9 + 9, 8 + $$4 * 18, 84 + $$3 * 18));
/*     */       }
/*     */     } 
/* 104 */     for (int $$5 = 0; $$5 < 9; $$5++) {
/* 105 */       addSlot(new Slot((Container)$$1, $$5, 8 + $$5 * 18, 142));
/*     */     }
/*     */     
/* 108 */     addDataSlot(this.selectedRecipeIndex);
/*     */   }
/*     */   
/*     */   public int getSelectedRecipeIndex() {
/* 112 */     return this.selectedRecipeIndex.get();
/*     */   }
/*     */   
/*     */   public List<RecipeHolder<StonecutterRecipe>> getRecipes() {
/* 116 */     return this.recipes;
/*     */   }
/*     */   
/*     */   public int getNumRecipes() {
/* 120 */     return this.recipes.size();
/*     */   }
/*     */   
/*     */   public boolean hasInputItem() {
/* 124 */     return (this.inputSlot.hasItem() && !this.recipes.isEmpty());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean stillValid(Player $$0) {
/* 129 */     return stillValid(this.access, $$0, Blocks.STONECUTTER);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean clickMenuButton(Player $$0, int $$1) {
/* 134 */     if (isValidRecipeIndex($$1)) {
/* 135 */       this.selectedRecipeIndex.set($$1);
/* 136 */       setupResultSlot();
/*     */     } 
/*     */     
/* 139 */     return true;
/*     */   }
/*     */   
/*     */   private boolean isValidRecipeIndex(int $$0) {
/* 143 */     return ($$0 >= 0 && $$0 < this.recipes.size());
/*     */   }
/*     */ 
/*     */   
/*     */   public void slotsChanged(Container $$0) {
/* 148 */     ItemStack $$1 = this.inputSlot.getItem();
/* 149 */     if (!$$1.is(this.input.getItem())) {
/* 150 */       this.input = $$1.copy();
/* 151 */       setupRecipeList($$0, $$1);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setupRecipeList(Container $$0, ItemStack $$1) {
/* 156 */     this.recipes.clear();
/* 157 */     this.selectedRecipeIndex.set(-1);
/* 158 */     this.resultSlot.set(ItemStack.EMPTY);
/*     */     
/* 160 */     if (!$$1.isEmpty()) {
/* 161 */       this.recipes = this.level.getRecipeManager().getRecipesFor(RecipeType.STONECUTTING, $$0, this.level);
/*     */     }
/*     */   }
/*     */   
/*     */   void setupResultSlot() {
/* 166 */     if (!this.recipes.isEmpty() && isValidRecipeIndex(this.selectedRecipeIndex.get())) {
/* 167 */       RecipeHolder<StonecutterRecipe> $$0 = this.recipes.get(this.selectedRecipeIndex.get());
/* 168 */       ItemStack $$1 = ((StonecutterRecipe)$$0.value()).assemble(this.container, this.level.registryAccess());
/* 169 */       if ($$1.isItemEnabled(this.level.enabledFeatures())) {
/* 170 */         this.resultContainer.setRecipeUsed($$0);
/* 171 */         this.resultSlot.set($$1);
/*     */       } else {
/* 173 */         this.resultSlot.set(ItemStack.EMPTY);
/*     */       } 
/*     */     } else {
/* 176 */       this.resultSlot.set(ItemStack.EMPTY);
/*     */     } 
/*     */     
/* 179 */     broadcastChanges();
/*     */   }
/*     */ 
/*     */   
/*     */   public MenuType<?> getType() {
/* 184 */     return MenuType.STONECUTTER;
/*     */   }
/*     */   
/*     */   public void registerUpdateListener(Runnable $$0) {
/* 188 */     this.slotUpdateListener = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canTakeItemForPickAll(ItemStack $$0, Slot $$1) {
/* 193 */     return ($$1.container != this.resultContainer && super.canTakeItemForPickAll($$0, $$1));
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack quickMoveStack(Player $$0, int $$1) {
/* 198 */     ItemStack $$2 = ItemStack.EMPTY;
/* 199 */     Slot $$3 = (Slot)this.slots.get($$1);
/* 200 */     if ($$3 != null && $$3.hasItem()) {
/* 201 */       ItemStack $$4 = $$3.getItem();
/* 202 */       Item $$5 = $$4.getItem();
/* 203 */       $$2 = $$4.copy();
/*     */       
/* 205 */       if ($$1 == 1) {
/* 206 */         $$5.onCraftedBy($$4, $$0.level(), $$0);
/* 207 */         if (!moveItemStackTo($$4, 2, 38, true)) {
/* 208 */           return ItemStack.EMPTY;
/*     */         }
/* 210 */         $$3.onQuickCraft($$4, $$2);
/* 211 */       } else if ($$1 == 0) {
/* 212 */         if (!moveItemStackTo($$4, 2, 38, false)) {
/* 213 */           return ItemStack.EMPTY;
/*     */         }
/* 215 */       } else if (this.level.getRecipeManager().getRecipeFor(RecipeType.STONECUTTING, (Container)new SimpleContainer(new ItemStack[] { $$4 }, ), this.level).isPresent()) {
/* 216 */         if (!moveItemStackTo($$4, 0, 1, false)) {
/* 217 */           return ItemStack.EMPTY;
/*     */         }
/* 219 */       } else if ($$1 >= 2 && $$1 < 29) {
/* 220 */         if (!moveItemStackTo($$4, 29, 38, false)) {
/* 221 */           return ItemStack.EMPTY;
/*     */         }
/* 223 */       } else if ($$1 >= 29 && $$1 < 38 && 
/* 224 */         !moveItemStackTo($$4, 2, 29, false)) {
/* 225 */         return ItemStack.EMPTY;
/*     */       } 
/*     */ 
/*     */       
/* 229 */       if ($$4.isEmpty()) {
/* 230 */         $$3.setByPlayer(ItemStack.EMPTY);
/*     */       }
/*     */       
/* 233 */       $$3.setChanged();
/*     */       
/* 235 */       if ($$4.getCount() == $$2.getCount()) {
/* 236 */         return ItemStack.EMPTY;
/*     */       }
/* 238 */       $$3.onTake($$0, $$4);
/* 239 */       broadcastChanges();
/*     */     } 
/*     */     
/* 242 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void removed(Player $$0) {
/* 247 */     super.removed($$0);
/*     */     
/* 249 */     this.resultContainer.removeItemNoUpdate(1);
/* 250 */     this.access.execute(($$1, $$2) -> clearContainer($$0, this.container));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\inventory\StonecutterMenu.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */