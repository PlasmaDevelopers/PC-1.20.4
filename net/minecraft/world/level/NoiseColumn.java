/*    */ package net.minecraft.world.level;
/*    */ 
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.chunk.BlockColumn;
/*    */ 
/*    */ public final class NoiseColumn implements BlockColumn {
/*    */   private final int minY;
/*    */   private final BlockState[] column;
/*    */   
/*    */   public NoiseColumn(int $$0, BlockState[] $$1) {
/* 12 */     this.minY = $$0;
/* 13 */     this.column = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState getBlock(int $$0) {
/* 18 */     int $$1 = $$0 - this.minY;
/* 19 */     if ($$1 < 0 || $$1 >= this.column.length) {
/* 20 */       return Blocks.AIR.defaultBlockState();
/*    */     }
/* 22 */     return this.column[$$1];
/*    */   }
/*    */ 
/*    */   
/*    */   public void setBlock(int $$0, BlockState $$1) {
/* 27 */     int $$2 = $$0 - this.minY;
/* 28 */     if ($$2 < 0 || $$2 >= this.column.length) {
/* 29 */       throw new IllegalArgumentException("Outside of column height: " + $$0);
/*    */     }
/* 31 */     this.column[$$2] = $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\NoiseColumn.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */