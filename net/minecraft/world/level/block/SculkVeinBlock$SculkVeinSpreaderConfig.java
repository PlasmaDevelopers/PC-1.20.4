/*     */ package net.minecraft.world.level.block;
/*     */ 
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class SculkVeinSpreaderConfig
/*     */   extends MultifaceSpreader.DefaultSpreaderConfig
/*     */ {
/*     */   private final MultifaceSpreader.SpreadType[] spreadTypes;
/*     */   
/*     */   public SculkVeinSpreaderConfig(MultifaceSpreader.SpreadType... $$0) {
/* 191 */     super(paramSculkVeinBlock);
/* 192 */     this.spreadTypes = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean stateCanBeReplaced(BlockGetter $$0, BlockPos $$1, BlockPos $$2, Direction $$3, BlockState $$4) {
/* 197 */     BlockState $$5 = $$0.getBlockState($$2.relative($$3));
/*     */ 
/*     */ 
/*     */     
/* 201 */     if ($$5.is(Blocks.SCULK) || $$5.is(Blocks.SCULK_CATALYST) || $$5.is(Blocks.MOVING_PISTON)) {
/* 202 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 207 */     if ($$1.distManhattan((Vec3i)$$2) == 2) {
/* 208 */       BlockPos $$6 = $$1.relative($$3.getOpposite());
/* 209 */       if ($$0.getBlockState($$6).isFaceSturdy($$0, $$6, $$3)) {
/* 210 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 214 */     FluidState $$7 = $$4.getFluidState();
/* 215 */     if (!$$7.isEmpty() && !$$7.is((Fluid)Fluids.WATER)) {
/* 216 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 220 */     if ($$4.is(BlockTags.FIRE)) {
/* 221 */       return false;
/*     */     }
/*     */     
/* 224 */     return ($$4.canBeReplaced() || super.stateCanBeReplaced($$0, $$1, $$2, $$3, $$4));
/*     */   }
/*     */ 
/*     */   
/*     */   public MultifaceSpreader.SpreadType[] getSpreadTypes() {
/* 229 */     return this.spreadTypes;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOtherBlockValidAsSource(BlockState $$0) {
/* 234 */     return !$$0.is(Blocks.SCULK_VEIN);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\SculkVeinBlock$SculkVeinSpreaderConfig.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */