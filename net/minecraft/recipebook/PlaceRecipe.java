/*    */ package net.minecraft.recipebook;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.item.crafting.Recipe;
/*    */ import net.minecraft.world.item.crafting.RecipeHolder;
/*    */ import net.minecraft.world.item.crafting.ShapedRecipe;
/*    */ 
/*    */ public interface PlaceRecipe<T>
/*    */ {
/*    */   default void placeRecipe(int $$0, int $$1, int $$2, RecipeHolder<?> $$3, Iterator<T> $$4, int $$5) {
/* 12 */     int $$6 = $$0;
/* 13 */     int $$7 = $$1;
/* 14 */     Recipe<?> $$8 = $$3.value();
/*    */     
/* 16 */     if ($$8 instanceof ShapedRecipe) { ShapedRecipe $$9 = (ShapedRecipe)$$8;
/* 17 */       $$6 = $$9.getWidth();
/* 18 */       $$7 = $$9.getHeight(); }
/*    */ 
/*    */     
/* 21 */     int $$10 = 0;
/* 22 */     for (int $$11 = 0; $$11 < $$1; $$11++) {
/* 23 */       if ($$10 == $$2) {
/* 24 */         $$10++;
/*    */       }
/*    */       
/* 27 */       boolean $$12 = ($$7 < $$1 / 2.0F);
/* 28 */       int $$13 = Mth.floor($$1 / 2.0F - $$7 / 2.0F);
/*    */       
/* 30 */       if ($$12 && $$13 > $$11) {
/* 31 */         $$10 += $$0;
/* 32 */         $$11++;
/*    */       } 
/*    */       
/* 35 */       for (int $$14 = 0; $$14 < $$0; $$14++) {
/* 36 */         if (!$$4.hasNext()) {
/*    */           return;
/*    */         }
/*    */         
/* 40 */         $$12 = ($$6 < $$0 / 2.0F);
/* 41 */         $$13 = Mth.floor($$0 / 2.0F - $$6 / 2.0F);
/* 42 */         int $$15 = $$6;
/* 43 */         boolean $$16 = ($$14 < $$6);
/* 44 */         if ($$12) {
/* 45 */           $$15 = $$13 + $$6;
/* 46 */           $$16 = ($$13 <= $$14 && $$14 < $$13 + $$6);
/*    */         } 
/*    */ 
/*    */         
/* 50 */         if ($$16) {
/* 51 */           addItemToSlot($$4, $$10, $$5, $$11, $$14);
/* 52 */         } else if ($$15 == $$14) {
/* 53 */           $$10 += $$0 - $$14;
/*    */           
/*    */           break;
/*    */         } 
/* 57 */         $$10++;
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   void addItemToSlot(Iterator<T> paramIterator, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\recipebook\PlaceRecipe.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */