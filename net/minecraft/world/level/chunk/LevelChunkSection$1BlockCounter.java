/*     */ package net.minecraft.world.level.chunk;
/*     */ 
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class BlockCounter
/*     */   implements PalettedContainer.CountConsumer<BlockState>
/*     */ {
/*     */   public int nonEmptyBlockCount;
/*     */   public int tickingBlockCount;
/*     */   public int tickingFluidCount;
/*     */   
/*     */   public void accept(BlockState $$0, int $$1) {
/* 121 */     FluidState $$2 = $$0.getFluidState();
/*     */     
/* 123 */     if (!$$0.isAir()) {
/* 124 */       this.nonEmptyBlockCount += $$1;
/* 125 */       if ($$0.isRandomlyTicking()) {
/* 126 */         this.tickingBlockCount += $$1;
/*     */       }
/*     */     } 
/* 129 */     if (!$$2.isEmpty()) {
/* 130 */       this.nonEmptyBlockCount += $$1;
/* 131 */       if ($$2.isRandomlyTicking())
/* 132 */         this.tickingFluidCount += $$1; 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\chunk\LevelChunkSection$1BlockCounter.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */