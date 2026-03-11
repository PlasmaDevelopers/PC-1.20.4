/*    */ package net.minecraft.world.level.chunk.storage;
/*    */ 
/*    */ import com.google.common.annotations.VisibleForTesting;
/*    */ import it.unimi.dsi.fastutil.ints.IntCollection;
/*    */ import it.unimi.dsi.fastutil.ints.IntSet;
/*    */ import java.util.BitSet;
/*    */ import java.util.function.Supplier;
/*    */ 
/*    */ public class RegionBitmap {
/* 10 */   private final BitSet used = new BitSet();
/*    */   
/*    */   public void force(int $$0, int $$1) {
/* 13 */     this.used.set($$0, $$0 + $$1);
/*    */   }
/*    */   
/*    */   public void free(int $$0, int $$1) {
/* 17 */     this.used.clear($$0, $$0 + $$1);
/*    */   }
/*    */   
/*    */   public int allocate(int $$0) {
/* 21 */     int $$1 = 0;
/*    */     while (true) {
/* 23 */       int $$2 = this.used.nextClearBit($$1);
/* 24 */       int $$3 = this.used.nextSetBit($$2);
/* 25 */       if ($$3 == -1 || $$3 - $$2 >= $$0) {
/* 26 */         force($$2, $$0);
/* 27 */         return $$2;
/*    */       } 
/* 29 */       $$1 = $$3;
/*    */     } 
/*    */   }
/*    */   
/*    */   @VisibleForTesting
/*    */   public IntSet getUsed() {
/* 35 */     return this.used.stream().<IntSet>collect(it.unimi.dsi.fastutil.ints.IntArraySet::new, IntCollection::add, IntCollection::addAll);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\chunk\storage\RegionBitmap.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */