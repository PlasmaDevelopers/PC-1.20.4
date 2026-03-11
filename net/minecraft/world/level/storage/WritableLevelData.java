/*    */ package net.minecraft.world.level.storage;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ 
/*    */ public interface WritableLevelData extends LevelData {
/*    */   void setXSpawn(int paramInt);
/*    */   
/*    */   void setYSpawn(int paramInt);
/*    */   
/*    */   void setZSpawn(int paramInt);
/*    */   
/*    */   void setSpawnAngle(float paramFloat);
/*    */   
/*    */   default void setSpawn(BlockPos $$0, float $$1) {
/* 15 */     setXSpawn($$0.getX());
/* 16 */     setYSpawn($$0.getY());
/* 17 */     setZSpawn($$0.getZ());
/* 18 */     setSpawnAngle($$1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\WritableLevelData.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */