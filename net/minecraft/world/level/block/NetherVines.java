/*    */ package net.minecraft.world.level.block;
/*    */ 
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class NetherVines {
/*    */   private static final double BONEMEAL_GROW_PROBABILITY_DECREASE_RATE = 0.826D;
/*    */   public static final double GROW_PER_TICK_PROBABILITY = 0.1D;
/*    */   
/*    */   public static boolean isValidGrowthState(BlockState $$0) {
/* 11 */     return $$0.isAir();
/*    */   }
/*    */   
/*    */   public static int getBlocksToGrowWhenBonemealed(RandomSource $$0) {
/* 15 */     double $$1 = 1.0D;
/* 16 */     int $$2 = 0;
/* 17 */     while ($$0.nextDouble() < $$1) {
/* 18 */       $$1 *= 0.826D;
/* 19 */       $$2++;
/*    */     } 
/* 21 */     return $$2;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\NetherVines.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */