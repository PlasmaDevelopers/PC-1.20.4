/*     */ package net.minecraft.world.entity.player;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import it.unimi.dsi.fastutil.ints.IntAVLTreeSet;
/*     */ import it.unimi.dsi.fastutil.ints.IntArrayList;
/*     */ import it.unimi.dsi.fastutil.ints.IntCollection;
/*     */ import it.unimi.dsi.fastutil.ints.IntIterator;
/*     */ import it.unimi.dsi.fastutil.ints.IntList;
/*     */ import it.unimi.dsi.fastutil.ints.IntListIterator;
/*     */ import java.util.BitSet;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.NonNullList;
/*     */ import net.minecraft.world.item.crafting.Ingredient;
/*     */ import net.minecraft.world.item.crafting.Recipe;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class RecipePicker
/*     */ {
/*     */   private final Recipe<?> recipe;
/*  95 */   private final List<Ingredient> ingredients = Lists.newArrayList();
/*     */   private final int ingredientCount;
/*     */   private final int[] items;
/*     */   private final int itemCount;
/*     */   private final BitSet data;
/* 100 */   private final IntList path = (IntList)new IntArrayList();
/*     */   
/*     */   public RecipePicker(Recipe<?> $$0) {
/* 103 */     this.recipe = $$0;
/* 104 */     this.ingredients.addAll((Collection<? extends Ingredient>)$$0.getIngredients());
/* 105 */     this.ingredients.removeIf(Ingredient::isEmpty);
/*     */     
/* 107 */     this.ingredientCount = this.ingredients.size();
/* 108 */     this.items = getUniqueAvailableIngredientItems();
/* 109 */     this.itemCount = this.items.length;
/*     */ 
/*     */     
/* 112 */     this.data = new BitSet(this.ingredientCount + this.itemCount + this.ingredientCount + this.ingredientCount * this.itemCount);
/* 113 */     for (int $$1 = 0; $$1 < this.ingredients.size(); $$1++) {
/* 114 */       IntList $$2 = ((Ingredient)this.ingredients.get($$1)).getStackingIds();
/* 115 */       for (int $$3 = 0; $$3 < this.itemCount; $$3++) {
/* 116 */         if ($$2.contains(this.items[$$3])) {
/* 117 */           this.data.set(getIndex(true, $$3, $$1));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean tryPick(int $$0, @Nullable IntList $$1) {
/* 124 */     if ($$0 <= 0) {
/* 125 */       return true;
/*     */     }
/*     */     
/* 128 */     int $$2 = 0;
/* 129 */     while (dfs($$0)) {
/* 130 */       StackedContents.this.take(this.items[this.path.getInt(0)], $$0);
/*     */       
/* 132 */       int $$3 = this.path.size() - 1;
/* 133 */       setSatisfied(this.path.getInt($$3));
/*     */       
/* 135 */       for (int $$4 = 0; $$4 < $$3; $$4++) {
/* 136 */         toggleResidual((($$4 & 0x1) == 0), this.path.get($$4).intValue(), this.path.get($$4 + 1).intValue());
/*     */       }
/*     */       
/* 139 */       this.path.clear();
/* 140 */       this.data.clear(0, this.ingredientCount + this.itemCount);
/*     */       
/* 142 */       $$2++;
/*     */     } 
/*     */     
/* 145 */     boolean $$5 = ($$2 == this.ingredientCount);
/* 146 */     boolean $$6 = ($$5 && $$1 != null);
/* 147 */     if ($$6) {
/* 148 */       $$1.clear();
/*     */     }
/*     */ 
/*     */     
/* 152 */     this.data.clear(0, this.ingredientCount + this.itemCount + this.ingredientCount);
/*     */     
/* 154 */     int $$7 = 0;
/* 155 */     NonNullList nonNullList = this.recipe.getIngredients();
/* 156 */     for (Ingredient $$9 : nonNullList) {
/* 157 */       if ($$6 && $$9.isEmpty()) {
/* 158 */         $$1.add(0); continue;
/*     */       } 
/* 160 */       for (int $$10 = 0; $$10 < this.itemCount; $$10++) {
/* 161 */         if (hasResidual(false, $$7, $$10)) {
/* 162 */           toggleResidual(true, $$10, $$7);
/* 163 */           StackedContents.this.put(this.items[$$10], $$0);
/*     */           
/* 165 */           if ($$6) {
/* 166 */             $$1.add(this.items[$$10]);
/*     */           }
/*     */         } 
/*     */       } 
/* 170 */       $$7++;
/*     */     } 
/*     */ 
/*     */     
/* 174 */     return $$5;
/*     */   }
/*     */   
/*     */   private int[] getUniqueAvailableIngredientItems() {
/* 178 */     IntAVLTreeSet intAVLTreeSet = new IntAVLTreeSet();
/* 179 */     for (Ingredient $$1 : this.ingredients) {
/* 180 */       intAVLTreeSet.addAll((IntCollection)$$1.getStackingIds());
/*     */     }
/*     */     
/* 183 */     IntIterator $$2 = intAVLTreeSet.iterator();
/* 184 */     while ($$2.hasNext()) {
/* 185 */       if (!StackedContents.this.has($$2.nextInt())) {
/* 186 */         $$2.remove();
/*     */       }
/*     */     } 
/* 189 */     return intAVLTreeSet.toIntArray();
/*     */   }
/*     */   
/*     */   private boolean dfs(int $$0) {
/* 193 */     int $$1 = this.itemCount;
/* 194 */     for (int $$2 = 0; $$2 < $$1; $$2++) {
/* 195 */       if (StackedContents.this.contents.get(this.items[$$2]) >= $$0) {
/* 196 */         visit(false, $$2);
/*     */         
/* 198 */         while (!this.path.isEmpty()) {
/* 199 */           int $$3 = this.path.size();
/* 200 */           boolean $$4 = (($$3 & 0x1) == 1);
/*     */           
/* 202 */           int $$5 = this.path.getInt($$3 - 1);
/* 203 */           if (!$$4 && !isSatisfied($$5)) {
/*     */             break;
/*     */           }
/*     */           
/* 207 */           int $$6 = $$4 ? this.ingredientCount : $$1;
/* 208 */           for (int $$7 = 0; $$7 < $$6; $$7++) {
/* 209 */             if (!hasVisited($$4, $$7) && hasConnection($$4, $$5, $$7) && hasResidual($$4, $$5, $$7)) {
/* 210 */               visit($$4, $$7);
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/* 215 */           int $$8 = this.path.size();
/* 216 */           if ($$8 == $$3) {
/* 217 */             this.path.removeInt($$8 - 1);
/*     */           }
/*     */         } 
/* 220 */         if (!this.path.isEmpty()) {
/* 221 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 226 */     return false;
/*     */   }
/*     */   
/*     */   private boolean isSatisfied(int $$0) {
/* 230 */     return this.data.get(getSatisfiedIndex($$0));
/*     */   }
/*     */   
/*     */   private void setSatisfied(int $$0) {
/* 234 */     this.data.set(getSatisfiedIndex($$0));
/*     */   }
/*     */   
/*     */   private int getSatisfiedIndex(int $$0) {
/* 238 */     return this.ingredientCount + this.itemCount + $$0;
/*     */   }
/*     */   
/*     */   private boolean hasConnection(boolean $$0, int $$1, int $$2) {
/* 242 */     return this.data.get(getIndex($$0, $$1, $$2));
/*     */   }
/*     */   
/*     */   private boolean hasResidual(boolean $$0, int $$1, int $$2) {
/* 246 */     return ($$0 != this.data.get(1 + getIndex($$0, $$1, $$2)));
/*     */   }
/*     */   
/*     */   private void toggleResidual(boolean $$0, int $$1, int $$2) {
/* 250 */     this.data.flip(1 + getIndex($$0, $$1, $$2));
/*     */   }
/*     */   
/*     */   private int getIndex(boolean $$0, int $$1, int $$2) {
/* 254 */     int $$3 = $$0 ? ($$1 * this.ingredientCount + $$2) : ($$2 * this.ingredientCount + $$1);
/* 255 */     return this.ingredientCount + this.itemCount + this.ingredientCount + 2 * $$3;
/*     */   }
/*     */   
/*     */   private void visit(boolean $$0, int $$1) {
/* 259 */     this.data.set(getVisitedIndex($$0, $$1));
/* 260 */     this.path.add($$1);
/*     */   }
/*     */   
/*     */   private boolean hasVisited(boolean $$0, int $$1) {
/* 264 */     return this.data.get(getVisitedIndex($$0, $$1));
/*     */   }
/*     */   
/*     */   private int getVisitedIndex(boolean $$0, int $$1) {
/* 268 */     return ($$0 ? 0 : this.ingredientCount) + $$1;
/*     */   }
/*     */   
/*     */   public int tryPickAll(int $$0, @Nullable IntList $$1) {
/* 272 */     int $$4, $$2 = 0;
/* 273 */     int $$3 = Math.min($$0, getMinIngredientCount()) + 1;
/*     */ 
/*     */     
/*     */     while (true) {
/* 277 */       $$4 = ($$2 + $$3) / 2;
/*     */       
/* 279 */       if (tryPick($$4, null)) {
/* 280 */         if ($$3 - $$2 <= 1) {
/*     */           break;
/*     */         }
/* 283 */         $$2 = $$4; continue;
/*     */       } 
/* 285 */       $$3 = $$4;
/*     */     } 
/*     */ 
/*     */     
/* 289 */     if ($$4 > 0) {
/* 290 */       tryPick($$4, $$1);
/*     */     }
/*     */     
/* 293 */     return $$4;
/*     */   }
/*     */   
/*     */   private int getMinIngredientCount() {
/* 297 */     int $$0 = Integer.MAX_VALUE;
/* 298 */     for (Ingredient $$1 : this.ingredients) {
/* 299 */       int $$2 = 0;
/* 300 */       for (IntListIterator<Integer> intListIterator = $$1.getStackingIds().iterator(); intListIterator.hasNext(); ) { int $$3 = ((Integer)intListIterator.next()).intValue();
/* 301 */         $$2 = Math.max($$2, StackedContents.this.contents.get($$3)); }
/*     */       
/* 303 */       if ($$0 > 0) {
/* 304 */         $$0 = Math.min($$0, $$2);
/*     */       }
/*     */     } 
/* 307 */     return $$0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\player\StackedContents$RecipePicker.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */