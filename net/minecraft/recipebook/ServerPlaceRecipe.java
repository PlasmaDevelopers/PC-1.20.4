/*     */ package net.minecraft.recipebook;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import it.unimi.dsi.fastutil.ints.IntArrayList;
/*     */ import it.unimi.dsi.fastutil.ints.IntList;
/*     */ import it.unimi.dsi.fastutil.ints.IntListIterator;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.game.ClientboundPlaceGhostRecipePacket;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.entity.player.Inventory;
/*     */ import net.minecraft.world.entity.player.StackedContents;
/*     */ import net.minecraft.world.inventory.RecipeBookMenu;
/*     */ import net.minecraft.world.inventory.Slot;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.crafting.Recipe;
/*     */ import net.minecraft.world.item.crafting.RecipeHolder;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class ServerPlaceRecipe<C extends Container> implements PlaceRecipe<Integer> {
/*  24 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*  26 */   protected final StackedContents stackedContents = new StackedContents();
/*     */   
/*     */   protected Inventory inventory;
/*     */   protected RecipeBookMenu<C> menu;
/*     */   
/*     */   public ServerPlaceRecipe(RecipeBookMenu<C> $$0) {
/*  32 */     this.menu = $$0;
/*     */   }
/*     */   
/*     */   public void recipeClicked(ServerPlayer $$0, @Nullable RecipeHolder<? extends Recipe<C>> $$1, boolean $$2) {
/*  36 */     if ($$1 == null || !$$0.getRecipeBook().contains($$1)) {
/*     */       return;
/*     */     }
/*     */     
/*  40 */     this.inventory = $$0.getInventory();
/*     */ 
/*     */     
/*  43 */     if (!testClearGrid() && !$$0.isCreative()) {
/*     */       return;
/*     */     }
/*     */     
/*  47 */     this.stackedContents.clear();
/*  48 */     $$0.getInventory().fillStackedContents(this.stackedContents);
/*  49 */     this.menu.fillCraftSlotsStackedContents(this.stackedContents);
/*     */     
/*  51 */     if (this.stackedContents.canCraft($$1.value(), null)) {
/*  52 */       handleRecipeClicked($$1, $$2);
/*     */     } else {
/*  54 */       clearGrid();
/*  55 */       $$0.connection.send((Packet)new ClientboundPlaceGhostRecipePacket($$0.containerMenu.containerId, $$1));
/*     */     } 
/*     */     
/*  58 */     $$0.getInventory().setChanged();
/*     */   }
/*     */   
/*     */   protected void clearGrid() {
/*  62 */     for (int $$0 = 0; $$0 < this.menu.getSize(); $$0++) {
/*  63 */       if (this.menu.shouldMoveToInventory($$0)) {
/*  64 */         ItemStack $$1 = this.menu.getSlot($$0).getItem().copy();
/*  65 */         this.inventory.placeItemBackInInventory($$1, false);
/*  66 */         this.menu.getSlot($$0).set($$1);
/*     */       } 
/*     */     } 
/*  69 */     this.menu.clearCraftingContent();
/*     */   }
/*     */   
/*     */   protected void handleRecipeClicked(RecipeHolder<? extends Recipe<C>> $$0, boolean $$1) {
/*  73 */     boolean $$2 = this.menu.recipeMatches($$0);
/*  74 */     int $$3 = this.stackedContents.getBiggestCraftableStack($$0, null);
/*     */ 
/*     */     
/*  77 */     if ($$2) {
/*  78 */       for (int $$4 = 0; $$4 < this.menu.getGridHeight() * this.menu.getGridWidth() + 1; $$4++) {
/*  79 */         if ($$4 != this.menu.getResultSlotIndex()) {
/*     */ 
/*     */           
/*  82 */           ItemStack $$5 = this.menu.getSlot($$4).getItem();
/*  83 */           if (!$$5.isEmpty() && Math.min($$3, $$5.getMaxStackSize()) < $$5.getCount() + 1) {
/*     */             return;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*  89 */     int $$6 = getStackSize($$1, $$3, $$2);
/*  90 */     IntArrayList intArrayList = new IntArrayList();
/*  91 */     if (this.stackedContents.canCraft($$0.value(), (IntList)intArrayList, $$6)) {
/*     */       
/*  93 */       int $$8 = $$6;
/*  94 */       for (IntListIterator<Integer> intListIterator = intArrayList.iterator(); intListIterator.hasNext(); ) { int $$9 = ((Integer)intListIterator.next()).intValue();
/*  95 */         int $$10 = StackedContents.fromStackingIndex($$9).getMaxStackSize();
/*  96 */         if ($$10 < $$8) {
/*  97 */           $$8 = $$10;
/*     */         } }
/*     */       
/* 100 */       $$6 = $$8;
/*     */ 
/*     */       
/* 103 */       if (this.stackedContents.canCraft($$0.value(), (IntList)intArrayList, $$6)) {
/* 104 */         clearGrid();
/* 105 */         placeRecipe(this.menu.getGridWidth(), this.menu.getGridHeight(), this.menu.getResultSlotIndex(), $$0, (Iterator<Integer>)intArrayList.iterator(), $$6);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void addItemToSlot(Iterator<Integer> $$0, int $$1, int $$2, int $$3, int $$4) {
/* 112 */     Slot $$5 = this.menu.getSlot($$1);
/* 113 */     ItemStack $$6 = StackedContents.fromStackingIndex(((Integer)$$0.next()).intValue());
/* 114 */     if (!$$6.isEmpty()) {
/* 115 */       for (int $$7 = 0; $$7 < $$2; $$7++) {
/* 116 */         moveItemToGrid($$5, $$6);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected int getStackSize(boolean $$0, int $$1, boolean $$2) {
/* 122 */     int $$3 = 1;
/* 123 */     if ($$0) {
/* 124 */       $$3 = $$1;
/* 125 */     } else if ($$2) {
/* 126 */       $$3 = 64;
/* 127 */       for (int $$4 = 0; $$4 < this.menu.getGridWidth() * this.menu.getGridHeight() + 1; $$4++) {
/* 128 */         if ($$4 != this.menu.getResultSlotIndex()) {
/*     */ 
/*     */ 
/*     */           
/* 132 */           ItemStack $$5 = this.menu.getSlot($$4).getItem();
/* 133 */           if (!$$5.isEmpty() && $$3 > $$5.getCount()) {
/* 134 */             $$3 = $$5.getCount();
/*     */           }
/*     */         } 
/*     */       } 
/* 138 */       if ($$3 < 64) {
/* 139 */         $$3++;
/*     */       }
/*     */     } 
/*     */     
/* 143 */     return $$3;
/*     */   }
/*     */   
/*     */   protected void moveItemToGrid(Slot $$0, ItemStack $$1) {
/* 147 */     int $$2 = this.inventory.findSlotMatchingUnusedItem($$1);
/* 148 */     if ($$2 == -1) {
/*     */       return;
/*     */     }
/* 151 */     ItemStack $$3 = this.inventory.getItem($$2);
/* 152 */     if ($$3.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 156 */     if ($$3.getCount() > 1) {
/* 157 */       this.inventory.removeItem($$2, 1);
/*     */     } else {
/* 159 */       this.inventory.removeItemNoUpdate($$2);
/*     */     } 
/*     */     
/* 162 */     if ($$0.getItem().isEmpty()) {
/* 163 */       $$0.set($$3.copyWithCount(1));
/*     */     } else {
/* 165 */       $$0.getItem().grow(1);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean testClearGrid() {
/* 170 */     List<ItemStack> $$0 = Lists.newArrayList();
/* 171 */     int $$1 = getAmountOfFreeSlotsInInventory();
/*     */     
/* 173 */     for (int $$2 = 0; $$2 < this.menu.getGridWidth() * this.menu.getGridHeight() + 1; $$2++) {
/* 174 */       if ($$2 != this.menu.getResultSlotIndex()) {
/*     */ 
/*     */ 
/*     */         
/* 178 */         ItemStack $$3 = this.menu.getSlot($$2).getItem().copy();
/* 179 */         if (!$$3.isEmpty()) {
/*     */ 
/*     */ 
/*     */           
/* 183 */           int $$4 = this.inventory.getSlotWithRemainingSpace($$3);
/* 184 */           if ($$4 == -1 && $$0.size() <= $$1) {
/* 185 */             for (ItemStack $$5 : $$0) {
/* 186 */               if (ItemStack.isSameItem($$5, $$3) && $$5.getCount() != $$5.getMaxStackSize() && $$5.getCount() + $$3.getCount() <= $$5.getMaxStackSize()) {
/* 187 */                 $$5.grow($$3.getCount());
/* 188 */                 $$3.setCount(0);
/*     */                 
/*     */                 break;
/*     */               } 
/*     */             } 
/* 193 */             if (!$$3.isEmpty()) {
/* 194 */               if ($$0.size() < $$1) {
/* 195 */                 $$0.add($$3);
/*     */               } else {
/* 197 */                 return false;
/*     */               
/*     */               }
/*     */ 
/*     */             
/*     */             }
/*     */           }
/* 204 */           else if ($$4 == -1) {
/* 205 */             return false;
/*     */           } 
/*     */         } 
/*     */       } 
/* 209 */     }  return true;
/*     */   }
/*     */   
/*     */   private int getAmountOfFreeSlotsInInventory() {
/* 213 */     int $$0 = 0;
/* 214 */     for (ItemStack $$1 : this.inventory.items) {
/* 215 */       if ($$1.isEmpty()) {
/* 216 */         $$0++;
/*     */       }
/*     */     } 
/* 219 */     return $$0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\recipebook\ServerPlaceRecipe.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */