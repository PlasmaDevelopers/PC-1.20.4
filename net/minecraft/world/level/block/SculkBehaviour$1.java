/*    */ package net.minecraft.world.level.block;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.material.Fluid;
/*    */ import net.minecraft.world.level.material.Fluids;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class null
/*    */   implements SculkBehaviour
/*    */ {
/*    */   public boolean attemptSpreadVein(LevelAccessor $$0, BlockPos $$1, BlockState $$2, @Nullable Collection<Direction> $$3, boolean $$4) {
/* 42 */     if ($$3 == null) {
/* 43 */       return (((SculkVeinBlock)Blocks.SCULK_VEIN).getSameSpaceSpreader().spreadAll($$0.getBlockState($$1), $$0, $$1, $$4) > 0L);
/*    */     }
/* 45 */     if (!$$3.isEmpty()) {
/* 46 */       if ($$2.isAir() || $$2.getFluidState().is((Fluid)Fluids.WATER)) {
/* 47 */         return SculkVeinBlock.regrow($$0, $$1, $$2, $$3);
/*    */       }
/* 49 */       return false;
/*    */     } 
/* 51 */     return super.attemptSpreadVein($$0, $$1, $$2, $$3, $$4);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int attemptUseCharge(SculkSpreader.ChargeCursor $$0, LevelAccessor $$1, BlockPos $$2, RandomSource $$3, SculkSpreader $$4, boolean $$5) {
/* 57 */     return ($$0.getDecayDelay() > 0) ? $$0.getCharge() : 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public int updateDecayDelay(int $$0) {
/* 62 */     return Math.max($$0 - 1, 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\SculkBehaviour$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */