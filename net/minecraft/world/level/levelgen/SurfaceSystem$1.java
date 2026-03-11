/*     */ package net.minecraft.world.level.levelgen;
/*     */ 
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.LevelHeightAccessor;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.chunk.BlockColumn;
/*     */ import net.minecraft.world.level.chunk.ChunkAccess;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ class null
/*     */   implements BlockColumn
/*     */ {
/*     */   public BlockState getBlock(int $$0) {
/*  91 */     return protoChunk.getBlockState((BlockPos)columnPos.setY($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBlock(int $$0, BlockState $$1) {
/*  96 */     LevelHeightAccessor $$2 = protoChunk.getHeightAccessorForGeneration();
/*  97 */     if ($$0 >= $$2.getMinBuildHeight() && $$0 < $$2.getMaxBuildHeight()) {
/*  98 */       protoChunk.setBlockState((BlockPos)columnPos.setY($$0), $$1, false);
/*  99 */       if (!$$1.getFluidState().isEmpty()) {
/* 100 */         protoChunk.markPosForPostprocessing((BlockPos)columnPos);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 107 */     return "ChunkBlockColumn " + chunkPos;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\SurfaceSystem$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */