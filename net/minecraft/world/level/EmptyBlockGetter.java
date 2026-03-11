/*    */ package net.minecraft.world.level;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.material.FluidState;
/*    */ import net.minecraft.world.level.material.Fluids;
/*    */ 
/*    */ public enum EmptyBlockGetter
/*    */   implements BlockGetter {
/* 13 */   INSTANCE;
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public BlockEntity getBlockEntity(BlockPos $$0) {
/* 18 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState getBlockState(BlockPos $$0) {
/* 23 */     return Blocks.AIR.defaultBlockState();
/*    */   }
/*    */ 
/*    */   
/*    */   public FluidState getFluidState(BlockPos $$0) {
/* 28 */     return Fluids.EMPTY.defaultFluidState();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMinBuildHeight() {
/* 33 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getHeight() {
/* 38 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\EmptyBlockGetter.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */