/*    */ package net.minecraft.world.level.lighting;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.SectionPos;
/*    */ import net.minecraft.world.level.ChunkPos;
/*    */ 
/*    */ public interface LightEventListener {
/*    */   void checkBlock(BlockPos paramBlockPos);
/*    */   
/*    */   boolean hasLightWork();
/*    */   
/*    */   int runLightUpdates();
/*    */   
/*    */   default void updateSectionStatus(BlockPos $$0, boolean $$1) {
/* 15 */     updateSectionStatus(SectionPos.of($$0), $$1);
/*    */   }
/*    */   
/*    */   void updateSectionStatus(SectionPos paramSectionPos, boolean paramBoolean);
/*    */   
/*    */   void setLightEnabled(ChunkPos paramChunkPos, boolean paramBoolean);
/*    */   
/*    */   void propagateLightSources(ChunkPos paramChunkPos);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\lighting\LightEventListener.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */