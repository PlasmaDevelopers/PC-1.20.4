/*     */ package net.minecraft.world.level.redstone;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.state.BlockState;
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
/*     */ final class MultiNeighborUpdate
/*     */   implements CollectingNeighborUpdater.NeighborUpdates
/*     */ {
/*     */   private final BlockPos sourcePos;
/*     */   private final Block sourceBlock;
/*     */   @Nullable
/*     */   private final Direction skipDirection;
/* 117 */   private int idx = 0;
/*     */   
/*     */   MultiNeighborUpdate(BlockPos $$0, Block $$1, @Nullable Direction $$2) {
/* 120 */     this.sourcePos = $$0;
/* 121 */     this.sourceBlock = $$1;
/* 122 */     this.skipDirection = $$2;
/* 123 */     if (NeighborUpdater.UPDATE_ORDER[this.idx] == $$2) {
/* 124 */       this.idx++;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean runNext(Level $$0) {
/* 130 */     BlockPos $$1 = this.sourcePos.relative(NeighborUpdater.UPDATE_ORDER[this.idx++]);
/* 131 */     BlockState $$2 = $$0.getBlockState($$1);
/* 132 */     NeighborUpdater.executeUpdate($$0, $$2, $$1, this.sourceBlock, this.sourcePos, false);
/* 133 */     if (this.idx < NeighborUpdater.UPDATE_ORDER.length && NeighborUpdater.UPDATE_ORDER[this.idx] == this.skipDirection) {
/* 134 */       this.idx++;
/*     */     }
/* 136 */     return (this.idx < NeighborUpdater.UPDATE_ORDER.length);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\redstone\CollectingNeighborUpdater$MultiNeighborUpdate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */