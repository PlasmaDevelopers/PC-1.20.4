/*    */ package net.minecraft.world.level;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface LevelWriter
/*    */ {
/*    */   boolean setBlock(BlockPos paramBlockPos, BlockState paramBlockState, int paramInt1, int paramInt2);
/*    */   
/*    */   default boolean setBlock(BlockPos $$0, BlockState $$1, int $$2) {
/* 15 */     return setBlock($$0, $$1, $$2, 512);
/*    */   }
/*    */ 
/*    */   
/*    */   boolean removeBlock(BlockPos paramBlockPos, boolean paramBoolean);
/*    */   
/*    */   default boolean destroyBlock(BlockPos $$0, boolean $$1) {
/* 22 */     return destroyBlock($$0, $$1, null);
/*    */   }
/*    */ 
/*    */   
/*    */   default boolean destroyBlock(BlockPos $$0, boolean $$1, @Nullable Entity $$2) {
/* 27 */     return destroyBlock($$0, $$1, $$2, 512);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   boolean destroyBlock(BlockPos paramBlockPos, boolean paramBoolean, @Nullable Entity paramEntity, int paramInt);
/*    */ 
/*    */   
/*    */   default boolean addFreshEntity(Entity $$0) {
/* 36 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\LevelWriter.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */