/*    */ package net.minecraft.world.level.block.entity;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.level.BaseSpawner;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.SpawnData;
/*    */ import net.minecraft.world.level.block.Blocks;
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
/*    */ class null
/*    */   extends BaseSpawner
/*    */ {
/*    */   public void broadcastEvent(Level $$0, BlockPos $$1, int $$2) {
/* 23 */     $$0.blockEvent($$1, Blocks.SPAWNER, $$2, 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setNextSpawnData(@Nullable Level $$0, BlockPos $$1, SpawnData $$2) {
/* 28 */     super.setNextSpawnData($$0, $$1, $$2);
/* 29 */     if ($$0 != null) {
/* 30 */       BlockState $$3 = $$0.getBlockState($$1);
/* 31 */       $$0.sendBlockUpdated($$1, $$3, $$3, 4);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\SpawnerBlockEntity$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */