/*     */ package net.minecraft.world.level.block;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.LevelAccessor;
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
/*     */ public interface SpreadConfig
/*     */ {
/*     */   @Nullable
/*     */   BlockState getStateForPlacement(BlockState paramBlockState, BlockGetter paramBlockGetter, BlockPos paramBlockPos, Direction paramDirection);
/*     */   
/*     */   boolean canSpreadInto(BlockGetter paramBlockGetter, BlockPos paramBlockPos, MultifaceSpreader.SpreadPos paramSpreadPos);
/*     */   
/*     */   default MultifaceSpreader.SpreadType[] getSpreadTypes() {
/* 112 */     return MultifaceSpreader.DEFAULT_SPREAD_ORDER;
/*     */   }
/*     */   
/*     */   default boolean hasFace(BlockState $$0, Direction $$1) {
/* 116 */     return MultifaceBlock.hasFace($$0, $$1);
/*     */   }
/*     */   
/*     */   default boolean isOtherBlockValidAsSource(BlockState $$0) {
/* 120 */     return false;
/*     */   }
/*     */   
/*     */   default boolean canSpreadFrom(BlockState $$0, Direction $$1) {
/* 124 */     return (isOtherBlockValidAsSource($$0) || hasFace($$0, $$1));
/*     */   }
/*     */   
/*     */   default boolean placeBlock(LevelAccessor $$0, MultifaceSpreader.SpreadPos $$1, BlockState $$2, boolean $$3) {
/* 128 */     BlockState $$4 = getStateForPlacement($$2, (BlockGetter)$$0, $$1.pos(), $$1.face());
/* 129 */     if ($$4 != null) {
/*     */       
/* 131 */       if ($$3) {
/* 132 */         $$0.getChunk($$1.pos()).markPosForPostprocessing($$1.pos());
/*     */       }
/* 134 */       return $$0.setBlock($$1.pos(), $$4, 2);
/*     */     } 
/* 136 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\MultifaceSpreader$SpreadConfig.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */