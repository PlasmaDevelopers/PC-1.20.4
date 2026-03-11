/*     */ package net.minecraft.world.level.block.state.pattern;
/*     */ 
/*     */ import com.google.common.base.MoreObjects;
/*     */ import com.google.common.cache.LoadingCache;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockPatternMatch
/*     */ {
/*     */   private final BlockPos frontTopLeft;
/*     */   private final Direction forwards;
/*     */   private final Direction up;
/*     */   private final LoadingCache<BlockPos, BlockInWorld> cache;
/*     */   private final int width;
/*     */   private final int height;
/*     */   private final int depth;
/*     */   
/*     */   public BlockPatternMatch(BlockPos $$0, Direction $$1, Direction $$2, LoadingCache<BlockPos, BlockInWorld> $$3, int $$4, int $$5, int $$6) {
/* 149 */     this.frontTopLeft = $$0;
/* 150 */     this.forwards = $$1;
/* 151 */     this.up = $$2;
/* 152 */     this.cache = $$3;
/* 153 */     this.width = $$4;
/* 154 */     this.height = $$5;
/* 155 */     this.depth = $$6;
/*     */   }
/*     */   
/*     */   public BlockPos getFrontTopLeft() {
/* 159 */     return this.frontTopLeft;
/*     */   }
/*     */   
/*     */   public Direction getForwards() {
/* 163 */     return this.forwards;
/*     */   }
/*     */   
/*     */   public Direction getUp() {
/* 167 */     return this.up;
/*     */   }
/*     */   
/*     */   public int getWidth() {
/* 171 */     return this.width;
/*     */   }
/*     */   
/*     */   public int getHeight() {
/* 175 */     return this.height;
/*     */   }
/*     */   
/*     */   public int getDepth() {
/* 179 */     return this.depth;
/*     */   }
/*     */   
/*     */   public BlockInWorld getBlock(int $$0, int $$1, int $$2) {
/* 183 */     return (BlockInWorld)this.cache.getUnchecked(BlockPattern.translateAndRotate(this.frontTopLeft, getForwards(), getUp(), $$0, $$1, $$2));
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 188 */     return MoreObjects.toStringHelper(this)
/* 189 */       .add("up", this.up)
/* 190 */       .add("forwards", this.forwards)
/* 191 */       .add("frontTopLeft", this.frontTopLeft)
/* 192 */       .toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\state\pattern\BlockPattern$BlockPatternMatch.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */