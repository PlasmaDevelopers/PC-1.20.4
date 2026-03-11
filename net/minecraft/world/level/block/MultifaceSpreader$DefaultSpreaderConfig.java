/*     */ package net.minecraft.world.level.block;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
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
/*     */ public class DefaultSpreaderConfig
/*     */   implements MultifaceSpreader.SpreadConfig
/*     */ {
/*     */   protected MultifaceBlock block;
/*     */   
/*     */   public DefaultSpreaderConfig(MultifaceBlock $$0) {
/* 144 */     this.block = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockState getStateForPlacement(BlockState $$0, BlockGetter $$1, BlockPos $$2, Direction $$3) {
/* 150 */     return this.block.getStateForPlacement($$0, $$1, $$2, $$3);
/*     */   }
/*     */   
/*     */   protected boolean stateCanBeReplaced(BlockGetter $$0, BlockPos $$1, BlockPos $$2, Direction $$3, BlockState $$4) {
/* 154 */     return ($$4.isAir() || $$4.is(this.block) || ($$4.is(Blocks.WATER) && $$4.getFluidState().isSource()));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSpreadInto(BlockGetter $$0, BlockPos $$1, MultifaceSpreader.SpreadPos $$2) {
/* 159 */     BlockState $$3 = $$0.getBlockState($$2.pos());
/* 160 */     return (stateCanBeReplaced($$0, $$1, $$2.pos(), $$2.face(), $$3) && this.block.isValidStateForPlacement($$0, $$3, $$2.pos(), $$2.face()));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\MultifaceSpreader$DefaultSpreaderConfig.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */