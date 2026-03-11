/*    */ package net.minecraft.world.level.lighting;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.longs.LongLinkedOpenHashSet;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class null
/*    */   extends LongLinkedOpenHashSet
/*    */ {
/*    */   null(int $$1, float $$2) {
/* 15 */     super($$1, $$2);
/*    */   }
/*    */   protected void rehash(int $$0) {
/* 18 */     if ($$0 > minSize)
/* 19 */       super.rehash($$0); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\lighting\LeveledPriorityQueue$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */