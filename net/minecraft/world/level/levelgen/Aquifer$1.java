/*    */ package net.minecraft.world.level.levelgen;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.world.level.block.state.BlockState;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ class null
/*    */   implements Aquifer
/*    */ {
/*    */   @Nullable
/*    */   public BlockState computeSubstance(DensityFunction.FunctionContext $$0, double $$1) {
/* 65 */     if ($$1 > 0.0D) {
/* 66 */       return null;
/*    */     }
/* 68 */     return fluidRule.computeFluid($$0.blockX(), $$0.blockY(), $$0.blockZ()).at($$0.blockY());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldScheduleFluidUpdate() {
/* 73 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\Aquifer$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */