/*     */ package net.minecraft.server.commands;
/*     */ 
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.BlockGetter;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class Position
/*     */ {
/*     */   double x;
/*     */   double z;
/*     */   
/*     */   double dist(Position $$0) {
/* 252 */     double $$1 = this.x - $$0.x;
/* 253 */     double $$2 = this.z - $$0.z;
/*     */     
/* 255 */     return Math.sqrt($$1 * $$1 + $$2 * $$2);
/*     */   }
/*     */   
/*     */   void normalize() {
/* 259 */     double $$0 = getLength();
/* 260 */     this.x /= $$0;
/* 261 */     this.z /= $$0;
/*     */   }
/*     */   
/*     */   double getLength() {
/* 265 */     return Math.sqrt(this.x * this.x + this.z * this.z);
/*     */   }
/*     */   
/*     */   public void moveAway(Position $$0) {
/* 269 */     this.x -= $$0.x;
/* 270 */     this.z -= $$0.z;
/*     */   }
/*     */   
/*     */   public boolean clamp(double $$0, double $$1, double $$2, double $$3) {
/* 274 */     boolean $$4 = false;
/*     */     
/* 276 */     if (this.x < $$0) {
/* 277 */       this.x = $$0;
/* 278 */       $$4 = true;
/* 279 */     } else if (this.x > $$2) {
/* 280 */       this.x = $$2;
/* 281 */       $$4 = true;
/*     */     } 
/*     */     
/* 284 */     if (this.z < $$1) {
/* 285 */       this.z = $$1;
/* 286 */       $$4 = true;
/* 287 */     } else if (this.z > $$3) {
/* 288 */       this.z = $$3;
/* 289 */       $$4 = true;
/*     */     } 
/*     */     
/* 292 */     return $$4;
/*     */   }
/*     */   
/*     */   public int getSpawnY(BlockGetter $$0, int $$1) {
/* 296 */     BlockPos.MutableBlockPos $$2 = new BlockPos.MutableBlockPos(this.x, ($$1 + 1), this.z);
/* 297 */     boolean $$3 = $$0.getBlockState((BlockPos)$$2).isAir();
/* 298 */     $$2.move(Direction.DOWN);
/* 299 */     boolean $$4 = $$0.getBlockState((BlockPos)$$2).isAir();
/* 300 */     while ($$2.getY() > $$0.getMinBuildHeight()) {
/* 301 */       $$2.move(Direction.DOWN);
/* 302 */       boolean $$5 = $$0.getBlockState((BlockPos)$$2).isAir();
/*     */       
/* 304 */       if (!$$5 && $$4 && $$3) {
/* 305 */         return $$2.getY() + 1;
/*     */       }
/* 307 */       $$3 = $$4;
/* 308 */       $$4 = $$5;
/*     */     } 
/*     */     
/* 311 */     return $$1 + 1;
/*     */   }
/*     */   
/*     */   public boolean isSafe(BlockGetter $$0, int $$1) {
/* 315 */     BlockPos $$2 = BlockPos.containing(this.x, (getSpawnY($$0, $$1) - 1), this.z);
/* 316 */     BlockState $$3 = $$0.getBlockState($$2);
/* 317 */     return ($$2.getY() < $$1 && !$$3.liquid() && !$$3.is(BlockTags.FIRE));
/*     */   }
/*     */   
/*     */   public void randomize(RandomSource $$0, double $$1, double $$2, double $$3, double $$4) {
/* 321 */     this.x = Mth.nextDouble($$0, $$1, $$3);
/* 322 */     this.z = Mth.nextDouble($$0, $$2, $$4);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\SpreadPlayersCommand$Position.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */