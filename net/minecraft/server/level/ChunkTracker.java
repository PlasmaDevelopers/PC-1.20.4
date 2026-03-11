/*    */ package net.minecraft.server.level;
/*    */ 
/*    */ import net.minecraft.world.level.ChunkPos;
/*    */ import net.minecraft.world.level.lighting.DynamicGraphMinFixedPoint;
/*    */ 
/*    */ public abstract class ChunkTracker extends DynamicGraphMinFixedPoint {
/*    */   protected ChunkTracker(int $$0, int $$1, int $$2) {
/*  8 */     super($$0, $$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isSource(long $$0) {
/* 13 */     return ($$0 == ChunkPos.INVALID_CHUNK_POS);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void checkNeighborsAfterUpdate(long $$0, int $$1, boolean $$2) {
/* 18 */     if ($$2 && $$1 >= this.levelCount - 2) {
/*    */       return;
/*    */     }
/*    */     
/* 22 */     ChunkPos $$3 = new ChunkPos($$0);
/* 23 */     int $$4 = $$3.x;
/* 24 */     int $$5 = $$3.z;
/* 25 */     for (int $$6 = -1; $$6 <= 1; $$6++) {
/* 26 */       for (int $$7 = -1; $$7 <= 1; $$7++) {
/* 27 */         long $$8 = ChunkPos.asLong($$4 + $$6, $$5 + $$7);
/* 28 */         if ($$8 != $$0)
/*    */         {
/*    */           
/* 31 */           checkNeighbor($$0, $$8, $$1, $$2);
/*    */         }
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   protected int getComputedLevel(long $$0, long $$1, int $$2) {
/* 38 */     int $$3 = $$2;
/* 39 */     ChunkPos $$4 = new ChunkPos($$0);
/* 40 */     int $$5 = $$4.x;
/* 41 */     int $$6 = $$4.z;
/* 42 */     for (int $$7 = -1; $$7 <= 1; $$7++) {
/* 43 */       for (int $$8 = -1; $$8 <= 1; $$8++) {
/* 44 */         long $$9 = ChunkPos.asLong($$5 + $$7, $$6 + $$8);
/* 45 */         if ($$9 == $$0) {
/* 46 */           $$9 = ChunkPos.INVALID_CHUNK_POS;
/*    */         }
/* 48 */         if ($$9 != $$1) {
/* 49 */           int $$10 = computeLevelFromNeighbor($$9, $$0, getLevel($$9));
/* 50 */           if ($$3 > $$10) {
/* 51 */             $$3 = $$10;
/*    */           }
/* 53 */           if ($$3 == 0) {
/* 54 */             return $$3;
/*    */           }
/*    */         } 
/*    */       } 
/*    */     } 
/* 59 */     return $$3;
/*    */   }
/*    */ 
/*    */   
/*    */   protected int computeLevelFromNeighbor(long $$0, long $$1, int $$2) {
/* 64 */     if ($$0 == ChunkPos.INVALID_CHUNK_POS) {
/* 65 */       return getLevelFromSource($$1);
/*    */     }
/* 67 */     return $$2 + 1;
/*    */   }
/*    */   
/*    */   protected abstract int getLevelFromSource(long paramLong);
/*    */   
/*    */   public void update(long $$0, int $$1, boolean $$2) {
/* 73 */     checkEdge(ChunkPos.INVALID_CHUNK_POS, $$0, $$1, $$2);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\level\ChunkTracker.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */