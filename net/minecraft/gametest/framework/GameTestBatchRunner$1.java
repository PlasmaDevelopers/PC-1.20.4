/*     */ package net.minecraft.gametest.framework;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.longs.LongArraySet;
/*     */ import net.minecraft.world.level.ChunkPos;
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
/*     */   implements GameTestListener
/*     */ {
/*     */   private void testCompleted() {
/*  81 */     if (currentBatchTracker.isDone()) {
/*  82 */       currentBatch.runAfterBatchFunction(GameTestBatchRunner.this.level);
/*  83 */       LongArraySet longArraySet = new LongArraySet(GameTestBatchRunner.this.level.getForcedChunks());
/*  84 */       longArraySet.forEach($$0 -> GameTestBatchRunner.this.level.setChunkForced(ChunkPos.getX($$0), ChunkPos.getZ($$0), false));
/*  85 */       GameTestBatchRunner.this.runBatch(batchIndex + 1);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void testStructureLoaded(GameTestInfo $$0) {}
/*     */ 
/*     */   
/*     */   public void testPassed(GameTestInfo $$0) {
/*  95 */     testCompleted();
/*     */   }
/*     */ 
/*     */   
/*     */   public void testFailed(GameTestInfo $$0) {
/* 100 */     testCompleted();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\gametest\framework\GameTestBatchRunner$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */