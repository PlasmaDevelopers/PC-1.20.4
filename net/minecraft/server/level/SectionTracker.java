/*    */ package net.minecraft.server.level;
/*    */ 
/*    */ import net.minecraft.core.SectionPos;
/*    */ import net.minecraft.world.level.lighting.DynamicGraphMinFixedPoint;
/*    */ 
/*    */ public abstract class SectionTracker extends DynamicGraphMinFixedPoint {
/*    */   protected SectionTracker(int $$0, int $$1, int $$2) {
/*  8 */     super($$0, $$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void checkNeighborsAfterUpdate(long $$0, int $$1, boolean $$2) {
/* 13 */     if ($$2 && $$1 >= this.levelCount - 2) {
/*    */       return;
/*    */     }
/*    */     
/* 17 */     for (int $$3 = -1; $$3 <= 1; $$3++) {
/* 18 */       for (int $$4 = -1; $$4 <= 1; $$4++) {
/* 19 */         for (int $$5 = -1; $$5 <= 1; $$5++) {
/* 20 */           long $$6 = SectionPos.offset($$0, $$3, $$4, $$5);
/* 21 */           if ($$6 != $$0)
/*    */           {
/*    */             
/* 24 */             checkNeighbor($$0, $$6, $$1, $$2);
/*    */           }
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   protected int getComputedLevel(long $$0, long $$1, int $$2) {
/* 32 */     int $$3 = $$2;
/* 33 */     for (int $$4 = -1; $$4 <= 1; $$4++) {
/* 34 */       for (int $$5 = -1; $$5 <= 1; $$5++) {
/* 35 */         for (int $$6 = -1; $$6 <= 1; $$6++) {
/* 36 */           long $$7 = SectionPos.offset($$0, $$4, $$5, $$6);
/* 37 */           if ($$7 == $$0) {
/* 38 */             $$7 = Long.MAX_VALUE;
/*    */           }
/* 40 */           if ($$7 != $$1) {
/* 41 */             int $$8 = computeLevelFromNeighbor($$7, $$0, getLevel($$7));
/* 42 */             if ($$3 > $$8) {
/* 43 */               $$3 = $$8;
/*    */             }
/* 45 */             if ($$3 == 0) {
/* 46 */               return $$3;
/*    */             }
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } 
/* 52 */     return $$3;
/*    */   }
/*    */ 
/*    */   
/*    */   protected int computeLevelFromNeighbor(long $$0, long $$1, int $$2) {
/* 57 */     if (isSource($$0)) {
/* 58 */       return getLevelFromSource($$1);
/*    */     }
/* 60 */     return $$2 + 1;
/*    */   }
/*    */   
/*    */   protected abstract int getLevelFromSource(long paramLong);
/*    */   
/*    */   public void update(long $$0, int $$1, boolean $$2) {
/* 66 */     checkEdge(Long.MAX_VALUE, $$0, $$1, $$2);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\level\SectionTracker.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */